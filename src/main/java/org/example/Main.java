package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        boolean isTuredOff = true;
        while (isTuredOff) {
            System.out.println("""
                
                Welcome to BYE Airlines! Select one of the options:
                
                1. Sign up
                2. Log in
                0. Exit
                """);
            int choice = cin.nextInt();

            boolean isClear = true;
            while(isClear){
                if(choice < 0 || choice > 2) {
                    System.out.println("Wrong option.");
                    choice = cin.nextInt();
                }
                else {
                    isClear = false;
                }
            }

            switch (choice) {
                case 1:
                    SignUp signUp = new SignUp();
                    signUp.SignAs();
                    break;
                case 2:
                    LogIn logIn = new LogIn();
                    logIn.logInAs();
                    break;
                default:
                    isTuredOff = false;
            }

        }
    }
}