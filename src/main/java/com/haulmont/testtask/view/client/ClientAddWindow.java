package com.haulmont.testtask.view.client;

import com.haulmont.testtask.DAO.ClientDAO;
import com.haulmont.testtask.model.entity.Client;
import com.haulmont.testtask.view.AbstractPersonWindow;
import com.vaadin.ui.*;

public class ClientAddWindow extends AbstractPersonWindow {
    public ClientAddWindow() {
        super("Добавление клиента");

        VerticalLayout content = new VerticalLayout();
        content.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        TextField firstName = createTextField("Имя");

        TextField lastName = createTextField("Фамилия");

        TextField patronymic = createTextField("Отчество");

        TextField phone = createTextField("Телефон");

        Button addButton = getOkButton();
        addButton.addClickListener((Button.ClickListener) clickEvent -> {
            if (validate(firstName, lastName, patronymic, phone)) {
                Client client = new Client(null, firstName.getValue(), lastName.getValue(),
                        patronymic.getValue(), phone.getValue());
                ClientDAO clientDAO = new ClientDAO();
                clientDAO.addClient(client);
                close();
            }
        });
        content.addComponentsAndExpand(firstName, lastName, patronymic, phone);
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.addComponents(addButton, getCancelButton());
        content.addComponent(buttonsLayout);
        setContent(content);
    }
}
