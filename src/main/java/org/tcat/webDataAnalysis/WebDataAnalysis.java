package org.tcat.webDataAnalysis;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.tcat.tools.StringTools;
import org.tcat.webDataAnalysis.load.LoadXML;
import org.tcat.webDataAnalysis.load.vo.DataAnalysisVo;
import org.tcat.webDataAnalysis.load.vo.DataFileVo;
import org.tcat.webDataAnalysis.mainRunnable.MultiRunnable;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 解析数据
 * Created by Lin on 2016/6/8.
 */
public class WebDataAnalysis {

    /**
     * 解析数据
     */
    public WebDataAnalysis() {
        DataFileVo load = LoadXML.load(ClassLoader.getSystemResourceAsStream(XMLNAME), CHATSET);
        this.execute(load);
    }

    /**
     * 执行主体
     *
     * @param dataFile
     */
    private void execute(DataFileVo dataFile) {
        Collection<File> fileList = FileUtils.listFiles(new File(dataFile.getReadFile()), new String[]{dataFile.getSuffix()}, true);

        System.out.println();
        System.out.println("--------------------------------------------------------");
        System.out.println(new StringBuffer().append("解析开始 （共").append(fileList.size()).append("个文件）"));
        System.out.println("--------------------------------------------------------");
        System.out.println();
        System.out.println("解析中...");
        System.out.println();

        WritableWorkbook workbook = null;
        WritableSheet sheet;
        ExecutorService pool = Executors.newFixedThreadPool(NTHREADS);
        CountDownLatch latch = new CountDownLatch(fileList.size());

        try {
            workbook = Workbook.createWorkbook(new File(dataFile.getOutFile()));
            sheet = workbook.createSheet("sheet", 0);
            for (DataAnalysisVo i : dataFile.getDataAnalysisList()) {
                if (!StringTools.isNotEmptyByTrim(i.getColumn()) && !StringTools.isNotEmptyByTrim(i.getColumnName())) {
                    sheet.addCell(new Label(Integer.valueOf(i.getColumn().trim()), 0, i.getColumnName()));
                }
            }
            for (File f : fileList) {
                Document doc = Jsoup.parse(f, dataFile.getReadFIleCharset());
                pool.submit(new MultiRunnable(latch, doc, sheet, dataFile.getDataAnalysisList()));
            }

            latch.await();
            workbook.write();

            System.out.println();
            System.out.println("--------------------------------------------------------");
            System.out.println("解析已完成");
            System.out.println("--------------------------------------------------------");
            System.out.println();

        } catch (IOException e) {
            throw new RuntimeException("文件读取失败", e);
        } catch (WriteException e) {
            throw new RuntimeException("文件写出失败", e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    throw new RuntimeException("xls写出关闭失败", e);
                } catch (WriteException e) {
                    throw new RuntimeException("xls写出关闭失败", e);
                }
            }
            pool.shutdown();
        }

    }

    /**
     * XMl文件路径
     */
    private static final String XMLNAME = "webDataAnalysis.xml";
    /**
     * XML文件解析编码
     */
    private static final String CHATSET = "utf-8";
    /**
     * 线程数
     */
    private static Integer NTHREADS = 20;

    public static void main(String[] args) {
        new WebDataAnalysis();
    }

}
