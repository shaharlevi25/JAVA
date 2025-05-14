/**
 * Shahar Levi 211885066
 * Adir Yossef Mohav 207855537
 */
package components;
import java.util.Objects;
import java.util.ArrayList;

/**
 * Abstract base class representing a delivery truck.
 * Contains shared properties and methods for different types of trucks.
 */
public abstract class Truck {
    private static int idCounter = 2000;
    protected int truckID;
    protected String licensePlate;
    protected String truckModel;
    protected boolean available;
    protected int timeLeft;
    protected ArrayList<Package> packages;

    /**
     * Default constructor for Truck.
     * Initializes with a random license plate and model, and sets the truck as available.
     */
    public Truck(){
        this.truckID = idCounter++;
        this.licensePlate = generateRandomPlate();
        this.truckModel = generateRandomModel();
        this.available = true;
        this.timeLeft = 0;
        this.packages = new ArrayList<>();
    }

    /**
     * Constructor that initializes the truck with a given license plate and model.
     * Validates the format of both parameters.
     */
    public Truck(String licensePlate, String truckModel){
        this.truckID = idCounter++;
        if (!licensePlate.matches("\\d{3}-\\d{2}-\\d{3}")) {
            throw new IllegalArgumentException("License plate must be in format xxx-xx-xxx");
        }
        if (!truckModel.matches("M[0-4]")) {
            throw new IllegalArgumentException("Model must be in format M0 to M4");
        }
        this.licensePlate = licensePlate;
        this.truckModel = truckModel;
        this.available = true;
        this.timeLeft = 0;
        this.packages = new ArrayList<>();
    }

    /** Generates a random license plate in the format xxx-xx-xxx. */
    private String generateRandomPlate() {
        int part1 = (int)(Math.random() * 1000);
        int part2 = (int)(Math.random() * 100);
        int part3 = (int)(Math.random() * 1000);
        return String.format("%03d-%02d-%03d", part1, part2, part3);
    }

    /** Generates a random truck model from M0 to M4. */
    private String generateRandomModel() {
        int modelNum = (int)(Math.random() * 5);
        return "M" + modelNum;
    }

    /** Returns a string representation of the truck including its details. */
    @Override
    public String toString() {
        return "truckID=" + truckID +", licensePlate=" + licensePlate + ", truckModel=" + truckModel + ", available=" + available;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Truck other = (Truck) obj;
        return truckID == other.truckID &&
               Objects.equals(licensePlate, other.licensePlate) &&
               Objects.equals(truckModel, other.truckModel);
    }
    /** Returns true if the truck is currently carrying no packages. */
    public boolean checkEmpty(){
        return packages.isEmpty();
    }
    public int getTruckID() {return truckID;}
    public void setTruckID(int truckID) {this.truckID = truckID;}
    public String getLicensePlate() {return licensePlate;}
    public void setLicensePlate(String licensePlate) {this.licensePlate = licensePlate;}
    public String getTruckModel() {return truckModel;}
    public void setTruckModel(String truckModel) {this.truckModel = truckModel;}
    public boolean isAvailable() {return available;}
    public void setAvailable(boolean available) {this.available = available;}
    public int getTimeLeft() {return timeLeft;}
    public void setTimeLeft(int timeLeft) {this.timeLeft = timeLeft;}
    public ArrayList<Package> getPackages() {return packages;}
    public void setPackages(ArrayList<Package> packages) {packages = packages;}
}
