package com.dzzxjl.data;

public class Sample {
	private double[] attributes;
//	private String label;
	private int predictLabel;
//	public Sample(double[] attributes, String label) {
//		this.attributes = attributes;
//		this.label = label;
//	}
	public Sample(double[] attributes) {
		this.attributes = attributes;
	}
	public double[] getAttributes() {
		return attributes;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return attributes[0] + "	" + attributes[1] + "	" + predictLabel;
	}
//	public String getLabel() {
//		return label;
//	}
	public int getPredictLabel() {
		return predictLabel;
	}
	public void setPredictLabel(int predictLabel) {
		this.predictLabel = predictLabel;
	}
	
}
