package edu.umd.physics.datagen;

import java.util.Vector;

public class LinearKernelDataGenerator extends AbstractKernelDataGenerator {
	private double b;
	private Vector<Double> w;
	
	public LinearKernelDataGenerator() {
		w = new Vector<Double>();
		w.setSize(getNumDim());
	}
	
	@Override
	public int getClass(Vector<Double> x) {
		return (getDecisionFunction(x)>=0)?1:-1;
	}

	public double getDecisionFunction(Vector<Double> x) {
		double decisionFcnValue = 0;
		if (x.size() != w.size()) {
			throw new RuntimeException("Wrong number of dimension x. Dimension of w = "+w.size()+" while dimension of x = "+x.size());
		} else {
			for (int i=0; i<getNumDim(); i++) {
				decisionFcnValue += w.get(i)*x.get(i);
			}
			decisionFcnValue += b;
		}
		return decisionFcnValue;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public Vector<Double> getW() {
		return w;
	}

	public void setW(Vector<Double> w) {
		this.w = w;
	}

}
