package Threads;

import java.util.Random;

import static java.lang.Thread.sleep;

public class RaceCarRunnable extends Car {

    private int passed;
    private int distance;

    private boolean isFinish;

    public RaceCarRunnable(String name, int maxSpeed, int distance) {
        super(name, maxSpeed);
        this.distance = distance;
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

            passed += getRandomSpeed();
        }
    }

    private int getRandomSpeed(){
        Random random = new Random();
        return random.nextInt(getMaxSpeed(), getMaxSpeed()/2);
    }

    public void printCarProgress(){
        System.out.println(getName() + " => " + "speed: "+ getRandomSpeed()+ ";" + "progress: "
                + passed + "/" + distance + "\n");
    }
}
