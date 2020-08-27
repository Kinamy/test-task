package com.haulmont.testtask.view.client;

import com.haulmont.testtask.DAO.ClientDAO;
import com.haulmont.testtask.StartView;
import com.haulmont.testtask.model.entity.Client;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;

public class ClientView extends StartView {
    public ClientView() {

        getMenuBar().getItems().get(0).setEnabled(false);

        ClientDAO clientDAO = new ClientDAO();

        Grid<Client> grid = new Grid();
        grid.setWidth(100, Unit.PERCENTAGE);
        grid.setItems(clientDAO.findAllClient());
        grid.addColumn(Client::getFirstName).setCaption("Имя");
        grid.addColumn(Client::getLastName).setCaption("Фамилия");
        grid.addColumn(Client::getPatronymic).setCaption("Отчество");
        grid.addColumn(Client::getPhone).setCaption("Телефон");

        grid.addColumn(patient -> "Изменить",
                new ButtonRenderer(clickEvent -> {
                    ClientEditWindow editWindow = new ClientEditWindow((Client) clickEvent.getItem());
                    editWindow.addCloseListener(closeEvent -> grid.setItems(clientDAO.findAllClient()));
                    UI.getCurrent().addWindow(editWindow);
                }));

        grid.addColumn(patient -> "Удалить",
                new ButtonRenderer(clickEvent -> {
                    if (clientDAO.removeClient((Client) clickEvent.getItem())) {
                        grid.setItems(clientDAO.findAllClient());
                    } else {
                        Notification.show("Данный клиент не может быть удалён");
                    }
                }));

        addComponentsAndExpand(grid);

        Button addButton = new Button("Добавить");
        addButton.addClickListener((Button.ClickListener) clickEvent -> {
            ClientAddWindow addWindow = new ClientAddWindow();
            addWindow.addCloseListener(closeEvent -> grid.setItems(clientDAO.findAllClient()));
            UI.getCurrent().addWindow(addWindow);
        });
        addComponent(addButton);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
