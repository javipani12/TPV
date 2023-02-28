package Modelos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Manuel López y Javier Fernández
 */
public class Venta {
    private Usuario usuario;
    private Productos productos;
    private List<Integer> unidades;
    double total;

    public Venta() {
    }

    public Venta(Usuario usuario) {
        this.usuario = usuario;
        productos = new Productos();
        unidades = new ArrayList();
        total = 0.0;
    }
    
    public Venta(Usuario usuario, Productos productos) {
        this.usuario = usuario;
        this.productos = productos;
        unidades = new ArrayList();
        total = 0.0;
    }
    
    /**
     * Calcula el precio total de la venta
     * @return double - el precio total
     */
    public double calcularTotal(){
        double total = 0;
        for (int i = 0; i < productos.size(); i++) {
            total += productos.obtenerListaProductos().get(i).getPvp() * unidades.get(i);
        }
        return total;
    }
    
    /**
     * Calcula el precio total de una linea (precio del producto multiplicado
     * por unidades)
     * @param linea int - La linea sobre la que calcular
     * @return double - El total del precio de la línea
     */
    public double calcularTotalLinea(int linea){
        return productos.obtenerListaProductos().get(linea).getPvp() * unidades.get(linea);
    }
    
    /**
     * Genera un código identificador de la venta formado por el id del vendedor,
     * el año, el mes, el día, la hora, los minutos y los segundos en los que se
     * realizó
     * @return int - El código identificador de la venta
     */
    public long generaCodigo(){
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        int anno = fechaHoraActual.getYear();
        String mes = normalizarCifras(fechaHoraActual.getMonthValue());
        String dia = normalizarCifras(fechaHoraActual.getDayOfMonth());
        String hora = normalizarCifras(fechaHoraActual.getHour());
        String minutos = normalizarCifras(fechaHoraActual.getMinute());
        String segundos = normalizarCifras(fechaHoraActual.getSecond());
        String codigo = String.valueOf(""+(anno-2000)+mes+dia+hora+minutos+segundos);
        System.out.println(codigo);
        return Long.parseLong(codigo);
    }
    
    /**
     * Método para añadir el 0 delante a las fechas u horas menores que 10.
     * @param numero int - el número a normalizar
     * @return int - el número normalizado, con un 0 delante si era menor a 10 o
     * el original en caso contrario
     */
    public String normalizarCifras(int numero){
        String numeroNormalizado = String.valueOf(numero);
        if(numero<10){
            numeroNormalizado = "0"+numero;
        }
        
        return numeroNormalizado;
    }
    
    /**
     * Método para insertar un producto en la lista de Productos y la cantidad
     * deseada en la lista de cantidades
     * @param producto Producto - Producto seleccionado
     * @param cantidad int - Cantidad indicada
     */
    public void annadirProductoALista(Producto producto, int cantidad){
        // Si la lista de Productos no contiene el producto, añade dicho 
        // Producto y la cantidad indicada
        if(!productos.obtenerListaProductos().contains(producto)){
            productos.annadirProducto(producto);
            unidades.add(cantidad);
        } else{
            // En caso de que sí exista, en la posición que ocupa el producto
            // en la lista de cantidades, sumamos a la cantidad que ya había
            // la cantidad que se pasa como parámetro
            int posicion = productos.obtenerListaProductos().indexOf(producto);
            int cantidadTotal = unidades.get(posicion) + cantidad;
            
            // Comprobamos primero que la cantidadTotal no sea mayor que el stock
            if (cantidadTotal > producto.getStock()) {
                cantidadTotal = producto.getStock();
            }
            
            unidades.set(posicion, cantidadTotal);
        }
    }
    
    public void eliminarProductoDeLista(Producto producto, int cantidad){
        int posicion = productos.obtenerListaProductos().indexOf(producto);
        int cantidadTotal = unidades.get(posicion) - cantidad;
        
        unidades.set(posicion, cantidadTotal);
        // Si se han eliminado todas las unidades del producto, se elimina de la lista
        if(cantidadTotal == 0){
            unidades.remove(posicion);
            productos.borrarProducto(producto.getCodProducto());
        }
    }
    
    public void reiniciarVenta(){
        this.productos.clear();
        this.unidades.clear();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Productos getProductos() {
        return productos;
    }

    public void setProductos(Productos productos) {
        this.productos = productos;
    }

    public List<Integer> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<Integer> unidades) {
        this.unidades = unidades;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    @Override
    public String toString() {
        return "Ventas{" 
                    + "usuario=" + usuario + ", productos=" + productos +
                    ", precios=" + unidades + 
                '}';
    }
    
    
}
