/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package browser;

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
    Cargar cargar;
    String server;
    ArrayList<String> historial = new ArrayList<>();
    int num=-1;
    JTextPane text;
    Navegador nav;

    /**
     * Creates new form Pestañas
     */    
    
   


    public Pestanas(Navegador navegador) {
        nav=navegador;
        initComponents();
        Directorio=getClass().getResource("").toExternalForm();
        Directorio=(String) Directorio.subSequence(0, Directorio.length()-8);
//        try {
////            cerrar.setIcon(new ImageIcon(new URL(Directorio+"imagenes/x.png")));
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(Pestanas.class.getName()).log(Level.SEVERE, null, ex);
//        }
        cerrar.setBorder(null);
        cerrar.setMargin(null);
        
    }
    
    public void setServer(String server){
        this.server=server;
    }
    
     public void setCargar(Cargar cargar){
        this.cargar = cargar;
    }
    
    public Cargar getCargar(){
        return cargar;
    }
    
    public boolean cargando(){
        return cargar.cargando();
    }
    
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
    
    public void setPagina(String url){
        this.historial.set(num,url);
    }
    
    //borra todas las paginas a partir de la actual
    public void delPags(){
        for(int i=historial.size()-1; i>num; i--)
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
    public void nuevoText(){
        
        JViewport jv = (JViewport) text.getParent();
        text = new JTextPane();
        text.setContentType("text/html");
        text.setEditable(false);
        jv.setView(text);
        
        
         //añadiendo leer links en todas las pestañas
            text.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
//                   JOptionPane.showMessageDialog( null, server+e.getDescription());
                    String ref[]=e.getDescription().replaceAll("http://", "").split("/");//todo el servidor de la pagina eliminando el protocolo y todo el path
                    String url;
                    
                    if(e.getDescription().startsWith("/")&&!e.getDescription().startsWith("//"))
                        url= server+e.getDescription();//asigno el url de la direccion a cargar
//                    
                    else
                        url=e.getDescription();
                    
                    
                    if(e.getDescription().startsWith("//")){
                        url=e.getDescription();
                        
                        while(url.startsWith("/"))
                        {
                            url =url.substring(1);
                        }
                    }
                    
                    
                    cargar.setUrl(url);//asigno el url de la direccion a cargar
                    
                    delPags();//borro todos los links de las paginas posteriores a la que me encontraba
                    addPagina(url);//añado la pagina cargada al historial de la pestaña
                    setNumS();//aumento el valor en un sobre en el que me encontraria en la lista de historiales de la pestaña
                    cargar.cargarPag();//cargo la pagina
//                        Adelante.setEnabled(false);//desactivo el boton adelante
                  


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

        setOpaque(false);

        title.setText("New Tab");

        cerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/x.png"))); // NOI18N
        cerrar.setContentAreaFilled(false);
        cerrar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/x2.png"))); // NOI18N
        cerrar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/x2.png"))); // NOI18N
        cerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cerrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cerrarMouseExited(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cerrarMouseClicked(evt);
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
            .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(icono, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(cerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    public JButton getCerrar(){
        return cerrar;
    }
    
    private void cerrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cerrarMouseEntered


//        try {
//            cerrar.setIcon(new ImageIcon(new URL(Directorio+"imagenes/x2.png")));
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(Pestanas.class.getName()).log(Level.SEVERE, null, ex);
//        }        // TODO add your handling code here:
    }//GEN-LAST:event_cerrarMouseEntered

    private void cerrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cerrarMouseExited

        
//        try {
//            cerrar.setIcon(new ImageIcon(new URL(Directorio+"imagenes/x.png")));
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(Pestanas.class.getName()).log(Level.SEVERE, null, ex);
//        }
        // TODO add your handling code here:
    }//GEN-LAST:event_cerrarMouseExited

    private void cerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cerrarMouseClicked

        if(nav.pestañas>1){//Validacion para que siempre quede almenos una pestaña

            //validando para que nunca quede activada la ultima pestaña(pestaña de agregacion '+')
            if(nav.getTab().getSelectedIndex()==nav.pestañas-1)
            nav.getTab().setSelectedIndex(nav.pestañas-2);

            //recuperando el indice del tab en el que se encuentra ese componente y con el elimino de los arreglos y asigno nuevo
            //valores de indice a los componentes restantes posteriores
            //                    int index=nueva.getIndex();
            int index =nav.getTab().indexOfTabComponent(this);
            System.out.println(nav.getTab().indexOfTabComponent(this));
            nav.getTab().remove(index);
            nav.pestañas--;
            getCargar().detener();

        }else System.exit(0);

        // TODO add your handling code here:
    }//GEN-LAST:event_cerrarMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cerrar;
    private javax.swing.JLabel icono;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}
