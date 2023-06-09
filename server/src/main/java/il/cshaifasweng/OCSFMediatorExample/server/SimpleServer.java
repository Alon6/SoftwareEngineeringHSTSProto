package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.Exam;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.util.*;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import org.hibernate.Session;

import javax.persistence.Query;
import static il.cshaifasweng.OCSFMediatorExample.server.App.getSessionFactory;

public class SimpleServer extends AbstractServer {

	public SimpleServer(int port) {
		super(port);

	}

	public static String getColumnAsString(String columnName) throws Exception {
		try (Session session = getSessionFactory().openSession()) {
			Query query = session.createNativeQuery("SELECT name FROM StudentsWithGrades");
			List<String> names = query.getResultList();
			String msg = "Student names: ";
			return msg+names.toString();
		}
	}
	public static String getGradesAsString(String name) throws Exception {
		try (Session session = getSessionFactory().openSession()) {
			Query query = session.createNativeQuery("SELECT grades FROM StudentsWithGrades where name = '" + name +"'");

			List<byte[]> gradesList = query.getResultList();

			// Convert byte arrays to strings
			List<String> gradesAsString = new ArrayList<>();
			for (byte[] gradeBytes : gradesList) {
				String gradeString = new String(gradeBytes);
				gradesAsString.add(gradeString);
			}

			String msg = "Grades: " + gradesAsString.toString();
			return msg;
		}
	}

	public static void updateGrades(/* String name, int test_id, String newGrade*/ String msg) throws Exception {
		try (Session session = getSessionFactory().openSession()) {

			String[] parts_name = msg.split(" ");
			String command = parts_name[0];
			String name = parts_name[1];
			msg = msg.replace(command +" ", "").replace(name+" ", "");

			// Extract the numbers from the input string
			String[] parts = msg.split("\\s+");
			List<Integer> numbers = new ArrayList<>();

			for (int i = 2; i < parts.length; i += 2) {
				numbers.add(Integer.parseInt(parts[i]));
			}
			session.getTransaction().begin();

			String[] test_id_array = new String[parts.length/2];
			String[] grades_array = new String[parts.length/2];

			for (int i = 0; i < parts.length; i++) {
				if (i % 2 == 0) {
					test_id_array[i / 2] = parts[i];
				} else {
					grades_array[i / 2] = parts[i];
				}
			}

			String newGrades = String.join(" ", grades_array);
			Query query2 = session.createNativeQuery("UPDATE StudentsWithGrades SET grades = '" + newGrades+ "' WHERE name = '" + name +"';");
			int rowsUpdated = query2.executeUpdate();
				// Begin a transaction
//				session.getTransaction().begin();
//				Query query2 = session.createNativeQuery("UPDATE StudentsWithGrades SET grades = '" + grades + "' WHERE name = '" + name +"';");
//				int rowsUpdated = query2.executeUpdate();

			// Commit the transaction
			session.getTransaction().commit();
		}
	}

	//success message format: LogIn <<role>> <<username>> (role is one of the following options: "student", "teacher, "principle")
	//error message format: InputError <<error description>>

	private static String getTableName(String role)
	{
		if(role.equals("principle")) {
			return "Principals";
		}
		else if(role.equals("teacher")) {
			return "Teachers";
		}
		else if(role.equals("student"))
		{
				return "Pupils";
		}
		else
		{
			return "";
		}
	}
	private String logIn(String role, String username, String password)
	{
		String loginResultMessage;
		int count = 0;
		Boolean isLoggedIn = true;
		try (Session session = getSessionFactory().openSession()) {
			session.getTransaction().begin();
			Query query1 = session.createNativeQuery("select count(*) from "+ getTableName(role) +" where name = '" + username  + "' and password = '"+ password + "';");
			count = ((Number) query1.getSingleResult()).intValue();

			if(count > 0)
			{
				Query query2 = session.createNativeQuery("select isLoggedIn from "+ getTableName(role) +" where name = '" + username  + "' and password = '"+ password + "';");
				isLoggedIn = ((Boolean) query2.getSingleResult()).booleanValue();
			}

			// Commit the transaction
			session.getTransaction().commit();
		}

		if (count > 0)
		{
			if(!isLoggedIn)
			{
				loginResultMessage = "LogIn "+  role + " " + username;
				try (Session session = getSessionFactory().openSession()) {
					session.getTransaction().begin();

					Query query3 = session.createNativeQuery("UPDATE "+ getTableName(role) + " SET isLoggedIn = true" +" where name = '" + username  + "' and password = '"+ password + "';");
					int rowCount = query3.executeUpdate();

					// Commit the transaction
					session.getTransaction().commit();
				}
			}
			else
			{
				loginResultMessage = "InputError You are already logged in";
			}
		}
		else
		{
			loginResultMessage = "InputError The identification details are incorrect";
		}
		return loginResultMessage;
	}
	//Success message: EnterExam <<exam_description>>
	//Error message: InputError <<error_description>>
	private String connectToExam(String code){

		return "";
	}
	//Success message: StartExam
	//Error message: InputError <<error_description>>
	private String StartExam(String id, String name){
		return "";
	}
	private void LogOut(String name, String role){
		try (Session session = getSessionFactory().openSession()) {
			session.getTransaction().begin();
			Query query = session.createNativeQuery("UPDATE "+ getTableName(role) + " SET isLoggedIn = false" +" where name = '" + name + "';");
			query.executeUpdate();

			// Commit the transaction
			session.getTransaction().commit();
		}
	}
	private String GetStudentGrades(String name) {
		return "StudentGrades";
	}
	@Override
	//Treating the message from the clint
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) throws Exception {
		String msgString = msg.toString();
		//validates the entered information and either lets the respective user to proceed to the rest of the system
		//(based on its role) or sends a relevant error message
		if (msgString.startsWith("LogIn")) {
			String[] parts = msgString.split(" ");

			String username = parts[1];
			String password = parts[2];
			String role = parts[3];
			System.out.println("Checking login"); // for debugging
			String message = logIn(role, username, password);
			System.out.println("Finished checking login"); // for debugging
			//message = "LogIn Alon student";

			sendMessage(message,client);
		}
		else if (msgString.startsWith("EnterExam")) {
			String[] parts = msgString.split(" ");
			String code = parts[1];
			String message = connectToExam(code);
			if (code.compareTo("1234") == 0){

				message = "EnterExam";
			}
			else {
				message = "InputError The code you entered does not exist";

			}
			sendMessage(message,client);
		}
		else if (msgString.startsWith("StartExam")) {
			String[] parts = msgString.split(" ");

			String id = parts[1];
			String name = parts[2];
			String message = StartExam(id,name);
			message = "StartExam";
			sendMessage(message,client);
		}
		else if (msgString.startsWith("LogOut")) {
			String[] parts = msgString.split(" ");
			String name = parts[1];
			String role = parts[2];
			LogOut(name,role);
			sendMessage("LogOut",client);
		}
		else if (msgString.startsWith("GetStudentGrades")) {
			String[] parts = msgString.split(" ");
			String name = parts[1];
			String grades = GetStudentGrades(name);
			sendMessage(grades,client);
		}
	}
	//Send received message to the relevant client
	protected void sendMessage (String msg, ConnectionToClient client){
		Message message = new Message(msg);
		try {
			client.sendToClient(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//Validates grade input
	private static boolean isGrade (String str) {
		if (str == null) {
			return false;
		}
		int length = str.length();
		if (length == 0 || (length > 2 && !str.equals("100"))) {
			return false;
		}
		for (int i = 0; i < length; i++) {
			char c = str.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}
		return true;
	}
}


