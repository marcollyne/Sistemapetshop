package modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import modelo.ProdutoServico;

/**
 *
 * @author karollyne
 */
public class ProdutoServicoDAO {

    public void salvar(ProdutoServico produtoservico) throws Exception {
        if (produtoservico.getId() == 0) {
            inserir(produtoservico);
        } else {
            alterar(produtoservico);
        }
    }

    public void inserir(ProdutoServico produtoservico) throws Exception {

        String sql = " insert into produtos_servicos (nome, servico, valor, "
                + " frequencia_aplicacao_dias, registro_ativo) "
                + " values (?, ?, ?, ? ,? )";

        PreparedStatement ps = Conexao.getConexao().prepareStatement(sql);

        ps.setString(1, produtoservico.getNome());
        ps.setBoolean(2, produtoservico.isServico());
        ps.setFloat(3, produtoservico.getValor());
        ps.setInt(4, produtoservico.getFrequenciaAplicacao());
        ps.setBoolean(5, produtoservico.isRegistro_ativo());

        ps.executeUpdate();
    }

    public ArrayList<ProdutoServico> listarProdutosServicos(String pesquisa) throws Exception {
        ArrayList<ProdutoServico> listaProdutoServico = new ArrayList<>();

        String sql = " select * from produtos_servicos ps where registro_ativo = true and "
                + " lower(ps.nome) like ? ";

        PreparedStatement ps = Conexao.getConexao().prepareStatement(sql);
        ps.setString(1, "%" + pesquisa.toLowerCase() + "%");
        ResultSet result = ps.executeQuery();

        while (result.next()) {
            ProdutoServico produtoservico = new ProdutoServico();

            produtoservico.setId(result.getInt("id"));
            produtoservico.setNome(result.getString("nome"));
            produtoservico.setServico(result.getBoolean("servico"));
            produtoservico.setValor(result.getFloat("valor"));
            produtoservico.setFrequenciaAplicacao(result.getInt("frequencia_aplicacao_dias"));
            produtoservico.setRegistro_ativo(result.getBoolean("registro_ativo"));

            listaProdutoServico.add(produtoservico);
        }
        return listaProdutoServico;
    }

    public ProdutoServico bucarPorId(int id) throws Exception {
        ProdutoServico produtoservico = new ProdutoServico();
        String sql = " select * from produtos_servicos where id = ? ";

        PreparedStatement ps = Conexao.getConexao().prepareStatement(sql);

        ps.setInt(1, id);

        ResultSet result = ps.executeQuery();

        if (result.next()) {

            produtoservico.setId(result.getInt("id"));
            produtoservico.setNome(result.getString("nome"));
            produtoservico.setServico(result.getBoolean("servico"));
            produtoservico.setValor(result.getFloat("valor"));
            produtoservico.setFrequenciaAplicacao(result.getInt("frequencia_aplicacao_dias"));
            produtoservico.setRegistro_ativo(result.getBoolean("registro_ativo"));

        }
        return produtoservico;
    }

    public void alterar(ProdutoServico produtoservico) throws Exception {
        String sql = " update produtos_servicos set nome = ?, servico = ?, "
                + " valor = ?, frequencia_aplicacao_dias = ?, registro_ativo = ? "
                + " where id = ? ";

        PreparedStatement ps = Conexao.getConexao().prepareStatement(sql);

        ps.setString(1, produtoservico.getNome());
        ps.setBoolean(2, produtoservico.isServico());
        ps.setFloat(3, produtoservico.getValor());
        ps.setInt(4, produtoservico.getFrequenciaAplicacao());
        ps.setBoolean(5, produtoservico.isRegistro_ativo());
        ps.setInt(6, produtoservico.getId());

        ps.executeUpdate();
    }

    
     public ArrayList<ProdutoServico> carregarProdutosServicos() throws Exception {
        ArrayList<ProdutoServico> carregaProdutoServico = new ArrayList<>();
        String sql = "select * from produtos_servicos where registro_ativo = true";
        PreparedStatement ps = Conexao.getConexao().prepareStatement(sql);
        ResultSet result = ps.executeQuery();

        while (result.next()) {
            ProdutoServico produtoservico = new ProdutoServico();

            produtoservico.setId(result.getInt("id"));
            produtoservico.setNome(result.getString("nome"));
            produtoservico.setServico(result.getBoolean("servico"));
            produtoservico.setValor(result.getFloat("valor"));
            produtoservico.setFrequenciaAplicacao(result.getInt("frequencia_aplicacao_dias"));
            produtoservico.setRegistro_ativo(result.getBoolean("registro_ativo"));

            carregaProdutoServico.add(produtoservico);
        }
        return carregaProdutoServico;
    }
}
