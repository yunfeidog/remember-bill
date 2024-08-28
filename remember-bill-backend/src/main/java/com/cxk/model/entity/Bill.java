package com.cxk.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 账单表
 * @TableName tb_bill
 */
@TableName(value ="tb_bill")
@Data
public class Bill implements Serializable {
    /**
     * 账单id
     */
    @TableId(type = IdType.AUTO)
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

    /**
     * 是否被删除 0-未删除 1-已删除
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}