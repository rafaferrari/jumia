package com.jumia.presentation;

import com.google.common.collect.ImmutableList;
import java.util.List;

/**
 * @author rafael.ferrari
 */
public enum ConsoleOptionsEnum {

    INITIAL_DATE("initialDate Ex: 2016-01-01 00:00:00", "Initial Date"),
    FINAL_DATE("finalDate Ex: 2017-01-01 00:00:00", "Final Date"),
    MONTH_SORT("monthSort Ex: 1-3, 4-6, 7-12", "Month Sort");

    public static final List<ConsoleOptionsEnum> VALUES = ImmutableList.copyOf(ConsoleOptionsEnum.values());
    private final String name;
    private final String description;

    private ConsoleOptionsEnum(final String name, final String description) {
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
