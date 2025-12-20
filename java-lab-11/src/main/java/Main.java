import java.sql.*;

public class Main {

    public static void main(String[] args) {
        try (Connection conn = DatabaseConnector.getConnection()) {
            System.out.println("З'єднання встановлено!");

            System.out.println("\n--- Всі співробітники ---");
            printEmployees(conn);

            System.out.println("\n--- Всі завдання ---");
            printTasks(conn);

            System.out.println("\n--- Співробітники відділу №1 ---");
            printEmployeesByDept(conn, 1);

            addTask(conn, "Write documentation", 101);

            System.out.println("\n--- Завдання співробітника 101 ---");
            printTasksByEmployee(conn, 101);

            deleteEmployee(conn, 102);
            System.out.println("\nСпівробітника 102 видалено.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printEmployees(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Employees";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("ID: %d | Прізвище: %s | Ім'я: %s | Посада: %s\n",
                        rs.getInt("emp_id"), rs.getString("last_name"),
                        rs.getString("first_name"), rs.getString("position"));
            }
        }
    }

    private static void printTasks(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Tasks";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("Task ID: %d | Опис: %s | Emp ID: %d\n",
                        rs.getInt("task_id"), rs.getString("description"), rs.getInt("emp_id"));
            }
        }
    }

    private static void printEmployeesByDept(Connection conn, int deptId) throws SQLException {
        String sql = "SELECT * FROM Employees WHERE dept_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, deptId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("last_name") + " " + rs.getString("first_name"));
            }
        }
    }

    private static void addTask(Connection conn, String desc, int empId) throws SQLException {
        String sql = "INSERT INTO Tasks (description, emp_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, desc);
            pstmt.setInt(2, empId);
            pstmt.executeUpdate();
            System.out.println("\nЗавдання додано успішно!");
        }
    }

    private static void printTasksByEmployee(Connection conn, int empId) throws SQLException {
        String sql = "SELECT description FROM Tasks WHERE emp_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, empId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("- " + rs.getString("description"));
            }
        }
    }

    private static void deleteEmployee(Connection conn, int empId) throws SQLException {
        String sql = "DELETE FROM Employees WHERE emp_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, empId);
            pstmt.executeUpdate();
        }
    }
}