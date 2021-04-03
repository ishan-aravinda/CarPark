
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

public class BambaCarParkManager implements CarParkManager {

    private static BambaCarParkManager instance = null;

    private ArrayList<Vehicle> listOfVehicle = new ArrayList<Vehicle>();

    private ArrayList<Vehicle> GroundFloor = new ArrayList<Vehicle>();
    private ArrayList<Vehicle> FirstFloor = new ArrayList<Vehicle>();
    private ArrayList<Vehicle> SecondFloor = new ArrayList<Vehicle>();


    private int availableSlots = 20;

    private int groundAvailSlots = 80;
    private int firstAvailSlots = 60;
    private int secondAvailSlots = 70;

    private boolean isGroundFloor;
    private boolean isFirstFloor;
    private boolean isSecondFloor;

    private double chargePerHour = 300;
    private double addCharge = 100;
    private double maxCharge = 3000;
    private int addFromthisHour = 3;

    public boolean isGroundFloor() {
        return isGroundFloor;
    }

    public boolean isFirstFloor() {
        return isFirstFloor;
    }

    public boolean isSecondFloor() {
        return isSecondFloor;
    }

    //private constructor
    private BambaCarParkManager() {
    }

    //method which returns an object of same type
    public static BambaCarParkManager getInstance() {
        if (instance == null) {
            synchronized (BambaCarParkManager.class) {
                if (instance == null) {
                    instance = new BambaCarParkManager();
                }
            }
        }
        return instance;
    }


    @Override
    public synchronized void addVehicle(Vehicle obj) {
        while (groundAvailSlots == 0 || firstAvailSlots == 0 || secondAvailSlots == 0) {
            try {
                System.out.println("Sorry...There are not space available for parking... Please Wait...");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //check whether the vehicle is already parked or not
        for (Vehicle item : listOfVehicle) {
            if (item.equals(obj)) {
                System.out.println("This vehicle is already parked.");
                return;
            }
        }
        // Check whether there are sufficient space available for any vehicle to park
        if (GroundFloor.size() < 79 && FirstFloor.size() < 59 && SecondFloor.size() < 69) {
            if (obj instanceof Van) {
                if (GroundFloor.size() < 79) {
                    GroundFloor.add(obj);
                    groundAvailSlots -= 2;
                    if (groundAvailSlots < 0) {
                        groundAvailSlots = 0;
                    }
                    System.out.println("Available slots GROUND : " + groundAvailSlots);
                    System.out.println("\n");
                } else if (FirstFloor.size() < 59) {
                    FirstFloor.add(obj);
                    firstAvailSlots -= 2;
                    if (firstAvailSlots < 0) {
                        firstAvailSlots = 0;
                    }
                    System.out.println("Available slots FIRST : " + firstAvailSlots);
                    System.out.println("\n");
                } else if (SecondFloor.size() < 69) {
                    SecondFloor.add(obj);
                    secondAvailSlots -= 2;
                    if (secondAvailSlots < 0) {
                        secondAvailSlots = 0;
                    }
                    System.out.println("Available slots SECOND : " + secondAvailSlots);
                    System.out.println("\n");
                } else {
                    System.out.println("Sorry..There are no slots available to park your Van." + "\n");
                }
            }
            if (obj instanceof MotorBike || obj instanceof Car) {
                if (GroundFloor.size() < 79) {
                    GroundFloor.add(obj);
                    groundAvailSlots -= 1;
                    if (groundAvailSlots < 0) {
                        groundAvailSlots = 0;
                    }
                    System.out.println("Available slots GROUND : " + groundAvailSlots);
                    System.out.println("\n");
                } else if (FirstFloor.size() < 59) {
                    FirstFloor.add(obj);
                    firstAvailSlots -= 1;
                    if (firstAvailSlots < 0) {
                        firstAvailSlots = 0;
                    }
                    System.out.println("Available slots FIRST : " + firstAvailSlots);
                    System.out.println("\n");
                } else if (SecondFloor.size() < 69) {
                    SecondFloor.add(obj);
                    secondAvailSlots -= 1;
                    if (secondAvailSlots < 0) {
                        secondAvailSlots = 0;
                    }
                    System.out.println("Available slots SECOND : " + secondAvailSlots);
                    System.out.println("\n");
                } else {
                    System.out.println("Sorry..There are no slots available to park your Vehicle." + "\n");
                }
            }
        }
        notifyAll();
    }

    @Override
    public synchronized void deleteVehicle(String IdPlate) {
        for (Vehicle item : listOfVehicle) {
            //Checking for a particular vehicle with its' plate ID
            if (item.getIdPlate().equals(IdPlate)) {
                System.out.println("Vehicle Found.");
                if (item instanceof Van) {
                    availableSlots += 2;
                    System.out.println("Space cleared after deleting a Van.\nAvailable Slots : "
                            + availableSlots);
                } else {
                    availableSlots++;
                    System.out.println("Space cleared after deleting a vehicle.\nAvailable Slots : "
                            + availableSlots);
                }
            } else {
                System.out.println("Vehicle not found.");
            }
        }
    }


    @Override
    public void printcurrentVehicles() {
        Collections.sort(GroundFloor, Collections.reverseOrder());
        Collections.sort(FirstFloor, Collections.reverseOrder());
        Collections.sort(SecondFloor, Collections.reverseOrder());

        if (GroundFloor.size() > 0) {
            for (Vehicle item : GroundFloor) {
                if (item instanceof Van) {
                    System.out.println("Vehicle Type is a Van");
                } else if (item instanceof MotorBike) {
                    System.out.println("Vehicle Type is a MotorBike");
                } else {
                    System.out.println("Vehicle Type is a Car.");
                }
                System.out.println("******************");
                System.out.println("ID Plate : " + item.getIdPlate());
                System.out.println("Entry Time : "
                        + item.getEntryDate().getHours() + ":" + item.getEntryDate().getMinutes()
                        + ":" + item.getEntryDate().getSeconds() + "-" + item.getEntryDate().getDate()
                        + "/" + item.getEntryDate().getMonth() + "/" + item.getEntryDate().getYear());
                System.out.println("\n");
            }
        }

        if (FirstFloor.size() > 0) {
            for (Vehicle item : FirstFloor) {
                if (item instanceof Van) {
                    System.out.println("Vehicle Type is a Van");
                } else if (item instanceof MotorBike) {
                    System.out.println("Vehicle Type is a MotorBike");
                } else {
                    System.out.println("Vehicle Type is a Car.");
                }
                System.out.println("******************");
                System.out.println("ID Plate : " + item.getIdPlate());
                System.out.println("Entry Time : "
                        + item.getEntryDate().getHours() + ":" + item.getEntryDate().getMinutes()
                        + ":" + item.getEntryDate().getSeconds() + "-" + item.getEntryDate().getDate()
                        + "/" + item.getEntryDate().getMonth() + "/" + item.getEntryDate().getYear());
                System.out.println("\n");
            }
        }

        if (SecondFloor.size() > 0) {
            for (Vehicle item : SecondFloor) {
                if (item instanceof Van) {
                    System.out.println("Vehicle Type is a Van");
                } else if (item instanceof MotorBike) {
                    System.out.println("Vehicle Type is a MotorBike");
                } else {
                    System.out.println("Vehicle Type is a Car.");
                }
                System.out.println("******************");
                System.out.println("ID Plate : " + item.getIdPlate());
                System.out.println("Entry Time : "
                        + item.getEntryDate().getHours() + ":" + item.getEntryDate().getMinutes()
                        + ":" + item.getEntryDate().getSeconds() + "-" + item.getEntryDate().getDate()
                        + "/" + item.getEntryDate().getMonth() + "/" + item.getEntryDate().getYear());
                System.out.println("\n");
            }
        }

        if (GroundFloor.size() == 0 && FirstFloor.size() == 0 && SecondFloor.size() == 0) {
            System.out.println("No vehicles currently parked");
        }


    }

    @Override
    public void printLongestPark() {
        //sort to the ascending order
        Collections.sort(listOfVehicle);
        System.out.println("The longest parked vehicle is : ");
        System.out.println("................................................");
        System.out.println("ID Plate : " + listOfVehicle.get(0).getIdPlate());
        if (listOfVehicle.get(0) instanceof Car) {
            System.out.println("Vehicle Type is a Car.");
        } else if (listOfVehicle.get(0) instanceof Van) {
            System.out.println("Vehicle Type is a Van.");
        } else {
            System.out.println("Vehicle Type is a MotorBike.");
        }
        System.out.println("Parked Time : " + listOfVehicle.get(0).getEntryDate().getHours()
                + ":" + listOfVehicle.get(0).getEntryDate().getMinutes()
                + ":" + listOfVehicle.get(0).getEntryDate().getSeconds());
        System.out.println("Parked Date  : " + listOfVehicle.get(0).getEntryDate().getDate()
                + "/" + listOfVehicle.get(0).getEntryDate().getMonth()
                + "/" + listOfVehicle.get(0).getEntryDate().getYear());
    }

    @Override
    public void printLatestPark() {
        // sort to the descending order
        Collections.sort(listOfVehicle, Collections.reverseOrder());
        System.out.println("The latest parked vehicle is : ");
        System.out.println("..............................................");
        System.out.println("ID Plate : " + listOfVehicle.get(0).getIdPlate());
        if (listOfVehicle.get(0) instanceof Car) {
            System.out.println("Vehicle Type is a Car.");
        } else if (listOfVehicle.get(0) instanceof Van) {
            System.out.println("Vehicle Type is a Van.");
        } else {
            System.out.println("Vehicle Type is a MotorBike.");
        }
        System.out.println("Parked Time : " + listOfVehicle.get(0).getEntryDate().getHours()
                + ":" + listOfVehicle.get(0).getEntryDate().getMinutes()
                + ":" + listOfVehicle.get(0).getEntryDate().getSeconds());
        System.out.println("Parked Date  : " + listOfVehicle.get(0).getEntryDate().getDate()
                + "/" + listOfVehicle.get(0).getEntryDate().getMonth()
                + "/" + listOfVehicle.get(0).getEntryDate().getYear());
    }


    @Override
    public void printVehicleByDay(DateTime givenDate) {
        for (Vehicle item : listOfVehicle) {
            if (givenDate.getYear() == item.getEntryDate().getYear() &&
                    givenDate.getMonth() == item.getEntryDate().getMonth() &&
                    givenDate.getDate() == item.getEntryDate().getDate()) {

                System.out.println("ID Plate : " + item.getIdPlate());

                System.out.println("Parked Date and Time : " + item.getEntryDate().getDate() + "/" +
                        item.getEntryDate().getMonth() + "/" + item.getEntryDate().getHours() + "-"
                        + item.getEntryDate().getHours() + ":" + item.getEntryDate().getMinutes()
                        + ":" + item.getEntryDate().getYear());

                if (item instanceof Van) {
                    System.out.println("Vehicle Type is a Van");
                } else if (item instanceof MotorBike) {
                    System.out.println("Vehicle Type is a Motor Bike.");
                } else {
                    System.out.println("Vehicle Type is a Car.");
                }
                System.out.println("--------------------------");
                System.out.println("\n");
            }
        }
    }

    @Override
    public void printVehiclePercentage() {
        int numCars = 0;
        int numBikes = 0;
        int numVans = 0;
        for (Vehicle item : listOfVehicle) {
            if (item instanceof Car) {
                numCars++;
            } else if (item instanceof MotorBike) {
                numBikes++;
            } else {
                numVans++;
            }
        }
        double carPercentage = 0;
        double bikePercentage = 0;
        double vanPercentage = 0;

        if (listOfVehicle.size() > 0) {
            carPercentage = (numCars / listOfVehicle.size()) * 100;
            bikePercentage = (numBikes / listOfVehicle.size()) * 100;
            vanPercentage = (numVans / listOfVehicle.size()) * 100;

        }


        System.out.printf("Car Percentage is : %.2f ", carPercentage);
        System.out.printf("\nBike Percentage is : %.2f ", bikePercentage);
        System.out.printf("\nVan Percentage is : %.2f ", vanPercentage);
        System.out.println("\n");
    }

    @Override
    public synchronized BigDecimal calculateChargers(String plateID, DateTime currentTime) {
        boolean found = false;
        BigDecimal charges = null;
        for (Vehicle item : listOfVehicle) {
            if (item.getIdPlate().equals(plateID)) {
                System.out.println("Vehicle found.");
                //vehicle parked time
                System.out.println("Parked Time : " + item.getEntryDate().getDate() + "/"
                        + item.getEntryDate().getMonth() + "/" + item.getEntryDate().getDate()
                        + "-" + item.getEntryDate().getHours() + ":" + item.getEntryDate().getMinutes()
                        + ":" + item.getEntryDate().getSeconds());
                //making the charges
                found = true;
                DateTime entryDateTime = item.getEntryDate();
                int differenceInSeconds = currentTime.compareTo(entryDateTime);
                double differenceInHours = differenceInSeconds / (60.0 * 60.0);

                double dayCharge = 0;
                double hourCharge = 0;
                double totalCost = 0;
                double days = differenceInHours / 24;

                if (days > 1) {
                    dayCharge = maxCharge;
                }
                if (differenceInHours >= 3) {
                    double additional = (differenceInHours - addFromthisHour);
                    hourCharge = (additional * addCharge) + (addFromthisHour * chargePerHour);
                    System.out.printf("hour Charge : %.2f", hourCharge);
                } else if (differenceInHours < 1) {
                    hourCharge = chargePerHour;
                } else {
                    hourCharge = (differenceInHours * chargePerHour);
                }

                totalCost = dayCharge + hourCharge;
                BigDecimal vehicleCharge = new BigDecimal(totalCost);
                System.out.printf("Total charge for the vehicle is LKR %.2f", vehicleCharge);
                System.out.println("\n");
            }
        }
        if (!found) {
            System.out.println("Vehicle not found\n");
        }
        return charges;
    }


}
