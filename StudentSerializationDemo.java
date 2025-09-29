import java.io.*;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private int studentID;
    private String name;
    private double grade;

    public Student(int studentID, String name, double grade) {
        this.studentID = studentID;
        this.name = name;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student{studentID=" + studentID + ", name='" + name + "', grade=" + grade + "}";
    }

    // Getters (optional)
    public int getStudentID() { return studentID; }
    public String getName() { return name; }
    public double getGrade() { return grade; }
}

public class StudentSerializationDemo {
    private static final String FILE_NAME = "student.dat";

    public static void main(String[] args) {
        // Create a student object
        Student s = new Student(13549, "ADARSH RAJ", 7.6);

        // Serialize
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(s);
            System.out.println("Serialized student to file: " + FILE_NAME);
        } catch (IOException e) {
            System.err.println("Error serializing student: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // Deserialize
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            Object obj = ois.readObject();
            if (obj instanceof Student) {
                Student deserialized = (Student) obj;
                System.out.println("Deserialized student object:");
                System.out.println(deserialized);
            } else {
                System.err.println("File does not contain a Student object.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error deserializing student: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
