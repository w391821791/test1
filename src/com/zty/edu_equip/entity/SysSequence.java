package com.zty.edu_equip.entity;

   /**
    * sys_sequence ʵ����
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
	///���췽�����Զ���������

	public SysSequence() throws Exception
	{
		super(null, SeqenceGenerator.getInstance());
	}
	///���췽�����������Ϊ�գ����Զ���������

	public SysSequence(String pk) throws Exception
	{
		super(pk, SeqenceGenerator.getInstance());
	}
	///���ܳ־û������ݿ��еĹ��췽����isNeedCreatePk�����Ƿ���Ҫ��������

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

