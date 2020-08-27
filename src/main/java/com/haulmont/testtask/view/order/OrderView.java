package com.haulmont.testtask.view.order;

import com.haulmont.testtask.DAO.ClientDAO;
import com.haulmont.testtask.DAO.OrderDAO;
import com.haulmont.testtask.StartView;
import com.haulmont.testtask.model.entity.Client;
import com.haulmont.testtask.model.entity.FilterType;
import com.haulmont.testtask.model.entity.Order;
import com.haulmont.testtask.model.entity.OrderStatus;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;

public class OrderView extends StartView {

    public OrderView() {

        getMenuBar().getItems().get(2).setEnabled(false);

        OrderDAO orderDAO = new OrderDAO();

        Grid<Order> grid = new Grid<>();
        grid.setWidth(100, Unit.PERCENTAGE);
        HorizontalLayout filterBlock = new HorizontalLayout();
        HorizontalLayout filterInput = new HorizontalLayout();
        ComboBox<FilterType> typeSelect = new ComboBox<>();
        typeSelect.setItems(FilterType.values());
        typeSelect.setEmptySelectionAllowed(false);
        Button doFilter = new Button("Поиск");
        filterBlock.addComponents(typeSelect, filterInput, doFilter);
        TextField filterText = new TextField();
        filterText.addValueChangeListener(event -> filterText.setStyleName("default"));
        ComboBox<Client> clientSelect = new ComboBox<>();
        clientSelect.setEmptySelectionAllowed(false);
        clientSelect.addValueChangeListener(event -> filterText.setStyleName("default"));
        ComboBox<OrderStatus> orderStatusSelect = new ComboBox<>();
        orderStatusSelect.setEmptySelectionAllowed(false);
        orderStatusSelect.addValueChangeListener(event -> filterText.setStyleName("default"));
        typeSelect.addValueChangeListener(event -> {
            if (typeSelect.getValue().equals(FilterType.ОПИСАНИЕ)) {
                filterInput.removeAllComponents();
                filterInput.addComponent(filterText);
            } else if (typeSelect.getValue().equals(FilterType.СТАТУС)) {
                filterInput.removeAllComponents();
                orderStatusSelect.setItems(OrderStatus.values());
                filterInput.addComponent(orderStatusSelect);
            } else if (typeSelect.getValue().equals(FilterType.КЛИЕНТ)) {
                filterInput.removeAllComponents();
                ClientDAO clientDAO = new ClientDAO();
                clientSelect.setItems(clientDAO.findAllClient());
                filterInput.addComponent(clientSelect);
            } else {
                filterInput.removeAllComponents();
            }
        });

        doFilter.addClickListener(click -> {
            if (validateFilterInput(typeSelect, filterText, clientSelect, orderStatusSelect)) {
                if (typeSelect.getValue().equals(FilterType.ОПИСАНИЕ)) {
                    grid.setItems(orderDAO.findByDescription(filterText.getValue()));
                } else if (typeSelect.getValue().equals(FilterType.СТАТУС)) {
                    grid.setItems(orderDAO.findByStatus(orderStatusSelect.getValue()));
                } else if (typeSelect.getValue().equals(FilterType.КЛИЕНТ)) {
                    grid.setItems(orderDAO.findByClient(clientSelect.getValue()));
                } else {
                    grid.setItems(orderDAO.findAllOrder());
                }
            }
        });

        addComponent(filterBlock);
        setComponentAlignment(filterBlock, Alignment.MIDDLE_RIGHT);
        grid.setItems(orderDAO.findAllOrder());

        grid.addColumn(Order::getDescription).setCaption("Описание");
        grid.addColumn(Order::getClient).setCaption("Клиент");
        grid.addColumn(Order::getMechanic).setCaption("Механик");
        grid.addColumn(Order::getCreateDate).setCaption("Дата создания");
        grid.addColumn(Order::getEndDate).setCaption("Дата окончания работ");
        grid.addColumn(Order::getPrice).setCaption("Стоимость");
        grid.addColumn(Order::getStatus).setCaption("Статус");

        grid.addColumn(order -> "Изменить",
                new ButtonRenderer(clickEvent -> {
                    OrderEditWindow orderEditWindow = new OrderEditWindow((Order) clickEvent.getItem());
                    orderEditWindow.addCloseListener(closeEvent -> grid.setItems(orderDAO.findAllOrder()));
                    UI.getCurrent().addWindow(orderEditWindow);
                }));

        grid.addColumn(order -> "Удалить",
                new ButtonRenderer(clickEvent -> {
                    orderDAO.removeOrder((Order) clickEvent.getItem());
                    grid.setItems(orderDAO.findAllOrder());
                }));

        addComponentsAndExpand(grid);

        Button addButton = new Button("Добавить");
        addButton.addClickListener((Button.ClickListener) clickEvent -> {
            OrderAddWindow orderAddWindow = new OrderAddWindow();
            orderAddWindow.addCloseListener(closeEvent -> grid.setItems(orderDAO.findAllOrder()));
            UI.getCurrent().addWindow(orderAddWindow);
        });

        addComponent(addButton);
    }

    private static boolean validateFilterInput(ComboBox<FilterType> typeSelect,
                                               TextField filterText,
                                               ComboBox<Client> clientSelect,
                                               ComboBox<OrderStatus> orderStatusSelect) {
        boolean isValid = true;
        if (typeSelect.getValue().equals(FilterType.ОПИСАНИЕ)) {
            if (filterText.getValue() == null || filterText.getValue().isEmpty()) {
                filterText.setStyleName("error");
                isValid = false;
            }
        } else if (typeSelect.getValue().equals(FilterType.СТАТУС)) {
            if (orderStatusSelect.getValue() == null) {
                orderStatusSelect.setStyleName("error");
                isValid = false;
            }
        } else if (typeSelect.getValue().equals(FilterType.КЛИЕНТ)) {
            if (clientSelect.getValue() == null) {
                clientSelect.setStyleName("error");
                isValid = false;
            }
        }
        return isValid;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
