package com.uinbdg.kas.algoritma;

import java.io.File;

import static java.lang.Math.abs;

public class TemplateMatching {


    /**
     *将图像（x，y）的RGB值保存至数组中
     * @param x 获取图片RGB的位置的x坐标
     * @param y 获取图片RGB的位置的y坐标
     * @param image 需要获取的图片
     * @return 返回的RGB数组[0]-r,[1]-g,[2]-b
     */
    public static int[] getRgbArrary(BufferedImage image, int x, int y) {
        int rgb[]=new int[3];
        int pixel = image.getRGB(x, y);
        rgb[0]=(pixel & 0xff0000 ) >> 16;
        rgb[1] = (pixel & 0xff00) >> 8;
        rgb[2]= pixel & 0xff;
        return rgb;
    }

    /**
     * 设置srcImage的像素，RGB的数据分别保存
     * @param Image 需要设置RGB值的对象
     * @param x 图片修改位置的x坐标
     * @param y 图片修改位置的y上坐标
     * @param  rgb 修改的rgb数组
     */
    public static void setRGB(BufferedImage Image, int x, int y, int[] rgb){
        int pixel;
        for(int i=0;i<3;i++){
            if(rgb[i]>255){
                rgb[i]=rgb[i]%255;
            }else if(rgb[i]<0){
                rgb[i]=abs(rgb[i]);
            }
        }
        pixel = (rgb[0] << 16) & 0x00ff0000 | (0xff00ffff);
        pixel = (rgb[1]  << 8) & 0x0000ff00 | (pixel & 0xffff00ff);
        pixel = (rgb[2] ) & 0x000000ff | (pixel & 0xffffff00);
        Image.setRGB(x, y, pixel);
    }

    /**
     * 使用给定的算子对图像进行一次二维卷积
     * @param srcImage 原图像
     * @param outImage 卷积产生的图像
     * @param Convolution 二维卷积核 int[][]
     * @param modulusOfNormalization 卷积核的归一化系数
     */
    public static void  Convolution(BufferedImage srcImage, BufferedImage outImage, int Convolution[][], int modulusOfNormalization){
        int [][]RGBArrary=new int[srcImage.getWidth()][srcImage.getHeight()];
        Convolution(srcImage,RGBArrary,Convolution,modulusOfNormalization);
        changePicture(outImage,RGBArrary);
    }

    /**
     * 对图像进行二维卷积，每个像素对应的结果保存在outArrary中。
     * @param srcImage 原图像
     * @param outArrary 卷积产生的结果，二维数组。是图像三通道里的其中之一
     * @param Convolution 二维卷积核 int[][]
     * @param modulusOfNormalization 卷积核的归一化系数
     */
    public static void  Convolution(BufferedImage srcImage, int outArrary[][], int Convolution[][], int modulusOfNormalization){
        int xStart=srcImage.getMinX();
        int yStart=srcImage.getMinY();
        int width = srcImage.getWidth();
        int height = srcImage.getHeight();
        int border;
        border=(Convolution.length-1)/2;
        int outRGB;
        for(int x = xStart; x < width ; x ++) {
            for(int y = yStart; y < height; y++) {
                outRGB = doKenel(x,y,border,Convolution,srcImage,xStart,width,yStart,height);
                try{
                    outRGB/=modulusOfNormalization;
                }catch (Exception e){
                    e.printStackTrace();
                }
                outArrary[x][y]=outRGB;
            }//遍历y
        }//遍历x
    }
    /**
     * 使用给定的算子对图像进行两次一维卷积
     * @param srcImage 原图像
     * @param outImage 卷积产生的图像
     * @param Convolution 一维卷积核 int[]
     * @param modulusOfNormalization 卷积核的归一化系数
     */
    public static void Convolution(BufferedImage srcImage,BufferedImage outImage,int Convolution[],int modulusOfNormalization){
        int xStart=srcImage.getMinX();
        int yStart=srcImage.getMinY();
        int width = srcImage.getWidth();
        int height = srcImage.getHeight();

        int border,center;
        border=center=(Convolution.length-1)/2;

        int outRGB;
        int timeXY[][]={{xStart,width},{yStart,height}};
        BufferedImage outImage_mid=new BufferedImage(width, height,srcImage.getType());

        for(int time=0;time<2;time++){//两次卷积

            for(int firstXY=timeXY[time][0]; firstXY<timeXY[time][1]; firstXY++){
                int secondStart=timeXY[time==0?1:0][0];
                int secondEnd=timeXY[time==0?1:0][1];

                for(int secondXY=secondStart; secondXY<secondEnd; secondXY++){
                    int x=(time==0?firstXY:secondXY);
                    int y=(time==1?firstXY:secondXY);
                    outRGB=0;
                    BufferedImage srcImg_time=(time==0?srcImage:outImage_mid);
                    BufferedImage outImg_time=(time==0?outImage_mid:outImage);

                    for(int i=-border;i<=border;i++){
                        int xy1=secondXY+i;
                        int srcRGB;

                        if (xy1<secondStart){
                            xy1=secondStart;
                        }else if(xy1>=secondEnd){
                            xy1=secondEnd-1;
                        }
                        srcRGB = getRgbArrary(srcImg_time,time==0?x:xy1,time==1?y:xy1)[0];
                        //-----------------------------------
                        outRGB+=(Convolution[center+i]*srcRGB);
                        //------------------------------------
                    }
                    /*-----------------*/
                    try{
                        outRGB/=modulusOfNormalization;

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    int temp[]={outRGB,outRGB,outRGB};
                    setRGB(outImg_time,x,y, temp);
                }
            }
        }
    }

    /**
     * 二维核的使用过程
     * @param x 核中心对应的图像X坐标
     * @param y 核中心对应的图像Y坐标
     * @param border 核半径
     * @param Convolution 核本体
     * @param srcImage 原图像
     * @param xStart 原图像起始x坐标
     * @param width 原图像宽度（比x坐标的最大值大1）
     * @param yStart 原图像起始y坐标
     * @param height 原图像高度（比y坐标的最大值大1）
     * @return
     */
    private static int doKenel(int x,int y,int border, int[][] Convolution,BufferedImage srcImage,int xStart,int width,int yStart,int height) {
        int srcRGB;
        int outRGB=0;
        int center = border;
        for (int i = -border; i <= border; i++) {
            int x1 = x + i;
            if(x1<xStart){
                x1=xStart;
            }else if(x1>=width){
                x1=width-1;
            }
            for (int j = -border; j <= border; j++) {
                int y1 = y + j;
                if(y1<yStart){
                    y1=yStart;
                }else if(y1>=height){
                    y1=height-1;
                }

                srcRGB = getRgbArrary(srcImage, x1, y1)[0];
                //-----------------------------------
                outRGB+= (Convolution[center + i][center + j] * srcRGB);
                //------------------------------------
            }
        }
        return  outRGB;
    }

    public static void changePicture(BufferedImage srcImage,int RGBArrary[][]){
        for(int x=0;x<srcImage.getWidth();x++){
            for(int y=0;y<srcImage.getHeight();y++){
                int temp[]={RGBArrary[x][y],RGBArrary[x][y],RGBArrary[x][y]};
                TemplateMatching.setRGB(srcImage,x,y, temp);
            }
        }
    }
    /**
     * 保存图像文件
     * @param srcImage 保存的图像
     * @param formatName 格式
     * @param file File对象 指出路径
     */
    public static void SaveImage(BufferedImage srcImage,String formatName, File file){
        try {
            ImageIO.write(srcImage, formatName, file);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
