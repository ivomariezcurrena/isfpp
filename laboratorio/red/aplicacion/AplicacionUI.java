package red.aplicacion;

import javax.swing.SwingUtilities;

import red.interfaz.InterfazGrafica;

public class AplicacionUI {
      // Metodo principal para iniciar la aplicacion
      public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfazGrafica());
    }
}
