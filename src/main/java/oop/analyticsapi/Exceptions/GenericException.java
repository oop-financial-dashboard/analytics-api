package oop.analyticsapi.Exceptions;

import lombok.Getter;
import oop.analyticsapi.Enums.ErrorEnum;

@Getter
public class GenericException extends RuntimeException {

    // If you need to retrieve the ErrorEnum later, you can provide a getter for it
    private final ErrorEnum errorEnum;

    public GenericException(ErrorEnum errorEnum) {
        super(errorEnum.toString()); // Call the constructor of RuntimeException with the custom message from the enum
        this.errorEnum = errorEnum;
    }

}
