package webDataDownload;

import org.junit.Test;
import org.tcat.tools.ListTools;
import org.tcat.tools.vo.ListMapCriteriaVo;
import org.tcat.webDataDownload.WebDataDownload;
import org.tcat.webDataDownload.enums.HttpMessageType;
import org.tcat.webDataDownload.load.LoadXML;
import org.tcat.webDataDownload.httpRequestUtils.HttpRequestGet;
import org.tcat.webDataDownload.httpRequestUtils.HttpRequestPost;
import org.tcat.webDataDownload.load.vo.WebDataVo;
import org.tcat.webDataDownload.mainRunnable.MultiRunnable;
import org.tcat.webDataDownload.mainRunnable.SingleRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lin on 2016/5/16.
 */
public class WebDataDownloadTest {

    @Test
    public void httpRequestGetTest() {
        HttpRequestGet httpGet = new HttpRequestGet();
        String concent = httpGet.httpGet("www.mr-world.com", "utf-8");
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
        List<WebDataVo> data = new LoadXML().load(ClassLoader.getSystemResourceAsStream("webDataDownload.xml"), "utf-8");
        data.forEach(d -> System.out.println(d.toString()));
    }

    @Test
    public void singleThreadTest() {
//        new SingleRunnable().download("www.mr-world.com","E:\\idea\\data\\webDataAnalysis\\file\\webDataDownload","utf-8", HttpMessageType.get.value());
        new SingleRunnable().download("www.mr-world.com", "E:\\idea\\data\\webDataAnalysis\\file\\webDataDownload", "utf-8", HttpMessageType.post.value());
    }

    @Test
    public void multiThreadTest() {
//        Thread t=new Thread(new MultiRunnable("www.mr-world.com","E:\\idea\\data\\webDataAnalysis\\file\\webDataDownload","utf-8", HttpMessageType.get.value()));
        Thread t = new Thread(new MultiRunnable("www.mr-world.com", "E:\\idea\\data\\webDataAnalysis\\file\\webDataDownload", "utf-8", HttpMessageType.post.value()));
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


}
