package com.ukpij;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
/**
 * @author Mateusz Gałażyn <mateusz.galazyn@gmail.com>
 * @since 04.11.13
 */
public class Interface extends ApplicationFrame {

  private XYSeries rawData = new XYSeries("Temperatura CPU");

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
    chartPanel.setPreferredSize(new java.awt.Dimension(1000, 800));
    setContentPane(chartPanel);

  }

  public static Interface draw() {

    final Interface demo = new Interface();
    demo.pack();
    RefineryUtilities.centerFrameOnScreen(demo);
    demo.setVisible(true);
    return demo;
  }

  public XYSeries getData() {
    return rawData;
  }

  public void setData(XYSeries data) {
    this.rawData = data;
  }
}
