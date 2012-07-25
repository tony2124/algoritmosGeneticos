/**
 * @(#)Viajero.java
 *
 *
 * @author: 	
 *				FRANCISCO JAVIER CALDERÓN CHÁVEZ
 *				ALFONSO CALDERÓN CHÁVEZ
 *
 * @Institutos:
 *				INSTITUTO TECNOLÓGICO SUPERIOR DE APATZINGÁN
 *				UNIVERSIDAD AUTONOMA DE MÉXICO
 *				INSTITUTO INVESTIGACIÓN DE MATEMATICAS APLICADAS Y SISTEMAS
 */


import java.util.Scanner;
import java.text.DecimalFormat;
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
    
    static int generarNumero(int lsup){
    	return (int)(Math.random()*lsup);
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
    	/*Incialización de un objeto de la clase Scanner*/
    	Scanner read = new Scanner(System.in);
    	
    	/*Incialización de un objeto de la clase DecimalFormat*/
    	DecimalFormat decimal = new DecimalFormat("0.0000");
    	
    	/****************************************************************
    	 *		DECLARACIÓN DE VARIABLES
    	 ****************************************************************/
		int nodo, nIndividuos, startCity, ind, t, generations, mayor, numero, 
			tamIndividuo, lsup, linf, numeroGenerado,Individuos[][]/*, distanceMatrix[][]*/;
		double Pm, Pc, min, F, aux, num, distanceTotal[], arreglo[];
		boolean flag=false;
		
		System.out.println("Número de ciudades: ");
		nodo = read.nextInt();
		System.out.println("De que ciudad iniciar: ");
		startCity = read.nextInt();
		System.out.println("Individuos: ");
		nIndividuos = read.nextInt();
		System.out.println("Cruza: ");
		Pc = read.nextDouble();
		System.out.println("Mutación: ");
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
		}*/

/**********************************************************************************
 *
 *			GENERACIÓN DE POBLACIÓN
 *
 **********************************************************************************/									
		Individuos = new int [nIndividuos][tamIndividuo];
		
		for(int i = 0; i<nIndividuos; i++){
			for(int j = 0; j<tamIndividuo; ){
				if(j==0){
					Individuos[i][j] = startCity;
					j++;
				}else{
					num = (int) generarNumero(lsup);
					flag=false;
					
					for(int verifica = 0; verifica < j; verifica++)
						if(Individuos[i][verifica] == num) { 
							flag = true; verifica = j; 
						}
						
					if(!flag){
						Individuos[i][j] = (int)num;		
						j++;						
					}
				}					
			}
		}
			
		imprimir(Individuos);
		t=0; mayor=0;
		arreglo = new double[generations];
		
	do{
		ind = 0;
		min = 9000.0;
/**********************************************************************************
 *
 *		EVALUACIÓN DE LA FUNCIÓN
 *
 *********************************************************************************/
		F = 0.0;
		distanceTotal = new double [nIndividuos];
		for(int i=0; i<nIndividuos; i++){
			
			distanceTotal[i] = evaluarFuncion(Individuos[i], distanceMatrix);
			System.out.println("Distancia individuo "+i+": "+distanceTotal[i]);
			
			if(distanceTotal[i] < min){
            	min = distanceTotal[i];
                ind = i;   
            }
            
            distanceTotal[i] = 50000.0 - distanceTotal[i];
            F += distanceTotal[i];
		}
		System.out.println("El mímino en la generación "+t+" es: "+min);
        imprimir(Individuos[ind]);
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
 * 		SELECCIÓN
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
     
    /*System.out.println("Nuevos individuos con cruza");
    imprimir( individuoSeleccionadoCopia );*/
    
 /******************************************************************************************
 *              
 * MUTACION
 * 
 ******************************************************************************************/
		System.out.println("Mutación");
		int x;
	    for(int i = 0; i < nIndividuos; i++){
	     	for(int j = 0; j < tamIndividuo; j++){
				numeroAleatorio = Math.random();
	     		if(numeroAleatorio <= Pm)
	     		{
	     			numero = generarNumero(tamIndividuo);
	     			numeroGenerado = generarNumero(tamIndividuo);
	     			if(numero == numeroGenerado){
						do{
	     					numeroGenerado = generarNumero(tamIndividuo);
	     				}while(numero == numeroGenerado);	     				
	     			}
	     			
	     			x = individuoSeleccionadoCopia[i][numero];
	     			individuoSeleccionadoCopia[i][numero] = individuoSeleccionadoCopia[i][numeroGenerado];
	     			individuoSeleccionadoCopia[i][numeroGenerado] = x;		
	     		}
			}
	     }
	    	
    	System.out.println();
    	
    	
        
  /******************************************************************************************
 *              
 * ELITISMO
 * 
 ******************************************************************************************/
 
        individuoSeleccionadoCopia[ind] = Individuos[ind].clone();
        
        /****************/
        
        Individuos = individuoSeleccionadoCopia;
        
        System.out.println("Nueva población de generación: "+t);
        imprimir(Individuos);
        
        t++;
        System.out.println("fin");
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