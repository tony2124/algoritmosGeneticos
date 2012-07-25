/*
/*
 * ALFONSO CALDERON CHAVEZ
 * FRANCISCO JAVIER CALDERÓN CHÁVEZ
 * USO DEL ALGORITMO GENETICO SIMPLE PARA MAXIMIZAR LA FUNCION
 * 
 * CODIFICACION BINARIA
 * 
 * INSTITUTO DE INVESTIGACION EN MATEMATICAS APLICADAS Y EN SISTEMAS
 * INSTITUTO TECNOLOGICO SUPERIOR DE APATZINGAN
 */
package nreynasbinario;

/**
 * @(#)Maximizar.java
 *
 *
 * @author Alfonso Calder�n Ch�vez
 * @version 1.00 2012/6/29
 */
import java.text.DecimalFormat;
import java.util.Scanner;

public class NReynasBinario {

    public NReynasBinario() {
    }
    
    static int evaluarFuncion(boolean numeroBinario[][], int lsup)
    {
    	int contador = 0;
    	double qi, qj;
    	
    	//verifico los ataques horizontales
    	for(int i = 0; i < numeroBinario.length-1; i++)
    	{
            qi = Math.ceil(obtenerDecimal(numeroBinario[i], lsup) * ( (lsup - 1 ) / ( Math.pow(2, numeroBinario[i].length ) - 1)));
            for(int j = i + 1; j < numeroBinario.length; j++)
            {
                qj = Math.ceil(obtenerDecimal(numeroBinario[j], lsup) * ( (lsup - 1 ) / ( Math.pow(2,numeroBinario[j].length) - 1)));	
                if( qi == qj)
                {
                        contador++;
                        break;
                }
            }	
    	}
    	
    	//verifica diagonales
    	for(int i = 0; i < numeroBinario.length-1; i++)
    	{
            qi = Math.ceil(obtenerDecimal(numeroBinario[i], lsup) * ( (lsup - 1 ) / ( Math.pow(2, numeroBinario[i].length ) - 1)));
            for(int j = i + 1; j < numeroBinario.length; j++)
            {
                qj = Math.ceil(obtenerDecimal(numeroBinario[j], lsup) * ( (lsup - 1 ) / ( Math.pow(2,numeroBinario[j].length) - 1)));	
                if(Math.abs(qi - qj) == Math.abs(i - j))
                {
                    contador++;
                }
            }	
    	}
 
    	if(contador == 0)
    	{
            for(int i = 0; i < numeroBinario.length; i++)
            {
                qi = Math.ceil(obtenerDecimal(numeroBinario[i], lsup) * ( (lsup - 1 ) / ( Math.pow(2, numeroBinario[i].length ) - 1)));
                System.out.println(qi + " - ");
            }	
    	}
        
    	return contador;
    }
    
    static int obtenerDecimal(boolean m[], int nreynas)
    {
        String cadena = "";
        for(int i = 0; i < m.length; i++)
        {
            cadena += (m[i] == false) ? "0" : "1";
        }

    	return Integer.parseInt(cadena,2) % nreynas;
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
    
    static void imprimirSolucion(boolean [][] numeroBinario, int nreynas)
    {
       
        for(int i = 0; i < nreynas; i++)
        {
            System.out.println();
            for(int j = 0; j < nreynas; j++)
                if(obtenerDecimal(numeroBinario[j], nreynas) != i)
                    System.out.print("◙");
                else
                    System.out.print("X");
        }
            
    }
    
    static boolean [][] obtenerVariables(int i, boolean [][] individuos, int tamVariable, int tamIndividuo, int nreynas)
    {
        int contador = -1;
        boolean [][] numeroBinario = new boolean[nreynas][tamVariable];
        for(int j = 0; j < tamIndividuo; j++ )
        {
                if(j%tamVariable == 0)
                    contador++;
                numeroBinario[contador][j%tamVariable] = individuos[i][j];
        }
        return numeroBinario;
    }
    
    public static void main (String[] args) 
    {
        Scanner leer = new Scanner(System.in);
        
        System.out.println("Numero de reynas: ");
    	int numeroReynas = leer.nextInt();
        
        System.out.println("Numero de individuos: ");
    	int numeroIndividuos = leer.nextInt();
        
        System.out.println("Porcentaje de cruza: ");
        double Pc = leer.nextDouble();
        
        System.out.println("Porcentaje de mutaci�n: ");
        double Pm = leer.nextDouble();

        System.out.println("Numero de generaciones: ");
        int generaciones = leer.nextInt();

    	int lsup = numeroReynas, linf = 0;
    	
    	int precision = 0;
    	
    	DecimalFormat digitos = new DecimalFormat("0.0000");		
    	
        //Calculo de tama�os
    	int tamVariable = obtenerTamanio(lsup, linf, precision);
    	System.out.println(tamVariable);
    	int tamIndividuo = tamVariable * numeroReynas;
    	
    	//generaci�n de la matriz para los individuos
    	boolean [][] individuos = new boolean[numeroIndividuos][tamIndividuo];
    	
    	//Generaci�n de genotipo
    	for(int i = 0; i < numeroIndividuos; i++)
            for(int j = 0; j < tamIndividuo; j++)
                individuos[i][j] = generarBinario();
    
    			
    	imprimir(individuos);
        double arreglo [] = new double[generaciones];
        int t = 0, mayor = 0;
        do
        {
         int ind = -1;
         double min = 500.00;
         
 /******************************************************************************************
 *              
 * EVALUACI�N DE LA FUNCI�N 
 * 
 ******************************************************************************************/
 
        double [] tablaDecodificacion = new double[numeroIndividuos];

    	boolean [][] numeroBinario = new boolean[numeroReynas][tamVariable];
    	double F = 0;
    	
        //Obtencion de una matriz donde la fila es el numero binario. Cada matriz es para cada individuo.
    	for(int i = 0; i < numeroIndividuos; i++)
    	{
            
            numeroBinario = obtenerVariables(i, individuos, tamVariable,tamIndividuo, numeroReynas);
                      
            //Se evalua la función mandando las variables (numeroBinario)
            tablaDecodificacion[i] = (double) evaluarFuncion(numeroBinario, lsup);                
            if(tablaDecodificacion[i] < min)
            {
                min = tablaDecodificacion[i];
                ind = i;
            }
		tablaDecodificacion[i] = 50000.0 - tablaDecodificacion[i];
    		F += tablaDecodificacion[i];
    	}
        
        System.out.println("El m�mino en la generaci�n "+t+" es: "+min);
        arreglo[mayor++] = min;
    	
 /******************************************************************************************
 *              
 * CÁLCULO DE LA APTITUD RELATIVA Y ACUMULADA
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
                    individuoSeleccionado[cont] = individuos[j].clone();
                    individuoSeleccionadoCopia[cont] = individuos[j].clone();
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
        if(min == 0.0)
        {
            t = generaciones;
        }
        
        if(t == generaciones)
        {
            numeroBinario = obtenerVariables(ind, individuos, tamVariable, tamIndividuo, numeroReynas);
            imprimir(numeroBinario);
            imprimirSolucion( numeroBinario, numeroReynas);
        }
            
        }while(t < generaciones);
    	
        ventanaGrafica miventana = new ventanaGrafica(arreglo);
        miventana.setSize(500,500);
        miventana.show();
	
    }
}