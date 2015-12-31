package com.zty.edu_equip.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zty.edu_equip.action.UtilAction;
import com.opensymphony.xwork2.ActionSupport;
import com.po.serverbase.BaseDao;
import com.po.serverbase.SystemInfo;


public class RedirectAction extends ActionSupport
{
	private Map map;
	private String IP;
	private String PORT;
	private String keyid;
	private String key;
	private String organ;
	public String redirect() throws Exception{
		System.out.println("½øÈëredirect");
		Date date = new Date();
		map = new HashMap();
		long curDate = date.getTime();
		UtilAction.keyID.put("keyid", keyid);
		UtilAction.keyID.put("curDate", curDate);
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		if(!"".equals(keyid) && null!=keyid && !"cloud".equals(keyid)){
			String sql = "select * from organ where ukey_serial like '%"+keyid+"%'";
			List list = baseDao.query(sql);
			System.out.println(list);
			if(null!=list && list.size()>0){
				Map organMap = (Map) list.get(0);
				UtilAction.keyID.put("organ_id", organMap.get("ORGAN_ID"));
				String sql2 = "select * from user_info where organ_id = '"+organMap.get("ORGAN_ID")+"' and USER_FLAG = 'SCHOOL_USER'";
				List userList = baseDao.query(sql2);
				
				String dkeyidset = organMap.get("UKEY_SERIAL").toString();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date limitDate = df.parse(organMap.get("UKEY_LIFETIME").toString());
				if(compare(dkeyidset,keyid) && (curDate<limitDate.getTime())){
					map.put("curDate",curDate);
					map.put("organID",organMap.get("ORGAN_ID"));
					map.put("keyid",keyid);
					map.put("key",key);

					return "login";
				}
				

				return "error";
			}

			return "error";
		}
		String sql3 = "select * from user_info where USER_FLAG != 'SCHOOL_USER'";
		List userList = baseDao.query(sql3);
		map.put("curDate",curDate);
		map.put("userList",userList);
		return "login";
	}
	private boolean compare(String dkeyidset, String keyid2)
    {
	    // TODO Auto-generated method stub
		boolean flag = false;
		String []dkeyids = dkeyidset.split(",");
		for(String dkeyid : dkeyids){
			if(dkeyid.equals(keyid2)){
				flag = true;
			}
		}
	    return flag;
    }
	public Map getMap()
	{
		return map;
	}
	public void setMap(Map map)
	{
		this.map = map;
	}
	public String getIP()
	{
		return IP;
	}
	public void setIP(String iP)
	{
		IP = iP;
	}
	public String getPORT()
	{
		return PORT;
	}
	public void setPORT(String pORT)
	{
		PORT = pORT;
	}
	public String getKeyid()
	{
		return keyid;
	}
	public void setKeyid(String keyid)
	{
		this.keyid = keyid;
	}
	public String getKey()
	{
		return key;
	}
	public void setKey(String key)
	{
		this.key = key;
	}
	public String getOrgan()
	{
		return organ;
	}
	public void setOrgan(String organ)
	{
		this.organ = organ;
	}
	
	
}
