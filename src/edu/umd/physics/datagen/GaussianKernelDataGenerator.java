package edu.umd.physics.datagen;

import java.util.ArrayList;
import java.util.Vector;

public class GaussianKernelDataGenerator extends AbstractKernelDataGenerator {
	private double b = 0.0;
	private ArrayList<Vector<Double>> supportVectors;
	private ArrayList<Integer> classes;
	private ArrayList<Double> alphas;
	private double gamma = 1.0;
	
	public GaussianKernelDataGenerator() {
		supportVectors = new ArrayList<Vector<Double>>();
		classes = new ArrayList<Integer>();
		alphas = new ArrayList<Double>();
		for (int i=0; i<3; i++) {
			Vector<Double> supportVector = new Vector<Double>();
			supportVector.setSize(getNumDim());
			supportVectors.add(supportVector);
		}
		classes.add(-1);
		classes.add(1);
		classes.add(1);
		alphas.add(0.5);
		alphas.add(0.25);
		alphas.add(0.25);
	}
	
	public Vector<Double> getSupportVector(int idx) {
		if ((idx>=0)&&(idx<3)) {
			return supportVectors.get(idx);
		} else {
			throw new RuntimeException("idx = "+idx+". idx must be between 0 and 2.");
		}
	}
	
	public void setSupportVector(int idx, Vector<Double> vector) {
		if ((idx>=0)&&(idx<3)) {
			supportVectors.set(idx, vector);
		} else {
			throw new RuntimeException("idx = "+idx+". idx must be between 0 and 2.");
		}
	}
	
	public double getAlpha(int idx) {
		if ((idx>=0)&&(idx<3)) {
			return alphas.get(idx).doubleValue();
		} else {
			throw new RuntimeException("idx = "+idx+". idx must be between 0 and 2.");
		}
	}
	
	public void setAlpha(int idx, double alpha) {
		if ((idx>=0)&&(idx<3)) {
			alphas.set(idx, alpha);
		} else {
			throw new RuntimeException("idx = "+idx+". idx must be between 0 and 2.");
		}
	}
	
	@Override
	public int getClass(Vector<Double> x) {
		return (getDecisionFunction(x)>=0)?1:-1;
	}
	
	protected double gaussianFunction(Vector<Double> x1, Vector<Double> x2) {
		if (x1.size() != x2.size()) {
			throw new RuntimeException("Error: Dimension of x1 = "+x1.size()+" but dimension of x2 = "+x2.size());
		}
		
		int numDim = x1.size();
		double sqDist = 0.0;
		for (int i=0; i<numDim; i++) {
			double xComp = x1.get(i) - x2.get(i);
			sqDist += xComp*xComp;
		}
		return Math.exp(-gamma*Math.sqrt(sqDist));
	}
	
	public double getDecisionFunction(Vector<Double> x) {
		double decisionFcnValue = 0.0;
		for (int i=0; i<3; i++) {
			decisionFcnValue += alphas.get(i)*classes.get(i)*gaussianFunction(x, supportVectors.get(i));
		}
		decisionFcnValue += b;
		return decisionFcnValue;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public double getGamma() {
		return gamma;
	}

	public void setGamma(double gamma) {
		this.gamma = gamma;
	}
}
