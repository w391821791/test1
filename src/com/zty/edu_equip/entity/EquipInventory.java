package com.zty.edu_equip.entity;

   /**
    * equip_inventory 实体类
    * Mon Nov 16 10:19:08 CST 2015 lp
    */ 

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import com.po.base.AbsDbObj2;
import com.po.base.DbMeta;
import com.po.base.ISeqenceGenerator;
import com.po.serverbase.SeqenceGenerator;

@DbMeta(DB_TABLE="EQUIP_INVENTORY", DB_PK_COL="INVENTORY_ID")
public class EquipInventory extends AbsDbObj2
{
	@DbMeta(DB_COL="ORGAN_ID")
	private BigDecimal organId;
	@DbMeta(DB_COL="OPT_TITLE")
	private String optTitle;
	@DbMeta(DB_COL="OPT_TYPE")
	private String optType;
	@DbMeta(DB_COL="OPT_DATE")
	private Timestamp optDate;
	@DbMeta(DB_COL="SCOPE_REMARK")
	private String scopeRemark;
	@DbMeta(DB_COL="OPT_STATUS")
	private String optStatus;
	@DbMeta(DB_COL="OPT_HEADER")
	private String optHeader;
	@DbMeta(DB_COL="RESULT_PARA")
	private String resultPara;
	@DbMeta(DB_COL="REMARK")
	private String remark;
	@DbMeta(DB_COL="VERSION")
	private BigDecimal version;
	///构造方法：自动产生主键

	public EquipInventory() throws Exception
	{
		super(null, SeqenceGenerator.getInstance());
	}
	///构造方法：如果主键为空，则自动产生主键

	public EquipInventory(String pk) throws Exception
	{
		super(pk, SeqenceGenerator.getInstance());
	}
	///不能持久化到数据库中的构造方法：isNeedCreatePk定义是否需要产生主键

	public EquipInventory(boolean isNeedCreatePk) throws Exception
	{
		super(isNeedCreatePk);
	}

	public EquipInventory(HashMap dataMap) throws Exception
	{
		super(dataMap);
	}
	public void setInventoryId(String inventoryId)
	{
		setPkValue(inventoryId);
	}
	public String getInventoryId()
	{
		return getPkValue();
	}
	public void setOrganId(BigDecimal organId)
	{
		changedFiled("organId");
		this.organId=organId;
	}
	public BigDecimal getOrganId()
	{
		return organId;
	}
	public void setOptTitle(String optTitle)
	{
		changedFiled("optTitle");
		this.optTitle=optTitle;
	}
	public String getOptTitle()
	{
		return optTitle;
	}
	public void setOptType(String optType)
	{
		changedFiled("optType");
		this.optType=optType;
	}
	public String getOptType()
	{
		return optType;
	}
	public void setOptDate(Timestamp optDate)
	{
		changedFiled("optDate");
		this.optDate=optDate;
	}
	public Timestamp getOptDate()
	{
		return optDate;
	}
	public void setScopeRemark(String scopeRemark)
	{
		changedFiled("scopeRemark");
		this.scopeRemark=scopeRemark;
	}
	public String getScopeRemark()
	{
		return scopeRemark;
	}
	public void setOptStatus(String optStatus)
	{
		changedFiled("optStatus");
		this.optStatus=optStatus;
	}
	public String getOptStatus()
	{
		return optStatus;
	}
	public void setOptHeader(String optHeader)
	{
		changedFiled("optHeader");
		this.optHeader=optHeader;
	}
	public String getOptHeader()
	{
		return optHeader;
	}
	public void setResultPara(String resultPara)
	{
		changedFiled("resultPara");
		this.resultPara=resultPara;
	}
	public String getResultPara()
	{
		return resultPara;
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

