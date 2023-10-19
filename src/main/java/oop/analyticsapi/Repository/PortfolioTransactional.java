package oop.analyticsapi.Repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
@Component
public class PortfolioTransactional {

    @Value("${spring.datasource.url}")
    private String db_url;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;
    private static final String INSERT_PORTFOLIO_SQL = "INSERT INTO portfolio" +
                                                   " (portfolio_id, quantity, symbol, average_price, total_value) VALUES " +
                                                   " (?, ?, ?, ?, ?);";
    private static final String UPDATE_PORTFOLIO = "UPDATE portfolio " +
                                                   "SET quantity = ?, average_price = ?, total_value = ? " +
                                                   "WHERE portfolio_id = ? AND symbol = ?;";

    public String createPortfolioRecord(String portfolioId, int quantity, String symbol, double averageCost, double totalValue) throws SQLException {
        // Step 1: Establishing a Connection
        try (Connection connection = DriverManager.getConnection(db_url, user, password);
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PORTFOLIO_SQL)) {
            preparedStatement.setString(1, portfolioId);
            preparedStatement.setInt(2, quantity);
            preparedStatement.setString(3, symbol);
            preparedStatement.setDouble(4, averageCost);
            preparedStatement.setDouble(5, totalValue);

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

    public String updatePortfolioRecords(String portfolioId, int quantity, String symbol, double averageCost, double totalValue) throws SQLException {
        // Step 1: Establishing a Connection
        try (Connection connection = DriverManager.getConnection(db_url, user, password);
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PORTFOLIO)) {
            preparedStatement.setInt(1, quantity);
            preparedStatement.setDouble(2, averageCost);
            preparedStatement.setDouble(3, totalValue);
            preparedStatement.setString(4, portfolioId);
            preparedStatement.setString(5, symbol);

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