package com.github.md.web.controller;

import com.github.md.web.WebException;
import com.github.md.analysis.kit.Kv;
import com.jfinal.kit.StrKit;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

/**
 * 因为Controller中使用了大量的 getParaXXX() api,在迁移到Springboot时
 * 为了对api进行过度 新建了ParameterHelper这个类
 * <p> @Date : 2021/9/7 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
public class ParameterHelper {

    HttpServletRequest request;

    public ParameterHelper(HttpServletRequest request) {
        this.request = request;
    }

    public Kv getKv() {
        Kv kv = new Kv();
        Map<String, String[]> paraMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : paraMap.entrySet()) {
            String[] values = entry.getValue();
            String value = (values != null && values.length > 0) ? values[0] : null;
            kv.put(entry.getKey(), "".equals(value) ? null : value);
        }
        return kv;
    }

    public String getPara() {
        String url = request.getRequestURL().toString();
        int i = url.lastIndexOf('/');
        if (i != -1) {
            url = url.substring(i + 1);
        }
        return url;
    }

    public String getPara(int index) {
        if (index < 0)
            return getPara();
        String[] urlParaArray = null;
        if (urlParaArray == null) {
            if (getPara() == null || "".equals(getPara()))    // urlPara maybe is "" see ActionMapping.getAction(String)
                urlParaArray = new String[0];
            else
                urlParaArray = getPara().split("-");

            for (int i = 0; i < urlParaArray.length; i++)
                if ("".equals(urlParaArray[i]))
                    urlParaArray[i] = null;
        }
        return urlParaArray.length > index ? urlParaArray[index] : null;
    }

    public String getPara(int index, String defaultValue) {
        String result = getPara(index);
        return result != null && !"".equals(result) ? result : defaultValue;
    }

    /**
     * Get para from url and conver to Integer. The first index is 0
     */
    public Integer getParaToInt(int index) {
        return toInt(getPara(index), null);
    }

    /**
     * Get para from url and conver to Integer with default value if it is null.
     */
    public Integer getParaToInt(int index, Integer defaultValue) {
        return toInt(getPara(index), defaultValue);
    }

    /**
     * Get all para from url and convert to Integer
     */
    public Integer getParaToInt() {
        return toInt(getPara(), null);
    }

    /**
     * Get all para from url and convert to Long
     */
    public Long getParaToLong() {
        return toLong(getPara(), null);
    }

    public Long getParaToLong(int index) {
        return toLong(getPara(index), null);
    }

    /**
     * Get para from url and conver to Long with default value if it is null.
     */
    public Long getParaToLong(int index, Long defaultValue) {
        return toLong(getPara(index), defaultValue);
    }

    public String getPara(String name) {
        // return request.getParameter(name);
        String result = request.getParameter(name);
        return "".equals(result) ? null : result;
    }

    public String getPara(String name, String defaultValue) {
        String result = request.getParameter(name);
        return result != null && !"".equals(result) ? result : defaultValue;
    }

    public Map<String, String[]> getParaMap() {
        return request.getParameterMap();
    }

    public Enumeration<String> getParaNames() {
        return request.getParameterNames();
    }

    public String[] getParaValues(String name) {
        return request.getParameterValues(name);
    }

    public Integer[] getParaValuesToInt(String name) {
        String[] values = request.getParameterValues(name);
        if (values == null || values.length == 0) {
            return null;
        }
        Integer[] result = new Integer[values.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = StrKit.isBlank(values[i]) ? null : Integer.parseInt(values[i]);
        }
        return result;
    }

    public Long[] getParaValuesToLong(String name) {
        String[] values = request.getParameterValues(name);
        if (values == null || values.length == 0) {
            return null;
        }
        Long[] result = new Long[values.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = StrKit.isBlank(values[i]) ? null : Long.parseLong(values[i]);
        }
        return result;
    }

    public Enumeration<String> getAttrNames() {
        return request.getAttributeNames();
    }

    public String getHeader(String name) {
        return request.getHeader(name);
    }

    private Integer toInt(String value, Integer defaultValue) {
        try {
            if (StrKit.isBlank(value))
                return defaultValue;
            value = value.trim();
            if (value.startsWith("N") || value.startsWith("n"))
                return -Integer.parseInt(value.substring(1));
            return Integer.parseInt(value);
        } catch (Exception e) {
            throw new WebException("Can not parse the parameter \"" + value + "\" to Integer value.");
        }
    }

    /**
     * Returns the value of a request parameter and convert to Integer.
     *
     * @param name a String specifying the name of the parameter
     *
     * @return a Integer representing the single value of the parameter
     */
    public Integer getParaToInt(String name) {
        return toInt(request.getParameter(name), null);
    }

    /**
     * Returns the value of a request parameter and convert to Integer with a default value if it is null.
     *
     * @param name a String specifying the name of the parameter
     *
     * @return a Integer representing the single value of the parameter
     */
    public Integer getParaToInt(String name, Integer defaultValue) {
        return toInt(request.getParameter(name), defaultValue);
    }

    private Long toLong(String value, Long defaultValue) {
        try {
            if (StrKit.isBlank(value))
                return defaultValue;
            value = value.trim();
            if (value.startsWith("N") || value.startsWith("n"))
                return -Long.parseLong(value.substring(1));
            return Long.parseLong(value);
        } catch (Exception e) {
            throw new WebException("Can not parse the parameter \"" + value + "\" to Long value.");
        }
    }

    /**
     * Returns the value of a request parameter and convert to Long.
     *
     * @param name a String specifying the name of the parameter
     *
     * @return a Integer representing the single value of the parameter
     */
    public Long getParaToLong(String name) {
        return toLong(request.getParameter(name), null);
    }

    /**
     * Returns the value of a request parameter and convert to Long with a default value if it is null.
     *
     * @param name a String specifying the name of the parameter
     *
     * @return a Integer representing the single value of the parameter
     */
    public Long getParaToLong(String name, Long defaultValue) {
        return toLong(request.getParameter(name), defaultValue);
    }

    private Boolean toBoolean(String value, Boolean defaultValue) {
        if (StrKit.isBlank(value))
            return defaultValue;
        value = value.trim().toLowerCase();
        if ("1".equals(value) || "true".equals(value))
            return Boolean.TRUE;
        else if ("0".equals(value) || "false".equals(value))
            return Boolean.FALSE;
        throw new WebException("Can not parse the parameter \"" + value + "\" to Boolean value.");
    }

    /**
     * Returns the value of a request parameter and convert to Boolean.
     *
     * @param name a String specifying the name of the parameter
     *
     * @return true if the value of the parameter is "true" or "1", false if it is "false" or "0", null if parameter is not exists
     */
    public Boolean getParaToBoolean(String name) {
        return toBoolean(request.getParameter(name), null);
    }

    /**
     * Returns the value of a request parameter and convert to Boolean with a default value if it is null.
     *
     * @param name a String specifying the name of the parameter
     *
     * @return true if the value of the parameter is "true" or "1", false if it is "false" or "0", default value if it is null
     */
    public Boolean getParaToBoolean(String name, Boolean defaultValue) {
        return toBoolean(request.getParameter(name), defaultValue);
    }

    /**
     * Get all para from url and convert to Boolean
     */
    public Boolean getParaToBoolean() {
        return toBoolean(getPara(), null);
    }

    /**
     * Get para from url and conver to Boolean. The first index is 0
     */
    public Boolean getParaToBoolean(int index) {
        return toBoolean(getPara(index), null);
    }

    /**
     * Get para from url and conver to Boolean with default value if it is null.
     */
    public Boolean getParaToBoolean(int index, Boolean defaultValue) {
        return toBoolean(getPara(index), defaultValue);
    }

    private Date toDate(String value, Date defaultValue) {
        try {
            if (StrKit.isBlank(value))
                return defaultValue;

            return new java.text.SimpleDateFormat("yyyy-MM-dd").parse(value.trim());
            //            return (Date) TypeConverter.me().convert(Date.class, value);

        } catch (Exception e) {
            throw new WebException("Can not parse the parameter \"" + value + "\" to Date value.");
        }
    }

    /**
     * Returns the value of a request parameter and convert to Date.
     *
     * @param name a String specifying the name of the parameter
     *
     * @return a Date representing the single value of the parameter
     */
    public Date getParaToDate(String name) {
        return toDate(request.getParameter(name), null);
    }

    /**
     * Returns the value of a request parameter and convert to Date with a default value if it is null.
     *
     * @param name a String specifying the name of the parameter
     *
     * @return a Date representing the single value of the parameter
     */
    public Date getParaToDate(String name, Date defaultValue) {
        return toDate(request.getParameter(name), defaultValue);
    }

    /**
     * Get all para from url and convert to Date
     */
    public Date getParaToDate() {
        return toDate(getPara(), null);
    }

    public String get(String name) {
        return getPara(name);
    }

    public String get(String name, String defaultValue) {
        return getPara(name, defaultValue);
    }

    public Integer getInt(String name) {
        return getParaToInt(name);
    }

    public Integer getInt(String name, Integer defaultValue) {
        return getParaToInt(name, defaultValue);
    }

    public Long getLong(String name) {
        return getParaToLong(name);
    }

    public Long getLong(String name, Long defaultValue) {
        return getParaToLong(name, defaultValue);
    }

    public Boolean getBoolean(String name) {
        return getParaToBoolean(name);
    }

    public Boolean getBoolean(String name, Boolean defaultValue) {
        return getParaToBoolean(name, defaultValue);
    }

    public Date getDate(String name) {
        return getParaToDate(name);
    }

    public Date getDate(String name, Date defaultValue) {
        return getParaToDate(name, defaultValue);
    }

    public String get(int index) {
        return getPara(index);
    }

    public String get(int index, String defaultValue) {
        return getPara(index, defaultValue);
    }

    public Integer getInt() {
        return getParaToInt();
    }

    public Integer getInt(int index) {
        return getParaToInt(index);
    }

    public Integer getInt(int index, Integer defaultValue) {
        return getParaToInt(index, defaultValue);
    }

    public Long getLong() {
        return getParaToLong();
    }

    public Long getLong(int index) {
        return getParaToLong(index);
    }

    public Long getLong(int index, Long defaultValue) {
        return getParaToLong(index, defaultValue);
    }

    public Boolean getBoolean() {
        return getParaToBoolean();
    }

    public Boolean getBoolean(int index) {
        return getParaToBoolean(index);
    }

    public Boolean getBoolean(int index, Boolean defaultValue) {
        return getParaToBoolean(index, defaultValue);
    }
}
