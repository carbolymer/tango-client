package com.ukpij;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Mateusz Gałażyn <mateusz.galazyn@gmail.com>
 * @since 02.12.13
 */
public class ComboBoxActionListener implements ActionListener {

  private DatePicker fromDate = null;

  private DatePicker toDate = null;


  public ComboBoxActionListener(DatePicker from, DatePicker to) {
    this.fromDate = from;
    this.toDate = to;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JComboBox cb = (JComboBox) e.getSource();
    Integer index = cb.getSelectedIndex();
    switch(index) {
      case 0:
        fromDate.disable(true);
        toDate.disable(true);
        break;
      default:
        fromDate.disable(false);
        toDate.disable(false);
        break;
    }
  }
}
