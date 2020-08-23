package com.zhangrh.smart.framework;

import com.zhangrh.smart.framework.bean.Data;
import com.zhangrh.smart.framework.bean.Handler;
import com.zhangrh.smart.framework.bean.Param;
import com.zhangrh.smart.framework.bean.View;
import com.zhangrh.smart.framework.helper.*;
import com.zhangrh.smart.framework.util.JsonUtil;
import com.zhangrh.smart.framework.util.RefectionUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @description: 请求转发器
 * @version: 1.0
 * @author: zhangrenhua
 * @date: 2020/8/11 21:37
 */
@WebServlet(urlPatterns = "/", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        //初始化相关Helper类
        HelpLoader.init();
        //获取ServletContext对象
        ServletContext servletContext = config.getServletContext();
        //注册处理jsp的servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath());
        //注册处理静态资源的默认servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");

        UploadHelper.init(servletContext);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletHelper.init(req, resp);
        try {
            String requestMethod = req.getMethod().toLowerCase();
            String requestPath = req.getServletPath();
            //获取handler
            Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
            if (handler != null) {
                Class<?> controllerClass = handler.getControllerClass();
                Object controllerBean = BeanHelper.getBean(controllerClass);
                //创建请求参数
                Param param;
                if (UploadHelper.isMultiPart(req)) {
                    param = UploadHelper.createParam(req);
                } else {
                    param = RequestHelper.createParam(req);
                }
                //调用Action方法
                Object result;
                Method actionMethod = handler.getActionMethod();
                if (param.isEmpty()) {
                    result = RefectionUtil.invokeMethod(controllerBean, actionMethod);
                } else {
                    result = RefectionUtil.invokeMethod(controllerBean, actionMethod, param);
                }
                //处理Action方法返回值
                if (result instanceof View) {
                    handleViewResult((View) result, req, resp);
                } else if (result instanceof Data) {
                    handleDataResult((Data) result, req, resp);
                }
            }
        } finally {
            ServletHelper.destroy();
        }
    }

    private void handleViewResult(View view, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String path = view.getPath();
        if (StringUtils.isNotBlank(path)) {
            if (path.startsWith("/")) {
                resp.sendRedirect(req.getContextPath() + path);
            } else {
                Map<String, Object> model = view.getModel();
                for (Map.Entry<String, Object> entry : model.entrySet()) {
                    req.setAttribute(entry.getKey(), entry.getValue());
                }
                req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
            }
        }
    }

    private void handleDataResult(Data data, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Object model = data.getModel();
        if (model != null) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter writer = resp.getWriter();
            writer.write(JsonUtil.toJson(model));
            writer.flush();
            writer.close();
        }
    }


}
