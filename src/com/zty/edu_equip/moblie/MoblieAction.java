package com.zty.edu_equip.moblie;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.registry.infomodel.User;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.po.base.Encrypt;
import com.po.base.IDbObj;
import com.po.base.Util;
import com.po.serverbase.BaseDao;
import com.po.serverbase.SeqenceGenerator;
import com.po.serverbase.SysConfig;
import com.po.serverbase.SystemInfo;
import com.zty.edu_equip.action.UtilAction;
import com.zty.edu_equip.entity.Equip;
import com.zty.edu_equip.entity.EquipInventory;
import com.zty.edu_equip.entity.EquipMove;
import com.zty.edu_equip.entity.RefEquipInventory;
import com.zty.edu_equip.entity.RefEquipMoveEquip;
import com.zty.edu_equip.entity.Room;
import com.zty.edu_equip.entity.UserInfo;
import com.zty.edu_equip.util.PageUtil;

public class MoblieAction extends ActionSupport
{
	private String account;
	private String password;
	private String organId;
	private String inventoryId;
	private String roomId;
	private String equipId;
	private String cardNo;
	private String roomNo;
	private String userId;
	private String userName;
	private String applyRemark;
	private String refId;
	private String num;
	private String optStatus;
	private Map map;
	private PageUtil pageUtil;
	private Room room;

	public String login() throws Exception
	{
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		map = new HashMap();
		List list = baseDao.getObjsFromDbById(UserInfo.class, " USER_CODE='"
		        + account + "' AND USER_STATUS='A'");
		map.put("boo", false);
		if (null != list && !list.isEmpty())
		{
			UserInfo user=null;
			int count=0;
			for(int i=0;i<list.size();i++){
				user = (UserInfo) list.get(i);
				System.out.println(Encrypt.encrypt2MD5(user.getPkValue() + "/" + Encrypt.encrypt2MD5(password))+"\t"+user.getUserPwd());
				System.out.println(Encrypt.encrypt2MD5(user.getPkValue() + "/" + Encrypt.encrypt2MD5(password)).equals(user.getUserPwd()));
				if (Encrypt.encrypt2MD5(
						user.getPkValue() + "/" + Encrypt.encrypt2MD5(password))
						.equals(user.getUserPwd())) {
					count++;
				}
			}
			if (count==0) {
				map.put("msg", "密码错误，请重新输入！注意大小写。");
				return SUCCESS;
			} else if(count>1){
				map.put("msg", "当前密码不可用。请联系管理员重置密码");
				return SUCCESS;
			}else{
				UserInfo loginUser = (UserInfo) list.get(0);
				ActionContext.getContext().getSession()
				        .put("moblieUser", loginUser);
				map.put("boo", true);
				map.put("user", loginUser);
				return SUCCESS;
			}
		}
		map.put("msg", "找不到该用户");
		return SUCCESS;
	}

	public String findRoom() throws Exception
	{

		String sql = "select * from room r  WHERE r.ORGAN_ID=" + organId;
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		if (null != room)
		{
			if (!room.getRoomName().isEmpty())
			{
				sql += " AND ROOM_NAME like '%" + room.getRoomName() + "%'";
			}
		}
		sql += " order by r.room_name";
		map = new HashMap();
		map.put("ROWS", baseDao.query(sql));
		return SUCCESS;
	}

	public String findEquipByRoomId() throws Exception
	{
		String sql = "select e.*,et.ASSET_TYPE_NAME,r.ROOM_NAME "
		        + " from equip e left join equip_type et on  e.EQUIP_TYPE_ID=et.EQUIP_TYPE_ID "
		        + " inner join room r on r.ROOM_ID=e.ROOM_ID WHERE e.EQUIP_STATUS='A' AND r.ROOM_ID="
		        + roomId;
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		map = new HashMap();
		map.put("ROWS", baseDao.query(sql));
		return SUCCESS;
	}

	public String findEquipById() throws Exception
	{
		String sql = " select e.*,et.ASSET_TYPE_NAME,r.ROOM_NAME,rm.STATUS "
		        + " from equip e left join equip_type et on  e.EQUIP_TYPE_ID=et.EQUIP_TYPE_ID "
		        + " inner join room r on r.ROOM_ID=e.ROOM_ID "
		        + " left join REF_EQUIP_MOVE_EQUIP rm on rm.EQUIP_ID=e.EQUIP_ID"
		        + " WHERE e.EQUIP_STATUS='A' AND e.EQUIP_ID=" + equipId;
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		List list = baseDao.query(sql);
		map = new HashMap();
		if (null != list && !list.isEmpty())
		{
			map.put("equipInfo", list.get(0));
		}
		return SUCCESS;
	}

	public String findEquipByNo() throws Exception
	{
		String sql = " select e.*,et.ASSET_TYPE_NAME,r.ROOM_NAME,rm.STATUS "
		        + " from equip e left join equip_type et on  e.EQUIP_TYPE_ID=et.EQUIP_TYPE_ID "
		        + " inner join room r on r.ROOM_ID=e.ROOM_ID "
		        + " left join REF_EQUIP_MOVE_EQUIP rm on rm.EQUIP_ID=e.EQUIP_ID"
		        + " WHERE e.EQUIP_STATUS='A' AND e.CARD_NO=" + cardNo;
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		List list = baseDao.query(sql);
		map = new HashMap();
		if (null != list && !list.isEmpty())
		{
			map.put("equipInfo", list.get(0));
		}
		return SUCCESS;
	}

	public String findEquipByRoomNo() throws Exception
	{
		String sql = "select e.*,et.ASSET_TYPE_NAME,r.ROOM_NAME "
		        + " from equip e left join equip_type et on  e.EQUIP_TYPE_ID=et.EQUIP_TYPE_ID "
		        + " inner join room r on r.ROOM_ID=e.ROOM_ID WHERE e.EQUIP_STATUS='A' AND r.CARD_NO="
		        + roomNo;
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		map = new HashMap();
		map.put("ROWS", baseDao.query(sql));
		return SUCCESS;
	}

	public String addEquipMove() throws Exception
	{
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		Equip equip = (Equip) baseDao.getObjFromDbById(Equip.class, equipId);
		EquipMove em = new EquipMove();
		em.setMoveType("B");
		em.setApplyerId(new BigDecimal(userId));
		em.setApplyTime(Util.getNow());
		em.setApplyRemark(applyRemark);
		em.setApproveReamrk("");
		em.setStatus("1");
		em.setVersion(SeqenceGenerator.getNextOutranetVer());
		RefEquipMoveEquip refME = new RefEquipMoveEquip();
		refME.setEquipMoveId(new BigDecimal(em.getPkValue()));
		refME.setEquipId(new BigDecimal(equipId));
		refME.setStatus("A");
		refME.setMoveType("B");
		refME.setRoomId1(equip.getRoomId());
		refME.setUserName1(equip.getUserName());
		refME.setRoomId2(new BigDecimal(roomId));
		refME.setUserName2(userName);
		baseDao.addObj4Execute(em);
		baseDao.addObj4Execute(refME);
		baseDao.execute();
		return SUCCESS;
	}

	public String moblieVersion()
	{
		String appVerion = SysConfig.getAttrValue("AppVersion");
		map = new HashMap();
		map.put("version", appVerion);
		return SUCCESS;
	}

	public String updateMoblie() throws Exception
	{
		String filename = SysConfig.getAttrValue("AppApkFilePath");
		ServletOutputStream sos = null;
		FileInputStream fis = null;
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");
		response.setHeader("content-disposition", "attachment;filename="
		        + URLEncoder.encode("教育装备.wgt", "UTF-8"));
		fis = new FileInputStream(filename);

		// 建立文件上传流
		sos = response.getOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = fis.read(buffer)) > 0)
		{
			sos.write(buffer, 0, len);
		}
		sos.close();
		fis.close();
		return null;
	}

	public String findInventory() throws Exception
	{
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		List inventorys = baseDao.getObjsFromDbById(EquipInventory.class,
		        "ORGAN_ID=" + organId);
		map = new HashMap();
		map.put("ROWS", inventorys);
		return SUCCESS;
	}

	public String findEquipByInventory() throws Exception
	{
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql = "select rei.*,e.EQUIP_NAME,e.COUNT,et.ASSET_TYPE_NAME,"
		        + " room.ROOM_NAME from REF_EQUIP_INVENTORY rei,EQUIP e"
		        + " left join EQUIP_TYPE et on et.EQUIP_TYPE_ID=e.EQUIP_TYPE_ID"
		        + " left join ROOM  room on room.ROOM_ID=e.ROOM_ID"
		        + " where e.EQUIP_ID=rei.EQUIP_ID and rei.INVENTORY_ID="
		        + inventoryId;
		map = new HashMap();
		map.put("ROWS", baseDao.query(sql));
		return SUCCESS;
	}

	public String updateRefInventory() throws Exception
	{
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		RefEquipInventory rei = (RefEquipInventory) baseDao.getObjFromDbById(
		        RefEquipInventory.class, refId);
		if ("N".equals(optStatus))
		{
			rei.setCount2(new BigDecimal(0));
		}
		else
		{
			rei.setCount2(new BigDecimal(num));
		}
		rei.setBalance(new BigDecimal(rei.getCount2().intValue()
		        - rei.getCount1().intValue()));
		baseDao.execute(rei);
		checkInventoryStatus(rei.getInventoryId()+"");
		return SUCCESS;
	}

	public void checkInventoryStatus(String invenId) throws Exception
	{
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		List reis=baseDao.getObjsFromDbById(RefEquipInventory.class,
				" COUNT2 is null AND INVENTORY_ID="+invenId);
		EquipInventory einv = (EquipInventory) baseDao.getObjFromDbById(
		        EquipInventory.class, invenId);
		if (null != reis && reis.size() > 0)
		{
			einv.setOptStatus("1");
		}
		else
		{
			einv.setOptStatus("2");
		}
		baseDao.execute(einv);
	}

	public String upInventoryByNo() throws Exception
	{
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		List<IDbObj> equips = baseDao.getObjsFromDbById(Equip.class,
		        " EQUIP_STATUS='A' AND CARD_NO=" + cardNo);
		if (null != equips && null != equips.get(0))
		{
			Equip equip = (Equip) equips.get(0);
			List<IDbObj> reis = baseDao.getObjsFromDbById(
			        RefEquipInventory.class, " INVENTORY_ID=" + inventoryId
			                + " AND EQUIP_ID=" + equip.getPkValue());
			if (null != reis && null != reis.get(0))
			{
				RefEquipInventory rei = (RefEquipInventory) reis.get(0);
				rei.setCount2(rei.getCount1());
				rei.setBalance(new BigDecimal(0));
				rei.setOperatorId(new BigDecimal(userId));
				baseDao.execute(rei);
				checkInventoryStatus(rei.getInventoryId()+"");
				return SUCCESS;
			}
			return ERROR;
		}
		else
		{
			return ERROR;
		}
	}

	public Room getRoom()
	{
		return room;
	}

	public void setRoom(Room room)
	{
		this.room = room;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public Map getMap()
	{
		return map;
	}

	public void setMap(Map map)
	{
		this.map = map;
	}

	public PageUtil getPageUtil()
	{
		return pageUtil;
	}

	public void setPageUtil(PageUtil pageUtil)
	{
		this.pageUtil = pageUtil;
	}

	public void setOrganId(String organId)
	{
		this.organId = organId;
	}

	public void setRoomId(String roomId)
	{
		this.roomId = roomId;
	}

	public void setEquipId(String equipId)
	{
		this.equipId = equipId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public void setApplyRemark(String applyRemark)
	{
		this.applyRemark = applyRemark;
	}

	public void setCardNo(String cardNo)
	{
		this.cardNo = cardNo;
	}

	public void setRoomNo(String roomNo)
	{
		this.roomNo = roomNo;
	}

	public void setInventoryId(String inventoryId)
	{
		this.inventoryId = inventoryId;
	}

	public void setRefId(String refId)
	{
		this.refId = refId;
	}

	public void setNum(String num)
	{
		this.num = num;
	}

	public void setOptStatus(String optStatus)
	{
		this.optStatus = optStatus;
	}

}
