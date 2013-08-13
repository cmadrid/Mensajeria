package browser;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocketFactory;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Cesar Madrid
 */
 public class Cargar implements Runnable{
    
    private boolean correr=false;//booleano que decide si entro a la parte importante del hilo q se encarga de cargar mis paginas
    private boolean running=true;//variable que decide cuando dejar de leer la respuesta del servidor en cuanto encuentre algo q defina el final de mensaje
    private String Url="";//variable dond guardare la direccion antes de cargarla
    private int seleccionado=0;//int que almacenara sobre que pestaña estare trabajando
    private boolean segura;
    private Socket conexion;
    private String Directorio;
    private String Html;
    private Navegador nav;
    //directorio dond localizar los recursos
    
    public Cargar(Navegador navegador){
        this.nav=navegador;
        Directorio=getClass().getResource("").toExternalForm();
        Directorio=(String) Directorio.subSequence(0, Directorio.length()-8);
    }
    
    Pestanas seleccionada;//almaceno la pestaña actual
        @Override
        public void run() {
            while(running){
                seleccionada.setIcon(null);//elimino cualquier icono activo en la pestaña
                try {
                    Thread.sleep(50);//asigno un tiempo de espera antes de volver a verificar si esta listo para cargar una pagina
                    if(correr){
                        segura=false;
                        seleccionada.nuevoText();//asigno un nuevo textPane a la pestaña para eliminar cualquer propiedad cargada
                        seleccionada.setIcon(Directorio+"imagenes/loader.gif");//agrego un gif de cargando pagina
                        
                        
                        nav.getUrl().setText(Url);//agrego el url a cargar en la barra de direcciones
                        
                        if(Url.toUpperCase().contains("HTTPS://"))
                            segura=true;
                        
                        Url =Url.replaceAll("(?i)http://", "");//elimino si es q existe el protocolo http://
                        Url =Url.replaceAll("(?i)https://", "");//elimino si es q existe el protocolo https://
                        
                        if(!Url.contains("/"))//agrego un / al final del url en casode no tenerlo
                            Url=Url+"/";
                        
                        String servidor = Url.substring(0, Url.indexOf("/"));//almaceno el servidor en un string
                        String pagina =Url.substring(Url.indexOf("/"));//tomo el path a partir de dond acaba elservidor y lo almaceno
                        if(segura)
                            conexion = SSLSocketFactory.getDefault().createSocket(servidor, 443);//nuevo tipo de socket para conexion segura
                        else
                            conexion=new Socket(servidor, 80);//hago la llamada al servidor al puerto 80 (http)
                        String requerimiento="GET "+pagina+" HTTP/1.1\n";//requerimiento http
                        requerimiento = requerimiento + "Host: "+servidor+"\n"; //host al que le pido el requerimiento
                        
                        requerimiento = requerimiento + nav.cargarCookies();//cargo las cookies
                        
                        requerimiento = requerimiento + "User-Agent: Mozilla/5.0 (Macintosh)\n";//clonando al mozilla feroz
                        requerimiento = requerimiento + "Connection: close\n\n";//cerrar la coneccion(los 2 enters al final son encesario para poder recibir la respuesta)
                        PrintWriter pw = new PrintWriter(conexion.getOutputStream());
                        pw.print(requerimiento);
                        pw.flush();
                        
                        //inicio un buffer para la lectura de lo que me responde el servidor
                        String guardar;
                        BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                        guardar = in.readLine();
                       
                        System.out.println(requerimiento);
                        
                        String linea;
                        while((linea= in.readLine())!=null)
                            guardar = guardar+"\n" +linea;//concateno la linea nueva con el resto del mensaje
                            
                           
                        conexion.close();//termino la conexion con cualquier socket
                        
                        
                        
                        if(guardar.contains("<"))
                            nav.guardarCookie(guardar.substring(0, guardar.indexOf("<")));
                        else
                            nav.guardarCookie(guardar);
//                        if(guardar.contains("<"))
//                            System.out.println(guardar.substring(0, guardar.indexOf("<")));
//                        else System.out.println(guardar);
                        
//                        Respuesta=guardar;//guardo todo el requerimiento recibido en respuesta
                        //comparo si dio algun error al cargar la pagina y valido la respuesta de algunos errores
                        
                        
                        if(guardar.substring(9, 12).equals("301")||guardar.substring(9, 12).equals("302"))
                        {
                            //tomo el link que el servidor respondio
                            int inicia =guardar.toUpperCase().indexOf("LOCATION: ")+10;
                            seleccionada.getCargar().setUrl(guardar.substring(inicia, inicia+guardar.substring(inicia).indexOf("\n")));
                            continue;
                            
                        }
                            
                        
                        //agregando el http actual de la pestaña
                        seleccionada.setHttp(guardar);
                        String[] codigo =guardar.split("<");
                        Html = guardar.substring(codigo[0].length());
                        
                        //reemplanzando el tag meta que provoca que las paginas dejen de ser reconocidas.
//                        Html=Html.replaceAll("<meta", "<metas");
                        Html=Html.replaceAll("(?i)<frame", "<Metas");
                        Html=Html.replaceAll("(?i)<MeTa", "<Metas");//el (?i) ayuda a que reemplace todos los tag meta sin importar sin importar las mayusculas o minusculas
//                        Html=Html.replaceAll("<META", "<METAS");
                        
                        
                      
                        
                        System.out.println("interpretando");
                        //agregando el html actual de la pestaña
                        seleccionada.setHtml(Html);//guardo en la pestaña el codigo html actual en la pag
                        seleccionada.getText().setContentType("text/html");//defino q codigo va a leer mi textpane
                        seleccionada.getText().setText(Html);//agrego el codigo a mi textpane
                        System.out.println("interpretado");
                        
                        
                        String titulo="";//inicializo la variable que guardara el titulo de mi pestaña
                        if(Html.toUpperCase().contains("<TITLE>"))//busco si mi pagina tiene el tag title
                            titulo=Html.substring(Html.toUpperCase().indexOf("<TITLE>")+7, Html.toUpperCase().indexOf("</TITLE>"));//asigno en contenido de title a mi pagina
                        
                        else
                            titulo=Url;//en caso de no tener el title le asigno de nombre el url de mi pagina
                        
                        nav.este.setTitle(titulo);//le asigno el titulo a toda la ventana
                        seleccionada.setTitle(titulo);//le asigno titulo ami pestaña
                        
                        if(seleccionada.getNum()!=0)//en caso de que no me encuentre sobre el elemento 0 de la lista de historial de la pestaña
                            nav.getAtras().setEnabled(true);//habilitare el boton atras
                        correr=false;//detendre el boolean para repetir el bucle hasgta otra orden
                        
                        //almacenar pagina en historial
                        nav.actualizarHistorial(titulo+"#"+Url);
                        nav.actualizarBtns();
//                        System.out.println(cooks);
                        
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
            
            correr=true;
        }
        
        public void detener(){
            running=false;
        }
        
        public void setPestana(Pestanas selec){
            seleccionada = selec;
        }
        
        //funcion para definir el url a cargar
        public void setUrl(String url){
            Url=url;
        }
        
        public boolean cargando(){
            return correr;
        }

}
