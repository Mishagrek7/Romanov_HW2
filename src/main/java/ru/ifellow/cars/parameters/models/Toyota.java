package ru.ifellow.cars.parameters.models;

import ru.ifellow.cars.parameters.Car;

public class Toyota extends Car {
    public Toyota(String model, int year, String transmission, String color, double engineCapacity, String fuelType, String drive) {
        super(model, year, transmission, color, "ru.ifellow.cars.parameters.models.Toyota", engineCapacity, fuelType, drive);
    }
}
