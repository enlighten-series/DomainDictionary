package org.enlightenseries.DomainDictionary.presentation.rest.error;

import org.enlightenseries.DomainDictionary.application.exception.ApplicationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ExceptionTranslator extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ApplicationException.class)
  public ResponseEntity<ApplicationErrorDto> whenOccurApplicationException(ApplicationException ex) {
    return ResponseEntity.badRequest().body(new ApplicationErrorDto(ex.getMessage()));
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

    BindingResult result = ex.getBindingResult();
    for (ObjectError error : result.getGlobalErrors()) {
      map.add("global", error.getDefaultMessage());
    }
    for (FieldError error : result.getFieldErrors()) {
      map.add(error.getField(), error.getDefaultMessage());
    }

    return ResponseEntity.badRequest().body(map);
  }

}
