package com.zty.edu_equip.entity;

   /**
    * ref_equip_purchase ʵ����
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
	///���췽�����Զ���������

	public RefEquipPurchase() throws Exception
	{
		super(null, SeqenceGenerator.getInstance());
	}
	///���췽�����������Ϊ�գ����Զ���������

	public RefEquipPurchase(String pk) throws Exception
	{
		super(pk, SeqenceGenerator.getInstance());
	}
	///���ܳ־û������ݿ��еĹ��췽����isNeedCreatePk�����Ƿ���Ҫ��������

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

