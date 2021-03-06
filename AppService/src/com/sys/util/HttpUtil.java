package com.sys.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.unionpay.acp.sdk.HttpClient;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    private static final String default_charset = "utf-8";
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static String get(String url0, String data, String charset) throws IOException {
        if (StringUtils.isBlank(charset)) {
            charset = default_charset;
        }
        /**
         * 首先要和URL下的URLConnection对话。 URLConnection可以很容易的从URL得到。比如： // Using
         * java.net.URL and //java.net.URLConnection
         */
        URL url = new URL(url0);
        URLConnection conn = url.openConnection();
        /**
         * 然后把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。
         * 通过把URLConnection设为输出，你可以把数据向你个Web页传送。下面是如何做：
         */
        conn.setDoOutput(true);

        //当存在post的值时，才打开OutputStreamWriter
        if (data != null && data.toString().trim().length() > 0) {
            /**
             * 最后，为了得到OutputStream，简单起见，把它约束在Writer并且放入POST信息中，例如： ...
             */
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), charset);
            out.write("username=kevin&password=*********"); // post的关键所在！
            // remember to clean up
            out.flush();
            out.close();
        }

        /**
         * 这样就可以发送一个看起来象这样的POST： POST /jobsearch/jobsearch.cgi HTTP 1.0 ACCEPT:
         * text/plain Content-type: application/x-www-form-urlencoded
         * Content-length: 99 username=bob password=someword
         */
        // 一旦发送成功，用以下方法就可以得到服务器的回应：
        String sCurrentLine = null;
        StringBuilder buff = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
        while ((sCurrentLine = reader.readLine()) != null) {
//			buff.append(new String(sCurrentLine.getBytes(), charset));
            logger.error("sCurrentLine=" + sCurrentLine);
            buff.append(sCurrentLine);
        }
        return buff.toString();
    }

    /**
     * 功能：后台交易提交请求报文并接收同步应答报文<br>
     *
     * @param reqData      请求报文<br>
     * @param reqUrl       请求地址<br>
     * @param encoding<br>
     * @return 应答http 200返回true ,其他false<br>
     */
    public static String post(Map<String, String> reqData, String reqUrl, String encoding) {
        String resultString = "";
        //发送后台请求数据
        HttpClient hc = new HttpClient(reqUrl, 30000, 30000);
        try {
            int status = hc.send(reqData, encoding);
            if (200 == status) {
                resultString = hc.getResult();
            } else {
                LogUtil.writeLog("返回http状态码[" + status + "]，请检查请求报文或者请求地址是否正确");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }

    /**
     * 执行POST请求
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String doPost(String url, Map<String, String> params, String encode) throws Exception {
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient httpClient = httpClientBuilder.build();
        httpPost.setHeader("Content-Type", "application/xml; charset=utf-8");

        if (null != params) {
            // 设置2个post参数，一个是scope、一个是q
            List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            // 构造一个form表单式的实体
            UrlEncodedFormEntity formEntity = null;
            if (encode != null) {
                formEntity = new UrlEncodedFormEntity(parameters, encode);
            } else {
                formEntity = new UrlEncodedFormEntity(parameters);
            }
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(formEntity);
        }

        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpClient.execute(httpPost);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    /**
     * 参数格式为JSON的post请求
     *
     * @param url
     * @param json
     * @return
     * @throws Exception
     */
    public static String doPostJson(String url, String json) throws Exception {
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient httpClient = httpClientBuilder.build();

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(60000)
                .setConnectTimeout(60000)
                .build();//设置请求和传输超时时间
        httpPost.setConfig(requestConfig);


        httpPost.setHeader("Content-Type", "application/json; charset=utf-8");

        if (null != json) {
            //设置请求体为 字符串
            StringEntity stringEntity = new StringEntity(json, "UTF-8");
            httpPost.setEntity(stringEntity);
        }
        CloseableHttpResponse response = null;
        BufferedReader reader = null;
        StringBuffer buf = null;
        try {
            // 执行请求
            response = httpClient.execute(httpPost);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                reader = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), "utf-8"));
                buf = new StringBuffer();
                String output;
                while ((output = reader.readLine()) != null) {
                    buf.append(output);
                }
                return buf.toString();
            } else {
                reader = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), "utf-8"));
                buf = new StringBuffer();
                String output;
                while ((output = reader.readLine()) != null) {
                    buf.append(output);
                }
                logger.error("错误码" + response.getStatusLine().getStatusCode());
                logger.error("错误信息" + buf.toString());
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(HttpUtil.get("http://www.baidu.com", null, "gbk"));
    }
}
