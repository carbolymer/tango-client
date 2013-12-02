package com.ukpij;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Mateusz Gałażyn <mateusz.galazyn@gmail.com>
 * @since 18.11.13
 */
public class DatePicker extends JPanel {
  private JSpinner spinner = null;

  public DatePicker(String label) {
    super(new SpringLayout());

    Calendar calendar = Calendar.getInstance();

    Date initDate = calendar.getTime();
    calendar.add(Calendar.YEAR, -100);
    Date earliestDate = calendar.getTime();
    calendar.add(Calendar.YEAR, 200);
    Date latestDate = calendar.getTime();
    SpinnerModel dateModel = new SpinnerDateModel(initDate,
        earliestDate,
        latestDate,
        Calendar.YEAR);//ignored for user input
    spinner = addLabeledSpinner(this, label, dateModel);
    spinner.setEditor(new JSpinner.DateEditor(spinner, "hh:mm:ss dd-MM-yyyy"));

    spinner.getValue().toString();

    //Lay out the panel.
    SpringUtilities.makeCompactGrid(this,
        1, 2,   //rows, cols
        10, 10, //initX, initY
        6, 10); //xPad, yPad
  }

  public void disable(Boolean value) {
    spinner.setEnabled(!value);
  }

  static protected JSpinner addLabeledSpinner(Container c,
                                              String label,
                                              SpinnerModel model) {
    JLabel l = new JLabel(label);
    c.add(l);

    JSpinner spinner = new JSpinner(model);
    l.setLabelFor(spinner);
    c.add(spinner);

    return spinner;
  }
}
