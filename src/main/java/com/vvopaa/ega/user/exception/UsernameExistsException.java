package com.vvopaa.ega.user.exception;

public class UsernameExistsException extends Exception {
  public UsernameExistsException(String name) {
    super("Username " + name + " already exists.");
  }
}
