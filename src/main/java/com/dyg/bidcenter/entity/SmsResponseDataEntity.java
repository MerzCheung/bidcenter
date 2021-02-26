package com.dyg.bidcenter.entity;

import lombok.Data;

/**
 * @author merz
 * @Description:
 */
@Data
public class SmsResponseDataEntity {

    private String Message;
    private String RequestId;
    private String Code;
    private String BizId;
}
