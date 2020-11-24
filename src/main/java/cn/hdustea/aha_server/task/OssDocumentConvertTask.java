package cn.hdustea.aha_server.task;

import cn.hdustea.aha_server.config.AliyunMNSConfig;
import cn.hdustea.aha_server.config.AliyunOSSConfig;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.imm.model.v20170906.CreateOfficeConversionTaskRequest;
import com.aliyuncs.imm.model.v20170906.CreateOfficeConversionTaskResponse;

import javax.annotation.Resource;

/**
 * oss文档转化任务
 *
 * @author STEA_YY
 **/

public class OssDocumentConvertTask {
    @Resource
    private AliyunOSSConfig aliyunOSSConfig;
    @Resource
    private AliyunMNSConfig aliyunMNSConfig;
    @Resource
    private IAcsClient iAcsClient;

    public void convertDocument(String filename) throws ClientException {
        String targetPath = "";
        CreateOfficeConversionTaskRequest taskRequest = new CreateOfficeConversionTaskRequest();
        int index = filename.lastIndexOf("/");
        if (index > 0) {
            targetPath = filename.substring(0, index) + "/";
        }
        taskRequest.setProject("aha-document-preview");
        taskRequest.setSrcUri("oss://" + aliyunOSSConfig.getPublicBucketName() + "/" + filename);
        taskRequest.setTgtType("jpg");
        taskRequest.setTgtUri("oss://" + aliyunOSSConfig.getPublicBucketName() + "/" + targetPath + "preview_files/");
        taskRequest.setEndPage(5L);
        taskRequest.setMaxSheetCount(1L);
        taskRequest.setNotifyEndpoint(aliyunMNSConfig.getEndpoint());
        taskRequest.setNotifyTopicName(aliyunMNSConfig.getDocumentConvertTopic());
        CreateOfficeConversionTaskResponse taskResponse = iAcsClient.getAcsResponse(taskRequest);
        System.out.println(taskResponse);
    }
}