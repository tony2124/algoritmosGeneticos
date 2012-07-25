/**
* @(#)NReynasEntero.java
*
*
* @author FRANCISCO JAVIER CALDERÓN CHÁVEZ.
* ALFONSO CALDERÓN CHÁVEZ.
*
* @Instituto: INSTITUTO TECNOLÓGICO SUPERIOR DE APATZINGÁN.
* UNIVERSIDAD NACIONAL AUTONOMA DE MÉXICO.
* INSTITUTO DE INVESTIGACION EN MATEMATICAS APLICADAS Y EN SISTEMAS.
*/
package nreynasentero;

import java.text.DecimalFormat;
import java.util.Scanner;
public class NReynasEntero {

    public NReynasEntero() {
    }
    
    static int evaluarFuncion(int numeroEntero[])
    {
        int contador = 0;
        double qi, qj;
        //verifico los ataques horizontales
        for(int i = 0; i < numeroEntero.length-1; i++)
        {
            qi=numeroEntero[i];

            for(int j = i + 1; j < numeroEntero.length; j++)
            {
                qj=numeroEntero[j];
                if( qi == qj)
                {
                    contador++;
                    break;
                }
    
            }
     }
    
     //verifica diagonales
     for(int i = 0; i < numeroEntero.length-1; i++)
     {
        qi=numeroEntero[i];

        for(int j = i + 1; j < numeroEntero.length; j++)
        {
            qj = numeroEntero[j];
            if(Math.abs(qi - qj) == Math.abs(i - j))
            {
                contador++;
                break;
            }
        }
     }
     
     
     if(contador == 0)
     {
         System.out.println("Número de ataques: "+contador);
        for(int i = 0; i < numeroEntero.length; i++)
        {
            qi = numeroEntero[i];
            System.out.println(qi + " - ");
        }
     }
     return contador;
    }
    
    static int generarNumero(int lsup){
        return (int)(Math.random()*lsup);
    }
    
    static void imprimir(int [][]m)
    {
     for(int i = 0; i < m.length; i++)
     {
            System.out.print("Individuo "+i+": ");
            for(int j = 0; j < m[i].length; j++)
                    System.out.print (m[i][j]);
            System.out.println();
     }
    }
    
    static void imprimir(int []m)
    {
     for(int i = 0; i < m.length; i++)
     {
           System.out.print(m[i]);
     }
    }
    
    static void imprimirSolucion(int [] a)
    {
        for(int i = 0; i < a.length; i++)
        {
            System.out.println();
            for(int j = 0; j < a.length; j++)
                if(a[j] != i)
                    System.out.print("◙");
                else
                    System.out.print("X");
        }
            
    }
    
    public static void main(String [] args){
     Scanner leer = new Scanner(System.in);
     DecimalFormat digitos = new DecimalFormat("0.0000");
        
        System.out.println("Numero de reynas: ");
     int numeroReynas = leer.nextInt();
        
        System.out.println("Numero de individuos: ");
     int numeroIndividuos = leer.nextInt();
        
        System.out.println("Porcentaje de cruza: ");
        double Pc = leer.nextDouble();
        
        System.out.println("Porcentaje de mutación: ");
        double Pm = leer.nextDouble();

        System.out.println("Numero de generaciones: ");
        int generaciones = leer.nextInt();
        
        int lsup = numeroReynas, linf = 0;
     
     //int precision = 0;
     int tamIndividuo = numeroReynas;
    
     int [][] individuos = new int[numeroIndividuos][tamIndividuo];
    
     int numero;
     
     //GENERACIÓN DE GENOTIPO, PERMUTACIONES DE NÚMEROS.
     for(int i = 0; i < numeroIndividuos; i++)
     {
        for(int j = 0; j < tamIndividuo; )
        {
            numero = generarNumero(lsup);
            boolean existe = false;
            for(int comp = 0; comp < j; comp++)
            {   
                if(numero == individuos[i][comp]){
                    existe = true; break;
                }
            }
            if(!existe)
            {
                individuos[i][j] = numero;
                j++;    
            }
                
        }

     }
    
     imprimir(individuos);
     double arreglo [] = new double[generaciones];
     int t = 0, mayor = 0;

     do
     {
        int ind = -1;
        double min = 500.00;

    /******************************************************************************************
    *
    * EVALUACIÓN DE LA FUNCIÓN
    *
    ******************************************************************************************/

        double [] tablaDecodificacion = new double[numeroIndividuos];

        double F = 0;

        for(int i = 0; i < numeroIndividuos; i++)
        {
            tablaDecodificacion[i] = (double) evaluarFuncion(individuos[i]);

            if(tablaDecodificacion[i] < min)
            {
                min = tablaDecodificacion[i];
                ind = i;
            }
            
            tablaDecodificacion[i] = 50000.0 - tablaDecodificacion[i];
            F += tablaDecodificacion[i];
        }
        
        System.out.print("El mímino en la generación "+t+" es: "+min+"  ---  ");
        imprimir(individuos[ind]);
        System.out.println();
        arreglo[mayor++] = min;



    /******************************************************************************************
    *
    * CALCULO DE LA APTITUD RELATIVA Y ACUMULADA
    *
    ******************************************************************************************/

        double aux = 0.0;
        for(int i = 0; i < numeroIndividuos; i++)
        {
            tablaDecodificacion[i] = Double.parseDouble(digitos.format(tablaDecodificacion[i]/F + aux));
            aux = tablaDecodificacion[i];
        }

    /******************************************************************************************
    *
    * SELECCION
    *
    ******************************************************************************************/
        double numeroAleatorio;
        int [][] individuoSeleccionado = new int[numeroIndividuos][tamIndividuo], individuoSeleccionadoCopia = new int[numeroIndividuos][tamIndividuo];
        int cont = 0;
        for(int i = 0; i < numeroIndividuos; i++)
        {
            numeroAleatorio = Math.random();
            for(int j = 0; j < numeroIndividuos; j++)
            {
                if(tablaDecodificacion[j] > numeroAleatorio)
                {
                    individuoSeleccionado[cont] = individuos[j].clone();
                    individuoSeleccionadoCopia[cont] = individuos[j].clone();
                    cont++;
                    break;
                }
            }
        }
        
        //imprimir(individuoSeleccionado);
    
/******************************************************************************************
*
* CRUZA
*
******************************************************************************************/
 
    
     //Aplicando la cruza a los individuos seleccionados (1 x 2 y 3 x 4)
     int [] indices = new int[numeroIndividuos];
     cont = 0;
     for(int i = 0; i < numeroIndividuos; i++)
     {
        numeroAleatorio = Math.random();
        if(numeroAleatorio <= Pc)
        {
            indices[cont++] = i;
           /* System.out.print("Ind selecccionado: "+i+"   ---  ");
            imprimir(individuoSeleccionado[i]);
            System.out.println(); */
        }
    
     }
        
     int num;
    
     for(int i = 0, j = 0; i < cont / 2; i++, j+=2)
     {
        num = (int) ((Math.random()*1000) % tamIndividuo);
       // System.out.println("Numero seleccionado: "+num);
        //Se realiza el intercambio entre el primer individuo y el segundo
        for(int pass = num; pass < tamIndividuo; pass++)
        {
            individuoSeleccionadoCopia[indices[j]][pass] = individuoSeleccionado[indices[j+1]][pass];
            individuoSeleccionadoCopia[indices[j+1]][pass] = individuoSeleccionado[indices[j]][pass];
        }
        
        
        //MAPEO H1 
        for(int pass = 0; pass < num; pass++)
        {
            boolean existe = false;
            for(int comp = num; comp < tamIndividuo; comp++)
            {
                if(individuoSeleccionadoCopia[indices[j]][pass] == individuoSeleccionadoCopia[indices[j]][comp])
                {
                    existe = true;
                    comp = tamIndividuo;
                }
            }
            if(existe)
            {
                do
                {
                    numero = generarNumero(lsup);
                    existe = false;
                    for(int comp = 0; comp < tamIndividuo; comp++)
                    {
                        if(numero == individuoSeleccionadoCopia[indices[j]][comp]){
                            existe = true; 
                            comp = tamIndividuo;
                        }
                    }
                    if(!existe)
                    {
                        individuoSeleccionadoCopia[indices[j]][pass] = numero;
                    }
                }while(existe);
            }
                
        }
       
        //MAPEO H2
        for(int pass = 0; pass < num; pass++)
        {
            boolean existe = false;
            for(int comp = num; comp < tamIndividuo; comp++)
            {
                if(individuoSeleccionadoCopia[indices[j+1]][pass] == individuoSeleccionadoCopia[indices[j+1]][comp])
                {
                    existe = true;
                    comp = tamIndividuo;
                }
            }
            if(existe)
            {
                do
                {
                    numero = generarNumero(lsup);
                    existe = false;
                    for(int comp = 0; comp < tamIndividuo; comp++)
                    {
                        if(numero == individuoSeleccionadoCopia[indices[j+1]][comp]){
                            existe = true; 
                            comp = tamIndividuo;
                        }
                    }
                    if(!existe)
                    {
                        individuoSeleccionadoCopia[indices[j+1]][pass] = numero;
                    }
                }while(existe);
            }
                
        }
          
     }

     
/******************************************************************************************
*
* MUTACION
*
******************************************************************************************/
     int num1, num2;
     for(int i = 0; i < numeroIndividuos; i++)
     {
            for(int j = 0; j < tamIndividuo; j++)
            {
                numeroAleatorio = Math.random();
                if(numeroAleatorio <= Pm)
                {
                     do{
                         num1 = generarNumero(lsup);
                         num2 = generarNumero(lsup);
                     }while(num1==num2);
                     
                     individuoSeleccionadoCopia[i][num1] = individuoSeleccionado[i][num2];
                     individuoSeleccionadoCopia[i][num2] = individuoSeleccionado[i][num1];
                     
                }
            }
     }
        
  /******************************************************************************************
*
* ELITISMO
*
******************************************************************************************/
 
        individuoSeleccionadoCopia[ind] = individuos[ind].clone();
        
        /****************/
        
        individuos = individuoSeleccionadoCopia;
        t++;
        if(min == 0.0)
        {
            t = generaciones;
        }
        if(t == generaciones)
            imprimirSolucion(individuoSeleccionadoCopia[ind]);
        }while(t < generaciones);
        
        ventanaGrafica miventana = new ventanaGrafica(arreglo);
        miventana.setSize(500,500);
        miventana.show();
 
     }
    
}