package com.bldrei.jsoln;

import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.jsonmodel.JsonArray;
import com.bldrei.jsoln.jsonmodel.JsonBoolean;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.jsonmodel.JsonNumber;
import com.bldrei.jsoln.jsonmodel.JsonObject;
import com.bldrei.jsoln.jsonmodel.JsonText;
import com.bldrei.jsoln.util.ClassTree;
import com.bldrei.jsoln.util.DeserializeUtil;
import com.bldrei.jsoln.util.ReflectionUtil;
import jdk.jshell.spi.ExecutionControl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import static com.bldrei.jsoln.util.DeserializeUtil.getNewEmptyInstance;
import static com.bldrei.jsoln.util.ReflectionUtil.findClass;
import static com.bldrei.jsoln.util.ReflectionUtil.findSetter;

public final class Jsoln {

  private Jsoln() {}

  public static <T> String serialize(T obj) {
    return "";
  }

  public static <T> T deserialize(String fullJson, Class<T> tClass) {
    return deserialize(DeserializeUtil.parseFullJson(fullJson), tClass);
  }

  private static <T> T deserialize(JsonObject jsonObject, Class<T> tClass) {
    return tClass.isRecord()
      ? deserializeRecordObject(jsonObject, tClass)
      : deserializeClassObject(jsonObject, tClass);
  }

  private static <T> T deserializeRecordObject(JsonObject jsonObject, Class<T> tClass) {
    return null;
  }

  private static <T> T deserializeClassObject(JsonObject jsonObject, Class<T> tClass) {
    T obj = getNewEmptyInstance(tClass);
    var fields = tClass.getDeclaredFields();

    for (Field fld : fields) {
      String fldName = fld.getName();
      ClassTree tree = ClassTree.fromField(fld);
      boolean isNullable = Optional.class.equals(tree.rawType());
      if (isNullable) tree = ClassTree.fromType(tree.genericParameters()[0]);
      var value = jsonObject.get(fldName);
      boolean valuePresent = value.isPresent();

      if (!valuePresent && !isNullable) {
        handleMissingValueForMandatoryField(fldName);
      }
      else {
        var setter = findSetter(tClass, fldName, fld.getType());
        if (setter.isPresent()) { //todo refactor
          if (!valuePresent) {
            ReflectionUtil.invokeMethod(obj, setter.get(), Optional.empty());
          }
          else {
            Object valueOfActualType = extractValueFromJsonElement(value.get(), tree);
            ReflectionUtil.invokeMethod(obj, setter.get(), isNullable ? Optional.ofNullable(valueOfActualType) : valueOfActualType);
          }
        } else {
          System.out.println("Warn: setter for %s not found".formatted(fldName));
        }

      }
    }
    return obj;
  }

  public static Object extractValueFromJsonElement(JsonElement val, ClassTree classTree) {
    return switch (val) {
      case JsonObject jo -> deserialize(jo, findClass(classTree.rawType()));
      case JsonArray ja -> ja.getCollection(classTree);
      case JsonText jt -> jt.getValue(classTree.rawType());
      case JsonNumber jn -> jn.getNumericValue(classTree);
      case JsonBoolean jb -> jb.getValue();
    };
  }

  private static void handleMissingValueForMandatoryField(String fldName) {
    final boolean strictMode = true;
    if (strictMode) {
      throw new JsolnException("Value not present, but field " + fldName + " is mandatory");
    } else {
      System.out.println("Warn: Value not present, but field " + fldName + " is mandatory");
    }
  }
}
