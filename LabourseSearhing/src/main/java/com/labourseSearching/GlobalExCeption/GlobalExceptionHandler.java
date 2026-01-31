package com.labourseSearching.GlobalExCeption;

import com.labourseSearching.GlobalExCeption.ExceptionInterface.InvalidPasswordException;
import com.labourseSearching.GlobalExCeption.ExceptionInterface.UserNotFoudnException;
import com.labourseSearching.GlobalExCeption.ExceptionInterface.userNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.InaccessibleObjectException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ============USERNOTFOUNDEXCEPTION =========================================
    @ExceptionHandler(userNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(userNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<String>invalidPassword(InaccessibleObjectException ex ) {
        return  ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ex.getMessage()) ;
    }

    // if user not found in the
    @ExceptionHandler(UserNotFoudnException.class)
    public  ResponseEntity<String>usernotFoundException (UserNotFoudnException ex ) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ex.getMessage()) ;
    }



}