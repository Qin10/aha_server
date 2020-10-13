package cn.hdustea.aha_server.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件上传的工具类
 *
 * @author STEA_YY
 **/
public class FileUtil {

    /**
     * 上传文件至指定路径
     *
     * @param file 文件
     * @param path 绝对路径
     * @return 文件名
     * @throws IOException IO操作异常
     */
    public static String upload(MultipartFile file, String path) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("文件为空！");
        }
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + file.getOriginalFilename();
        File dest = new File(path + fileName);
        file.transferTo(dest);
        return fileName;
    }

    /**
     * 删除文件
     *
     * @param filePath 文件绝对路径
     * @return 是否删除成功
     */
    public static boolean delete(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return file.delete();
        } else {
            return false;
        }
    }
}
