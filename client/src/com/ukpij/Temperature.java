package com.ukpij;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Mateusz Gałażyn <mateusz.galazyn@gmail.com>
 * @since 04.11.13
 */
public class Temperature {
  private long timestamp = 0;
  private double value = 0;

  public Date getDate() {
    return new Date(timestamp);
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }
}
