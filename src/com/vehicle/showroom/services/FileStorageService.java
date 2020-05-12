package com.vehicle.showroom.services;

import com.vehicle.showroom.model.Vehicle;
import com.google.gson.*;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.List;

public class FileStorageService {
    public String directory;
    public String filePath;

    public FileStorageService() throws IOException {
        this.directory = System.getProperty("user.home") + File.separator + "Documents" +
                File.separator + "Vehicles";

        this.filePath = this.directory + File.separator + "vehicles.txt";

        this.createFile();
    }

    public void createFile() throws IOException {
        File dir = new File(this.directory);
        if (!dir.exists()) {
            dir.mkdir();
        }

        File file = new File(this.filePath);

        file.createNewFile();
    }

    public void saveVehicle(Vehicle vehicle) throws IOException {
        String json = new Gson().toJson(vehicle);
        FileWriter fileWriter = new FileWriter(filePath, true);
        fileWriter.append(json);
        fileWriter.append(System.lineSeparator());
        fileWriter.close();

        Vehicle obj = new Gson().fromJson(json, Vehicle.class);
        System.out.println("Vehicle added successfully");
    }

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
}
