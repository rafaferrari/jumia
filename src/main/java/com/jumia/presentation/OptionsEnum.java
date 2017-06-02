package com.jumia.presentation;

import com.google.common.collect.ImmutableList;
import java.util.List;

/**
 * @author rafael.ferrari
 */
public enum OptionsEnum {

    INITIAL_DATE("initialDate", "Initial Date"),
    FINAL_DATE("finalDate", "Final Date");

    public static final List<OptionsEnum> VALUES = ImmutableList.copyOf(OptionsEnum.values());
    private final String name;
    private final String description;

    private OptionsEnum(final String name, final String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

}
