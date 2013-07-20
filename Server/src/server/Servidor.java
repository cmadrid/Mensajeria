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
    int num = 0;
    MiRunnable probando;
    
    int intentos=0 ;
    public void initServer(){
        try {
            servidor = new ServerSocket(PUERTO);
            System.out.println("Esperando una conexión");
            probando =new MiRunnable();
            Thread hilo = new Thread(probando);
            hilo.start();

            
        } catch (IOException ex) {
            if(intentos<=5){
                intentos++;
                System.out.println("error inicio hilo servidor");
                initServer();
            }else
                System.out.println("Sistema ocupado");
        } 
 
    
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
                ((DataOutputStream)salidas.get(i)).writeUTF("$$$$$$$$$$$$$$$OFF$$El servidor ha sido cerrado.");
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
                System.out.println("numero de usuarios despues "+sockets.size());
                System.out.println ("Se conecto el cliente #"+(num+1));
                salida = new DataOutputStream(socket.getOutputStream());
                salida.writeUTF(""+num);
                System.out.println("hola");
                try {
                    entrada = new ObjectInputStream(socket.getInputStream());
                    System.out.println(entrada.readObject());
                } catch (ClassNotFoundException ex) {
                    System.out.println("falla leer nick");
                }
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
                entrada = new ObjectInputStream(sockets.get(n).getInputStream());
                mensajeRecibido = (String) (entradas.get(n)).readObject();
                System.out.println(mensajeRecibido);
                //el comando mandado para eliminar a un usuario de la lista
                if(mensajeRecibido.length()>=10)
                    if(mensajeRecibido.substring(0, 10).equals("$$cerrar$$")){
                        System.out.println(n);
                        entradas.remove(n);
                        salidas.remove(n);
                        ((Escritura)escrituras.get(n)).detener();
                        escrituras.remove(n);
                        ((Socket)sockets.get(n)).close();
                        sockets.remove(n);
                        num--;
                        mensajeRecibido = mensajeRecibido.substring(17)+" ha abandonado la conversacion.</font>";
                        System.out.println(mensajeRecibido);
                        
                        int j = entradas.size();
                        for(int i = n; i<j;i++)
                            ((Escritura)escrituras.get(i)).reducen();
//                        for(int i = n; i<j;i++)
//                            entradas.add(new BufferedReader(new InputStreamReader(((Socket)usuarios.get(n)).getInputStream())));
                            }
                
                System.out.println(mensajeRecibido+" enviado desde el cliente #"+n);
                System.out.println(mensajeRecibido);
                //envio mensaje de vuelta a los chats
                System.out.println("numero de salidas "+salidas.size());
                for(int j = 0;j<salidas.size();j++){
                    ((DataOutputStream)salidas.get(j)).writeUTF(mensajeRecibido);
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

