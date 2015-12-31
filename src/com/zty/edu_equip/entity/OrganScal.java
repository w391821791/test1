package com.zty.edu_equip.entity;

   /**
    * organ_scal ʵ����
    * Mon Nov 16 10:19:08 CST 2015 lp
    */ 

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import com.po.base.AbsDbObj2;
import com.po.base.DbMeta;
import com.po.base.ISeqenceGenerator;
import com.po.serverbase.SeqenceGenerator;

@DbMeta(DB_TABLE="ORGAN_SCAL", DB_PK_COL="SCAL_ID")
public class OrganScal extends AbsDbObj2
{
	@DbMeta(DB_COL="ORGAN_TYPE")
	private String organType;
	@DbMeta(DB_COL="SCAL_NAME")
	private String scalName;
	@DbMeta(DB_COL="CLASS_COUNT")
	private BigDecimal classCount;
	@DbMeta(DB_COL="STUDENT_COUNT")
	private BigDecimal studentCount;
	///���췽�����Զ���������

	public OrganScal() throws Exception
	{
		super(null, SeqenceGenerator.getInstance());
	}
	///���췽�����������Ϊ�գ����Զ���������

	public OrganScal(String pk) throws Exception
	{
		super(pk, SeqenceGenerator.getInstance());
	}
	///���ܳ־û������ݿ��еĹ��췽����isNeedCreatePk�����Ƿ���Ҫ��������

	public OrganScal(boolean isNeedCreatePk) throws Exception
	{
		super(isNeedCreatePk);
	}

	public OrganScal(HashMap dataMap) throws Exception
	{
		super(dataMap);
	}
	public void setScalId(String scalId)
	{
		setPkValue(scalId);
	}
	public String getScalId()
	{
		return getPkValue();
	}
	public void setOrganType(String organType)
	{
		changedFiled("organType");
		this.organType=organType;
	}
	public String getOrganType()
	{
		return organType;
	}
	public void setScalName(String scalName)
	{
		changedFiled("scalName");
		this.scalName=scalName;
	}
	public String getScalName()
	{
		return scalName;
	}
	public void setClassCount(BigDecimal classCount)
	{
		changedFiled("classCount");
		this.classCount=classCount;
	}
	public BigDecimal getClassCount()
	{
		return classCount;
	}
	public void setStudentCount(BigDecimal studentCount)
	{
		changedFiled("studentCount");
		this.studentCount=studentCount;
	}
	public BigDecimal getStudentCount()
	{
		return studentCount;
	}
}

