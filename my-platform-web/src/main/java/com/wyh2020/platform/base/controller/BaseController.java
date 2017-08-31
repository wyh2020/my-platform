package com.wyh2020.platform.base.controller;


import com.wyh2020.platform.base.common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with wyh.
 * Date: 2017/7/12
 * Time: 下午2:30
 * 场景:控制层基类
 */
public class BaseController implements Dictionary {
    protected MessageSource resource;
    /**
     * 系统日志配置.
     */
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * The action execution was a failure.
     */
    public static final String ERROR = "error/error";
    /**
     * 转到error/error页面时,model里面存储的错位信息的键.
     */
    public static final String ERRORKEY = "errorMsg";
    /**
     * 未知异常，提示请求失败.
     */
    public static final String UNKNOWNEXCEPTION = "请求失败";
    /**
     * 未知异常，提示请求失败.
     */
    public static final String PARAMSVALIDFAIL = "参数错误！";
    /**
     * 成功的Status Code.
     */
    private static final int RESCODE_OK = 200;
    /**
     * 失败的Status Code.
     */
    private static final int RESCODE_FAIL = 201;


    /**
     * 业务异常控制
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MyFlatformException.class)
    public @ResponseBody
    Map<String,Object> EhomeExceptionHandler(MyFlatformException e) {
        logger.warn(e.getLocalizedMessage());
        return this.failResult(e.getErrCode(), e.getMessage());
    }

    /**
     * 运行期异常控制
     *
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public @ResponseBody
    Map<String,Object> runtimeExceptionHandler(RuntimeException e) {
        logger.error(e.getLocalizedMessage());
        return this.failResult(e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public @ResponseBody
    Map<String,Object> bindExceptionHandler(BindException e) {
        logger.error(e.getLocalizedMessage());
        return this.failResult(e.getFieldError().getDefaultMessage());
    }


    /**
     * 描述：获取成功结果
     * @advised {@link #successDataResult(Object)}
     * @param obj
     * @return
     */
    @Deprecated
    protected Map<String, Object> getSuccessResult(Object obj) {
        return JSONUtil.createJson(true, RESCODE_OK, "操作成功", obj);
    }

    /**
     * 获取成功信息.
     * @advised
     * @param msg
     * @return
     */
    @Deprecated
    protected Map<String, Object> getSuccessResult(String msg) {
        return JSONUtil.createJson(true, RESCODE_OK, msg, Collections.EMPTY_MAP);
    }



    /**
     * 获取失败信息.
     * @param msg
     * @return
     */
    protected Map<String, Object> failResult(String msg) {
        return JSONUtil.createJson(false, RESCODE_FAIL, msg, Collections.EMPTY_MAP);
    }
    /**
     * 获取失败信息.
     * @param msg
     * @return
     */
    protected Map<String, Object> failResult(int errCode,String msg) {
        return JSONUtil.createJson(false, errCode, msg, Collections.EMPTY_MAP);
    }
    /**
     * 获取成功信息.
     * 只返回数据，默认操作信息为操作成功
     * @param obj
     * @return
     */
    protected Map<String, Object> successDataResult(Object obj) {
        return JSONUtil.createJson(true, RESCODE_OK, "操作成功", obj);
    }

    /**
     * 获取默认ajax成功信息.
     * @return
     */
    protected Map<String, Object> successResult() {
        return JSONUtil.createJson(true, RESCODE_OK, "操作成功！", Collections.EMPTY_MAP);
    }

    /**
     * 获取默认ajax成功信息.
     * @advised {@link #successResult()}
     * @return
     */
    @Deprecated
    protected Map<String, Object> getSuccessResult() {
        return JSONUtil.createJson(true, RESCODE_OK, "操作成功！", Collections.EMPTY_MAP);
    }

    /**
     * 描述：获取失败结果
     * 已移除,不要再使用
     * @param msg
     * @return
     */
    @Deprecated
    protected Map<String, Object> getFailResult(String msg) {
        return JSONUtil.createJson(false, RESCODE_FAIL, msg, Collections.EMPTY_MAP);
    }


    /**
     * 获取dataTables需要的数据格式.
     * @Title: dataTableJson
     * @param totalCount 总数目
     * @param dataList 数据列表
     * @return Map<String,Object> 返回类型
     * @throws
     */
    protected Map<String, Object> dataTableJson(int totalCount, List<?> dataList) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("iTotalDisplayRecords", totalCount);
        data.put("iTotalRecords", totalCount);
        data.put("aaData", dataList == null ? Collections.EMPTY_LIST : dataList);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", data);
        map.put("result", "ok");
        return map;
    }


    /**
     * 获取会话作用域.
     * @return
     */
    protected HttpSession sessionContext() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession(false);
    }

    /**
     * 获取请求作用域.
     * @return
     */
    protected HttpServletRequest requestContext() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取application作用域.
     * @return
     */
    protected ServletContext applicationContext() {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                .getSession(false);
        if (null != session) {
            return session.getServletContext();
        }
        return null;
    }

    /**
     * 根据相对路径获取资源绝对路径.
     * @param path
     * @return
     */
    protected String getRealPath(String path) {
        ServletContext app = applicationContext();
        if (null != app) {
            String root = app.getRealPath(String.valueOf(File.separatorChar));
            return root + path;
        }
        return path;
    }

    /**
     * 获取IP地址.
     * @return
     */
    protected String getIp() {
        HttpServletRequest request = requestContext();
        return IPUtil.getIp(request);
    }



    /**
     * 转换为返回的不带分页的数据列表
     *
     * @param dataList
     * @return
     */
    protected <T> CentreListResponse<T> getListResponse(List<T> dataList) {
        CentreListResponse<T> response = new CentreListResponse<T>();
        response.setDataList(dataList);
        return response;
    }



    /**
     * 转换为返回的带分页数据
     *
     * @param condition
     * @param totalCount
     * @param dataList
     * @return
     */
    protected <T> CentreCutPageResponse<T> getPageResponse(BaseCondition condition, int totalCount, List<T> dataList) {
        CentreCutPageResponse<T> response = new CentreCutPageResponse<T>();
        response.setPageNum(condition.getPageNo());
        response.setPageSize(condition.getPageSize());
        response.setTotalCount(totalCount);
        response.setDataList(dataList);

        return response;
    }

    /**
     * 转换为返回的带分页数据
     *
     * @param form
     * @param totalCount
     * @param dataList
     * @return
     */
    protected <T> CentreCutPageResponse<T> getPageResponse(BaseForm form, int totalCount, List<T> dataList) {
        CentreCutPageResponse<T> response = new CentreCutPageResponse<T>();
        response.setPageNum(form.getPageNum());
        response.setPageSize(form.getPageSize());
        response.setTotalCount(totalCount);
        response.setDataList(dataList);

        return response;
    }

    /**
     * 转换为返回的带分页数据
     *
     * @param pageNo
     * @param pageSize
     * @param totalCount
     * @param dataList
     * @return
     */
    protected <T> CentreCutPageResponse<T> getPageResponse(int pageNo, int pageSize, int totalCount, List<T> dataList) {
        CentreCutPageResponse<T> response = new CentreCutPageResponse<T>();
        response.setPageNum(pageNo);
        response.setPageSize(pageSize);
        response.setTotalCount(totalCount);
        response.setDataList(dataList);

        return response;
    }

    /**
     * 将bean转成map
     * @param source
     * @return
     */
    protected Map<String,Object> getMapByBean(Object source) throws MyFlatformException {
        Map<String,Object> filedMap = new HashMap<String,Object>();
        //反射publicFiled类的所有字段
        Class cla = source.getClass();
        Field[] filed = cla.getDeclaredFields();
        for(Field fd : filed) {
            String filedName = fd.getName();
            String firstLetter = filedName.substring(0,1).toUpperCase(); //获得字段第一个字母大写
            String getMethodName = "get"+firstLetter+filedName.substring(1); //转换成字段的get方法

            try {
                Method getMethod = cla.getMethod(getMethodName, new Class[] {});
                Object value = getMethod.invoke(source, new Object[] {}); //这个对象字段get方法的值

                filedMap.put(filedName, value); //添加到Map集合

            } catch (Exception e) {
                logger.error(e.getMessage(),e);
                throw new MyFlatformException(e.getMessage());
            }

        }
        return filedMap;
    }

    /**
     * 获取cookie值*/
    protected String getCookieValue(HttpServletRequest request,String name){
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length > 0){
            for (Cookie cookie : cookies){
                if(name.equals(cookie.getName())){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }


    /**
     * 获取格式为yyyy-MM-dd的日期处理类
     *
     * @return
     */
    protected DateFormat getShortDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    /**
     * 获取格式为yyyy-MM-dd HH:mm:ss的日期处理类
     *
     * @return
     */
    protected DateFormat getFullDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

}
