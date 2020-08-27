package com.haulmont.testtask.view.mechanic;

import com.haulmont.testtask.DAO.MechanicDAO;
import com.haulmont.testtask.model.entity.Mechanic;
import com.haulmont.testtask.view.AbstractPersonWindow;
import com.vaadin.ui.*;

public class MechanicEditWindow extends AbstractPersonWindow {
    public MechanicEditWindow(Mechanic mechanic) {
        super("Изменение механика");

        VerticalLayout content = new VerticalLayout();
        content.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        TextField firstName = createTextField("Имя");
        firstName.setValue(mechanic.getFirstName());

        TextField lastName = createTextField("Фамилия");
        lastName.setValue(mechanic.getLastName());

        TextField patronymic = createTextField("Отчество");
        patronymic.setValue(mechanic.getPatronymic());

        TextField salary = createTextField("Почасовая ставка");
        salary.setValue(mechanic.getSalary().toString());

        Button editButton = getOkButton();
        editButton.addClickListener((Button.ClickListener) clickEvent -> {
            if (validate(firstName, lastName, patronymic, salary)) {
                Mechanic newMechanic = new Mechanic(mechanic.getId(), firstName.getValue(), lastName.getValue(),
                        patronymic.getValue(), Double.parseDouble(salary.getValue()));
                MechanicDAO mechanicDAO = new MechanicDAO();
                mechanicDAO.updateMechanic(newMechanic);
                close();
            }
        });

        content.addComponentsAndExpand(firstName, lastName, patronymic, salary);
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.addComponents(editButton, getCancelButton());
        content.addComponent(buttonsLayout);
        setContent(content);
    }
}
