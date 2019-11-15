package com.danbro.gmall.common.utils.util;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Danrbo
 * @date 2019/11/14 10:58
 * description
 **/
public class ObjectUtil {

    public static Map<String,String> objectToMap(Object object){
        Class<?> clazz = object.getClass();
        HashMap<String, String> objectMap = new HashMap<>(16);
        List<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null){
        List<Field> superClassFields = new ArrayList<>(Arrays.asList(superclass.getDeclaredFields()));
        fields.addAll(superClassFields);
        }
        for (Field field : fields) {
            field.setAccessible(true);
            String name = StringTransferUtil.camelCaseToUnderLine(field.getName());
            Object o = null;
            try {
                o = field.get(object);
                objectMap.put(name,o.toString());
            } catch (IllegalAccessException e) {
                objectMap.put(name,null);
            }
        }
        return objectMap;
    }

}
