package Threads;

import java.util.ArrayList;
import java.util.List;

public class Race {
    public static void main(String[] args) {
        ArrayList<RaceCarRunnable> cars = new ArrayList<>();
        cars.add(new RaceCarRunnable("Форрари", 320, 1900));
        cars.add(new RaceCarRunnable("Ламборгини", 350, 2100));
        cars.add(new RaceCarRunnable("Порше", 310, 3300));


        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < cars.size(); i++) {
            threads.add(new Thread(cars.get(i)));
        }

    }
}
