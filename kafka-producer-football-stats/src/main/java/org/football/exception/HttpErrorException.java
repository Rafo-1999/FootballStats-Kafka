package org.football.exception;

public class HttpErrorException extends RuntimeException {
  public HttpErrorException(String message) {
    super(message);
  }
}
