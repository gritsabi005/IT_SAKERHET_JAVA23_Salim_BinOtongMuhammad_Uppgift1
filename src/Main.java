import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static int adder = 0;
    public static void main(String[] args) throws SQLException {
        UserRegistration userRegistration = new UserRegistration();
        userRegistration.initializeDatabaseConnection();

        Scanner scanner = new Scanner(System.in);

        // Ta in användarinmatning

        int answer;
        do{
            System.out.print("NyhetsWebbplats Kontohantering \n");
            showMenu();
             answer = scanner.nextInt();
            if (answer == 1){
                createAccount();
            } else if (answer == 2) {
                userRegistration.showAccountsInDb();
            } else if (answer == 3) {
                System.out.println("Ange ID-et att radera: ");
                int removedId = scanner.nextInt();
                userRegistration.deleteAccount(removedId);
            } else if (answer == 4) {
                System.exit(1);
            }
        } while (answer != 4);
    }
    

    static void showMenu(){
        System.out.println("1. Skapa Konto");
        System.out.println("2. Visa Konto");
        System.out.println("3. Radera Konto");
        System.out.println("4. Avsluta");
        System.out.println("Välj ett alternativ: ");
    }

    static void createAccount(){
        Scanner scanner = new Scanner(System.in);
        /*adder = adder + 1;
        if (adder >= 1){
            adder = adder + 1;
        }*/
        int id = adder;
        System.out.println("Ange fullnamn: ");
        String namn = scanner.nextLine();
        System.out.println("Ange E-post: ");
        String mail = scanner.nextLine();
        System.out.println("Ange telefon nummer: ");
        String telefonnummer = scanner.nextLine();
        System.out.println("Ange adress (Gatuadress, Lägenhet nummer, post nummer, och ort): ");
        String address = scanner.nextLine();
        /*System.out.println("Ange ålder: ");
        String age = scanner.nextLine();
        System.out.println("Ange kön (M/F/Other): ");
        String sex = scanner.nextLine();*/
        System.out.println("Ange password: ");
        String password = scanner.nextLine();
        UserRegistration userRegistration = new UserRegistration();
        //userRegistration.saveToFile(id, namn, mail, age, sex, password);
        userRegistration.saveToDb(id, namn, mail, telefonnummer, address, password);
    }
}