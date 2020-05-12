package com.vehicle.showroom.services;

import com.google.gson.Gson;
import com.vehicle.showroom.model.Vehicle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileStorageService {
    private String directory;
    private String filePath;

    public FileStorageService() throws IOException {
        this.directory = System.getProperty("user.home") + File.separator + "Documents" +
                File.separator + "Vehicles";

        this.filePath = this.directory + File.separator + "vehicles.txt";

        this.createFile();
    }

    /**
     * creates file in the directory
     * @throws IOException
     */
    public void createFile() throws IOException {
        File dir = new File(this.directory);
        if (!dir.exists()) {
            dir.mkdir();
        }

        File file = new File(this.filePath);

        file.createNewFile();
    }

    /**
     * stores a vehicle in files as json
     * @param vehicle
     * @throws IOException
     */
    public void saveVehicle(Vehicle vehicle) throws IOException {
        String json = new Gson().toJson(vehicle);
        FileWriter fileWriter = new FileWriter(filePath, true);
        fileWriter.append(json);
        fileWriter.append(System.lineSeparator());
        fileWriter.close();

        Vehicle obj = new Gson().fromJson(json, Vehicle.class);
        System.out.println("Vehicle added successfully");
    }

    /**
     * deletes a vehicle from the file
     * @param vehicle
     * @throws IOException
     */
    public void deleteVehicle(Vehicle vehicle) throws IOException {
        String json = new Gson().toJson(vehicle);
        boolean hasRemoved = false;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));

        String currentLine;
        String trimmedLine;
        String linesToWrite = "";

        while ((currentLine = bufferedReader.readLine()) != null) {
            trimmedLine = currentLine.trim();
            if (trimmedLine.equals(json) && !hasRemoved) {
                hasRemoved = true;
                continue;
            }
            linesToWrite += trimmedLine + System.lineSeparator();
        }
        bufferedReader.close();

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
        bufferedWriter.write(linesToWrite.toString());
        bufferedWriter.close();

        System.out.println(hasRemoved ? "Vehicle removed successfully" : "No such vehicle found");
    }

    /**
     * gathers all vehicles information from the file
     * @return a list of json string
     * @throws IOException
     */
    public List<String> getVehiclesAsJsonString() throws IOException {
        List<String> vehicles = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));

        String currentLine;
        String trimmedLine;

        while ((currentLine = bufferedReader.readLine()) != null) {
            trimmedLine = currentLine.trim();
            vehicles.add(trimmedLine);
        }
        bufferedReader.close();

        return vehicles;
    }
}
