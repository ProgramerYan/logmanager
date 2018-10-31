package com.yjw.springtest.realization;

import com.yjw.springtest.dao.ServiceMethod;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class StartServiceRealization implements ServiceMethod {
    @Override
    public boolean ServiceMethod(String agentaddress, String agentport,String filename,String serviceport) {
        try {
            String url = "http://"+agentaddress+":"+agentport+"/startService/"+filename;
            System.out.println(url);
            URL Url  = new URL(url);
            URLConnection conn =  Url.openConnection();
            //发送数据包(可以直接抓取浏览器数据包然后复制)
            conn.setRequestProperty("accept", "*/*" );
            conn.setRequestProperty("Connection", "Keep-Alive" );
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.86 Safari/537.36");
            conn.connect();
            //返回浏览器的输出信息
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream() ));
            String line = reader.readLine();
            reader.close();
//            if(line == null) {
//                return true;
//            }
        }catch (Exception e) {
            System.out.println("启动服务接口失败");
        }
        return false;
    }
}
