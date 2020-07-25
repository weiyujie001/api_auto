package com.tingge.Until;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.*;

public class HttpUntil {
    public static Map<String ,String > cookies = new HashMap<String, String>();

    public static String doPost(String url, Map<String, String> params)  {
        // 接口地址
        //请求方式
        HttpPost Post = new HttpPost(url);

        List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
        Set<String> keys = params.keySet();
        for (String key : keys) {
            String value = params.get(key);
            parameters.add(new BasicNameValuePair(key, value));
        }
        String result = "";

        try {
            // 设置字符编码，并且以表单形式
            Post.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
            addCookiesInRequest(Post);
            HttpClient client = HttpClients.createDefault();
            HttpResponse response = client.execute(Post);
            getAndStoreCookies(response);

            int code = response.getStatusLine().getStatusCode();
            // 响应报文， EntityUntils.toString    json 转换为字符串
            result = EntityUtils.toString(response.getEntity());
//            System.out.println(code);
//            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }

    private static void addCookiesInRequest(HttpRequest request) {
        String jessionidCookie = cookies.get("JESSIONID");
        if (jessionidCookie != null){
            request.setHeader("Cookie",jessionidCookie);
        }
    }

    private static void getAndStoreCookies(HttpResponse httpResponse) {
        Header firstHeader = httpResponse.getFirstHeader("Set-Cookies");
        if (firstHeader!=null){
            String cookieParams = firstHeader.getValue();
            if (cookieParams!= null && cookieParams.trim().length()>0){
                //切割
                String[] cookieParam = cookieParams.split(";");
                if (cookieParam != null){
                    for (String cookiePair : cookieParam) {
                        //如果包含了JESSIONID,则有ID这个数据
                        if (cookiePair.contains("JESSIONID")){
                            cookies.put("JSEIIONID",cookiePair);
                        }
                    }
                }
            }
        }


    }

    public static String doGet(String url, Map<String, String> params)  {
        // 提供接口地址
        //准备数据
        Set<String> keys = params.keySet();
        int mark = 1;

        for (String key : keys) {
            if(mark==1){
                url += ("?"+key+params.get(key));
            }else{
                url+=("&"+key+params.get(key));
            }
            mark++;
        }
        //指定接口提交的方式
        HttpGet get = new HttpGet(url);
        // 发送请求,拿到相应报文
        HttpClient client = HttpClients.createDefault();
        HttpResponse response = null;
        String result ="";
        try {
            addCookiesInRequest(get);
            response = client.execute(get);
            getAndStoreCookies(response);
            int code = response.getStatusLine().getStatusCode();
//            System.out.println(code);

            result = EntityUtils.toString(response.getEntity());
//            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    public  static  String doService(String url,String type,Map<String,String> params){
        String result ="";
        if("post".equalsIgnoreCase(type)){
            result = HttpUntil.doPost(url, params);
        }else if("get".equalsIgnoreCase(type)){
            result = HttpUntil.doGet(url,params);
        }
        return result;
    }
}
