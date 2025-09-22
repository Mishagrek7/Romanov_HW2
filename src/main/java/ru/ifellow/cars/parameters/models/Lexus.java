package ru.ifellow.cars.parameters.models;

import ru.ifellow.cars.parameters.Car;

public class Lexus extends Car {
    public Lexus(String model, int year, String transmission, String color, double engineCapacity, String fuelType, String drive) {
        super(model, year, transmission, color, "Япония", engineCapacity, fuelType, drive);
    }
}