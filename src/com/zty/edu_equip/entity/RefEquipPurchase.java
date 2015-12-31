package com.zty.edu_equip.entity;

   /**
    * ref_equip_purchase 实体类
    * Mon Nov 16 10:19:08 CST 2015 lp
    */ 

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import com.po.base.AbsDbObj2;
import com.po.base.DbMeta;
import com.po.base.ISeqenceGenerator;
import com.po.serverbase.SeqenceGenerator;

@DbMeta(DB_TABLE="REF_EQUIP_PURCHASE", DB_PK_COL="REF_ID")
public class RefEquipPurchase extends AbsDbObj2
{
	@DbMeta(DB_COL="EQUIP_ID")
	private BigDecimal equipId;
	@DbMeta(DB_COL="PURCHASE_ID")
	private BigDecimal purchaseId;
	@DbMeta(DB_COL="COUNT")
	private BigDecimal count;
	@DbMeta(DB_COL="AMOUNT")
	private BigDecimal amount;
	///构造方法：自动产生主键

	public RefEquipPurchase() throws Exception
	{
		super(null, SeqenceGenerator.getInstance());
	}
	///构造方法：如果主键为空，则自动产生主键

	public RefEquipPurchase(String pk) throws Exception
	{
		super(pk, SeqenceGenerator.getInstance());
	}
	///不能持久化到数据库中的构造方法：isNeedCreatePk定义是否需要产生主键

	public RefEquipPurchase(boolean isNeedCreatePk) throws Exception
	{
		super(isNeedCreatePk);
	}

	public RefEquipPurchase(HashMap dataMap) throws Exception
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
	public void setEquipId(BigDecimal equipId)
	{
		changedFiled("equipId");
		this.equipId=equipId;
	}
	public BigDecimal getEquipId()
	{
		return equipId;
	}
	public void setPurchaseId(BigDecimal purchaseId)
	{
		changedFiled("purchaseId");
		this.purchaseId=purchaseId;
	}
	public BigDecimal getPurchaseId()
	{
		return purchaseId;
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
	public void setAmount(BigDecimal amount)
	{
		changedFiled("amount");
		this.amount=amount;
	}
	public BigDecimal getAmount()
	{
		return amount;
	}
}

