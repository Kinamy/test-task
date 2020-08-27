package com.haulmont.testtask.view;

import com.haulmont.testtask.model.entity.Client;
import com.haulmont.testtask.model.entity.Mechanic;
import com.haulmont.testtask.model.entity.OrderStatus;
import com.haulmont.testtask.view.AbstractModalWindow;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;

public class AbstractOrderWindow extends AbstractModalWindow {

    public AbstractOrderWindow(String caption) {
        super(caption);
    }

    public static void setWidth(AbstractComponent... components) {
        for (AbstractComponent component : components)
            component.setWidth(100, Unit.PERCENTAGE);
    }

    public static boolean validate(TextField description, ComboBox<Client> clientSelect,
                                   ComboBox<Mechanic> mechanicSelect, DateField endDateField, TextField price,
                                   ComboBox<OrderStatus> orderStatusSelect) {
        boolean isValid = true;
        if (description.getValue() == null || description.getValue().isEmpty()) {
            description.setStyleName("error");
            isValid = false;
        }
        if (clientSelect.getValue() == null) {
            clientSelect.setStyleName("error");
            isValid = false;
        }
        if (mechanicSelect.getValue() == null) {
            mechanicSelect.setStyleName("error");
            isValid = false;
        }
        if (endDateField.getValue() == null) {
            endDateField.setStyleName("error");
            isValid = false;
        }
        if (price.getValue() == null || price.getValue().isEmpty()) {
            price.setStyleName("error");
            isValid = false;
        }
        if (orderStatusSelect.getValue() == null) {
            orderStatusSelect.setStyleName("error");
            isValid = false;
        }

        return isValid;
    }
}
