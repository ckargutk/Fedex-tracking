package Shipment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;

public class Shipping_details extends Thread{
	public String service;
	Travel_history ts=new Travel_history();

	public String tracking_no;
	public float kg;
	public float lb;
	public String Dimensions;
	public String signature;
	public String pkg;
	public String total;
	public String instruction;
	static Connection connect1 = null;
	ResultSet resultSet1 = null;
	static PreparedStatement preparedStatement1 = null;
	public int len;
	public String source = null;
	public String Location = null;
	public boolean threadCreate=true;

	public void ask(){

		System.out.println(DateFormat.getTimeInstance().format((Calendar.getInstance().getTime())));
		kg = (float) ((lb)/2.2);


		if(pkg == "Letter")
			Dimensions = "10 * 10 * 10 in.";
		if(pkg == "Package")
			Dimensions = "40 * 40 * 40 in.";

		ts.source = source;
		ts.Location = Location;

		//Tracking Number
		Random r = new Random();
		tracking_no = ""+r.nextInt(999999);
		ts.tracking_no = tracking_no;
		System.out.println(tracking_no);
		ts.start();
		threadCreate=false;

	}

	public void get_start(){
		ts.start();
	}

	public void run(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connect1 = DriverManager
					.getConnection("jdbc:mysql://localhost/fedex?"
							+ "user=sqluser&password=sqluserpw&verifyServerCertificate=false&useSSL=true");
			Statement statement = connect1.createStatement();
			String weight=lb+" lbs/ "+kg+" kg";
			preparedStatement1=connect1.prepareStatement("insert into fedex.shipping_details () values ('"+tracking_no+"','"+service+"','"+weight
					+"','"+Dimensions+"','"+signature+"','"+total+"','"+instruction+"','"+pkg+"')");

			preparedStatement1.executeUpdate();
			System.out.println(ts.tracking_no);

		}catch(Exception e)
		{
			System.out.println("OK");
			e.printStackTrace();
		}
	}
}


