package Client;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//import ServerInterface.ServerInterface;

public class PatientClient extends Client implements Runnable{
	static Map<Integer, String> patientMap = new HashMap<>();
	public PatientClient()
	{
		patientMap.put(1,"MTLP0001");patientMap.put(2,"MTLP0002");patientMap.put(3,"MTLP0003");patientMap.put(4,"MTLP0004");patientMap.put(5,"MTLP0005");	
		patientMap.put(6,"MTLP0006");patientMap.put(7,"MTLP0007");patientMap.put(8,"MTLP0008");patientMap.put(9,"MTLP0009");patientMap.put(10,"MTLP0010");	
		
		patientMap.put(11,"QUEP0001");patientMap.put(12,"QUEP0002");patientMap.put(13,"QUEP0003");patientMap.put(14,"QUEP0004");patientMap.put(15,"QUEP0005");	
		patientMap.put(16,"QUEP0006");patientMap.put(17,"QUEP0007");patientMap.put(18,"QUEP0008");patientMap.put(19,"QUEP0009");patientMap.put(10,"QUEP0020");
		
		patientMap.put(21,"SHEP0001");patientMap.put(22,"SHEP0002");patientMap.put(23,"SHEP0003");patientMap.put(24,"SHEP0004");patientMap.put(25,"SHEP0005");	
		patientMap.put(26,"SHEP0006");patientMap.put(27,"SHEP0007");patientMap.put(28,"SHEP0008");patientMap.put(29,"SHEP0009");patientMap.put(20,"SHEP0010");	

	}
	public Map<Integer, String> getPatientMap()
	{
		return patientMap;
	}
	public void addPatientMap(String clientId)
	{
		int index=patientMap.size()+1;
		patientMap.put(index, clientId);
	}
	public void patientStart(String clientID) throws Exception
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
					+ "\n1. Exit\n2. Book appointment;\n3. Get appointment Schedule;\n4. Cancel appointment;\n5. Swap appointment;");
				
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

				String appointmentID=setAppointmentID();
				String appointmentType=setAppointmentType();	
				if(clientID.contains("MTL"))
				{
					String result=MTLobj.bookAppointment(clientID,clientID, appointmentID, appointmentType);
					String resultStr=(result.contains("Congratulations"))?"Success":"Failed";
					System.out.println(result);
					MTLobj.writeTxtClient(clientID,"book Appointment", resultStr);
					MTLobj.writeTxtServerMTL(clientID,clientID,appointmentType,appointmentID,"book Appointment", resultStr);
				}
				else if(clientID.contains("QUE"))
				{
					String result=QUEobj.bookAppointment(clientID,clientID, appointmentID, appointmentType);
					String resultStr=(result.contains("Congratulations"))?"Success":"Failed";
					System.out.println(result);
					QUEobj.writeTxtClient(clientID,"book Appointment", resultStr);
					QUEobj.writeTxtServerQUE(clientID,clientID,appointmentType,appointmentID,"book Appointment", resultStr);
				}
				else if(clientID.contains("SHE"))
				{
					String result=SHEobj.bookAppointment(clientID,clientID, appointmentID, appointmentType);
					String resultStr=(result.contains("Congratulations"))?"Success":"Failed";
					System.out.println(result);
					SHEobj.writeTxtClient(clientID,"book Appointment", resultStr);
					SHEobj.writeTxtServerSHE(clientID,clientID,appointmentType,appointmentID,"book Appointment", resultStr);
				}
				
				operations(clientID);
				
				
			}break;
			case 3:
			{
				if(clientID.contains("MTL"))
				{
					String result=MTLobj.getAppointmentSchedule(clientID);
					String resultStr=(result.contains("true"))?"Success":"Failed";
					System.out.println("Successfully get the appointment for "+patientID+"\n"+result);
					MTLobj.writeTxtClient(clientID,"get Appointment Schedule", resultStr);
					MTLobj.writeTxtServerMTL(clientID,clientID,"-","-","get Appointment Schedule", resultStr);
				}
				else if(clientID.contains("QUE"))
				{
					String result=QUEobj.getAppointmentSchedule(clientID);
					String resultStr=(result.contains("true"))?"Success":"Failed";
					System.out.println("Successfully get the appointment for "+patientID+"\n"+result);
					QUEobj.writeTxtClient(clientID,"get Appointment Schedule", resultStr);
					QUEobj.writeTxtServerQUE(clientID,clientID,"-","-","get Appointment Schedule", resultStr);
				}
				else if(clientID.contains("SHE"))
				{
					String result=SHEobj.getAppointmentSchedule(clientID);
					String resultStr=(result.contains("true"))?"Success":"Failed";
					System.out.println("Successfully get the appointment for "+patientID+"\n"+result);
					SHEobj.writeTxtClient(clientID,"get Appointment Schedule", resultStr);
					SHEobj.writeTxtServerSHE(clientID,clientID,"-","-","get Appointment Schedule", resultStr);
				}
				
				operations(clientID);
								
			}break;
			case 4:
			{
					String appointmentID=setAppointmentID();
					String appointmentType=setAppointmentType();
					if(clientID.contains("MTL"))
					{
						String result=MTLobj.cancelAppointment(clientID, clientID,appointmentID, appointmentType);
						String resultStr=(result.contains("Successfully cancelled"))?"Success":"Failed";
						System.out.println(result);
						MTLobj.writeTxtClient(clientID,"cancel Appointment", resultStr);
						MTLobj.writeTxtServerMTL(clientID,clientID,"-","-","cancel Appointment", resultStr);
					}
					else if(clientID.contains("QUE"))
					{
						String result=QUEobj.cancelAppointment(clientID, clientID,appointmentID, appointmentType);
						String resultStr=(result.contains("Successfully cancelled"))?"Success":"Failed";
						System.out.println(result);
						QUEobj.writeTxtClient(clientID,"cancel Appointment", resultStr);
						QUEobj.writeTxtServerQUE(clientID,clientID,"-","-","cancel Appointment", resultStr);
					}
					else if(clientID.contains("SHE"))
					{
						String result=SHEobj.cancelAppointment(clientID, clientID,appointmentID, appointmentType);
						String resultStr=(result.contains("Successfully cancelled"))?"Success":"Failed";
						System.out.println(result);
						SHEobj.writeTxtClient(clientID,"cancel Appointment", resultStr);
						SHEobj.writeTxtServerSHE(clientID,clientID,"-","-","cancel Appointment", resultStr);
					}
					
					operations(clientID);
								
			}break;
			case 5:
			{
				System.out.println("Please type the old appointment ID and type:");
				String oldAppointmentID=setAppointmentID();
				String oldAppointmentType=setAppointmentType();
				System.out.println("Please type the new appointment ID and type:");
				String newAppointmentID=setAppointmentID();
				String newAppointmentType=setAppointmentType();
				if(clientID.contains("MTL"))
				{
					String resultSwap=MTLobj.swapAppointment(clientID, clientID,oldAppointmentID, oldAppointmentType,newAppointmentID, newAppointmentType);
					String resultStr=(resultSwap.contains("Successfully swapped"))?"Success":"Failed";
					System.out.println(resultSwap);
					MTLobj.writeTxtClient(clientID,"swap Appointment", resultStr);
					MTLobj.writeTxtServerMTL(clientID,clientID,"-","-","swap Appointment", resultStr);
				}
				else if(clientID.contains("QUE"))
				{
					String resultSwap=QUEobj.swapAppointment(clientID, clientID,oldAppointmentID, oldAppointmentType,newAppointmentID, newAppointmentType);
					String resultStr=(resultSwap.contains("Successfully swapped"))?"Success":"Failed";
					System.out.println(resultSwap);
					QUEobj.writeTxtClient(clientID,"swap Appointment", resultStr);
					QUEobj.writeTxtServerQUE(clientID,clientID,"-","-","swap Appointment", resultStr);
				}
				else if(clientID.contains("SHE"))
				{
					String resultSwap=SHEobj.swapAppointment(clientID, clientID,oldAppointmentID, oldAppointmentType,newAppointmentID, newAppointmentType);
					String resultStr=(resultSwap.contains("Successfully swapped"))?"Success":"Failed";
					System.out.println(resultSwap);
					SHEobj.writeTxtClient(clientID,"swap Appointment", resultStr);
					SHEobj.writeTxtServerSHE(clientID,clientID,"-","-","swap Appointment", resultStr);
				}
				
				operations(clientID);	
				
			}break;			
		}
	}
	public void outputClientInfo()
	{
		patientMap.entrySet().forEach(entry->{System.out.print(" "+entry.getValue());});
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
