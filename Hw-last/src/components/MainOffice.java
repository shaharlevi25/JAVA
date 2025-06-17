package components;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class MainOffice implements Runnable {
	private static volatile MainOffice instance; // 🔒 Singleton instance
	private static final Object lock = new Object(); // 🔒 Lock for synchronization

	private static int clock = 0;
	private static Hub hub;
	private ArrayList<Package> packages = new ArrayList<>();
	private JPanel panel;
	private int maxPackages;
	private boolean threadSuspend = false;

	// 🧱 Private constructor
	private MainOffice(int branches, int trucksForBranch, JPanel panel, int maxPack) {
		this.panel = panel;
		this.maxPackages = maxPack;
		addHub(trucksForBranch);
		addBranches(branches, trucksForBranch);
		System.out.println("\n\n========================== START ==========================");
	}

	// 🚪 Public getter with DCL
	public static MainOffice getInstance(int branches, int trucksForBranch, JPanel panel, int maxPack) {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new MainOffice(branches, trucksForBranch, panel, maxPack);
				}
			}
		}
		return instance;
	}

	// 👇 Methods below remain unchanged
	public static Hub getHub() {
		return hub;
	}

	public static int getClock() {
		return clock;
	}

	@Override
	public void run() {
		Thread hubThread = new Thread(hub);
		hubThread.start();

		for (Truck t : hub.listTrucks) {
			Thread trackThread = new Thread(t);
			trackThread.start();
		}

		for (Branch b : hub.getBranches()) {
			Thread branch = new Thread(b);
			for (Truck t : b.listTrucks) {
				Thread trackThread = new Thread(t);
				trackThread.start();
			}
			branch.start();
		}

		while (true) {
			synchronized (this) {
				while (threadSuspend) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			tick();
		}
	}

	public void printReport() {
		for (Package p : packages) {
			System.out.println("\nTRACKING " + p);
			for (Tracking t : p.getTracking())
				System.out.println(t);
		}
	}

	public String clockString() {
		String s = "";
		int minutes = clock / 60;
		int seconds = clock % 60;
		s += (minutes < 10) ? "0" + minutes : minutes;
		s += ":";
		s += (seconds < 10) ? "0" + seconds : seconds;
		return s;
	}

	public void tick() {
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(clockString());
		if (clock++ % 5 == 0 && maxPackages > 0) {
			addPackage();
			maxPackages--;
		}
		panel.repaint();
	}

	public void branchWork(Branch b) {
		for (Truck t : b.listTrucks) {
			t.work();
		}
		b.work();
	}

	public void addHub(int trucksForBranch) {
		hub = new Hub();
		for (int i = 0; i < trucksForBranch; i++) {
			Truck t = new StandardTruck();
			hub.addTruck(t);
		}
		Truck t = new NonStandardTruck();
		hub.addTruck(t);
	}

	public void addBranches(int branches, int trucks) {
		for (int i = 0; i < branches; i++) {
			Branch branch = new Branch();
			for (int j = 0; j < trucks; j++) {
				branch.addTruck(new Van());
			}
			hub.add_branch(branch);
		}
	}

	public ArrayList<Package> getPackages() {
		return this.packages;
	}

	public void addPackage() {
		Random r = new Random();
		Package p;
		Branch br;
		Priority priority = Priority.values()[r.nextInt(3)];
		Address sender = new Address(r.nextInt(hub.getBranches().size()), r.nextInt(999999) + 100000);
		Address dest = new Address(r.nextInt(hub.getBranches().size()), r.nextInt(999999) + 100000);

		switch (r.nextInt(3)) {
			case 0:
				p = new SmallPackage(priority, sender, dest, r.nextBoolean());
				br = hub.getBranches().get(sender.zip);
				br.addPackage(p);
				p.setBranch(br);
				break;
			case 1:
				p = new StandardPackage(priority, sender, dest, r.nextFloat() + (r.nextInt(9) + 1));
				br = hub.getBranches().get(sender.zip);
				br.addPackage(p);
				p.setBranch(br);
				break;
			case 2:
				p = new NonStandardPackage(priority, sender, dest, r.nextInt(1000), r.nextInt(500), r.nextInt(400));
				hub.addPackage(p);
				break;
			default:
				p = null;
				return;
		}
		this.packages.add(p);
	}

	public synchronized void setSuspend() {
		threadSuspend = true;
		for (Truck t : hub.listTrucks) {
			t.setSuspend();
		}
		for (Branch b : hub.getBranches()) {
			for (Truck t : b.listTrucks) {
				t.setSuspend();
			}
			b.setSuspend();
		}
		hub.setSuspend();
	}

	public synchronized void setResume() {
		threadSuspend = false;
		notify();
		hub.setResume();
		for (Truck t : hub.listTrucks) {
			t.setResume();
		}
		for (Branch b : hub.getBranches()) {
			b.setResume();
			for (Truck t : b.listTrucks) {
				t.setResume();
			}
		}
	}
}
