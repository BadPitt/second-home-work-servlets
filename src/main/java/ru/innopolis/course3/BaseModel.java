package ru.innopolis.course3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * Created by danil on 24/12/16.
 */
public abstract class BaseModel {

    private static Logger logger = LoggerFactory.getLogger(BaseModel.class);

    // TODO: refactor it
    @Override
    public boolean equals(Object obj) {
        if (obj != null && this.getClass().getName().equals(obj.getClass().getName())) {
            try {
                Field[] thisFields = this.getClass().getDeclaredFields();
                boolean r = false;
                for (Field of : obj.getClass().getDeclaredFields()) {
                    boolean isFieldExists = false;
                    for (Field tf : thisFields) {
                        if (tf.getName().equalsIgnoreCase(of.getName())) {
                            tf.setAccessible(true);
                            of.setAccessible(true);
                            Object tfValue = tf.get(this);
                            Object ofValue = of.get(obj);
                            isFieldExists = (tfValue != null &&
                                    ofValue != null
                                    && tfValue.equals(ofValue)) ||
                                    (tfValue == null && ofValue == null);
                        }
                    }
                    if (!isFieldExists) {
                        return false;
                    } else {
                        r = isFieldExists;
                    }
                }
                return r;
            } catch (IllegalAccessException e) {
                logger.error("BaseModel equals() exception", e);
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 37;
        for (Field f: this.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            try {
                hash = hash * 17 + (f.get(this) != null ? f.get(this).hashCode() : 0);
            } catch (IllegalAccessException e) {
                logger.error("BaseModel hashCode() exception", e);
            }
        }
        return hash;
    }
}
