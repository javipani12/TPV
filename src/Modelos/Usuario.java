package Modelos;

/**
 *
 * @author Manuel López y Javier Fernández
 */
public class Usuario {
    private int idUsuario;
    private String nombre;
    private String apellidos;
    private String login;
    private int passwd;
    private boolean rol;

    public Usuario() {
        idUsuario = -1;
    }

    public Usuario(String nombre, String apellido, String login, int passwd, boolean rol) {
        this.nombre = nombre;
        this.apellidos = apellido;
        this.login = login;
        this.passwd = passwd;
        this.rol = rol;
    }

    public Usuario(int idUsuario, String nombre, String apellido, String login, int passwd, boolean rol) {
        this(nombre, apellido, login, passwd, rol);
        this.idUsuario = idUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellido) {
        this.apellidos = apellido;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getPasswd() {
        return passwd;
    }

    public void setPasswd(int passwd) {
        this.passwd = passwd;
    }

    public boolean isRol() {
        return rol;
    }

    public void setRol(boolean rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                    "idUsuario=" + idUsuario + ", nombre=" + nombre + 
                    ", apellido=" + apellidos + ", login=" + login + 
                    ", passwd=" + passwd + ", rol=" + rol + 
                '}';
    }
    
    
}
