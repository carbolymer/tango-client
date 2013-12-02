package com.ukpij;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;

/**
 * @author Mateusz Gałażyn <mateusz.galazyn@gmail.com>
 * @since 04.11.13
 */
public class Interface extends ApplicationFrame {

  private XYSeries rawData = new XYSeries("Temperatura CPU");

  private JButton switchButton = new JButton();

  private DatePicker dateFrom  = new DatePicker("Początek:");

  private DatePicker dateTo = new DatePicker("Koniec:");

  private JLabel text = new JLabel("OFF");

  private final String[] options = {"Autoaktualizacja", "Zakres dat"};

  private JComboBox refreshModeCombo = new JComboBox(options);

  private Interface() {

    super("Temperatura CPU");
    final XYSeriesCollection data = new XYSeriesCollection(rawData);
    final JFreeChart chart = ChartFactory.createXYLineChart(
        "Temperatura CPU",
        "Czas [s]",
        "Temperatura [C]",
        data,
        PlotOrientation.VERTICAL,
        true,
        true,
        false
    );

    final ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new java.awt.Dimension(1000, 750));

    GridBagConstraints c = new GridBagConstraints();

    Container pane = getContentPane();
    pane.setLayout(new GridBagLayout());
    c.fill = GridBagConstraints.BOTH;
    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 5;
    c.gridheight = 1;
    pane.add(chartPanel,c);
    c.fill = GridBagConstraints.NONE;
    c.gridwidth = 1;
    c.gridheight = 1;

    c.gridx = 0;
    c.gridy = 1;
    pane.add(text,c);

    c.gridx = 1;
    c.gridy = 1;
    pane.add(switchButton,c);

    c.gridx = 2;
    c.gridy = 1;
    pane.add(getDateFrom(),c);

    c.gridx = 3;
    c.gridy = 1;
    pane.add(getDateTo(),c);

    c.gridx = 4;
    c.gridy = 1;
    pane.add(getRefreshModeCombo(),c);

    text.setFont(text.getFont().deriveFont(64.0f));
    setSize(1000, 800);
    setMinimumSize(new Dimension(1000,800));
  }

  public static Interface draw() {

    final Interface demo = new Interface();
    demo.pack();
    RefineryUtilities.centerFrameOnScreen(demo);
    demo.setVisible(true);
    return demo;
  }

  private JComboBox getRefreshSelector() {

    return getRefreshModeCombo();
  }

  public XYSeries getData() {
    return rawData;
  }

  public void setData(XYSeries data) {
    this.rawData = data;
  }

  public JButton getSwitchButton() {
    return switchButton;
  }

  public void setSwitchButton(JButton switchButton) {
    this.switchButton = switchButton;
  }

  public JLabel getText() {
    return text;
  }

  public void setText(JLabel text) {
    this.text = text;
  }

  public DatePicker getDateFrom() {
    return dateFrom;
  }

  public void setDateFrom(DatePicker dateFrom) {
    this.dateFrom = dateFrom;
  }

  public DatePicker getDateTo() {
    return dateTo;
  }

  public void setDateTo(DatePicker dateTo) {
    this.dateTo = dateTo;
  }

  public JComboBox getRefreshModeCombo() {
    return refreshModeCombo;
  }

  public void setRefreshModeCombo(JComboBox refreshModeCombo) {
    this.refreshModeCombo = refreshModeCombo;
  }
}
