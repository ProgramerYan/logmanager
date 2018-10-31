package com.yjw.springtest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// todo: 工厂模式 反转控制 ioc di  aop 基于接口的编程 angular2
// 响应式的编程  reactive 异步  java8   函数编程 OP + OO scala
// oo java 模式
// xml  注解
@Configuration
public class SpringConfig implements WebMvcConfigurer {
    /**
     * 拦截器配置
     * @param registry
     */

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        registry.addInterceptor(new InterceptorConfig()).addPathPatterns("/doMain");
//    }

    /**
     *  addViewControllers可以方便的实现一个请求直接映射成视图，而无需书写controller
     *  registry.addViewController("请求路径").setViewName("请求页面文件路径")
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //登录界面
        registry.addViewController("/dologin").setViewName("logmis_login");
        //首页面
        registry.addViewController("/dosyxx").setViewName("logmis_syxx");
        //服务节点页面
        registry.addViewController("/dofwjd").setViewName("logmis_fwjd");
        //服务集群页面
        registry.addViewController("/dofwjq").setViewName("logmis_fwjq");
        //服务注册页面
        registry.addViewController("/dozcfw").setViewName("logmis_zcfw");
        //应用服务页面
        registry.addViewController("/doyyfw").setViewName("logmis_yyfw");
        //日志图表页面
        registry.addViewController("/dorztb").setViewName("logmis_rztb");
        //实时日志页面
        registry.addViewController("/dossrz").setViewName("logmis_ssrz");
        registry.addViewController("/dotree").setViewName("tree");
        registry.addViewController("/dotest").setViewName("test");
    }
}
