package com.csc345.gui.components;

import java.util.function.UnaryOperator;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.util.converter.DoubleStringConverter;

public class FloatField extends TextField {
    public FloatField(double defaultValue) {
        UnaryOperator<Change> floatFilter = change -> {
            if (change.isReplaced())
                if (change.getText().matches("[^0-9]"))
                    change.setText(change.getControlText().substring(change.getRangeStart(), change.getRangeEnd()));

            if (change.isAdded()) {
                if (change.getControlText().contains(".")) {
                    if (change.getText().matches("[^0-9]")) {
                        change.setText("");
                    }
                } else if (change.getText().matches("[^0-9.]")) {
                    change.setText("");
                }
            }

            return change;
        };

        this.setTextFormatter(new TextFormatter<Double>(new DoubleStringConverter(), defaultValue, floatFilter));
    }
    
    public double getDouble() {
        return Double.parseDouble(this.getText());
    }
}
