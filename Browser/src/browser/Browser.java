/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package browser;

/**
 *
 * @author Cesar Madrid
 */
public class Browser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String prueba = "hola probando /este metodo de encontrar / un slash/ xD xD//";
        System.out.println(prueba.indexOf("/"));
        System.out.println(prueba.substring(prueba.indexOf("/")));
        Navegador nav = new Navegador();
        
        nav.setVisible(true);
        // TODO code application logic here
    }
}
