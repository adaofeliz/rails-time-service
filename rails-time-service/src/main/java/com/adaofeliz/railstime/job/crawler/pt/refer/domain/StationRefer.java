package com.adaofeliz.railstime.job.crawler.pt.refer.domain;

import com.adaofeliz.railstime.job.crawler.pt.refer.domain.station.FotografiasEstacao;
import com.adaofeliz.railstime.job.crawler.pt.refer.domain.station.OperadoresServemEstacao;
import com.adaofeliz.railstime.job.crawler.pt.refer.domain.station.ServicosEquipamentos;
import com.adaofeliz.railstime.job.crawler.pt.refer.domain.station.TiposDeComboiosServemEstacao;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class StationRefer {

    @JsonProperty("AcessoMobilidadeReduzida")
    public String acessoMobilidadeReduzida;
    @JsonProperty("AeroportoDistancia")
    public String aeroportoDistancia;
    @JsonProperty("AeroportoNome")
    public String aeroportoNome;
    @JsonProperty("AnoContrucao")
    public String anoContrucao;
    @JsonProperty("Bilheteira")
    public String bilheteira;
    @JsonProperty("BombeirosNome")
    public String bombeirosNome;
    @JsonProperty("BombeirosTelefone")
    public String bombeirosTelefone;
    @JsonProperty("CentroCidadeProximaDistancia")
    public String centroCidadeProximaDistancia;
    @JsonProperty("CidadeProxima")
    public String cidadeProxima;
    @JsonProperty("CidadeProximaDistancia")
    public String cidadeProximaDistancia;
    @JsonProperty("Coordenadas")
    public String coordenadas;
    @JsonProperty("DescricaoEstacao")
    public String descricaoEstacao;
    @JsonProperty("EPClassificadoInteresse")
    public String ePClassificadoInteresse;
    @JsonProperty("EquipamentomobilidadeReduzidaPlataformas")
    public String equipamentomobilidadeReduzidaPlataformas;
    @JsonProperty("EstacionamentoBicicletas")
    public String estacionamentoBicicletas;
    @JsonProperty("FarmaciaDistancia")
    public String farmaciaDistancia;
    @JsonProperty("FarmaciaNome")
    public String farmaciaNome;
    @JsonProperty("FotografiasEstacao")
    public List<FotografiasEstacao> fotografiasEstacao;
    @JsonProperty("HorarioBilheteira")
    public String horarioBilheteira;
    @JsonProperty("HorarioEstacao")
    public String horarioEstacao;
    @JsonProperty("HospitalNome")
    public String hospitalNome;
    @JsonProperty("HospitalTelefone")
    public String hospitalTelefone;
    @JsonProperty("Linha")
    public String linha;
    @JsonProperty("Morada")
    public String morada;
    @JsonProperty("NodeID")
    public String nodeID;
    @JsonProperty("Nome")
    public String nome;
    @JsonProperty("OperadoresServemEstacao")
    public List<OperadoresServemEstacao> operadoresServemEstacao;
    @JsonProperty("PK")
    public String pK;
    @JsonProperty("PoliciaNome")
    public String policiaNome;
    @JsonProperty("PoliciaTelefone")
    public String policiaTelefone;
    @JsonProperty("ServicosEquipamentos")
    public ServicosEquipamentos servicosEquipamentos;
    @JsonProperty("TarifaEstacionamento")
    public String tarifaEstacionamento;
    @JsonProperty("Telefone")
    public String telefone;
    @JsonProperty("tiposDeComboiosServemEstacao")
    public List<TiposDeComboiosServemEstacao> tiposDeComboiosServemEstacao;

    public String getAcessoMobilidadeReduzida() {
        return this.acessoMobilidadeReduzida;
    }

    public void setAcessoMobilidadeReduzida(String acessoMobilidadeReduzida) {
        this.acessoMobilidadeReduzida = acessoMobilidadeReduzida;
    }

    public String getAeroportoDistancia() {
        return this.aeroportoDistancia;
    }

    public void setAeroportoDistancia(String aeroportoDistancia) {
        this.aeroportoDistancia = aeroportoDistancia;
    }

    public String getAeroportoNome() {
        return this.aeroportoNome;
    }

    public void setAeroportoNome(String aeroportoNome) {
        this.aeroportoNome = aeroportoNome;
    }

    public String getAnoContrucao() {
        return this.anoContrucao;
    }

    public void setAnoContrucao(String anoContrucao) {
        this.anoContrucao = anoContrucao;
    }

    public String getBilheteira() {
        return this.bilheteira;
    }

    public void setBilheteira(String bilheteira) {
        this.bilheteira = bilheteira;
    }

    public String getBombeirosNome() {
        return this.bombeirosNome;
    }

    public void setBombeirosNome(String bombeirosNome) {
        this.bombeirosNome = bombeirosNome;
    }

    public String getBombeirosTelefone() {
        return this.bombeirosTelefone;
    }

    public void setBombeirosTelefone(String bombeirosTelefone) {
        this.bombeirosTelefone = bombeirosTelefone;
    }

    public String getCentroCidadeProximaDistancia() {
        return this.centroCidadeProximaDistancia;
    }

    public void setCentroCidadeProximaDistancia(String centroCidadeProximaDistancia) {
        this.centroCidadeProximaDistancia = centroCidadeProximaDistancia;
    }

    public String getCidadeProxima() {
        return this.cidadeProxima;
    }

    public void setCidadeProxima(String cidadeProxima) {
        this.cidadeProxima = cidadeProxima;
    }

    public String getCidadeProximaDistancia() {
        return this.cidadeProximaDistancia;
    }

    public void setCidadeProximaDistancia(String cidadeProximaDistancia) {
        this.cidadeProximaDistancia = cidadeProximaDistancia;
    }

    public String getCoordenadas() {
        return this.coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public String getDescricaoEstacao() {
        return this.descricaoEstacao;
    }

    public void setDescricaoEstacao(String descricaoEstacao) {
        this.descricaoEstacao = descricaoEstacao;
    }

    public String getEPClassificadoInteresse() {
        return this.ePClassificadoInteresse;
    }

    public void setEPClassificadoInteresse(String ePClassificadoInteresse) {
        this.ePClassificadoInteresse = ePClassificadoInteresse;
    }

    public String getEquipamentomobilidadeReduzidaPlataformas() {
        return this.equipamentomobilidadeReduzidaPlataformas;
    }

    public void setEquipamentomobilidadeReduzidaPlataformas(String equipamentomobilidadeReduzidaPlataformas) {
        this.equipamentomobilidadeReduzidaPlataformas = equipamentomobilidadeReduzidaPlataformas;
    }

    public String getEstacionamentoBicicletas() {
        return this.estacionamentoBicicletas;
    }

    public void setEstacionamentoBicicletas(String estacionamentoBicicletas) {
        this.estacionamentoBicicletas = estacionamentoBicicletas;
    }

    public String getFarmaciaDistancia() {
        return this.farmaciaDistancia;
    }

    public void setFarmaciaDistancia(String farmaciaDistancia) {
        this.farmaciaDistancia = farmaciaDistancia;
    }

    public String getFarmaciaNome() {
        return this.farmaciaNome;
    }

    public void setFarmaciaNome(String farmaciaNome) {
        this.farmaciaNome = farmaciaNome;
    }

    public List<FotografiasEstacao> getFotografiasEstacao() {
        return this.fotografiasEstacao;
    }

    public void setFotografiasEstacao(List<FotografiasEstacao> fotografiasEstacao) {
        this.fotografiasEstacao = fotografiasEstacao;
    }

    public String getHorarioBilheteira() {
        return this.horarioBilheteira;
    }

    public void setHorarioBilheteira(String horarioBilheteira) {
        this.horarioBilheteira = horarioBilheteira;
    }

    public String getHorarioEstacao() {
        return this.horarioEstacao;
    }

    public void setHorarioEstacao(String horarioEstacao) {
        this.horarioEstacao = horarioEstacao;
    }

    public String getHospitalNome() {
        return this.hospitalNome;
    }

    public void setHospitalNome(String hospitalNome) {
        this.hospitalNome = hospitalNome;
    }

    public String getHospitalTelefone() {
        return this.hospitalTelefone;
    }

    public void setHospitalTelefone(String hospitalTelefone) {
        this.hospitalTelefone = hospitalTelefone;
    }

    public String getLinha() {
        return this.linha;
    }

    public void setLinha(String linha) {
        this.linha = linha;
    }

    public String getMorada() {
        return this.morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getNodeID() {
        return this.nodeID;
    }

    public void setNodeID(String nodeID) {
        this.nodeID = nodeID;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<OperadoresServemEstacao> getOperadoresServemEstacao() {
        return this.operadoresServemEstacao;
    }

    public void setOperadoresServemEstacao(List<OperadoresServemEstacao> operadoresServemEstacao) {
        this.operadoresServemEstacao = operadoresServemEstacao;
    }

    public String getPK() {
        return this.pK;
    }

    public void setPK(String pK) {
        this.pK = pK;
    }

    public String getPoliciaNome() {
        return this.policiaNome;
    }

    public void setPoliciaNome(String policiaNome) {
        this.policiaNome = policiaNome;
    }

    public String getPoliciaTelefone() {
        return this.policiaTelefone;
    }

    public void setPoliciaTelefone(String policiaTelefone) {
        this.policiaTelefone = policiaTelefone;
    }

    public ServicosEquipamentos getServicosEquipamentos() {
        return this.servicosEquipamentos;
    }

    public void setServicosEquipamentos(ServicosEquipamentos servicosEquipamentos) {
        this.servicosEquipamentos = servicosEquipamentos;
    }

    public String getTarifaEstacionamento() {
        return this.tarifaEstacionamento;
    }

    public void setTarifaEstacionamento(String tarifaEstacionamento) {
        this.tarifaEstacionamento = tarifaEstacionamento;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<TiposDeComboiosServemEstacao> getTiposDeComboiosServemEstacao() {
        return this.tiposDeComboiosServemEstacao;
    }

    public void setTiposDeComboiosServemEstacao(List<TiposDeComboiosServemEstacao> tiposDeComboiosServemEstacao) {
        this.tiposDeComboiosServemEstacao = tiposDeComboiosServemEstacao;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
        System.out.println("Ignored: " + key + " value: " + value);
    }
}
