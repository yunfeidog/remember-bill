package com.cxk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxk.model.domain.request.BillAddRequest;
import com.cxk.model.domain.response.DateOrCategoryResponse;
import com.cxk.model.domain.response.StatisticsResponse;
import com.cxk.model.entity.Bill;

import java.util.Date;
import java.util.List;

/**
* @description 针对表【tb_bill(账单表)】的数据库操作Service
* @createDate 2023-02-10 16:14:48
*/
public interface BillService extends IService<Bill> {

    boolean addBill(BillAddRequest bill, Integer userId);

    List<BillAddRequest> getBillList(Integer userId);


    Bill getOldBill(Integer id);


    boolean updateBill(Bill bill, Bill oldBill);

    /**
     * 根据日期或分类查询账单
     * @param category 分类
     * @param date 日期 yyyy-MM-dd
     * @param userId 用户id
     * @return 响应给前端的数据
     */
    List<DateOrCategoryResponse> getBillListByDateOrCategory(String category, Date date, Integer userId);


    List<Bill> getBillByMonth(String month, Integer userId);


    StatisticsResponse statisticsByYear(String date, Integer userId);

    StatisticsResponse statisticsByMonth(String date, Integer userId);
}
