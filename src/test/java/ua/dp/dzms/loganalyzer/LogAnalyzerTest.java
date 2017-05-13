package ua.dp.dzms.loganalyzer;

import org.junit.Assert;
import org.junit.Test;
import ua.dp.dzms.loganalyzer.entity.HttpMethod;
import ua.dp.dzms.loganalyzer.entity.LogToken;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;


public class LogAnalyzerTest {
    @Test
    public void parseFile(){
        LogAnalyzer logAnalyzer = new LogAnalyzer();
        LocalDateTime timeFrom = LocalDateTime.of(2001, 3, 10, 10, 0, 0);
        LocalDateTime timeTo = LocalDateTime.of(2017, 5, 27, 15, 33, 0);
        ArrayList list = (ArrayList) logAnalyzer.parseFile("src/main/resources/LogAnalyzer/access_logTest", timeFrom, timeTo);
        Assert.assertEquals(10, list.size());
        Assert.assertEquals(HttpMethod.GET, ((LogToken)list.get(0)).getMethod());
        Assert.assertEquals(HttpMethod.POST, ((LogToken)list.get(1)).getMethod());

        timeFrom = LocalDateTime.of(2004, 3, 8, 17, 1, 54);
        timeTo = LocalDateTime.of(2004, 3, 14, 15, 33, 0);
        list = (ArrayList) logAnalyzer.parseFile("src/main/resources/LogAnalyzer/access_logTest", timeFrom, timeTo);
        Assert.assertEquals(5, list.size());
        Assert.assertEquals(LocalDateTime.of(2004, 3, 13, 16, 29, 16), ((LogToken)list.get(4)).getLocalDateTime());
    }

}