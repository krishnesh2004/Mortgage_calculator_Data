// Libraries import kiye hain jo code me use honge
import java.awt.*;    // is program me actually use nahi ho raha
import java.lang.*;   // yeh default hota hai, likhne ki zarurat nahi
import java.util.Arrays;  // use nahi ho raha
import java.util.Scanner; // input lene ke liye
import java.text.NumberFormat; // currency format dikhane ke liye

public class Main {
    final static byte MONTHS_IN_YEAR = 12; // ek saal me kitne months
    final static byte PERCENT = 100;       // percentage calculation ke liye

    public static void main(String[] args)
    {
        // user se principal (loan amount), interest rate aur years input le rahe hain
        int principal = (int) readNumber("Principal: ", 1000, 1_000_000);
        float annualInterest = (float) readNumber("Annual Interest Rate: ", 1, 30);
        byte years = (byte) readNumber("Period (Years): ", 1, 30);

        // monthly mortgage print karna
        printMortgage(principal, annualInterest, years);

        // pura payment schedule print karna (har month kitna bacha)
        printPaymentSchedule(principal, annualInterest, years);
    }

    private static void printMortgage(int principal, float annualInterest, byte years)
    {
        double mortgage = calculateMortgage(principal, annualInterest, years);
        String mortgageFormatted = NumberFormat.getCurrencyInstance().format(mortgage);

        System.out.println();
        System.out.println("MORTGAGE");
        System.out.println("--------");
        System.out.println("Monthly Payments: " + mortgageFormatted);
    }

    private static void printPaymentSchedule(int principal, float annualInterest, byte years)
    {
        System.out.println();
        System.out.println("PAYMENT SCHEDULE");
        System.out.println("----------------");

        for (short month = 1; month <= years * MONTHS_IN_YEAR; month++) {
            double balance = calculateBalance(principal, annualInterest, years, month);
            System.out.println(NumberFormat.getCurrencyInstance().format(balance));
        }
    }

    public static double readNumber(String prompt, double min, double max)
    {
        // user input ko repeatedly tab tak lete hain jab tak valid nahi deta
        Scanner scanner = new Scanner(System.in);
        double value;
        while (true) {
            System.out.print(prompt);
            value = scanner.nextFloat();
            if (value >= min && value <= max)
                break;
            System.out.println("Enter a value between " + min + " and " + max);
        }
        return value;
    }

    public static double calculateBalance(int principal,float annualInterest, byte years, short numberOfPaymentsMade)
    {
        // monthly interest rate nikalna
        float monthlyInterest = annualInterest / PERCENT / MONTHS_IN_YEAR;
        float numberOfPayments = years * MONTHS_IN_YEAR;

        // formula se remaining balance nikalna
        double balance = principal
                * (Math.pow(1 + monthlyInterest, numberOfPayments) - Math.pow(1 + monthlyInterest, numberOfPaymentsMade))
                / (Math.pow(1 + monthlyInterest, numberOfPayments) - 1);

        return balance;
    }

    public static double calculateMortgage(
            int principal,
            float annualInterest,
            byte years) {

        // monthly interest aur total number of payments nikalna
        float monthlyInterest = annualInterest / PERCENT / MONTHS_IN_YEAR;
        float numberOfPayments = years * MONTHS_IN_YEAR;

        // formula lagana monthly payment nikalne ke liye
        double mortgage = principal
                * (monthlyInterest * Math.pow(1 + monthlyInterest, numberOfPayments))
                / (Math.pow(1 + monthlyInterest, numberOfPayments) - 1);

        return mortgage;
    }
}
