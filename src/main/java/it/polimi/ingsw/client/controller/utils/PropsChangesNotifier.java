package it.polimi.ingsw.client.controller.utils;

import java.beans.*;
import java.lang.reflect.InvocationTargetException;

/**
 * Checks for the differences between the attributes of two instances of the same class
 * and notifies the "PropertyChangeListeners"
 *
 * @author Luca Guffanti
 * @param <T> the type of object to be checked
 */
public class PropsChangesNotifier<T> {

    public void checkAndNotify(T oldObj, T newObj, PropertyChangeSupport support) throws IntrospectionException {

        BeanInfo beanInfo = Introspector.getBeanInfo(oldObj.getClass());

        for (PropertyDescriptor propertyDesc : beanInfo.getPropertyDescriptors()) {
            String propertyName = propertyDesc.getName();

            // Necessary try/ catch because ClientState is not a bean
            try {
                Object value1 = propertyDesc.getReadMethod().invoke(oldObj);
                Object value2 = propertyDesc.getReadMethod().invoke(newObj);

                if ((value1 == null && value2 != null) || (value1 != null && !value1.equals(value2))) {
                    support.firePropertyChange(propertyName, value1, value2);
                }

            }catch (Exception ignored){}//ex.printStackTrace();}

        }
    }


}