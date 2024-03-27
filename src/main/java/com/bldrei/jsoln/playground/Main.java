package com.bldrei.jsoln.playground;

import com.bldrei.jsoln.Jsoln;
import jdk.jshell.spi.ExecutionControl;

import java.lang.reflect.InvocationTargetException;

public class Main {


  public static void main(String[] args) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, ExecutionControl.NotImplementedException, NoSuchMethodException, InstantiationException, ClassNotFoundException {
    Application application = Jsoln.deserialize("{  \"country\": \"EE\",  \"channelId\": 6  }", Application.class);

    System.out.println(application);
  }



}
