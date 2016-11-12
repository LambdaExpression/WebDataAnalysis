package org.tcat.webDataDownload;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpMessage;
import org.apache.http.client.fluent.Executor;
import org.tcat.webDataDownload.enums.HttpMessageType;
import org.tcat.webDataDownload.load.LoadXML;
import org.tcat.webDataDownload.load.vo.WebDataVo;
import org.tcat.webDataDownload.mainThread.MultiThread;
import org.tcat.webDataDownload.mainThread.SingleThread;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static javafx.scene.input.KeyCode.T;

/**
 * 批量下载网页
 * Created by Lin on 2016/5/17.
 */
public class WebDataDownload {

    /**
     * 无参构造器
     */
    public WebDataDownload() {
        List<WebDataVo> cDatas = new LoadXML().load(PATHXML, CHARSET);
        for (WebDataVo wdv : cDatas) {
            execute(wdv);
        }
    }

    /**
     * 执行主体
     *
     * @param webData 配置参数
     */
    private void execute(WebDataVo webData) {
        File outFile = new File(webData.getOutFileCharset());
        List<String> lineConcent;
        try {
            lineConcent = FileUtils.readLines(new File(webData.getReadFile()), webData.getReadFIleCharset());
        } catch (IOException e) {
            throw new RuntimeException("文件读取失败", e);
        }
        if (outFile == null) {
            outFile.mkdirs();
        }
        if (webData.getTime() != 0L) {
            singleThread(webData, lineConcent);
        } else {
            multiThread(webData, lineConcent);
        }
    }

    /**
     * 单线程运行
     *
     * @param cData       配置参数
     * @param lineConcent 读取的网址文件
     */
    private void singleThread(WebDataVo cData, List<String> lineConcent) {
        for (String lc : lineConcent) {
            new SingleThread().download(lc, cData.getOutFile(), cData.getOutFileCharset(), getHttpMessageType(cData.getType()));
            try {
                Thread.sleep(Long.valueOf(cData.getTime()));
            } catch (InterruptedException e) {
                throw new RuntimeException("线程休眠中断");
            }
        }

    }

    /**
     * 多线程运行
     *
     * @param cData       配置参数
     * @param lineConcent 读取的网址文件
     */
    private void multiThread(WebDataVo cData, List<String> lineConcent) {
//        ThreadPoolExecutor pool = new ThreadPoolExecutor(5,20,200, TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(3), new ThreadPoolExecutor.CallerRunsPolicy());
        CountDownLatch latch = new CountDownLatch(lineConcent.size());
        ExecutorService pool = Executors.newFixedThreadPool(20);
        try {
            for (String lc : lineConcent) {
                Runnable r = new MultiThread(latch, lc, cData.getOutFile(), cData.getOutFileCharset(), getHttpMessageType(cData.getType()));
                pool.execute(new Thread(r));
//              r.run();
            }
        } finally {
            pool.shutdown();
        }
        try {
            latch.wait();
            System.out.println("All task is over....");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//      try {
//          while(true){
//              if(pool.isTerminated()){
//                  System.out.println("It is over....");
//                  break;
//              }
//              Thread.sleep(2000);
//          }
//      }catch (Exception e){
//          e.printStackTrace();
//      }
    }

    /**
     * 识别消息报文类型
     *
     * @param type 消息报文类型（String）
     * @return 消息报文类型（Integer）
     */
    private Integer getHttpMessageType(String type) {
        if ("get".equals(type)) {
            return HttpMessageType.get.value();
        } else if ("post".equals(type)) {
            return HttpMessageType.post.value();
        }
        return null;
    }

    private static String PATHXML = "src\\org\\tcat\\webDataDownload\\webDataDownload.xml";
    private static String CHARSET = "utf-8";
    private static Integer NTHREADS = 20;

    public static void main(String[] args) {
        new WebDataDownload();
    }

}
