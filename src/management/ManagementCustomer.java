package management;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Pattern;
import dao.Operation2;
import data.Customer;
import interfaces.Management;

public class ManagementCustomer implements Management<Customer> {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	Operation2<Customer> opObj;

	public ManagementCustomer() {
		opObj = new Operation2<>();
	}

	public boolean checkNumber(String number) {
		return Pattern.matches("[0-9]+", number);
	}

	public boolean checkName(String name) {
		return Pattern.matches("[A-Za-z ]*", name);
	}

	public boolean checkNegativeValue(String value) {
		return Pattern.matches("-[0-9]+", value);
	}

	// Creates an employee record
	// Enters none for the entries left empty
	public void create() {

		try {
			readAll();
			System.out.println("Enter the Non-Existing Customer id (Integer): ");
			String customerId = br.readLine();
			// Valid Id check
			while (checkNumber(customerId) != true && checkNegativeValue(customerId) == false) {
				System.out.println("Invalid ID, Enter INTEGER ID");
				customerId = br.readLine();
			}
			if (opObj.checkIdInDB("Customer", Integer.parseInt(customerId)) == true) {
				System.out.println("Record Already EXists!!");
				return;
			}
			Customer tempObj = new Customer(Integer.parseInt(customerId));
			System.out.println("Enter the customer name: ");
			String customerName = br.readLine();// sc.nextLine(); // employee name
			// Checks for Valid Name
			while (true) {
				if (checkName(customerName) == true && customerName.isEmpty() == false) {
					tempObj.setName(customerName);
					break;
				} else {
					System.out.println("Invalid name!! RE-ENTER THE NAME (ALPHABETS AND SPACE ONLY)");
					customerName = br.readLine();
				}
			}
			System.out.println("\nEnter the amount spent: ");
			String amount = br.readLine();
			while (true) {
				if (checkNumber(amount) == true && checkNegativeValue(amount) == false) {
					tempObj.setAmount(Integer.parseInt(amount));
					break;
				} else {
					System.out.println("Invalid amount!! RE-ENTER THE AMOUNT ( POSITIVE )");
					amount = br.readLine();
				}
			}
			System.out.println(tempObj);
			try {
				opObj.insertInto(tempObj);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Reads customer details on the basis of id
	public void read() {
		try {
			System.out.println("Enter the customer id (Integer): ");
			String customerId = br.readLine();

			// Valid Id check
			while (checkNumber(customerId) != true && checkNegativeValue(customerId) == false) {
				System.out.println("Invalid ID, Enter POSITIVE INTEGER ID");
				customerId = br.readLine();// sc.nextLine();
			}
			Customer tempObj = new Customer(Integer.parseInt(customerId));
			try {
				tempObj = opObj.read(tempObj, Integer.parseInt(customerId));
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
	/*
	 * Read the object with that id and then update details and send it back to
	 * database
	 */

	public void update() {
		try {
			readAll();
			System.out.println("Enter Customer Id: ");
			String customerId = br.readLine();// sc.nextInt();
			while (checkNumber(customerId) != true && checkNegativeValue(customerId)) {
				System.out.println("Invalid ID, Enter POSITIVE INTEGER ID");
				customerId = br.readLine();
			}
			Customer tempObj = new Customer();
			int id = Integer.parseInt(customerId);
			tempObj = opObj.read(tempObj, id);
			if (tempObj == null) {
				System.out.println("No Such Record Exists!!");
				return;
			}
			System.out.println("Update Customer Name ? ( Old Name: " + tempObj.getName() + " )");
			System.out.println("New Name : ");
			String oldName = tempObj.getName();
			String name = br.readLine();// sc.nextLine();
			// Checks name
			while (true) {
				if (checkName(name) == true) {
					tempObj.setName(name);
					break;
				} else {
					System.out.println("Invalid name!! RE-ENTER THE NAME (ALPHABETS AND SPACE ONLY)");
					name = br.readLine();
				}
			}
			if (name.isEmpty()) {
				tempObj.setName(oldName);
			} else {
				tempObj.setName(name);
			}
			System.out.println("Employee Name: " + tempObj.getName());
			System.out.println("Update Amount ? ( Old Amount: " + tempObj.getAmount() + " )");

			System.out.println("New Amount : ");
			String amount = br.readLine();
			int oldAmount = tempObj.getAmount();
			while (true) {
				if (checkNumber(amount) == true && checkNegativeValue(amount) == false) {
					tempObj.setAmount(Integer.parseInt(amount));
					break;
				} else {

					if (amount.isEmpty()) {
						tempObj.setAmount(oldAmount);
						break;
					}
					System.out.println("Invalid amount!! RE-ENTER THE AMOUNT ( POSITIVE INTEGER NUMBER)");
					amount = br.readLine();
				}
			}
			try {
				System.out.println(tempObj);
				opObj.update(tempObj, Integer.parseInt(customerId));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * Deletes an employee details on the basis of id(non-Javadoc)
	 */
	public void delete() {
		try {
			readAll();
			System.out.println("Enter the Customer ID (Integer): ");
			String customerId = br.readLine();// sc.nextInt();
			while (checkNumber(customerId) != true) {
				System.out.println("Invalid ID, Enter INTEGER ID");
				customerId = br.readLine();// sc.nextLine();
			}
			Customer c = new Customer(Integer.parseInt(customerId));
			if (opObj.delete(c, Integer.parseInt(customerId)) == false) {
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

	/*
	 * Reads All Employees Details(non-Javadoc)
	 * 
	 * @see interfaces.Management#readAll()
	 */
	public void readAll() {
		Customer tempObj = new Customer();
		List<Customer> resultList = opObj.readAll(tempObj);
		if (resultList == null) {
			System.out.println("No Records to display!!");
			return;
		}
		try {
			for (int i = 0; i < resultList.size(); i++) {
				System.out.println(resultList.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeConnection() {
		opObj.connectClose();
	}

}
