package Componentes;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

public class Tabla extends JTable {

    private final Color colorHeader = Color.decode("#BD081C");       // Rojo McDonald's
    private final Color colorFilaImpar = new Color(250, 250, 250);
    private final Color colorSeleccionRojo = new Color(255, 235, 238); // Rojo claro al seleccionar
    private final Color colorLineaGris = new Color(242, 242, 242);
    private final int arc = 20;

    public Tabla() {
        setRowHeight(52);
        setShowGrid(false);
        setIntercellSpacing(new Dimension(0, 0));
        setSelectionBackground(colorSeleccionRojo);
        setSelectionForeground(Color.BLACK);
        setFont(new Font("Segoe UI", Font.PLAIN, 13));
        setOpaque(false);

        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Modelo de datos con 8 columnas
        String[] columnas = {"", "ID", "Nombre", "Usuario", "Contraseña", "Rol", "Correo", "Acciones"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Boolean.class; // Checkbox nativo
                return Object.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 7; // Editable checkbox y botones de acción
            }
        };
        setModel(model);

        // Anchos proporcionales equilibrados
        getColumnModel().getColumn(0).setPreferredWidth(45);  // Checkbox
        getColumnModel().getColumn(1).setPreferredWidth(50);  // ID
        getColumnModel().getColumn(2).setPreferredWidth(170); // Nombre
        getColumnModel().getColumn(3).setPreferredWidth(130); // Usuario
        getColumnModel().getColumn(4).setPreferredWidth(130); // Contraseña
        getColumnModel().getColumn(5).setPreferredWidth(140); // Rol
        getColumnModel().getColumn(6).setPreferredWidth(210); // Correo
        getColumnModel().getColumn(7).setPreferredWidth(120); // Acciones

        // 1. CABECERA ROJA CON TÍTULOS CENTRADOS Y BORDES REDONDEADOS SUPERIORES
        JTableHeader header = getTableHeader();
        header.setPreferredSize(new Dimension(0, 48));
        header.setOpaque(false);
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
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

                        g2.setColor(colorHeader);
                        g2.fill(fondoHeader);

                        g2.dispose();
                        super.paintComponent(g);
                    }
                };
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
                lbl.setForeground(Color.WHITE);
                lbl.setOpaque(false);
                lbl.setHorizontalAlignment(JLabel.CENTER); // ALINEACIÓN CENTRAL DE CABECERAS
                return lbl;
            }
        });

        // Aplicar Renderizador Personalizado
        RenderizadorEstilo renderizador = new RenderizadorEstilo();
        for (int i = 1; i < getColumnCount(); i++) {
            getColumnModel().getColumn(i).setCellRenderer(renderizador);
        }
    }

    private class RenderizadorEstilo extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            String texto = value != null ? value.toString() : "";

            JPanel panel = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g.create();
                    
                    if (isSelected) {
                        g2.setColor(colorSeleccionRojo);
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

            // COLUMNA 5: ROL (Badge Redondeado)
            if (column == 5) {
                JLabel lbl = new JLabel(texto, JLabel.CENTER);
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));

                Color bg;
                if (texto.equalsIgnoreCase("Administrador")) {
                    lbl.setForeground(Color.decode("#C0392B"));
                    bg = Color.decode("#FADBD8");
                } else {
                    lbl.setForeground(Color.decode("#D37418"));
                    bg = Color.decode("#FCF1DF");
                }

                return crearBadge(lbl, bg, row, isSelected);
            }

            // COLUMNA 7: ACCIONES (Botones Editar y Eliminar)
            if (column == 7) {
                JPanel acciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 10)) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2 = (Graphics2D) g.create();
                        if (isSelected) {
                            g2.setColor(colorSeleccionRojo);
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

            // CELDAS DE TEXTO (ID, Nombre, Usuario, Contraseña, Correo) -> TODAS CENTRADAS
            JLabel label = new JLabel(texto, JLabel.CENTER);
            label.setFont(new Font("Segoe UI", column == 2 ? Font.BOLD : Font.PLAIN, 13));
            label.setForeground(Color.decode("#2C3E50"));

            panel.add(label, BorderLayout.CENTER);
            return panel;
        }

        private JPanel crearBadge(JLabel label, Color colorFondo, int row, boolean isSelected) {
            JPanel p = new JPanel(new GridBagLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    if (isSelected) {
                        g2.setColor(colorSeleccionRojo);
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

        private JButton crearBotonAccion(String icono, Color bg, Color fg) {
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
}