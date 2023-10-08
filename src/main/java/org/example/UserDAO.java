package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDAO {

	public UserDAO() {
		
	}

	// connection to database
	public Connection connectDB() throws InstantiationException, IllegalAccessException {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/daaw_db", "root", "25102003");
			System.out.println("Connection avec succes a la base de donnees !");
		} catch (ClassNotFoundException cnf) {
			System.out.print("Driver non chargé...");
		} catch (SQLException sqlex) {
			System.out.println("Incapable de connecter a la base de donnees...");
		}
		return connection;

	}


	public void addUser(User user) throws InstantiationException, IllegalAccessException, SQLException{

		Connection connection=null;
		String requete;
		PreparedStatement stmt;

		connection = connectDB();
		requete = "INSERT INTO User(id,first_name,last_name,email) VALUES(?,?,?,?)";
		stmt = connection.prepareStatement(requete);
		stmt.setInt(1, user.getId());
		stmt.setString(2, user.getFirstname());
		stmt.setString(3, user.getLastname());
		stmt.setString(4, user.getEmail());

		stmt.executeUpdate();
		stmt.close();

		System.out.println("Inserted !");

	}

	// UPDATE USER  METHODE
	public void updateUser(User a,User b) throws InstantiationException, IllegalAccessException, SQLException{
		Connection connection=null;
		String requete;
		PreparedStatement stmt;
		connection = connectDB();
		requete ="UPDATE User SET  ";
		if(b.getFirstname()!=""){
			requete=requete.concat("first_name ='"+b.getFirstname()+"' ," );
		}else{
			requete=requete.concat("first_na" +"me ='"+a.getFirstname()+"' ," );
		}
		if (b.getLastname()!=""){
			requete=requete.concat(" last_name ='"+b.getLastname()+"' ," );
		}else{
			requete=requete.concat(" last_name ='"+a.getLastname()+"' ," );
		}
		if (b.getEmail()!=""){
			requete=requete.concat("email ='"+b.getEmail()+"' " );
		}else{
			requete=requete.concat("email ='"+a.getEmail()+"' ");
		}

		requete=requete.concat("WHERE id =  " + a.getId() +";");
		System.out.println(requete);
		stmt = connection.prepareStatement(requete);
		stmt.executeUpdate();
		stmt.close();
		System.out.println("Updated !");
	}

	// DELETE USER
	public void DeleteUser(User a)  throws InstantiationException, IllegalAccessException, SQLException{
		Connection connection=null;
		String requete;
		PreparedStatement stmt;
		connection = connectDB();
		requete= "DELETE FROM User WHERE id = " + a.getId() +";";
		stmt = connection.prepareStatement(requete);
		stmt.executeUpdate();
		stmt.close();
		System.out.println("Deleted !");
	}
	public List<User> getAllUser () throws InstantiationException, IllegalAccessException, SQLException{
		Connection connection=null;
		PreparedStatement stmt;
		connection = connectDB();
		List<User> a = new ArrayList<User>();
		stmt = connection.prepareStatement("Select id from User;");
		ResultSet resultSet = stmt.executeQuery();
		while(resultSet.next()) {
			a.add(getUserById(resultSet.getInt("id")));
		}
		stmt.close();

		System.out.println("Get All the User List!");
		return a;

	};


    
	public User  getUserById(int i) throws InstantiationException, IllegalAccessException, SQLException{

		Connection connection=null;
		String requete;
		PreparedStatement stmt;
		User myUser = null;

		connection = connectDB();
		requete = "SELECT * FROM User WHERE id = "+i +";";
		stmt = connection.prepareStatement(requete);
		

		ResultSet resultSet = stmt.executeQuery();

		if(resultSet.next()){
			myUser = new User();
			myUser.setId(resultSet.getInt("id"));
			myUser.setFirstname(resultSet.getString("first_name"));
			myUser.setLastname(resultSet.getString("last_name"));
			myUser.setEmail(resultSet.getString("email"));

		}

		return  myUser;

	}

	public User getUserByFirstName(String name) throws InstantiationException, IllegalAccessException, SQLException {
		Connection connection=null;
		String requete;
		PreparedStatement stmt;
		User myUser = null;

		connection = connectDB();
		requete = "SELECT * FROM User WHERE first_name = '"+ name+"';";
		stmt = connection.prepareStatement(requete);


		ResultSet resultSet = stmt.executeQuery();

		if(resultSet.next()){
			myUser = new User();
			myUser.setId(resultSet.getInt("id"));
			myUser.setFirstname(resultSet.getString("first_name"));
			myUser.setLastname(resultSet.getString("last_name"));
			myUser.setEmail(resultSet.getString("email"));

	}
		return myUser;

	
}}
