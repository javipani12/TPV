package Modelos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Manuel López y Javier Fernández
 */
public class Usuarios {
    
    private List<Usuario> listaUsuarios;

    public Usuarios() {
        listaUsuarios = new ArrayList();
    }
    
    /**
     * Método para obtener la lista completa de Usuarios
     * @return List<Usuario> - Nos devuelve la lista de Usuarios
     */
    public List<Usuario> obtenerListaUsuarios(){
        return listaUsuarios;
    }
    
    /**
     * Método para obtener el tamaño de la lista de Usuarios
     * @return int - Tamaño de la lista
     */
    public int size(){
        return listaUsuarios.size();
    }
    
    /**
     * Método para obtener un usuario de la lista por su idUsuario
     * @param idUsuario int - Código del usuario que queremos buscar
     * @return 
     */
    public Usuario obtenerUsuarioPorCodigo(int idUsuario){
        Usuario Usuario = new Usuario();
        
        for (Usuario UsuarioBuscar : listaUsuarios) {
            if (UsuarioBuscar.getIdUsuario() == idUsuario) {
                Usuario = UsuarioBuscar;
            }
        }
        
        return Usuario;
    }
    
    /**
     * Método para obtener un usuario de la lista por su nombreUsuario y login
     * @param nombreUsuario String - Nombre del usuario que queremos buscar
     * @param login String - Login del usuario que queremos buscar
     * @return 
     */
    public Usuario obtenerUsuarioPorNombre(String nombreUsuario, String login){
        Usuario usuario = new Usuario();
        
        for (Usuario usuarioBuscar : listaUsuarios) {
            if (usuarioBuscar.getNombre().equals(nombreUsuario) &&
                    usuarioBuscar.getLogin().equals(login)) 
            {
                usuario = usuarioBuscar;
            }
        }
        
        return usuario;
    }
    
    /**
     * Método que nos devuelve el Usuario que se encuentra en la posición
     * que se recibe como parámetro
     * @param posicion int - Posición sobre la que vamos a buscar
     * @return Usuario - Nos devuelve el Usuario encontrado
     */
    public Usuario obtenerUsuarioPorPosicion( int posicion ) {
        return listaUsuarios.get(posicion);
    }

    /**
     * Método para eliminar un usuario de lista de listaUsuarios
     * @param codUsuario int - ID del usuario a eliminar
     * @return boolean - Nos devuelve true si lo ha elimanado,
     * en caso contrario false.
     */
    public boolean borrarUsuario( int codUsuario ){
        return listaUsuarios.remove(obtenerUsuarioPorCodigo(codUsuario));
    }
    
    /**
     * Método para añadir un usuario a la lista
     * @param usuario Usuario - Usuario que se añadirá a la lista
     * @return boolean - Devuelve true si se ha insertado, false 
     * en caso contrario
     */
    public boolean annadirUsuario( Usuario usuario ){
        return listaUsuarios.add(usuario);
    }
    
}
