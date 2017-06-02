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

/**
 * @author rafael.ferrari
 */
public class NotificadorBuilder {

    private CommandLine cmd;
    private final Options options = new Options();
    private final String[] arguments;
    private boolean isHelp;

    public NotificadorBuilder(final String... arguments) {
        this.arguments = arguments;
    }

    public NotificadorBuilder addOptions() {
        OptionsEnum.VALUES.stream().forEach(optionEnum -> {
            this.options.addOption(this.createOption(optionEnum.getName(), optionEnum.getDescription()));
        });
        return this;
    }

    private Option createOption(final String name, final String description) {
        final Option.Builder builder = Option.builder(name);
        return builder.hasArg().desc(description).required().build();
    }

    public NotificadorBuilder validateArguments() {
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
        formatter.setArgName("Insira o parametro obrigatorio");
        formatter.printHelp("help", this.options);
    }

    public Optional<JsonObject> create() {
        if (this.isHelp) {
            return Optional.empty();
        }
        final JsonObject jsonObject = new JsonObject();
        for (final Option option : this.cmd.getOptions()) {
            jsonObject.addProperty(option.getOpt(), option.getValue());
        }
        return Optional.of(jsonObject);
    }

}
