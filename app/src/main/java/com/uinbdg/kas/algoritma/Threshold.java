package com.uinbdg.kas.algoritma;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Threshold {
    /**
     * 保留大于（原图像最大灰度的percentNUM %）的像素
     * @param image 原图像
     * @param percentNUM
     */
    public static void thresholdProcessing(BufferedImage image, int percentNUM){
        int xStart=image.getMinX();
        int yStart=image.getMinY();
        int width = image.getWidth();
        int height = image.getHeight();
        int max=0;
        for(int x = xStart; x < width ; x ++) {
            for(int y = yStart; y < height; y++) {
                if(TemplateMatching.getRgbArrary(image,x,y)[0]>max){
                    max= TemplateMatching.getRgbArrary(image,x,y)[0];
                }
            }
        }
        int target=max*percentNUM/100;
        for(int x = xStart; x < width ; x ++) {
            for(int y = yStart; y < height; y++) {
                if(TemplateMatching.getRgbArrary(image,x,y)[0]<=target){
                    image.setRGB(x,y,0);
                }
            }
        }
    }


    public static void doubleThreshold(BufferedImage NMSimage,BufferedImage gradImage,String imageSaveDir){
        int xStart=NMSimage.getMinX();
        int yStart=NMSimage.getMinY();
        int width = NMSimage.getWidth();
        int height = NMSimage.getHeight();
        int Hist[]=getHist(NMSimage,gradImage);
        int threshold[];
        BufferedImage hightTimage=new BufferedImage(width,height,gradImage.getType());
        BufferedImage lowTimage=new BufferedImage(width,height,gradImage.getType());
        BufferedImage weakEdge=new BufferedImage(width,height,gradImage.getType());
        BufferedImage outImage=new BufferedImage(width,height,gradImage.getType());
        threshold=getThreshold(Hist);

        Queue <Point>pointList=new LinkedList<>();
        Stack <Point>pointStack=new Stack<>();
        boolean connected;

        int X8[]={-1,-1,-1,0,0,0,+1,+1,+1};
        int Y8[]={-1,0,+1,-1,0,+1,-1,0,+1};

        boolean isXYCheck[][]=new boolean[NMSimage.getWidth()][NMSimage.getHeight()];
        for(int x=xStart;x<width;x++) {
            for (int y = 0; y < height; y++) {
                isXYCheck[x][y]=false;
            }
        }
        for(int x=xStart;x<width;x++){
            for(int y=yStart;y<height;y++){
                if(TemplateMatching.getRgbArrary(NMSimage,x,y)[0] > threshold[1]){
                    hightTimage.setRGB(x,y,0xffffffff);
                    outImage.setRGB(x,y,0xffffffff);
                }else{
                    hightTimage.setRGB(x,y,0x00000000);
                }
                if (TemplateMatching.getRgbArrary(NMSimage,x,y)[0] > threshold[0]){
                    lowTimage.setRGB(x,y,0xffffffff);
                }else {
                    lowTimage.setRGB(x,y,0x00000000);
                }
                weakEdge.setRGB(x,y,lowTimage.getRGB(x,y)-hightTimage.getRGB(x,y));
            }
        }
        for(int x=xStart;x<width;x++){
            for (int y = yStart; y < height; y++){
                connected=false;
                if(weakEdge.getRGB(x,y)==0xffffffff && !isXYCheck[x][y]){
                    isXYCheck[x][y]=true;
                    Point temp1=new Point(x,y);
                    pointStack.push(temp1);
                    pointList.offer(temp1);
                    while(!pointStack.empty()){
                        Point temp=pointStack.pop();
                        for(int i=0;i<8;i++){
                            int x1=temp.getX()+X8[i];
                            int y1=temp.getY()+Y8[i];
                            x1=(x1<xStart?xStart:x1);
                            x1=(x1>=width?(width-1):x1);
                            y1=(y1<yStart?yStart:y1);
                            y1=(y1>=height?(height-1):y1);
                            if(weakEdge.getRGB(x1,y1)==0xffffffff&&!isXYCheck[x1][y1]){
                                Point temp2=new Point(x1,y1);
                                pointStack.push(temp2);
                                pointList.offer(temp2);
                                isXYCheck[x1][y1]=true;
                            }
                            if(hightTimage.getRGB(x1,y1)==0xffffffff){
                                connected=true;
                            }
                        }
                    }
                    if(connected){
                            while (!pointList.isEmpty()){
                                Point point=pointList.poll();
                                outImage.setRGB(point.getX(),point.getY(),0xffffffff);
                            }
                    } else{
                            while (!pointList.isEmpty()){
                                Point point=pointList.poll();
                                isXYCheck[point.getX()][point.getY()]=false;
                            }
                    }
                }
            }
        }
        TemplateMatching.SaveImage(outImage,"jpg",new File(imageSaveDir+"result.jpg"));
        TemplateMatching.SaveImage(hightTimage,"jpg",new File(imageSaveDir+"hight.jpg"));
        TemplateMatching.SaveImage(lowTimage,"jpg",new File(imageSaveDir+"low.jpg"));
        TemplateMatching.SaveImage(weakEdge,"jpg",new File(imageSaveDir+"weak.jpg"));
    }

    private static int[] getThreshold(int[] hist) {
        int [] threshold=new int[2];
        int maxGrad=getMaxGrad(hist);
        int edgeNum=getEdgeNum(hist);

        double  dRatHigh = 0.89;
        double  dRatLow = 0.3;
        int HighCount = (int)(dRatHigh * edgeNum);
        int j=0;
        int edgeNum1 = hist[0];
        while((j<(maxGrad-1)) && (edgeNum1 <  HighCount))
        {
            j++;
            edgeNum1 += hist[j];
        }
        threshold[1] = j;                                   //高阈值
        threshold[0] = (int)((threshold[1]) * dRatLow );
        return  threshold;
    }

    private static int getEdgeNum(int[] hist) {
        int EdgeNum=hist[0];
        for(int i=0;i<hist.length;i++){
            EdgeNum+=hist[i];
        }
        return EdgeNum;
    }

    private static int getMaxGrad(int[] hist) {
        int max=0;
        for(int i=0;i<hist.length;i++){
            if(hist[i]!=0){
                max=i;
            }
        }
        return max;
    }

    private static int[] getHist(BufferedImage NmsImage,BufferedImage gradImage){
        int []hist=new int[360];
        int xStart=NmsImage.getMinX();
        int yStart=NmsImage.getMinY();
        int width = NmsImage.getWidth();
        int height = NmsImage.getHeight();
        for(int i=0;i<hist.length;i++)
            hist[i] = 0;
        for(int x=xStart; x<width; x++) {
            for(int y=yStart; y<height; y++)
            {
                if(NmsImage.getRGB(x,y)!=0)//???????????????????????????
                    hist[TemplateMatching.getRgbArrary(gradImage,x,y)[0]]++;
            }
        }
        return hist;
    }
}

class Point{
    private int x;
    private int y;
    public Point(int x,int y){
        this.x=x;
        this.y=y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
