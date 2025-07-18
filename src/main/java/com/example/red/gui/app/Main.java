package com.example.red.gui.app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JLayeredPane;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import com.example.red.conexion.ConexionBD;
import com.example.red.gui.InterfazUI;
import com.example.red.gui.component.Message;
import com.example.red.gui.component.PanelCover;
import com.example.red.gui.component.PanelLoading;
import com.example.red.gui.component.PanelLoginAndRegister;
import com.example.red.gui.component.PanelVerifyCode;
import com.example.red.modelo.ModelLogin;
import com.example.red.modelo.ModelMessage;
import com.example.red.modelo.ModelUser;
import com.example.red.servicio.IdiomaService;
import com.example.red.servicio.ServiceMail;
import com.example.red.servicio.ServiceUsuario;

import net.miginfocom.swing.MigLayout;

/**
 * Esta clase es una ventana (JFrame) que contiene una interfaz de inicio de
 * sesión y registro con animaciones suaves, utilizando componentes
 * personalizados y la librería MigLayout para organizar los componentes dentro
 * del formulario.
 */
public class Main extends javax.swing.JFrame {

    /** idioma */
    private ResourceBundle idioma;

    private final DecimalFormat df = new DecimalFormat("##0.###", DecimalFormatSymbols.getInstance(Locale.US));

    /**
     * se utiliza para organizar los componentes dentro del panel bg (la capa
     * principal que contiene los otros componentes)
     */
    private MigLayout layout;
    private PanelCover cover;
    private PanelLoading loading;
    private PanelVerifyCode verifyCode;
    private PanelLoginAndRegister loginAndRegister;
    private boolean isLogin;
    private final double addSize = 30;
    private final double coverSize = 40;
    private final double loginSize = 60;
    private ServiceUsuario service;

    public Main() {
        // Idioma
        idioma = IdiomaService.getRb();

        initComponents();
        init();
    }

    private void init() {
        // El diseño utilizado para organizar los componentes cover y loginAndRegister.
        // Es un layout muy flexible y simplifica la disposición de elementos en filas y
        // columnas
        service = new ServiceUsuario();
        layout = new MigLayout("fill, insets 0");
        cover = new PanelCover();
        loading = new PanelLoading();
        verifyCode = new PanelVerifyCode();
        ActionListener eventRegister = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                register();
            }
        };
        ActionListener eventLogin = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                login();
            }
        };
        loginAndRegister = new PanelLoginAndRegister(eventRegister, eventLogin);
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            // La variable fraction indica el progreso de la animación, y se utiliza para
            // calcular las proporciones de los paneles (cover y loginAndRegister) en
            // función de su estado de visibilidad.
            public void timingEvent(float fraction) {
                double fractionCover;
                double fractionLogin;
                double size = coverSize;
                if (fraction <= 0.5f) {
                    size += fraction * addSize;
                } else {
                    size += addSize - fraction * addSize;
                }
                if (isLogin) {
                    fractionCover = 1f - fraction;
                    fractionLogin = fraction;
                    if (fraction >= 0.5f) {
                        cover.registroDerecha(fractionCover * 100);
                    } else {
                        cover.loginDerecha(fractionLogin * 100);
                    }
                } else {
                    fractionCover = fraction;
                    fractionLogin = 1f - fraction;
                    if (fraction <= 0.5f) {
                        cover.registroDerecha(fraction * 100);
                    } else {
                        cover.loginIzquierda((1f - fraction) * 100);
                    }
                }
                if (fraction >= 0.5f) {
                    loginAndRegister.showRegister(isLogin);
                }
                // Aqui se controla la animación de transicion entre los paneles de inicio de
                // sesion y registro. Dependiendo del valor de isLogin, la animacion alterna
                // entre las vistas de "login" y "register". Las funciones registerRight,
                // loginRight, registerLeft y loginLeft probablemente animan la posicion y
                // visibilidad de los paneles

                // Actualiza las restricciones del layout de los paneles durante la animacion,
                // cambiando su tamaño y posicion. fractionCover y fractionLogin se utilizan
                // para calcular la posicion exacta de los paneles
                fractionCover = Double.valueOf(df.format(fractionCover));
                fractionLogin = Double.valueOf(df.format(fractionLogin));
                layout.setComponentConstraints(cover, "width " + size + "%, pos " + fractionCover + "al 0 n 100%");
                layout.setComponentConstraints(loginAndRegister,
                        "width " + loginSize + "%, pos " + fractionLogin + "al 0 n 100%");
                bg.revalidate();
            }

            @Override
            public void end() {
                isLogin = !isLogin;
            }
        };
        Animator animator = new Animator(800, target);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.setResolution(0); // for smooth animation
        bg.setLayout(layout);
        bg.setLayer(loading, JLayeredPane.POPUP_LAYER);
        bg.setLayer(verifyCode, JLayeredPane.POPUP_LAYER);
        bg.add(loading, "pos 0 0 100% 100%");
        bg.add(verifyCode, "pos 0 0 100% 100%");
        bg.add(cover, "width " + coverSize + "%, pos 0al 0 n 100%");
        bg.add(loginAndRegister, "width " + loginSize + "%, pos 1al 0 n 100%"); // 1al as 100%
        cover.agregarEvento(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!animator.isRunning()) {
                    animator.start();
                }
            }
        });
        verifyCode.addEventButtonOK(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    ModelUser user = loginAndRegister.getUser();
                    if (service.verifyCodeWithUser(user.getUserID(), verifyCode.getInputCode())) {
                        service.doneVerify(user.getUserID());
                        showMessage(Message.MessageType.SUCCESS, idioma.getString("label_verificacion_exito"));
                        verifyCode.setVisible(false);
                    } else {
                        showMessage(Message.MessageType.ERROR, idioma.getString("label_verificacion_error"));
                    }
                } catch (SQLException e) {

                    showMessage(Message.MessageType.ERROR,
                            idioma.getStringArray("label_error") + ": " + e.getMessage());
                }
            }
        });

    }

    private void register() {
        ModelUser user = loginAndRegister.getUser();
        try {
            if (service.checkDuplicateUser(user.getUserName())) {
                showMessage(Message.MessageType.ERROR, idioma.getString("label_verificacion_nombreExiste"));
            } else if (service.checkDuplicateEmail(user.getEmail())) {
                showMessage(Message.MessageType.ERROR, idioma.getString("label_verificacion_emailExiste"));
            } else {
                service.insertUser(user);
                sendMain(user);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Esto imprimirá el error en la consola.
            showMessage(Message.MessageType.ERROR,
                    idioma.getString("label_crearCuenta_errorRegistro") + ": " + e.getMessage());
        }
    }

    private void login() {
        ModelLogin data = loginAndRegister.getDataLogin();
        try {
            ModelUser user = service.login(data);
            if (user != null) {
                this.dispose();
                InterfazUI.main(null);// deberia agregar user luego para que inicie el sistema
            } else {
                showMessage(Message.MessageType.ERROR, idioma.getString("label_iniciarSesion_datosInvalidos"));
            }

        } catch (SQLException e) {
            showMessage(Message.MessageType.ERROR, idioma.getString("label_iniciarSesion_errorInicioSesion"));
        }
    }

    private void sendMain(ModelUser user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                loading.setVisible(true);
                ModelMessage ms = new ServiceMail().sendMain(user.getEmail(), user.getVerifyCode());
                if (ms.isSuccess()) {
                    loading.setVisible(false);
                    verifyCode.setVisible(true);
                } else {
                    loading.setVisible(false);
                    showMessage(Message.MessageType.ERROR, ms.getMessage());
                }
            }
        }).start();
    }

    private void showMessage(Message.MessageType messageType, String message) {
        Message ms = new Message();
        ms.showMessage(messageType, message);
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void begin() {
                if (!ms.isShow()) {
                    bg.add(ms, "pos 0.5al -30", 0); // Insert to bg fist index 0
                    ms.setVisible(true);
                    bg.repaint();
                }
            }

            @Override
            public void timingEvent(float fraction) {
                float f;
                if (ms.isShow()) {
                    f = 40 * (1f - fraction);
                } else {
                    f = 40 * fraction;
                }
                layout.setComponentConstraints(ms, "pos 0.5al " + (int) (f - 30));
                bg.repaint();
                bg.revalidate();
            }

            @Override
            public void end() {
                if (ms.isShow()) {
                    bg.remove(ms);
                    bg.repaint();
                    bg.revalidate();
                } else {
                    ms.setShow(true);
                }
            }
        };
        Animator animator = new Animator(300, target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    animator.start();
                } catch (InterruptedException e) {
                    System.err.println(e);
                }
            }
        }).start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        bg = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setOpaque(true);

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
                bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 933, Short.MAX_VALUE));
        bgLayout.setVerticalGroup(
                bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 537, Short.MAX_VALUE));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(bg, javax.swing.GroupLayout.Alignment.TRAILING));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(bg));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>

        /* Create and display the form */
        try {
            ConexionBD.getInstance().connectToDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JLayeredPane bg;
    // End of variables declaration
}
