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

import static com.bldrei.jsoln.Primitives.wrap;

public final class Jsoln {

  private static final String OPTIONAL_VALUE_FLD_NAME = "value";

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
    List<Method> setters = Arrays.stream(tClass.getDeclaredMethods())
      .filter(m -> m.getName().startsWith("set") && m.getParameterTypes().length == 1)
      .toList();

    for (Field fld : fields) {
      String fldName = fld.getName();
      boolean isOptional = fld.getType().equals(Optional.class);
      Class<?> fldType = isOptional ? getOptionalFieldValueType(fld) : fld.getType();
      fldType = wrap(fldType);
      boolean noValuePresent = !jsonObject.hasField(fldName);
      if (noValuePresent && isOptional) {
        fld.setAccessible(true);
        fld.set(obj, Optional.empty());
        continue;
      }
      if (noValuePresent) throw new IllegalArgumentException("Value not present, but field " + fldName + " is mandatory");

      //value is present
      JsonElement val = jsonObject.get(fldName);
      var valueOfActualType = switch (val) {
        case JsonObject jo -> deserialize(jo, fldType);
        case JsonArray ignored -> throw new ExecutionControl.NotImplementedException("collection not done");
        case JsonBoolean jb -> convertPlainJsonElementToObjectOfType(val, fldType);
        case JsonText jt -> convertPlainJsonElementToObjectOfType(val, fldType);
        case JsonNumber jn -> convertPlainJsonElementToObjectOfType(val, fldType);
        case JsonNull jnull -> convertPlainJsonElementToObjectOfType(val, fldType);
      };



      for (Method setter : setters) {
        if (setter.getName().equals("set" + capitalizeFirstLetter(fldName))
          && wrap(setter.getParameterTypes()[0]).equals(isOptional ? Optional.class : fldType)) {
          setter.invoke(obj, isOptional ? Optional.ofNullable(valueOfActualType) : valueOfActualType);
          break;
        }
      }
    }
    return obj;
  }

  //not jsonobject, not jsonarray
  private static <E> E convertPlainJsonElementToObjectOfType(JsonElement jsonElement, Class<E> elClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

    Object val = switch (jsonElement) {
      case JsonBoolean jb -> jb.getValue();
      case JsonNull ignored -> null;
      case JsonNumber jn -> elClass.getDeclaredMethod("valueOf", String.class).invoke(null, jn.getNumberAsString());
      case JsonText jt -> jt.getValue();
      default -> throw new IllegalStateException("Unexpected value: " + jsonElement);
    };

    return val == null ? null : (E) val;
  }

//  private static Map<String, Object> getJsonMap(String json) {
//    Map<String, Object> jsonMap = new HashMap<>();
//
//
//    var tokenizer = new StringTokenizer(json, ",");
//    while (tokenizer.hasMoreTokens()) {
//      StringTokenizer kvPairTokenizer = new StringTokenizer(tokenizer.nextToken(), DOUBLE_QUOTE);
//
//      String keyStr = kvPairTokenizer.nextToken();
//      String valStr = kvPairTokenizer.nextToken();
//
//      if (keyStr.charAt(0) != DOUBLE_QUOTE_CHAR
//        || keyStr.charAt(keyStr.length() - 1) != DOUBLE_QUOTE_CHAR) {
//        throw new IllegalArgumentException("Incorrect json");
//      }
//      keyStr = keyStr.substring(1, keyStr.length() - 1);
//      if (valStr.startsWith("{") && valStr.endsWith("}")) {
//        jsonMap.put(keyStr, getJsonMap(valStr));
//      } else if (valStr.startsWith("[") && valStr.endsWith("]")) {
//        jsonMap.put(keyStr, getJsonMap(valStr));
//      }
//    }
//  }



  private static <T> T getNewEmptyInstance(Class<T> tClass) {
    try {
      return tClass.getDeclaredConstructor().newInstance();
    }
    catch (NoSuchMethodException e) {
      throw new RuntimeException("TODO: find any accessible constructor");
    }
    catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  private static String capitalizeFirstLetter(String str) {
    return str.substring(0, 1).toUpperCase() + str.substring(1);
  }

  private static Class<?> getOptionalFieldValueType(Field optionalField) throws NoSuchFieldException {
    Class<Optional<?>> optionalClass = (Class<Optional<?>>) optionalField.getType();
    return optionalClass.getDeclaredField(OPTIONAL_VALUE_FLD_NAME).getType();
  }
}
