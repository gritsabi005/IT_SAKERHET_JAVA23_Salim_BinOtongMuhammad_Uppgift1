import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class UserRegistration {
    private Connection connection;
    private static final String BASE_DIRECTORY = Paths.get("").toAbsolutePath().toString() + "/src/Files";

    public void initializeDatabaseConnection() {
        Database.username = "root";
        Database.password = "root";
        Database.port = 3306;
        Database.database = "userregistration2";
        this.connection = Database.getConnection();
        if (this.connection == null) {
            JOptionPane.showMessageDialog((Component)null, "Couldn't connect to the database. Exiting.");
            System.exit(-1);
        } else {
            System.out.println("succeeded");
        }

    }
    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private File getSecureFile(String fileName) throws IOException {
        File file = new File(BASE_DIRECTORY, fileName);
        String canonicalBase = new File(BASE_DIRECTORY).getCanonicalPath();
        String canonicalFile = file.getCanonicalPath();

        // Kontrollera att filen ligger inom den tillåtna katalogen
        if (!canonicalFile.startsWith(canonicalBase)) {
            throw new SecurityException("Otillåten filåtkomst.");
        }
        return file;
    }

    public void saveToDb(int ID, String name, String mail, String telefonnummer, String address, String password){
        initializeDatabaseConnection();
        String hashedPassword = hashPassword(password);  // Hasha lösenordet
        try {

            try (PreparedStatement ps = connection.prepareStatement( "insert into users (name, mail, telefonnummer, address, password) values (?, ?, ?, ?, ?)")){
            //Statement statement = connection.createStatement();
                ps.setString(1, name);
                ps.setString(2, mail);
                ps.setString(3, telefonnummer);
                ps.setString(4, address);
                ps.setString(5, hashedPassword);
            System.out.println();
            ps.executeUpdate();
            }

        } catch(SQLException ex) {
            Database.PrintSQLException(ex);
        }
    }

    public void showAccountsInDb() {
        initializeDatabaseConnection();
        try (PreparedStatement ps = connection.prepareStatement("select * from users");
            ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    int id = rs.getInt("ID");
                    String name = rs.getString("name");
                    String mail = rs.getString("mail");
                    String telefonnummer = rs.getString("telefonnummer");
                    String address = rs.getString("address");
                    String hashedPassword = rs.getString("password");
                    System.out.println(id + ". " + name + ", " + mail + ", " + telefonnummer + ", " + address + ", " + hashedPassword);
                }
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAccount(int removeId) throws SQLException {
        initializeDatabaseConnection();
        try (PreparedStatement ps = connection.prepareStatement("Delete from users where id = " + removeId)){
            ps.executeUpdate();
            System.out.println("Kontot raderas bitch! <3");
        }
    }

}
