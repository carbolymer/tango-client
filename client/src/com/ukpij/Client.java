package com.ukpij;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.TangoDs.Except;

import java.util.Calendar;


/**
 * @author Mateusz Gałażyn <mateusz.galazyn@gmail.com>
 * @since 04.11.13
 */
public class Client {
  Thermometer thermometer = null;
  Interface iface = null;

  public static void main(String[] args) {
    System.out.println("TERMOMETR");
    try {
      Client client = new Client();
      while(true) {
        client.fillPlot();
        Thread.sleep(500);
      }
    } catch (DevFailed e) {
      Except.print_exception(e);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public Client() throws DevFailed {
    thermometer = Thermometer.getInstance();
    iface = Interface.draw();
  }

  public void fillPlot() throws DevFailed {
    Temperature temperature;
    if(thermometer.getState() == DevState.ON) {
      temperature = thermometer.getTemperature();
      iface.getData().add(time(),temperature.getValue());
    } else
    {
      iface.getData().add(time(),null);
    }
  }

  public static long time() {
    return Calendar.getInstance().getTime().getTime();
  }
}
