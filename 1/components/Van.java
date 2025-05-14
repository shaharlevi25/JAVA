/**
 * Shahar Levi 211885066
 * Adir Yossef Mohav 207855537
 */
package components;

public class Van extends Truck implements Node {

    public Van(){
        super();
    }
    public Van(String licensePlate, String truckModel) {
        super(licensePlate, truckModel);
    }

    /**
     * Starts the collection process for a package.
     * Sets the van's time to reach the sender's address based on ZIP code.
     * Updates the package status and tracking.
     */
    public void collectPackage(Package p) {
        setTimeLeft(p.getSenderAddress().getZip()%10 +1);
        packages.add(p);
        p.setStatus(Status.COLLECTION);
        p.addTracking(this, Status.COLLECTION);
        System.out.println("Van " + getTruckID() + " is collecting package " + packages.getFirst().getPackageID()+ ", time to arrive: " + this.getTimeLeft());
        setAvailable(false);
    }


    /**
     * Starts the delivery process for a package.
     * Sets the van's time to reach the destination based on ZIP code.
     * Updates the package status and tracking.
     */
    public void deliverPackage(Package p) {
        setTimeLeft(p.getDestinationAddress().getZip()%10 +1);
        packages.add(p);
        p.setStatus(Status.DISTRIBUTION);
        p.addTracking(this, Status.DISTRIBUTION);
        System.out.println("Van " + getTruckID() + " is delivering package " + packages.getFirst().getPackageID() + ", time to arrive:" + this.getTimeLeft());
        setAvailable(false);
    }

    /**
     * Simulates the van's behavior during each clock tick.
     * Handles completion of collection or delivery when time runs out.
     */
    public void work() {
        if (!isAvailable()){
            setTimeLeft(getTimeLeft()-1);
            if (getTimeLeft() == 0 && !checkEmpty()){
                if (packages.getFirst().getStatus() == Status.COLLECTION){
                    packages.getFirst().setStatus(Status.BRANCH_STORAGE);
                    System.out.println("Van " + getTruckID() + " is collecting package " + packages.getFirst().getPackageID()+ " and arrived back to branch: " + packages.getFirst().getSenderAddress().getZip());
                }
                else if (packages.getFirst().getStatus() == Status.DISTRIBUTION){
                    packages.getFirst().setStatus(Status.DELIVERED);
                    packages.getFirst().addTracking(null,Status.DELIVERED);
                    if(this.getPackages().getFirst() instanceof SmallPackage){
                        SmallPackage sp = (SmallPackage) getPackages().getFirst();
                        System.out.println(sp.toString());
                    }
                    System.out.println("Van " + this.getTruckID() + " is delivering package " +  packages.getFirst().getPackageID() + " to the destination");
                    packages.removeFirst();
                    setAvailable(true);

                }
            }


        }
    }

    @Override
    public String toString() {
        return "Creating Van [" +super.toString() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Van other = (Van) obj;
        return super.equals(other);
    }
}