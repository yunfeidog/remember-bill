package com.cxk.service;

import com.cxk.model.domain.response.DateOrCategoryResponse;
import com.cxk.model.domain.response.StatisticsResponse;
import com.cxk.model.entity.Bill;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
        Bill bill = new Bill();
        bill.setUserId(1);
        bill.setMoney(new BigDecimal("200"));
        bill.setCategory("其他");
        //格式化日期
        bill.setBillDate(new Date());
        bill.setShop("蔡徐坤");
        bill.setRemark("蔡徐坤");

    }

    @Test
    void getBillList() {
        double a=0.56;
        double b=0.11;
        System.out.println(a+b);
    }

    @Test
    void getOldBill() {
    }

    @Test
    void updateBill() {
        Bill newBill = new Bill();
        Integer billId = 4;
        newBill.setId(billId);
        newBill.setUserId(1);
        newBill.setMoney(new BigDecimal("500"));
        newBill.setCategory("其他");
        boolean result = billService.updateBill(newBill, billId);
        System.out.println("result = " + result);

    }

    @Test
    void getBillListByDateOrCategory() {
        String category = "";
        String date = "2023-02";
        Integer userId = 1;
        List<DateOrCategoryResponse> billListByDateOrCategory = billService.getBillListByDateOrCategory(category, date, userId);
        billListByDateOrCategory.forEach(System.out::println);
    }

    @Test
    void handleImage() {
    }

    @Test
    void getBillBetweenDates() {
        String startDate = "2023-02-01";
        String endDate = "2023-02-04";
        Integer userId = 1;
        List<Bill> billBetweenDates = billService.getBillBetweenDates(startDate, endDate, userId);
        log.info("一共有{}条数据", billBetweenDates.size());
        billBetweenDates.forEach(System.out::println);
    }
}