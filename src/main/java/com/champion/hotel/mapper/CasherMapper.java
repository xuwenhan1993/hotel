package com.champion.hotel.mapper;

import com.champion.hotel.entity.Casher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author xuwenhan
 * @version v1.0
 * @create 2020/8/7
 */
@Mapper
public interface CasherMapper {

    int insert(Casher casher);

    List<Casher> getList(@Param("groupBy") String groupBy, @Param("start") Date start, @Param("end") Date end);
}
