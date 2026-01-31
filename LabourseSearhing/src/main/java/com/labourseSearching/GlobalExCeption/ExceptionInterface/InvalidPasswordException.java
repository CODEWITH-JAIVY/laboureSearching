package com.labourseSearching.GlobalExCeption.ExceptionInterface;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException (String massage ) {
        super(massage);
    }
}