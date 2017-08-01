package com.dzzxjl.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataReader {
	private ArrayList<Sample> samples;
	
	public void readData(String filePath) {
		File file = new File(filePath);
//		File file = new File("./data/dataset1.txt");
		samples = new ArrayList<Sample>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			while((line = br.readLine())!=null ) {
				double[] atts = new double[2];
				if(line.length() ==0) continue;
				String[] seg = line.split("\t");
				atts[0] = Double.parseDouble(seg[1]);
				atts[1] = - Double.parseDouble(seg[2]);
				samples.add(new Sample(atts));
				/*
				 * 针对实验所用数据集进行修改
				 */
//				double[] atts = new double[2];
//				if(line.length() ==0) continue;
//				String[] seg = line.split(",");
//				atts[0] = Double.parseDouble(seg[1]);
//				atts[1] = Double.parseDouble(seg[0]);
//				samples.add(new Sample(atts));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Sample> getSamples() {
		return samples;
	}
//	public static void main(String[] args) {
//		DataReader reader = new DataReader();
//		reader.readData();
//		System.out.println(reader.getSamples());
//	}
}
