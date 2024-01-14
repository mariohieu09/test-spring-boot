package com.example.springproject.controller.advice;

import com.example.springproject.dto.base.ResponseGeneral;
import com.example.springproject.exception.base.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.example.springproject.constant.ExceptionCode.*;

@RestControllerAdvice
@ControllerAdvice
public class ExceptionHandlerAdvice {
  private final MessageSource messageSource;

  @Autowired
  public ExceptionHandlerAdvice(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ResponseGeneral<Object>> handleBaseException(BaseException ex, Locale locale) {
    String message = getMessage(ex.getCode(), locale, ex.getParams());
    ResponseGeneral<Object> response = ResponseGeneral.of(ex.getStatus(), message, null);
    return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getStatus()));
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ResponseGeneral<Object>> handleNotFoundException(NotFoundException ex, Locale locale) {
    String message = getMessage(ex.getCode(), locale, ex.getParams());
    ResponseGeneral<Object> response = ResponseGeneral.of(ex.getStatus(), message, null);
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }
  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ResponseGeneral<Object>> handleBadRequestException(BadRequestException ex, Locale locale) {
    String message = getMessage(ex.getCode(), locale, ex.getParams());
    ResponseGeneral<Object> response = ResponseGeneral.of(ex.getStatus(), message, null);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler(ConflictException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseEntity<ResponseGeneral<Object>> handleConflictException(ConflictException ex, Locale locale) {
    String message = getMessage(ex.getCode(), locale, ex.getParams());
    ResponseGeneral<Object> response = ResponseGeneral.of(ex.getStatus(), message, null);
    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
  }
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseGeneral<Map<String, String>> handleBindingException(MethodArgumentNotValidException ex, WebRequest webRequest){
    BindingResult bindingResult = ex.getBindingResult();
    Map<String, String> errors = new HashMap<>();

    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      errors.put(fieldError.getField(), fieldError.getDefaultMessage());
    }
    return ResponseGeneral.<Map<String, String>>of(400, "Error binding", errors);
  }

  @ExceptionHandler(DuplicateException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ResponseGeneral<Object>> handleDuplicateData(DuplicateException ex, WebRequest webRequest, Locale locale){
    String message = getMessage(ex.getCode(), locale, null);
    ResponseGeneral<Object> response = ResponseGeneral.of(ex.getStatus(), message, null);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BadCredentialsException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ResponseGeneral<Object>> handleBadCredentials(Exception ex, WebRequest webRequest, Locale locale){
    String message = getMessage(BAD_CREDENTIALS_CODE, locale, null);
    ResponseGeneral<Object> response = ResponseGeneral.of(HttpStatus.BAD_REQUEST.value(), message, null);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ResponseGeneral<Object>> handleGenericException(Locale locale) {
    String message = getMessage(GENERIC_CODE, locale, null);
    ResponseGeneral<Object> response = ResponseGeneral.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private String getMessage(String code, Locale locale, Map<String, String> params) {
    try {
      return messageSource.getMessage(code, params != null ? params.values().toArray() : null, locale);
    } catch (Exception e) {
      return code;
    }
  }
}