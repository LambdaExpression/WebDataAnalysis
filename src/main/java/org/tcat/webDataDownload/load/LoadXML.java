package org.tcat.webDataDownload.load;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.tcat.tools.StringTools;
import org.tcat.webDataDownload.load.vo.WebDataVo;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;


/**
 * 读取配置文件
 * Created by Lin on 2016/5/16.
 */
public final class LoadXML {

    /**
     * 无参构造
     */
    public LoadXML() {
    }

    /**
     * 读取配置文件
     *
     * @param input    文件路径
     * @param charset 文件读取字符集
     * @return 配置数据
     */
    public List<WebDataVo> load(InputStream input, String charset) {
        if (input == null || StringTools.isNotEmptyByTrim(charset))
            return null;
        List<WebDataVo> configurationData = new ArrayList<>();
        String content = "";
        try {
            StringWriter writer = new StringWriter();
            IOUtils.copy(input, writer, charset);
            content = writer.toString();
        } catch (IOException e) {
            throw new RuntimeException("文件读取异常", e);
        }

        Document doc = Jsoup.parse(content, charset, Parser.xmlParser());
        checkVersion(doc.select("version"));
        Elements dataDownloads = doc.select("dataDownload");
        for (Element dataDownload : dataDownloads) {
            WebDataVo data = new WebDataVo();
            data.setReadFile(pathIsEmptyByTrim(dataDownload.select("readFile").text()).toLowerCase());
            data.setReadFIleCharset(parametersIsEmptyByTrim(dataDownload.select("readFIleCharset").text(), CHARSETDEFAULT).toLowerCase());
            data.setOutFile(pathIsEmptyByTrim(dataDownload.select("outFile").text()).toLowerCase());
            data.setOutFileCharset(parametersIsEmptyByTrim(dataDownload.select("outFileCharset").text(), CHARSETDEFAULT).toLowerCase());
            data.setTime(parametersIsEmptyByTrim(dataDownload.select("time").text(), TIMEDEFAULT));
            data.setType(parametersIsEmptyByTrim(dataDownload.select("type").text(), TYPEDEFAULT).toLowerCase());
            configurationData.add(data);
        }
        return configurationData;
    }

    /**
     * 校对路径参数是否有误
     *
     * @param path 参数
     * @return 结果参数
     */
    private String pathIsEmptyByTrim(String path) {
        if (StringTools.isNotEmptyByTrim(path)) {
            throw new RuntimeException("路径参数读取失败");
        } else {
            return path;
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
     * 校对参数是否为空
     *
     * @param parameters  参数
     * @param valueOfNull 默认参数
     * @return 参数
     */
    private Long parametersIsEmptyByTrim(String parameters, Long valueOfNull) {
        if (StringTools.isNotEmptyByTrim(parameters)) {
            return valueOfNull;
        } else {
            return Long.valueOf(parameters);
        }
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
     * 默认参数
     */
    private static String CHARSETDEFAULT = "utf-8";
    private static Long TIMEDEFAULT = 0L;
    private static String TYPEDEFAULT = "get";

}
