package com.cxk.service;

import com.cxk.model.domain.response.StatisticsResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        StatisticsResponse statisticsResponse = billService.statisticsByYear(date, userId);
        List<BigDecimal> incomeList = statisticsResponse.getIncomeList();
        List<BigDecimal> expenseList = statisticsResponse.getExpenseList();
        List<String> categoryList = statisticsResponse.getCategoryList();
        List<BigDecimal> categoryListMoney = statisticsResponse.getCategoryListMoney();
        for (int i = 0; i < categoryList.size(); i++) {
            log.info("分类：{},金额：{}", categoryList.get(i), categoryListMoney.get(i));
        }
        for (int i = 0; i < incomeList.size(); i++) {
            log.info("月份：{},收入：{},支出：{}", i + 1, incomeList.get(i), expenseList.get(i));
        }

    }

    @Test
    void statisticsByMonth() {
        String date = "2023-03";
        Integer userId = 17;
        StatisticsResponse statisticsResponse = billService.statisticsByMonth(date, userId);
        List<BigDecimal> incomeList = statisticsResponse.getIncomeList();
        List<BigDecimal> expenseList = statisticsResponse.getExpenseList();
        for (int i = 0; i < incomeList.size(); i++) {
            log.info("日期：{},收入：{},支出：{}", i + 1, incomeList.get(i), expenseList.get(i));
        }

    }

    @Test
    public void test() {
        Date date = new Date();
        //获取月份
        int month = date.getMonth() + 1;
        System.out.println("month = " + month);
    }

    @Test
    void addBill() {
    }

    @Test
    void getBillList() {
    }

    @Test
    void getOldBill() {
    }

    @Test
    void updateBill() {
    }

    @Test
    void getBillListByDateOrCategory() {
    }
}