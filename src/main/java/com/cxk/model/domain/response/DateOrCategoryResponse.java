package com.cxk.model.domain.response;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 响应给前端的数据
 */
@Data
public class DateOrCategoryResponse {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private BigDecimal income;

    private BigDecimal expense;

    private List<BillResponse> bills;



}
