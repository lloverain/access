package com.company.entity;


import java.sql.Timestamp;

/**
 * 对应t_xxfp表
 * @author 杨佳颖 38个字段
 */
public class AccessXxfp {
    private int id;
    private String fpzl;//发票种类
    private String fpdm;//发票代码
    private int fphm;//发票号码
    private String gfmc;//购方名称
    private String gfbh;//购方编号
    private String gfsh;//购方税号
    private String gfdzdh;//购方地址电话
    private String gfyhzh;//购方银行账号
    private String xfmc;//销方名称
    private String xfsh;//销方税号
    private String xfdzdh;//销方地址电话
    private String xfyhzh;//销方银行账户
    private Timestamp kprq;//开票日期
    private double hjje;//合计金额
    private double slv;//税率
    private double hjse;//合计税率
    private String kpr;//开票人
    private String skr;//收款人
    private String fhr;//复核人
    private int zfbz;//作废标志
    private Timestamp zfrq;//作废日期
    private String bz;//备注
    private String xsdjbh;//销售单据编号
    private int returntag;//是否反馈业务系统
    private String sqr;//申请人
    private String hyk;//会员卡
    private String fpmw;
    private String jym;
    private String ewm;
    private String pdffile;
    private String qzcode;
    private String qzmsg;
    private String emailtag;
    private String pre1;
    private String pre2;
    private String pre3;
    private String pre4;

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

    public String getGfmc() {
        return gfmc;
    }

    public void setGfmc(String gfmc) {
        this.gfmc = gfmc;
    }

    public String getGfbh() {
        return gfbh;
    }

    public void setGfbh(String gfbh) {
        this.gfbh = gfbh;
    }

    public String getGfsh() {
        return gfsh;
    }

    public void setGfsh(String gfsh) {
        this.gfsh = gfsh;
    }

    public String getGfdzdh() {
        return gfdzdh;
    }

    public void setGfdzdh(String gfdzdh) {
        this.gfdzdh = gfdzdh;
    }

    public String getGfyhzh() {
        return gfyhzh;
    }

    public void setGfyhzh(String gfyhzh) {
        this.gfyhzh = gfyhzh;
    }

    public String getXfmc() {
        return xfmc;
    }

    public void setXfmc(String xfmc) {
        this.xfmc = xfmc;
    }

    public String getXfsh() {
        return xfsh;
    }

    public void setXfsh(String xfsh) {
        this.xfsh = xfsh;
    }

    public String getXfdzdh() {
        return xfdzdh;
    }

    public void setXfdzdh(String xfdzdh) {
        this.xfdzdh = xfdzdh;
    }

    public String getXfyhzh() {
        return xfyhzh;
    }

    public void setXfyhzh(String xfyhzh) {
        this.xfyhzh = xfyhzh;
    }

    public Timestamp getKprq() {
        return kprq;
    }

    public void setKprq(Timestamp kprq) {
        this.kprq = kprq;
    }

    public double getHjje() {
        return hjje;
    }

    public void setHjje(double hjje) {
        this.hjje = hjje;
    }

    public double getSlv() {
        return slv;
    }

    public void setSlv(double slv) {
        this.slv = slv;
    }

    public double getHjse() {
        return hjse;
    }

    public void setHjse(double hjse) {
        this.hjse = hjse;
    }

    public String getKpr() {
        return kpr;
    }

    public void setKpr(String kpr) {
        this.kpr = kpr;
    }

    public String getSkr() {
        return skr;
    }

    public void setSkr(String skr) {
        this.skr = skr;
    }

    public String getFhr() {
        return fhr;
    }

    public void setFhr(String fhr) {
        this.fhr = fhr;
    }

    public int getZfbz() {
        return zfbz;
    }

    public void setZfbz(int zfbz) {
        this.zfbz = zfbz;
    }

    public Timestamp getZfrq() {
        return zfrq;
    }

    public void setZfrq(Timestamp zfrq) {
        this.zfrq = zfrq;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getXsdjbh() {
        return xsdjbh;
    }

    public void setXsdjbh(String xsdjbh) {
        this.xsdjbh = xsdjbh;
    }

    public int getReturntag() {
        return returntag;
    }

    public void setReturntag(int returntag) {
        this.returntag = returntag;
    }

    public String getSqr() {
        return sqr;
    }

    public void setSqr(String sqr) {
        this.sqr = sqr;
    }

    public String getHyk() {
        return hyk;
    }

    public void setHyk(String hyk) {
        this.hyk = hyk;
    }

    public String getFpmw() {
        return fpmw;
    }

    public void setFpmw(String fpmw) {
        this.fpmw = fpmw;
    }

    public String getJym() {
        return jym;
    }

    public void setJym(String jym) {
        this.jym = jym;
    }

    public String getEwm() {
        return ewm;
    }

    public void setEwm(String ewm) {
        this.ewm = ewm;
    }

    public String getPdffile() {
        return pdffile;
    }

    public void setPdffile(String pdffile) {
        this.pdffile = pdffile;
    }

    public String getQzcode() {
        return qzcode;
    }

    public void setQzcode(String qzcode) {
        this.qzcode = qzcode;
    }

    public String getQzmsg() {
        return qzmsg;
    }

    public void setQzmsg(String qzmsg) {
        this.qzmsg = qzmsg;
    }

    public String getEmailtag() {
        return emailtag;
    }

    public void setEmailtag(String emailtag) {
        this.emailtag = emailtag;
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
        return "AccessXxfp{" +
                "id=" + id +
                ", fpzl='" + fpzl + '\'' +
                ", fpdm='" + fpdm + '\'' +
                ", fphm=" + fphm +
                ", gfmc='" + gfmc + '\'' +
                ", gfbh='" + gfbh + '\'' +
                ", gfsh='" + gfsh + '\'' +
                ", gfdzdh='" + gfdzdh + '\'' +
                ", gfyhzh='" + gfyhzh + '\'' +
                ", xfmc='" + xfmc + '\'' +
                ", xfsh='" + xfsh + '\'' +
                ", xfdzdh='" + xfdzdh + '\'' +
                ", xfyhzh='" + xfyhzh + '\'' +
                ", kprq=" + kprq +
                ", hjje=" + hjje +
                ", slv=" + slv +
                ", hjse=" + hjse +
                ", kpr='" + kpr + '\'' +
                ", skr='" + skr + '\'' +
                ", fhr='" + fhr + '\'' +
                ", zfbz=" + zfbz +
                ", zfrq=" + zfrq +
                ", bz='" + bz + '\'' +
                ", xsdjbh='" + xsdjbh + '\'' +
                ", returntag=" + returntag +
                ", sqr='" + sqr + '\'' +
                ", hyk='" + hyk + '\'' +
                ", fpmw='" + fpmw + '\'' +
                ", jym='" + jym + '\'' +
                ", ewm='" + ewm + '\'' +
                ", pdffile='" + pdffile + '\'' +
                ", qzcode='" + qzcode + '\'' +
                ", qzmsg='" + qzmsg + '\'' +
                ", emailtag='" + emailtag + '\'' +
                ", pre1='" + pre1 + '\'' +
                ", pre2='" + pre2 + '\'' +
                ", pre3='" + pre3 + '\'' +
                ", pre4='" + pre4 + '\'' +
                '}';
    }
}
