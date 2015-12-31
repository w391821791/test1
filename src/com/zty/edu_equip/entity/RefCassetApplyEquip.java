package com.zty.edu_equip.entity;

   /**
    * ref_casset_apply_equip 实体类
    * Mon Nov 16 10:19:08 CST 2015 lp
    */ 

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import com.po.base.AbsDbObj2;
import com.po.base.DbMeta;
import com.po.base.ISeqenceGenerator;
import com.po.serverbase.SeqenceGenerator;

@DbMeta(DB_TABLE="REF_CASSET_APPLY_EQUIP", DB_PK_COL="REF_ID")
public class RefCassetApplyEquip extends AbsDbObj2
{
	@DbMeta(DB_COL="CASSET_APPLY_ID")
	private BigDecimal cassetApplyId;
	@DbMeta(DB_COL="EQUIP_ID")
	private BigDecimal equipId;
	@DbMeta(DB_COL="STATUS")
	private String status;
	@DbMeta(DB_COL="APPLY_COUNT")
	private BigDecimal applyCount;
	@DbMeta(DB_COL="APPLY_AMOUNT")
	private BigDecimal applyAmount;
	@DbMeta(DB_COL="USER_ID")
	private BigDecimal userId;
	@DbMeta(DB_COL="USER_NAME")
	private String userName;
	///构造方法：自动产生主键

	public RefCassetApplyEquip() throws Exception
	{
		super(null, SeqenceGenerator.getInstance());
	}
	///构造方法：如果主键为空，则自动产生主键

	public RefCassetApplyEquip(String pk) throws Exception
	{
		super(pk, SeqenceGenerator.getInstance());
	}
	///不能持久化到数据库中的构造方法：isNeedCreatePk定义是否需要产生主键

	public RefCassetApplyEquip(boolean isNeedCreatePk) throws Exception
	{
		super(isNeedCreatePk);
	}

	public RefCassetApplyEquip(HashMap dataMap) throws Exception
	{
		super(dataMap);
	}
	public void setRefId(String refId)
	{
		setPkValue(refId);
	}
	public String getRefId()
	{
		return getPkValue();
	}
	public void setCassetApplyId(BigDecimal cassetApplyId)
	{
		changedFiled("cassetApplyId");
		this.cassetApplyId=cassetApplyId;
	}
	public BigDecimal getCassetApplyId()
	{
		return cassetApplyId;
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
	public void setStatus(String status)
	{
		changedFiled("status");
		this.status=status;
	}
	public String getStatus()
	{
		return status;
	}
	public void setApplyCount(BigDecimal applyCount)
	{
		changedFiled("applyCount");
		this.applyCount=applyCount;
	}
	public BigDecimal getApplyCount()
	{
		return applyCount;
	}
	public void setApplyAmount(BigDecimal applyAmount)
	{
		changedFiled("applyAmount");
		this.applyAmount=applyAmount;
	}
	public BigDecimal getApplyAmount()
	{
		return applyAmount;
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
}

