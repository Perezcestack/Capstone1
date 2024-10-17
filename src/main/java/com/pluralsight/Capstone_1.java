package com.pluralsight;
import javax.sound.sampled.Line;
import java.io.*;
import java.lang.reflect.Array;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

    static List<Transactions> depositList = new ArrayList<>();
    static List<Transactions> paymentList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        LocalDate now = LocalDate.now();
        DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("yyyy/M/d");
//        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("yyyy/MM");
//        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("HH:mm:ss");
        //read file to add stuff to list then parse index 4 to >0 to check if payment if not add to deposit
       //String[] arrayToList = "transactions.csv".split(",");
        String line;
        while ((line = buffReader.readLine()) != null) {
            String[] transactionArray = line.split("\\|");
            Transactions t = new Transactions(LocalDate.parse(transactionArray[0],dateTimeFormatter), LocalTime.parse(transactionArray[1]),transactionArray[2],transactionArray[3],Double.parseDouble(transactionArray[4]));
            if(t.getAmount() > 0){
                depositList.add(t);
            }else {
                paymentList.add(t);
            }
        }
        depositList.sort(Comparator.comparing(Transactions::getDate).thenComparing(Transactions::getTime).reversed());
        paymentList.sort(Comparator.comparing(Transactions::getDate).thenComparing(Transactions::getTime).reversed());


        //First I'm making my home menu and displaying options for the user to select.
        //have to restructure make home menu a static method maybe
        //make sure ledger gets updated as i do payments i can do this by making an arraylist as the transactions get put in.
        //there should be a screen for home ledger then reports.
        //close buffered writer after deposit as it needs to read so it doesnt overwrite the payment.
        boolean home = true;
        boolean ledger = true;
        boolean reports = true;


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
                    ledger = true;
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
                                handleAllReports();
                                break;
                            case "D":
                                    for(Transactions transactions:depositList){
                                        System.out.println(transactions);
                                    }
                                     //might have to make a method for filereader would make more sense than writing it out everytime
                                break;
                            case "P":
                                    for(Transactions transactions:paymentList){
                                        System.out.println(transactions);
                                    }
                                break;

                            case "R":
                                reports = true;
                                ledger = false;
                                //System.out.println(LocalDateTime.now().format(dateTimeFormatter1));
                                System.out.println("Moving to reports");
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                               while(reports) {
                                    System.out.println("_____________________");
                                    System.out.println("Hi, Welcome to reports How would you like to divide the reports?");
                                    System.out.println("(1)Month to Date \n(2)Previous Month \n(3)Year to Date \n(4)Previous Year \n(5)Search by Vendor \n(0)Back to home");
                                    String reportSelect = input.nextLine();

                                   List<Transactions> transactionsListAll = new ArrayList<>();

                                   transactionsListAll.addAll(depositList);
                                   transactionsListAll.addAll(paymentList);

                                    switch (reportSelect) {
                                        case "1":
                                            Month thisMonth = now.getMonth();

                                            transactionsListAll = transactionsListAll.stream().filter(transactions -> transactions.getDate().getMonth() == thisMonth).collect(Collectors.toList());

                                            for (Transactions transactions : transactionsListAll) {
                                                System.out.println(transactions);
                                            }
                                            break;
                                        case "2":
                                            Month previousMonth = now.minusMonths(1).getMonth();

                                            transactionsListAll = transactionsListAll.stream().filter(t -> t.getDate().getMonth() == previousMonth).collect(Collectors.toList());
                                            for (Transactions transactions : transactionsListAll) {
                                                System.out.println(transactions);
                                            }

                                            break;
                                        case "3":
                                            int currentYear = now.getYear();

                                            transactionsListAll = transactionsListAll.stream().filter(transactions -> transactions.getDate().getYear() == currentYear).collect(Collectors.toList());
                                            for(Transactions transactions : transactionsListAll){
                                                System.out.println(transactions);
                                            }
//
                                            break;
                                        case "4":
                                            int previousYear = now.minusYears(1).getYear();

                                            transactionsListAll = transactionsListAll.stream().filter(transactions -> transactions.getDate().getYear() == previousYear).collect(Collectors.toList());
                                            for(Transactions transactions : transactionsListAll){
                                                System.out.println(transactions);
                                            }

                                            break;
                                        case "5":
                                            System.out.println("What vendor are you searching for? ");
                                            String vendor = input.nextLine();

                                            transactionsListAll = transactionsListAll.stream().filter(transactions -> transactions.getVendor().equalsIgnoreCase(vendor)).collect(Collectors.toList());
                                            for (Transactions transactions : transactionsListAll){
                                                System.out.println(transactions);
                                            }

                                        case "0":
                                            System.out.println("Exiting Reports");
                                            System.out.println("________________________");
                                            reports = false;
                                            home = true;
                                            break;

                                    }
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
                    exitProgram();
                    break;

                default:
                    System.out.println("Sorry That input is invalid please try again");
                    continue;

            }
        }
    }
    static void exitProgram(){

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
    }
    static void handleAllReports() {
        List<Transactions> allTransactions = new ArrayList<>();
        allTransactions.addAll(depositList);
        allTransactions.addAll(paymentList);

        allTransactions.sort(Comparator.comparing(Transactions::getDate).thenComparing(Transactions::getTime).reversed());

        for (Transactions transactions : allTransactions) {
            System.out.println(transactions);
        }
    }

    static void handlePayments() {
        try {
            System.out.println("Im going to need details surrounding your payment!");
            System.out.println("Please enter your information in the following format");
            System.out.println("2023/04/15|10:13:25|ergonomic keyboard|Amazon|-89.50");
            String paymentInfo = input.nextLine();
            String[] transactionArray = paymentInfo.split("\\|");
            DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("yyyy/M/d");

            Transactions t = new Transactions(LocalDate.parse(transactionArray[0],dateTimeFormatter), LocalTime.parse(transactionArray[1]),transactionArray[2],transactionArray[3],Double.parseDouble(transactionArray[4]));

            paymentList.add(t);

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
            System.out.println("e.g 2023/04/15|11:15:00|Invoice 1001 paid|Joe|1500.00 ");
            String depositInfo = input.nextLine();
            String[] transactionArray = depositInfo.split("\\|");
            DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("yyyy/M/d");

            Transactions t = new Transactions(LocalDate.parse(transactionArray[0],dateTimeFormatter), LocalTime.parse(transactionArray[1]),transactionArray[2],transactionArray[3],Double.parseDouble(transactionArray[4]));

            depositList.add(t);

            writer.write("\n" + (depositInfo));
            writer.close();

        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}




