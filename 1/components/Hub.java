/**
 * Shahar Levi 211885066
 * Adir Yossef Mohav 207855537
 */
package components;

import java.util.ArrayList;

public class Hub extends Branch implements Node {
    private static int lastId;
    private ArrayList<Branch> branches;

    public Hub() {
        super("HUB");
        this.branches = new ArrayList<>();
    }
    /**
     * Assigns a non-standard truck from the hub to collect a non-standard package,
     * if a suitable available truck exists.
     */
    public void collectPackage(Package p) {
        for (Truck t : listTrucks){
            if (t instanceof NonStandardTruck && p instanceof  NonStandartPackage && t.isAvailable()){
                NonStandardTruck nst = (NonStandardTruck)t;
                NonStandartPackage nsp = (NonStandartPackage)p;
                if (nst.getHeight()>nsp.getHeight() && nst.getLength() > nsp.getLength() && nst.getWidth()> nsp.getwidth()){
                    nst.setTimeLeft((int) (Math.random() * 10) + 1);
                    nst.collectPackage(p);
                }
            }

        }
    }
    /**
     * Assigns a standard truck to deliver a package from the hub to a destination branch,
     * based on destination and weight capacity.
     */
    public void deliverPackage(Package p) {
        for (Truck t : listTrucks) {
            if (t instanceof StandardTruck && t.isAvailable()) {
                StandardTruck stt = (StandardTruck) t;
                if (stt.getDestination().getBranchId() == p.getDestinationAddress().getZip()) {
                    if (p instanceof StandartPackage) {
                        StandartPackage stp = (StandartPackage) p;
                        if (stt.getMaxWeight() >=  stt.getPackagesW() +stp.getWeight()) {
                            stt.addPackagesW(stp.getWeight());
                            stt.deliverPackage(p);
                        }
                    }
                    if (p instanceof SmallPackage) {
                        SmallPackage sp = (SmallPackage) p;
                        if (stt.getMaxWeight() >= stt.getPackagesW() +1) {
                            stt.addPackagesW(1);
                            stt.deliverPackage(p);
                        }
                    }
                }
            }
        }
    }

    /**
     * Simulates the hub's operations for a time unit.
     * Updates truck movements, manages package assignments and routing.
     */
    public void work() {
        for (Truck t : listTrucks) {
            if (t instanceof StandardTruck) {
                StandardTruck stt = (StandardTruck) t;
                stt.work();
                if (stt.getTimeLeft() == 0 && !stt.isAvailable()){
                    if (stt.getDestination() instanceof  Hub){
                        stt.setAvailable(true);
                    }
                    else {
                        System.out.println("StandardTruck " + stt.getTruckID() +" loaded packages at" + stt.getDestination().getBranchName());
                        stt.setDestination(this);
                        stt.setTimeLeft((int)(Math.random() * 6) + 1);

                        System.out.println("StandardTruck "+  stt.getTruckID() + " is on it's way to the HUB, time to arrive: " + stt.getTimeLeft());
                    }
                }
                if (stt.isAvailable()) {
                    stt.setTimeLeft((int) (Math.random() * 10) + 1);
                    stt.setDestination(branches.getFirst());
                    branches.removeFirst();
                    branches.add(stt.getDestination());
                    System.out.println("StandardTruck " + stt.getTruckID() +" loaded packages at HUB");
                    System.out.println("StandardTruck " +  stt.getTruckID() + " is on it's way to " +  stt.getDestination().getBranchName() + " , time to arrive: " + stt.getTimeLeft());
                }
            }
            else if (t instanceof NonStandardTruck) {
                NonStandardTruck nst = (NonStandardTruck) t;
                nst.work();
            }
        }
        for (int i =0; i<listPackages.size(); i++){
            if (listPackages.get(i).getStatus() == Status.CREATION){
                collectPackage(listPackages.get(i));
            }
            else if (listPackages.get(i).getStatus() == Status.HUB_STORAGE){
                deliverPackage(listPackages.get(i));
                if (listPackages.get(i).getStatus() == Status.HUB_TRANSPORT){
                    listPackages.remove(listPackages.get(i));
                    i--;
                }
            }
        }
        for (Truck t : listTrucks) {
            if (t instanceof StandardTruck && t.isAvailable()) {
               t.setAvailable(false);
            }
        }
    }

    public ArrayList<Branch> getBranches() {
        return branches;
    }
    public void setBranches(ArrayList<Branch> branches) {
        this.branches = branches;
    }

    /** Adds a single branch to the hub's list of branches. */
    public void addBranch(Branch branch) {
        branches.add(branch);
    }

    /** Returns a string representation of the hub's state, including packages and trucks. */
    @Override
    public String toString() {
        return "Creating Branch " + getBranchId() + ",name:" + getBranchName() + ", packages: " + listPackages.size() + ",trucks: " + listTrucks.size();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Hub other = (Hub) obj;
        return this.getBranchId() == other.getBranchId() &&
               this.getBranchName().equals(other.getBranchName());
    }
}
