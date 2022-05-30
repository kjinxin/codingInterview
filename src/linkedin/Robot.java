package linkedin;

import java.util.concurrent.atomic.AtomicInteger;

public class Robot {
    public int legs;
    public static AtomicInteger current = new AtomicInteger(0);
    public static Object lock = new Object();
    public Robot(int legs) {
        this.legs = legs;
    }

    public static void main(String args[])
            throws InterruptedException {
        Robot robot = new Robot(3);
        for(int i = 0; i < robot.legs; i ++) {
            new Thread(new MoveRobotLeg(i)).start();
        }
    }
    private static class MoveRobotLeg implements Runnable {
        private int leg;
        public MoveRobotLeg(int leg) {
            this.leg = leg;
        }

        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()) {
                synchronized (lock) {
                    while (current.get() != leg) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println(leg);
                    current.set((leg + 1) % 3);
                    lock.notifyAll();
                }
            }
        }
    }
}
