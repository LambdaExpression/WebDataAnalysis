package org.tcat.webDataAnalysis.mainRunnable;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.tcat.tools.StringTools;
import org.tcat.webDataAnalysis.enums.DataAnalysisPriority;
import org.tcat.webDataAnalysis.load.vo.DataAnalysisVo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件解析线程
 * Created by Lin on 2016/6/8.
 */
public class MultiRunnable implements Runnable {

    /**
     * 无参数构造器
     */
    public MultiRunnable() {
    }

    /**
     * 有参数构造器
     *
     * @param latch
     * @param doc
     * @param sheet
     * @param dataAnalyList
     */
    public MultiRunnable(CountDownLatch latch, Document doc, WritableSheet sheet, List<DataAnalysisVo> dataAnalyList) {
        this.latch = latch;
        this.doc = doc;
        this.sheet = sheet;
        this.dataAnalyList = dataAnalyList;
    }


    public void run() {
        try {
            //1.创建容器
            Map<String, Elements> analyMap = new HashMap<>();
            Map<String, Object> analyData = new HashMap<>();
            List<DataAnalysisVo> analyFirst = new ArrayList<>();
            List<DataAnalysisVo> analySecond = new ArrayList<>();
            List<DataAnalysisVo> analyThird = new ArrayList<>();
            List<DataAnalysisVo> analyFourth = new ArrayList<>();

            //2.转载容器
            dataAnalyList.stream().forEach(i -> {
                Integer priority = i.getPriority();
                if ((Integer.valueOf(DataAnalysisPriority.priorityFisrt.value())).equals(priority)) {
                    analyFirst.add(i);
                } else if ((Integer.valueOf(DataAnalysisPriority.prioritySecond.value())).equals(priority)) {
                    analySecond.add(i);
                } else if ((Integer.valueOf(DataAnalysisPriority.priorityThird.value())).equals(priority)) {
                    analyThird.add(i);
                } else if ((Integer.valueOf(DataAnalysisPriority.priorityFourth.value())).equals(priority)) {
                    analyFourth.add(i);
                }
            });

            //3.解析容器数据
            analysisDataAnalysisVo(analyFirst, doc, analyMap, analyData);
            analysisDataAnalysisVo(analySecond, doc, analyMap, analyData);
            analysisDataAnalysisVo(analyThird, doc, analyMap, analyData);
            analysisDataAnalysisVo(analyFourth, doc, analyMap, analyData);

            //4.输出容器数据
            int l;
            if (analyData.size() > 0) {
                l = line.get();
                line.getAndIncrement();
//              System.err.println(new StringBuffer().append("解析中：第").append(l).append("个"));
                for (String key : analyData.keySet()) {
                    try {
                        sheet.addCell(new Label(Integer.valueOf(key), l, analyData.get(key).toString()));
                    } catch (WriteException e) {
                        throw new RuntimeException("解析数据输出失败", e);
                    }
                }
            } else {
                System.err.println(new StringBuffer().append("无数据:").append(noData.getAndIncrement()).append("个"));
            }

        } catch (Exception e) {
            System.err.println(new StringBuffer().append("解析异常文件:").append(error.getAndIncrement()).append("个"));
            e.printStackTrace();
        } finally {
            latch.countDown();
//            System.err.println(new StringBuffer().append("计时器剩余:").append(latch.getCount()));
        }
    }

    /**
     * 解析文件数据
     *
     * @param analy
     * @param doc
     * @param analyMap
     * @param analyData
     */
    private void analysisDataAnalysisVo(List<DataAnalysisVo> analy, Document doc, Map<String, Elements> analyMap, Map<String, Object> analyData) {
        analy.forEach(i -> {
            Elements data = null;
            if (!StringTools.isNotEmptyByTrim(i.getId()) && !StringTools.isNotEmptyByTrim(i.getSelect())) {
                if (!StringTools.isNotEmptyByTrim(i.getExtendsInId())) {
                    analyMap.put(i.getId(), data = ((Elements) analyMap.get(i.getExtendsInId())).select(i.getSelect()));
                } else {
                    analyMap.put(i.getId(), data = doc.select(i.getSelect()));
                }
            } else if (!StringTools.isNotEmptyByTrim(i.getExtendsInId()) && !StringTools.isNotEmptyByTrim(i.getSelect())) {
                data = ((Elements) analyMap.get(i.getExtendsInId())).select(i.getSelect());
            } else if (!StringTools.isNotEmptyByTrim(i.getSelect())) {
                data = doc.select(i.getSelect());
            }
            if (data != null && data.size() > 0 && !StringTools.isNotEmptyByTrim(i.getColumn())) {
                if ("text".equals(i.getValueType())) {
                    analyData.put(i.getColumn(), data.get(0).text());
                }
                Matcher m = null;
                if ((m = Pattern.compile("attr:(.*)").matcher(i.getValueType())).find()) {
                    analyData.put(i.getColumn(), data.get(0).attr(m.group(1)));
                }
            }
        });
    }

    /**
     * xls行数
     */
    private static AtomicInteger line = new AtomicInteger(1);
    /**
     * 没有数据的文件数
     */
    private static AtomicInteger noData = new AtomicInteger(0);
    /**
     * 解析错误的文件数
     */
    private static AtomicInteger error = new AtomicInteger(0);

    private CountDownLatch latch;
    private Document doc;
    private WritableSheet sheet;
    private List<DataAnalysisVo> dataAnalyList;

}
