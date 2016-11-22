package org.tcat.webDataAnalysis.enums;

/**
 * 文件解析优先级
 * Created by Lin on 2016/6/8.
 */
public enum DataAnalysisPriority {

    /**
     * 优先级1
     */
    priorityFisrt(1),
    /**
     * 优先级2
     */
    prioritySecond(2),
    /**
     * 优先级3
     */
    priorityThird(3),
    /**
     * 优先级4
     */
    priorityFourth(4);

    private final Integer value;

    private DataAnalysisPriority(Integer value){
        this.value=value;
    }

    public static DataAnalysisPriority findByValue(int value){
        switch (value){
            case 1:
                return priorityFisrt;
            case 2:
                return prioritySecond;
            case 3:
                return priorityThird;
            case 4:
                return priorityFourth;
            default:
                return null;
        }
    }

    public int value(){
        return value.intValue();
    }

    public String toString(){
        return value.toString();
    }

}
