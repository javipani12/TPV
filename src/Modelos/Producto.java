package Modelos;

import javax.swing.ImageIcon;

/**
 *
 * @author Manuel López y Javier Fernández
 */
public class Producto {
    private int codProducto;
    private String nombre;
    private double pvp;
    private int stock;
    private ImageIcon imagen;

    public Producto() {
        codProducto = -1;
        imagen = null;
    }

    public Producto(String nombre, double pvp, int stock, ImageIcon imagen) {
        this.nombre = nombre;
        this.pvp = pvp;
        this.stock = stock;
        this.imagen = imagen;
    }

    public Producto(int codProducto, String nombre, double pvp, int stock, ImageIcon imagen) {
        this(nombre, pvp, stock, imagen);
        this.codProducto = codProducto;
    }

    public int getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(int codProducto) {
        this.codProducto = codProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPvp() {
        return pvp;
    }

    public void setPvp(double pvp) {
        this.pvp = pvp;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public ImageIcon getImagen() {
        return imagen;
    }

    public void setImagen(ImageIcon imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Producto{" + 
                    "codProducto=" + codProducto + ", nombre=" + nombre + 
                    ", pvp=" + pvp + ", stock=" + stock + ", imagen=" + imagen +
                '}';
    }
    
}
