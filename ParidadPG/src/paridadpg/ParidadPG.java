/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paridadpg;

import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author Invitado
 */
public class ParidadPG {

    /**
     * @param args the command line arguments
     */
    
    
    static void generarIndividuo(String funciones[], String operadores[], int profundidad, LinkedList lista)
    {
        if(profundidad == 0)
        {
            int num = (int)(Math.random() * operadores.length);
            lista.add(operadores[num]);
        }
        else
        {
            //FULL -> :D
            int num = (int)(Math.random() * funciones.length);
            lista.add(funciones[num]);
            generarIndividuo(funciones, operadores, profundidad - 1, lista);
            if(funciones[num].compareTo("NOT") != 0)
            {
                generarIndividuo(funciones, operadores, profundidad - 1, lista);
            }
        }
    }
    
    public static void evaluar(int[][]tablaVerdad, String expresion)
    {
        
    }
    
    static void imprimir(LinkedList lista)
    {
        for(int i = 0; i < lista.size(); i++)
            System.out.print(lista.get(i) + "-");
        System.out.println();
    }
    
    public static void main(String[] args) {
        LinkedList <String> lista = new LinkedList();
        Scanner leer = new Scanner(System.in);
        
        String []funciones = {"AND","OR","NOT"};
        String []operadores = {"A","B","C"};
        
        int[][] tablaVerdad = {
            {0,0,0,1},
            {0,0,1,0},
            {0,1,0,0},
            {0,1,1,1},
            {1,0,0,0},
            {1,0,1,1},
            {1,1,0,1},
            {1,1,1,0}};
        
        System.out.println("Numero de individuos: ");
       
        int nInd = leer.nextInt();
        
        for(int i = 0; i < nInd; i++)
        {
            generarIndividuo(funciones, operadores, 4, lista);
            evaluar(tablaVerdad, lista.get(i));
            imprimir(lista);
            lista = new LinkedList();
        }
            
        
    }
}
