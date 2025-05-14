/**
 * Shahar Levi 211885066
 * Adir Yossef Mohav 207855537
 */
package components;

public class NonStandardTruck extends Truck implements Node{

    private int width;
    private int length;
    private int height;

    public NonStandardTruck (){
        super();
        this.width = (int)(Math.random() * 2000)+1;
        this.height = (int)(Math.random() * 2000)+1;
        this.length = (int)(Math.random() * 2000)+1;
    }
    public NonStandardTruck(String licensePlate, String truckModel, int length, int width, int height){
        super(licensePlate, truckModel);
        this.width = width;
        this.length = length;
        this.height = height;
    }

    /**
     * Begins the package collection process.
     * Updates the package status and tracking, and marks the truck as unavailable.
     */
    public void collectPackage(Package p) {
        packages.add(p);
        setAvailable(false);
        p.setStatus(Status.COLLECTION);
        p.addTracking(this, Status.COLLECTION);
        System.out.println("NonStandardTruck " + getTruckID() + " is collecting package " + packages.getFirst().getPackageID()+ ", time to arrive: " + this.getTimeLeft());

    }
    /**
     * Begins the package delivery process.
     * Updates the package status and tracking.
     */
    public void deliverPackage(Package p) {
        p.setStatus((Status.DISTRIBUTION));
        p.addTracking(this, Status.DISTRIBUTION);
        System.out.println("NonStandardTruck " + getTruckID() + " is delivering package " + packages.getFirst().getPackageID() + ", time to arrive:" + this.getTimeLeft());

    }
    /**
     * Simulates the truck's operation each time unit.
     * Handles transition from collection to delivery and final delivery completion.
     */
    public void work() {
        if (!isAvailable()) {
            setTimeLeft(getTimeLeft() - 1);
            if(getTimeLeft() == 0){
                if(packages.getFirst().getStatus() == Status.COLLECTION){
                    int zip_difference = packages.getFirst().getSenderAddress().getZip() - packages.getFirst().getDestinationAddress().getZip();
                    zip_difference = zip_difference%10 +1;
                    setTimeLeft(Math.abs(zip_difference));
                    deliverPackage(packages.getFirst());
                }
                else if(packages.getFirst().getStatus() == Status.DISTRIBUTION){
                    packages.getFirst().setStatus((Status.DELIVERED));
                    packages.getFirst().addTracking(null, Status.DELIVERED);
                    System.out.println("NonStandardTruck" + this.getTruckID() + " is delivering package " +  packages.getFirst().getPackageID() + " to the destination");
                    packages.removeFirst();
                    setAvailable(true);
                }
            }
        }



    }
    /** Returns a string describing the NonStandardTruck with its details. */
    @Override
    public String toString() {
        return "NonStandardTruck [" +super.toString() + ", length=" + length + ", weight=" + width + ", height="+ height +"]";
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        NonStandardTruck other = (NonStandardTruck) obj;
        return this.length == other.length && this.height == other.height && this.width == other.width;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = this.width;
    }
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
}
