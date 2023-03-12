package com.cxk.model.domain.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
public class DateRequest {

    private String date;
}
