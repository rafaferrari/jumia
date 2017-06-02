package com.jumia.presentation;

import com.google.gson.JsonObject;
import com.jumia.domain.order.SearchOrderDTO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @author rafael.ferrari
 */
public class OrderDtoBuilder {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final Options options = new Options();
    private final String[] arguments;
    private CommandLine cmd;
    private boolean isHelp;

    public OrderDtoBuilder(final String... arguments) {
        this.arguments = arguments;
    }

    public OrderDtoBuilder addOptions() {
        OptionsEnum.VALUES.stream().forEach(optionEnum -> {
            this.options.addOption(this.createOption(optionEnum.getName(), optionEnum.getDescription()));
        });
        return this;
    }

    private Option createOption(final String name, final String description) {
        final Option.Builder builder = Option.builder(name);
        return builder.hasArg().desc(description).required().build();
    }

    public OrderDtoBuilder validateArguments() {
        try {
            if (this.isHelpArgument()) {
                this.isHelp = true;
                this.addHelp();
            } else {
                final CommandLineParser parser = new DefaultParser();
                this.cmd = parser.parse(this.options, this.arguments);
            }
        } catch (final ParseException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
        return this;
    }

    private boolean isHelpArgument() {
        return this.arguments.length == 0 || this.arguments[0].contains("-help");
    }

    private void addHelp() {
        final HelpFormatter formatter = new HelpFormatter();
        formatter.setArgName("Insert the required parameter.");
        formatter.printHelp("help", this.options);
    }

    public Optional<SearchOrderDTO> create() {
        if (this.isHelp) {
            return Optional.empty();
        }

        final JsonObject jsonObject = new JsonObject();
        for (final Option option : this.cmd.getOptions()) {
            jsonObject.addProperty(option.getOpt(), option.getValue());
        }

        final LocalDateTime initialDate = LocalDateTime.parse(jsonObject.get("initialDate").getAsString(), DATE_TIME_FORMATTER);
        final LocalDateTime finalDate = LocalDateTime.parse(jsonObject.get("finalDate").getAsString(), DATE_TIME_FORMATTER);
        validateRangeDate(initialDate, finalDate);

        final SearchOrderDTO orderDTO = new SearchOrderDTO();
        orderDTO.setInitialDate(initialDate);
        orderDTO.setFinalDate(finalDate);
        return Optional.of(orderDTO);
    }

    private void validateRangeDate(final LocalDateTime initialDate, final LocalDateTime finalDate) {
        if (initialDate.toLocalDate().isAfter(finalDate.toLocalDate())) {
            throw new IllegalStateException("Invalid Range of Dates.");
        }
    }

}
