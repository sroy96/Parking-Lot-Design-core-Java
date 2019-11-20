package com.parkinglot.AppServices;

import com.parkinglot.Model.AllocationStatus;
import com.parkinglot.Model.CarModel;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TicketServiceTest {

    @Test
    public void integrationTest() {
        TicketService ticketService = TicketService.createInstance(6);

        int slot1 = ticketService.issueParkingTicket(new CarModel("KA-01-HH-1234", "White"));
        Assert.assertEquals(1, slot1);

        int slot2 = ticketService.issueParkingTicket(new CarModel("KA-01-HH-9999", "White"));
        Assert.assertEquals(2, slot2);

        int slot3 = ticketService.issueParkingTicket(new CarModel("KA-01-BB-0001", "Black"));
        Assert.assertEquals(3, slot3);

        int slot4 = ticketService.issueParkingTicket(new CarModel("KA-01-HH-7777", "Red"));
        Assert.assertEquals(4, slot4);

        int slot5 = ticketService.issueParkingTicket(new CarModel("KA-01-HH-2701", "Blue"));
        Assert.assertEquals(5, slot5);

        int slot6 = ticketService.issueParkingTicket(new CarModel("KA-01-HH-3141", "Black"));
        Assert.assertEquals(6, slot6);

        ticketService.exitVehicle(4);

        int slot7 = ticketService.issueParkingTicket(new CarModel("KA-01-P-333", "White"));
        Assert.assertEquals(4, slot7);




        try {
            ticketService.issueParkingTicket(new CarModel("DL-12-AA-9999", "White"));
            Assert.assertTrue("parking lot is full", false);
        } catch (Exception e) {
            Assert.assertEquals("", "Sorry, parking lot is full", e.getMessage());
        }

        List<String> registrationNumbers = ticketService.getRegistrationNumbersFromColor("White");
        Assert.assertEquals(3, registrationNumbers.size());

        List<Integer> slotNumbers = ticketService.getSlotNumbersFromColor("White");
        Assert.assertEquals(3, slotNumbers.size());

        int slotNumber = ticketService.getSlotNumberFromRegistrationNumber("KA-01-HH-3141");
        Assert.assertEquals(6, slotNumber);

        try {
            int slotNumber2 = ticketService.getSlotNumberFromRegistrationNumber("MH-04-AY-1111");
            Assert.assertTrue("should throw not found exception", true);
        } catch (Exception e) {
            Assert.assertEquals("", "Not found", e.getMessage());
        }
    }

    @Test
    public void issueParkingTicketWithValidVehicle() {
       TicketService ticketService = new TicketService(new DemoParkingLot(3));

        int slot1 = ticketService.issueParkingTicket(new CarModel("", ""));
        Assert.assertEquals(3, slot1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void issueParkingTicketWithNullVehicle() {
       TicketService ticketService = new TicketService(new DemoParkingLot(3));
        int slot1 = ticketService.issueParkingTicket(null);
    }

    @Test
    public void exitVehicleWithValidRegistrationNumber() {
        DemoParkingLot DemoParkingLot = new DemoParkingLot(3);
        TicketService ticketService = new TicketService(DemoParkingLot);
        ticketService.issueParkingTicket(new CarModel("KA-01-HH-3141", "White"));

        ticketService.exitVehicle(3);
        Assert.assertEquals(3, DemoParkingLot.emptySlotNumber);
    }

    @Test
    public void exitVehicleWithInvalidSlotNumber() {
        DemoParkingLot DemoParkingLot = new DemoParkingLot(3);
      TicketService ticketService = new TicketService(DemoParkingLot);

        try {
            ticketService.exitVehicle(3);
            Assert.assertTrue("should throw ticket not found exception", false);
        } catch (Exception e) {
            Assert.assertEquals("", "No vehicle found at given slot. Incorrect input", e.getMessage());
        }
    }

    @Test
    public void getRegistrationNumbersFromColor() {
       TicketService ticketService = new TicketService(new ParkinglotService(5));

        ticketService.issueParkingTicket(new CarModel("KA-01-HH-1234", "White"));
        ticketService.issueParkingTicket(new CarModel("KA-01-HH-9999", "White"));
        ticketService.issueParkingTicket(new CarModel("KA-01-BB-0001", "Black"));
        ticketService.issueParkingTicket(new CarModel("KA-01-HH-7777", "Blue"));
        ticketService.issueParkingTicket(new CarModel("KA-01-HH-2701", "Blue"));

        // TODO: Assert Values
        List<String> registrationNumbers = ticketService.getRegistrationNumbersFromColor("White");
        Assert.assertEquals(2, registrationNumbers.size());

        List<String> registrationNumbers2 = ticketService.getRegistrationNumbersFromColor("Black");
        Assert.assertEquals(1, registrationNumbers2.size());

        List<String> registrationNumbers3 = ticketService.getRegistrationNumbersFromColor("Blue");
        Assert.assertEquals(2, registrationNumbers3.size());

        List<String> registrationNumbers4 = ticketService.getRegistrationNumbersFromColor("Green");
        Assert.assertEquals(0, registrationNumbers4.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getRegistrationNumbersFromNullColor() {
        TicketService ticketService = new TicketService(new ParkinglotService(5));
        ticketService.getRegistrationNumbersFromColor(null);
    }

    @Test
    public void getSlotNumberFromRegistrationNumberWithValidRegistrationNumber() {
        TicketService ticketService = new TicketService(new ParkinglotService(5));

        ticketService.issueParkingTicket(new CarModel("KA-01-HH-1234", "White"));
        ticketService.issueParkingTicket(new CarModel("KA-01-HH-9999", "White"));

        int slot1 = ticketService.getSlotNumberFromRegistrationNumber("KA-01-HH-1234");
        Assert.assertEquals(1, slot1);

        int slot2 = ticketService.getSlotNumberFromRegistrationNumber("KA-01-HH-9999");
        Assert.assertEquals(2, slot2);
    }

    @Test
    public void getSlotNumberFromRegistrationNumberWithInvalidRegistrationNumber() {
        TicketService ticketService = new TicketService(new ParkinglotService(5));

        try {
            ticketService.getSlotNumberFromRegistrationNumber("KA-01-HH-9999");
            Assert.assertTrue("should throw not found exception", false);
        } catch (Exception e) {
            Assert.assertEquals("", "Not found", e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void getSlotNumberFromRegistrationNumberWithNullRegistrationNumber() {
        TicketService ticketService = new TicketService(new ParkinglotService(5));
        ticketService.getSlotNumberFromRegistrationNumber(null);
    }

    @Test
    public void getSlotNumbersFromColor() {
        TicketService ticketService = new TicketService(new ParkinglotService(5));

        ticketService.issueParkingTicket(new CarModel("KA-01-HH-1234", "White"));
        ticketService.issueParkingTicket(new CarModel("KA-01-HH-9999", "White"));
        ticketService.issueParkingTicket(new CarModel("KA-01-BB-0001", "Black"));
        ticketService.issueParkingTicket(new CarModel("KA-01-HH-7777", "Blue"));
        ticketService.issueParkingTicket(new CarModel("KA-01-HH-2701", "Blue"));

        // TODO: Assert Values
        List<Integer> slotNumbers = ticketService.getSlotNumbersFromColor("White");
        Assert.assertEquals(2, slotNumbers.size());

        List<Integer> slotNumbers2 = ticketService.getSlotNumbersFromColor("Black");
        Assert.assertEquals(1, slotNumbers2.size());

        List<Integer> slotNumbers3 = ticketService.getSlotNumbersFromColor("Blue");
        Assert.assertEquals(2, slotNumbers3.size());

        List<Integer> slotNumbers4 = ticketService.getSlotNumbersFromColor("Green");
        Assert.assertEquals(0, slotNumbers4.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getSlotNumbersFromNullColor() {
        TicketService ticketService = new TicketService(new ParkinglotService(5));
        ticketService.getSlotNumbersFromColor(null);
    }

    @Test
    public void getStatus() {
        TicketService ticketService = new TicketService(new ParkinglotService(1));
        ticketService.issueParkingTicket(new CarModel("KA-01-HH-2701", "Blue"));

        List<AllocationStatus> statusResponseList = ticketService.getStatus();
        Assert.assertEquals(1, statusResponseList.size());
        Assert.assertEquals("KA-01-HH-2701", statusResponseList.get(0).getRegistrationNumber());
        Assert.assertEquals("Blue", statusResponseList.get(0).getColor());
        Assert.assertEquals(1, statusResponseList.get(0).getSlot());

    }

    /**
     * DemoParkingLot  to test ParkingLotService class and override some functions
     * for testing
     *
     */
    private class DemoParkingLot extends ParkinglotService {

        private int nextAvailableSlotNumber;
        private int emptySlotNumber;

        DemoParkingLot (int slotNumber) {
            super(1);
            this.nextAvailableSlotNumber = slotNumber;
            this.emptySlotNumber = slotNumber;
        }

        @Override
        int fillAvailableSlot() {
            return nextAvailableSlotNumber;
        }

        @Override
        void emptySlot(int slotNumber) {
            this.emptySlotNumber = slotNumber;
        }
    }
}
