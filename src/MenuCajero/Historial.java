
package MenuCajero;

import Modelo.MoldeProductos;
import java.awt.CardLayout;
import java.awt.Component;
import java.time.LocalTime;


public class Historial extends javax.swing.JPanel {

    private CardLayout cardLayout;
    // CACHÉ EN RAM: Almacena los productos por categoría para NO volver a consultar MySQL
    private static final java.util.Map<Integer, java.util.List<Modelo.Producto>> CACHE_PRODUCTOS_BD = new java.util.HashMap<>();
    
    private Componentes.PanelDetalleProducto panelDetalle = new Componentes.PanelDetalleProducto();

    // Instancia única del DAO
    private final Conexion.ProductoDAO productoDAO = new Conexion.ProductoDAO();
    private final Conexion.PagoDAO pagoDAO = new Conexion.PagoDAO();

    public Historial() {
        initComponents();
        construirDisenoHistorial();
        cargarHistorialDesdeBD();
        actualizarResumenVentas();
        precargarTodasLasImagenes(); // Inicia la carga transparente en segundo plano
        jScrollPane1.getViewport().setOpaque(false);
        jScrollPane1.setOpaque(false);
     
        
        
        jScrollPane3.getViewport().setOpaque(false);
        jScrollPane3.setOpaque(false);
    
     
        // Conectar el botón del panel derecho con el carrito
        panelDetalle.getBtnAgregarCarrito().addActionListener(e -> {
            if (panelDetalle.getProductoActual() != null) {
                agregarProductoAOrden(panelDetalle.getProductoActual());
            }
        });

        
    

          

            // 2. Definir tamaño fijo para cada botón
            java.awt.Dimension tamanoBoton = new java.awt.Dimension(160, 45);
            int anchoTotal = 0;
            int espacioEntreBotones = 10;



            

        
            panelDetalle.limpiarListenersAgregar();

// 2. Asignar la acción una sola vez
panelDetalle.getBtnAgregarCarrito().addActionListener(e -> {
    Modelo.Producto p = panelDetalle.getProductoActual();
    if (p != null) {
        int cant = panelDetalle.getCantidad();
        double extraTamano = panelDetalle.getCostoTamanoExtra();

        // Construir el objeto ajustado según la selección de tamaño
        Modelo.Producto productoAjustado = new Modelo.Producto();
        productoAjustado.setNombre(p.getNombre() + (extraTamano > 0 ? " (Agrandado)" : ""));
        productoAjustado.setPrecio(p.getPrecio() + extraTamano);
        productoAjustado.setImagenPath(p.getImagenPath());

        // Agregar al carrito exactamente la cantidad indicada en el contador (- 1 +)
        for (int i = 0; i < cant; i++) {
            agregarProductoAOrden(productoAjustado);
        }
    }
});
            
    }
    
    
    

    
    
    // Elimina todos los elementos del carrito y reinicia los totales
public void limpiarOrden() {
    jPanel3.removeAll(); // Elimina todos los ItemOrden de la lista visual
    jPanel3.revalidate();
    jPanel3.repaint();
    
    // Recalcula los totales para dejar todos los labels en Q 0.00
    recalcularSubtotal();
}

public void actualizarDetalleDerecho(Modelo.Producto p) {
    panelDetalle.mostrarProducto(p);
}

// Devuelve el id_categoria correspondiente al horario actual
private int obtenerCategoriaSegunHora() {
    LocalTime horaActual = LocalTime.now();
    int hora = horaActual.getHour();

    // Horario de Desayunos: 07:00 AM a 10:59 AM (IDs según tu base de datos)
    if (hora >= 7 && hora < 11) {
        return 4; // ID 4: Desayunos
    } 
    // Horario de Almuerzos/Cenas: Resto del día (11:00 AM a 06:59 AM)
    else {
        return 1; // ID 1: Almuerzos
    }
}

// Precarga ULTRA RÁPIDA con Pool de 4 Hilos en paralelo
private void precargarTodasLasImagenes() {
    new Thread(() -> {
        java.util.concurrent.ExecutorService executor = java.util.concurrent.Executors.newFixedThreadPool(4); // 4 hilos paralelos
        
        for (int idCat = 1; idCat <= 8; idCat++) {
            final int categoriaActual = idCat;
            executor.submit(() -> {
                try {
                    java.util.List<Modelo.Producto> productos;
                    synchronized (CACHE_PRODUCTOS_BD) {
                        if (!CACHE_PRODUCTOS_BD.containsKey(categoriaActual)) {
                            productos = productoDAO.obtenerProductosPorCategoria(categoriaActual);
                            CACHE_PRODUCTOS_BD.put(categoriaActual, productos);
                        } else {
                            productos = CACHE_PRODUCTOS_BD.get(categoriaActual);
                        }
                    }

                    for (Modelo.Producto p : productos) {
                        String ruta = p.getImagenPath();
                        if (ruta != null && !ruta.trim().isEmpty()) {
                            String rutaLimpia = ruta.trim();
                            if (!MoldeProductos.existeEnCache(rutaLimpia)) {
                                java.io.File archivo = new java.io.File(rutaLimpia);
                                if (archivo.exists()) {
                                    javax.swing.ImageIcon icono = MoldeProductos.escalarImagenRapida(archivo, 175, 125);
                                    if (icono != null) {
                                        MoldeProductos.guardarEnCache(rutaLimpia, icono);
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown(); // Cierra los hilos al finalizar
    }).start();
}


    


private void agregarProductoAOrden(Modelo.Producto producto) {
   // 1. Si el producto ya está marcado como no disponible en BD, bloquear
    if (!producto.isDisponible()) {
        javax.swing.JOptionPane.showMessageDialog(
            this,
            "Este producto no está disponible en el menú.",
            "Producto Agotado",
            javax.swing.JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    // 2. Contar cuántas unidades de este mismo producto ya existen en el carrito (jPanel3)
    int cantidadEnCarrito = 0;
    for (java.awt.Component c : jPanel3.getComponents()) {
        if (c instanceof Componentes.ItemOrden) {
            Componentes.ItemOrden itemExistente = (Componentes.ItemOrden) c;
            if (itemExistente.getIdProducto() == producto.getIdProducto()) {
                cantidadEnCarrito += itemExistente.getCantidad();
            }
        }
    }

    // 3. Límite de 1 unidad según estado de disponibilidad
    if (cantidadEnCarrito >= 1) {
        javax.swing.JOptionPane.showMessageDialog(
            this,
            "No hay más unidades disponibles de este producto.",
            "Límite Alcanzado",
            javax.swing.JOptionPane.WARNING_MESSAGE
        );
        return; // Bloquea la adición
    }

    // 4. Si aún no está en el carrito, proceder a agregarlo
    java.awt.Image img = null;
    String ruta = producto.getImagenPath();

    if (ruta != null && !ruta.trim().isEmpty()) {
        String rutaLimpia = ruta.trim();
        if (MoldeProductos.existeEnCache(rutaLimpia)) {
            img = MoldeProductos.obtenerDeCache(rutaLimpia).getImage();
        } else {
            java.io.File archivo = new java.io.File(rutaLimpia);
            if (archivo.exists()) {
                javax.swing.ImageIcon icon = MoldeProductos.escalarImagenRapida(archivo, 175, 125);
                if (icon != null) img = icon.getImage();
            }
        }
    }

    Componentes.ItemOrden item = new Componentes.ItemOrden(
            producto.getIdProducto(),
            producto.getNombre(),
            producto.getPrecio(),
            img,
            producto.getImagenPath(),
            () -> recalcularSubtotal()
    );

    jPanel3.add(item);
    jPanel3.revalidate();
    jPanel3.repaint();

    recalcularSubtotal();
}

// Recorre los ítems agregados y actualiza Subtotal, IVA (12%) y Total
private void recalcularSubtotal() {
    double sumaTotalProductos = 0.0;
    
    // 1. Sumar el total de los productos añadidos a la orden
    for (Component c : jPanel3.getComponents()) {
        if (c instanceof Componentes.ItemOrden) {
            sumaTotalProductos += ((Componentes.ItemOrden) c).getSubtotal();
        }
    }
    
    // 2. Cálculo matemáticamente exacto para desglose de IVA 12%
    // El subtotal base sin impuesto es: Total / 1.12
    double subtotalBase = sumaTotalProductos / 1.12;
    // El IVA (12%) es la diferencia entre el Total y la base sin impuesto
    double impuestoIVA = sumaTotalProductos - subtotalBase;

    // 3. Actualizar los Labels en pantalla formateados a 2 decimales
    jLabel6.setText(String.format("Q %.2f", subtotalBase));       // Sub Total (sin IVA)
    jLabel3.setText(String.format("Q %.2f", impuestoIVA));        // Impuesto (12%)
    subtotal.setText(String.format("Q %.2f", sumaTotalProductos));  // TOTAL (rojo grande)
}

    /** Obtiene una copia estable de las líneas visibles del carrito. */
    public java.util.List<Modelo.ItemPedido> getPedidoActual() {
        java.util.List<Modelo.ItemPedido> pedido = new java.util.ArrayList<>();
        for (Component c : jPanel3.getComponents()) {
            if (c instanceof Componentes.ItemOrden) {
                Componentes.ItemOrden item = (Componentes.ItemOrden) c;
                pedido.add(new Modelo.ItemPedido(
                        item.getIdProducto(),
                        item.getNombreProducto(),
                        item.getPrecioUnitario(),
                        item.getCantidad(),
                        item.getImagenPath()
                ));
            }
        }
        return pedido;
    }

    public final void cargarHistorialDesdeBD() {
        historialCompleto = pagoDAO.obtenerHistorialCajero();

        tablaHistorial1.limpiarTabla();

        for (Object[] fila : historialCompleto) {
            tablaHistorial1.agregarFila(
                    String.valueOf(fila[0]),
                    String.valueOf(fila[1]),
                    String.valueOf(fila[2]),
                    String.valueOf(fila[3]),
                    String.valueOf(fila[4]),
                    String.valueOf(fila[5])
            );
        }

        tablaHistorial1.revalidate();
        tablaHistorial1.repaint();
        actualizarResumenVentas();
    }


    // ============================================================
    // DISEÑO NUEVO DEL HISTORIAL
    // Se construye en tiempo de ejecución para no romper el .form.
    // ============================================================

    private javax.swing.JTextField txtBuscarHistorial;
    private javax.swing.JCheckBox chkEfectivo;
    private javax.swing.JCheckBox chkTarjeta;
    private javax.swing.JCheckBox chkQR;
    private javax.swing.JCheckBox chkCompletadas;
    private javax.swing.JCheckBox chkCanceladas;

    private javax.swing.JLabel lblVentasTotales;
    private javax.swing.JLabel lblPedidos;
    private javax.swing.JLabel lblTicketPromedio;
    private javax.swing.JLabel lblCancelaciones;

    private javax.swing.JLabel lblResumenEfectivo;
    private javax.swing.JLabel lblResumenTarjeta;
    private javax.swing.JLabel lblResumenQR;

    private javax.swing.JLabel lblUltimoPedido;
    private javax.swing.JLabel lblUltimoTotal;
    private javax.swing.JLabel lblUltimaHora;
    private javax.swing.JLabel lblUltimoEstado;

    private java.util.List<Object[]> historialCompleto = new java.util.ArrayList<>();

    private final java.awt.Color ROJO_WC = new java.awt.Color(190, 0, 18);
    private final java.awt.Color AMARILLO_WC = new java.awt.Color(255, 188, 13);
    private final java.awt.Color TEXTO_AZUL = new java.awt.Color(28, 48, 78);
    private final java.awt.Color BORDE_SUAVE = new java.awt.Color(224, 230, 237);
    private final java.awt.Color FONDO_SUAVE = new java.awt.Color(248, 250, 252);

    private void construirDisenoHistorial() {
        removeAll();
        setLayout(new java.awt.BorderLayout());
        setBackground(new java.awt.Color(245, 247, 250));

        javax.swing.JPanel raiz = new javax.swing.JPanel(new java.awt.BorderLayout(10, 0));
        raiz.setBackground(new java.awt.Color(245, 247, 250));
        raiz.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 14, 14, 14));

        raiz.add(crearPanelFiltros(), java.awt.BorderLayout.WEST);
        raiz.add(crearPanelCentroHistorial(), java.awt.BorderLayout.CENTER);
        raiz.add(crearPanelResumen(), java.awt.BorderLayout.EAST);

        add(raiz, java.awt.BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private javax.swing.JPanel crearPanelFiltros() {
        javax.swing.JPanel panel = crearTarjeta();
        panel.setPreferredSize(new java.awt.Dimension(260, 0));
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
        panel.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(BORDE_SUAVE),
                javax.swing.BorderFactory.createEmptyBorder(16, 14, 16, 14)));

        panel.add(crearTituloSeccion("▣  Filtros de ventas",
                "Refina la búsqueda de tus transacciones"));
        panel.add(javax.swing.Box.createVerticalStrut(12));

        txtBuscarHistorial = new javax.swing.JTextField();
        txtBuscarHistorial.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        txtBuscarHistorial.setForeground(TEXTO_AZUL);
        txtBuscarHistorial.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(BORDE_SUAVE),
                javax.swing.BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        txtBuscarHistorial.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 38));
        txtBuscarHistorial.setPreferredSize(new java.awt.Dimension(220, 38));
        txtBuscarHistorial.setToolTipText("Buscar pedido o cliente");
        panel.add(txtBuscarHistorial);

        javax.swing.event.DocumentListener buscador = new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { aplicarFiltros(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { aplicarFiltros(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { aplicarFiltros(); }
        };
        txtBuscarHistorial.getDocument().addDocumentListener(buscador);

        panel.add(javax.swing.Box.createVerticalStrut(18));
        panel.add(crearSubtitulo("▣  Rango de fechas"));
        panel.add(javax.swing.Box.createVerticalStrut(8));

        javax.swing.JPanel fechas1 = new javax.swing.JPanel(new java.awt.GridLayout(1, 2, 8, 0));
        fechas1.setOpaque(false);
        fechas1.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 34));
        fechas1.add(crearBotonFiltro("Hoy", true));
        fechas1.add(crearBotonFiltro("Ayer", false));
        panel.add(fechas1);

        panel.add(javax.swing.Box.createVerticalStrut(8));

        javax.swing.JPanel fechas2 = new javax.swing.JPanel(new java.awt.GridLayout(1, 2, 8, 0));
        fechas2.setOpaque(false);
        fechas2.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 34));
        fechas2.add(crearBotonFiltro("Esta semana", false));
        fechas2.add(crearBotonFiltro("Este mes", false));
        panel.add(fechas2);

        panel.add(javax.swing.Box.createVerticalStrut(20));
        panel.add(crearSeparador());
        panel.add(javax.swing.Box.createVerticalStrut(12));
        panel.add(crearSubtitulo("▣  Método de pago"));
        panel.add(javax.swing.Box.createVerticalStrut(8));

        chkEfectivo = crearCheck("Efectivo");
        chkTarjeta = crearCheck("Tarjeta");
        chkQR = crearCheck("QR");
        panel.add(chkEfectivo);
        panel.add(chkTarjeta);
        panel.add(chkQR);

        panel.add(javax.swing.Box.createVerticalStrut(14));
        panel.add(crearSeparador());
        panel.add(javax.swing.Box.createVerticalStrut(12));
        panel.add(crearSubtitulo("◉  Estado del pedido"));
        panel.add(javax.swing.Box.createVerticalStrut(8));

        chkCompletadas = crearCheck("Completadas");
        chkCanceladas = crearCheck("Canceladas");
        panel.add(chkCompletadas);
        panel.add(chkCanceladas);

        java.awt.event.ActionListener filtroListener = e -> aplicarFiltros();
        chkEfectivo.addActionListener(filtroListener);
        chkTarjeta.addActionListener(filtroListener);
        chkQR.addActionListener(filtroListener);
        chkCompletadas.addActionListener(filtroListener);
        chkCanceladas.addActionListener(filtroListener);

        panel.add(javax.swing.Box.createVerticalGlue());

        javax.swing.JButton limpiar = new javax.swing.JButton("Limpiar filtros");
        limpiar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        limpiar.setForeground(ROJO_WC);
        limpiar.setBackground(java.awt.Color.WHITE);
        limpiar.setFocusPainted(false);
        limpiar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        limpiar.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 38));
        limpiar.setPreferredSize(new java.awt.Dimension(220, 38));
        limpiar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(238, 65, 75)));
        limpiar.addActionListener(e -> limpiarFiltros());
        panel.add(limpiar);

        return panel;
    }

    private javax.swing.JPanel crearPanelCentroHistorial() {
        javax.swing.JPanel centro = new javax.swing.JPanel(new java.awt.BorderLayout(0, 12));
        centro.setOpaque(false);

        javax.swing.JPanel cabecera = crearTarjeta();
        cabecera.setLayout(new javax.swing.BoxLayout(cabecera, javax.swing.BoxLayout.Y_AXIS));
        cabecera.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(BORDE_SUAVE),
                javax.swing.BorderFactory.createEmptyBorder(14, 14, 14, 14)));

        javax.swing.JLabel titulo = new javax.swing.JLabel("Mis ventas");
        titulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 20));
        titulo.setForeground(TEXTO_AZUL);
        titulo.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        cabecera.add(titulo);

        javax.swing.JLabel sub = new javax.swing.JLabel("Consulta las transacciones realizadas durante tu turno");
        sub.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        sub.setForeground(new java.awt.Color(94, 113, 140));
        sub.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        cabecera.add(sub);
        cabecera.add(javax.swing.Box.createVerticalStrut(12));

        javax.swing.JPanel tarjetas = new javax.swing.JPanel(new java.awt.GridLayout(1, 4, 10, 0));
        tarjetas.setOpaque(false);
        tarjetas.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        tarjetas.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 78));

        lblVentasTotales = new javax.swing.JLabel("Q 0.00");
        lblPedidos = new javax.swing.JLabel("0");
        lblTicketPromedio = new javax.swing.JLabel("Q 0.00");
        lblCancelaciones = new javax.swing.JLabel("0");

        tarjetas.add(crearTarjetaMetrica("Ventas totales", lblVentasTotales, "$"));
        tarjetas.add(crearTarjetaMetrica("Pedidos", lblPedidos, "▣"));
        tarjetas.add(crearTarjetaMetrica("Ticket promedio", lblTicketPromedio, "="));
        tarjetas.add(crearTarjetaMetrica("Cancelaciones", lblCancelaciones, "×"));

        cabecera.add(tarjetas);
        centro.add(cabecera, java.awt.BorderLayout.NORTH);

        javax.swing.JPanel tablaCard = crearTarjeta();
        tablaCard.setLayout(new java.awt.BorderLayout());
        tablaCard.setBorder(javax.swing.BorderFactory.createLineBorder(BORDE_SUAVE));

        javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(tablaHistorial1);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(java.awt.Color.WHITE);
        scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        tablaCard.add(scroll, java.awt.BorderLayout.CENTER);

        centro.add(tablaCard, java.awt.BorderLayout.CENTER);
        return centro;
    }

    private javax.swing.JPanel crearPanelResumen() {
        javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.setOpaque(false);
        panel.setPreferredSize(new java.awt.Dimension(275, 0));
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));

        javax.swing.JPanel resumen = crearTarjeta();
        resumen.setLayout(new javax.swing.BoxLayout(resumen, javax.swing.BoxLayout.Y_AXIS));
        resumen.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(BORDE_SUAVE),
                javax.swing.BorderFactory.createEmptyBorder(14, 14, 14, 14)));
        resumen.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 300));

        resumen.add(crearTituloSeccion("▣  Resumen de ventas", "Ventas por método de pago"));
        resumen.add(javax.swing.Box.createVerticalStrut(12));

        lblResumenEfectivo = crearValorResumen("Efectivo");
        lblResumenTarjeta = crearValorResumen("Tarjeta");
        lblResumenQR = crearValorResumen("QR");

        resumen.add(crearFilaResumen("$", "Efectivo", lblResumenEfectivo));
        resumen.add(crearFilaResumen("▣", "Tarjeta", lblResumenTarjeta));
        resumen.add(crearFilaResumen("QR", "QR", lblResumenQR));

        panel.add(resumen);
        panel.add(javax.swing.Box.createVerticalStrut(10));

        javax.swing.JPanel ultima = crearTarjeta();
        ultima.setLayout(new javax.swing.BoxLayout(ultima, javax.swing.BoxLayout.Y_AXIS));
        ultima.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(BORDE_SUAVE),
                javax.swing.BorderFactory.createEmptyBorder(14, 14, 14, 14)));
        ultima.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 210));

        ultima.add(crearSubtitulo("▣  Última transacción"));
        ultima.add(javax.swing.Box.createVerticalStrut(10));

        lblUltimoPedido = crearDatoUltimo();
        lblUltimoTotal = crearDatoUltimo();
        lblUltimaHora = crearDatoUltimo();
        lblUltimoEstado = crearDatoUltimo();

        ultima.add(crearLineaDato("Pedido", lblUltimoPedido));
        ultima.add(crearLineaDato("Total", lblUltimoTotal));
        ultima.add(crearLineaDato("Hora", lblUltimaHora));
        ultima.add(crearLineaDato("Estado", lblUltimoEstado));

        ultima.add(javax.swing.Box.createVerticalStrut(10));

        javax.swing.JButton comprobante = new javax.swing.JButton("Ver comprobante");
        comprobante.setForeground(ROJO_WC);
        comprobante.setBackground(java.awt.Color.WHITE);
        comprobante.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11));
        comprobante.setFocusPainted(false);
        comprobante.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 34));
        comprobante.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(238, 65, 75)));
        ultima.add(comprobante);

        panel.add(ultima);
        panel.add(javax.swing.Box.createVerticalStrut(10));

        javax.swing.JButton exportar = new javax.swing.JButton("Exportar mi historial");
        exportar.setForeground(java.awt.Color.WHITE);
        exportar.setBackground(ROJO_WC);
        exportar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        exportar.setFocusPainted(false);
        exportar.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 40));
        exportar.setPreferredSize(new java.awt.Dimension(260, 40));
        exportar.setBorderPainted(false);
        exportar.addActionListener(e -> javax.swing.JOptionPane.showMessageDialog(
                this,
                "La exportación puede conectarse después a PDF o CSV.",
                "Exportar historial",
                javax.swing.JOptionPane.INFORMATION_MESSAGE));
        panel.add(exportar);

        panel.add(javax.swing.Box.createVerticalGlue());
        return panel;
    }

    private javax.swing.JPanel crearTarjeta() {
        javax.swing.JPanel p = new javax.swing.JPanel();
        p.setBackground(java.awt.Color.WHITE);
        return p;
    }

    private javax.swing.JPanel crearTituloSeccion(String titulo, String subtitulo) {
        javax.swing.JPanel p = new javax.swing.JPanel();
        p.setOpaque(false);
        p.setLayout(new javax.swing.BoxLayout(p, javax.swing.BoxLayout.Y_AXIS));
        p.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        javax.swing.JLabel t = new javax.swing.JLabel(titulo);
        t.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        t.setForeground(TEXTO_AZUL);
        t.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        javax.swing.JLabel s = new javax.swing.JLabel(subtitulo);
        s.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 10));
        s.setForeground(new java.awt.Color(102, 121, 146));
        s.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        p.add(t);
        p.add(javax.swing.Box.createVerticalStrut(2));
        p.add(s);
        return p;
    }

    private javax.swing.JLabel crearSubtitulo(String texto) {
        javax.swing.JLabel l = new javax.swing.JLabel(texto);
        l.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        l.setForeground(TEXTO_AZUL);
        l.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        return l;
    }

    private javax.swing.JSeparator crearSeparador() {
        javax.swing.JSeparator s = new javax.swing.JSeparator();
        s.setForeground(BORDE_SUAVE);
        s.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 1));
        return s;
    }

    private javax.swing.JButton crearBotonFiltro(String texto, boolean activo) {
        javax.swing.JButton b = new javax.swing.JButton(texto);
        b.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 10));
        b.setFocusPainted(false);
        b.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        b.setBackground(activo ? AMARILLO_WC : java.awt.Color.WHITE);
        b.setForeground(TEXTO_AZUL);
        b.setBorder(javax.swing.BorderFactory.createLineBorder(
                activo ? AMARILLO_WC : BORDE_SUAVE));
        return b;
    }

    private javax.swing.JCheckBox crearCheck(String texto) {
        javax.swing.JCheckBox c = new javax.swing.JCheckBox(texto);
        c.setOpaque(false);
        c.setForeground(new java.awt.Color(66, 86, 115));
        c.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 11));
        c.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        c.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 28));
        return c;
    }

    private javax.swing.JPanel crearTarjetaMetrica(String titulo, javax.swing.JLabel valor, String icono) {
        javax.swing.JPanel p = new javax.swing.JPanel(new java.awt.BorderLayout(8, 0));
        p.setBackground(new java.awt.Color(252, 253, 254));
        p.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(BORDE_SUAVE),
                javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        javax.swing.JLabel ico = new javax.swing.JLabel(icono, javax.swing.SwingConstants.CENTER);
        ico.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 17));
        ico.setForeground(ROJO_WC);
        ico.setOpaque(true);
        ico.setBackground(new java.awt.Color(255, 238, 213));
        ico.setPreferredSize(new java.awt.Dimension(38, 38));

        javax.swing.JPanel textos = new javax.swing.JPanel();
        textos.setOpaque(false);
        textos.setLayout(new javax.swing.BoxLayout(textos, javax.swing.BoxLayout.Y_AXIS));

        javax.swing.JLabel t = new javax.swing.JLabel(titulo);
        t.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 10));
        t.setForeground(new java.awt.Color(86, 106, 134));

        valor.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        valor.setForeground(TEXTO_AZUL);

        textos.add(t);
        textos.add(javax.swing.Box.createVerticalStrut(2));
        textos.add(valor);

        p.add(ico, java.awt.BorderLayout.WEST);
        p.add(textos, java.awt.BorderLayout.CENTER);
        return p;
    }

    private javax.swing.JLabel crearValorResumen(String nombre) {
        javax.swing.JLabel l = new javax.swing.JLabel("Q 0.00");
        l.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11));
        l.setForeground(TEXTO_AZUL);
        return l;
    }

    private javax.swing.JPanel crearFilaResumen(String icono, String nombre, javax.swing.JLabel valor) {
        javax.swing.JPanel p = new javax.swing.JPanel(new java.awt.BorderLayout(8, 0));
        p.setOpaque(false);
        p.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 42));
        p.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, BORDE_SUAVE));

        javax.swing.JLabel i = new javax.swing.JLabel(icono);
        i.setForeground(new java.awt.Color(24, 145, 81));
        i.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        i.setPreferredSize(new java.awt.Dimension(25, 35));

        javax.swing.JLabel n = new javax.swing.JLabel(nombre);
        n.setForeground(TEXTO_AZUL);
        n.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 11));

        p.add(i, java.awt.BorderLayout.WEST);
        p.add(n, java.awt.BorderLayout.CENTER);
        p.add(valor, java.awt.BorderLayout.EAST);
        return p;
    }

    private javax.swing.JLabel crearDatoUltimo() {
        javax.swing.JLabel l = new javax.swing.JLabel("-");
        l.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11));
        l.setForeground(TEXTO_AZUL);
        return l;
    }

    private javax.swing.JPanel crearLineaDato(String nombre, javax.swing.JLabel valor) {
        javax.swing.JPanel p = new javax.swing.JPanel(new java.awt.BorderLayout());
        p.setOpaque(false);
        p.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 27));

        javax.swing.JLabel n = new javax.swing.JLabel(nombre);
        n.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 11));
        n.setForeground(new java.awt.Color(85, 104, 130));

        p.add(n, java.awt.BorderLayout.WEST);
        p.add(valor, java.awt.BorderLayout.EAST);
        return p;
    }

    private void limpiarFiltros() {
        txtBuscarHistorial.setText("");
        chkEfectivo.setSelected(false);
        chkTarjeta.setSelected(false);
        chkQR.setSelected(false);
        chkCompletadas.setSelected(false);
        chkCanceladas.setSelected(false);
        aplicarFiltros();
    }

    private void aplicarFiltros() {
        if (tablaHistorial1 == null || historialCompleto == null) return;

        String buscar = txtBuscarHistorial == null
                ? ""
                : txtBuscarHistorial.getText().trim().toLowerCase();

        tablaHistorial1.limpiarTabla();

        for (Object[] fila : historialCompleto) {
            String pedido = String.valueOf(fila[1]);
            String cliente = String.valueOf(fila[2]);
            String metodo = String.valueOf(fila[3]);
            String estado = String.valueOf(fila[5]);

            boolean coincideTexto = buscar.isEmpty()
                    || pedido.toLowerCase().contains(buscar)
                    || cliente.toLowerCase().contains(buscar);

            boolean algunMetodo = chkEfectivo != null
                    && (chkEfectivo.isSelected() || chkTarjeta.isSelected() || chkQR.isSelected());

            boolean coincideMetodo = !algunMetodo
                    || (chkEfectivo.isSelected() && "Efectivo".equalsIgnoreCase(metodo))
                    || (chkTarjeta.isSelected() && "Tarjeta".equalsIgnoreCase(metodo))
                    || (chkQR.isSelected() && "QR".equalsIgnoreCase(metodo));

            boolean algunEstado = chkCompletadas != null
                    && (chkCompletadas.isSelected() || chkCanceladas.isSelected());

            boolean coincideEstado = !algunEstado
                    || (chkCompletadas.isSelected() && "Completada".equalsIgnoreCase(estado))
                    || (chkCanceladas.isSelected() && "Cancelada".equalsIgnoreCase(estado));

            if (coincideTexto && coincideMetodo && coincideEstado) {
                tablaHistorial1.agregarFila(
                        String.valueOf(fila[0]),
                        pedido,
                        cliente,
                        metodo,
                        String.valueOf(fila[4]),
                        estado);
            }
        }

        tablaHistorial1.revalidate();
        tablaHistorial1.repaint();
    }

    private double extraerMonto(String texto) {
        if (texto == null) return 0.0;
        try {
            String limpio = texto.replace("Q", "")
                    .replace(",", "")
                    .trim();
            return Double.parseDouble(limpio);
        } catch (Exception e) {
            return 0.0;
        }
    }

    private void actualizarResumenVentas() {
        if (historialCompleto == null) return;

        double total = 0.0;
        double efectivo = 0.0;
        double tarjeta = 0.0;
        double qr = 0.0;
        int canceladas = 0;

        for (Object[] fila : historialCompleto) {
            double monto = extraerMonto(String.valueOf(fila[4]));
            String metodo = String.valueOf(fila[3]);
            String estado = String.valueOf(fila[5]);

            if ("Cancelada".equalsIgnoreCase(estado)) {
                canceladas++;
            } else {
                total += monto;
                if ("Efectivo".equalsIgnoreCase(metodo)) efectivo += monto;
                else if ("Tarjeta".equalsIgnoreCase(metodo)) tarjeta += monto;
                else if ("QR".equalsIgnoreCase(metodo)) qr += monto;
            }
        }

        int pedidos = historialCompleto.size();
        double promedio = pedidos == 0 ? 0.0 : total / Math.max(1, pedidos - canceladas);

        if (lblVentasTotales != null) lblVentasTotales.setText(String.format("Q %.2f", total));
        if (lblPedidos != null) lblPedidos.setText(String.valueOf(pedidos));
        if (lblTicketPromedio != null) lblTicketPromedio.setText(String.format("Q %.2f", promedio));
        if (lblCancelaciones != null) lblCancelaciones.setText(String.valueOf(canceladas));

        if (lblResumenEfectivo != null) lblResumenEfectivo.setText(String.format("Q %.2f", efectivo));
        if (lblResumenTarjeta != null) lblResumenTarjeta.setText(String.format("Q %.2f", tarjeta));
        if (lblResumenQR != null) lblResumenQR.setText(String.format("Q %.2f", qr));

        if (!historialCompleto.isEmpty()) {
            Object[] ultima = historialCompleto.get(0);
            lblUltimoPedido.setText(String.valueOf(ultima[1]));
            lblUltimoTotal.setText(String.valueOf(ultima[4]));
            lblUltimaHora.setText(String.valueOf(ultima[0]));
            lblUltimoEstado.setText(String.valueOf(ultima[5]));
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

        panelRedondeadoSombra1 = new Componentes.PanelRedondeadoSombra();
        Encabezado = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lineaGris2 = new Componentes.LineaGris();
        panelTotalesAcciones = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        subtotal = new javax.swing.JLabel();
        subtotal1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lineaGris1 = new Componentes.LineaGris();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        panelRedondeadoSombra2 = new Componentes.PanelRedondeadoSombra();
        PanelCentral = new javax.swing.JPanel();
        EncabezadoCentral = new Componentes.PanelRedondeadoSombra();
        jPanel7 = new javax.swing.JPanel();
        panelRedondeadoSombra3 = new Componentes.PanelRedondeadoSombra();
        panelRedondeadoSombra4 = new Componentes.PanelRedondeadoSombra();
        jLabel7 = new javax.swing.JLabel();
        panelRedondeadoSombra6 = new Componentes.PanelRedondeadoSombra();
        PanelComidas = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaHistorial1 = new Componentes.TablaHistorial();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());

        panelRedondeadoSombra1.setBackground(new java.awt.Color(255, 255, 255));
        panelRedondeadoSombra1.setPreferredSize(new java.awt.Dimension(430, 100));
        panelRedondeadoSombra1.setLayout(new java.awt.BorderLayout());

        Encabezado.setBackground(new java.awt.Color(255, 255, 255));
        Encabezado.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 1, 1));
        Encabezado.setOpaque(false);
        Encabezado.setPreferredSize(new java.awt.Dimension(200, 100));
        Encabezado.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("TU ORDEN ");
        jLabel2.setToolTipText("");
        jLabel2.setAlignmentY(5.0F);
        Encabezado.add(jLabel2);

        lineaGris2.setPreferredSize(new java.awt.Dimension(380, 5));
        Encabezado.add(lineaGris2);

        panelRedondeadoSombra1.add(Encabezado, java.awt.BorderLayout.NORTH);

        panelTotalesAcciones.setBackground(new java.awt.Color(102, 102, 0));
        panelTotalesAcciones.setForeground(new java.awt.Color(255, 255, 255));
        panelTotalesAcciones.setOpaque(false);
        panelTotalesAcciones.setPreferredSize(new java.awt.Dimension(0, 350));
        panelTotalesAcciones.setLayout(new javax.swing.BoxLayout(panelTotalesAcciones, javax.swing.BoxLayout.Y_AXIS));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setMinimumSize(new java.awt.Dimension(550, 100));
        jPanel5.setOpaque(false);
        jPanel5.setPreferredSize(new java.awt.Dimension(800, 110));

        jPanel4.setMinimumSize(new java.awt.Dimension(500, 89));
        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(400, 120));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        subtotal.setFont(new java.awt.Font("Arial Black", 0, 27)); // NOI18N
        subtotal.setForeground(new java.awt.Color(173, 8, 15));
        subtotal.setText("Q 0.00");
        jPanel4.add(subtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 80, 150, -1));

        subtotal1.setFont(new java.awt.Font("Arial Black", 0, 27)); // NOI18N
        subtotal1.setForeground(new java.awt.Color(0, 0, 0));
        subtotal1.setText("TOTAL");
        jPanel4.add(subtotal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 150, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Q 0.00");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 45, 90, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 21)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Impuesto");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 45, 120, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 21)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Sub Total");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 9, 120, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Q0.00");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 9, 90, 30));
        jPanel4.add(lineaGris1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, -5, -1, 20));

        jPanel5.add(jPanel4);

        panelTotalesAcciones.add(jPanel5);

        panelRedondeadoSombra1.add(panelTotalesAcciones, java.awt.BorderLayout.SOUTH);

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);
        jScrollPane1.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.setOpaque(false);
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(jPanel3);

        panelRedondeadoSombra1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(panelRedondeadoSombra1, java.awt.BorderLayout.WEST);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        panelRedondeadoSombra2.setBackground(new java.awt.Color(255, 255, 255));
        panelRedondeadoSombra2.setPreferredSize(new java.awt.Dimension(400, 100));
        jPanel2.add(panelRedondeadoSombra2, java.awt.BorderLayout.EAST);

        PanelCentral.setBackground(new java.awt.Color(255, 255, 255));
        PanelCentral.setPreferredSize(new java.awt.Dimension(350, 0));
        PanelCentral.setLayout(new java.awt.BorderLayout());

        EncabezadoCentral.setBackground(new java.awt.Color(255, 255, 255));
        EncabezadoCentral.setPreferredSize(new java.awt.Dimension(0, 250));
        EncabezadoCentral.setLayout(new javax.swing.BoxLayout(EncabezadoCentral, javax.swing.BoxLayout.Y_AXIS));

        jPanel7.setBackground(new java.awt.Color(255, 102, 51));
        jPanel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 30, 0, 20));
        jPanel7.setOpaque(false);
        jPanel7.setPreferredSize(new java.awt.Dimension(0, 60));

        panelRedondeadoSombra3.setBackground(new java.awt.Color(255, 255, 255));

        panelRedondeadoSombra4.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelRedondeadoSombra4Layout = new javax.swing.GroupLayout(panelRedondeadoSombra4);
        panelRedondeadoSombra4.setLayout(panelRedondeadoSombra4Layout);
        panelRedondeadoSombra4Layout.setHorizontalGroup(
            panelRedondeadoSombra4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 229, Short.MAX_VALUE)
        );
        panelRedondeadoSombra4Layout.setVerticalGroup(
            panelRedondeadoSombra4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("TU ORDEN ");
        jLabel7.setToolTipText("");
        jLabel7.setAlignmentY(5.0F);

        panelRedondeadoSombra6.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelRedondeadoSombra6Layout = new javax.swing.GroupLayout(panelRedondeadoSombra6);
        panelRedondeadoSombra6.setLayout(panelRedondeadoSombra6Layout);
        panelRedondeadoSombra6Layout.setHorizontalGroup(
            panelRedondeadoSombra6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 244, Short.MAX_VALUE)
        );
        panelRedondeadoSombra6Layout.setVerticalGroup(
            panelRedondeadoSombra6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 119, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelRedondeadoSombra3, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelRedondeadoSombra4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelRedondeadoSombra6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelRedondeadoSombra6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(panelRedondeadoSombra4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelRedondeadoSombra3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)))
                .addGap(26, 26, 26))
        );

        EncabezadoCentral.add(jPanel7);

        PanelCentral.add(EncabezadoCentral, java.awt.BorderLayout.NORTH);

        PanelComidas.setBackground(new java.awt.Color(255, 255, 255));
        PanelComidas.setForeground(new java.awt.Color(255, 255, 255));
        PanelComidas.setLayout(new java.awt.BorderLayout());

        jScrollPane3.setBorder(null);
        jScrollPane3.setViewportView(tablaHistorial1);

        PanelComidas.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        PanelCentral.add(PanelComidas, java.awt.BorderLayout.CENTER);

        jPanel2.add(PanelCentral, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Encabezado;
    private Componentes.PanelRedondeadoSombra EncabezadoCentral;
    private javax.swing.JPanel PanelCentral;
    private javax.swing.JPanel PanelComidas;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private Componentes.LineaGris lineaGris1;
    private Componentes.LineaGris lineaGris2;
    private Componentes.PanelRedondeadoSombra panelRedondeadoSombra1;
    private Componentes.PanelRedondeadoSombra panelRedondeadoSombra2;
    private Componentes.PanelRedondeadoSombra panelRedondeadoSombra3;
    private Componentes.PanelRedondeadoSombra panelRedondeadoSombra4;
    private Componentes.PanelRedondeadoSombra panelRedondeadoSombra6;
    private javax.swing.JPanel panelTotalesAcciones;
    private javax.swing.JLabel subtotal;
    private javax.swing.JLabel subtotal1;
    private Componentes.TablaHistorial tablaHistorial1;
    // End of variables declaration//GEN-END:variables
}
