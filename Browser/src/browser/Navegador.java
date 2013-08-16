/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package browser;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;



/**
 *
 * @author Cesar Madrid
 */
public class Navegador extends javax.swing.JFrame {

    Socket conexion ;
    Navegador este = this;
    String Directorio;
    String Html="";
    String Respuesta="";
    int pestañas=0;
    String home="";
    ArrayList<String>marcadores = new ArrayList<>();
    ArrayList<String> historial = new ArrayList<>();
    Historial histo=null;
    int numMarca=-1;
    Map<String,String>cooks=new HashMap<>();
    Map<String, Map<String,Cookie>> cookies= new HashMap<>();
    
    /**
     * Creates new form Navegador
     */
    public JTextField getUrl(){
        return url;
    }
    
    public JTabbedPane getTab(){
        return Tab1;
    }
    
    public JButton getAtras(){
        return Atras;
    }
    public Navegador() {
        initComponents();
        MarcadoresPanel.setLayout(new FlowLayout(0));
        MarcadoresPanel.setVisible(false);
        
        //directorio dond localizar los recursos
        Directorio=getClass().getResource("").toExternalForm();
        Directorio=(String) Directorio.subSequence(0, Directorio.length()-8);
        
        
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
        
        
        
        cargarArchivos();
        
        
        leerCookies();
        System.out.println(cookies);
        
        
        
        
        
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
        
        ;
        

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
        Abrirpag = new javax.swing.JMenuItem();
        guardarpag = new javax.swing.JMenuItem();
        definir_home = new javax.swing.JMenuItem();
        cerrar = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        añadir_marcador = new javax.swing.JMenuItem();
        menu_historial = new javax.swing.JMenuItem();

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

        jMenu1.setText("Archivo");

        Abrirpag.setText("Abrir Pagina");
        Abrirpag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AbrirpagActionPerformed(evt);
            }
        });
        jMenu1.add(Abrirpag);

        guardarpag.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        guardarpag.setText("Guardar Página");
        guardarpag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarpagActionPerformed(evt);
            }
        });
        jMenu1.add(guardarpag);

        definir_home.setText("Definir pagina de Inicio");
        definir_home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                definir_homeActionPerformed(evt);
            }
        });
        jMenu1.add(definir_home);

        cerrar.setText("Cerrar");
        cerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerrarActionPerformed(evt);
            }
        });
        jMenu1.add(cerrar);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Herramientas");

        añadir_marcador.setText("Añadir a marcadores");
        añadir_marcador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                añadir_marcadorActionPerformed(evt);
            }
        });
        jMenu2.add(añadir_marcador);

        menu_historial.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        menu_historial.setText("Historial");
        menu_historial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_historialActionPerformed(evt);
            }
        });
        jMenu2.add(menu_historial);

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
        
        //controlo si tiene presionado control para agregar al url .com
        if(key==KeyEvent.VK_CONTROL)
            crtl=true;
            
        Pestanas seleccionada = ((Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex()));
        
        if(key==KeyEvent.VK_ENTER && seleccionada.cargando()==false)
        {
            //si crtl es true agregara el .com al final
            if(crtl==true)
                url.setText(url.getText()+".com");
            
            
            //mando el url a cargar, actualizo el historial de la pestaña, mando a cargar la pagina y deshabilito el boton adelante
            seleccionada.getCargar().setUrl(url.getText());
            alterarHistorial(url.getText());
            
            seleccionada.getCargar().cargarPag();
            Adelante.setEnabled(false);
        
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_urlKeyPressed

    private void AtrasMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AtrasMouseReleased
        Pestanas seleccionada = ((Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex()));//recupero la pestaña actual
        //verifico que el boton atras este activado y no este cargando una pagina
        if(Atras.isEnabled()&&!seleccionada.cargando()){
            seleccionada.setNumR();//reduce en uno mi variable que indica donde estoy aputando de la lista de historiales
            seleccionada.getCargar().setUrl(seleccionada.getPagina());
            seleccionada.getCargar().cargarPag();
            Adelante.setEnabled(true);
            if(seleccionada.getNum()==0)//si ya estoy apuntando al index 0 de mi lista se deshabilita el boton atras
                Atras.setEnabled(false);
            System.out.println(seleccionada.getNum());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_AtrasMouseReleased

    private void AdelanteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AdelanteMouseReleased
        
        Pestanas seleccionada = ((Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex()));
        //verifico que el boton adelante este activado y no este cargando una pagina
        if(Adelante.isEnabled()&&!seleccionada.cargando()){
            //recupero la pestaña que tengo seleccionada y hago un recorrido hacia adelante de su historial y activo el boton atras
            seleccionada.getCargar().setUrl(seleccionada.getPaginaS());
            seleccionada.setNumS();//aumenta en uno mi variable que indica donde estoy aputando de la lista de historiales
            seleccionada.getCargar().cargarPag();
            if(seleccionada.getHistorialSize()==seleccionada.getNum()+1)//si ya estoy apuntando al index final de mi lista se deshabilita el boton adelante
                Adelante.setEnabled(false);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_AdelanteMouseReleased

    //boton recargar de la aplicacion
    private void RecargarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RecargarMouseReleased
        
        Pestanas seleccionada = ((Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex()));
        seleccionada.getCargar().cargarPag();
        // TODO add your handling code here:
    }//GEN-LAST:event_RecargarMouseReleased

    //funcion cuando levanto una tecla, hace que desactive la notificacion de presionar control si es el boton levantado
    private void urlKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_urlKeyReleased

        int key=evt.getKeyCode();
        
        if(key==KeyEvent.VK_CONTROL)
            crtl=false;
        // TODO add your handling code here:
    }//GEN-LAST:event_urlKeyReleased

    //esta funcion manda a cerrar la aplicacion
    private void cerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrarActionPerformed

        System.exit(0);
        // TODO add your handling code here:
    }//GEN-LAST:event_cerrarActionPerformed

    //cambios de pestaña en mi tab
    private void Tab1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_Tab1StateChanged
        //detecto si el cambio fue realizado a el ultimo tab designado para abrir nuevas pestañas, de ser asi llama a la funcion
        if(Tab1.getSelectedIndex()==pestañas){
            
            Tab1.setSelectedIndex(pestañas-1);//quita la seleccion de la pestaña final
            crearPestaña();//funcion para crear la nueva pestaña
            
        }
        else//en caso de se cualquier otra pestaña siempre y cuandoexista mas de una (Sin incluir la de creacion)
            if(pestañas>1)
            {
                
                Pestanas seleccionada = ((Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex()));
                setTitle(((Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex())).getTitle());//agrego a la ventana el titulo de la pestaña seleccionada
                actualizarBtns();//actualizo los botones de historial de la pestaña
                if(seleccionada.getNum()==-1)
                    url.setText("");//en caso de ser pestaña nueva pestaña quitar el url
                else
                    url.setText(seleccionada.getPagina());//en caso de ser una pestaña con paginas ya cargadas colocar el url de la pag actual
                
            }
        
        
        // TODO add your handling code here:
    }//GEN-LAST:event_Tab1StateChanged

    //accion del boton home o pagina de inicio
    private void HomeMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HomeMouseReleased

        
        Pestanas seleccionada = ((Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex()));
        //verifico q no este cargando una pagina antes de iniciar el evento
        if(!seleccionada.cargando()){
            
            alterarHistorial(home);//actualizo el historial de la pestaña
            seleccionada.getCargar().setUrl(home);//agrego el url a cargar(el home)
            seleccionada.getCargar().cargarPag();//cargo la pagina
            
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_HomeMouseReleased
    
    //metodo que actualiza el historial de mi pestaña
    public void alterarHistorial(String pag){
        //recupero la pestaña seleccionada
        Pestanas seleccionada = ((Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex()));
        
       seleccionada.delPags();//manda a borrar todas las paginas posteriores a la que me encuentro 
       seleccionada.addPagina(pag);//agrego la pagina recien cargada al final de la lista
       seleccionada.setNumS();//aumento en uno el numero indicador de ubicacion en la lista
           
    }
    
    //accion del boton home(pagina de inicio)
    private void definir_homeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_definir_homeActionPerformed
        //recupero la pestaña sobre la que estoy ubicado y obtengo su pagina actual
        Pestanas seleccionada = ((Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex()));
        if(seleccionada.getNum()==-1){
            JOptionPane.showMessageDialog(este, "Ninguna página disponible.");
            return;
        }
        
        home=seleccionada.getPagina();//le asigno la pagina a la variable home
        actualizarArchivo();// lo cual llamar a este metodo sobreescribira el archivo con los nuevos datos de las variables
        // TODO add your handling code here:
    }//GEN-LAST:event_definir_homeActionPerformed

    //evento de cambio de estado del boton para mostrar marcadores
    private void MostrarMarStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_MostrarMarStateChanged

        //si elboton esta activado mostrara el panel de marcadores, caso contrario lo ocultara
        if(MostrarMar.isSelected())
            MarcadoresPanel.setVisible(true);
        else
            MarcadoresPanel.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_MostrarMarStateChanged

    //agrega un nuevo marcador a la lista
    private void añadir_marcadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_añadir_marcadorActionPerformed
        
        //recupero la activa 
        Pestanas seleccionada = ((Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex()));
        if(seleccionada.getNum()>-1){
            crearMarcador(seleccionada.getTitle()+"#"+seleccionada.getPagina());//llamo a la funcion crearMarcador mandandole el titulo y el link de la pag actual
            actualizarArchivo();//actualizo el archivo con las nuevas variables
            MarcadoresPanel.setVisible(false);//oculto el panel
            if(MostrarMar.isSelected())//y si el boton esta activado lo vuelvo a mostrar
            MarcadoresPanel.setVisible(true);
        }
        
        // TODO add your handling code here:
    }//GEN-LAST:event_añadir_marcadorActionPerformed

    
    //evento para abir la ventana de historiales
    private void menu_historialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_historialActionPerformed
        //si no esta instanceada la ventana la intancio y la llamo
        if(histo==null){
            histo = new Historial(este);
            histo.setHisto(historial);//le mando la lista de historiales de todo el navegador
            histo.setVisible(true);
        }//en caso q ya este en pantalla
        histo.setState(0);//le asigno el estado para q no este minimizada
        histo.setVisible(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_menu_historialActionPerformed

    private void AbrirpagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AbrirpagActionPerformed
        //recupero la activa 
        Pestanas seleccionada = ((Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex()));
        JFileChooser fc =new JFileChooser();
        FileNameExtensionFilter tipo=new FileNameExtensionFilter("HTML, HTM & TXT","html","htm","txt");
        fc.setFileFilter(tipo);
        int result = fc.showOpenDialog(null);
        if(result==JFileChooser.APPROVE_OPTION){
            
            if(!fc.getSelectedFile().exists()){
                JOptionPane.showMessageDialog(this, "Archivo no encontrado.");
                return;
            }
            
            URI file = fc.getSelectedFile().toURI();
            System.out.println(file);
//            cargarArchivo(file);
            
            alterarHistorial(file.toString());
            seleccionada.getCargar().setUrl(file.toString());
            seleccionada.getCargar().cargarPag();
            Adelante.setEnabled(false);
            
        }
            
        // TODO add your handling code here:
    }//GEN-LAST:event_AbrirpagActionPerformed

    private void guardarpagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarpagActionPerformed
        
         //recupero la activa 
        Pestanas seleccionada = ((Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex()));
        JFileChooser fc =new JFileChooser();
        fc.setApproveButtonText("Guardar");
        FileNameExtensionFilter tipo=new FileNameExtensionFilter("HTML, HTM & TXT","html","htm","txt");
        fc.setFileFilter(tipo);
        int result = fc.showOpenDialog(null);
        if(result==JFileChooser.APPROVE_OPTION){
            File file = fc.getSelectedFile();
            
            if(!file.getName().contains(".")){
                System.out.println(file.getParent()+"\\"+file.getName()+".html");
                file = new File(file.getParent()+"\\"+file.getName()+".html");
            }
            
            
            if(file.exists())
                if(0!=JOptionPane.showConfirmDialog(este,"fichero ya existe, Modificar?"))
                    return;
            try {
                BufferedWriter br = new BufferedWriter(new FileWriter(file));
                br.write(seleccionada.getText().getText());
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Navegador.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        // TODO add your handling code here:
    }//GEN-LAST:event_guardarpagActionPerformed

    public void cargarArchivo(File file){
        try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String linea;
                String guardar="";
                while((linea=br.readLine())!=null)
                    guardar+=linea;
                br.close();
                Pestanas seleccionada=(Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex());
                seleccionada.getText().setText(guardar);
                    
                    
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Navegador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Navegador.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    
    /***************************************************************************************/
    //actualizo el estado de los botones adelante y atras dependiendo de en q punto de la lista del historialde esapestaña me encuentre
    public void actualizarBtns(){///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        Pestanas seleccionada = ((Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex()));
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
        
    
            //creo el nuevo jTextPane que se asignara a mi nueva pestaña
            JTextPane n =new JTextPane();
            
            
            //creando JScrollPane dond voy a meter a mi JTextPane
            JScrollPane sc = new JScrollPane();////////////////////////////////////////////////////////////////////////////////////////////////////////
            //Añadiendo el JTextPane al JScrollPane
            sc.setViewportView(n);
            //Añadiendo el JTextPane al tab
            Tab1.add(sc, pestañas);
            
           
            
            //llamada a mi componente Pestaña y añadiendole el index del tab al que estará asignado
            final Pestanas nueva = new Pestanas(este);
            nueva.setText(n);
//            nueva.setIndex(pestañas);
            Cargar cargar = new Cargar(este);
            Thread carga = new Thread(cargar);
            carga.start();
            cargar.setPestana(nueva);
            
            nueva.setCargar(cargar);
            
            
            
            nueva.nuevoText();
    
            //añado el componente Pestaña al tab recien creado, aumento el numero de tabs y selecciono el final.
            Tab1.setTabComponentAt(pestañas, nueva);
            pestañas++;
            Tab1.setSelectedIndex(pestañas-1);
            
        
    }
/***********************************************FIN CREAR PESTAÑAS**************************************************************************************/    
    public void cargarHistorial(String aCargar){ 
        Pestanas seleccionada = ((Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex()));
              
        if(!seleccionada.cargando()){
            seleccionada.getCargar().setUrl(aCargar);
            seleccionada.getCargar().cargarPag();
        }
    }
   
    public void guardarCookie(String cadena,String host){
        System.out.println("*************************************************");
        String[] cookLista =cadena.split("Set-Cookie: ");
        int i =1;
        for(i = 1;i<cookLista.length;i++){
//            cooks.put("Cookie: "+cookies[i].substring(0, cookies[i].indexOf("=")), cookies[i].substring(cookies[i].indexOf("=")+1, cookies[i].indexOf("\n")));
//            Cookie cook = new Cookie(cookies[i]);
            System.out.println("d");
            System.out.println(cookLista[i].substring(0,cookLista[i].indexOf("\n")));
            Cookie cook = new Cookie(cookLista[i].substring(0,cookLista[i].indexOf("\n")));
            if(cookies.containsKey(host))
                cookies.get(host).put(cook.getId(),cook);
            else{
                cookies.put(host, new HashMap<String, Cookie>());
                cookies.get(host).put(cook.getId(),cook);
            }
            ActualizarCookies();
        }
        
        System.out.println(cookies);
        System.out.println("***************************************************");
    }
    
    
    public void ActualizarCookies(){
        File file = new File("cookies.txt");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            Iterator cookHost = cookies.keySet().iterator();
            while(cookHost.hasNext())
            {
                String key = (String) cookHost.next();
                bw.write("<"+key+"\n");
                
                Map<String,Cookie> temp = cookies.get(key);
                Iterator cooks = temp.keySet().iterator();
                
                while(cooks.hasNext()){
                    String key2 = (String) cooks.next();
                    bw.write(temp.get(key2).getCookie()+"\n");
                }
                bw.write(">\n");
            }
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Navegador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
 /******************************************************************************************************************************************************/
    public String cargarCookies(String host){
        String cadena="";
        Map<String,Cookie> cooks;
        String key;
        if(cookies.containsKey(host)){
            cooks=cookies.get(host);
            Iterator it = cooks.keySet().iterator();
            while(it.hasNext()){
                key = (String)it.next();
                cadena += "Cookie: "+cooks.get(key).getId()+"="+cooks.get(key).getContenido()+"\n";
                
            }
            
            
        }
        
        return cadena;
    }
    
    public void leerCookies(){
        File  ck= new File("cookies.txt");
        
        try{
            
            if(!ck.exists()){
                BufferedWriter bw = new BufferedWriter(new FileWriter(ck));
                bw.close();
            }
            
            
            FileReader fl=new FileReader(ck);
            BufferedReader bf=new BufferedReader(fl);
            String linea;
            
            while((linea=bf.readLine())!=null){
                
                if(linea.contains("<")){
                    Map map = new HashMap();
                    String host=linea.substring(1);
                    while(!(linea=bf.readLine()).equals(">")){
                        System.out.println(linea);
                        Cookie cook = new Cookie(linea);
                        map.put(cook.id, cook);
                    }
                    cookies.put(host, map);
                    
                }
                    
            }
            
        }catch(Exception ex){
            System.out.println("oooo");
        
        }
    }
    
    
    
    
/**************************************************************************************************/

    
    //muestra la pagina tal como se debe ver
    public void verPag(){
        
        ((Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex())).getText().setContentType("text/html");//le programo que lea html
        ((Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex())).getText().setText(((Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex())).getHtml());
    }
    
    //muestra la respuesta del requerimiento HTTP
    public void verHttp(){
        
        ((Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex())).getText().setContentType("text/text");//le programo q muestre el texto como lo mando
        ((Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex())).getText().setText(((Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex())).getHttp());
    }
    
    //funcion para escribir el archivo de configuraciones
    public void actualizarArchivo(){
        File homepag= new File("home.cm");//archivo el cual voy a modificar
        try {
            //inicio un nuevo filewriter
            FileWriter fw = new FileWriter(homepag);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("<HOME>"+home+"</HOME>\n");//escribi el nuevo home x si ha cambiado
            bw.write("\n");
            bw.write("<MARCADORES>\n");//inicio para los marcadores

            for(String marcador:marcadores)//repetire esta parte tantas veces como marcadores tenga en mi lista
                bw.write(marcador+"\n");

            bw.write("</MARCADORES>\n");//cerrare el tag de los marcadores
            bw.write("\n");
            bw.write("<HISTORIAL>\n");// iniciare el tag historial 

            for(String histo:historial)//repetire esta parte tantas veces como amrcadores tenga
                bw.write(histo+"\n");

            bw.write("</HISTORIAL>\n");//cierro el tag de historial
            bw.write("\n");
            bw.write("<FIN>\n");//fin =D
            bw.close();//termino la escritura para que se guarde el archivo
        } catch (Exception e) {
        }
        
        
    }
    
    public void cargarArchivos(){
        File homepag=new File("home.cm");
        try {
            //verificando si ya esxiste el archivo... de no ser asi lo crea
            if(!homepag.exists()){
                home="http://www.cs.bham.ac.uk/~tpc/testpages/";
                marcadores.add("pagina1#http://www.cs.bham.ac.uk/~tpc/testpages/");
                marcadores.add("pagina2#http://sheldonbrown.com/web_sample1.html");
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
            home=home.substring(6, home.indexOf("</HOME>"));//recupero el home y lo almaceno
            buff.readLine();
            buff.readLine();
            String marca;
            while(!(marca=buff.readLine()).equals("</MARCADORES>")){//recupero los marcadores uno x uno
                crearMarcador(marca);
            }
            buff.readLine();
            buff.readLine();
            while(!(marca=buff.readLine()).equals("</HISTORIAL>")){//recupero el historial uno x uno
                historial.add(marca);
            }
            
            buff.close();//termino la lectura de archivo
            fr.close();
        } catch (Exception ex) {
         
            JOptionPane.showMessageDialog(null, "error: "+ex.getMessage());
        }
    }
    
    //funcion para crear un nuevo marcador
    public void crearMarcador(String marca){
        marcadores.add(marca);//recibo un string de la forma nombre#link para añadir a marcadores
                final String marcas[]=marca.split("#");//separo el nombre dellink
                numMarca++;//aumento el numero de marcadores que tengo
                JButton m=new JButton(marcas[0]);//añado el boton paraelnuevomarcador con el nombre
                
                /**Creando limitante de tamaño del contenido de los botones*/
                if(marcas[0].length()>20)
                    m.setText(marcas[0].substring(0, 17)+"...");
                if(marcas[0].length()<20)
                    for(int i = marcas[0].length();i<20;i++)
                        m.setText(m.getText()+" ");
                if(marcas[0].length()==20)
                    m.setText(marcas[0]);
                        
                JPopupMenu pop = new JPopupMenu(numMarca+"");//se crea un popmenu y le asigno un valor de la posicion en la que se encuantra ubicado
                JMenuItem menu1 = new JMenuItem("Eliminar");//creo el menu a para eliminar el marcador
                menu1.addActionListener(new java.awt.event.ActionListener() {
                     public void actionPerformed(java.awt.event.ActionEvent evt) {
                         MarcadoresPanel.remove(Integer.parseInt(((JPopupMenu)((JComponent)evt.getSource()).getParent()).getLabel()));//elimino el marcadorpor medio del indicador que tenia el popmenu
                         marcadores.remove(Integer.parseInt(((JPopupMenu)((JComponent)evt.getSource()).getParent()).getLabel()));//tambien lo saco de mi lista demarcadores
                         actualizarArchivo();//actualizo el archivo de configuraciones
                         for(int i =0;i<MarcadoresPanel.getComponents().length;i++)//les asigno a cada componente su nuevo indice de ubicacion(Solo a los que se encontraba despues del eliminado)
                             ((JButton)MarcadoresPanel.getComponent(i)).getComponentPopupMenu().setLabel(i+"");
                         MarcadoresPanel.setVisible(false);//realizo una recargade la vista del panel de marcadores remostrandola
                         MarcadoresPanel.setVisible(true);
                         numMarca--;//redusco el numero de marcadores en 1
                         
                     }
                 });
                 pop.add(menu1);//le añado el menu de eliminar popmenu
                 m.setComponentPopupMenu(pop);//añado el popmenu al marcador
                 
                //creo el evento de abrir la pagina al clickear el popmenu
                m.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseReleased(java.awt.event.MouseEvent evt) {
                        
                        Pestanas seleccionada = ((Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex()));//recupero la pestaña que estoy usando
                        if(!seleccionada.cargando()){//siempre y cuando noi este cargando inguna pagina cargara el marcador
                            seleccionada.getCargar().setUrl(marcas[1]);//asigno el url del marcador
                            seleccionada.getCargar().cargarPag();//cargo el url
                            Adelante.setEnabled(false);//desactivo el boton adelante
                            seleccionada.delPags();//borro todas las paginas posteriores a la que tenia
                            seleccionada.addPagina(marcas[1]);//añado la pagina a cargar al historial de la pestaña de la sesion
                            seleccionada.setNumS();//aumento el numero depaginas de la pestana en 1
                        }  
                    }
                });
                
                MarcadoresPanel.add(m);//añado el boton al panel
    }
    
    //actualizo el historial del navegador
    public void actualizarHistorial(String Url){
        if(historial.contains(Url))//valido si esa pagina ya esta contenida en la lista del url 
            historial.remove(historial.indexOf(Url));//en caso de esta contenida la elimino
        historial.add(Url);//y la añado al final de la lista
        actualizarArchivo();//actualizo el archivo de conf
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
    private javax.swing.JMenuItem Abrirpag;
    private javax.swing.JButton Adelante;
    private javax.swing.JButton Atras;
    private javax.swing.JButton Home;
    private javax.swing.JPanel MarcadoresPanel;
    private javax.swing.JToggleButton MostrarMar;
    private javax.swing.JButton Recargar;
    private javax.swing.JTabbedPane Tab1;
    private javax.swing.JMenuItem añadir_marcador;
    private javax.swing.JMenuItem cerrar;
    private javax.swing.JMenuItem definir_home;
    private javax.swing.JMenuItem guardarpag;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JMenuItem menu_historial;
    private javax.swing.JTextField url;
    // End of variables declaration//GEN-END:variables
}
