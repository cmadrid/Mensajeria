/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
    ArrayList<Socket> sockets = new ArrayList<Socket>();
    ArrayList usuarios = new ArrayList();
    ArrayList<ObjectInputStream> entradas = new ArrayList<ObjectInputStream>();
    ArrayList salidas = new ArrayList();
    ArrayList escrituras = new ArrayList();
    ArrayList paquete = new ArrayList();
    int num = 0;
    MiRunnable probando;
    
    int intentos=0 ;
    public boolean initServer(){
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
    
    public void armarPaquete(String comando,String cuerpo,int conversacion,String nick){
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
    
    public void terminar(){
        try {
            servidor.close();
            probando.detener();
            System.out.println(sockets.size());
            System.out.println(entradas.size());
            System.out.println(salidas.size());
            System.out.println(escrituras.size());
            
            for(int i=0;i<escrituras.size();i++){
                armarPaquete("OFF", null,0, null);
                new ObjectOutputStream(sockets.get(i).getOutputStream()).writeObject(paquete);
                ((Escritura)escrituras.get(i)).detener();
            }
            sockets = new ArrayList();
            entradas = new ArrayList();
            salidas = new ArrayList();
            escrituras = new ArrayList();
            num=0;
            
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
                System.out.println("numero de usuarios antes "+sockets.size());
                socket = servidor.accept();
                sockets.add(socket);
                
                new ObjectOutputStream(socket.getOutputStream()).writeObject(usuarios);
                
                System.out.println("numero de usuarios despues "+sockets.size());
                System.out.println ("Se conecto el cliente #"+(num+1));
                salida = new DataOutputStream(socket.getOutputStream());
                salida.writeUTF(""+num);
                try {
                    entrada = new ObjectInputStream(socket.getInputStream());
                    usuarios.add(entrada.readObject());
                } catch (ClassNotFoundException ex) {
                    System.out.println("falla leer nick");
                }
                //envio de lista de conectados a todos
                //for(int i = 0; i<=num+1;i++)
                new ObjectOutputStream(socket.getOutputStream()).writeObject(usuarios);
                
                
                System.out.println(usuarios);
                entradas.add(entrada);
                salidas.add(salida);

                Escritura escritura = new Escritura();
                escritura.setNum(num);
                escrituras.add(escritura);
                Thread hilo3 = new Thread((Escritura)escrituras.get(num));
                hilo3.start();

                num++;
            }
            
        } catch (IOException ex) {
            System.out.println("error creacion del "+num+" usuario");
            run();
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
    public void setNum(int numero){
        this.n=numero;
    
    }
    @Override
    public void run ()
    {
        try {
            
            
            while(running){
                System.out.println("asd");
                paquete =new ArrayList();
                entrada = new ObjectInputStream(sockets.get(n).getInputStream());
                paquete =(ArrayList) (entradas.get(n)).readObject();
                System.out.println("paquete recibe 1");
                System.out.println(paquete);
                System.out.println("paquete recibe 2");
                
                //el comando mandado para eliminar a un usuario de la lista
                mensajeRecibido="<font color=\"#CC66CC\">"+paquete.get(3)+": </font>"+paquete.get(1);
                
                
                if(paquete.get(0).equals("CERRAR")){
                    System.out.println(n);
                    entradas.remove(n);
                    salidas.remove(n);
                    usuarios.remove(n);
                    ((Escritura)escrituras.get(n)).detener();
                    escrituras.remove(n);
                    ((Socket)sockets.get(n)).close();
                    sockets.remove(n);
                    num--;
                    mensajeRecibido = "<font color=\"#CC66CC\">"+paquete.get(3)+" ha abandonado la conversacion.</font>";

                    int j = entradas.size();
                    for(int i = n; i<j;i++)
                        ((Escritura)escrituras.get(i)).reducen();
                        }
                //System.out.println(usuarios+" es la lista de usuarios guardados");
                paquete.remove(2);
                paquete.add(2,usuarios);
                System.out.println("inicia la muestra");
                System.out.println(paquete);
                System.out.println("acaba la muestra");
                for(int j = 0;j<salidas.size();j++){
                    
                    new ObjectOutputStream(sockets.get(j).getOutputStream()).writeObject(paquete);
                }
            }
        } catch (IOException ex) {
            System.out.println("error envio de mensaje a todos los usuarios");
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

