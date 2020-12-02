package cn.hdustea.aha_server.dto;

import lombok.Data;

/**
 * 文档转换相关信息DTO
 *
 * @author STEA_YY
 **/
@Data
public class DocumentConvertInfoDto {
    /**
     * 项目资源id
     */
    private int projectResourceId;
    /**
     * OSS文档转换任务id
     */
    private String taskId;
    /**
     * 转换源文件名
     */
    private String srcFilename;
    /**
     * 转换目标路径
     */
    private String targetFilePath;
}
