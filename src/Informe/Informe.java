package Informe;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.swing.*;

/**
 *
 * @author Manuel López y Javier Fernández
 */
public class Informe {
    
    public Informe(int idUsuario) {
        
        String HOST = "localhost";
        String BD = "tpv";
        String USUARIO = "usuario";
        String PASSWORD = "usuario";
        //Conectarnos a la base de datos
        try{
            //Cargamos driver mysql
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            //declaramos el objeto conexion
            Connection conexion = DriverManager.getConnection("jdbc:mysql://"+HOST+"/"+BD+"?serverTimezone=UTC",USUARIO, PASSWORD);
            
            //Ruta del informe
            String reportName = "src\\informes\\InformeVentas.jrxml";
            
            //Llamada a jasperReport
            JasperReport jrcm = JasperCompileManager.compileReport(reportName);
            
            //Creamos un hashmap para pasar los parametros del informe
            HashMap<String, Object> parametros = new HashMap<>();
            
            //Rellenar los parametros para pasarlos al informe
            parametros.put("idUsuario", idUsuario);
            
            //Rellenar los parametros en el informe
            JasperPrint jp = JasperFillManager.fillReport(jrcm, parametros, conexion);
            JRViewer visor = new JRViewer(jp);
            
            //Creamos un frame o ventana
            JFrame jf = new JFrame();
            //Añadir el visor al jframe
            jf.getContentPane().add(visor);
            jf.validate();
            //Tamaño de ventana
            jf.setSize(new Dimension(800,600));
            //Posicion de la pantalla
            jf.setLocation(300, 100);
            //Hacer visible la ventana
            jf.setVisible(true);
            
        }catch(ClassNotFoundException | SQLException | JRException ex){
            System.out.println("Error " + ex.getMessage()); 
        }
    }
}
