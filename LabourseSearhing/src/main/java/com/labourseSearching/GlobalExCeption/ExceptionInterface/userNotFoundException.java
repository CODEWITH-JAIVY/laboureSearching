package com.labourseSearching.GlobalExCeption.ExceptionInterface;

public class userNotFoundException  extends RuntimeException {

    public userNotFoundException(String massage ) {
        super(massage);

    }
}