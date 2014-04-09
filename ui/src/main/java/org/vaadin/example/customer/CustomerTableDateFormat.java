package org.vaadin.example.customer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

public class CustomerTableDateFormat implements Converter<String, Date> {

	@Override
	public Date convertToModel(String value, Class<? extends Date> targetType,
			Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		return null;
	}

	@Override
	public String convertToPresentation(Date value,
			Class<? extends String> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		return sdf.format(value);
	}

	@Override
	public Class<Date> getModelType() {
		return Date.class;
	}

	@Override
	public Class<String> getPresentationType() {
		return String.class;
	}
}
