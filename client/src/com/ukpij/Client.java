package com.ukpij;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.TangoDs.Except;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Calendar;


/**
 * @author Mateusz Gałażyn <mateusz.galazyn@gmail.com>
 * @since 04.11.13
 */
public class Client {
  private static Client instance = null;
  Thermometer thermometer = Thermometer.getInstance();
  Interface iface = Interface.draw();

  ButtonSwitchActionListener buttonListener = null;

  ComboBoxActionListener comboListener = null;

  public static void main(String[] args) {
    try {
      Client client = Client.getInstance();
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

  public static Client getInstance() throws DevFailed {
    if(!(instance instanceof  Client)) {
      instance = new Client();
    }
    return instance;
  }

  private Client() throws DevFailed {
  }

  public void fillPlot() throws DevFailed {
    // w zaleznosci od wartosci refreshmode albo odczyt z bazy, albo z tango
    Temperature temperature;
    try {
      buttonListener.setApropiateStatus();
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
    JComboBox refreshMode = iface.getRefreshModeCombo();
    if(thermometer.getState() == DevState.ON) {
      button.setText("Wyłącz");
    } else {
      button.setText("Włącz");
    }

    iface.getDateFrom().disable(true);
    iface.getDateTo().disable(true);

    comboListener = new ComboBoxActionListener(iface.getDateFrom(),iface.getDateTo());
    refreshMode.addActionListener(comboListener);

    buttonListener = new ButtonSwitchActionListener();
    buttonListener.setButton(button);
    buttonListener.setText(iface.getText());
    button.addActionListener(buttonListener);
  }

  public static long time() {
    return Calendar.getInstance().getTime().getTime();
  }
}
