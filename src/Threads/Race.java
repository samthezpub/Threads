package Threads;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.Thread.sleep;

public class Race {

    public static AtomicLong startRaceTime;

    public static AtomicLong getStartRaceTime() {
        return startRaceTime;
    }

    static void startRace(List<Thread> carThreads, ArrayList<RaceCarRunnable> cars) {
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
                    System.out.println("GO!");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }



                System.out.println(startRaceTime);

                for (int i = 0; i < carThreads.size(); i++) {
                    carThreads.get(i).start();
                }


            }
        }).start();
    }

    public static void main(String[] args) {
        ArrayList<RaceCarRunnable> cars = new ArrayList<>();
        CountDownLatch raceResult = new CountDownLatch(cars.size());
        cars.add(new RaceCarRunnable("Форрари", 320, 1900, raceResult));
        cars.add(new RaceCarRunnable("Ламборгини", 350, 2100, raceResult));
        cars.add(new RaceCarRunnable("Порше", 310, 3300, raceResult));


        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < cars.size(); i++) {
            threads.add(new Thread(cars.get(i)));
        }

        Instant currentTime = Instant.now();
        startRaceTime = new AtomicLong(currentTime.toEpochMilli());
        startRace(threads, cars);



        var min= cars.stream().min((o1, o2) -> Long.compare(o1.getFinishTime().get(), o2.getFinishTime().get())).get();

        System.out.println(min);
    }
}
