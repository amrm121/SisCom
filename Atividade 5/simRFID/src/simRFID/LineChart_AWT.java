package simRFID;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class LineChart_AWT extends ApplicationFrame {
	int sel =4; //1 pra slots total, 2 pra slotsvazios, 3 pra slots colisão, 4 tempo, 5 personalizado
   public LineChart_AWT( String applicationTitle , String chartTitle ) {
      super(applicationTitle);
      JFreeChart lineChart = ChartFactory.createLineChart(
         chartTitle,
         "Número total de Slots","Slots",
         createDataset(),
         PlotOrientation.VERTICAL,
         true,true,false);
         
      ChartPanel chartPanel = new ChartPanel( lineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
      setContentPane( chartPanel );
   }

   private DefaultCategoryDataset createDataset( ) {
      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
      if (sel ==1){
      // Slot total
	      dataset.addValue( 275 , "Schoute" , "100" );
	      dataset.addValue( 593 , "Schoute" , "200" );
	      dataset.addValue( 913 , "Schoute" , "300" );
	      dataset.addValue( 1232 , "Schoute" , "400" );
	      dataset.addValue( 1553 , "Schoute" , "500" );
	      dataset.addValue( 1876 , "Schoute" , "600" );
	      dataset.addValue( 2199 , "Schoute" , "700" );
	      dataset.addValue( 2520 , "Schoute" , "800" );
	      dataset.addValue( 2840 , "Schoute" , "900" );
	      dataset.addValue( 3164 , "Schoute" , "1000" );
	      dataset.addValue( 281 , "lowerBound" , "100" );
	      dataset.addValue( 616 , "lowerBound" , "200" );
	      dataset.addValue( 958 , "lowerBound" , "300" );
	      dataset.addValue( 1302 , "lowerBound" , "400" );
	      dataset.addValue( 1644 , "lowerBound" , "500" );
	      dataset.addValue( 1984 , "lowerBound" , "600" );
	      dataset.addValue( 2329 , "lowerBound" , "700" );
	      dataset.addValue( 2672 , "lowerBound" , "800" );
	      dataset.addValue( 3014 , "lowerBound" , "900" );
	      dataset.addValue( 3356 , "lowerBound" , "1000" );
	      dataset.addValue( 274 , "Eom Lee" , "100" );
	      dataset.addValue( 581 , "Eom Lee" , "200" );
	      dataset.addValue( 874 , "Eom Lee" , "300" );
	      dataset.addValue( 1194 , "Eom Lee" , "400" );
	      dataset.addValue( 1462 , "Eom Lee" , "500" );
	      dataset.addValue( 1709 , "Eom Lee" , "600" );
	      dataset.addValue( 1965 , "Eom Lee" , "700" );
	      dataset.addValue( 2233 , "Eom Lee" , "800" );
	      dataset.addValue( 2517 , "Eom Lee" , "900" );
	      dataset.addValue( 2807 , "Eom Lee" , "1000" );   
      }   else if (sel == 2){
      //Slots Vazios
	      dataset.addValue( 84 , "Schoute" , "100" );
	      dataset.addValue( 169 , "Schoute" , "200" );
	      dataset.addValue( 255 , "Schoute" , "300" );
	      dataset.addValue( 340 , "Schoute" , "400" );
	      dataset.addValue( 427 , "Schoute" , "500" );
	      dataset.addValue( 514 , "Schoute" , "600" );
	      dataset.addValue( 602 , "Schoute" , "700" );
	      dataset.addValue( 689 , "Schoute" , "800" );
	      dataset.addValue( 775 , "Schoute" , "900" );
	      dataset.addValue( 863 , "Schoute" , "1000" );
	      dataset.addValue( 72 , "lowerBound" , "100" );
	      dataset.addValue( 140 , "lowerBound" , "200" );
	      dataset.addValue( 211 , "lowerBound" , "300" );
	      dataset.addValue( 283 , "lowerBound" , "400" );
	      dataset.addValue( 354 , "lowerBound" , "500" );
	      dataset.addValue( 424 , "lowerBound" , "600" );
	      dataset.addValue( 496 , "lowerBound" , "700" );
	      dataset.addValue( 568 , "lowerBound" , "800" );
	      dataset.addValue( 639 , "lowerBound" , "900" );
	      dataset.addValue( 710 , "lowerBound" , "1000" );
	      dataset.addValue( 92 , "Eom Lee" , "100" );
	      dataset.addValue( 195 , "Eom Lee" , "200" );
	      dataset.addValue( 308 , "Eom Lee" , "300" );
	      dataset.addValue( 479 , "Eom Lee" , "400" );
	      dataset.addValue( 598 , "Eom Lee" , "500" );
	      dataset.addValue( 666 , "Eom Lee" , "600" );
	      dataset.addValue( 722 , "Eom Lee" , "700" );
	      dataset.addValue( 784 , "Eom Lee" , "800" );
	      dataset.addValue( 856 , "Eom Lee" , "900" );
	      dataset.addValue( 934 , "Eom Lee" , "1000" ); 
      } else if (sel == 3){
      //Slots colisão
	      dataset.addValue( 90 , "Schoute" , "100" );
	      dataset.addValue( 224 , "Schoute" , "200" );
	      dataset.addValue( 358 , "Schoute" , "300" );
	      dataset.addValue( 492 , "Schoute" , "400" );
	      dataset.addValue( 626 , "Schoute" , "500" );
	      dataset.addValue( 762 , "Schoute" , "600" );
	      dataset.addValue( 897 , "Schoute" , "700" );
	      dataset.addValue( 1031 , "Schoute" , "800" );
	      dataset.addValue( 1165 , "Schoute" , "900" );
	      dataset.addValue( 1301 , "Schoute" , "1000" );
	      dataset.addValue( 108 , "lowerBound" , "100" );
	      dataset.addValue( 276 , "lowerBound" , "200" );
	      dataset.addValue( 447 , "lowerBound" , "300" );
	      dataset.addValue( 619 , "lowerBound" , "400" );
	      dataset.addValue( 790 , "lowerBound" , "500" );
	      dataset.addValue( 960 , "lowerBound" , "600" );
	      dataset.addValue( 1132 , "lowerBound" , "700" );
	      dataset.addValue( 1304 , "lowerBound" , "800" );
	      dataset.addValue( 1475 , "lowerBound" , "900" );
	      dataset.addValue( 1646 , "lowerBound" , "1000" );
	      dataset.addValue( 82 , "Eom Lee" , "100" );
	      dataset.addValue( 185 , "Eom Lee" , "200" );
	      dataset.addValue( 265 , "Eom Lee" , "300" );
	      dataset.addValue( 314 , "Eom Lee" , "400" );
	      dataset.addValue( 364 , "Eom Lee" , "500" );
	      dataset.addValue( 442 , "Eom Lee" , "600" );
	      dataset.addValue( 542 , "Eom Lee" , "700" );
	      dataset.addValue( 649 , "Eom Lee" , "800" );
	      dataset.addValue( 760 , "Eom Lee" , "900" );
	      dataset.addValue( 872 , "Eom Lee" , "1000" ); 
      } else if (sel == 4){
      //Tempo
	      dataset.addValue( 42 , "Schoute" , "100" );
	      dataset.addValue( 68 , "Schoute" , "200" );
	      dataset.addValue( 93 , "Schoute" , "300" );
	      dataset.addValue( 138 , "Schoute" , "400" );
	      dataset.addValue( 156 , "Schoute" , "500" );
	      dataset.addValue( 189 , "Schoute" , "600" );
	      dataset.addValue( 229 , "Schoute" , "700" );
	      dataset.addValue( 263 , "Schoute" , "800" );
	      dataset.addValue( 305 , "Schoute" , "900" );
	      dataset.addValue( 342 , "Schoute" , "1000" );
	      dataset.addValue( 38 , "lowerBound" , "100" );
	      dataset.addValue( 67 , "lowerBound" , "200" );
	      dataset.addValue( 119 , "lowerBound" , "300" );
	      dataset.addValue( 161 , "lowerBound" , "400" );
	      dataset.addValue( 194 , "lowerBound" , "500" );
	      dataset.addValue( 227 , "lowerBound" , "600" );
	      dataset.addValue( 268 , "lowerBound" , "700" );
	      dataset.addValue( 316 , "lowerBound" , "800" );
	      dataset.addValue( 373 , "lowerBound" , "900" );
	      dataset.addValue( 415 , "lowerBound" , "1000" );
	      dataset.addValue( 47 , "Eom Lee" , "100" );
	      dataset.addValue( 66 , "Eom Lee" , "200" );
	      dataset.addValue( 95 , "Eom Lee" , "300" );
	      dataset.addValue( 229 , "Eom Lee" , "400" );
	      dataset.addValue( 348 , "Eom Lee" , "500" );
	      dataset.addValue( 401 , "Eom Lee" , "600" );
	      dataset.addValue( 439 , "Eom Lee" , "700" );
	      dataset.addValue( 470 , "Eom Lee" , "800" );
	      dataset.addValue( 493 , "Eom Lee" , "900" );
	      dataset.addValue( 532 , "Eom Lee" , "1000" ); 
      } else if (sel == 5){
          //personalizado
          dataset.addValue( 0 , "Personalizado" , "100" );
          dataset.addValue( 0 , "Personalizado" , "200" );
          dataset.addValue( 0 , "Personalizado" , "300" );
          dataset.addValue( 0 , "Personalizado" , "400" );
          dataset.addValue( 0 , "Personalizado" , "500" );
          dataset.addValue( 0 , "Personalizado" , "600" );
          dataset.addValue( 0 , "Personalizado" , "700" );
          dataset.addValue( 0 , "Personalizado" , "800" );
          dataset.addValue( 0 , "Personalizado" , "900" );
          dataset.addValue( 0 , "Personalizado" , "1000" );
          }
      return dataset;
   }
}