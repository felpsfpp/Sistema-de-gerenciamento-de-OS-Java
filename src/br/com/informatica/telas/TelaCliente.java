package br.com.informatica.telas;

import java.sql.*;
import br.com.informatica.dal.Conexao;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author felip
 */
public class TelaCliente extends javax.swing.JInternalFrame {
    
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    public TelaCliente() {
        initComponents();
        conexao = Conexao.conectar();
        pesquisa_cliente();
    }

    //metodo para adicionar clientes
    private void adicionar() {
        String sql = "insert into tbclientes(nomecli,endcli,fonecli,emailcli) values(?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtClienteNome.getText());
            pst.setString(2, txtClienteEndereco.getText());
            pst.setString(3, txtClienteTelefone.getText());
            pst.setString(4, txtClienteEmail.getText());
            // validaçao campos obrigatorios
            if ((txtClienteNome.getText().isEmpty()) || (txtClienteTelefone.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatorios");
            } else {
                //update na tabela com os dados acima
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso");
                    //limpa os campos
                    limpar();
                    //atualiza a tabela
                    pesquisa_cliente();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //metodo que pesquisa clientes com filtro
    private void pesquisa_cliente() {
        String sql = "select idcli as Id,nomecli as Nome, endcli as Endereço, fonecli as Telefone, emailcli as Email from tbclientes where nomecli like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtClientePesquisar.getText() + "%");
            rs = pst.executeQuery();
            //biblioteca rs2xml.jar
            tblCliente.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //metodo que preenche os campos do formulario com a tabela
    public void set_campos() {
        int setar = tblCliente.getSelectedRow();
        txtClienteId.setText(tblCliente.getModel().getValueAt(setar, 0).toString());
        txtClienteNome.setText(tblCliente.getModel().getValueAt(setar, 1).toString());
        txtClienteEndereco.setText(tblCliente.getModel().getValueAt(setar, 2).toString());
        txtClienteTelefone.setText(tblCliente.getModel().getValueAt(setar, 3).toString());
        txtClienteEmail.setText(tblCliente.getModel().getValueAt(setar, 4).toString());
        //desabilita o botao de adicionar para evitar duplicidade de cadastros
        btnClienteAdicionar.setEnabled(false);
        btnClienteEditar.setEnabled(true);
        btnClienteRemover.setEnabled(true);
        
    }

    //metodo para alterar clientes
    private void alterar() {
        String sql = "update tbclientes set nomecli=?,endcli=?,fonecli=?,emailcli=? where idcli=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtClienteNome.getText());
            pst.setString(2, txtClienteEndereco.getText());
            pst.setString(3, txtClienteTelefone.getText());
            pst.setString(4, txtClienteEmail.getText());
            pst.setString(5, txtClienteId.getText());
            
            if ((txtClienteNome.getText().isEmpty()) || (txtClienteTelefone.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatorios");
            } else {
                //update na tabela com os dados acima
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do cliente alterados com sucesso");
                    //limpa os campos
                    limpar();
                    //habilita o botao de adicionar
                    btnClienteAdicionar.setEnabled(true);
                    //desabilita os botoes
                    btnClienteRemover.setEnabled(false);
                    btnClienteEditar.setEnabled(false);
                    //atualiza a tabela
                    pesquisa_cliente();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //metdo para remover clientes
    private void remover() {
        //confirma a remoçao
        int confirmar = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este cliente?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            String sql = "delete from tbclientes where idcli=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtClienteId.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente apagado com sucesso");
                    //limpa os campos
                    limpar();
                    //habilita o botao de adicionar
                    btnClienteAdicionar.setEnabled(true);
                    //desabilita os botoes
                    btnClienteRemover.setEnabled(false);
                    btnClienteEditar.setEnabled(false);
                    //atualiza a tabela
                    pesquisa_cliente();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    //metodo para limpar os campos
    private void limpar() {
        txtClienteId.setText(null);
        txtClientePesquisar.setText(null);
        txtClienteNome.setText(null);
        txtClienteEndereco.setText(null);
        txtClienteTelefone.setText(null);
        txtClienteEmail.setText(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnClienteAdicionar = new javax.swing.JButton();
        btnClienteEditar = new javax.swing.JButton();
        btnClienteRemover = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtClienteNome = new javax.swing.JTextField();
        txtClienteEndereco = new javax.swing.JTextField();
        txtClienteTelefone = new javax.swing.JTextField();
        txtClienteEmail = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCliente = new javax.swing.JTable();
        txtClientePesquisar = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtClienteId = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Clientes");
        setPreferredSize(new java.awt.Dimension(560, 470));

        jLabel1.setText("* Campos obrigatórios");

        btnClienteAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/informatica/icones/create.png"))); // NOI18N
        btnClienteAdicionar.setToolTipText("Adicionar");
        btnClienteAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClienteAdicionar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnClienteAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClienteAdicionarActionPerformed(evt);
            }
        });

        btnClienteEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/informatica/icones/update.png"))); // NOI18N
        btnClienteEditar.setToolTipText("Editar");
        btnClienteEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClienteEditar.setEnabled(false);
        btnClienteEditar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnClienteEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClienteEditarActionPerformed(evt);
            }
        });

        btnClienteRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/informatica/icones/delete.png"))); // NOI18N
        btnClienteRemover.setToolTipText("Remover");
        btnClienteRemover.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClienteRemover.setEnabled(false);
        btnClienteRemover.setPreferredSize(new java.awt.Dimension(80, 80));
        btnClienteRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClienteRemoverActionPerformed(evt);
            }
        });

        jLabel2.setText("Nome *");

        jLabel3.setText("Endereço");

        jLabel4.setText("Telefone *");

        jLabel5.setText("Email");

        tblCliente = new javax.swing.JTable() {
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tblCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Nome", "Endereço", "Telefone", "Email"
            }
        ));
        tblCliente.setToolTipText("");
        tblCliente.setFocusable(false);
        tblCliente.getTableHeader().setReorderingAllowed(false);
        tblCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClienteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCliente);

        txtClientePesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtClientePesquisarKeyReleased(evt);
            }
        });

        jLabel6.setText("ID");

        txtClienteId.setEnabled(false);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/informatica/icones/x.png"))); // NOI18N
        jButton1.setToolTipText("Limpar pesquisa");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setPreferredSize(new java.awt.Dimension(30, 30));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/informatica/icones/lupa20px.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(txtClientePesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(39, 39, 39)
                            .addComponent(jLabel1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtClienteNome)
                            .addComponent(txtClienteEndereco)
                            .addComponent(txtClienteEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtClienteTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(txtClienteId, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(25, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnClienteAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(btnClienteRemover, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(btnClienteEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(105, 105, 105))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(20, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtClientePesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtClienteNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtClienteEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtClienteEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtClienteTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtClienteId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnClienteRemover, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnClienteAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnClienteEditar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );

        setBounds(0, 0, 560, 470);
    }// </editor-fold>//GEN-END:initComponents

    private void btnClienteAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClienteAdicionarActionPerformed
        //adiciona clientes
        adicionar();
    }//GEN-LAST:event_btnClienteAdicionarActionPerformed

    private void txtClientePesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClientePesquisarKeyReleased
        //pesquisar clientes
        pesquisa_cliente();
    }//GEN-LAST:event_txtClientePesquisarKeyReleased

    private void tblClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClienteMouseClicked
        // quando mouse clica em opçao na tabela
        set_campos();
    }//GEN-LAST:event_tblClienteMouseClicked

    private void btnClienteEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClienteEditarActionPerformed
        // altera clientes
        alterar();
    }//GEN-LAST:event_btnClienteEditarActionPerformed

    private void btnClienteRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClienteRemoverActionPerformed
        // remove clientes
        remover();
    }//GEN-LAST:event_btnClienteRemoverActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // limpa o campo de pesquisa
        txtClientePesquisar.setText(null);
        //atualiza a tabela
        pesquisa_cliente();        
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClienteAdicionar;
    private javax.swing.JButton btnClienteEditar;
    private javax.swing.JButton btnClienteRemover;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblCliente;
    private javax.swing.JTextField txtClienteEmail;
    private javax.swing.JTextField txtClienteEndereco;
    private javax.swing.JTextField txtClienteId;
    private javax.swing.JTextField txtClienteNome;
    private javax.swing.JTextField txtClientePesquisar;
    private javax.swing.JTextField txtClienteTelefone;
    // End of variables declaration//GEN-END:variables
}
