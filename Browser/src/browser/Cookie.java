package browser;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Cesar Madrid
 */
public class Cookie {
    String cookie;
    String id;
    String contenido;
    String exp;
    String dom;
    String path;
    public Cookie(String cook) {
        this.cookie=cook;
        id = cookie.substring(0,cookie.indexOf("="));
        if(cookie.contains(";"))
            contenido = cookie.substring(cookie.indexOf("=")+1, cookie.indexOf(";"));
        else
            contenido = cookie.substring(cookie.indexOf("=")+1);
        exp();
    }

    public String getCookie() {
        return cookie;
    }
    

    public String getId() {
        return id;
    }

    public String getContenido() {
        return contenido;
    }
    
    public void exp(){
        if(cookie.contains("expires=")){
            String temp;
            temp = cookie.substring(cookie.indexOf("expires=")+1);
            if(cookie.contains(";"))
                exp = temp.substring(0,temp.indexOf(";"));
            
        }
    }
    
    
    
    public void dom(){}
    
}
