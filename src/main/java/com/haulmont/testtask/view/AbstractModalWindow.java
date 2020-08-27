package com.haulmont.testtask.view;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.Window;

public class AbstractModalWindow extends Window {

    private final Button okButton;

    private final Button cancelButton;

    public AbstractModalWindow(String caption) {
        super(caption);

        setModal(true);
        setResizable(false);
        setClosable(false);
        setHeight(50, Unit.PERCENTAGE);
        setWidth(20, Unit.PERCENTAGE);

        okButton = new Button("ОК");

        cancelButton = new Button("Отменить");
        cancelButton.addClickListener((Button.ClickListener) clickEvent -> close());
    }

    public Button getOkButton() {
        return okButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }
}
