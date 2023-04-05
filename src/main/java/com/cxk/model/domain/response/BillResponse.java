package com.cxk.model.domain.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 */
@Data
public class BillResponse {
    private Integer id;
    private String category;


    private BigDecimal money;

    private String shop;
    private Date billDate;
    private String remark;
}
