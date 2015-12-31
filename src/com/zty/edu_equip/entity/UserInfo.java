package com.zty.edu_equip.entity;

   /**
    * user_info 实体类
    * Mon Nov 16 10:19:08 CST 2015 lp
    */ 

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import com.po.base.AbsDbObj2;
import com.po.base.DbMeta;
import com.po.base.ISeqenceGenerator;
import com.po.serverbase.SeqenceGenerator;

@DbMeta(DB_TABLE="USER_INFO", DB_PK_COL="USER_ID")
public class UserInfo extends AbsDbObj2
{
	@DbMeta(DB_COL="ORGAN_ID")
	private BigDecimal organId;
	@DbMeta(DB_COL="USER_CODE")
	private String userCode;
	@DbMeta(DB_COL="USER_NAME")
	private String userName;
	@DbMeta(DB_COL="NICK_NAME")
	private String nickName;
	@DbMeta(DB_COL="USER_PWD")
	private String userPwd;
	@DbMeta(DB_COL="USER_PERMS")
	private String userPerms;
	@DbMeta(DB_COL="APP_DEVICE_CODE")
	private String appDeviceCode;
	@DbMeta(DB_COL="APP_DEVICE_REMARK")
	private String appDeviceRemark;
	@DbMeta(DB_COL="MOBILE_NUMBER")
	private String mobileNumber;
	@DbMeta(DB_COL="USER_STATUS")
	private String userStatus;
	@DbMeta(DB_COL="VERSION")
	private BigDecimal version;
	@DbMeta(DB_COL="USER_FLAG")
	private String userFlag;
	@DbMeta(DB_COL="LAST_CLIENT_IP")
	private String lastClientIp;
	@DbMeta(DB_COL="LAST_CLIENT_PORT")
	private String lastClientPort;
	///构造方法：自动产生主键

	public UserInfo() throws Exception
	{
		super(null, SeqenceGenerator.getInstance());
	}
	///构造方法：如果主键为空，则自动产生主键

	public UserInfo(String pk) throws Exception
	{
		super(pk, SeqenceGenerator.getInstance());
	}
	///不能持久化到数据库中的构造方法：isNeedCreatePk定义是否需要产生主键

	public UserInfo(boolean isNeedCreatePk) throws Exception
	{
		super(isNeedCreatePk);
	}

	public UserInfo(HashMap dataMap) throws Exception
	{
		super(dataMap);
	}
	public void setUserId(String userId)
	{
		setPkValue(userId);
	}
	public String getUserId()
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
	public void setUserCode(String userCode)
	{
		changedFiled("userCode");
		this.userCode=userCode;
	}
	public String getUserCode()
	{
		return userCode;
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
	public void setNickName(String nickName)
	{
		changedFiled("nickName");
		this.nickName=nickName;
	}
	public String getNickName()
	{
		return nickName;
	}
	public void setUserPwd(String userPwd)
	{
		changedFiled("userPwd");
		this.userPwd=userPwd;
	}
	public String getUserPwd()
	{
		return userPwd;
	}
	public void setUserPerms(String userPerms)
	{
		changedFiled("userPerms");
		this.userPerms=userPerms;
	}
	public String getUserPerms()
	{
		return userPerms;
	}
	public void setAppDeviceCode(String appDeviceCode)
	{
		changedFiled("appDeviceCode");
		this.appDeviceCode=appDeviceCode;
	}
	public String getAppDeviceCode()
	{
		return appDeviceCode;
	}
	public void setAppDeviceRemark(String appDeviceRemark)
	{
		changedFiled("appDeviceRemark");
		this.appDeviceRemark=appDeviceRemark;
	}
	public String getAppDeviceRemark()
	{
		return appDeviceRemark;
	}
	public void setMobileNumber(String mobileNumber)
	{
		changedFiled("mobileNumber");
		this.mobileNumber=mobileNumber;
	}
	public String getMobileNumber()
	{
		return mobileNumber;
	}
	public void setUserStatus(String userStatus)
	{
		changedFiled("userStatus");
		this.userStatus=userStatus;
	}
	public String getUserStatus()
	{
		return userStatus;
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
	public void setUserFlag(String userFlag)
	{
		changedFiled("userFlag");
		this.userFlag=userFlag;
	}
	public String getUserFlag()
	{
		return userFlag;
	}
	public void setLastClientIp(String lastClientIp)
	{
		changedFiled("lastClientIp");
		this.lastClientIp=lastClientIp;
	}
	public String getLastClientIp()
	{
		return lastClientIp;
	}
	public void setLastClientPort(String lastClientPort)
	{
		changedFiled("lastClientPort");
		this.lastClientPort=lastClientPort;
	}
	public String getLastClientPort()
	{
		return lastClientPort;
	}
}

