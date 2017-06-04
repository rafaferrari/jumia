package com.jumia.presentation;

import com.google.common.collect.ImmutableList;
import java.util.List;

/**
 * Enum with Console Options to Pass as Arguments.
 *
 * @author rafael.ferrari
 */
public enum ConsoleOptionsEnum {

    INITIAL_DATE("initialDate", "Initial Date. Ex: 2016-01-01 00:00:00"),
    FINAL_DATE("finalDate", "Final Date. Ex: 2017-01-01 00:00:00"),
    MONTH_SORT("monthSort", "Month Sort Interval. Ex: 1-3, 4-6, 7-12");

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
