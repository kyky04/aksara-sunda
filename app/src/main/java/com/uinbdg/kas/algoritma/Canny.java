package com.uinbdg.kas.algoritma;

import java.io.File;

public class Canny {


    /**
     * 生成Canny图像
     * @param sigam 高斯核的sigam
     * @param guiYiMode 计算高斯核归一化的方式
     * @param dimension 高斯核的维度
     * @param srcImagePath 原图像路径
     * @param imageSaveDir 结果及中间图像保存目录
     */
    public void CannyPicture(double sigam,int guiYiMode,int dimension,String srcImagePath,String imageSaveDir){

        //读取原始图片
        BufferedImage srcImage=getImage(srcImagePath);
        int width = srcImage.getWidth();
        int height = srcImage.getHeight();
        double theta[][];
//        BufferedImage grayImage=new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);
        BufferedImage grayImage=new BufferedImage(width,height,srcImage.getType());
        BufferedImage gsImage1D=new BufferedImage(width,height,grayImage.getType());
        BufferedImage gradBYsoble=new BufferedImage(width,height,gsImage1D.getType());
        BufferedImage NMSImage=new BufferedImage(width,height,gradBYsoble.getType());

        long startTime,endTime;
        //转化为灰度图
        startTime=System.currentTimeMillis();
        Gray.toGray(srcImage,grayImage,imageSaveDir);
        endTime=System.currentTimeMillis();
        System.out.println("Running Time Gray="+(endTime-startTime)+"ms");


        Gaussian gaussian=new Gaussian(sigam,guiYiMode,dimension,grayImage,gsImage1D,imageSaveDir );

        //高斯模糊 1D
        startTime=System.currentTimeMillis();
        gaussian.gaussianPicture();
        endTime=System.currentTimeMillis();
        System.out.println("Running Time Gaussian 1d="+(endTime-startTime)+"ms");

        //梯度图像
        startTime=System.currentTimeMillis();
        theta=Grad.gradPictureSobel(gsImage1D,gradBYsoble,imageSaveDir);
        endTime=System.currentTimeMillis();
        System.out.println("Running Time Grad="+(endTime-startTime)+"ms");

        //非最大抑制
        startTime=System.currentTimeMillis();
        NMS.NMSwithPowerWeight(Grad.getGxGy(),gradBYsoble,NMSImage,theta,imageSaveDir);
        endTime=System.currentTimeMillis();
        System.out.println("Running Time NMS="+(endTime-startTime)+"ms");

        //双阙值处理
        startTime=System.currentTimeMillis();
        Threshold.doubleThreshold(NMSImage,gradBYsoble,imageSaveDir);
        endTime=System.currentTimeMillis();
        System.out.println("Running Time Threshold="+(endTime-startTime)+"ms");

    }
    private BufferedImage getImage(String srcImagePath){
        File file = new File(srcImagePath);
        BufferedImage bi = null;

        return bi;
    }
}
