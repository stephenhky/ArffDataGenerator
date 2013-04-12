package edu.umd.physics.datagen;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Vector;

public abstract class AbstractKernelDataGenerator {
	private int numDim;
	private Random randNumGenerator;
	private double lowerLimit, upperLimit;
	private Vector<String> attributeName;
	
	public AbstractKernelDataGenerator() {
		this.numDim = 2;
		this.randNumGenerator = new Random();
		this.lowerLimit = -10.0;
		this.upperLimit = +10.0;
		attributeName = new Vector<String>();
		this.attributeName.setSize(numDim);
	}
	
	public abstract int getClass(Vector<Double> x);
	
	private Vector<Double> getRandomVector() {
		Vector<Double> vector = new Vector<Double>();
		vector.setSize(numDim);
		for (int i=0; i<numDim; i++) {
			double num = randNumGenerator.nextDouble()*(upperLimit-lowerLimit) + lowerLimit;
			vector.set(i, num);
		}
		return vector;
	}
	
	public void setAttributeName(int idx, String name) {
		attributeName.set(idx, name);
	}
	
	public String getAttributeName(int idx) {
		return attributeName.get(idx);
	}
	
	public void writeArffFile(String fileName, String relationName, int numData) throws IOException {
		File outputFile = new File(fileName);
		PrintWriter writer = new PrintWriter(outputFile);
		writer.println("@relation '"+relationName+"'");
		for (int i=0; i<numDim; i++) {
			writer.println("@attribute "+attributeName.get(i)+" NUMERIC");
		}
		writer.println("@attribute class {1, -1}");
		writer.println("@data");
		for (int i=0; i<numData; i++) {
			StringBuffer lineToWrite = new StringBuffer("");
			Vector<Double> vector = getRandomVector();
			for (int j=0; j<numDim; j++) {
				lineToWrite.append(vector.get(j)+", ");
			}
			lineToWrite.append(getClass(vector)+" ");
			writer.println(lineToWrite.toString());
		}
		writer.close();
	}

	public int getNumDim() {
		return numDim;
	}

	public void setNumDim(int numDim) {
		this.numDim = numDim;
	}

	public double getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(double lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	public double getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(double upperLimit) {
		this.upperLimit = upperLimit;
	}
}
