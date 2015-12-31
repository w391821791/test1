package com.zty.edu_equip.entity;

   /**
    * room_type ʵ����
    * Mon Nov 16 10:19:08 CST 2015 lp
    */ 

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import com.po.base.AbsDbObj2;
import com.po.base.DbMeta;
import com.po.base.ISeqenceGenerator;
import com.po.serverbase.SeqenceGenerator;

@DbMeta(DB_TABLE="ROOM_TYPE", DB_PK_COL="ROOM_TYPE_ID")
public class RoomType extends AbsDbObj2
{
	@DbMeta(DB_COL="ROOM_TYPE_NAME")
	private String roomTypeName;
	@DbMeta(DB_COL="REINDEX")
	private String reindex;
	///���췽�����Զ���������

	public RoomType() throws Exception
	{
		super(null, SeqenceGenerator.getInstance());
	}
	///���췽�����������Ϊ�գ����Զ���������

	public RoomType(String pk) throws Exception
	{
		super(pk, SeqenceGenerator.getInstance());
	}
	///���ܳ־û������ݿ��еĹ��췽����isNeedCreatePk�����Ƿ���Ҫ��������

	public RoomType(boolean isNeedCreatePk) throws Exception
	{
		super(isNeedCreatePk);
	}

	public RoomType(HashMap dataMap) throws Exception
	{
		super(dataMap);
	}
	public void setRoomTypeId(String roomTypeId)
	{
		setPkValue(roomTypeId);
	}
	public String getRoomTypeId()
	{
		return getPkValue();
	}
	public void setRoomTypeName(String roomTypeName)
	{
		changedFiled("roomTypeName");
		this.roomTypeName=roomTypeName;
	}
	public String getRoomTypeName()
	{
		return roomTypeName;
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

