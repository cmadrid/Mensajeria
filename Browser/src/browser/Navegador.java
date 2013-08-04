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
import javax.swing.event.ChangeEvent;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

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
    ArrayList<ArrayList<String>> Historiales= new ArrayList<>();
    ArrayList<Integer>nums=new ArrayList<>();
//    int num=-1;
    String Directorio;
    ArrayList<String> Htmls = new ArrayList<>();
    ArrayList<String> Https = new ArrayList<>();
    String Html="";
    String Respuesta="";
    int pestañas=0;
    ArrayList<JTextPane> texts = new ArrayList();
    String home="";
    int numMarca=-1;
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
        System.out.println(((JButton)MarcadoresPanel.getComponent(1)).getText());
        
        
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
            int temp = Historiales.get(Tab1.getSelectedIndex()).size();
            if(temp!=nums.get(Tab1.getSelectedIndex())+1)
                for(int i=nums.get(Tab1.getSelectedIndex())+1;i<temp;i++)
                    Historiales.get(Tab1.getSelectedIndex()).remove(i);
           Historiales.get(Tab1.getSelectedIndex()).add(url.getText());
           nums.set(Tab1.getSelectedIndex(),nums.get(Tab1.getSelectedIndex())+1);
            cargar.cargarPag();
            Adelante.setEnabled(false);
        
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_urlKeyPressed

    private void AtrasMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AtrasMouseReleased
        if(Atras.isEnabled()&&!cargar.correr){
            nums.set(Tab1.getSelectedIndex(),nums.get(Tab1.getSelectedIndex())-1);
            cargar.setUrl(Historiales.get(Tab1.getSelectedIndex()).get(nums.get(Tab1.getSelectedIndex())));
    //        if(Historial.size()!=num+1)
    //            System.out.println("ohlasdasd");
            cargar.cargarPag();
            Adelante.setEnabled(true);
            if(nums.get(Tab1.getSelectedIndex())==0)
                Atras.setEnabled(false);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_AtrasMouseReleased

    private void AdelanteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AdelanteMouseReleased
        if(Adelante.isEnabled()&&!cargar.correr){
            cargar.setUrl(Historiales.get(Tab1.getSelectedIndex()).get(nums.get(Tab1.getSelectedIndex())+1));
            nums.set(Tab1.getSelectedIndex(),nums.get(Tab1.getSelectedIndex())+1);
            cargar.cargarPag();
            if(Historiales.get(Tab1.getSelectedIndex()).size()==nums.get(Tab1.getSelectedIndex())+1)
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
                actualizarBtns();
                if(nums.get(Tab1.getSelectedIndex())==-1)
                    url.setText("");
                else
                    url.setText(Historiales.get(Tab1.getSelectedIndex()).get(nums.get(Tab1.getSelectedIndex())));
            }
        
        
        // TODO add your handling code here:
    }//GEN-LAST:event_Tab1StateChanged

    private void HomeMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HomeMouseReleased

//        if(nums.get(Tab1.getSelectedIndex())>1)
//            Atras.setEnabled(true);
        if(!cargar.correr){
            int temp = Historiales.get(Tab1.getSelectedIndex()).size();
            if(temp!=nums.get(Tab1.getSelectedIndex())+1)
                for(int i=nums.get(Tab1.getSelectedIndex())+1;i<temp;i++)
                    Historiales.get(Tab1.getSelectedIndex()).remove(i);
           Historiales.get(Tab1.getSelectedIndex()).add(home);
           nums.set(Tab1.getSelectedIndex(),nums.get(Tab1.getSelectedIndex())+1);        
            System.out.println(home);
            cargar.setUrl(home);
            cargar.cargarPag();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_HomeMouseReleased

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        home=Historiales.get(Tab1.getSelectedIndex()).get(nums.get(Tab1.getSelectedIndex()));
        File homepag=new File("home.cm");
        try {
            FileReader fr = new FileReader(homepag);
            BufferedReader br = new BufferedReader(fr);
            String archivo="";
            String linea;
            while(!(linea=br.readLine()).equals("<FIN>")){
                if(linea.contains("<HOME>"))
                    archivo=archivo+"<HOME>"+home+"</HOME>\n";
                else    
                    archivo=archivo+linea+"\n";
            }
            archivo = archivo +"<FIN>\n";
            System.out.println(archivo);
            
            //Escribiendo la nueva pagina de inicio.
             System.out.println(homepag);
                FileWriter fw = new FileWriter(homepag);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(archivo);
                bw.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error :"+e.getMessage());
        }
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void MostrarMarStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_MostrarMarStateChanged

        if(MostrarMar.isSelected())
            MarcadoresPanel.setVisible(true);
        else
            MarcadoresPanel.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_MostrarMarStateChanged
/***************************************************************************************/
    public void actualizarBtns(){
        if(nums.get(Tab1.getSelectedIndex())>0)
            Atras.setEnabled(true);
        else
            Atras.setEnabled(false);

        if(nums.get(Tab1.getSelectedIndex())<Historiales.get(Tab1.getSelectedIndex()).size()-1)
            Adelante.setEnabled(true);
        else
            Adelante.setEnabled(false);
    }
  /***************************************************************************************/
    
    
    
    
 /************************************CREAR PESTAÑAS*****************************************/
    public void crearPestaña(){
        
            //Agregando historiales para cada uno de las pestañas
            ArrayList<String> Historial = new ArrayList<>();
            Historiales.add(Historial);
            Htmls.add("");
            Https.add("");
            int num=-1;
            nums.add(num);
    
            //creo el nuevo jTextPane que se asignara a mi nueva pestaña
            JTextPane n =new JTextPane();
            n.setContentType("text/html");
            n.setEditable(false);
            
            //añadiendo leer links en todas las pestañas
            n.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
          //         JOptionPane.showMessageDialog( null, e.getURL().toString());
                   cargar.setUrl(e.getURL().toExternalForm());
                   int temp = Historiales.get(Tab1.getSelectedIndex()).size();
                    if(temp!=nums.get(Tab1.getSelectedIndex())+1)
                        for(int i=nums.get(Tab1.getSelectedIndex())+1;i<temp;i++)
                            Historiales.get(Tab1.getSelectedIndex()).remove(i);
                   Historiales.get(Tab1.getSelectedIndex()).add(e.getURL().toExternalForm());
                   nums.set(Tab1.getSelectedIndex(),nums.get(Tab1.getSelectedIndex())+1);
                   cargar.cargarPag();
                   Adelante.setEnabled(false);


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
            nueva.setIndex(pestañas);
            
            
            
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
                    int index=nueva.getIndex();
                    Tab1.remove(index);
                    texts.remove(index);
                    pestañas--;
                    for(int i = index;i<pestañas;i++)
                        ((Pestañas)Tab1.getTabComponentAt(i)).setIndex(i);
                    
                    
                    //eliminando arreglos de los historiales
                    Historiales.remove(index);
                    nums.remove(index);
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
                        String requerimiento="\n\n\n\nGET "+pagina+" HTTP/1.1\nHost: "+servidor+"\n\n\n";

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
                            
                            //verificando si las paginas iniciaban con el tag html
                            if(inicio){
                                if(linea.toUpperCase().contains("</HTML>"))
                                    continuar=false;
                            }
                            else
                                if(linea.toUpperCase().contains("</BODY>")||linea==null)
                                    continuar=false;
                        }
                        
                        Respuesta=guardar;
                        Https.set(Tab1.getSelectedIndex(), Respuesta);
                        String[] codigo =guardar.split("<");
                        Html = guardar.substring(codigo[0].length());
                        Htmls.set(Tab1.getSelectedIndex(), Html);
                        System.out.println(Html);
                        System.out.println("1");
                        
                        texts.get(seleccionado).setContentType("text/html");
                        texts.get(seleccionado).setText(Html);
                        System.out.println(texts.get(pestañas-1));
                        System.out.println("2");
                        if(Html.toUpperCase().contains("<TITLE>")){
                            este.setTitle(Html.substring(Html.toUpperCase().indexOf("<TITLE>")+7, Html.toUpperCase().indexOf("</TITLE>")));
//                            p1.setTitle(Html.substring(Html.toUpperCase().indexOf("<TITLE>")+7, Html.toUpperCase().indexOf("</TITLE>")));
                            ((Pestañas)Tab1.getTabComponentAt(seleccionado)).setTitle(Html.substring(Html.toUpperCase().indexOf("<TITLE>")+7, Html.toUpperCase().indexOf("</TITLE>")));
                        }
                        else{
                            este.setTitle(Url);
                            ((Pestañas)Tab1.getTabComponentAt(seleccionado)).setTitle(Url);
                        }
                        if(nums.get(Tab1.getSelectedIndex())!=0)
                            Atras.setEnabled(true);
                        correr=false;
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
    
    public void cargarArchivos(){
        File homepag=new File("home.cm");
        try {
            //verificando si ya esxiste el archivo... de no ser asi lo crea
            if(!homepag.exists()){
                System.out.println(homepag);
                FileWriter fw = new FileWriter(homepag);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("<HOME>http://www.cs.bham.ac.uk/~tpc/testpages/</HOME>\n");
                bw.write("\n");
                bw.write("<INICIO MARCADORES>\n");
                bw.write("prueba1#http://www.cs.bham.ac.uk/~tpc/testpages/\n");
                bw.write("prueba2#http://sheldonbrown.com/web_sample1.html\n");
                bw.write("<FIN MARCADORES>\n");
                bw.write("\n");
                bw.write("<FIN>\n");
                bw.close();
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
            while(!(marca=buff.readLine()).equals("</MARCADORES>")){
                
                final String marcas[]=marca.split("#");
                numMarca++;
                JButton m = new JButton(marcas[0]);
                JPopupMenu pop = new JPopupMenu(numMarca+"");
                JMenuItem menu1 = new JMenuItem("Eliminar");
                menu1.addActionListener(new java.awt.event.ActionListener() {
                     public void actionPerformed(java.awt.event.ActionEvent evt) {
                         System.out.println(((JPopupMenu)((JComponent)evt.getSource()).getParent()).getLabel());
                         MarcadoresPanel.remove(Integer.parseInt(((JPopupMenu)((JComponent)evt.getSource()).getParent()).getLabel()));
                         for(int i =0;i<MarcadoresPanel.getComponents().length;i++)
                             ((JButton)MarcadoresPanel.getComponent(i)).getComponentPopupMenu().setLabel(i+"");
                         MarcadoresPanel.setVisible(false);
                         MarcadoresPanel.setVisible(true);
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
                            int temp = Historiales.get(Tab1.getSelectedIndex()).size();
                            if(temp!=nums.get(Tab1.getSelectedIndex())+1)
                                for(int i=nums.get(Tab1.getSelectedIndex())+1;i<temp;i++)
                                    Historiales.get(Tab1.getSelectedIndex()).remove(i);
                           Historiales.get(Tab1.getSelectedIndex()).add(marcas[1]);
                            nums.set(Tab1.getSelectedIndex(),nums.get(Tab1.getSelectedIndex())+1);
                        }  
                    }
                });
                
                MarcadoresPanel.add(m);
            }
            
            buff.close();
            fr.close();
        } catch (Exception ex) {
         
            JOptionPane.showMessageDialog(null, "error: "+ex.getMessage());
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
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextField url;
    // End of variables declaration//GEN-END:variables
}
