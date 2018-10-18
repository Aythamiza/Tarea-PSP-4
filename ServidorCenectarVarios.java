import java.io.* ;

import java.net.* ;
import java.util.Random;


class ServidorConectarVarios extends Thread{

    Socket skCliente;

    static final int Puerto=2000;


    public ServidorConectarVarios(Socket sCliente) {

        skCliente=sCliente;

    }


    public static void main( String[] arg ) {

        try{

            // Inicio el servidor en el puerto

            ServerSocket skServidor = new ServerSocket(Puerto);

            System.out.println("Escucho el puerto " + Puerto );


            while(true){

                // Se conecta un cliente

                Socket skCliente = skServidor.accept();

                System.out.println("Cliente conectado");

// Atiendo al cliente mediante un thread

                new Servidor(skCliente).start();

            }

        } catch (Exception e) {;}

    }



    public void run(){

        try {

            // Creo los flujos de entrada y salida

            DataInputStream flujo_entrada = new DataInputStream(skCliente.getInputStream());

            DataOutputStream flujo_salida= new DataOutputStream(skCliente.getOutputStream());


// ATENDER PETICIÓN DEL CLIENTE

            for ( int nCli = 0; nCli < 3; nCli++) {


                System.out.println("Sirvo al cliente " + nCli);
                Random randomGenerator = new Random();
                int num_secreto = randomGenerator.nextInt(100);
                int encontrado = 0;
                String num_cliente = new String();
                flujo_salida.writeUTF("Tienes que adivinar un numero del 1 al 100");

                while (encontrado == 0) {
                    num_cliente = flujo_entrada.readUTF();
                    System.out.println("\tEl cliente ha dicho " + num_cliente);
                    if (num_secreto == Integer.parseInt(num_cliente)) {
                        flujo_salida.writeUTF("Correcto");
                        encontrado = 1;

                    } else {
                        if (num_secreto > Integer.parseInt(num_cliente)) {
                            flujo_salida.writeUTF("El numero secreto es mayor");

                        } else
                            flujo_salida.writeUTF("El numero secreto es menor");

                    }
                }

                // Se cierra la conexión

            skCliente.close();

            System.out.println("Cliente desconectado");
            }

        } catch( Exception e ) {

            System.out.println( e.getMessage() );

        }

    }

}

