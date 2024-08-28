package com.cxk.model.domain.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;


@Data
public class StatisticsResponse {

    List<BigDecimal> incomeList;
    List<BigDecimal> expenseList;

    List<HashMap<String,BigDecimal>> expenseMapList;

    //分类列表
    List<String> categoryList;
    List<BigDecimal> categoryListMoney;
}
