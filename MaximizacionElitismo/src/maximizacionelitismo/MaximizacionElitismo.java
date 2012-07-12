/*
 * ALFONSO CALDERON CHAVEZ
 * USO DEL ALGORITMO GENETICO SIMPLE PARA MAXIMIZAR LA FUNCION
 * 
 * CODIFICACION BINARIA
 * 
 * INSTITUTO DE INVESTIGACION EN MATEMATICAS APLICADAS Y EN SISTEMAS
 * INSTITUTO TECNOLOGICO SUPERIOR DE APATZINGAN
 */
package maximizacionelitismo;

/**
 * @(#)Maximizar.java
 *
 *
 * @author Alfonso Calderón Chávez
 * @version 1.00 2012/6/29
 */
import java.text.DecimalFormat;
import java.util.Scanner;

public class MaximizacionElitismo {

    public MaximizacionElitismo() {
    }
    
    static double evaluarFuncion(double x, double y)
    {
    	return (double) (21.5 + x * Math.sin(4*Math.PI*x) + y * Math.sin(20 * Math.PI * y));
    }
    
    static int obtenerDecimal(boolean m[])
    {
        String cadena = "";
        for(int i = 0; i < m.length; i++)
        {
            cadena += (m[i] == false) ? "0" : "1";
        }
    	return Integer.parseInt(cadena,2);
    }
    
    static int obtenerTamanio(double lsup, double linf, int precision)
    {
    	return (int)Math.ceil( Math.log((lsup-linf) * Math.pow(10,precision))  / Math.log(2));
    }
    
    static boolean generarBinario()
    {
    	return (Math.random() > 0.5) ? true : false;
    }
    
    static void imprimir(boolean [][]m)
    {
    	for(int i = 0; i < m.length; i++)
    	{   
            System.out.print("Individuo "+i+": ");
            for(int j = 0; j < m[i].length; j++)		
                    System.out.print((m[i][j]==false) ? "0" : "1");
            System.out.println();
    	}
    }
    
    static void imprimir(boolean []m)
    {
    	for(int i = 0; i < m.length; i++)
    	{   
           System.out.print((m[i]==false) ? "0" : "1");
    	}
    }
    
    public static void main (String[] args) 
    {
        Scanner leer = new Scanner(System.in);
        System.out.println("Numero de individuos: ");
    	int numeroIndividuos = leer.nextInt();
        
        System.out.println("Porcentaje de cruza: ");
        double Pc = leer.nextDouble();
        
        System.out.println("Porcentaje de mutación: ");
        double Pm = leer.nextDouble();

        System.out.println("Numero de generaciones: ");
        int generaciones = leer.nextInt();

    	double linfx = -3.0, lsupx = 12.1, linfy = 4.1, lsupy = 5.8;
    	int precision = 4;
    	
    	DecimalFormat digitos = new DecimalFormat("0.0000");		
    	
        //Calculo de tamaños
    	int tamX = obtenerTamanio(lsupx, linfx, precision);
    	int tamY = obtenerTamanio(lsupy, linfy, precision);
    	int tamIndividuo = tamX + tamY;
    	
    	//generación de la matriz para los individuos
    	boolean [][] individuos = new boolean[numeroIndividuos][tamIndividuo];
    	
    	//Generación de genotipo
    	for(int i = 0; i < numeroIndividuos; i++)
    	{
    		for(int j = 0; j < tamIndividuo; j++)
    			individuos[i][j] = generarBinario();
    	}
    			
    	//imprimir(individuos);
        double arreglo [] = new double[generaciones];
        int t = 0,mayor = 0;;
        
        
        do
        {
         int ind = -1;
         double max = -500.00;
         
 /******************************************************************************************
 *              
 * EVALUACIÓN DE LA FUNCIÓN 
 * 
 ******************************************************************************************/
 
        double [] tablaDecodificacion = new double[numeroIndividuos];

    	boolean [] numeroBinarioX = new boolean[tamX], numeroBinarioY  = new boolean[tamY];
    	double x, y, F = 0;
    	for(int i = 0; i < numeroIndividuos; i++)
    	{
    		for(int j = 0; j < tamIndividuo; j++ )
    		{
                    if(j < tamX)
    			numeroBinarioX[j] = individuos[i][j];
                    else
    			numeroBinarioY[j-tamX] = individuos[i][j];
    		}
    		
                x = Double.parseDouble(digitos.format(linfx + obtenerDecimal(numeroBinarioX) * ( ( lsupx - linfx) / ( Math.pow(2,tamX) - 1))));
    		y = Double.parseDouble(digitos.format(linfy + obtenerDecimal(numeroBinarioY) * ( ( lsupy - linfy) / ( Math.pow(2,tamY) - 1))));
    		
    		tablaDecodificacion[i] = Double.parseDouble(digitos.format(evaluarFuncion(x,y)));
                
                if(tablaDecodificacion[i] > max)
                {
                    max = tablaDecodificacion[i];
                    ind = i;
                   
                }

    		F += tablaDecodificacion[i];
    		
    	}
            System.out.println("El maximo es: "+max);
         
        arreglo[mayor++] = max;
    	
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
        
    	boolean [][] individuoSeleccionado = new boolean[numeroIndividuos][tamIndividuo], individuoSeleccionadoCopia = new boolean[numeroIndividuos][tamIndividuo];
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
    		}
    			
    	}
        
    	int num;
    	
    	for(int i = 0, j = 0; i < cont / 2; i++, j+=2)
    	{
    		num = (int) ((Math.random()*1000) % tamIndividuo);
                
                //Se realiza el intercambio entre el primer individuo y el segundo
                for(int pass = 0; pass < tamIndividuo;  pass++)
                    if(pass < num)
                        individuoSeleccionadoCopia[indices[j]][pass] = individuoSeleccionado[indices[j]][pass] ;
                    else
                        individuoSeleccionadoCopia[indices[j]][pass] = individuoSeleccionado[indices[j+1]][pass] ;
                
                for(int pass = 0; pass < tamIndividuo;  pass++)
                    if(pass < num)
                        individuoSeleccionadoCopia[indices[j+1]][pass] = individuoSeleccionado[indices[j+1]][pass] ;
                    else
                        individuoSeleccionadoCopia[indices[j+1]][pass] = individuoSeleccionado[indices[j]][pass] ;
                
    	}
 /******************************************************************************************
 *              
 * MUTACION
 * 
 ******************************************************************************************/
 
    	for(int i = 0; i < numeroIndividuos; i++)
    	{
            for(int j = 0; j < tamIndividuo; j++)
            {
                numeroAleatorio = Math.random();
    		if(numeroAleatorio <= Pm)
    		{
                    individuoSeleccionadoCopia[i][j] = (individuoSeleccionadoCopia[i][j] == true) ? false : true;
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
        }while(t < generaciones);
    	
        ventanaGrafica miventana = new ventanaGrafica(arreglo);
        miventana.setSize(900,900);
        miventana.show();
	
    }
}