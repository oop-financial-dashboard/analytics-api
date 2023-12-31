package oop.analyticsapi.Repository;

import oop.analyticsapi.Enums.ErrorEnum;
import oop.analyticsapi.Exceptions.GenericException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;

@Component
public class UserPortfolioTransactional {

    @Value("${spring.datasource.url}")
    private String db_url;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;
    private static final String INSERT_USERS_SQL = "INSERT INTO user_portfolio" +
                                                   " (user_id, portfolio_id, description, initial_capital, created_at) VALUES " +
                                                   " (?, ?, ?, ?, ?);" ;
    private static final String UPDATE_PORTFOLIO = "UPDATE user_portfolio" +
            " SET description = ? , initial_capital = ? " +
            "WHERE user_id = ? AND portfolio_id = ?;";

    public String createUserPortfolioRecord(String userId, String portfolioId, String description, Double initialCapital, LocalDate createdAt) throws GenericException {
        // Step 1: Establishing a Connection
        try (Connection connection = DriverManager.getConnection(db_url, user, password);
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, portfolioId);
            preparedStatement.setString(3, description);
            preparedStatement.setDouble(4, initialCapital);
            preparedStatement.setDate(5, java.sql.Date.valueOf(createdAt));

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            return "Success";
        } catch (SQLException e) {
            // print SQL exception information
            System.out.println(e.getMessage());
//            System.out.println("Something went wrong!");
            throw new GenericException(ErrorEnum.ExistingPID);
        }
    }

    public String updateUserPortfolio(String userId, String portfolioId, Double initialCapital, String description) throws SQLException {
        // Step 1: Establishing a Connection
        try (Connection connection = DriverManager.getConnection(db_url, user, password);
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PORTFOLIO)) {
            preparedStatement.setString(1, description);
            preparedStatement.setDouble(2, initialCapital);
            preparedStatement.setString(3, userId);
            preparedStatement.setString(4, portfolioId);

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