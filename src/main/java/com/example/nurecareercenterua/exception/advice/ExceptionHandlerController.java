package com.example.nurecareercenterua.exception.advice;

import com.example.nurecareercenterua.domain.account.exception.AccountNotFoundException;
import com.example.nurecareercenterua.domain.account.exception.EmailAlreadyRegisteredException;
import com.example.nurecareercenterua.domain.account.exception.IllegalPasswordArgumentException;
import com.example.nurecareercenterua.domain.account.exception.PhoneAlreadyRegisteredException;
import com.example.nurecareercenterua.exception.model.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler({EmailAlreadyRegisteredException.class,
            PhoneAlreadyRegisteredException.class,
            IllegalPasswordArgumentException.class})
    public ResponseEntity<?> handlerAccountException(Exception e) {
        var status = HttpStatus.CONFLICT;
        var response = generateResponse(e.getMessage(), status);

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<?> handlerNotFoundAccountException(Exception e) {
        var status = HttpStatus.NOT_FOUND;
        var response = generateResponse(e.getMessage(), status);

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleFieldsException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    private HttpResponse generateResponse(String message, HttpStatus status) {
        return new HttpResponse(status.name(), status.value(), message, new Date());
    }
}
