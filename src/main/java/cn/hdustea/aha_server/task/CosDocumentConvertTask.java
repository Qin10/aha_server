package cn.hdustea.aha_server.task;

import cn.hdustea.aha_server.config.TencentCosConfig;
import cn.hdustea.aha_server.constants.RedisConstants;
import cn.hdustea.aha_server.dto.DocumentConvertInfoDto;
import cn.hdustea.aha_server.mapper.ProjectResourceMapper;
import cn.hdustea.aha_server.service.RedisService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.job.DocJobObject;
import com.qcloud.cos.model.ciModel.job.DocJobRequest;
import com.qcloud.cos.model.ciModel.job.DocJobResponse;
import com.qcloud.cos.model.ciModel.job.DocProcessObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 腾讯云COS文档转换任务类
 *
 * @author STEA_YY
 **/
@Component
@Slf4j
public class CosDocumentConvertTask {
    @Resource
    private TencentCosConfig tencentCosConfig;
    @Resource
    private COSClient cosClient;
    @Resource
    private RedisService redisService;
    @Resource
    private ProjectResourceMapper projectResourceMapper;

    /**
     * 处理文档转换队列
     */
    @Scheduled(fixedDelay = 1000)
    public void runConvertTask() {
        DocumentConvertInfoDto runningDocumentConvertInfoDto = (DocumentConvertInfoDto) redisService.get(RedisConstants.DOCUMENT_CONVERT_RUNNING_TASK_KEY);
        if (runningDocumentConvertInfoDto != null) {
            DocJobRequest docJobRequest = new DocJobRequest();
            docJobRequest.setBucketName(tencentCosConfig.getResourceBucketName());
            docJobRequest.setJobId(runningDocumentConvertInfoDto.getTaskId());
            DocJobResponse docJobResponse = cosClient.describeDocProcessJob(docJobRequest);
            if (docJobResponse.getJobsDetail().getState().equals("Running") || docJobResponse.getJobsDetail().getState().equals("Submitted")) {
                return;
            } else {
                redisService.del(RedisConstants.DOCUMENT_CONVERT_RUNNING_TASK_KEY);
                if (docJobResponse.getJobsDetail().getState().equals("Success")) {
                    String previewUrl = "https://" + tencentCosConfig.getPublicBucketName() + ".cos." + tencentCosConfig.getRegion() + ".myqcloud.com" + runningDocumentConvertInfoDto.getTargetFilePath();
                    projectResourceMapper.updatePreviewUrlById(previewUrl, runningDocumentConvertInfoDto.getProjectResourceId());
                    log.info(runningDocumentConvertInfoDto.getSrcFilename() + "的转换成功");
                } else {
                    log.warn(runningDocumentConvertInfoDto.getSrcFilename() + "的转换出错，错误代码为：" + docJobResponse.getJobsDetail().getCode());
                }
            }
        }
        DocumentConvertInfoDto documentConvertInfoDto = (DocumentConvertInfoDto) redisService.lPop(RedisConstants.DOCUMENT_CONVERT_LIST_KEY);
        if (documentConvertInfoDto != null) {
            String filename = documentConvertInfoDto.getSrcFilename();
            log.info("已收到转换请求，预备开始转换：" + filename);
            String targetPath;
            targetPath = "/preview_files" + filename + "/";
            documentConvertInfoDto.setTargetFilePath(targetPath);
            convertDocument(tencentCosConfig.getResourceBucketName(), tencentCosConfig.getPublicBucketName(), documentConvertInfoDto);
        }
    }

    public void convertDocument(String srcBucketName, String targetBucketName, DocumentConvertInfoDto documentConvertInfoDto) {
        DocJobRequest request = new DocJobRequest();
        request.setBucketName(srcBucketName);
        DocJobObject docJobObject = request.getDocJobObject();
        docJobObject.setTag("DocProcess");
        docJobObject.getInput().setObject(documentConvertInfoDto.getSrcFilename());
        docJobObject.setQueueId(tencentCosConfig.getDocumentConvertQueueId());
        DocProcessObject docProcessObject = docJobObject.getOperation().getDocProcessObject();
        docProcessObject.setQuality("100");
        docProcessObject.setZoom("100");
        docProcessObject.setStartPage("1");
        docProcessObject.setEndPage("5");
        docProcessObject.setTgtType("jpg");
        MediaOutputObject output = docJobObject.getOperation().getOutput();
        output.setRegion(tencentCosConfig.getRegion());
        output.setBucket(targetBucketName);
        output.setObject(documentConvertInfoDto.getTargetFilePath() + "${Page}.jpg");
        DocJobResponse docProcessJobs = cosClient.createDocProcessJobs(request);
        if (!docProcessJobs.getJobsDetail().getState().equals("Failed")) {
            documentConvertInfoDto.setTaskId(docProcessJobs.getJobsDetail().getJobId());
            redisService.set(RedisConstants.DOCUMENT_CONVERT_RUNNING_TASK_KEY, documentConvertInfoDto);
        }
    }
}
