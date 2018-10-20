
import java.io.*;
import java.net.*;
import java.util.Scanner;

class Cliente {

    static final String HOST = "localhost";

    static final int Puerto = 2000;

    public Cliente() {
        Scanner sc = new Scanner(System.in);

        try {

            Socket sCliente = new Socket(HOST, Puerto);

            // Creo los flujos de entrada y salida
            DataInputStream flujo_entrada = new DataInputStream(sCliente.getInputStream());

            DataOutputStream flujo_salida = new DataOutputStream(sCliente.getOutputStream());

            // TAREAS QUE REALIZA EL CLIENTE
            String leerUsuario = flujo_entrada.readUTF();

            System.out.println(leerUsuario);

            String usuario = sc.next();

            flujo_salida.writeUTF(usuario);

            String leercontraseña = flujo_entrada.readUTF();

            System.out.println(leercontraseña);

            String contraseña = sc.next();

            flujo_salida.writeUTF(contraseña);

          

            int estado = 1;

            do {

                String comando = new String();

                switch (estado) {
                    case 1:
                        String leerOpcion = flujo_entrada.readUTF();
                     
                        System.out.println(leerOpcion);

                        String escribirOpcion = sc.next();

                        flujo_salida.writeUTF(escribirOpcion);

                        if ("dir".equals(escribirOpcion)) {

                            String tamañodelarray = flujo_entrada.readUTF();
                            
                            int num = Integer.parseInt(tamañodelarray);
                            
                              for (int i = 0; i < num; i++) {
                                String datosdelarray = flujo_entrada.readUTF();
                                System.out.println(datosdelarray);
                            }
                              
                            estado = 1;
                            break;
                        }

                        if ("ver".equals(escribirOpcion)) {
                            
                       String laOpcionElegida = flujo_entrada.readUTF();

                        System.out.println(laOpcionElegida);

                        String elergirArchivo = sc.next();

                        flujo_salida.writeUTF(elergirArchivo);
                        
                      
                          
                            
                      String contenido = " ";
                        
                        
                        do {  
                            contenido=flujo_entrada.readUTF();
                            System.out.println(contenido);

                            
                        } while (contenido != null);
                        
                        
                        
                        
                        
                        
                           System.err.println(" me he saltado");
                            estado=1;
                        
                            break;
                               
                        } else if (comando.equals("exit")) {

                            estado = -1;
                            
                            break;

                        }

                }

            } while (estado != -1);
        
            

              sCliente.close();

        }
        
        catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public static void main(String[] arg) {

        new Cliente();

    }

}
