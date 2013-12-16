package com.ukpij.gui;

import com.ukpij.DAO;
import com.ukpij.Temperature;
import com.ukpij.Thermometer;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.TangoDs.Except;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


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

  SpinnerListener spinnerListener = null;

  DAO dao = null;

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
    dao = new DAO();
  }

  public void fillPlot() throws DevFailed {
    Temperature temperature;
    // w zaleznosci od wartosci refreshmode albo odczyt z bazy, albo z tango
    if( iface.getRefreshModeCombo().getSelectedIndex() == 1) {
      return; // nie ma po co odswierzac, skoro jest wybrany zakres dat
    }
    try {
      buttonListener.setApropiateStatus();
      if (thermometer.getState() == DevState.ON) {
        temperature = thermometer.getTemperature();
        iface.getData().add(new Millisecond(date()), temperature.getValue());
      } else {
        iface.getData().add(new Millisecond(date()), null);
      }
    } catch (DevFailed e) {
      ;
    }
  }

  public void fillPlotFromHistory(Date startDate, Date endDate) {
    ArrayList<Temperature> temperatures = dao.getTemperatures(startDate,endDate);
    TimeSeries data = iface.getData();
    data.clear();
    for( int i = 0; i < temperatures.size() ; ++i) {
      data.add(new Millisecond(temperatures.get(i).getDate()), temperatures.get(i).getValue());
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

    spinnerListener = new SpinnerListener(iface.getDateFrom(),iface.getDateTo());
    iface.getDateFrom().disable(true);
    iface.getDateFrom().addSpinnerListener(spinnerListener);
    iface.getDateTo().disable(true);
    iface.getDateTo().addSpinnerListener(spinnerListener);

    comboListener = new ComboBoxActionListener(iface.getDateFrom(),iface.getDateTo());
    refreshMode.addActionListener(comboListener);

    buttonListener = new ButtonSwitchActionListener();
    buttonListener.setButton(button);
    buttonListener.setText(iface.getText());
    button.addActionListener(buttonListener);
  }

//  public static long time() {
//    return Calendar.getInstance().getTime().getTime();
//  }

  public static Date date() {
    return Calendar.getInstance().getTime();
  }
}
