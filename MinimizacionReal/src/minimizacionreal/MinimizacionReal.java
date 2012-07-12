/*
 * ALFONSO CALDERON CHAVEZ
 * USO DEL ALGORITMO GENETICO SIMPLE PARA MINIMIZAR LA FUNCION
 * 
 * CODIFICACION REAL
 * 
 * INSTITUTO DE INVESTIGACION EN MATEMATICAS APLICADAS Y EN SISTEMAS
 * INSTITUTO TECNOLOGICO SUPERIOR DE APATZINGAN
 */
package minimizacionreal;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 *
 * @author Alfonso Calderón.
 */
public class MinimizacionReal {

    static double evaluarFuncion(double x, double y)
    {
    	return (double) (21.5 + x * Math.sin(4*Math.PI*x) + y * Math.sin(20 * Math.PI * y));
    }
    
    
    static double generarReal(double lsup, double linf)
    {
        double aleatorio = linf + Math.random()*(lsup - linf);        
    	return aleatorio;
    }
    
    static void imprimir(double [][]m)
    {
    	for(int i = 0; i < m.length; i++)
    	{   
            System.out.print("Individuo "+i+": ");
            for(int j = 0; j < m[i].length; j++)		
                    System.out.print(m[i][j]+"    ");
            System.out.println();
    	}
    }
    
    static void imprimir(double []m)
    {
    	for(int i = 0; i < m.length; i++)
    	{   
           System.out.print(m[i]+"    ");
    	}
    }
    
    public static void main (String[] args) 
    {
        Scanner leer = new Scanner(System.in);
        System.out.println("Numero de individuos: ");
    	int numeroIndividuos = leer.nextInt();
        
        System.out.println("Porcentaje de cruza: ");
        double Pc = leer.nextDouble();
        
        System.out.println("Porcentaje de mutaciÃ³n: ");
        double Pm = leer.nextDouble();

        System.out.println("Numero de generaciones: ");
        int generaciones = leer.nextInt();

    	DecimalFormat digitos = new DecimalFormat("0.0000");		
    	
        //Calculo de tamaÃ±os
    	int tamIndividuo = 2;
        
        //Limites de las variables
        double limites[][] = new double[tamIndividuo][2];
        //Para x1
        limites[0][0] = -3.0;
        limites[0][1] = 12.1;
        
        //Para x2 (y)
        limites[1][0] = 4.1;
        limites[1][1] = 5.8;
        
        
        double arreglo[] = new double[generaciones];
    	int mejor=0;
    	//generaciÃ³n de la matriz para los individuos
    	double [][] individuos = new double[numeroIndividuos][tamIndividuo];
    	
    	//GeneraciÃ³n de genotipo
    	for(int i = 0; i < numeroIndividuos; i++)
    	{
            for(int j = 0; j < tamIndividuo; j++)
                individuos[i][j] = generarReal(limites[j][0], limites[j][1]);
            
    	}
    			
    	imprimir(individuos);
        
        int t = 0;
        do
        {
/******************************************************************************************
 *              
 * EVALUACIÓN DE LA FUNCIÓN 
 * 
 ******************************************************************************************/
    	
        double [] tablaDecodificacion = new double[numeroIndividuos];
        int ind=-1;
    	double x, y, F = 0, min = 500.0;
    	for(int i = 0; i < numeroIndividuos; i++)
    	{
    		x = individuos[i][0];
                y = individuos[i][1];
                
    		tablaDecodificacion[i] = Double.parseDouble(digitos.format(evaluarFuncion(x,y)));
                if(tablaDecodificacion[i] < min )
                {
                    ind=i;
                    min = tablaDecodificacion[i];
                    System.out.println("El mejor individuo es: "+ind+"      "+x+"   "+y+"    ----   "+min);

                }
               
                tablaDecodificacion[i] = 500000 - tablaDecodificacion[i]; 
    		
                F += tablaDecodificacion[i];
    		
    	}
        arreglo[mejor++] = min;
       
        System.out.println("\n\nEl mínimo es: "+min);
    	
 /******************************************************************************************
 *              
 * APTITUD RELATIVA Y ACUMULATIVA
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
 * SELECCIÓN 
 * 
 ******************************************************************************************/
   
    	double numeroAleatorio;
        
    	double [][] individuoSeleccionado = new double[numeroIndividuos][tamIndividuo], individuoSeleccionadoCopia = new double[numeroIndividuos][tamIndividuo];
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
        //Encontrando los individuos a los que se les aplicará la cruza
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
        
    	//Aplicando la cruza
        double num, menor=0, mayor=0;
        
    	for(int i = 0, j = 0; i < cont / 2; i++, j+=2)
    	{ 
           
            for(int k = 0; k < tamIndividuo; k++)
            {
                //Considerando numeros diferentes
                if (individuoSeleccionado[j][k] < individuoSeleccionado[j+1][k]) {
                    menor = individuoSeleccionado[j][k];
                    mayor = individuoSeleccionado[j+1][k];
                }else{
                    menor = individuoSeleccionado[j+1][k];
                    mayor = individuoSeleccionado[j][k];
                }

                num = menor + Math.random() * (mayor - menor);
                individuoSeleccionadoCopia[j][k] = num;
                num = menor + Math.random() * (mayor - menor);
                individuoSeleccionadoCopia[j+1][k] = num;
            }
          
             
    	}
        
 /******************************************************************************************
 *              
 * MUTACIÓN
 * (Se generan dos números aleatorios entre los genes de ambos padres para generar 2 hijos)
 * 
 ******************************************************************************************/
   
    	for(int i = 0; i < numeroIndividuos; i++)
    	{
            for(int j = 0; j < tamIndividuo; j++)
            {
    		if(Math.random() <= Pm)
    		{        
                    if(Math.random() < 0.5)
                    {
                        //No sobrepasar los limites superiores
                        if(j == 0)
                            numeroAleatorio = ( Math.random() * (12.1-individuoSeleccionadoCopia[i][j]));
                        else
                            numeroAleatorio = ( Math.random() * (5.8-individuoSeleccionadoCopia[i][j]));
                        individuoSeleccionadoCopia[i][j] = individuoSeleccionadoCopia[i][j]+numeroAleatorio;
                    }
                    else
                    {
                        //No bajar de los limites inferiores.
                        if(j == 0)
                            numeroAleatorio = ( Math.random() * (individuoSeleccionadoCopia[i][j] + 3) );
                        else
                            numeroAleatorio = ( Math.random() * (individuoSeleccionadoCopia[i][j] - 4.1));
                        individuoSeleccionadoCopia[i][j] = individuoSeleccionadoCopia[i][j]-numeroAleatorio;
                    }
                        
                    
                        
    		}
            }
    	}
 /******************************************************************************************
 *              
 * ELITISMO
 * (El mejor individuo lo paso a la siguiente generación para asegurar una la mejor solución)
 ******************************************************************************************/
        
        individuoSeleccionadoCopia[ind] = individuos[ind].clone();
        
/******************************************************************************************/
        
        individuos = individuoSeleccionadoCopia;

        t++;
        }while(t < generaciones);
    	
/******************************************************************************************
 *              
 * GENERACIÓN DEL GRÁFICO
 * 
 ******************************************************************************************/
   
        ventanaGrafica miventana = new ventanaGrafica(arreglo);
        miventana.setSize(900,900);
        miventana.show();
        
	}
}
