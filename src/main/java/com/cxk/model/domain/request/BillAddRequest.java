package com.cxk.model.domain.request;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class BillAddRequest {
    private Integer billType;// 账单类型 0-收入 1-支出

    private List<String> billCategory; //账单分类 分类(数组)(餐饮/购物/交通/住房/娱乐/医疗/通讯/人情/其他)

    private BigDecimal billAmount; //账单金额

    @DateTimeFormat(pattern = "yyyy-MM-dd")

    private Date billDate; //账单日期

    private String billShopkeeper; //账单商家

    private String billRemark;  //账单备注


}
