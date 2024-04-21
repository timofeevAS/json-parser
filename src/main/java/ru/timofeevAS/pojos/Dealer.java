package ru.timofeevAS.pojos;

import java.util.ArrayList;
import java.util.Arrays;

public class Dealer {
    private Car[] cars;
    private Person manager;

    public Dealer() {
        this.cars = new Car[]{};
        this.manager = new Person();
    }

    @Override
    public String toString() {
        return "Dealer{" +
                "cars=" + Arrays.toString(cars) +
                ", manager=" + manager +
                '}';
    }

    public Car[] getCars() {
        return cars;
    }

    public void setCars(Car[] cars) {
        this.cars = cars;
    }

    public Person getManager() {
        return manager;
    }

    public void setManager(Person manager) {
        this.manager = manager;
    }
}
