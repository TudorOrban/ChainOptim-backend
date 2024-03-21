package org.chainoptim.shared.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtil {

    public static <T> void setPropertyWithNullCheck(T object, String property, Object value) {
        if (value != null) {
            try {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(property, object.getClass());
                Method setter = propertyDescriptor.getWriteMethod();
                if (setter != null) {
                    setter.invoke(object, value);
                }
            } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
