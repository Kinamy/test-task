package com.haulmont.testtask.view.mechanic;

import com.haulmont.testtask.model.DTO.StatisticDTO;
import com.haulmont.testtask.model.entity.Mechanic;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class MechanicStatisticsWindow extends Window {
    public MechanicStatisticsWindow(Mechanic mechanic, StatisticDTO statisticDTO) {
        super("Статистика механика");
        setHeight(20, Unit.PERCENTAGE);
        setWidth(50, Unit.PERCENTAGE);
        center();

        VerticalLayout content = new VerticalLayout();
        content.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);

        Label name = new Label(mechanic.toString());
        name.addStyleName(ValoTheme.LABEL_H1);

        Label amount = new Label("Всего заказов: " + statisticDTO.getAmount());
        amount.addStyleName(ValoTheme.LABEL_H3);


        Button closeButton = new Button("ОК");
        closeButton.addClickListener(click -> close());

        content.addComponentsAndExpand(name, amount);
        content.addComponent(closeButton);
        content.setComponentAlignment(closeButton, Alignment.MIDDLE_CENTER);

        setContent(content);
    }
}
