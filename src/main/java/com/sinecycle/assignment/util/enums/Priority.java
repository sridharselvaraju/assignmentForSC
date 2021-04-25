package com.sinecycle.assignment.util.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum Priority {
  HIGH("HIGH"),
  MEDIUM("MEDIUM"),
  NORMAL("NORMAL"),
  LOW("LOW");

  private final String value;
  private final static Map<String, Priority> CONSTANTS = new HashMap<String, Priority>();

  static {
    for (Priority c : values()) {
      CONSTANTS.put(c.value, c);
    }
  }

  private Priority(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return this.value;
  }

  @JsonValue
  public String value() {
    return this.value;
  }

  @JsonCreator
  public static Priority fromValue(String value) {
    Priority constant = CONSTANTS.get(value);
    if (constant == null) {
      throw new IllegalArgumentException(value);
    } else {
      return constant;
    }
  }
}
