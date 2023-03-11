package com.cxk.model.domain.response;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class StatisticResponse {

    //消费趋势
    List<HashMap<String, BigDecimal>> yearAmount;

    //月份消费趋势
    List<HashMap<String, BigDecimal>> monthAmount;


    //年份消费占比
    List<HashMap<String, BigDecimal>> yearProportion;


    //月份消费占比
    List<HashMap<String, BigDecimal>> monthProportion;


}
