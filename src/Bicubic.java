
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import lib.Matrix;

public class Bicubic {
    public static void driver(Scanner sc1) {
        Scanner sc = new Scanner(System.in);
        BufferedReader scFile = new BufferedReader(new InputStreamReader(System.in));
        System.out.printf("1. Masukan dari keyboard\n2. Masukan dari file\nPilihan: ");
        int choice = sc.nextInt();
        while(choice != 1 && choice != 2){
            System.out.printf("Masukan tidak valid! Silakan ulangi...\n");
            choice = sc.nextInt();
        }
        Matrix flatMatrix = new Matrix(16, 1);
        double a, b;
        a = b = 0;
        if(choice == 1){
            System.out.println("Masukkan Matrix : 4x4");
            Matrix X = new Matrix(4, 4);
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    double temp = sc.nextDouble();
                    X.setMat(i, j, temp);  
                }
            }
            flatMatrix = flatten(X);
            a = sc.nextDouble();
            b = sc.nextDouble();
        }
        else{
            Boolean found = false;
            while(!found){
                found = true;
                String fileName = "";
                System.out.printf("Masukkan nama file: ");
                try{
                    fileName = scFile.readLine();
                }
                catch(IOException err){
                    // err.printStackTrace();
                }
                try{
                    Scanner file = new Scanner(new File("../test/"+fileName));
                    Matrix X = new Matrix(4, 4);
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            double temp = file.nextDouble();
                            X.setMat(i, j, temp);  
                        }
                    }
                    flatMatrix = flatten(X);
                    a = file.nextDouble();
                    b = file.nextDouble();
                    file.close();
                }
                catch(FileNotFoundException err){
                    // err.printStackTrace();
                    found = false;
                }
            }
        }
        bicubicInterpolation(flatMatrix, a, b);
    }
    
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
            switch(i){
                case 0 -> {
                    powx = 0;
                    powy = 0;
                }
                case 1 -> {
                    powx = 0;
                    powy = 1;
                }
                case 2 -> {
                    powx = 1;
                    powy = 0;
                }
                case 3 -> {
                    powx = 1;
                    powy = 1;
                }
            }
            for (int j = 0; j < 4; j++) {
                switch(j){
                    case 0 -> {
                        x = 0;
                        y = 0;
                    }
                    case 1 -> {
                        x = 0;
                        y = 1;
                    }
                    case 2 -> {
                        x = 1;
                        y = 0;
                    }
                    case 3 -> {
                        x = 1;
                        y = 1;
                    }
                }
                for (int k = powx; k < 4; k++) {
                    for (int l = powy; l < 4; l++) {
                        ret.setMat(i * 4 + j, k * 4 + l, Math.pow(k,powx) * Math.pow(l,powy) * Math.pow(x, k-powx) * Math.pow(y, l-powy));
                    }
                }
            }
        }
        // ret.print();
        ret.matBalikan();
        return ret;
    }    
    public static Matrix muliD() {
        Matrix ret = new Matrix(16, 16);
        int x,y;
        x = 0; y = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                switch(j){
                    case 0 -> {
                        x = 0;
                        y = 0;
                    }
                    case 1 -> {
                        x = 1;
                        y = 0;
                    }
                    case 2 -> {
                        x = 0;
                        y = 1;
                    }
                    case 3 -> {
                        x = 1;
                        y = 1;
                    }
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
        // ret.print();
        return ret;
    }  

    public static void bicubicInterpolation(Matrix f, double tx, double ty) {
        Matrix a = muli().mulMatrix(f);
        // a.print();
		double ans = 0;
		for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                ans += a.getMat(i * 4 + j, 0) * Math.pow(tx, i) * Math.pow(ty, j);
			}
		}
		System.out.println("f(" + tx + " , " + ty + ") = " + ans);
	}
}
