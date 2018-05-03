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
	
	public Sim_Plot(String title) throws FileNotFoundException, IOException {
		super(title);
		final XYSeriesCollection data = new XYSeriesCollection(null);
		for(int i = 1; i < 4; i++) {
			Simulador sim = new Simulador(i, 64, 100);
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
			data.addSeries(serie);
		}
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
		final Sim_Plot lower = new Sim_Plot("Plotted");
	    lower.pack();	
	    RefineryUtilities.centerFrameOnScreen(lower);
	    lower.setVisible(true);
	}

}
