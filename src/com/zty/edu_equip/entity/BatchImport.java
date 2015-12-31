package com.zty.edu_equip.entity;

   /**
    * batch_import 实体类
    * Mon Nov 16 10:19:08 CST 2015 lp
    */ 

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import com.po.base.AbsDbObj2;
import com.po.base.DbMeta;
import com.po.base.ISeqenceGenerator;
import com.po.serverbase.SeqenceGenerator;

@DbMeta(DB_TABLE="BATCH_IMPORT", DB_PK_COL="IMPORT_ID")
public class BatchImport extends AbsDbObj2
{
	@DbMeta(DB_COL="IMPORT_TIME")
	private Timestamp importTime;
	@DbMeta(DB_COL="IMPORTER")
	private String importer;
	@DbMeta(DB_COL="REMARK")
	private String remark;
	///构造方法：自动产生主键

	public BatchImport() throws Exception
	{
		super(null, SeqenceGenerator.getInstance());
	}
	///构造方法：如果主键为空，则自动产生主键

	public BatchImport(String pk) throws Exception
	{
		super(pk, SeqenceGenerator.getInstance());
	}
	///不能持久化到数据库中的构造方法：isNeedCreatePk定义是否需要产生主键

	public BatchImport(boolean isNeedCreatePk) throws Exception
	{
		super(isNeedCreatePk);
	}

	public BatchImport(HashMap dataMap) throws Exception
	{
		super(dataMap);
	}
	public void setImportId(String importId)
	{
		setPkValue(importId);
	}
	public String getImportId()
	{
		return getPkValue();
	}
	public void setImportTime(Timestamp importTime)
	{
		changedFiled("importTime");
		this.importTime=importTime;
	}
	public Timestamp getImportTime()
	{
		return importTime;
	}
	public void setImporter(String importer)
	{
		changedFiled("importer");
		this.importer=importer;
	}
	public String getImporter()
	{
		return importer;
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

