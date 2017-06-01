package com.jumia;

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
    private final String[] argumentos;
    private boolean isHelp;

    public NotificadorBuilder(final String... argumentos) {
        this.argumentos = argumentos;
    }

    public NotificadorBuilder adicionarOpcoes() {
        OptionsEnum.VALUES.stream().forEach(opcaoEnum -> {
            this.options.addOption(this.criarOpcao(opcaoEnum.getNome(), opcaoEnum.getDescricao()));
        });
        return this;
    }

    private Option criarOpcao(final String nome, final String descricao) {
        final Option.Builder builder = Option.builder(nome);
        return builder.hasArg().desc(descricao).required().build();
    }

    public NotificadorBuilder validarArgumentos() {
        try {
            if (this.isArgumentoAjuda()) {
                this.isHelp = true;
                this.adicionarAjuda();
            } else {
                final CommandLineParser parser = new DefaultParser();
                this.cmd = parser.parse(this.options, this.argumentos);
            }
        } catch (final ParseException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
        return this;
    }

    private boolean isArgumentoAjuda() {
        return this.argumentos.length == 0 || this.argumentos[0].contains("-help");
    }

    private void adicionarAjuda() {
        final HelpFormatter formatter = new HelpFormatter();
        formatter.setArgName("Insira o parametro obrigatorio");
        formatter.printHelp("help", this.options);
    }

    public Optional<JsonObject> criarObjeto() {
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
