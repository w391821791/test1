package com.zty.edu_equip.entity;

   /**
    * purchase 实体类
    * Mon Nov 16 10:19:08 CST 2015 lp
    */ 

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import com.po.base.AbsDbObj2;
import com.po.base.DbMeta;
import com.po.base.ISeqenceGenerator;
import com.po.serverbase.SeqenceGenerator;

@DbMeta(DB_TABLE="PURCHASE", DB_PK_COL="PURCHASE_ID")
public class Purchase extends AbsDbObj2
{
	@DbMeta(DB_COL="ORGAN_ID")
	private BigDecimal organId;
	@DbMeta(DB_COL="BATCH_NO")
	private String batchNo;
	@DbMeta(DB_COL="PURCHASE_DATE")
	private Timestamp purchaseDate;
	@DbMeta(DB_COL="PURCHASE_TITLE")
	private String purchaseTitle;
	@DbMeta(DB_COL="PURCHASE_REASON")
	private String purchaseReason;
	@DbMeta(DB_COL="ALL_COUNT")
	private BigDecimal allCount;
	@DbMeta(DB_COL="ALL_AMOUNT")
	private BigDecimal allAmount;
	@DbMeta(DB_COL="WARRANT_NO")
	private String warrantNo;
	@DbMeta(DB_COL="WARRANT_DATE")
	private Timestamp warrantDate;
	@DbMeta(DB_COL="BIZ_PERSON")
	private String bizPerson;
	@DbMeta(DB_COL="REAMRK")
	private String reamrk;
	///构造方法：自动产生主键

	public Purchase() throws Exception
	{
		super(null, SeqenceGenerator.getInstance());
	}
	///构造方法：如果主键为空，则自动产生主键

	public Purchase(String pk) throws Exception
	{
		super(pk, SeqenceGenerator.getInstance());
	}
	///不能持久化到数据库中的构造方法：isNeedCreatePk定义是否需要产生主键

	public Purchase(boolean isNeedCreatePk) throws Exception
	{
		super(isNeedCreatePk);
	}

	public Purchase(HashMap dataMap) throws Exception
	{
		super(dataMap);
	}
	public void setPurchaseId(String purchaseId)
	{
		setPkValue(purchaseId);
	}
	public String getPurchaseId()
	{
		return getPkValue();
	}
	public void setOrganId(BigDecimal organId)
	{
		changedFiled("organId");
		this.organId=organId;
	}
	public BigDecimal getOrganId()
	{
		return organId;
	}
	public void setBatchNo(String batchNo)
	{
		changedFiled("batchNo");
		this.batchNo=batchNo;
	}
	public String getBatchNo()
	{
		return batchNo;
	}
	public void setPurchaseDate(Timestamp purchaseDate)
	{
		changedFiled("purchaseDate");
		this.purchaseDate=purchaseDate;
	}
	public Timestamp getPurchaseDate()
	{
		return purchaseDate;
	}
	public void setPurchaseTitle(String purchaseTitle)
	{
		changedFiled("purchaseTitle");
		this.purchaseTitle=purchaseTitle;
	}
	public String getPurchaseTitle()
	{
		return purchaseTitle;
	}
	public void setPurchaseReason(String purchaseReason)
	{
		changedFiled("purchaseReason");
		this.purchaseReason=purchaseReason;
	}
	public String getPurchaseReason()
	{
		return purchaseReason;
	}
	public void setAllCount(BigDecimal allCount)
	{
		changedFiled("allCount");
		this.allCount=allCount;
	}
	public BigDecimal getAllCount()
	{
		return allCount;
	}
	public void setAllAmount(BigDecimal allAmount)
	{
		changedFiled("allAmount");
		this.allAmount=allAmount;
	}
	public BigDecimal getAllAmount()
	{
		return allAmount;
	}
	public void setWarrantNo(String warrantNo)
	{
		changedFiled("warrantNo");
		this.warrantNo=warrantNo;
	}
	public String getWarrantNo()
	{
		return warrantNo;
	}
	public void setWarrantDate(Timestamp warrantDate)
	{
		changedFiled("warrantDate");
		this.warrantDate=warrantDate;
	}
	public Timestamp getWarrantDate()
	{
		return warrantDate;
	}
	public void setBizPerson(String bizPerson)
	{
		changedFiled("bizPerson");
		this.bizPerson=bizPerson;
	}
	public String getBizPerson()
	{
		return bizPerson;
	}
	public void setReamrk(String reamrk)
	{
		changedFiled("reamrk");
		this.reamrk=reamrk;
	}
	public String getReamrk()
	{
		return reamrk;
	}
}

