package com.zty.edu_equip.entity;

   /**
    * room_type 实体类
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
	///构造方法：自动产生主键

	public RoomType() throws Exception
	{
		super(null, SeqenceGenerator.getInstance());
	}
	///构造方法：如果主键为空，则自动产生主键

	public RoomType(String pk) throws Exception
	{
		super(pk, SeqenceGenerator.getInstance());
	}
	///不能持久化到数据库中的构造方法：isNeedCreatePk定义是否需要产生主键

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

