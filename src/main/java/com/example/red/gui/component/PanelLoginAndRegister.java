package com.example.red.gui.component;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import com.example.red.gui.swing.Boton;
import com.example.red.gui.swing.CampoContrasena;
import com.example.red.gui.swing.CampoTexto;
import com.example.red.modelo.ModelLogin;
import com.example.red.modelo.ModelUser;
import com.example.red.servicio.IdiomaService;

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
    private ResourceBundle idioma;

    public PanelLoginAndRegister(ActionListener eventRegister, ActionListener eventLogin) {
        // idioma
        idioma = IdiomaService.getRb();

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
        JLabel label = new JLabel(idioma.getString("label_crearCuenta_titulo"));
        label.setFont(new Font("sansserif", 1, 30));
        label.setForeground(new Color(0, 133, 132));
        registrar.add(label);

        // Es un campo de texto (MyTextField es una clase dentro de la carpeta swing),
        // que incluye un icono de usuario al principio y un placeholder con el texto
        // "Nombre"
        CampoTexto txtUser = new CampoTexto();
        txtUser.establecerIconoPrefijo(
                new ImageIcon(getClass().getResource("/com/example/red/gui/icon/user.png")));
        txtUser.establecerSugerencia(idioma.getString("label_nombre"));
        registrar.add(txtUser, "w 60%");

        // similar pero con otro icono para el mail
        CampoTexto txtEmail = new CampoTexto();
        txtEmail.establecerIconoPrefijo(
                new ImageIcon(getClass().getResource("/com/example/red/gui/icon/mail.png")));
        txtEmail.establecerSugerencia(idioma.getString("label_email"));
        registrar.add(txtEmail, "w 60%");

        // Un campo de contraseña personalizado (MyPasswordField) con un icono de
        // candado y un placeholder que dice "Contraseña"
        CampoContrasena txtPass = new CampoContrasena();
        txtPass.establecerIconoPrefijo(
                new ImageIcon(getClass().getResource("/com/example/red/gui/icon/pass.png")));
        txtPass.establecerSugerencia(idioma.getString("label_contrasena"));
        registrar.add(txtPass, "w 60%");

        // Un boton personalizado con el texto "REGISTRARSE" y colores especificos. Este
        // boton probablemente este vinculado a un evento en otro lugar del codigo
        Boton cmd = new Boton();
        cmd.setBackground(new Color(0, 133, 132));
        cmd.setForeground(new Color(250, 250, 250));
        cmd.addActionListener(eventRegister);
        cmd.setText(idioma.getString("label_boton_registrarse"));
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
        JLabel label = new JLabel(idioma.getString("label_iniciarSesion_titulo"));
        label.setFont(new Font("sansserif", 1, 30));
        label.setForeground(new Color(0, 133, 132));
        login.add(label);
        CampoTexto txtEmail = new CampoTexto();
        txtEmail.establecerIconoPrefijo(
                new ImageIcon(getClass().getResource("/com/example/red/gui/icon/mail.png")));
        txtEmail.establecerSugerencia(idioma.getString("label_email"));
        login.add(txtEmail, "w 60%");
        CampoContrasena txtPass = new CampoContrasena();
        txtPass.establecerIconoPrefijo(
                new ImageIcon(getClass().getResource("/com/example/red/gui/icon/pass.png")));
        txtPass.establecerSugerencia(idioma.getString("label_contrasena"));
        login.add(txtPass, "w 60%");
        JButton cmdForget = new JButton(idioma.getString("label_iniciarSesion_contrasenaOlvidada"));
        cmdForget.setForeground(new Color(100, 100, 100));
        cmdForget.setFont(new Font("sansserif", 1, 12));
        cmdForget.setContentAreaFilled(false);
        cmdForget.setCursor(new Cursor(Cursor.HAND_CURSOR));
        login.add(cmdForget);
        Boton cmd = new Boton();
        cmd.setBackground(new Color(0, 133, 132));
        cmd.setForeground(new Color(250, 250, 250));
        cmd.addActionListener(eventLogin);
        cmd.setText(idioma.getString("label_boton_iniciarSesion"));
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
/**
 * La clase PanelLoginAndRegister proporciona una interfaz gráfica para
 * gestionar el
 * proceso de registro e inicio de sesión. Este panel alterna entre dos vistas
 * (registro
 * y login) y permite capturar datos de usuario para crear instancias de
 * ModelUser y
 * ModelLogin, facilitando la integración con un backend o sistema de
 * autenticación.
 *
 * Funcionalidades principales:
 * - **Registro**: Contiene campos personalizados para ingresar nombre, email y
 * contraseña. Al hacer clic en el botón "REGISTRARSE", los datos se guardan en
 * un
 * objeto `ModelUser` para su procesamiento.
 * - **Login**: Proporciona un formulario para iniciar sesión con email y
 * contraseña.
 * Los datos se almacenan en un objeto `ModelLogin` al presionar el botón
 * "INICIAR".
 * - **Cambio de paneles**: Mediante el método `showRegister`, permite alternar
 * entre el panel de registro y el de inicio de sesión, según las necesidades de
 * la aplicación.
 *
 * Parámetros del constructor:
 * - `eventRegister`: Un `ActionListener` para manejar el evento de registro.
 * - `eventLogin`: Un `ActionListener` para manejar el evento de inicio de
 * sesión.
 *
 */