package com.champion.hotel.service;

import com.champion.hotel.constant.Constants;
import com.champion.hotel.entity.Casher;
import com.champion.hotel.entity.Record;
import com.champion.hotel.entity.Room;
import com.champion.hotel.mapper.CasherMapper;
import com.champion.hotel.mapper.RecordMapper;
import com.champion.hotel.mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author xuwenhan
 * @version v1.0
 * @create 2020/8/4
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class RecordService {

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private CasherMapper casherMapper;

    public List<Record> listRecords(String roomId, String state, String name, String idCard, String predictOutDateOrder) {
        return recordMapper.listRecords(roomId, state, name, idCard, predictOutDateOrder);
    }

    public Record getRecordById(Integer id) {
        return recordMapper.getRecordById(id);
    }

    /**
     * 续住
     * @param record
     * @return
     */
    public int update(Record record, String suppleAccount) {
        // 更新入住记录
        recordMapper.update(record);
        // 记账
        Casher casher = new Casher();
        casher.setMoreAccount(new BigDecimal(suppleAccount));
        casher.setRecordId(record.getId());
        casher.setOperateDate(new Date());
        casherMapper.insert(casher);
        return 0;
    }

    /**
     * 退房
     * @param record
     */
    @Transactional
    public void outOfService(Record record) {
        // 更新入住记录
        record.setState(Constants.OUT);
        recordMapper.update(record);
        // 更新库存
        Integer id = record.getRId();
        Room room = roomMapper.getRoomById(id);
        if (Constants.YES.equals(record.getWholeFlg())) {
            // 包房
            room.setRemainNum(room.getBedNum());
        } else {
            room.setRemainNum(room.getRemainNum() + 1);
        }
        roomMapper.update(room);
        // 性别
        if (room.getBedNum().equals(room.getRemainNum())) {
            roomMapper.setCurrentGenderNull(id);
        }
        // 记账
        Casher casher = new Casher();
        casher.setMoreAccount(new BigDecimal(record.getMoreAccount()));
        casher.setBackAccount(new BigDecimal(record.getBackAccount()));
        casher.setBackDeposit(new BigDecimal(record.getDeposit()));
        casher.setRecordId(record.getId());
        casher.setOperateDate(record.getActualOutDate());
        casherMapper.insert(casher);
    }

    /**
     * 换房间
     * @param record
     */
    public void changeRoom(Record record) {
        // 先查库里的原房间的信息
        Record oldRecord = recordMapper.getRecordById(record.getId());
        Room oldRoom = oldRecord.getRoom();
        oldRoom.setRemainNum(oldRoom.getRemainNum() + 1);
        if (oldRoom.getRemainNum().equals(oldRoom.getBedNum())) {
            // 一个人都没有
            roomMapper.setCurrentGenderNull(oldRoom.getId());
        }
        oldRoom.setCurrentGender(null);
        roomMapper.update(oldRoom);
        // 保存record
        recordMapper.update(record);
        // 新房间库存 -1
        Room newRoom = roomMapper.getRoomById(record.getRId());
        if (newRoom.getRemainNum().equals(oldRoom.getBedNum())) {
            newRoom.setCurrentGender(record.getGender());
        }
        newRoom.setRemainNum(newRoom.getRemainNum() - 1);
        roomMapper.update(newRoom);
    }


    /**
     * 校验该身份证是否在住／欠费
     * @param idCard
     * @return
     */
    public boolean checkIdCard(String idCard) {
        return recordMapper.getInCountByIdCard(idCard) > 0;
    }
}
