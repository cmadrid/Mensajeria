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
import javax.swing.JViewport;
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
    String Directorio;
    String Html="";
    String Respuesta="";
    int pestañas=0;
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
        definir_home = new javax.swing.JMenuItem();
        añadir_marcador = new javax.swing.JMenuItem();
        menu_historial = new javax.swing.JMenuItem();
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

        definir_home.setText("Definir pagina de Inicio");
        definir_home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                definir_homeActionPerformed(evt);
            }
        });
        jMenu1.add(definir_home);

        añadir_marcador.setText("Añadir a marcadores");
        añadir_marcador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                añadir_marcadorActionPerformed(evt);
            }
        });
        jMenu1.add(añadir_marcador);

        menu_historial.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        menu_historial.setText("Historial");
        menu_historial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_historialActionPerformed(evt);
            }
        });
        jMenu1.add(menu_historial);

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
        
        //controlo si tiene presionado control para agregar al url .com
        if(key==KeyEvent.VK_CONTROL)
            crtl=true;
            
        
        if(key==KeyEvent.VK_ENTER)
        {
            //si crtl es true agregara el .com al final
            if(crtl==true)
                url.setText(url.getText()+".com");
            
            //mando el url a cargar, actualizo el historial de la pestaña, mando a cargar la pagina y deshabilito el boton adelante
            cargar.setUrl(url.getText());
            alterarHistorial(url.getText());
            cargar.cargarPag();
            Adelante.setEnabled(false);
        
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_urlKeyPressed

    private void AtrasMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AtrasMouseReleased
        //verifico que el boton atras este activado y no este cargando una pagina
        if(Atras.isEnabled()&&!cargar.correr){
            //recupero la pestaña que tengo seleccionada y hago un recorrido hacia atras de su historial y activo el boton adelante
            Pestanas seleccionada = ((Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex()));
            seleccionada.setNumR();//reduce en uno mi variable que indica donde estoy aputando de la lista de historiales
            cargar.setUrl(seleccionada.getPagina());
            cargar.cargarPag();
            Adelante.setEnabled(true);
            if(seleccionada.getNum()==0)//si ya estoy apuntando al index 0 de mi lista se deshabilita el boton atras
                Atras.setEnabled(false);
            System.out.println(seleccionada.getNum());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_AtrasMouseReleased

    private void AdelanteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AdelanteMouseReleased
        //verifico que el boton adelante este activado y no este cargando una pagina
        if(Adelante.isEnabled()&&!cargar.correr){
            //recupero la pestaña que tengo seleccionada y hago un recorrido hacia adelante de su historial y activo el boton atras
            Pestanas seleccionada = ((Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex()));
            cargar.setUrl(seleccionada.getPaginaS());
            seleccionada.setNumS();//aumenta en uno mi variable que indica donde estoy aputando de la lista de historiales
            cargar.cargarPag();
            if(seleccionada.getHistorialSize()==seleccionada.getNum()+1)//si ya estoy apuntando al index final de mi lista se deshabilita el boton adelante
                Adelante.setEnabled(false);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_AdelanteMouseReleased

    //boton recargar de la aplicacion
    private void RecargarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RecargarMouseReleased
        
        cargar.cargarPag();
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

        //verifico q no este cargando una pagina antes de iniciar el evento
        if(!cargar.correr){
            
            alterarHistorial(home);//actualizo el historial de la pestaña
            cargar.setUrl(home);//agrego el url a cargar(el home)
            cargar.cargarPag();//cargo la pagina
            
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_HomeMouseReleased
    
    //metodo que actualiza el historial de mi pestaña
    public void alterarHistorial(String pag){
        //recupero la pestaña seleccionada
        Pestanas seleccionada = ((Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex()));
        System.out.println(seleccionada.getNum()+ "sss");
        int temp = seleccionada.getHistorialSize();//recupero la longitud del historial de esa pestaña
        System.out.println(seleccionada.getHistorialSize());
       seleccionada.delPags();//manda a borrar todas las paginas posteriores a la que me encuentro 
        System.out.println(seleccionada.getHistorialSize());
       seleccionada.addPagina(pag);//agrego la pagina recien cargada al final de la lista
       seleccionada.setNumS();//aumento en uno el numero indicador de ubicacion en la lista
        System.out.println(seleccionada.getNum()+ "sss");
           
    }
    
    //accion del boton home(pagina de inicio)
    private void definir_homeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_definir_homeActionPerformed
        //recupero la pestaña sobre la que estoy ubicado y obtengo su pagina actual
        Pestanas seleccionada = ((Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex()));
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
            histo = new Historial();
            histo.setHisto(historial);//le mando la lista de historiales de todo el navegador
            histo.setVisible(true);
        }//en caso q ya este en pantalla
        histo.setState(0);//le asigno el estado para q no este minimizada
        histo.setVisible(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_menu_historialActionPerformed

    
    
    
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
            final Pestanas nueva = new Pestanas();
            nueva.setText(n);
//            nueva.setIndex(pestañas);
            
            nueva.nuevoText(cargar);
            
            
            
            
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
                    pestañas--;
                    
                    
                }else System.exit(0);
                
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
    //hilo principal con el que cargo mis paginas
    public class Cargar implements Runnable{
    
    boolean correr=false;//booleano que decide si entro a la parte importante del hilo q se encarga de cargar mis paginas
    boolean continuar=true;//variable que decide cuando dejar de leer la respuesta del servidor en cuanto encuentre algo q defina el final de mensaje
    String Url="";//variable dond guardare la direccion antes de cargarla
    int seleccionado=0;//int que almacenara sobre que pestaña estare trabajando
        @Override
        public void run() {
            
            while(true){
                
                seleccionado=Tab1.getSelectedIndex();//asigno el index de la pestaña actual
                Pestanas seleccionada = (Pestanas) Tab1.getTabComponentAt(seleccionado);//almaceno la pestaña actual
                seleccionada.setIcon(null);//elimino cualquier icono activo en la pestaña
                try {
                    Thread.sleep(50);//asigno un tiempo de espera antes de volver a verificar si esta listo para cargar una pagina
                    if(correr){
                        seleccionada.nuevoText(cargar);//asigno un nuevo textPane a la pestaña para eliminar cualquer propiedad cargada
                        
                        seleccionado=Tab1.getSelectedIndex();//asigno el index de la pestaña actual
                        seleccionada = (Pestanas) Tab1.getTabComponentAt(seleccionado);//almaceno la pestaña actual
                        seleccionada.setIcon(Directorio+"imagenes/loader.gif");//agrego un gif de cargando pagina
                        
                        
                        url.setText(Url);//agrego el url a cargar en la barra de direcciones
                        Url =Url.replaceAll("http://", "");//elimino si es q existe el protocolo http:// ya q solo cargo paginas de ese tipo
                        
                        if(!Url.contains("/"))//agrego un / al final del url en casode no tenerlo
                            Url=Url+"/";
                        
                        String cadenas[]=Url.split("/");//separo el string por '/' para tomar el primero(el servidor)
                        String servidor=cadenas[0];//almaceno el servidor en un string
                        String pagina =Url.substring(cadenas[0].length());//tomo el path a partir de dond acaba elservidor y lo almaceno
                        conexion.close();//termino la conexion con cualquier socket
                        conexion=new Socket(servidor, 80);//hago la llamada al servidor al puerto 80 (http)
                        String requerimiento="\n\n\n\nGET "+pagina+" HTTP/1.1\nHost: "+servidor+"\nConnection: close\n\n";//requerimiento http

                        //envio el requerimiento al servidor
                        PrintWriter pw = new PrintWriter(conexion.getOutputStream());
                        pw.print(requerimiento);
                        pw.flush();
                        
                        //inicio un buffer para la lectura de lo que me responde el servidor
                        String guardar;
                        BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                        guardar = in.readLine();
                       
                        
                        
                        continuar=true;
                        boolean inicio=false;
                        while(continuar){
                            String linea= in.readLine();//leo la siguiente linea
                            
                            //validando hasta donde llega la pagina web
                            if(linea.toUpperCase().contains("<HTML")&&!inicio)//compruebo si la pagina inicia con un Html
                                inicio=true;                                
                            
                            
                            if(linea!=null)//mientras el valor recibido sea diferente de null lo almaceno
                                guardar = guardar+"\n" +linea;
                            else//en caso que sea null salgo del ciclo
                                break;
                            
                            //verificando si las paginas iniciaban con el tag html
                            if(inicio){
                                if(linea.toUpperCase().contains("</HTML>"))
                                    break;
                            }
                            else
                                if(linea.toUpperCase().contains("</BODY>"))
                                    break;
                        }
                        
                        
                        Respuesta=guardar;//guardo todo el requerimiento recibido en respuesta
                        //comparo si dio algun error al cargar la pagina y valido la respuesta de algunos errores
                        if(Respuesta.substring(9, 12).equals("301")||Respuesta.substring(9, 12).equals("302"))
                        {
                            //tomo el link que el servidor respondio
                            int inicia =Respuesta.toUpperCase().indexOf("LOCATION: ")+10;
                            cargar.setUrl(Respuesta.substring(inicia, inicia+Respuesta.substring(inicia).indexOf("\n")));
                            continue;
                            
                        }
                            
                        //agregando el http actual de la pestaña
                        seleccionada.setHttp(Respuesta);
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
                        
                        Html=fasz;
                        /*Espacio para intentar eliminar los scripts*/ 
                        
                        
                        
                        //agregando el html actual de la pestaña
                        seleccionada.setHtml(Html);//guardo en la pestaña el codigo html actual en la pag
                        seleccionada.getText().setContentType("text/html");//defino q codigo va a leer mi textpane
                        seleccionada.getText().setText(Html);//agrego el codigo a mi textpane
                        
                        
                        
                        String titulo="";//inicializo la variable que guardara el titulo de mi pestaña
                        if(Html.toUpperCase().contains("<TITLE>"))//busco si mi pagina tiene el tag title
                            titulo=Html.substring(Html.toUpperCase().indexOf("<TITLE>")+7, Html.toUpperCase().indexOf("</TITLE>"));//asigno en contenido de title a mi pagina
                        
                        else
                            titulo=Url;//en caso de no tener el title le asigno de nombre el url de mi pagina
                        
                        este.setTitle(titulo);//le asigno el titulo a toda la ventana
                        ((Pestanas)Tab1.getTabComponentAt(seleccionado)).setTitle(titulo);//le asigno titulo ami pestaña
                        
                        if(seleccionada.getNum()!=0)//en caso de que no me encuentre sobre el elemento 0 de la lista de historial de la pestaña
                            Atras.setEnabled(true);//habilitare el boton atras
                        correr=false;//detendre el boolean para repetir el bucle hasgta otra orden
                        
                        //almacenar pagina en historial
                        actualizarHistorial(titulo+"#"+Url);
                        actualizarBtns();
                        
                    }
//                    System.out.println(Respuesta);
                
                } catch (IOException ex) {
    //                Logger.getLogger(Navegador.class.getName()).log(Level.SEVERE, null, ex);
                    seleccionada.getText().setText("<h>Error: Pagina no encontrada</h>");
                    correr=false;
                } catch (InterruptedException ex) {
                Logger.getLogger(Navegador.class.getName()).log(Level.SEVERE, null, ex);
                }catch(Exception e){
                    seleccionada.getText().setText("<h>Error: Pagina no encontrada</h>");
                    correr=false;
                }
            
            }
            
        }
        
        //funcion que activa el boolean para que inicie otro bucle de cargar pagina
        public void cargarPag(){
            continuar=false;
            
            correr=true;
        }
        
        //funcion para definir el url a cargar
        public void setUrl(String url){
            Url=url;
        }

}
/*******************************************************************************************************************/
     
    /*
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     comentado todo hasta est punto
     * 
     * 
     * 
     * 
     * 
     *      
     * 
     * 
     * 
     * 
     */
    
    //muestra la pagina tal como se debe ver
    public void verPag(){
        
        ((Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex())).getText().setContentType("text/html");//le programo que lea html
        ((Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex())).getText().setText(((Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex())).getHtml());
    }
    
    //muestra la respuesta del requerimiento HTTP
    public void verHttp(){
        
        ((Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex())).getText().setContentType("");//le programo q muestre el texto como lo mando
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
            home=home.substring(6, home.indexOf("</HOME>"));//recupero el home y lo almaceno
            buff.readLine();
            buff.readLine();
            String marca;
            marcadores=new ArrayList<>();
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
                        if(!cargar.correr){//siempre y cuando noi este cargando inguna pagina cargara el marcador
                            cargar.setUrl(marcas[1]);//asigno el url del marcador
                            cargar.cargarPag();//cargo el url
                            Adelante.setEnabled(false);//desactivo el boton adelante
                            Pestanas seleccionada = ((Pestanas)Tab1.getTabComponentAt(Tab1.getSelectedIndex()));//recupero la pestaña que estoy usando
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
