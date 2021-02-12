package modelo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rafael
 */
public class ProdutoServico {

    private int id;
    private String nome;
    private boolean servico;
    private float valor;
    private int frequenciaAplicacao;
    private boolean registro_ativo = true;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isServico() {
        return servico;
    }

    public void setServico(boolean servico) {
        this.servico = servico;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public int getFrequenciaAplicacao() {
        return frequenciaAplicacao;
    }

    public void setFrequenciaAplicacao(int frequenciaAplicacao) {
        this.frequenciaAplicacao = frequenciaAplicacao;
    }

    public boolean isRegistro_ativo() {
        return registro_ativo;
    }

    public void setRegistro_ativo(boolean registro_ativo) {
        this.registro_ativo = registro_ativo;
    }

    @Override
    public String toString() {
        return "ProdutoServico{" + "id = " + id + ", nome = " + nome + ", "
                + " servico = " + servico + ", valor = " + valor
                + ", frequenciaAplicacao = " + frequenciaAplicacao + ", ativo = " + registro_ativo + '}';
    }

}
