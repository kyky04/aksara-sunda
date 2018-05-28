package com.uinbdg.kas.algoritma;

import java.io.File;

import static java.lang.Math.E;
import static java.lang.Math.pow;

public class Gaussian {

    private double sigam;
    private int sizeOfKenel;
    private int normalizationMode;
    private int indexKenelCenter;
    private int gaussianKenel[][];
    private int modulusOfNormalization =0;

    private BufferedImage srcImage,outImage;
    private String  imageSaveDir;

    /**
     * 高斯模糊的构造函数
     * @param sigam  高斯公式里的sigam
     * @param normalizationMode 归一化的方式（0：整形  else：小数型）
     * @param dimension 产生的高斯核的维数，1：使用一维核 2：使用二维核
     * @param srcImage 原始图像
     * @param outImage 结果图像
     * @param imageSaveDir 结果图像的保存目录
     */
    public Gaussian(double sigam, int normalizationMode, int dimension,BufferedImage srcImage,BufferedImage outImage,String imageSaveDir){
        this.sigam=sigam;
        this.normalizationMode =normalizationMode;
        this.srcImage=srcImage;
        this.outImage=outImage;
        this.imageSaveDir=imageSaveDir;
        sizeOfKenel = getSizeOfKenel();
        indexKenelCenter =(sizeOfKenel -1)/2;
        if(dimension==2){
            gaussianKenel =new int[sizeOfKenel][sizeOfKenel];
        }else if(dimension==1){
            gaussianKenel =new int[1][sizeOfKenel];
        }
    }

    /**
     * 返回高斯核对应的归一化系数
     * @return
     */
    public int getModulusOfNormalization(){
        switch (normalizationMode){
            case 0:
                return modulusOfNormalization;
            default:
                return 1;
        }
    }


    /**
     *  获取最佳的高斯核大小 大于6*sigam的最小奇整数
     */
    private int getSizeOfKenel(){
        int temp= (int) (6*sigam);
        if(temp<=1){
            return 3;
        }
        if(temp%2==1){
            return temp;
        }else{
            return temp+1;
        }
    }

    /**
     *高斯核生成
     */
    private void generateGaussianTemplate(){
        double temp[][]=new double[gaussianKenel.length][gaussianKenel[0].length];
        double sigamaSquare2=2*sigam*sigam;
        for(int i = 0; i< gaussianKenel.length; i++){
            int iSubK=i- indexKenelCenter;
            for(int j = 0; j< gaussianKenel[0].length; j++) {
                int jSubK = j- indexKenelCenter;
                double g = (-(pow(iSubK,2)+pow(jSubK,2)) / sigamaSquare2);
                temp[i][j] = pow(E, g);
            }
        }
        //归一化
        Normalization(temp);
        for(int i = 0; i< gaussianKenel.length; i++){
            for(int j = 0; j< gaussianKenel[0].length; j++) {
                gaussianKenel[i][j]=(int) temp[i][j];
                modulusOfNormalization += gaussianKenel[i][j];
            }
        }
    }

    /**
     * 将产生的高斯核根据normalizationMode进行归一化
     * @param temp
     */
    private void Normalization(double[][] temp) {
        double t1;
        switch (normalizationMode){
            case 0:
                t1 =1/temp[0][0];
                break;
            default:
                double sum=0.0;
                for(int i = 0; i< gaussianKenel.length; i++){
                    for(int j = 0; j< gaussianKenel[0].length; j++) {
                        sum+=temp[i][j];
                    }
                }
                t1 =1/sum;
                break;
        }
        for(int i = 0; i< gaussianKenel.length; i++){
            for(int j = 0; j< gaussianKenel[0].length; j++) {
                temp[i][j]*= t1;
            }
        }
    }

    /**
     *获得高斯图片
     */
    public void gaussianPicture(){
        generateGaussianTemplate();


        try {
            switch (gaussianKenel.length){
            //使用一维高斯模板两次卷积 O(n*M*N)
            case 1:
                TemplateMatching.Convolution(srcImage,outImage,gaussianKenel[0],getModulusOfNormalization());
                break;
            //使用二维高斯模板一次卷积 O(n*n*M*N)
            default:
                TemplateMatching.Convolution(srcImage,outImage,gaussianKenel,getModulusOfNormalization());
                break;
        }
        //保存高斯模糊后的图片
            TemplateMatching.SaveImage(outImage, "jpg", new File(imageSaveDir+"gaussian.jpg"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
