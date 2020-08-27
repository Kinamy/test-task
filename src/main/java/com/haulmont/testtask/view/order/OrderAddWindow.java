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

public class OrderAddWindow extends AbstractOrderWindow {

    public OrderAddWindow() {
        super("Добавление заказа");

        VerticalLayout content = new VerticalLayout();
        content.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        TextField description = new TextField("Описание");
        description.setMaxLength(200);
        description.addValueChangeListener(event -> description.setStyleName("default"));
        ComboBox<Client> clientSelect = new ComboBox<>("Клиент");
        ClientDAO clientDAO = new ClientDAO();
        clientSelect.setItems(clientDAO.findAllClient());
        clientSelect.setEmptySelectionAllowed(false);
        clientSelect.addValueChangeListener(event -> clientSelect.setStyleName("default"));
        ComboBox<Mechanic> mechanicSelect = new ComboBox<>("Механик");
        MechanicDAO mechanicDAO = new MechanicDAO();
        mechanicSelect.setItems(mechanicDAO.findAllMechanic());
        mechanicSelect.setEmptySelectionAllowed(false);
        mechanicSelect.addValueChangeListener(event -> mechanicSelect.setStyleName("default"));
        DateField endDateField = new DateField("Дата окончания работ");
        endDateField.setValue(LocalDate.now());
        endDateField.setLocale(new Locale("ru", "RU"));
        endDateField.addValueChangeListener(event -> endDateField.setStyleName("default"));
        TextField price = new TextField("Стоимость");
        price.addValueChangeListener(event -> price.setStyleName("default"));
        ComboBox<OrderStatus> orderStatusSelect = new ComboBox<>("Статус");
        orderStatusSelect.setItems(OrderStatus.values());
        orderStatusSelect.setEmptySelectionAllowed(false);
        orderStatusSelect.addValueChangeListener(event -> orderStatusSelect.setStyleName("default"));

        setWidth(description, clientSelect, mechanicSelect, endDateField, price, orderStatusSelect);

        Button addButton = new Button("ОК");
        addButton.addClickListener((Button.ClickListener) clickEvent -> {
            if (validate(description, clientSelect, mechanicSelect, endDateField, price, orderStatusSelect)) {
                Order order = new Order(null, description.getValue(), clientSelect.getValue(),
                        mechanicSelect.getValue(), LocalDate.now(), endDateField.getValue(), Double.parseDouble(price.getValue()),
                        orderStatusSelect.getValue());
                OrderDAO orderDAO = new OrderDAO();
                orderDAO.addOrder(order);
                close();
            }
        });

        content.addComponentsAndExpand(description, clientSelect, mechanicSelect, endDateField, price, orderStatusSelect);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.addComponents(addButton, getCancelButton());

        content.addComponent(buttonsLayout);
        setContent(content);
    }
}
