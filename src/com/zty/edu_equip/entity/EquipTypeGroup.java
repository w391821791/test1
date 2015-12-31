package com.zty.edu_equip.entity;

   /**
    * equip_type_group 实体类
    * Mon Nov 16 10:19:08 CST 2015 lp
    */ 

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import com.po.base.AbsDbObj2;
import com.po.base.DbMeta;
import com.po.base.ISeqenceGenerator;
import com.po.serverbase.SeqenceGenerator;

@DbMeta(DB_TABLE="EQUIP_TYPE_GROUP", DB_PK_COL="GROUP_ID")
public class EquipTypeGroup extends AbsDbObj2
{
	@DbMeta(DB_COL="PARENT_GROUP_ID")
	private BigDecimal parentGroupId;
	@DbMeta(DB_COL="GROUP_NAME")
	private String groupName;
	///构造方法：自动产生主键

	public EquipTypeGroup() throws Exception
	{
		super(null, SeqenceGenerator.getInstance());
	}
	///构造方法：如果主键为空，则自动产生主键

	public EquipTypeGroup(String pk) throws Exception
	{
		super(pk, SeqenceGenerator.getInstance());
	}
	///不能持久化到数据库中的构造方法：isNeedCreatePk定义是否需要产生主键

	public EquipTypeGroup(boolean isNeedCreatePk) throws Exception
	{
		super(isNeedCreatePk);
	}

	public EquipTypeGroup(HashMap dataMap) throws Exception
	{
		super(dataMap);
	}
	public void setGroupId(String groupId)
	{
		setPkValue(groupId);
	}
	public String getGroupId()
	{
		return getPkValue();
	}
	public void setParentGroupId(BigDecimal parentGroupId)
	{
		changedFiled("parentGroupId");
		this.parentGroupId=parentGroupId;
	}
	public BigDecimal getParentGroupId()
	{
		return parentGroupId;
	}
	public void setGroupName(String groupName)
	{
		changedFiled("groupName");
		this.groupName=groupName;
	}
	public String getGroupName()
	{
		return groupName;
	}
}

