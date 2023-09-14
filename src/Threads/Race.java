package Threads;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.Thread.sleep;

public class Race {
    public static AtomicLong startRaceTime;
    private static boolean isFinished = false;
    public static String convertToTime(long time){
        int hours = (int) (time / (60 * 60 * 1000));
        int minutes = (int) (time / (60 * 1000)) % 60;
        int seconds = (int) (time / 1000) % 60;

        return hours + ":" + minutes + ":" + seconds;
    }

    static void startRace(List<Thread> cars) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("3...");
                    sleep(500);
                    System.out.println("2...");
                    sleep(500);
                    System.out.println("1...");
                    sleep(500);
                    System.out.println("GO!!!");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                Date date = new Date();
                startRaceTime = new AtomicLong(date.getTime());

                for (int i = 0; i < cars.size(); i++) {
                    cars.get(i).start();
                }

            }
        }).start();
    }

    public static void main(String[] args) {


        CountDownLatch countDownLatch = new CountDownLatch(3);

        ArrayList<RaceCarRunnable> carRunnables = new ArrayList<>();
        carRunnables.add(new RaceCarRunnable("Бугатти", 150, 2000, countDownLatch));
        carRunnables.add(new RaceCarRunnable("Феррари", 170, 2000, countDownLatch));
        carRunnables.add(new RaceCarRunnable("GT-400", 250, 2000, countDownLatch));


        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < carRunnables.size(); i++) {
            threads.add(new Thread(carRunnables.get(i)));
        }

        startRace(threads);

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (!carRunnables.stream().allMatch(RaceCarRunnable::getIsFinish)) {

                }
                ;

                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (carRunnables.stream().allMatch(RaceCarRunnable::getIsFinish)) {
                    String winnerName = carRunnables.stream().min(Comparator.comparingLong(RaceCarRunnable::getFinishTime)).get().getName();
                    System.out.println(winnerName);

                    isFinished = false;
                }

            }
        }).start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
