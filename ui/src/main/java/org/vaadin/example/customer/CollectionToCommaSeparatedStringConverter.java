package org.vaadin.example.customer;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import com.vaadin.data.util.converter.Converter;

// TODO looks like a pretty generic helper, move to Maddon
public class CollectionToCommaSeparatedStringConverter implements
        Converter<Object, String> {

    private static final long serialVersionUID = 2561720427210173006L;

    @Override
    public String convertToModel(Object value,
            Class<? extends String> targetType, Locale locale)
            throws com.vaadin.data.util.converter.Converter.ConversionException {
        if (value == null) {
            return null;
        }

        if (Collection.class.isAssignableFrom(value.getClass())) {
            StringBuilder builder = new StringBuilder();

            Collection<Object> valueSet = (Collection<Object>) value;
            Iterator<Object> valueIterator = valueSet.iterator();

            while (valueIterator.hasNext()) {
                Object valueObject = valueIterator.next();

                builder.append(valueObject.toString());

                if (valueIterator.hasNext()) {
                    builder.append(",");
                }
            }

            return builder.toString();
        }

        return value.toString();
    }

    @Override
    public Object convertToPresentation(String value,
            Class<? extends Object> targetType, Locale locale)
            throws ConversionException {

        if (value == null)
            return null;

        if (targetType.equals(Object.class)) {
            return value;
        }

        Set<String> values = new HashSet<String>();
        for (String item : Arrays.asList(value.split(","))) {
            if (!item.isEmpty()) {
                values.add(item);
            }
        }

        return values;
    }

    @Override
    public Class<String> getModelType() {
        return String.class;
    }

    @Override
    public Class<Object> getPresentationType() {
        return Object.class;
    }
}
