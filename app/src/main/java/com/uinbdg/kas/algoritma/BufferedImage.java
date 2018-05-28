package com.uinbdg.kas.algoritma;

class BufferedImage {
    public static final Object TYPE_BYTE_GRAY = "";
    private int minX;
    private int minY;
    private int width;
    private int height;
    private Object type;

    public BufferedImage(int width, int height, Object type) {
    }

    public int getMinX() {
        return minX;
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public int getMinY() {
        return minY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getRGB(int x, int i) {
        return 0;
    }

    public void setRGB(int x, int y, int i) {
    }

    public Object getType() {
        return type;
    }
}
