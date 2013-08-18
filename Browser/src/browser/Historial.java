/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package browser;

import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.ListModel;

/**
 *  La clase historial muestra por pantalla todas visitas hechas por el usuario, el cual lee el archivo historial y lo modifica según 
 *  las visitas que haga el cliente.
 *  
 * @author Marlon Espinoza
 */
public class Historial extends javax.swing.JFrame {

    ArrayList<String> histo = new ArrayList<>();
    Navegador nav;
    
    /**
     * 
     * @param navegador es la referencia del navegador principal (la ventana) guardado en el atributo nav
     * para poder abrir las direcciones del historial
     */
    public Historial(Navegador navegador) {
        initComponents();
        Image icon = new ImageIcon(getClass().getResource("logo.png")).getImage();
        setIconImage(icon);
        nav=navegador;
        //añado un evento para que en cuanto se cierre la ventana mande un valor de null a la variable histo de la clase Navegador
        //mientras histo sea null yo creare nueva instancia caso contraro no lo hare
        addWindowListener(new WindowAdapter(){
                        public void windowClosing(WindowEvent we){
                            nav.histo=null;
                        }});
//        Lista.setCellRenderer(new MyCellRender());
    }

    /**
     * 
     * @param histo copia todo el contenido del historial en el atributo histo
     */
    public void setHisto(ArrayList<String> histo) {
        this.histo = new ArrayList<>();
        this.histo = (ArrayList<String>) histo.clone();
    }
    
    //esta funcion muestra en el jlist todos los elementos del historial que coincidan con la barra de busqueda
    /**
     * Inserta todos los elementos del la lista histo en un JList y además administra la búsqueda de una dirección en el historial
     */
    public void enListar(){
        DefaultListModel modelo = new DefaultListModel();
        for(int i = histo.size()-1;i>-1;i--){
            String s = histo.get(i);
            if(s.toUpperCase().contains(busqueda.getText().toUpperCase())){//comparo que tengan en comun lo escrito 
                String h[]=s.split("#");
                modelo.addElement(h[0]+" <--> "+h[1]);
            }
        }
        Lista.setModel(modelo);
    }
        

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        busqueda = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Lista = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Historial - YasunET 1.0");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        busqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                busquedaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                busquedaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                busquedaKeyTyped(evt);
            }
        });

        jLabel1.setText("Buscar en el Historial");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Historial");

        Lista.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ListaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Lista);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(busqueda))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 762, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * 
     * @param evt es el evento al escribir sobre el campo de búsqueda
     */
    private void busquedaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_busquedaKeyTyped
       
        enListar(); 
        // TODO add your handling code here:
    }//GEN-LAST:event_busquedaKeyTyped

    private void busquedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_busquedaKeyPressed
        
        // TODO add your handling code here:
    }//GEN-LAST:event_busquedaKeyPressed

    /**
     * 
     * @param evt es el evento al soltar una tecla sobre el campo de búsqueda
     */
    private void busquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_busquedaKeyReleased
    enListar();
        // TODO add your handling code here:
    }//GEN-LAST:event_busquedaKeyReleased
    /**
     * 
     * @param evt es el evento al mostrar la pantalla historial
     */
    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown

        enListar();
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentShown
    /**
     * 
     * @param evt es el evento al dar clic sobre una dirección dentro del historial
     */
    private void ListaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListaMouseClicked

    if(Lista.isEnabled())
        if(evt.getClickCount() == 2){//hago un conteo x si es doble click
            int index = Lista.locationToIndex(evt.getPoint());
            ListModel dlm = Lista.getModel();
            Object item = dlm.getElementAt(index);//obtengo el elemento sobre el que se clickeo
            String url =item.toString().split(" <--> ")[1];//asigno el url a cargar
            nav.cargarHistorial(url);
            this.dispose();//cierro esta ventana al cargar el url

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_ListaMouseClicked

    
    
    
    
    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Historial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Historial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Historial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Historial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Historial().setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList Lista;
    private javax.swing.JTextField busqueda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
