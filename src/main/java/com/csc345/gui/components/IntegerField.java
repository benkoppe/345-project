package com.csc345.gui.components;

import java.util.function.UnaryOperator;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.util.converter.IntegerStringConverter;

public class IntegerField extends TextField {
    public IntegerField(int defaultValue) {
        UnaryOperator<Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?([1-9][0-9]*)?")) {
                return change;
            }
            return null;
        };

        this.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), defaultValue, integerFilter));
    }

    public int getInt() {
        return Integer.parseInt(this.getText());
    }
}
