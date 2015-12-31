package com.zty.edu_equip.entity;

   /**
    * equip 实体类
    * Mon Nov 16 10:19:08 CST 2015 lp
    */ 

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import com.po.base.AbsDbObj2;
import com.po.base.DbMeta;
import com.po.base.ISeqenceGenerator;
import com.po.serverbase.SeqenceGenerator;

@DbMeta(DB_TABLE="EQUIP", DB_PK_COL="EQUIP_ID")
public class Equip extends AbsDbObj2
{
	@DbMeta(DB_COL="ROOM_ID")
	private BigDecimal roomId;
	@DbMeta(DB_COL="EQUIP_TYPE_ID")
	private BigDecimal equipTypeId;
	@DbMeta(DB_COL="FACTORY_NO")
	private String factoryNo;
	@DbMeta(DB_COL="CARD_NO")
	private String cardNo;
	@DbMeta(DB_COL="EQUIP_NAME")
	private String equipName;
	@DbMeta(DB_COL="EQUIP_SPECIFY")
	private String equipSpecify;
	@DbMeta(DB_COL="EQUIP_BRAND")
	private String equipBrand;
	@DbMeta(DB_COL="UNIT_CODE")
	private String unitCode;
	@DbMeta(DB_COL="COUNT")
	private BigDecimal count;
	@DbMeta(DB_COL="EQUIP_STATUS")
	private String equipStatus;
	@DbMeta(DB_COL="CARD_STATUS")
	private String cardStatus;
	@DbMeta(DB_COL="UNIT_PRICE")
	private BigDecimal unitPrice;
	@DbMeta(DB_COL="PURCHASE_DATE")
	private Timestamp purchaseDate;
	@DbMeta(DB_COL="LIFE_YEARS")
	private BigDecimal lifeYears;
	@DbMeta(DB_COL="LIFE_END_DATE")
	private Timestamp lifeEndDate;
	@DbMeta(DB_COL="CONTRACT_NO")
	private String contractNo;
	@DbMeta(DB_COL="VOUCHER_NO")
	private String voucherNo;
	@DbMeta(DB_COL="BID_NO")
	private String bidNo;
	@DbMeta(DB_COL="MANUFACTURER")
	private String manufacturer;
	@DbMeta(DB_COL="SUPPLIER")
	private String supplier;
	@DbMeta(DB_COL="MAINTENANCE_END_DATE")
	private Timestamp maintenanceEndDate;
	@DbMeta(DB_COL="REMARK")
	private String remark;
	@DbMeta(DB_COL="USER_ID")
	private BigDecimal userId;
	@DbMeta(DB_COL="USER_NAME")
	private String userName;
	@DbMeta(DB_COL="REAL_POSITION")
	private String realPosition;
	@DbMeta(DB_COL="CREATE_TIME")
	private Timestamp createTime;
	@DbMeta(DB_COL="LAST_MODIFY_TIME")
	private Timestamp lastModifyTime;
	@DbMeta(DB_COL="VERSION")
	private BigDecimal version;
	///构造方法：自动产生主键

	public Equip() throws Exception
	{
		super(null, SeqenceGenerator.getInstance());
	}
	///构造方法：如果主键为空，则自动产生主键

	public Equip(String pk) throws Exception
	{
		super(pk, SeqenceGenerator.getInstance());
	}
	///不能持久化到数据库中的构造方法：isNeedCreatePk定义是否需要产生主键

	public Equip(boolean isNeedCreatePk) throws Exception
	{
		super(isNeedCreatePk);
	}

	public Equip(HashMap dataMap) throws Exception
	{
		super(dataMap);
	}
	public void setEquipId(String equipId)
	{
		setPkValue(equipId);
	}
	public String getEquipId()
	{
		return getPkValue();
	}
	public void setRoomId(BigDecimal roomId)
	{
		changedFiled("roomId");
		this.roomId=roomId;
	}
	public BigDecimal getRoomId()
	{
		return roomId;
	}
	public void setEquipTypeId(BigDecimal equipTypeId)
	{
		changedFiled("equipTypeId");
		this.equipTypeId=equipTypeId;
	}
	public BigDecimal getEquipTypeId()
	{
		return equipTypeId;
	}
	public void setFactoryNo(String factoryNo)
	{
		changedFiled("factoryNo");
		this.factoryNo=factoryNo;
	}
	public String getFactoryNo()
	{
		return factoryNo;
	}
	public void setCardNo(String cardNo)
	{
		changedFiled("cardNo");
		this.cardNo=cardNo;
	}
	public String getCardNo()
	{
		return cardNo;
	}
	public void setEquipName(String equipName)
	{
		changedFiled("equipName");
		this.equipName=equipName;
	}
	public String getEquipName()
	{
		return equipName;
	}
	public void setEquipSpecify(String equipSpecify)
	{
		changedFiled("equipSpecify");
		this.equipSpecify=equipSpecify;
	}
	public String getEquipSpecify()
	{
		return equipSpecify;
	}
	public void setEquipBrand(String equipBrand)
	{
		changedFiled("equipBrand");
		this.equipBrand=equipBrand;
	}
	public String getEquipBrand()
	{
		return equipBrand;
	}
	public void setUnitCode(String unitCode)
	{
		changedFiled("unitCode");
		this.unitCode=unitCode;
	}
	public String getUnitCode()
	{
		return unitCode;
	}
	public void setCount(BigDecimal count)
	{
		changedFiled("count");
		this.count=count;
	}
	public BigDecimal getCount()
	{
		return count;
	}
	public void setEquipStatus(String equipStatus)
	{
		changedFiled("equipStatus");
		this.equipStatus=equipStatus;
	}
	public String getEquipStatus()
	{
		return equipStatus;
	}
	public void setCardStatus(String cardStatus)
	{
		changedFiled("cardStatus");
		this.cardStatus=cardStatus;
	}
	public String getCardStatus()
	{
		return cardStatus;
	}
	public void setUnitPrice(BigDecimal unitPrice)
	{
		changedFiled("unitPrice");
		this.unitPrice=unitPrice;
	}
	public BigDecimal getUnitPrice()
	{
		return unitPrice;
	}
	public void setPurchaseDate(Timestamp purchaseDate)
	{
		changedFiled("purchaseDate");
		this.purchaseDate=purchaseDate;
	}
	public Timestamp getPurchaseDate()
	{
		return purchaseDate;
	}
	public void setLifeYears(BigDecimal lifeYears)
	{
		changedFiled("lifeYears");
		this.lifeYears=lifeYears;
	}
	public BigDecimal getLifeYears()
	{
		return lifeYears;
	}
	public void setLifeEndDate(Timestamp lifeEndDate)
	{
		changedFiled("lifeEndDate");
		this.lifeEndDate=lifeEndDate;
	}
	public Timestamp getLifeEndDate()
	{
		return lifeEndDate;
	}
	public void setContractNo(String contractNo)
	{
		changedFiled("contractNo");
		this.contractNo=contractNo;
	}
	public String getContractNo()
	{
		return contractNo;
	}
	public void setVoucherNo(String voucherNo)
	{
		changedFiled("voucherNo");
		this.voucherNo=voucherNo;
	}
	public String getVoucherNo()
	{
		return voucherNo;
	}
	public void setBidNo(String bidNo)
	{
		changedFiled("bidNo");
		this.bidNo=bidNo;
	}
	public String getBidNo()
	{
		return bidNo;
	}
	public void setManufacturer(String manufacturer)
	{
		changedFiled("manufacturer");
		this.manufacturer=manufacturer;
	}
	public String getManufacturer()
	{
		return manufacturer;
	}
	public void setSupplier(String supplier)
	{
		changedFiled("supplier");
		this.supplier=supplier;
	}
	public String getSupplier()
	{
		return supplier;
	}
	public void setMaintenanceEndDate(Timestamp maintenanceEndDate)
	{
		changedFiled("maintenanceEndDate");
		this.maintenanceEndDate=maintenanceEndDate;
	}
	public Timestamp getMaintenanceEndDate()
	{
		return maintenanceEndDate;
	}
	public void setRemark(String remark)
	{
		changedFiled("remark");
		this.remark=remark;
	}
	public String getRemark()
	{
		return remark;
	}
	public void setUserId(BigDecimal userId)
	{
		changedFiled("userId");
		this.userId=userId;
	}
	public BigDecimal getUserId()
	{
		return userId;
	}
	public void setUserName(String userName)
	{
		changedFiled("userName");
		this.userName=userName;
	}
	public String getUserName()
	{
		return userName;
	}
	public void setRealPosition(String realPosition)
	{
		changedFiled("realPosition");
		this.realPosition=realPosition;
	}
	public String getRealPosition()
	{
		return realPosition;
	}
	public void setCreateTime(Timestamp createTime)
	{
		changedFiled("createTime");
		this.createTime=createTime;
	}
	public Timestamp getCreateTime()
	{
		return createTime;
	}
	public void setLastModifyTime(Timestamp lastModifyTime)
	{
		changedFiled("lastModifyTime");
		this.lastModifyTime=lastModifyTime;
	}
	public Timestamp getLastModifyTime()
	{
		return lastModifyTime;
	}
	public void setVersion(BigDecimal version)
	{
		changedFiled("version");
		this.version=version;
	}
	public BigDecimal getVersion()
	{
		return version;
	}
}

