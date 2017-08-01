package com.dzzxjl.data;

public class DBScanPoint {
    private double x;
    private double y;
    private boolean isVisit;//是否被访问过
    private int cluster;//属于簇的标号
    private boolean isNoised;//是否为噪音点

    public DBScanPoint(double x,double y) {
        this.x = x;
        this.y = y;
        this.isVisit = false;
        this.cluster = 0;
        this.isNoised = false;
    }

    public double getDistance(DBScanPoint point) {//与另外一个点的距离
        return Math.sqrt((x-point.x)*(x-point.x)+(y-point.y)*(y-point.y));
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void setVisit(boolean isVisit) {
        this.isVisit = isVisit;
    }

    public boolean getVisit() {
        return isVisit;
    }

    public int getCluster() {
        return cluster;
    }

    public void setNoised(boolean isNoised) {
        this.isNoised = isNoised;
    }

    public void setCluster(int cluster) {
        this.cluster = cluster;
    }

    public boolean getNoised() {
        return this.isNoised;
    }

    @Override
    public String toString() {
        return x+" "+y+" 所属簇："+cluster+" 是否为噪音点："+(isNoised?1:0);   //cluster为所属类别，是否为噪声点
    }

}
