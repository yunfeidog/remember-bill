package com.cxk.model.domain.response;

import lombok.Data;

@Data
public class OCRResponse {

    private String money;//金额

    private String date;//日期

    private String no ;//小票号

    private String shop;//商户

    private String shop_no;//商户号

    private String sku;//商品

}
