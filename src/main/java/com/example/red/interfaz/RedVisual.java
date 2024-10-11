package com.example.red.interfaz;

import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

import com.example.red.modelo.Equipo;
import com.example.red.modelo.Conexion;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RedVisual extends JFrame {

    // Para renderizado
    private mxGraph redVisual = new mxGraph() {
        // Deshabilitar la edición de todos los labels
        @Override
        public boolean isCellEditable(Object cell) { return false; }

        // Permitir solo el movimiento de nodos y arcos
        @Override
        public boolean isCellsMovable() { return true; }

        // No permitir desconectar aristas de nodos
        @Override
        public boolean isCellDisconnectable(Object cell, Object terminal, boolean source) { return false; }

        // No permitir doblar las aristas (mover sus puntos de control)
        @Override
        public boolean isCellBendable(Object cell) { return false; }

        // Habilitar el redimensionamiento de los nodos
        @Override
        public boolean isCellResizable(Object cell) { return true; };
    };
    private mxGraphComponent graphComponent = new mxGraphComponent(redVisual);
    private Object parent = redVisual.getDefaultParent();
    private Map<String, Object> tablaNodos = new TreeMap<>(); // Tabla "ID" -> Nodo renderizado
    private Map<String, Object> tablaArcos = new TreeMap<>(); // Tabla "ID ID" -> Arco renderizado

    // Para funcionamiento lógico
    private Map<String, Equipo> tablaEquipos = new TreeMap<>(); // Tabla "ID" -> Equipo
    private Map<String, Conexion> tablaConexiones = new TreeMap<>(); // Tabla "ID ID" -> Conexion

    // Configuraciones globales de colores y tamaños para estilos
    private String verdeClaro = "#008584";
    private String verdeOscuro = "006666";
    private String grisClaro = "#E9E9E9";
    private String grisOscuro = "#CCCCCC";
    private String highlight = "#0FB8B8";
    private String blanco = "#F5F5F5";
    private String fuente = "Sans serif";
    private int fondo = 0xFDFDFD;
    private int tamanioFuente = 16; //px
    private int tamanioFuente2 = 12; //px
    private int altoNodo = 50; //px
    private int anchoNodo = 100; //px
    private int espacioEntreNodos = 120; //px

    // Estilos 
    Map<String, Object> estiloAccessPointActivo = new TreeMap<>();
    Map<String, Object> estiloAccessPointInactivo = new TreeMap<>();
    Map<String, Object> estiloAccessPointHighlight = new TreeMap<>();
    Map<String, Object> estiloRouterActivo = new TreeMap<>();
    Map<String, Object> estiloRouterInactivo = new TreeMap<>();
    Map<String, Object> estiloRouterHighlight = new TreeMap<>();
    Map<String, Object> estiloComputadoraActivo = new TreeMap<>();
    Map<String, Object> estiloComputadoraInactivo = new TreeMap<>();
    Map<String, Object> estiloComputadoraHighlight = new TreeMap<>();
    Map<String, Object> estiloSwitchActivo = new TreeMap<>();
    Map<String, Object> estiloSwitchInactivo = new TreeMap<>();
    Map<String, Object> estiloSwitchHighlight = new TreeMap<>();
    Map<String, Object> estiloConexionActivo = new TreeMap<>();
    Map<String, Object> estiloConexionInactivo = new TreeMap<>();
    Map<String, Object> estiloConexionHighlight = new TreeMap<>();
    
    public RedVisual(){

    }

    public void cargarDatos(Map<String, Equipo> tablaEquipos, Map<String, Conexion> tablaConexiones) {
        // Iniciar cambios en la red
        redVisual.getModel().beginUpdate();
        
        // Generar los estilos basados en las configuraciones globales de estilos
        inicializarEstilos();

        // Cargar tablas de equipos y conexiones
        this.tablaEquipos = tablaEquipos;
        this.tablaConexiones = tablaConexiones;

        // Renderizar red
        renderizarRed();

        // Renderizar botones
        renderizarBotones();
    }

    public void renderizarRed(){
        try {
            // Renderizar equipos
            // Mapear los nodos en una tabla "ID"->Nodo
            for (Equipo equipo : tablaEquipos.values()){
                String id = equipo.getCodigo();
                String estilo = getEstiloNodo(id, "default");
                Object nodo = redVisual.insertVertex(parent, null, id, 0, 0, anchoNodo, altoNodo, estilo);

                tablaNodos.put(id, nodo);
            }

            // Renderizar conexiones
            // Mapear los arcos en una tabla ["ID","ID"]->Arco
		    for (Conexion conexion : tablaConexiones.values()) {
			    Equipo equipo1 = conexion.getEquipo1();
			    Equipo equipo2 = conexion.getEquipo2();

    			if (equipo1 != null || equipo2 != null) {
                    String id1 = equipo1.getCodigo();
                    String id2 = equipo2.getCodigo();
                    String estilo = getEstiloArco(id1, id2, "default");
                    String clave = id1 + " " + id2;
                    Object nodo1 = tablaNodos.get(id1);
                    Object nodo2 = tablaNodos.get(id2);
                    String label = conexion.getTipocable().getVelocidad()+" Mbps";

                    Object arco = redVisual.insertEdge(parent, null, label, nodo1, nodo2, estilo);
                    tablaArcos.put(clave, arco);
                }

		    }
        } finally {
            redVisual.getModel().endUpdate();
        }

        // Mostrar
        getContentPane().add(graphComponent);
        setTitle("Visualización del Grafo");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);

        // Color de fondo
        graphComponent.getViewport().setBackground(new Color(fondo)); 

        // Layout
        mxFastOrganicLayout layoutParent = new mxFastOrganicLayout(redVisual);
        layoutParent.setForceConstant(espacioEntreNodos);  // Espacio entre nodos
        layoutParent.execute(parent); // Aplicar layout

        // Deshabilitar edición
        graphComponent.setConnectable(false);
        graphComponent.setInvokesStopCellEditing(false);

    }

    public void renderizarBotones(){
        // Crear botones de zoom
        JButton zoomInButton = new JButton("Zoom In");
        JButton zoomOutButton = new JButton("Zoom Out");

        // Agregar listeners a los botones
                zoomInButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        graphComponent.zoomIn(); // Zoom In
                    }
                });
        
                zoomOutButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        graphComponent.zoomOut(); // Zoom Out
                    }
                });

        // Crear un panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(zoomInButton);
        buttonPanel.add(zoomOutButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Agregar listener de rueda del ratón para zoom
        graphComponent.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                // Verificar si la tecla Ctrl está presionada
                if (e.isControlDown()) {
                    if (e.getWheelRotation() < 0) {
                        graphComponent.zoomIn(); // Zoom In
                    } else {
                        graphComponent.zoomOut(); // Zoom Out
                    }
                }
            }
        });

        // Agregar listener de teclado para zoom
        graphComponent.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Verificar si la tecla Ctrl está presionada
                if (e.isControlDown()) {
                    if (e.getKeyCode() == KeyEvent.VK_PLUS || e.getKeyCode() == KeyEvent.VK_EQUALS) {
                        graphComponent.zoomIn(); // Zoom In
                    } else if (e.getKeyCode() == KeyEvent.VK_MINUS) {
                        graphComponent.zoomOut(); // Zoom Out
                    } 
                }
            }
        });
    }


    /*
     * Obtiene el estilo correspondiente a un nodo, segun el tipo de equipo y el tipo de estilo que se desea
     */
    private String getEstiloNodo(String id, String tipoEstilo){
        String estilo = "";
        Equipo equipo = tablaEquipos.get(id);

        if (equipo == null)
            return estilo;

        if (tipoEstilo.equals("default")){
            if (equipo.getTipoEquipo().getCodigo().equals("AP"))
                estilo = equipo.isActivo() ? "Access point activo" : "Access point inactivo";
            else if (equipo.getTipoEquipo().getCodigo().equals("COM"))          
                estilo = equipo.isActivo() ? "Computadora activo" : "Computadora inactivo";
            else if (equipo.getTipoEquipo().getCodigo().equals("RT"))
                estilo = equipo.isActivo() ? "Router activo" : "Router inactivo";
            else if (equipo.getTipoEquipo().getCodigo().equals("SW"))
                estilo = equipo.isActivo() ? "Switch activo" : "Switch inactivo";   

        } else if (tipoEstilo.equals("highlight")){
            if (equipo.getTipoEquipo().getCodigo().equals("AP"))
                estilo = "Access point highlight";
            else if (equipo.getTipoEquipo().getCodigo().equals("COM"))
                estilo = "Computadora highlight";
            else if (equipo.getTipoEquipo().getCodigo().equals("RT"))
                estilo = "Router highlight";
            else if (equipo.getTipoEquipo().getCodigo().equals("SW"))
                estilo = "Switch highlight";
        }

        return estilo;
    }

    private String getEstiloArco(String id1, String id2, String tipoEstilo){
        String estilo = "";
        
        if (tipoEstilo.equals("highlight"))
            estilo = "Conexion highlight";
        else if (tipoEstilo.equals("default")){
            Equipo equipo1 = tablaEquipos.get(id1);
            Equipo equipo2 = tablaEquipos.get(id2);
            estilo = (equipo1.isActivo() && equipo2.isActivo()) ? "Conexion activo" : "Conexion inactivo";
        }

        return estilo;
    }

    public void setEstiloNodo(String id, String tipoEstilo){
        redVisual.getModel().beginUpdate();
        try {
            String estilo = getEstiloNodo(id, tipoEstilo); // Obtiene el estilo correcto
            mxCell cell = (mxCell) tablaNodos.get(id); // Obtiene el nodo renderizado
            if (cell != null)
                cell.setStyle(estilo);
            redVisual.refresh();
        } finally {
            redVisual.getModel().endUpdate();
        }
    }

    public void setEstiloCaminoNodos(List<String> ids, String tipoEstilo){
        redVisual.getModel().beginUpdate();
        try {
            for (int i = 0; i < ids.size()-1; i++){
                String id1 = ids.get(i);
                String id2 = ids.get(i+1);
                String estilo = getEstiloNodo(id1, tipoEstilo); // Obtiene el estilo correcto para el nodo 1
                mxCell cell = (mxCell) tablaNodos.get(id1); // Obtiene el nodo 1 renderizado
                if (cell != null)
                    cell.setStyle(estilo); // Aplica el cambio 

                estilo = getEstiloArco(id1, id2, tipoEstilo); // Obtiene el estilo correcto para el arco
                cell = (mxCell) tablaArcos.get(id1+" "+id2); // Obtiene el arco renderizado
                if (cell == null)
                    cell = (mxCell) tablaArcos.get(id2+" "+id1);
                if (cell != null)
                    cell.setStyle(estilo); // Aplica el cambio 
                
            }
            String id = ids.get(ids.size()-1); 
            String estilo = getEstiloNodo(id, tipoEstilo); // Obtiene el estilo correcto para el ultimo nodo
            mxCell cell = (mxCell) tablaNodos.get(id); // Obtiene el ultimo nodo
            if (cell != null)
                cell.setStyle(estilo); // Aplica el cambio 

            redVisual.refresh();
        } finally {
            redVisual.getModel().endUpdate();
        }
    }

    public void setEstiloNodoTodos(String tipoEstilo){
        redVisual.getModel().beginUpdate();
        try {
            for (Equipo equipo : tablaEquipos.values()){
                String id = equipo.getCodigo();
                String estilo = getEstiloNodo(id, tipoEstilo); // Obtiene el estilo correcto
                mxCell cell = (mxCell) tablaNodos.get(id); // Obtiene el nodo renderizado
                if (cell != null)
                    cell.setStyle(estilo);
            }
            redVisual.refresh();
        } finally {
            redVisual.getModel().endUpdate();
        }
    }

    public void inicializarEstilos(){
        estiloAccessPointActivo.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CLOUD); // Forma 
        estiloAccessPointActivo.put(mxConstants.STYLE_STROKECOLOR, verdeClaro); // Color linea
        estiloAccessPointActivo.put(mxConstants.STYLE_FILLCOLOR, verdeClaro); // Fondo
        estiloAccessPointActivo.put(mxConstants.STYLE_FONTFAMILY, fuente); // Familia fuente
        estiloAccessPointActivo.put(mxConstants.STYLE_FONTCOLOR, blanco); // Color fuente
        estiloAccessPointActivo.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD); // Estilo fuente
        estiloAccessPointActivo.put(mxConstants.STYLE_FONTSIZE, tamanioFuente); // Tamaño fuente px
        estiloAccessPointActivo.put(mxConstants.STYLE_VERTICAL_LABEL_POSITION, mxConstants.ALIGN_MIDDLE); // Centrado vertical
        estiloAccessPointActivo.put(mxConstants.STYLE_SHADOW, true); // Sombra
        redVisual.getStylesheet().putCellStyle("Access point activo", estiloAccessPointActivo);

        estiloAccessPointInactivo.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CLOUD); // Forma
        estiloAccessPointInactivo.put(mxConstants.STYLE_STROKECOLOR, grisOscuro); // Color linea
        estiloAccessPointInactivo.put(mxConstants.STYLE_FILLCOLOR, grisOscuro); // Fondo
        estiloAccessPointInactivo.put(mxConstants.STYLE_FONTFAMILY, fuente); // Familia fuente
        estiloAccessPointInactivo.put(mxConstants.STYLE_FONTCOLOR, blanco); // Color fuente
        estiloAccessPointInactivo.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD); // Estilo fuente
        estiloAccessPointInactivo.put(mxConstants.STYLE_FONTSIZE, tamanioFuente); // Tamaño fuente px
        estiloAccessPointInactivo.put(mxConstants.STYLE_VERTICAL_LABEL_POSITION, mxConstants.ALIGN_MIDDLE); // Centrado vertical
        estiloAccessPointInactivo.put(mxConstants.STYLE_SHADOW, true); // Sombra
        redVisual.getStylesheet().putCellStyle("Access point inactivo", estiloAccessPointInactivo);

        estiloAccessPointHighlight.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CLOUD); // Forma
        estiloAccessPointHighlight.put(mxConstants.STYLE_STROKECOLOR, highlight); // Color linea
        estiloAccessPointHighlight.put(mxConstants.STYLE_FILLCOLOR, highlight); // Fondo
        estiloAccessPointHighlight.put(mxConstants.STYLE_FONTFAMILY, fuente); // Familia fuente
        estiloAccessPointHighlight.put(mxConstants.STYLE_FONTCOLOR, blanco); // Color fuente
        estiloAccessPointHighlight.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD); // Estilo fuente
        estiloAccessPointHighlight.put(mxConstants.STYLE_FONTSIZE, tamanioFuente); // Tamaño fuente px
        estiloAccessPointHighlight.put(mxConstants.STYLE_VERTICAL_LABEL_POSITION, mxConstants.ALIGN_MIDDLE); // Centrado vertical
        estiloAccessPointHighlight.put(mxConstants.STYLE_SHADOW, false); // Sombra
        redVisual.getStylesheet().putCellStyle("Access point highlight", estiloAccessPointHighlight);

        estiloRouterActivo.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE); // Forma 
        estiloRouterActivo.put(mxConstants.STYLE_STROKECOLOR, verdeClaro); // Color linea
        estiloRouterActivo.put(mxConstants.STYLE_FILLCOLOR, verdeClaro); // Fondo
        estiloRouterActivo.put(mxConstants.STYLE_FONTFAMILY, fuente); // Familia fuente
        estiloRouterActivo.put(mxConstants.STYLE_FONTCOLOR, blanco); // Color fuente
        estiloRouterActivo.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD); // Estilo fuente
        estiloRouterActivo.put(mxConstants.STYLE_FONTSIZE, tamanioFuente); // Tamaño fuente px
        estiloRouterActivo.put(mxConstants.STYLE_VERTICAL_LABEL_POSITION, mxConstants.ALIGN_MIDDLE); // Centrado vertical
        estiloRouterActivo.put(mxConstants.STYLE_SHADOW, true); // Sombra
        redVisual.getStylesheet().putCellStyle("Router activo", estiloRouterActivo);

        estiloRouterInactivo.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE); // Forma
        estiloRouterInactivo.put(mxConstants.STYLE_STROKECOLOR, grisOscuro); // Color linea
        estiloRouterInactivo.put(mxConstants.STYLE_FILLCOLOR, grisOscuro); // Fondo
        estiloRouterInactivo.put(mxConstants.STYLE_FONTFAMILY, fuente); // Familia fuente
        estiloRouterInactivo.put(mxConstants.STYLE_FONTCOLOR, blanco); // Color fuente
        estiloRouterInactivo.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD); // Estilo fuente
        estiloRouterInactivo.put(mxConstants.STYLE_FONTSIZE, tamanioFuente); // Tamaño fuente px
        estiloRouterInactivo.put(mxConstants.STYLE_VERTICAL_LABEL_POSITION, mxConstants.ALIGN_MIDDLE); // Centrado vertical
        estiloRouterInactivo.put(mxConstants.STYLE_SHADOW, true); // Sombra
        redVisual.getStylesheet().putCellStyle("Router inactivo", estiloRouterInactivo);

        estiloRouterHighlight.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE); // Forma
        estiloRouterHighlight.put(mxConstants.STYLE_STROKECOLOR, highlight); // Color linea
        estiloRouterHighlight.put(mxConstants.STYLE_FILLCOLOR, highlight); // Fondo
        estiloRouterHighlight.put(mxConstants.STYLE_FONTFAMILY, fuente); // Familia fuente
        estiloRouterHighlight.put(mxConstants.STYLE_FONTCOLOR, blanco); // Color fuente
        estiloRouterHighlight.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD); // Estilo fuente
        estiloRouterHighlight.put(mxConstants.STYLE_FONTSIZE, tamanioFuente); // Tamaño fuente px
        estiloRouterHighlight.put(mxConstants.STYLE_VERTICAL_LABEL_POSITION, mxConstants.ALIGN_MIDDLE); // Centrado vertical
        estiloRouterHighlight.put(mxConstants.STYLE_SHADOW, false); // Sombra
        redVisual.getStylesheet().putCellStyle("Router highlight", estiloRouterHighlight);

        estiloComputadoraActivo.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE); // Forma 
        estiloComputadoraActivo.put(mxConstants.STYLE_STROKECOLOR, verdeClaro); // Color linea
        estiloComputadoraActivo.put(mxConstants.STYLE_FILLCOLOR, verdeClaro); // Fondo
        estiloComputadoraActivo.put(mxConstants.STYLE_FONTFAMILY, fuente); // Familia fuente
        estiloComputadoraActivo.put(mxConstants.STYLE_FONTCOLOR, blanco); // Color fuente
        estiloComputadoraActivo.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD); // Estilo fuente
        estiloComputadoraActivo.put(mxConstants.STYLE_FONTSIZE, tamanioFuente); // Tamaño fuente px
        estiloComputadoraActivo.put(mxConstants.STYLE_VERTICAL_LABEL_POSITION, mxConstants.ALIGN_MIDDLE); // Centrado vertical
        estiloComputadoraActivo.put(mxConstants.STYLE_SHADOW, true); // Sombra
        redVisual.getStylesheet().putCellStyle("Computadora activo", estiloComputadoraActivo);

        estiloComputadoraInactivo.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE); // Forma
        estiloComputadoraInactivo.put(mxConstants.STYLE_STROKECOLOR, grisOscuro); // Color linea
        estiloComputadoraInactivo.put(mxConstants.STYLE_FILLCOLOR, grisOscuro); // Fondo
        estiloComputadoraInactivo.put(mxConstants.STYLE_FONTFAMILY, fuente); // Familia fuente
        estiloComputadoraInactivo.put(mxConstants.STYLE_FONTCOLOR, blanco); // Color fuente
        estiloComputadoraInactivo.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD); // Estilo fuente
        estiloComputadoraInactivo.put(mxConstants.STYLE_FONTSIZE, tamanioFuente); // Tamaño fuente px
        estiloComputadoraInactivo.put(mxConstants.STYLE_VERTICAL_LABEL_POSITION, mxConstants.ALIGN_MIDDLE); // Centrado vertical
        estiloComputadoraInactivo.put(mxConstants.STYLE_SHADOW, true); // Sombra
        redVisual.getStylesheet().putCellStyle("Computadora inactivo", estiloComputadoraInactivo);

        estiloComputadoraHighlight.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE); // Forma
        estiloComputadoraHighlight.put(mxConstants.STYLE_STROKECOLOR, highlight); // Color linea
        estiloComputadoraHighlight.put(mxConstants.STYLE_FILLCOLOR, highlight); // Fondo
        estiloComputadoraHighlight.put(mxConstants.STYLE_FONTFAMILY, fuente); // Familia fuente
        estiloComputadoraHighlight.put(mxConstants.STYLE_FONTCOLOR, blanco); // Color fuente
        estiloComputadoraHighlight.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD); // Estilo fuente
        estiloComputadoraHighlight.put(mxConstants.STYLE_FONTSIZE, tamanioFuente); // Tamaño fuente px
        estiloComputadoraHighlight.put(mxConstants.STYLE_VERTICAL_LABEL_POSITION, mxConstants.ALIGN_MIDDLE); // Centrado vertical
        estiloComputadoraHighlight.put(mxConstants.STYLE_SHADOW, false); // Sombra
        redVisual.getStylesheet().putCellStyle("Computadora highlight", estiloComputadoraHighlight);

        estiloSwitchActivo.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RHOMBUS); // Forma 
        estiloSwitchActivo.put(mxConstants.STYLE_STROKECOLOR, verdeClaro); // Color linea
        estiloSwitchActivo.put(mxConstants.STYLE_FILLCOLOR, verdeClaro); // Fondo
        estiloSwitchActivo.put(mxConstants.STYLE_FONTFAMILY, fuente); // Familia fuente
        estiloSwitchActivo.put(mxConstants.STYLE_FONTCOLOR, blanco); // Color fuente
        estiloSwitchActivo.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD); // Estilo fuente
        estiloSwitchActivo.put(mxConstants.STYLE_FONTSIZE, tamanioFuente); // Tamaño fuente px
        estiloSwitchActivo.put(mxConstants.STYLE_VERTICAL_LABEL_POSITION, mxConstants.ALIGN_MIDDLE); // Centrado vertical
        estiloSwitchActivo.put(mxConstants.STYLE_SHADOW, true); // Sombra
        redVisual.getStylesheet().putCellStyle("Switch activo", estiloSwitchActivo);

        estiloSwitchInactivo.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RHOMBUS); // Forma 
        estiloSwitchInactivo.put(mxConstants.STYLE_STROKECOLOR, grisOscuro); // Color linea
        estiloSwitchInactivo.put(mxConstants.STYLE_FILLCOLOR, grisOscuro); // Fondo
        estiloSwitchInactivo.put(mxConstants.STYLE_FONTFAMILY, fuente); // Familia fuente
        estiloSwitchInactivo.put(mxConstants.STYLE_FONTCOLOR, blanco); // Color fuente
        estiloSwitchInactivo.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD); // Estilo fuente
        estiloSwitchInactivo.put(mxConstants.STYLE_FONTSIZE, tamanioFuente); // Tamaño fuente px
        estiloSwitchInactivo.put(mxConstants.STYLE_VERTICAL_LABEL_POSITION, mxConstants.ALIGN_MIDDLE); // Centrado vertical
        estiloSwitchInactivo.put(mxConstants.STYLE_SHADOW, true); // Sombra
        redVisual.getStylesheet().putCellStyle("Switch inactivo", estiloSwitchInactivo);
        
        estiloSwitchHighlight.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RHOMBUS); // Forma
        estiloSwitchHighlight.put(mxConstants.STYLE_STROKECOLOR, highlight); // Color linea
        estiloSwitchHighlight.put(mxConstants.STYLE_FILLCOLOR, highlight); // Fondo
        estiloSwitchHighlight.put(mxConstants.STYLE_FONTFAMILY, fuente); // Familia fuente
        estiloSwitchHighlight.put(mxConstants.STYLE_FONTCOLOR, blanco); // Color fuente
        estiloSwitchHighlight.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD); // Estilo fuente
        estiloSwitchHighlight.put(mxConstants.STYLE_FONTSIZE, tamanioFuente); // Tamaño fuente px
        estiloSwitchHighlight.put(mxConstants.STYLE_VERTICAL_LABEL_POSITION, mxConstants.ALIGN_MIDDLE); // Centrado vertical
        estiloSwitchHighlight.put(mxConstants.STYLE_SHADOW, false); // Sombra
        redVisual.getStylesheet().putCellStyle("Switch highlight", estiloSwitchHighlight);

        estiloConexionActivo.put(mxConstants.STYLE_ENDARROW, mxConstants.NONE); // Sin punta
        estiloConexionActivo.put(mxConstants.STYLE_STROKECOLOR, verdeClaro); // Color linea
        estiloConexionActivo.put(mxConstants.STYLE_FONTFAMILY, fuente); // Familia fuente
        estiloConexionActivo.put(mxConstants.STYLE_FONTCOLOR, verdeClaro); // Color fuente
        estiloConexionActivo.put(mxConstants.STYLE_FONTSIZE, tamanioFuente2); // Tamaño fuente
        estiloConexionActivo.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD); // Estilo fuente
        redVisual.getStylesheet().putCellStyle("Conexion activo", estiloConexionActivo);

        estiloConexionInactivo.put(mxConstants.STYLE_ENDARROW, mxConstants.NONE); // Sin punta
        estiloConexionInactivo.put(mxConstants.STYLE_STROKECOLOR, grisOscuro); // Color linea
        estiloConexionInactivo.put(mxConstants.STYLE_FONTFAMILY, fuente); // Familia fuente
        estiloConexionInactivo.put(mxConstants.STYLE_FONTCOLOR, grisOscuro); // Color fuente
        estiloConexionInactivo.put(mxConstants.STYLE_FONTSIZE, tamanioFuente2); // Tamaño fuente
        estiloConexionInactivo.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD); // Estilo fuente
        redVisual.getStylesheet().putCellStyle("Conexion inactivo", estiloConexionInactivo);

        estiloConexionHighlight.put(mxConstants.STYLE_ENDARROW, mxConstants.NONE); // Sin punta
        estiloConexionHighlight.put(mxConstants.STYLE_STROKECOLOR, highlight); // Color linea
        estiloConexionHighlight.put(mxConstants.STYLE_FONTFAMILY, fuente); // Familia fuente
        estiloConexionHighlight.put(mxConstants.STYLE_FONTCOLOR, highlight); // Color fuente
        estiloConexionHighlight.put(mxConstants.STYLE_FONTSIZE, tamanioFuente2); // Tamaño fuente
        estiloConexionHighlight.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD); // Estilo fuente
        redVisual.getStylesheet().putCellStyle("Conexion highlight", estiloConexionHighlight);
    }
}