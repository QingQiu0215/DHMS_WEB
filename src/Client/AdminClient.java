package Client;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//import ServerInterface.ServerInterface;

public class AdminClient extends Client implements Runnable{
	static Map<Integer, String> adminMap = new HashMap<>();
	
	public AdminClient()
	{
		adminMap.put(1,"MTLA0001");adminMap.put(2,"QUEA0001");adminMap.put(3,"SHEA0001");
	}
	public Map<Integer, String> getAdminMap()
	{
		return adminMap;
	}
	public void addAdminMap(String id)
	{
		adminMap.put(adminMap.size()+1, id);
	}
	public void adminStart(String clientID) throws Exception
	{		
		operations(clientID);

	}
	public void operations(String clientID)
	{
		System.out.println();
		if(clientID.contains("MTL"))
		{
			System.out.println("*** Welcome to MTL Hospital ***");
		}
		else if(clientID.contains("QUE"))
		{
			System.out.println("*** Welcome to QUE Hospital ***");
		}
		else if(clientID.contains("SHE"))
		{
			System.out.println("*** Welcome to SHE Hospital ***");
		}
		

		System.out.println("Choose one of the following options:"
			+ "\n1. Exit\n2. Book appointment;\n3. Get appointment Schedule;\n4. Cancel appointment;\n5. Swap appointment;"
			+"\n6. Add appointment;\n7. Remove appointment;\n8. List appointment availability;");
		
		int option=0;
		Scanner keyboard=new Scanner(System.in);
		option=keyboard.nextInt();
		try {
			try {
				selectOperations(option,clientID);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void selectOperations(int option, String clientID) throws ClassNotFoundException, IOException
	{
		char clientType=clientID.charAt(3);
		switch(option) 
		{
			case 1:
				return;
			case 2:
			{
				if(clientType=='A')
				{
					String patientID=super.setPatientID();
					String appointmentID=super.setAppointmentID();
					String appointmentType=super.setAppointmentType();	
					if(clientID.contains("MTL"))
					{
						String result=MTLobj.bookAppointment(clientID,patientID, appointmentID, appointmentType);
						String resultStr=(result.contains("Congratulations"))?"Success":"Failed";
						System.out.println(result);
						MTLobj.writeTxtClient(clientID,"book Appointment", resultStr);
						MTLobj.writeTxtServerMTL(clientID,patientID,appointmentType,appointmentID,"book Appointment", resultStr);
					}
					else if(clientID.contains("QUE"))
					{
						String result=MTLobj.bookAppointment(clientID,patientID, appointmentID, appointmentType);
						String resultStr=(result.contains("Congratulations"))?"Success":"Failed";
						System.out.println(result);
						QUEobj.writeTxtClient(clientID,"book Appointment", resultStr);
						QUEobj.writeTxtServerQUE(clientID,patientID,appointmentType,appointmentID,"book Appointment", resultStr);
					}
					else if(clientID.contains("SHE"))
					{
						String result=MTLobj.bookAppointment(clientID,patientID, appointmentID, appointmentType);
						String resultStr=(result.contains("Congratulations"))?"Success":"Failed";
						System.out.println(result);
						SHEobj.writeTxtClient(clientID,"book Appointment", resultStr);
						SHEobj.writeTxtServerSHE(clientID,patientID,appointmentType,appointmentID,"book Appointment", resultStr);
					}
					
					operations(clientID);
				}
				
			}break;
			case 3:
			{
				if(clientType=='A') {
					String patientID=setPatientID();
					if(clientID.contains("MTL"))
					{
						String result=MTLobj.getAppointmentSchedule(patientID);
						String resultStr=(result.contains("true"))?"Success":"Failed";
						System.out.println("Successfully get the appointment for "+patientID+"\n"+result);
						MTLobj.writeTxtClient(clientID,"get Appointment Schedule", resultStr);
						MTLobj.writeTxtServerMTL(clientID,patientID,"-","-","get Appointment Schedule", resultStr);
					}
					else if(clientID.contains("QUE"))
					{
						String result=QUEobj.getAppointmentSchedule(patientID);
						String resultStr=(result.contains("true"))?"Success":"Failed";
						System.out.println("Successfully get the appointment for "+patientID+"\n"+result);
						QUEobj.writeTxtClient(clientID,"get Appointment Schedule", resultStr);
						QUEobj.writeTxtServerQUE(clientID,patientID,"-","-","get Appointment Schedule", resultStr);
					}
					else if(clientID.contains("SHE"))
					{
						String result=SHEobj.getAppointmentSchedule(patientID);
						String resultStr=(result.contains("true"))?"Success":"Failed";
						System.out.println("Successfully get the appointment for "+patientID+"\n"+result);
						SHEobj.writeTxtClient(clientID,"get Appointment Schedule", resultStr);
						SHEobj.writeTxtServerSHE(clientID,patientID,"-","-","get Appointment Schedule", resultStr);
					}
					
					operations(clientID);
				}
								
			}break;
			case 4:
			{
				if(clientType=='A') {
					String patientID=setPatientID();
					String appointmentID=setAppointmentID();
					String appointmentType=setAppointmentType();
					if(clientID.contains("MTL"))
					{
						String result=MTLobj.cancelAppointment(clientID, patientID,appointmentID, appointmentType);
						String resultStr=(result.contains("Successfully cancelled"))?"Success":"Failed";
						System.out.println(result);
						MTLobj.writeTxtClient(clientID,"cancel Appointment", resultStr);
						MTLobj.writeTxtServerMTL(clientID,patientID,"-","-","cancel Appointment", resultStr);
					}
					else if(clientID.contains("QUE"))
					{
						String result=QUEobj.cancelAppointment(clientID, patientID,appointmentID, appointmentType);
						String resultStr=(result.contains("Successfully cancelled"))?"Success":"Failed";
						System.out.println(result);
						QUEobj.writeTxtClient(clientID,"cancel Appointment", resultStr);
						QUEobj.writeTxtServerQUE(clientID,patientID,"-","-","cancel Appointment", resultStr);
					}
					else if(clientID.contains("SHE"))
					{
						String result=SHEobj.cancelAppointment(clientID, patientID,appointmentID, appointmentType);
						String resultStr=(result.contains("Successfully cancelled"))?"Success":"Failed";
						System.out.println(result);
						SHEobj.writeTxtClient(clientID,"cancel Appointment", resultStr);
						SHEobj.writeTxtServerSHE(clientID,patientID,"-","-","cancel Appointment", resultStr);
					}
					
					operations(clientID);
				}				
			}break;
			case 5:
			{
				if(clientType=='A') {
					String patientID=setPatientID();
					System.out.println("Please type the old appointment ID and type:");
					String oldAppointmentID=setAppointmentID();
					String oldAppointmentType=setAppointmentType();
					System.out.println("Please type the new appointment ID and type:");
					String newAppointmentID=setAppointmentID();
					String newAppointmentType=setAppointmentType();
					if(clientID.contains("MTL"))
					{
						String resultSwap=MTLobj.swapAppointment(clientID, patientID,oldAppointmentID, oldAppointmentType,newAppointmentID, newAppointmentType);
						String resultStr=(resultSwap.contains("Successfully swapped"))?"Success":"Failed";
						System.out.println(resultSwap);
						MTLobj.writeTxtClient(clientID,"swap Appointment", resultStr);
						MTLobj.writeTxtServerMTL(clientID,patientID,"-","-","swap Appointment", resultStr);
					}
					else if(clientID.contains("QUE"))
					{
						String resultSwap=QUEobj.swapAppointment(clientID, patientID,oldAppointmentID, oldAppointmentType,newAppointmentID, newAppointmentType);
						String resultStr=(resultSwap.contains("Successfully swapped"))?"Success":"Failed";
						System.out.println(resultSwap);
						QUEobj.writeTxtClient(clientID,"swap Appointment", resultStr);
						QUEobj.writeTxtServerQUE(clientID,patientID,"-","-","swap Appointment", resultStr);
					}
					else if(clientID.contains("SHE"))
					{
						String resultSwap=SHEobj.swapAppointment(clientID, patientID,oldAppointmentID, oldAppointmentType,newAppointmentID, newAppointmentType);
						String resultStr=(resultSwap.contains("Successfully swapped"))?"Success":"Failed";
						System.out.println(resultSwap);
						SHEobj.writeTxtClient(clientID,"swap Appointment", resultStr);
						SHEobj.writeTxtServerSHE(clientID,patientID,"-","-","swap Appointment", resultStr);
					}
					
					operations(clientID);					
					
				}
			}break;
			case 6:
			{
				System.out.println("You are trying to add an appointment.");
				boolean doLoop=false;
				String appointmentID="";
				String appointmentType="";
				int appointmentWeekInt=0;
				String appointmentWeekStr="";
				int capacity=0;
				capacity=setCapacity();
				String capacityStr=capacity+"";
				
				if(clientID.contains("MTL"))
				{
					do {
						appointmentID=setAppointmentID();
						appointmentType=setAppointmentType();
						appointmentWeekInt=setAppointmentWeek();
						appointmentWeekStr=appointmentWeekInt+"";
						if(MTLobj.checkAppointmentExisted(appointmentID,appointmentType)) {
							System.out.println("The appointment you entered exists in MTL Database. Please enter another one.");
							doLoop=true;
						}else {
							doLoop=false;
						}
					}while(doLoop);
					String result=MTLobj.addAppointment(appointmentID,appointmentType,capacityStr,appointmentWeekStr);
					String resultStr=(MTLobj.checkAppointmentExisted(appointmentID,appointmentType)==true)?"Success":"Failed";
					System.out.println(result);
					MTLobj.writeTxtClient(clientID,"add Appointment", resultStr);
					MTLobj.writeTxtServerMTL(clientID,clientID,"-","-","add Appointment", resultStr);
				}
				else if(clientID.contains("QUE"))
				{
					do {
						appointmentID=setAppointmentID();
						appointmentType=setAppointmentType();
						appointmentWeekInt=setAppointmentWeek();
						appointmentWeekStr=appointmentWeekInt+"";
						if(QUEobj.checkAppointmentExisted(appointmentID,appointmentType)) {
							System.out.println("The appointment you entered exists in MTL Database. Please enter another one.");
							doLoop=true;
						}else {
							doLoop=false;
						}
					}while(doLoop);
					String result=QUEobj.addAppointment(appointmentID,appointmentType,capacityStr,appointmentWeekStr);
					String resultStr=(QUEobj.checkAppointmentExisted(appointmentID,appointmentType)==true)?"Success":"Failed";
					System.out.println(result);
					QUEobj.writeTxtClient(clientID,"add Appointment", resultStr);
					QUEobj.writeTxtServerQUE(clientID,clientID,"-","-","add Appointment", resultStr);
				}
				else if(clientID.contains("SHE"))
				{
					do {
						appointmentID=setAppointmentID();
						appointmentType=setAppointmentType();
						appointmentWeekInt=setAppointmentWeek();
						appointmentWeekStr=appointmentWeekInt+"";
						if(SHEobj.checkAppointmentExisted(appointmentID,appointmentType)) {
							System.out.println("The appointment you entered exists in MTL Database. Please enter another one.");
							doLoop=true;
						}else {
							doLoop=false;
						}
					}while(doLoop);
					String result=SHEobj.addAppointment(appointmentID,appointmentType,capacityStr,appointmentWeekStr);
					String resultStr=(SHEobj.checkAppointmentExisted(appointmentID,appointmentType)==true)?"Success":"Failed";
					System.out.println(result);
					SHEobj.writeTxtClient(clientID,"add Appointment", resultStr);
					SHEobj.writeTxtServerSHE(clientID,clientID,"-","-","add Appointment", resultStr);
				}
				
				operations(clientID);
			}break;
			case 7:
			{
				System.out.println("You are trying to remove an appointment.");
				boolean doLoop=false;
				String appointmentID="";
				String appointmentType="";
				
				if(clientID.contains("MTL"))
				{
					do {
						appointmentID=setAppointmentID();
						appointmentType=setAppointmentType();
						if(!MTLobj.checkAppointmentExisted(appointmentID,appointmentType)) {
							System.out.println("The appointment you entered does not exist in MTL Database. Please enter another one.");
							MTLobj.writeTxtServerMTL(clientID,clientID,"-","-","remove Appointment", "Failed");
							doLoop=true;
						}else {
							doLoop=false;
						}
					}while(doLoop);
					String result=MTLobj.removeAppointment(appointmentID,appointmentType);
					String resultStr=(result.equalsIgnoreCase("Successfully removed"))?"Success":"Failed";
					System.out.println(result);
					MTLobj.writeTxtClient(clientID,"remove Appointment", resultStr);
					MTLobj.writeTxtServerMTL(clientID,clientID,"-","-","remove Appointment", resultStr);
				}
				else if(clientID.contains("QUE"))
				{
					do {
						appointmentID=setAppointmentID();
						appointmentType=setAppointmentType();
						if(!QUEobj.checkAppointmentExisted(appointmentID,appointmentType)) {
							System.out.println("The appointment you entered does not exist in MTL Database. Please enter another one.");
							QUEobj.writeTxtServerQUE(clientID,clientID,"-","-","remove Appointment", "Failed");
							doLoop=true;
						}else {
							doLoop=false;
						}
					}while(doLoop);
					String result=QUEobj.removeAppointment(appointmentID,appointmentType);
					String resultStr=(result.equalsIgnoreCase("Successfully removed"))?"Success":"Failed";
					System.out.println(result);
					QUEobj.writeTxtClient(clientID,"remove Appointment", resultStr);
					QUEobj.writeTxtServerMTL(clientID,clientID,"-","-","remove Appointment", resultStr);
				}
				else if(clientID.contains("SHE"))
				{
					do {
						appointmentID=setAppointmentID();
						appointmentType=setAppointmentType();
						if(!SHEobj.checkAppointmentExisted(appointmentID,appointmentType)) {
							System.out.println("The appointment you entered does not exist in MTL Database. Please enter another one.");
							SHEobj.writeTxtServerSHE(clientID,clientID,"-","-","remove Appointment", "Failed");
							doLoop=true;
						}else {
							doLoop=false;
						}
					}while(doLoop);
					String result=SHEobj.removeAppointment(appointmentID,appointmentType);
					String resultStr=(result.equalsIgnoreCase("Successfully removed"))?"Success":"Failed";
					System.out.println(result);
					SHEobj.writeTxtClient(clientID,"remove Appointment", resultStr);
					SHEobj.writeTxtServerMTL(clientID,clientID,"-","-","remove Appointment", resultStr);
				}
				
				operations(clientID);
			}break;
			case 8:
			{
				String appointmentType=setAppointmentType();
				if(clientID.contains("MTL"))
				{
					String result=MTLobj.listAppointmentAvailability(appointmentType);
					String resultStr=(result.contains("Successfully list"))?"Success":"Failed";
					System.out.println(result);
					MTLobj.writeTxtClient(clientID,"list Appointment Availability", resultStr);
					MTLobj.writeTxtServerMTL(clientID,clientID,"-","-","list Appointment Availability", resultStr);
				}
				else if(clientID.contains("QUE"))
				{
					String result=QUEobj.listAppointmentAvailability(appointmentType);
					String resultStr=(result.contains("Successfully list"))?"Success":"Failed";
					System.out.println(result);
					QUEobj.writeTxtClient(clientID,"list Appointment Availability", resultStr);
					QUEobj.writeTxtServerQUE(clientID,clientID,"-","-","list Appointment Availability", resultStr);
				}
				else if(clientID.contains("SHE"))
				{
					String result=SHEobj.listAppointmentAvailability(appointmentType);
					String resultStr=(result.contains("Successfully list"))?"Success":"Failed";
					System.out.println(result);
					SHEobj.writeTxtClient(clientID,"list Appointment Availability", resultStr);
					SHEobj.writeTxtServerSHE(clientID,clientID,"-","-","list Appointment Availability", resultStr);
				}
				
				operations(clientID);
			}break;			
			
		}
			
	}
	
	public void outputClientInfo()
	{
		adminMap.entrySet().forEach(entry->{System.out.print(" "+entry.getValue());});
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
