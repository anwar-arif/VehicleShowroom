package com.vehicle.showroom.controllers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import com.vehicle.showroom.model.Vehicle;
import com.vehicle.showroom.services.FileStorageService;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CliController {
    public Options getCliOptions() {
        Options opt = new Options();

        opt.addOption("h", "help", false, "Print help for this application");

        opt.addOption("a", "add", false, "Add a vehicle");
        opt.addOption("r", "remove", false, "Remove a vehicle");

        opt.addOption("m", "modelNumber", true, "Vehicle model");
        opt.addOption("et", "engineType", true, "Engine Type");
        opt.addOption("vt", "vehicleType", true, "Vehicle Type: normal, sports or heavy");

        opt.addOption("ep", "enginePower", true, "Engine Power");
        opt.addOption("ts", "tireSize", true, "Tire Size");
        opt.addOption("wgt", "weight", true, "Vehicle Weight");

        opt.addOption("turbo",true, "Is Turbo");

        opt.addOption("ls", "list", false, "List of vehicles");
        opt.addOption("vc", "visitorCount", false, "Show list of vehicles with visitor count");

//        -a -m model123 -et oil -vt sports -ep 150 -ts 50 -turbo true
        return opt;
    }

    public void showVehicleList(FileStorageService fileStorageService, boolean showVisitor) throws IOException {
        List<String> vehicles = fileStorageService.getVehiclesAsJsonString();
        int visitorCount = 30;

        for (String vehicle: vehicles) {
            JsonObject jsonObject = new JsonParser().parse(vehicle).getAsJsonObject();

            for (Map.Entry<String, JsonElement> item: jsonObject.entrySet()) {
                System.out.print(item.getKey() + ": " + item.getValue().toString() + " ");

                if (item.getValue().equals("sports")) visitorCount += 20;
            }
            if (showVisitor) System.out.print("visitor count: " + visitorCount);

            System.out.println();
        }
    }

    public Vehicle getVehicleFromCli(CommandLine cl) {
        Vehicle vehicle = new Vehicle();

        vehicle.setModelNumber(cl.getOptionValue("modelNumber"));
        vehicle.setEngineType(cl.getOptionValue("engineType"));
        vehicle.setVehicleType(cl.getOptionValue("vehicleType"));

        vehicle.setEnginePower(Integer.parseInt(cl.getOptionValue("enginePower")));
        vehicle.setTireSize(Integer.parseInt(cl.getOptionValue("tireSize")));

        if (cl.hasOption("weight")) vehicle.setWeight(Integer.parseInt(cl.getOptionValue("weight")));
        if (cl.hasOption("turbo")) vehicle.setTurbo(Boolean.parseBoolean(cl.getOptionValue("turbo")));

        return vehicle;
    }

    public String validateCli(CommandLine cl) {
        String errMsg = "";

        if (cl.getOptionValue("modelNumber") == null) errMsg += nonEmptyError("model number");
        if (cl.getOptionValue("engineType") == null) errMsg += nonEmptyError("engine type");
        if (cl.getOptionValue("turbo") == null) errMsg += booleanError("turbo");

        return errMsg;
    }

    public String nonEmptyError(String prop) {
        return (prop + " can't be empty");
    }
    public String booleanError(String prop) {
        return (prop + " must be true or false");
    }
}
