package com.champion.hotel.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.PropertyVetoException;
import java.util.Date;

/**
 * @author xuwenhan
 * @version v1.0
 * @create 2020/8/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Record {
    private Integer id;
    private String idCard;
    private String name;
    // 入住日期
    private Date inDate;
    // 预计离店
    private Date predictOutDate;
    // 实际离店
    private Date actualOutDate;

    private Integer days;
    // 预交房费
    private Integer account;
    // 押金
    private Integer deposit;
    // 返还押金
    private Integer backDeposit;
    private Integer rId;
    // 补缴房费
    private Integer moreAccount;
    // 退回房费
    private Integer backAccount;
    // 状态
    private String state;
    private String gender;
    private String phone;
    // 是否包房
    private String wholeFlg;
    // 单价(床／包间)
    private int price;

    private Room room;
}
