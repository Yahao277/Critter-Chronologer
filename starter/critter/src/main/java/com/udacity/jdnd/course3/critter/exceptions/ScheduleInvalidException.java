package com.udacity.jdnd.course3.critter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND)
public class ScheduleInvalidException extends RuntimeException{
  public ScheduleInvalidException(String message) {
    super(message);
  }
}
