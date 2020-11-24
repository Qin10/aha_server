package cn.hdustea.aha_server.dto;

import lombok.Data;

/**
 * 文档转换相关信息DTO
 *
 * @author STEA_YY
 **/
@Data
public class DocumentConvertInfoDto {
    private int projectResourceId;
    private String taskId;
    private String srcFilename;
    private String targetFilePath;
}
