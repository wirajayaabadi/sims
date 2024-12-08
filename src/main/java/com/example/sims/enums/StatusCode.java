package com.example.sims.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

public enum StatusCode {
  SUCCESS(0),
  BAD_REQUEST(102),
  UNAUTHORIZED_TOKEN(108);

  private final int code;

  StatusCode(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
