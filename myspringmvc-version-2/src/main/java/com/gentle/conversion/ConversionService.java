package com.gentle.conversion;

/**
 * @author Gentle
 * @date 2019/06/20 : 17:20
 */
public interface ConversionService {

    //    Object convert( Object source,  TypeDescriptor sourceType, TypeDescriptor targetType);
    Object convert(String source, Object targetType);

    <T> T convert(String source, Class<T> targetType);
}
