package com.zty.edu_equip.entity;

   /**
    * equip_part 实体类
    * Mon Nov 16 10:19:08 CST 2015 lp
    */ 

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import com.po.base.AbsDbObj2;
import com.po.base.DbMeta;
import com.po.base.ISeqenceGenerator;
import com.po.serverbase.SeqenceGenerator;

@DbMeta(DB_TABLE="EQUIP_PART", DB_PK_COL="EQUIP_PART_ID")
public class EquipPart extends AbsDbObj2
{
	@DbMeta(DB_COL="EQUIP_ID")
	private BigDecimal equipId;
	@DbMeta(DB_COL="PART_NAME")
	private String partName;
	@DbMeta(DB_COL="PART_SPECIFY")
	private String partSpecify;
	@DbMeta(DB_COL="PART_BRAND")
	private String partBrand;
	@DbMeta(DB_COL="UNIT_CODE")
	private String unitCode;
	@DbMeta(DB_COL="STAND_COUNT")
	private String standCount;
	@DbMeta(DB_COL="COUNT")
	private BigDecimal count;
	@DbMeta(DB_COL="UNIT_PRICE")
	private BigDecimal unitPrice;
	@DbMeta(DB_COL="EQUIP_STATUS")
	private String equipStatus;
	@DbMeta(DB_COL="PURCHASE_DATE")
	private Timestamp purchaseDate;
	@DbMeta(DB_COL="MANUFACTURER")
	private String manufacturer;
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
	///构造方法：自动产生主键

	public EquipPart() throws Exception
	{
		super(null, SeqenceGenerator.getInstance());
	}
	///构造方法：如果主键为空，则自动产生主键

	public EquipPart(String pk) throws Exception
	{
		super(pk, SeqenceGenerator.getInstance());
	}
	///不能持久化到数据库中的构造方法：isNeedCreatePk定义是否需要产生主键

	public EquipPart(boolean isNeedCreatePk) throws Exception
	{
		super(isNeedCreatePk);
	}

	public EquipPart(HashMap dataMap) throws Exception
	{
		super(dataMap);
	}
	public void setEquipPartId(String equipPartId)
	{
		setPkValue(equipPartId);
	}
	public String getEquipPartId()
	{
		return getPkValue();
	}
	public void setEquipId(BigDecimal equipId)
	{
		changedFiled("equipId");
		this.equipId=equipId;
	}
	public BigDecimal getEquipId()
	{
		return equipId;
	}
	public void setPartName(String partName)
	{
		changedFiled("partName");
		this.partName=partName;
	}
	public String getPartName()
	{
		return partName;
	}
	public void setPartSpecify(String partSpecify)
	{
		changedFiled("partSpecify");
		this.partSpecify=partSpecify;
	}
	public String getPartSpecify()
	{
		return partSpecify;
	}
	public void setPartBrand(String partBrand)
	{
		changedFiled("partBrand");
		this.partBrand=partBrand;
	}
	public String getPartBrand()
	{
		return partBrand;
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
	public void setStandCount(String standCount)
	{
		changedFiled("standCount");
		this.standCount=standCount;
	}
	public String getStandCount()
	{
		return standCount;
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
	public void setUnitPrice(BigDecimal unitPrice)
	{
		changedFiled("unitPrice");
		this.unitPrice=unitPrice;
	}
	public BigDecimal getUnitPrice()
	{
		return unitPrice;
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
	public void setPurchaseDate(Timestamp purchaseDate)
	{
		changedFiled("purchaseDate");
		this.purchaseDate=purchaseDate;
	}
	public Timestamp getPurchaseDate()
	{
		return purchaseDate;
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
}

