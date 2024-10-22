package br.com.informatica.telas;

import java.sql.*;
import br.com.informatica.dal.Conexao;
import javax.swing.JOptionPane;

/**
 *
 * @author felip
 */
public class TelaCadUsuario extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaCadUsuario() {
        initComponents();
        conexao = Conexao.conectar();
    }

    //consulta de usuarios
    private void consultar() {
        String sql = "select * from tbusuarios where iduser = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuariosId.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                txtUsuariosNome.setText(rs.getString(2));
                txtUsuariosLogin.setText(rs.getString(3));
                txtUsuariosSenha.setText(rs.getString(4));
                //combobox seleçao de usuarios admin ou user
                cboxUsuariosPerfil.setSelectedItem(rs.getString(5));
                //habilita e desabilita botoes
                btnUsuariosDelete.setEnabled(true);
                btnUsuariosEditar.setEnabled(true);
                btnUsuariosCreate.setEnabled(false);
                btnUsuariosPesquisar.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não cadastrado!");
                //limpa os campos
                txtUsuariosNome.setText(null);
                txtUsuariosLogin.setText(null);
                txtUsuariosSenha.setText(null);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //adicionar usuarios
    private void adicionar() {
        String sql = "insert into tbusuarios(iduser,usuario,login,senha,perfil) values(?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuariosId.getText());
            pst.setString(2, txtUsuariosNome.getText());
            pst.setString(3, txtUsuariosLogin.getText());
            pst.setString(4, txtUsuariosSenha.getText());
            pst.setString(5, cboxUsuariosPerfil.getSelectedItem().toString());
            // validaçao campos obrigatorios
            if ((txtUsuariosId.getText().isEmpty()) || (txtUsuariosNome.getText().isEmpty()) || (txtUsuariosLogin.getText().isEmpty()) || (txtUsuariosSenha.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatorios");
            } else {
                //update na tabela com os dados acima
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário adicionado com sucesso");
                    //limpa os campos
                    txtUsuariosId.setText(null);
                    txtUsuariosNome.setText(null);
                    txtUsuariosLogin.setText(null);
                    txtUsuariosSenha.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void alterar() {
        String sql = "update tbusuarios set usuario=?,login=?,senha=?,perfil=? where iduser=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuariosNome.getText());
            pst.setString(2, txtUsuariosLogin.getText());
            pst.setString(3, txtUsuariosSenha.getText());
            pst.setString(4, cboxUsuariosPerfil.getSelectedItem().toString());
            pst.setString(5, txtUsuariosId.getText());

            if ((txtUsuariosId.getText().isEmpty()) || (txtUsuariosNome.getText().isEmpty()) || (txtUsuariosLogin.getText().isEmpty()) || (txtUsuariosSenha.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatorios");
            } else {
                //update na tabela com os dados acima
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do usuário alterados com sucesso");
                    //limpa os campos
                    txtUsuariosId.setText(null);
                    txtUsuariosNome.setText(null);
                    txtUsuariosLogin.setText(null);
                    txtUsuariosSenha.setText(null);
                    //habilita e desabilita botoes
                    btnUsuariosDelete.setEnabled(false);
                    btnUsuariosEditar.setEnabled(false);
                    btnUsuariosCreate.setEnabled(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void remover() {
        //confirma a remoçao
        int confirmar = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este usuario?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            String sql = "delete from tbusuarios where iduser=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtUsuariosId.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário apagado com sucesso");
                    txtUsuariosId.setText(null);
                    txtUsuariosNome.setText(null);
                    txtUsuariosLogin.setText(null);
                    txtUsuariosSenha.setText(null);
                    //habilita e desabilita botoes
                    btnUsuariosDelete.setEnabled(false);
                    btnUsuariosEditar.setEnabled(false);
                    btnUsuariosCreate.setEnabled(true);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtUsuariosId = new javax.swing.JTextField();
        txtUsuariosNome = new javax.swing.JTextField();
        txtUsuariosLogin = new javax.swing.JTextField();
        txtUsuariosSenha = new javax.swing.JTextField();
        cboxUsuariosPerfil = new javax.swing.JComboBox<>();
        btnUsuariosCreate = new javax.swing.JButton();
        btnUsuariosDelete = new javax.swing.JButton();
        btnUsuariosEditar = new javax.swing.JButton();
        btnUsuariosPesquisar = new javax.swing.JButton();
        btnUsuariosApagarID = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Usuarios");
        setPreferredSize(new java.awt.Dimension(560, 470));

        jLabel1.setText("ID*");

        jLabel2.setText("Nome *");

        jLabel3.setText("Login *");

        jLabel4.setText("Senha *");

        jLabel5.setText("Perfil *");

        cboxUsuariosPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "admin", "user" }));

        btnUsuariosCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/informatica/icones/create.png"))); // NOI18N
        btnUsuariosCreate.setToolTipText("Adicionar");
        btnUsuariosCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuariosCreate.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUsuariosCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuariosCreateActionPerformed(evt);
            }
        });

        btnUsuariosDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/informatica/icones/delete.png"))); // NOI18N
        btnUsuariosDelete.setToolTipText("Remover");
        btnUsuariosDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuariosDelete.setEnabled(false);
        btnUsuariosDelete.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUsuariosDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuariosDeleteActionPerformed(evt);
            }
        });

        btnUsuariosEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/informatica/icones/update.png"))); // NOI18N
        btnUsuariosEditar.setToolTipText("Editar");
        btnUsuariosEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuariosEditar.setEnabled(false);
        btnUsuariosEditar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUsuariosEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuariosEditarActionPerformed(evt);
            }
        });

        btnUsuariosPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/informatica/icones/lupa20px.png"))); // NOI18N
        btnUsuariosPesquisar.setToolTipText("Pesquisar");
        btnUsuariosPesquisar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuariosPesquisar.setPreferredSize(new java.awt.Dimension(20, 20));
        btnUsuariosPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuariosPesquisarActionPerformed(evt);
            }
        });

        btnUsuariosApagarID.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/informatica/icones/x.png"))); // NOI18N
        btnUsuariosApagarID.setToolTipText("Limpar Campo ID");
        btnUsuariosApagarID.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuariosApagarID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuariosApagarIDActionPerformed(evt);
            }
        });

        jLabel7.setText("* Campos obrigatorios");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addComponent(btnUsuariosCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(btnUsuariosDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(btnUsuariosEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(101, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtUsuariosId, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUsuariosPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUsuariosApagarID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboxUsuariosPerfil, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtUsuariosLogin))
                        .addGap(32, 32, 32)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtUsuariosSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtUsuariosNome))
                .addGap(34, 34, 34))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtUsuariosId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnUsuariosApagarID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsuariosPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtUsuariosNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsuariosLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtUsuariosSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboxUsuariosPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUsuariosCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsuariosEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsuariosDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50))
        );

        setBounds(0, 0, 560, 470);
    }// </editor-fold>//GEN-END:initComponents

    private void btnUsuariosPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosPesquisarActionPerformed
        consultar();
    }//GEN-LAST:event_btnUsuariosPesquisarActionPerformed

    private void btnUsuariosCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosCreateActionPerformed
        adicionar();
    }//GEN-LAST:event_btnUsuariosCreateActionPerformed

    private void btnUsuariosApagarIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosApagarIDActionPerformed
        //apaga texto do ID
        txtUsuariosId.setText(null);
    }//GEN-LAST:event_btnUsuariosApagarIDActionPerformed

    private void btnUsuariosEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosEditarActionPerformed
        alterar();
    }//GEN-LAST:event_btnUsuariosEditarActionPerformed

    private void btnUsuariosDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosDeleteActionPerformed
        remover();
    }//GEN-LAST:event_btnUsuariosDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUsuariosApagarID;
    private javax.swing.JButton btnUsuariosCreate;
    private javax.swing.JButton btnUsuariosDelete;
    private javax.swing.JButton btnUsuariosEditar;
    private javax.swing.JButton btnUsuariosPesquisar;
    private javax.swing.JComboBox<String> cboxUsuariosPerfil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtUsuariosId;
    private javax.swing.JTextField txtUsuariosLogin;
    private javax.swing.JTextField txtUsuariosNome;
    private javax.swing.JTextField txtUsuariosSenha;
    // End of variables declaration//GEN-END:variables
}
