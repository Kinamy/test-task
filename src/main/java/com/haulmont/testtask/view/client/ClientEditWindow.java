package com.haulmont.testtask.view.client;

import com.haulmont.testtask.DAO.ClientDAO;
import com.haulmont.testtask.model.entity.Client;
import com.haulmont.testtask.view.AbstractPersonWindow;
import com.vaadin.ui.*;

public class ClientEditWindow extends AbstractPersonWindow {

    public ClientEditWindow(Client client) {
        super("Изменение клиента");

        VerticalLayout content = new VerticalLayout();
        content.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        TextField firstName = createTextField("Имя");
        firstName.setValue(client.getFirstName());

        TextField lastName = createTextField("Фамилия");
        lastName.setValue(client.getLastName());

        TextField patronymic = createTextField("Отчество");
        patronymic.setValue(client.getPatronymic());

        TextField phone = createTextField("Телефон");
        phone.setValue(client.getPhone());

        Button editButton = getOkButton();
        editButton.addClickListener((Button.ClickListener) clickEvent -> {
            if (validate(firstName, lastName, patronymic, phone)) {
                Client newClient = new Client(client.getId(), firstName.getValue(), lastName.getValue(),
                        patronymic.getValue(), phone.getValue());
                ClientDAO clientDAO = new ClientDAO();
                clientDAO.updateClient(newClient);
                close();
            }
        });

        content.addComponentsAndExpand(firstName, lastName, patronymic, phone);
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.addComponents(editButton, getCancelButton());
        content.addComponent(buttonsLayout);
        setContent(content);
    }
}
