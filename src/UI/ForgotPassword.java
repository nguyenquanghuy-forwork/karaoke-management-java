/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author NQHuy
 */

public class ForgotPassword extends javax.swing.JFrame {
    Connection conn;
    ResultSet rs;
    PreparedStatement pst;

    /**
     * Creates new form ForgotPassword
     */
    public ForgotPassword() {
        super("Forgot Password");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/ICoinLoGo/password.png")));
        initComponents();
        conn=javaconnect.ConnectDB();
        setExtendedState(JFrame.MAXIMIZED_HORIZ);
        
        setVisible(true);
        setResizable(false);
        Toolkit toolkit=getToolkit();
        Dimension size;
        size = toolkit.getScreenSize();
        setLocation(size.width/2-getWidth()/2,size.height/2-getHeight()/2);
    }
    
    private void Failed(String str1){
        ImageIcon icon = new ImageIcon(getClass().getResource("/IconForm/icons8_failed.png"));
        JOptionPane.showMessageDialog(null, str1,"...Warning...",JOptionPane.DEFAULT_OPTION, icon);
    }
    
    private void Completed(String str1){
        ImageIcon icon = new ImageIcon(getClass().getResource("/IconForm/icons8_completed.png"));
        JOptionPane.showMessageDialog(null, str1, "....Notification...",JOptionPane.DEFAULT_OPTION, icon);
    }
    
    public void Search(){
        String a1=fgUserName.getText();
        String sql="select * from Account where UserName='"+a1+"'";
        try{
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            if (rs.next()){
                fgUserName.setText(rs.getString(1));
                fgName.setText(rs.getString(2));
                fgYourPhone.setText(rs.getString(3));
                fgBirth.setText(rs.getString(4));
                fgSecurityQuestion.addItem(rs.getString(5));
                fgSecurityQuestion.setSelectedItem(rs.getString(5));
            }
            
            else{
                Failed("Incorrect User Name - Please Try Again!");
            }
            rs.close();
            pst.close();
        }catch(Exception e){
            Failed("Search Unknown Error");
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

        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        fgBirth = new javax.swing.JTextField();
        fgNewPassword = new javax.swing.JPasswordField();
        fgName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        fgAnswer = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        fgUserName = new javax.swing.JTextField();
        fgYourPhone = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btnTryChangePassword = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        fgSecurityQuestion = new javax.swing.JComboBox<>();
        btnFind = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));

        jLabel6.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        jLabel6.setText("Security Question:");

        jLabel5.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        jLabel5.setText("Birth:");

        jLabel1.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        jLabel1.setText("User name:");

        fgBirth.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N

        fgNewPassword.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        fgNewPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fgNewPasswordActionPerformed(evt);
            }
        });

        fgName.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        jLabel3.setText("Your Phone:");

        fgAnswer.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        jLabel2.setText("Name:");

        fgUserName.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        fgUserName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fgUserNameActionPerformed(evt);
            }
        });

        fgYourPhone.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        jLabel4.setText("New Password:");

        jLabel7.setBackground(new java.awt.Color(255, 0, 0));
        jLabel7.setFont(new java.awt.Font("Sitka Banner", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 51, 51));
        jLabel7.setText("Forgot Password");

        jButton1.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IcoinUI/right-arrow.png"))); // NOI18N
        jButton1.setText("Back");
        jButton1.setToolTipText("Back Login");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnTryChangePassword.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        btnTryChangePassword.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IcoinUI/shield.png"))); // NOI18N
        btnTryChangePassword.setText("Try Change Password");
        btnTryChangePassword.setToolTipText("");
        btnTryChangePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTryChangePasswordActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        jLabel8.setText("Answer:");

        fgSecurityQuestion.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        fgSecurityQuestion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Who is your mother?", "What is your nick name?", "Who is your best friend?" }));

        btnFind.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        btnFind.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IcoinUI/icons8-find-user-male-16.png"))); // NOI18N
        btnFind.setText("Find");
        btnFind.setToolTipText("Find User Name");
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
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
                            .addComponent(jLabel3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(90, 90, 90)
                                .addComponent(fgBirth, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel4)
                                    .addComponent(jButton1))
                                .addGap(34, 34, 34)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(fgNewPassword)
                                    .addComponent(fgAnswer)
                                    .addComponent(btnTryChangePassword, javax.swing.GroupLayout.PREFERRED_SIZE, 168, Short.MAX_VALUE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel6)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(fgUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnFind, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(fgName, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fgYourPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fgSecurityQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(fgUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFind))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fgName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(fgYourPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fgBirth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fgSecurityQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fgAnswer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fgNewPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(btnTryChangePassword))
                .addContainerGap(72, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(418, 461));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void fgNewPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fgNewPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fgNewPasswordActionPerformed

    private void fgUserNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fgUserNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fgUserNameActionPerformed

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        // TODO add your handling code here:
        Search();
    }//GEN-LAST:event_btnFindActionPerformed

    private void btnTryChangePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTryChangePasswordActionPerformed
        // TODO add your handling code here:
        String User=fgUserName.getText();
        String Ans=fgAnswer.getText();
        String sql="select * from Account where UserName='"+User+"'";
        try{
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            if ((rs.getString(6)).equals(Ans)){
                
                try {           
                    PreparedStatement st = conn.prepareStatement("UPDATE Account SET Password = ? WHERE UserName = ?");
                    st.setString(1, fgNewPassword.getText());
                    st.setString(2,fgUserName.getText());
                    st.executeUpdate();
                    Completed("Updated");
                    st.close();
                }
                catch (Exception e ) {
                    Failed("Update Password Error");      
                }           
            }
            else{
                Failed("Incorrect Security Questions - Please Try Again!");
            }
            rs.close();
            pst.close();
            
        }catch(Exception e){
           Failed("Please Input All Information Carefully ");
        }
        
    }//GEN-LAST:event_btnTryChangePasswordActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        Login ob=new Login();
        ob.setVisible(true);
        
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(ForgotPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ForgotPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ForgotPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ForgotPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ForgotPassword().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnTryChangePassword;
    private javax.swing.JTextField fgAnswer;
    private javax.swing.JTextField fgBirth;
    private javax.swing.JTextField fgName;
    private javax.swing.JPasswordField fgNewPassword;
    private javax.swing.JComboBox<String> fgSecurityQuestion;
    private javax.swing.JTextField fgUserName;
    private javax.swing.JTextField fgYourPhone;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
