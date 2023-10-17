package com.example.springapp.exception;

public class CarIsBusy extends Exception
{
  public CarIsBusy(String message)
  {
    super(message);
  }
}