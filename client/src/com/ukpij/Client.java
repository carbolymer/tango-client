package com.ukpij;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.TangoDs.Except;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;


/**
 * @author Mateusz Gałażyn <mateusz.galazyn@gmail.com>
 * @since 04.11.13
 */
public class Client {
  Thermometer thermometer = Thermometer.getInstance();
  Interface iface = Interface.draw();

  ButtonSwitchActionListener listener = null;

  public static void main(String[] args) {
    System.out.println("TERMOMETR");
    try {
      Client client = new Client();
      client.updateButton();
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

  }

  public void fillPlot() throws DevFailed {
    Temperature temperature;
    try {
      listener.setApropiateStatus();
      if (thermometer.getState() == DevState.ON) {
        temperature = thermometer.getTemperature();
        iface.getData().add(time(), temperature.getValue());
      } else {
        iface.getData().add(time(), null);
      }
    } catch (DevFailed e) {
      ;
    }
  }

  public void updateButton() throws DevFailed {
    JButton button = iface.getSwitchButton();
    if(thermometer.getState() == DevState.ON) {
      button.setText("Wyłącz");
    } else {
      button.setText("Włącz");
    }

    listener = new ButtonSwitchActionListener();
    listener.setButton(button);
    listener.setText(iface.getText());
    button.addActionListener(listener);
  }

  public static long time() {
    return Calendar.getInstance().getTime().getTime();
  }
}
