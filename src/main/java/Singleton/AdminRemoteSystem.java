package Singleton;

import Singleton.Flights.Flights;
import Singleton.Users.Users;

import java.sql.*;
import java.util.Scanner;

public class AdminRemoteSystem {
    public static void main(String[] args) throws SQLException {
        Scanner cin = new Scanner(System.in);

        boolean isOff = true;

        while (isOff) {

        System.out.println("""
                Choose Action:
                1. Add Flight
                2. Flight Schedule
                3. Get User List
                4. Ban user\n\n""");
        int choice = cin.nextInt();

            switch (choice) {
                case 1:
                    Flights.getFlights().createFlight();
                    Flights.getFlights().handleEvent();
                    break;
                case 2:
                    Flights.getFlights().showFlights();
                    break;
                case 3:
                    Users.getUsers().showUsers();
                    break;
                case 4:
                    Users.getUsers().removeUsers(cin);
                    break;
                case 0:
                    isOff = false;
                default:
                    System.out.println("Wrong option.");
                    choice = cin.nextInt();
            }

        }

    }
}
