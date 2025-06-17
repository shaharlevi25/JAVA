package program;

import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import components.*;
import components.Package;

import java.util.ArrayList;

public class PostSystemPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private Main frame;
	private JPanel p1;
	private JButton[] b_num;
	private String[] names = {"Create system","Start","Stop","Resume","All packages info","Branch info"};
	private JScrollPane scrollPane;
	private boolean isTableVisible = false;
	private boolean isTable2Visible = false;
	private int colorInd = 0;
	private boolean started = false;
	private MainOffice game = null;
	private int packagesNumber;
	private int branchesNumber;
	private int trucksNumber;

	// Constructor
	public PostSystemPanel(Main f) {
		frame = f;
		isTableVisible = false;
		setBackground(new Color(255,255,255));

		// Initialize buttons panel
		p1 = new JPanel();
		p1.setLayout(new GridLayout(1, 7, 0, 0));
		p1.setBackground(new Color(0,150,255));
		b_num = new JButton[names.length];

		// Create and add buttons
		for (int i = 0; i < names.length; i++) {
			b_num[i] = new JButton(names[i]);
			b_num[i].addActionListener(this);
			b_num[i].setBackground(Color.lightGray);
			p1.add(b_num[i]);
		}

		setLayout(new BorderLayout());
		add("South", p1);
	}

	// Create a new MainOffice system
	public void createNewPostSystem(int branches, int trucks, int packages) {
		if (started) return;
		game = MainOffice.getInstance(branches, trucks, this, packages);
		packagesNumber = packages;
		trucksNumber = trucks;
		branchesNumber = branches;
		repaint();
	}

	// Draw all components: hub, branches, trucks, and packages
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (game == null) return;

		Hub hub = game.getHub();
		ArrayList<Branch> branches = hub.getBranches();

		// Set spacing between branches
		int offset = 403 / (branchesNumber - 1);
		int y = 100;
		int y2 = 246;
		int offset2 = 140 / (branchesNumber - 1);

		// Draw the hub (green rectangle)
		g.setColor(new Color(0,102,0));
		g.fillRect(1120, 216, 40, 200);

		// Draw each branch
		for (Branch br : branches) {
			br.paintComponent(g, y, y2);
			y += offset;
			y2 += offset2;
		}

		// Draw packages on conveyor line
		int x = 150;
		int offset3 = (1154 - 300) / (packagesNumber - 1);

		for (Package p : game.getPackages()) {
			p.paintComponent(g, x, offset);
			x += offset3;
		}

		// Draw trucks from each branch
		for (Branch br : branches) {
			for (Truck tr : br.getTrucks()) {
				tr.paintComponent(g);
			}
		}

		// Draw trucks from the hub
		for (Truck tr : hub.getTrucks()) {
			tr.paintComponent(g);
		}
	}

	// Set the color index (used for customization)
	public void setColorIndex(int ind) {
		this.colorInd = ind;
		repaint();
	}

	// Change background color
	public void setBackgr(int num) {
		switch(num) {
			case 0:
				setBackground(new Color(255,255,255));
				break;
			case 1:
				setBackground(new Color(0,150,255));
				break;
		}
		repaint();
	}

	// Open dialog to create a new post system
	public void add() {
		CreatePostSystemlDialog dial = new CreatePostSystemlDialog(frame, this, "Create post system");
		dial.setVisible(true);
	}

	// Start simulation in a new thread
	public void start() {
		if (game == null || started) return;
		Thread t = new Thread(game);  // MainOffice implements Runnable
		started = true;
		t.start();
	}

	// Resume suspended simulation
	public void resume() {
		if (game == null) return;
		game.setResume();
	}

	// Suspend simulation
	public void stop() {
		if (game == null) return;
		game.setSuspend();
	}

	// Show info about all packages
	public void info() {
		if (game == null || !started) return;

		// Hide branch info table if visible
		if (isTable2Visible) {
			scrollPane.setVisible(false);
			isTable2Visible = false;
		}

		if (!isTableVisible) {
			int i = 0;
			String[] columnNames = {"Package ID", "Sender", "Destination", "Priority", "Staus"};
			ArrayList<Package> packages = game.getPackages();
			String[][] data = new String[packages.size()][columnNames.length];

			// Fill table data
			for (Package p : packages) {
				data[i][0] = "" + p.getPackageID();
				data[i][1] = "" + p.getSenderAddress();
				data[i][2] = "" + p.getDestinationAddress();
				data[i][3] = "" + p.getPriority();
				data[i][4] = "" + p.getStatus();
				i++;
			}

			JTable table = new JTable(data, columnNames);
			scrollPane = new JScrollPane(table);
			scrollPane.setSize(450, table.getRowHeight() * (packages.size()) + 24);
			add(scrollPane, BorderLayout.CENTER);
			isTableVisible = true;
		} else {
			isTableVisible = false;
		}

		scrollPane.setVisible(isTableVisible);
		repaint();
	}

	// Show info about selected branch
	public void branchInfo() {
		if (game == null || !started) return;

		if (scrollPane != null) scrollPane.setVisible(false);
		isTableVisible = false;
		isTable2Visible = false;

		String[] branchesStrs = new String[game.getHub().getBranches().size() + 1];
		branchesStrs[0] = "Sorting center";
		for (int i = 1; i < branchesStrs.length; i++)
			branchesStrs[i] = "Branch " + i;

		JComboBox cb = new JComboBox(branchesStrs);
		String[] options = { "OK", "Cancel" };
		int selection = JOptionPane.showOptionDialog(null, cb, "Choose branch",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

		if (selection == 1) return; // Cancel pressed

		if (!isTable2Visible) {
			int i = 0;
			String[] columnNames = {"Package ID", "Sender", "Destination", "Priority", "Staus"};
			List<Package> packages = null;
			int size = 0;

			// Sorting center
			if (cb.getSelectedIndex() == 0) {
				packages = game.getHub().getPackages();
				size = packages.size();
			} else {
				// Specific branch
				packages = game.getHub().getBranches().get(cb.getSelectedIndex() - 1).getPackages();
				size = packages.size();
				int diff = 0;
				for (Package p : packages) {
					if (p.getStatus() == Status.BRANCH_STORAGE)
						diff++;
				}
				size = size - diff / 2;
			}

			String[][] data = new String[size][columnNames.length];

			for (Package p : packages) {
				boolean flag = false;
				for (int j = 0; j < i; j++)
					if (data[j][0].equals("" + p.getPackageID())) {
						flag = true;
						break;
					}
				if (flag) continue;

				data[i][0] = "" + p.getPackageID();
				data[i][1] = "" + p.getSenderAddress();
				data[i][2] = "" + p.getDestinationAddress();
				data[i][3] = "" + p.getPriority();
				data[i][4] = "" + p.getStatus();
				i++;
			}

			JTable table = new JTable(data, columnNames);
			scrollPane = new JScrollPane(table);
			scrollPane.setSize(450, table.getRowHeight() * size + 24);
			add(scrollPane, BorderLayout.CENTER);
			isTable2Visible = true;
		} else {
			isTable2Visible = false;
		}

		scrollPane.setVisible(isTable2Visible);
		repaint();
	}

	// Exit application
	public void destroy() {
		System.exit(0);
	}

	// Handle button clicks
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b_num[0])
			add();
		else if (e.getSource() == b_num[1])
			start();
		else if (e.getSource() == b_num[2])
			stop();
		else if (e.getSource() == b_num[3])
			resume();
		else if (e.getSource() == b_num[4])
			info();
		else if (e.getSource() == b_num[5])
			branchInfo();
	}
}
