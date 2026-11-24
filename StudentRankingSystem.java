import java.io.*;
import java.util.Scanner;

/**
 * Student Ranking System (Console, Java)
 * Concepts: Array, Sorting, Searching, Methods, File Handling
 * Data file: students.txt  (format: roll,name,marks)
 */
public class StudentRankingSystem {

    // ---------- Student record ----------
    static class Student {
        String roll;
        String name;
        int marks;

        Student(String roll, String name, int marks) {
            this.roll = roll;
            this.name = name;
            this.marks = marks;
        }
    }

    static final String FILE_NAME = "students.txt";
    static final int FIRST_ROLL = 101;

    static Student[] students = new Student[10];
    static int count = 0;
    static int nextRoll = FIRST_ROLL;

    // ---------- Array handling ----------
    static void addToArray(Student s) {
        if (count == students.length) {            // grow array manually
            Student[] bigger = new Student[students.length * 2];
            for (int i = 0; i < count; i++) bigger[i] = students[i];
            students = bigger;
        }
        students[count++] = s;
    }

    // ---------- Feature 1: add ----------
    static void addStudent(Scanner sc) {
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        if (name.length() == 0 || name.indexOf(',') >= 0) {
            System.out.println("Invalid name. Name cannot be empty or contain a comma.");
            return;
        }
        System.out.print("Marks: ");
        String m = sc.nextLine().trim();
        int marks = parseMarks(m);
        if (marks < 0) {
            System.out.println("Invalid marks. Marks must be between 0 and 100.");
            return;
        }
        String roll = "R" + nextRoll++;
        addToArray(new Student(roll, name, marks));
        System.out.println("Student added. Roll: " + roll + " | " + name + " | " + marks);
    }

    // returns -1 if not a whole number in 0..100
    static int parseMarks(String s) {
        if (s.length() == 0 || s.length() > 3) return -1;
        int v = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') return -1;
            v = v * 10 + (c - '0');
        }
        return (v >= 0 && v <= 100) ? v : -1;
    }

    // ---------- Sorting (own logic): insertion sort, descending by marks ----------
    // Insertion sort is stable, so equal marks keep their entry (roll) order.
    static Student[] sortedCopy() {
        Student[] a = new Student[count];
        for (int i = 0; i < count; i++) a[i] = students[i];
        for (int i = 1; i < count; i++) {
            Student key = a[i];
            int j = i - 1;
            while (j >= 0 && a[j].marks < key.marks) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
        return a;
    }

    // ---------- Feature 2: rank list ----------
    static void displayRankList() {
        if (count == 0) {
            System.out.println("No students found.");
            return;
        }
        Student[] a = sortedCopy();
        for (int i = 0; i < count; i++) {
            System.out.println("Rank " + (i + 1) + ": " + a[i].roll + " | " + a[i].name + " | " + a[i].marks);
        }
    }

    // ---------- Searching (own logic): linear search ----------
    static boolean sameText(String a, String b) {   // case-insensitive compare
        if (a.length() != b.length()) return false;
        for (int i = 0; i < a.length(); i++) {
            if (Character.toLowerCase(a.charAt(i)) != Character.toLowerCase(b.charAt(i))) return false;
        }
        return true;
    }

    // ---------- Feature 3: search by roll or name ----------
    static void search(Scanner sc) {
        System.out.print("Enter Roll or Name: ");
        String key = sc.nextLine().trim();
        if (key.length() == 0) {
            System.out.println("Search text cannot be empty.");
            return;
        }
        Student[] a = sortedCopy();
        boolean found = false;
        for (int i = 0; i < count; i++) {
            if (sameText(a[i].roll, key) || sameText(a[i].name, key)) {
                System.out.println("Found: " + a[i].roll + " | " + a[i].name + " | " + a[i].marks + " | Rank: " + (i + 1));
                found = true;
            }
        }
        if (!found) System.out.println("Student not found: " + key);
    }

    // ---------- Feature 4: topper ----------
    static void showTopper() {
        if (count == 0) {
            System.out.println("No students found.");
            return;
        }
        int max = students[0].marks;
        for (int i = 1; i < count; i++) if (students[i].marks > max) max = students[i].marks;
        for (int i = 0; i < count; i++) {                // all students sharing the top marks
            if (students[i].marks == max) {
                System.out.println("Topper: " + students[i].roll + " | " + students[i].name + " | " + students[i].marks);
            }
        }
    }

    // ---------- File handling ----------
    static void loadFromFile() {
        File f = new File(FILE_NAME);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.length() == 0) continue;
                String[] p = line.split(",");
                if (p.length != 3) continue;             // skip damaged lines
                int marks = parseMarks(p[2].trim());
                if (marks < 0) continue;
                addToArray(new Student(p[0].trim(), p[1].trim(), marks));
                // roll numbers can exceed 100, so parse digits directly
                int r = 0;
                String digits = p[0].trim().substring(1);
                boolean ok = digits.length() > 0;
                for (int i = 0; i < digits.length(); i++) {
                    char c = digits.charAt(i);
                    if (c < '0' || c > '9') { ok = false; break; }
                    r = r * 10 + (c - '0');
                }
                if (ok && r >= nextRoll) nextRoll = r + 1;
            }
        } catch (IOException e) {
            System.out.println("Could not read " + FILE_NAME + ": " + e.getMessage());
        }
    }

    static void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < count; i++) {
                pw.println(students[i].roll + "," + students[i].name + "," + students[i].marks);
            }
        } catch (IOException e) {
            System.out.println("Could not save " + FILE_NAME + ": " + e.getMessage());
        }
    }

    // ---------- Menu ----------
    static void printMenu() {
        System.out.println();
        System.out.println("===== Student Ranking System =====");
        System.out.println("1 - Add Student");
        System.out.println("2 - Display Rank List");
        System.out.println("3 - Search (by Roll or Name)");
        System.out.println("4 - Show Topper");
        System.out.println("5 - Save and Exit");
        System.out.print("Choose an option: ");
    }

    public static void main(String[] args) {
        loadFromFile();
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        while (running && sc.hasNextLine()) {
            printMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": addStudent(sc); break;
                case "2": displayRankList(); break;
                case "3": search(sc); break;
                case "4": showTopper(); break;
                case "5": running = false; break;
                default: System.out.println("Invalid option. Please choose 1 to 5.");
            }
        }
        saveToFile();
        System.out.println("Data saved to " + FILE_NAME + ". Goodbye!");
    }
}
