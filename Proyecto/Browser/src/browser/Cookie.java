package browser;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Clase para crear una cookie y facilitar la lectura de un cookie recibido
 * @author Marlon Espinoza
 * 
 */
public class Cookie {
    String cookie;
    String id;
    String contenido;
    String exp;
    String dom;
    String path;
    /**
     * 
     * @param cook es la línea del cookie recibido el cual será leído y guardado en sus atributos respectivos
     */
    public Cookie(String cook) {
        this.cookie=cook+";";
        id = cookie.substring(0,cookie.indexOf("="));
        contenido = cookie.substring(cookie.indexOf("=")+1, cookie.indexOf(";"));
        exp();
    }
    /**
     * 
     * @return retorna toda la cadena del cookie mas el delimitador ";"
     */
    public String getCookie() {
        return cookie;
    }
    /**
     * 
     * @return identificador del cookie
     */

    public String getId() {
        return id;
    }
    /**
     * 
     * @return devuelve elcontenido del cookie
     */
    public String getContenido() {
        return contenido;
    }
    /**
     * busca la fecha de expiración de un cookie si tiene y lo guarda en exp 
     */
    public void exp(){
        if(cookie.contains("expires=")){
            String temp;
            temp = cookie.substring(cookie.indexOf("expires=")+8);
            exp = temp.substring(0,temp.indexOf(";"));
            
        }
    }
    
    
}
