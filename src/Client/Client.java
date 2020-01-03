/*
 * This is final version of DHMS_CORBA. 
 */

package Client;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.web.service.WebInterface;


public class Client extends Thread implements Runnable{
	static String clientID;
	static String patientID;
	static String oldAppointmentID;
	static String oldAppointmentType;
	static String newAppointmentID;
	static String newAppointmentType;
	
	public static Service MTLService;
	public static Service QUEService;
	public static Service SHEService;
	static WebInterface MTLobj;
	static WebInterface QUEobj;
	static WebInterface SHEobj;
	static boolean repeat=true;
	public Client() {
		
		URL mtlURL;
		URL queURL;
		URL sheURL;
		try {
			mtlURL = new URL("http://localhost:1010/MTL?wsdl");
			QName mtlQName = new QName("http://Server/", "MTLServerService");
			MTLService = Service.create(mtlURL, mtlQName);
			
			queURL = new URL("http://localhost:2020/QUE?wsdl");
			QName queQName = new QName("http://Server/", "QUEServerService");
			QUEService = Service.create(queURL, queQName);
			
			sheURL = new URL("http://localhost:3030/SHE?wsdl");
			QName sheQName = new QName("http://Server/", "SHEServerService");
			SHEService = Service.create(sheURL, sheQName);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MTLobj = MTLService.getPort(WebInterface.class);
		QUEobj = QUEService.getPort(WebInterface.class);
		SHEobj = SHEService.getPort(WebInterface.class);
		
	}
	public static void main(String args[]) throws Exception
	{
		Client client=new Client();
		AdminClient admin=new AdminClient();
		PatientClient patient=new PatientClient();
		Scanner keyboard=new Scanner(System.in);		
		System.out.println("*** Welcome to use DHMS ***");
		System.out.println("");
		System.out.println("The following are all Admin:");
		admin.outputClientInfo();
		System.out.println("");
		System.out.println("The following are all Patient:");
		patient.outputClientInfo();
		System.out.println("");
		System.out.println("\nAre you a new client? \n1.yes\n2.no");
		
		int choose=0;
		boolean boundOk=true;
		String clientID="";
		choose=keyboard.nextInt();

		while(boundOk) {
			if(choose==1){
				while(repeat) {
					System.out.println("Please sign up your ClientID:");
					Scanner keyboard2=new Scanner(System.in);
					String newClient=keyboard2.nextLine();
					if(newClient.charAt(3)=='A'){
						if(!admin.getAdminMap().containsValue(newClient))
							admin.addAdminMap(newClient);	
						
						admin.adminStart(newClient);
						System.out.println("Do you want to continue? yes/no");
						Scanner keyboard3=new Scanner(System.in);
						String continue3=keyboard3.nextLine();
						if(continue3.equalsIgnoreCase("yes"))
							repeat=true;
						else {
							System.out.println("Thanks for using DHMS.");
							repeat=false;
						}					
					}					
					else {
						if(!patient.getPatientMap().containsValue(newClient))
							patient.addPatientMap(newClient);
						
						patient.patientStart(newClient);
						System.out.println("Do you want to continue? yes/no");
						Scanner keyboard3=new Scanner(System.in);
						String continue3=keyboard3.nextLine();
						if(continue3.equalsIgnoreCase("yes"))
							repeat=true;
						else {
							System.out.println("Thanks for using DHMS.");
							repeat=false;
						}	
					}				
					boundOk=false;
				}
				
			}else if(choose==2){
				while(repeat) {
					System.out.println("Please enter your clientID:");
					Scanner keyboard4=new Scanner(System.in);
					clientID=keyboard4.nextLine();
					char type=clientID.charAt(3);		
					if(type=='A') {
						admin.adminStart(clientID);
						System.out.println("Do you want to continue? yes/no");
						Scanner keyboard5=new Scanner(System.in);
						String continue5=keyboard5.nextLine();
						if(continue5.equalsIgnoreCase("yes"))
							repeat=true;
						else {
							System.out.println("Thanks for using DHMS.");
							repeat=false;
						}
					}					
					else {
						patient.patientStart(clientID);
						System.out.println("Do you want to continue? yes/no");
						Scanner keyboard5=new Scanner(System.in);
						String continue5=keyboard5.nextLine();
						if(continue5.equalsIgnoreCase("yes"))
							repeat=true;
						else {
							System.out.println("Thanks for using DHMS.");
							repeat=false;
						}
					}					
					boundOk=false;
				}
				
			}else {
				System.out.println("Please select 1 or 2:");
				choose=keyboard.nextInt();
			}
		}
	}
	public static String setAppointmentID()
	{		
		System.out.println("Please enter the appointmentID:");
		String appointmentID="";
		Scanner keyboard=new Scanner(System.in);
		appointmentID=keyboard.nextLine();
		return appointmentID;
	}
	public static String setAppointmentType()
	{
		System.out.println("Please enter the appointmentType:\n1. Physician;\n2. Surgeon;\n3. Dental");
		String appointmentType="";
		int input=0;
		Scanner keyboard=new Scanner(System.in);
		input=keyboard.nextInt();
		switch(input) {
			case 1: appointmentType="Physician";break;
			case 2: appointmentType="Surgeon";break;
			case 3: appointmentType="Dental";break;
		}
		return appointmentType;
	}
	public int setAppointmentWeek()
	{
		System.out.println("Please enter the appointment week");
		int input=0;
		Scanner keyboard=new Scanner(System.in);
		input=keyboard.nextInt();
		return input;
	}
	public int setCapacity()
	{
		System.out.println("Please enter the capacity you want(should be less than the maximum capacity:3)");
		int input=0;
		boolean doLoop=false;
		Scanner keyboard=new Scanner(System.in);
		do {			
			input=keyboard.nextInt();
			if(input>3) {
				System.out.println("Should be less than the maximum capacity:3. Please enter again.");
				doLoop=true;
			}else {
				doLoop=false;
			}
		}while(doLoop);
		
		return input;
	}
	public static String setPatientID()
	{
		System.out.println("Please enter the patientID:");
		String patientID="";
		Scanner keyboard=new Scanner(System.in);
		patientID=keyboard.nextLine();
		return patientID;
	}
	public static String setClientID()
	{
		System.out.println("Please enter the ClientID:");
		String clientID="";
		Scanner keyboard=new Scanner(System.in);
		clientID=keyboard.nextLine();
		return clientID;
	}

}
