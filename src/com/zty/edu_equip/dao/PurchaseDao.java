package com.zty.edu_equip.dao;

import java.util.List;

import com.po.serverbase.BaseDao;
import com.po.serverbase.SystemInfo;

public class PurchaseDao
{
	public List getRoom(String organID) throws Exception{
		BaseDao baseDao=new BaseDao(SystemInfo.getMainJndi(), null);
		String sql = "SELECT * FROM ROOM WHERE ORGAN_ID = '"+organID+"'";
		List list = baseDao.query(sql);
		return list;
	}
}
