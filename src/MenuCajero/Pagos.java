package MenuCajero;

import Componentes.PanelRedondeadoSombra;
import Conexion.PagoDAO;
import Modelo.ItemPedido;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Pantalla de cobro responsiva inspirada en el diseño de referencia.
 * Se implementa a mano (sin AbsoluteLayout) para que se adapte a la ventana.
 */
public class Pagos extends JPanel {

    private static final Color ROJO = new Color(218, 0, 28);
    private static final Color ROJO_OSCURO = new Color(173, 8, 15);
    private static final Color FONDO = new Color(247, 247, 247);
    private static final Color BORDE = new Color(226, 226, 226);
    private static final Color TEXTO_SEC = new Color(105, 105, 105);
    private static final Color VERDE = new Color(32, 150, 73);

    private final int idUsuario;
    private final PagoDAO pagoDAO = new PagoDAO();
    private final List<ItemPedido> pedido = new ArrayList<>();

    private JPanel panelListaProductos;
    private JLabel lblSubtotalIzq;
    private JLabel lblImpuestoIzq;
    private JLabel lblDescuentoIzq;
    private JLabel lblTotalIzq;
    private JLabel lblSubtotalDer;
    private JLabel lblImpuestoDer;
    private JLabel lblDescuentoDer;
    private JLabel lblTotalDer;
    private JLabel lblTotalGrande;
    private JLabel lblPedido;

    private CardLayout cardPago;
    private JPanel panelContenidoPago;
    private JButton btnTarjeta;
    private JButton btnEfectivo;
    private JButton btnQR;
    private JButton btnRegalo;
    private String metodoPago = "TARJETA";

    private JTextField txtNumeroTarjeta;
    private JTextField txtTitular;
    private JTextField txtFecha;
    private JPasswordField txtCVV;
    private JTextField txtNombreFactura;
    private JTextField txtDireccionFactura;

    private JTextField txtEfectivo;
    private JLabel lblCambio;
    private JButton btnConfirmarQR;
    private boolean qrConfirmado = false;
    private JTextField txtCodigoRegalo;
    private JLabel lblSaldoRegalo;

    private JCheckBox chkFactura;
    private JTextField txtCorreoFactura;
    private JTextField txtNitFactura;
    private JButton btnProcesar;

    private double porcentajeCupon = 0.0;
    private String codigoCupon = null;
    private double subtotalSinIVA = 0.0;
    private double impuesto = 0.0;
    private double descuento = 0.0;
    private double total = 0.0;

    public Pagos() {
        this(2); // Cajero de ejemplo de la BD; el login real envía el id correcto.
    }

    public Pagos(int idUsuario) {
        this.idUsuario = idUsuario > 0 ? idUsuario : 2;
        construirUI();
        actualizarTotales();
    }

    public void setPedido(List<ItemPedido> items) {
        pedido.clear();
        if (items != null) pedido.addAll(items);
        porcentajeCupon = 0.0;
        codigoCupon = null;
        qrConfirmado = false;
        lblPedido.setText("Pedido nuevo");
        cargarResumenProductos();
        actualizarTotales();
    }

    private void construirUI() {
        setBackground(FONDO);
        setLayout(new BorderLayout());

        JPanel contenedor = new JPanel(new GridBagLayout());
        contenedor.setBackground(FONDO);
        contenedor.setBorder(new EmptyBorder(10, 10, 10, 10));

        GridBagConstraints g = new GridBagConstraints();
        g.gridy = 0;
        g.fill = GridBagConstraints.BOTH;
        g.weighty = 1.0;

        JPanel izquierda = crearPanelResumen();
        JPanel centro = crearPanelMedioPago();
        JPanel derecha = crearPanelTotal();

        g.gridx = 0; g.weightx = 0.27; g.insets = new Insets(0, 0, 0, 6);
        contenedor.add(izquierda, g);
        g.gridx = 1; g.weightx = 0.46; g.insets = new Insets(0, 6, 0, 6);
        contenedor.add(centro, g);
        g.gridx = 2; g.weightx = 0.27; g.insets = new Insets(0, 6, 0, 0);
        contenedor.add(derecha, g);

        add(contenedor, BorderLayout.CENTER);
    }

    private JPanel crearPanelResumen() {
        PanelRedondeadoSombra p = tarjeta();
        p.setLayout(new BorderLayout(0, 8));
        p.setPreferredSize(new Dimension(300, 620));
        p.setMinimumSize(new Dimension(240, 420));
        p.setBorder(new EmptyBorder(22, 18, 18, 18));

        JPanel top = transparente(new BorderLayout());
        JLabel titulo = tituloConIcono("Resumen de venta", IconoTipo.RESUMEN, 18);
        JLabel numero = textoSec("#0012", 11);
        top.add(titulo, BorderLayout.WEST);
        top.add(numero, BorderLayout.EAST);
        p.add(top, BorderLayout.NORTH);

        panelListaProductos = new JPanel();
        panelListaProductos.setOpaque(false);
        panelListaProductos.setLayout(new BoxLayout(panelListaProductos, BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(panelListaProductos);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(14);
        p.add(scroll, BorderLayout.CENTER);

        JPanel abajo = transparente();
        abajo.setLayout(new BoxLayout(abajo, BoxLayout.Y_AXIS));
        abajo.add(separador());
        lblSubtotalIzq = filaImporte(abajo, "Subtotal", "Q 0.00", false);
        lblImpuestoIzq = filaImporte(abajo, "Impuestos (12%)", "Q 0.00", false);
        lblDescuentoIzq = filaImporte(abajo, "Descuento", "Q 0.00", false);
        abajo.add(Box.createVerticalStrut(4));
        lblTotalIzq = filaImporte(abajo, "Total a pagar", "Q 0.00", true);
        abajo.add(Box.createVerticalStrut(8));

        JButton btnCupon = botonBorde("▣  Aplicar cupón");
        btnCupon.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnCupon.addActionListener(e -> aplicarCupon());
        abajo.add(btnCupon);
        abajo.add(Box.createVerticalStrut(10));

        JPanel banner = new JPanel(new BorderLayout());
        banner.setBackground(new Color(190, 15, 20));
        banner.setBorder(new EmptyBorder(12, 12, 12, 12));
        JLabel gracias = new JLabel("<html><center><b style='color:white;font-size:14px'>¡Gracias por tu compra!</b><br><span style='color:white'>WcDonald's</span></center></html>");
        gracias.setHorizontalAlignment(SwingConstants.CENTER);
        banner.add(gracias, BorderLayout.CENTER);
        banner.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
        banner.setPreferredSize(new Dimension(260, 75));
        abajo.add(banner);

        p.add(abajo, BorderLayout.SOUTH);
        return p;
    }

    private JPanel crearPanelMedioPago() {
        PanelRedondeadoSombra p = tarjeta();
        p.setLayout(new BorderLayout(0, 12));
        p.setPreferredSize(new Dimension(530, 620));
        p.setMinimumSize(new Dimension(420, 420));
        p.setBorder(new EmptyBorder(20, 18, 18, 18));

        JPanel encabezado = transparente();
        encabezado.setLayout(new BoxLayout(encabezado, BoxLayout.Y_AXIS));
        JLabel t = tituloConIcono("Medio de pago", IconoTipo.PAGO, 20);
        t.setAlignmentX(Component.LEFT_ALIGNMENT);
        encabezado.add(t);
        JLabel s = textoSec("Selecciona el método de pago para completar la transacción.", 11);
        s.setAlignmentX(Component.LEFT_ALIGNMENT);
        encabezado.add(s);
        encabezado.add(Box.createVerticalStrut(12));

        JPanel metodos = transparente(new GridLayout(1, 4, 8, 0));
        btnTarjeta = botonMetodo("Tarjeta", IconoTipo.TARJETA);
        btnEfectivo = botonMetodo("Efectivo", IconoTipo.EFECTIVO);
        btnQR = botonMetodo("QR (Billetera)", IconoTipo.QR);
        btnRegalo = botonMetodo("Tarjeta regalo", IconoTipo.REGALO);
        metodos.add(btnTarjeta); metodos.add(btnEfectivo); metodos.add(btnQR); metodos.add(btnRegalo);
        encabezado.add(metodos);
        p.add(encabezado, BorderLayout.NORTH);

        cardPago = new CardLayout();
        panelContenidoPago = transparente();
        panelContenidoPago.setLayout(cardPago);
        panelContenidoPago.add(crearFormularioTarjeta(), "TARJETA");
        panelContenidoPago.add(crearFormularioEfectivo(), "EFECTIVO");
        panelContenidoPago.add(crearFormularioQR(), "QR");
        panelContenidoPago.add(crearFormularioRegalo(), "REGALO");
        p.add(panelContenidoPago, BorderLayout.CENTER);

        btnTarjeta.addActionListener(e -> seleccionarMetodo("TARJETA"));
        btnEfectivo.addActionListener(e -> seleccionarMetodo("EFECTIVO"));
        btnQR.addActionListener(e -> seleccionarMetodo("QR"));
        btnRegalo.addActionListener(e -> seleccionarMetodo("REGALO"));
        seleccionarMetodo("TARJETA");
        return p;
    }

    private JPanel crearFormularioTarjeta() {
        JPanel main = transparente();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(seccionTitulo("Datos de la tarjeta", IconoTipo.TARJETA));
        main.add(Box.createVerticalStrut(10));

        txtNumeroTarjeta = campo("1234 5678 9012 3456");
        txtTitular = campo("Como aparece en la tarjeta");
        txtFecha = campoCompacto("MM/AA", 110);
        txtCVV = new JPasswordField();
        estilizarCampo(txtCVV, "123");
        txtCVV.setPreferredSize(new Dimension(100, 36));
        txtCVV.setMinimumSize(new Dimension(90, 36));
        txtCVV.setMaximumSize(new Dimension(100, 36));

        JPanel cardData = panelSuave();
        cardData.setLayout(new BoxLayout(cardData, BoxLayout.Y_AXIS));
        cardData.setAlignmentX(Component.LEFT_ALIGNMENT);
        cardData.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));

        // Número de tarjeta alineado firmemente al borde derecho del bloque.
        JPanel filaNumero = transparente(new BorderLayout());
        filaNumero.setAlignmentX(Component.LEFT_ALIGNMENT);
        filaNumero.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));
        JPanel numeroWrap = campoConEtiqueta("Número de tarjeta", txtNumeroTarjeta);
        numeroWrap.setPreferredSize(new Dimension(245, 56));
        numeroWrap.setMinimumSize(new Dimension(245, 56));
        numeroWrap.setMaximumSize(new Dimension(245, 56));
        filaNumero.add(numeroWrap, BorderLayout.EAST);
        cardData.add(filaNumero);
        cardData.add(Box.createVerticalStrut(8));

        JPanel filaInferior = transparente(new GridBagLayout());
        filaInferior.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridy = 0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(0, 0, 0, 10);

        gc.gridx = 0; gc.weightx = 0.52;
        filaInferior.add(campoConEtiqueta("Nombre del titular", txtTitular), gc);

        gc.gridx = 1; gc.weightx = 0.24;
        filaInferior.add(campoConEtiqueta("Fecha de expiración", txtFecha), gc);

        gc.gridx = 2; gc.weightx = 0.24; gc.insets = new Insets(0, 0, 0, 0);
        filaInferior.add(campoConEtiqueta("CVV", txtCVV), gc);

        cardData.add(filaInferior);
        main.add(cardData);

        main.add(Box.createVerticalStrut(16));
        main.add(seccionTitulo("Dirección de facturación  (opcional)", IconoTipo.FACTURA));
        main.add(Box.createVerticalStrut(10));
        txtDireccionFactura = campo("Dirección / zona / referencia");

        JPanel factCard = panelSuave();
        factCard.setLayout(new BoxLayout(factCard, BoxLayout.Y_AXIS));
        factCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        factCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 84));
        factCard.add(campoConEtiqueta("Dirección", txtDireccionFactura));
        main.add(factCard);
        main.add(Box.createVerticalGlue());

        JLabel seguridad = textoSec("Tu información está protegida. Este sistema no guarda el número de tarjeta ni el CVV.", 10);
        seguridad.setIcon(new MiniIcon(IconoTipo.SEGURIDAD, 15, TEXTO_SEC));
        seguridad.setIconTextGap(6);
        seguridad.setAlignmentX(Component.LEFT_ALIGNMENT);
        main.add(seguridad);
        return main;
    }

    private JPanel crearFormularioEfectivo() {
        JPanel main = transparente();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(seccionTitulo("Pago en efectivo", IconoTipo.EFECTIVO));
        main.add(Box.createVerticalStrut(14));

        JPanel tarjetaEfectivo = panelSuave();
        tarjetaEfectivo.setLayout(new BoxLayout(tarjetaEfectivo, BoxLayout.Y_AXIS));
        tarjetaEfectivo.setAlignmentX(Component.LEFT_ALIGNMENT);
        tarjetaEfectivo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 155));

        txtEfectivo = campoCompacto("0.00", 160);
        JPanel filaRecibido = filaCampoCompacta("Monto recibido", "Q", txtEfectivo);
        tarjetaEfectivo.add(filaRecibido);
        tarjetaEfectivo.add(Box.createVerticalStrut(10));

        JPanel filaCambio = transparente(new BorderLayout(12, 0));
        filaCambio.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        JLabel etCambio = new JLabel("Cambio");
        etCambio.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblCambio = titulo("Q 0.00", 20);
        lblCambio.setForeground(VERDE);
        filaCambio.add(etCambio, BorderLayout.WEST);
        filaCambio.add(lblCambio, BorderLayout.EAST);
        tarjetaEfectivo.add(filaCambio);

        JLabel ayuda = textoSec("El cambio se calcula automáticamente.", 10);
        ayuda.setAlignmentX(Component.LEFT_ALIGNMENT);
        tarjetaEfectivo.add(Box.createVerticalStrut(6));
        tarjetaEfectivo.add(ayuda);

        main.add(tarjetaEfectivo);
        main.add(Box.createVerticalGlue());
        txtEfectivo.getDocument().addDocumentListener(new SimpleDocumentListener(this::calcularCambio));
        return main;
    }

    private JPanel crearFormularioQR() {
        JPanel main = transparente();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(seccionTitulo("Pago con QR / billetera", IconoTipo.QR));
        main.add(Box.createVerticalStrut(18));
        JLabel qr = new JLabel("<html><center><div style='font-size:48px'>▦</div><b>QR DE DEMOSTRACIÓN</b><br>En un sistema real aquí se conecta la pasarela de pago.</center></html>");
        qr.setHorizontalAlignment(SwingConstants.CENTER);
        qr.setAlignmentX(Component.CENTER_ALIGNMENT);
        qr.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDE), new EmptyBorder(24, 24, 24, 24)));
        main.add(qr);
        main.add(Box.createVerticalStrut(18));
        btnConfirmarQR = botonRojo("Confirmar pago QR recibido");
        btnConfirmarQR.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnConfirmarQR.addActionListener(e -> {
            qrConfirmado = true;
            btnConfirmarQR.setText("✓ Pago QR confirmado");
            btnConfirmarQR.setBackground(VERDE);
        });
        main.add(btnConfirmarQR);
        main.add(Box.createVerticalGlue());
        return main;
    }

    private JPanel crearFormularioRegalo() {
        JPanel main = transparente();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(seccionTitulo("Tarjeta de regalo", IconoTipo.REGALO));
        main.add(Box.createVerticalStrut(12));
        JPanel tarjetaRegalo = panelSuave();
        tarjetaRegalo.setLayout(new BoxLayout(tarjetaRegalo, BoxLayout.Y_AXIS));
        tarjetaRegalo.setAlignmentX(Component.LEFT_ALIGNMENT);
        tarjetaRegalo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 170));
        txtCodigoRegalo = campoCompacto("Código de tarjeta", 230);
        tarjetaRegalo.add(campoConEtiqueta("Código", txtCodigoRegalo));
        JButton consultar = botonBorde("Consultar saldo");
        consultar.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblSaldoRegalo = titulo("Saldo: --", 18);
        lblSaldoRegalo.setAlignmentX(Component.LEFT_ALIGNMENT);
        consultar.addActionListener(e -> {
            double saldo = pagoDAO.consultarSaldoTarjetaRegalo(txtCodigoRegalo.getText());
            if (saldo < 0) lblSaldoRegalo.setText("Saldo: tarjeta no encontrada");
            else lblSaldoRegalo.setText(String.format("Saldo: Q %.2f", saldo));
        });
        tarjetaRegalo.add(consultar);
        tarjetaRegalo.add(Box.createVerticalStrut(12));
        tarjetaRegalo.add(lblSaldoRegalo);
        main.add(tarjetaRegalo);
        main.add(Box.createVerticalGlue());
        return main;
    }

    private JPanel crearPanelTotal() {
        PanelRedondeadoSombra p = tarjeta();
        p.setLayout(new BorderLayout(0, 10));
        p.setPreferredSize(new Dimension(300, 620));
        p.setMinimumSize(new Dimension(250, 420));
        p.setBorder(new EmptyBorder(20, 18, 18, 18));

        JPanel top = transparente();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        JLabel marca = tituloConIcono("Total a pagar", IconoTipo.TOTAL, 14);
        marca.setAlignmentX(Component.LEFT_ALIGNMENT);
        top.add(marca);
        lblTotalGrande = titulo("Q 0.00", 28);
        lblTotalGrande.setForeground(ROJO);
        lblTotalGrande.setAlignmentX(Component.LEFT_ALIGNMENT);
        top.add(Box.createVerticalStrut(8));
        top.add(lblTotalGrande);
        top.add(Box.createVerticalStrut(10));
        lblPedido = textoSec("Pedido nuevo", 11);
        lblPedido.setAlignmentX(Component.LEFT_ALIGNMENT);
        top.add(lblPedido);
        p.add(top, BorderLayout.NORTH);

        JPanel centro = transparente();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.add(separador());
        centro.add(seccionTitulo("Resumen del pago", IconoTipo.RESUMEN));
        lblSubtotalDer = filaImporte(centro, "Subtotal", "Q 0.00", false);
        lblImpuestoDer = filaImporte(centro, "Impuestos (12%)", "Q 0.00", false);
        lblDescuentoDer = filaImporte(centro, "Descuento", "Q 0.00", false);
        centro.add(Box.createVerticalStrut(4));
        lblTotalDer = filaImporte(centro, "Total", "Q 0.00", true);
        centro.add(Box.createVerticalStrut(10));
        centro.add(separador());
        centro.add(seccionTitulo("Opciones adicionales", IconoTipo.OPCIONES));
        centro.add(Box.createVerticalStrut(6));

        // Tarjeta de opciones más compacta y pegada al lado derecho.
        final int anchoOpciones = 252;
        JPanel opcionesCard = panelSuave();
        opcionesCard.setLayout(new BoxLayout(opcionesCard, BoxLayout.Y_AXIS));
        opcionesCard.setPreferredSize(new Dimension(anchoOpciones, 260));
        opcionesCard.setMinimumSize(new Dimension(anchoOpciones, 260));
        opcionesCard.setMaximumSize(new Dimension(anchoOpciones, 260));

        JPanel filaFactura = transparente(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        filaFactura.setAlignmentX(Component.LEFT_ALIGNMENT);
        filaFactura.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        chkFactura = new JCheckBox("Quiero factura");
        chkFactura.setOpaque(false);
        chkFactura.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        filaFactura.add(chkFactura);
        opcionesCard.add(filaFactura);
        opcionesCard.add(Box.createVerticalStrut(5));

        // Los campos se apilan para evitar que los textos se corten en el panel lateral.
        txtNombreFactura = campoCompacto("Nombre o razón social", 220);
        txtNitFactura = campoCompacto("CF o 1234567-8", 220);
        txtCorreoFactura = campoCompacto("ejemplo@correo.com", 220);

        JPanel nombreWrap = campoConEtiqueta("Nombre para factura", txtNombreFactura);
        nombreWrap.setPreferredSize(new Dimension(220, 54));
        nombreWrap.setMaximumSize(new Dimension(220, 54));
        nombreWrap.setAlignmentX(Component.RIGHT_ALIGNMENT);
        opcionesCard.add(nombreWrap);

        JPanel nitWrap = campoConEtiqueta("NIT", txtNitFactura);
        nitWrap.setPreferredSize(new Dimension(220, 54));
        nitWrap.setMaximumSize(new Dimension(220, 54));
        nitWrap.setAlignmentX(Component.RIGHT_ALIGNMENT);
        opcionesCard.add(nitWrap);

        JPanel correoWrap = campoConEtiqueta("Correo electrónico para factura", txtCorreoFactura);
        correoWrap.setPreferredSize(new Dimension(220, 54));
        correoWrap.setMaximumSize(new Dimension(220, 54));
        correoWrap.setAlignmentX(Component.RIGHT_ALIGNMENT);
        opcionesCard.add(correoWrap);

        JPanel opcionesAlineadas = transparente(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        opcionesAlineadas.setAlignmentX(Component.LEFT_ALIGNMENT);
        opcionesAlineadas.setMaximumSize(new Dimension(Integer.MAX_VALUE, 262));
        opcionesAlineadas.add(opcionesCard);
        centro.add(opcionesAlineadas);
        chkFactura.addActionListener(e -> {
            boolean on = chkFactura.isSelected();
            txtCorreoFactura.setEnabled(on);
            txtNombreFactura.setEnabled(on);
            txtNitFactura.setEnabled(on);
            txtDireccionFactura.setEnabled(on);
        });
        txtCorreoFactura.setEnabled(false);
        txtNombreFactura.setEnabled(false);
        txtNitFactura.setEnabled(false);
        txtDireccionFactura.setEnabled(false);
        p.add(centro, BorderLayout.CENTER);

        JPanel acciones = transparente();
        acciones.setLayout(new BoxLayout(acciones, BoxLayout.Y_AXIS));
        btnProcesar = botonRojo("Procesar pago  ›");
        btnProcesar.setIcon(new MiniIcon(IconoTipo.PAGO, 16, Color.WHITE));
        btnProcesar.setIconTextGap(8);
        btnProcesar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnProcesar.addActionListener(e -> procesarPago());
        JButton cancelar = botonBorde("‹  Cancelar");
        cancelar.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelar.addActionListener(e -> cancelarPago());
        acciones.add(btnProcesar);
        acciones.add(Box.createVerticalStrut(8));
        acciones.add(cancelar);
        p.add(acciones, BorderLayout.SOUTH);
        return p;
    }

    private void cargarResumenProductos() {
        panelListaProductos.removeAll();
        if (pedido.isEmpty()) {
            JLabel vacio = textoSec("No hay productos en la orden.", 12);
            vacio.setBorder(new EmptyBorder(20, 4, 20, 4));
            panelListaProductos.add(vacio);
        } else {
            for (ItemPedido item : pedido) {
                panelListaProductos.add(crearFilaProducto(item));
                panelListaProductos.add(Box.createVerticalStrut(3));
            }
        }
        panelListaProductos.revalidate();
        panelListaProductos.repaint();
    }

    private JPanel crearFilaProducto(ItemPedido item) {
        JPanel fila = transparente(new BorderLayout(8, 0));
        fila.setBorder(new EmptyBorder(5, 2, 5, 2));
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 62));
        fila.setPreferredSize(new Dimension(260, 62));

        JLabel img = new JLabel();
        img.setPreferredSize(new Dimension(48, 48));
        if (item.getImagenPath() != null) {
            File f = new File(item.getImagenPath());
            if (f.exists()) {
                Image i = new ImageIcon(f.getAbsolutePath()).getImage().getScaledInstance(44, 44, Image.SCALE_SMOOTH);
                img.setIcon(new ImageIcon(i));
            }
        }
        fila.add(img, BorderLayout.WEST);

        JPanel info = transparente();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        JLabel n = new JLabel(item.getNombre());
        n.setFont(new Font("Segoe UI", Font.BOLD, 12));
        JLabel c = textoSec("Q  " + item.getCantidad(), 11);
        info.add(n); info.add(Box.createVerticalStrut(4)); info.add(c);
        fila.add(info, BorderLayout.CENTER);

        JLabel precio = new JLabel(String.format("Q %.2f", item.getSubtotal()));
        precio.setForeground(ROJO);
        precio.setFont(new Font("Segoe UI", Font.BOLD, 12));
        fila.add(precio, BorderLayout.EAST);
        return fila;
    }

    private void seleccionarMetodo(String metodo) {
        this.metodoPago = metodo;
        cardPago.show(panelContenidoPago, metodo);
        estilizarMetodo(btnTarjeta, "TARJETA".equals(metodo));
        estilizarMetodo(btnEfectivo, "EFECTIVO".equals(metodo));
        estilizarMetodo(btnQR, "QR".equals(metodo));
        estilizarMetodo(btnRegalo, "REGALO".equals(metodo));
    }

    private void aplicarCupon() {
        String cod = JOptionPane.showInputDialog(this, "Ingrese el código del cupón:", codigoCupon == null ? "" : codigoCupon);
        if (cod == null) return;
        PagoDAO.CuponInfo info = pagoDAO.validarCupon(cod);
        if (!info.valido) {
            JOptionPane.showMessageDialog(this, info.mensaje, "Cupón", JOptionPane.WARNING_MESSAGE);
            return;
        }
        codigoCupon = cod.trim();
        porcentajeCupon = info.porcentaje;
        actualizarTotales();
        JOptionPane.showMessageDialog(this, String.format("Cupón aplicado: %.0f%% de descuento.", porcentajeCupon));
    }

    private void actualizarTotales() {
        double bruto = 0.0;
        for (ItemPedido i : pedido) bruto += i.getSubtotal();
        descuento = bruto * porcentajeCupon / 100.0;
        total = Math.max(0, bruto - descuento);
        subtotalSinIVA = total / 1.12;
        impuesto = total - subtotalSinIVA;

        String sub = money(subtotalSinIVA);
        String imp = money(impuesto);
        String des = money(descuento);
        String tot = money(total);
        lblSubtotalIzq.setText(sub); lblSubtotalDer.setText(sub);
        lblImpuestoIzq.setText(imp); lblImpuestoDer.setText(imp);
        lblDescuentoIzq.setText(des); lblDescuentoDer.setText(des);
        lblTotalIzq.setText(tot); lblTotalDer.setText(tot); lblTotalGrande.setText(tot);
        calcularCambio();
    }

    private void calcularCambio() {
        if (lblCambio == null || txtEfectivo == null) return;
        try {
            double recibido = Double.parseDouble(txtEfectivo.getText().trim().replace(',', '.'));
            double cambio = recibido - total;
            if (cambio < 0) {
                lblCambio.setForeground(ROJO);
                lblCambio.setText(String.format("Faltan Q %.2f", -cambio));
            } else {
                lblCambio.setForeground(VERDE);
                lblCambio.setText(String.format("Q %.2f", cambio));
            }
        } catch (Exception ex) {
            lblCambio.setForeground(TEXTO_SEC);
            lblCambio.setText("Q 0.00");
        }
    }

    private void procesarPago() {
        if (pedido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay productos para cobrar.", "Pago", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (total <= 0) {
            JOptionPane.showMessageDialog(this, "El total del pedido no es válido.");
            return;
        }

        Double recibido = null;
        Double cambio = null;
        String codigoRegalo = null;

        if ("TARJETA".equals(metodoPago)) {
            if (!validarTarjeta()) return;
        } else if ("EFECTIVO".equals(metodoPago)) {
            try {
                recibido = Double.parseDouble(txtEfectivo.getText().trim().replace(',', '.'));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Ingrese un monto de efectivo válido.");
                return;
            }
            if (recibido < total) {
                JOptionPane.showMessageDialog(this, "El efectivo recibido es insuficiente.");
                return;
            }
            cambio = recibido - total;
        } else if ("QR".equals(metodoPago)) {
            if (!qrConfirmado) {
                JOptionPane.showMessageDialog(this, "Confirme primero que el pago QR fue recibido.");
                return;
            }
        } else if ("REGALO".equals(metodoPago)) {
            codigoRegalo = txtCodigoRegalo.getText().trim();
            double saldo = pagoDAO.consultarSaldoTarjetaRegalo(codigoRegalo);
            if (saldo < total) {
                JOptionPane.showMessageDialog(this, saldo < 0 ? "Tarjeta de regalo no encontrada." : "Saldo insuficiente en la tarjeta de regalo.");
                return;
            }
        }

        boolean factura = chkFactura.isSelected();
        String correo = txtCorreoFactura.getText().trim();
        String nombre = txtNombreFactura.getText().trim();
        String nit = txtNitFactura.getText().trim();
        String direccion = txtDireccionFactura.getText().trim();
        if (factura) {
            if (correo.isEmpty() || !correo.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
                JOptionPane.showMessageDialog(this, "Ingrese un correo válido para la factura.");
                return;
            }
            if (nombre.isEmpty()) nombre = "Consumidor Final";
            if (nit.isEmpty()) nit = "CF";
        }

        btnProcesar.setEnabled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        PagoDAO.ResultadoPago r = pagoDAO.registrarPago(
                idUsuario, pedido, subtotalSinIVA, descuento, total, metodoPago,
                recibido, cambio, codigoRegalo, factura, nombre, nit, correo, direccion);
        btnProcesar.setEnabled(true);
        setCursor(Cursor.getDefaultCursor());

        if (!r.ok) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo registrar el pago:\n" + r.mensaje +
                    "\n\nSi es la primera vez que usa esta versión, ejecute database/actualizacion_pagos.sql.",
                    "Error de pago", JOptionPane.ERROR_MESSAGE);
            return;
        }

        lblPedido.setText(String.format("Pedido #%04d", r.idPedido));
        String msg = "Pago realizado correctamente.\nPedido #" + String.format("%04d", r.idPedido) + "\nTotal: " + money(total);
        if (cambio != null) msg += "\nCambio: " + money(cambio);
        JOptionPane.showMessageDialog(this, msg, "Pago completado", JOptionPane.INFORMATION_MESSAGE);

        if (factura) {
            mostrarFacturaGenerada(r.idPedido, nombre, nit, correo, direccion);
        }

        Cajero cajero = (Cajero) SwingUtilities.getWindowAncestor(this);
        if (cajero != null) cajero.pagoCompletado();
    }

    private boolean validarTarjeta() {
        String numero = txtNumeroTarjeta.getText().replaceAll("\\D", "");
        String titular = txtTitular.getText().trim();
        String fecha = txtFecha.getText().trim();
        String cvv = new String(txtCVV.getPassword()).trim();
        if (numero.length() < 13 || numero.length() > 19) {
            JOptionPane.showMessageDialog(this, "Ingrese un número de tarjeta válido (13 a 19 dígitos).");
            return false;
        }
        if (titular.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre del titular.");
            return false;
        }
        if (!fecha.matches("(0[1-9]|1[0-2])/\\d{2}")) {
            JOptionPane.showMessageDialog(this, "La fecha debe tener formato MM/AA.");
            return false;
        }
        if (!cvv.matches("\\d{3,4}")) {
            JOptionPane.showMessageDialog(this, "El CVV debe tener 3 o 4 dígitos.");
            return false;
        }
        return true;
    }

    private void cancelarPago() {
        int op = JOptionPane.showConfirmDialog(this, "¿Desea volver al menú? La orden seguirá disponible.", "Cancelar pago", JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) {
            Cajero c = (Cajero) SwingUtilities.getWindowAncestor(this);
            if (c != null) c.mostrarVista("PANEL_MENU");
        }
    }

    private void mostrarFacturaGenerada(int idPedido, String nombre, String nit, String correo, String direccion) {
        StringBuilder sb = new StringBuilder();
        sb.append("WcDonald's - FACTURA\n");
        sb.append("Pedido #").append(String.format("%04d", idPedido)).append("\n");
        sb.append("Cliente: ").append(nombre).append("\n");
        sb.append("NIT: ").append((nit == null || nit.isBlank()) ? "CF" : nit).append("\n");
        if (correo != null && !correo.isBlank()) sb.append("Correo: ").append(correo).append("\n");
        if (direccion != null && !direccion.isBlank()) sb.append("Dirección: ").append(direccion).append("\n");
        sb.append("\nDETALLE DE COMPRA\n");
        sb.append(String.format("%-4s %-21s %10s %10s\n", "Cant", "Producto", "P.Unit", "Importe"));
        sb.append("--------------------------------------------------------\n");
        for (ItemPedido item : pedido) {
            String nombreProd = item.getNombre();
            if (nombreProd.length() > 21) nombreProd = nombreProd.substring(0, 21);
            sb.append(String.format("%-4d %-21s %10s %10s\n",
                    item.getCantidad(), nombreProd, money(item.getPrecioUnitario()), money(item.getSubtotal())));
        }
        sb.append("--------------------------------------------------------\n");
        sb.append(String.format("%-30s %10s\n", "Subtotal:", money(subtotalSinIVA)));
        sb.append(String.format("%-30s %10s\n", "Impuestos (12%):", money(impuesto)));
        sb.append(String.format("%-30s %10s\n", "Descuento:", money(descuento)));
        sb.append(String.format("%-30s %10s\n", "TOTAL:", money(total)));

        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        area.setCaretPosition(0);
        area.setBorder(new EmptyBorder(10, 10, 10, 10));
        JScrollPane sp = new JScrollPane(area);
        sp.setPreferredSize(new Dimension(590, 440));
        JOptionPane.showMessageDialog(this, sp, "Factura generada", JOptionPane.INFORMATION_MESSAGE);
    }

    // ---------------- Helpers visuales ----------------
    private PanelRedondeadoSombra tarjeta() {
        PanelRedondeadoSombra p = new PanelRedondeadoSombra();
        p.setBackground(Color.WHITE);
        return p;
    }

    private JPanel transparente() { return transparente(new FlowLayout(FlowLayout.LEFT, 0, 0)); }
    private JPanel transparente(LayoutManager l) { JPanel p = new JPanel(l); p.setOpaque(false); return p; }

    private JLabel titulo(String t, int size) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Segoe UI", Font.BOLD, size));
        l.setForeground(new Color(25, 25, 25));
        return l;
    }

    private JLabel textoSec(String t, int size) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Segoe UI", Font.PLAIN, size));
        l.setForeground(TEXTO_SEC);
        return l;
    }

    private JSeparator separador() {
        JSeparator s = new JSeparator();
        s.setForeground(BORDE);
        s.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        return s;
    }

    private JLabel filaImporte(JPanel padre, String nombre, String valor, boolean fuerte) {
        JPanel fila = transparente(new BorderLayout());
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        JLabel n = new JLabel(nombre);
        n.setFont(new Font("Segoe UI", fuerte ? Font.BOLD : Font.PLAIN, fuerte ? 13 : 12));
        JLabel v = new JLabel(valor);
        v.setFont(new Font("Segoe UI", Font.BOLD, fuerte ? 15 : 12));
        if (fuerte) v.setForeground(ROJO);
        fila.add(n, BorderLayout.WEST); fila.add(v, BorderLayout.EAST);
        padre.add(fila);
        return v;
    }

    private JLabel seccionTitulo(String text) {
        JLabel l = titulo(text, 13);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JLabel seccionTitulo(String text, IconoTipo tipo) {
        JLabel l = tituloConIcono(text, tipo, 13);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JLabel tituloConIcono(String text, IconoTipo tipo, int size) {
        JLabel l = titulo(text, size);
        l.setIcon(new MiniIcon(tipo, Math.max(15, size), ROJO));
        l.setIconTextGap(7);
        return l;
    }

    private JButton botonMetodo(String texto, IconoTipo tipo) {
        JButton b = new JButton(texto);
        b.setIcon(new MiniIcon(tipo, 18, new Color(55, 55, 55)));
        b.setHorizontalTextPosition(SwingConstants.CENTER);
        b.setVerticalTextPosition(SwingConstants.BOTTOM);
        b.setIconTextGap(5);
        b.setFont(new Font("Segoe UI", Font.BOLD, 11));
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setBackground(Color.WHITE);
        b.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDE, 1), new EmptyBorder(8, 4, 8, 4)));
        return b;
    }

    private void estilizarMetodo(JButton b, boolean seleccionado) {
        if (seleccionado) {
            b.setForeground(ROJO);
            b.setBackground(new Color(255, 245, 246));
            b.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(ROJO, 2), new EmptyBorder(7, 3, 7, 3)));
        } else {
            b.setForeground(new Color(45, 45, 45));
            b.setBackground(Color.WHITE);
            b.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDE, 1), new EmptyBorder(8, 4, 8, 4)));
        }
    }

    private JTextField campo(String hint) {
        JTextField f = new JTextField();
        estilizarCampo(f, hint);
        return f;
    }

    private void estilizarCampo(JTextField f, String hint) {
        f.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        f.setBackground(Color.WHITE);
        f.setForeground(new Color(35, 35, 35));
        f.setCaretColor(ROJO_OSCURO);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE),
                new EmptyBorder(8, 10, 8, 10)));
        f.setPreferredSize(new Dimension(180, 36));
        f.setMinimumSize(new Dimension(110, 36));
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        f.setToolTipText(hint);
    }

    private JPanel campoConEtiqueta(String etiqueta, JComponent campo) {
        JPanel p = transparente();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(new EmptyBorder(3, 0, 7, 0));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel l = textoSec(etiqueta, 10);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        if (campo instanceof JTextField || campo instanceof JPasswordField) {
            Dimension pref = campo.getPreferredSize();
            int alto = Math.max(36, pref.height);
            int ancho = Math.max(110, pref.width);
            campo.setPreferredSize(new Dimension(ancho, alto));
            if (campo.getMaximumSize().width >= Integer.MAX_VALUE / 2) {
                campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, alto));
            } else {
                campo.setMaximumSize(new Dimension(campo.getMaximumSize().width, alto));
            }
        }
        p.add(l);
        p.add(Box.createVerticalStrut(4));
        p.add(campo);
        return p;
    }

    private JTextField campoCompacto(String hint, int ancho) {
        JTextField f = campo(hint);
        Dimension d = new Dimension(ancho, 34);
        f.setPreferredSize(d);
        f.setMaximumSize(d);
        return f;
    }

    private JPanel campoConEtiquetaDerecha(String etiqueta, JComponent campo) {
        JPanel p = transparente();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(new EmptyBorder(0, 0, 0, 0));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel l = textoSec(etiqueta, 10);
        l.setAlignmentX(Component.RIGHT_ALIGNMENT);
        p.add(l);
        p.add(Box.createVerticalStrut(4));

        JPanel wrap = transparente(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        wrap.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrap.add(campo);
        p.add(wrap);
        return p;
    }

    private JPanel filaCampoCompacta(String etiqueta, String prefijo, JTextField campo) {
        JPanel fila = transparente(new BorderLayout(12, 0));
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        JLabel l = new JLabel(etiqueta);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        JPanel entrada = transparente(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        JLabel q = new JLabel(prefijo);
        q.setFont(new Font("Segoe UI", Font.BOLD, 13));
        entrada.add(q);
        entrada.add(campo);
        fila.add(l, BorderLayout.WEST);
        fila.add(entrada, BorderLayout.EAST);
        return fila;
    }

    private JPanel panelSuave() {
        JPanel p = new JPanel();
        p.setBackground(new Color(251, 251, 251));
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE),
                new EmptyBorder(14, 14, 14, 14)));
        return p;
    }

    private JButton botonRojo(String texto) {
        JButton b = new JButton(texto);
        b.setForeground(Color.WHITE);
        b.setBackground(ROJO);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setBorder(new EmptyBorder(12, 20, 12, 20));
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        b.setPreferredSize(new Dimension(260, 44));
        return b;
    }

    private JButton botonBorde(String texto) {
        JButton b = new JButton(texto);
        b.setForeground(ROJO_OSCURO);
        b.setBackground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 11));
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(235, 120, 130)), new EmptyBorder(8, 12, 8, 12)));
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        return b;
    }

    private String money(double n) { return String.format("Q %.2f", n); }

    private enum IconoTipo {
        RESUMEN, PAGO, TARJETA, EFECTIVO, QR, REGALO, FACTURA, TOTAL, OPCIONES, SEGURIDAD
    }

    /** Íconos vectoriales simples: evitan los cuadrados vacíos de algunos emojis/fuentes. */
    private static class MiniIcon implements Icon {
        private final IconoTipo tipo;
        private final int size;
        private final Color color;

        MiniIcon(IconoTipo tipo, int size, Color color) {
            this.tipo = tipo;
            this.size = size;
            this.color = color;
        }

        public int getIconWidth() { return size; }
        public int getIconHeight() { return size; }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(Math.max(1.5f, size / 10f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            int s = size;
            switch (tipo) {
                case RESUMEN:
                    g2.drawRoundRect(x + 2, y + 2, s - 5, s - 5, 3, 3);
                    g2.drawLine(x + 5, y + 6, x + s - 5, y + 6);
                    g2.drawLine(x + 5, y + 10, x + s - 5, y + 10);
                    g2.drawLine(x + 5, y + 14, x + s - 8, y + 14);
                    break;
                case PAGO:
                    g2.drawRoundRect(x + 1, y + 4, s - 3, s - 8, 4, 4);
                    g2.fillRect(x + 3, y + 7, s - 6, 3);
                    g2.drawLine(x + 5, y + s - 5, x + s - 7, y + s - 5);
                    break;
                case TARJETA:
                    g2.drawRoundRect(x + 1, y + 3, s - 3, s - 6, 3, 3);
                    g2.fillRect(x + 3, y + 6, s - 6, 3);
                    g2.fillRoundRect(x + 4, y + s - 7, Math.max(4, s / 4), 2, 2, 2);
                    break;
                case EFECTIVO:
                    g2.drawRoundRect(x + 1, y + 4, s - 3, s - 8, 3, 3);
                    g2.drawOval(x + s / 3, y + s / 3, s / 3, s / 3);
                    g2.drawLine(x + 3, y + 7, x + 6, y + 7);
                    g2.drawLine(x + s - 7, y + s - 7, x + s - 4, y + s - 7);
                    break;
                case QR:
                    int q = Math.max(4, s / 4);
                    g2.drawRect(x + 2, y + 2, q, q);
                    g2.drawRect(x + s - q - 2, y + 2, q, q);
                    g2.drawRect(x + 2, y + s - q - 2, q, q);
                    g2.fillRect(x + s / 2, y + s / 2, 3, 3);
                    g2.fillRect(x + s - 6, y + s - 6, 3, 3);
                    break;
                case REGALO:
                    g2.drawRoundRect(x + 2, y + 7, s - 5, s - 9, 3, 3);
                    g2.drawLine(x + s / 2, y + 7, x + s / 2, y + s - 3);
                    g2.drawLine(x + 2, y + 11, x + s - 3, y + 11);
                    g2.drawArc(x + s / 2 - 7, y + 1, 7, 8, 0, 180);
                    g2.drawArc(x + s / 2, y + 1, 7, 8, 0, 180);
                    break;
                case FACTURA:
                    g2.drawRoundRect(x + 3, y + 2, s - 7, s - 4, 3, 3);
                    g2.drawLine(x + s - 8, y + 2, x + s - 3, y + 7);
                    g2.drawLine(x + 6, y + 8, x + s - 7, y + 8);
                    g2.drawLine(x + 6, y + 12, x + s - 8, y + 12);
                    g2.drawLine(x + 6, y + 16, x + s - 10, y + 16);
                    break;
                case TOTAL:
                    g2.drawOval(x + 2, y + 3, s - 6, s - 6);
                    g2.drawLine(x + s / 2, y + 5, x + s / 2, y + s - 7);
                    g2.drawLine(x + s / 2 - 3, y + 8, x + s / 2 + 3, y + 8);
                    g2.drawLine(x + s / 2 - 3, y + s - 10, x + s / 2 + 3, y + s - 10);
                    break;
                case OPCIONES:
                    g2.drawLine(x + 4, y + 5, x + s - 5, y + 5);
                    g2.drawLine(x + 4, y + 10, x + s - 5, y + 10);
                    g2.drawLine(x + 4, y + 15, x + s - 5, y + 15);
                    g2.fillOval(x + s - 8, y + 3, 4, 4);
                    g2.fillOval(x + 7, y + 8, 4, 4);
                    g2.fillOval(x + s / 2 - 2, y + 13, 4, 4);
                    break;
                case SEGURIDAD:
                    int[] xs = {x + s/2, x + s - 3, x + s - 5, x + s/2, x + 4, x + 2};
                    int[] ys = {y + 1, y + 4, y + s - 5, y + s - 2, y + s - 5, y + 4};
                    g2.drawPolygon(xs, ys, xs.length);
                    g2.drawLine(x + s/3, y + s/2, x + s/2, y + s - 6);
                    g2.drawLine(x + s/2, y + s - 6, x + s - 5, y + 6);
                    break;
            }
            g2.dispose();
        }
    }

    private static class SimpleDocumentListener implements DocumentListener {
        private final Runnable r;
        SimpleDocumentListener(Runnable r) { this.r = r; }
        public void insertUpdate(DocumentEvent e) { r.run(); }
        public void removeUpdate(DocumentEvent e) { r.run(); }
        public void changedUpdate(DocumentEvent e) { r.run(); }
    }
}
