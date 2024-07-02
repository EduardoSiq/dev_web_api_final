package com.dac.api.app.exception;

import com.dac.api.app.dto.ErrorMessageDTO;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlerController {

    private final MessageSource messageSource;

    public ExceptionHandlerController(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<Exception> handleException(final Exception ex) {
        return ResponseEntity.internalServerError().body(ex);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<List<ErrorMessageDTO>> handleAuthenticationException(
            final AuthenticationException e) {
        final List<ErrorMessageDTO> dto = new ArrayList<>();

        final ErrorMessageDTO error = new ErrorMessageDTO(e.getMessage(), "password");
        dto.add(error);

        return ResponseEntity.badRequest().body(dto);
    }

    @ExceptionHandler(UserFoundException.class)
    public ResponseEntity<List<ErrorMessageDTO>> handleUserFoundException(
            final UserFoundException e) {
        final List<ErrorMessageDTO> dto = new ArrayList<>();

        final ErrorMessageDTO error = new ErrorMessageDTO(e.getMessage(), e.getLocalizedMessage());
        dto.add(error);

        return ResponseEntity.badRequest().body(dto);

    }

    @ExceptionHandler(EditionNotFoundException.class)
    public ResponseEntity<List<ErrorMessageDTO>> handleEditionNotFoundException(
            final EditionNotFoundException e) {
        final List<ErrorMessageDTO> dto = new ArrayList<>();

        final ErrorMessageDTO error = new ErrorMessageDTO(e.getMessage(), "");
        dto.add(error);

        return ResponseEntity.badRequest().body(dto);

    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<List<ErrorMessageDTO>> handleEventNotFoundException(
            final EventNotFoundException e) {
        final List<ErrorMessageDTO> dto = new ArrayList<>();

        final ErrorMessageDTO error = new ErrorMessageDTO(e.getMessage(), "");
        dto.add(error);

        return ResponseEntity.badRequest().body(dto);

    }

}
