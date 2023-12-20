package az.ubank.msexchange.controller.error;

import az.ubank.msexchange.model.ErrorDto;
import az.ubank.msexchange.exception.ClientServerException;
import az.ubank.msexchange.exception.InvalidAmountException;
import az.ubank.msexchange.exception.InvalidCurrencyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static az.ubank.msexchange.exception.ErrorCodes.*;


@ControllerAdvice
@Slf4j
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<Object> handleInvalidAmountException(InvalidAmountException ex,
                                                                 WebRequest webRequest) {
        log.info(ex.toString());

        return handleExceptionInternal(ex, ErrorDto.builder()
                        .code(INVALID_AMOUNT)
                        .message(ex.getMessage())
                        .build(),
                new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, webRequest);
    }

    @ExceptionHandler(InvalidCurrencyException.class)
    public ResponseEntity<Object> handleInvalidCurrencyException(InvalidCurrencyException ex,
                                                                WebRequest webRequest) {
        log.info(ex.toString());

        return handleExceptionInternal(ex, ErrorDto.builder()
                        .code(INVALID_CURRENCY)
                        .message(ex.getMessage())
                        .build(),
                new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, webRequest);
    }

    @ExceptionHandler(ClientServerException.class)
    public ResponseEntity<Object> handleClientServerException(ClientServerException ex,
                                                               WebRequest webRequest) {
        log.info(ex.toString());

        return handleExceptionInternal(ex, ErrorDto.builder()
                        .code(INVALID_AMOUNT)
                        .message(ex.getMessage())
                        .build(),
                new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, webRequest);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception ex,
                                                     WebRequest webRequest) {
        log.info(ex.getMessage());

        return handleExceptionInternal(ex, ErrorDto.builder()
                        .code(UNEXPECTED_EXCEPTION)
                        .message(ex.getMessage())
                        .build(),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
    }
}