package Program;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import Values.BestSolution;
import Values.Data;
import Values.Parameter;

public class MainMethod {
	
	private boolean isInteger = false;
	
	private Data data = Data.getInstance();
	private Parameter parameter = Parameter.getInstance();
	private Optimization optimization = null;
	private Update update = null;
	
	private ArrayList<Integer>[] ants = null;
	private double[] distance = null;
	private double[][] lineDistance = null;
	private double[][] linePheromone = null;
	
	// 演算法主程式
	public BestSolution mainProgram(boolean isInteger) {
		BestSolution bestSolution = new BestSolution();
		this.isInteger = isInteger;
		this.update = new Update();
		this.optimization = new Optimization();
		// 初始所有線段費洛蒙
		initialAllLine();
		for (int g=0; g<parameter.getGeneration(); g++) {
			// 初始螞蟻
			initialAnt();
			// 建構螞蟻路徑
			antCreatePath();
			// 二元優化所有螞蟻路徑
			bestSolution = optimizationPath();
			// 全域更新
			this.linePheromone = this.update.globalUpdate(bestSolution.solution, bestSolution.distance, this.linePheromone);
		}
		return bestSolution;
	}
	// 初始螞蟻
	@SuppressWarnings("unchecked")
	private void initialAnt() {
		this.ants = new ArrayList[this.parameter.getAntAmount()];
		this.distance = new double[this.parameter.getAntAmount()];
	}
	
	// 初始所有線段費洛蒙與計算距離
	private void initialAllLine() {
		int size = this.data.total;
		double initialPheromone = this.parameter.getPheromone();
		this.linePheromone = new double[size][size];
		this.lineDistance = new double[size][size];
		for (int i=0; i<size; i++) {
			for (int j=0; j<size; j++) {
				if (i==j) {
					this.linePheromone[i][j] = 0;
					this.lineDistance[i][j] = 0;
				} else {
					this.linePheromone[i][j] = initialPheromone;
					this.lineDistance[i][j] = calculationDistance(i, j);
				}
			}
		}
	}
	
	//螞蟻產生路徑
	private void antCreatePath() {
		int antAmount = this.parameter.getAntAmount();
		int cityAmount = this.data.total;
		Random random = new Random();
		for (int city=0; city<=cityAmount; city++) {
			for (int ant=0; ant<antAmount; ant++) {
				if (city==0) {
					this.ants[ant] = new ArrayList<Integer>();
					this.ants[ant].add(random.nextInt(cityAmount));
				} else {
					// 尋找下一個點
					this.ants[ant].add(nextCity(ant, city));
					// 計算距離並記錄
					this.distance[ant] += this.lineDistance[this.ants[ant].get(city)][this.ants[ant].get(city-1)];
					// 局部更新費洛蒙
					this.linePheromone = update.partUpdate(this.linePheromone, 
															this.ants[ant].get(city-1), 
															this.ants[ant].get(city));
				}
			}
		}
	}
	
	// 尋找下一個點
	private int nextCity(int antCount, int cityCount) {
		Formula formula = new Formula();
		if (cityCount == 0) {
			return new Random().nextInt(this.data.total);
		} else if (cityCount == this.data.total) {
			return this.ants[antCount].get(0);
		} else {
			 double rate = new Random().nextDouble();
			 if (rate <= this.parameter.getTransform()) {
				 // 轉換規則
				 return formula.transformRoll(this.ants[antCount], this.linePheromone);
			 } else {
				 // 轉換機率
				 return formula.transformProbability(this.ants[antCount], this.linePheromone);
			 }
		}
	}
	
	// 二元優化路徑
	private BestSolution optimizationPath() {
		BestSolution bestSolution = new BestSolution();
		for (int ant=0; ant<this.ants.length; ant++) {
			this.ants[ant] = this.optimization.twoOptimization(this.ants[ant]);
			double dis = 0;
			for (int city=0; city<this.ants[ant].size()-1; city++) {
				dis += calculationDistance(this.ants[ant].get(city), this.ants[ant].get(city+1));
			}
			this.distance[ant] = dis;
			if (bestSolution.distance == -1 || bestSolution.distance > this.distance[ant]) {
				bestSolution.distance = this.distance[ant];
				bestSolution.solution = this.ants[ant];
				bestSolution.fitness = 1/this.distance[ant];
			}
		}
		return bestSolution;
	}
	
	// 計算距離
	private double calculationDistance(int pointA, int pointB) {
		if (isInteger)
			return calculationDistanceInteger(pointA, pointB);
		else
			return calculationDistanceDouble(pointA, pointB);
	}
	
	private double calculationDistanceInteger(int pointA, int pointB) {
		return (double)Math.round(Point.distance(
				this.data.x[pointA], this.data.y[pointA], 
				this.data.x[pointB], this.data.y[pointB]));
	}
	
	private double calculationDistanceDouble(int pointA, int pointB) {
		return Point.distance(
				this.data.x[pointA], this.data.y[pointA], 
				this.data.x[pointB], this.data.y[pointB]);
	}
}
