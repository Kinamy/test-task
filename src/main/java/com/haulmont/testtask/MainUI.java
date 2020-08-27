package com.haulmont.testtask;

import com.haulmont.testtask.DAO.MechanicDAO;
import com.haulmont.testtask.view.client.ClientView;
import com.haulmont.testtask.view.mechanic.MechanicView;
import com.haulmont.testtask.view.order.OrderView;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    public static Navigator navigator;

    public static final String CLIENT = "client";

    public static final String MECHANIC = "mechanic";

    public static final String ORDER = "order";

    MechanicDAO mechanicDAO = new MechanicDAO();

    @Override
    protected void init(VaadinRequest request) {
        ConnectionManager.createDB();
        if(mechanicDAO.findAllMechanic().isEmpty()){
            ConnectionManager.fillingData();
        }

        navigator = new Navigator(this, this);
        navigator.addView("", new StartView());
        navigator.addView(CLIENT, new ClientView());
        navigator.addView(MECHANIC, new MechanicView());
        navigator.addView(ORDER, new OrderView());
    }
}