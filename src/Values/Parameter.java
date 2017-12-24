package Values;

public class Parameter {

	private int generation;
	private int antAmount;
	private double pheromone;
	private double alpha;
	private double beta;
	private double transform;
	private double rho;
	
	private double sizeX;
	private double sizeY;
	private double addNumber;
	private static Parameter instance = null;
	private Parameter(){}
	
	public static synchronized Parameter getInstance() {
		if (instance == null) {
			instance = new Parameter();
		}
		return instance;
	}
	
	public void setGeneration(int generation) {
		this.generation = generation;
	}
	
	public void setAntAmount(int antAmount) {
		this.antAmount = antAmount;
	}
	
	public void setPheromone(double pheromone) {
		this.pheromone = pheromone;
	}
	
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}
	
	public void setBeta(double beta) {
		this.beta = beta;
	}
	
	public void setTransform(double transform) {
		this.transform = transform;
	}
	
	public void setRho(double rho) {
		this.rho = rho;
	}
	
	public void setPointParameter(double sizeX, double sizeY, double addNumber) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.addNumber = addNumber;
	}
	
	public int getGeneration() {
		return this.generation;
	}
	
	public int getAntAmount() {
		return  this.antAmount;
	}
	
	public double getPheromone() {
		return this.pheromone;
	}
	
	public double getAlpha() {
		return this.alpha;
	}
	
	public double getBeta() {
		return this.beta;
	}
	
	public double getTransform() {
		return this.transform;
	}
	
	public double getRho() {
		return this.rho;
	}
	
	public double getSizeX () {
		return this.sizeX;
	}
	
	public double getSizeY () {
		return this.sizeY;
	}
	
	public double getAddNumber () {
		return this.addNumber;
	}
}
