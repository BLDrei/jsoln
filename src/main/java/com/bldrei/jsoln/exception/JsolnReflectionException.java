package com.bldrei.jsoln.exception;

public class JsolnReflectionException extends RuntimeException {

  public JsolnReflectionException(ReflectiveOperationException e) {
    super(e.getCause());
  }

}
