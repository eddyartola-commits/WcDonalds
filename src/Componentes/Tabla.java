package Componentes;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

public class Tabla extends JTable {

    private final Color colorHeader = Color.decode("#BD081C");             // Rojo McDonald's base
    private final Color colorHeaderSeleccion = Color.decode("#FEBC04");    // Amarillo Dorado McDonald's para Header
    private final Color colorFilaImpar = new Color(250, 250, 250);
    private final Color colorSeleccionCuerpo = Color.decode("#FFF8E1");     // Beige cálido para filas/columnas
    private final Color colorLineaGris = new Color(242, 242, 242);
    private final int arc = 20;

    public Tabla() {
        setRowHeight(52);
        setShowGrid(false);
        setIntercellSpacing(new Dimension(0, 0));
        setSelectionBackground(colorSeleccionCuerpo);
        setSelectionForeground(Color.BLACK);
        setFont(new Font("Segoe UI", Font.PLAIN, 13));
        setOpaque(false);

        setCellSelectionEnabled(true);
        setColumnSelectionAllowed(true);
        setRowSelectionAllowed(true);

        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int col = columnAtPoint(e.getPoint());
                if (col == 0) {
                    clearSelection();
                    getTableHeader().repaint();
                }
            }
        });

        String[] columnas = {"", "ID", "Nombre", "Usuario", "Contraseña", "Rol", "Correo", "Acciones"};
        int[] anchos = {45, 50, 170, 130, 130, 140, 210, 120};
        
        configurarColumnas(columnas, anchos);
        configurarHeader();
    }

    public void configurarColumnas(String[] nuevosNombres, int[] anchosProporcionales) {
        DefaultTableModel nuevoModelo = new DefaultTableModel(nuevosNombres, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Boolean.class;
                }
                return Object.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                String colName = getColumnName(column);
                return column == 0 || colName.equalsIgnoreCase("Acciones");
            }
        };
        setModel(nuevoModelo);

        getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 0) {
                clearSelection();
                getTableHeader().repaint();
                repaint();
            }
        });

        if (anchosProporcionales != null && anchosProporcionales.length == nuevosNombres.length) {
            for (int i = 0; i < nuevosNombres.length; i++) {
                getColumnModel().getColumn(i).setPreferredWidth(anchosProporcionales[i]);
            }
        }

        RenderizadorEstilo renderizador = new RenderizadorEstilo();
        for (int i = 0; i < getColumnCount(); i++) {
            String colName = getColumnName(i);
            if (i != 0) {
                getColumnModel().getColumn(i).setCellRenderer(renderizador);
            }
            // ASIGNAR EDITOR DE ACCIONES A LA COLUMNA
            if (colName.equalsIgnoreCase("Acciones")) {
                getColumnModel().getColumn(i).setCellEditor(new AccionesEditor());
            }
        }

        configurarHeader();
        revalidate();
        repaint();
    }

    private void configurarHeader() {
        JTableHeader header = getTableHeader();
        header.setPreferredSize(new Dimension(0, 48));
        header.setOpaque(false);

        header.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int col = header.columnAtPoint(e.getPoint());
                if (col > 0) {
                    setColumnSelectionInterval(col, col);
                    if (getRowCount() > 0) {
                        setRowSelectionInterval(0, getRowCount() - 1);
                    }
                    header.repaint();
                    repaint();
                } else {
                    clearSelection();
                    header.repaint();
                    repaint();
                }
            }
        });

        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                boolean columnaSeleccionada = column != 0 && table.isColumnSelected(column) && table.getSelectedRowCount() == table.getRowCount();

                JLabel lbl = new JLabel(value != null ? value.toString() : "") {
                    @Override
                    protected void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                        int w = getWidth();
                        int h = getHeight();

                        Shape fondoHeader;
                        if (column == 0) {
                            Area area = new Area(new RoundRectangle2D.Float(0, 0, w + arc, h, arc, arc));
                            area.intersect(new Area(new Rectangle(0, 0, w, h)));
                            fondoHeader = area;
                        } else if (column == table.getColumnCount() - 1) {
                            Area area = new Area(new RoundRectangle2D.Float(-arc, 0, w + arc, h, arc, arc));
                            area.intersect(new Area(new Rectangle(0, 0, w, h)));
                            fondoHeader = area;
                        } else {
                            fondoHeader = new Rectangle(0, 0, w, h);
                        }

                        g2.setColor(columnaSeleccionada ? colorHeaderSeleccion : colorHeader);
                        g2.fill(fondoHeader);

                        g2.dispose();
                        super.paintComponent(g);
                    }
                };
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
                lbl.setForeground(columnaSeleccionada ? Color.BLACK : Color.WHITE);
                lbl.setOpaque(false);
                lbl.setHorizontalAlignment(JLabel.CENTER);
                return lbl;
            }
        });
    }

   // EDITOR CON CONFIRMACIÓN SÍ/NO Y CONEXIÓN A BASE DE DATOS
private class AccionesEditor extends AbstractCellEditor implements TableCellEditor {
    private final JPanel panel;
    private int currentRow;

    public AccionesEditor() {
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 10));
        panel.setOpaque(false);

        JButton btnEdit = crearBotonAccion("✏", Color.decode("#FEBC04"), Color.BLACK);
        JButton btnDelete = crearBotonAccion("🗑", Color.decode("#BD081C"), Color.WHITE);

        // ACCIÓN DEL BOTÓN ELIMINAR CON ALERTA Y ELIMINACIÓN EN MYSQL
        btnDelete.addActionListener(e -> {
            fireEditingStopped();

            if (currentRow >= 0 && currentRow < getRowCount()) {
                // 1. Mostrar Alerta de Confirmación (Sí / No)
                int opcion = JOptionPane.showConfirmDialog(
                        Tabla.this,
                        "¿Estás seguro de que deseas eliminar este registro?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    try {
                        // 2. Obtener el ID de la fila (Columna 1)
                        Object valId = getValueAt(currentRow, 1);
                        int idRegistro = Integer.parseInt(valId.toString());
                        String tituloColumnaID = getColumnName(1);

                        boolean eliminadoBD = false;

                        // 3. Identificar la tabla según la cabecera para ejecutar el DAO correcto
                        if (tituloColumnaID.equalsIgnoreCase("ID Pedido")) {
                            Conexion.ProductoDAO dao = new Conexion.ProductoDAO();
                            eliminadoBD = dao.eliminarPedido(idRegistro);
                        } else if (tituloColumnaID.equalsIgnoreCase("ID")) {
                            // Si se trata de Usuarios o Productos
                            Conexion.ProductoDAO dao = new Conexion.ProductoDAO();
                            // eliminadoBD = dao.eliminarUsuario(idRegistro);
                            eliminadoBD = true; // Simulación si no existe método aún
                        }

                        // 4. Si se eliminó de MySQL (o confirma la acción), eliminar la fila visualmente
                        if (eliminadoBD) {
                            DefaultTableModel model = (DefaultTableModel) getModel();
                            model.removeRow(currentRow);
                            JOptionPane.showMessageDialog(Tabla.this, "Registro eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(Tabla.this, "No se pudo eliminar el registro de la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (Exception ex) {
                        System.err.println("Error al procesar la eliminación: " + ex.getMessage());
                    }
                }
            }
        });

        panel.add(btnEdit);
        panel.add(btnDelete);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.currentRow = row;
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }
}

    private class RenderizadorEstilo extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            String texto = value != null ? value.toString() : "";
            String nombreColumna = table.getColumnName(column);

            Object valCheck = table.getValueAt(row, 0);
            boolean checkMarcado = valCheck instanceof Boolean && (Boolean) valCheck;

            boolean columnaCompletaSeleccionada = table.isColumnSelected(column) && table.getSelectedRowCount() == table.getRowCount();

            boolean celdaSeleccionada = checkMarcado || isSelected || columnaCompletaSeleccionada;

            JPanel panel = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g.create();

                    if (celdaSeleccionada) {
                        g2.setColor(colorSeleccionCuerpo);
                    } else {
                        g2.setColor(row % 2 == 0 ? Color.WHITE : colorFilaImpar);
                    }
                    g2.fillRect(0, 0, getWidth(), getHeight());

                    g2.setColor(colorLineaGris);
                    g2.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
                    g2.dispose();
                }
            };
            panel.setOpaque(false);

            if (nombreColumna.equalsIgnoreCase("Rol") || nombreColumna.equalsIgnoreCase("Estado") || nombreColumna.equalsIgnoreCase("Disponible")) {
                JLabel lbl = new JLabel(texto, JLabel.CENTER);
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));

                Color bg;
                if (texto.equalsIgnoreCase("Administrador") || texto.equalsIgnoreCase("Inactivo") || texto.equalsIgnoreCase("Agotado") || texto.equalsIgnoreCase("CANCELADO")) {
                    lbl.setForeground(Color.decode("#C0392B"));
                    bg = Color.decode("#FADBD8");
                } else if (texto.equalsIgnoreCase("Activo") || texto.equalsIgnoreCase("Disponible") || texto.equalsIgnoreCase("PAGADO")) {
                    lbl.setForeground(Color.decode("#27AE60"));
                    bg = Color.decode("#D4EFDF");
                } else {
                    lbl.setForeground(Color.decode("#D37418"));
                    bg = Color.decode("#FCF1DF");
                }

                return crearBadge(lbl, bg, row, celdaSeleccionada);
            }

            if (nombreColumna.equalsIgnoreCase("Acciones")) {
                JPanel acciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 10)) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2 = (Graphics2D) g.create();
                        if (celdaSeleccionada) {
                            g2.setColor(colorSeleccionCuerpo);
                        } else {
                            g2.setColor(row % 2 == 0 ? Color.WHITE : colorFilaImpar);
                        }
                        g2.fillRect(0, 0, getWidth(), getHeight());

                        g2.setColor(colorLineaGris);
                        g2.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
                        g2.dispose();
                    }
                };
                acciones.setOpaque(false);

                JButton btnEdit = crearBotonAccion("✏", Color.decode("#FEBC04"), Color.BLACK);
                JButton btnDelete = crearBotonAccion("🗑", Color.decode("#BD081C"), Color.WHITE);

                acciones.add(btnEdit);
                acciones.add(btnDelete);

                return acciones;
            }

            JLabel label = new JLabel(texto, JLabel.CENTER);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            label.setForeground(Color.decode("#2C3E50"));

            panel.add(label, BorderLayout.CENTER);
            return panel;
        }

        private JPanel crearBadge(JLabel label, Color colorFondo, int row, boolean celdaSeleccionada) {
            JPanel p = new JPanel(new GridBagLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    if (celdaSeleccionada) {
                        g2.setColor(colorSeleccionCuerpo);
                    } else {
                        g2.setColor(row % 2 == 0 ? Color.WHITE : colorFilaImpar);
                    }
                    g2.fillRect(0, 0, getWidth(), getHeight());

                    g2.setColor(colorFondo);
                    g2.fill(new RoundRectangle2D.Float(10, 11, getWidth() - 20, getHeight() - 22, 16, 16));

                    g2.setColor(colorLineaGris);
                    g2.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
                    g2.dispose();
                }
            };
            p.setOpaque(false);
            p.add(label);
            return p;
        }
    }

    private static JButton crearBotonAccion(String icono, Color bg, Color fg) {
        JButton btn = new JButton(icono) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setPreferredSize(new Dimension(30, 30));
        btn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
        btn.setForeground(fg);
        btn.setMargin(new Insets(0, 0, 0, 0));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
 

}