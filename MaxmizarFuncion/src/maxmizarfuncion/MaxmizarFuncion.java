/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package maxmizarfuncion;

/**
 * @(#)Maximizar.java
 *
 *
 * @author Alfonso Calderón Chávez
 * @version 1.00 2012/6/29
 */
import java.text.DecimalFormat;
import java.util.Scanner;

public class MaxmizarFuncion {

    public MaxmizarFuncion() {
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
    	//Decodificación y calculo de la aptitup relativa
    	//System.out.println("\nProceso de decodificación\n");
    	
        double [] tablaDecodificacion = new double[numeroIndividuos];

    	boolean [] numeroBinarioX = new boolean[tamX], numeroBinarioY  = new boolean[tamY];
    	double x, y, F = 0;
    	for(int i = 0; i < numeroIndividuos; i++)
    	{
    		/*numeroBinarioX = individuos[i].substring(0,tamX);
    		numeroBinarioY = individuos[i].substring(tamX,tamIndividuo);
    		System.out.println("NumeroBinarioX"+i+": "+numeroBinarioX);
    		System.out.println("NumeroBinarioY"+i+": "+numeroBinarioY);*/
    		
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
                
                /*if(t == generaciones - 1)
                    System.out.println("Individuo "+i+": f("+x+","+y+") = "+tablaDecodificacion[i]);*/
    		F += tablaDecodificacion[i];
    		
    	}
        // if(t == generaciones - 1)
            System.out.println("El maximo es: "+max);
         
        arreglo[mayor++] = max;
    	
    	//cálculo de aptitud relativa y acumulativa
    //	System.out.println("\nValores normalizados y aptitud acumulada:\n");
    	
        double aux = 0.0;
    	for(int i = 0; i < numeroIndividuos; i++)
    	{
    		tablaDecodificacion[i] = Double.parseDouble(digitos.format(tablaDecodificacion[i]/F + aux)); 
    		aux = tablaDecodificacion[i];
    		/*System.out.println("Individuo "+i+":"+tablaDecodificacion[i]);*/
    	}
    	
    	//Aplicando el método de la ruleta para la SELECCION
    //	System.out.println("\nAplicando el método de la ruleta para selección\n");
    	
    	double numeroAleatorio;
        
    	boolean [][] individuoSeleccionado = new boolean[numeroIndividuos][tamIndividuo], individuoSeleccionadoCopia = new boolean[numeroIndividuos][tamIndividuo];
    	int cont = 0;
    	for(int i = 0; i < numeroIndividuos; i++)
    	{
    		numeroAleatorio = Math.random();
    		//System.out.println("\nNúmero generado: "+numeroAleatorio+"\n");
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
                          /*  System.out.print("Individuo seleccionado: "+j+":           ");
                            imprimir(individuoSeleccionado[cont-1]);
                            System.out.println("    ("+(cont-1)+")");*/
                            break;
    			}
    		}
    	}
        
      //  System.out.println("\nNueva Población Generada\n");
       // imprimir(individuoSeleccionado);
    
    	//Aplicando la cruza a los individuos seleccionados (1 x 2 y 3 x 4)
    //	System.out.println("\nSeleccionando individuos para la cruza\n");
    	int [] indices = new int[numeroIndividuos];
    	cont = 0;
    	for(int i = 0; i < numeroIndividuos; i++)
    	{
    		numeroAleatorio = Math.random();
    		if(numeroAleatorio <= Pc)
    		{
    			indices[cont++] = i;
    			/*System.out.print("Ind. Seleccionado para cruza: "+i+"   ");
                        imprimir(individuoSeleccionado[i]);
                        System.out.println();*/
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
                
              /*  
    		System.out.println("\nNumero de donde se cortará la cadena: "+num);

    		System.out.print("Valor de individuo cruzado "+indices[j]+": ");
                imprimir(individuoSeleccionadoCopia[indices[j]]);
                System.out.println();
    		System.out.print("Valor de individuo cruzado "+indices[j+1]+": ");	
                imprimir(individuoSeleccionadoCopia[indices[j+1]]);*/
    	}/*
    	System.out.println();
    	System.out.println("Individuos seleccionados no mutados y no cruzados");
    	System.out.println();
    	imprimir( individuoSeleccionado );
    	System.out.println();
    	System.out.println("Individuos cruzados");
    	System.out.println();
    	imprimir( individuoSeleccionadoCopia );
    	*/
    	//Aplicando las mutaciones
    	
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
        
        individuos = individuoSeleccionadoCopia;
    	/*System.out.println("\n\nGeneración anterior");
    	System.out.println();
    	imprimir( individuos);
    	System.out.println();
    	System.out.println("Individuos Seleccionados ");
    	System.out.println();
    	imprimir( individuoSeleccionado );
        System.out.println();
    	System.out.println("Individuos cruzados y mutados");
    	System.out.println();
    	imprimir( individuoSeleccionadoCopia );
    	System.out.println();*/
        t++;
        }while(t < generaciones);
    	
        ventanaGrafica miventana = new ventanaGrafica(arreglo);
        miventana.setSize(900,900);
        miventana.show();
	
    }
}