package org.tcat.webDataDownload.enums;

/**
 * 消息报文格式类型
 * Created by Lin on 2016/5/17.
 */
public enum HttpMessageType {

    get(1),
    post(2);

    private final Integer value;

    private HttpMessageType(Integer value) {
        this.value = value;
    }

    public Integer value() {
        return value;
    }

    public static HttpMessageType findByValue(int value) {
        switch (value) {
            case 1:
                return get;
            case 2:
                return post;
            default:
                return null;
        }
    }

    public String toString() {
        return value.toString();
    }

}
