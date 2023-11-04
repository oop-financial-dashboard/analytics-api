package oop.analyticsapi.Enums;

public enum ErrorEnum {
    ExistingPID("This portfolio name already exists, please use another portfolio name!"),
    NoStockData("There is no stock data available - this might be the weekend"),
    StockAlreadyExists("Stock already exists in the portfolio!");

    private final String message;

    ErrorEnum(String message) {
        this.message = message;
    }
    @Override
    public String toString() {
        return message;
    }

}
