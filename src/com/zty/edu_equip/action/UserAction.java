package com.zty.edu_equip.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.po.base.Encrypt;
import com.po.serverbase.BaseDao;
import com.po.serverbase.SeqenceGenerator;
import com.po.serverbase.SystemInfo;
import com.zty.edu_equip.entity.UserInfo;
import com.zty.edu_equip.util.PageUtil;

public class UserAction extends ActionSupport
{
		private UserInfo  user;
		private Map  map;
		private PageUtil pageUtil;
		private Map<String, String[]> pageMap;
		private String url;
		public String jump(){
			return url;
		}
		public String findUser() throws Exception{
			BaseDao baseDao=new BaseDao(SystemInfo.getMainJndi(), null);
			UserInfo sessionUser=(UserInfo) ActionContext.getContext().getSession().get("user");
			String sql="SELECT * FROM USER_INFO WHERE ORGAN_ID="+sessionUser.getOrganId();
			System.out.println(user==null);
			if(null!=user){
				if(!user.getUserName().isEmpty()){
					sql+=" AND USER_NAME like '%"+user.getUserName()+"%'";
				}
			}
			sql += " AND USER_FLAG = 'SCHOOL_USER'";
			if(!pageUtil.getOrderField().isEmpty()){
				sql+=" order by "+pageUtil.getOrderField()+pageUtil.getOrder();
			}
			map=baseDao.getRowsWithPaging(sql,pageUtil.getPage()-1,pageUtil.getLines());
			return SUCCESS;
		}
		public String findUserById() throws Exception{
			BaseDao baseDao=new BaseDao(SystemInfo.getMainJndi(), null);
			user=(UserInfo) baseDao.getObjFromDbById(UserInfo.class, user.getPkValue());
			map=new HashMap();
			map.put("userInfo", user);
			return "edit";
		}
		public String delUser() throws Exception{
			BaseDao baseDao=new BaseDao(SystemInfo.getMainJndi(), null);
			String str=pageMap.get("userIds")[0];
			String[] idArr=str.split(",");
			for(int i=0;i<idArr.length;i++){
				user=(UserInfo) baseDao.getObjFromDbById(UserInfo.class,idArr[i]);
				user.setUserStatus("V");
				baseDao.addObj4Execute(user);
			}
			baseDao.execute();
			return NONE;
		}
		public String resetPass() throws Exception{
			BaseDao baseDao=new BaseDao(SystemInfo.getMainJndi(), null);
			user=(UserInfo) baseDao.getObjFromDbById(UserInfo.class, user.getPkValue());
			String pwd=(int)(Math.random()*1000000)+"";
			user.setUserPwd(Encrypt.encrypt2MD5(user.getPkValue() + "/"+ Encrypt.encrypt2MD5(pwd)));
			baseDao.execute(user);
			map=new HashMap();
			map.put("pwd", pwd);
			return SUCCESS;
		}
		public String recoveryUser() throws Exception{
			BaseDao baseDao=new BaseDao(SystemInfo.getMainJndi(), null);
			String str=pageMap.get("userIds")[0];
			String[] idArr=str.split(",");
			for(int i=0;i<idArr.length;i++){
				user=(UserInfo) baseDao.getObjFromDbById(UserInfo.class,idArr[i]);
				user.setUserStatus("A");
				baseDao.addObj4Execute(user);
			}
			baseDao.execute();
			return NONE;
		}
		public String addUser() throws Exception{
			UserInfo sessionUser= (UserInfo) ActionContext.getContext().getSession().get("user");
			String pwd=(int)(Math.random()*1000000)+"";
			user.setOrganId(sessionUser.getOrganId());
			user.setUserPwd(Encrypt.encrypt2MD5(user.getPkValue() + "/"+ Encrypt.encrypt2MD5(pwd)));
			user.setUserStatus("A");
			user.setUserFlag("SCHOOL_USER");
			BaseDao baseDao=new BaseDao(SystemInfo.getMainJndi(), null);
			baseDao.execute(user);
			map=new HashMap();
			map.put("pwd", pwd);
			return SUCCESS;
		}
		public String editUser() throws Exception{
			BaseDao baseDao=new BaseDao(SystemInfo.getMainJndi(), null);
			baseDao.execute(user);
			return NONE;
		}
		public String checkUserCode() throws Exception{
			BaseDao baseDao=new BaseDao(SystemInfo.getMainJndi(), null);
			List users=baseDao.getObjsFromDbById(UserInfo.class, "USER_CODE='"+user.getUserCode()+"' AND ORGAN_ID = '"+UtilAction.getUser().getOrganId()+"'");
			map=new HashMap();
			if(users.isEmpty()){
				map.put("boo",true);
			}else{
				map.put("boo",false);
			}
			return SUCCESS;
		}
		
		public UserInfo getUser()
		{
			return user;
		}

		public void setUser(UserInfo user)
		{
			this.user = user;
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
		
}
