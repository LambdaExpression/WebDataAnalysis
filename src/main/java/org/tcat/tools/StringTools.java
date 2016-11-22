package org.tcat.tools;

/**
 * Created by Lin on 2016/5/17.
 */
public class StringTools {

    /**
     * 判断str是否为null和""，是true，否flase
     * @param str
     * @return
     */
    public static Boolean isNotEmptyByTrim(String str){
        if(str==null||"".equals(str.trim())){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 从name中过滤 "\","/",":","*","?",""","<",">","|"
     * @param name
     * @return
     */
    public static String filterFolderName(String name){
        return name.replace("\\","").replace("/","").replace(":","").replace("*","").replace("?","").replace("\"","").replace("<","").replace(">","").replace("|","");
    }

}
