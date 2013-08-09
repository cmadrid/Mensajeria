/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package browser;

import java.awt.Insets;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JViewport;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 *
 * @author Cesar Madrid
 */
public class Pestanas extends javax.swing.JPanel {
    String Directorio;
    Pestanas seleccionada = this;
//    int index;
    String Html;
    String Http;
    Navegador.Cargar cargar;
    ArrayList<String> historial = new ArrayList<>();
    int num=-1;
    JTextPane text = new JTextPane();

    /**
     * Creates new form Pestañas
     */    
    public JTextPane getText() {
        return text;
    }

    public void setText(JTextPane text) {
        this.text = text;
    }

    
    //devuelve el tamaño de el historial de esta pestaña
    public int getHistorialSize() {
        return historial.size();
    }

    //añade una nueva pagina al historial de esa pestaña
    public void addPagina(String url) {
        this.historial.add(url);
    }
    
    //borra todas las paginas a partir de la actual
    public void delPags(){
        for(int i=historial.size()-1;i>num;i--)
            historial.remove(i);
    }
    
    //obtiene la pagina actual de la pestaña
    public String getPagina(){
        return historial.get(num);
    }
    
    //obtiene la proxima pagina en la lista
    public String getPaginaS(){
        return historial.get(num+1);
    }
    
    //devuelve la pagina anterior en la lista
    public String getPaginaR(){
        return historial.get(num-1);
    }
    
    //indica sobre que index del historial esta ubicado
    public int getNum() {
        return num;
    }

    public String getHtml() {
        return Html;
    }

    public void setHtml(String Html) {
        this.Html = Html;
    }

    public String getHttp() {
        return Http;
    }

    public void setHttp(String Http) {
        this.Http = Http;
    }
    
    

//    public void setNum(int num) {
//        this.num = num;
//    }
    
    //aumenta en la posicion sobre que elemento estoy ubicado
    public void setNumS() {
        this.num++;
    }
    
    //reduce en uno sobree que elemento estoy ubicado
    public void setNumR() {
        this.num--;
    }

    
//    public void setIndex(int index){
//        this.index=index;
//    }
//    public int getIndex(){
//        return index;
//    }
    public Pestanas() {
        initComponents();
        Directorio=getClass().getResource("").toExternalForm();
        Directorio=(String) Directorio.subSequence(0, Directorio.length()-8);
        System.out.println(Directorio+"asdcc");
        try {
            cerrar.setIcon(new ImageIcon(new URL(Directorio+"imagenes/x.png")));
        } catch (MalformedURLException ex) {
            Logger.getLogger(Pestanas.class.getName()).log(Level.SEVERE, null, ex);
        }
        cerrar.setBorder(null);
        cerrar.setMargin(null);
        
    }
    
    public void setTitle(String titulo){
        title.setText(titulo);
    }
    public String getTitle(){
        return title.getText();
    }
    public void setIcon(String icon){
        if(icon==null)
            icono.setIcon(null);
        else
            try {
                icono.setIcon(new ImageIcon(new URL(icon)));
            } catch (MalformedURLException ex) {
                Logger.getLogger(Pestanas.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    
    
    //funcion que crea un nuevo JtextPane para eliminar todas las propoiedadesadquiridas x alguna pagina antigua
    public void nuevoText(Navegador.Cargar cargar1){
        
        JViewport jv = (JViewport) text.getParent();
        text = new JTextPane();
        text.setContentType("text/html");
        text.setEditable(false);
        jv.setView(text);
        cargar=cargar1;
        
        
         //añadiendo leer links en todas las pestañas
            text.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    
//                   JOptionPane.showMessageDialog( null, e.getURL().toString());
                    String ref[]=e.getDescription().replaceAll("http://", "").split("/");//todo el servidor de la pagina eliminando el protocolo y todo el path

                    //comprobando que sea una direccion valida
                    System.out.println(ref[0]);
                    if(ref[0].contains(".")){//si la direccion no contiene un '.' entonces no es una direccion valida o es una de referencia(no implementada aun)
                       
                       
                        
                        cargar.setUrl(e.getURL().toExternalForm());//asigno el url de la direccion a cargar
                        delPags();//borro todos los links de las paginas posteriores a la que me encontraba
                        addPagina(e.getURL().toExternalForm());//añado la pagina cargada al historial de la pestaña
                        setNumS();//aumento el valor en un sobre en el que me encontraria en la lista de historiales de la pestaña
                        cargar.cargarPag();//cargo la pagina
//                        Adelante.setEnabled(false);//desactivo el boton adelante
                   }
                   else
                    JOptionPane.showMessageDialog( null, "Direcciones de referencia no disponibles");


                }
            }
        });
        
    }
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        title = new javax.swing.JLabel();
        cerrar = new javax.swing.JButton();
        icono = new javax.swing.JLabel();

        title.setText("New Tab");

        cerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cerrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cerrarMouseExited(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(icono, javax.swing.GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(title)
                .addGap(31, 31, 31)
                .addComponent(cerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(icono, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(cerrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    public JButton getCerrar(){
        return cerrar;
    }
    
    private void cerrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cerrarMouseEntered


        try {
            cerrar.setIcon(new ImageIcon(new URL(Directorio+"imagenes/x2.png")));
        } catch (MalformedURLException ex) {
            Logger.getLogger(Pestanas.class.getName()).log(Level.SEVERE, null, ex);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_cerrarMouseEntered

    private void cerrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cerrarMouseExited

        
        try {
            cerrar.setIcon(new ImageIcon(new URL(Directorio+"imagenes/x.png")));
        } catch (MalformedURLException ex) {
            Logger.getLogger(Pestanas.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_cerrarMouseExited

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cerrar;
    private javax.swing.JLabel icono;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}