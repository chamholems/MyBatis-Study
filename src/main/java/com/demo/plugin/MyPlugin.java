package com.demo.plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;
import java.util.Properties;

/**
 * 拦截器可以同时对多个方法进行拦截 使用多个@Signature即可
 * @Signature type：拦截的核心对象，method：指定拦截哪个方法 args：拦截方法的参数，方法重载时通过参数确定方法
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}),
})
public class MyPlugin implements Interceptor {

    /**
     * 只要被拦截的目标对象的目标方法被执行时，都会执行intercept方法
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("自定义插件生效了");
        // 执行原方法
        return invocation.proceed();
    }

    /**
     * 主要是为了把这个拦截器生成一个代理放到拦截器链中
     * @Description 包装目标对象 为目标对象创建代理对象
     * @param target 要拦截的对象
     * @return 代理对象
     */
    @Override
    public Object plugin(Object target) {
        System.out.println("要包装的对象" + target);
        return Plugin.wrap(target, this);
    }

    /**
     * 获取配置文件的属性
     * 插件初始化的时候调用，也只调用一次
     * 插件配置的属性从这里设置进来
     * @param properties 插件配置参数
     */
    @Override
    public void setProperties(Properties properties) {
        System.out.println("插件的参数" + properties);
    }
}
