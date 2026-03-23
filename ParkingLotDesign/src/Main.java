import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        ParkingLotService parkingLot = new ParkingLotService(new NearestSlotStrategy());

        parkingLot.setHourlyRate(SlotType.SMALL, 10);
        parkingLot.setHourlyRate(SlotType.MEDIUM, 20);
        parkingLot.setHourlyRate(SlotType.LARGE, 30);

        ParkingFloor floor1 = new ParkingFloor(1);
        floor1.addSlot(new ParkingSlot(1, 1, SlotType.SMALL));
        floor1.addSlot(new ParkingSlot(2, 1, SlotType.SMALL));
        floor1.addSlot(new ParkingSlot(3, 1, SlotType.MEDIUM));
        floor1.addSlot(new ParkingSlot(4, 1, SlotType.MEDIUM));
        floor1.addSlot(new ParkingSlot(5, 1, SlotType.LARGE));

        ParkingFloor floor2 = new ParkingFloor(2);
        floor2.addSlot(new ParkingSlot(1, 2, SlotType.SMALL));
        floor2.addSlot(new ParkingSlot(2, 2, SlotType.MEDIUM));
        floor2.addSlot(new ParkingSlot(3, 2, SlotType.MEDIUM));
        floor2.addSlot(new ParkingSlot(4, 2, SlotType.LARGE));
        floor2.addSlot(new ParkingSlot(5, 2, SlotType.LARGE));

        parkingLot.addFloor(floor1);
        parkingLot.addFloor(floor2);

        parkingLot.addGate(new EntryGate(1, 1));
        parkingLot.addGate(new EntryGate(2, 2));

        LocalDateTime now = LocalDateTime.of(2024, 1, 15, 10, 0);

        System.out.println("========================================");
        System.out.println("       MULTILEVEL PARKING LOT");
        System.out.println("========================================");
        System.out.println("Floors: 2 | Gates: 2");
        System.out.println("Rates: SMALL=Rs.10/hr, MEDIUM=Rs.20/hr, LARGE=Rs.30/hr");
        System.out.println();

        System.out.println("--- Parking Vehicles ---");

        Vehicle bike1 = new Vehicle("KA-01-1234", VehicleType.TWO_WHEELER);
        ParkingTicket t1 = parkingLot.park(bike1, now, SlotType.SMALL, 1);
        printTicket(t1);

        Vehicle car1 = new Vehicle("KA-02-5678", VehicleType.CAR);
        ParkingTicket t2 = parkingLot.park(car1, now, SlotType.MEDIUM, 1);
        printTicket(t2);

        Vehicle bus1 = new Vehicle("KA-03-9999", VehicleType.BUS);
        ParkingTicket t3 = parkingLot.park(bus1, now, SlotType.LARGE, 2);
        printTicket(t3);

        System.out.println("\n--- Parking Status ---");
        parkingLot.printStatus();

        Vehicle bike2 = new Vehicle("KA-04-2222", VehicleType.TWO_WHEELER);
        parkingLot.park(bike2, now, SlotType.SMALL, 1);
        Vehicle bike3 = new Vehicle("KA-05-3333", VehicleType.TWO_WHEELER);
        parkingLot.park(bike3, now, SlotType.SMALL, 1);

        System.out.println("\n--- Bike requesting SMALL but all full (gets MEDIUM instead) ---");
        Vehicle bike4 = new Vehicle("KA-06-4444", VehicleType.TWO_WHEELER);
        ParkingTicket t6 = parkingLot.park(bike4, now, SlotType.SMALL, 1);
        printTicket(t6);

        System.out.println("\n--- Bus trying SMALL slot (rejected) ---");
        Vehicle bus2 = new Vehicle("KA-07-8888", VehicleType.BUS);
        parkingLot.park(bus2, now, SlotType.SMALL, 1);

        System.out.println("\n--- Vehicle Exit ---");
        LocalDateTime exitTime = now.plusHours(3);
        Bill bill = parkingLot.exit(t1, exitTime);
        printBill(bill);

        System.out.println("\n--- Final Status ---");
        parkingLot.printStatus();

        System.out.println("\n========================================");
    }

    private static void printTicket(ParkingTicket ticket) {
        if (ticket == null) return;
        ParkingSlot slot = ticket.getAllocatedSlot();
        System.out.println("  Ticket " + ticket.getTicketId()
            + " | " + ticket.getVehicle().getLicensePlate()
            + " (" + ticket.getVehicle().getType() + ")"
            + " | Floor " + slot.getFloorNumber()
            + " Slot " + slot.getSlotNumber()
            + " (" + slot.getSlotType() + ")"
            + " | Entry: " + ticket.getEntryTime());
    }

    private static void printBill(Bill bill) {
        ParkingTicket ticket = bill.getTicket();
        System.out.println("  Ticket: " + ticket.getTicketId()
            + " | Vehicle: " + ticket.getVehicle().getLicensePlate()
            + " | Slot type: " + ticket.getAllocatedSlot().getSlotType()
            + " | Hours: " + bill.getHoursParked()
            + " | Amount: Rs." + bill.getTotalAmount());
    }
}
