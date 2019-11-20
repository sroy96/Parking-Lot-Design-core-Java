package com.parkinglot.AppServices;

import com.parkinglot.ExceptionHandle.AppException;

import java.util.HashMap;
import java.util.Map;

public class ParkinglotService {

    private static ParkinglotService parkinglotService;
    private static Map<Integer,Slots> slotMap;
    static  int count =0;

    /**
     * Count Called Commands
     * @return Number of times the Command has been called
     */
    static int CountCalledCommands(){
        count++;
        return count;
    }

    /**
     * Protected for Testing purpose else Private
     * @param slotNumbers
     */
    protected ParkinglotService(int slotNumbers){
    slotMap = new HashMap<Integer, Slots>();
    for (int i = 1; i <= slotNumbers; i++) {
        slotMap.put(i, new Slots(i));
    }
}


    /**
     * Singleton Class => Returns a single instance of the class
     *  Since a parking lot once created cannot be override
     * @param slotNumbers => number of slots in the parking lot
     * @return ParkingLotServices instance
     */


static ParkinglotService getInstance(int slotNumbers){
    if(parkinglotService ==  null){
        parkinglotService=new ParkinglotService(slotNumbers);
    }
    return parkinglotService;
}


    /**
     * Finds the next available slot and marks it unavailable
     *
     * @return slot number which was marked unavailable
     */
    int fillAvailableSlot() throws AppException {
        int nextAvailableSlotNumber = -1;
        for (int i = 1; i <= slotMap.size(); i++) {
            Slots s = slotMap.get(i);
            if (s.status) {
                nextAvailableSlotNumber = s.slotNumbers;
                s.status = false;
                break;
            }
        }
        if (nextAvailableSlotNumber != -1) {
            return nextAvailableSlotNumber;
        } else {
            throw new AppException("Sorry, parking lot is full");
        }
    }

    /**
     * Empties the Slot => marks the slot available
     *
     * @param slotNumber => the slot number to be made empty
     */
    void emptySlot(int slotNumber) {
        if (slotMap.containsKey(slotNumber)) {
            if (slotMap.get(slotNumber).status) {
                throw new IllegalStateException("The slot is already empty");
            } else {
                slotMap.get(slotNumber).status = true;
            }
        } else {
            throw new IllegalStateException("The slot number is invalid");
        }
    }






    /**        contains:-->
     *  private class --->Slots entity associated only with Parking Lot Services thus private
     */
    private class Slots {
    private int slotNumbers;
    private boolean status;

        public Slots(int slotNumbers) {
            this.slotNumbers = slotNumbers;
            this.status = true;
        }
    }




}


