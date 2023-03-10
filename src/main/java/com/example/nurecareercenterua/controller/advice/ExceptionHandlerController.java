package com.example.nurecareercenterua.controller.advice;

import com.example.nurecareercenterua.domain.account.exception.AccountNotFoundException;
import com.example.nurecareercenterua.domain.account.exception.EmailAlreadyRegisteredException;
import com.example.nurecareercenterua.domain.account.exception.IllegalPasswordArgumentException;
import com.example.nurecareercenterua.domain.account.exception.PhoneAlreadyRegisteredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler({EmailAlreadyRegisteredException.class,
            PhoneAlreadyRegisteredException.class, IllegalPasswordArgumentException.class})
    public ResponseEntity<?> handlerAccountException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<?> handlerNotFoundAccountException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
