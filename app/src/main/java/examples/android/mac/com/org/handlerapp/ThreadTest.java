package examples.android.mac.com.org.handlerapp;

/**
 * Created by User on 10/22/2016.
 */

public class ThreadTest {

    private static ThreadTest instance;

    public static ThreadTest getInstance(){
        if(instance == null){
            synchronized (ThreadTest.class){
                if(instance == null){
                    instance = new ThreadTest();
                }
            }
        }
        return instance;
    }


    public static void main(String...args){
        Thread t1 = new T1();
        t1.start();

        new Thread(new T2()).start();
    }

    public static class T1 extends Thread{
        @Override
        public void run() {
            super.run();
            System.out.println("This is thread 1");
        }
    }

    public static class T2 implements Runnable{
        @Override
        public void run() {
            System.out.println("This is thread 2");
        }
    }

}
