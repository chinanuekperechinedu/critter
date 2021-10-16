package com.udacity.jdnd.course3.critter.utility;


import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.DateConverter;


import java.lang.reflect.InvocationTargetException;

public class Utils {
    public static void copyProperties(Object arg0, Object arg1) throws InvocationTargetException, IllegalAccessException {
        java.util.Date defaultValue = null;
        Converter converter = new DateConverter(defaultValue);
        BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance();
        beanUtilsBean.getConvertUtils().register(converter, java.util.Date.class);
        beanUtilsBean.copyProperties(arg0, arg1);
    }

}
