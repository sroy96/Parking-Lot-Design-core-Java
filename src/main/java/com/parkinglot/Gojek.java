package com.parkinglot;

import com.parkinglot.AppServices.CommandService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
public class Gojek {
    public static void main(String[] args) throws Exception {
        CommandService commandService = CommandService.getInstance();
        BufferedReader bufferedReader;

        if (args.length == 0) {
            // Input Command Line Reader
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        } else {
            // Input File Reader
            String filePath = args[0];
            File inputFile = new File(filePath);
            bufferedReader = new BufferedReader(new FileReader(inputFile));
        }

        while (true) {
            String commandText = bufferedReader.readLine();
            if (commandText == null || "exit".equalsIgnoreCase(commandText)) {
                break;
            } else {
                 boolean executionSuccess = commandService.execute(commandText);
                /**
                 * Checking the execution status if fails then the break
                 */
                if (!executionSuccess) {
                    break;
                }
            }
        }
    }
}
