
package com.adaofeliz.railstime.job.crawler.pt.refer.domain.station;

import org.codehaus.jackson.annotate.JsonProperty;

public class OperadoresServemEstacao {
    @JsonProperty("Nome")
    private String nome;

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
