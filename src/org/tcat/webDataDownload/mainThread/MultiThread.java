package org.tcat.webDataDownload.mainThread;

import java.util.concurrent.CountDownLatch;

import static sun.java2d.cmm.ColorTransform.In;

/**
 * 主线程（多线程）
 * Created by Lin on 2016/5/17.
 */
public final class MultiThread implements Runnable {
    private CountDownLatch latch;

    /**
     * 无参构造器
     */
    public MultiThread() {
    }

    /**
     * 有参构造器
     *
     * @param url     网络地址
     * @param outPath 输出路径
     * @param charset 字符集
     * @param type    消息报文类型
     */
    public MultiThread(String url, String outPath, String charset, Integer type) {
        this.url = url;
        this.outPath = outPath;
        this.charset = charset;
        this.type = type;
    }

    /**
     * 有参构造器
     *
     * @param url     网络地址
     * @param outPath 输出路径
     * @param charset 字符集
     * @param type    消息报文类型
     */
    public MultiThread(CountDownLatch latch, String url, String outPath, String charset, Integer type) {
        this.latch = latch;
        this.url = url;
        this.outPath = outPath;
        this.charset = charset;
        this.type = type;
    }


    public void run() {
        new SingleThread().download(url, outPath, charset, type);
        latch.countDown();
    }

    private String url;
    private String outPath;
    private String charset;
    private Integer type;

}
