package com.ukpij;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * @author Mateusz Gałażyn <mateusz.galazyn@gmail.com>
 * @since 09.12.13
 */
public class DAO {
  Connection connection = null;

  public DAO() {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/temperature_meter", "root", "razdwa34");
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      System.out.println("Where is your MySQL JDBC Driver?");
      e.printStackTrace();
      return;
    }
  }

  public Temperature[] getTemperatures(Date startDate, Date endDate) {
    Temperature[] temperatures;
    int i = 0;
    try {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM `values` WHERE timestamp > ? AND timestamp < ?");
      statement.setLong(1,startDate.getTime());
      statement.setLong(2, endDate.getTime());
      ResultSet resultSet = statement.executeQuery();
      temperatures = new Temperature[resultSet.getFetchSize()];
      while(resultSet.next()) {
        temperatures[i].setTimestamp(resultSet.getLong("timestamp"));
        temperatures[i++].setValue(resultSet.getDouble("temperature"));
      }
      return temperatures;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void insertTemperature(Temperature temperature) {
    try {
      PreparedStatement statement = connection.prepareStatement("INSERT INTO `values` (timestamp, temperature) VALUES (?,?)");
      statement.setLong(1, temperature.getTimestamp());
      statement.setDouble(2,temperature.getValue());
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
