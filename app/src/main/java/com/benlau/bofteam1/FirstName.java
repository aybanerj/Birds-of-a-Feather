package com.benlau.bofteam1;

public class FirstName {
  private String firstName;

  public FirstName(String name) {
    firstName = name;
  }

  private void setName(String name) {
    firstName = name;
  }

  public String getName() {
    return firstName;
  }

  public boolean checkValid(String name) {
    if (name.length() > 100) return false;
    if (!name.matches("[a-zA-Z]*")) return false;
    return true;
  }
}
