package com.yjw.springtest.realization;

import com.yjw.springtest.dao.AgentMethod;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class StartAgentRealization implements AgentMethod {
    @Override
    public boolean AgentMethod(String agentaddress,int agentport) {
        try {
            System.out.println("启动服务"+agentaddress+"port:"+agentport);
            String url = "http://"+agentaddress+":"+agentport+"/startAgent";
            URL Url  = new URL(url );
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
//            System.out.println(line);
//            line = new String(line.getBytes() , "gbk" );
//            line = line.replace("{","");
//            line = line.replace("}","");
//            line = line.replace("\"","");
//            String info[] = line.split(":");
//            line = info[1];
            reader.close();
            if(line.equals("true")) {
                return true;
            }
        }catch (Exception e) {
            System.out.println("启动服务接口失败");
        }
        return false;
    }
}
