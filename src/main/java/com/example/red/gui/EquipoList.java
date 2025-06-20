/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.example.red.gui;

import com.example.red.conexion.ConexionBD;
import com.example.red.modelo.Equipo;
import com.example.red.negocio.Red;
import com.example.red.servicio.IdiomaService;
import com.example.red.servicio.EquipoServiceImpl;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;

import java.util.ResourceBundle;
import javax.swing.UIManager;

import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Lautaro
 */
public class EquipoList extends javax.swing.JFrame {
   // private Red red;
   // private Calculo calculo;
    private Equipo equipo;
    DefaultTableModel E = new DefaultTableModel();
    EquipoServiceImpl EquipoServise = new EquipoServiceImpl();
    private ResourceBundle idioma;

    /**
     * Creates new form EquipoList
     */
    public EquipoList() {
        idioma = IdiomaService.getRb();
        initComponents();
        setTable();
    //    red = Red.getRed();
       // calculo = new Calculo();
       // calculo.cargarDatos(red.getTablaEquipos(), red.getConexiones());
       setDatos();	
               // Configurar que la ventana no se pueda redimensionar
        this.setResizable(false);
        // Centrar la ventana en la pantalla
        this.setLocationRelativeTo(null);
    }
    
    private void setTable (){                     //CREA Y NOMBRA COLUMNAS
   // String[] title = {"Codigo","Descripcion","modelo","ubicacion", "IP"};
    String[] title = {idioma.getString("label_codigo"),idioma.getString("label_descripcion"), idioma.getString("label_modelo"),idioma.getString("label_ubicacion"),idioma.getString("label_ip")};
    E.setColumnIdentifiers(title);
    TableEquipos.setModel(E);
    }
    private void setDatos(){                      //CREA FILA PARA CADA EQUIPO
        Red red = new Red();
        Object[] datos = new Object[E.getColumnCount()];
        for (Equipo hard : red.getEquipos()) {          
            datos[0] = hard.getCodigo();
            datos[1] = hard.getDescripcion();
            datos[2] = hard.getModelo();
            datos[3] = hard.getUbicacion().getDescripcion();
            datos[4] = hard.getDireccionesIP();
            E.addRow(datos);
        }       
    }
    private void ResetTabla(){
        E.setRowCount(0);      
        setDatos();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        TableEquipos = new javax.swing.JTable();
        AddBoton = new javax.swing.JButton();
        ModBoton = new javax.swing.JButton();
        BorrarBoton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(233, 233, 233), 2));

        TableEquipos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(TableEquipos);

        AddBoton.setBackground(new java.awt.Color(0, 133, 132));
        AddBoton.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        AddBoton.setForeground(new java.awt.Color(255, 255, 255));
        AddBoton.setText("Agregar");
        AddBoton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddBotonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                AddBotonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                AddBotonMouseExited(evt);
            }
        });
        AddBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddBotonActionPerformed(evt);
            }
        });

        ModBoton.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        ModBoton.setForeground(new java.awt.Color(0, 133, 132));
        ModBoton.setText("Modificar");
        ModBoton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ModBotonMouseClicked(evt);
            }
        });

        BorrarBoton.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        BorrarBoton.setForeground(new java.awt.Color(0, 133, 132));
        BorrarBoton.setText("Borrar");
        BorrarBoton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BorrarBotonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BorrarBotonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BorrarBotonMouseExited(evt);
            }
        });
        BorrarBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BorrarBotonActionPerformed(evt);
            }
        });

        jButton1.setForeground(new java.awt.Color(0, 133, 132));
        jButton1.setText("Reset");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(0, 133, 132));
        jLabel1.setText(" ");

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 102));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Administrador de Equipos");

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 102));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Toque los botones \"Modificar\" para cambiar caracteristicas");

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 102));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("o \"Agregar\"  ingresar un nuevo equipo.");

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 102));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Si desea eliminar un equipo, seleccionelo de la lista, luego toque \"Borrar\".");

        jLabel9.setFont(new java.awt.Font("SansSerif", 3, 10)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 51, 51));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("¡Advertencia, esta accion no puede desaserse!");

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 102, 102));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Al terminar cada accion toque \"Reset\" para ver los cambios reflejados");
        jLabel10.setToolTipText("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(AddBoton)
                        .addGap(92, 92, 92)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ModBoton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(216, 216, 216)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(360, 360, 360)
                                        .addComponent(BorrarBoton))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(100, 100, 100)
                                        .addComponent(jLabel2)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(149, 149, 149)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addGap(54, 54, 54)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(AddBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ModBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BorrarBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 952, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AddBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddBotonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddBotonActionPerformed

    private void BorrarBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BorrarBotonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BorrarBotonActionPerformed

    private void BorrarBotonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BorrarBotonMouseExited
        BorrarBoton.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_BorrarBotonMouseExited

    private void BorrarBotonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BorrarBotonMouseEntered
        BorrarBoton.setBackground(Color.red);
    }//GEN-LAST:event_BorrarBotonMouseEntered

    private void AddBotonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddBotonMouseEntered
        AddBoton.setBackground(new java.awt.Color(100,204,79));
    }//GEN-LAST:event_AddBotonMouseEntered

    private void AddBotonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddBotonMouseExited
        AddBoton.setBackground(new java.awt.Color(0, 102, 102));
    }//GEN-LAST:event_AddBotonMouseExited

    private void AddBotonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddBotonMouseClicked
        EquipoAdd.main(null);
    }//GEN-LAST:event_AddBotonMouseClicked

    private void ModBotonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ModBotonMouseClicked
        EquipoMod.main(null);
    }//GEN-LAST:event_ModBotonMouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        ResetTabla();
    }//GEN-LAST:event_jButton1MouseClicked

    private void BorrarBotonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BorrarBotonMouseClicked
         Red ned = new Red();
    int selectRow = TableEquipos.getSelectedRow();
    if (selectRow != -1) {
        Equipo E1 = null;
        String AE1 = (String)TableEquipos.getValueAt(selectRow, 0);
        for (Equipo equipo : ned.getEquipos()) {
                String equi = equipo.getCodigo();
                if(equi.equals(AE1)){
                E1 = equipo;}}
        EquipoServise.borrar(E1);
        jLabel1.setText("El equipo "+E1.getCodigo()+ " se borro");               
     }
    }//GEN-LAST:event_BorrarBotonMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
                      // Configurar FlatLaf como Look and Feel
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        try {
            ConexionBD.getInstance().connectToDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EquipoList().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddBoton;
    private javax.swing.JButton BorrarBoton;
    private javax.swing.JButton ModBoton;
    private javax.swing.JTable TableEquipos;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
