package com.example.red.gui.component;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import com.example.red.gui.swing.Boton;
import com.example.red.gui.swing.CampoContrasena;
import com.example.red.gui.swing.CampoTexto;
import com.example.red.gui.model.ModelLogin;
import com.example.red.gui.model.ModelUser;

import net.miginfocom.swing.MigLayout;

//es un panel que puede contener múltiples capas de componentes. Este panel específico gestiona dos secciones: una para iniciar sesión y otra para registrarse. Dependiendo del estado, una de las dos vistas (login o register) se hace visible
public class PanelLoginAndRegister extends javax.swing.JLayeredPane {

    public ModelUser getUser() {
        return user;
    }

    public ModelLogin getDataLogin() {
        return dataLogin;
    }

    private ModelUser user;
    private ModelLogin dataLogin;

    public PanelLoginAndRegister(ActionListener eventRegister, ActionListener eventLogin) {
        // es responsable de inicializar los componentes del panel
        initComponents();
        initRegister(eventRegister);// para configurar los paneles de registro e inicio de sesion
        initLogin(eventLogin);// para configurar los paneles de registro e inicio de sesion
        login.setVisible(false);// login configurado como invisible
        registrar.setVisible(true);// registrar confirmado como visible
    }

    // metodo para iniciar in panel
    private void initRegister(ActionListener eventRegister) {
        registrar.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push"));
        JLabel label = new JLabel("Crear Cuenta");
        label.setFont(new Font("sansserif", 1, 30));
        label.setForeground(new Color(0, 133, 132));
        registrar.add(label);

        // Es un campo de texto (MyTextField es una clase dentro de la carpeta swing),
        // que incluye un icono de usuario al principio y un placeholder con el texto
        // "Nombre"
        CampoTexto txtUser = new CampoTexto();
        txtUser.establecerIconoPrefijo(
                new ImageIcon(getClass().getResource("/com/example/red/gui/icon/user.png")));
        txtUser.establecerSugerencia("Nombre");
        registrar.add(txtUser, "w 60%");

        // similar pero con otro icono para el mail
        CampoTexto txtEmail = new CampoTexto();
        txtEmail.establecerIconoPrefijo(
                new ImageIcon(getClass().getResource("/com/example/red/gui/icon/mail.png")));
        txtEmail.establecerSugerencia("Email");
        registrar.add(txtEmail, "w 60%");

        // Un campo de contraseña personalizado (MyPasswordField) con un icono de
        // candado y un placeholder que dice "Contraseña"
        CampoContrasena txtPass = new CampoContrasena();
        txtPass.establecerIconoPrefijo(
                new ImageIcon(getClass().getResource("/com/example/red/gui/icon/pass.png")));
        txtPass.establecerSugerencia("Contraseña");
        registrar.add(txtPass, "w 60%");

        // Un boton personalizado con el texto "REGISTRARSE" y colores especificos. Este
        // boton probablemente este vinculado a un evento en otro lugar del codigo
        Boton cmd = new Boton();
        cmd.setBackground(new Color(0, 133, 132));
        cmd.setForeground(new Color(250, 250, 250));
        cmd.addActionListener(eventRegister);
        cmd.setText("REGISTRARSE");
        registrar.add(cmd, "w 40%, h 40");
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String userName = txtUser.getText().trim();
                String email = txtEmail.getText().trim();
                String password = String.valueOf(txtPass.getPassword());
                user = new ModelUser(0, userName, email, password);
            }
        });
    }

    private void initLogin(ActionListener eventLogin) {
        login.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push"));
        JLabel label = new JLabel("Iniciar sesion");
        label.setFont(new Font("sansserif", 1, 30));
        label.setForeground(new Color(0, 133, 132));
        login.add(label);
        CampoTexto txtEmail = new CampoTexto();
        txtEmail.establecerIconoPrefijo(
                new ImageIcon(getClass().getResource("/com/example/red/gui/icon/mail.png")));
        txtEmail.establecerSugerencia("Email");
        login.add(txtEmail, "w 60%");
        CampoContrasena txtPass = new CampoContrasena();
        txtPass.establecerIconoPrefijo(
                new ImageIcon(getClass().getResource("/com/example/red/gui/icon/pass.png")));
        txtPass.establecerSugerencia("Contraseña");
        login.add(txtPass, "w 60%");
        JButton cmdForget = new JButton("Olvidaste tu contraseña ?");
        cmdForget.setForeground(new Color(100, 100, 100));
        cmdForget.setFont(new Font("sansserif", 1, 12));
        cmdForget.setContentAreaFilled(false);
        cmdForget.setCursor(new Cursor(Cursor.HAND_CURSOR));
        login.add(cmdForget);
        Boton cmd = new Boton();
        cmd.setBackground(new Color(0, 133, 132));
        cmd.setForeground(new Color(250, 250, 250));
        cmd.addActionListener(eventLogin);
        cmd.setText("INICIAR");
        login.add(cmd, "w 40%, h 40");
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                String email = txtEmail.getText().trim();
                String password = String.valueOf(txtPass.getPassword());
                dataLogin = new ModelLogin(email, password);
            }
        });
    }

    public void showRegister(boolean show) {
        if (show) {
            registrar.setVisible(true);
            login.setVisible(false);
        } else {
            registrar.setVisible(false);
            login.setVisible(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        login = new javax.swing.JPanel();
        registrar = new javax.swing.JPanel();

        setLayout(new java.awt.CardLayout());

        login.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout loginLayout = new javax.swing.GroupLayout(login);
        login.setLayout(loginLayout);
        loginLayout.setHorizontalGroup(
                loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 327, Short.MAX_VALUE));
        loginLayout.setVerticalGroup(
                loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE));

        add(login, "card3");

        registrar.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout registerLayout = new javax.swing.GroupLayout(registrar);
        registrar.setLayout(registerLayout);
        registerLayout.setHorizontalGroup(
                registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 327, Short.MAX_VALUE));
        registerLayout.setVerticalGroup(
                registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE));

        add(registrar, "card2");
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel login;
    private javax.swing.JPanel registrar;
    // End of variables declaration//GEN-END:variables
}
