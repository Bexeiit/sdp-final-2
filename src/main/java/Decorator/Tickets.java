package Decorator;

import Database.DatabaseConnection;

import java.sql.*;
import java.util.Scanner;

public class Tickets {

    public void selectFlight(Scanner cin, String email){
        try{
            Connection connection = DatabaseConnection.ConnectionDB();

            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM flights";
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                System.out.println("Flight Nummber|| From City || To City || Departure Date || Departure Time || Arrival Date || Arrival Time||");

                String flightNumber = resultSet.getString("flight_number");
                String fromCity = resultSet.getString("from_city");
                String toCity = resultSet.getString("to_city");
                java.sql.Date departureDate = resultSet.getDate("departure_date");
                java.sql.Time departureTime = resultSet.getTime("departure_time");
                java.sql.Date arrivalDate = resultSet.getDate("arrival_date");
                java.sql.Time arrivalTime = resultSet.getTime("arrival_time");

                System.out.printf("%s\t\t\t%s\t\t%s \t%s\t%s\t\t%s\t\t%s%n",
                        flightNumber, fromCity, toCity, departureDate, departureTime, arrivalDate, arrivalTime);
                connection.close();
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.print("Jaz flight number: ");
        String flightNum = cin.next();
        try {
            Connection connection = DatabaseConnection.ConnectionDB();
            Statement statement = connection.createStatement();
            String sql = "SELECT COUNT(*) FROM flights WHERE flight_number = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, flightNum);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int flightExists = rs.getInt(1);
                if (flightExists>0) {

                } else {
                    System.err.println("Invalid flight number or flight number does not exist");
                    flightNum = cin.next();
                }
            }

//            initingFlightNumberToUser(flightNum, email);
            buy();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initingFlightNumberToUser(String flightNumber, String email) throws SQLException {
        Connection connection = DatabaseConnection.ConnectionDB();
        String sql = "UPDATE users SET flight_number = ? WHERE email = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, flightNumber);
        ps.setString(2, email);

        int affectedRows = ps.executeUpdate(); // Используйте executeUpdate() для UPDATE запроса

        connection.close();
    }

    public void buy(){

    }
}
