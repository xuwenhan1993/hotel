package com.champion.hotel.mapper;

import com.champion.hotel.entity.Room;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author xuwenhan
 * @version v1.0
 * @create 2020/8/2
 */
@Mapper
public interface RoomMapper {

    int insert(Room room);

    List<Room> listRooms(String roomId);

    Room getRoomById(Integer id);

    int update(Room room);

    int delete(Integer id);

    int setCurrentGenderNull(Integer id);

    Room getRoomByRoomId(String roomId);
}
