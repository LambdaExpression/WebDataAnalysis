package webDataDownload;

import org.junit.Test;
import org.tcat.webDataAnalysis.load.LoadXML;

/**
 * Created by Lin on 2016/5/30.
 */
public class WebDataAnalysisTest {

    @Test
    public void testLoadXML(){
        System.out.println(new LoadXML().load("E:\\idea\\WebDataAnalysis\\src\\org\\tcat\\webDataAnalysis\\webDataAnalysis.xml","utf-8"));
    }
}
