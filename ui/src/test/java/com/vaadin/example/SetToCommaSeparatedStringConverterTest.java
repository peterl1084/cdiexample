package com.vaadin.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.vaadin.example.customer.CollectionToCommaSeparatedStringConverter;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.OptionGroup;

public class SetToCommaSeparatedStringConverterTest {

    @Test
    public void testSetToCommaSepartedString_OK() {
        CollectionToCommaSeparatedStringConverter converter = new CollectionToCommaSeparatedStringConverter();

        List<String> setValue = new ArrayList<>();
        setValue.add("first");
        setValue.add("second");

        Assert.assertEquals("first,second",
                converter.convertToModel(setValue, String.class, null));
    }

    @Test
    public void testEmptySetToEmptyString_OK() {
        CollectionToCommaSeparatedStringConverter converter = new CollectionToCommaSeparatedStringConverter();

        Set<String> setValue = new HashSet<>();

        Assert.assertEquals("",
                converter.convertToModel(setValue, String.class, null));
    }

    @Test
    public void testNullSetToNullString_OK() {
        CollectionToCommaSeparatedStringConverter converter = new CollectionToCommaSeparatedStringConverter();

        Assert.assertNull(converter.convertToModel(null, String.class, null));
    }

    @Test
    public void testCommaSeparatedStringToCollection_OK() {
        CollectionToCommaSeparatedStringConverter converter = new CollectionToCommaSeparatedStringConverter();

        List<String> setValue = new ArrayList<>();
        setValue.add("first");
        setValue.add("second");
        setValue.add("third");

        Object presentation = converter.convertToPresentation(
                "first,second,third", Collection.class, null);

        Assert.assertTrue(Collection.class.isAssignableFrom(presentation
                .getClass()));

        Collection<Object> collectionPresentation = (Collection<Object>) presentation;

        for (Object objectValue : collectionPresentation) {
            Assert.assertTrue(setValue.contains(objectValue));
        }
    }

    @Test
    public void testWithNonMultiSelectOptionGroup_OK() {
        OptionGroup optionGroup = new OptionGroup();
        optionGroup.setMultiSelect(false);
        optionGroup
                .setConverter(new CollectionToCommaSeparatedStringConverter());

        ObjectProperty<String> objectValue = new ObjectProperty<String>("");

        optionGroup.addItem("first");
        optionGroup.addItem("second");
        optionGroup.addItem("third");

        optionGroup.setPropertyDataSource(objectValue);

        optionGroup.select("first");
        Assert.assertEquals("first", objectValue.getValue());

        optionGroup.select("second");
        Assert.assertEquals("second", objectValue.getValue());
    }

    @Test
    public void testWithMultiSelectOptionGroup_OK() {
        OptionGroup optionGroup = new OptionGroup();
        optionGroup.setMultiSelect(true);
        optionGroup
                .setConverter(new CollectionToCommaSeparatedStringConverter());

        ObjectProperty<String> objectValue = new ObjectProperty<String>("");

        optionGroup.addItem("first");
        optionGroup.addItem("second");
        optionGroup.addItem("third");

        optionGroup.setPropertyDataSource(objectValue);

        optionGroup.select("first");
        optionGroup.select("second");

        Assert.assertTrue(objectValue.getValue().contains("first"));
        Assert.assertTrue(objectValue.getValue().contains("second"));
    }
}
