package com.gentle.web;

import com.gentle.beanfactory.DefaultBeanFactory;
import com.gentle.context.DefaultApplicationContext;
import com.gentle.web.method.MethodHandler;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gentle
 * @date 2019/06/18 : 14:12
 */
public class DispatcherServlet extends HttpServlet {

    private Map<String, Object> urlMapping = new ConcurrentHashMap<>();


    private DefaultBeanFactory beanFactory;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doDispatcher(req, resp);

    }

    /**
     * 参数分派
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void doDispatcher(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取请求的 URL
        String requestURI = request.getRequestURI();
        //获取 上下文
        String contextPath = request.getContextPath();
        //如果有上下文，替换掉，否则都是404
        String url = requestURI.replace(contextPath, "");
        //将请求来的第一个 /  去掉
        url = url.substring(url.indexOf("/") + 1);

        Method method = beanFactory.getMapping(url);
        PrintWriter writer = response.getWriter();
        try {


            if (method == null) {
                writer.write("404 not the fond");
                return;
            }
            MethodHandler methodHandler = new MethodHandler(beanFactory, method);
            Object o = methodHandler.invokeForRequest(request);
            if (o == null) {
                writer.write("没有返回值");
            } else {
                writer.write(String.valueOf(o));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }

    }

    @Override
    public void init(ServletConfig servletConfig) {

        String contextConfigLocation = servletConfig.getInitParameter("contextConfigLocation");

        DefaultApplicationContext applicationContext = new DefaultApplicationContext();

        applicationContext.loader(contextConfigLocation);

        this.beanFactory = applicationContext.getBeanFactory();

    }


}
