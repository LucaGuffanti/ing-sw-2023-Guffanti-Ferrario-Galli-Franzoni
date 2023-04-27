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
            Object value1 = propertyDesc.getReadMethod().invoke(oldObj);
            Object value2 = propertyDesc.getReadMethod().invoke(newObj);

            if (!value1.equals(value2)) {
                System.out.println("===============FIRING===============");
                System.out.println("Differences on "+ propertyName+ ": "+value1.toString()+ " / " +value2.toString());
                System.out.println("Firing property change on: " + propertyName);
                support.firePropertyChange(propertyName, value1, value2);
                System.out.println("====================================");

            }

        }
    }


}