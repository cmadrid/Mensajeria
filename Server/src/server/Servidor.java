/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cesar Madrid
 */
public class Servidor {
    int PUERTO = 5000;
    DataOutputStream salida;
    String mensajeRecibido;
    ServerSocket servidor;
    Socket socket;
    ObjectInputStream entrada;
    
    ArrayList<Socket> socketsConv = new ArrayList<Socket>();
    Map<String, ArrayList<Socket>> sockets= new HashMap<>();
    
    ArrayList usuariosConv = new ArrayList();
    Map<String, ArrayList> usuarios = new HashMap<>();
    
    ArrayList<Escritura> escriturasConv = new ArrayList();
    Map<String, ArrayList<Escritura>> escrituras= new HashMap<>();
    
    ArrayList paquete = new ArrayList();
    
    Map<String, Integer> ubicacion = new HashMap<>();
    MiRunnable probando;
    
    int intentos=0 ;
    public boolean initServer(){
        sockets.put("TODOS", socketsConv);
        escrituras.put("TODOS",escriturasConv);
        usuarios.put("TODOS", usuariosConv);
        ubicacion.put("TODOS", 0);
        
            boolean inicio = true;
        try {
            servidor = new ServerSocket(PUERTO);
            System.out.println("Esperando una conexión");
            probando =new MiRunnable();
            Thread hilo = new Thread(probando);
            hilo.start();
            inicio=true;

            
        } catch (IOException ex) {
            if(intentos<=5){
                intentos++;
                System.out.println("error inicio hilo servidor");
                initServer();
                inicio=false;
            }else
                System.out.println("Sistema ocupado");
        } 
        return inicio;
 
    
    }
    
    public void armarPaquete(String comando,String cuerpo,String remitentes,String nick){
        paquete = new ArrayList();
        if(comando==null)
            comando="";
        if(cuerpo==null)
            cuerpo="";
        if(nick==null)
            nick="";
        paquete.add(comando);
        paquete.add(cuerpo);
        paquete.add(usuarios.get(remitentes));
        paquete.add(nick);
    }
    
    public void terminar(){
        try {
            servidor.close();
            probando.detener();
            
            
            Iterator it = escrituras.keySet().iterator();
            while(it.hasNext()){
                String key = (String) it.next();
                ArrayList<Escritura> lista = escrituras.get(key);
                for(int i=0;i<lista.size();i++){
                    armarPaquete("OFF", null,null, null);
                    new ObjectOutputStream(sockets.get(key).get(i).getOutputStream()).writeObject(paquete);
                    lista.get(i).detener();
                }
                
            }
//            for(int j = 0;j<escrituras.size();j++){
//                for(int i=0;i<escrituras.get(j).size();i++){
//                    armarPaquete("OFF", null,null, null);
//                    new ObjectOutputStream(sockets.get(j).get(i).getOutputStream()).writeObject(paquete);
//                    ((Escritura)escrituras.get(j).get(i)).detener();
//                }
//            }
            
            sockets = new HashMap<>();
            socketsConv = new ArrayList<>();
            sockets.put("TODOS", socketsConv);
            
            escrituras = new HashMap<>();
            escriturasConv = new ArrayList();
            escrituras.put("TODOS", escriturasConv);
            
            ubicacion.put(mensajeRecibido, 0);
            
        } catch (Exception e) {
            System.out.println("murio");
        }
    }
    
public class MiRunnable implements Runnable
{
    boolean running =true;
    @Override
    public void run ()
    {
        try {
            while(running){
//                System.out.println("numero de usuarios antes "+sockets.get(remitentes).size());
                socket = servidor.accept();
                System.out.println("1");
                String remitentes=(String) new ObjectInputStream(socket.getInputStream()).readObject();
                System.out.println(remitentes);
                System.out.println("2");
                //añadir en mensajero para q envie!!!!!
                if(sockets.containsKey(remitentes))
                    System.out.println("si contiene");
//                sockets.get(remitentes).add(socket);
                System.out.println(sockets);

                sockets.get(remitentes).add(socket);
                System.out.println("3");
                System.out.println("antes");
                System.out.println(usuarios.get(remitentes));
                System.out.println(usuarios.get(remitentes));
                System.out.println((ArrayList)usuarios.get(remitentes));
                System.out.println((ArrayList)usuarios.get(remitentes));
                new ObjectOutputStream(socket.getOutputStream()).writeObject(usuarios.get(remitentes));
                System.out.println("fin");
                System.out.println("4");
                
                System.out.println("numero de usuarios despues "+sockets.get(remitentes).size());
                System.out.println ("Se conecto el cliente #"+(ubicacion.get(remitentes)+1));
                salida = new DataOutputStream(socket.getOutputStream());
                salida.writeUTF(""+ubicacion.get(remitentes));
                try {
                    if(remitentes.equals("TODOS"))
                        usuarios.get(remitentes).add(new ObjectInputStream(socket.getInputStream()).readObject());
                    else
                        new ObjectInputStream(socket.getInputStream()).readObject();

                    System.out.println("q hago aca");
                } catch (ClassNotFoundException ex) {
                    System.out.println("falla leer nick");
                }
                //envio de lista de conectados a TODOS
                //for(int i = 0; i<=num+1;i++)
                new ObjectOutputStream(socket.getOutputStream()).writeObject(usuarios.get(remitentes));
                
                
                System.out.println(usuarios.get(remitentes));

                Escritura escritura = new Escritura(remitentes);
                escritura.setNum(ubicacion.get(remitentes));
                escrituras.get(remitentes).add(escritura);
                Thread hilo3 = new Thread(escritura);
                hilo3.start();

                ubicacion.put(remitentes, ubicacion.get(remitentes)+1);
            }
            
        } catch (IOException ex) {
            System.out.println("error creacion del "+" usuario");
            run();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void actualizar(){
        
    }
    public void detener(){
        running = false;
    }
}

public class Escritura implements Runnable
{
    int n;
    boolean running = true;
    String remitentes;
    String respaldo;
    
    public Escritura(String remitentes){
        this.remitentes=remitentes;
        this.respaldo=remitentes;
    }
    
    public void setNum(int numero){
        this.n=numero;
    
    }
    @Override
    public void run ()
    {
        try {
            
            
            while(running){
                remitentes=respaldo;
                System.out.println("asd "+n);
                paquete =new ArrayList();
                System.out.println(remitentes);
                if(sockets.containsKey(remitentes))
                paquete = (ArrayList) new ObjectInputStream(sockets.get(remitentes).get(n).getInputStream()).readObject();
                remitentes=(String) paquete.get(4);
                    System.out.println("aqui \\"+remitentes);
                System.out.println("2");
//                remitentes =(ArrayList) ((entradas.get(n)).readObject());
                System.out.println("paquete recibe 1");
                System.out.println(paquete);
                System.out.println("paquete recibe 2");
                
                //el comando mandado para eliminar a un usuario de la lista
                mensajeRecibido="<font color=\"#CC66CC\">"+paquete.get(3)+": </font>"+paquete.get(1);
                
                if(paquete.get(0).equals("INIPRI")){
                    
                    if(!usuarios.containsKey(((ArrayList)paquete.get(2)).get(0)+" "+((ArrayList)paquete.get(2)).get(1))&&!usuarios.containsKey(((ArrayList)paquete.get(2)).get(1)+" "+((ArrayList)paquete.get(2)).get(0))){
                        ArrayList nuevoUsuarios = new ArrayList();
                        for(int i =0;i<2;i++)
                            nuevoUsuarios.add(((ArrayList)paquete.get(2)).get(i));
                        System.out.println("probando "+nuevoUsuarios);
                        

                        usuarios.put(remitentes, nuevoUsuarios);
                        sockets.put(remitentes, new ArrayList());
                        escrituras.put(remitentes, new ArrayList());
                        ubicacion.put(remitentes, 0);
                        System.out.println("exitoso");
                        System.out.println(((ArrayList)paquete.get(2)).get(0)+" "+((ArrayList)paquete.get(2)).get(1));
                        System.out.println("probando "+nuevoUsuarios);
                        System.out.println(usuarios.get(remitentes)+"1");
                        System.out.println("probando "+nuevoUsuarios);
                    }else
                        continue;
                    
                        System.out.println(usuarios.get(remitentes)+"2");
                }
                
                        System.out.println(usuarios.get(remitentes)+"3");
                if(paquete.get(0).equals("CERRAR")){
                    System.out.println(n);
                    System.out.println(remitentes);
                    System.out.println(usuarios.get(remitentes));
                    usuarios.get(remitentes).remove(n);
                    System.out.println(usuarios.get(remitentes));
                    (escrituras.get(remitentes).get(n)).detener();
                    escrituras.get(remitentes).remove(n);
                    (sockets.get(remitentes).get(n)).close();
                    sockets.get(remitentes).remove(n);
                    ubicacion.put(remitentes, ubicacion.get(remitentes)-1);
                    mensajeRecibido = "<font color=\"#CC66CC\">"+paquete.get(3)+" ha abandonado la conversacion.</font>";

                    int j = escrituras.get(remitentes).size();
                    for(int i = n; i<j;i++)
                        
                        ((Escritura)escrituras.get(remitentes).get(i)).reducen();
                }
                        System.out.println(usuarios.get(remitentes)+"4");
                //System.out.println(usuarios+" es la lista de usuarios guardados");
                if(paquete.get(4).equals("TODOS")){
                    System.out.println("usuariosasdasd "+ usuarios.get(remitentes));
                    paquete.remove(2);
                    paquete.add(2,usuarios.get(remitentes));
                }
                System.out.println(paquete);
                System.out.println("inicia la muestra");
                System.out.println(paquete);
                System.out.println("acaba la muestra");
                
                if(!paquete.get(0).equals("INIPRI")){ 
                    for(int j = 0;j<escrituras.get(remitentes).size();j++){

                        new ObjectOutputStream(sockets.get(remitentes).get(j).getOutputStream()).writeObject(paquete);

                    }
                    if(paquete.get(0).equals("MOSTRAR"))
                        remitentes=respaldo;
                }
                else{
                    for(int j = 0;j<escrituras.get("TODOS").size();j++){
                        if(((ArrayList)paquete.get(2)).contains(usuarios.get("TODOS").get(j)))
                            new ObjectOutputStream(sockets.get("TODOS").get(j).getOutputStream()).writeObject(paquete);
                        
                    }remitentes="TODOS";
                }
            }
        } catch (IOException ex) {
            System.out.println("error envio de mensaje a TODOS los usuarios");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void reducen(){
        n--;
    }
    
    public void detener(){
    
        running=false;
    }
}

    public static void main(String[] args) {
        
        Servidor c=new Servidor();
        c.initServer();
        // TODO code application logic here
    }
}

