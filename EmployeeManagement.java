import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Employee implements Serializable {
    private static final long serialVersionUID = 2L;
    private String name;
    private String id;
    private String designation;
    private double salary;

    public Employee(String name, String id, String designation, double salary) {
        this.name = name;
        this.id = id;
        this.designation = designation;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return String.format("Employee{id='%s', name='%s', designation='%s', salary=%.2f}", id, name, designation, salary);
    }

    // Getters if needed
    public String getName() { return name; }
    public String getId() { return id; }
    public String getDesignation() { return designation; }
    public double getSalary() { return salary; }
}

public class EmployeeManagement {
    private static final String FILE_NAME = "employees.dat";

    @SuppressWarnings("unchecked")
    private static ArrayList<Employee> loadEmployees() {
        File f = new File(FILE_NAME);
        if (!f.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList<?>) {
                return (ArrayList<Employee>) obj;
            } else {
                System.err.println("Data file corrupted or in unexpected format. Starting with empty list.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Could not load employees: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    private static void saveEmployees(ArrayList<Employee> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(list);
        } catch (IOException e) {
            System.err.println("Error saving employees: " + e.getMessage());
        }
    }

    private static void addEmployee(Scanner scanner, ArrayList<Employee> list) {
        System.out.print("Enter employee name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter employee ID: ");
        String id = scanner.nextLine().trim();

        System.out.print("Enter designation: ");
        String designation = scanner.nextLine().trim();

        double salary = 0.0;
        while (true) {
            System.out.print("Enter salary: ");
            String s = scanner.nextLine().trim();
            try {
                salary = Double.parseDouble(s);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid salary. Please enter a numeric value.");
            }
        }

        Employee emp = new Employee(name, id, designation, salary);
        list.add(emp);
        saveEmployees(list);
        System.out.println("Employee added and saved successfully.");
    }

    private static void displayAllEmployees(ArrayList<Employee> list) {
        if (list.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        System.out.println("Employees:");
        for (Employee e : list) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Employee> employees = loadEmployees();
        boolean running = true;

        while (running) {
            System.out.println("\nEmployee Management Menu");
            System.out.println("1. Add an Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Exit");
            System.out.print("Choose an option (1-3): ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    addEmployee(scanner, employees);
                    break;
                case "2":
                    displayAllEmployees(employees);
                    break;
                case "3":
                    System.out.println("Exiting application. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }

        scanner.close();
    }
}
