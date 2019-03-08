package com.vvopaa.ega.security.exception;

public class UsernameAlreadyExistsException extends Exception {
  public UsernameAlreadyExistsException(String name) {
    super("Username " + name + " already exists.");
  }
}
