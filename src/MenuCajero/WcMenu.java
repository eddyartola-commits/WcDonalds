
package MenuCajero;

import Modelo.MoldeProductos;
import java.awt.CardLayout;
import java.awt.Component;
import java.time.LocalTime;


public class WcMenu extends javax.swing.JPanel {

    private CardLayout cardLayout;
    // CACHÉ EN RAM: Almacena los productos por categoría para NO volver a consultar MySQL
    private static final java.util.Map<Integer, java.util.List<Modelo.Producto>> CACHE_PRODUCTOS_BD = new java.util.HashMap<>();
    
    private Componentes.PanelDetalleProducto panelDetalle = new Componentes.PanelDetalleProducto();

    // Instancia única del DAO
    private final Conexion.ProductoDAO productoDAO = new Conexion.ProductoDAO();

    public WcMenu() {
        initComponents();
        precargarTodasLasImagenes(); // Inicia la carga transparente en segundo plano
        jScrollPane1.getViewport().setOpaque(false);
        jScrollPane1.setOpaque(false);
        
        jScrollPane2.getViewport().setOpaque(false);
        jScrollPane2.setOpaque(false);
        
        jScrollPane3.getViewport().setOpaque(false);
        jScrollPane3.setOpaque(false);
        panelGrid.setOpaque(false);
    
        Panelzquierda.setLayout(new java.awt.BorderLayout());
        Panelzquierda.add(panelDetalle, java.awt.BorderLayout.CENTER);

        // Conectar el botón del panel derecho con el carrito
        panelDetalle.getBtnAgregarCarrito().addActionListener(e -> {
            if (panelDetalle.getProductoActual() != null) {
                agregarProductoAOrden(panelDetalle.getProductoActual());
            }
        });

        aplicarFiltroHorarioCategorias();
    // 1. MARCAR EL BOTÓN DE HAMBURGUESAS COMO SELECCIONADO (AMARILLO)
    btnAlmuerzos.setSelected(true);
    
    // 2. CARGAR LOS PRODUCTOS DE LA CATEGORÍA 1 (HAMBURGUESAS)
    cargarProductosPorCategoria(1);

            // 1. Quitar fondos del ScrollPane y Viewport
            jScrollPane2.setOpaque(false);
            jScrollPane2.getViewport().setOpaque(false);
            jScrollPane2.setBorder(null);

            // 2. Definir tamaño fijo para cada botón
            java.awt.Dimension tamanoBoton = new java.awt.Dimension(160, 45);
            int anchoTotal = 0;
            int espacioEntreBotones = 10;

            for (java.awt.Component comp : panelBotonesCategorias.getComponents()) {
                if (comp instanceof javax.swing.JComponent) {
                    javax.swing.JComponent btn = (javax.swing.JComponent) comp;
                    btn.setPreferredSize(tamanoBoton);
                    btn.setMaximumSize(tamanoBoton);
                    btn.setMinimumSize(tamanoBoton);

                    // Sumar el ancho de cada botón más el espacio
                    anchoTotal += tamanoBoton.width + espacioEntreBotones;
                }
            }

            // 3. FORZAR al panel contenedor a medir el ancho total real de todos los botones juntos
            panelBotonesCategorias.setPreferredSize(new java.awt.Dimension(anchoTotal, 45));

            // 4. Activar scroll horizontal mediante la rueda del mouse
            jScrollPane2.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
                @Override
                public void mouseWheelMoved(java.awt.event.MouseWheelEvent e) {
                    javax.swing.JScrollBar bar = jScrollPane2.getHorizontalScrollBar();
                    bar.setValue(bar.getValue() + (e.getWheelRotation() * 30));
                }
            });

        
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
    
    
    private void aplicarFiltroHorarioCategorias() {

         LocalTime horaActual = LocalTime.now();
            int hora = horaActual.getHour();

            boolean esHorarioDesayuno = (hora >= 7 && hora < 11);

            // Habilitar / Deshabilitar botones superiores de categorías
            if (btnDesayunos != null) {
                btnDesayunos.setEnabled(esHorarioDesayuno);
            }
            
            

            if (btnAlmuerzos != null) {
                btnAlmuerzos.setEnabled(!esHorarioDesayuno);
                // Opcional: btnAlmuerzos.setVisible(!esHorarioDesayuno);
            }

            // Cargar automáticamente los productos del menú según el horario
            int idCategoriaAuto = obtenerCategoriaSegunHora();
            cargarProductosPorCategoria(idCategoriaAuto); 
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


    
public void cargarProductosPorCategoria(int idCategoria) {
    panelGrid.setVisible(false);
    panelGrid.removeAll();

    javax.swing.SwingWorker<java.util.List<Modelo.Producto>, Void> worker = new javax.swing.SwingWorker<>() {
        @Override
        protected java.util.List<Modelo.Producto> doInBackground() throws Exception {
            // Si la categoría ya está guardada en RAM, la devuelve en 0 ms
            synchronized (CACHE_PRODUCTOS_BD) {
                if (CACHE_PRODUCTOS_BD.containsKey(idCategoria)) {
                    return CACHE_PRODUCTOS_BD.get(idCategoria);
                }
            }
            
            // Si es la primera vez, consulta a MySQL y guarda en caché
            java.util.List<Modelo.Producto> lista = productoDAO.obtenerProductosPorCategoria(idCategoria);
            synchronized (CACHE_PRODUCTOS_BD) {
                CACHE_PRODUCTOS_BD.put(idCategoria, lista);
            }
            return lista;
        }

        @Override
        protected void done() {
            try {
                java.util.List<Modelo.Producto> lista = get();
                panelGrid.setLayout(new java.awt.GridLayout(0, 3, 15, 15));

                for (Modelo.Producto p : lista) {
                    if (p.isDisponible()) {
                        MoldeProductos tarjeta = new MoldeProductos();
                        tarjeta.setDatos(p);

                        tarjeta.getBtnAgregar().addActionListener(e -> {
                            agregarProductoAOrden(p);
                        });

                        panelGrid.add(tarjeta);
                    }
                }

                javax.swing.JPanel panelContenedor = new javax.swing.JPanel(new java.awt.BorderLayout());
                panelContenedor.setOpaque(false);
                panelContenedor.add(panelGrid, java.awt.BorderLayout.NORTH);

                jScrollPane3.setViewportView(panelContenedor);

                panelGrid.setVisible(true);
                panelGrid.revalidate();
                panelGrid.repaint();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    worker.execute();
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
        boton2 = new Componentes.boton();
        limpiarOrden1 = new Componentes.LimpiarOrden();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        PanelCentral = new javax.swing.JPanel();
        EncabezadoCentral = new Componentes.PanelRedondeadoSombra();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        buscador1 = new Componentes.Buscador();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        panelBotonesCategorias = new javax.swing.JPanel();
        btnAlmuerzos = new Componentes.BotonCategoria();
        botonCategoria5 = new Componentes.BotonCategoria();
        botonCategoria6 = new Componentes.BotonCategoria();
        btnDesayunos = new Componentes.BotonCategoria();
        botonCategoria2 = new Componentes.BotonCategoria();
        btnCafe = new Componentes.BotonCategoria();
        botonCategoria8 = new Componentes.BotonCategoria();
        botonCategoria4 = new Componentes.BotonCategoria();
        PanelComidas = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        panelGrid = new javax.swing.JPanel();
        Panelzquierda = new Componentes.PanelRedondeadoSombra();

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

        boton2.setText("PAGAR");
        boton2.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        boton2.setPreferredSize(new java.awt.Dimension(350, 60));
        boton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton2ActionPerformed(evt);
            }
        });
        jPanel5.add(boton2);

        limpiarOrden1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpiarOrden1ActionPerformed(evt);
            }
        });
        jPanel5.add(limpiarOrden1);

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

        PanelCentral.setBackground(new java.awt.Color(255, 255, 255));
        PanelCentral.setPreferredSize(new java.awt.Dimension(350, 0));
        PanelCentral.setLayout(new java.awt.BorderLayout());

        EncabezadoCentral.setBackground(new java.awt.Color(255, 255, 255));
        EncabezadoCentral.setPreferredSize(new java.awt.Dimension(0, 150));
        EncabezadoCentral.setLayout(new javax.swing.BoxLayout(EncabezadoCentral, javax.swing.BoxLayout.Y_AXIS));

        jPanel7.setBackground(new java.awt.Color(255, 102, 51));
        jPanel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 30, 0, 20));
        jPanel7.setOpaque(false);
        jPanel7.setPreferredSize(new java.awt.Dimension(0, 60));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Comida");
        jPanel7.add(jLabel1, java.awt.BorderLayout.CENTER);
        jPanel7.add(buscador1, java.awt.BorderLayout.EAST);

        EncabezadoCentral.add(jPanel7);

        jPanel8.setOpaque(false);
        jPanel8.setPreferredSize(new java.awt.Dimension(0, 140));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane2.setPreferredSize(new java.awt.Dimension(0, 80));

        panelBotonesCategorias.setBackground(new java.awt.Color(255, 255, 255));
        panelBotonesCategorias.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        panelBotonesCategorias.setOpaque(false);
        panelBotonesCategorias.setPreferredSize(new java.awt.Dimension(0, 100));
        panelBotonesCategorias.setLayout(new javax.swing.BoxLayout(panelBotonesCategorias, javax.swing.BoxLayout.X_AXIS));

        btnAlmuerzos.setText("Almuerzos ");
        btnAlmuerzos.setMaximumSize(new java.awt.Dimension(113, 50));
        btnAlmuerzos.setMinimumSize(new java.awt.Dimension(113, 50));
        btnAlmuerzos.setPreferredSize(new java.awt.Dimension(165, 50));
        btnAlmuerzos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlmuerzosActionPerformed(evt);
            }
        });
        panelBotonesCategorias.add(btnAlmuerzos);

        botonCategoria5.setText("Antojos");
        botonCategoria5.setPreferredSize(new java.awt.Dimension(165, 50));
        botonCategoria5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCategoria5ActionPerformed(evt);
            }
        });
        panelBotonesCategorias.add(botonCategoria5);

        botonCategoria6.setText("Bebidas");
        botonCategoria6.setPreferredSize(new java.awt.Dimension(165, 50));
        botonCategoria6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCategoria6ActionPerformed(evt);
            }
        });
        panelBotonesCategorias.add(botonCategoria6);

        btnDesayunos.setText("Desayunos ");
        btnDesayunos.setPreferredSize(new java.awt.Dimension(165, 50));
        btnDesayunos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesayunosActionPerformed(evt);
            }
        });
        panelBotonesCategorias.add(btnDesayunos);

        botonCategoria2.setText("Extras");
        botonCategoria2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCategoria2ActionPerformed(evt);
            }
        });
        panelBotonesCategorias.add(botonCategoria2);

        btnCafe.setText("Wc Cafe");
        btnCafe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCafeActionPerformed(evt);
            }
        });
        panelBotonesCategorias.add(btnCafe);

        botonCategoria8.setText("Postres");
        botonCategoria8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCategoria8ActionPerformed(evt);
            }
        });
        panelBotonesCategorias.add(botonCategoria8);

        botonCategoria4.setText("Para Compartir");
        botonCategoria4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCategoria4ActionPerformed(evt);
            }
        });
        panelBotonesCategorias.add(botonCategoria4);

        jScrollPane2.setViewportView(panelBotonesCategorias);

        jPanel8.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        EncabezadoCentral.add(jPanel8);

        PanelCentral.add(EncabezadoCentral, java.awt.BorderLayout.NORTH);

        PanelComidas.setBackground(new java.awt.Color(255, 255, 255));
        PanelComidas.setForeground(new java.awt.Color(255, 255, 255));
        PanelComidas.setLayout(new java.awt.BorderLayout());

        jScrollPane3.setBorder(null);

        panelGrid.setLayout(new java.awt.GridLayout(15, 3, 0, 15));
        jScrollPane3.setViewportView(panelGrid);

        PanelComidas.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        PanelCentral.add(PanelComidas, java.awt.BorderLayout.CENTER);

        jPanel2.add(PanelCentral, java.awt.BorderLayout.CENTER);

        Panelzquierda.setBackground(new java.awt.Color(255, 255, 255));
        Panelzquierda.setPreferredSize(new java.awt.Dimension(400, 100));
        Panelzquierda.setLayout(new java.awt.BorderLayout());
        jPanel2.add(Panelzquierda, java.awt.BorderLayout.EAST);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAlmuerzosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlmuerzosActionPerformed
        cargarProductosPorCategoria(1); // Muestra Cafe/Bebidas
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAlmuerzosActionPerformed

    private void botonCategoria5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCategoria5ActionPerformed

        cargarProductosPorCategoria(2); // Muestra Cafe/Bebidas
// TODO add your handling code here:
    }//GEN-LAST:event_botonCategoria5ActionPerformed

    private void botonCategoria6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCategoria6ActionPerformed
 cargarProductosPorCategoria(3);        // TODO add your handling code here:
    }//GEN-LAST:event_botonCategoria6ActionPerformed

    private void btnDesayunosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesayunosActionPerformed
         cargarProductosPorCategoria(4);// TODO add your handling code here:
    }//GEN-LAST:event_btnDesayunosActionPerformed

    private void botonCategoria2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCategoria2ActionPerformed
 cargarProductosPorCategoria(5);        // TODO add your handling code here:
    }//GEN-LAST:event_botonCategoria2ActionPerformed

    private void btnCafeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCafeActionPerformed
 cargarProductosPorCategoria(6);        // TODO add your handling code here:
    }//GEN-LAST:event_btnCafeActionPerformed

    private void botonCategoria8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCategoria8ActionPerformed
 cargarProductosPorCategoria(7);        // TODO add your handling code here:
    }//GEN-LAST:event_botonCategoria8ActionPerformed

    private void botonCategoria4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCategoria4ActionPerformed
 cargarProductosPorCategoria(8);        // TODO add your handling code here:
    }//GEN-LAST:event_botonCategoria4ActionPerformed

    private void boton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton2ActionPerformed
       if (jPanel3.getComponentCount() == 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Debe agregar al menos un producto a la orden.");
            return;
        }

        // Enviar una copia de la orden actual a la pantalla de pagos.
        Cajero cajeroPrincipal = (Cajero) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (cajeroPrincipal != null) {
            cajeroPrincipal.iniciarPago(getPedidoActual());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_boton2ActionPerformed

    private void limpiarOrden1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpiarOrden1ActionPerformed
        int respuesta = javax.swing.JOptionPane.showConfirmDialog(
            this,
            "¿Estás seguro de vaciar la orden actual?",
            "Vaciar Carrito",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE
        );

        if (respuesta == javax.swing.JOptionPane.YES_OPTION) {
            limpiarOrden();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_limpiarOrden1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Encabezado;
    private Componentes.PanelRedondeadoSombra EncabezadoCentral;
    private javax.swing.JPanel PanelCentral;
    private javax.swing.JPanel PanelComidas;
    private Componentes.PanelRedondeadoSombra Panelzquierda;
    private Componentes.boton boton2;
    private Componentes.BotonCategoria botonCategoria2;
    private Componentes.BotonCategoria botonCategoria4;
    private Componentes.BotonCategoria botonCategoria5;
    private Componentes.BotonCategoria botonCategoria6;
    private Componentes.BotonCategoria botonCategoria8;
    private Componentes.BotonCategoria btnAlmuerzos;
    private Componentes.BotonCategoria btnCafe;
    private Componentes.BotonCategoria btnDesayunos;
    private Componentes.Buscador buscador1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private Componentes.LimpiarOrden limpiarOrden1;
    private Componentes.LineaGris lineaGris1;
    private Componentes.LineaGris lineaGris2;
    private javax.swing.JPanel panelBotonesCategorias;
    private javax.swing.JPanel panelGrid;
    private Componentes.PanelRedondeadoSombra panelRedondeadoSombra1;
    private javax.swing.JPanel panelTotalesAcciones;
    private javax.swing.JLabel subtotal;
    private javax.swing.JLabel subtotal1;
    // End of variables declaration//GEN-END:variables
}
