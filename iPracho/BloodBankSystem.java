import java.sql.*;
import java.util.Scanner;

public class BloodBankSystem {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/bloodbankdb";
        String user = "root";
        String password = "M@nsi120";  // change to your MySQL password
        Scanner sc = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Connected to Blood Bank Database");

            while (true) {
                System.out.println("\n=== BLOOD BANK MANAGEMENT SYSTEM ===");
                System.out.println("1. Add Donor");
                System.out.println("2. View All Donors");
                System.out.println("3. Update Donor");
                System.out.println("4. Delete Donor");
                System.out.println("5. Search Donor by ID");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        addDonor(con, sc);
                        break;
                    case 2:
                        viewDonors(con);
                        break;
                    case 3:
                        updateDonor(con, sc);
                        break;
                    case 4:
                        deleteDonor(con, sc);
                        break;
                    case 5:
                        searchDonor(con, sc);
                        break;
                    case 6:
                        System.out.println("🚪 Exiting... Goodbye!");
                        con.close();
                        sc.close();
                        System.exit(0);
                    default:
                        System.out.println("❌ Invalid choice! Try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ➕ Add New Donor
    public static void addDonor(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter Donor ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Blood Group: ");
        String group = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Gender: ");
        String gender = sc.nextLine();
        System.out.print("Enter Contact No: ");
        String contact = sc.nextLine();
        System.out.print("Enter City: ");
        String city = sc.nextLine();

        String query = "INSERT INTO donor VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, id);
        pst.setString(2, name);
        pst.setString(3, group);
        pst.setInt(4, age);
        pst.setString(5, gender);
        pst.setString(6, contact);
        pst.setString(7, city);

        pst.executeUpdate();
        System.out.println("✅ Donor added successfully!");
        pst.close();
    }

    // 📋 View All Donors
    public static void viewDonors(Connection con) throws SQLException {
        String query = "SELECT * FROM donor";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        System.out.printf("%-5s %-20s %-10s %-5s %-10s %-15s %-10s%n",
                "ID", "Name", "Group", "Age", "Gender", "Contact", "City");
        System.out.println("---------------------------------------------------------------");

        while (rs.next()) {
            System.out.printf("%-5d %-20s %-10s %-5d %-10s %-15s %-10s%n",
                    rs.getInt("donor_id"),
                    rs.getString("name"),
                    rs.getString("blood_group"),
                    rs.getInt("age"),
                    rs.getString("gender"),
                    rs.getString("contact_no"),
                    rs.getString("city"));
        }
        rs.close();
        st.close();
    }

    // ✏️ Update Donor Details
    public static void updateDonor(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter Donor ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter new Contact No: ");
        String contact = sc.nextLine();
        System.out.print("Enter new City: ");
        String city = sc.nextLine();

        String query = "UPDATE donor SET contact_no=?, city=? WHERE donor_id=?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, contact);
        pst.setString(2, city);
        pst.setInt(3, id);

        int rows = pst.executeUpdate();
        if (rows > 0) {
            System.out.println("✅ Donor updated successfully!");
        } else {
            System.out.println("❌ Donor ID not found!");
        }
        pst.close();
    }

    // ❌ Delete Donor
    public static void deleteDonor(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter Donor ID to delete: ");
        int id = sc.nextInt();

        String query = "DELETE FROM donor WHERE donor_id=?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, id);

        int rows = pst.executeUpdate();
        if (rows > 0) {
            System.out.println("✅ Donor deleted successfully!");
        } else {
            System.out.println("❌ Donor ID not found!");
        }
        pst.close();
    }

    // 🔍 Search Donor by ID
    public static void searchDonor(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter Donor ID: ");
        int id = sc.nextInt();

        String query = "SELECT * FROM donor WHERE donor_id=?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            System.out.println("\n📋 Donor Details:");
            System.out.println("ID: " + rs.getInt("donor_id"));
            System.out.println("Name: " + rs.getString("name"));
            System.out.println("Blood Group: " + rs.getString("blood_group"));
            System.out.println("Age: " + rs.getInt("age"));
            System.out.println("Gender: " + rs.getString("gender"));
            System.out.println("Contact No: " + rs.getString("contact_no"));
            System.out.println("City: " + rs.getString("city"));
        } else {
            System.out.println("❌ No donor found with ID " + id);
        }

        rs.close();
        pst.close();
    }
}
