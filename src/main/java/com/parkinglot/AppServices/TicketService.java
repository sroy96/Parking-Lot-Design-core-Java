package com.parkinglot.AppServices;

import com.parkinglot.ExceptionHandle.AppException;
import com.parkinglot.Model.AllocationStatus;
import com.parkinglot.Model.Vehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketService {
    private static TicketService ticketService;
    private ParkinglotService parkinglotService;
    private static Map<Integer, Ticket> ticketMap;



    /**
     * Visible for Testing purpose only
     * @param parkinglotService
     */
    TicketService(ParkinglotService parkinglotService) {
        this.parkinglotService = parkinglotService;
        ticketMap = new HashMap<Integer, Ticket>();
    }

    /**
     * Singleton Class => Returns a single instance of the class
     *
     * @param numberOfSlots => Number of slots of the parking lot that this
     *                      ticketing system is managing
     * @return ticketService instance
     */
    static TicketService createInstance(int numberOfSlots) {
        if(numberOfSlots < 1) {
            throw new AppException("Number of slots cannot be less than 1");
        }
        if (ticketService == null) {
            ParkinglotService parkingLot = ParkinglotService.getInstance(numberOfSlots);
            ticketService = new TicketService(parkingLot);
        }
        return ticketService;
    }

    /**
     *
     * @return ticketService instance
     */
    static TicketService getInstance() {
        if(ticketService== null) {
            throw new IllegalStateException("Parking Lot is not initialized");
        }
        return ticketService;
    }

    /**
     * Parks a vehicle
     *
     * @return slotNumber => slot number at which the vehicle needs to be parked
     */


    int issueParkingTicket(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }



            boolean check= true;

                for (Ticket ticket : ticketMap.values()) {
                    if(vehicle.getRegistrationNumber().equals(ticket.vehicle.getRegistrationNumber())){
                        check= false;
                    }
                 }
                if(check) {
                    int assignedSlotNumber = parkinglotService.fillAvailableSlot();
                    Ticket ticket = new Ticket(assignedSlotNumber, vehicle);

                    ticketMap.put(assignedSlotNumber, ticket);
                    return assignedSlotNumber;
                }
                else{
                    throw new AppException("Same Registration Number Vehicles are not allowed");
                }


    }

    /**
     * Exits a vehicle from the parking lot
     *
     * @param slotNumber
     * @return slotNumber => the slot from the car has exited.
     */
    void exitVehicle(int slotNumber) {
        if (ticketMap.containsKey(slotNumber)) {
            parkinglotService.emptySlot(slotNumber);
            ticketMap.remove(slotNumber);
            return;
        } else {
            throw new AppException("No vehicle found at given slot. Incorrect input");
        }
    }

    /**
     * returns all the registration numbers of the vehicles with the given color
     *
     * @param color => Color of the Vehicle
     * @return List of all the registration numbers of the vehicles with the given
     *         color
     */
    List<String> getRegistrationNumbersFromColor(String color) {
        if (color == null) {
            throw new IllegalArgumentException("color cannot be null");
        }
        List<String> registrationNumbers = new ArrayList<String>();
        for (Ticket ticket : ticketMap.values()) {
            if (color.equals(ticket.vehicle.getColor())) {
                registrationNumbers.add(ticket.vehicle.getRegistrationNumber());
            }
        }
        return registrationNumbers;
    }

    /**
     * returns the slot number at which the Vehicle with given registrationNumber is
     * parked
     *
     * @param registrationNumber => Registration Number of the Vehicle
     * @return slot number at which the Vehicle with given registrationNumber is
     *         parked
     */
    int getSlotNumberFromRegistrationNumber(String registrationNumber) {
        if (registrationNumber == null) {
            throw new IllegalArgumentException("registrationNumber cannot be null");
        }
        for (Ticket ticket : ticketMap.values()) {
            if (registrationNumber.equals(ticket.vehicle.getRegistrationNumber())) {
                return ticket.slotNumber;
            }
        }

        throw new AppException("Not found");
    }

    /**
     * returns all the slot numbers of the vehicles with the given color
     *
     * @param color => Color of the Vehicle
     * @return List of all the slot numbers of the vehicles with the given color
     */
    List<Integer> getSlotNumbersFromColor(String color) {
        if (color == null) {
            throw new IllegalArgumentException("color cannot be null");
        }
        List<Integer> registrationNumbers = new ArrayList<Integer>();
        for (Ticket ticket : ticketMap.values()) {
            if (color.equals(ticket.vehicle.getColor())) {
                registrationNumbers.add(ticket.slotNumber);
            }
        }
        return registrationNumbers;
    }

    /**
     * returns the status of the ticketing system, a list of all the tickets
     * converted to status objects
     *
     * @return List of StatusResponse => List of (slotNumber, registrationNumber,
     *         color)
     */
    List<AllocationStatus> getStatus() {
        List<AllocationStatus>allocationStatusList= new ArrayList<AllocationStatus>();
        for (Ticket ticket : ticketMap.values()) {
            allocationStatusList.add(new AllocationStatus(ticket.slotNumber, ticket.vehicle.getRegistrationNumber(),
                    ticket.vehicle.getColor()));
        }
        return allocationStatusList;
    }

    /**
     * Ticketing System issues a ticket => an object known only to Ticketing System
     *
     */
    private class Ticket {
        int slotNumber;
        Vehicle vehicle;

        Ticket(int slotNumber, Vehicle vehicle) {
            this.slotNumber = slotNumber;
            this.vehicle = vehicle;
        }
    }
}
