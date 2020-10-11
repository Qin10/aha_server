package cn.hdustea.aha_server.util;

import cn.hdustea.aha_server.bean.ResponseBean;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: aha_server
 * @description: 文件上传的工具类
 * @author: HduStea_YY
 * @create: 2020-10-11 16:39
 **/
public class FileUtil {

    public static String upload(MultipartFile file, String path) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("文件为空！");
        }
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + file.getOriginalFilename();
        File dest = new File(path + fileName);
        file.transferTo(dest);
        return fileName;
    }

    public static boolean delete(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return file.delete();
        } else {
            return false;
        }
    }
}
