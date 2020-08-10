package com.champion.hotel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xuwenhan
 * @version v1.0
 * @create 2020/8/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Casher {
    private Integer id;
    private BigDecimal account;
    private BigDecimal moreAccount;
    private BigDecimal backAccount;
    private BigDecimal deposit;
    private BigDecimal backDeposit;
    private Integer recordId;
    private Date operateDate;
}
