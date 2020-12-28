package cn.hdustea.aha_server.task;

import cn.hdustea.aha_server.config.AliyunOssConfig;
import cn.hdustea.aha_server.constants.RedisConstants;
import cn.hdustea.aha_server.dto.DocumentConvertInfoDto;
import cn.hdustea.aha_server.mapper.ProjectResourceMapper;
import cn.hdustea.aha_server.service.RedisService;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.imm.model.v20170906.CreateOfficeConversionTaskRequest;
import com.aliyuncs.imm.model.v20170906.CreateOfficeConversionTaskResponse;
import com.aliyuncs.imm.model.v20170906.GetOfficeConversionTaskRequest;
import com.aliyuncs.imm.model.v20170906.GetOfficeConversionTaskResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * oss文档转化任务
 *
 * @author STEA_YY
 **/
@Component
@Slf4j
public class OssDocumentConvertTask {
    @Resource
    private AliyunOssConfig aliyunOssConfig;
    @Resource
    private IAcsClient iAcsClient;
    @Resource
    private RedisService redisService;
    @Resource
    private ProjectResourceMapper projectResourceMapper;

    /**
     * 处理文档转换队列
     *
     * @throws ClientException OSS客户端异常
     */
//    @Scheduled(fixedDelay = 1000)
    public void runConvertTask() throws ClientException {
        DocumentConvertInfoDto runningDocumentConvertInfoDto = (DocumentConvertInfoDto) redisService.get(RedisConstants.DOCUMENT_CONVERT_RUNNING_TASK_KEY);
        if (runningDocumentConvertInfoDto != null) {
            GetOfficeConversionTaskRequest conversionTaskRequest = new GetOfficeConversionTaskRequest();
            conversionTaskRequest.setProject("aha-document-preview");
            conversionTaskRequest.setTaskId(runningDocumentConvertInfoDto.getTaskId());
            GetOfficeConversionTaskResponse conversionTaskResponse = iAcsClient.getAcsResponse(conversionTaskRequest);
            if (conversionTaskResponse.getStatus().equals("Running")) {
                return;
            } else {
                redisService.del(RedisConstants.DOCUMENT_CONVERT_RUNNING_TASK_KEY);
                if (conversionTaskResponse.getStatus().equals("Finished")) {
                    String previewUrl = "https://" +
                            aliyunOssConfig.getPublicBucketName() +
                            "." +
                            aliyunOssConfig.getEndpoint() +
                            "/" +
                            runningDocumentConvertInfoDto.getTargetFilePath() +
                            "1.pdf";
                    projectResourceMapper.updatePreviewUrlById(previewUrl, runningDocumentConvertInfoDto.getProjectResourceId());
                    log.info(runningDocumentConvertInfoDto.getSrcFilename() + "的转换成功");
                } else {
                    log.warn(runningDocumentConvertInfoDto.getSrcFilename() + "的转换出错，错误代码为：" + conversionTaskResponse.getFailDetail().getCode());
                }
            }
        }
        DocumentConvertInfoDto documentConvertInfoDto = (DocumentConvertInfoDto) redisService.lPop(RedisConstants.DOCUMENT_CONVERT_LIST_KEY);
        if (documentConvertInfoDto != null) {
            String filename = documentConvertInfoDto.getSrcFilename();
            log.info("已收到转换请求，预备开始转换：" + filename);
            int index = filename.lastIndexOf("/");
            String targetPath;
            if (index > 0) {
                targetPath = "preview_files/" + filename.substring(0, index) + "/";
            } else {
                targetPath = "preview_files/";
            }
            documentConvertInfoDto.setTargetFilePath(targetPath);
            convertDocument(aliyunOssConfig.getPrivateBucketName(), aliyunOssConfig.getPublicBucketName(), documentConvertInfoDto);
        }

    }

    /**
     * 转换文档
     *
     * @param srcBucketName          源bucket名称
     * @param targetBucketName       目标bucket名称
     * @param documentConvertInfoDto 文档转换信息
     * @throws ClientException OSS客户端异常
     */
    public void convertDocument(String srcBucketName, String targetBucketName, DocumentConvertInfoDto documentConvertInfoDto) throws ClientException {
        CreateOfficeConversionTaskRequest taskRequest = new CreateOfficeConversionTaskRequest();
        taskRequest.setProject("aha-document-preview");
        taskRequest.setSrcUri("oss://" + srcBucketName + "/" + documentConvertInfoDto.getSrcFilename());
        taskRequest.setTgtType("pdf");
        taskRequest.setTgtUri("oss://" + targetBucketName + "/" + documentConvertInfoDto.getTargetFilePath());
        taskRequest.setEndPage(5L);
        taskRequest.setMaxSheetCount(1L);
        CreateOfficeConversionTaskResponse taskResponse = iAcsClient.getAcsResponse(taskRequest);
        if (taskResponse.getStatus().equals("Running")) {
            log.info(documentConvertInfoDto.getSrcFilename() + "的转换已经开始，任务id为：" + taskResponse.getTaskId());
            documentConvertInfoDto.setTaskId(taskResponse.getTaskId());
            redisService.set(RedisConstants.DOCUMENT_CONVERT_RUNNING_TASK_KEY, documentConvertInfoDto);
        }
    }
}