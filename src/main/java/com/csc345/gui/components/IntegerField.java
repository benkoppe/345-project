package com.csc345.gui.components;

import java.util.function.UnaryOperator;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.util.converter.IntegerStringConverter;

/**
 * A TextField that only accepts integer numbers.
 
 */
public class IntegerField extends TextField {
    
    /**
     * Create a new IntegerField with a given default value.
     * 
     * @param defaultValue the default value of the IntegerField
     */
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

    /**
     * Get the value of the IntegerField as an integer.
     * 
     * @return the value of the IntegerField as an integer
     */
    public int getInt() {
        return Integer.parseInt(this.getText());
    }
}
