import java.util.Scanner;

public class InGate implements Runnable {
    private static BambaCarParkManager bambaCarParkManager =  BambaCarParkManager.getInstance();

    @Override
    public void run() {
        //getting choice from the user
        System.out.println("Select your choice : ");
        System.out.println("******************");
        System.out.println("1. To add a Car.");
        System.out.println("2. To add a Motor Bike.");
        System.out.println("3. To add a Van.");
        System.out.println(">>>>");

        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        VehicleType type = (choice == 1)?VehicleType.Car:(choice == 2)?
                VehicleType.MotorBike:(choice == 3)?
                VehicleType.Van:null;
        ObjectCreator creater = new ObjectCreator();
        Vehicle vehicle = creater.createVehicle(type);

        if(choice ==1){
            Thread.currentThread().setPriority(9);
        }
        else if(choice ==2){
            Thread.currentThread().setPriority(8);
        }
        else {
            Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        }

        if(bambaCarParkManager.isSecondFloor() && choice ==2){
            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        }

        System.out.println(Thread.currentThread().getName());
        bambaCarParkManager.addVehicle(vehicle);


    }
}
