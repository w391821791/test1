package com.zty.edu_equip.entity;

   /**
    * equip_dispose 实体类
    * Mon Nov 16 10:19:08 CST 2015 lp
    */ 

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import com.po.base.AbsDbObj2;
import com.po.base.DbMeta;
import com.po.base.ISeqenceGenerator;
import com.po.serverbase.SeqenceGenerator;

@DbMeta(DB_TABLE="EQUIP_DISPOSE", DB_PK_COL="EQUIP_DISPOSE_ID")
public class EquipDispose extends AbsDbObj2
{
	@DbMeta(DB_COL="ORGAN_ID")
	private BigDecimal organId;
	@DbMeta(DB_COL="APPLYER_ID")
	private BigDecimal applyerId;
	@DbMeta(DB_COL="APPLY_TIME")
	private Timestamp applyTime;
	@DbMeta(DB_COL="APPLY_REMARK")
	private String applyRemark;
	@DbMeta(DB_COL="DISPOSE_REASON")
	private String disposeReason;
	@DbMeta(DB_COL="DISPOSE_TYPE")
	private String disposeType;
	@DbMeta(DB_COL="APPROVER_ID")
	private BigDecimal approverId;
	@DbMeta(DB_COL="APPROVE_TIME")
	private Timestamp approveTime;
	@DbMeta(DB_COL="APPROVE_RESULT")
	private String approveResult;
	@DbMeta(DB_COL="APPROVE_REAMRK")
	private String approveReamrk;
	@DbMeta(DB_COL="DISPOSE_DATE")
	private Timestamp disposeDate;
	@DbMeta(DB_COL="STATUS")
	private String status;
	@DbMeta(DB_COL="VERSION")
	private BigDecimal version;
	@DbMeta(DB_COL="VALID_FLAG")
	private String validFlag;
	///构造方法：自动产生主键

	public EquipDispose() throws Exception
	{
		super(null, SeqenceGenerator.getInstance());
	}
	///构造方法：如果主键为空，则自动产生主键

	public EquipDispose(String pk) throws Exception
	{
		super(pk, SeqenceGenerator.getInstance());
	}
	///不能持久化到数据库中的构造方法：isNeedCreatePk定义是否需要产生主键

	public EquipDispose(boolean isNeedCreatePk) throws Exception
	{
		super(isNeedCreatePk);
	}

	public EquipDispose(HashMap dataMap) throws Exception
	{
		super(dataMap);
	}
	public void setEquipDisposeId(String equipDisposeId)
	{
		setPkValue(equipDisposeId);
	}
	public String getEquipDisposeId()
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
	public void setDisposeReason(String disposeReason)
	{
		changedFiled("disposeReason");
		this.disposeReason=disposeReason;
	}
	public String getDisposeReason()
	{
		return disposeReason;
	}
	public void setDisposeType(String disposeType)
	{
		changedFiled("disposeType");
		this.disposeType=disposeType;
	}
	public String getDisposeType()
	{
		return disposeType;
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
	public void setApproveResult(String approveResult)
	{
		changedFiled("approveResult");
		this.approveResult=approveResult;
	}
	public String getApproveResult()
	{
		return approveResult;
	}
	public void setApproveReamrk(String approveReamrk)
	{
		changedFiled("approveReamrk");
		this.approveReamrk=approveReamrk;
	}
	public String getApproveReamrk()
	{
		return approveReamrk;
	}
	public void setDisposeDate(Timestamp disposeDate)
	{
		changedFiled("disposeDate");
		this.disposeDate=disposeDate;
	}
	public Timestamp getDisposeDate()
	{
		return disposeDate;
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
	public void setVersion(BigDecimal version)
	{
		changedFiled("version");
		this.version=version;
	}
	public BigDecimal getVersion()
	{
		return version;
	}
	public void setValidFlag(String validFlag)
	{
		changedFiled("validFlag");
		this.validFlag=validFlag;
	}
	public String getValidFlag()
	{
		return validFlag;
	}
}

