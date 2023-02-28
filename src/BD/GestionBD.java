package BD;

import Modelos.Producto;
import Modelos.Productos;
import Modelos.Usuario;
import Modelos.Usuarios;
import Modelos.Venta;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Manuel López y Javier Fernández
 */
public class GestionBD {
    
    // Atributos
    private final String HOST;
    private final String USUARIO;
    private final String PASSWORD;
    private final String BD;
    private Connection conexion;

    public GestionBD(String HOST, String USUARIO, String PASSWORD, String BD) {
        this.HOST = HOST;
        this.USUARIO = USUARIO;
        this.PASSWORD = PASSWORD;
        this.BD = BD;
    }
    

    // ------------- CONEXIÓN Y DESCONEXIÓN BD ---------------//
    
    /**
     * Método para realizar la conexión con la BD
     * @return - boolean - Devuelve true si se ha conectado, en caso contrario
     * false.
     */
    private boolean conectar(){
        
        boolean isConectado = true;
        
        try {
            // Indicamos el driver utilizado para la conexión
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Inicializamos la cadena de conexion
            this.conexion = DriverManager.getConnection( "jdbc:mysql://" + HOST + "/" + BD + "?serverTimezone=UTC", USUARIO, PASSWORD );
            
        } catch(ClassNotFoundException e) {
            System.err.println("Error al cargar el driver MySQL, " + e.getMessage());
            isConectado = false;
            
        }  catch(SQLException e) {
            System.err.println("Error de conexión, " + e.getMessage());
            isConectado = false;
            
        }
        
        return isConectado;
        
    }
    
    /**
     * Método para finalizar la conexión
     * @return boolean - Devulve true si se ha finalizado la conexión,
     * false en caso contrario.
     */
    private boolean desconectar(){
        
        boolean isDesconectado = true;
        
        try {
            this.conexion.close();
        } catch(SQLException e) {
            System.err.println("Error en la desconexión, " + e.getMessage());
            isDesconectado = false;
        }
        
        return isDesconectado;
        
    }
    
    // ------------- FIN CONEXIÓN Y DESCONEXIÓN BD ---------------//
    
    
    
    // ----------------- MÉTODOS PRODUCTO/S --------------------- //
    
    
    /**
     * Método para insertar un Producto en la BD
     * @param producto Producto - producto a insertar
     * @return boolean Devuelve true si se ha insertado, false en
     * caso contrario
     */
    public boolean insertarProducto( Producto producto ) throws FileNotFoundException {
        FileInputStream fis;
        boolean insertado = false;
        
        // Nos conectamos a la BD
        conectar();
        
        // Preparamos la cadena de inserción
        try {
            PreparedStatement ps = conexion.prepareStatement(
                    "INSERT INTO productos"
                            + " VALUES (NULL, ?, ?, ?, ?)"
            );
            
            // Enlazamos los valores con su posición correspondiente
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPvp());
            ps.setInt(3, producto.getStock());
            
            fis = new FileInputStream(producto.getImagen().toString());
            ps.setBinaryStream(4, fis);
            
            // Ejecutamos la sentencia
            if (ps.execute()) {
                insertado = true;
            }
            
            // Nos desconectamos
            desconectar();
            
        } catch (SQLException ex) {
            System.err.println("Error en el método insertarProducto(), " +
                    ex.getMessage()
            );
        }
        
        return insertado;
    }
    
    /**
     * Método para borrar un producto de la BD
     * @param producto Producto - Producto a borrar de la BD
     * @return boolean - Retorna true si lo ha borrado, en caso contrario
     * retornará false.
     */
    public boolean borrarProducto( Producto producto ){
        
        boolean borrado = false;
        
        conectar();
        
        try {
            PreparedStatement ps = this.conexion.prepareStatement(
                    "DELETE FROM productos "
                            + "WHERE cod_producto = ?"
            );
            
            ps.setInt(1, producto.getCodProducto());
            
            if (ps.execute()) {
                borrado = ps.execute();
            }
            
            desconectar();
            
        } catch (SQLException ex) {
            System.err.println("Error en el método borrarProducto(), " + 
                    ex.getMessage()
            );
        }
        
        return borrado;
        
    }
    
    /**
     * Método para actualizar los datos de un Producto
     * @param productoOld Producto - Producto al que actualizaremos los datos
     * @param productoNew Producto - Nuevos datos del Producto
     * @return boolean - Devuelve true si lo ha podido actualizar, en caso
     * contrario devolverá false
     */
    public boolean modificarProducto( Producto productoOld, Producto productoNew ) {
        FileInputStream fis;
        boolean modificado = false;
        PreparedStatement ps;
        
        conectar();
        
        try {
            
            if (productoNew.getImagen() == null) {
                ps = this.conexion.prepareStatement(
                    "UPDATE productos "
                            + "SET nombre = ?, pvp = ?, stock = ? "
                            + "WHERE cod_producto = ?"
                );
                
                ps.setString(1, productoNew.getNombre());
                ps.setDouble(2, productoNew.getPvp());
                ps.setInt(3, productoNew.getStock());
                ps.setInt(4, productoOld.getCodProducto());
                
                ps.executeUpdate();
                modificado = true;

                desconectar();
                
            } else {
                ps = this.conexion.prepareStatement(
                    "UPDATE productos "
                            + "SET nombre = ?, pvp = ?, stock = ?, "
                            + "imagen = ? "
                            + "WHERE cod_producto = ?"
                );
                
                ps.setString(1, productoNew.getNombre());
                ps.setDouble(2, productoNew.getPvp());
                ps.setInt(3, productoNew.getStock());


                fis = new FileInputStream(productoNew.getImagen().toString());
                ps.setBinaryStream(4, fis);
                ps.setInt(5, productoOld.getCodProducto());
                
                ps.executeUpdate();
                modificado = true;

                desconectar();
            }
            
        } catch (SQLException ex) {
            System.err.println("Error en el método modificarProducto(), " +
                    ex.getMessage()
            );
        } catch (FileNotFoundException ex) {
            System.err.println("Error al cargar la imagen en modificarProducto(), "
                    + ex.getMessage()
            );
        }
        
        return modificado;
        
    }
    
    /**
     * Método para listar todos los productos de la tabla productos
     * @return Productos - Nos devuelve una lista con todos los productos
     * de la tabla.
     * @throws java.io.IOException
     */
    public Productos listarProductos() throws IOException{
        
        int blobLength;
        byte[] blobAsBytes;
        Productos listaProductos = new Productos();
        
        conectar();
        
        try {
            PreparedStatement ps = this.conexion.prepareStatement(
                    "SELECT * FROM productos"
            );
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                Producto producto = new Producto();
                producto.setCodProducto(rs.getInt(1));
                producto.setNombre(rs.getString(2));
                producto.setPvp(rs.getDouble(3));
                producto.setStock(rs.getInt(4));
                
                Blob blob = rs.getBlob(5);
                blobLength = (int) blob.length();
                blobAsBytes = blob.getBytes(1, blobLength);
                final BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(blobAsBytes));
                producto.setImagen(new ImageIcon(bufferedImage));
                                
                listaProductos.annadirProducto(producto);
            }
            
            desconectar();
        } catch (SQLException ex) {
            System.err.println("Error en listarProductos(), " + ex.getMessage());
        }  
        
        return listaProductos;
        
    }
    
    
    // --------------- FIN MÉTODOS PRODUCTO/S --------------------- //
    
    
    
    // --------------- MÉTODOS USUARIO/S --------------------- //

    /**
     * Método para insertar un Usuario en la BD
     * @param usuario Usuario - Usuario a insertar
     * @return boolean - Devuelve true si se ha insertado, en caso contrario
     * false.
     */
    public boolean insertarUsuario( Usuario usuario ) {
        boolean insertado = false;
        
        // Nos conectamos a la BD
        conectar();
        
        try {
            // Preparamos la cadena de inserción
            PreparedStatement ps = conexion.prepareStatement(
                    "INSERT INTO usuarios"
                    + " VALUES ( NULL, ?, ?, ?, ?, ? )"
            );
            
            // Enlazamos los valores a la sentencia en su posición correspondiente
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getLogin());
            ps.setInt(4, usuario.getPasswd());
            ps.setBoolean(5, usuario.isRol());
            
            // Ejecutamos la sentencia
            if( ps.execute() ) {
                insertado = true;
            }
            
            // Nos desconectamos de la BD
            desconectar();
            
        } catch (SQLException ex) {
            System.err.println("Error en el método insertarUsuario()" + 
                    ex.getMessage()
            );
        }
        
        return insertado;
    }
    
    /**
     * Método para borrar un Usuario de la BD
     * @param usuario Usuario - Usuario a borrar de la BD
     * @return boolean - Retorna true si lo ha borrado, en caso contrario
     * retornará false.
     */
    public boolean borrarUsuario( Usuario usuario ){
        
        boolean borrado = false;
        
        conectar();
        
        try {
            PreparedStatement ps = this.conexion.prepareStatement(
                    "DELETE FROM usuarios WHERE id = ?"
            );
            
            ps.setInt(1, usuario.getIdUsuario());
            
            if (ps.execute()) {
                borrado = ps.execute();
            }
            
            desconectar();
            
        } catch (SQLException ex) {
            System.err.println("Error en el método borrarUsuario(), " + 
                    ex.getMessage()
            );
        }
        
        return borrado;
        
    }
    
    /**
     * Método para actualizar los datos de un Usuario
     * @param usuarioOld Usuario - Usuario al que actualizaremos los datos
     * @param usuarioNew Usuario - Nuevos datos del usuario
     * @return boolean - Devuelve true si lo ha podido actualizar, en caso
     * contrario devolverá false
     */
    public boolean modificarUsuario( Usuario usuarioOld, Usuario usuarioNew ) {
        
        boolean modificado = false;
        
        conectar();
        
        try {
            PreparedStatement ps = this.conexion.prepareStatement(
                    "UPDATE usuarios "
                            + "SET nombre = ?, apellidos = ?, "
                            + "login = ?, passwd = ?, rol = ? "
                            + "WHERE id = ?"
            );
            
            ps.setString(1, usuarioNew.getNombre());
            ps.setString(2, usuarioNew.getApellidos());
            ps.setString(3, usuarioNew.getLogin());
            ps.setInt(4, usuarioNew.getPasswd());
            ps.setBoolean(5, usuarioNew.isRol());
            ps.setInt(6, usuarioOld.getIdUsuario());
            
            if (ps.execute()) {
                modificado = true;
            }
            
            desconectar();
            
        } catch (SQLException ex) {
            System.err.println("Error en el méotodo modificarUsuario(), " + 
                    ex.getMessage()
            );
        }
        
        return modificado;
        
    }
    
    /**
     * Método para listar todos los usuarios de la tabla usuarios
     * @return Usuarios - Nos devuelve una lista con todos los usuarios
     * de la tabla.
     */
    public Usuarios listarUsuarios(){
        
        Usuarios listaUsuarios = new Usuarios();
        
        conectar();
        
        try {
            PreparedStatement ps = this.conexion.prepareStatement(
                    "SELECT * FROM usuarios"
            );
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getInt(1), 
                    rs.getString(2), 
                    rs.getString(3), 
                    rs.getString(4),
                    rs.getInt(5),
                    rs.getBoolean(6)
                );
                
                listaUsuarios.annadirUsuario(usuario);
            }
            desconectar();
        } catch (SQLException ex) {
            System.err.println("Error en el método listarUsuarios(), " + 
                    ex.getMessage()
            );
        }  
        
        return listaUsuarios;
        
    }
    
    /**
     * Método para comprobar si un determinado usuario existe en la tabla usuarios
     * @param login String - Login del usuario
     * @param passwd int - Contraseña del usuario
     * @return boolean - Si existe el usuario nos devuelve true, en caso 
     * contrario false
     */
    public boolean comprobarUsuario( String login, int passwd ){
        boolean existe = false;
        conectar();
        
        try {
            PreparedStatement ps = this.conexion.prepareStatement(
                    "SELECT * FROM usuarios WHERE login = ? "
                            + "AND passwd = ?"
            );
            
            ps.setString(1, login);
            ps.setInt(2, passwd);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                if (rs.getString(1) != null) {
                    existe = true;
                }
            }
            
            desconectar();
        } catch (SQLException ex) {
            System.err.println("Error en el método comprobarUsuario(), " + 
                    ex.getMessage()
            );
        }  
        
        return existe;
        
    }
    
    /**
     * Método para obtener un usuario de la BBDD
     * @param login String - Login del usuario
     * @param passwd int - Contraseña del usuario
     * @return Usuario - Nos devuelve el usuario encontrado
     */
    public Usuario obtenerUsuario( String login, int passwd ){
        Usuario user = new Usuario();
        
        conectar();
        
        try {
            PreparedStatement ps = this.conexion.prepareStatement(
                    "SELECT * FROM usuarios WHERE login = ? "
                            + "AND passwd = ?"
            );
            
            ps.setString(1, login);
            ps.setInt(2, passwd);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                user.setIdUsuario(rs.getInt(1));
                user.setNombre(rs.getString(2));
                user.setApellidos(rs.getString(3));
                user.setLogin(rs.getString(4));
                user.setPasswd(rs.getInt(5));
                user.setRol(rs.getBoolean(6));
            }
            
            desconectar();
        } catch (SQLException ex) {
            System.err.println("Error en el método obtenerUsuario(), " + 
                    ex.getMessage()
            );
        }  
        
        return user;
        
    }
    
    // ------------- FIN MÉTODOS USUARIO/S ------------------ //
    
    
    
    // ------------- MÉTODOS VENTAS ------------------ //
    

    /**
     * Método para insertar una Venta en la BD
     * @param linea int - la linea del ticket (indice)
     * @param venta Venta - Venta que insertaremos en la BD
     * @param codigo int - el código identificador de la venta
     * @return boolean - Devuelve true si se ha podido insertar, false si no
     */
    public boolean insertarVenta( int linea, Venta venta, long codigo ) {
        boolean insertado = false;
        
        // Nos conectamos a la BD
        conectar();
        
        try {
            // Preparamos la cadena de inserción
            PreparedStatement ps = conexion.prepareStatement(
                    "INSERT INTO ventas"
                    + " VALUES ( ?, ?, ?, ?, ?, ?, NOW() )"
            );
            
            // Enlazamos los valores a la sentencia en su posición correspondiente
            ps.setLong(1, codigo);
            ps.setInt(2, linea);
            ps.setInt(3, venta.getUsuario().getIdUsuario());
            ps.setInt(4, venta.getProductos().obtenerListaProductos().get(linea).getCodProducto());
            ps.setInt(5, venta.getUnidades().get(linea));
            ps.setDouble(6, venta.calcularTotalLinea(linea));
            
            // Ejecutamos la sentencia
            ps.execute();
            insertado = true;
            
            // Nos desconectamos de la BD
            desconectar();
            
        } catch (SQLException ex) {
            System.err.println("Error en el método insertarVenta()" + 
                    ex.getMessage()
            );
        }
        
        return insertado;
    }
    
    /**
     * Actualiza el stock restándole las unidades vendidas
     * @param unidades int - Las unidades vendidas a restar
     * @param codProducto int - El código del producto a actualizar
     * @return boolean - Devuelve true si se ha podido actualizar, false si no
     */
    public boolean actualizarStock(int unidades, int codProducto){
        boolean modificado = false;

        conectar();

        try {
            PreparedStatement ps = this.conexion.prepareStatement(
                    "UPDATE productos "
                            + "SET stock = ? "
                            + "WHERE cod_producto = ?"
            );

            ps.setInt(1, unidades);
            ps.setInt(2, codProducto);

            if (ps.execute()) {
                modificado = true;
            }

            desconectar();

        } catch (SQLException ex) {
            System.err.println("Error en el méotodo actualizarStock(), " + 
                    ex.getMessage()
            );
        }

        return modificado;
        
    }
}
