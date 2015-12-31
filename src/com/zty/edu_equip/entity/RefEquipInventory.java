package com.zty.edu_equip.entity;

   /**
    * ref_equip_inventory 实体类
    * Mon Nov 16 10:19:08 CST 2015 lp
    */ 

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import com.po.base.AbsDbObj2;
import com.po.base.DbMeta;
import com.po.base.ISeqenceGenerator;
import com.po.serverbase.SeqenceGenerator;

@DbMeta(DB_TABLE="REF_EQUIP_INVENTORY", DB_PK_COL="REF_ID")
public class RefEquipInventory extends AbsDbObj2
{
	@DbMeta(DB_COL="EQUIP_ID")
	private BigDecimal equipId;
	@DbMeta(DB_COL="INVENTORY_ID")
	private BigDecimal inventoryId;
	@DbMeta(DB_COL="OPERATOR_ID")
	private BigDecimal operatorId;
	@DbMeta(DB_COL="COUNT1")
	private BigDecimal count1;
	@DbMeta(DB_COL="COUNT2")
	private BigDecimal count2;
	@DbMeta(DB_COL="BALANCE")
	private BigDecimal balance;
	@DbMeta(DB_COL="REMARK")
	private String remark;
	///构造方法：自动产生主键

	public RefEquipInventory() throws Exception
	{
		super(null, SeqenceGenerator.getInstance());
	}
	///构造方法：如果主键为空，则自动产生主键

	public RefEquipInventory(String pk) throws Exception
	{
		super(pk, SeqenceGenerator.getInstance());
	}
	///不能持久化到数据库中的构造方法：isNeedCreatePk定义是否需要产生主键

	public RefEquipInventory(boolean isNeedCreatePk) throws Exception
	{
		super(isNeedCreatePk);
	}

	public RefEquipInventory(HashMap dataMap) throws Exception
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
	public void setInventoryId(BigDecimal inventoryId)
	{
		changedFiled("inventoryId");
		this.inventoryId=inventoryId;
	}
	public BigDecimal getInventoryId()
	{
		return inventoryId;
	}
	public void setOperatorId(BigDecimal operatorId)
	{
		changedFiled("operatorId");
		this.operatorId=operatorId;
	}
	public BigDecimal getOperatorId()
	{
		return operatorId;
	}
	public void setCount1(BigDecimal count1)
	{
		changedFiled("count1");
		this.count1=count1;
	}
	public BigDecimal getCount1()
	{
		return count1;
	}
	public void setCount2(BigDecimal count2)
	{
		changedFiled("count2");
		this.count2=count2;
	}
	public BigDecimal getCount2()
	{
		return count2;
	}
	public void setBalance(BigDecimal balance)
	{
		changedFiled("balance");
		this.balance=balance;
	}
	public BigDecimal getBalance()
	{
		return balance;
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
}

