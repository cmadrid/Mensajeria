/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package browser;

import java.awt.Button;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import sun.org.mozilla.javascript.internal.ast.ContinueStatement;

/**
 *
 * @author Cesar Madrid
 */
public class Navegador extends javax.swing.JFrame {

    Socket conexion = new Socket();
    Cargar cargar = new Cargar();
    Thread carga;
    JFrame este = this;
//    ArrayList<String> Historial = new ArrayList<>();
//    ArrayList<ArrayList<String>> Historiales= new ArrayList<>();
//    ArrayList<Integer>nums=new ArrayList<>();
//    int num=-1;
    String Directorio;
    ArrayList<String> Htmls = new ArrayList<>();
    ArrayList<String> Https = new ArrayList<>();
    String Html="";
    String Respuesta="";
    int pestañas=0;
    ArrayList<JTextPane> texts = new ArrayList();
    String home="";
    ArrayList<String>marcadores = new ArrayList<>();
    ArrayList<String> historial = new ArrayList<>();
    static Historial histo=null;
    int numMarca=-1;
    static JToggleButton bandera= new JToggleButton();
    static String aCargar="";
    /**
     * Creates new form Navegador
     */
    public Navegador() {
        initComponents();
        MarcadoresPanel.setLayout(new FlowLayout(0));
        MarcadoresPanel.setVisible(false);
        
        //directorio dond localizar los recursos
        Directorio=getClass().getResource("").toExternalForm();
        Directorio=(String) Directorio.subSequence(0, Directorio.length()-8);
        
        
        cargarArchivos();
        
        bandera.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                if(bandera.isSelected()&&!cargar.correr){
                    cargar.setUrl(aCargar);
                    cargar.cargarPag();
                }
            }
        });
        
        
        
 //*************************************************************************************************************
        //leer archivos dentro del .jar: leer la informacion de home
//        try {
//            InputStream in = Navegador.class.getClassLoader().getResourceAsStream("archivos/home.cm");
//            BufferedReader buff = new BufferedReader(new InputStreamReader(in));
//            home=buff.readLine();
//        } catch (Exception ex) {
//         
//            JOptionPane.showMessageDialog(null, "error: "+ex.getMessage());
//        }
        
   //******************************************************************************************************************     
        
        //Añadiendo iconos a los botones
        ImageIcon atras=new ImageIcon();
        ImageIcon adelante=new ImageIcon();
        ImageIcon recargar=new ImageIcon();
        ImageIcon home=new ImageIcon();
        try {
            atras = new ImageIcon(new URL(Directorio+"imagenes/izquierda.png"));
            adelante = new ImageIcon(new URL(Directorio+"imagenes/derecha.png"));
            recargar = new ImageIcon(new URL(Directorio+"imagenes/actualizar.png"));
            home = new ImageIcon(new URL(Directorio+"imagenes/home.png"));
        } catch (MalformedURLException ex) {
            Logger.getLogger(Navegador.class.getName()).log(Level.SEVERE, null, ex);
        }
        Icon icono = new ImageIcon(atras.getImage().getScaledInstance(30, 23, Image.SCALE_DEFAULT));
        Atras.setIcon(icono);
        icono = new ImageIcon(adelante.getImage().getScaledInstance(30, 23, Image.SCALE_DEFAULT));
        Adelante.setIcon(icono);
        icono = new ImageIcon(recargar.getImage().getScaledInstance(23, 23, Image.SCALE_DEFAULT));
        Recargar.setIcon(icono);
        icono = new ImageIcon(home.getImage().getScaledInstance(23, 23, Image.SCALE_DEFAULT));
        Home.setIcon(icono);
       
        //creando y añadiendo los popmenu a las pestañas
       JMenuItem menu = new JMenuItem("Mostrar Página");
       menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verPag();
            }
        });
       JMenuItem menu1 = new JMenuItem("Mostrar HTTP");
       menu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verHttp();
            }
        });
        jPopupMenu1.add(menu);
        jPopupMenu1.add(menu1);
        Tab1.setComponentPopupMenu(jPopupMenu1);
        
        //iniciando hilo de cargar las paginas
        carga = new Thread(cargar);
        carga.start();
        

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        url = new javax.swing.JTextField();
        Atras = new javax.swing.JButton();
        Adelante = new javax.swing.JButton();
        Recargar = new javax.swing.JButton();
        Tab1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        Home = new javax.swing.JButton();
        MarcadoresPanel = new javax.swing.JPanel();
        MostrarMar = new javax.swing.JToggleButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        cerrar = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        url.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                urlKeyReleased(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                urlKeyPressed(evt);
            }
        });

        Atras.setEnabled(false);
        Atras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                AtrasMouseReleased(evt);
            }
        });

        Adelante.setEnabled(false);
        Adelante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                AdelanteMouseReleased(evt);
            }
        });

        Recargar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                RecargarMouseReleased(evt);
            }
        });

        Tab1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                Tab1StateChanged(evt);
            }
        });

        jScrollPane1.setViewportView(jTextPane1);

        Tab1.addTab("+", jScrollPane1);

        Home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                HomeMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout MarcadoresPanelLayout = new javax.swing.GroupLayout(MarcadoresPanel);
        MarcadoresPanel.setLayout(MarcadoresPanelLayout);
        MarcadoresPanelLayout.setHorizontalGroup(
            MarcadoresPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        MarcadoresPanelLayout.setVerticalGroup(
            MarcadoresPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        MostrarMar.setText("Marcadores");
        MostrarMar.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                MostrarMarStateChanged(evt);
            }
        });

        jMenu1.setText("File");

        jMenuItem1.setText("Definir pagina de Inicio");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Añadir a marcadores");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Historial");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        cerrar.setText("Cerrar");
        cerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerrarActionPerformed(evt);
            }
        });
        jMenu1.add(cerrar);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MarcadoresPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Tab1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Atras)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Adelante)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Recargar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Home)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(url, javax.swing.GroupLayout.DEFAULT_SIZE, 805, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(MostrarMar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Adelante, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Recargar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(url, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Atras)
                        .addComponent(Home)
                        .addComponent(MostrarMar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MarcadoresPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Tab1, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
boolean crtl=false;
    private void urlKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_urlKeyPressed

        int key=evt.getKeyCode();
        
        if(key==KeyEvent.VK_CONTROL)
            crtl=true;
            
        
        if(key==KeyEvent.VK_ENTER)
        {
            if(crtl==true)
                url.setText(url.getText()+".com");
            
            cargar.setUrl(url.getText());
            Pestañas seleccionada=((Pestañas)Tab1.getTabComponentAt(Tab1.getSelectedIndex()));
            int temp = seleccionada.getHistorialSize();
            if(temp!=seleccionada.getNum()+1)////////////////////////////////////////////////////////////////////////
                seleccionada.delPags();///////////////////////////////////////////////////////////////////////////
           seleccionada.addPagina(url.getText());
           seleccionada.setNumS();
            cargar.cargarPag();
            Adelante.setEnabled(false);
        
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_urlKeyPressed

    private void AtrasMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AtrasMouseReleased
        if(Atras.isEnabled()&&!cargar.correr){
            Pestañas seleccionada = ((Pestañas)Tab1.getTabComponentAt(Tab1.getSelectedIndex()));
            seleccionada.setNumR();
            cargar.setUrl(seleccionada.getPagina());
    //        if(Historial.size()!=num+1)
    //            System.out.println("ohlasdasd");
            cargar.cargarPag();
            Adelante.setEnabled(true);
            if(seleccionada.getNum()==0)
                Atras.setEnabled(false);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_AtrasMouseReleased

    private void AdelanteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AdelanteMouseReleased
        if(Adelante.isEnabled()&&!cargar.correr){
            Pestañas seleccionada = ((Pestañas)Tab1.getTabComponentAt(Tab1.getSelectedIndex()));
            cargar.setUrl(seleccionada.getPaginaS());
            seleccionada.setNumS();
            cargar.cargarPag();
            if(seleccionada.getHistorialSize()==seleccionada.getNum()+1)
                Adelante.setEnabled(false);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_AdelanteMouseReleased

    private void RecargarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RecargarMouseReleased

        cargar.cargarPag();
        // TODO add your handling code here:
    }//GEN-LAST:event_RecargarMouseReleased

    private void urlKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_urlKeyReleased

        int key=evt.getKeyCode();
        
        if(key==KeyEvent.VK_CONTROL)
            crtl=false;
        // TODO add your handling code here:
    }//GEN-LAST:event_urlKeyReleased

    
    private void cerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrarActionPerformed

        System.exit(0);
        // TODO add your handling code here:
    }//GEN-LAST:event_cerrarActionPerformed

    private void Tab1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_Tab1StateChanged
        
        if(Tab1.getSelectedIndex()==pestañas){
            Tab1.setSelectedIndex(pestañas-1);
            
            crearPestaña();
            
        }
        else
            if(pestañas>1)
            {
                
                Pestañas seleccionada = ((Pestañas)Tab1.getTabComponentAt(Tab1.getSelectedIndex()));
                setTitle(((Pestañas)Tab1.getTabComponentAt(Tab1.getSelectedIndex())).getTitle());
                actualizarBtns();
                if(seleccionada.getNum()==-1)
                    url.setText("");
                else
                    url.setText(seleccionada.getPagina());
            }
        
        
        // TODO add your handling code here:
    }//GEN-LAST:event_Tab1StateChanged

    private void HomeMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HomeMouseReleased

//        if(nums.get(Tab1.getSelectedIndex())>1)
//            Atras.setEnabled(true);
        if(!cargar.correr){
            ///
            alterarHistorial(home);
            //////
            System.out.println(home);
            cargar.setUrl(home);
            cargar.cargarPag();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_HomeMouseReleased
    public void alterarHistorial(String pag){
        
        Pestañas seleccionada = ((Pestañas)Tab1.getTabComponentAt(Tab1.getSelectedIndex()));
        int temp = seleccionada.getHistorialSize();
            if(temp!=seleccionada.getNum()+1)/////////////////////////////////////////////////////////////////////////////////////////
                seleccionada.delPags();/////////////////////////////////////////////////////////////////////////////////////////////////
           seleccionada.addPagina(pag);
           seleccionada.setNumS();
    }
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        Pestañas seleccionada = ((Pestañas)Tab1.getTabComponentAt(Tab1.getSelectedIndex()));
        home=seleccionada.getPagina();
        actualizarArchivo();
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void MostrarMarStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_MostrarMarStateChanged

        if(MostrarMar.isSelected())
            MarcadoresPanel.setVisible(true);
        else
            MarcadoresPanel.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_MostrarMarStateChanged

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        
        Pestañas seleccionada = ((Pestañas)Tab1.getTabComponentAt(Tab1.getSelectedIndex()));
        if(seleccionada.getNum()>-1){
            crearMarcador(seleccionada.getTitle()+"#"+seleccionada.getPagina());
            actualizarArchivo();
            MarcadoresPanel.setVisible(false);
            if(MostrarMar.isSelected())
            MarcadoresPanel.setVisible(true);
        }
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed

        if(histo==null){
            histo = new Historial();
            histo.setHisto(historial);
            histo.setVisible(true);
        }
        histo.setState(0);
        histo.setVisible(true);

        System.out.println("**********Imprimiendo historiales**********");
        for(int i =0;i<historial.size();i++)
        System.out.println(historial.get(i));
        System.out.println("******************Fin**********************");
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    
    public void cargarDelHisto(String url){
        if(!cargar.correr){
            alterarHistorial(url);
            cargar.setUrl(url);
            cargar.cargarPag();
        }
       
    }
    
    
    
    /***************************************************************************************/
    public void actualizarBtns(){
        
        Pestañas seleccionada = ((Pestañas)Tab1.getTabComponentAt(Tab1.getSelectedIndex()));
        if(seleccionada.getNum()>0)
            Atras.setEnabled(true);
        else
            Atras.setEnabled(false);

        if(seleccionada.getNum()<seleccionada.getHistorialSize()-1)
            Adelante.setEnabled(true);
        else
            Adelante.setEnabled(false);
    }
  /***************************************************************************************/
    
    
    
    
 /************************************CREAR PESTAÑAS*****************************************/
    public void crearPestaña(){
        
            //Agregando historiales para cada uno de las pestañas
            Htmls.add("");
            Https.add("");
//            int num=-1;
//            nums.add(num);
    
            //creo el nuevo jTextPane que se asignara a mi nueva pestaña
            JTextPane n =new JTextPane();
            n.setContentType("text/html");
            n.setEditable(false);
//            n.setText("<!doctype html><head><metas itemprop=\"image\" content=\"/images/google_favicon_128.png\"><title>Google</title><style>#gb{font:13px/27px Arial,sans-serif;height:30px}#gbz,#gbg{position:absolute;white-space:nowrap;top:0;height:30px;z-index:1000}#gbz{left:0;padding-left:4px}#gbg{right:0;padding-right:5px}#gbs{background:transparent;position:absolute;top:-999px;visibility:hidden;z-index:998;right:0}.gbto #gbs{background:#fff}#gbx3,#gbx4{background-color:#2d2d2d;background-image:none;_background-image:none;background-position:0 -138px;background-repeat:repeat-x;border-bottom:1px solid #000;font-size:24px;height:29px;_height:30px;opacity:1;filter:alpha(opacity=100);position:absolute;top:0;width:100%;z-index:990}#gbx3{left:0}#gbx4{right:0}#gbb{position:relative}#gbbw{left:0;position:absolute;top:30px;width:100%}.gbtcb{position:absolute;visibility:hidden}#gbz .gbtcb{right:0}#gbg .gbtcb{left:0}.gbxx{display:none !important}.gbxo{opacity:0 !important;filter:alpha(opacity=0) !important}.gbm{position:absolute;z-index:999;top:-999px;visibility:hidden;text-align:left;border:1px solid #bebebe;background:#fff;-moz-box-shadow:-1px 1px 1px rgba(0,0,0,.2);-webkit-box-shadow:0 2px 4px rgba(0,0,0,.2);box-shadow:0 2px 4px rgba(0,0,0,.2)}.gbrtl .gbm{-moz-box-shadow:1px 1px 1px rgba(0,0,0,.2)}.gbto .gbm,.gbto #gbs{top:29px;visibility:visible}#gbz .gbm{left:0}#gbg .gbm{right:0}.gbxms{background-color:#ccc;display:block;position:absolute;z-index:1;top:-1px;left:-2px;right:-2px;bottom:-2px;opacity:.4;-moz-border-radius:3px;filter:progid:DXImageTransform.Microsoft.Blur(pixelradius=5);*opacity:1;*top:-2px;*left:-5px;*right:5px;*bottom:4px;-ms-filter:\"progid:DXImageTransform.Microsoft.Blur(pixelradius=5)\";opacity:1\\0/;top:-4px\\0/;left:-6px\\0/;right:5px\\0/;bottom:4px\\0/}.gbma{position:relative;top:-1px;border-style:solid dashed dashed;border-color:transparent;border-top-color:#c0c0c0;display:-moz-inline-box;display:inline-block;font-size:0;height:0;line-height:0;width:0;border-width:3px 3px 0;padding-top:1px;left:4px}#gbztms1,#gbi4m1,#gbi4s,#gbi4t{zoom:1}.gbtc,.gbmc,.gbmcc{display:block;list-style:none;margin:0;padding:0}.gbmc{background:#fff;padding:10px 0;position:relative;z-index:2;zoom:1}.gbt{position:relative;display:-moz-inline-box;display:inline-block;line-height:27px;padding:0;vertical-align:top}.gbt{*display:inline}.gbto{box-shadow:0 2px 4px rgba(0,0,0,.2);-moz-box-shadow:0 2px 4px rgba(0,0,0,.2);-webkit-box-shadow:0 2px 4px rgba(0,0,0,.2)}.gbzt,.gbgt{cursor:pointer;display:block;text-decoration:none !important}span#gbg6,span#gbg4{cursor:default}.gbts{border-left:1px solid transparent;border-right:1px solid transparent;display:block;*display:inline-block;padding:0 5px;position:relative;z-index:1000}.gbts{*display:inline}.gbzt .gbts{display:inline;zoom:1}.gbto .gbts{background:#fff;border-color:#bebebe;color:#36c;padding-bottom:1px;padding-top:2px}.gbz0l .gbts{color:#fff;font-weight:bold}.gbtsa{padding-right:9px}#gbz .gbzt,#gbz .gbgt,#gbg .gbgt{color:#ccc!important}.gbtb2{display:block;border-top:2px solid transparent}.gbto .gbzt .gbtb2,.gbto .gbgt .gbtb2{border-top-width:0}.gbtb .gbts{background:url(//ssl.gstatic.com/gb/images/b_8d5afc09.png);_background:url(//ssl.gstatic.com/gb/images/b8_3615d64d.png);background-position:-27px -22px;border:0;font-size:0;padding:29px 0 0;*padding:27px 0 0;width:1px}.gbzt:hover,.gbzt:focus,.gbgt-hvr,.gbgt:focus{background-color:#4c4c4c;background-image:none;_background-image:none;background-position:0 -102px;background-repeat:repeat-x;outline:none;text-decoration:none !important}.gbpdjs .gbto .gbm{min-width:99%}.gbz0l .gbtb2{border-top-color:#dd4b39!important}#gbi4s,#gbi4s1{font-weight:bold}#gbg6.gbgt-hvr,#gbg6.gbgt:focus{background-color:transparent;background-image:none}.gbg4a{font-size:0;line-height:0}.gbg4a .gbts{padding:27px 5px 0;*padding:25px 5px 0}.gbto .gbg4a .gbts{padding:29px 5px 1px;*padding:27px 5px 1px}#gbi4i,#gbi4id{left:5px;border:0;height:24px;position:absolute;top:1px;width:24px}.gbto #gbi4i,.gbto #gbi4id{top:3px}.gbi4p{display:block;width:24px}#gbi4id{background-position:-44px -101px}#gbmpid{background-position:0 0}#gbmpi,#gbmpid{border:none;display:inline-block;height:48px;width:48px}#gbmpiw{display:inline-block;line-height:9px;padding-left:20px;margin-top:10px;position:relative}#gbmpi,#gbmpid,#gbmpiw{*display:inline}#gbg5{font-size:0}#gbgs5{padding:5px !important}.gbto #gbgs5{padding:7px 5px 6px !important}#gbi5{background:url(//ssl.gstatic.com/gb/images/b_8d5afc09.png);_background:url(//ssl.gstatic.com/gb/images/b8_3615d64d.png);background-position:0 0;display:block;font-size:0;height:17px;width:16px}.gbto #gbi5{background-position:-6px -22px}.gbn .gbmt,.gbn .gbmt:visited,.gbnd .gbmt,.gbnd .gbmt:visited{color:#dd8e27 !important}.gbf .gbmt,.gbf .gbmt:visited{color:#900 !important}.gbmt,.gbml1,.gbmlb,.gbmt:visited,.gbml1:visited,.gbmlb:visited{color:#36c !important;text-decoration:none !important}.gbmt,.gbmt:visited{display:block}.gbml1,.gbmlb,.gbml1:visited,.gbmlb:visited{display:inline-block;margin:0 10px}.gbml1,.gbmlb,.gbml1:visited,.gbmlb:visited{*display:inline}.gbml1,.gbml1:visited{padding:0 10px}.gbml1-hvr,.gbml1:focus{outline:none;text-decoration:underline !important}#gbpm .gbml1{display:inline;margin:0;padding:0;white-space:nowrap}.gbmlb,.gbmlb:visited{line-height:27px}.gbmlb-hvr,.gbmlb:focus{outline:none;text-decoration:underline !important}.gbmlbw{color:#ccc;margin:0 10px}.gbmt{padding:0 20px}.gbmt:hover,.gbmt:focus{background:#eee;cursor:pointer;outline:0 solid black;text-decoration:none !important}.gbm0l,.gbm0l:visited{color:#000 !important;font-weight:bold}.gbmh{border-top:1px solid #bebebe;font-size:0;margin:10px 0}#gbd4 .gbmc{background:#f5f5f5;padding-top:0}#gbd4 .gbsbic::-webkit-scrollbar-track:vertical{background-color:#f5f5f5;margin-top:2px}#gbmpdv{background:#fff;border-bottom:1px solid #bebebe;-moz-box-shadow:0 2px 4px rgba(0,0,0,.12);-o-box-shadow:0 2px 4px rgba(0,0,0,.12);-webkit-box-shadow:0 2px 4px rgba(0,0,0,.12);box-shadow:0 2px 4px rgba(0,0,0,.12);position:relative;z-index:1}#gbd4 .gbmh{margin:0}.gbmtc{padding:0;margin:0;line-height:27px}.GBMCC:last-child:after,#GBMPAL:last-child:after{content:'\\0A\\0A';white-space:pre;position:absolute}#gbmps{*zoom:1}#gbd4 .gbpc,#gbmpas .gbmt{line-height:17px}#gbd4 .gbpgs .gbmtc{line-height:27px}#gbd4 .gbmtc{border-bottom:1px solid #bebebe}#gbd4 .gbpc{display:inline-block;margin:16px 0 10px;padding-right:50px;vertical-align:top}#gbd4 .gbpc{*display:inline}.gbpc .gbps,.gbpc .gbps2{display:block;margin:0 20px}#gbmplp.gbps{margin:0 10px}.gbpc .gbps{color:#000;font-weight:bold}.gbpc .gbpd{margin-bottom:5px}.gbpd .gbmt,.gbpd .gbps{color:#666 !important}.gbpd .gbmt{opacity:.4;filter:alpha(opacity=40)}.gbps2{color:#666;display:block}.gbp0{display:none}.gbp0 .gbps2{font-weight:bold}#gbd4 .gbmcc{margin-top:5px}.gbpmc{background:#fef9db}.gbpmc .gbpmtc{padding:10px 20px}#gbpm{border:0;*border-collapse:collapse;border-spacing:0;margin:0;white-space:normal}#gbpm .gbpmtc{border-top:none;color:#000 !important;font:11px Arial,sans-serif}#gbpms{*white-space:nowrap}.gbpms2{font-weight:bold;white-space:nowrap}#gbmpal{*border-collapse:collapse;border-spacing:0;border:0;margin:0;white-space:nowrap;width:100%}.gbmpala,.gbmpalb{font:13px Arial,sans-serif;line-height:27px;padding:10px 20px 0;white-space:nowrap}.gbmpala{padding-left:0;text-align:left}.gbmpalb{padding-right:0;text-align:right}#gbmpasb .gbps{color:#000}#gbmpal .gbqfbb{margin:0 20px}.gbp0 .gbps{*display:inline}a.gbiba{margin:8px 20px 10px}.gbmpiaw{display:inline-block;padding-right:10px;margin-bottom:6px;margin-top:10px}.gbxv{visibility:hidden}.gbmpiaa{display:block;margin-top:10px}.gbmpia{border:none;display:block;height:48px;width:48px}.gbmpnw{display:inline-block;height:auto;margin:10px 0;vertical-align:top}.gbqfb,.gbqfba,.gbqfbb{-moz-border-radius:2px;-webkit-border-radius:2px;border-radius:2px;cursor:default !important;display:inline-block;font-weight:bold;height:29px;line-height:29px;min-width:54px;*min-width:70px;padding:0 8px;text-align:center;text-decoration:none !important;-moz-user-select:none;-webkit-user-select:none}.gbqfb:focus,.gbqfba:focus,.gbqfbb:focus{border:1px solid #4d90fe;-moz-box-shadow:inset 0 0 0 1px rgba(255, 255, 255, 0.5);-webkit-box-shadow:inset 0 0 0 1px rgba(255, 255, 255, 0.5);box-shadow:inset 0 0 0 1px rgba(255, 255, 255, 0.5);outline:none}.gbqfb-hvr:focus,.gbqfba-hvr:focus,.gbqfbb-hvr:focus{-webkit-box-shadow:inset 0 0 0 1px #fff,0 1px 1px rgba(0,0,0,.1);-moz-box-shadow:inset 0 0 0 1px #fff,0 1px 1px rgba(0,0,0,.1);box-shadow:inset 0 0 0 1px #fff,0 1px 1px rgba(0,0,0,.1)}.gbqfb-no-focus:focus{border:1px solid #3079ed;-moz-box-shadow:none;-webkit-box-shadow:none;box-shadow:none}.gbqfb-hvr,.gbqfba-hvr,.gbqfbb-hvr{-webkit-box-shadow:0 1px 1px rgba(0,0,0,.1);-moz-box-shadow:0 1px 1px rgba(0,0,0,.1);box-shadow:0 1px 1px rgba(0,0,0,.1)}.gbqfb::-moz-focus-inner,.gbqfba::-moz-focus-inner,.gbqfbb::-moz-focus-inner{border:0}.gbqfba,.gbqfbb{border:1px solid #dcdcdc;border-color:rgba(0,0,0,.1);color:#444 !important;font-size:11px}.gbqfb{background-color:#4d90fe;background-image:-webkit-gradient(linear,left top,left bottom,from(#4d90fe),to(#4787ed));background-image:-webkit-linear-gradient(top,#4d90fe,#4787ed);background-image:-moz-linear-gradient(top,#4d90fe,#4787ed);background-image:-ms-linear-gradient(top,#4d90fe,#4787ed);background-image:-o-linear-gradient(top,#4d90fe,#4787ed);background-image:linear-gradient(top,#4d90fe,#4787ed);filter:progid:DXImageTransform.Microsoft.gradient(startColorStr='#4d90fe',EndColorStr='#4787ed');border:1px solid #3079ed;color:#fff!important;margin:0 0}.gbqfb-hvr{border-color:#2f5bb7}.gbqfb-hvr:focus{border-color:#2f5bb7}.gbqfb-hvr,.gbqfb-hvr:focus{background-color:#357ae8;background-image:-webkit-gradient(linear,left top,left bottom,from(#4d90fe),to(#357ae8));background-image:-webkit-linear-gradient(top,#4d90fe,#357ae8);background-image:-moz-linear-gradient(top,#4d90fe,#357ae8);background-image:-ms-linear-gradient(top,#4d90fe,#357ae8);background-image:-o-linear-gradient(top,#4d90fe,#357ae8);background-image:linear-gradient(top,#4d90fe,#357ae8)}.gbqfb:active{background-color:inherit;-webkit-box-shadow:inset 0 1px 2px rgba(0, 0, 0, 0.3);-moz-box-shadow:inset 0 1px 2px rgba(0, 0, 0, 0.3);box-shadow:inset 0 1px 2px rgba(0, 0, 0, 0.3)}.gbqfba{background-color:#f5f5f5;background-image:-webkit-gradient(linear,left top,left bottom,from(#f5f5f5),to(#f1f1f1));background-image:-webkit-linear-gradient(top,#f5f5f5,#f1f1f1);background-image:-moz-linear-gradient(top,#f5f5f5,#f1f1f1);background-image:-ms-linear-gradient(top,#f5f5f5,#f1f1f1);background-image:-o-linear-gradient(top,#f5f5f5,#f1f1f1);background-image:linear-gradient(top,#f5f5f5,#f1f1f1);filter:progid:DXImageTransform.Microsoft.gradient(startColorStr='#f5f5f5',EndColorStr='#f1f1f1')}.gbqfba-hvr,.gbqfba-hvr:active{background-color:#f8f8f8;background-image:-webkit-gradient(linear,left top,left bottom,from(#f8f8f8),to(#f1f1f1));background-image:-webkit-linear-gradient(top,#f8f8f8,#f1f1f1);background-image:-moz-linear-gradient(top,#f8f8f8,#f1f1f1);background-image:-ms-linear-gradient(top,#f8f8f8,#f1f1f1);background-image:-o-linear-gradient(top,#f8f8f8,#f1f1f1);background-image:linear-gradient(top,#f8f8f8,#f1f1f1);filter:progid:DXImageTransform.Microsoft.gradient(startColorStr='#f8f8f8',EndColorStr='#f1f1f1')}.gbqfbb{background-color:#fff;background-image:-webkit-gradient(linear,left top,left bottom,from(#fff),to(#fbfbfb));background-image:-webkit-linear-gradient(top,#fff,#fbfbfb);background-image:-moz-linear-gradient(top,#fff,#fbfbfb);background-image:-ms-linear-gradient(top,#fff,#fbfbfb);background-image:-o-linear-gradient(top,#fff,#fbfbfb);background-image:linear-gradient(top,#fff,#fbfbfb);filter:progid:DXImageTransform.Microsoft.gradient(startColorStr='#ffffff',EndColorStr='#fbfbfb')}.gbqfbb-hvr,.gbqfbb-hvr:active{background-color:#fff;background-image:-webkit-gradient(linear,left top,left bottom,from(#fff),to(#f8f8f8));background-image:-webkit-linear-gradient(top,#fff,#f8f8f8);background-image:-moz-linear-gradient(top,#fff,#f8f8f8);background-image:-ms-linear-gradient(top,#fff,#f8f8f8);background-image:-o-linear-gradient(top,#fff,#f8f8f8);background-image:linear-gradient(top,#fff,#f8f8f8);filter:progid:DXImageTransform.Microsoft.gradient(startColorStr='#ffffff',EndColorStr='#f8f8f8')}.gbqfba-hvr,.gbqfba-hvr:active,.gbqfbb-hvr,.gbqfbb-hvr:active{border-color:#c6c6c6;-webkit-box-shadow:0 1px 1px rgba(0,0,0,.1);-moz-box-shadow:0 1px 1px rgba(0,0,0,.1);box-shadow:0 1px 1px rgba(0,0,0,.1);color:#222 !important}.gbqfba:active,.gbqfbb:active{-webkit-box-shadow:inset 0 1px 2px rgba(0,0,0,.1);-moz-box-shadow:inset 0 1px 2px rgba(0,0,0,.1);box-shadow:inset 0 1px 2px rgba(0,0,0,.1)}#gbmpas{max-height:220px}#gbmm{max-height:530px}.gbsb{-webkit-box-sizing:border-box;display:block;position:relative;*zoom:1}.gbsbic{overflow:auto}.gbsbis .gbsbt,.gbsbis .gbsbb{-webkit-mask-box-image:-webkit-gradient(linear,left top,right top,color-stop(0,rgba(0,0,0,.1)),color-stop(.5,rgba(0,0,0,.8)),color-stop(1,rgba(0,0,0,.1)));left:0;margin-right:0;opacity:0;position:absolute;width:100%}.gbsb .gbsbt:after,.gbsb .gbsbb:after{content:\"\";display:block;height:0;left:0;position:absolute;width:100%}.gbsbis .gbsbt{background:-webkit-gradient(linear,left top,left bottom,from(rgba(0,0,0,.2)),to(rgba(0,0,0,0)));background-image:-webkit-linear-gradient(top,rgba(0,0,0,.2),rgba(0,0,0,0));background-image:-moz-linear-gradient(top,rgba(0,0,0,.2),rgba(0,0,0,0));background-image:-ms-linear-gradient(top,rgba(0,0,0,.2),rgba(0,0,0,0));background-image:-o-linear-gradient(top,rgba(0,0,0,.2),rgba(0,0,0,0));background-image:linear-gradient(top,rgba(0,0,0,.2),rgba(0,0,0,0));height:6px;top:0}.gbsb .gbsbt:after{border-top:1px solid #ebebeb;border-color:rgba(0,0,0,.3);top:0}.gbsb .gbsbb{-webkit-mask-box-image:-webkit-gradient(linear,left top,right top,color-stop(0,rgba(0,0,0,.1)),color-stop(.5,rgba(0,0,0,.8)),color-stop(1,rgba(0,0,0,.1)));background:-webkit-gradient(linear,left bottom,left top,from(rgba(0,0,0,.2)),to(rgba(0,0,0,0)));background-image:-webkit-linear-gradient(bottom,rgba(0,0,0,.2),rgba(0,0,0,0));background-image:-moz-linear-gradient(bottom,rgba(0,0,0,.2),rgba(0,0,0,0));background-image:-ms-linear-gradient(bottom,rgba(0,0,0,.2),rgba(0,0,0,0));background-image:-o-linear-gradient(bottom,rgba(0,0,0,.2),rgba(0,0,0,0));background-image:linear-gradient(bottom,rgba(0,0,0,.2),rgba(0,0,0,0));bottom:0;height:4px}.gbsb .gbsbb:after{border-bottom:1px solid #ebebeb;border-color:rgba(0,0,0,.3);bottom:0}</style><style>body{font-family:arial,sans-serif}td{font-family:arial,sans-serif}a{font-family:arial,sans-serif}p{font-family:arial,sans-serif}.h{font-family:arial,sans-serif}body{margin:0;overflow-y:scroll}#gog{padding:3px 8px 0}td{line-height:.8em}.gac_m td{line-height:17px}form{margin-bottom:20px}.h{color:#36c}.q{color:#00c}.ts td{padding:0}.ts{border-collapse:collapse}em{font-weight:bold;font-style:normal}.lst{height:25px;width:496px;font:18px arial,sans-serif}.gsfi{font:18px arial,sans-serif}.gsfs{font:17px arial,sans-serif}.ds{display:inline-box;display: inline-block;margin:3px 0 4px;margin-left:4px}input{font-family:inherit}body{background:#fff;color:black}a.gb1{color:#11c !important}a.gb2{color:#11c !important}a.gb3{color:#11c !important}a.gb4{color:#11c !important}a{color:#11c;text-decoration:none}a:hover{text-decoration:underline}a:active{text-decoration:underline}.fl a{color:#36c}a:visited{color:#551a8b}a.gb1{text-decoration:underline}a.gb4{text-decoration:underline}a.gb3:hover{text-decoration:none}.sblc{padding-top:5px}.sblc a{display:block;margin:2px 0;margin-left:13px;font-size:11px}.lsbb{background:#eee;border:solid 1px;border-color:#ccc #999 #999 #ccc;height:30px}#ghead a.gb2:hover{color:#fff !important}.lsbb{display:block}.ftl{display:inline-block;margin:0 12px}#fll a{display:inline-block;margin:0 12px}.lsb{background:url(/images/srpr/nav_logo80.png) 0 -258px repeat-x;border:none;color:#000;cursor:pointer;height:30px;margin:0;outline:0;font:15px arial,sans-serif;vertical-align:top}.lsb:active{background:#ccc}.lst:focus{outline:none}#addlang a{padding:0 3px}</style></head><body bgcolor=\"#fff\"><textarea id=\"csi\" style=\"display:none\"></textarea><div id=\"mngb\"><div id=gb><div id=gbw><div id=gbz><span class=gbtcb></span><ol id=gbzc class=gbtc><li class=gbt><a onclick=gbar.logger.il(1,{t:1}); class=\"gbzt gbz0l gbp1\" id=gb_1 href=\"http://www.google.com.ec/webhp?hl=es-419&tab=ww\"><span class=gbtb2></span><span class=gbts>B�squeda</span></a></li><li class=gbt><a onclick=gbar.qs(this);gbar.logger.il(1,{t:2}); class=gbzt id=gb_2 href=\"http://www.google.com.ec/imghp?hl=es-419&tab=wi\"><span class=gbtb2></span><span class=gbts>Im�genes</span></a></li><li class=gbt><a onclick=gbar.qs(this);gbar.logger.il(1,{t:8}); class=gbzt id=gb_8 href=\"http://maps.google.com.ec/maps?hl=es-419&tab=wl\"><span class=gbtb2></span><span class=gbts>Maps</span></a></li><li class=gbt><a onclick=gbar.qs(this);gbar.logger.il(1,{t:78}); class=gbzt id=gb_78 href=\"https://play.google.com/?hl=es-419&tab=w8\"><span class=gbtb2></span><span class=gbts>Play</span></a></li><li class=gbt><a onclick=gbar.logger.il(1,{t:23}); class=gbzt id=gb_23 href=\"https://mail.google.com/mail/?tab=wm\"><span class=gbtb2></span><span class=gbts>Gmail</span></a></li><li class=gbt><a onclick=gbar.logger.il(1,{t:25}); class=gbzt id=gb_25 href=\"https://drive.google.com/?tab=wo\"><span class=gbtb2></span><span class=gbts>Drive</span></a></li><li class=gbt><a onclick=gbar.logger.il(1,{t:24}); class=gbzt id=gb_24 href=\"https://www.google.com/calendar?tab=wc\"><span class=gbtb2></span><span class=gbts>Calendario</span></a></li><li class=gbt><a onclick=gbar.qs(this);gbar.logger.il(1,{t:51}); class=gbzt id=gb_51 href=\"http://translate.google.com.ec/?hl=es-419&tab=wT\"><span class=gbtb2></span><span class=gbts>Traductor</span></a></li><li class=gbt><a class=gbgt id=gbztm href=\"http://www.google.com.ec/intl/es-419/options/\" onclick=\"gbar.tg(event,this)\" aria-haspopup=true aria-owns=gbd><span class=gbtb2></span><span id=gbztms class=\"gbts gbtsa\"><span id=gbztms1>M�s</span><span class=gbma></span></span></a><div class=gbm id=gbd aria-owner=gbztm><div id=gbmmb class=\"gbmc gbsb gbsbis\"><ol id=gbmm class=\"gbmcc gbsbic\"><li class=gbmtc><a onclick=gbar.qs(this);gbar.logger.il(1,{t:10}); class=gbmt id=gb_10 href=\"http://books.google.com.ec/bkshp?hl=es-419&tab=wp\">Libros</a></li><li class=gbmtc><a onclick=gbar.logger.il(1,{t:30}); class=gbmt id=gb_30 href=\"http://www.blogger.com/?tab=wj\">Blogger</a></li><li class=gbmtc><a onclick=gbar.qs(this);gbar.logger.il(1,{t:12}); class=gbmt id=gb_12 href=\"http://video.google.com.ec/?hl=es-419&tab=wv\">Videos</a></li><li class=gbmtc><div class=\"gbmt gbmh\"></div></li><li class=gbmtc><a onclick=gbar.logger.il(1,{t:66}); href=\"http://www.google.com.ec/intl/es-419/options/\" class=gbmt>Todav�a m�s &raquo;</a></li></ol><div class=gbsbt></div><div class=gbsbb></div></div></div></li></ol></div><div id=gbg><h2 class=gbxx>Account Options</h2><span class=gbtcb></span><ol class=gbtc><li class=gbt><a target=_top href=\"https://accounts.google.com/ServiceLogin?hl=es-419&continue=http://www.google.com.ec/%3Fgws_rd%3Dcr\" onclick=\"gbar.logger.il(9,{l:'i'})\" id=gb_70 class=gbgt><span class=gbtb2></span><span id=gbgs4 class=gbts><span id=gbi4s1>Iniciar sesi�n</span></span></a></li><li class=\"gbt gbtb\"><span class=gbts></span></li><li class=gbt><a class=gbgt id=gbg5 href=\"http://www.google.com.ec/preferences?hl=es-419\" title=\"Opciones\" onclick=\"gbar.tg(event,this)\" aria-haspopup=true aria-owns=gbd5><span class=gbtb2></span><span id=gbgs5 class=gbts><span id=gbi5></span></span></a><div class=gbm id=gbd5 aria-owner=gbg5><div class=gbmc><ol id=gbom class=gbmcc><li class=\"gbkc gbmtc\"><a  class=gbmt href=\"/preferences?hl=es-419\">Configuraci�n de b�squeda</a></li><li class=gbmtc><div class=\"gbmt gbmh\"></div></li><li class=\"gbkp gbmtc\"><a class=gbmt href=\"http://www.google.com.ec/history/optout?hl=es-419\">Historial web</a></li></ol></div></div></li></ol></div></div><div id=gbx3></div><div id=gbx4></div></div></div><center><br clear=\"all\" id=\"lgpd\"><div id=\"lga\"><div style=\"padding:28px 0 3px\"><div title=\"Google\" align=\"left\" id=\"hplogo\" onload=\"window.lol&&lol()\" style=\"height:110px;width:276px;background:url(/images/srpr/logo1w.png) no-repeat\"><div nowrap=\"nowrap\" style=\"color:#777;font-size:16px;font-weight:bold;position:relative;top:70px;left:214px\">Ecuador</div></div></div><br></div><form action=\"/search\" name=\"f\"><table cellpadding=\"0\" cellspacing=\"0\"><tr valign=\"top\"><td width=\"25%\">&nbsp;</td><td align=\"center\" nowrap=\"nowrap\"><input name=\"ie\" value=\"ISO-8859-1\" type=\"hidden\"><input value=\"es-419\" name=\"hl\" type=\"hidden\"><input name=\"source\" type=\"hidden\" value=\"hp\"><div class=\"ds\" style=\"height:32px;margin:4px 0\"><input autocomplete=\"off\" class=\"lst\" value=\"\" title=\"Buscar con Google\" maxlength=\"2048\" name=\"q\" size=\"57\" style=\"color:#000;margin:0;padding:5px 8px 0 6px;vertical-align:top\"></div><br style=\"line-height:0\"><span class=\"ds\"><span class=\"lsbb\"><input class=\"lsb\" value=\"Buscar con Google\" name=\"btnG\" type=\"submit\"></span></span><span class=\"ds\"><span class=\"lsbb\"><input class=\"lsb\" value=\"Me siento con suerte \" name=\"btnI\" type=\"submit\" onclick=\"if(this.form.q.value)this.checked=1; else top.location='/doodles/'\"></span></span></td><td class=\"fl sblc\" align=\"left\" nowrap=\"nowrap\" width=\"25%\"><a href=\"/advanced_search?hl=es-419&amp;authuser=0\">B�squeda avanzada</a><a href=\"/language_tools?hl=es-419&amp;authuser=0\">Herramientas de idioma</a></td></tr></table><input type=\"hidden\" id=\"gbv\" name=\"gbv\" value=\"1\"></form><div id=\"gac_scont\"></div><div style=\"font-size:83%;min-height:3.5em\"><br></div><span id=\"footer\"><div style=\"font-size:10pt\"><div id=\"fll\" style=\"margin:19px auto;text-align:center\"><a href=\"/intl/es-419/ads/\">Programas de publicidad</a><a href=\"/services/\">Soluciones Empresariales</a><a href=\"/intl/es-419/about.html\">Todo acerca de Google</a><a href=\"http://www.google.com.ec/setprefdomain?prefdom=US&amp;sig=0_y0XZK5S3y6D1RZKKNYC8y0VEyU4%3D\" id=\"fehl\">Google.com</a></div></div><p style=\"color:#767676;font-size:8pt\">&copy; 2013 - <a href=\"/intl/es-419/policies/\">Privacidad y condiciones</a></p></span></center><div id=xjsd></div><div id=xjsi></div></body></html>");
            //añadiendo leer links en todas las pestañas
            n.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
//                   JOptionPane.showMessageDialog( null, e.getURL().toString());
                   String ref[]=e.getDescription().replaceAll("http://", "").split("/");
                   
                   //comprobando que sea una direccion valida
                    System.out.println(ref[0]);
                   if(ref[0].contains(".")){
                       
                       
                    Pestañas seleccionada = ((Pestañas)Tab1.getTabComponentAt(Tab1.getSelectedIndex()));
                    cargar.setUrl(e.getURL().toExternalForm());
                    int temp = seleccionada.getHistorialSize();
                     if(temp!=seleccionada.getNum()+1)
                         seleccionada.delPags();
                    seleccionada.addPagina(e.getURL().toExternalForm());
                    seleccionada.setNumS();
                    cargar.cargarPag();
                    Adelante.setEnabled(false);
                   }
                   else
                    JOptionPane.showMessageDialog( null, "Direcciones de referencia no disponibles");


                }
            }
        });
            
            //creando JScrollPane dond voy a meter a mi JTextPane
            JScrollPane sc = new JScrollPane();
            //Añadiendo el JTextPane al JScrollPane
            sc.setViewportView(n);
            System.out.println(pestañas);
            //Añadiendo el JTextPane al tab
            Tab1.add(sc, pestañas);
       
            //almacenando los textpane en un arreglo para luego poder editarlos ordenadamente
            texts.add(n);
            
            //llamada a mi componente Pestaña y añadiendole el index del tab al que estará asignado
            final Pestañas nueva = new Pestañas();
//            nueva.setIndex(pestañas);
            
            
            
      //**************************************************************************************************************      
            //evento para cerrar pestañas
            nueva.getCerrar().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //Validacion para que siempre quede almenos una pestaña
                if(pestañas>1){
                    
                    
                    //validando para que nunca quede activada la ultima pestaña(pestaña de agregacion '+')
                    if(Tab1.getSelectedIndex()==pestañas-1)
                        Tab1.setSelectedIndex(pestañas-2);
                    
                    
                    //recuperando el indice del tab en el que se encuentra ese componente y con el elimino de los arreglos y asigno nuevo 
                    //valores de indice a los componentes restantes posteriores
//                    int index=nueva.getIndex();
                    int index =Tab1.indexOfTabComponent(nueva);
                    System.out.println(Tab1.indexOfTabComponent(nueva));
                    Tab1.remove(index);
                    texts.remove(index);
                    pestañas--;
//                    for(int i = index;i<pestañas;i++)
//                        ((Pestañas)Tab1.getTabComponentAt(i)).setIndex(i);
                    
                    
                    //eliminando arreglos de los historiales
                    Htmls.remove(index);
                    Https.remove(index);
                    
                    
                    actualizarBtns();
                    
                    
                }
                
            }
             });
            
            
        //*********************************************************************************************************************    
            
          
            //añado el componente Pestaña al tab recien creado, aumento el numero de tabs y selecciono el final.
            Tab1.setTabComponentAt(pestañas, nueva);
            pestañas++;
            Tab1.setSelectedIndex(pestañas-1);
            
        
    }
/***********************************************FIN CREAR PESTAÑAS**************************************************************************************/    
    
    
    
/**************************************************************************************************/
    public class Cargar implements Runnable{
        
    boolean correr=false;
    boolean continuar=true;
    String Url="";
    int seleccionado=0;
        @Override
        public void run() {
            while(true){
                seleccionado=Tab1.getSelectedIndex();
                ((Pestañas)Tab1.getTabComponentAt(seleccionado)).setIcon(null);
                try {
                    Thread.sleep(50);
                    if(correr){
                        seleccionado=Tab1.getSelectedIndex();
                        ((Pestañas)Tab1.getTabComponentAt(seleccionado)).setIcon(Directorio+"imagenes/loader.gif");
                        texts.get(seleccionado).removeAll();
                        url.setText(Url);
                        Url =Url.replaceAll("http://", "");
                        if(!Url.contains("/"))
                            Url=Url+"/";
                        String cadenas[]=Url.split("/");
                        System.out.println(Url.substring(cadenas[0].length()));
                        String servidor=cadenas[0];
                        String pagina =Url.substring(cadenas[0].length());
                        conexion.close();
                        conexion=new Socket(InetAddress.getByName(servidor), 80);
                        System.out.println(pagina+' '+servidor);
                        String requerimiento="\n\n\n\nGET "+pagina+" HTTP/1.1\nHost: "+servidor+"\nConnection: close\n\n";

                        PrintWriter pw = new PrintWriter(conexion.getOutputStream());
                        pw.print(requerimiento);
                        pw.flush();
                        String guardar;
                        BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                        guardar = in.readLine();
                       
                        
                        
                        continuar=true;
                        boolean inicio=false;
                        while(continuar){
                            String linea= in.readLine();
                            
                            //validando hasta donde llega la pagina web
                            if(linea.toUpperCase().contains("<HTML")&&!inicio){
                                inicio=true;                                
                            }
                            
                            if(linea!=null)
                                guardar = guardar+"\n" +linea;
                            else
                                continuar=false;
                            
                            //verificando si las paginas iniciaban con el tag html
                            if(inicio){
                                if(linea.toUpperCase().contains("</HTML>"))
                                    continuar=false;
                            }
                            else
                                if(linea.toUpperCase().contains("</BODY>"))
                                    continuar=false;
                        }
                        
                        Respuesta=guardar;
                        if(Respuesta.substring(9, 12).equals("301")||Respuesta.substring(9, 12).equals("302"))
                        {
                            int inicia =Respuesta.toUpperCase().indexOf("LOCATION: ")+10;
                            cargar.setUrl(Respuesta.substring(inicia, inicia+Respuesta.substring(inicia).indexOf("\n")));
                            continue;
                            
                        }
                            
                        Https.set(Tab1.getSelectedIndex(), Respuesta);
                        String[] codigo =guardar.split("<");
                        Html = guardar.substring(codigo[0].length());
                        
                        //reemplanzando el tag meta que provoca que las paginas dejen de ser reconocidas.
                        Html=Html.replaceAll("<meta", "<metas");
                        Html=Html.replaceAll("<Meta", "<Metas");
                        Html=Html.replaceAll("<META", "<METAS");
                        
                        /*Espacio para intentar eliminar los scripts*/ 
                        
                        String fas[] = Html.split("<script>");
                        String fasz="";
                        int i=0;
                        fasz=fas[i];
                        for(i=1;i<fas.length;i++)
                            fasz=fasz+fas[i].substring(fas[i].indexOf("</script>")+9)+"\n";
//                        fasz=fasz+fas[i];
                        System.out.println(fasz);
                        Html=fasz;
                        /*Espacio para intentar eliminar los scripts*/ 
                        
                        Htmls.set(Tab1.getSelectedIndex(), Html);
                        System.out.println(Html);
                        System.out.println("1");
                        
                        
                        texts.get(seleccionado).setContentType("text/html");
                        texts.get(seleccionado).setText(Html);
                        System.out.println(texts.get(pestañas-1));
                        System.out.println("2");
                        
                        String titulo="";
                        if(Html.toUpperCase().contains("<TITLE>"))
                            titulo=Html.substring(Html.toUpperCase().indexOf("<TITLE>")+7, Html.toUpperCase().indexOf("</TITLE>"));
                        
                        else
                            titulo=Url;
                        
                        este.setTitle(titulo);
                        ((Pestañas)Tab1.getTabComponentAt(seleccionado)).setTitle(titulo);
                        
                        Pestañas seleccionada = ((Pestañas)Tab1.getTabComponentAt(Tab1.getSelectedIndex()));
                        if(seleccionada.getNum()!=0)
                            Atras.setEnabled(true);
                        correr=false;
                        
                        //almacenar pagina en historial
                        actualizarHistorial(titulo+"#"+Url);
                        
                    }
//                    System.out.println(Respuesta);
                
                } catch (IOException ex) {
    //                Logger.getLogger(Navegador.class.getName()).log(Level.SEVERE, null, ex);
                    texts.get(seleccionado).setText("<h>Error: Pagina no encontrada</h>");
                    correr=false;
                } catch (InterruptedException ex) {
                Logger.getLogger(Navegador.class.getName()).log(Level.SEVERE, null, ex);
                }catch(Exception e){
                    texts.get(seleccionado).setText("<h>Error: Pagina no encontrada</h>");
                    correr=false;
                }
            
            }
            
        }
        
        public void cargarPag(){
            continuar=false;
            
            correr=true;
        }
        
        public void setUrl(String url){
            Url=url;
        }

}
/*******************************************************************************************************************/
    
    
    public void verPag(){
        
        texts.get(Tab1.getSelectedIndex()).setContentType("text/html");
        texts.get(Tab1.getSelectedIndex()).setText(Htmls.get(Tab1.getSelectedIndex()));
    }
    
    
    public void verHttp(){
        
        texts.get(Tab1.getSelectedIndex()).setContentType("");
        texts.get(Tab1.getSelectedIndex()).setText(Https.get(Tab1.getSelectedIndex()));
    }
    
    public void actualizarArchivo(){
        File homepag= new File("home.cm");
        try {
            FileWriter fw = new FileWriter(homepag);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("<HOME>"+home+"</HOME>\n");
            bw.write("\n");
            bw.write("<MARCADORES>\n");

            for(String marcador:marcadores)
                bw.write(marcador+"\n");

            bw.write("</MARCADORES>\n");
            bw.write("\n");
            bw.write("<HISTORIAL>\n");

            for(String histo:historial)
                bw.write(histo+"\n");

            bw.write("</HISTORIAL>\n");
            bw.write("\n");
            bw.write("<FIN>\n");
            bw.close();
        } catch (Exception e) {
        }
        
        
    }
    
    public void cargarArchivos(){
        File homepag=new File("home.cm");
        try {
            //verificando si ya esxiste el archivo... de no ser asi lo crea
            if(!homepag.exists()){
                home="http://www.cs.bham.ac.uk/~tpc/testpages/";
                marcadores.add("prueba1#http://www.cs.bham.ac.uk/~tpc/testpages/");
                marcadores.add("prueba2#http://sheldonbrown.com/web_sample1.html");
                System.out.println(homepag);
                actualizarArchivo();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error :"+e.getMessage());
        }
        
        //lectura del archivo de configuraciones.
        try {
            FileReader fr = new FileReader(homepag);
            BufferedReader buff = new BufferedReader(fr);
            home=buff.readLine();
            home=home.substring(6, home.indexOf("</HOME>"));
            buff.readLine();
            buff.readLine();
            String marca;
            marcadores=new ArrayList<>();
            while(!(marca=buff.readLine()).equals("</MARCADORES>")){
                crearMarcador(marca);
            }
            buff.readLine();
            buff.readLine();
            while(!(marca=buff.readLine()).equals("</HISTORIAL>")){
                historial.add(marca);
            }
            
            buff.close();
            fr.close();
        } catch (Exception ex) {
         
            JOptionPane.showMessageDialog(null, "error: "+ex.getMessage());
        }
    }
    
    
    public void crearMarcador(String marca){
        marcadores.add(marca);
                final String marcas[]=marca.split("#");
                numMarca++;
                JButton m=new JButton(marcas[0]);
                
                /**Creando limitante de tamaño del contenido de los botones*/
                if(marcas[0].length()>20)
                    m.setText(marcas[0].substring(0, 17)+"...");
                if(marcas[0].length()<20)
                    for(int i = marcas[0].length();i<20;i++)
                        m.setText(m.getText()+" ");
                if(marcas[0].length()==20)
                    m.setText(marcas[0]);
                        
                JPopupMenu pop = new JPopupMenu(numMarca+"");
                JMenuItem menu1 = new JMenuItem("Eliminar");
                menu1.addActionListener(new java.awt.event.ActionListener() {
                     public void actionPerformed(java.awt.event.ActionEvent evt) {
                         System.out.println(((JPopupMenu)((JComponent)evt.getSource()).getParent()).getLabel());
                         MarcadoresPanel.remove(Integer.parseInt(((JPopupMenu)((JComponent)evt.getSource()).getParent()).getLabel()));
                         marcadores.remove(Integer.parseInt(((JPopupMenu)((JComponent)evt.getSource()).getParent()).getLabel()));
                         actualizarArchivo();
                         for(int i =0;i<MarcadoresPanel.getComponents().length;i++)
                             ((JButton)MarcadoresPanel.getComponent(i)).getComponentPopupMenu().setLabel(i+"");
                         MarcadoresPanel.setVisible(false);
                         MarcadoresPanel.setVisible(true);
                         numMarca--;
                         
                     }
                 });
                 pop.add(menu1);
                 m.setComponentPopupMenu(pop);
                 
                
                m.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseReleased(java.awt.event.MouseEvent evt) {
                        if(!cargar.correr){
                            cargar.setUrl(marcas[1]);
                            cargar.cargarPag();
                            Adelante.setEnabled(false);
                            Pestañas seleccionada = ((Pestañas)Tab1.getTabComponentAt(Tab1.getSelectedIndex()));
                            int temp = seleccionada.getHistorialSize();
                            if(temp!=seleccionada.getNum()+1)
                                seleccionada.delPags();
                            seleccionada.addPagina(marcas[1]);
                            seleccionada.setNumS();
                        }  
                    }
                });
                
                MarcadoresPanel.add(m);
    }
    
    public void actualizarHistorial(String Url){
        if(historial.contains(Url))
            historial.remove(historial.indexOf(Url));
        historial.add(Url);
        actualizarArchivo();
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
            java.util.logging.Logger.getLogger(Navegador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Navegador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Navegador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Navegador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Navegador().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Adelante;
    private javax.swing.JButton Atras;
    private javax.swing.JButton Home;
    private javax.swing.JPanel MarcadoresPanel;
    private javax.swing.JToggleButton MostrarMar;
    private javax.swing.JButton Recargar;
    private javax.swing.JTabbedPane Tab1;
    private javax.swing.JMenuItem cerrar;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextField url;
    // End of variables declaration//GEN-END:variables
}
