package com.parkinglot.AppServices;

import com.parkinglot.ExceptionHandle.AppException;
import com.parkinglot.Model.AllocationStatus;
import com.parkinglot.Model.CarModel;


import java.util.List;

public class CommandService {
private static  CommandService commandService;
private  CommandService(){

    }

/**
 * Singleton Class to return one single Instance of the class
 * @return CommandService instance
 */
public static  CommandService getInstance(){
        if (commandService== null){
            commandService = new CommandService();

        }
        return commandService;
}

    /**
     * All commands
     */
    private enum CommandLine {
        create_parking_lot, park, leave, status, registration_numbers_for_cars_with_colour,
        slot_numbers_for_cars_with_colour, slot_number_for_registration_number
    }

    /**
     *Validate and Execute Command
     */
   private interface Command {
        public void validate();

        public String execute();
    }

    /**
     *Getting the Commands from the source and validating it
     * @param command
     * @return CommandLine commandLine;
     */

    private CommandLine getCommandLine(String command){
        CommandLine commandLine = null;

        if(command== null){
            System.out.println("Not a valid Input");
        }
        else{
            String[] commandArray =command.split(" ");
            if("".equals(commandArray[0])){
                System.out.println("Empty Command");
            }
            else {
                try {
                    commandLine = CommandLine.valueOf(commandArray[0]);
                } catch (Exception e) {
                    System.out.println("Command not Available");
                }
            }
        }
        return commandLine;
    }

    /**
     *
     * @param executeString
     * @return
     */
    public boolean execute(String executeString) {

        CommandLine commandLine = getCommandLine(executeString);

        if (commandLine == null) {
            return false;
        }
        String[] commandStringArray = executeString.split(" ");
        Command command;

        switch (commandLine) {
            case create_parking_lot:
                command = new CreateParkingLot(commandStringArray);
                break;
            case park:
                command = new Park(commandStringArray);
                break;
            case leave:
                command = new Leave(commandStringArray);
                break;
            case status:
                command = new StatusCommand(commandStringArray);
                break;
            case registration_numbers_for_cars_with_colour:
                command = new RegistrationNumbersForColor(commandStringArray);
                break;
            case slot_numbers_for_cars_with_colour:
                command = new SlotNumbersForColor(commandStringArray);
                break;
            case slot_number_for_registration_number:
                command = new SlotNumber(commandStringArray);
                break;
            default:
                System.out.println("Unknown Command");
                return false;
        }

        try {
            command.validate();
        } catch (IllegalArgumentException e) {
            System.out.println("Please provide a valid argument");
            return false;
        }

        String output = "";
        try {
            output = command.execute();
        } catch (AppException e) {
            System.out.print(e.getMessage());
        } catch(Exception e) {
            System.out.println("Unknown System Issue");
            e.printStackTrace();
            return false;
        }
        System.out.println(output);
        return true;
    }

    /**
     *
     */

    private class CreateParkingLot implements CommandService.Command {
        private String[] commandStringArray;

        CreateParkingLot(String[] s) {
            commandStringArray = s;
        }
        public void validate() {

            if( ParkinglotService.CountCalledCommands()>1){
                throw new AppException("Parking Lot once created cannot be Override");
            }
            if (commandStringArray.length != 2) {
                throw new IllegalArgumentException("create_parking_lot command should have exactly 1 argument");
            }
        }

        public String execute() {
            int numberOfSlots = Integer.parseInt(commandStringArray[1]);
            TicketService.createInstance(numberOfSlots);
            ParkinglotService.CountCalledCommands();
            return "Created a parking lot with " + commandStringArray[1] + " slots";
        }
    }

    /**
     *
     */

    private class Park implements Command {
        private String[] commandStringArray;

        Park(String[] s) {
            commandStringArray = s;
        }

        public void validate() {

            if (commandStringArray.length != 3) {
                throw new IllegalArgumentException("park command should have exactly 2 arguments");
            }
        }

        public String execute() {
            TicketService ticketService = TicketService.getInstance();
            int allocatedSlotNumber = ticketService
                    .issueParkingTicket(new CarModel(commandStringArray[1], commandStringArray[2]));
            return "Allocated slot number: " + allocatedSlotNumber;
        }
    }

    /**
     *
     */

    private class Leave implements Command {
        private String[] commandStringArray;

        Leave(String[] s) {
            commandStringArray = s;
        }

        public void validate() {
            if (commandStringArray.length != 2) {
                throw new IllegalArgumentException("leave command should have exactly 1 argument");
            }
        }

        public String execute() {
            TicketService ticketService = TicketService.getInstance();
            ticketService.exitVehicle(Integer.parseInt(commandStringArray[1]));
            return "Slot number " + commandStringArray[1] + " is free";
        }
    }
    private class StatusCommand implements Command {
        private String[] commandStringArray;

        StatusCommand(String[] s) {
            commandStringArray = s;
        }

        public void validate() {
            if (commandStringArray.length != 1) {
                throw new IllegalArgumentException("status command should have no arguments");
            }
        }

        public String execute() {
            TicketService ticketService = TicketService.getInstance();
            List<AllocationStatus> statusResponseList = ticketService.getStatus();

            StringBuilder outputStringBuilder = new StringBuilder("Slot No.    Registration No    Colour");
            for (AllocationStatus allocationStatus: statusResponseList) {
                outputStringBuilder.append("\n").append(allocationStatus);
            }
            return outputStringBuilder.toString();
        }
    }

    /**
     * holds the responsibility of implementing
     * registration_numbers_for_cars_with_colour command
     *
     */
    private class RegistrationNumbersForColor implements Command {
        private String[] commandStringArray;

        RegistrationNumbersForColor(String[] s) {
            commandStringArray = s;
        }

        public void validate() {
            if (commandStringArray.length != 2) {
                throw new IllegalArgumentException(
                        "registration_numbers_for_cars_with_colour command should have exactly 1 argument");
            }
        }

        public String execute() {
            TicketService ticketService = TicketService.getInstance();
            List<String> registrationNumbersList = ticketService
                    .getRegistrationNumbersFromColor(commandStringArray[1]);
            StringBuilder outputStringBuilder = new StringBuilder();
            for (String registrationNumber : registrationNumbersList) {
                if (outputStringBuilder.length() > 0) {
                    outputStringBuilder.append(", ");
                }
                outputStringBuilder.append(registrationNumber);
            }
            return outputStringBuilder.toString();
        }
    }

    /**
     * holds the responsibility of implementing slot_numbers_for_cars_with_colour
     * command
     *
     */
    private class SlotNumbersForColor implements Command {
        private String[] commandStringArray;

        SlotNumbersForColor(String[] s) {
            commandStringArray = s;
        }

        public void validate() {
            if (commandStringArray.length != 2) {
                throw new IllegalArgumentException(
                        "slot_numbers_for_cars_with_colour command should have exactly 1 argument");
            }
        }

        public String execute() {
            TicketService ticketService = TicketService.getInstance();
            List<Integer> slotNumbersList = ticketService.getSlotNumbersFromColor(commandStringArray[1]);
            StringBuilder outputStringBuilder = new StringBuilder();
            for (int slotNumber : slotNumbersList) {
                if (outputStringBuilder.length() > 0) {
                    outputStringBuilder.append(", ");
                }
                outputStringBuilder.append(slotNumber);
            }
            return outputStringBuilder.toString();
        }
    }

    /**
     * holds the responsibility of implementing slot_number_for_registration_number
     * command
     *
     */
    private class SlotNumber implements Command {
        private String[] commandStringArray;

        SlotNumber(String[] s) {
            commandStringArray = s;
        }

        public void validate() {
            if (commandStringArray.length != 2) {
                throw new IllegalArgumentException(
                        "slot_number_for_registration_number command line should have exactly 1 argument");
            }
        }

        public String execute() {
            TicketService ticketService = TicketService.getInstance();
            int slotNumber = ticketService.getSlotNumberFromRegistrationNumber(commandStringArray[1]);
            return "" + slotNumber;
        }
    }
}
