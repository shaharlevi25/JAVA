/**
 * Shahar Levi 211885066
 * Adir Yossef Mohav 207855537
 */
package components;
import java.util.ArrayList;

public abstract class Package {
    private static int runner = 1000;
    private static int TrackingTime = 0;
    private int packageID;
    private Priority priority;
    private Status status;
    private Address senderAddress;
    private Address destinationAddress;
    protected ArrayList<Tracking> tracking;

    public Package(Priority priority, Address senderAddress, Address destinationAddress) {
        this.packageID = runner++;
        this.priority = priority;
        this.status = Status.CREATION;
        this.senderAddress = senderAddress;
        this.destinationAddress = destinationAddress;
        this.tracking = new ArrayList<Tracking>();
        tracking.add(new Tracking(TrackingTime, null, status));
    }

    /**
     * Adds a tracking entry for the package with the current tracking time,
     * the given node (location), and the updated status.
     */
    public void addTracking(Node node, Status status) {
        tracking.add(new Tracking(TrackingTime, node, status));
        this.status = status;
    }

    /**
     * Prints all tracking events associated with the package.
     */
    public void printTracking() {
        for (Tracking t : tracking) {
            System.out.println(t);
        }
    }

    /**
     * Returns a string representation of the package with its ID, priority, status, and addresses.
     */
    @Override
    public String toString() {
        return "packageID=" + packageID +", priority=" + priority + ", status=" + status + ",startTime=" + ",senderAddress=" + getSenderAddress() + ",destinationAddress=" + getDestinationAddress();
    }

    /**
     * Checks if another object is equal to this package by comparing package IDs.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Package other = (Package) obj;
        return packageID == other.packageID;
    }

    public int getPackageID() {
        return packageID;
    }
    public void setPackageID(int packageID) {
        this.packageID = packageID;
    }
    public Priority getPriority() {
        return priority;
    }
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public Address getSenderAddress() {
        return senderAddress;
    }
    public void setSenderAddress(Address senderAddress) {
        this.senderAddress = senderAddress;
    }
    public Address getDestinationAddress() {
        return destinationAddress;
    }
    public void setDestinationAddress(Address destinationAddress) {
        this.destinationAddress = destinationAddress;
    }
    public ArrayList<Tracking> getTracking() {
        return tracking;
    }
    public void setTracking(ArrayList<Tracking> tracking) {
        this.tracking = tracking;
    }
    public void setTrackingTime(int t){
        TrackingTime = t;
    }
    public int getTrackingTime(){
        return TrackingTime;
    }

}
