package management;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Pattern;
import dao.Operation2;
import data.Employee;
import interfaces.Management;

public class ManagementEmployee implements Management<Employee> {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	Operation2<Employee> opObj;

	public ManagementEmployee() throws Exception {
		opObj = new Operation2<>();
	}

	// Returns true if matches
	public boolean checkID(String name) {
		return Pattern.matches("[0-9]+", name);
	}

	// Returns true if matches
	public boolean checkName(String name) {
		return Pattern.matches("[A-Za-z ]*", name);
	}

	// Creates an employee record
	// Enters none for the entries left empty
	public void create() {

		try {
			readAll();
			System.out.println("Enter the Non-Existing Employee id (Integer): ");
			String empId = br.readLine();
			// Valid Id check
			while (checkID(empId) != true) {
				System.out.println("Invalid ID, Enter INTEGER ID");
				empId = br.readLine();
			}
			if (opObj.checkIdInDB("Employee", Integer.parseInt(empId)) == true) {
				System.out.println("Record Already EXists!!");
				return;
			}
			Employee tempObj = new Employee(Integer.parseInt(empId));
			System.out.println("Enter the Employee name: ");
			String empName = br.readLine();
			// Checks for Valid Name
			while (true) {
				if (checkName(empName) == true && empName.isEmpty() == false) {
					tempObj.setName(empName);
					break;
				} else {
					System.out.println("Invalid name!! RE-ENTER THE NAME (ALPHABETS AND SPACE ONLY)");
					empName = br.readLine();
				}
			}

			System.out.println("\nEnter the team name: ");
			String team = br.readLine();
			while (true) {
				if (checkName(team) == true && team.isEmpty() == false) {
					tempObj.setTeam(team);
					break;
				} else {
					System.out.println("Invalid team name!! RE-ENTER THE TEAM NAME (APLHABETS AND SPACE ONLY)");
					team = br.readLine();// sc.nextLine();
				}
			}
			System.out.println(tempObj);
			try {
				opObj.insertInto(tempObj);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Reads an employee details on the basis of id
	public void read() {
		try {
			System.out.println("Enter the employee id (Integer): ");
			String employee = br.readLine();// sc.nextLine(); // employee id
			while (checkID(employee) != true) {
				System.out.println("Invalid ID, Enter INTEGER ID");
				employee = br.readLine();// sc.nextLine();
			}
			Employee tempObj = new Employee(Integer.parseInt(employee));
			try {
				tempObj = opObj.read(tempObj, Integer.parseInt(employee));
				if (tempObj == null) {
					System.out.println("ID Does Not Exist!");
					return;
				}
				System.out.println(tempObj);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Updates an employee details on the basis of id
	public void update() {
		try {
			readAll();
			System.out.println("Enter Employee Id: ");
			String empId = br.readLine();// sc.nextInt();
			while (checkID(empId) != true) {
				System.out.println("Invalid ID, Enter POSITIVE INTEGER ID");
				empId = br.readLine();// sc.nextLine();
			}
			Employee tempObj = new Employee();
			int id = Integer.parseInt(empId);
			tempObj = opObj.read(tempObj, id);
			if (tempObj == null) {
				System.out.println("No Such Record Exists!!");
				return;
			}

			System.out.println("Update Employee Name ? ( Old Name: " + tempObj.getName() + " )");

			System.out.println("New Name : ");
			String oldName = tempObj.getName();
			String name = br.readLine();// sc.nextLine();
			while (true) {
				if (checkName(name) == true) {
					tempObj.setName(name);
					break;
				} else {
					System.out.println("Invalid name!! RE-ENTER THE NAME (ALPHABETS AND SPACE ONLY)");
					name = br.readLine();// sc.nextLine();
				}
			}
			if (name.isEmpty()) {
				tempObj.setName(oldName);
			} else {
				tempObj.setName(name);
			}
			System.out.println("Employee Name: " + tempObj.getName());
			System.out.println("Update Team ? ( Old Team: " + tempObj.getTeam() + " )");

			System.out.println("New Team : ");
			String team = br.readLine();// sc.nextLine();
			String oldTeam = tempObj.getTeam();
			// Checks Team name
			while (true) {
				if (checkName(team) == true) {
					tempObj.setTeam(team);
					break;
				} else {
					System.out.println("Incorrect team!! RE-ENTER THE TEAM (ALPHABETS AND SPACE ONLY)");
					team = br.readLine();// sc.nextLine();
				}
			}
			if (team.isEmpty()) {
				tempObj.setTeam(oldTeam);
			} else {
				tempObj.setTeam(team);
			}
			System.out.println("Team Name: " + tempObj.getTeam());
			try {
				System.out.println(tempObj);
				opObj.update(tempObj, Integer.parseInt(empId));
			} catch (Exception e) {
				e.printStackTrace();
			}
//		}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Deletes an employee details on the basis of id
	public void delete() {
		try {
			readAll();
			System.out.println("Enter the Employee ID (Integer): ");
			String customerId = br.readLine();// sc.nextInt();
			while (checkID(customerId) != true) {
				System.out.println("Invalid ID, Enter INTEGER ID");
				customerId = br.readLine();// sc.nextLine();
			}
			Employee emp = new Employee(Integer.parseInt(customerId));
			if (opObj.delete(emp, Integer.parseInt(customerId)) == false) {
				System.out.println("ID Doesnt Exists");
			} else {
				System.out.println("Record Deleted");
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Reads All Employees Details
	public void readAll() {
		Employee tempObj = new Employee();
		List<Employee> resultList = opObj.readAll(tempObj);
		int rowCount = resultList.size();
		if (rowCount == 0) {
			System.out.println("No Records To display!!");
			return;
		}
		try {
			for (int row = 0; row < rowCount; row++) {
				System.out.println(resultList.get(row));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeConnection() {
		opObj.connectClose();
	}

}
