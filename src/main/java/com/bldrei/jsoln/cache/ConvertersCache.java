package com.bldrei.jsoln.cache;

import com.bldrei.jsoln.converter.ArrayConverter;
import com.bldrei.jsoln.converter.array.ListConverter;
import com.bldrei.jsoln.converter.array.SetConverter;
import com.bldrei.jsoln.converter.bool.BooleanConverter;
import com.bldrei.jsoln.converter.number.BigDecimalConverter;
import com.bldrei.jsoln.converter.number.BigIntegerConverter;
import com.bldrei.jsoln.converter.number.ByteConverter;
import com.bldrei.jsoln.converter.number.DoubleConverter;
import com.bldrei.jsoln.converter.number.FloatConverter;
import com.bldrei.jsoln.converter.number.IntegerConverter;
import com.bldrei.jsoln.converter.number.LongConverter;
import com.bldrei.jsoln.converter.number.NumberConverter;
import com.bldrei.jsoln.converter.number.ShortConverter;
import com.bldrei.jsoln.converter.text.EnumConverter;
import com.bldrei.jsoln.converter.text.LocalDateConverter;
import com.bldrei.jsoln.converter.text.LocalDateTimeConverter;
import com.bldrei.jsoln.converter.text.StringConverter;
import com.bldrei.jsoln.converter.text.TextConverter;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    Float.class, new FloatConverter(),
    BigDecimal.class, new BigDecimalConverter(),
    BigInteger.class, new BigIntegerConverter(),
    Short.class, new ShortConverter(),
    Byte.class, new ByteConverter()
  );
  @Getter
  private static final BooleanConverter booleanConverter = new BooleanConverter();
  private static final Map<Class<?>, ArrayConverter<?>> arrayConvertersCache = Map.of(
    List.class, new ListConverter(),
    Set.class, new SetConverter()
  );

  @SuppressWarnings("unchecked")
  public static <T> TextConverter<T> getTextConverter(Class<T> clazz) {
    var converter = clazz.isEnum()
      ? enumConvertersCache.computeIfAbsent(clazz, ecl -> new EnumConverter<>(clazz))
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
  public static <N> NumberConverter<N> getNumberConverter(Class<N> clazz) {
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
  public static <C> ArrayConverter<C> getArrayConverter(Class<C> clazz) {
    var converter = (ArrayConverter<C>) arrayConvertersCache.get(clazz);
    if (converter == null) {
      throw new IllegalStateException("""
        Unexpected json type: %s.
        Supported JsonArray converters are: %s"""
        .formatted(clazz, arrayConvertersCache.values()));
    }
    return converter;
  }
}
