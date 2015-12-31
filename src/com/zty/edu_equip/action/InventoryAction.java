package com.zty.edu_equip.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.po.base.Util;
import com.po.serverbase.BaseDao;
import com.po.serverbase.SeqenceGenerator;
import com.po.serverbase.SystemInfo;
import com.zty.edu_equip.entity.Equip;
import com.zty.edu_equip.entity.EquipInventory;
import com.zty.edu_equip.entity.RefEquipInventory;
import com.zty.edu_equip.util.PageUtil;

public class InventoryAction extends ActionSupport
{

	private Map map;
	private String url;
	private PageUtil pageUtil;
	private Map<String, String[]> pageMap;
	private EquipInventory eInventory;
	private String boxValue;
	private String refId;
	private String num;
	private String optStatus;

	public String findInventory() throws Exception
	{
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql = "SELECT * FROM EQUIP_INVENTORY WHERE ORGAN_ID="
		        + UtilAction.getUser().getOrganId();
		if (null != eInventory)
		{
			System.out.println(eInventory.getOptTitle());
			if (null != eInventory.getOptTitle()
			        && !"".equals(eInventory.getOptTitle()))
			{
				sql += " AND OPT_TITLE LIKE '%" + eInventory.getOptTitle()
				        + "%'";
			}
			if (null != eInventory.getOptType()
			        && !"".equals(eInventory.getOptType()))
			{
				sql += " AND OPT_TYPE='" + eInventory.getOptType() + "'";
			}
			if (null != eInventory.getOptStatus()
			        && !"".equals(eInventory.getOptStatus()))
			{

				sql += " AND OPT_STATUS='" + eInventory.getOptStatus() + "'";
			}
		}
		if (!pageUtil.getOrderField().isEmpty())
		{
			sql += " order by " + pageUtil.getOrderField()
			        + pageUtil.getOrder();
		}
		map = baseDao.getRowsWithPaging(sql, pageUtil.getPage() - 1,
		        pageUtil.getLines());
		return SUCCESS;
	}

	public String saveInventory() throws Exception
	{
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		eInventory.setOptDate(Util.getNow());
		eInventory.setOrganId(UtilAction.getUser().getOrganId());
		eInventory.setOrganId(UtilAction.getUser().getOrganId());
		eInventory.setOptStatus(null == eInventory.getOptStatus() ? "1"
		        : eInventory.getOptStatus());
		eInventory.setVersion(SeqenceGenerator.getNextOutranetVer());
		baseDao.addObj4Execute(eInventory);
		if (null != boxValue && !"".equals(boxValue))
		{
			String[] arr = boxValue.split(",");
			for (int i = 0; i < arr.length; i++)
			{
				RefEquipInventory rei = new RefEquipInventory();
				Equip equip = (Equip) baseDao.getObjFromDbById(Equip.class,
				        arr[i]);
				rei.setEquipId(new BigDecimal(arr[i]));
				rei.setCount1(equip.getCount());
				rei.setInventoryId(new BigDecimal(eInventory.getPkValue()));
				baseDao.addObj4Execute(rei);
			}
		}
		baseDao.execute();
		return SUCCESS;
	}

	public String findInventoryById() throws Exception
	{
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		eInventory = (EquipInventory) baseDao.getObjFromDbById(
		        EquipInventory.class, eInventory.getPkValue());
		map = new HashMap();
		map.put("eInventory", eInventory);
		return "add";
	}

	public String findEquipByInventory() throws Exception
	{
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql = "select rei.*,e.EQUIP_NAME,e.COUNT,et.ASSET_TYPE_NAME,"
		        + " room.ROOM_NAME from REF_EQUIP_INVENTORY rei,EQUIP e"
		        + " left join EQUIP_TYPE et on et.EQUIP_TYPE_ID=e.EQUIP_TYPE_ID"
		        + " left join ROOM  room on room.ROOM_ID=e.ROOM_ID"
		        + " where e.EQUIP_ID=rei.EQUIP_ID and rei.INVENTORY_ID="
		        + eInventory.getPkValue();
		if (!pageUtil.getOrderField().isEmpty())
		{
			sql += " order by " + pageUtil.getOrderField()
			        + pageUtil.getOrder();
		}
		map = baseDao.getRowsWithPaging(sql, pageUtil.getPage() - 1,
		        pageUtil.getLines());
		return SUCCESS;
	}

	public String updateRefInventory() throws Exception
	{
		BaseDao baseDao=new BaseDao(SystemInfo.getMainJndi(), null);
		RefEquipInventory rei=(RefEquipInventory) baseDao.getObjFromDbById(RefEquipInventory.class, refId);
		if("N".equals(optStatus)){
			rei.setCount2(new BigDecimal(0));
		}else{
			rei.setCount2(new BigDecimal(num));
		}
		rei.setOperatorId(UtilAction.getUser().getOrganId());
		rei.setBalance(new BigDecimal(rei.getCount2().intValue()-rei.getCount1().intValue()));
		baseDao.execute(rei);
		List reis=baseDao.getObjsFromDbById(RefEquipInventory.class,
					" COUNT2 is null AND INVENTORY_ID="+rei.getInventoryId());
		EquipInventory einv=(EquipInventory) baseDao.getObjFromDbById(EquipInventory.class,rei.getInventoryId()+"");
		if(null!=reis && reis.size()>0){
			einv.setOptStatus("1");
		}else{
			einv.setOptStatus("2");
		}
		baseDao.execute(einv);
		return SUCCESS;
	}

	public String jump()
	{
		return url;
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

	public Map<String, String[]> getPageMap()
	{
		return pageMap;
	}

	public void setPageMap(Map<String, String[]> pageMap)
	{
		this.pageMap = pageMap;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public void seteInventory(EquipInventory eInventory)
	{
		this.eInventory = eInventory;
	}

	public EquipInventory geteInventory()
	{
		return eInventory;
	}

	public void setBoxValue(String boxValue)
	{
		this.boxValue = boxValue;
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
