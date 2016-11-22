package org.tcat.webDataDownload.mainRunnable;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.tcat.tools.StringTools;
import org.tcat.webDataDownload.enums.HttpMessageType;
import org.tcat.webDataDownload.httpRequestUtils.HttpRequestGet;
import org.tcat.webDataDownload.httpRequestUtils.HttpRequestPost;

import java.io.File;
import java.io.IOException;

/**
 * 主程序（单线程）
 * Created by Lin on 2016/5/17.
 */
public final class SingleRunnable {

    /**
     * 无参构造器
     */
    public SingleRunnable() {
    }

    /**
     * 下载页面
     *
     * @param url     网站地址
     * @param outPath 下载输出路径
     * @param charset 下载及输出的字符集
     * @param type    消息报文类型
     */
    public void download(String url, String outPath, String charset, Integer type) {
        System.err.println(url);
        String concent = "";
        if (type == HttpMessageType.get.value()) {
            concent = new HttpRequestGet().httpGet(url, charset);
        } else if (type == HttpMessageType.post.value()) {
            concent = new HttpRequestPost().httpPost(url, charset, null);
        }
        Document doc = Jsoup.parse(concent);
        Elements title = doc.select("title");
        if (StringTools.isNotEmptyByTrim(concent)) {
            System.err.println(new StringBuilder().append(url).append("(").append(title.text()).append(")").append("下载失败"));
            return;
        }
        if (StringTools.isNotEmptyByTrim(title.text())) {
            System.err.println(new StringBuilder().append(url).append("(").append(title.text()).append(")").append("title为空，下载失败"));
            return;
        }
        try {
            FileUtils.write(new File(outPath, StringTools.filterFolderName(new StringBuffer().append(title.text()).append(".html").toString())), concent, charset);
        } catch (IOException e) {
            throw new RuntimeException("文件写出失败", e);
        } catch (NullPointerException e) {
            throw new RuntimeException("网站页面数据异常，标题title为空", e);
        }
        System.err.println(new StringBuilder().append(url).append("(").append(title.text()).append(")").append("下载完成"));
    }

}
