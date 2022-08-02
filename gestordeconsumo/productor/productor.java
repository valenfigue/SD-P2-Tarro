package com.gdc.gestordeconsumo.productor;

import java.util.Scanner; // para recibir por consola.

public class productor{
    
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
        System.out.println("Menú productor");
        System.out.println("1. Llenar");
        System.out.println("2. Respaldo");
        System.out.println("3. Restauración");
        System.out.println("0. Salir");
        System.out.println("----------------"); 
        return recibirOpcion();
    }
    
    private static void consumirLlenado(){
        //servicioLlenado servicio = new servicioLlenado();        
        try {
            System.out.println("Llenado productos");
            //servicio.llenado('A', 60);
            //servicio.llenado('B', 40);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) {
        while(true){
            try{
                switch (presentarOpcionMenu()) {
                    case 1:
                        consumirLlenado();
                        break;
                    case 2:
                        System.out.println("Solicitando respaldo");
                        break;
                    case 3:
                        System.out.println("Solicitando restauración");
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
