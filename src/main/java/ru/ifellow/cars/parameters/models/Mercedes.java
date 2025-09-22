package ru.ifellow.cars.parameters.models;

import ru.ifellow.cars.parameters.Car;

public class Mercedes extends Car {
    public Mercedes(String model, int year, String transmission, String color, double engineCapacity, String fuelType, String drive) {
        super(model, year, transmission, color, "Германия", engineCapacity, fuelType, drive);
    }
}