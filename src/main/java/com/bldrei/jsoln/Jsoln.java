package com.bldrei.jsoln;

import com.bldrei.jsoln.jsonmodel.JsonArray;
import com.bldrei.jsoln.jsonmodel.JsonBoolean;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.jsonmodel.JsonNull;
import com.bldrei.jsoln.jsonmodel.JsonNumber;
import com.bldrei.jsoln.jsonmodel.JsonObject;
import com.bldrei.jsoln.jsonmodel.JsonText;
import com.bldrei.jsoln.util.DeserializeUtil;
import com.bldrei.jsoln.util.ReflectionUtil;
import jdk.jshell.spi.ExecutionControl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import static com.bldrei.jsoln.util.DeserializeUtil.getNewEmptyInstance;

public final class Jsoln {

  private Jsoln() {}

  public static <T> String serialize(T obj) {
    return "";
  }

  public static <T> T deserialize(String fullJson, Class<T> tClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException, ExecutionControl.NotImplementedException {
    return deserialize(DeserializeUtil.parseFullJson(fullJson), tClass);
  }

  private static <T> T deserialize(JsonObject jsonObject, Class<T> tClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException, ExecutionControl.NotImplementedException {
    return tClass.isRecord()
      ? deserializeRecordObject(jsonObject, tClass)
      : deserializeClassObject(jsonObject, tClass);
  }

  private static <T> T deserializeRecordObject(JsonObject jsonObject, Class<T> tClass) {
    return null;
  }

  private static <T> T deserializeClassObject(JsonObject jsonObject, Class<T> tClass) throws NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException, ExecutionControl.NotImplementedException {
    T obj = getNewEmptyInstance(tClass);
    var fields = tClass.getDeclaredFields();

    for (Field fld : fields) {
      String fldName = fld.getName();
      Class<?> fieldType = fld.getType();
      boolean isOptional = fieldType.equals(Optional.class);
      Class<?> actualType = isOptional ? ReflectionUtil.getActualTypesFromGenericField(fld).getFirst() : fieldType;
      boolean valuePresent = jsonObject.hasField(fldName);

      if (!valuePresent && !isOptional) {
        handleMissingValueForMandatoryField(fldName);
      }
      else {
        Optional<Method> setter = findSetter(tClass, fldName, fieldType);
        if (setter.isPresent()) { //todo refactor
          if (!valuePresent) {
            setter.get().invoke(obj, Optional.empty());
          }
          else {
            Object valueOfActualType = extractValueFromJsonElement(jsonObject.get(fldName), actualType, ReflectionUtil.getActualTypesFromGenericField(fld));
            setter.get().invoke(obj, isOptional ? Optional.ofNullable(valueOfActualType) : valueOfActualType);
          }
        } else {
          System.out.println("Warn: setter for %s not found".formatted(fldName));
        }

      }
    }
    return obj;
  }

  public static Object extractValueFromJsonElement(JsonElement val, Class actualType, List<Class> genericParams) throws ExecutionControl.NotImplementedException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    return switch (val) {
      case JsonObject jo -> deserialize(jo, actualType);
      case JsonArray ja -> ja.getCollection(actualType, genericParams.getFirst());
      case JsonText jt -> jt.getValue(actualType);
      case JsonNumber jn -> jn.getNumericValue((Class<? extends Number>) actualType);
      case JsonBoolean jb -> jb.getValue();
      case JsonNull jNull -> null;
    };
  }

  private static void handleMissingValueForMandatoryField(String fldName) {
    final boolean strictMode = true;
    if (strictMode) {
      throw new IllegalArgumentException("Value not present, but field " + fldName + " is mandatory");
    } else {
      System.out.println("Warn: Value not present, but field " + fldName + " is mandatory");
    }
  }

  private static Optional<Method> findSetter(Class dto, String fldName, Class fieldType) {
    try {
      return Optional.of(dto.getDeclaredMethod("set" + capitalizeFirstLetter(fldName), fieldType));
    }
    catch (NoSuchMethodException e) {
      return Optional.empty();
    }
  }

  private static String capitalizeFirstLetter(String str) {
    return Character.toUpperCase(str.charAt(0)) + str.substring(1);
  }
}
