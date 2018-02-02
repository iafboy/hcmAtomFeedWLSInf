package com.cncustompoc.SingletonSrvcs.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SampleRepository {
    /*
    @Select("select * from License where ID =#{ID}")
    public LicenseEntity selectLicenseByID(@Param("ID") String ID);
    @Select("select * from License")
    public List<LicenseEntity> selectLicenses();
    @Select("select * from License where EnterpriseID =#{EnterpriseID} and GoodsCode =#{GoodsCode}")
    public List<LicenseEntity> selectLicensesByEnterpriseIDAndGoodsCode(@Param("EnterpriseID") String EnterpriseID, @Param("GoodsCode") String GoodsCode);
    @Insert("insert into License(LicenseID,GoodsID,GoodsCode,EnterpriseID,EnterpriseName,EnterpriseValidNumber,EnterpriseAvailableNumber,GoodsTax,ValidDate) values (#{LicenseID},#{GoodsID},#{GoodsCode},#{EnterpriseID},#{EnterpriseName},#{EnterpriseValidNumber},#{EnterpriseAvailableNumber},#{GoodsTax},#{ValidDate})")
    public void insertLicense(LicenseEntity licenseEntity);
    @Update("update License set LicenseID=#{LicenseID},GoodsID=#{GoodsID},GoodsCode=#{GoodsCode},EnterpriseID=#{EnterpriseID},EnterpriseName=#{EnterpriseName},EnterpriseValidNumber=#{EnterpriseValidNumber},EnterpriseAvailableNumber=#{EnterpriseAvailableNumber},GoodsTax=#{GoodsTax},ValidDate=#{ValidDate} where ID=#{ID}")
    public void updateLicenseByID(LicenseEntity licenseEntity);
    @Delete("Delete from License where ID =#{ID}")
    public void deleteLicenseByID(@Param("ID") String ID);
    */
}
