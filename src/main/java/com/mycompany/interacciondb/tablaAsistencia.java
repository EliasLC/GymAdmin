
package com.mycompany.interacciondb;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author elias
 */
public class tablaAsistencia {
       private String fecha;
        
        public tablaAsistencia(Date fecha){
            this.fecha = obtenerDia(fecha)+"/"+obtenerMes(fecha)+"/"+obtenerAño(fecha);
        }
        
        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
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
}
