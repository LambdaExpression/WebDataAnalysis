package org.tcat.webDataDownload.mainRunnable;

import java.util.concurrent.CountDownLatch;

/**
 * 主线程（多线程）
 * Created by Lin on 2016/5/17.
 */
public final class MultiRunnable implements Runnable {
    private CountDownLatch latch;

    /**
     * 无参构造器
     */
    public MultiRunnable() {
    }

    /**
     * 有参构造器
     *
     * @param url     网络地址
     * @param outPath 输出路径
     * @param charset 字符集
     * @param type    消息报文类型
     */
    public MultiRunnable(String url, String outPath, String charset, Integer type) {
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
    public MultiRunnable(CountDownLatch latch, String url, String outPath, String charset, Integer type) {
        this.latch = latch;
        this.url = url;
        this.outPath = outPath;
        this.charset = charset;
        this.type = type;
    }


    public void run() {
        try{
            new SingleRunnable().download(url, outPath, charset, type);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            latch.countDown();
        }
    }

    private String url;
    private String outPath;
    private String charset;
    private Integer type;

}
