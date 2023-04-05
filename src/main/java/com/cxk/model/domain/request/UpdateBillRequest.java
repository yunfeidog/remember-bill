package com.cxk.model.domain.request;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UpdateBillRequest {
    /**
     * 账单id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 金额(可能小数)
     */
    private BigDecimal money;



    /**
     * 分类(数组)(餐饮/购物/交通/住房/娱乐/医疗/通讯/人情/其他)
     *收入： ['工资','奖金','兼职','理财','投资','红包','转账','退款','其他']
     * 收入：工资,奖金,兼职,理财,投资,红包,转账,退款,其他
     *支出： ['餐饮','购物','交通','住房','娱乐','医疗','通讯','学习','其他']
     * 支出：餐饮,购物,交通,住房,娱乐,医疗,通讯,学习,其他
     */
    private String category;

    /**
     * 日期(年月日)
     */
    private Date billDate;

    /**
     * 商家(可为空)
     */
    private String shop;

    /**
     * 备注(可为空)
     */
    private String remark;


}

