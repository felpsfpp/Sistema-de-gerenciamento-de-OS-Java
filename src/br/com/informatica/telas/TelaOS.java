package br.com.informatica.telas;

import java.sql.*;
import br.com.informatica.dal.Conexao;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author felip
 */
public class TelaOS extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    //cria uma variavel para armazenar texto de acordo com o radio button selecionado
    private String tipo;

    /**
     * Creates new form TelaOS
     */
    public TelaOS() {
        initComponents();
        conexao = Conexao.conectar();
        pesquisa_cliente();

        // cria uma variavel para armazenar texto d radion button selecionado
    }

    private void pesquisa_cliente() {
        String sql = "select idcli as Id, nomecli as Nome, fonecli as Fone from tbclientes where nomecli like?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtOsPesquisar.getText() + "%");
            rs = pst.executeQuery();
            tblOsClientes.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //metodo para setar campo id da tabela
    private void set_campos() {
        int setar = tblOsClientes.getSelectedRow();
        txtOsId.setText(tblOsClientes.getModel().getValueAt(setar, 0).toString());
    }

    private void emitir_os() {
        String sql = "insert into tbos(tipo,situacao,equipamento,defeito,servico,tecnico,valor,idcli) values(?,?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareCall(sql);
            pst.setString(1, tipo);
            pst.setString(2, cboxOsSituacao.getSelectedItem().toString());
            pst.setString(3, txtOsEquipamento.getText());
            pst.setString(4, txtOsDefeito.getText());
            pst.setString(5, txtOsServico.getText());
            pst.setString(6, txtOsTecnico.getText());
            //replace substitui virgula pelo ponto
            pst.setString(7, txtOsValorTotal.getText().replace(",", "."));
            pst.setString(8, txtOsId.getText());

            //validar campos obrigatorios
            if ((txtOsId.getText().isEmpty()) || (txtOsEquipamento.getText().isEmpty()) || (txtOsDefeito.getText().isEmpty()) || (cboxOsSituacao.getSelectedItem().equals(" "))) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatorios");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "OS emitida com sucesso");
                    limpar();
                    //btnOsPesquisar.setEnabled(false);
                    //btnOsPrint.setEnabled(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //metodo que pesquisa os
    private void pesquisa_os() {
        //cria uma caixa de entrada
        String num_os = JOptionPane.showInputDialog("Numero da OS");
        String sql = "select os,date_format(data_os,'%d/%m/%Y - %H:%i'),tipo,situacao,equipamento,defeito,servico,tecnico,valor,idcli from tbos where os= " + num_os;
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtOsNumOs.setText(rs.getString(1));
                txtOsData.setText(rs.getString(2));
                //setando radio butons
                String rbtTipo = rs.getString(3);
                if (rbtTipo.equals("OS")) {
                    rbtOsOrdemdeservico.setSelected(true);
                    tipo = "OS";
                } else {
                    rbtOsOrcamento.setSelected(true);
                    tipo = "Orçamento";
                }
                cboxOsSituacao.setSelectedItem(rs.getString(4));
                txtOsEquipamento.setText(rs.getString(5));
                txtOsDefeito.setText(rs.getString(6));
                txtOsServico.setText(rs.getString(7));
                txtOsTecnico.setText(rs.getString(8));
                txtOsValorTotal.setText(rs.getString(9));
                txtOsId.setText(rs.getString(10));
                //desabilitando objetos
                btnOsAdicionar.setEnabled(false);
                btnOsPesquisar.setEnabled(false);
                txtOsPesquisar.setEnabled(false);
                tblOsClientes.setVisible(false);
                btnOsPrint.setEnabled(false);
                //ativando botoes
                btnOsEditar.setEnabled(true);
                btnOsApagar.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "OS não cadastrada");
            }
        } catch (SQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "OS Inválida");
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, e2);
        }
    }

    //metodo para alterar os
    private void alterar_os() {
        String sql = "update tbos set tipo=?,situacao=?,equipamento=?,defeito=?,servico=?,tecnico=?,valor=? where os=?";
        try {
            pst = conexao.prepareCall(sql);
            pst.setString(1, tipo);
            pst.setString(2, cboxOsSituacao.getSelectedItem().toString());
            pst.setString(3, txtOsEquipamento.getText());
            pst.setString(4, txtOsDefeito.getText());
            pst.setString(5, txtOsServico.getText());
            pst.setString(6, txtOsTecnico.getText());
            //replace substitui virgula pelo ponto
            pst.setString(7, txtOsValorTotal.getText().replace(",", "."));
            pst.setString(8, txtOsNumOs.getText());

            //validar campos obrigatorios
            if ((txtOsId.getText().isEmpty()) || (txtOsEquipamento.getText().isEmpty()) || (txtOsDefeito.getText().isEmpty()) || (cboxOsSituacao.getSelectedItem().equals(" "))) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatorios");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "OS alterada com sucesso");
                    limpar();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //metodo para excluir os
    private void excluir_os() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir esta OS?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tbos where os=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtOsNumOs.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "OS excluida com sucesso");
                    limpar();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    //metodo para limpar campose habilitar botoes
    private void limpar() {
        //limpa os campos
        txtOsPesquisar.setText(null);
        cboxOsSituacao.setSelectedItem(" ");
        txtOsNumOs.setText(null);
        txtOsData.setText(null);
        txtOsId.setText(null);
        txtOsEquipamento.setText(null);
        txtOsDefeito.setText(null);
        txtOsServico.setText(null);
        txtOsTecnico.setText(null);
        txtOsValorTotal.setText("0");
        //habilitando objetos
        btnOsAdicionar.setEnabled(true);
        btnOsPesquisar.setEnabled(true);
        txtOsPesquisar.setEnabled(true);
        tblOsClientes.setVisible(true);
        //desabilitar os botoes
        btnOsEditar.setEnabled(false);
        btnOsApagar.setEnabled(false);
        btnOsPrint.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtOsNumOs = new javax.swing.JTextField();
        txtOsData = new javax.swing.JTextField();
        rbtOsOrcamento = new javax.swing.JRadioButton();
        rbtOsOrdemdeservico = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        cboxOsSituacao = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        txtOsPesquisar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOsClientes = new javax.swing.JTable();
        txtOsId = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtOsEquipamento = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtOsTecnico = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtOsDefeito = new javax.swing.JTextField();
        txtOsServico = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtOsValorTotal = new javax.swing.JTextField();
        btnOsAdicionar = new javax.swing.JButton();
        btnOsPesquisar = new javax.swing.JButton();
        btnOsEditar = new javax.swing.JButton();
        btnOsApagar = new javax.swing.JButton();
        btnOsPrint = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Ordem de serviço");
        setPreferredSize(new java.awt.Dimension(560, 470));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Nº OS");

        jLabel2.setText("Data");

        txtOsNumOs.setEditable(false);

        txtOsData.setEditable(false);

        buttonGroup1.add(rbtOsOrcamento);
        rbtOsOrcamento.setText("Orçamento");
        rbtOsOrcamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOsOrcamentoActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtOsOrdemdeservico);
        rbtOsOrdemdeservico.setText("OS");
        rbtOsOrdemdeservico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOsOrdemdeservicoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtOsNumOs, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 129, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtOsData)
                                .addContainerGap())))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rbtOsOrcamento)
                        .addGap(28, 28, 28)
                        .addComponent(rbtOsOrdemdeservico)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOsNumOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOsData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtOsOrcamento)
                    .addComponent(rbtOsOrdemdeservico))
                .addContainerGap())
        );

        jLabel3.setText("Situação");

        cboxOsSituacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Na bancada", "Entrega OK", "Orçamento REPROVADO", "Aguardando Aprovação", "Aguardando Peças", "Abandonado pelo cliente", "Retornou" }));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtOsPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtOsPesquisarKeyReleased(evt);
            }
        });

        tblOsClientes = new javax.swing.JTable() {
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tblOsClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Nome", "Telefone"
            }
        ));
        tblOsClientes.setFocusable(false);
        tblOsClientes.getTableHeader().setReorderingAllowed(false);
        tblOsClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOsClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblOsClientes);

        txtOsId.setEditable(false);

        jLabel5.setText("ID*");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/informatica/icones/lupa20px.png"))); // NOI18N

        jLabel11.setText("Cliente");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(47, 47, 47))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtOsPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(txtOsId, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 20, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtOsId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtOsPesquisar))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel6.setText("Equipamento*");

        jLabel7.setText("Defeito*");

        jLabel8.setText("Serviço");

        jLabel9.setText("Tecnico");

        jLabel10.setText("Valor Total");

        txtOsValorTotal.setText("0");

        btnOsAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/informatica/icones/create.png"))); // NOI18N
        btnOsAdicionar.setToolTipText("Adicionar");
        btnOsAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsAdicionar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnOsAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsAdicionarActionPerformed(evt);
            }
        });

        btnOsPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/informatica/icones/read.png"))); // NOI18N
        btnOsPesquisar.setToolTipText("Pesquisar");
        btnOsPesquisar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsPesquisar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnOsPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsPesquisarActionPerformed(evt);
            }
        });

        btnOsEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/informatica/icones/update.png"))); // NOI18N
        btnOsEditar.setToolTipText("Editar");
        btnOsEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsEditar.setEnabled(false);
        btnOsEditar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnOsEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsEditarActionPerformed(evt);
            }
        });

        btnOsApagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/informatica/icones/delete.png"))); // NOI18N
        btnOsApagar.setToolTipText("Remover");
        btnOsApagar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsApagar.setEnabled(false);
        btnOsApagar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnOsApagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsApagarActionPerformed(evt);
            }
        });

        btnOsPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/informatica/icones/printer.png"))); // NOI18N
        btnOsPrint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsPrint.setEnabled(false);
        btnOsPrint.setPreferredSize(new java.awt.Dimension(80, 80));

        jLabel12.setText("* Campos obrigatórios");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cboxOsSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel12)))
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtOsEquipamento, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnOsAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnOsApagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnOsEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnOsPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnOsPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel8)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel9))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(txtOsTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel10)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txtOsValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(txtOsDefeito)
                                        .addComponent(txtOsServico, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cboxOsSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtOsEquipamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtOsDefeito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtOsServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtOsTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtOsValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnOsAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOsApagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOsEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOsPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOsPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        setBounds(0, 0, 560, 470);
    }// </editor-fold>//GEN-END:initComponents

    private void txtOsPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOsPesquisarKeyReleased
        // chamar metodo pesquisa clientes
        pesquisa_cliente();
    }//GEN-LAST:event_txtOsPesquisarKeyReleased

    private void tblOsClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOsClientesMouseClicked
        // chamar metodo set campos
        set_campos();
    }//GEN-LAST:event_tblOsClientesMouseClicked

    private void rbtOsOrcamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOsOrcamentoActionPerformed
        // atribui texto a variavel dizendo qual tipo foi selecionado
        tipo = "Orçamento";
    }//GEN-LAST:event_rbtOsOrcamentoActionPerformed

    private void rbtOsOrdemdeservicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOsOrdemdeservicoActionPerformed
        // atribui texto a variavel dizendo qual tipo foi selecionado
        tipo = "OS";
    }//GEN-LAST:event_rbtOsOrdemdeservicoActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // quando abrir form marca o radio button orçamento
        rbtOsOrcamento.setSelected(true);
        tipo = "Orçamento";
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnOsAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsAdicionarActionPerformed
        // chama metodo de emitir os
        emitir_os();
    }//GEN-LAST:event_btnOsAdicionarActionPerformed

    private void btnOsPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsPesquisarActionPerformed
        // chama metodo pesquisa os
        pesquisa_os();
    }//GEN-LAST:event_btnOsPesquisarActionPerformed

    private void btnOsEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsEditarActionPerformed
        // chama metodo alterar os
        alterar_os();
    }//GEN-LAST:event_btnOsEditarActionPerformed

    private void btnOsApagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsApagarActionPerformed
        // chama metodo excluir os
        excluir_os();
    }//GEN-LAST:event_btnOsApagarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOsAdicionar;
    private javax.swing.JButton btnOsApagar;
    private javax.swing.JButton btnOsEditar;
    private javax.swing.JButton btnOsPesquisar;
    private javax.swing.JButton btnOsPrint;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboxOsSituacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbtOsOrcamento;
    private javax.swing.JRadioButton rbtOsOrdemdeservico;
    private javax.swing.JTable tblOsClientes;
    private javax.swing.JTextField txtOsData;
    private javax.swing.JTextField txtOsDefeito;
    private javax.swing.JTextField txtOsEquipamento;
    private javax.swing.JTextField txtOsId;
    private javax.swing.JTextField txtOsNumOs;
    private javax.swing.JTextField txtOsPesquisar;
    private javax.swing.JTextField txtOsServico;
    private javax.swing.JTextField txtOsTecnico;
    private javax.swing.JTextField txtOsValorTotal;
    // End of variables declaration//GEN-END:variables
}
