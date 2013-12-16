package com.ukpij;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

  public ArrayList<Temperature> getTemperatures(Date startDate, Date endDate) {
    ArrayList<Temperature> temperatures = new ArrayList<>();
    int i = 0;
    PreparedStatement statement = null;
    try {
      statement = connection.prepareStatement("SELECT * FROM `values` WHERE timestamp > ? AND timestamp < ?");
//      System.out.println("SELECT * FROM `values` WHERE timestamp > " + startDate.getTime() + " AND timestamp < " + endDate.getTime());
      statement.setLong(1, startDate.getTime());
      statement.setLong(2, endDate.getTime());
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        temperatures.add(new Temperature());
        temperatures.get(i).setTimestamp(resultSet.getLong("timestamp"));
        temperatures.get(i++).setValue(resultSet.getDouble("temperature"));
      }
      return temperatures;
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (statement != null) {
        try {
          statement.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
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
