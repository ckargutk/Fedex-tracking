package Shipment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Travel_history extends Thread{
	public static Connection connect1 = null;
	public ResultSet resultSet1 = null;
	public static PreparedStatement preparedStatement1 = null;
	public static PreparedStatement preparedStatement2 = null;
	public String Location;
	public String source;
	public String tracking_no;
	public String Activity;
	public String day;
	public String date;
	String time;
	SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE");
	SimpleDateFormat simpleDateformat1 = new SimpleDateFormat("MM-dd-yyyy");
	SimpleDateFormat simpleDateformat2 = new SimpleDateFormat("HH : MM");

	public void run(){
		try{
			day = simpleDateformat.format(Calendar.getInstance().getTime());
			time = DateFormat.getTimeInstance().format((Calendar.getInstance().getTime()));
			date = simpleDateformat1.format(Calendar.getInstance().getTime());

			System.out.println("\nDay:" +day);
			System.out.println("\nDate:" +date);
			System.out.println("\nTime:" +time);

			Activity="Departed from fedex location";
			System.out.println(Activity+": " +source);

			Class.forName("com.mysql.jdbc.Driver");

			connect1 = DriverManager
					.getConnection("jdbc:mysql://localhost/fedex?"
							+ "user=sqluser&password=sqluserpw&verifyServerCertificate=false&useSSL=true");
			Statement statement=connect1.createStatement();

			preparedStatement2 = connect1.prepareStatement("insert into fedex.travel_history values ('"+tracking_no+"','"+date+"','"+time+"','"+
					day+"','"+Activity+"','"+source+"')");
			System.out.println(source);
			System.out.println(tracking_no);
			preparedStatement2.executeUpdate();

			int sc,dn,up;
			sc = swch(source);
			dn = swch(Location);
			checknDo(sc, dn);
		}catch(Exception e)
		{
			System.out.println("OK");
			e.printStackTrace();

		}
	}
	public int swch(String sw){
		int n = 30;

		switch(sw){
		case "Northborough, MA":
			n = 1; 
			break;
		case "Edison, NJ": 
			n = 2; 
			break;
		case "Pittsburgh, PA": 
			n = 3; 
			break;
		case "Allentown, PABD Strap Endlinksth":
			n = 4; 
			break;
		case "Martinsburg, WV":  
			n = 5; 
			break;
		case "Charlotte, NC": 
			n = 6;
			break;
		case "Atlanta, GA":
			n = 7; 
			break;
		case "Orlando, FL":
			n = 8; 
			break;
		case "Memphis, TN":n = 9;
		break;
		case "Grove City, OH":
			n = 10; 
			break;
		case "Indianapolis, IN":
			n = 11; 
			break;
		case "Detroit, MI":
			n = 12; 
			break;
		case "New Berlin, WI":
			n = 13; 
			break;
		case "Minneapolis, MN":
			n = 14; 
			break;
		case "St. Louis, MO": 
			n = 15;
			break;
		case "Kansas, KS":
			n = 16;
			break;
		case "Dallas, TX":
			n = 17;
			break;
		case "Houston, TX":
			n = 18; 
			break;
		case "Denver, CO": 
			n = 19; 
			break;
		case "Salt Lake City, UT": 
			n = 20; 
			break;
		case "Phoenix, AZ": 
			n = 21; 
			break;
		case "Los Angeles, CA":
			n = 22; 
			break;
		case "Chino, CA":
			n = 23; 
			break;
		case "Sacramento, CA": 
			n = 24; 
			break;
		case "Seattle, WA": 
			n = 25; 
			break;
		default: 
			break;
		}
		return n;
	}
	public String unswch(int n){
		String city="";

		switch(n){
		case 1: 
			city = "Northborough, MA";
			break;
		case 2: 
			city = "Edison, NJ";
			break;
		case 3: 
			city = "Pittsburgh, PA";
			break;
		case 4:
			city = "Allentown, PABD Strap Endlinksth";
			break;
		case 5: 
			city = "Martinsburg, WV";
			break;
		case 6: 
			city = "Charlotte, NC";
			break;
		case 7:
			city = "Atlanta, GA"; 
			break;
		case 8:	
			city = "Orlando, FL";
			break;
		case 9: 
			city = "Memphis, TN"; 
			break;
		case 10: 
			city = "Grove City, OH";
			break;
		case 11:
			city = "Indianapolis, IN";
			break;
		case 12:
			city = "Detroit, MI";
			break;
		case 13:
			city = "New Berlin, WI"; 
			break;
		case 14:
			city = "Minneapolis, MN";
			break;
		case 15:
			city = "St. Louis, MO";
			break ;
		case 16: 
			city = "Kansas, KS";
			break;
		case 17:
			city = "Dallas, TX"; 
			break;
		case 18:
			city = "Houston, TX";
			break;
		case 19:
			city = "Denver, CO";
			break;
		case 20: 
			city = "Salt Lake City, UT"; 
			break;
		case 21:
			city = "Phoenix, AZ"; 
			break;
		case 22:
			city = "Los Angeles, CA"; 
			break;
		case 23: 
			city = "Chino, CA"; 
			break;
		case 24:
			city = "Sacramento, CA";
			break;
		case 25:
			city = "Seattle, WA";
			break;
		default: 
			break;
		}
		return city;
	}

	public void checknDo(int sc,int dn){
		System.out.println("\nInside ChecknDo!!");
		boolean direction;
		int up = 0;
		if(Math.abs(sc-dn) < 14) direction=true;
		else direction = false;
		int prevup;
		Activity = "being shipped";

		up = sc;
		System.out.println("UP value before insert(up): "+up);
		insert(up);

		if(dn <= sc) direction = !direction;


		try{
			while(up != dn){
				if(direction){

					TimeUnit.SECONDS.sleep(15);
					prevup=up;

					up++;
					if((prevup == 25) && (up == 26))
						up=1;
					Activity = "shipped";
					insert(up);
					System.out.println(tracking_no);
				}
				else {
					TimeUnit.SECONDS.sleep(15);
					prevup = up;
					up--;
					if((prevup == 1) && (up == 0))
						up = 25;
					Activity="shipped";
					insert(up);
					System.out.println(tracking_no);
				}
			}

			if(up == dn){
				Activity = "delivered";
				insert(up);
				System.out.println(tracking_no);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	public void insert(int up){
		String currentloc = unswch(up);
		System.out.println("\nCurrent Location is:" +currentloc);
		try{
			day = simpleDateformat.format(Calendar.getInstance().getTime());
			time = DateFormat.getTimeInstance().format((Calendar.getInstance().getTime()));
			date = simpleDateformat1.format(Calendar.getInstance().getTime());

			preparedStatement1=connect1.prepareStatement("insert into fedex.travel_history values ('"+tracking_no+"','"+date+"','"+time+"','"+
					day+"','"+Activity+"','"+currentloc+"')");

			preparedStatement1.executeUpdate();
			System.out.println(tracking_no);
		}
		catch(Exception e){
			System.out.println("inside insert");
			e.printStackTrace();
		}
	}
}


