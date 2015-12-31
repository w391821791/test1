package com.zty.edu_equip.entity;

   /**
    * room_stand_equip 实体类
    * Mon Nov 16 10:19:08 CST 2015 lp
    */ 

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import com.po.base.AbsDbObj2;
import com.po.base.DbMeta;
import com.po.base.ISeqenceGenerator;
import com.po.serverbase.SeqenceGenerator;

@DbMeta(DB_TABLE="ROOM_STAND_EQUIP", DB_PK_COL="ROOM_STAND_EQUIP_ID")
public class RoomStandEquip extends AbsDbObj2
{
	@DbMeta(DB_COL="EQUIP_TYPE_ID")
	private BigDecimal equipTypeId;
	@DbMeta(DB_COL="ROOM_TYPE_ID")
	private BigDecimal roomTypeId;
	@DbMeta(DB_COL="EQUIP_SPECIFY")
	private String equipSpecify;
	@DbMeta(DB_COL="EQUIPS_MIN")
	private BigDecimal equipsMin;
	@DbMeta(DB_COL="EQUIPS_MAX")
	private BigDecimal equipsMax;
	@DbMeta(DB_COL="REMARK")
	private String remark;
	@DbMeta(DB_COL="UNIT_PRICE")
	private BigDecimal unitPrice;
	@DbMeta(DB_COL="ORGAN_TYPE")
	private String organType;
	@DbMeta(DB_COL="INPUT_BATCH")
	private BigDecimal inputBatch;
	///构造方法：自动产生主键

	public RoomStandEquip() throws Exception
	{
		super(null, SeqenceGenerator.getInstance());
	}
	///构造方法：如果主键为空，则自动产生主键

	public RoomStandEquip(String pk) throws Exception
	{
		super(pk, SeqenceGenerator.getInstance());
	}
	///不能持久化到数据库中的构造方法：isNeedCreatePk定义是否需要产生主键

	public RoomStandEquip(boolean isNeedCreatePk) throws Exception
	{
		super(isNeedCreatePk);
	}

	public RoomStandEquip(HashMap dataMap) throws Exception
	{
		super(dataMap);
	}
	public void setRoomStandEquipId(String roomStandEquipId)
	{
		setPkValue(roomStandEquipId);
	}
	public String getRoomStandEquipId()
	{
		return getPkValue();
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
	public void setRoomTypeId(BigDecimal roomTypeId)
	{
		changedFiled("roomTypeId");
		this.roomTypeId=roomTypeId;
	}
	public BigDecimal getRoomTypeId()
	{
		return roomTypeId;
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
	public void setEquipsMin(BigDecimal equipsMin)
	{
		changedFiled("equipsMin");
		this.equipsMin=equipsMin;
	}
	public BigDecimal getEquipsMin()
	{
		return equipsMin;
	}
	public void setEquipsMax(BigDecimal equipsMax)
	{
		changedFiled("equipsMax");
		this.equipsMax=equipsMax;
	}
	public BigDecimal getEquipsMax()
	{
		return equipsMax;
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
	public void setUnitPrice(BigDecimal unitPrice)
	{
		changedFiled("unitPrice");
		this.unitPrice=unitPrice;
	}
	public BigDecimal getUnitPrice()
	{
		return unitPrice;
	}
	public void setOrganType(String organType)
	{
		changedFiled("organType");
		this.organType=organType;
	}
	public String getOrganType()
	{
		return organType;
	}
	public void setInputBatch(BigDecimal inputBatch)
	{
		changedFiled("inputBatch");
		this.inputBatch=inputBatch;
	}
	public BigDecimal getInputBatch()
	{
		return inputBatch;
	}
}

