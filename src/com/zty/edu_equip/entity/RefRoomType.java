package com.zty.edu_equip.entity;

   /**
    * ref_room_type 实体类
    * Mon Nov 16 10:19:08 CST 2015 lp
    */ 

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import com.po.base.AbsDbObj2;
import com.po.base.DbMeta;
import com.po.base.ISeqenceGenerator;
import com.po.serverbase.SeqenceGenerator;

@DbMeta(DB_TABLE="REF_ROOM_TYPE", DB_PK_COL="REF_ID")
public class RefRoomType extends AbsDbObj2
{
	@DbMeta(DB_COL="ROOM_ID")
	private BigDecimal roomId;
	@DbMeta(DB_COL="ROOM_TYPE_ID")
	private BigDecimal roomTypeId;
	///构造方法：自动产生主键

	public RefRoomType() throws Exception
	{
		super(null, SeqenceGenerator.getInstance());
	}
	///构造方法：如果主键为空，则自动产生主键

	public RefRoomType(String pk) throws Exception
	{
		super(pk, SeqenceGenerator.getInstance());
	}
	///不能持久化到数据库中的构造方法：isNeedCreatePk定义是否需要产生主键

	public RefRoomType(boolean isNeedCreatePk) throws Exception
	{
		super(isNeedCreatePk);
	}

	public RefRoomType(HashMap dataMap) throws Exception
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
	public void setRoomId(BigDecimal roomId)
	{
		changedFiled("roomId");
		this.roomId=roomId;
	}
	public BigDecimal getRoomId()
	{
		return roomId;
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
}

