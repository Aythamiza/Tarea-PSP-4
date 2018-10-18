
import java.io.*;

import java.net.*;

class Servidor extends Thread {

    Socket skCliente;

    static final int Puerto = 2000;

    public Servidor(Socket sCliente) {

        skCliente = sCliente;

    }

    public static void main(String[] arg) {

        try {

            // Inicio el servidor en el puerto
            ServerSocket skServidor = new ServerSocket(Puerto);

            System.out.println("Escucho el puerto " + Puerto);

            while (true) {

                // Se conecta un cliente
                Socket skCliente = skServidor.accept();

                System.out.println("Cliente conectado");
// Atiendo al cliente mediante un thread
                new Servidor(skCliente).start();

            }

        } catch (Exception e) {;
        }

    }

    public void run() {

        try {

            // Creo los flujos de entrada y salida
            DataInputStream flujo_entrada = new DataInputStream(skCliente.getInputStream());

            DataOutputStream flujo_salida = new DataOutputStream(skCliente.getOutputStream());

            // ATENDER PETICIÓN DEL CLIENTE
            int encontrado = 0;
            String usuario = new String();
            String contraseña = new String();

            flujo_salida.writeUTF(" Introduce tu usuario ");

            usuario = flujo_entrada.readUTF();
            System.out.println("\tEl cliente ha dicho " + usuario);

            flujo_salida.writeUTF("Contraseña");
            contraseña = flujo_entrada.readUTF();

            if (usuario.equals("Juan") && contraseña.equals("Secreta")) {

                int estado = 1;
                do {

                    String comando = new String();
                    switch (estado) {
                        case 1:

                            flujo_salida.writeUTF("Introduce comando (ver/dir/exit)");

                            comando = flujo_entrada.readUTF();

                            if (comando.equals("dir")) {

                                System.out.println("\tEl cliente quiere ver el contenido del directorio");
                                String miDir = "./src";
                                flujo_salida.writeUTF(miDir);
                                estado = 1;
                                break;
                            } else if (comando.equals("ver")) {

                                // Voy al estado 3 para mostrar el ficher
                                estado = 3;

                                break;

                            } else {
                                estado = 1;
                            }

                            break;

                        case 3:

                            //falta que el cliente pueda ver el archivo
                            flujo_salida.writeUTF("Introduce el nombre de archivo a ver");
                            String nombreDelArchivo = flujo_entrada.readUTF();
                            File archivo = new File(nombreDelArchivo);
                            FileReader fr = new FileReader(archivo);
                            BufferedReader br = new BufferedReader(fr);
                            estado = 1;
                            break;

                    }

                } while (estado != -1);

                skCliente.close();
                
            } else {
                flujo_salida.writeUTF("Usuario y contraseña inconrectos");

            }

            // Se cierra la conexión
            skCliente.close();

            System.out.println("Cliente desconectado");

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

}
