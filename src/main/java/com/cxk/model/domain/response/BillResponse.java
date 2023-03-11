package com.cxk.model.domain.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 */
@Data
public class BillResponse {
    private Integer id;
    private String typeName;

    /**
     * 0-支出 1-收入
     */
    private Integer payType;
    private BigDecimal amount;

    private String shop;
    private Date date;
    private String remark;
}
