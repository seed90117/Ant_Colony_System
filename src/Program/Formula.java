package Program;

import java.util.ArrayList;
import java.util.Random;

import Values.Data;
import Values.Parameter;

public class Formula {
	
	private Data data = Data.getInstance();
	private Parameter parameter = Parameter.getInstance();
	
	// 轉換機率
	public int transformProbability(ArrayList<Integer> ant, double[][] linePheromone) {
		if (linePheromone == null)
			System.out.println("Stop");
		int size = this.data.total;
		int lastCity = ant.get(ant.size()-1);
		double total = 0;
		double[] rate = new double[size];
		// 計算線段機率
		for (int i=0; i<size; i++) {
			if (!ant.contains(i)) {
				double roll = transform(linePheromone[lastCity][i]);
				total += roll;
				rate[i] = roll;
			}
		}
		
		// 選擇路線
		Random random = new Random();
		int point = 0;
		double transformRate = random.nextDouble();
		for (int i=0; i<size; i++) {
			transformRate -= rate[i]/total;
			if (transformRate <= 0) {
				point = i;
				break;
			}
		}
		return point;
	}
	
	// 轉換規則
	public int transformRoll(ArrayList<Integer> ant, double[][] linePheromone) {
		int size = this.data.total;
		int lastCity = ant.get(ant.size()-1);
		int max = -1;
		double maxRoll = 0;
		for (int i=0; i<size; i++) {
			if (!ant.contains(i)) {
				double roll = transform(linePheromone[lastCity][i]);
				if (max == -1 || roll > maxRoll) {
					max = i;
					maxRoll = roll;
				}
			}
		}
		return max;
	}
	
	// 轉換公式
	private double transform(double linePheromone) {
		return Math.pow(linePheromone, this.parameter.getAlpha()) * 
				Math.pow(linePheromone, this.parameter.getBeta());
	}
	
	// 費洛蒙全域更新
	public double globalUpdatePheromone(double linePheromone, double totaldistance) {
		int Q = 100;
		double tau = Q/totaldistance;
		if (totaldistance == -1) {
			tau = 0;
		}
		return ((1-this.parameter.getRho()) * linePheromone) + (this.parameter.getRho()*tau);
	}
	
	// 費洛蒙局部更新
	public double partUpdatePheromone(double linePheromone) {
		double tau = this.parameter.getPheromone();
		return ((1-this.parameter.getRho()) * linePheromone) + (this.parameter.getRho()*tau);
	}
}
