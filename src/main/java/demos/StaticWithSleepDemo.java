package demos;

import java.util.concurrent.ScheduledExecutorService;

/**
 * 静态代码块中使用lambda表达式表示的线程调用静态成员导致死锁
 * https://blog.csdn.net/jobsandczj/article/details/96628832
 */
public class StaticWithSleepDemo {
    static int init = 0;
    static{
        //如果使用lambda表达式，编译后可以看出，是生成了一个调用lambda的静态方法
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("thread in static block");
                    // 加上对init的使用，则死锁
                    //System.out.println(init);
                    // 调用静态方法,同样会死锁
                    sout();
                }
            }
        },"thread in static block").start();
        System.out.println(init);
        System.out.println("enter static code");
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            System.out.println("sleep interrupted ");
        }
    }

    public static void sout(){
        System.out.println("invoking method sout()");
    }

    public static void main(String[] args) {
        System.out.println("main thread");
        new Thread(()->{
            while (true){
                System.out.println("thread in main method");
            }
        },"thread in static block").start();
        StaticWithSleepDemo.sout();
    }
}
