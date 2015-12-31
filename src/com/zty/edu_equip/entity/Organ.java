package com.zty.edu_equip.entity;

   /**
    * organ 实体类
    * Mon Nov 16 10:19:08 CST 2015 lp
    */ 

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import com.po.base.AbsDbObj2;
import com.po.base.DbMeta;
import com.po.base.ISeqenceGenerator;
import com.po.serverbase.SeqenceGenerator;

@DbMeta(DB_TABLE="ORGAN", DB_PK_COL="ORGAN_ID")
public class Organ extends AbsDbObj2
{
	@DbMeta(DB_COL="SCAL_ID")
	private BigDecimal scalId;
	@DbMeta(DB_COL="PARENT_ID")
	private BigDecimal parentId;
	@DbMeta(DB_COL="ORGAN_TYPE")
	private String organType;
	@DbMeta(DB_COL="ORGAN_NAME")
	private String organName;
	@DbMeta(DB_COL="REINDEX")
	private String reindex;
	@DbMeta(DB_COL="TELEPHONE")
	private String telephone;
	@DbMeta(DB_COL="ADDRESS")
	private String address;
	@DbMeta(DB_COL="AREA_COVERED")
	private BigDecimal areaCovered;
	@DbMeta(DB_COL="COVERED_AREA")
	private BigDecimal coveredArea;
	@DbMeta(DB_COL="GREEN_AREA")
	private BigDecimal greenArea;
	@DbMeta(DB_COL="STUDENT_COUNT")
	private BigDecimal studentCount;
	@DbMeta(DB_COL="TEACHER_COUNT")
	private BigDecimal teacherCount;
	@DbMeta(DB_COL="CREATE_ORGAN_TIME")
	private Timestamp createOrganTime;
	@DbMeta(DB_COL="CONTACT")
	private String contact;
	@DbMeta(DB_COL="UKEY_SERIAL")
	private String ukeySerial;
	@DbMeta(DB_COL="UKEY_CHECKCODE")
	private String ukeyCheckcode;
	@DbMeta(DB_COL="UKEY_LIFETIME")
	private Timestamp ukeyLifetime;
	@DbMeta(DB_COL="IMPORT_DATA")
	private BigDecimal importData;
	///构造方法：自动产生主键

	public Organ() throws Exception
	{
		super(null, SeqenceGenerator.getInstance());
	}
	///构造方法：如果主键为空，则自动产生主键

	public Organ(String pk) throws Exception
	{
		super(pk, SeqenceGenerator.getInstance());
	}
	///不能持久化到数据库中的构造方法：isNeedCreatePk定义是否需要产生主键

	public Organ(boolean isNeedCreatePk) throws Exception
	{
		super(isNeedCreatePk);
	}

	public Organ(HashMap dataMap) throws Exception
	{
		super(dataMap);
	}
	public void setOrganId(String organId)
	{
		setPkValue(organId);
	}
	public String getOrganId()
	{
		return getPkValue();
	}
	public void setScalId(BigDecimal scalId)
	{
		changedFiled("scalId");
		this.scalId=scalId;
	}
	public BigDecimal getScalId()
	{
		return scalId;
	}
	public void setParentId(BigDecimal parentId)
	{
		changedFiled("parentId");
		this.parentId=parentId;
	}
	public BigDecimal getParentId()
	{
		return parentId;
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
	public void setOrganName(String organName)
	{
		changedFiled("organName");
		this.organName=organName;
	}
	public String getOrganName()
	{
		return organName;
	}
	public void setReindex(String reindex)
	{
		changedFiled("reindex");
		this.reindex=reindex;
	}
	public String getReindex()
	{
		return reindex;
	}
	public void setTelephone(String telephone)
	{
		changedFiled("telephone");
		this.telephone=telephone;
	}
	public String getTelephone()
	{
		return telephone;
	}
	public void setAddress(String address)
	{
		changedFiled("address");
		this.address=address;
	}
	public String getAddress()
	{
		return address;
	}
	public void setAreaCovered(BigDecimal areaCovered)
	{
		changedFiled("areaCovered");
		this.areaCovered=areaCovered;
	}
	public BigDecimal getAreaCovered()
	{
		return areaCovered;
	}
	public void setCoveredArea(BigDecimal coveredArea)
	{
		changedFiled("coveredArea");
		this.coveredArea=coveredArea;
	}
	public BigDecimal getCoveredArea()
	{
		return coveredArea;
	}
	public void setGreenArea(BigDecimal greenArea)
	{
		changedFiled("greenArea");
		this.greenArea=greenArea;
	}
	public BigDecimal getGreenArea()
	{
		return greenArea;
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
	public void setTeacherCount(BigDecimal teacherCount)
	{
		changedFiled("teacherCount");
		this.teacherCount=teacherCount;
	}
	public BigDecimal getTeacherCount()
	{
		return teacherCount;
	}
	public void setCreateOrganTime(Timestamp createOrganTime)
	{
		changedFiled("createOrganTime");
		this.createOrganTime=createOrganTime;
	}
	public Timestamp getCreateOrganTime()
	{
		return createOrganTime;
	}
	public void setContact(String contact)
	{
		changedFiled("contact");
		this.contact=contact;
	}
	public String getContact()
	{
		return contact;
	}
	public void setUkeySerial(String ukeySerial)
	{
		changedFiled("ukeySerial");
		this.ukeySerial=ukeySerial;
	}
	public String getUkeySerial()
	{
		return ukeySerial;
	}
	public void setUkeyCheckcode(String ukeyCheckcode)
	{
		changedFiled("ukeyCheckcode");
		this.ukeyCheckcode=ukeyCheckcode;
	}
	public String getUkeyCheckcode()
	{
		return ukeyCheckcode;
	}
	public void setUkeyLifetime(Timestamp ukeyLifetime)
	{
		changedFiled("ukeyLifetime");
		this.ukeyLifetime=ukeyLifetime;
	}
	public Timestamp getUkeyLifetime()
	{
		return ukeyLifetime;
	}
	public void setImportData(BigDecimal importData)
	{
		changedFiled("importData");
		this.importData=importData;
	}
	public BigDecimal getImportData()
	{
		return importData;
	}
}

