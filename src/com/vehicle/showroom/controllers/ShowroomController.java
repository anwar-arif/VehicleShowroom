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

    public void process(String[] args) throws IOException, ParseException {
        CliController cliController = new CliController();

        Options opt = cliController.getCliOptions();
        FileStorageService fileStorageService = new FileStorageService();

        BasicParser parser = new BasicParser();
        CommandLine cl = parser.parse(opt, args);

        String cliErrorMsg = cliController.validateCli(cl);
        if (!cliErrorMsg.isEmpty()) {
            System.out.println(cliErrorMsg);
            return;
        }

        if (cl.hasOption('h')) {
            HelpFormatter f = new HelpFormatter();
            f.printHelp("Options Help", opt);
        } else if (cl.hasOption("add") || cl.hasOption("remove")) {
            Vehicle vehicle = cliController.getVehicleFromCli(cl);

            if (cl.hasOption("add")) fileStorageService.saveVehicle(vehicle);
            else fileStorageService.deleteVehicle(vehicle);
        } else if (cl.hasOption("list")) {

        }
    }
}
