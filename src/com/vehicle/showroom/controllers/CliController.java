package com.vehicle.showroom.controllers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vehicle.showroom.model.Vehicle;
import com.vehicle.showroom.services.FileStorageService;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CliController {

    private List<String> allowedVehicleTypes = Arrays.asList("normal", "sports", "heavy");
    private List<String> allowedEngineTypes = Arrays.asList("oil", "diesel", "gas");
    private List<String> allowedTurboValues = Arrays.asList("true", "false");

    /**
     * produce allowed commands
     * @return options for command line
     */
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

        return opt;
    }

    /**
     * show available vehicle list
     * @param fileStorageService
     * @param showVisitor
     * @throws IOException
     */
    public void showVehicleList(FileStorageService fileStorageService, boolean showVisitor) throws IOException {
        List<String> vehicles = fileStorageService.getVehiclesAsJsonString();
        int visitorCount = 30;

        for (String vehicle: vehicles) {
            JsonObject jsonObject = new JsonParser().parse(vehicle).getAsJsonObject();

            for (Map.Entry<String, JsonElement> item: jsonObject.entrySet()) {
                System.out.print(item.getKey() + ": " + item.getValue().toString() + " ");

                if (item.getValue().toString().equals("sports")) visitorCount += 20;
            }
            if (showVisitor) System.out.print("visitor count: " + visitorCount);

            System.out.println();
        }
    }

    /**
     * parse a vehicle from command line
     * @param commandLine
     * @return parsed vehicle from command line arguments
     */
    public Vehicle getVehicleFromCli(CommandLine commandLine) {
        Vehicle vehicle = new Vehicle();

        vehicle.setModelNumber(commandLine.getOptionValue("modelNumber"));
        vehicle.setEngineType(commandLine.getOptionValue("engineType"));
        vehicle.setVehicleType(commandLine.getOptionValue("vehicleType"));

        vehicle.setEnginePower(Integer.parseInt(commandLine.getOptionValue("enginePower")));
        vehicle.setTireSize(Integer.parseInt(commandLine.getOptionValue("tireSize")));

        if (commandLine.hasOption("weight")) vehicle.setWeight(Integer.parseInt(commandLine.getOptionValue("weight")));
        if (commandLine.hasOption("turbo")) vehicle.setTurbo(Boolean.parseBoolean(commandLine.getOptionValue("turbo")));

        return vehicle;
    }

    /**
     * validate each commands
     * @param commandLine
     * @return error message if there is any error, otherwise empty
     */
    public String validateCli(CommandLine commandLine) {
        String errMsg = "";

        /**
         * Checking error for common properties
         */
        if (commandLine.getOptionValue("modelNumber") == null) errMsg += nonEmptyError("model number");
        if (commandLine.getOptionValue("engineType") == null) errMsg += nonEmptyError("engine type");
        if (commandLine.getOptionValue("tireSize") == null) errMsg += nonEmptyError("tire size");
        if (commandLine.getOptionValue("enginePower") == null) errMsg += nonEmptyError("engine power");
        if (commandLine.getOptionValue("vehicleType") == null) errMsg += nonEmptyError("vehicle type");

        if (!errMsg.isEmpty()) {
            return errMsg;
        }

        if (!allowedEngineTypes.contains(commandLine.getOptionValue("engineType"))) {
            errMsg += "engine type must be any of these: " + allowedEngineTypes.toString() + "\n";
        }

        String vehicleType = commandLine.getOptionValue("vehicleType");

        if (!allowedVehicleTypes.contains(vehicleType)) {
            errMsg += "vehicle type must be any of these: " + allowedVehicleTypes.toString() + "\n";
        }

        if (!errMsg.isEmpty()) {
            return errMsg;
        }

        /**
         * Checking if normal vehicle has no extra properties
         */
        if (vehicleType.equals("normal")) {
            if (commandLine.getOptionValue("turbo") != null) {
                errMsg += "normal vehicle can't have turbo\n";
            }
            if (commandLine.getOptionValue("weight") !=  null) {
                errMsg += "normal vehicle can't have weight\n";
            }
        }

        /**
         * Checking error for sports vehicle
         */
        if (vehicleType.equals("sports")) {
            String turbo = commandLine.getOptionValue("turbo");
            if (turbo == null || !allowedTurboValues.contains(turbo)) errMsg += booleanError("turbo");
            if (!commandLine.getOptionValue("engineType").equals("gas")) {
                errMsg += "engine type for sports vehicle must be gas\n";
            }
        }

        /**
         * Checking error for heavy vehicle
         */
        if (vehicleType.equals("heavy")) {
            if (!commandLine.getOptionValue("engineType").equals("diesel")) {
                errMsg += "engine type for heavy vehicle must be diesel\n";
            }
        }

        return errMsg;
    }

    public String nonEmptyError(String prop) {
        return (prop + " can't be empty\n");
    }
    public String booleanError(String prop) {
        return (prop + " must be true or false\n");
    }
}
