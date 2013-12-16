package com.ukpij;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.TangoApi.DeviceAttribute;
import fr.esrf.TangoApi.DeviceProxy;

/**
 * @author Mateusz Gałażyn <mateusz.galazyn@gmail.com>
 * @since 04.11.13
 */
public class Thermometer {
  private static Thermometer instance = null;

  DeviceProxy device = new DeviceProxy("tmeter/one/two");

  private Thermometer() throws DevFailed {
    ;
  }

  public static Thermometer getInstance() throws DevFailed {
    if(!(instance instanceof  Thermometer)) {
      instance = new Thermometer();
    }
    return instance;
  }

  public DevState getState() throws DevFailed {
    return device.read_attribute("state").extractState();
  }

  public Temperature getTemperature() throws DevFailed {
    Temperature temperature =  new Temperature();
    DeviceAttribute attribute;
    attribute = device.read_attribute("temperature");
    temperature.setTimestamp(attribute.getTime());
    temperature.setValue(attribute.extractDouble());
    return temperature;
  }

  public boolean Start() throws DevFailed {
    return device.command_inout("Start").extractBoolean();
  }

  public boolean Stop() throws DevFailed {
    return device.command_inout("Stop").extractBoolean();
  }
}
