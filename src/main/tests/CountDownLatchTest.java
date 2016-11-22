import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Lin on 2016/6/8.
 */
public class CountDownLatchTest implements Runnable{

    public CountDownLatchTest(CountDownLatch latch){
        this.latch=latch;
    }

    public void run(){
        try{
            /*
             * 逻辑语句
             */
            test2.TEST.getInstance().put(String.valueOf(new Random().nextInt(10000)+"-"+new Random().nextInt(10000)+"-"+latch.getCount()),String.valueOf(new Random().nextInt(100)));
            Map<String,String> map=test2.TEST.getInstance();
//            System.out.println(latch.getCount()+" ----> "+map.toString()+" --- "+map.size());


//            test3.getInstance().put(String.valueOf(new Random().nextInt(10000)+"-"+new Random().nextInt(10000)+"-"+latch.getCount()),String.valueOf(new Random().nextInt(100)));
//            Map<String,String> map=test3.getInstance();
//            System.out.println(latch.getCount()+" ----> "+map.toString()+" --- "+map.size());

//            System.out.println("\n----------------------\n");

        }finally {
            /*
             * synchronized 是为了保证打印输出时线程安全，不是因为 latch.countDown(); 。
             * latch.countDown(); 是被final修饰的，属于线程安全
             */
            synchronized (this){
                latch.countDown();
//                System.err.println(new StringBuffer().append("剩余计数器：").append(latch.getCount()).append("个"));
            }
        }
    }

    private CountDownLatch latch;

    public static void main(String[] args) {
        int threadCount=20;
        int count=10000;
        ExecutorService pool= Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch=new CountDownLatch(count);
        try{
            for(int i=0;i<count;i++){
                pool.submit(new CountDownLatchTest(latch));
            }
            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            pool.shutdown();
        }


        Map<String,String> map=test2.TEST.getInstance();
//        Map<String,String> map=test3.getInstance();
        System.out.println("later ----> "+map.size());
    }

}

enum test2 {
    TEST;
    private Map<String, String> loginSessionMap;
    private test2() {
        loginSessionMap = new ConcurrentHashMap<String, String>();
    }
    public Map<String, String> getInstance(){
        return loginSessionMap;
    }
}

class test3 {

    private static Map<String, String> loginSessionMap = new ConcurrentHashMap<String, String>();

    private test3() {
    }

    public static Map<String, String> getInstance() {
        return loginSessionMap;
    }

}
