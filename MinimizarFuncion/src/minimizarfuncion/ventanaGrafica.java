package minimizarfuncion;

import java.awt.image.BufferedImage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/*
 * ventanaGrafica.java
 *
 * Created on 9 de agosto de 2003, 9:11
 */

/**
 *
 * @author  Roberto Canales
 */
public class ventanaGrafica extends java.awt.Frame {

    BufferedImage grafica = null;
    double []arreglo;
    /** Creates new form ventanaGrafica */
    public ventanaGrafica(double [] arreglo) {
        this.arreglo = arreglo;
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        pack();
    }

    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {
        System.exit(0);
    }

    /**
     * @param args the command line arguments
     */
    

    public BufferedImage creaImagen()
    {
        XYSeries series = new XYSeries("Evaluacion de la funcion");
        
        for(int i = 0; i < arreglo.length; i++)
            series.add(i, arreglo[i]);
        XYDataset juegoDatos= new XYSeriesCollection(series);
        
        
        
        JFreeChart chart = ChartFactory.createXYLineChart("MINIMIZAR", "Generaciones","f(x,y)",  juegoDatos, PlotOrientation.VERTICAL, true, true, true);

         BufferedImage image = chart.createBufferedImage(800,800);
        return image;
    }

    public void paint(java.awt.Graphics g) {
        //super.paint(g);

        if(grafica == null)
        {
            grafica = this.creaImagen();
        }
        g.drawImage(grafica,30,30,null);
    }

}