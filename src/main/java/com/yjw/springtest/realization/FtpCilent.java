package com.yjw.springtest.realization;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FtpCilent {

    public FTPClient ftpClient = null;

    public FTPClient initFtpClient(String address) {
        ftpClient = new FTPClient();
        ftpClient.setControlEncoding("utf-8");
        try {
            ftpClient.connect(address, 21); //连接ftp服务器
            ftpClient.login("yan", "2015.zyjw"); //登录ftp服务器
            int replyCode = ftpClient.getReplyCode(); //是否成功登录服务器
            if(!FTPReply.isPositiveCompletion(replyCode)){
                System.out.println("connect failed");
            }
            System.out.println("connect successfu:"+address);
            return ftpClient;
        }catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }
}
