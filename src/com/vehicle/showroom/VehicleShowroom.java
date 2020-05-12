package com.vehicle.showroom;

import com.vehicle.showroom.controllers.ShowroomController;

public class VehicleShowroom {

    public static void main(String[] args) {
        try {
            new ShowroomController().process(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
