package com.ukpij.logger;

import com.ukpij.DAO;
import com.ukpij.Temperature;
import com.ukpij.Thermometer;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;

/**
 * @author Mateusz Gałażyn <mateusz.galazyn@gmail.com>
 * @since 09.12.13
 */
public class DatabaseLogger {
  private static DatabaseLogger instance = null;
  Thermometer thermometer = null;
  DAO dao = null;
  int animationPosition = 0;

  public static DatabaseLogger getInstance() {
    if(! (instance instanceof DatabaseLogger) ) {
      instance = new DatabaseLogger();
    }
    return instance;
  }

  private DatabaseLogger() {
    try {
      dao = new DAO();
      thermometer = Thermometer.getInstance();
      while (true) {
        fillDatabase();
        drawAnimation();
        Thread.sleep(500);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (DevFailed devFailed) {
      devFailed.printStackTrace();
    }
  }

  public static void main(String[] args) {
    System.out.println("DatabaseLogger started");
    new DatabaseLogger();
  }

  private void fillDatabase() {
    Temperature temperature;
    try {
      if (thermometer.getState() == DevState.ON) {
        temperature = thermometer.getTemperature();
        dao.insertTemperature(temperature);
      }
    } catch (DevFailed e) {
      ;
    }
  }

  private void drawAnimation() {
    String[] animation = {
        "###    ",
        " ###   ",
        "  ###  ",
        "   ### ",
        "    ###",
        "   ### ",
        "  ###  ",
        " ###   "
    };
    String state;
    try {
      if (thermometer.getState() == DevState.ON) {
        state = "ON";
      } else {
        state = "OFF";
      }
        System.out.print("[" + animation[animationPosition++] + "] Device State: "+state +"   \r");
        if (animationPosition == animation.length) {
          animationPosition = 0;
        }
    } catch (DevFailed e) {
      ;
    }
  }
}
