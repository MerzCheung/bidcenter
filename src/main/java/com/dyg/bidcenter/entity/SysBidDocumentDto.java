package com.dyg.bidcenter.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @author merz
 * @Description:
 */
@Data
public class SysBidDocumentDto {
    private Integer id;
    private Integer bidId;
    private String companyName;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    private String time;
    private String fileName;
    private String fileUrl;
    private String number;
}
