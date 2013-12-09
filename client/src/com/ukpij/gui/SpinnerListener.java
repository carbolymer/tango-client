package com.ukpij.gui;

import fr.esrf.Tango.DevFailed;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Mateusz Gałażyn <mateusz.galazyn@gmail.com>
 * @since 09.12.13
 */
public class SpinnerListener implements ChangeListener {

  private DatePicker fromDate = null;

  private DatePicker toDate = null;


  public SpinnerListener(DatePicker from, DatePicker to) {
    this.fromDate = from;
    this.toDate = to;
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    try {
      Client.getInstance().fillPlotFromHistory(fromDate.getDate(), toDate.getDate());
    } catch (DevFailed devFailed) {
      devFailed.printStackTrace();
    }
  }
}
