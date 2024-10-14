package com.pluralsight;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class Capstone_1 {

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        DateTimeFormatter.ofPattern("yyyy/MM/dd");
        //First I'm making my home menu and displaying options for the user to select.
        //have to restructure make home menu a static method maybe
        //make sure ledger gets updated as i do payments i can do this by making an arraylist as the transactions get put in.
        //there should be a screen for home ledger then reports.
        //close buffered writer after deposit as it needs to read so it doesnt overwrite the payment.
            boolean home = true;
            while(home){
                System.out.println("Hello! Welcome to the CLI finance App ");
                System.out.println("-------------------------------------");
                System.out.println("Please select one our options below!");
                System.out.println("(D) Add Deposit \n(P) Make Payment (Debit) \n(L) Displays Ledger Menu \n(X) Exit Application");
                String selection1 = input.nextLine();
                //make seperate methods for cases body is becoming enlarged

            switch (selection1.toUpperCase()) {
                case "D":
                    //in this case im writing a edit onto a csv file based on the info they put in, It should all be in one main csv
                    //Make sure to append or else im rewriting the file every time
                    try {
                        System.out.println("Im going to need your deposit information");
                        System.out.println("Please enter all the info in this exact format (MM/dd/yyyy)|(Military time e.g 17:00)| description | amount");
                        System.out.println("e.g 2023-04-15|11:15:00|Invoice 1001 paid|Joe|1500.00 ");
                        String depositInfo = input.nextLine();
                        //split using |
                        String[] depositArray = depositInfo.split("\\|");

                        FileWriter writeon = new FileWriter( "transactions.csv", true);

                        writeon.write(Arrays.toString(depositArray) + "Added on " );
                        writeon.close();

                    } catch (RuntimeException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "P":
                    try{
                    System.out.println("Im going to need your debit information");
                    System.out.println("Please enter your information in the following format");
                    System.out.println("2023-04-15|10:13:25|ergonomic keyboard|Amazon|-89.50");
                    String paymentInfo = input.nextLine();

                    FileWriter writeon = new FileWriter( "transactions.csv", true);
                    String[] paymentArray = paymentInfo.split("\\|");

                    writeon.write(Arrays.toString(paymentArray) + "Added on ");
                    writeon.close();

                    } catch (RuntimeException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "L": home = false;
                    //should open a ledger screen pressing a should print all transactions etc
                    //there should be a function that sorts all transactions and stuff
                    System.out.println("Moving to Ledger");
                    try {
                        System.out.println("______________________");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println("Welcome to the ledger, Please select an option: ");
                    System.out.println("(A) Display All reports.\n(D) Display Deposits into the account\n(P) Display all negative entries (payments)\n(R) Display Reports\n(H) Navigate back to home.");
                    String ledgerSelection = input.nextLine();

                    switch (ledgerSelection.toUpperCase()){
                        case "A":
                            try {

                                FileReader fileread = new FileReader("transactions.csv");
                                BufferedReader bufReader1 = new BufferedReader(fileread);

                                String readPut;

                                while ((readPut = bufReader1.readLine()) != null) {
                                    System.out.println(readPut);
                                }
                                bufReader1.close();
                            }catch(IOException e) {
                                e.printStackTrace();
                            }break;
                        case "H":
                            home = true;
                    }
                    // if statement to make when H is selected home will equal true

                    break;

                case "X":

                    System.out.println("Thank you for choosing CLI Finance App,Have a wonderful Day");
                    System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀      ⢀⡤⠒⠒⠢⢄⡀⢠⡏⠉⠉⠉⠑⠒⠤⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀  ⢀⡞⠀⠀⠀⠀⠀⠙⢦⠀⡇⡇⠀⠀⠀⠀⠀⠀ ⠈⠱⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⠊⠉⠉⠙⠒⢤⡀⠀⣼⠀⠀⢀⣶⣤⠀⠀⠀⢣⡇⡇⠀⠀⢴⣶⣦⠀⠀⠀⢳⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⢀⣠⠤⢄⠀⠀⢰⡇⠀⠀⣠⣀⠀⠀⠈⢦⡿⡀⠀⠈⡟⣟⡇⠀⠀⢸⡇⡆⠀⠀⡼⢻⣠⠀⠀⠀⣸⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⢀⠖⠉⠀⠀⠀⣱⡀⡞⡇⠀⠀⣿⣿⢣⠀⠀⠈⣧⣣⠀⠀⠉⠋⠀⠀⠀⣸⡇⠇⠀⠀⠈⠉⠀⠀⠀⢀⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⣠⠏⠀⠀⣴⢴⣿⣿⠗⢷⡹⡀⠀⠘⠾⠾⠀⠀⠀⣿⣿⣧⡀⠀⠀⠀⢀⣴⠇⣇⣆⣀⢀⣀⣀⣀⣀⣤⠟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⣿⠀⠀⢸⢻⡞⠋⠀⠀⠀⢿⣷⣄⠀⠀⠀⠀⠀⣠⡇⠙⢿⣽⣷⣶⣶⣿⠋⢰⣿⣿⣿⣿⣿⣿⠿⠛⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⡿⡄⠀⠈⢻⣝⣶⣶⠀⠀⠀⣿⣿⣱⣶⣶⣶⣾⡟⠀⠀⠀⢈⡉⠉⢩⡖⠒⠈⠉⡏⡴⡏⠉⠉⠉⠉⠉⠉⠉⠉⡇⠀⠀⢀⣴⠒⠢⠤⣀\n" +
                            "⢣⣸⣆⡀⠀⠈⠉⠁⠀⠀⣠⣷⠈⠙⠛⠛⠛⠉⢀⣴⡊⠉⠁⠈⢢⣿⠀⠀⠀⢸⠡⠀⠁⠀⠀⠀⣠⣀⣀⣀⣀⡇⠀⢰⢁⡇⠀⠀⠀⢠\n" +
                            "⠀⠻⣿⣟⢦⣤⡤⣤⣴⣾⡿⢃⡠⠔⠒⠉⠛⠢⣾⢿⣿⣦⡀⠀⠀⠉⠀⠀⢀⡇⢸⠀⠀⠀⠀⠀⠿⠿⠿⣿⡟⠀⢀⠇⢸⠀⠀⠀⠀⠘\n" +
                            "⠀⠀⠈⠙⠛⠿⠿⠿⠛⠋⢰⡋⠀⠀⢠⣤⡄⠀⠈⡆⠙⢿⣿⣦⣀⠀⠀⠀⣜⠀⢸⠀⠀⠀⠀⠀⠀⠀⠀⢀⠃⠀⡸⠀⠇⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡇⢣⠀⠀⠈⠛⠁⠀⢴⠥⡀⠀⠙⢿⡿⡆⠀⠀⢸⠀⢸⢰⠀⠀⠀⢀⣿⣶⣶⡾⠀⢀⠇⣸⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠹⡀⢇⠀⠀⠀⢀⡀⠀⠀⠈⢢⠀⠀⢃⢱⠀⠀⠀⡇⢸⢸⠀⠀⠀⠈⠉⠉⠉⢱⠀⠼⣾⣿⣿⣷⣦⠴⠀⠀\n" +
                            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢱⠘⡄⠀⠀⢹⣿⡇⠀⠀⠈⡆⠀⢸⠈⡇⢀⣀⣵⢨⣸⣦⣤⣤⣄⣀⣀⣀⡞⠀⣠⡞⠉⠈⠉⢣⡀⠀⠀\n" +
                            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢃⠘⡄⠀⠀⠉⠀⠀⣠⣾⠁⠀⠀⣧⣿⣿⡿⠃⠸⠿⣿⣿⣿⣿⣿⣿⠟⠁⣼⣾⠀⠀⠀⠀⢠⠇⠀⠀\n" +
                            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⡄⠹⣀⣀⣤⣶⣿⡿⠃⠀⠀⠀⠈⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠁⠀⠀⢻⣿⣷⣦⣤⣤⠎⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⣤⣿⡿⠟⠛⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠉⠉⠀⠀");
                        System.exit(0);
                    break;

                default:
                    System.out.println("Sorry That input is invalid please try again");
                    continue;

            }
        }
    }
}

