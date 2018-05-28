package com.uinbdg.kas.quis;

public class Pertanyaan {
    private String A;
    private String B;
    private String C;
    private int ID;
    private String JAWAB;
    private String PERTANYAAN;

    public Pertanyaan() {
        this.ID = 0;
        this.PERTANYAAN = "";
        this.A = "";
        this.B = "";
        this.C = "";
        this.JAWAB = "";
    }

    public Pertanyaan(String tnya, String a, String b, String c, String jwb) {
        this.PERTANYAAN = tnya;
        this.A = a;
        this.B = b;
        this.C = c;
        this.JAWAB = jwb;
    }

    public int getID() {
        return this.ID;
    }

    public String getPERTANYAAN() {
        return this.PERTANYAAN;
    }

    public String getA() {
        return this.A;
    }

    public String getB() {
        return this.B;
    }

    public String getC() {
        return this.C;
    }

    public String getJAWAB() {
        return this.JAWAB;
    }

    public void setID(int id) {
        this.ID = id;
    }

    public void setPERTANYAAN(String tnya) {
        this.PERTANYAAN = tnya;
    }

    public void setA(String a) {
        this.A = a;
    }

    public void setB(String b) {
        this.B = b;
    }

    public void setC(String c) {
        this.C = c;
    }

    public void setJAWAB(String jwb) {
        this.JAWAB = jwb;
    }
}
