package it.polimi.ingsw.client.controller.utils;

import java.beans.*;
import java.lang.reflect.InvocationTargetException;

/**
 * Checks for the differences between the attributes of two instances of the same class
 * and notifies the "PropertyChangeListeners"
 *
 * @author Luca Guffanti
 * @param <T>
 */
public class PropsChangesNotifier<T> {

    public void checkAndNotify(T oldObj, T newObj, PropertyChangeSupport support) throws InvocationTargetException, IllegalAccessException, IntrospectionException {

        BeanInfo beanInfo = Introspector.getBeanInfo(oldObj.getClass());

        for (PropertyDescriptor propertyDesc : beanInfo.getPropertyDescriptors()) {
            String propertyName = propertyDesc.getName();

            // Necessary try/ catch because ClientState is not a bean
            // @todo: not the cleanest solution
            try {
                Object value1 = propertyDesc.getReadMethod().invoke(oldObj);
                Object value2 = propertyDesc.getReadMethod().invoke(newObj);

                if ((value1 == null && value2 != null) || (value1 != null && !value1.equals(value2))) {
                    String value1Str = value1 == null ? "null" :  value1.toString();
                    String value2Str = value2 == null ? "null" :  value2.toString();

                    // System.out.println("===============FIRING===============");
                    // System.out.println("Differences on "+ propertyName+ ": "+value1Str+ " / " +value2Str);
                    // System.out.println("Firing property change on: " + propertyName);
                    // System.out.println("====================================");
                    support.firePropertyChange(propertyName, value1, value2);


                }

            }catch (Exception ex){}//ex.printStackTrace();}




        }
    }


}