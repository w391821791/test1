package com.zty.edu_equip.entity;

   /**
    * card_log 实体类
    * Mon Nov 16 10:19:08 CST 2015 lp
    */ 

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import com.po.base.AbsDbObj2;
import com.po.base.DbMeta;
import com.po.base.ISeqenceGenerator;
import com.po.serverbase.SeqenceGenerator;

@DbMeta(DB_TABLE="CARD_LOG", DB_PK_COL="CARD_LOG_ID")
public class CardLog extends AbsDbObj2
{
	@DbMeta(DB_COL="EQUIP_ID")
	private BigDecimal equipId;
	@DbMeta(DB_COL="ROOM_ID")
	private BigDecimal roomId;
	@DbMeta(DB_COL="CARD_NO")
	private String cardNo;
	@DbMeta(DB_COL="PRINT_DATE")
	private Timestamp printDate;
	@DbMeta(DB_COL="PASTE_DATE")
	private Timestamp pasteDate;
	@DbMeta(DB_COL="STATUS")
	private String status;
	///构造方法：自动产生主键

	public CardLog() throws Exception
	{
		super(null, SeqenceGenerator.getInstance());
	}
	///构造方法：如果主键为空，则自动产生主键

	public CardLog(String pk) throws Exception
	{
		super(pk, SeqenceGenerator.getInstance());
	}
	///不能持久化到数据库中的构造方法：isNeedCreatePk定义是否需要产生主键

	public CardLog(boolean isNeedCreatePk) throws Exception
	{
		super(isNeedCreatePk);
	}

	public CardLog(HashMap dataMap) throws Exception
	{
		super(dataMap);
	}
	public void setCardLogId(String cardLogId)
	{
		setPkValue(cardLogId);
	}
	public String getCardLogId()
	{
		return getPkValue();
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
	public void setRoomId(BigDecimal roomId)
	{
		changedFiled("roomId");
		this.roomId=roomId;
	}
	public BigDecimal getRoomId()
	{
		return roomId;
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
	public void setPrintDate(Timestamp printDate)
	{
		changedFiled("printDate");
		this.printDate=printDate;
	}
	public Timestamp getPrintDate()
	{
		return printDate;
	}
	public void setPasteDate(Timestamp pasteDate)
	{
		changedFiled("pasteDate");
		this.pasteDate=pasteDate;
	}
	public Timestamp getPasteDate()
	{
		return pasteDate;
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
}

