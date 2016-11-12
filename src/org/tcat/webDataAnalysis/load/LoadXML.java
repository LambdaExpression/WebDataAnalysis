package org.tcat.webDataAnalysis.load;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.tcat.tools.StringTools;
import org.tcat.webDataAnalysis.load.vo.DataAnalysisVo;
import org.tcat.webDataAnalysis.load.vo.DataFileVo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lin on 2016/5/20.
 */
public class LoadXML {

    public LoadXML() {
    }

    /**
     * 读取配置文件
     *
     * @param path
     * @param charset
     * @return
     */
    public DataFileVo load(String path, String charset) {
        if (StringTools.isNotEmptyByTrim(path) || StringTools.isNotEmptyByTrim(charset)) {
            return null;
        }
        Document doc;
        try {
            FileInputStream fis = new FileInputStream(new File(path));
            doc = Jsoup.parse(fis, charset, "", Parser.xmlParser());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("配置文件未找到", e);
        } catch (IOException e) {
            throw new RuntimeException("配置文件读取异常", e);
        }
        checkVersion(doc.select("version"));
        DataFileVo dfv = new DataFileVo();
        setDataFile(doc, dfv);
        dfv.setDataAnalysisList(setDataAnalysis(doc));
        dfv.isFull();
        return dfv;
    }

    /**
     * 设置dataFile参数
     *
     * @param doc
     * @param dfv
     * @return
     */
    private DataFileVo setDataFile(Document doc, DataFileVo dfv) {

        Elements dataFile = doc.select("dataFile");
        if (dataFile.size() <= 0) {
            throw new RuntimeException("配置文件 dataFile标签 解析异常");
        }
        dfv.setReadFile(parametersIsEmptyByTrim(dataFile.get(0).select("readFile").text(), null));
        dfv.setReadFIleCharset(parametersIsEmptyByTrim(dataFile.get(0).select("readFIleCharset").text(), CHARSETDEFAULT));
        dfv.setSuffix(parametersIsEmptyByTrim(dataFile.get(0).select("suffix").text(), null));
        dfv.setOutFile(parametersIsEmptyByTrim(dataFile.get(0).select("outFile").text(), null));
        dfv.setOutFileCharset(parametersIsEmptyByTrim(dataFile.get(0).select("outFileCharset").text(), CHARSETDEFAULT));
        return dfv;
    }

    /**
     * 设置DataAnalysis
     *
     * @param doc
     * @return
     */
    private List<DataAnalysisVo> setDataAnalysis(Document doc) {

        Elements dataAnalysis = doc.select("dataAnalysis");
        if (dataAnalysis.size() <= 0) {
            throw new RuntimeException("配置文件 dataAnalysis标签 解析异常");
        }
        List<DataAnalysisVo> dataAnalysisVoList = new ArrayList<>();
        for (Element da : dataAnalysis) {
            DataAnalysisVo dav = new DataAnalysisVo();
            dav.setId(parametersIsEmptyByTrim(da.select("id").text(), null));
            dav.setExtendsInId(parametersIsEmptyByTrim(da.select("extendsInId").text(), null));
            dav.setSelect(parametersIsEmptyByTrim(da.select("select").text(), null));
            dav.setValueType(parametersIsEmptyByTrim(da.select("valueType").text(), null));
            dav.setColumn(parametersIsEmptyByTrim(da.select("column").text(), null));
            dav.setColumnName(parametersIsEmptyByTrim(da.select("columnName").text(), null));
            dataAnalysisVoList.add(dav);
        }
        return dataAnalysisVoList;
    }

    /**
     * 检查配置文件版本
     *
     * @param version 版本号
     */
    private void checkVersion(Elements version) {
        if (new Double(version.text()) != 1.0) {
            throw new RuntimeException("配置文件版本异常");
        }
    }

    /**
     * 校对参数是否为空
     *
     * @param parameters  参数
     * @param valueOfNull 默认参数
     * @return 参数
     */
    private String parametersIsEmptyByTrim(String parameters, String valueOfNull) {
        if (StringTools.isNotEmptyByTrim(parameters)) {
            return valueOfNull;
        } else {
            return parameters;
        }
    }

    /**
     * 默认参数
     */
    private static String CHARSETDEFAULT = "utf-8";

}
