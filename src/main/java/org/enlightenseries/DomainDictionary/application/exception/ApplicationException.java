package org.enlightenseries.DomainDictionary.application.exception;

public class ApplicationException extends Exception {
  public ApplicationException(String message) {
    super(message);
  }
  public ApplicationException(String message, Exception cause){
    super(message, cause);
  }
}
