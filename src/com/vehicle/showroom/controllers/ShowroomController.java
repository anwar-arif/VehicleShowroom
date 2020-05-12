package com.vehicle.showroom.controllers;

import com.vehicle.showroom.model.Vehicle;
import com.vehicle.showroom.services.FileStorageService;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.IOException;

public class ShowroomController {

    public ShowroomController() {}

    /**
     * Process the command line arguments with proper actions
     * @param args
     * @throws IOException
     * @throws ParseException
     */
    public void process(String[] args) throws IOException, ParseException {
        CliController cliController = new CliController();
        Options opt = cliController.getCliOptions();

        FileStorageService fileStorageService = new FileStorageService();

        BasicParser parser = new BasicParser();
        CommandLine commandLine = parser.parse(opt, args);

        if (commandLine.hasOption('h')) {
            HelpFormatter f = new HelpFormatter();
            f.printHelp("Options Help", opt);
        }
        else if (commandLine.hasOption("add") || commandLine.hasOption("remove")) {
            String cliErrorMsg = cliController.validateCli(commandLine);
            if (!cliErrorMsg.isEmpty()) {
                System.out.println(cliErrorMsg);
                System.out.println("See -h for help");
                return;
            }

            Vehicle vehicle = cliController.getVehicleFromCli(commandLine);

            if (commandLine.hasOption("add")) fileStorageService.saveVehicle(vehicle);
            else fileStorageService.deleteVehicle(vehicle);
        }
        else if (commandLine.hasOption("list")) {
            cliController.showVehicleList(fileStorageService, commandLine.hasOption("visitorCount"));
        }
    }
}
