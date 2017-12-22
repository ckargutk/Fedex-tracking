package Shipment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FedExPacketInputGUI extends JFrame implements ActionListener {

	//Shipping_details.java
	String serviceName= "";
	float weightLbs = 0;
	float weightKgs = 0;
	String weight = "";
	String packageType = "";
	String packageSize = "";
	String signatureName = "";
	String pieceNum = "";
	String instruction = "";
	int trackingID;
	String sourceLocation = "";
	String destLocation = "";


	//Ship.java
	public ResultSet resultSet1 = null;
	public ResultSet resultSet2 = null;
	public static PreparedStatement preparedStatement1 = null;
	public static Connection connect = null;
	public static int len = 0;




	private final static int INFO_SIZE = 30;
	private JTextField _info = new JTextField("Welcome to FedEx Package Services!!",100);

	private JFrame frame =  new JFrame();
	private String bnames[]={ "New Packet","Enter Tracking ID"};
	private String lnames[]={ "Fedex Service", "Weight in Lbs.", "Package Type", 
			"Signature Service", "Enter Total Pieces", "Special Instructions", 
			"Source Location", "Destination Location", "Tracking ID","Your Tracking ID is"};
	private String bnames2[]={ "Enter"};
	private String mnames[]={ "Reset", "Exit"};

	private JButton buttons[];
	private JLabel labels[];
	private JButton buttons2[];   
	private JTextField tfields[];
	private JMenuItem menuitems[];

	private JMenuBar bar = new JMenuBar();
	private JMenu file = new JMenu("File");
	private int selecteditem = 0;

	//Array Lists
	public ArrayList<String> date = new ArrayList<>();
	public ArrayList<String> time = new ArrayList<>();
	public ArrayList<String> day = new ArrayList<>();
	public ArrayList<String> activity = new ArrayList<>();
	public ArrayList<String> location = new ArrayList<>();

	Shipping_details sd = new Shipping_details();

	/**
	 * Create the application.
	 */
	@SuppressWarnings("deprecation")
	public FedExPacketInputGUI() {
		super("FedExPacketInputGUI");
		//		initialize();
		setSize(800,800);
		setLocation(100,100);
		setlabels();
		setbuttons();
		settextfield();
		setbutton2();
		setmenubar();
		ContainerSetup();
		show();
	}

	/**
	 * Declares the Labels
	 */
	private void setlabels()
	{
		labels = new JLabel[lnames.length];
		for (int i = 0; i < lnames.length; i++) 
		{
			labels[i] = new JLabel(lnames[i]);
			labels[i].setHorizontalAlignment(SwingConstants.CENTER);
			labels[i].setEnabled(false);
		}
	}

	/**
	 * Declares the Buttons
	 */
	private void setbuttons()
	{
		buttons = new JButton[bnames.length];
		for (int i = 0; i < bnames.length; i++) 
		{
			buttons[i] = new JButton( bnames[i]);
			buttons[i].addActionListener(this);
		}
	}

	/**
	 * Declares the TextField
	 */
	private void settextfield()
	{
		tfields = new JTextField[lnames.length];
		for (int i = 0; i < lnames.length; i++) 
		{
			tfields[i] = new JTextField(INFO_SIZE);
			tfields[i].setEnabled(false);
		}
	}

	/**
	 * Declares the Enter Button
	 */
	private void setbutton2(){
		buttons2 = new JButton[bnames2.length];
		for (int i = 0; i < bnames2.length; i++) 
		{
			buttons2[i] = new JButton(bnames2[i]);
			buttons2[i].addActionListener(this);
		}
	}

	private void setmenubar()
	{
		menuitems = new JMenuItem[mnames.length];
		for (int i = 0; i < mnames.length; i++) 
		{
			menuitems[i] = new JMenuItem(mnames[i]);
			menuitems[i].addActionListener(this);
		}

	}

	private void ContainerSetup()
	{
		Container c = getContentPane();

		for (int i = 0; i < mnames.length; i++) file.add(menuitems[i]);
		bar.add(file);
		setJMenuBar(bar);

		//North Layout
		_info.setEditable(false);
		_info.setBackground(Color.white);
		c.add(_info,BorderLayout.NORTH);


		//South Layout
		JPanel spanel = new JPanel();
		for (int i = 0; i < bnames2.length; i++) spanel.add(buttons2[i]);
		c.add(spanel,BorderLayout.SOUTH);

		//Center Layout
		JPanel cpanel = new JPanel();
		cpanel.setBorder(BorderFactory.createLoweredBevelBorder());
		cpanel.setLayout(new GridLayout(lnames.length,2));
		for (int i = 0; i < lnames.length; i++) 
		{
			cpanel.add(labels[i]);
			cpanel.add(tfields[i]);
		}
		c.add(cpanel,BorderLayout.CENTER);

		//West Layout
		JPanel wpanel = new JPanel();
		wpanel.setLayout(new GridLayout(4,0));
		for (int i = 0; i< bnames.length; i++) wpanel.add(buttons[i]);
		c.add(wpanel,BorderLayout.WEST);

	}

	private void resetinfo()
	{
		for (int i=0; i< lnames.length; i++) 
		{
			tfields[i].setText("");
		}
	}

	private void inablebuttonsAll()
	{
		for (int i=0; i< buttons.length; i++) 
		{
			buttons[i].setEnabled(true);
		}
	}

	private void disableinfoAll()
	{
		for (int i=0; i <lnames.length; i++) 
		{
			labels[i].setEnabled(false);
			tfields[i].setEnabled(false);
		}
	}

	private void inableinfo(int b)
	{
		for (int i = 0; i < lnames.length; i++) 
		{
			if (b == i) 
			{
				labels[i].setEnabled(true);
				tfields[i].setEnabled(true);
			}
		} // end for
	}

	private void disablebuttons(int b)
	{
		for (int i=0; i< buttons.length; i++) 
		{
			if (b != i) buttons[i].setEnabled(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == menuitems[0])  //Clear
		{
			resetinfo();
			inablebuttonsAll();
			disableinfoAll();
		}
		if (source == menuitems[1])  //Exit
		{
			System.exit(1);
		}
		if (source == buttons[0])  // New Packet
		{
			for(int i = 0; i < 8; i++){
				inableinfo(i); //name
			}
			selecteditem = 0;
		}
		if (source == buttons[1])  // Enter Tracking ID
		{
			inableinfo(8); //Tracking ID
			selecteditem = 1;
		}

		if (source == buttons2[0])  //Enter Details
		{
			inablebuttonsAll();
			disableinfoAll();
			inableinfo(9);

			Shipping_details sd = new Shipping_details();
			switch (selecteditem) 
			{
			case 0:

				sd.service = tfields[0].getText();
				sd.lb = (float)(Float.parseFloat(tfields[1].getText()));
				sd.pkg = tfields[2].getText();
				sd.signature = tfields[3].getText();
				sd.total = tfields[4].getText();
				sd.instruction = tfields[5].getText();
				sd.source = tfields[6].getText();
				sd.Location = tfields[7].getText();
				sd.ask();
				labels[9].setText("Your Tracking ID is "+sd.tracking_no);
				sd.start();
				break;

			case 1:

				getDatabase(tfields[8].getText());
			}

			try	
			{
				_info.setText("Number of packets");
			} // end try

			catch (Exception ref) 
			{
				_info.setText("Invalid Entry");
			}

			finally 
			{
				resetinfo();
			}
		}

		for (int i=0; i <buttons.length; i++)  //types
		{
			if (source == buttons[i]) 
			{
				disablebuttons(i);
			}
		}

	}


	public void getDatabase(String tracking_no){
		System.out.println("Tracking Number:"+tracking_no);
		try {
			Class.forName("com.mysql.jdbc.Driver");

			connect = DriverManager.getConnection("jdbc:mysql://localhost/fedex?"
					+ "user=sqluser&password=sqluserpw&verifyServerCertificate=false&useSSL=true");
			Statement statement=connect.createStatement();

			resultSet2 = statement.executeQuery("SELECT * from travel_history where Tracking_no = '"+tracking_no+"'");


			while (resultSet2.next()) {
				date.add(resultSet2.getString(2));
				time.add(resultSet2.getString(3));
				day.add(resultSet2.getString(4));
				activity.add(resultSet2.getString(5));
				location.add(resultSet2.getString(6));
			}

			JFrame frame = new JFrame();
			frame.setLayout(new GridLayout(15,1));

			JLabel label1 = new JLabel("Travel History");
			frame.add(label1);

			JLabel label2 = new JLabel(date.get(1)+"  "+day.get(1));
			frame.add(label2);

			for(int j = 0; j < date.size(); j++){
				JLabel label3 = new JLabel(time.get(j)+"   "+activity.get(j)+"   "+location.get(j));
				frame.add(label3);
			}

			JLabel label4 = new JLabel("Shipment Details");
			frame.add(label4);

			JLabel label5 = new JLabel("Tracking no:"+tfields[8].getText());
			frame.add(label5);

			frame.setTitle("Tracker Details");
			frame.setSize(600,400);
			frame.setVisible(true);

		}catch (Exception e) {
			e.printStackTrace();
		}


	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FedExPacketInputGUI window = new FedExPacketInputGUI();
					window.frame.setVisible(true);
					window.addWindowListener( new WindowAdapter()
					{
						public void windowClosing(WindowEvent e) { System.exit(0); } 
					});


				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}


}
