package com.haulmont.testtask.view.mechanic;

import com.haulmont.testtask.DAO.MechanicDAO;
import com.haulmont.testtask.StartView;
import com.haulmont.testtask.model.entity.Mechanic;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.renderers.ButtonRenderer;

public class MechanicView extends StartView {

    public MechanicView() {

        getMenuBar().getItems().get(1).setEnabled(false);

        MechanicDAO mechanicDAO = new MechanicDAO();

        Grid<Mechanic> grid = new Grid<>();
        grid.setWidth(100, Sizeable.Unit.PERCENTAGE);
        grid.setItems(mechanicDAO.findAllMechanic());
        grid.addColumn(Mechanic::getFirstName).setCaption("Имя");
        grid.addColumn(Mechanic::getLastName).setCaption("Фамилия");
        grid.addColumn(Mechanic::getPatronymic).setCaption("Отчество");
        grid.addColumn(Mechanic::getSalary).setCaption("Почасовая ставка");
        grid.addColumn(mechanic -> "Изменить",
                new ButtonRenderer(clickEvent -> {
                    MechanicEditWindow editWindow = new MechanicEditWindow((Mechanic) clickEvent.getItem());
                    editWindow.addCloseListener(closeEvent -> grid.setItems(mechanicDAO.findAllMechanic()));
                    UI.getCurrent().addWindow(editWindow);
                }));

        grid.addColumn(mechanic -> "Удалить",
                new ButtonRenderer(clickEvent -> {
                    if (mechanicDAO.removeMechanic((Mechanic) clickEvent.getItem())) {
                        grid.setItems(mechanicDAO.findAllMechanic());
                    } else {
                        Notification.show("Данный механик не может быть удалён");
                    }
                }));

        grid.addColumn(mechanic -> "Статистика",
                new ButtonRenderer<>(clickEvent -> {
                    MechanicStatisticsWindow statisticsWindow = new MechanicStatisticsWindow(
                            clickEvent.getItem(),
                            mechanicDAO.getStatistic(clickEvent.getItem())
                    );
                    UI.getCurrent().addWindow(statisticsWindow);
                }));

        addComponentsAndExpand(grid);

        Button addButton = new Button("Добавить");
        addButton.addClickListener((Button.ClickListener) clickEvent -> {
            MechanicAddWindow addWindow = new MechanicAddWindow();
            addWindow.addCloseListener(closeEvent -> grid.setItems(mechanicDAO.findAllMechanic()));
            UI.getCurrent().addWindow(addWindow);
        });
        addComponent(addButton);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
