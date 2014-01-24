
package com.adaofeliz.railstime.job.crawler.pt.refer.domain.station;

import org.codehaus.jackson.annotate.JsonProperty;

public class TiposDeComboiosServemEstacao {
    @JsonProperty("Nome")
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
