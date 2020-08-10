package com.champion.hotel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuwenhan
 * @version v1.0
 * @create 2020/8/2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    private Integer id;
    private String roomId;
    // 房间类型
    private String type;
    // 床位数
    private Integer bedNum;
    // 剩余床位
    private Integer remainNum;
    // 是否独立卫生间
    private String bathFlg;
    // 当前入住性别
    private String currentGender;
    private Integer price;
}
