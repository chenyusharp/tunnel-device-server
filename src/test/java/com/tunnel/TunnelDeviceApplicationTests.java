package com.tunnel;

import com.aliyuncs.exceptions.ClientException;
import com.tunnel.common.utils.DayuSms;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TunnelDeviceApplicationTests {

    @Autowired
    private DayuSms dayuSms;

    @Test
    public void contextLoads() {
        Map map = new HashMap<>();
        map.put("code","123");
        dayuSms.sendSms("18663868251","SMS_138195141",map);
    }

}
