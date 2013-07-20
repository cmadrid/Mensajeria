/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mensajeria;

import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.text.StyledEditorKit;

/**
 *
 * @author Cesar Madrid
 */
public class mensajero extends javax.swing.JFrame {

    /**
     * Creates new form mensajero
     */
    int PUERTO = 5000;
    String DIR = "10.0.0.17";
    Socket mens;
    String NICK;
    int id;
    JFrame este = this;
    String mensajes="";
   
    public mensajero() {
        initComponents();
        
        DefaultListModel modelo = new DefaultListModel();

        for(int i = 1; i<=10; i++){
                modelo.addElement("hola"+i);
        }

        Usuarios.setModel(modelo);
        
        
        editor.setContentType("text/html");
        editor.setEditable(false);
        //editor.setEnabled(false);
        DIR = JOptionPane.showInputDialog(this,"Indique el servidor a conectarse",JOptionPane.QUESTION_MESSAGE);
        NICK = JOptionPane.showInputDialog(this,"Ingrese su nick",JOptionPane.QUESTION_MESSAGE);
        if(NICK==null)
            NICK="UsuarioAnónimo";
        System.out.println(NICK);
        setTitle(NICK+" - Aplicacion de Mensajeria");
        
        final Llegada llegada = new Llegada();

        
            System.out.println("1");
            int vueltas = 0;
            try {
                
                mens = new Socket(DIR,PUERTO);
                System.out.println();
                vueltas=6;
                System.out.println("2");
                id = Integer.parseInt((new DataInputStream((mens.getInputStream()))).readUTF());
                new ObjectOutputStream(mens.getOutputStream()).writeObject(NICK+"\n");
                new ObjectOutputStream(mens.getOutputStream()).writeObject(NICK+"\n");
                Thread llega = new Thread(llegada);
                llega.start();
            } catch (Exception e) {
                vueltas++;
                JOptionPane.showMessageDialog(this,"El servidor no fue encontrado. Adios");
                System.exit(0);


            }
            
                    addWindowListener(new WindowAdapter(){
        public void windowClosing(WindowEvent we){
        int eleccion = JOptionPane.showConfirmDialog(este, "Desea salir?");
        if ( eleccion == 0) {
            JOptionPane.showMessageDialog(este, "saliendo del sistema...");
            try {
                new ObjectOutputStream(mens.getOutputStream()).writeObject("$$cerrar$$"+"$$$$$$$$$$$$$$$$$$$$<font color=\"#CC66CC\">"+NICK+"\n");
                System.out.println("paso la prueba");
            } catch (IOException ex) {
                System.out.println("no se envio el cerrar");
            }
//            
            llegada.detener();
            System.exit(0);
        }   
    }
});
            
            
            
        
        
    }
    
    public void cerrando(){
        JOptionPane.showMessageDialog(this,"La conexion con el serviodor finalizo, el programa se cerrará.");
        System.exit(0);

System.out.println("ya estas avisado");
    }
    
    public class Llegada implements Runnable{
        boolean running =true;
        @Override
        public void run() {
            try {
               while(running){
                int pos=0;
                DataInputStream entra = new DataInputStream((mens.getInputStream()));
                String mens = entra.readUTF();
                String men = mens.substring(13);
                if(mens.subSequence(13, 20).equals("$$OFF$$"))
                       cerrando();
                pos=19;
                for(int h=19;h<men.length();h++)
                    if(men.charAt(h)==':'){
                        pos=h;
                        h=999;
                    }
                   if(men.substring(18, pos).equals(NICK))
                       men="<font color=\"red\">Yo</font>"+men.substring(pos);
                   
                   /** seccion emoticones **/
                   

                men = men.replace(":D", "<img src='"+new File("emoti/feliz.gif").toURL().toExternalForm()+"' width=15 height=15></img>");
                men = men.replace(":S", "<img src='"+new File("emoti/ss.png").toURL().toExternalForm()+"' width=15 height=15></img>");
                men = men.replace(":PALM:", "<img src='"+new File("emoti/palm.png").toURL().toExternalForm()+"' width=15 height=15></img>");
                men = men.replace(":EVIL:", "<img src='"+new File("emoti/evil.png").toURL().toExternalForm()+"' width=15 height=15></img>");
                men = men.replace("(Y)", "<img src='"+new File("emoti/Me gusta.jpg").toURL().toExternalForm()+"' width=15 height=15></img>");
                //<img src='' width=15 height=15></img>
                
                
                /**Seccion Emoticones fin**/
                
                mensajes = mensajes+men+"<br>";
                editor.setText(mensajes);
                //mantener el scroll abajo
                int x;
                editor.selectAll();
                x = editor.getSelectionEnd();
                editor.select(x,x);
               }
            } catch (Exception e) {
                System.out.println("error en actualizar el chat");
                run();
            }
        }
        
        public void detener(){
        
            running = false;
        }
        
    
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        msj = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        editor = new javax.swing.JEditorPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        Usuarios = new javax.swing.JList();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        msj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                msjKeyPressed(evt);
            }
        });

        jLabel1.setText("Sistema de Mensajeria");

        jScrollPane2.setViewportView(editor);

        jScrollPane1.setViewportView(Usuarios);

        jLabel2.setText("Usuarios Conectados");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(142, 142, 142)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(msj)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addComponent(msj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void msjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_msjKeyPressed

        int key=evt.getKeyCode();
    
        if(key==KeyEvent.VK_ENTER)
        { 
            if(!msj.getText().equalsIgnoreCase("")){
                try {

                ObjectOutputStream mensaje = new ObjectOutputStream(mens.getOutputStream());
                mensaje.writeObject("$$$$$$$$$$$$$"+"<font color=\"red\">"+NICK+": </font>"+msj.getText()+"\n");
//                    System.out.println(editor.getText()+"asd ");
//                editor.setText(editor.getText()+
//
//"<font color=\"red\">"+NICK+": </font>"+msj.getText());
                msj.setText(null);
                } catch (Exception e) {
                }
            }
        }
    
        // TODO add your handling code here:
    }//GEN-LAST:event_msjKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mensajero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mensajero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mensajero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mensajero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mensajero().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList Usuarios;
    private javax.swing.JEditorPane editor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField msj;
    // End of variables declaration//GEN-END:variables
}
