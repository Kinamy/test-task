package com.haulmont.testtask;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;

public class StartView extends VerticalLayout implements View {

    private static MenuBar menuBar;

    public StartView() {

        menuBar = new MenuBar();
        menuBar.addItem("Клиенты",
                (MenuBar.Command) selectedItem -> MainUI.navigator.navigateTo("client"));
        menuBar.addItem("Механики",
                (MenuBar.Command) selectedItem -> MainUI.navigator.navigateTo("mechanic"));
        menuBar.addItem("Заказы",
                (MenuBar.Command) selectedItem -> MainUI.navigator.navigateTo("order"));
        setMargin(true);
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponent(menuBar);
    }

    public static MenuBar getMenuBar() {
        return menuBar;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
