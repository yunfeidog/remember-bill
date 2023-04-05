package com.cxk.model.domain.request;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 */
@Data
public class BillAddRequest {



    /**
     * 账单分类 分类(数组)(餐饮/购物/交通/住房/娱乐/医疗/通讯/人情/其他)
     */
    private String billCategory;

    /**
     * 账单金额
     */
    private BigDecimal billAmount;

    /**
     * 账单日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")

    private Date billDate;

    /**
     * 账单商家
     */
    private String billShopkeeper;

    /**
     * 账单备注
     */
    private String billRemark;


}
