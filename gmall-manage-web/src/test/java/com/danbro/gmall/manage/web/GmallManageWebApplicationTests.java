package com.danbro.gmall.manage.web;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallManageWebApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testUpload() throws IOException, MyException {
        String file = this.getClass().getResource("/tracker.conf").getFile();
        ClientGlobal.init(file);
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageClient storageClient = new StorageClient(trackerServer, null);
        String[] uploadFileInfo = storageClient.upload_file("C:\\Users\\liweimo\\Desktop\\suchmos.jpg", "jpg", null);
        String url = "192.168.2.56/" + uploadFileInfo[0] + "/" +uploadFileInfo[1];
        System.out.println(url);
    }

}
