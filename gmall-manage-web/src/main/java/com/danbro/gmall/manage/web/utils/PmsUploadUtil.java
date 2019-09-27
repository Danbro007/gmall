package com.danbro.gmall.manage.web.utils;

import com.danbro.gmall.manage.web.GmallManageWebApplication;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Danrbo
 * @date 2019/9/15 13:23
 * description
 **/
public class PmsUploadUtil {

    public static String uploadImage(MultipartFile multipartFile) {

        String file = GmallManageWebApplication.class.getResource("/tracker.conf").getFile();
        TrackerClient trackerClient;
        TrackerServer trackerServer;
        StorageClient storageClient;
        try {
            ClientGlobal.init(file);
            trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            storageClient = new StorageClient(trackerServer, null);
            byte[] bytes = multipartFile.getBytes();
            String originalFilename = multipartFile.getOriginalFilename();
            int i = originalFilename.lastIndexOf(".");
            String extName = originalFilename.substring(i + 1);
            String[] uploadFileInfo = storageClient.upload_file(bytes, extName, null);
            return "http://192.168.0.109/" + uploadFileInfo[0] + "/" + uploadFileInfo[1];
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
