package oop.analyticsapi.Repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;

@Component
public class PortfolioTransactional {

    @Value("${spring.datasource.url}")
    private String db_url;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;
    private static final String INSERT_PORTFOLIO_SQL = "INSERT INTO portfolio" +
                                                   " (user_id, portfolio_id, quantity, symbol, average_price, total_value, date_added) VALUES " +
                                                   " (?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_PORTFOLIO = "UPDATE portfolio " +
                                                   "SET quantity = ?, average_price = ?, total_value = ? " +
                                                   "WHERE user_id = ? AND portfolio_id = ? AND symbol = ?;";

    public String createPortfolioRecord(
            String userId,
            String portfolioId,
            int quantity,
            String symbol,
            double averageCost,
            double totalValue,
            LocalDate dateAdded
    ) throws SQLException {
        // Step 1: Establishing a Connection
        try (Connection connection = DriverManager.getConnection(db_url, user, password);
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PORTFOLIO_SQL)) {
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, portfolioId);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setString(4, symbol);
            preparedStatement.setDouble(5, averageCost);
            preparedStatement.setDouble(6, totalValue);
            preparedStatement.setDate(7, Date.valueOf(dateAdded));

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            return "Success";
        } catch (SQLException e) {
            // print SQL exception information
            System.out.println(e.getMessage());
            System.out.println("Something went wrong!");
            return "Failed";
        }
    }

    public String updatePortfolioRecords(String userId, String portfolioId, int quantity, String symbol, double averageCost, double totalValue) throws SQLException {
        // Step 1: Establishing a Connection
        try (Connection connection = DriverManager.getConnection(db_url, user, password);
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PORTFOLIO)) {
            preparedStatement.setInt(1, quantity);
            preparedStatement.setDouble(2, averageCost);
            preparedStatement.setDouble(3, totalValue);
            preparedStatement.setString(4, userId);
            preparedStatement.setString(5, portfolioId);
            preparedStatement.setString(6, symbol);

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            return "Success";
        } catch (SQLException e) {
            // print SQL exception information
            System.out.println(e.getMessage());
            System.out.println("Something went wrong!");
            return "Failed";
        }
    }
}