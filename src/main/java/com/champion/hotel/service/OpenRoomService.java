package com.champion.hotel.service;

import com.champion.hotel.constant.Constants;
import com.champion.hotel.entity.Casher;
import com.champion.hotel.entity.Record;
import com.champion.hotel.entity.Room;
import com.champion.hotel.mapper.CasherMapper;
import com.champion.hotel.mapper.RecordMapper;
import com.champion.hotel.mapper.RoomMapper;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author xuwenhan
 * @version v1.0
 * @create 2020/8/3
 */
@Service
public class OpenRoomService {

    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private CasherMapper casherMapper;

    @Transactional
    public int openRoom(Record record) {
        // 插入入住记录
        record.setState(Constants.IN);
        recordMapper.insert(record);
        // 更新库存
        Room room = roomMapper.getRoomById(record.getRId());
        if (room.getBedNum().equals(room.getRemainNum())) {
            room.setCurrentGender(record.getGender());
        }
        if (Constants.YES.equals(record.getWholeFlg())) {
            // 包房
            room.setRemainNum(0);
        } else {
            room.setRemainNum(room.getRemainNum() - 1);
        }
        roomMapper.update(room);
        // 记账
        Casher casher = new Casher();
        casher.setAccount(new BigDecimal(record.getAccount()));
        casher.setDeposit(new BigDecimal(record.getDeposit()));
        casher.setRecordId(record.getId());
        casher.setOperateDate(record.getInDate());
        casherMapper.insert(casher);
        return 0;
    }
}
