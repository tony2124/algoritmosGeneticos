/**
 * @(#)Viajero.java
 *
 *
 * @author: 	
 *				FRANCISCO JAVIER CALDER�N CH�VEZ
 *				ALFONSO CALDER�N CH�VEZ
 *
 * @Institutos:
 *				INSTITUTO TECNOL�GICO SUPERIOR DE APATZING�N
 *				UNIVERSIDAD AUTONOMA DE M�XICO
 *				INSTITUTO INVESTIGACI�N DE MATEMATICAS APLICADAS Y SISTEMAS
 */


import java.util.Scanner;
import java.text.DecimalFormat;
import java.util.Arrays;
public class Viajero {

    public Viajero() {
    }
    
    static int evaluarFuncion(int individuo[],int m[][]){
    	int distancia=0;
    	
    	for(int i=0; i<(individuo.length-1); i++){
    		distancia += m[individuo[i]][individuo[i+1]]; 
    	}
    	distancia += m[individuo[individuo.length-1]][individuo[0]];
    	
    	return distancia;
    }
    
    static double generarNumero(int lsup){
    	return Math.random()*lsup;
    }
    
    
    static void imprimir(double [][]m)
    {
    	for(int i = 0; i < m.length; i++)
    	{   
            System.out.print("Individuo "+i+": ");
            for(int j = 0; j < m[i].length; j++)		
                    System.out.print (m[i][j]+", ");
            System.out.println();
    	}
    }
    
    static void imprimir(double []m)
    {
    	for(int i = 0; i < m.length; i++)
    	{   
           System.out.print(m[i]+", ");
    	}
    	
    	System.out.println();
    }
    
     static void imprimir(int [][]m)
    {
    	for(int i = 0; i < m.length; i++)
    	{   
            System.out.print("Individuo "+i+": ");
            for(int j = 0; j < m[i].length; j++)		
                    System.out.print (m[i][j]+", ");
            System.out.println();
    	}
    }
    
    static void imprimir(int []m)
    {
    	for(int i = 0; i < m.length; i++)
    	{   
           System.out.print(m[i]+", ");
    	}
    	
    	System.out.println();
    }
    
    public static void main(String [] args){
    	/*Incializacion de un objeto de la clase Scanner*/
    	Scanner read = new Scanner(System.in);
    	
    	/*Incializacion de un objeto de la clase DecimalFormat*/
    	DecimalFormat decimal = new DecimalFormat("0.0000");
    	
    	/****************************************************************
    	 *		DECLARACION DE VARIABLES
    	 ****************************************************************/
		int nodo, nIndividuos, ind, t, generations, mayor, tamIndividuo, lsup, linf, Individuos[][]/*, distanceMatrix[][]*/;
		double Pm, numero, numeroGenerado, Pc, min, F, aux, num, distanceTotal[], arreglo[], fenotipo[][], fenotipoCopia[][];
		boolean flag=false;
		
		System.out.println("Numero de ciudades: ");
		nodo = read.nextInt();
		System.out.println("Individuos: ");
		nIndividuos = read.nextInt();
		System.out.println("Cruza: ");
		Pc = read.nextDouble();
		System.out.println("Mutacion: ");
		Pm = read.nextDouble();
		System.out.println("Generaciones: ");
		generations = read.nextInt();
		
		lsup=nodo;
		linf=0;
		tamIndividuo = nodo;
		
		int distanceMatrix[][] = {	{0,10,500,500,500,500,500,500,500,500},
									{500,0,20,500,500,500,500,500,500,500},
									{500,500,0,30,500,500,500,500,500,500},
									{500,500,500,0,40,500,500,500,500,500},
									{500,500,500,500,0,50,500,500,500,500},
									{500,500,500,500,500,0,60,500,500,500},
									{500,500,500,500,500,500,0,70,500,500},
									{500,500,500,500,500,500,500,0,80,500},
									{500,500,500,500,500,500,500,500,0,90},
									{500,500,500,500,500,500,500,500,500,0},	};
									
		/*distanceMatrix = new int[nodo][nodo];
									
		for(int i =0; i<nodo; i++){
			for(int j=1; j<nodo; j++){
				if(j!=i){
					numeroGenerado = (int)(Math.random()*10);
					distanceMatrix[i][j] = numeroGenerado;
					distanceMatrix[j][i] = numeroGenerado;
				}
			}
		}
		
		System.out.println("Matriz de distancias: ");
		imprimir(distanceMatrix);
		System.out.println("\n\n");*/
/**********************************************************************************
 *
 *			GENERACION DE POBLACION
 *
 **********************************************************************************/									
		Individuos = new int [nIndividuos][tamIndividuo];
		fenotipo = new double [nIndividuos][tamIndividuo];
		fenotipoCopia = new double [nIndividuos][tamIndividuo];
		
		for(int i = 0; i<nIndividuos; i++){
			for(int j = 0; j<tamIndividuo; ){
				num = generarNumero(lsup);
				flag=false;
				
				for(int verifica = 0; verifica < j; verifica++)
					if(Individuos[i][verifica] == num) { 
						flag = true; verifica = j; 
					}
						
					if(!flag){
						fenotipo[i][j] = Double.parseDouble(decimal.format(num));
						fenotipoCopia[i][j]=fenotipo[i][j];
						j++;						
					}					
			}
		}
		

		for(int i=0; i<nIndividuos; i++){
				Arrays.sort(fenotipoCopia[i]);
		}
		
	/*	imprimir(fenotipoCopia);
		System.out.println("\n\n");
		imprimir(fenotipo);*/
		
		for(int i=0; i<nIndividuos; i++){
			for(int j=0; j<tamIndividuo; j++){
				num = fenotipoCopia[i][j];
				for(int k=0; k<tamIndividuo; k++){
					if(num == fenotipo[i][k]){
						Individuos[i][j]=k;
					}
				}
			}
		}
		
	
	//	imprimir(Individuos);
		t=0; mayor=0;
		arreglo = new double[generations];
		
			
	do{
		imprimir(Individuos);
		ind = 0;
		min = 9000.0;
/**********************************************************************************
 *
 *		EVALUACION DE LA FUNCION
 *
 *********************************************************************************/
		F = 0.0;
		distanceTotal = new double [nIndividuos];
		for(int i=0; i<nIndividuos; i++){
			
			distanceTotal[i] = evaluarFuncion(Individuos[i], distanceMatrix);
		//	System.out.println("Distancia individuo "+i+": "+distanceTotal[i]);
			
			if(distanceTotal[i] < min){
            	min = distanceTotal[i];
                ind = i;   
            }
            
            distanceTotal[i] = 50000.0 - distanceTotal[i];
            F += distanceTotal[i];
		}
		
        //System.out.println();
        arreglo[mayor++] = min;
		
/******************************************************************************************
 *              
 * 		CALCULO DE LA APTITUD RELATIVA Y ACUMULADA
 * 
 ******************************************************************************************/
 		aux = 0.0;
 		for(int i=0; i<nIndividuos; i++){
 			distanceTotal[i] = Double.parseDouble(decimal.format(distanceTotal[i]/F + aux)); 
    		aux = distanceTotal[i];
 		}
 
 /******************************************************************************************
 *              
 * 		SELECCION
 * 
 ******************************************************************************************/
 		double numeroAleatorio;
        
    	int [][] individuoSeleccionado = new int[nIndividuos][tamIndividuo], individuoSeleccionadoCopia = new int[nIndividuos][tamIndividuo];
    	int cont = 0;
    	for(int i = 0; i < nIndividuos; i++)
    	{
    		numeroAleatorio = Math.random();
    		for(int j = 0; j < nIndividuos; j++)
    		{
    			if(distanceTotal[j] > numeroAleatorio)
    			{
                            for(int pass = 0; pass < tamIndividuo; pass++)
                            {
                                individuoSeleccionado[cont][pass] = Individuos[j][pass];
                                individuoSeleccionadoCopia[cont][pass] = Individuos[j][pass];
                            }
                            cont++;
                            j=nIndividuos;
    			}
    		}
    	}
    	
    /*	System.out.println("Seleccion: ");
    	
    	imprimir(individuoSeleccionado);*/
       
 /******************************************************************************************
 *              
 * 				CRUZA
 * 
 ******************************************************************************************/    
     //Aplicando la cruza a los individuos seleccionados (1 x 2 y 3 x 4)
     int [] indices = new int[nIndividuos];
     cont = 0;
     for(int i = 0; i < nIndividuos; i++)
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
       
     for(int i = 0, j = 0; i < cont / 2; i++, j+=2)
     {
        num = (int) ((Math.random()*1000) % tamIndividuo);
       // System.out.println("Numero seleccionado: "+num);
        //Se realiza el intercambio entre el primer individuo y el segundo
        for(int pass = (int)num; pass < tamIndividuo; pass++)
        {
            individuoSeleccionadoCopia[indices[j]][pass] = individuoSeleccionado[indices[j+1]][pass];
            individuoSeleccionadoCopia[indices[j+1]][pass] = individuoSeleccionado[indices[j]][pass];
        }
        
        //MAPEO H1 
        for(int pass = 0; pass < num; pass++)
        {
            boolean existe = false;
            for(int comp = (int)num; comp < tamIndividuo; comp++)
            {
                if(individuoSeleccionadoCopia[indices[j]][pass] == individuoSeleccionadoCopia[indices[j]][comp])
                {
                    existe = true;
                  //  System.out.println(individuoSeleccionadoCopia[indices[j]][pass]+" = "+individuoSeleccionadoCopia[indices[j]][comp]);
                    comp = tamIndividuo;
                }
            }
            
            if(existe)
            {
                do
                {
                    numero = (int)generarNumero(lsup);
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
                        individuoSeleccionadoCopia[indices[j]][pass] = (int)numero;
                    }
                }while(existe);
            }
        }
       
        //MAPEO H2
        for(int pass = 0; pass < num; pass++)
        {
            boolean existe = false;
            for(int comp = (int)num; comp < tamIndividuo; comp++)
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
                    numero = (int)generarNumero(lsup);
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
                        individuoSeleccionadoCopia[indices[j+1]][pass] = (int)numero;
                    }
                }while(existe);
            }
                
        }
          
     }
     
    /*System.out.println("Nuevos individuos con cruza");
    imprimir( individuoSeleccionadoCopia );*/
    
 /******************************************************************************************
 *              
 * MUTACION
 * 
 ******************************************************************************************/
	//	System.out.println("Mutacion");
		int x;
	    for(int i = 0; i < nIndividuos; i++){
	     	for(int j = 0; j < tamIndividuo; j++){
				numeroAleatorio = Math.random();
	     		if(numeroAleatorio <= Pm)
	     		{
	     			numero = (int)generarNumero(tamIndividuo);
	     			numeroGenerado = (int)generarNumero(tamIndividuo);
	     			if(numero == numeroGenerado){
						do{
	     					numeroGenerado = generarNumero(tamIndividuo);
	     				}while(numero == numeroGenerado);	     				
	     			}
	     			
	     			x = individuoSeleccionadoCopia[i][(int)numero];
	     			individuoSeleccionadoCopia[i][(int)numero] = individuoSeleccionadoCopia[i][(int)numeroGenerado];
	     			individuoSeleccionadoCopia[i][(int)numeroGenerado] = x;		
	     		}
			}
	     }
	    	
    	//System.out.println();
    	
    	
        
  /******************************************************************************************
 *              
 * ELITISMO
 * 
 ******************************************************************************************/
 
        individuoSeleccionadoCopia[ind] = Individuos[ind].clone();
        
        /****************/
        
        Individuos = individuoSeleccionadoCopia;
        
       /* System.out.println("Nueva poblacion de generacion: "+t);
        imprimir(Individuos);*/
        
        t++;
        //System.out.println("fin");
        System.out.println("El mimino en la generacion "+t+" es: "+min);
        imprimir(Individuos[ind]);
        /*if(min == 0.0)
        	t = generations;*/
      }while(t < generations);
		
		System.out.println("Matriz de distancias: ");
		imprimir(distanceMatrix);
		System.out.println("\n\n");
    	
       	ventanaGrafica miventana = new ventanaGrafica(arreglo);
        miventana.setSize(500,500);
        miventana.show();	
    
	}

}