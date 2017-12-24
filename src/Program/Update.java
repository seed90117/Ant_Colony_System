package Program;

import java.util.ArrayList;

public class Update {
	
	Formula formula = new Formula();
	
	public double[][] globalUpdate(ArrayList<Integer> ant, double distance, double[][] linePheromone) {
		double[][] newPheromone = linePheromone;
		int size = linePheromone.length;
		for (int i=0; i<size-1; i++) {
			for (int j=i+1; j<size; j++) {
				int position = ant.indexOf(i)+1;
				if (ant.get(position) == j) {
					newPheromone[i][j] = formula.globalUpdatePheromone(linePheromone[i][j], distance);
				} else {
					newPheromone[i][j] = formula.globalUpdatePheromone(linePheromone[i][j], -1);
				}
				newPheromone[j][i] = newPheromone[i][j];
			}
		}
		return newPheromone;
	}
	
	public double[][] partUpdate(double[][] linePheromone, int pointA, int pointB) {
		double[][] newPheromone = linePheromone;
		newPheromone[pointA][pointB] = formula.partUpdatePheromone(linePheromone[pointA][pointB]);
		newPheromone[pointB][pointA] = newPheromone[pointA][pointB];
		return newPheromone;
	}
}
