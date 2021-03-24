/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao_controle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Animal;
import modelo.Cidade;
import modelo.Estado;
import modelo.Pessoa;
import modelo.dao.CidadeEstadoDAO;
import modelo.dao.PessoaAnimalDAO;
import visao_controle.PesquisaClientes;

/**
 *
 * @author karol
 */
public class CadastroClienteAnimal extends javax.swing.JDialog {

    private Pessoa pessoaEmEdicao;
    private Animal animalEmEdicao;
    private ArrayList<Estado> estados;
    private ArrayList<Cidade> cidades;
    private PessoaAnimalDAO pessoaDAO = new PessoaAnimalDAO();
    private CidadeEstadoDAO cidadeEstadoDAO = new CidadeEstadoDAO();

    public CadastroClienteAnimal() {
        initComponents();
        this.pessoaEmEdicao = new Pessoa();
        carregarEstados();
        setModal(true);
    }

    public void setPessoaEmEdicao(Pessoa pessoaEmEdicao) {
        this.pessoaEmEdicao = pessoaEmEdicao;
        preencherDadosTela();
    }

    private void carregarEstados() {
        try {
            this.estados = cidadeEstadoDAO.carregarEstados();
            jcbEstado.removeAllItems();
            if (this.estados != null) {
                for (Estado e : this.estados) {
                    jcbEstado.addItem(e.getSigla());
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar estados.", "ERRO",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarCidades(String siglaEstado) {
        try {
            this.cidades = cidadeEstadoDAO.carregarCidades(siglaEstado);
            jcbCidade.removeAllItems();
            if (this.cidades != null) {
                for (Cidade c : this.cidades) {
                    jcbCidade.addItem(c.getNome());
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar cidades.", "ERRO",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean preencheuTodosCamposObrigatorios() {
        if (jtfNome.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Informe o nome do cliente.", "Atenção",
                    JOptionPane.WARNING_MESSAGE);
            jtfNome.requestFocus();
            return false;
        }
        if (jtfEndereco.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Informe o endereco do cliente.", "Atenção",
                    JOptionPane.WARNING_MESSAGE);
            jtfEndereco.requestFocus();
            return false;
        }
        if (jtfBairro.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Informe o bairo do cliente.", "Atenção",
                    JOptionPane.WARNING_MESSAGE);
            jtfBairro.requestFocus();
            return false;
        }
        if (jtfNumero.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Informe o número da residêmcia.", "Atenção",
                    JOptionPane.WARNING_MESSAGE);
            jtfNumero.requestFocus();
            return false;
        }
        return true;
    }

    private void preencherDadosTela() {
        jtfNome.setText(pessoaEmEdicao.getNome());
        jtfEndereco.setText(pessoaEmEdicao.getEndereco());

        if (pessoaEmEdicao.getNumero() > 0) {
            jtfNumero.setText(String.valueOf(pessoaEmEdicao.getNumero()));
        }

        jtfBairro.setText(pessoaEmEdicao.getBairro());

        if (pessoaEmEdicao.getDataNascimento() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            jftfDataNascimento.setText(sdf.format(pessoaEmEdicao.getDataNascimento()));
        }

        if (pessoaEmEdicao.getCidade() != null) {
            jcbEstado.setSelectedItem(pessoaEmEdicao.getCidade().getEstado().getSigla());
            jcbCidade.setSelectedItem(pessoaEmEdicao.getCidade().getNome());
        }

//        int indiceCidadeSelecionada = jcbCidade.getSelectedIndex();
//        Cidade cidadeSelecionada = this.cidades.get(indiceCidadeSelecionada);
//        pessoaEmEdicao.setCidade(cidadeSelecionada);
        atualizarListaAnimais();
    }

    private void lerDadosTela() {
        pessoaEmEdicao.setNome(jtfNome.getText());

        pessoaEmEdicao.setEndereco(jtfEndereco.getText());

        if (!jtfNumero.getText().trim().equals("")) {
            int numero = Integer.parseInt(jtfNumero.getText());
            pessoaEmEdicao.setNumero(numero);
        }

        pessoaEmEdicao.setBairro(jtfBairro.getText());

        if (!jftfDataNascimento.getText().replace("/", "").trim().equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date dataNascimento = sdf.parse(jftfDataNascimento.getText());
                pessoaEmEdicao.setDataNascimento(dataNascimento);
            } catch (ParseException ex) {
                Logger.getLogger(CadastroClienteAnimal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        int indiceCidadeSelecionada = jcbCidade.getSelectedIndex();
        Cidade cidadeSelecionada = this.cidades.get(indiceCidadeSelecionada);
        pessoaEmEdicao.setCidade(cidadeSelecionada);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jtfNome = new javax.swing.JTextField();
        jtfEndereco = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jtfBairro = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jftfDataNascimento = new javax.swing.JFormattedTextField();
        jTextField8 = new javax.swing.JTextField();
        jtfNumero = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jcbCidade = new javax.swing.JComboBox<>();
        jcbEstado = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jtfNomeAnimal = new javax.swing.JTextField();
        jbAdicionar = new javax.swing.JButton();
        jbExcluir = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtListaAnimais = new javax.swing.JTable();
        jbSalvar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Cadastro de Cliente e Animal");

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jTextField1.setEditable(false);
        jTextField1.setBackground(new java.awt.Color(255, 255, 255));
        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(1, 22, 59));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("Nome");
        jTextField1.setBorder(null);
        jTextField1.setPreferredSize(new java.awt.Dimension(280, 32));

        jTextField2.setEditable(false);
        jTextField2.setBackground(new java.awt.Color(255, 255, 255));
        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(1, 22, 59));
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.setText("Bairro");
        jTextField2.setBorder(null);
        jTextField2.setPreferredSize(new java.awt.Dimension(280, 32));

        jtfNome.setBackground(new java.awt.Color(1, 22, 59));
        jtfNome.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtfNome.setForeground(new java.awt.Color(255, 255, 255));
        jtfNome.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfNome.setBorder(null);
        jtfNome.setPreferredSize(new java.awt.Dimension(280, 32));

        jtfEndereco.setBackground(new java.awt.Color(1, 22, 59));
        jtfEndereco.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtfEndereco.setForeground(new java.awt.Color(255, 255, 255));
        jtfEndereco.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfEndereco.setBorder(null);
        jtfEndereco.setPreferredSize(new java.awt.Dimension(280, 32));

        jTextField5.setEditable(false);
        jTextField5.setBackground(new java.awt.Color(255, 255, 255));
        jTextField5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(1, 22, 59));
        jTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField5.setText("Endereço");
        jTextField5.setBorder(null);
        jTextField5.setPreferredSize(new java.awt.Dimension(280, 32));

        jtfBairro.setBackground(new java.awt.Color(1, 22, 59));
        jtfBairro.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtfBairro.setForeground(new java.awt.Color(255, 255, 255));
        jtfBairro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfBairro.setBorder(null);
        jtfBairro.setPreferredSize(new java.awt.Dimension(280, 32));

        jTextField7.setEditable(false);
        jTextField7.setBackground(new java.awt.Color(255, 255, 255));
        jTextField7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jTextField7.setForeground(new java.awt.Color(1, 22, 59));
        jTextField7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField7.setText("Data de Nascimento");
        jTextField7.setBorder(null);
        jTextField7.setPreferredSize(new java.awt.Dimension(280, 32));

        jftfDataNascimento.setBackground(new java.awt.Color(1, 22, 59));
        jftfDataNascimento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(1, 22, 59), 4));
        jftfDataNascimento.setForeground(new java.awt.Color(255, 255, 255));
        try {
            jftfDataNascimento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jftfDataNascimento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jftfDataNascimento.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jftfDataNascimento.setPreferredSize(new java.awt.Dimension(52, 32));

        jTextField8.setEditable(false);
        jTextField8.setBackground(new java.awt.Color(255, 255, 255));
        jTextField8.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jTextField8.setForeground(new java.awt.Color(1, 22, 59));
        jTextField8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField8.setText("Número");
        jTextField8.setBorder(null);
        jTextField8.setPreferredSize(new java.awt.Dimension(280, 32));

        jtfNumero.setBackground(new java.awt.Color(1, 22, 59));
        jtfNumero.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtfNumero.setForeground(new java.awt.Color(255, 255, 255));
        jtfNumero.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfNumero.setBorder(null);
        jtfNumero.setPreferredSize(new java.awt.Dimension(280, 32));
        jtfNumero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfNumeroKeyTyped(evt);
            }
        });

        jTextField12.setEditable(false);
        jTextField12.setBackground(new java.awt.Color(255, 255, 255));
        jTextField12.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jTextField12.setForeground(new java.awt.Color(1, 22, 59));
        jTextField12.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField12.setText("Estado - - Cidade");
        jTextField12.setBorder(null);
        jTextField12.setPreferredSize(new java.awt.Dimension(43, 32));

        jcbCidade.setBackground(new java.awt.Color(1, 22, 59));
        jcbCidade.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jcbCidade.setForeground(new java.awt.Color(255, 255, 255));
        jcbCidade.setMaximumRowCount(30);
        jcbCidade.setBorder(null);
        jcbCidade.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbCidade.setMinimumSize(new java.awt.Dimension(53, 31));
        jcbCidade.setPreferredSize(new java.awt.Dimension(48, 31));

        jcbEstado.setBackground(new java.awt.Color(1, 22, 59));
        jcbEstado.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jcbEstado.setForeground(new java.awt.Color(255, 255, 255));
        jcbEstado.setMaximumRowCount(1000);
        jcbEstado.setBorder(null);
        jcbEstado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbEstado.setMinimumSize(new java.awt.Dimension(53, 31));
        jcbEstado.setPreferredSize(new java.awt.Dimension(48, 31));
        jcbEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbEstadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(jftfDataNascimento, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jtfEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jtfNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jcbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jcbCidade, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(30, 30, 30))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jftfDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Cliente", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jtfNomeAnimal.setBackground(new java.awt.Color(1, 22, 59));
        jtfNomeAnimal.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtfNomeAnimal.setForeground(new java.awt.Color(255, 255, 255));
        jtfNomeAnimal.setPreferredSize(new java.awt.Dimension(342, 32));

        jbAdicionar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbAdicionar.setForeground(new java.awt.Color(1, 22, 59));
        jbAdicionar.setText("ADICIONAR");
        jbAdicionar.setBorder(null);
        jbAdicionar.setContentAreaFilled(false);
        jbAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbAdicionar.setPreferredSize(new java.awt.Dimension(45, 32));
        jbAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAdicionarActionPerformed(evt);
            }
        });

        jbExcluir.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbExcluir.setForeground(new java.awt.Color(1, 22, 59));
        jbExcluir.setText("EXCLUIR");
        jbExcluir.setBorder(null);
        jbExcluir.setContentAreaFilled(false);
        jbExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbExcluir.setPreferredSize(new java.awt.Dimension(45, 32));
        jbExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbExcluirActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/lupa.png"))); // NOI18N

        jtListaAnimais.setBackground(new java.awt.Color(1, 22, 59));
        jtListaAnimais.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtListaAnimais.setForeground(new java.awt.Color(255, 255, 255));
        jtListaAnimais.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NOME"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtListaAnimais.setColumnSelectionAllowed(true);
        jtListaAnimais.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jtListaAnimais.setFillsViewportHeight(true);
        jtListaAnimais.setGridColor(new java.awt.Color(1, 22, 59));
        jtListaAnimais.setRowHeight(25);
        jScrollPane2.setViewportView(jtListaAnimais);
        jtListaAnimais.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (jtListaAnimais.getColumnModel().getColumnCount() > 0) {
            jtListaAnimais.getColumnModel().getColumn(0).setResizable(false);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfNomeAnimal, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jtfNomeAnimal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(233, 233, 233))
        );

        jTabbedPane1.addTab("Animal", jPanel3);

        jbSalvar.setBackground(new java.awt.Color(1, 22, 59));
        jbSalvar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbSalvar.setForeground(new java.awt.Color(255, 255, 255));
        jbSalvar.setText("SALVAR");
        jbSalvar.setBorder(null);
        jbSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbSalvar.setPreferredSize(new java.awt.Dimension(125, 32));
        jbSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(282, 282, 282)
                        .addComponent(jbSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 658, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(45, 45, 45)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalvarActionPerformed
        if (preencheuTodosCamposObrigatorios()) {
            try {
                lerDadosTela();
                //Grava no banco
                PessoaAnimalDAO dao = new PessoaAnimalDAO();
                dao.gravar(this.pessoaEmEdicao);
                JOptionPane.showMessageDialog(this, "Operação realizada com sucesso!", "Confirmação", JOptionPane.INFORMATION_MESSAGE);
                //Fecha a tela
                this.setVisible(false);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao gravar cliente!", "ERRO", JOptionPane.ERROR_MESSAGE);
            }
        }
        PesquisaClientes pc = new PesquisaClientes();
        pc.rodarPesquisa();

    }//GEN-LAST:event_jbSalvarActionPerformed

    private void jtfNumeroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfNumeroKeyTyped
        if (!String.valueOf(evt.getKeyChar()).matches("[0-9]")) {
            evt.consume();
        }
    }//GEN-LAST:event_jtfNumeroKeyTyped

    private void jbAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAdicionarActionPerformed
        if (jtfNomeAnimal.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Informe o nome do animal.", "ATENÇÃO",
                    JOptionPane.WARNING_MESSAGE);
            jtfNomeAnimal.requestFocus();
            return;
        }

        if (this.animalEmEdicao != null) {
            animalEmEdicao.setNome(jtfNomeAnimal.getText().trim());
        } else {
            Animal animal = new Animal();
            animal.setNome(jtfNomeAnimal.getText().trim());
            animal.setDono(pessoaEmEdicao);

            pessoaEmEdicao.getAnimais().add(animal);
        }

        atualizarListaAnimais();
        jtfNomeAnimal.setText("");
        this.animalEmEdicao = null;
    }//GEN-LAST:event_jbAdicionarActionPerformed

    private void jbExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbExcluirActionPerformed
        int linhaSelecionada = jtListaAnimais.getSelectedRow();
        if (linhaSelecionada < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um animal da lista.", "Atenção",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            int resposta = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir este animal?", "Atenção",
                    JOptionPane.YES_NO_OPTION);

            if (resposta == JOptionPane.YES_OPTION) {
                int indiceAnimalLista = getIndiceAnimalListaBD(linhaSelecionada);
                Animal animal = pessoaEmEdicao.getAnimais().get(indiceAnimalLista);
                if (animal.getId() == 0) {//Animal nunca foi gravado no BD
                    pessoaEmEdicao.getAnimais().remove(indiceAnimalLista);
                } else {
                    animal.setAtivo(false);
                }
                atualizarListaAnimais();
            }
        }
    }//GEN-LAST:event_jbExcluirActionPerformed

    private void jcbEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbEstadoActionPerformed
        if (jcbEstado.getSelectedItem() != null) {
            String siglaSelecionada = jcbEstado.getSelectedItem().toString();
            carregarCidades(siglaSelecionada);
        }
    }//GEN-LAST:event_jcbEstadoActionPerformed

    private void atualizarListaAnimais() {
        DefaultTableModel modelo = (DefaultTableModel) jtListaAnimais.getModel();
        modelo.setNumRows(0);
        for (Animal animal : pessoaEmEdicao.getAnimais()) {
            if (animal.isAtivo()) {
                Object[] dados = new Object[]{
                    animal.getNome()
                };
                modelo.addRow(dados);
            }
        }
    }

    private int getIndiceAnimalListaBD(int linhaSelecionada) {
        int indiceAnimaisAtivos = -1;
        for (int i = 0; i < pessoaEmEdicao.getAnimais().size(); i++) {
            if (pessoaEmEdicao.getAnimais().get(i).isAtivo()) {
                indiceAnimaisAtivos++;
            }
            if (indiceAnimaisAtivos == linhaSelecionada) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CadastroClienteAnimal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadastroClienteAnimal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadastroClienteAnimal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadastroClienteAnimal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadastroClienteAnimal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JButton jbAdicionar;
    private javax.swing.JButton jbExcluir;
    private javax.swing.JButton jbSalvar;
    private javax.swing.JComboBox<String> jcbCidade;
    private javax.swing.JComboBox<String> jcbEstado;
    private javax.swing.JFormattedTextField jftfDataNascimento;
    private javax.swing.JTable jtListaAnimais;
    private javax.swing.JTextField jtfBairro;
    private javax.swing.JTextField jtfEndereco;
    private javax.swing.JTextField jtfNome;
    private javax.swing.JTextField jtfNomeAnimal;
    private javax.swing.JTextField jtfNumero;
    // End of variables declaration//GEN-END:variables
}
