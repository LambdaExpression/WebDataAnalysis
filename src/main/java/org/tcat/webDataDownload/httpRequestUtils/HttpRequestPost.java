package org.tcat.webDataDownload.httpRequestUtils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.tcat.tools.StringTools;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 模仿浏览器Post请求
 * Created by Lin on 2016/5/16.
 */
public final class HttpRequestPost {

    /**
     * 无参构造器
     */
    public HttpRequestPost() {
    }

    /**
     * 模仿浏览器Post请求
     *
     * @param url     网络地址
     * @param charset 字符集类型
     * @param data    表单数据
     * @return 页面内容
     */
    public String httpPost(String url, String charset, Map<String, String> data) {

        if (StringTools.isNotEmptyByTrim(url)) {
            return null;
        }
        if (!url.matches("[\\s]*http://(.*)") && !url.matches("[\\s]*http://(.*)")) {
            url = "http://" + url.trim();
        } else {
            url = url.trim();
        }

        HttpPost httpPost = new HttpPost(url);
        List<BasicNameValuePair> bnvpList = mapToBasicNameValuePair(data);
        List<NameValuePair> formParams = Arrays.asList(bnvpList.toArray(new NameValuePair[]{}));

        try {
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formParams, charset);
            httpPost.setEntity(uefEntity);
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            try {
                return EntityUtils.toString(entity, charset);
            } catch (IOException e) {
                throw new RuntimeException(new StringBuffer().append("解析url(").append(url).append(")失败").toString(), e);
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(new StringBuffer().append("使用").append(charset).append("编码异常").toString(), e);
        } catch (IOException e) {
            throw new RuntimeException(new StringBuffer().append("读取url(").append(url).append(")失败").toString(), e);
        }

    }

    /**
     * 关闭httpClient
     */
    public void close() {
        try {
            if (entity != null) {
                EntityUtils.consume(entity);
            }
        } catch (IOException e) {
            throw new RuntimeException("关闭entity失败", e);
        }
        try {
            if (response != null) {
                response.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("关闭response失败", e);
        }
        try {
            if (httpClient != null) {
                httpClient.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("关闭httpClient失败", e);
        }
    }

    /**
     * Map To BasicNameValuePair
     *
     * @param map Map
     * @return List<BasicNameValuePair>
     */
    private List<BasicNameValuePair> mapToBasicNameValuePair(Map<String, String> map) {
        List<BasicNameValuePair> bnvPair = new ArrayList<>();
        if (map == null || map.size() == 0) {
            return bnvPair;
        }
        map.forEach((name, value) -> bnvPair.add(new BasicNameValuePair(name, value)));
        return bnvPair;
    }

    HttpEntity entity = null;
    CloseableHttpResponse response = null;
    CloseableHttpClient httpClient = HttpClients.createDefault();


}
