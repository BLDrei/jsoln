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

import static com.bldrei.jsoln.DeserializeUtil.getNewEmptyInstance;

public final class Jsoln {

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

    for (Field fld : fields) {
      String fldName = fld.getName();
      Class<?> fieldType = fld.getType();
      boolean isOptional = fieldType.equals(Optional.class);
      Class<?> actualType = isOptional ? getActualTypeFromOptional(fld) : fieldType;
      boolean noValuePresent = !jsonObject.hasField(fldName);
      if (noValuePresent && isOptional) {
        fld.setAccessible(true);
        fld.set(obj, Optional.empty());
        continue;
      }
      if (noValuePresent) throw new IllegalArgumentException("Value not present, but field " + fldName + " is mandatory");

      //value is present
      Object valueOfActualType = extractValueFromJsonElement(jsonObject.get(fldName), actualType);

      try {
        Method setter = tClass.getDeclaredMethod("set" + capitalizeFirstLetter(fldName), fieldType);
        setter.invoke(obj, isOptional ? Optional.ofNullable(valueOfActualType) : valueOfActualType);
      } catch (NoSuchMethodException e) {
        System.out.println("Warn: setter for %s not found".formatted(fldName));
      }
    }
    return obj;
  }

  private static Object extractValueFromJsonElement(JsonElement val, Class actualType) throws ExecutionControl.NotImplementedException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    return switch (val) {
      case JsonObject jo -> deserialize(jo, actualType);
      case JsonArray ignored -> throw new ExecutionControl.NotImplementedException("collection not implemented");
      case JsonText jt -> jt.getValue();
      case JsonNumber jn -> jn.getNumericValue((Class<? extends Number>) actualType);
      case JsonBoolean jb -> jb.getValue();
      case JsonNull jNull -> null;
    };
  }

  private static String capitalizeFirstLetter(String str) {
    return Character.toUpperCase(str.charAt(0)) + str.substring(1);
  }

  private static Class<?> getActualTypeFromOptional(Field optionalField) throws ClassNotFoundException {
    var actualClassName = optionalField.getGenericType().getTypeName()
      .replaceFirst("java.util.Optional<", "")
      .replace(">", ""); //optimize
    return Class.forName(actualClassName);
  }
}
