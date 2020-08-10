package com.champion.hotel;

import com.champion.hotel.constant.Constants;
import com.champion.hotel.entity.Casher;
import com.champion.hotel.entity.Record;
import com.champion.hotel.entity.Room;
import com.champion.hotel.mapper.CasherMapper;
import com.champion.hotel.mapper.RecordMapper;
import com.champion.hotel.mapper.RoomMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RunWith(SpringRunner.class)
@SpringBootTest
public class HotelApplicationTests {

    @Autowired
    RecordMapper recordMapper;

    @Autowired
    RoomMapper roomMapper;

    @Autowired
    CasherMapper casherMapper;

    @Test
    public void contextLoads() {
        // '%Y-%m-%d'
        List<Casher> list = casherMapper.getList("%Y-%m-%d", null, null);
        System.out.println(list);
//        Casher casher = new Casher();
//        casher.setAccount(new BigDecimal(200));
//        casher.setDeposit(new BigDecimal(80));
//        casher.setOperateDate(new Date());
//        casherMapper.insert(casher);
    }

}
