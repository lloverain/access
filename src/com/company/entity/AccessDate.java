package com.company.entity;

/**
 * 对应t_xxfp_mx表
 * @author 杨佳颖
 * t_ttfp_mx
 * 23个字段
 */
public class AccessDate {
    private int id;
    private String fpzl;//发表种类
    private String fpdm;//发票代码

    private int fphm;//发票号码1
    private int xh;//排序号
    private int mxtype;//明细类型
    private double je;//金额

    private double slv;//税率
    private double se;//税额
    private String ggxh;//规格型号
    private String ziduan;

    private String spbh;//商品编号
    private String spmc;//商品名称
    private String spsm;//商品税目1
    private String jldw;//计量单位

    private double dj;//单价
    private double sl;//数量
    private int hsjbz;//含税价标志
    private String xsdjmxid;

    private String pre1;//保留1
    private String pre2;//保留2
    private String pre3;//保留3
    private String pre4;//保留4

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFpzl() {
        return fpzl;
    }

    public void setFpzl(String fpzl) {
        this.fpzl = fpzl;
    }

    public String getFpdm() {
        return fpdm;
    }

    public void setFpdm(String fpdm) {
        this.fpdm = fpdm;
    }

    public int getFphm() {
        return fphm;
    }

    public void setFphm(int fphm) {
        this.fphm = fphm;
    }

    public int getXh() {
        return xh;
    }

    public void setXh(int xh) {
        this.xh = xh;
    }

    public int getMxtype() {
        return mxtype;
    }

    public void setMxtype(int mxtype) {
        this.mxtype = mxtype;
    }

    public double getJe() {
        return je;
    }

    public void setJe(double je) {
        this.je = je;
    }

    public double getSlv() {
        return slv;
    }

    public void setSlv(double slv) {
        this.slv = slv;
    }

    public double getSe() {
        return se;
    }

    public void setSe(double se) {
        this.se = se;
    }

    public String getGgxh() {
        return ggxh;
    }

    public void setGgxh(String ggxh) {
        this.ggxh = ggxh;
    }

    public String getZiduan() {
        return ziduan;
    }

    public void setZiduan(String ziduan) {
        this.ziduan = ziduan;
    }

    public String getSpbh() {
        return spbh;
    }

    public void setSpbh(String spbh) {
        this.spbh = spbh;
    }

    public String getSpmc() {
        return spmc;
    }

    public void setSpmc(String spmc) {
        this.spmc = spmc;
    }

    public String getSpsm() {
        return spsm;
    }

    public void setSpsm(String spsm) {
        this.spsm = spsm;
    }

    public String getJldw() {
        return jldw;
    }

    public void setJldw(String jldw) {
        this.jldw = jldw;
    }

    public double getDj() {
        return dj;
    }

    public void setDj(double dj) {
        this.dj = dj;
    }

    public double getSl() {
        return sl;
    }

    public void setSl(double sl) {
        this.sl = sl;
    }

    public int getHsjbz() {
        return hsjbz;
    }

    public void setHsjbz(int hsjbz) {
        this.hsjbz = hsjbz;
    }

    public String getXsdjmxid() {
        return xsdjmxid;
    }

    public void setXsdjmxid(String xsdjmxid) {
        this.xsdjmxid = xsdjmxid;
    }

    public String getPre1() {
        return pre1;
    }

    public void setPre1(String pre1) {
        this.pre1 = pre1;
    }

    public String getPre2() {
        return pre2;
    }

    public void setPre2(String pre2) {
        this.pre2 = pre2;
    }

    public String getPre3() {
        return pre3;
    }

    public void setPre3(String pre3) {
        this.pre3 = pre3;
    }

    public String getPre4() {
        return pre4;
    }

    public void setPre4(String pre4) {
        this.pre4 = pre4;
    }

    @Override
    public String toString() {
        return "AccessDate{" +
                "id=" + id +
                ", fpzl='" + fpzl + '\'' +
                ", fpdm='" + fpdm + '\'' +
                ", fphm=" + fphm +
                ", xh=" + xh +
                ", mxtype=" + mxtype +
                ", je=" + je +
                ", slv=" + slv +
                ", se=" + se +
                ", ggxh='" + ggxh + '\'' +
                ", ziduan='" + ziduan + '\'' +
                ", spbh='" + spbh + '\'' +
                ", spmc='" + spmc + '\'' +
                ", spsm='" + spsm + '\'' +
                ", jldw='" + jldw + '\'' +
                ", dj=" + dj +
                ", sl=" + sl +
                ", hsjbz=" + hsjbz +
                ", xsdjmxid='" + xsdjmxid + '\'' +
                ", pre1='" + pre1 + '\'' +
                ", pre2='" + pre2 + '\'' +
                ", pre3='" + pre3 + '\'' +
                ", pre4='" + pre4 + '\'' +
                '}';
    }
}
