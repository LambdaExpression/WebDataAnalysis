package org.tcat.webDataDownload.load.vo;

import java.io.Serializable;

/**
 * Created by Lin on 2016/5/25.
 */
public class WebDataVo implements Serializable{

    private String readFile;
    private String readFIleCharset;
    private String outFile;
    private String outFileCharset;
    private Long time;
    private String type;

    public String getReadFile() {
        return readFile;
    }

    public void setReadFile(String readFile) {
        this.readFile = readFile;
    }

    public String getReadFIleCharset() {
        return readFIleCharset;
    }

    public void setReadFIleCharset(String readFIleCharset) {
        this.readFIleCharset = readFIleCharset;
    }

    public String getOutFile() {
        return outFile;
    }

    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }

    public String getOutFileCharset() {
        return outFileCharset;
    }

    public void setOutFileCharset(String outFileCharset) {
        this.outFileCharset = outFileCharset;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {

        return new StringBuffer().append("WebDataVo{")
                .append("readFile='").append(readFile).append("\',")
                .append("readFIleCharset='").append(readFIleCharset).append("\',")
                .append("outFile='").append(outFile).append("\',")
                .append("outFileCharset='").append(outFileCharset).append("\',")
                .append("time='").append(time).append("\',")
                .append("type='").append(type).append("\',")
                .append("}").toString();

    }
}
