package com.cxk;

import com.cxk.mapper.UserMapper;
import com.cxk.model.entity.Bill;
import com.cxk.service.BillService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class KeepAccountMainTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private BillService billService;

    @Test
    public void testSelect() {
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        for (int i = 0; i < integers.size(); i++) {
            integers.set(i, integers.get(i) + 1);
        }
        System.out.println("integers = " + integers);

    }

    @Test
    void testGetBillByMonth() {
        String month = "2023-02";
        Integer userId=17;
        List<Bill> billList = billService.getBillByMonth(month, userId);
        for (Bill bill : billList) {
            System.out.println("bill = " + bill);
        }
    }




}
