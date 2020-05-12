package com.company;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;

public class Main {

    public static void main(String[] args) {
        try {
            Options opt = new CommandLineOptions().getCliOptions();

            BasicParser parser = new BasicParser();
            CommandLine cl = parser.parse(opt, args);

            if ( cl.hasOption('h') ) {
                HelpFormatter f = new HelpFormatter();
                f.printHelp("Options Help", opt);
            } else if (cl.hasOption("add")) {
                System.out.println("okay");
            }
            else {
                System.out.println(cl.getOptionValue("u"));
                System.out.println(cl.getOptionValue("dsn"));
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
