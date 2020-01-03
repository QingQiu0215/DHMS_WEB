package com.web.service;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface WebInterface {
	 public void writeTxtClient(String clientID,String task, String resultStr);
	 public void writeTxtServerMTL(String clientID,String patientID,String appointmentType,String appointmentID,String task, String resultStr);
	 public void writeTxtServerQUE(String clientID,String patientID,String appointmentType,String appointmentID,String task, String resultStr);
	 public void writeTxtServerSHE(String clientID,String patientID,String appointmentType,String appointmentID,String task, String resultStr);
	 public String bookAppointment(String clientID,String patientID, String appointmentID, String appointmentType);
	 public String getAppointmentSchedule(String patientID);
	 public String cancelAppointment(String clientID, String patientID,String appointmentID, String appointmentType);
	 public String swapAppointment(String clientID, String patientID,String oldAppointmentID, String oldAppointmentType,String newAppointmentID, String newAppointmentType);
	 public String addAppointment(String appointmentID,String appointmentType,String capacity,String appointmentWeekStr);
	 public boolean checkAppointmentExisted(String appointmentID,String appointmentType);
	 public String removeAppointment(String appointmentID,String appointmentType);
	 public String listAppointmentAvailability(String appointmentType);

}
