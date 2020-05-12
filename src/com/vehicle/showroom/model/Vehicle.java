package com.vehicle.showroom.model;

public class Vehicle {
    private String modelNumber;
    private String engineType;
    private String vehicleType;

    private Integer enginePower;
    private Integer tireSize;
    private Integer weight;

    private Boolean turbo;

    public Vehicle() {}
    public Vehicle(String modelNumber, String engineType, String vehicleType,
                   Integer enginePower, Integer tireSize, Integer weight, Boolean turbo) {
        this.modelNumber = modelNumber;
        this.engineType = engineType;
        this.vehicleType = vehicleType;

        this.enginePower = enginePower;
        this.tireSize = tireSize;
        this.weight = weight;

        this.turbo = turbo;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setEnginePower(Integer enginePower) {
        this.enginePower = enginePower;
    }

    public void setTireSize(Integer tireSize) {
        this.tireSize = tireSize;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setTurbo(Boolean turbo) {
        this.turbo = turbo;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public String getEngineType() {
        return engineType;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public Integer getEnginePower() {
        return enginePower;
    }

    public Integer getTireSize() {
        return tireSize;
    }

    public Integer getWeight() {
        return weight;
    }

    public Boolean getTurbo() {
        return turbo;
    }
}
