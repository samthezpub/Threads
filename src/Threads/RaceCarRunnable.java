package Threads;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import static java.lang.Thread.sleep;

public class RaceCarRunnable extends Car {

    private int passed;
    private int distance;

    private boolean isFinish;
    private CountDownLatch countDownLatch;
    private long finishTime;

    public RaceCarRunnable(String name, int maxSpeed, int distance, CountDownLatch countDownLatch) {
        super(name, maxSpeed);
        this.distance = distance;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        while (!isFinish){
            if (passed >= distance){
                isFinish = true;
            }

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            printCarProgress();

            passed += getRandomSpeed();
            countDownLatch.countDown();

            Date date = new Date();
            finishTime = date.getTime() - Race.startRaceTime.get();


        };

        System.out.println(getName() + " FINISHED! " + "TIME: " + Race.convertToTime(finishTime));

    }

    private int getRandomSpeed(){
        Random random = new Random();
        return random.nextInt(getMaxSpeed()/2, getMaxSpeed());
    }

    public void printCarProgress(){
        System.out.println(getName() + " => " + "speed: "+ getRandomSpeed()+ ";" + "progress: "
                + passed + "/" + distance);
    }

    public long getFinishTime() {
        return finishTime;
    }

    public boolean getIsFinish() {
        return isFinish;
    }
}
