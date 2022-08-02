package com.gdc.gestordeconsumo.consumidor;

import java.util.Scanner;  // para recibir por consola.

public class consumidor{
    
   //Recibo las opciones del consumidor por pantalla
    private static int recibirOpcion(){ 
        String entradaTeclado;
        Scanner entradaEscaner = new Scanner (System.in); 
        entradaTeclado = entradaEscaner.nextLine (); 
        int opcion = Integer.parseInt(entradaTeclado);   
        return opcion;
    }
    
    //Presento las opciones por pantalla para el consumidor
    private static int presentarOpcionMenu(){ 
        System.out.println("");  
        System.out.println("----------------"); 
        System.out.println("Menú consumidor");
        System.out.println("1. Consumir un producto");
        System.out.println("2. Consultar transacciones");
        System.out.println("0. Salir");
        System.out.println("----------------"); 
        return recibirOpcion();
    }
    
    //Tomo las dos opciones del menú producto que quiere consultar
    private static int[] presentarOpcionProducto(){ 
        int[] response = new int[2];
        System.out.println("");  
        System.out.println("----------------"); 
        System.out.println("Menú productos");
        System.out.println("1. Producto A");
        System.out.println("2. Producto B");
        System.out.println("----------------"); 
        response[0] = recibirOpcion();
        System.out.println("Ingrese la cantidad de productos");
        response[1] = recibirOpcion();
        System.out.println("RESPUESTA: Producto: "+response[0]+", Cantidad: "+response[1]); 
        return response;
    }
    
    //Implemento el consumo del servicio desde otra clase
    private static void consumirServicio(int tipo, int cantidad){
        //servicioConsumir servicio = new servicioConsumir();
        try {
            System.out.println("Estoy consumiendo del servicio");
            //servicio.consumir(tipo, cantidad);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    //Implemento la consulta del servicio desde otra clase
    private static void consultarServicio(){
        //servicioConsultar servicio = new servicioConsultar();
        try {
            System.out.println("Estoy consultado del servicio");
            //servicio.consultar();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
            while(true){
            try{
                switch (presentarOpcionMenu()) {
                    case 1:
                        int[] response = presentarOpcionProducto();
                        consumirServicio(response[0], response[1]);
                        break;
                    case 2:
                        consultarServicio();
                        break;
                    case 0:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opción invalida");
                }
            }
            catch (Exception e){
                e.getMessage();
            }
            }
    }
}

