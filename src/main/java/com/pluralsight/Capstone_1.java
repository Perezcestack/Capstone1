package com.pluralsight;
import javax.sound.sampled.Line;
import java.io.*;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Capstone_1 {
    //psv innit and put all 17-31 in method

    static Scanner input = new Scanner(System.in);
    static FileReader fileReader;

    static {
        try {
            fileReader = new FileReader("transactions.csv");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static BufferedReader buffReader = new BufferedReader(fileReader);
    static FileWriter writer;

    static {
        try {
            writer = new FileWriter("transactions.csv", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static List<String> depositList = new ArrayList<>();
    static List<String> paymentList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        DateTimeFormatter.ofPattern("yyyy/MM/dd");

        //read file to add stuff to list then parse index 4 to >0 to check if payment if not add to deposit
       //String[] arrayToList = "transactions.csv".split(",");
        String line;

        while ((line = buffReader.readLine()) != null) {
            String[] transactionArray = line.split("\\|");
            if(Double.parseDouble(transactionArray[4]) > 0){
                depositList.add(line);
            }else {
                paymentList.add(line);
            }
        }

        //First I'm making my home menu and displaying options for the user to select.
        //have to restructure make home menu a static method maybe
        //make sure ledger gets updated as i do payments i can do this by making an arraylist as the transactions get put in.
        //there should be a screen for home ledger then reports.
        //close buffered writer after deposit as it needs to read so it doesnt overwrite the payment.
        boolean home = true;
        boolean ledger = true;


        while (home) {
            System.out.println("Hello! Welcome to the CLI finance App ");
            System.out.println("-------------------------------------");
            System.out.println("Please select one our options below!");
            System.out.println("(D) Add Deposit \n(P) Make Payment (Debit) \n(L) Displays Ledger Menu \n(X) Exit Application");
            String selection1 = input.nextLine();
            //make seperate methods for cases body is becoming enlarged


            switch (selection1.toUpperCase()) {
                case "D":
                    // in this case im writing a edit onto a csv file based on the info they put in, It should all be in one main csv
                    //Make sure to append or else im rewriting the file every time
                    handleDeposit();
                    break;
                case "P":
                    handlePayments();
                    break;
                case "L":
                    home = false;
                    //should open a ledger screen pressing a should print all transactions etc
                    //there should be a function that sorts all transactions and stuff
                    System.out.println("Moving to Ledger");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    while(ledger) { //should move to seperate function
                        System.out.println("______________________");
                        System.out.println("Welcome to the ledger, Please select an option: ");
                        System.out.println("(A) Display All reports.\n(D) Display Deposits into the account\n(P) Display all negative entries (payments)\n(R) Display Reports\n(H) Navigate back to home.");
                        String ledgerSelection = input.nextLine();


                        switch (ledgerSelection.toUpperCase()) {
                            case "A":
                                try {

                                    FileReader fileread = new FileReader("transactions.csv");
                                    BufferedReader bufReader1 = new BufferedReader(fileread);

                                    String readPut;

                                    while ((readPut = bufReader1.readLine()) != null) {
                                        System.out.println(readPut);
                                    }
                                    bufReader1.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case "D":
                                try {

                                    FileReader fileread = new FileReader("transactions.csv");
                                    BufferedReader bufferedRead2 = new BufferedReader(fileread);
                                    while ((line = bufferedRead2.readLine()) != null) {
                                        String[] transactionArray = line.split("\\|");
                                        if (Double.parseDouble(transactionArray[4]) > 0) {
                                            System.out.println(line);
                                        }

                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }       //might have to make a method for filereader would make more sense than writing it out everytime
                                break;
                            case "P":
                                try {
                                    FileReader fileReadPay = new FileReader("transactions.csv");
                                    BufferedReader buffReaderPay = new BufferedReader(fileReadPay);

                                    while ((line = buffReaderPay.readLine()) != null) {
                                        String[] transactionArray = line.split("\\|");
                                        if (Double.parseDouble(transactionArray[4]) < 0) {
                                            System.out.println(line);
                                        }
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;

                            case "H":
                                ledger = false;
                                home = true;
                        }
                        // if statement to make when H is selected home will equal true
                        //create loops to make report\
                    }
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

    static void handlePayments() {
        try {
            System.out.println("Im going to need your debit information");
            System.out.println("Please enter your information in the following format");
            System.out.println("2023-04-15|10:13:25|ergonomic keyboard|Amazon|-89.50");
            String paymentInfo = input.nextLine();


            paymentList.add(paymentInfo);

            writer.write("\n" + paymentInfo);
            writer.close();

        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void handleDeposit() {
        try {
            System.out.println("Im going to need your deposit information");
            System.out.println("Please enter all the info in this exact format (MM/dd/yyyy)|(Military time e.g 17:00)| description | amount");
            System.out.println("e.g 2023-04-15|11:15:00|Invoice 1001 paid|Joe|1500.00 ");
            String depositInfo = input.nextLine();
            //split using |

            depositList.add(depositInfo);

            writer.write("\n" + (depositInfo));
            writer.close();

        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}




