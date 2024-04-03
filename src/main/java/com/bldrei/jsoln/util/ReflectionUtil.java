package com.bldrei.jsoln.util;

import com.bldrei.jsoln.exception.JsolnException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Optional;

public class ReflectionUtil {

  public static Class findClass(Type type) {
    try {
      return Class.forName(type.getTypeName());
    } catch (ClassNotFoundException d) {
      throw new JsolnException("Error: class with name '%s' not found".formatted(type.getTypeName()));
    }
  }

  public static Optional<Method> findMethod(Class<?> clazz, String name, Class<?>... params) {
    try {
      return Optional.of(clazz.getMethod(name, params));
    }
    catch (NoSuchMethodException e) {
      return Optional.empty();
    }
  }

  public static Optional<Method> findDeclaredMethod(Class<?> clazz, String name, Class<?>... params) {
    try {
      return Optional.of(clazz.getDeclaredMethod(name, params));
    }
    catch (NoSuchMethodException e) {
      return Optional.empty();
    }
  }

  public static Optional<Method> findSetter(Class<?> dto, String fldName, Class<?> param) {
    return findDeclaredMethod(dto, "set" + capitalizeFirstLetter(fldName), param);
  }

  public static Object invokeMethod(Object obj, Method method, Object... args) {
    try {
      return method.invoke(obj, args);
    }
    catch (InvocationTargetException e) {
      throw new RuntimeException("Method " + method + " threw an exception");
    }
    catch (IllegalAccessException e) {
      System.out.println("Warn: method " + method.getName() + " that you tried to invoke is not accessible");
    }
    return null;
  }

  private static String capitalizeFirstLetter(String str) {
    return Character.toUpperCase(str.charAt(0)) + str.substring(1);
  }
}
