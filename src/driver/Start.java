package driver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import management.ManagementCustomer;
import management.ManagementEmployee;

public class Start {

	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int select = 0;
		try {
			while(true) {
			System.out.println("			Enter your choice of system\n 1 Employee Management System\n 2 Customer Management System\n 3 Exit System");
			select = Integer.parseInt(br.readLine());
			int choice = 0;
			switch (select) {

			case 1: {// Create EMS
				ManagementEmployee manEmpObj = new ManagementEmployee();

				while (choice != 6) {
					System.out.println("\n				EMPLOYEE MANAGEMENT SYSTEM 				\n");
					System.out.println("1 to Create Employee\n2 to Read Employee Detail\n3 to Update Employee Detail\n"
							+ "4 to Delete Employee Detail\n5 to Read All Employees Details\n6 to Exit EMPLOYEE MANAGEMENT SYSTEM\nEnter your choice (Integer): ");
					choice = Integer.parseInt(br.readLine());
					switch (choice) {
					case 1: {
						manEmpObj.create();
					}
						break;
					case 2: { // Read an employee details on the basis of ID
						manEmpObj.read();
					}
						break;
					case 3: { // Update the employee details
						manEmpObj.update();
					}
						break;
					case 4: { // Delete employee details from the system
						manEmpObj.delete();
					}
						break;
					case 5: { // Read All the Employee details
						manEmpObj.readAll();
					}
						break;
					case 6: {
						manEmpObj.closeConnection();
						System.out.println(" Exiting..");
					}
						break;
					default: {
						System.out.println(" Make another choice");
					}
					} 
				} 
			} 
				break;

			case 2: { // Create CMS
				ManagementCustomer manCustObj = new ManagementCustomer();

				while (choice != 6) {
					System.out.println("\n				CUSTOMER MANAGEMENT SYSTEM 				\n");
					System.out.println("1 to Create Customer\n2 to Read Customer Detail\n3 to Update Customer Detail\n"
							+ "4 to Delete Customer Detail\n5 to Read All Customer Details\n6 to Exit CUSTOMER MANAGEMENT SYSTEM \nEnter your choice (Integer): ");
					choice = Integer.parseInt(br.readLine());
					switch (choice) {
					case 1: {
						manCustObj.create();
					}
						break;
					case 2: { // Read an employee details on the basis of ID
						manCustObj.read();
					}
						break;
					case 3: { // Update the employee details
						manCustObj.update();
					}
						break;
					case 4: { // Delete employee details from the system
						manCustObj.delete();
					}
						break;
					case 5: { // Read All the Employee details
						manCustObj.readAll();
					}
						break;
					case 6: {
						manCustObj.closeConnection();
						System.out.println(" Exiting..");
					}
						break;
					default: {
						System.out.println(" Make another choice");
					}
					} 
				} 
			} 
				break;
			case 3: { // Exit
				System.out.println(" Exiting..");
				System.out.println(" Rerun the application!!");
				System.exit(0);
			}
				break;
			default:
				System.out.println("Make a valid choice of System!");
			} // external switch brace
				// } // while brace
			}

		} catch (InputMismatchException e) {
			System.out.println("Inappropriate Input Type");
		} catch (Exception e) {
			System.out.println(" Application Under Construction ");
			e.printStackTrace();
		}
	}
}
