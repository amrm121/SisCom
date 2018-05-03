package simRFID;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartTheme;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class Sim_Plot extends ApplicationFrame{
	
	public Sim_Plot(String title, int k) throws FileNotFoundException, IOException {
		super(title);
		final XYSeriesCollection data = new XYSeriesCollection(null);
		String type = null;
		for(int i = 1; i < 4; i++) {
			Simulador sim = new Simulador(i, 64, 100);
			sim.Simulation();
			final XYSeries serie = new XYSeries(sim.getAlgo().substring(0, sim.getAlgo().indexOf(".")));
			FileReader fr = new FileReader(sim.getAlgo());
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			while((line = br.readLine()) != null) {
				int tags = Integer.parseInt(line.substring(line.indexOf("T")+2, line.indexOf(" ")));
				line = line.substring(line.indexOf(" ")+1);
				int slots = Integer.parseInt(line.substring(line.indexOf("S")+2, line.indexOf(" "))); //k = 1
				line = line.substring(line.indexOf(" ")+1);
				int vazio = Integer.parseInt(line.substring(line.indexOf("V")+2, line.indexOf(" "))); //k = 2
				line = line.substring(line.indexOf(" ")+1);
				int collis = Integer.parseInt(line.substring(line.indexOf("C")+2, line.indexOf(" ")));//k = 3
				line = line.substring(line.indexOf(" ")+1);
				double time = Double.parseDouble(line.substring(line.indexOf("t")+2));//k = 4
				switch(k) {
				case 1:
					serie.add(tags, slots);
					if(type == null) type = "Num. de Slots";
					break;
				case 2:
					serie.add(tags, vazio);
					if(type == null)type = "Num. de Slots Vazios";
					break;
				case 3:
					serie.add(tags, collis);
					if(type == null)type = "Num. de Colisoes";
					break;
				case 4:
					serie.add(tags, time);
					if(type == null)type = "Tempo de Simulacao";
					break;
				}
			}
			br.close();
			data.addSeries(serie);
		}
		final JFreeChart chart = ChartFactory.createXYLineChart(
		        "",
		        "Etiquetas", 
		        type,
		        data,
		        PlotOrientation.VERTICAL,
		        true,
		        true,
		        false
		);
		final ChartPanel chartPanel = new ChartPanel(chart);
	    chartPanel.setPreferredSize(new java.awt.Dimension(600, 300));
	    setContentPane(chartPanel);
	}

	public Sim_Plot(String title, int algo, int i_frame, int incr) throws FileNotFoundException, IOException {
		super(title);
		Simulador sim = new Simulador(algo, i_frame, incr);
		sim.Simulation();
		final XYSeries serie = new XYSeries(sim.getAlgo());
		FileReader fr = new FileReader(sim.getAlgo());
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		while((line = br.readLine()) != null) {
			int tags = Integer.parseInt(line.substring(line.indexOf("T")+2, line.indexOf(" ")));
			line = line.substring(line.indexOf(" ")+1);
			int slots = Integer.parseInt(line.substring(line.indexOf("S")+2, line.indexOf(" ")));
			serie.add(tags, slots);
		}
		br.close();
		final XYSeriesCollection data = new XYSeriesCollection(serie);
		final JFreeChart chart = ChartFactory.createXYLineChart(
		        "",
		        "Etiquetas", 
		        "Slots",
		        data,
		        PlotOrientation.VERTICAL,
		        true,
		        true,
		        false
		);
		final ChartPanel chartPanel = new ChartPanel(chart);
	    chartPanel.setPreferredSize(new java.awt.Dimension(500, 300));
	    setContentPane(chartPanel);
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		final Sim_Plot lower = new Sim_Plot("Grafico", 3); //1 = SlotsPerFrame / 2 = SlotsVazios / 3 = SlotsEmColisao / 4 = TempoDeSim
	    lower.pack();	
	    RefineryUtilities.centerFrameOnScreen(lower);
	    lower.setVisible(true);
	}

}
