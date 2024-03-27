package com.bldrei.jsoln;

import com.bldrei.jsoln.jsonmodel.JsonArray;
import com.bldrei.jsoln.jsonmodel.JsonBoolean;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.jsonmodel.JsonNull;
import com.bldrei.jsoln.jsonmodel.JsonNumber;
import com.bldrei.jsoln.jsonmodel.JsonObject;
import com.bldrei.jsoln.jsonmodel.JsonText;
import jdk.jshell.spi.ExecutionControl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public final class Jsoln {

  private static final String OPTIONAL_VALUE_FLD_NAME = "value";

  private Jsoln() {}

  public static <T> String serialize(T obj) {
    return "";
  }

  public static <T> T deserialize(String fullJson, Class<T> tClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException, ExecutionControl.NotImplementedException, ClassNotFoundException {
    return deserialize(DeserializeUtil.parseFullJson(fullJson), tClass);
  }

  private static <T> T deserialize(JsonObject jsonObject, Class<T> tClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException, ExecutionControl.NotImplementedException, ClassNotFoundException {
    return tClass.isRecord()
      ? deserializeRecordObject(jsonObject, tClass)
      : deserializeClassObject(jsonObject, tClass);
  }

  private static <T> T deserializeRecordObject(JsonObject jsonObject, Class<T> tClass) {
    return null;
  }

  private static <T> T deserializeClassObject(JsonObject jsonObject, Class<T> tClass) throws NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException, ExecutionControl.NotImplementedException, ClassNotFoundException {
    T obj = getNewEmptyInstance(tClass);
    var fields = tClass.getDeclaredFields();
    List<Method> setters = Arrays.stream(tClass.getDeclaredMethods())
      .filter(m -> m.getName().startsWith("set") && m.getParameterTypes().length == 1)
      .toList();

    for (Field fld : fields) {
      String fldName = fld.getName();
      boolean isOptional = fld.getType().equals(Optional.class);
      Class<?> fldType = isOptional ? getOptionalFieldValueType(fld) : fld.getType();
      boolean noValuePresent = !jsonObject.hasField(fldName);
      if (noValuePresent && isOptional) {
        fld.setAccessible(true);
        fld.set(obj, Optional.empty());
        continue;
      }
      if (noValuePresent) throw new IllegalArgumentException("Value not present, but field " + fldName + " is mandatory");

      //value is present
      JsonElement val = jsonObject.get(fldName);
      Object valueOfActualType;
      if (val instanceof JsonObject jo) {
        valueOfActualType = deserialize(jo, fldType);
      }
      else if (val instanceof JsonArray ignored) {
        throw new ExecutionControl.NotImplementedException("collection not done");
      }
      else if (val instanceof JsonText jt) {
        valueOfActualType = jt.getValue();
      }
      else if (val instanceof JsonNumber jn) {
        valueOfActualType = jn.getNumericValue((Class<? extends Number>) fldType);
      }
      else if (val instanceof JsonBoolean jb) {
        valueOfActualType = jb.getValue();
      }
      else if (val instanceof JsonNull jNull) {
        valueOfActualType = null;
      }
      else {
        throw new IllegalStateException("Will be gone with pattern matching, unexpected jsonElement: " + val.getClass().getSimpleName());
      }

      for (Method setter : setters) {
        if (setter.getName().equals("set" + capitalizeFirstLetter(fldName))
          && setter.getParameterTypes()[0].equals(isOptional ? Optional.class : fldType)) {
          setter.invoke(obj, isOptional ? Optional.ofNullable(valueOfActualType) : valueOfActualType);
          break;
        }
      }
    }
    return obj;
  }


  private static <T> T getNewEmptyInstance(Class<T> tClass) {
    try {
      return tClass.getDeclaredConstructor().newInstance();
    }
    catch (NoSuchMethodException e) {
      throw new RuntimeException("Zero-argument constructor missing");
    }
    catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  private static String capitalizeFirstLetter(String str) {
    return str.substring(0, 1).toUpperCase() + str.substring(1);
  }

  private static Class<?> getOptionalFieldValueType(Field optionalField) throws ClassNotFoundException {
    var actualClassName = optionalField.getGenericType().getTypeName()
      .replaceFirst("java.util.Optional<", "")
      .replace(">", ""); //optimize
    return Class.forName(actualClassName);
  }
}
