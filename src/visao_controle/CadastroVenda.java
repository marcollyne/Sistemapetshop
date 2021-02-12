/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao_controle;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Pessoa;
import modelo.dao.PessoaAnimalDAO;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Animal;
import modelo.ItemVenda;
import modelo.ProdutoServico;
import modelo.Venda;
import modelo.dao.ProdutoServicoDAO;
import modelo.dao.VendaDAO;

/**
 *
 * @author karol
 */
public class CadastroVenda extends javax.swing.JDialog {

    /**
     * Creates new form CadastroVenda
     */
    private Pessoa pessoaselecionada;
    private PessoaAnimalDAO padao = new PessoaAnimalDAO();
    private ProdutoServicoDAO psdao = new ProdutoServicoDAO();
    private ArrayList<Pessoa> listapessoa;
    private ArrayList<ProdutoServico> listaps;
    private ArrayList<ItemVenda> carrinho = new ArrayList<>();
    Venda venda = new Venda();
    VendaDAO vdao = new VendaDAO();

    private float totalVenda = 0;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    LocalDateTime agora = LocalDateTime.now();

    public CadastroVenda() {
        initComponents();
        jtfData.setText(String.valueOf(agora.format(formatter)));
        carregarPessoas();
        carregarProdutoServico();
        setModal(true);

    }

    public CadastroVenda(Venda venda) {
        initComponents();
        jtfData.setText(String.valueOf(agora.format(formatter)));
        carregarPessoas();
        carregarProdutoServico();
        setModal(true);
        this.venda = venda;
        preencherDados();
    }

    private void carregarPessoas() {
        try {
            this.listapessoa = padao.listar();
            jcbCliente.removeAllItems();
            if (this.listapessoa != null) {
                for (Pessoa p : this.listapessoa) {
                    jcbCliente.addItem(p.getNome());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(CadastroVenda.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Erro ao carregar clientes.", "ERRO",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarProdutoServico() {
        try {
            this.listaps = psdao.carregarProdutosServicos();
            jcbProdutoServico.removeAllItems();
            if (this.listaps != null) {
                for (ProdutoServico ps : this.listaps) {
                    jcbProdutoServico.addItem(ps.getNome());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(CadastroVenda.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos e serviços.", "ERRO",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarItem() {

        ItemVenda iv = new ItemVenda();
        Animal animalselecionado = pessoaselecionada.getAnimais().get(jcbAnimal.getSelectedIndex());
        iv.setAnimal(animalselecionado);

        ProdutoServico produtoServicoSelecionado = listaps.get(jcbProdutoServico.getSelectedIndex());
        iv.setProdutoServico(produtoServicoSelecionado);

        iv.setQuantidade(Integer.parseInt(jtfQuantidade.getText()));

        iv.setValorUnitario(Float.parseFloat(jtfPreco.getText()));

        carrinho.add(iv);

        Object itens[] = new Object[5];
        itens[0] = iv.getAnimal().getNome();
        itens[1] = iv.getProdutoServico().getNome();
        itens[2] = iv.getQuantidade();
        itens[3] = iv.getValorUnitario();
        itens[4] = iv.getQuantidade() * iv.getValorUnitario();

        DefaultTableModel modelo = (DefaultTableModel) jtVenda.getModel();

        modelo.addRow(itens);

        atualizarTotalCompra();
    }

    private void removerItem() {
        DefaultTableModel modelo = (DefaultTableModel) jtVenda.getModel();
        carrinho.remove(jtVenda.getSelectedRow());

        modelo.setRowCount(0);

        for (ItemVenda iv : carrinho) {
            Object itens[] = new Object[5];
            itens[0] = iv.getAnimal().getNome();
            itens[1] = iv.getProdutoServico().getNome();
            itens[2] = iv.getQuantidade();
            itens[3] = iv.getValorUnitario();
            itens[4] = iv.getQuantidade() * iv.getValorUnitario();
            modelo.addRow(itens);
        }
        atualizarTotalCompra();

    }

    private void atualizarTotalCompra() {
        totalVenda = 0;
        for (ItemVenda iv : carrinho) {
            totalVenda += iv.getValorUnitario() * iv.getQuantidade();

        }
        jtfTotal.setText(String.valueOf("R$ " + totalVenda));
    }

    private void atualizaListaItem() {
        DefaultTableModel modelo = (DefaultTableModel) jtVenda.getModel();

        modelo.setRowCount(0);

        for (ItemVenda iv : carrinho) {
            Object itens[] = new Object[5];
            itens[0] = iv.getAnimal().getNome();
            itens[1] = iv.getProdutoServico().getNome();
            itens[2] = iv.getQuantidade();
            itens[3] = iv.getValorUnitario();
            itens[4] = iv.getQuantidade() * iv.getValorUnitario();
            modelo.addRow(itens);
        }
    }

    private void preencherDados() {
        pessoaselecionada = venda.getCliente();
        jcbCliente.setSelectedItem(pessoaselecionada.getNome());
        try {
            carrinho = vdao.listaItens(venda);
        } catch (Exception ex) {
            Logger.getLogger(CadastroVenda.class.getName()).log(Level.SEVERE, null, ex);
        }
        atualizaListaItem();
        atualizarTotalCompra();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jbHome = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jcbCliente = new javax.swing.JComboBox<>();
        jTextField2 = new javax.swing.JTextField();
        jcbAnimal = new javax.swing.JComboBox<>();
        jTextField3 = new javax.swing.JTextField();
        jcbProdutoServico = new javax.swing.JComboBox<>();
        jTextField4 = new javax.swing.JTextField();
        jtfQuantidade = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jtfPreco = new javax.swing.JTextField();
        jbAdicionarItem = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtVenda = new javax.swing.JTable();
        jbExcluirItem = new javax.swing.JButton();
        jTextField8 = new javax.swing.JTextField();
        jbSalvar = new javax.swing.JButton();
        jTextField9 = new javax.swing.JTextField();
        jtfTotal = new javax.swing.JTextField();
        jtfData = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(900, 690));

        jbHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/home.png"))); // NOI18N
        jbHome.setBorderPainted(false);
        jbHome.setContentAreaFilled(false);
        jbHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbHomeActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(1, 22, 59));
        jLabel1.setText("Cadastro de Vendas");

        jTextField1.setEditable(false);
        jTextField1.setBackground(new java.awt.Color(255, 255, 255));
        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(1, 22, 59));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("Cliente");
        jTextField1.setBorder(null);
        jTextField1.setPreferredSize(new java.awt.Dimension(48, 32));

        jcbCliente.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jcbCliente.setForeground(new java.awt.Color(1, 22, 59));
        jcbCliente.setBorder(null);
        jcbCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbCliente.setPreferredSize(new java.awt.Dimension(37, 32));
        jcbCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbClienteActionPerformed(evt);
            }
        });

        jTextField2.setEditable(false);
        jTextField2.setBackground(new java.awt.Color(255, 255, 255));
        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(1, 22, 59));
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.setText("Animal");
        jTextField2.setBorder(null);
        jTextField2.setPreferredSize(new java.awt.Dimension(48, 32));

        jcbAnimal.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jcbAnimal.setForeground(new java.awt.Color(1, 22, 59));
        jcbAnimal.setBorder(null);
        jcbAnimal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbAnimal.setPreferredSize(new java.awt.Dimension(37, 32));

        jTextField3.setEditable(false);
        jTextField3.setBackground(new java.awt.Color(255, 255, 255));
        jTextField3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(1, 22, 59));
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField3.setText("Produto -- Serviço");
        jTextField3.setBorder(null);
        jTextField3.setPreferredSize(new java.awt.Dimension(48, 32));

        jcbProdutoServico.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jcbProdutoServico.setForeground(new java.awt.Color(1, 22, 59));
        jcbProdutoServico.setBorder(null);
        jcbProdutoServico.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jcbProdutoServico.setPreferredSize(new java.awt.Dimension(37, 32));
        jcbProdutoServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbProdutoServicoActionPerformed(evt);
            }
        });

        jTextField4.setEditable(false);
        jTextField4.setBackground(new java.awt.Color(255, 255, 255));
        jTextField4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(1, 22, 59));
        jTextField4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField4.setText("Quantidade");
        jTextField4.setBorder(null);
        jTextField4.setPreferredSize(new java.awt.Dimension(48, 32));

        jtfQuantidade.setBackground(new java.awt.Color(1, 22, 59));
        jtfQuantidade.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtfQuantidade.setForeground(new java.awt.Color(255, 255, 255));
        jtfQuantidade.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfQuantidade.setBorder(null);
        jtfQuantidade.setPreferredSize(new java.awt.Dimension(48, 32));
        jtfQuantidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfQuantidadeKeyTyped(evt);
            }
        });

        jTextField6.setEditable(false);
        jTextField6.setBackground(new java.awt.Color(255, 255, 255));
        jTextField6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jTextField6.setForeground(new java.awt.Color(1, 22, 59));
        jTextField6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField6.setText("Preço unitário");
        jTextField6.setBorder(null);
        jTextField6.setPreferredSize(new java.awt.Dimension(48, 32));

        jtfPreco.setEditable(false);
        jtfPreco.setBackground(new java.awt.Color(1, 22, 59));
        jtfPreco.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtfPreco.setForeground(new java.awt.Color(255, 255, 255));
        jtfPreco.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfPreco.setBorder(null);
        jtfPreco.setPreferredSize(new java.awt.Dimension(48, 32));

        jbAdicionarItem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbAdicionarItem.setForeground(new java.awt.Color(1, 22, 59));
        jbAdicionarItem.setText("ADICIONAR ITEM");
        jbAdicionarItem.setToolTipText("");
        jbAdicionarItem.setBorderPainted(false);
        jbAdicionarItem.setContentAreaFilled(false);
        jbAdicionarItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbAdicionarItem.setPreferredSize(new java.awt.Dimension(125, 40));
        jbAdicionarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAdicionarItemActionPerformed(evt);
            }
        });

        jtVenda.setBackground(new java.awt.Color(1, 22, 59));
        jtVenda.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jtVenda.setForeground(new java.awt.Color(255, 255, 255));
        jtVenda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Animal", "Produto/Serviço", "Quantidade", "Preço unitário", "Total por item"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtVenda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jtVenda.setFillsViewportHeight(true);
        jtVenda.setRowHeight(25);
        jtVenda.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jtVenda);
        if (jtVenda.getColumnModel().getColumnCount() > 0) {
            jtVenda.getColumnModel().getColumn(2).setPreferredWidth(18);
            jtVenda.getColumnModel().getColumn(3).setPreferredWidth(27);
            jtVenda.getColumnModel().getColumn(4).setPreferredWidth(20);
        }

        jbExcluirItem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbExcluirItem.setForeground(new java.awt.Color(1, 22, 59));
        jbExcluirItem.setText("REMOVER ITEM");
        jbExcluirItem.setToolTipText("");
        jbExcluirItem.setBorderPainted(false);
        jbExcluirItem.setContentAreaFilled(false);
        jbExcluirItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbExcluirItem.setPreferredSize(new java.awt.Dimension(125, 40));
        jbExcluirItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbExcluirItemActionPerformed(evt);
            }
        });

        jTextField8.setEditable(false);
        jTextField8.setBackground(new java.awt.Color(255, 255, 255));
        jTextField8.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jTextField8.setForeground(new java.awt.Color(1, 22, 59));
        jTextField8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField8.setText("Informações de venda");
        jTextField8.setBorder(null);
        jTextField8.setPreferredSize(new java.awt.Dimension(48, 32));

        jbSalvar.setBackground(new java.awt.Color(1, 22, 59));
        jbSalvar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbSalvar.setForeground(new java.awt.Color(255, 255, 255));
        jbSalvar.setText("SALVAR");
        jbSalvar.setToolTipText("");
        jbSalvar.setBorderPainted(false);
        jbSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbSalvar.setMaximumSize(new java.awt.Dimension(103, 79));
        jbSalvar.setMinimumSize(new java.awt.Dimension(103, 79));
        jbSalvar.setPreferredSize(new java.awt.Dimension(125, 40));
        jbSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalvarActionPerformed(evt);
            }
        });

        jTextField9.setEditable(false);
        jTextField9.setBackground(new java.awt.Color(255, 255, 255));
        jTextField9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField9.setForeground(new java.awt.Color(1, 22, 59));
        jTextField9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField9.setText("TOTAL:");
        jTextField9.setBorder(null);
        jTextField9.setPreferredSize(new java.awt.Dimension(48, 32));

        jtfTotal.setEditable(false);
        jtfTotal.setBackground(new java.awt.Color(1, 22, 59));
        jtfTotal.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtfTotal.setForeground(new java.awt.Color(255, 255, 255));
        jtfTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfTotal.setBorder(null);
        jtfTotal.setPreferredSize(new java.awt.Dimension(48, 32));

        jtfData.setEditable(false);
        jtfData.setBackground(new java.awt.Color(255, 255, 255));
        jtfData.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jtfData.setForeground(new java.awt.Color(1, 22, 59));
        jtfData.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfData.setToolTipText("");
        jtfData.setBorder(null);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jbHome, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jtfData, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jbAdicionarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jcbCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jcbAnimal, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                                .addComponent(jcbProdutoServico, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                                .addComponent(jtfQuantidade, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                                .addComponent(jTextField6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jtfPreco, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtfTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(206, 206, 206)
                                    .addComponent(jbExcluirItem, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jbSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jtfData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jbHome)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbExcluirItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jcbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbAnimal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbProdutoServico, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfPreco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAdicionarItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jbSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbHomeActionPerformed
        dispose();
    }//GEN-LAST:event_jbHomeActionPerformed

    private void jcbProdutoServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbProdutoServicoActionPerformed
        ProdutoServico produtoservicoselecionado = listaps.get(jcbProdutoServico.getSelectedIndex());

        jtfPreco.setText(String.valueOf(produtoservicoselecionado.getValor()));

    }//GEN-LAST:event_jcbProdutoServicoActionPerformed

    private void jcbClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbClienteActionPerformed
        pessoaselecionada = listapessoa.get(jcbCliente.getSelectedIndex());

        jcbAnimal.removeAllItems();
        for (Animal a : pessoaselecionada.getAnimais()) {
            jcbAnimal.addItem(a.getNome());
        }
    }//GEN-LAST:event_jcbClienteActionPerformed

    private void jbAdicionarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAdicionarItemActionPerformed
        adicionarItem();
    }//GEN-LAST:event_jbAdicionarItemActionPerformed

    private void jbExcluirItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbExcluirItemActionPerformed
        removerItem();
    }//GEN-LAST:event_jbExcluirItemActionPerformed

    private void jbSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalvarActionPerformed

        venda.setItens(carrinho);
        venda.setCliente(pessoaselecionada);
        venda.setDataHora(agora);

        VendaDAO vdao = new VendaDAO();
        try {
            vdao.gravar(venda);
            JOptionPane.showMessageDialog(this, "Salvo com sucesso, obrigado pela compra!", "♥",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            Logger.getLogger(CadastroVenda.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Erro ao salvar venda", "ERRO",
                    JOptionPane.ERROR_MESSAGE);
        }

        dispose();


    }//GEN-LAST:event_jbSalvarActionPerformed

    private void jtfQuantidadeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfQuantidadeKeyTyped
        if (!(evt.getKeyChar() + "").matches("[0-9]")) {
            evt.consume();
        }
    }//GEN-LAST:event_jtfQuantidadeKeyTyped

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
            java.util.logging.Logger.getLogger(CadastroVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadastroVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadastroVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadastroVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadastroVenda().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JButton jbAdicionarItem;
    private javax.swing.JButton jbExcluirItem;
    private javax.swing.JButton jbHome;
    private javax.swing.JButton jbSalvar;
    private javax.swing.JComboBox<String> jcbAnimal;
    private javax.swing.JComboBox<String> jcbCliente;
    private javax.swing.JComboBox<String> jcbProdutoServico;
    private javax.swing.JTable jtVenda;
    private javax.swing.JTextField jtfData;
    private javax.swing.JTextField jtfPreco;
    private javax.swing.JTextField jtfQuantidade;
    private javax.swing.JTextField jtfTotal;
    // End of variables declaration//GEN-END:variables
}
