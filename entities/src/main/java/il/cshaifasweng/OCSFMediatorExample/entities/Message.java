package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;

public class Message<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8224097662914849956L;
	private String message;
	private LocalTime time;
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Message(String message) {
		this.message = message;
		this.time = LocalTime.now();
	}
	public Message(String message, T param) {
		this.message = message;
		this.time = LocalTime.now();
	}
	@Override
	public String toString(){
		return getMessage();
	}
	public LocalTime getTime() {
		return time;
	}
}
