package com.mycompany.interacciondb;
import java.util.Calendar;
import java.util.Date;
/**
 *
 * @author elias
 */
public class TablaTrans  {

    private String fecha,tipo,importe;
  
    
    public TablaTrans(Date fecha,String tipo, float importe){
        this.fecha = String.valueOf(obtenerDia(fecha))+"/"+String.valueOf(obtenerMes(fecha))+"/"+String.valueOf(obtenerAño(fecha));
        this.tipo=tipo; this.importe= String.valueOf(importe);
    }
    
    
    private int obtenerDia(Date date){
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                return  cal.get(Calendar.DAY_OF_MONTH);
            }

            private int obtenerMes(Date date){
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                return  cal.get(Calendar.MONTH)+1;
            }

            private int obtenerAño(Date date){
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                return  cal.get(Calendar.YEAR);
            }
    
      public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }
}
