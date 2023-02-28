package Modelos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Manuel López y Javier Fernández
 */
public class Productos {
    
    private List<Producto> listaProductos;

    public Productos() {
        listaProductos = new ArrayList();
    }
    
    /**
     * Método para obtener la lista completa de Productos
     * @return List<Producto> - Nos devuelve la lista de Productos
     */
    public List<Producto> obtenerListaProductos(){
        return listaProductos;
    }
    
    /**
     * Método para obtener el tamaño de la lista de Productos
     * @return int - Tamaño de la lista
     */
    public int size(){
        return listaProductos.size();
    }
    
    /**
     * Método para obtener un Producto de la lista por su codProducto
     * @param codProducto int - Código del producto que queremos buscar
     * @return 
     */
    public Producto obtenerProductoPorCodigo(int codProducto){
        Producto producto = new Producto();
        
        for (Producto productoBuscar : listaProductos) {
            if (productoBuscar.getCodProducto() == codProducto) {
                producto = productoBuscar;
            }
        }
        
        return producto;
    }
    
    /**
     * Método para obtener un Producto de la lista por su codProducto
     * @param nombreProducto String - Nombre del producto que queremos buscar
     * @return 
     */
    public Producto obtenerProductoPorNombre(String nombreProducto){
        Producto producto = new Producto();
        
        for (Producto productoBuscar : listaProductos) {
            if (productoBuscar.getNombre().equals(nombreProducto)) {
                producto = productoBuscar;
            }
        }
        
        return producto;
    }
    
    /**
     * Método que nos devuelve el producto que se encuentra en la posición
     * que se recibe como parámetro
     * @param posicion int - Posición sobre la que vamos a buscar
     * @return Producto - Nos devuelve el producto encontrado
     */
    public Producto obtenerProductoPorPosicion( int posicion ) {
        return listaProductos.get(posicion);
    }

    /**
     * Método para eliminar un producto de lista de listaProductos
     * @param codProducto int - ID del producto a eliminar
     * @return boolean - Nos devuelve true si lo ha elimanado,
     * en caso contrario false.
     */
    public boolean borrarProducto( int codProducto ){
        return listaProductos.remove(obtenerProductoPorCodigo(codProducto));
    }
    
    /**
     * Método para añadir un producto a la lista
     * @param producto Producto - Producto que se añadirá a la lista
     * @return boolean - Devuelve true si se ha insertado, false 
     * en caso contrario
     */
    public boolean annadirProducto( Producto producto ){
        return listaProductos.add(producto);
    }
    
    public void clear(){
        listaProductos.clear();
    }
    
}
