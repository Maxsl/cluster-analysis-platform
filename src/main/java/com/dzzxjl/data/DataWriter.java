package com.dzzxjl.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class DataWriter {
	
	ArrayList<Sample> samples = null;
	
	public DataWriter(ArrayList<Sample> samples){
		this.samples = samples;
	}
	
	public void writeOutputData(){
		File file = new File("./data/output.txt");
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		{
		try {
			fileWriter = new FileWriter(file);
			bufferedWriter = new BufferedWriter(fileWriter);
			Iterator<Sample> iterator = samples.iterator();
			while(iterator.hasNext()){
				bufferedWriter.write(iterator.next().toString());
				bufferedWriter.newLine();
			}
			bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
			try {
				fileWriter.close();
				bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		}
	}
}
