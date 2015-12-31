package com.zty.edu_equip.entity;

   /**
    * sys_sequence 实体类
    * Mon Nov 16 10:19:08 CST 2015 lp
    */ 

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import com.po.base.AbsDbObj2;
import com.po.base.DbMeta;
import com.po.base.ISeqenceGenerator;
import com.po.serverbase.SeqenceGenerator;

@DbMeta(DB_TABLE="SYS_SEQUENCE", DB_PK_COL="CODE")
public class SysSequence extends AbsDbObj2
{
	@DbMeta(DB_COL="VALUE")
	private BigDecimal value;
	///构造方法：自动产生主键

	public SysSequence() throws Exception
	{
		super(null, SeqenceGenerator.getInstance());
	}
	///构造方法：如果主键为空，则自动产生主键

	public SysSequence(String pk) throws Exception
	{
		super(pk, SeqenceGenerator.getInstance());
	}
	///不能持久化到数据库中的构造方法：isNeedCreatePk定义是否需要产生主键

	public SysSequence(boolean isNeedCreatePk) throws Exception
	{
		super(isNeedCreatePk);
	}

	public SysSequence(HashMap dataMap) throws Exception
	{
		super(dataMap);
	}
	public void setCode(String code)
	{
		setPkValue(code);
	}
	public String getCode()
	{
		return getPkValue();
	}
	public void setValue(BigDecimal value)
	{
		changedFiled("value");
		this.value=value;
	}
	public BigDecimal getValue()
	{
		return value;
	}
}

