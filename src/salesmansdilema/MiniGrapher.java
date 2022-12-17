package salesmansdilema;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DLtd;
import info.monitorenter.gui.chart.traces.Trace2DSimple;

public class MiniGrapher {

	Chart2D chart = null;
	ITrace2D trace = null;
	int cityCount = 0;
	Map map = null;

	public MiniGrapher(Map m) {
		map = m;
	}
		
	public void updateGraph(Solution sol) {
		if (chart == null) {
			
			chart = new Chart2D();
			
			trace = new Trace2DLtd(map.numberOfCities);
			ITrace2D sizeTrace = new Trace2DSimple();

			chart.addTrace(trace);
			chart.addTrace(sizeTrace);

			sizeTrace.addPoint(0, map.worldDimension);
			sizeTrace.addPoint(0, 0);
			sizeTrace.addPoint(map.worldDimension, 0);
			
			startGraphing();
		}
		
		
		for (int j=0; j<sol.cities.length; j++) {
			trace.addPoint(map.cities[sol.cities[j]].xPos, map.cities[sol.cities[j]].yPos);
//			System.out.println(j+": "+map.cities[sol.cities[j]].xPos+" "+map.cities[sol.cities[j]].yPos+" "+sol.length);
		}
	}
	
	private void startGraphing() {

		JFrame frame = new JFrame("miniGrapher");
			// add the chart to the frame: 
			
		frame.getContentPane().add(chart);
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
