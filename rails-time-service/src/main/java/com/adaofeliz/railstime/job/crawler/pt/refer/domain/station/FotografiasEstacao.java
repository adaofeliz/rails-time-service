
package com.adaofeliz.railstime.job.crawler.pt.refer.domain.station;

import org.codehaus.jackson.annotate.JsonProperty;

public class FotografiasEstacao {
    @JsonProperty("Endereco")
    private String endereco;
    @JsonProperty("Titulo")
    private String titulo;

    public String getEndereco() {
        return this.endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
