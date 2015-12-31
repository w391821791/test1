package com.zty.edu_equip.entity;

   /**
    * ref_equip_move_equip 实体类
    * Mon Nov 16 10:19:08 CST 2015 lp
    */ 

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import com.po.base.AbsDbObj2;
import com.po.base.DbMeta;
import com.po.base.ISeqenceGenerator;
import com.po.serverbase.SeqenceGenerator;

@DbMeta(DB_TABLE="REF_EQUIP_MOVE_EQUIP", DB_PK_COL="REF_ID")
public class RefEquipMoveEquip extends AbsDbObj2
{
	@DbMeta(DB_COL="EQUIP_MOVE_ID")
	private BigDecimal equipMoveId;
	@DbMeta(DB_COL="EQUIP_ID")
	private BigDecimal equipId;
	@DbMeta(DB_COL="STATUS")
	private String status;
	@DbMeta(DB_COL="MOVE_TYPE")
	private String moveType;
	@DbMeta(DB_COL="ROOM_ID1")
	private BigDecimal roomId1;
	@DbMeta(DB_COL="USER_ID1")
	private BigDecimal userId1;
	@DbMeta(DB_COL="USER_NAME1")
	private String userName1;
	@DbMeta(DB_COL="ROOM_ID2")
	private BigDecimal roomId2;
	@DbMeta(DB_COL="USER_ID2")
	private BigDecimal userId2;
	@DbMeta(DB_COL="USER_NAME2")
	private String userName2;
	///构造方法：自动产生主键

	public RefEquipMoveEquip() throws Exception
	{
		super(null, SeqenceGenerator.getInstance());
	}
	///构造方法：如果主键为空，则自动产生主键

	public RefEquipMoveEquip(String pk) throws Exception
	{
		super(pk, SeqenceGenerator.getInstance());
	}
	///不能持久化到数据库中的构造方法：isNeedCreatePk定义是否需要产生主键

	public RefEquipMoveEquip(boolean isNeedCreatePk) throws Exception
	{
		super(isNeedCreatePk);
	}

	public RefEquipMoveEquip(HashMap dataMap) throws Exception
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
	public void setEquipMoveId(BigDecimal equipMoveId)
	{
		changedFiled("equipMoveId");
		this.equipMoveId=equipMoveId;
	}
	public BigDecimal getEquipMoveId()
	{
		return equipMoveId;
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
	public void setStatus(String status)
	{
		changedFiled("status");
		this.status=status;
	}
	public String getStatus()
	{
		return status;
	}
	public void setMoveType(String moveType)
	{
		changedFiled("moveType");
		this.moveType=moveType;
	}
	public String getMoveType()
	{
		return moveType;
	}
	public void setRoomId1(BigDecimal roomId1)
	{
		changedFiled("roomId1");
		this.roomId1=roomId1;
	}
	public BigDecimal getRoomId1()
	{
		return roomId1;
	}
	public void setUserId1(BigDecimal userId1)
	{
		changedFiled("userId1");
		this.userId1=userId1;
	}
	public BigDecimal getUserId1()
	{
		return userId1;
	}
	public void setUserName1(String userName1)
	{
		changedFiled("userName1");
		this.userName1=userName1;
	}
	public String getUserName1()
	{
		return userName1;
	}
	public void setRoomId2(BigDecimal roomId2)
	{
		changedFiled("roomId2");
		this.roomId2=roomId2;
	}
	public BigDecimal getRoomId2()
	{
		return roomId2;
	}
	public void setUserId2(BigDecimal userId2)
	{
		changedFiled("userId2");
		this.userId2=userId2;
	}
	public BigDecimal getUserId2()
	{
		return userId2;
	}
	public void setUserName2(String userName2)
	{
		changedFiled("userName2");
		this.userName2=userName2;
	}
	public String getUserName2()
	{
		return userName2;
	}
}

