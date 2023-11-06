package org.example;

import Database.DatabaseConnection;
import Decorator.UserRemoteSystem;
import Singleton.AdminRemoteSystem;

import java.sql.*;
import java.util.Scanner;

public class LogIn {
    Scanner cin = new Scanner(System.in);

    public void logInAs() throws SQLException {
        System.out.println("""
                I'm a:
                1. Customer
                2. Administrator""");
        int logIn = cin.nextInt();

        boolean isClear = true;
        while(isClear){
            if(logIn < 1 || logIn > 2) {
                System.out.println("Wrong option.");
                logIn = cin.nextInt();
            }
            else {
                isClear = false;
            }
        }

        switch (logIn) {
            case 1 -> logInAsCustomer();
            case 2 -> logInAsAdministrator();
        }
    }
    public void logInAsCustomer() {
        System.out.print("Enter your E-Mail: ");
        String email = cin.next();

        boolean isEmailExists = true;
        while (isEmailExists) {
            try {
                Connection connection = DatabaseConnection.ConnectionDB();
                String emailSql = "SELECT COUNT(*) FROM users WHERE email = ?";
                PreparedStatement emailStatement = connection.prepareStatement(emailSql);
                emailStatement.setString(1, email);
                ResultSet emailResultSet = emailStatement.executeQuery();

                if (emailResultSet.next()) {
                    int emailCount = emailResultSet.getInt(1);
                    if (emailCount > 0) {
                        isEmailExists = false;
                    } else {
                        System.out.println("Invalid E-Mail or you're not registered yet. Try again.");
                        email = cin.next();
                    }
                }

                // После того как email подтвержден, мы можем получить flight_number
                String flightNumber = null;
                if (!isEmailExists) {
                    String flightSql = "SELECT flight_number FROM users WHERE email = ?";
                    PreparedStatement flightStatement = connection.prepareStatement(flightSql);
                    flightStatement.setString(1, email);
                    ResultSet flightResultSet = flightStatement.executeQuery();

                    if (flightResultSet.next()) {
                        flightNumber = flightResultSet.getString("flight_number");
                    }
                }

                connection.close();

                System.out.print("Enter password: ");
                String password = cin.next();

                boolean isPasswordExists = true;
                while (isPasswordExists) {
                    try {
                        connection = DatabaseConnection.ConnectionDB();
                        String passwordSql = "SELECT COUNT(*) FROM users WHERE password = ?";
                        PreparedStatement passwordStatement = connection.prepareStatement(passwordSql);
                        passwordStatement.setString(1, password);
                        ResultSet passwordResultSet = passwordStatement.executeQuery();

                        if (passwordResultSet.next()) {
                            int passwordCount = passwordResultSet.getInt(1);
                            if (passwordCount > 0) {
                                isPasswordExists = false;
                                System.out.println("\n You've successfully logged in.");
                                // передал короче Flightnumber в RemoteSystem
                                UserRemoteSystem.userRemote(flightNumber, email);
                            } else {
                                System.out.println("Invalid Password. Try again.");
                                password = cin.next();
                            }
                        }


                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void logInAsAdministrator() throws SQLException {
        System.out.print("Enter your E-Mail: ");
        String email = cin.next();

        boolean isEmailExists = true;
        while (isEmailExists) {
            try {
                Connection connection = DatabaseConnection.ConnectionDB();
                String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    if (count > 0) {
                        String checkRole = "SELECT COUNT(*) FROM users WHERE email = ? AND role = 'admin'";
                        PreparedStatement ps = connection.prepareStatement(checkRole);
                        ps.setString(1, email);
                        ResultSet rs = ps.executeQuery();

                        if (rs.next()) {
                            int countRole = rs.getInt(1);
                            if (countRole > 0) {
                                isEmailExists = false;
                            } else {
                                System.out.println("Invalid E-Mail or you are not an Admin. Try again.");
                                email = cin.next();
                            }
                        }
                    } else {
                        System.out.println("Invalid E-Mail or you are not registered yet. Try again.");
                        email = cin.next();
                    }
                }

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        System.out.print("Enter password: ");
        String password = cin.next();

        boolean isPasswordExists = true;
        while (isPasswordExists) {
            try {
                Connection connection = DatabaseConnection.ConnectionDB();
                String sql = "SELECT COUNT(*) FROM users WHERE password = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, password);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (((ResultSet) resultSet).next()) {
                    int count = resultSet.getInt(1);
                    if (count > 0) {
                        isPasswordExists = false;
                        System.out.println("\n You've successfully logged in.");
                    }
                    else {
                        System.out.println("Invalid Password. Try again.");
                        password = cin.next();
                    }
                }

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        AdminRemoteSystem.adminRemote();
    }
}
