package webDataDownload;

import java.math.BigInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by Lin on 2016/6/8.
 */
public class test {
    public static void main(String[] args) {
//        Stream<Integer> f = Stream.generate(new FibonacciSupplier());

    }
}

class FibonacciSupplier implements Supplier<BigInteger> {
    BigInteger a=new BigInteger("0");
    BigInteger b=new BigInteger("1");

    @Override
    public BigInteger get() {
        a=a.add(b).multiply(b=a);
        return b=a.add(b);
    }
}

