package com.labourseSearching.GlobalExCeption.ExceptionInterface;

public class UserNotFoudnException extends  RuntimeException {

    public UserNotFoudnException(String massage ) {
          super(massage);
    }
}