package com.ukpij.gui;

import com.ukpij.Thermometer;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Mateusz Gałażyn <mateusz.galazyn@gmail.com>
 * @since 18.11.13
 */
public class ButtonSwitchActionListener implements ActionListener {

  private JButton button = null;

  private JLabel text = null;

  @Override
  public void actionPerformed(ActionEvent e) {
    try {
      Thermometer thermometer = Thermometer.getInstance();
      if(thermometer.getState() == DevState.ON) {
        thermometer.Stop();
        button.setText("Włącz");
        text.setText("OFF");
      } else {
        thermometer.Start();
        button.setText("Wyłącz");
        text.setText("ON");
      }
    } catch (DevFailed devFailed) {
      devFailed.printStackTrace();
    }
  }

  public void setApropiateStatus() {
    try {
      Thermometer thermometer = Thermometer.getInstance();
      if(thermometer.getState() == DevState.ON) {
        button.setText("Wyłącz");
        text.setText("ON");
      } else {
        button.setText("Włącz");
        text.setText("OFF");
      }
    } catch (DevFailed devFailed) {
      devFailed.printStackTrace();
    }
  }

  public JButton getButton() {
    return button;
  }

  public void setButton(JButton button) {
    this.button = button;
  }

  public JLabel getText() {
    return text;
  }

  public void setText(JLabel text) {
    this.text = text;
  }
}
