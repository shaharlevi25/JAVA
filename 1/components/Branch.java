/**
 * Shahar Levi 211885066
 * Adir Yossef Mohav 207855537
 */
package components;
import java.util.ArrayList;

public class Branch implements Node{

    private static int runner = -1;
    private int branchId;
    private String branchName;
    protected ArrayList<Truck> listTrucks;
    protected ArrayList<Package> listPackages;

    public Branch() {
            this.branchId = runner++;
            this.branchName = "Branch " + branchId;
            this.listTrucks = new ArrayList<>();
            this.listPackages = new ArrayList<>();
    }
    public Branch(String branchName){

            this.branchId = runner++;
            this.branchName = branchName;
            this.listTrucks = new ArrayList<>();
            this.listPackages = new ArrayList<>();
        }

    /**
     * Assigns an available van in the branch to collect the given package.
     * Updates the package tracking accordingly.
     */
    public void collectPackage(Package p) {
             for (Truck t : listTrucks) {
                 if (t instanceof Van && t.isAvailable()) {
                     Van v = (Van) t;
                     v.collectPackage(p);
                     return;
                 }
             }
    }

    /**
     * Assigns an available van in the branch to deliver the given package if it is ready for delivery.
     * Updates the package status and tracking.
     */
    public void deliverPackage(Package p) {
        if (p.getStatus() == Status.DELIVERY) {
            for (Truck t : listTrucks) {
                if (t instanceof Van && t.isAvailable()) {
                    Van v = (Van)t;
                    v.deliverPackage(p);
                    return;
                }
            }
        }
    }

    /**
     * Simulates a time unit of operation for the branch.
     * Updates truck activities and processes package collections and deliveries.
     */
    public void work() {
        for (Truck t : listTrucks) {
            if (t instanceof Van && !t.isAvailable()) {
                Van v = (Van)t;
                v.work();
                if (!v.packages.isEmpty()) {
                    if (v.packages.getFirst().getStatus() == Status.BRANCH_STORAGE && v.getTimeLeft() == 0) {
                        v.packages.getFirst().setStatus(Status.BRANCH_STORAGE);
                        v.packages.getFirst().addTracking(this, Status.BRANCH_STORAGE);
                        listPackages.add(v.packages.getFirst());
                        v.packages.removeFirst();

                    }
                }

            }
        }
        for (int i = 0; i<listPackages.size(); i++){
            if ( listPackages.get(i).getStatus() == Status.CREATION){
                collectPackage(listPackages.get(i));

            }
            else if (listPackages.get(i).getStatus() == Status.DELIVERY){
                deliverPackage(listPackages.get(i));
                if (listPackages.get(i).getStatus() == Status.DISTRIBUTION){
                    listPackages.remove(i);
                    i--;
                }
            }
        }
    }

    public int getBranchId() {
        return branchId;
    }
    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }
    public String getBranchName() {
        return branchName;
    }
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
    public ArrayList<Package> getListPackages() {
        return listPackages;
    }
    public void setListPackages(ArrayList<Package> listPackages) {
        this.listPackages = listPackages;
    }
    public ArrayList<Truck> getListTrucks() {
        return listTrucks;
    }
    public void setListTrucks(ArrayList<Truck> listTrucks) {
        this.listTrucks = listTrucks;
    }

    /** Adds a package to the branch's list of packages. */
    public void addPackage(Package p){
        listPackages.add(p);
    }

    /** Removes a package from the branch's list of packages. */
    public void removePackage(Package p){
        listPackages.remove(p);
    }

    /** Adds a truck to the branch's list of trucks. */
    public void addTrucks(Truck t){
        listTrucks.add(t);
    }

    /** Returns a string representation of the branch, including its ID, name, packages, and trucks. */
    @Override
    public String toString() {
        return "Creating Branch " + branchId + ",name:" + branchName + ", packages: " + listPackages.size() + ",trucks: " + listTrucks.size();
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Branch other = (Branch) obj;
        return branchId == other.branchId && branchName.equals(other.branchName);
    }
}