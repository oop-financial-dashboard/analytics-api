package oop.analyticsapi.Repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
public class PortfolioHistoricalValueTransactional {
    @Value("${spring.datasource.url}")
    private String db_url;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;
    private static final String INSERT_HISTORICALS_SQL = "INSERT INTO portfolio_historicals" +
            " (user_id, portfolio_id, total_value, date) VALUES " +
            " (?, ?, ?, ?);";

    public String insertDailyPortfolioValue(String userId, String portfolioId, Double totalValue, LocalDate date) throws SQLException {
        // Step 1: Establishing a Connection
        try (Connection connection = DriverManager.getConnection(db_url, user, password);
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_HISTORICALS_SQL)) {
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, portfolioId);
            preparedStatement.setDouble(3, totalValue);
            preparedStatement.setDate(4, java.sql.Date.valueOf(date));

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
            preparedStatement.close();

            return "Success";
        } catch (SQLException e) {
            // print SQL exception information
            System.out.println(e.getMessage());
            System.out.println("Something went wrong!");
            return "Failed";
        }
    }
}
