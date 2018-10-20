
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
            String usuario = new String("Juan");
            String contraseña = new String("Secreta");

            flujo_salida.writeUTF(" Introduce tu usuario ");

            usuario = flujo_entrada.readUTF();
            System.out.println("\tEl cliente ha dicho " + usuario);

            flujo_salida.writeUTF("Contraseña");
            contraseña = flujo_entrada.readUTF();

            if (usuario.equals(usuario) && contraseña.equals(contraseña)) {

                int estado = 1;
                do {

                    String comando = new String();
                    switch (estado) {
                        case 1:

                            flujo_salida.writeUTF("Introduce comando (dir/ver/exit)");

                            comando = flujo_entrada.readUTF();

                            if (comando.equals("dir")) {

                                System.out.println("\tEl cliente quiere ver el contenido del directorio");

                                String miDir = "./src";

                                File ficheroMiDir = new File(miDir);

                                File[] arrayFicheros = ficheroMiDir.listFiles();

                                flujo_salida.writeUTF(String.valueOf(arrayFicheros.length));

                                for (int i = 0; i < arrayFicheros.length; i++) {
                                    flujo_salida.writeUTF(arrayFicheros[i].getName());
                                }

                                estado = 1;

                                break;

                            } else if (comando.equals("ver")) {

                                flujo_salida.writeUTF("Introduce el nombre de archivo a ver");

                                String nombreDelArchivo = flujo_entrada.readUTF();

                                System.out.println("El cliente quiere leer el archivo: " + nombreDelArchivo);

                                File archivo = new File("./src/" + nombreDelArchivo);
                                FileReader fr = new FileReader(archivo);
                                BufferedReader br = new BufferedReader(fr);

                                String cadena;

                                while ((cadena = br.readLine()) != null) {
                                    flujo_salida.writeUTF(cadena);
                                    System.out.println(cadena);

                                }
                                System.out.println("Archivo enviado correcto");

                                estado = 1;

                                break;

                            }
                            if (comando.equals("exit")) {

                                estado = -1;
                                break;

                            }
                    }

                } while (estado != -1);

                skCliente.close();

                System.out.println("Cliente desconectado");

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
