package com.champion.hotel.mapper;

import com.champion.hotel.entity.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xuwenhan
 * @version v1.0
 * @create 2020/8/3
 */
@Mapper
public interface RecordMapper {

    int insert(Record record);

    List<Record> listRecords(@Param("roomId") String roomId, @Param("state") String state,
        @Param("name") String name, @Param("idCard") String idCard, @Param("predictOutDateOrder") String predictOutDateOrder);

    Record getRecordById(Integer id);

    int update(Record record);

    int scheduleUpdateState();

    int getInCountByIdCard(String idCard);
}
