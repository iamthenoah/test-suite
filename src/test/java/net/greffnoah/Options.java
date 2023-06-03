package net.greffnoah;

import io.cucumber.java.ParameterType;

public class Options {

    @ParameterType("true|True|TRUE|false|False|FALSE")
    public Boolean booleanValue(String value) {
        return Boolean.valueOf(value);
    }
}
