package com.haulmont.testtask.view;

import com.vaadin.ui.TextField;

public class AbstractPersonWindow extends AbstractModalWindow {
    public AbstractPersonWindow(String caption) {
        super(caption);
    }

    public static TextField createTextField(String caption) {
        TextField textField = new TextField(caption);
        textField.setWidth(100, Unit.PERCENTAGE);
        textField.setMaxLength(255);
        textField.addValueChangeListener(click -> textField.setStyleName("default"));
        return textField;
    }

    public static boolean validate(TextField... textField) {
        boolean isValid = true;
        for (TextField field : textField) {
            if (field.getValue() == null || field.getValue().isEmpty()) {
                field.setStyleName("error");
                isValid = false;
            }
        }

        return isValid;
    }
}
