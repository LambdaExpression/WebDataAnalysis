package webDataDownload;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.tcat.tools.ListTools;
import org.tcat.tools.StringTools;
import org.tcat.tools.vo.ListMapCriteriaVo;
import org.tcat.webDataDownload.WebDataDownload;
import org.tcat.webDataDownload.enums.HttpMessageType;
import org.tcat.webDataDownload.load.LoadXML;
import org.tcat.webDataDownload.httpRequestUtils.HttpRequestGet;
import org.tcat.webDataDownload.httpRequestUtils.HttpRequestPost;
import org.tcat.webDataDownload.load.vo.WebDataVo;
import org.tcat.webDataDownload.mainThread.MultiThread;
import org.tcat.webDataDownload.mainThread.SingleThread;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javafx.scene.input.KeyCode.L;

/**
 * Created by Lin on 2016/5/16.
 */
public class WebDataDownloadTest {

    @Test
    public void httpRequestGetTest() {
        HttpRequestGet httpGet = new HttpRequestGet();
        String concent = httpGet.httpGet("www.mr-world.com");
        System.out.println(concent);
        httpGet.close();
    }

    @Test
    public void httRequestPostTest() {
        HttpRequestPost httpPost = new HttpRequestPost();
        String concent = httpPost.httpPost("www.mr-world.com", "utf-8", null);
        System.out.println(concent);
        httpPost.close();
    }

    @Test
    public void loadXMLTest() {
        List<WebDataVo> data = new LoadXML().load("src\\org\\tcat\\webDataDownload\\webDataDownload.xml", "utf-8");
        data.forEach(d->System.out.println(d.toString()));
    }

    @Test
    public void singleThreadTest() {
//        new SingleThread().download("www.mr-world.com","E:\\idea\\data\\webDataAnalysis\\file\\webDataDownload","utf-8", HttpMessageType.get.value());
        new SingleThread().download("www.mr-world.com", "E:\\idea\\data\\webDataAnalysis\\file\\webDataDownload", "utf-8", HttpMessageType.post.value());
    }

    @Test
    public void multiThreadTest() {
//        Thread t=new Thread(new MultiThread("www.mr-world.com","E:\\idea\\data\\webDataAnalysis\\file\\webDataDownload","utf-8", HttpMessageType.get.value()));
        Thread t = new Thread(new MultiThread("www.mr-world.com", "E:\\idea\\data\\webDataAnalysis\\file\\webDataDownload", "utf-8", HttpMessageType.post.value()));
        t.run();
    }


    @Test
    public void test() {
        List<Map<String, Object>> dataList = new ArrayList<>();
        Map<String, Object> m1 = new HashMap<>();
        m1.put("level", 1);
        m1.put("name", "m1");
        m1.put("name1", "m1");
        dataList.add(m1);
        Map<String, Object> m2 = new HashMap<>();
        m2.put("level", 2);
        m2.put("name", "m2");
        m2.put("name1", "m1");
        dataList.add(m2);
        Map<String, Object> m3 = new HashMap<>();
        m3.put("level", 1);
        m3.put("name", "m3");
        m3.put("name1", "m1");
        dataList.add(m3);
        List<ListMapCriteriaVo> lMCVo = new ArrayList<>();
        lMCVo.add(new ListMapCriteriaVo("level", 1));
        lMCVo.add(new ListMapCriteriaVo("name1", "m1"));

        dataList = ListTools.findListMapCriterias(dataList, lMCVo);
        dataList.forEach(d -> {
            System.out.println(d.toString());
        });

    }



    @Test
    public void webDataDownloadTest() {
        new WebDataDownload();
    }

    @Test
    public void httpRequestGet(){
        HttpRequestGet hrg=new HttpRequestGet();
        try{
            for(int i=1;i<1000;i++){
                FileUtils.write(new File("E:\\MySelf\\Workspaces\\IDEA\\data\\youku.html"),hrg.httpGet("http://v.youku.com/v_show/id_XNzU0OTA5OTk2.html?from=s1.8-1-1.2"),"utf-8");
                System.out.println(new StringBuffer().append("第").append(i).append("次").toString());
                Thread.sleep(30000);
            }
        }catch (InterruptedException e){
            throw new RuntimeException("系统异常！",e);
        }catch (IOException e){
            throw new RuntimeException("文件输出失败",e);
        }finally {
            hrg.close();
        }
    }


}
