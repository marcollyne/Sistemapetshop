/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import modelo.Animal;
import modelo.ItemVenda;
import modelo.Venda;
import modelo.Pessoa;
import modelo.ProdutoServico;

/**
 *
 * @author karol
 */
public class VendaDAO {

    public void gravar(Venda venda) throws Exception {
        if (venda.getId() == 0) {
            inserir(venda);
        } else {
            alterar(venda);
        }
        inserirOuAtualizarItensVenda(venda);

    }

    private void inserir(Venda venda) throws Exception {

        String sql = "insert into venda (pessoa_id, data_hora) "
                + "values (?, ?)";

        PreparedStatement ps = Conexao.getConexao().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, venda.getCliente().getId());
        ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));

        ps.executeUpdate();//Roda o insert no BD
        ResultSet genKeys = ps.getGeneratedKeys();
        if (genKeys.next()) {
            venda.setId(genKeys.getInt(1));
        }
        inserirOuAtualizarItensVenda(venda);
    }

    private void alterar(Venda venda) throws Exception {
        String sql = "update venda set "
                + "pessoa_id = ?, data_hora = ?, registro_ativo = ? "
                + "where id = ? ";

        PreparedStatement ps = Conexao.getConexao().prepareStatement(sql);
        ps.setInt(1, venda.getCliente().getId());
        ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
        ps.setBoolean(3, venda.isAtivo());
        ps.setInt(4, venda.getId());

        ps.executeUpdate();//Roda o insert no BD
    }

    public ArrayList<Venda> listar() throws Exception {
        ArrayList<Venda> vendas = new ArrayList();
        String sql = "select * from venda "
                + "where registro_ativo = true";

        PreparedStatement ps = Conexao.getConexao().prepareStatement(sql);
        ResultSet result = ps.executeQuery();

        //Faz a varredura na tabelinha de resultado da consulta
        while (result.next()) {
            Venda venda = new Venda();
            venda.getCliente().setId(result.getInt("pessoa_id"));
            venda.setDataHora(result.getTimestamp("data_hora").toLocalDateTime());
            venda.setId(result.getInt("id"));
            vendas.add(venda);
        }

        return vendas;
    }

    public ArrayList<Venda> pesquisaVenda(String pesquisa) throws Exception {
        ArrayList<Venda> listaVendas = new ArrayList<>();

        String sql = " select v.id, data_hora, p.nome, p.id as pid "
                + " from venda v join pessoa p on p.id = v.pessoa_id "
                + " where v.registro_ativo = true and lower(p.nome) like ? "
                + " order by data_hora desc ";

        PreparedStatement ps = Conexao.getConexao().prepareStatement(sql);
        ps.setString(1, "%" + pesquisa.toLowerCase() + "%");
        ResultSet result = ps.executeQuery();

        while (result.next()) {
            Venda venda = new Venda();
            Pessoa pessoa = new Pessoa();
            venda.setId(result.getInt("id"));
            venda.setDataHora(result.getTimestamp("data_hora").toLocalDateTime());
            pessoa.setNome(result.getString("nome"));
            pessoa.setId(result.getInt("pid"));
            venda.setCliente(pessoa);
            listaVendas.add(venda);
        }
        return listaVendas;
    }

    public void excluir(Venda venda) throws Exception {
        String sql = "update venda set registro_ativo = false "
                + " where id = ? ";

        PreparedStatement ps = Conexao.getConexao().prepareStatement(sql);
        ps.setInt(1, venda.getId());
        ps.executeUpdate();//Roda o insert no BD
    }

    public ArrayList<ItemVenda> listaItens(Venda venda) throws Exception {
        ArrayList<ItemVenda> itens = new ArrayList();

        String sql = " select iv.id, "
                + " iv.venda_id, "
                + " iv.produtos_servicos_id, "
                + " iv.animal_id, "
                + " iv.quantidade, "
                + " iv.valor_unitario, "
                + " ps.nome as produtoserviconome, "
                + " animal.nome as animalnome "
                + " from itens_venda iv join produtos_servicos ps on ps.id = iv.produtos_servicos_id "
                + " join animal animal on animal.id = iv.animal_id "
                + " where iv.venda_id = ?; ";

        PreparedStatement consulta = Conexao.getConexao().prepareStatement(sql);
        consulta.setInt(1, venda.getId());
        ResultSet result = consulta.executeQuery();

        //Faz a varredura na tabelinha de resultado da consulta
        while (result.next()) {
            ItemVenda iv = new ItemVenda();
            ProdutoServico ps = new ProdutoServico();
            Animal a = new Animal();

            iv.setId(result.getInt("id"));
            iv.setQuantidade(result.getFloat("quantidade"));
            iv.setValorUnitario(result.getFloat("valor_unitario"));
            ps.setId(result.getInt("produtos_servicos_id"));
            ps.setNome(result.getString("produtoserviconome"));
            a.setId(result.getInt("animal_id"));
            a.setNome(result.getString("animalnome"));
            iv.setProdutoServico(ps);
            iv.setAnimal(a);
            itens.add(iv);
        }

        return itens;
    }

    private void inserirOuAtualizarItensVenda(Venda venda) throws Exception {
        //Primeiro deleta todos os itens
        PreparedStatement consultaDeleteItens
                = Conexao.getConexao().prepareStatement("delete from itens_venda where venda_id = ?");
        consultaDeleteItens.setInt(1, venda.getId());
        consultaDeleteItens.executeUpdate();

        //depois insere tudo novamente
        for (ItemVenda item : venda.getItens()) {
            PreparedStatement consultaReinsereItens = Conexao.getConexao().prepareStatement(
                    "insert into itens_venda (venda_id, produtos_servicos_id, animal_id, "
                    + " quantidade, valor_unitario) values (?, ?, ?, ?, ?)");

            consultaReinsereItens.setInt(1, venda.getId());
            consultaReinsereItens.setInt(2, item.getProdutoServico().getId());
            consultaReinsereItens.setInt(3, item.getAnimal().getId());
            consultaReinsereItens.setFloat(4, item.getQuantidade());
            consultaReinsereItens.setFloat(5, item.getValorUnitario());
            consultaReinsereItens.executeUpdate();
        }
    }

}
