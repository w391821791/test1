package com.zty.edu_equip.entity;

   /**
    * ref_batch_import_equip 实体类
    * Mon Nov 16 10:19:08 CST 2015 lp
    */ 

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import com.po.base.AbsDbObj2;
import com.po.base.DbMeta;
import com.po.base.ISeqenceGenerator;
import com.po.serverbase.SeqenceGenerator;

@DbMeta(DB_TABLE="REF_BATCH_IMPORT_EQUIP", DB_PK_COL="REF_ID")
public class RefBatchImportEquip extends AbsDbObj2
{
	@DbMeta(DB_COL="IMPORT_ID")
	private BigDecimal importId;
	@DbMeta(DB_COL="EQUIP_ID")
	private BigDecimal equipId;
	///构造方法：自动产生主键

	public RefBatchImportEquip() throws Exception
	{
		super(null, SeqenceGenerator.getInstance());
	}
	///构造方法：如果主键为空，则自动产生主键

	public RefBatchImportEquip(String pk) throws Exception
	{
		super(pk, SeqenceGenerator.getInstance());
	}
	///不能持久化到数据库中的构造方法：isNeedCreatePk定义是否需要产生主键

	public RefBatchImportEquip(boolean isNeedCreatePk) throws Exception
	{
		super(isNeedCreatePk);
	}

	public RefBatchImportEquip(HashMap dataMap) throws Exception
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
	public void setImportId(BigDecimal importId)
	{
		changedFiled("importId");
		this.importId=importId;
	}
	public BigDecimal getImportId()
	{
		return importId;
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
}

