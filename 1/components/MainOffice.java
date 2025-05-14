/**
 * Shahar Levi 211885066
 * Adir Yossef Mohav 207855537
 */
package components;
import java.util.ArrayList;
public class MainOffice {

    private int clock;
    private Hub hub;
    private ArrayList<Package> Packages;

    /**
     * Initializes the MainOffice with a hub, branches, and trucks.
     * Creates and assigns trucks and vans to the hub and branches.
     */
    public MainOffice(int branches, int trucksForBranch){
        this.hub = new Hub();
        System.out.println(hub);
        for (int i = 0; i < trucksForBranch; i++) {
            StandardTruck standardTruck = new StandardTruck();
            System.out.println(standardTruck);
            hub.addTrucks(standardTruck);
        }
        NonStandardTruck nonStandardTruck = new NonStandardTruck();
        System.out.println(nonStandardTruck);
        hub.addTrucks(nonStandardTruck);
        for (int i = 0; i < branches; i++) {
            Branch branch = new Branch();
            System.out.println(branch);
            for (int j = 0; j < trucksForBranch; j++) {
                Van van = new Van();
                System.out.println(van);
                branch.addTrucks(van);

            }
            hub.addBranch(branch);
        }
        Packages = new ArrayList<>();


    }

    /**
     * Runs the simulation for a given number of time units.
     * Prints the start and stop banners and calls tick() repeatedly.
     */
    public void play(int playTime){
        System.out.println("========================== Start ==========================");
        for (int i = 0; i < playTime; i++) {
            tick();
        }
        System.out.println("========================== STOP ==========================");
        printReport();
    }

    /**
     * Prints a tracking report for all packages in the system.
     */
    public void printReport(){
        for (Package p : Packages) {
            System.out.println("  TRACKING " + p);
            p.printTracking();
        }
    }

    /**
     * Returns the current clock time in mm:ss format.
     */
    public String clockString() {
        return String.format("%02d:%02d", clock / 60, clock % 60);
    }

    /**
     * Advances the simulation by one time unit.
     * Updates work status for all branches and hub, adds packages periodically.
     */
    public void tick(){
        System.out.println(clockString());
        for (Branch b : hub.getBranches()) {
            b.work();
        }
        hub.work();
        if (clock % 5 == 0 ) {
            addPackage();
        }
        Packages.getFirst().setTrackingTime(Packages.getFirst().getTrackingTime() + 1);
        clock++;
    }

    /**
     * Randomly creates and adds a new package (small, standard, or non-standard).
     * Assigns the package to either the hub or a branch based on type.
     */
    public void addPackage(){
        Package p;
        Priority priority;
        int randomP = (int)(Math.random() * 3) + 1;
        if (randomP == 1){
           priority = Priority.STANDARD;
        }
        else if (randomP == 2){
            priority = Priority.LOW;
        }
        else {
            priority = Priority.HIGH;
        }
        int randomT = (int)(Math.random() * 3) + 1;
        int randomZip = (int)(Math.random() * hub.getBranches().size());
        int randomA = (int)(Math.random() * 900000) + 100000;
        Address sen =new Address(randomZip, randomA);
        randomZip = (int)(Math.random() * hub.getBranches().size());
        randomA = (int)(Math.random() * 900000) + 100000;
        Address des =new Address(randomZip, randomA);
        switch (randomT){
            case 1 :
                int randomac=(int)(Math.random() * 2);
                if (randomac >1){
                    p = new SmallPackage(priority,sen,des,true) ;
                }
                else {
                    p = new SmallPackage(priority,sen,des,false);
                }
                break;
            case 2 :
                double randomw=Math.random() * 5 ;
                p= new StandartPackage(priority,sen,des,randomw);
                break;
            default :
                int w=(int)(Math.random() * 2000) +1 ;
                int l= (int)(Math.random() * 2000) +1 ;
                int h = (int)(Math.random() * 2000) +1 ;
                p= new NonStandartPackage(priority,sen,des,w,l,h );
                break;
        }
        Packages.add(p);
        System.out.println("Creating " + p);
        if (p instanceof NonStandartPackage) {
            hub.addPackage(p);
        }
        else{
            hub.getBranches().get(p.getSenderAddress().getZip()).addPackage(p);
        }
    }
}


