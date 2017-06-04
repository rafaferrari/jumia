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
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Options options = new Options();
    private final String[] arguments;
    private CommandLine cmd;
    private boolean isHelp;
    
    public ConsoleOptionsBuilder(final String... arguments) {
        this.arguments = arguments;
    }
    
    public ConsoleOptionsBuilder addOptions() {
        logger.info("Adding Options.");
        ConsoleOptionsEnum.VALUES.stream().forEach(optionEnum -> {
            this.options.addOption(this.createOption(optionEnum.getName(), optionEnum.getDescription()));
        });
        logger.info("Options Added.");
        return this;
    }
    
    private Option createOption(final String name, final String description) {
        logger.info("Creating Options.");
        final Option.Builder builder = Option.builder(name);
        return builder.hasArg().desc(description).required().build();
    }
    
    public ConsoleOptionsBuilder validateArguments() {
        logger.info("Validating Arguments.");
        try {
            if (this.isHelpArgument()) {
                this.isHelp = true;
                this.addHelp();
            } else {
                final CommandLineParser parser = new DefaultParser();
                this.cmd = parser.parse(this.options, this.arguments);
            }
        } catch (final ParseException ex) {
            logger.error("Error Validating Arguments.");
            throw new IllegalArgumentException(ex.getMessage());
        }
        logger.info("Arguments Validated.");
        return this;
    }
    
    private boolean isHelpArgument() {
        return this.arguments.length == 0 || this.arguments[0].contains("-help");
    }
    
    private void addHelp() {
        logger.info("Adding Help To Show.");
        final HelpFormatter formatter = new HelpFormatter();
        formatter.setArgName("Insert the required parameter.");
        formatter.printHelp("help", this.options);
    }
    
    public Optional<JsonObject> create() {
        logger.info("Creating.");
        if (this.isHelp) {
            return Optional.empty();
        }
        
        final JsonObject jsonObject = new JsonObject();
        for (final Option option : this.cmd.getOptions()) {
            jsonObject.addProperty(option.getOpt(), option.getValue());
        }
        
        logger.info("Created.");
        return Optional.of(jsonObject);
    }
    
}
