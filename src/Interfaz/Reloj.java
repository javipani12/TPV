package Interfaz;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Manuel López y Javier Fernández
 */
public class Reloj extends Thread {
    
    private static LocalDateTime fechaHora;
    private VentanaProductos ventanaProductos;
    
    public Reloj(VentanaProductos ventanaProductos){
        this.ventanaProductos = ventanaProductos;
    }
    
    @Override
    public void run(){
        while(true){
            fechaHora = LocalDateTime.now();
            int anno = fechaHora.getYear();
            int mes = fechaHora.getMonthValue();
            int dia = fechaHora.getDayOfMonth();
            int horas = fechaHora.getHour();
            int minutos = fechaHora.getMinute();
            int segundos = fechaHora.getSecond();
            ventanaProductos.getjLabelFechaHora().setText(dia+"/"+mes+"/"+anno+" "+horas+":"+minutos+":"+segundos);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Reloj.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    public static LocalDateTime getFechaHora() {
        return fechaHora;
    }
    
    
}
