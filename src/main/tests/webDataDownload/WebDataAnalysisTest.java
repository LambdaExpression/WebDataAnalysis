package webDataDownload;

import org.junit.Test;
import org.tcat.webDataAnalysis.load.LoadXML;

import java.math.BigInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by Lin on 2016/5/30.
 */
public class WebDataAnalysisTest {

    @Test
    public void testLoadXML() {
        System.out.println(LoadXML.load(ClassLoader.getSystemResourceAsStream("webDataAnalysis.xml"), "utf-8"));
    }

    @Test
    public void test() {

    }
}

