package com.gwg.demo.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "zto_enter_stock")
public class ZtoEnterStock implements Serializable {
    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 单号
     */
    @Column(name = "bill_code")
    private String billCode;

    /**
     * 形象店编号
     */
    @Column(name = "depot_code")
    private String depotCode;

    /**
     * 取件码
     */
    @Column(name = "take_code")
    private String takeCode;

    /**
     * 入库时间
     */
    @Column(name = "upload_date")
    private Date uploadDate;

    /**
     * 快递公司编号
     */
    @Column(name = "express_company_code")
    private String expressCompanyCode;

    /**
     * 快件类型
     */
    @Column(name = "express_type")
    private Integer expressType;

    /**
     * 收件人姓名
     */
    @Column(name = "receive_man")
    private String receiveMan;

    /**
     * 收件人联系方式
     */
    @Column(name = "receive_man_mobile")
    private String receiveManMobile;

    /**
     * 收件人地址
     */
    @Column(name = "receive_man_addr")
    private String receiveManAddr;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取单号
     *
     * @return bill_code - 单号
     */
    public String getBillCode() {
        return billCode;
    }

    /**
     * 设置单号
     *
     * @param billCode 单号
     */
    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    /**
     * 获取形象店编号
     *
     * @return depot_code - 形象店编号
     */
    public String getDepotCode() {
        return depotCode;
    }

    /**
     * 设置形象店编号
     *
     * @param depotCode 形象店编号
     */
    public void setDepotCode(String depotCode) {
        this.depotCode = depotCode;
    }

    /**
     * 获取取件码
     *
     * @return take_code - 取件码
     */
    public String getTakeCode() {
        return takeCode;
    }

    /**
     * 设置取件码
     *
     * @param takeCode 取件码
     */
    public void setTakeCode(String takeCode) {
        this.takeCode = takeCode;
    }

    /**
     * 获取入库时间
     *
     * @return upload_date - 入库时间
     */
    public Date getUploadDate() {
        return uploadDate;
    }

    /**
     * 设置入库时间
     *
     * @param uploadDate 入库时间
     */
    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    /**
     * 获取快递公司编号
     *
     * @return express_company_code - 快递公司编号
     */
    public String getExpressCompanyCode() {
        return expressCompanyCode;
    }

    /**
     * 设置快递公司编号
     *
     * @param expressCompanyCode 快递公司编号
     */
    public void setExpressCompanyCode(String expressCompanyCode) {
        this.expressCompanyCode = expressCompanyCode;
    }

    /**
     * 获取快件类型
     *
     * @return express_type - 快件类型
     */
    public Integer getExpressType() {
        return expressType;
    }

    /**
     * 设置快件类型
     *
     * @param expressType 快件类型
     */
    public void setExpressType(Integer expressType) {
        this.expressType = expressType;
    }

    /**
     * 获取收件人姓名
     *
     * @return receive_man - 收件人姓名
     */
    public String getReceiveMan() {
        return receiveMan;
    }

    /**
     * 设置收件人姓名
     *
     * @param receiveMan 收件人姓名
     */
    public void setReceiveMan(String receiveMan) {
        this.receiveMan = receiveMan;
    }

    /**
     * 获取收件人联系方式
     *
     * @return receive_man_mobile - 收件人联系方式
     */
    public String getReceiveManMobile() {
        return receiveManMobile;
    }

    /**
     * 设置收件人联系方式
     *
     * @param receiveManMobile 收件人联系方式
     */
    public void setReceiveManMobile(String receiveManMobile) {
        this.receiveManMobile = receiveManMobile;
    }

    /**
     * 获取收件人地址
     *
     * @return receive_man_addr - 收件人地址
     */
    public String getReceiveManAddr() {
        return receiveManAddr;
    }

    /**
     * 设置收件人地址
     *
     * @param receiveManAddr 收件人地址
     */
    public void setReceiveManAddr(String receiveManAddr) {
        this.receiveManAddr = receiveManAddr;
    }
}