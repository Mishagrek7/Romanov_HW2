package ru.ifellow.cars.parameters.models;

import ru.ifellow.cars.parameters.Car;

public class Lada extends Car {
    public Lada(String model, int year, String transmission, String color, double engineCapacity, String fuelType, String drive) {
        super(model, year, transmission, color, "ru.ifellow.cars.parameters.models.Lada", engineCapacity, fuelType, drive);
    }
}

