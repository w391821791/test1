package com.zty.edu_equip.entity;

   /**
    * equip_type ʵ����
    * Mon Nov 16 10:19:08 CST 2015 lp
    */ 

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import com.po.base.AbsDbObj2;
import com.po.base.DbMeta;
import com.po.base.ISeqenceGenerator;
import com.po.serverbase.SeqenceGenerator;

@DbMeta(DB_TABLE="EQUIP_TYPE", DB_PK_COL="EQUIP_TYPE_ID")
public class EquipType extends AbsDbObj2
{
	@DbMeta(DB_COL="GROUP_ID")
	private BigDecimal groupId;
	@DbMeta(DB_COL="ASSET_TYPE_CODE")
	private String assetTypeCode;
	@DbMeta(DB_COL="ASSET_TYPE_NAME")
	private String assetTypeName;
	@DbMeta(DB_COL="GB_CODE")
	private String gbCode;
	@DbMeta(DB_COL="UNIT_CODE")
	private String unitCode;
	@DbMeta(DB_COL="LIFE_YEAR")
	private BigDecimal lifeYear;
	@DbMeta(DB_COL="REINDEX")
	private String reindex;
	///���췽�����Զ���������

	public EquipType() throws Exception
	{
		super(null, SeqenceGenerator.getInstance());
	}
	///���췽�����������Ϊ�գ����Զ���������

	public EquipType(String pk) throws Exception
	{
		super(pk, SeqenceGenerator.getInstance());
	}
	///���ܳ־û������ݿ��еĹ��췽����isNeedCreatePk�����Ƿ���Ҫ��������

	public EquipType(boolean isNeedCreatePk) throws Exception
	{
		super(isNeedCreatePk);
	}

	public EquipType(HashMap dataMap) throws Exception
	{
		super(dataMap);
	}
	public void setEquipTypeId(String equipTypeId)
	{
		setPkValue(equipTypeId);
	}
	public String getEquipTypeId()
	{
		return getPkValue();
	}
	public void setGroupId(BigDecimal groupId)
	{
		changedFiled("groupId");
		this.groupId=groupId;
	}
	public BigDecimal getGroupId()
	{
		return groupId;
	}
	public void setAssetTypeCode(String assetTypeCode)
	{
		changedFiled("assetTypeCode");
		this.assetTypeCode=assetTypeCode;
	}
	public String getAssetTypeCode()
	{
		return assetTypeCode;
	}
	public void setAssetTypeName(String assetTypeName)
	{
		changedFiled("assetTypeName");
		this.assetTypeName=assetTypeName;
	}
	public String getAssetTypeName()
	{
		return assetTypeName;
	}
	public void setGbCode(String gbCode)
	{
		changedFiled("gbCode");
		this.gbCode=gbCode;
	}
	public String getGbCode()
	{
		return gbCode;
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
	public void setLifeYear(BigDecimal lifeYear)
	{
		changedFiled("lifeYear");
		this.lifeYear=lifeYear;
	}
	public BigDecimal getLifeYear()
	{
		return lifeYear;
	}
	public void setReindex(String reindex)
	{
		changedFiled("reindex");
		this.reindex=reindex;
	}
	public String getReindex()
	{
		return reindex;
	}
}

