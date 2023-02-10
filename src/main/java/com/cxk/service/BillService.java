package com.cxk.service;

import com.cxk.model.domain.Bill;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cxk.model.domain.request.BillAddRequest;

import java.util.List;

/**
* @author houyunfei
* @description 针对表【tb_bill(账单表)】的数据库操作Service
* @createDate 2023-02-10 16:14:48
*/
public interface BillService extends IService<Bill> {

    boolean addBill(BillAddRequest bill, Integer userId);

    List<BillAddRequest> getBillList(Integer userId);


    Bill getOldBill(Integer id);


    boolean updateBill(Bill bill, Bill oldBill);
}
