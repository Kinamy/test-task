package com.haulmont.testtask.view.order;

import com.haulmont.testtask.DAO.ClientDAO;
import com.haulmont.testtask.DAO.MechanicDAO;
import com.haulmont.testtask.DAO.OrderDAO;
import com.haulmont.testtask.model.entity.Client;
import com.haulmont.testtask.model.entity.Mechanic;
import com.haulmont.testtask.model.entity.Order;
import com.haulmont.testtask.model.entity.OrderStatus;
import com.haulmont.testtask.view.AbstractOrderWindow;
import com.vaadin.ui.*;

import java.time.LocalDate;
import java.util.Locale;

public class OrderEditWindow extends AbstractOrderWindow {

    public OrderEditWindow(Order order) {
        super("Изменение заказа");

        VerticalLayout content = new VerticalLayout();
        content.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        TextField description = new TextField("Описание");
        description.setWidth(100, Unit.PERCENTAGE);
        description.setMaxLength(2000);
        description.setValue(order.getDescription());
        description.addValueChangeListener(click -> description.setStyleName("default"));
        ComboBox<Client> clientSelect = new ComboBox<>("Клиент");
        ClientDAO clientDAO = new ClientDAO();
        clientSelect.setItems(clientDAO.findAllClient());
        clientSelect.setWidth(100, Unit.PERCENTAGE);
        clientSelect.setValue(order.getClient());
        clientSelect.setEmptySelectionAllowed(false);
        clientSelect.addValueChangeListener(click -> clientSelect.setStyleName("default"));
        ComboBox<Mechanic> mechanicSelect = new ComboBox<>("Механик");
        MechanicDAO mechanicDAO = new MechanicDAO();
        mechanicSelect.setItems(mechanicDAO.findAllMechanic());
        mechanicSelect.setWidth(100, Unit.PERCENTAGE);
        mechanicSelect.setValue(order.getMechanic());
        mechanicSelect.setEmptySelectionAllowed(false);
        mechanicSelect.addValueChangeListener(click -> mechanicSelect.setStyleName("default"));
        DateField endDateField = new DateField("Дата окончания работ");
        endDateField.setWidth(100, Unit.PERCENTAGE);
        endDateField.setValue(order.getEndDate());
        endDateField.setLocale(new Locale("ru", "RU"));
        endDateField.addValueChangeListener(click -> endDateField.setStyleName("default"));
        TextField price = new TextField("Стоимость");
        price.setWidth(100, Unit.PERCENTAGE);
        price.setValue(order.getPrice().toString());
        price.addValueChangeListener(event -> price.setStyleName("default"));
        ComboBox<OrderStatus> orderStatusSelect = new ComboBox<>("Статус");
        orderStatusSelect.setItems(OrderStatus.values());
        orderStatusSelect.setWidth(100, Unit.PERCENTAGE);
        orderStatusSelect.setValue(order.getStatus());
        orderStatusSelect.setEmptySelectionAllowed(false);
        orderStatusSelect.addValueChangeListener(click -> orderStatusSelect.setStyleName("default"));

        setWidth(description, clientSelect, mechanicSelect, endDateField, price, orderStatusSelect);

        Button editButton = new Button("ОК");
        editButton.addClickListener((Button.ClickListener) clickEvent -> {
            if (validate(description, clientSelect, mechanicSelect, endDateField, price, orderStatusSelect)) {
                Order orderEdit = new Order(order.getId(), description.getValue(), clientSelect.getValue(),
                        mechanicSelect.getValue(), LocalDate.now(), endDateField.getValue(), Double.parseDouble(price.getValue()),
                        orderStatusSelect.getValue());
                OrderDAO orderDAO = new OrderDAO();
                orderDAO.updateOrder(orderEdit);
                close();
            }
        });

        content.addComponentsAndExpand(description, clientSelect, mechanicSelect, endDateField, price, orderStatusSelect);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.addComponents(editButton, getCancelButton());

        content.addComponent(buttonsLayout);
        setContent(content);
    }
}
