package com.zty.edu_equip.entity;

   /**
    * ref_equip_dispose 实体类
    * Mon Nov 16 10:19:08 CST 2015 lp
    */ 

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import com.po.base.AbsDbObj2;
import com.po.base.DbMeta;
import com.po.base.ISeqenceGenerator;
import com.po.serverbase.SeqenceGenerator;

@DbMeta(DB_TABLE="REF_EQUIP_DISPOSE", DB_PK_COL="REF_ID")
public class RefEquipDispose extends AbsDbObj2
{
	@DbMeta(DB_COL="EQUIP_ID")
	private BigDecimal equipId;
	@DbMeta(DB_COL="EQUIP_DISPOSE_ID")
	private BigDecimal equipDisposeId;
	@DbMeta(DB_COL="COUNT")
	private BigDecimal count;
	///构造方法：自动产生主键

	public RefEquipDispose() throws Exception
	{
		super(null, SeqenceGenerator.getInstance());
	}
	///构造方法：如果主键为空，则自动产生主键

	public RefEquipDispose(String pk) throws Exception
	{
		super(pk, SeqenceGenerator.getInstance());
	}
	///不能持久化到数据库中的构造方法：isNeedCreatePk定义是否需要产生主键

	public RefEquipDispose(boolean isNeedCreatePk) throws Exception
	{
		super(isNeedCreatePk);
	}

	public RefEquipDispose(HashMap dataMap) throws Exception
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
	public void setEquipDisposeId(BigDecimal equipDisposeId)
	{
		changedFiled("equipDisposeId");
		this.equipDisposeId=equipDisposeId;
	}
	public BigDecimal getEquipDisposeId()
	{
		return equipDisposeId;
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
}

