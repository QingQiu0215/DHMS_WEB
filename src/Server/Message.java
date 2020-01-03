package Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Message implements Serializable {
	Map<String, Map<String,ArrayList<String>>> mapMessage = new HashMap<String, Map<String,ArrayList<String>>>();
	private String msg="";
	
	public Message(String msg) {
		this.msg=msg;
	}
	public Message(Map<String, Map<String,ArrayList<String>>> map) {
		mapMessage=map;
	}
	public Map<String, Map<String,ArrayList<String>>> getMap(){
		return mapMessage;
	}
	public void setMap(Map<String, Map<String,ArrayList<String>>> map){
		this.mapMessage=map;
	}
	public void setMessage(Map<String, Map<String,ArrayList<String>>> map) {
		mapMessage=map;
	}
	public String getMsg() {
		return msg;
	}

}
