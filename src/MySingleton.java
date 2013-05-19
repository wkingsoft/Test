

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class MySingleton {

    private static MySingleton mySingleton;

    /**
     * 原子计数器
     */
    private static AtomicInteger count=new AtomicInteger(0);

    private MySingleton() {

        //模拟长时间初始化
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(count.incrementAndGet());
    }
    
    private static class SingletonHolder{
    	 private static MySingleton instance = new MySingleton();   
    }
    public static MySingleton getInstance() {
        return SingletonHolder.instance;
    }
    

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int c = 0; c < 40; c++) {
            executorService.execute(new TestRun());
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static class TestRun implements Runnable {

        @Override
        public void run() {
            System.out.println(MySingleton.getInstance());
        }
    }

}