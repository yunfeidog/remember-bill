package com.cxk.service;

import com.cxk.model.domain.response.StatisticsByMonthResponse;
import com.cxk.model.domain.response.StatisticsByYearResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@SpringBootTest
@Slf4j
class BillServiceTest {


    @Autowired
    private BillService billService;

    @Test
    void getBillByMonth() {
        String date = "2023-02";
        Integer userId = 17;
        billService.getBillByMonth(date, userId).forEach(System.out::println);
    }

    @Test
    void statisticsByYear() {
        String date = "2023";
        Integer userId = 17;
        StatisticsByYearResponse statisticsByYearResponse = billService.statisticsByYear(date, userId);
        List<BigDecimal> incomeList = statisticsByYearResponse.getIncomeList();
        List<BigDecimal> expenseList = statisticsByYearResponse.getExpenseList();
        for(int i=0;i<incomeList.size();i++){
            log.info("月份：{},收入：{},支出：{}",i+1,incomeList.get(i),expenseList.get(i));
        }

    }

    @Test
    void statisticsByMonth() {
        String date = "2023-02";
        Integer userId = 17;
        StatisticsByMonthResponse statisticsByMonthResponse = billService.statisticsByMonth(date, userId);
        List<BigDecimal> incomeList = statisticsByMonthResponse.getIncomeList();
        List<BigDecimal> expenseList = statisticsByMonthResponse.getExpenseList();
        for(int i=0;i<incomeList.size();i++){
            log.info("日期：{},收入：{},支出：{}",i+1,incomeList.get(i),expenseList.get(i));
        }

    }

    @Test
    public void test(){
        Date date = new Date();
        //获取月份
        int month = date.getMonth() + 1;
        System.out.println("month = " + month);
    }
}