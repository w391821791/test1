package com.zty.edu_equip.entity;

   /**
    * casset_apply 实体类
    * Mon Nov 16 10:19:08 CST 2015 lp
    */ 

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import com.po.base.AbsDbObj2;
import com.po.base.DbMeta;
import com.po.base.ISeqenceGenerator;
import com.po.serverbase.SeqenceGenerator;

@DbMeta(DB_TABLE="CASSET_APPLY", DB_PK_COL="CASSET_APPLY_ID")
public class CassetApply extends AbsDbObj2
{
	@DbMeta(DB_COL="ORGAN_ID")
	private BigDecimal organId;
	@DbMeta(DB_COL="APPLYER_ID")
	private BigDecimal applyerId;
	@DbMeta(DB_COL="USER_ID")
	private BigDecimal userId;
	@DbMeta(DB_COL="USER_NAME")
	private String userName;
	@DbMeta(DB_COL="APPLY_TIME")
	private Timestamp applyTime;
	@DbMeta(DB_COL="APPLY_REMARK")
	private String applyRemark;
	@DbMeta(DB_COL="APPROVER_ID")
	private BigDecimal approverId;
	@DbMeta(DB_COL="APPROVE_TIME")
	private Timestamp approveTime;
	@DbMeta(DB_COL="APPROVE_REMARK")
	private String approveRemark;
	@DbMeta(DB_COL="APPROVE_RESULT")
	private String approveResult;
	@DbMeta(DB_COL="TRANSFER_DATE")
	private Timestamp transferDate;
	@DbMeta(DB_COL="STATUS")
	private String status;
	@DbMeta(DB_COL="REMARK")
	private String remark;
	@DbMeta(DB_COL="VERSION")
	private BigDecimal version;
	///构造方法：自动产生主键

	public CassetApply() throws Exception
	{
		super(null, SeqenceGenerator.getInstance());
	}
	///构造方法：如果主键为空，则自动产生主键

	public CassetApply(String pk) throws Exception
	{
		super(pk, SeqenceGenerator.getInstance());
	}
	///不能持久化到数据库中的构造方法：isNeedCreatePk定义是否需要产生主键

	public CassetApply(boolean isNeedCreatePk) throws Exception
	{
		super(isNeedCreatePk);
	}

	public CassetApply(HashMap dataMap) throws Exception
	{
		super(dataMap);
	}
	public void setCassetApplyId(String cassetApplyId)
	{
		setPkValue(cassetApplyId);
	}
	public String getCassetApplyId()
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
	public void setApplyerId(BigDecimal applyerId)
	{
		changedFiled("applyerId");
		this.applyerId=applyerId;
	}
	public BigDecimal getApplyerId()
	{
		return applyerId;
	}
	public void setUserId(BigDecimal userId)
	{
		changedFiled("userId");
		this.userId=userId;
	}
	public BigDecimal getUserId()
	{
		return userId;
	}
	public void setUserName(String userName)
	{
		changedFiled("userName");
		this.userName=userName;
	}
	public String getUserName()
	{
		return userName;
	}
	public void setApplyTime(Timestamp applyTime)
	{
		changedFiled("applyTime");
		this.applyTime=applyTime;
	}
	public Timestamp getApplyTime()
	{
		return applyTime;
	}
	public void setApplyRemark(String applyRemark)
	{
		changedFiled("applyRemark");
		this.applyRemark=applyRemark;
	}
	public String getApplyRemark()
	{
		return applyRemark;
	}
	public void setApproverId(BigDecimal approverId)
	{
		changedFiled("approverId");
		this.approverId=approverId;
	}
	public BigDecimal getApproverId()
	{
		return approverId;
	}
	public void setApproveTime(Timestamp approveTime)
	{
		changedFiled("approveTime");
		this.approveTime=approveTime;
	}
	public Timestamp getApproveTime()
	{
		return approveTime;
	}
	public void setApproveRemark(String approveRemark)
	{
		changedFiled("approveRemark");
		this.approveRemark=approveRemark;
	}
	public String getApproveRemark()
	{
		return approveRemark;
	}
	public void setApproveResult(String approveResult)
	{
		changedFiled("approveResult");
		this.approveResult=approveResult;
	}
	public String getApproveResult()
	{
		return approveResult;
	}
	public void setTransferDate(Timestamp transferDate)
	{
		changedFiled("transferDate");
		this.transferDate=transferDate;
	}
	public Timestamp getTransferDate()
	{
		return transferDate;
	}
	public void setStatus(String status)
	{
		changedFiled("status");
		this.status=status;
	}
	public String getStatus()
	{
		return status;
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
	public void setVersion(BigDecimal version)
	{
		changedFiled("version");
		this.version=version;
	}
	public BigDecimal getVersion()
	{
		return version;
	}
}

