package salesmansdilema;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JFrame;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DLtd;
import info.monitorenter.gui.chart.traces.Trace2DSimple;

public class Grapher {

	Chart2D[] charts = null;
	ITrace2D[] traces = null;
	ArrayList<TSTracker> trackers = null;
	int cityCount = 0;
	Map map = null;

	public Grapher(ArrayList<TSTracker> t, Map m) {
		trackers = t;
		map = m;
	}
		
	public void updateGraph() {
		if (charts == null) {
			
			traces = new ITrace2D[trackers.size()];
			charts = new Chart2D[trackers.size()];

			for (int i=0; i<trackers.size(); i++) {
				charts[i] = new Chart2D();
				
				traces[i] = new Trace2DLtd(map.numberOfCities);
				ITrace2D sizeTrace = new Trace2DSimple();

				charts[i].addTrace(traces[i]);
				charts[i].addTrace(sizeTrace);

				sizeTrace.addPoint(0, map.worldDimension);
				sizeTrace.addPoint(0, 0);
				sizeTrace.addPoint(map.worldDimension, 0);
				
			}
			
			startGraphing();
		}
		
		
		for (int i=0; i<trackers.size(); i++) {
			ITrace2D trace = traces[i];
			TSTracker track = trackers.get(i);
			
			int[] solCities = track.tsSol.cities;
			//trace.removeAllPoints();
			//trace.setName(track.tsp.description());

			for (int j=0; j<solCities.length; j++) {
				trace.addPoint(map.cities[solCities[j]].xPos, map.cities[solCities[j]].yPos);
			}
		}
	}
	
	private void startGraphing() {
		// // Create a chart:  
		// chart = new Chart2D();
		// // Create an ITrace: 
		// ITrace2D trace = new Trace2DSimple(); 
		// // Add the trace to the chart. This has to be done before adding points (deadlock prevention): 
		// chart.addTrace(trace);    
		// // Add all points, as it is static: 
		// Random random = new Random();
		// for(int i=100;i>=0;i--){
		// 	trace.addPoint(i,random.nextDouble()*10.0+i);
		// }
		// Make it visible:
		// Create a frame.

		int i=0;
		for (TSTracker t: trackers) {
			JFrame frame = new JFrame(t.tsp.description());
			// add the chart to the frame: 
			
			frame.getContentPane().add(charts[i++]);
			frame.setSize(600,600);
			// Enable the termination button [cross on the upper right edge]: 
			frame.addWindowListener(
					new WindowAdapter(){
						public void windowClosing(WindowEvent e){
							System.exit(0);
						}
					}
					);
			frame.setVisible(true);
		}
	}
}

