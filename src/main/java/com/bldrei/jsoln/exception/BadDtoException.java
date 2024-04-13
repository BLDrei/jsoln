package com.bldrei.jsoln.exception;

public class BadDtoException extends JsolnException {
  public BadDtoException(String message) {
    super(message);
  }

  public static BadDtoException nestedOptional(Class<?> dto) {
    return new BadDtoException("There is no reason to have Optional<Optional<?>> as param type: " + dto);
  }
}
