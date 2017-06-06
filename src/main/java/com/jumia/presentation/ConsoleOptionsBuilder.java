package com.jumia.presentation;

import com.google.gson.JsonObject;
import java.util.Optional;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rafael.ferrari
 */
public class ConsoleOptionsBuilder {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final Options OPTIONS = new Options();
    private final String[] arguments;
    private CommandLine cmd;
    private boolean isHelp;

    public ConsoleOptionsBuilder(final String... arguments) {
        this.arguments = arguments;
    }

    public ConsoleOptionsBuilder addOptions() {
        LOGGER.debug("Adding Options.");
        ConsoleOptionsEnum.VALUES.stream().forEach(optionEnum -> {
            this.OPTIONS.addOption(this.createOption(optionEnum.getName(), optionEnum.getDescription()));
        });
        LOGGER.debug("Options Added.");
        return this;
    }

    private Option createOption(final String name, final String description) {
        LOGGER.debug("Creating Options.");
        final Option.Builder builder = Option.builder(name);
        return builder.hasArg().desc(description).required().build();
    }

    public ConsoleOptionsBuilder validateArguments() {
        LOGGER.debug("Validating Arguments.");
        try {
            if (this.isHelpArgument()) {
                this.isHelp = true;
                this.addHelp();
            } else {
                final CommandLineParser parser = new DefaultParser();
                this.cmd = parser.parse(this.OPTIONS, this.arguments);
            }
        } catch (final ParseException ex) {
            LOGGER.error("Error Validating Arguments.");
            throw new IllegalArgumentException(ex.getMessage());
        }
        LOGGER.debug("Arguments Validated.");
        return this;
    }

    private boolean isHelpArgument() {
        return this.arguments.length == 0 || this.arguments[0].contains("-help");
    }

    private void addHelp() {
        LOGGER.debug("Adding Help To Show.");
        final HelpFormatter formatter = new HelpFormatter();
        formatter.setArgName("Insert the required parameter.");
        formatter.printHelp("help", this.OPTIONS);
    }

    public Optional<JsonObject> create() {
        LOGGER.debug("Creating.");
        if (this.isHelp) {
            return Optional.empty();
        }

        final JsonObject jsonObject = new JsonObject();
        for (final Option option : this.cmd.getOptions()) {
            jsonObject.addProperty(option.getOpt(), option.getValue());
        }

        LOGGER.debug("Created.");
        return Optional.of(jsonObject);
    }

}
