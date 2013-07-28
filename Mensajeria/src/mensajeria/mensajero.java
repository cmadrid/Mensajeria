/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mensajeria;


import java.awt.Dimension;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
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
    static Map<String, mensajero> chats = new HashMap<>();
    String TIPO;
    int detener =999;
    String Directorio;
    String letra="Arial";
    
    static Map<String,Llegada> llegadas = new HashMap<>();

    public ArrayList getUsuarios() {
        return usuarios;
    }
    public String getNick() {
        return NICK;
    }
    
    
    public mensajero(final String tipo,String dir,String nick) {
        //Ubico directorio dond almaceno mis archivos dentro del .jar
        Directorio=getClass().getResource("").toExternalForm();
        Directorio=(String) Directorio.subSequence(0, Directorio.length()-11);
        TIPO=tipo;
        initComponents();
        try {
        zumbidoBtn.setIcon(new ImageIcon(new URL(Directorio+"emoti/zumbido.png")));
            
        } catch (Exception e) {
        }
        zumbido.start();
        
        editor.setContentType("text/html");
        editor.setEditable(false);
        
        if(tipo.equals("TODOS")){
        DIR = JOptionPane.showInputDialog(this,"Indique el servidor a conectarse",JOptionPane.QUESTION_MESSAGE);
        }
        else 
            DIR=dir;
        
        

    final Llegada llegada = new Llegada();
    llegadas.put(tipo, llegada);
        
            System.out.println("1");
            int vueltas = 0;
            try {
                
                mens = new Socket(DIR,PUERTO);
                new ObjectOutputStream(mens.getOutputStream()).writeObject(tipo);
                System.out.println();
                vueltas=6;
                System.out.println("2");
                
                
                
                //Pidiendo y validando nick sin repetirse
                
                ObjectInputStream entrada = new ObjectInputStream(mens.getInputStream());
                usuarios = (ArrayList) entrada.readObject();
                
                boolean p=true;
                while(p&&tipo.equals("TODOS")){
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
                if(!tipo.equals("TODOS"))
                    NICK=nick;
                
                if(NICK==null)
                    NICK="UsuarioAnónimo";
                System.out.println(NICK);
                
                if(tipo.equals("TODOS"))
                    setTitle(NICK+" - Aplicacion de Mensajeria");
                else
                    setTitle(NICK+" - Chat privado - Aplicacion de Mensajeria");
                    
                
                
                
                
                
                
                
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
                armaPaquete("ENTRAR", null,usuarios, NICK,TIPO);
                System.out.println("este es el ");
                System.out.println("paquete de arranque a mandar"+ paquete);
                new ObjectOutputStream(mens.getOutputStream()).writeObject(paquete);
                
                
                
                
                
                Thread llega = new Thread(llegadas.get(tipo));
                llega.start();
            } catch (Exception e) {
                vueltas++;
                JOptionPane.showMessageDialog(this,"El servidor no fue encontrado. Adios");
                if(tipo.equals("TODOS"))
                    System.exit(0);
                else este.dispose();


            }
                if(tipo.equals("TODOS")){
                    addWindowListener(new WindowAdapter(){
                        public void windowClosing(WindowEvent we){
                        int eleccion = JOptionPane.showConfirmDialog(este, "Desea salir?");
                        if ( eleccion == 0) {
                            JOptionPane.showMessageDialog(este, "saliendo...");
                            try {
                                ArrayList<ArrayList> paquetes = new ArrayList<>();
                                Iterator it = chats.keySet().iterator();
                                while(it.hasNext()){
                                    String key= (String) it.next();
                                        armaPaquete("CERRARP",null,chats.get(key).getUsuarios(), NICK,key);
                                        paquetes.add(paquete);
                                        chats.get(key).dispose();
                                }
                                
                                System.out.println("paso la prueba");
                                System.out.println(paquetes);
                                for(int i =0;i<paquetes.size();i++){
                                    new ObjectOutputStream(mens.getOutputStream()).writeObject(paquetes.get(i));
                                    Thread.sleep(50);
                                }
                                armaPaquete("CERRAR", null,usuarios, NICK,TIPO);
                                new ObjectOutputStream(mens.getOutputStream()).writeObject(paquete);
                                detener=1000;
                                llegada.detener();
                                System.exit(0);
                            } catch (IOException ex) {
                                System.out.println("no se envio el cerrar");
                            } catch (InterruptedException ex) {
                                Logger.getLogger(mensajero.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }   
                    }
                     });
                }else{
                    addWindowListener(new WindowAdapter(){
                        public void windowClosing(WindowEvent we){
                            mensajes="";
                            editor.setText(mensajes);
                            este.setVisible(false);
                    }
                     });
                }
                
                
            
            
            
        
        
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
                   Thread.sleep(50);
                   
                ObjectInputStream entrada = new ObjectInputStream(mens.getInputStream());
                paquete =new ArrayList();
                paquete = (ArrayList) entrada.readObject();
                   System.out.println("paquete recien llegado :p "+ paquete);
                   
                   
                //comando para mandar a cerrar las ventanas. 
                if(paquete.get(0).equals("OFF"))
                       cerrando();
                   
                   
                
                
                
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
                //cuando llamo a una conversacion q ya esta inicializada
                if(paquete.get(0).equals("MOSTRAR")){
                    System.out.println(chats);
                    System.out.println(((ArrayList)paquete.get(2)).get(0)+" "+((ArrayList)paquete.get(2)).get(1)+" <-1111111111111111111111");
                    System.out.println(chats.get(((ArrayList)paquete.get(2)).get(0)+" "+((ArrayList)paquete.get(2)).get(1))+" 222");
                    if(NICK.equals(paquete.get(3)))
                        chats.get(((ArrayList)paquete.get(2)).get(0)+" "+((ArrayList)paquete.get(2)).get(1)).setVisible(true);
                    continue;
                }
                if(paquete.get(0).equals("INIPRI")){
                    System.out.println("esto son los destinatarios "+paquete.get(4));
                    mensajero privado = new mensajero((String) paquete.get(4),DIR,NICK);
                    privado.jLabel2.setVisible(false);
                    privado.jScrollPane1.setVisible(false);
                    chats.put((String) paquete.get(4), privado);
                    System.out.println(paquete.get(4)+"<-----------------1");
                    System.out.println(chats.get(paquete.get(4)));
                    System.out.println(chats);
                    if(NICK.equals(paquete.get(3)))
                        privado.setVisible(true);
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
                
                
                
                
                   if(paquete.get(3).equals(NICK)){
                       paquete.remove(3);
                       paquete.add(3,"Yo");
                   }
                   
                   
                   
                String men = (String) paquete.get(1);
                
                
                
                
                
                /** seccion emoticones **/
                men = men.replace(":D", "<img src='"+Directorio+"emoti/feliz.gif"+"' width=15 height=15></img>");
                men = men.replace(":S", "<img src='"+Directorio+"emoti/ss.png"+"' width=15 height=15></img>");
                men = men.replace(":PALM:", "<img src='"+Directorio+"emoti/palm.png"+"' width=15 height=15></img>");
                men = men.replace(":EVIL:", "<img src='"+Directorio+"emoti/evil.png"+"' width=15 height=15></img>");
                men = men.replace("(Y)", "<img src='"+Directorio+"emoti/Me gusta.jpg"+"' width=15 height=15></img>");
                
                //añadiendo fuentes y estilos
                
                paquete.remove(1);
                paquete.add(1, men);
                //<img src='' width=15 height=15></img>
                
                
                
                
                
                /**Seccion Emoticones fin**/
                
                //instrucciones segun comandos
//                if(paquete.get(0).equals("CERARP")){
//                    Iterator it = chats.keySet().iterator();
//                    while(it.hasNext()){
//                        String key = (String) it.next();
//                        mensajero mensajeross = chats.get(key);
//                        JOptionPane.showConfirmDialog(null,"Ha terminado la conversacion");
//                        mensajeross.dispose();
//                
//                    }
//                }
                if(paquete.get(0).equals("CERRARP")){
//                    armaPaquete((String)paquete.get(0), null, (ArrayList)paquete.get(2), NICK, (String)paquete.get(4));
//                    new ObjectOutputStream(mens.getOutputStream()).writeObject(paquete);
//                    llegada.detener();
//                    JOptionPane.showConfirmDialog(null, "Secion finalizo");
//                    este.dispose();
//                    continue;
                    try {
                                Iterator it = chats.keySet().iterator();
                                while(it.hasNext()){
                                    String key = (String) it.next();
                                    if(((mensajero)chats.get(key)).getUsuarios().contains(paquete.get(3))){
                                        armaPaquete("CERRARP",null,chats.get(key).getUsuarios(), chats.get(key).getNick(),key);
                                        new ObjectOutputStream(mens.getOutputStream()).writeObject(paquete);
                                        llegadas.get(key).detener();
//                                        JOptionPane.showMessageDialog(chats.get(key), "El otro cliente se ha desconectado.");
                                        chats.get(key).Usuarios.setEnabled(false);
                                        chats.get(key).zumbidoBtn.disable();
                                        chats.get(key).msj.setText("El otro usuario se ha desconectado.");
                                        chats.get(key).msj.setEnabled(false);
                                        chats.get(key).mensajes=chats.get(key).mensajes+"<font color=\"green\"> Se perdio la conexion con el otro usuario </font><br>";
                                        chats.get(key).editor.setText(chats.get(key).mensajes);
                                        if(!chats.get(key).isVisible())
                                            chats.get(key).dispose();
                                        chats.remove(key);
                                        
                                        
//                                        chats.get(key).
                                        
                                    }
                                }
                            } catch (IOException ex) {
                                System.out.println("no se envio el cerrar");
                            }
                    continue;
                }
                
                if(!paquete.get(0).equals("CERRAR") && !paquete.get(0).equals("ENTRAR")){
                    mensajes = mensajes+"<font color=\"red\">"+paquete.get(3)+": </font>"+paquete.get(1)+"<br>";
                    if(!paquete.get(3).equals("Yo"))alertmsj.start();//reproduce el audio.
                    este.setVisible(true);
                }
                
                //para que no lleguen notificaciones de ingresos a las conversaciones privadas.
                if(paquete.get(0).equals("ENTRAR")&&!paquete.get(4).equals("TODOS"))
                    continue;
                
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

                if(detener!=1000)
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
        zumbidoBtn = new javax.swing.JButton();
        FuentesCB = new javax.swing.JComboBox();
        BoldBtn = new javax.swing.JToggleButton();
        ItalicBtn = new javax.swing.JToggleButton();

        setResizable(false);

        msj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                msjKeyPressed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Sistema de Mensajeria");

        jScrollPane2.setViewportView(editor);

        Usuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Usuarios);

        jLabel2.setText("Usuarios Conectados");

        zumbidoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zumbidoBtnActionPerformed(evt);
            }
        });

        FuentesCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Arial", "Arial Black", "Comic Sans MS", "Georgia", "Kristen ITC", "Tahoma", "Times New Roman", "Verdana", "Wide Latin" }));
        FuentesCB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FuentesCBItemStateChanged(evt);
            }
        });

        BoldBtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BoldBtn.setText("N");

        ItalicBtn.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        ItalicBtn.setText("I");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(zumbidoBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FuentesCB, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BoldBtn)
                        .addGap(10, 10, 10)
                        .addComponent(ItalicBtn)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(msj)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)))
                        .addGap(18, 18, 18))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(19, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(9, 9, 9)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(zumbidoBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ItalicBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(FuentesCB, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BoldBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                    String enviar =msj.getText();
                    //eliminando codigo html no permitido
                    enviar = enviar.replace("<", "&lt;");
                    enviar = enviar.replace(">", "&gt;");
                    //agregando fuentes y estilos 
                    enviar="<FONT FACE=\""+letra+"\">"+enviar+"</FONT>";
                    if(BoldBtn.isSelected())
                        enviar="<b>"+enviar+"</b>";
                    if(ItalicBtn.isSelected())
                        enviar="<i>"+enviar+"</i>";
                    armaPaquete(null,enviar,usuarios, NICK,TIPO);
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

  if(Usuarios.isEnabled())
    if(evt.getClickCount() == 2){
        int index = Usuarios.locationToIndex(evt.getPoint());
        ListModel dlm = Usuarios.getModel();
        Object item = dlm.getElementAt(index);
        System.out.println(item.toString()+" y "+NICK);
        ArrayList participantes = new ArrayList();
        
        for(int i = 0  ; i < usuarios.size() ; i++)
            if(usuarios.get(i).equals(item.toString())||usuarios.get(i).equals(NICK))
                participantes.add(usuarios.get(i));
        
        

        if(!item.equals(NICK)){
            if(chats.containsKey(participantes.get(0)+" "+participantes.get(1))){
                armaPaquete("MOSTRAR", null, participantes, NICK, participantes.get(0)+" "+participantes.get(1));

            }else


            if(!item.equals(NICK)){

                Usuarios.ensureIndexIsVisible(index);
                armaPaquete("INIPRI", null,participantes, NICK,participantes.get(0)+" "+participantes.get(1));


            }
             try {
                 if(!item.equals(NICK))
                    new ObjectOutputStream(mens.getOutputStream()).writeObject(paquete);
                } catch (Exception e) {
                }
        }
    }
        // TODO add your handling code here:
    }//GEN-LAST:event_UsuariosMouseClicked

    private void zumbidoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zumbidoBtnActionPerformed

        try {

            armaPaquete("ZUMBIDO", null,usuarios, NICK,TIPO);
            ObjectOutputStream mensaje = new ObjectOutputStream(mens.getOutputStream());
            mensaje.writeObject(paquete);

        } catch (Exception e) {
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_zumbidoBtnActionPerformed

    private void FuentesCBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FuentesCBItemStateChanged

        letra = (String) FuentesCB.getSelectedItem();
        // TODO add your handling code here:
    }//GEN-LAST:event_FuentesCBItemStateChanged

    public void armaPaquete(String comando,String cuerpo,ArrayList us, String nick,String tipo){
    
        paquete = new ArrayList();
        if(comando==null)
            comando="";
        if(cuerpo==null)
            cuerpo="";
        if(nick==null)
            nick="";
        paquete.add(comando);
        paquete.add(cuerpo);
        paquete.add(us);
        paquete.add(nick);
        paquete.add(tipo);
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
                new mensajero("TODOS",null,null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton BoldBtn;
    private javax.swing.JComboBox FuentesCB;
    private javax.swing.JToggleButton ItalicBtn;
    private javax.swing.JList Usuarios;
    private javax.swing.JEditorPane editor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField msj;
    private javax.swing.JButton zumbidoBtn;
    // End of variables declaration//GEN-END:variables
}
