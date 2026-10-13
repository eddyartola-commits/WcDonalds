package Modelo;

/**
 * DTO sencillo para transportar una línea del carrito entre WcMenu y Pagos.
 */
public class ItemPedido {
    private final int idProducto;
    private final String nombre;
    private final double precioUnitario;
    private final int cantidad;
    private final String imagenPath;

    public ItemPedido(int idProducto, String nombre, double precioUnitario, int cantidad, String imagenPath) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.imagenPath = imagenPath;
    }

    public int getIdProducto() { return idProducto; }
    public String getNombre() { return nombre; }
    public double getPrecioUnitario() { return precioUnitario; }
    public int getCantidad() { return cantidad; }
    public String getImagenPath() { return imagenPath; }
    public double getSubtotal() { return precioUnitario * cantidad; }
}
