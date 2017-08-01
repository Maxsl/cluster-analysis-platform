package com.dzzxjl.main;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import com.dzzxjl.algorithm.DBScan;
import com.dzzxjl.data.DBScanPoint;
import com.dzzxjl.data.DataReader;
import com.dzzxjl.data.Sample;

public class DBScanTest {
	private static DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
	public static ArrayList<DBScanPoint> generateSinData(int size) {
        ArrayList<DBScanPoint> DBScanPoints = new ArrayList<DBScanPoint>(size);
        for (int i=0;i<size/2;i++) {
            double x = format(Math.PI / (size / 2) * (i + 1));
            double y = format(Math.sin(x)) ;
            DBScanPoints.add(new DBScanPoint(x,y));
        }
        for (int i=0;i<size/2;i++) {
            double x = format(1.5 + Math.PI / (size/2) * (i+1));
            double y = format(Math.cos(x));
            DBScanPoints.add(new DBScanPoint(x,y));
        }
        return DBScanPoints;
    }

    public static ArrayList<DBScanPoint> generateSpecialData() {
        ArrayList<DBScanPoint> DBScanPoints = new ArrayList<DBScanPoint>();
        
        DataReader reader = new DataReader();
		reader.readData("./data/dataset1.txt");
		ArrayList<Sample> samples = reader.getSamples();
		double latitude;
		double longitude;
		for(Sample sample : samples){
			latitude = sample.getAttributes()[0];
			longitude = sample.getAttributes()[1];
			DBScanPoints.add(new DBScanPoint(latitude, longitude));
		}      
        return DBScanPoints;
    }

    public static void showData(ArrayList<DBScanPoint> DBScanPoints) {
            for (DBScanPoint DBScanPoint:DBScanPoints) {
                System.out.println(DBScanPoint.toString());
            }
    }

    private static double format(double x) {
        return Double.valueOf(df.format(x));
    }
     
    
	public static void main(String[] args) {
		DBScanTest dbScanTest = new DBScanTest();
		ArrayList<DBScanPoint> points = dbScanTest.generateSpecialData();
//        DBScan dbScan = new DBScan(2,3); //扫描半径为3，最小包含点数也为3
		DBScan dbScan = new DBScan(0.001, 15);
        dbScan.process(points);
        for (DBScanPoint p:points) {
            System.out.println(p);
        }
        dbScanTest.showData(points);
	}

}
