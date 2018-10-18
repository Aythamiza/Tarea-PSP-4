
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

            String leerOpcion = flujo_entrada.readUTF();

            int estado = 1;

            do {

                String comando = new String();

                switch (estado) {
                    case 1:

                        System.out.println(leerOpcion);

                        String escribirOpcion = sc.next();

                        flujo_salida.writeUTF(escribirOpcion);

                        if ("dir".equals(escribirOpcion)) {

                            String laOpcionElegida = flujo_entrada.readUTF();

                            File miDir = new File(laOpcionElegida);

                            File[] ficheros = miDir.listFiles();

                            for (int x = 0; x < ficheros.length; x++) {
                                System.out.println(ficheros[x].getName());

                            }

                            estado = 1;

                            break;

                        }

                        if ("ver".equals(escribirOpcion)) {
                            String laOpcionElegida = flujo_entrada.readUTF();

                            System.out.println(laOpcionElegida);

                            String elergirArchivo = sc.next();
                            
                            flujo_salida.writeUTF(elergirArchivo);
                            
                            
                            estado = 1;

                            break;

                        } else if (comando.equals("exit")) {

                            estado = -1;
                            break;

                        }

                }

            } while (estado != -1);

            sCliente.close();

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public static void main(String[] arg) {

        new Cliente();

    }

}
