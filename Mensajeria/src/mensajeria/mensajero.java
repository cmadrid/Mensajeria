/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mensajeria;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
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
    String NICK="";
    int id;
    JFrame este = this;
    String mensajes="";
    ArrayList paquete = new ArrayList();
    ArrayList usuarios = new ArrayList();
    Zumbido zumba = new Zumbido();
    Thread zumbido = new Thread(zumba);
            
    public mensajero() {
        initComponents();
        zumbido.start();
        
        
        editor.setContentType("text/html");
        editor.setEditable(false);
        //editor.setEnabled(false);
        DIR = JOptionPane.showInputDialog(this,"Indique el servidor a conectarse",JOptionPane.QUESTION_MESSAGE);
        
        final Llegada llegada = new Llegada();

        
            System.out.println("1");
            int vueltas = 0;
            try {
                
                mens = new Socket(DIR,PUERTO);
                System.out.println();
                vueltas=6;
                System.out.println("2");
                
                
                
                //Pidiendo y validando nick sin repetirse
                
                ObjectInputStream entrada = new ObjectInputStream(mens.getInputStream());
                usuarios = (ArrayList) entrada.readObject();
                
                boolean p=true;
                while(p){
                    NICK = JOptionPane.showInputDialog(this,"Ingrese su nick",JOptionPane.QUESTION_MESSAGE);
                    if(usuarios.size()==0&&NICK!=null)
                        p=false;
                    if(usuarios.size()==0&&NICK==null)
                        JOptionPane.showMessageDialog(este, "Ingrese un Nick");
                    for(int i =0;i<usuarios.size();i++){
                        if(((String)usuarios.get(i)).equalsIgnoreCase(NICK))
                            JOptionPane.showMessageDialog(este, "Usuario ya en uso, intente con otro");
                        else{
                            
                            if(NICK==null||NICK.equals(mensajes))
                                JOptionPane.showMessageDialog(este, "Ingrese un Nick");
                            else
                                p=false; 
                        }
                    }
                }
                if(NICK==null)
                    NICK="UsuarioAnónimo";
                System.out.println(NICK);
                setTitle(NICK+" - Aplicacion de Mensajeria");
                
                
                
                
                
                
                
                id = Integer.parseInt((new DataInputStream((mens.getInputStream()))).readUTF());
                new ObjectOutputStream(mens.getOutputStream()).writeObject(NICK);
                entrada = new ObjectInputStream(mens.getInputStream());
                usuarios = new ArrayList();
                usuarios = (ArrayList) entrada.readObject();
                //llenando la lista con los usuarios
                DefaultListModel modelo = new DefaultListModel();
                for(int i = 0; i<usuarios.size(); i++){
                    modelo.addElement(usuarios.get(i));
                }
                Usuarios.setModel(modelo);
                armaPaquete("ENTRAR", null, NICK);
                System.out.println("este es el ");
                System.out.println("paquete de arranque a mandar"+ paquete);
                new ObjectOutputStream(mens.getOutputStream()).writeObject(paquete);
                
                
                
                
                
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
                armaPaquete("CERRAR", null, NICK);
                
                new ObjectOutputStream(mens.getOutputStream()).writeObject(paquete);
                System.out.println("paso la prueba");
            } catch (IOException ex) {
                System.out.println("no se envio el cerrar");
            }
           
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
                   
                ObjectInputStream entrada = new ObjectInputStream(mens.getInputStream());
                paquete =new ArrayList();
                paquete = (ArrayList) entrada.readObject();
                
                //Ubico directorio dond almaceno mis archivos dentro del .jar
                String Directorio=getClass().getResource("").toExternalForm();
                Directorio=(String) Directorio.subSequence(0, Directorio.length()-11);
                
                URL zumbidoUrl = new URL(Directorio+"sonidos/nudge.wav");
                Clip zumb = AudioSystem.getClip();
                zumb.open(AudioSystem.getAudioInputStream(zumbidoUrl));
                
                if(paquete.get(0).equals("ZUMBIDO")){
                    if(paquete.get(3).equals(NICK))
                        mensajes=mensajes+"<font color=\"#CCAB23\">Has enviado un zumbido. </font><br>";
                    else{
                        mensajes=mensajes+"<font color=\"#CCAB23\">" +paquete.get(3)+" ha enviado un zumbido.</font> <br>";
                       zumba.zumbar();
                       este.setVisible(true);
                       este.setExtendedState(este.NORMAL);
                        
                    }
                    editor.setText(mensajes);
                       if(!zumb.isActive())
                            zumb.start();
                    int x;
                    editor.selectAll();
                    x = editor.getSelectionEnd();
                    editor.select(x,x);
                    continue;
                }
                
                //creando sonido de alerta
                URL alertUrl = new URL(Directorio+"sonidos/MSN alert.wav");
                Clip alertmsj = AudioSystem.getClip();
                alertmsj.open(AudioSystem.getAudioInputStream(alertUrl));
                //sonido.start();
//                zumba.zumbar();
                
                
                        
//                   System.out.println(paquete+" completo ");
                System.out.println(paquete.get(2)+" lista actual de usuarios");
                
                //agregando los usuarios a la lista
                usuarios = new ArrayList();
                usuarios = (ArrayList) paquete.get(2);
                DefaultListModel modelo = new DefaultListModel();
                for(int i = 0; i<usuarios.size(); i++){
                    modelo.addElement(usuarios.get(i));
                }
                Usuarios.setModel(modelo);
                
                
                
                
                //String men = mens.substring(13);
                if(paquete.get(0).equals("OFF"))
                       cerrando();
                
                
                
                   if(paquete.get(3).equals(NICK)){
                       paquete.remove(3);
                       paquete.add("Yo");
                   }
                   
                   /** seccion emoticones **/
                   
                String men = (String) paquete.get(1);
                
                
                
                
                men = men.replace(":D", "<img src='"+Directorio+"emoti/feliz.gif"+"' width=15 height=15></img>");
                
//                   System.out.println(getClass().getResource("emoti/feliz.gif").toExternalForm()+"(Y)");
                men = men.replace(":S", "<img src='"+Directorio+"emoti/ss.png"+"' width=15 height=15></img>");
                men = men.replace(":PALM:", "<img src='"+Directorio+"emoti/palm.png"+"' width=15 height=15></img>");
                men = men.replace(":EVIL:", "<img src='"+Directorio+"emoti/evil.png"+"' width=15 height=15></img>");
                men = men.replace("(Y)", "<img src='"+Directorio+"emoti/Me gusta.jpg"+"' width=15 height=15></img>");
                paquete.remove(1);
                paquete.add(1, men);
                //<img src='' width=15 height=15></img>
                
                
                
                
                
                /**Seccion Emoticones fin**/
                
                //instrucciones segun comandos
                if(!paquete.get(0).equals("CERRAR") && !paquete.get(0).equals("ENTRAR")){
                    mensajes = mensajes+"<font color=\"red\">"+paquete.get(3)+": </font>"+paquete.get(1)+"<br>";
                    if(!paquete.get(3).equals("Yo"))alertmsj.start();//reproduce el audio.
                    este.setVisible(true);
                }
                else
                    if(paquete.get(0).equals("CERRAR"))
                        mensajes = mensajes+"<font color=\"#CC66CC\">"+paquete.get(3)+" ha abandonado la conversacion.</font>"+"<br>";
                    else
                        if(paquete.get(0).equals("ENTRAR") && !paquete.get(3).equals("Yo"))
                            mensajes = mensajes+"<font color=\"#009933\">"+paquete.get(3)+" ha ingresado la conversacion.</font>"+"<br>";
                        else
                            if(paquete.get(0).equals("ENTRAR") && paquete.get(3).equals("Yo"))
                                mensajes=mensajes;
                            
                
                
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
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        msj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                msjKeyPressed(evt);
            }
        });

        jLabel1.setText("Sistema de Mensajeria");

        jScrollPane2.setViewportView(editor);

        Usuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Usuarios);

        jLabel2.setText("Usuarios Conectados");

        jButton1.setText("Zumbido ((:S))");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(142, 142, 142)
                .addComponent(jLabel1)
                .addContainerGap(271, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(msj)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18))))
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
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(4, 4, 4)
                .addComponent(msj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void msjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_msjKeyPressed

        int key=evt.getKeyCode();
    
        if(key==KeyEvent.VK_ENTER)
        { 
            if(!msj.getText().equalsIgnoreCase("")){
                try {
                    
                    armaPaquete(null, msj.getText(), NICK);
                    ObjectOutputStream mensaje = new ObjectOutputStream(mens.getOutputStream());
                    mensaje.writeObject(paquete);
                    System.out.println(paquete);
                    
                msj.setText(null);
                } catch (Exception e) {
                }
            }
        }
    
        // TODO add your handling code here:
    }//GEN-LAST:event_msjKeyPressed

    private void UsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UsuariosMouseClicked

    if(evt.getClickCount() == 2){
    int index = Usuarios.locationToIndex(evt.getPoint());
    ListModel dlm = Usuarios.getModel();
    Object item = dlm.getElementAt(index);
    Usuarios.ensureIndexIsVisible(index);
    JOptionPane.showMessageDialog(este, "Chat privado no disponible en version de prueba...");
    }
        // TODO add your handling code here:
    }//GEN-LAST:event_UsuariosMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        try {

            armaPaquete("ZUMBIDO", null, NICK);
            ObjectOutputStream mensaje = new ObjectOutputStream(mens.getOutputStream());
            mensaje.writeObject(paquete);

        } catch (Exception e) {
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    public void armaPaquete(String comando,String cuerpo, String nick){
    
        paquete = new ArrayList();
        if(comando==null)
            comando="";
        if(cuerpo==null)
            cuerpo="";
        if(nick==null)
            nick="";
        paquete.add(comando);
        paquete.add(cuerpo);
        paquete.add(this.usuarios);
        paquete.add(nick);
    }
    
    public class Zumbido implements Runnable{
        boolean running =true;
         boolean seguir =false;
        @Override
        public void run() {
            while(running){
                try {
                    Thread.sleep(60);
                } catch (InterruptedException ex) {
                    Logger.getLogger(mensajero.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(seguir){
                    int x=este.getX();
                    int y = este.getY();
                    este.setLocation(x+25, y);
                    try {
                        Thread.sleep(60);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(mensajero.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    este.setLocation(x-25, y);
                    try {
                        Thread.sleep(60);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(mensajero.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    este.setLocation(x+25, y);
                    try {
                        Thread.sleep(60);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(mensajero.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    este.setLocation(x-25, y);
                    try {
                        Thread.sleep(60);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(mensajero.class.getName()).log(Level.SEVERE, null, ex);
                    }este.setLocation(x+25, y);
                    try {
                        Thread.sleep(60);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(mensajero.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    este.setLocation(x-25, y);
                    try {
                        Thread.sleep(60);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(mensajero.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    este.setLocation(x+25, y);
                    try {
                        Thread.sleep(60);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(mensajero.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    este.setLocation(x, y);
                    try {
                        Thread.sleep(60);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(mensajero.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }seguir=false;
            }
            
        }
        public void zumbar(){
            seguir=true;
        }
    }
    
    
    
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
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField msj;
    // End of variables declaration//GEN-END:variables
}
