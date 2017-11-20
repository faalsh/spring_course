package com.example.demo;

import com.example.demo.backend.service.I18nService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private I18nService i18nService;

    @Test
    public void testMessageByLocaleService() throws Exception {
        String expectedResult = "Fahad";
        String messageId = "dev.name";
        String actual = i18nService.getMessage(messageId);
        Assert.assertEquals("no match", expectedResult, actual);
    }

}
