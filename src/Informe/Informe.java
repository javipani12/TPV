package Informe;

import Interfaz.VentanaProductos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.swing.*;

/**
 *
 * @author Manuel López y Javier Fernández
 */
public class Informe {
    
    //Impresión del informe
    JasperPrint jp = null;
    //El visor del informe
    JRViewer viewer;
    //Ruta de la plantilla del reporte
    String ficheroReporte = "src\\informes\\Usuarios.jrxml";
    //Parametros Map
    HashMap<String, Object> parameterMap = new HashMap<String, Object>();
    //conexión con la BD
    Connection conexion;
    VentanaProductos ventana;

    public Informe(VentanaProductos ventana) {
        this.ventana = ventana;
    }
    
    public void generarInforme(){
        try {
            // Indicamos el driver utilizado para la conexión
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Inicializamos la cadena de conexion
            this.conexion = DriverManager.getConnection( "jdbc:mysql://localhost/tpv?serverTimezone=UTC", "usuario", "usuario" );
            //Compilamos el fichero de reporte
            JasperReport reporteCompilado = JasperCompileManager.compileReport(ficheroReporte);
            //Establecemos los parametros del reporte
            parameterMap.put("SUBREPORT_DIR", "src\\informes\\");
            //Rellenamos el reporte con los parametros,
            jp = JasperFillManager.fillReport(reporteCompilado, parameterMap,conexion);
        } catch (ClassNotFoundException | SQLException | JRException ex) {
            System.out.println(ex);
        }
        
        viewer = new JRViewer(jp);
        this.ventana.add(viewer);
        this.ventana.revalidate();
    }
}