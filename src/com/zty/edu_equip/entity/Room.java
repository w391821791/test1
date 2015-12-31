package com.zty.edu_equip.entity;

   /**
    * room ʵ����
    * Mon Nov 16 10:19:08 CST 2015 lp
    */ 

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import com.po.base.AbsDbObj2;
import com.po.base.DbMeta;
import com.po.base.ISeqenceGenerator;
import com.po.serverbase.SeqenceGenerator;

@DbMeta(DB_TABLE="ROOM", DB_PK_COL="ROOM_ID")
public class Room extends AbsDbObj2
{
	@DbMeta(DB_COL="ORGAN_ID")
	private BigDecimal organId;
	@DbMeta(DB_COL="ROOM_NAME")
	private String roomName;
	@DbMeta(DB_COL="ROOM_NO")
	private String roomNo;
	@DbMeta(DB_COL="CARD_NO")
	private String cardNo;
	@DbMeta(DB_COL="DUTY_MAN")
	private String dutyMan;
	@DbMeta(DB_COL="FEE_SOURCE")
	private String feeSource;
	@DbMeta(DB_COL="AREA")
	private String area;
	@DbMeta(DB_COL="PERSON_COUNT")
	private BigDecimal personCount;
	@DbMeta(DB_COL="REMARK")
	private String remark;
	@DbMeta(DB_COL="REINDEX")
	private String reindex;
	///���췽�����Զ���������

	public Room() throws Exception
	{
		super(null, SeqenceGenerator.getInstance());
	}
	///���췽�����������Ϊ�գ����Զ���������

	public Room(String pk) throws Exception
	{
		super(pk, SeqenceGenerator.getInstance());
	}
	///���ܳ־û������ݿ��еĹ��췽����isNeedCreatePk�����Ƿ���Ҫ��������

	public Room(boolean isNeedCreatePk) throws Exception
	{
		super(isNeedCreatePk);
	}

	public Room(HashMap dataMap) throws Exception
	{
		super(dataMap);
	}
	public void setRoomId(String roomId)
	{
		setPkValue(roomId);
	}
	public String getRoomId()
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
	public void setRoomName(String roomName)
	{
		changedFiled("roomName");
		this.roomName=roomName;
	}
	public String getRoomName()
	{
		return roomName;
	}
	public void setRoomNo(String roomNo)
	{
		changedFiled("roomNo");
		this.roomNo=roomNo;
	}
	public String getRoomNo()
	{
		return roomNo;
	}
	public void setCardNo(String cardNo)
	{
		changedFiled("cardNo");
		this.cardNo=cardNo;
	}
	public String getCardNo()
	{
		return cardNo;
	}
	public void setDutyMan(String dutyMan)
	{
		changedFiled("dutyMan");
		this.dutyMan=dutyMan;
	}
	public String getDutyMan()
	{
		return dutyMan;
	}
	public void setFeeSource(String feeSource)
	{
		changedFiled("feeSource");
		this.feeSource=feeSource;
	}
	public String getFeeSource()
	{
		return feeSource;
	}
	public void setArea(String area)
	{
		changedFiled("area");
		this.area=area;
	}
	public String getArea()
	{
		return area;
	}
	public void setPersonCount(BigDecimal personCount)
	{
		changedFiled("personCount");
		this.personCount=personCount;
	}
	public BigDecimal getPersonCount()
	{
		return personCount;
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

