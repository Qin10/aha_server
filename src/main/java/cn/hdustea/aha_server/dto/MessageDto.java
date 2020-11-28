package cn.hdustea.aha_server.dto;

import lombok.Data;

/**
 * 站内信DTO
 *
 * @author STEA_YY
 **/
@Data
public class MessageDto {
    private String reveiverPhone;
    private Integer type;
    private int textId;
    private String content;
}