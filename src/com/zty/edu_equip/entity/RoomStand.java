package com.zty.edu_equip.entity;

   /**
    * room_stand ʵ����
    * Mon Nov 16 10:19:08 CST 2015 lp
    */ 

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import com.po.base.AbsDbObj2;
import com.po.base.DbMeta;
import com.po.base.ISeqenceGenerator;
import com.po.serverbase.SeqenceGenerator;

@DbMeta(DB_TABLE="ROOM_STAND", DB_PK_COL="ROOM_STAND_ID")
public class RoomStand extends AbsDbObj2
{
	@DbMeta(DB_COL="ROOM_TYPE_ID")
	private BigDecimal roomTypeId;
	@DbMeta(DB_COL="SCAL_ID")
	private BigDecimal scalId;
	@DbMeta(DB_COL="ROOM_AREA")
	private String roomArea;
	@DbMeta(DB_COL="ROOM_COUNT")
	private BigDecimal roomCount;
	@DbMeta(DB_COL="ROOM_COUNT2")
	private BigDecimal roomCount2;
	@DbMeta(DB_COL="REINDEX")
	private String reindex;
	///���췽�����Զ���������

	public RoomStand() throws Exception
	{
		super(null, SeqenceGenerator.getInstance());
	}
	///���췽�����������Ϊ�գ����Զ���������

	public RoomStand(String pk) throws Exception
	{
		super(pk, SeqenceGenerator.getInstance());
	}
	///���ܳ־û������ݿ��еĹ��췽����isNeedCreatePk�����Ƿ���Ҫ��������

	public RoomStand(boolean isNeedCreatePk) throws Exception
	{
		super(isNeedCreatePk);
	}

	public RoomStand(HashMap dataMap) throws Exception
	{
		super(dataMap);
	}
	public void setRoomStandId(String roomStandId)
	{
		setPkValue(roomStandId);
	}
	public String getRoomStandId()
	{
		return getPkValue();
	}
	public void setRoomTypeId(BigDecimal roomTypeId)
	{
		changedFiled("roomTypeId");
		this.roomTypeId=roomTypeId;
	}
	public BigDecimal getRoomTypeId()
	{
		return roomTypeId;
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
	public void setRoomArea(String roomArea)
	{
		changedFiled("roomArea");
		this.roomArea=roomArea;
	}
	public String getRoomArea()
	{
		return roomArea;
	}
	public void setRoomCount(BigDecimal roomCount)
	{
		changedFiled("roomCount");
		this.roomCount=roomCount;
	}
	public BigDecimal getRoomCount()
	{
		return roomCount;
	}
	public void setRoomCount2(BigDecimal roomCount2)
	{
		changedFiled("roomCount2");
		this.roomCount2=roomCount2;
	}
	public BigDecimal getRoomCount2()
	{
		return roomCount2;
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
}

