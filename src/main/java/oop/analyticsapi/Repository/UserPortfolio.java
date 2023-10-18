package oop.analyticsapi.Repository;

import org.springframework.beans.factory.annotation.Value;

import java.sql.*;
import java.time.LocalDate;

public class UserPortfolio {
    @Value("${spring.datasource.url}")
    private String db_url;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;
    private static final String INSERT_USERS_SQL = "INSERT INTO user_portfolio" +
                                                   " (userId, portfolioId, createdAt) VALUES " +
                                                   " (?, ?, ?);";

    public String createUserPortfolioRecord(String userId, String portfolioId, LocalDate createdAt) throws SQLException {
        // Step 1: Establishing a Connection
        try (Connection connection = DriverManager.getConnection(db_url, user, password);
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, portfolioId);
            preparedStatement.setDate(3, Date.valueOf(createdAt));

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
            return "Success";
        } catch (SQLException e) {
            // print SQL exception information
            System.out.println(e.getMessage());
            System.out.println("Something went wrong!");
            return "Failed";
        }
    }
}