/*
 * COPY OF Operation.java for backup
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import data.Customer;
import data.Employee;
import interfaces.CustomerEmployee;

public class Operation2<T extends CustomerEmployee> {

	private Connection connect = null; // Sets up connection to the database and tables
	private PreparedStatement prepStatement = null; // Sends query containing variables

	public Operation2() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/details", "root", "infoobjects");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public T objectReturn(T tempObj, int id, List<String> resultList) {
		if (tempObj instanceof Employee) {
			Employee empObj = new Employee(id);
			empObj.setName(resultList.get(1));
			empObj.setTeam(resultList.get(2));
			return (T) empObj;
		} else if (tempObj instanceof Customer) {
			Customer custObj = new Customer(id);
			custObj.setName(resultList.get(1));
			custObj.setAmount(Integer.parseInt(resultList.get(2)));
			return (T) custObj;
		}
		return null;
	}

	/*
	 * Returns false if id does not exist in database
	 */
	public boolean checkIdInDB(String workingClass, int id) {
		try {
			StringBuilder sql = new StringBuilder("select id from " + workingClass + " where id= ? ;");
			prepStatement = connect.prepareStatement(sql.toString());

			prepStatement.setString(1, Integer.toString(id));

			ResultSet rs = prepStatement.executeQuery();
			if (rs.next() == false) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/*
	 * Closes Connection
	 */
	public void connectClose() {
		try {
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Create an entry in Employee and Customer table
	 */
	public void insertInto(T tempObj) throws Exception {

		Map<String, String> map = tempObj.toMap();
		int mapSize = map.size();
		String workingClass = tempObj.getClass().getSimpleName();
		StringBuilder sql = new StringBuilder("insert into " + workingClass + " values ( ");
		for (int i = 0; i < mapSize - 1; i++) {
			sql.append("?, ");
		}
		sql.append("? );");
		prepStatement = connect.prepareStatement(sql.toString()); // because prepareStatement doesnt work on string
																	// builder
		int index = 1;
		for (String key : map.keySet()) {
			prepStatement.setString(index, map.get(key));
			index++;
		}
		prepStatement.executeUpdate();
		prepStatement.close();
	}

	public T read(T tempObj, int id) throws Exception {
		List<String> resultList = new ArrayList<>();// used to store result object
		try {
			String workingClass = tempObj.getClass().getSimpleName();

			if (checkIdInDB(workingClass, id) == false) {
				return null;
			}
			StringBuilder sql = new StringBuilder("select * from " + workingClass + " where id = ?;");
			prepStatement = connect.prepareStatement(sql.toString()); // because prepareStatement doesnt work on string
																		// builder
			prepStatement.setString(1, Integer.toString(id));
			ResultSet rs = prepStatement.executeQuery();
			ResultSetMetaData rsMetaData = rs.getMetaData();
			int numberOfColumns = rsMetaData.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= numberOfColumns; i++) {
					String value = rs.getString(i);
					resultList.add(value);
				}
			}
			T typeObject = objectReturn(tempObj, id, resultList);
			if (typeObject != null) {
				return typeObject;
			}

			prepStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// return resultList;
		return null;
	}

	/*
	 * Returns True if Record deleted
	 */
	public boolean delete(T tempObj, int id) {
		try {
			String workingClass = tempObj.getClass().getSimpleName();
			if (checkIdInDB(workingClass, id) == false) {
				return false;
			}
			StringBuilder sql = new StringBuilder("delete from " + workingClass + " where id = ?;");
			prepStatement = connect.prepareStatement(sql.toString());
			prepStatement.setString(1, Integer.toString(id));
			prepStatement.executeUpdate();
			prepStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/*
	 * Returns a list of T type objects
	 */
	public List<T> readAll(T tempObj) {
		List<T> resultList = new ArrayList<>();

		try {
			String workingClass = tempObj.getClass().getSimpleName();
			StringBuilder sql = new StringBuilder("select * from " + workingClass + " ;");
			prepStatement = connect.prepareStatement(sql.toString()); // because prepareStatement doesnt work on string
																		// builder
			ResultSet rs = prepStatement.executeQuery(); // rs used for rows and its meta data used for iterating over
															// columns

			ResultSetMetaData rsMetaData = rs.getMetaData();
			int numberOfColumns = rsMetaData.getColumnCount();
			while (rs.next()) {
				ArrayList<String> stringList = new ArrayList<>();
				for (int i = 1; i <= numberOfColumns; i++) {
					stringList.add(rs.getString(i));
				}
				resultList.add(objectReturn(tempObj, Integer.parseInt(rs.getString(1)), stringList));
			}
			prepStatement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}

	public void update(T tempObj, int id) throws Exception {
		String workingClass = tempObj.getClass().getSimpleName();
		Map<String, String> map = tempObj.toMap();
		int mapSize = map.size();
		int i = 0;
		StringBuilder sql = new StringBuilder("update " + workingClass + " set ");
		for (String key : map.keySet()) {
			sql.append(key + " = ?");
			if (i < mapSize - 1) {
				sql.append(", ");
				i++;
			}
		}
		sql.append(" where id = '" + tempObj.getId() + "';"); // single quotes as the values are inserted in string
		prepStatement = connect.prepareStatement(sql.toString()); // because prepareStatement doesnt work on string
																	// builder
		// insert values for parameters
		int index = 1;
		for (String key : map.keySet()) {
			prepStatement.setObject(index, map.get(key));
			index++;
		}
		prepStatement.executeUpdate();
		prepStatement.close();
	}

}
