package org.tcat.tools;

/**
 * Created by Lin on 2016/5/17.
 */
public class StringTools {

    public static Boolean isNotEmptyByTrim(String str){
        if(str==null||"".equals(str.trim())){
            return true;
        }else{
            return false;
        }
    }

    public static String filterFolderName(String name){
        return name.replace("\\","").replace("/","").replace(":","").replace("*","").replace("?","").replace("\"","").replace("<","").replace(">","").replace("|","");
    }

}
