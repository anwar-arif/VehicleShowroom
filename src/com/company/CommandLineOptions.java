package com.company;

import org.apache.commons.cli.Options;

public class CommandLineOptions {

    public CommandLineOptions() {}

    public Options getCliOptions() {
        Options opt = new Options();

        opt.addOption("h", "help", false, "Print help for this application");
        opt.addOption("a", "add", false, "Add a vehicle");
        opt.addOption("r", "remove", false, "Remove a vehicle");
        opt.addOption("m", "model", true, "Vehicle model");
        opt.addOption("et", "engineType", true, "Engine Type");
        opt.addOption("ep", "enginePower", true, "Engine Power");
        opt.addOption("ts", "tireSize", true, "Tire Size");
        opt.addOption("vt", "vehicleType", true, "Vehicle Type");
        opt.addOption("turbo",true, "Is Turbo");
        opt.addOption("wgt", "weight", true, "Vehicle Weight");
        opt.addOption("wgt", "weight", true, "Vehicle Weight");

        return opt;
    }
}
