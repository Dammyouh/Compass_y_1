package com.example.yangxiaoyu.compass_y;

/**
 * Created by yangxiaoyu on 17-7-24.
 */

class Position {


    private  int degree;
    private  String positionstr;
    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public String getPositionstr() {
        return positionstr;
    }

    public void setPositionstr(String positionstr) {
        this.positionstr = positionstr;
    }

    public Position(float mdegree,String mpositionstr){
        mdegree = this.getDegree();
        mpositionstr = this.getPositionstr();
    }

}
