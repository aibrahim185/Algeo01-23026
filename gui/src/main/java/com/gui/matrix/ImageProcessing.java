package com.gui.matrix;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.gui.matrix.lib.Matrix;

public class ImageProcessing {
    public static BufferedImage imageProcessing(BufferedImage img, double w, double h) {

        int newWidth = (int)(img.getWidth() * w);
        int newHeight = (int)(img.getHeight() * h);
        BufferedImage imgZoom = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        
        double widthRatio = (double) img.getWidth() / newWidth;
        double heightRatio = (double) img.getHeight() / newHeight;

        for (int i = 0; i < newWidth; i++) {
            for (int j = 0; j < newHeight; j++) {
                Matrix a = Bicubic.muli();
                
                double srcX = i * widthRatio;
                double srcY = j * heightRatio;

                int x = (int) srcX;
                int y = (int) srcY;

                Matrix mr = new Matrix(16, 1);
                Matrix mg = new Matrix(16, 1);
                Matrix mb = new Matrix(16, 1);

                int idx = 0;
                for (int ii = x - 1; ii <= x + 2; ii++) {
                    for (int jj = y - 1; jj <= y + 2; jj++) {
                        int idx_i = Math.max(Math.min(ii, img.getWidth() - 1), 0);
                        int idx_j = Math.max(Math.min(jj, img.getHeight() - 1), 0);

                        Color c = new Color(img.getRGB(idx_i, idx_j));
                        mr.setMat(idx, 0, c.getRed());
                        mg.setMat(idx, 0, c.getGreen());
                        mb.setMat(idx, 0, c.getBlue());
                        idx++;
                    }
                }

                Color c = new Color(
                        clamp((int) bicubic(mr, srcX - x, srcY - y, a), 0, 255),
                        clamp((int) bicubic(mg, srcX - x, srcY - y, a), 0, 255),
                        clamp((int) bicubic(mb, srcX - x, srcY - y, a), 0, 255)
                );

                imgZoom.setRGB(i, j, c.getRGB());
            }
        }

        return imgZoom;
    }
    public static double bicubic(Matrix f, double tx, double ty, Matrix a) {
        double ans = 0;
        a = a.mulMatrix(f);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                ans += a.getMat(i * 4 + j, 0) * Math.pow(tx, i) * Math.pow(ty, j);
            }
        }
        return ans;
    }

    // Fungsi untuk memastikan nilai berada dalam batasan 0-255
    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(value, max));
    }
}