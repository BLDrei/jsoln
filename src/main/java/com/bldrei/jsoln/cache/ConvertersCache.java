package com.bldrei.jsoln.cache;

import com.bldrei.jsoln.converter.AbstractConverter;
import com.bldrei.jsoln.converter.array.ArrayConverter;
import com.bldrei.jsoln.converter.array.ListConverter;
import com.bldrei.jsoln.converter.bool.BooleanConverter;
import com.bldrei.jsoln.converter.number.BigDecimalConverter;
import com.bldrei.jsoln.converter.number.BigIntegerConverter;
import com.bldrei.jsoln.converter.number.DoubleConverter;
import com.bldrei.jsoln.converter.number.IntegerConverter;
import com.bldrei.jsoln.converter.number.LongConverter;
import com.bldrei.jsoln.converter.number.NumberConverter;
import com.bldrei.jsoln.converter.object.MapConverter;
import com.bldrei.jsoln.converter.object.ObjectConverter;
import com.bldrei.jsoln.converter.object.RecordConverter;
import com.bldrei.jsoln.converter.text.EnumConverter;
import com.bldrei.jsoln.converter.text.LocalDateConverter;
import com.bldrei.jsoln.converter.text.LocalDateTimeConverter;
import com.bldrei.jsoln.converter.text.StringConverter;
import com.bldrei.jsoln.converter.text.TextConverter;
import com.bldrei.jsoln.jsonmodel.JsonModelType;
import lombok.AccessLevel;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConvertersCache {
  private ConvertersCache() {}

  private static final Map<Class<?>, TextConverter<?>> textConvertersCache = Map.of(
    String.class, new StringConverter(),
    LocalDate.class, new LocalDateConverter(),
    LocalDateTime.class, new LocalDateTimeConverter()
  );
  private static final Map<Class<?>, TextConverter<?>> enumConvertersCache = new HashMap<>();
  private static final Map<Class<?>, NumberConverter<?>> numberConvertersCache = Map.of(
    Integer.class, new IntegerConverter(),
    Long.class, new LongConverter(),
    Double.class, new DoubleConverter(),
    BigDecimal.class, new BigDecimalConverter(),
    BigInteger.class, new BigIntegerConverter()
  );
  @Getter(AccessLevel.PRIVATE)
  private static final BooleanConverter booleanConverter = new BooleanConverter();
  private static final Map<Class<?>, ArrayConverter<?>> arrayConvertersCache = Map.of(
    List.class, new ListConverter()
  );
  private static final Map<Class<?>, ObjectConverter<?>> recordConvertersCache = new HashMap<>();
  private static final Map<Class<?>, ObjectConverter<?>> objectConvertersCache = Map.of(
    Map.class, new MapConverter()
  );

  public static AbstractConverter getConverter(Class<?> clazz, JsonModelType jsonDataType) {
    return switch (jsonDataType) {
      case ARRAY -> getArrayConverter(clazz);
      case BOOLEAN -> getBooleanConverter();
      case NUMBER -> getNumberConverter(clazz);
      case OBJECT -> getObjectConverter(clazz);
      case TEXT -> getTextConverter(clazz);
    };
  }


  @SuppressWarnings("unchecked")
  private static <T> TextConverter<T> getTextConverter(Class<T> clazz) {
    var converter = clazz.isEnum()
      ? enumConvertersCache.computeIfAbsent(clazz, EnumConverter::new)
      : textConvertersCache.get(clazz);
    if (converter == null) {
      throw new IllegalStateException("""
        Unexpected json type: %s.
        Supported JsonText converters are: %s"""
        .formatted(clazz, Arrays.toString(TextConverter.class.getPermittedSubclasses())));
    }
    return (TextConverter<T>) converter;
  }

  @SuppressWarnings("unchecked")
  private static <N> NumberConverter<N> getNumberConverter(Class<N> clazz) {
    var converter = (NumberConverter<N>) numberConvertersCache.get(clazz);
    if (converter == null) {
      throw new IllegalStateException("""
        Unexpected json type: %s.
        Supported JsonNumber converters are: %s"""
        .formatted(clazz, numberConvertersCache.values()));
    }
    return converter;
  }

  @SuppressWarnings("unchecked")
  private static <C> ArrayConverter<C> getArrayConverter(Class<C> clazz) {
    var converter = (ArrayConverter<C>) arrayConvertersCache.get(clazz);
    if (converter == null) {
      throw new IllegalStateException("""
        Unexpected json type: %s.
        Supported JsonArray converters are: %s"""
        .formatted(clazz, arrayConvertersCache.values()));
    }
    return converter;
  }

  @SuppressWarnings("unchecked")
  private static <O> ObjectConverter<O> getObjectConverter(Class<O> clazz) {
    var converter = clazz.isRecord()
      ? recordConvertersCache.computeIfAbsent(clazz, it -> new RecordConverter<O>())
      : objectConvertersCache.get(clazz);
    if (converter == null) {
      throw new IllegalStateException("""
        Unexpected json type: %s.
        Supported JsonObject converters are: %s"""
        .formatted(clazz, Arrays.toString(ObjectConverter.class.getPermittedSubclasses())));
    }
    return (ObjectConverter<O>) converter;
  }
}
