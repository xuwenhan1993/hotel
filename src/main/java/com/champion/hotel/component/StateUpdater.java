package com.champion.hotel.component;

import com.champion.hotel.mapper.RecordMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author xuwenhan
 * @version v1.0
 * @create 2020/8/6
 */
@Component
public class StateUpdater {

    private RecordMapper recordMapper;

    public StateUpdater(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") RecordMapper recordMapper) {
        this.recordMapper = recordMapper;
    }

    @Scheduled(cron = "0 0 12 * * ?")
    @PostConstruct
    public void updateState() {
        recordMapper.scheduleUpdateState();
    }

}
