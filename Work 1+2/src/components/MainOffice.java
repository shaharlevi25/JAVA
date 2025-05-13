package components;

import java.util.ArrayList;
import java.util.Random;

public class MainOffice {
	private static int clock=0;
	private static Hub hub;
	private ArrayList<Package> packages=new ArrayList<Package>();
	
	public MainOffice(int branches, int trucksForBranch) {
		addHub(trucksForBranch);
		addBranches(branches, trucksForBranch);
		System.out.println("\n\n========================== START ==========================");

	}
	
	
	public static Hub getHub() {
		return hub;
	}


	public static int getClock() {
		return clock;
	}

	
	public void play(int playTime) {	
		for (int i=0; i<playTime; i++) {
			tick();
		}
		System.out.println("\n========================== STOP ==========================\n\n");
		printReport();
	}
	
	
	public void printReport() {
		for (Package p: packages) {
			System.out.println("\nTRACKING " +p);
			for (Tracking t: p.getTracking())
				System.out.println(t);
		}
	}
	
	public String clockString() {
		String s="";
		int minutes=clock/60;
		int seconds=clock%60;
		s+=(minutes<10) ? "0" + minutes : minutes;
		s+=":";
		s+=(seconds<10) ? "0" + seconds : seconds;
		return s;
	}
	
	
	public void tick() {
		System.out.println(clockString());
		if (clock%5==0)
			addPackage();
		hub.work();
		for (Branch b: hub.getBranches()) {
			b.work();
		}
		clock++;
	}
		
	
	public void addHub(int trucksForBranch) {
		hub=new Hub();
		for (int i=0; i<trucksForBranch; i++) {
			hub.addTruck(new StandardTruck());
		}
		hub.addTruck(new NonStandardTruck());
	}
	
	
	public void addBranches(int branches, int trucks) {
		for (int i=0; i<branches; i++) {
			Branch branch=new Branch();
			for (int j=0; j<trucks; j++) {
				branch.addTruck(new Van());
			}
			hub.add_branch(branch);		
		}
	}
	
	
	public void addPackage() {
		Random r = new Random();
		Package p;
		Priority priority=Priority.values()[r.nextInt(3)];
		Address sender = new Address(r.nextInt(hub.getBranches().size()), r.nextInt(999999)+100000);
		Address dest = new Address(r.nextInt(hub.getBranches().size()), r.nextInt(999999)+100000);

		switch (r.nextInt(3)){
		case 0:
			p = new SmallPackage(priority,  sender, dest, r.nextBoolean() );
			p.getSenderBranch().addPackage(p);
			break;
		case 1:
			p = new StandardPackage(priority,  sender, dest, r.nextFloat()+(r.nextInt(9)+1));
			p.getSenderBranch().addPackage(p);
			break;
		case 2:
			p=new NonStandardPackage(priority,  sender, dest,  r.nextInt(1000), r.nextInt(500), r.nextInt(400));
			hub.addPackage(p);
			break;
		default:
			p=null;
			return;
		}
		
		this.packages.add(p);
		
	}
}
