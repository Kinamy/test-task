package com.haulmont.testtask.view.mechanic;

import com.haulmont.testtask.DAO.MechanicDAO;
import com.haulmont.testtask.model.entity.Mechanic;
import com.haulmont.testtask.view.AbstractPersonWindow;
import com.vaadin.ui.*;

public class MechanicAddWindow extends AbstractPersonWindow {
    public MechanicAddWindow() {
        super("Добавление механика");

        VerticalLayout content = new VerticalLayout();
        content.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        TextField firstName = createTextField("Имя");

        TextField lastName = createTextField("Фамилия");

        TextField patronymic = createTextField("Отчество");

        TextField salary = createTextField("Почасовая ставка");

        Button addButton = getOkButton();
        addButton.addClickListener((Button.ClickListener) clickEvent -> {
            if (validate(firstName, lastName, patronymic, salary)) {
                Mechanic mechanic = new Mechanic(null, firstName.getValue(), lastName.getValue(),
                        patronymic.getValue(), Double.parseDouble(salary.getValue()));
                MechanicDAO mechanicDAO = new MechanicDAO();
                mechanicDAO.addMechanic(mechanic);
                close();
            }
        });

        content.addComponentsAndExpand(firstName, lastName, patronymic, salary);
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.addComponents(addButton, getCancelButton());
        content.addComponent(buttonsLayout);
        setContent(content);
    }
}
