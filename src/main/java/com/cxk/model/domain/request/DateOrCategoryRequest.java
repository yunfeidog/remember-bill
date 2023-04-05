package com.cxk.model.domain.request;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 日期或分类查询请求
 */
@Data
public class DateOrCategoryRequest {

        /**
         * xxxx-xx
         */
        private String date;

        private String category;
}
