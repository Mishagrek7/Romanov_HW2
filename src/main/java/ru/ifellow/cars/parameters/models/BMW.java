package ru.ifellow.cars.parameters.models;

import ru.ifellow.cars.parameters.Car;

public class BMW extends Car {
    public BMW(String model, int year, String transmission, String color, double engineCapacity, String fuelType, String drive) {
        super(model, year, transmission, color, "Германия", engineCapacity, fuelType, drive);
    }
}
