package org.tcat.webDataAnalysis.load.vo;

import org.tcat.tools.StringTools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 配置文件解析Vo
 * Created by Lin on 2016/5/30.
 */
public class DataFileVo implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;
    /**
     * 读取文件地址
     */
    private String readFile;
    /**
     * 读取文件的字符集
     */
    private String readFIleCharset;
    /**
     * 文本后缀
     */
    private String suffix;
    /**
     * 输出文件地址
     */
    private String outFile;
    /**
     * 输出文件的字符集
     */
    private String outFileCharset;
    /**
     * 页面解析条件
     */
    private List<DataAnalysisVo> dataAnalysisList;

    /**
     * 获取 读取文件地址
     */
    public String getReadFile() {
        return readFile;
    }

    /**
     * 设置 读取文件地址
     */
    public void setReadFile(String readFile) {
        this.readFile = readFile;
    }

    /**
     * 获取 读取文件的字符集
     */
    public String getReadFIleCharset() {
        return readFIleCharset;
    }

    /**
     * 设置 读取文件的字符集
     */
    public void setReadFIleCharset(String readFIleCharset) {
        this.readFIleCharset = readFIleCharset;
    }

    /**
     * 获取 文本后缀
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * 设置 文本后缀
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * 获取 输出文件地址
     */
    public String getOutFile() {
        return outFile;
    }

    /**
     * 设置 输出文件地址
     */
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }

    /**
     * 获取 输出文件的字符集
     */
    public String getOutFileCharset() {
        return outFileCharset;
    }

    /**
     * 设置 输出文件的字符集
     */
    public void setOutFileCharset(String outFileCharset) {
        this.outFileCharset = outFileCharset;
    }

    /**
     * 获取 页面解析条件
     */
    public List<DataAnalysisVo> getDataAnalysisList() {
        return dataAnalysisList;
    }

    /**
     * 设置 页面解析条件
     */
    public void setDataAnalysisList(List<DataAnalysisVo> dataAnalysisList) {
        this.dataAnalysisList = dataAnalysisList;
    }

    @Override
    public String toString() {

        StringBuffer content = new StringBuffer();
        content.append("DataFileVo{\n");
        List<StringBuffer> dataList = new ArrayList<>();
        if (readFile != null) {
            dataList.add(new StringBuffer().append("\treadFile='").append(readFile).append("'"));
        }
        if (readFIleCharset != null) {
            dataList.add(new StringBuffer().append("\treadFIleCharset='").append(readFIleCharset).append("'"));
        }
        if (suffix != null) {
            dataList.add(new StringBuffer().append("\tsuffix='").append(suffix).append("'"));
        }
        if (outFile != null) {
            dataList.add(new StringBuffer().append("\toutFile='").append(outFile).append("'"));
        }
        if (outFileCharset != null) {
            dataList.add(new StringBuffer().append("\toutFileCharset='").append(outFileCharset).append("'"));
        }
        if (dataAnalysisList != null) {
            dataList.add(new StringBuffer().append("\tdataAnalysisList='").append(dataAnalysisList).append("'"));
        }
        return content.append(dataList.stream().collect(Collectors.joining(",\n"))).append("\n}").toString();

    }

    /**
     * 检查信息的完整性
     *
     * @return
     */
    public void isFull() {
        if (StringTools.isNotEmptyByTrim(readFile)) {
            throw new RuntimeException("readFile不能为空");
        }
        if (StringTools.isNotEmptyByTrim(suffix)) {
            throw new RuntimeException("suffix不能为空");
        }
        if (StringTools.isNotEmptyByTrim(outFile)) {
            throw new RuntimeException("outFile不能为空");
        }
        if (dataAnalysisList.isEmpty()) {
            throw new RuntimeException("dataAnalysisList不能为空");
        }
    }

}
