package com.csc345.gui.components;

import java.util.function.UnaryOperator;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.util.converter.DoubleStringConverter;

/**
 * A TextField that only accepts floating point numbers.
 
 */
public class FloatField extends TextField {

    /**
     * Create a new FloatField with a given default value.
     * 
     * @param defaultValue the default value of the FloatField
     */
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
    
    /**
     * Get the value of the FloatField as a double.
     * 
     * @return the value of the FloatField as a double
     */
    public double getDouble() {
        return Double.parseDouble(this.getText());
    }
}
