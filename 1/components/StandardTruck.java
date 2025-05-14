/**
 * Shahar Levi 211885066
 * Adir Yossef Mohav 207855537
 */
package components;

public class StandardTruck extends Truck implements Node{
    private int maxWeight;
    private double packagesW;
    private Branch destination;


    public StandardTruck (){
        super();
        this.maxWeight = (int)(Math.random() * 300)+100;
        this.packagesW = 0;
    }

    public StandardTruck(String licensePlate,String truckModel,int maxWeight){
        super(licensePlate,truckModel);
        this.maxWeight = maxWeight;
    }
    /**
     * Collects a package and updates its status and tracking to HUB_TRANSPORT.
     */
    public void collectPackage(Package p) {
        packages.add(p);
        p.setStatus(Status.HUB_TRANSPORT);
        p.addTracking(this,Status.HUB_TRANSPORT);
    }
    /**
     * Loads a package for delivery to a branch and updates its status to BRANCH_TRANSPORT.
     */
    public void deliverPackage(Package p) {
        packages.add(p);
        p.setStatus(Status.BRANCH_TRANSPORT);
        p.addTracking(this,Status.BRANCH_TRANSPORT);
    }
    /**
     * Simulates the truck's behavior for each tick.
     * If time is up, delivers or stores packages based on their status and picks up new ones if capacity allows.
     */
    public void work() {
        if(!isAvailable()){
            setTimeLeft(getTimeLeft()-1);
            if(getTimeLeft() ==0) {

                System.out.println("StandardTruck " + getTruckID() + "arrived to " + getDestination().getBranchName());
                if (!packages.isEmpty()) {
                    for (int i = 0 ; i<packages.size(); i++){

                        if (packages.get(i).getStatus() == Status.BRANCH_TRANSPORT) {
                            packages.get(i).setStatus(Status.DELIVERY);
                            packages.get(i).addTracking(destination, Status.DELIVERY);
                            destination.addPackage(packages.get(i));
                            if ( packages.get(i) instanceof StandartPackage) {
                                StandartPackage stp = (StandartPackage) packages.get(i);
                                packagesW -= stp.getWeight();// check
                            }
                            else if (packages.get(i) instanceof SmallPackage) {
                                packagesW -= 1;
                            }
                            packages.remove(i);
                            i--;
                        } else if (packages.get(i).getStatus() == Status.HUB_TRANSPORT) {
                            packages.get(i).setStatus(Status.HUB_STORAGE);
                            packages.get(i).addTracking(destination, Status.HUB_STORAGE);
                            destination.addPackage(packages.get(i));
                            if ( packages.get(i) instanceof StandartPackage) {
                                StandartPackage stp = (StandartPackage) packages.get(i);
                                packagesW -= stp.getWeight();// check
                            }
                            else if (packages.get(i) instanceof SmallPackage) {
                                packagesW -= 1;
                            }
                            packages.remove(i);
                            i--;
                        }
                    }
                }
                System.out.println("StandardTruck " + getTruckID() + " unloaded packages at " + destination.getBranchName());
                for (int i =0; i<destination.listPackages.size(); i++) {
                    if (destination.listPackages.get(i).getStatus() == Status.BRANCH_STORAGE) {
                        if (destination.listPackages.get(i) instanceof StandartPackage) {
                            StandartPackage stp = (StandartPackage) destination.listPackages.get(i);
                            if (maxWeight >= getPackagesW() + stp.getWeight()) {
                                packagesW += stp.getWeight();// check
                                collectPackage(destination.listPackages.get(i));
                            }
                        } else if (destination.listPackages.get(i) instanceof SmallPackage) {
                            if (maxWeight >= getPackagesW() +1) {
                                packagesW += 1;
                                collectPackage(destination.listPackages.get(i));
                            }
                        }
                    }
                }
            }
        }
    }

    public int getMaxWeight() {
        return maxWeight;
    }
    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }
    public Branch getDestination() {
        return destination;
    }
    public void setDestination(Branch destination) {
        this.destination = destination;
    }
    public double getPackagesW(){
        return  packagesW;
    }
    public void addPackagesW(double w){
        packagesW+=w;
    }

    /**
     * Returns a string representation of the truck including its type and max weight.
     */
    @Override
    public String toString() {
        return "Creating StandartTruck [" +super.toString() + ",maxWeight=" + maxWeight +"]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false; // שונה מסוג
        StandardTruck other = (StandardTruck) obj;
        return this.maxWeight == other.maxWeight && this.destination.equals(other.destination);
    }
}
