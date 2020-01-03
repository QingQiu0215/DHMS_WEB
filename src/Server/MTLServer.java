package Server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import com.web.service.WebInterface;
@WebService(endpointInterface="com.web.service.WebInterface")
public class MTLServer implements WebInterface{
	DatagramSocket socketSer = null;
	DatagramSocket SocketCli=null;
	static MTLServer obj=null;
	InetAddress IPAddress = null;
	byte[] incomingData = null;
	ByteArrayOutputStream outputStream = null;
	ObjectOutputStream os = null;
	byte[] data = null;
	DatagramPacket sendPacket = null;
	DatagramPacket incomingPacket = null;	
	byte[] dataBack = null;
	ByteArrayInputStream in = null;
	ObjectInputStream is = null;
	
	DatagramSocket SocketCli2 = null;
	InetAddress IPAddress2 = null;
	byte[] incomingData2 = null;
	ByteArrayOutputStream outputStream2 = null;
	ObjectOutputStream os2 = null;
	byte[] data2 = null;
	DatagramPacket sendPacket2 = null;
	DatagramPacket incomingPacket2 = null;
	byte[] dataBack2 = null;
	ByteArrayInputStream in2 = null;
	ObjectInputStream is2 = null;
	
	int accessCount1=0;
	int accessCount2=0;
	static Map<String, Map<String,ArrayList<String>>> MTLMap = new HashMap<String, Map<String,ArrayList<String>>>();
	static Map<String, Map<String,ArrayList<String>>> otherMap1=null;
	static Map<String, Map<String,ArrayList<String>>> otherMap2=null;
	private final int maxCapacity=3;
	PrintWriter outputTxtClient = null;
	PrintWriter outputTxtServer = null;
	String cityForRemove="";
	String cityForAdd="";

	public MTLServer() throws RemoteException{
		super();
		ArrayList<String> temp1=new ArrayList<String>();
		ArrayList<String> temp2=new ArrayList<String>();	
		ArrayList<String> temp3=new ArrayList<String>();	
		ArrayList<String> temp4=new ArrayList<String>();	
		ArrayList<String> temp5=new ArrayList<String>();	
		ArrayList<String> temp6=new ArrayList<String>();	
		ArrayList<String> temp7=new ArrayList<String>();
		ArrayList<String> temp8=new ArrayList<String>();	
		ArrayList<String> temp9=new ArrayList<String>();
		temp1.add("2");temp1.add("2");temp1.add("MTLP0001");temp2.add("2");temp2.add("2");temp2.add("MTLP0002");
		temp3.add("2");temp3.add("2");temp3.add("MTLP0003");temp4.add("2");temp4.add("2");temp4.add("MTLP0004");
		temp5.add("2");temp5.add("2");temp5.add("MTLP0005");temp6.add("2");temp6.add("2");temp6.add("MTLP0006");
		temp7.add("2");temp7.add("2");temp7.add("MTLP0007");temp8.add("2");temp8.add("2");temp8.add("MTLP0008");
		temp9.add("0");temp9.add("2");temp9.add("MTLP0009");temp9.add("MTLP0010");temp9.add("MTLP0011");
	    Map<String,ArrayList<String>> t1=new HashMap<String,ArrayList<String>>();
		t1.put("MTLA101119",temp1);
		MTLMap.put("Physician",t1);
		MTLMap.get("Physician").put("MTLM101119",temp2);
		MTLMap.get("Physician").put("MTLE101119",temp3);
		Map<String,ArrayList<String>> t2=new HashMap<String,ArrayList<String>>();
		t2.put("MTLA101119",temp4);
		MTLMap.put("Surgeon",t2);
		MTLMap.get("Surgeon").put("MTLM101119",temp5);
		MTLMap.get("Surgeon").put("MTLE101119",temp6);
		Map<String,ArrayList<String>> t3=new HashMap<String,ArrayList<String>>();
		t3.put("MTLA101119",temp7);
		MTLMap.put("Dental",t3);
		MTLMap.get("Dental").put("MTLM101119",temp8);
		MTLMap.get("Dental").put("MTLE101119",temp9);
	}
	public static void main(String args[]) throws Exception
	{
		obj = new MTLServer();
		try {
			System.out.println("MTL Server Started...");
			MTLServer MTLServer = new MTLServer();
			Endpoint endpoint = Endpoint.publish("http://localhost:1010/MTL", MTLServer);
			obj.createAndListenSocketSer();

		}
		catch (Exception e) {
			System.err.println("ERROR: " + e);
			e.printStackTrace(System.out);
		}
		System.out.println("MTLServer Exiting ...");
		System.out.println("Server is Up & Running");
		
	}
	public void writeTxtClient(String clientID,String task, String result) {
		try
		 {
			 Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			 outputTxtClient =new PrintWriter(new FileOutputStream("client.txt",true));
			 outputTxtClient.flush();
			 outputTxtClient.printf("%-15s%-35s%-15s%-60s%n",clientID,task,result,timestamp);
			 outputTxtClient.close();
		 }
		 catch (FileNotFoundException e)
		 {
			 System.out.println("Error opening the file client.txt.");
			 System.exit(0);
		 }
	}
	public void writeTxtServerMTL(String clientID,String patientID,String appointmentType,String appointmentID,String task, String result) {
		try
		 {
			 Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			 outputTxtServer =new PrintWriter(new FileOutputStream("MTLServer.txt",true));
			 outputTxtServer.flush();
			 outputTxtServer.printf("%-15s%-15s%-20s%-20s%-35s%-15s%-60s%n",clientID,patientID,appointmentType,appointmentID,task,result,timestamp);
			 outputTxtServer.close();
		 }
		 catch (FileNotFoundException e)
		 {
			 System.out.println("Error opening the file MTLServer.txt.");
			 System.exit(0);
		 }
	}
	public void writeTxtServerQUE(String clientID,String patientID,String appointmentType,String appointmentID,String task, String result) {
		try
		 {
			 Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			 outputTxtServer =new PrintWriter(new FileOutputStream("QUEServer.txt",true));
			 outputTxtServer.flush();
			 outputTxtServer.printf("%-15s%-15s%-20s%-20s%-35s%-15s%-60s%n",clientID,patientID,appointmentType,appointmentID,task,result,timestamp);
			 outputTxtServer.close();
		 }
		 catch (FileNotFoundException e)
		 {
			 System.out.println("Error opening the file MTLServer.txt.");
			 System.exit(0);
		 }
	}
	public void writeTxtServerSHE(String clientID,String patientID,String appointmentType,String appointmentID,String task, String result) {
		try
		 {
			 Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			 outputTxtServer =new PrintWriter(new FileOutputStream("SHEServer.txt",true));
			 outputTxtServer.flush();
			 outputTxtServer.printf("%-15s%-15s%-20s%-20s%-35s%-15s%-60s%n",clientID,patientID,appointmentType,appointmentID,task,result,timestamp);
			 outputTxtServer.close();
		 }
		 catch (FileNotFoundException e)
		 {
			 System.out.println("Error opening the file MTLServer.txt.");
			 System.exit(0);
		 }
	}
	
	public synchronized String addAppointment(String appointmentID, String appointmentType, String strCapacity,String appointmentWeekStr)
	{
		ArrayList<String> subValue=new ArrayList<String>();
		//String strCapacity=String.valueOf(capacity);
		subValue.add(strCapacity);subValue.add(appointmentWeekStr);
		String cityName=appointmentID.substring(0, 3);
		if(cityName.equalsIgnoreCase("MTL")) {
			MTLMap.get(appointmentType).put(appointmentID, subValue);
//			System.out.println("The following appointment was added in the MTL Hospital System:");
//			System.out.println(appointmentType+"  "+appointmentID+"  "+strCapacity);
			printAppointment(MTLMap);
			return "The following appointment was added in the MTL Hospital System:"+appointmentType+"  "+appointmentID+"  "+strCapacity;
		}			
		else {
			//System.out.println("You cannot add appointment for other cities.");
			return "You cannot add appointment for other cities.";
		}
			
		
	}
	public synchronized boolean checkAppointmentExisted(String appointmentID, String appointmentType)
	{
		boolean key1=MTLMap.containsKey(appointmentType);
		boolean key2=MTLMap.get(appointmentType).containsKey(appointmentID);
		if(key1&&key2) {
			return true;
		}
		return false;
	}
	public synchronized String removeAppointment(String appointmentID, String appointmentType)
	{
		int arraySize=MTLMap.get(appointmentType).get(appointmentID).size();
		if(checkCapacityAfterDate(appointmentID,appointmentType)) {
			distributePatient(appointmentID, appointmentType);
			return "Successfully removed";
		}		
		//System.out.println("The appointment cannot be removed because there is not enough capacity in the further to distribute these patients.");
		return "The appointment cannot be removed because there is not enough capacity in the further to distribute these patients.";
	}
	private synchronized boolean checkCapacityAfterDate(String appointmentID, String appointmentType)
	{
		int removingQty=MTLMap.get(appointmentType).get(appointmentID).size()-2;		
		String checkDate=appointmentID.substring(4);
		String orderedCheckDate=checkDate.substring(4)+checkDate.substring(2, 4)+checkDate.substring(0, 2);
		int convertOrderedCheckDate=Integer.parseInt(orderedCheckDate);
		String date="";
		String orderedDate="";
		int convertOrderedDate=0;
		
		char checkSlot=appointmentID.charAt(3);
		int remainingCapacity=0;
		
		for(Map.Entry<String, ArrayList<String>> nestedMap:MTLMap.get(appointmentType).entrySet()) {
			date=nestedMap.getKey().substring(4);
			orderedDate=date.substring(4)+date.substring(2, 4)+date.substring(0, 2);
			convertOrderedDate=Integer.parseInt(orderedDate);
			char slot=nestedMap.getKey().charAt(3);
			String capacityStr="";
			int capacityInt=0;
			if(convertOrderedDate>convertOrderedCheckDate) {
				capacityStr=nestedMap.getValue().get(0);
				capacityInt=Integer.parseInt(capacityStr);
				remainingCapacity=remainingCapacity+capacityInt;
			}else if(convertOrderedDate==convertOrderedCheckDate&&checkSlot!=slot) {
				capacityStr=nestedMap.getValue().get(0);
				capacityInt=Integer.parseInt(capacityStr);
				if((checkSlot=='M') ||(checkSlot=='A'&&slot!='M')) {
					remainingCapacity=remainingCapacity+capacityInt;
				}
			}
		}
		if(remainingCapacity>=removingQty) {
			return true;
		}
		return false;
	}
	public synchronized void distributePatient(String appointmentID, String appointmentType) 
	{
		int removingQty=MTLMap.get(appointmentType).get(appointmentID).size()-2;		
		String checkDate=appointmentID.substring(4);
		String orderedCheckDate=checkDate.substring(4)+checkDate.substring(2, 4)+checkDate.substring(0, 2);
		int convertOrderedCheckDate=Integer.parseInt(orderedCheckDate);
		String date="";
		String orderedDate="";
		int convertOrderedDate=0;
		
		char checkSlot=appointmentID.charAt(3);
		int track=2;
		for(Map.Entry<String, ArrayList<String>> nestedMap:MTLMap.get(appointmentType).entrySet()) {
			date=nestedMap.getKey().substring(4);
			orderedDate=date.substring(4)+date.substring(2, 4)+date.substring(0, 2);
			convertOrderedDate=Integer.parseInt(orderedDate);
			char slot=nestedMap.getKey().charAt(3);
			String capacityStr="";
			int capacityInt=0;
			if(convertOrderedDate>convertOrderedCheckDate) {
				capacityStr=nestedMap.getValue().get(0);
				capacityInt=Integer.parseInt(capacityStr);
				if(removingQty<=capacityInt) {
					String cell="";
					while(removingQty!=0) {
						cell=MTLMap.get(appointmentType).get(appointmentID).get(track);
						nestedMap.getValue().add(cell);
						track++;
						capacityInt--;
						removingQty--;
					}
					String temp=capacityInt+"";
					nestedMap.getValue().set(0, temp);
					MTLMap.get(appointmentType).remove(appointmentID);
					track=1;
					if(removingQty==0)
						break;
				}else {
					String cell="";

					while(capacityInt!=0) {
						cell=MTLMap.get(appointmentType).get(appointmentID).get(track);
						nestedMap.getValue().add(cell);
						track++;
						capacityInt--;
						removingQty--;
					}
					String temp=0+"";
					nestedMap.getValue().set(0, temp);
				}						
			}else if(convertOrderedDate==convertOrderedCheckDate&&checkSlot!=slot) {
				capacityStr=nestedMap.getValue().get(0);
				capacityInt=Integer.parseInt(capacityStr);
				if((checkSlot=='M') ||(checkSlot=='A'&&slot!='M')) {
					if(removingQty<=capacityInt) {
						String cell="";
						while(removingQty!=0) {
							cell=MTLMap.get(appointmentType).get(appointmentID).get(track);
							nestedMap.getValue().add(cell);
							track++;
							capacityInt--;
							removingQty--;
						}
						String temp=capacityInt+"";
						nestedMap.getValue().set(0, temp);
						MTLMap.get(appointmentType).remove(appointmentID);
						track=1;
						if(removingQty==0)
							break;
					}else {
						String cell="";

						while(capacityInt!=0) {
							cell=MTLMap.get(appointmentType).get(appointmentID).get(track);
							nestedMap.getValue().add(cell);
							track++;
							capacityInt--;
							removingQty--;
						}
						String temp=0+"";
						nestedMap.getValue().set(0, temp);
					}	
				}
			}
		}			
	}
	public String listAppointmentAvailability(String appointmentType)
	{
		//System.out.println("*** The following is QUE INFO ***");
		String listAppointmentAvailability="";
		try {
			listAppointmentAvailability=obj.createAndListenSocketCli(appointmentType,"-","listAppointmentAvailability","-","-","-","-");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Successfully list for "+appointmentType+"\n"+listAppointmentAvailability;
	}
	public synchronized String bookAppointment(String clientID,String patientID, String appointmentID, String appointmentType)
	{
		String success="";
		try {
			success = obj.createAndListenSocketCli(appointmentType,patientID,"bookAppointment",appointmentID,clientID,"-","-");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
	}
	public String getAppointmentSchedule(String patientID)
	{
		String getAppointmentSchedule="";
		try {
			getAppointmentSchedule=obj.createAndListenSocketCli("-",patientID,"getAppointmentSchedule","-","-","-","-");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "true"+"\n"+getAppointmentSchedule;
	}
	public synchronized String cancelAppointment(String clientID,String patientID, String appointmentID,String appointmentType)
	{
		String success="";
		try {
			success = obj.createAndListenSocketCli(appointmentType,patientID,"cancelAppointment",appointmentID,clientID,"-","-");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
	}
	
	public synchronized String swapAppointment(String clientID, String patientID,String oldAppointmentID, String oldAppointmentType,String newAppointmentID, String newAppointmentType) {
		String success="";
		try {
			success = obj.createAndListenSocketCli(oldAppointmentType,patientID,"swapAppointment",oldAppointmentID,clientID,newAppointmentID,newAppointmentType);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
	}
	
	
	
	public void printAppointment(Map<String, Map<String,ArrayList<String>>> map)
	{
		for(Map.Entry<String, Map<String,ArrayList<String>>> mtl:map.entrySet())
		{
			String appointmentType=mtl.getKey();
			System.out.println(appointmentType);
			for(Map.Entry<String, ArrayList<String>> nestedMap:mtl.getValue().entrySet())
			{
				System.out.print("    "+nestedMap.getKey()+" ");
				for(String op:nestedMap.getValue()) {
					System.out.print(op+"  ");
				}
				System.out.println("");
			}
		}
	}

	public String printAppointmentByType(Map<String, Map<String,ArrayList<String>>> map, String type) {
		String printAppointmentByType="";
		for(Map.Entry<String, Map<String,ArrayList<String>>> mtl:map.entrySet())
		{
			String appointmentType=mtl.getKey();
			if(appointmentType.equalsIgnoreCase(type)) {
				printAppointmentByType+=appointmentType+"  \n";
				for(Map.Entry<String, ArrayList<String>> nestedMap:mtl.getValue().entrySet())
				{
					printAppointmentByType += "    "+nestedMap.getKey()+" ";
					for(String op:nestedMap.getValue()) {
						printAppointmentByType +=op+"  ";
					}
					printAppointmentByType +="\n";
				}
				printAppointmentByType +="\n";
			}
			
		}
		return printAppointmentByType;
	}

	public String printAppointmentBySchedule(Map<String, Map<String,ArrayList<String>>> map,String clientID){
		String printAppointmentBySchedule="";
		for(Map.Entry<String, Map<String,ArrayList<String>>> mtl:map.entrySet())
		{
			String appointmentType=mtl.getKey();			
			for(Map.Entry<String, ArrayList<String>> nestedMap:mtl.getValue().entrySet())
			{
				for(int i=0;i<nestedMap.getValue().size();i++)
				{
					if(nestedMap.getValue().get(i).equalsIgnoreCase(clientID)) 						
						printAppointmentBySchedule+=appointmentType+" "+nestedMap.getKey()+"  "+nestedMap.getValue().get(i)+"\n";
				}
			}
			printAppointmentBySchedule+="\n";
		}
		return printAppointmentBySchedule;
	}
	
	public synchronized String createAndListenSocketCli(String appointmentType,String patientID,String task, String appointmentID,String clientID,
			String newAppointmentID, String newAppointmentType) throws ClassNotFoundException, IOException {
		String listAppointmentByType="";
		String listAppointmentBySchedule="";
		try {
			Message msg=null;
			if(accessCount1==0) {
				SocketCli = new DatagramSocket();
				IPAddress = InetAddress.getByName("localhost");				
				incomingData = new byte[1024];
			}
			
			if(task.equalsIgnoreCase("listAppointmentAvailability")||task.equalsIgnoreCase("getAppointmentSchedule")) {
				msg = new Message("Connect for listing");
			}else if(task.equalsIgnoreCase("bookAppointment")||task.equalsIgnoreCase("cancelAppointment")||task.equalsIgnoreCase("swapAppointment")) {
				msg = new Message("Connect for modifying");
			}
			if(accessCount1==0) {
				outputStream = new ByteArrayOutputStream();
				os = new ObjectOutputStream(outputStream);
				os.writeObject(msg);
				data = outputStream.toByteArray();
				sendPacket = new DatagramPacket(data, data.length, IPAddress, 9876);
				SocketCli.send(sendPacket);
				incomingPacket = new DatagramPacket(incomingData, incomingData.length);
				SocketCli.receive(incomingPacket);
				
				dataBack = incomingPacket.getData();
				in = new ByteArrayInputStream(dataBack);
				is = new ObjectInputStream(in);
				Message msg1 = (Message) is.readObject();
				otherMap1=msg1.getMap();
				accessCount1=1;
				//SocketCli.close();
			}
			listAppointmentByType+=	"*** Appointments Summary (QUE) ***"+"\n";	
			listAppointmentBySchedule+="*** Appointments Summary (QUE) ***"+"\n";	
			//System.out.println("*** Appointments Summary (QUE) ***");
			if(task.equalsIgnoreCase("listAppointmentAvailability")) {
				listAppointmentByType+=printAppointmentByType(otherMap1,appointmentType);
				writeTxtServerQUE(clientID,patientID,appointmentType,appointmentID,"list Appointment Availability", "Success");
			}				
			else if(task.equalsIgnoreCase("getAppointmentSchedule"))
			{
				listAppointmentBySchedule+=printAppointmentBySchedule(otherMap1,patientID);
				writeTxtServerQUE(clientID,patientID,appointmentType,appointmentID,"get Appointment Schedule", "Success");
			}				
			else if(task.equalsIgnoreCase("bookAppointment"))
			{
				printAppointment(otherMap1);
				writeTxtServerQUE(clientID,patientID,appointmentType,appointmentID,"book Appointment", "accessed");
			}else if(task.equalsIgnoreCase("cancelAppointment"))
			{
				printAppointment(otherMap1);
				writeTxtServerQUE(clientID,patientID,appointmentType,appointmentID,"cancelAppointment", "accessed");
			}else if(task.equalsIgnoreCase("swapAppointment"))
			{
				printAppointment(otherMap1);
				writeTxtServerQUE(clientID,patientID,appointmentType,appointmentID,"swapAppointment", "accessed");
			}
			Thread.sleep(2000);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		try {
			Message msg2=null;
			if(accessCount2==0) {
				SocketCli2 = new DatagramSocket();
			    IPAddress2 = InetAddress.getByName("localhost");
			    incomingData2 = new byte[1024];
			}
			if(task.equalsIgnoreCase("listAppointmentAvailability")||task.equalsIgnoreCase("getAppointmentSchedule")) {
				msg2 = new Message("Connect for listing");
			}else if(task.equalsIgnoreCase("bookAppointment")||task.equalsIgnoreCase("cancelAppointment")||task.equalsIgnoreCase("swapAppointment")) {
				msg2 = new Message("Connect for modifying");
			}
			if(accessCount2==0) {
				outputStream2 = new ByteArrayOutputStream();
				os2 = new ObjectOutputStream(outputStream2);
				os2.writeObject(msg2);
				data2 = outputStream2.toByteArray();
				sendPacket2 = new DatagramPacket(data2, data2.length, IPAddress2, 9875);
				SocketCli2.send(sendPacket2);
				incomingPacket2 = new DatagramPacket(incomingData2, incomingData2.length);
				SocketCli2.receive(incomingPacket2);
				
				dataBack2 = incomingPacket2.getData();
				in2 = new ByteArrayInputStream(dataBack2);
				is2 = new ObjectInputStream(in2);
				Message msg3 = (Message) is2.readObject();
				otherMap2=msg3.getMap();
				accessCount2=1;
				//SocketCli2.close();
			}
			
			listAppointmentByType+=	"*** Appointments Summary (SHE) ***"+"\n";	
			listAppointmentBySchedule+="*** Appointments Summary (SHE) ***"+"\n";
			//System.out.println("*** Appointments Summary (SHE) ***");
			if(task.equalsIgnoreCase("listAppointmentAvailability"))
			{
				listAppointmentByType+=printAppointmentByType(otherMap2,appointmentType);
				writeTxtServerSHE(clientID,patientID,appointmentType,appointmentID,"list Appointment Availability", "Success");
			}				
			else if(task.equalsIgnoreCase("getAppointmentSchedule")) {
				listAppointmentBySchedule+=printAppointmentBySchedule(otherMap2,patientID);
				writeTxtServerSHE(clientID,patientID,appointmentType,appointmentID,"get Appointment Schedule", "Success");
			}				
			else if(task.equalsIgnoreCase("bookAppointment"))
			{
				writeTxtServerSHE(clientID,patientID,appointmentType,appointmentID,"book Appointment", "accessed");
				printAppointment(otherMap2);
			}else if(task.equalsIgnoreCase("cancelAppointment"))
			{
				writeTxtServerSHE(clientID,patientID,appointmentType,appointmentID,"cancel Appointment", "accessed");
				printAppointment(otherMap2);
			}else if(task.equalsIgnoreCase("swapAppointment"))
			{
				printAppointment(otherMap2);
				writeTxtServerSHE(clientID,patientID,appointmentType,appointmentID,"swapAppointment", "accessed");
			}				
			Thread.sleep(2000);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		listAppointmentByType+=	"*** Appointments Summary (MTL) ***"+"\n";	
		listAppointmentBySchedule+="*** Appointments Summary (MTL) ***"+"\n";
		//System.out.println("*** Appointments Summary (MTL) ***");
		if(task.equalsIgnoreCase("listAppointmentAvailability"))
		{
			writeTxtServerMTL(clientID,patientID,appointmentType,appointmentID,"list Appointment Availability", "Success");
			listAppointmentByType+=printAppointmentByType(MTLMap,appointmentType);
		}			
		else if(task.equalsIgnoreCase("getAppointmentSchedule")) {
			writeTxtServerMTL(clientID,patientID,appointmentType,appointmentID,"get Appointment Schedule", "Success");
			listAppointmentBySchedule+=printAppointmentBySchedule(MTLMap,patientID);
		}			
		else if(task.equalsIgnoreCase("bookAppointment")) {
			writeTxtServerMTL(clientID,patientID,appointmentType,appointmentID,"book Appointment", "accessed");
			printAppointment(MTLMap);
		}
		else if(task.equalsIgnoreCase("cancelAppointment")) {
			writeTxtServerMTL(clientID,patientID,appointmentType,appointmentID,"cancel Appointment", "accessed");
			printAppointment(MTLMap);
		}else if(task.equalsIgnoreCase("swapAppointment"))
		{
			printAppointment(MTLMap);
			writeTxtServerMTL(clientID,patientID,appointmentType,appointmentID,"swapAppointment", "accessed");
		}	
		if(task.equalsIgnoreCase("listAppointmentAvailability")) {
			return listAppointmentByType;
		}else if(task.equalsIgnoreCase("getAppointmentSchedule")) {
			return listAppointmentBySchedule;
		}else if(task.equalsIgnoreCase("bookAppointment")) {
        	String success=bookOperation(appointmentType,patientID,task, appointmentID,clientID);
        	if(success.equalsIgnoreCase("true"))      		
        		return "           Congratulations ! New appointment was booked.";
        	String response=(success.equalsIgnoreCase("true"))?"Success":"Failed";
        	writeTxtServerMTL(clientID,patientID,appointmentType,appointmentID,"book Appointment", response);
//        		operations(clientID);  
        	return success;
        }else if(task.equalsIgnoreCase("cancelAppointment")) {
        	String success=cancelOperation(appointmentType,patientID,task, appointmentID,clientID);
//        	if(success) 
//        		System.out.println("           The appointment was canceled successfully.");
        	String response=(success.equalsIgnoreCase("Successfully cancelled"))?"Success":"Failed";
        	writeTxtServerMTL(clientID,patientID,appointmentType,appointmentID,"cancel Appointment", response);
    		//operations(clientID);  
        	return success;
        }else if(task.equalsIgnoreCase("swapAppointment")) {
        	String success=swapCheck(appointmentType,patientID,task, appointmentID,clientID,newAppointmentID,newAppointmentType);
//        	if(success) 
//        		System.out.println("           The appointment was swaped successfully.");
        	String response=(success.equalsIgnoreCase("Successfully swapped"))?"Success":"Failed";
        	writeTxtServerMTL(clientID,patientID,appointmentType,appointmentID,"swap Appointment(old)", response);
        	writeTxtServerMTL(clientID,patientID,newAppointmentType,newAppointmentID,"swap Appointment(new)", response);
        	if(success.equalsIgnoreCase("Successfully swapped")) {
	    		swapOperation(appointmentType,patientID,task, appointmentID,clientID,newAppointmentID, newAppointmentType,cityForRemove, cityForAdd);
        	}       		
    		//operations(clientID);  
        	return success;
        }       	
		return "true";
	}
	public void createAndListenSocketSer() {
		try {
			socketSer = new DatagramSocket(9874);
			
		while (true) {
			byte[] incomingData = new byte[1024];
			DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
			socketSer.receive(incomingPacket);
			byte[] data = incomingPacket.getData();
			ByteArrayInputStream in = new ByteArrayInputStream(data);
			ObjectInputStream is = new ObjectInputStream(in);
			String str="";
			try {
				Message msg = (Message) is.readObject();
				str=msg.getMsg();
				if(str.equalsIgnoreCase("Connect for listing")) {
					Message msgSend=new Message(MTLMap);

					ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
					ObjectOutput os = new ObjectOutputStream(outputStream1);
					os.writeObject(msgSend);
					
					InetAddress IPAddress = incomingPacket.getAddress();
					int port = incomingPacket.getPort();
					
					byte[] dataSend = outputStream1.toByteArray();
					DatagramPacket replyPacket =new DatagramPacket(dataSend, dataSend.length, IPAddress, port);
					socketSer.send(replyPacket);
					writeTxtServerMTL("-","-","-","-","Send DB", "Success");
					outputStream1.close();
					os.close();
				}
				if(str.equalsIgnoreCase("Connect for modifying")) {
					Message msgSend=new Message(MTLMap);

					ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
					ObjectOutput os = new ObjectOutputStream(outputStream1);
					os.writeObject(msgSend);
					
					InetAddress IPAddress = incomingPacket.getAddress();
					int port = incomingPacket.getPort();
					
					byte[] dataSend = outputStream1.toByteArray();
					DatagramPacket replyPacket =new DatagramPacket(dataSend, dataSend.length, IPAddress, port);
					socketSer.send(replyPacket);
					writeTxtServerMTL("-","-","-","-","Send DB", "Success");
//					outputStream1.close();
//					os.close();
					socketSer.receive(incomingPacket);
					writeTxtServerMTL("-","-","-","-","Received DB", "Success");
					byte[] dataBack = incomingPacket.getData();
					Message msg1=null;
					try {
						msg1 = (Message) is.readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//return message from server.
					MTLMap=msg1.getMap();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//					in.close();
//					is.close();
				
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}		
		}

		} catch (SocketException e) {
		e.printStackTrace();
		} catch (IOException i) {
		i.printStackTrace();
		}
		}
	public synchronized String bookOperation(String appointmentType,String patientID,String task, String appointmentID,String clientID) throws IOException {
		//System.out.println("Which city you want to book appointment?");
		String success=bookInTheCity(appointmentType,patientID,task, appointmentID,clientID);		
		return success;
	}
	public synchronized String bookInTheCity(String appointmentType,String patientID,String task, String appointmentID,String clientID) throws IOException
	{
//		System.out.println("Please enter the City:\n1. MTL;\n2. QUE;\n3. SHE");
		
//		int input=0;
//		Scanner keyboard=new Scanner(System.in);
//		input=keyboard.nextInt();
		String city=appointmentID.substring(0, 3);
		String valid="";
		switch(city) {
			case "MTL": 
			{
//				city="MTL";
				valid=validation(appointmentType,patientID,task, appointmentID,clientID,MTLMap,otherMap1,otherMap2);
				String response=(valid.equalsIgnoreCase("true"))?"Success":"Failed";
	        	writeTxtServerMTL(clientID,patientID,appointmentType,appointmentID,"book Appointment", response);
				if(!valid.equalsIgnoreCase("true"))
					return valid;
					//operations(clientID);
				else {
					MTLMap.get(appointmentType).get(appointmentID).add(patientID);
					String changeCapacityStr=MTLMap.get(appointmentType).get(appointmentID).get(0);
					int changeCapacityInt=Integer.parseInt(changeCapacityStr);
					int changed=changeCapacityInt-1;
					String changedStr=changed+"";
					MTLMap.get(appointmentType).get(appointmentID).set(0, changedStr);
				}
					
			}break;
			case "QUE": 
			{
//				city="QUE";
				valid=validation(appointmentType,patientID,task, appointmentID,clientID,otherMap1,otherMap2,MTLMap);
				String response=(valid.equalsIgnoreCase("true"))?"Success":"Failed";
	        	writeTxtServerQUE(clientID,patientID,appointmentType,appointmentID,"book Appointment", response);
				if(!valid.equalsIgnoreCase("true"))
					return valid;
					//operations(clientID);
				else {
					otherMap1.get(appointmentType).get(appointmentID).add(patientID);
					String changeCapacityStr=otherMap1.get(appointmentType).get(appointmentID).get(0);
					int changeCapacityInt=Integer.parseInt(changeCapacityStr);
					int changed=changeCapacityInt-1;
					String changedStr=changed+"";
					otherMap1.get(appointmentType).get(appointmentID).set(0, changedStr);
			
					Message msg5 = new Message(otherMap1);
					os.writeObject(msg5);
					data = outputStream.toByteArray();
					sendPacket = new DatagramPacket(data, data.length, IPAddress, 9876);
					SocketCli.send(sendPacket);
				}
					
			}break;
			case "SHE": 
			{
//				city="SHE";
				valid=validation(appointmentType,patientID,task, appointmentID,clientID,otherMap2,otherMap1,MTLMap);
				String response=(valid.equalsIgnoreCase("true"))?"Success":"Failed";
	        	writeTxtServerSHE(clientID,patientID,appointmentType,appointmentID,"book Appointment", response);
				if(!valid.equalsIgnoreCase("true"))
					return valid;
					//operations(clientID);
				else {
					otherMap2.get(appointmentType).get(appointmentID).add(patientID);
					String changeCapacityStr=otherMap2.get(appointmentType).get(appointmentID).get(0);
					int changeCapacityInt=Integer.parseInt(changeCapacityStr);
					int changed=changeCapacityInt-1;
					String changedStr=changed+"";
					otherMap2.get(appointmentType).get(appointmentID).set(0, changedStr);

					Message msg6 = new Message(otherMap2);

					os2.writeObject(msg6);
					data2 = outputStream2.toByteArray();
					sendPacket2 = new DatagramPacket(data2, data2.length, IPAddress2, 9875);
					SocketCli2.send(sendPacket2);					
				}
			}break;
		}
		return valid;
	}
	public synchronized String validation(String appointmentType,String patientID,String task, String appointmentID,String clientID,
			Map<String, Map<String,ArrayList<String>>> otherMapA,Map<String, Map<String,ArrayList<String>>> otherMapB,
			Map<String, Map<String,ArrayList<String>>> otherMapC) {	
		//a patient cannot book an appointment which does not exist
		boolean existInCity=otherMapA.get(appointmentType).containsKey(appointmentID);
		if(!existInCity) {
			//System.out.println("You cannot book this appointment because this appointment does not exist.");
			return "You cannot book this appointment because this appointment does not exist.";
		}
				
		//a patient cannot book more than one appointment with the same appointment id and same appointment type
		String capacityStr1=otherMapA.get(appointmentType).get(appointmentID).get(0);
		int capacityInt1=Integer.parseInt(capacityStr1);
		boolean capacityOk1=capacityInt1>0?true:false;
		boolean contain1=otherMapA.get(appointmentType).get(appointmentID).contains(patientID);
		boolean success1=capacityOk1&&!contain1;
		if(contain1) {
			//System.out.println("You cannot book more than one appointment with the same appointment id and same appointment type.");
			return "You cannot book more than one appointment with the same appointment id and same appointment type.";
		}else if(!capacityOk1) {
			//System.out.println("You cannot book this appointment because it is full.");
			return "You cannot book this appointment because it is full.";
		}
		
		//a patient cannot have more than one booking of same appointment type in a day.
		boolean contain2=false;
		String checkDate=appointmentID.substring(4);
		String orderedCheckDate=checkDate.substring(4)+checkDate.substring(2, 4)+checkDate.substring(0, 2);
		int convertOrderedCheckDate=Integer.parseInt(orderedCheckDate);
		String date="";
		String orderedDate="";
		int convertOrderedDate=0;
		String capacityStr2="";
		int capacityInt2=0;
		for(Map.Entry<String, ArrayList<String>> nestedMap:otherMapA.get(appointmentType).entrySet()) {
			String capacityStrTemp=otherMapA.get(appointmentType).get(appointmentID).get(0);
			capacityInt2=capacityInt2+Integer.parseInt(capacityStrTemp);			
			date=nestedMap.getKey().substring(4);
			orderedDate=date.substring(4)+date.substring(2, 4)+date.substring(0, 2);
			convertOrderedDate=Integer.parseInt(orderedDate);
			if(convertOrderedDate==convertOrderedCheckDate) {
				contain2=nestedMap.getValue().contains(patientID);
				if(contain2==true)
					break;
			}
		}
		boolean capacityOk2=capacityInt2>0?true:false;		
		boolean success2=capacityOk2&&!contain2;
		if(contain2) {
			//System.out.println("cannot have more than one booking of same appointment type in a day.");
			return "cannot have more than one booking of same appointment type in a day.";
		}else if(!capacityOk2) {
			//System.out.println("You cannot book this appointment because it is full.");
			return "You cannot book this appointment because it is full.";
		}
		//a patient can only book at most 3 appointments from other cities.
		String cityToBook=appointmentID.substring(0, 3);
		boolean success3=false;
		if(!cityToBook.equalsIgnoreCase("MTL")) {			
		
			int count1=0;
			String dateToCheck1="";
			String weekCheck1="0";
			for(Map.Entry<String, Map<String,ArrayList<String>>> map:otherMapB.entrySet())
			{
				for(Map.Entry<String, ArrayList<String>> nestedMap:map.getValue().entrySet())
				{
					if(nestedMap.getValue().contains(patientID))
					{
						if(count1==0) {
							dateToCheck1=nestedMap.getKey();
							weekCheck1=nestedMap.getValue().get(1);
						    count1++;
						}else {
							String dateToCheck11=nestedMap.getKey();
							String subdateToCheck1=dateToCheck1.substring(6);
							String subdateToCheck11=dateToCheck11.substring(6);
							if(weekCheck1.equalsIgnoreCase(nestedMap.getValue().get(1))&&subdateToCheck1.equalsIgnoreCase(subdateToCheck11))
								count1++;
						}					   
					}					
				}
			}
			int count2=0;
			String dateToCheck2="";
			String weekCheck2="0";
			for(Map.Entry<String, Map<String,ArrayList<String>>> map:otherMapA.entrySet())
			{
				for(Map.Entry<String, ArrayList<String>> nestedMap:map.getValue().entrySet())
				{
					if(nestedMap.getValue().contains(patientID))
					{
						if(count2==0) {
							dateToCheck2=nestedMap.getKey();
							weekCheck2=nestedMap.getValue().get(1);
						    count2++;
						}else {
							String dateToCheck22=nestedMap.getKey();
							String subdateToCheck2=dateToCheck2.substring(6);
							String subdateToCheck22=dateToCheck22.substring(6);
							if(weekCheck1.equalsIgnoreCase(nestedMap.getValue().get(1))&&subdateToCheck2.equalsIgnoreCase(subdateToCheck22))
								count2++;
						}					   
					}					
				}
			}
			int count=count1+count2;
			success3=count<3?true:false;
			if(!success3) {
				//System.out.println("You cannot book more than 3 appointments from other cities.");
				return "You cannot book more than 3 appointments from other cities.";
				}
		}
		if(cityToBook.equalsIgnoreCase("MTL")) {
			if(success1&&success2)
				return "true";
			else
				return "false";
		}else {
			if(success1&&success2&&success3)
				return "true";
			else
				return "false";
		}
		
	}
	public synchronized String cancelOperation(String appointmentType,String patientID,String task, String appointmentID,String clientID) throws IOException {
//		System.out.println("Please enter the City:\n1. MTL;\n2. QUE;\n3. SHE");
//		
//		int input=0;
//		Scanner keyboard=new Scanner(System.in);
//		input=keyboard.nextInt();
		//String cityName1=appointmentID.substring(0, 3);
		String city=appointmentID.substring(0, 3);
		switch(city) {
			//case "MTL": 
			case "MTL":
			{
				String valid=cancelValid(appointmentType,patientID,task, appointmentID,clientID,MTLMap);
				String response=(valid.equalsIgnoreCase("true"))?"Success":"Failed";
	        	writeTxtServerMTL(clientID,patientID,appointmentType,appointmentID,"cancel Appointment", response);
				if(!valid.equalsIgnoreCase("true"))
					return valid;
					//operations(clientID);
				else {
					MTLMap.get(appointmentType).get(appointmentID).remove(patientID);
					int remainingCapacity=maxCapacity-MTLMap.get(appointmentType).get(appointmentID).size()+2;
					String remainingCapacityStr=remainingCapacity+"";
					MTLMap.get(appointmentType).get(appointmentID).set(0, remainingCapacityStr);
				}
					
			}break;
			case "QUE": 
			{
				String valid=cancelValid(appointmentType,patientID,task, appointmentID,clientID,otherMap1);
				String response=(valid.equalsIgnoreCase("true"))?"Success":"Failed";
	        	writeTxtServerQUE(clientID,patientID,appointmentType,appointmentID,"cancel Appointment", response);
				if(!valid.equalsIgnoreCase("true"))
					return valid;
					//operations(clientID);
				else {
					otherMap1.get(appointmentType).get(appointmentID).remove(patientID);
					int remainingCapacity=maxCapacity-otherMap1.get(appointmentType).get(appointmentID).size()+2;
					String remainingCapacityStr=remainingCapacity+"";
					otherMap1.get(appointmentType).get(appointmentID).set(0, remainingCapacityStr);
					
					Message msg7 = new Message(otherMap1);
					os.writeObject(msg7);
					data = outputStream.toByteArray();
					sendPacket = new DatagramPacket(data, data.length, IPAddress, 9876);
					SocketCli.send(sendPacket);		
				}
					
			}break;
			case "SHE": 
			{
				String valid=cancelValid(appointmentType,patientID,task, appointmentID,clientID,otherMap2);
				String response=(valid.equalsIgnoreCase("true"))?"Success":"Failed";
	        	writeTxtServerSHE(clientID,patientID,appointmentType,appointmentID,"cancel Appointment", response);
				if(!valid.equalsIgnoreCase("true"))
					return valid;
					//operations(clientID);
				else {
					otherMap2.get(appointmentType).get(appointmentID).remove(patientID);
					int remainingCapacity=maxCapacity-otherMap2.get(appointmentType).get(appointmentID).size()+2;
					String remainingCapacityStr=remainingCapacity+"";
					otherMap2.get(appointmentType).get(appointmentID).set(0, remainingCapacityStr);
					Message msg8 = new Message(otherMap2);
					os2.writeObject(msg8);
					data2 = outputStream2.toByteArray();
					sendPacket2 = new DatagramPacket(data2, data2.length, IPAddress2, 9875);
					SocketCli2.send(sendPacket2);	
				}					
			}break;
		}
		return "Successfully cancelled";
	}
	public synchronized String cancelValid(String appointmentType,String patientID,String task, String appointmentID,String clientID,Map<String, Map<String,ArrayList<String>>> theMap) {
		boolean contained=theMap.get(appointmentType).get(appointmentID).contains(patientID);
		if(contained) {
			return "true";
		}else {
			//System.out.println("You cannot cancel this appointment because the appointment was not booked by this patient.");
			return "You cannot cancel this appointment because the appointment was not booked by this patient.";
		}
	}
	public synchronized String addValidation(String appointmentTypeOld,String appointmentIDOld,String appointmentType,String patientID,String task, String appointmentID,String clientID,
			Map<String, Map<String,ArrayList<String>>> otherMapA,Map<String, Map<String,ArrayList<String>>> otherMapB,
			Map<String, Map<String,ArrayList<String>>> otherMapC) {
		String oldCity=appointmentIDOld.substring(0, 3);
		String newCity=appointmentID.substring(0, 3);
		if(appointmentTypeOld.equalsIgnoreCase(appointmentType)&&oldCity.equalsIgnoreCase(newCity)) {
			return "true";
		}
		else {
			//a patient cannot book more than one appointment with the same appointment id and same appointment type
			String capacityStr1=otherMapA.get(appointmentType).get(appointmentID).get(0);
			int capacityInt1=Integer.parseInt(capacityStr1);
			boolean capacityOk1=capacityInt1>0?true:false;
			boolean contain1=otherMapA.get(appointmentType).get(appointmentID).contains(patientID);
			boolean success1=capacityOk1&&!contain1;
			if(contain1) {
				//System.out.println("You cannot book more than one appointment with the same appointment id and same appointment type.");
				return "You cannot book more than one appointment with the same appointment id and same appointment type.";
			}else if(!capacityOk1) {
				//System.out.println("You cannot book this appointment because it is full.");
				return "You cannot book this appointment because it is full.";
			}
			
			//a patient cannot have more than one booking of same appointment type in a day.
			boolean contain2=false;
			String checkDate=appointmentID.substring(4);
			String orderedCheckDate=checkDate.substring(4)+checkDate.substring(2, 4)+checkDate.substring(0, 2);
			int convertOrderedCheckDate=Integer.parseInt(orderedCheckDate);
			String date="";
			String orderedDate="";
			int convertOrderedDate=0;
			String capacityStr2="";
			int capacityInt2=0;
			for(Map.Entry<String, ArrayList<String>> nestedMap:otherMapA.get(appointmentType).entrySet()) {
				String capacityStrTemp=otherMapA.get(appointmentType).get(appointmentID).get(0);
				capacityInt2=capacityInt2+Integer.parseInt(capacityStrTemp);			
				date=nestedMap.getKey().substring(4);
				orderedDate=date.substring(4)+date.substring(2, 4)+date.substring(0, 2);
				convertOrderedDate=Integer.parseInt(orderedDate);
				if(convertOrderedDate==convertOrderedCheckDate) {
					contain2=nestedMap.getValue().contains(patientID);
					if(contain2==true)
						break;
				}
			}
			boolean capacityOk2=capacityInt2>0?true:false;		
			boolean success2=capacityOk2&&!contain2;
			if(contain2) {
				//System.out.println("cannot have more than one booking of same appointment type in a day.");
				return "cannot have more than one booking of same appointment type in a day.";
			}else if(!capacityOk2) {
				System.out.println("You cannot book this appointment because it is full.");
				return "You cannot book this appointment because it is full.";
			}
			//a patient can only book at most 3 appointments from other cities.
			boolean success3=false;
			String cityToBook=appointmentID.substring(0, 3);
			if(!cityToBook.equalsIgnoreCase("MTL")) {
				
			int count1=0;
			String dateToCheck1="";
			String weekCheck1="0";
			for(Map.Entry<String, Map<String,ArrayList<String>>> map:otherMapB.entrySet())
			{
				for(Map.Entry<String, ArrayList<String>> nestedMap:map.getValue().entrySet())
				{
					if(nestedMap.getValue().contains(patientID))
					{
						if(count1==0) {
							dateToCheck1=nestedMap.getKey();
							weekCheck1=nestedMap.getValue().get(1);
						    count1++;
						}else {
							String dateToCheck11=nestedMap.getKey();
							String subdateToCheck1=dateToCheck1.substring(6);
							String subdateToCheck11=dateToCheck11.substring(6);
							if(weekCheck1.equalsIgnoreCase(nestedMap.getValue().get(1))&&subdateToCheck1.equalsIgnoreCase(subdateToCheck11))
								count1++;
						}					   
					}					
				}
			}
			int count2=0;
			String dateToCheck2="";
			String weekCheck2="0";
			for(Map.Entry<String, Map<String,ArrayList<String>>> map:otherMapA.entrySet())
			{
				for(Map.Entry<String, ArrayList<String>> nestedMap:map.getValue().entrySet())
				{
					if(nestedMap.getValue().contains(patientID))
					{
						if(count2==0) {
							dateToCheck2=nestedMap.getKey();
							weekCheck2=nestedMap.getValue().get(1);
						    count2++;
						}else {
							String dateToCheck22=nestedMap.getKey();
							String subdateToCheck2=dateToCheck2.substring(6);
							String subdateToCheck22=dateToCheck22.substring(6);
							if(weekCheck1.equalsIgnoreCase(nestedMap.getValue().get(1))&&subdateToCheck2.equalsIgnoreCase(subdateToCheck22))
								count2++;
						}					   
					}					
				}
			}
			int count=count1+count2;
			success3=count<3?true:false;
			if(!success3) {
				//System.out.println("You cannot book more than 3 appointments from other cities.");
				return "You cannot book more than 3 appointments from other cities.";
				}
			}
			if(cityToBook.equalsIgnoreCase("MTL")) {
				if(success1&&success2)
					return "true";
				else
					return "false";
			}else {
				if(success1&&success2&&success3)
					return "true";
				else
					return "false";
			}
		}
	}
	public synchronized String swapCheck(String appointmentType,String patientID,String task, String appointmentID,String clientID,String newAppointmentID,String newAppointmentType) {

		String cityOld=appointmentID.substring(0, 3);
		String validRemove="";
		String result="";
		switch(cityOld) {
			case "MTL": 
			{
				cityForRemove="MTL";
				validRemove=cancelValid(appointmentType,patientID,task, appointmentID,clientID,MTLMap);				
			}break;
			case "QUE": 
			{
				cityForRemove="QUE";
				validRemove=cancelValid(appointmentType,patientID,task, appointmentID,clientID,otherMap1);	
					
			}break;
			case "SHE": 
			{
				cityForRemove="SHE";
				validRemove=cancelValid(appointmentType,patientID,task, appointmentID,clientID,otherMap2);			
			}break;
		}
		if(!validRemove.equalsIgnoreCase("true")) {
			result="The old one appointment cannot be removed.";
		}

		String cityNew=newAppointmentID.substring(0, 3);
		String validAdd="";
		switch(cityNew) {
		case "MTL": 
		{
			cityForAdd="MTL";
			//validAdd=validation(newAppointmentType,patientID,task, newAppointmentID,clientID,MTLMap,otherMap1,otherMap2);
			validAdd=addValidation(appointmentType,appointmentID,newAppointmentType,patientID,task, newAppointmentID,clientID,MTLMap,otherMap1,otherMap2);
				
		}break;
		case "QUE": 
		{
			cityForAdd="QUE";
			//validAdd=validation(newAppointmentType,patientID,task, newAppointmentID,clientID,otherMap1,otherMap2,MTLMap);
			validAdd=addValidation(appointmentType,appointmentID,newAppointmentType,patientID,task, newAppointmentID,clientID,otherMap1,otherMap2,MTLMap);
				
		}break;
		case "SHE": 
		{
			cityForAdd="SHE";
			//validAdd=validation(newAppointmentType,patientID,task, newAppointmentID,clientID,otherMap2,otherMap1,MTLMap);
			validAdd=addValidation(appointmentType,appointmentID,newAppointmentType,patientID,task, newAppointmentID,clientID,otherMap2,otherMap1,MTLMap);
		}break;
		
		}
		if(!validAdd.equalsIgnoreCase("true")) {
			result="The new one appointment cannot be added.";
		}
		if(validRemove.equalsIgnoreCase("true")&&validAdd.equalsIgnoreCase("true"))
			result="Successfully swapped";
		return result;
	}
	public synchronized boolean swapOperation(String appointmentType,String patientID,String task, String appointmentID,String clientID,String newAppointmentID, String newAppointmentType,
			String cityForRemove, String cityForAdd) throws IOException
	{
	
		switch(cityForAdd) {
			case "MTL": 
			{
				//city="MTL";
		
				MTLMap.get(newAppointmentType).get(newAppointmentID).add(patientID);
				String changeCapacityStr=MTLMap.get(newAppointmentType).get(newAppointmentID).get(0);
				int changeCapacityInt=Integer.parseInt(changeCapacityStr);
				int changed=changeCapacityInt-1;
				String changedStr=changed+"";
				MTLMap.get(newAppointmentType).get(newAppointmentID).set(0, changedStr);
				
					
			}break;
			case "QUE": 
			{
				//city="QUE";
			
				otherMap1.get(newAppointmentType).get(newAppointmentID).add(patientID);
				String changeCapacityStr=otherMap1.get(newAppointmentType).get(newAppointmentID).get(0);
				int changeCapacityInt=Integer.parseInt(changeCapacityStr);
				int changed=changeCapacityInt-1;
				String changedStr=changed+"";
				otherMap1.get(newAppointmentType).get(newAppointmentID).set(0, changedStr);
		
				Message msg5 = new Message(otherMap1);
				os.writeObject(msg5);
				data = outputStream.toByteArray();
				sendPacket = new DatagramPacket(data, data.length, IPAddress, 9876);
				SocketCli.send(sendPacket);
				
					
			}break;
			case "SHE": 
			{
				//city="SHE";
			
				otherMap2.get(newAppointmentType).get(newAppointmentID).add(patientID);
				String changeCapacityStr=otherMap2.get(newAppointmentType).get(newAppointmentID).get(0);
				int changeCapacityInt=Integer.parseInt(changeCapacityStr);
				int changed=changeCapacityInt-1;
				String changedStr=changed+"";
				otherMap2.get(newAppointmentType).get(newAppointmentID).set(0, changedStr);

				Message msg6 = new Message(otherMap2);

				os2.writeObject(msg6);
				data2 = outputStream2.toByteArray();
				sendPacket2 = new DatagramPacket(data2, data2.length, IPAddress2, 9875);
				SocketCli2.send(sendPacket2);					
				
			}break;
		}
		switch(cityForRemove) {

			case "MTL":
			{
				MTLMap.get(appointmentType).get(appointmentID).remove(patientID);
				int remainingCapacity=maxCapacity-MTLMap.get(appointmentType).get(appointmentID).size()+2;
				String remainingCapacityStr=remainingCapacity+"";
				MTLMap.get(appointmentType).get(appointmentID).set(0, remainingCapacityStr);								
			}break;
			case "QUE": 
			{
				otherMap1.get(appointmentType).get(appointmentID).remove(patientID);
				int remainingCapacity=maxCapacity-otherMap1.get(appointmentType).get(appointmentID).size()+2;
				String remainingCapacityStr=remainingCapacity+"";
				otherMap1.get(appointmentType).get(appointmentID).set(0, remainingCapacityStr);
				
				Message msg7 = new Message(otherMap1);
				os.writeObject(msg7);
				data = outputStream.toByteArray();
				sendPacket = new DatagramPacket(data, data.length, IPAddress, 9876);
				SocketCli.send(sendPacket);								
			}break;
			case "SHE": 
			{
				otherMap2.get(appointmentType).get(appointmentID).remove(patientID);
				int remainingCapacity=maxCapacity-otherMap2.get(appointmentType).get(appointmentID).size()+2;
				String remainingCapacityStr=remainingCapacity+"";
				otherMap2.get(appointmentType).get(appointmentID).set(0, remainingCapacityStr);
				Message msg8 = new Message(otherMap2);
				os2.writeObject(msg8);
				data2 = outputStream2.toByteArray();
				sendPacket2 = new DatagramPacket(data2, data2.length, IPAddress2, 9875);
				SocketCli2.send(sendPacket2);														
			}break;
		}
		
		return true;
	}
}
