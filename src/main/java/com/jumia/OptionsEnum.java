package com.jumia;

import com.google.common.collect.ImmutableList;
import java.util.List;

/**
 * @author rafael.ferrari
 */
public enum OptionsEnum {

    INITIAL_DATE("initialDate", "Initial Date"),
    FINAL_DATE("finalDate", "Final Date");

    public static final List<OptionsEnum> VALUES = ImmutableList.copyOf(OptionsEnum.values());
    private final String nome;
    private final String descricao;

    private OptionsEnum(final String nome, final String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() {
        return this.nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

}
