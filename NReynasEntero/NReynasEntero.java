/**
 * @(#)NReynasEntero.java
 *
 *
 * @author 		FRANCISCO JAVIER CALDERÓN CHÁVEZ.
 *				ALFONSO CALDERÓN CHÁVEZ.
 *
 * @Instituto: 	INSTITUTO TECNOLÓGICO SUPERIOR DE APATZINGÁN.
 *				UNIVERSIDAD NACIONAL AUTONOMA DE MÉXICO.
 *				INSTITUTO DE INVESTIGACION EN MATEMATICAS APLICADAS Y EN SISTEMAS.
 */

import java.util.Scanner;
import java.text.DecimalFormat;
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
    	System.out.println("Numero de ataques: "+contador);
    	if(contador == 0)
    	{
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
    
    static int obtenerTamanio(double lsup, double linf, int precision)
    {
    	return (int)Math.ceil( Math.log((lsup-linf) * Math.pow(10,precision))  / Math.log(2));
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
    	
    	int auxi;
    	for(int i = 0; i < numeroIndividuos; i++)
    	{
    		for(int j = 0; j < tamIndividuo; j++)
    			individuos[i][j] = generarNumero(lsup);
    	}
    	
    	imprimir(individuos);
    	double arreglo [] = new double[generaciones];
        int t = 0, mayor = 0;

    	int ind = -1;
        double min = 500.00;
         
 /******************************************************************************************
 *              
 * EVALUACIÓN DE LA FUNCIÓN 
 * 
 ******************************************************************************************/
 
        double [] tablaDecodificacion = new double[numeroIndividuos];
        
        double x, y, F = 0;
    	
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
    	System.out.println("El mímino en la generación es: "+min);
        imprimir(individuos[ind]);
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
                            for(int pass = 0; pass < tamIndividuo; pass++)
                            {
                                individuoSeleccionado[cont][pass] = individuos[j][pass];
                                individuoSeleccionadoCopia[cont][pass] = individuos[j][pass];
                            }
                            cont++;
                            break;
    			}
    		}
    	}
    	
    	
    	
 
     }
    
}