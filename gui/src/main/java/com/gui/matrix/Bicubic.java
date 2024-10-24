package com.gui.matrix;

import com.gui.matrix.lib.Matrix;

public class Bicubic {
    public static Matrix flatten(Matrix X) {
        Matrix flatMatrix = new Matrix(16, 1); // 16x1 matrix
        int idx = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                flatMatrix.setMat(idx, 0, X.getMat(i, j));
                idx++;
            }
        }
        return flatMatrix;
    }

    public static Matrix muli() {
        Matrix ret = new Matrix(16, 16);
        int x,y,powx,powy;
        x = 0; y = 0; powx = 0; powy = 0;
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                powx = 0;
                powy = 0;
            } else if (i == 1) {
                powx = 0;
                powy = 1;
            } else if (i == 2) {
                powx = 1;
                powy = 0;
            } else if (i == 3) {
                powx = 1;
                powy = 1;
            }
            for (int j = 0; j < 4; j++) {
                if (j == 0) {
                    x = 0;
                    y = 0;
                } else if (j == 1) {
                    x = 0;
                    y = 1;
                } else if (j == 2) {
                    x = 1;
                    y = 0;
                } else if (j == 3) {
                    x = 1;
                    y = 1;
                }
                for (int k = powx; k < 4; k++) {
                    for (int l = powy; l < 4; l++) {
                        ret.setMat(i * 4 + j, k * 4 + l, Math.pow(k,powx) * Math.pow(l,powy) * Math.pow(x, k-powx) * Math.pow(y, l-powy));
                    }
                }
            }
        }
        ret.matBalikan();
        return ret;
    }  
    
    public static Matrix muliD() {
        Matrix ret = new Matrix(16, 16);
        int x,y;
        x = 0; y = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (j == 0) {
                    x = 0;
                    y = 0;
                } else if (j == 1) {
                    x = 1;
                    y = 0;
                } else if (j == 2) {
                    x = 0;
                    y = 1;
                } else if (j == 3) {
                    x = 1;
                    y = 1;
                }
                for (int k = -1; k < 3; k++) {
                    for (int l = -1; l < 3; l++) {
                        if(i==0){
                            if((x==l)&&(y==k)) ret.setMat(i * 4 + j, (k+1) * 4 + l+1, 1);
                            else ret.setMat(i * 4 + j, (k+1) * 4 + l+1, 0);
                        }
                        if(i==1){
                            if((x+1==l)&&(y==k)) ret.setMat(i * 4 + j, (k+1) * 4 + l+1, 0.5);
                            else if((x-1==l)&&(y==k)) ret.setMat(i * 4 + j, (k+1) * 4 + l+1, -0.5);
                            else ret.setMat(i * 4 + j, (k+1) * 4 + l+1, 0);
                        }
                        if(i==2){
                            if((x==l)&&(y+1==k)) ret.setMat(i * 4 + j, (k+1) * 4 + l+1, 0.5);
                            else if((x==l)&&(y-1==k)) ret.setMat(i * 4 + j, (k+1) * 4 + l+1, -0.5);
                            else ret.setMat(i * 4 + j, (k+1) * 4 + l+1, 0);
                        }
                        if(i==3){
                            if((x+1==l)&&(y+1==k)) ret.setMat(i * 4 + j, (k+1) * 4 + l+1, 0.25);
                            else if((x-1==l)&&(y==k)) ret.setMat(i * 4 + j, (k+1) * 4 + l+1, -0.25);
                            else if((x==l)&&(y-1==k)) ret.setMat(i * 4 + j, (k+1) * 4 + l+1, -0.25);
                            else if((x==l)&&(y==k)) ret.setMat(i * 4 + j, (k+1) * 4 + l+1, -0.25);
                            else ret.setMat(i * 4 + j, (k+1) * 4 + l+1, 0);
                        }
                    }
                }
            }
        }
        return ret;
    }  
    
    public static String bicubicInterpolation(Matrix f, double tx, double ty) {
        Matrix b = muli();
        Matrix a = b.mulMatrix(f);
        double ans = 0;
    
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                ans += a.getMat(i * 4 + j, 0) * Math.pow(tx, i) * Math.pow(ty, j);
            }
        }
        return "f(" + tx + ", " + ty + ") = " + String.format("%.2f", ans);
    }
    
}