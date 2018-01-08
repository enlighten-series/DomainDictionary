package org.enlightenseries.DomainDictionary.presentation.rest.error;

import org.enlightenseries.DomainDictionary.application.exception.ApplicationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionTranslator {

  @ExceptionHandler(ApplicationException.class)
  public ResponseEntity<BusinessErrorDto> whenOccurApplicationException(ApplicationException ex) {
    return ResponseEntity.badRequest().body(new BusinessErrorDto(ex.getMessage()));
  }

}
