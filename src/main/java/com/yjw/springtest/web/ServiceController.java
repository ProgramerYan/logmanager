package com.yjw.springtest.web;

import com.yjw.springtest.dao.*;
import com.yjw.springtest.entity.*;
import com.yjw.springtest.facetory.ServiceFactory;
import com.yjw.springtest.realization.FtpCilent;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ServiceController {
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private ColonyRepository colonyRepository;
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private ServiceGroupRepository serviceGroupRepository;
    @Autowired
    private ServiceGroupSecondRepository serviceGroupSecondRepository;

    /**
     * 获得Service表中服务信息
     * 传递给前端服务表格之中
     * @return
     */
    @GetMapping("/getService")
    public List<Service> getService() {
        List<Service> serviceList = serviceRepository.findAll();
        return serviceList;
    }

    /**
     * 根据 服务群组 来获得同一组服务信息
     * @param nodename
     * @return
     */
    @GetMapping("/getServiceByGroupname")
    public List<Service> getServiceByGroupname(@RequestParam("nodename")String nodename) {
        List<Servicegroup> servicegroupList = serviceGroupRepository.findByGroupname(nodename);
        List<Service> serviceList = new ArrayList<>();
        if(servicegroupList.size() != 0) {
            List<Servicegroupsecond> servicegroupsecondList = serviceGroupSecondRepository.findByGroupname(nodename);
            for(int i=0;i<servicegroupsecondList.size();i++) {
                List<Service> serviceList1 = serviceRepository.findByGroupname(servicegroupsecondList.get(i).getSecondname());
                for(int j=0;j<serviceList1.size();j++) {
                    serviceList.add(serviceList1.get(j));
                }
            }
            return serviceList;
        }else {
            List<Service> serviceList1 = serviceRepository.findByGroupname(nodename);
            return serviceList1;
        }
    }

    /**
     * 添加服务信息
     * @param servicename
     * @return
     */
    @GetMapping("/addService")
    public boolean addService(@RequestParam("serviceinfo") String servicename[]){
        if(servicename.length<4) {
            return false;
        } else
        {

            Service service = new Service();
            service.setFilepath(servicename[4]);
            service.setServicename(servicename[0]);
            service.setServiceversion(servicename[1]);
            service.setMaxsrc("0");
            service.setColonyname("暂无");
            service.setServicestatus("停止");
            service.setDescrption(servicename[3]);

            servicename[4] = servicename[4].replace("\\",",");
            String filepath[] = servicename[4].split(",");
            String filename = filepath[filepath.length-1];

            service.setFilename(filename);
            service.setServiceport(servicename[2]);
            service.setGroupname(servicename[5]);
            serviceRepository.save(service);
            return true;
        }
    }

    /**
     * 更新Service表中信息，将集群、最大内存使用信息添加在里面
     * @param servicename
     * @param serviceversion
     * @param colonyname
     * @return
     */
    @GetMapping("/updateService")
    public String updateService(@RequestParam("servicename") String servicename,@RequestParam("serviceversion") String serviceversion,@RequestParam("colonyname") String colonyname,@RequestParam("maxsrc") String maxsrc) {
        List<Service> serviceList = serviceRepository.findByServicenameAndServiceversion(servicename,serviceversion);
        serviceList.get(0).setColonyname(colonyname);
        serviceList.get(0).setMaxsrc(maxsrc);
        String filepath = serviceList.get(0).getFilepath();
        serviceRepository.save(serviceList.get(0));
        return filepath;
    }

    /**
     * 文件上传
     */
    @GetMapping("/upFile")
    public boolean upload(@RequestParam("filepath") String filepath,@RequestParam("colonyname") String colonyname) {
        List<Colony> colonyList = colonyRepository.findByColonyname(colonyname);
        for(int i = 0 ;i<colonyList.size();i++) {
            //创建客户端对象
            FtpCilent ftpCilent = new FtpCilent();
            if(ftpCilent.initFtpClient(colonyList.get(i).getAgentaddress())==null) {
                return false;
            }
            FTPClient ftp = ftpCilent.initFtpClient(colonyList.get(i).getAgentaddress());
            InputStream local=null;
            try {
                //设置上传路径
                String path="E:\\ftpserver";
                //检查上传路径是否存在 如果不存在返回false
                boolean flag = ftp.changeWorkingDirectory(path);
                if(!flag){
                    //创建上传的路径  该方法只能创建一级目录，在这里如果/home/ftpuser存在则可创建image
                    ftp.makeDirectory(path);
                }
                //指定上传路径
                ftp.changeWorkingDirectory(path);
                //指定上传文件的类型  二进制文件
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
                //读取本地文件
                File file1 = new File(filepath);//E:\helloword.jar
                local = new FileInputStream(file1);
                //第一个参数是文件名
                ftp.storeFile(file1.getName(), local);

            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    //关闭文件流
                    local.close();
                    //退出
                    ftp.logout();
                    //断开连接
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return true;
    }

    /**
     * 删除服务
     * @param servicename
     * @param serviceversion
     * @return
     */
    @GetMapping("/deleteService")
    public boolean deleteService(@RequestParam("servicename") String servicename,@RequestParam("serviceversion") String serviceversion) {
        List<Service> serviceList = serviceRepository.findByServicenameAndServiceversion(servicename,serviceversion);
        List<Colony> colonyList = colonyRepository.findByColonyname(serviceList.get(0).getColonyname());
        if(serviceList.get(0).getServicestatus().equals("运行")) {
            return false;
        } else {
            if(serviceList.get(0).getColonyname().equals("暂无")) {
                serviceRepository.deleteByServicenameAndServiceversion(servicename,serviceversion);
                return true;
            }else {
                for(int i = 0;i<colonyList.size();i++) {

                    FtpCilent ftpCilent = new FtpCilent();
                    if(ftpCilent.initFtpClient(colonyList.get(i).getAgentaddress())==null) {
                        return false;
                    }
                    FTPClient ftpClient = ftpCilent.initFtpClient(colonyList.get(i).getAgentaddress());
                    try{
                        //切换FTP目录
                        ftpClient.changeWorkingDirectory("E:\\ftpserver");
                        ftpClient.dele(serviceList.get(0).getFilename());
                        ftpClient.logout();
                        serviceRepository.deleteByServicenameAndServiceversion(servicename,serviceversion);
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if(ftpClient.isConnected()){
                            try{
                                ftpClient.disconnect();
                            }catch(IOException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 启动服务
     * 向节点发送请求 参数：地址，端口，文件名称
     * @return
     */
    @GetMapping("/startService")
    public boolean startService(@RequestParam("servicename") String servicename,@RequestParam("serviceversion") String serviceversion) {
        boolean isStart = false;
        List<Service> serviceList = serviceRepository.findByServicenameAndServiceversion(servicename,serviceversion);
        List<Colony> colonyList = colonyRepository.findByColonyname(serviceList.get(0).getColonyname());
        ServiceMethod serviceMethod = ServiceFactory.produceStartService();
        List<Service> serviceList1 = serviceRepository.findByServicenameAndServiceversion(servicename,serviceversion);
        serviceList1.get(0).setServicestatus("运行");
        serviceRepository.save(serviceList1.get(0));
        for(int i = 0;i<colonyList.size();i++) {
            Agent agent = agentRepository.findByAgentaddress(colonyList.get(i).getAgentaddress());
            //向节点发送请求 参数：地址，端口，文件名称
            isStart = serviceMethod.ServiceMethod(colonyList.get(i).getAgentaddress(),String.valueOf(agent.getAgentport()),serviceList.get(0).getFilename(),serviceList.get(0).getServiceport());
        }
        return true;
    }

    /**
     * 停止服务
     * 向节点发送请求 参数：地址，端口，文件名称，服务端口
     * @return
     */
    @GetMapping("/stopService")
    public boolean stopService(@RequestParam("servicename") String servicename,@RequestParam("serviceversion") String serviceversion) {
        boolean isStop = false;
        List<Service> serviceList = serviceRepository.findByServicenameAndServiceversion(servicename,serviceversion);
        List<Colony> colonyList = colonyRepository.findByColonyname(serviceList.get(0).getColonyname());
        ServiceMethod serviceMethod = ServiceFactory.produceStopService();
        for(int i = 0;i<colonyList.size();i++) {
            Agent agent = agentRepository.findByAgentaddress(colonyList.get(i).getAgentaddress());
            //向节点发送请求 参数：地址，端口，文件名称
            isStop = serviceMethod.ServiceMethod(colonyList.get(i).getAgentaddress(),String.valueOf(agent.getAgentport()),serviceList.get(0).getFilename(),serviceList.get(0).getServiceport());
        }
        List<Service> serviceList1 = serviceRepository.findByServicenameAndServiceversion(servicename,serviceversion);
        serviceList1.get(0).setServicestatus("停止");
        serviceRepository.save(serviceList1.get(0));
        return isStop;
    }

    /**
     * 获取Service表中的服务信息
     * @param servicename
     * @return
     */
    @GetMapping("/getServiceInfo")
    public List<Service> getServiceInfo(@RequestParam("servicename") String servicename,@RequestParam("serviceversion") String serviceversion) {
        List<Service> serviceList = serviceRepository.findByServicenameAndServiceversion(servicename,serviceversion);
        return serviceList;
    }

    @GetMapping("/setSession")
    public boolean setSession(@RequestParam("servername") String servername, HttpServletRequest request) {
        HttpSession session=request.getSession();
        session.setAttribute("servername",servername);
        return true;
    }

    @GetMapping("/getSession")
    public String setSession(HttpServletRequest request) {
        HttpSession session=request.getSession();
        String servername = String.valueOf(session.getAttribute("servername"));
        return servername;
    }
}
