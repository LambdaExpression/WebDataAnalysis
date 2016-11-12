package org.tcat.webDataDownload.httpRequestUtils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.tcat.tools.StringTools;

import java.io.IOException;

/**
 * 模仿浏览器et请求
 * Created by Lin on 2016/5/16.
 */
public final class HttpRequestGet {

    /**
     * 无参构造器
     */
    public HttpRequestGet() {
    }

    /**
     * 模仿浏览器et请求
     *
     * @param url 网络地址
     * @return 页面内容
     */
    public String httpGet(String url) {
        if (StringTools.isNotEmptyByTrim(url)) {
            return null;
        }
        if (!url.matches("[\\s]*http://(.*)") && !url.matches("[\\s]*http://(.*)")) {
            url = "http://" + url.trim();
        } else {
            url = url.trim();
        }

        try {
            HttpGet httpGet = new HttpGet(url);
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            try {
                return EntityUtils.toString(entity);
            } catch (IOException e) {
                throw new RuntimeException(new StringBuffer().append("解析url(").append(url).append(")失败").toString(), e);
            }
        } catch (IOException e) {
            throw new RuntimeException(new StringBuffer().append("读取url(").append(url).append(")失败").toString(), e);
        }
    }

    /**
     * 关闭response和httpClient
     */
    public void close() {
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

    CloseableHttpResponse response = null;
    CloseableHttpClient httpClient = HttpClients.createDefault();

}
