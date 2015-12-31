package com.zty.edu_equip.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.opensymphony.xwork2.ActionSupport;
import com.po.base.IDbObj;
import com.po.base.Util;
import com.po.serverbase.BaseDao;
import com.po.serverbase.PageListData;
import com.po.serverbase.SeqenceGenerator;
import com.po.serverbase.SqlWhereHelper;
import com.po.serverbase.SysConfig;
import com.po.serverbase.SystemInfo;

import com.zty.edu_equip.entity.Equip;
import com.zty.edu_equip.entity.EquipDispose;
import com.zty.edu_equip.entity.RefEquipDispose;
import com.zty.edu_equip.entity.RefEquipMoveEquip;
import com.zty.edu_equip.entity.UserInfo;
import com.zty.edu_equip.util.Common;
import com.zty.edu_equip.util.PageUtil;

public class DisposeAction extends ActionSupport
{
	

	private String url;
	//分页工具类
	private PageUtil pageUtil;
	// 使用map 获取页面传递过来的数据 传递普通数据
	private Map<String, String[]> pageMap;

	// 使用 map 给页面传递数据
	private Map map;
	private EquipDispose equipDispose;
	private String applytime1;
	private String applytime2;
	private String equip_id;
	private String pk_id;
	private String apply_remark;

	


	public String jump(){
		map=new HashMap();
		map.put("equip_id", equip_id);
		map.put("pk_id", pk_id);
		return url;
	}
	
	public String selectEquip(){
		map=new HashMap();
		map.put("key", "Dispose");
		map.put("url", "disposeAdd");
		//map.put("user_id", UtilAction.getUser().getPkValue());
		return url;
	}

	//转移申请页面数据
	public String findDispose() throws Exception {
		map = new HashMap();	
		UserInfo user = UtilAction.getUser();
			BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
			String sql = "select EQUIP_DISPOSE.*,USER_INFO.NICK_NAME from EQUIP_DISPOSE,USER_INFO where USER_INFO.USER_ID=EQUIP_DISPOSE.APPLYER_ID and  APPLYER_ID="+user.getPkValue()+" " +
					" and EQUIP_DISPOSE.VALID_FLAG!='V'";
			if(null!=applytime1&&!"".equals(applytime1)){
				sql += " and EQUIP_DISPOSE.APPLY_TIME < "+applytime1+"";
			}
			if(null!=applytime2&&!"".equals(applytime2)){
				sql += " and EQUIP_DISPOSE.APPLY_TIME > "+applytime2+"";
			}
			
			if(null!=equipDispose){
				if(null!=equipDispose.getStatus()&&!"".equals(equipDispose.getStatus().toString())){
					sql += " and EQUIP_DISPOSE.STATUS = "+equipDispose.getStatus()+"";
				}
				if(null!=equipDispose.getDisposeType()&&!"".equals(equipDispose.getDisposeType().toString())){
					sql += " and EQUIP_DISPOSE.DISPOSE_TYPE = "+equipDispose.getDisposeType()+"";
				}
				if(null!=equipDispose.getDisposeReason()&&!"".equals(equipDispose.getDisposeReason().toString())){
					sql += " and EQUIP_DISPOSE.DISPOSE_REASON = "+equipDispose.getDisposeReason()+"";
				}
				
			}
			sql+=" order by APPLY_TIME desc";
			map=base.getRowsWithPaging(sql,pageUtil.getPage()-1,pageUtil.getLines());
			return SUCCESS;
		}
		
		//转移装备页面数据
		public String findDisposeEquip() throws Exception {
			map = new HashMap();	
				BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
				String sql = "SELECT equip.*,room.room_name   " +
						" FROM room,equip,ref_equip_dispose " +
						" where room.room_id=equip.room_id,ref_equip_dispose.equip_id=equip.equip_id " +
						"  and ref_equip_dispose.EQUIP_DISPOSE_ID="+pk_id+" ";
				map=base.getRowsWithPaging(sql,pageUtil.getPage()-1,pageUtil.getLines());
				return SUCCESS;
			}

		//保存转移
	public String saveDispose() throws Exception{
		BaseDao baseDao=new BaseDao(SystemInfo.getMainJndi(), null);
		//room.setPersonCount(null);
	
		 UserInfo user =UtilAction.getUser();
		 String organ_id=user.getOrganId()+"";
		 String user_id=user.getPkValue()+"";
		 EquipDispose Dispose = new EquipDispose();
		 Dispose.setApplyerId(Util.newBigDecimal(user_id));
		 Dispose.setApplyTime(Util.getNow());
		 Dispose.setApplyRemark(apply_remark);
		 Dispose.setStatus("2");
		 Dispose.setOrganId(Util.newBigDecimal(organ_id));
		 Dispose.setDisposeReason(equipDispose.getDisposeReason());
		 Dispose.setDisposeType(equipDispose.getDisposeType());
		 Dispose.setValidFlag("A");
		 Dispose.setVersion(SeqenceGenerator.getNextOutranetVer());
		 baseDao.addObj4Execute(Dispose);
		 
		 String equipId[] = equip_id.split(",");
			for(int i=0;i<equipId.length;i++){
				RefEquipDispose	refEquipDispose = new RefEquipDispose();
				
				refEquipDispose.setEquipId(Util.newBigDecimal(equipId[i]));
				refEquipDispose.setCount(new BigDecimal(equipId.length));
				refEquipDispose.setEquipDisposeId(Util.newBigDecimal(Dispose.getPkValue()));
				baseDao.addObj4Execute(refEquipDispose);
			}
		 
		baseDao.execute();
		return SUCCESS;
	} 
	
	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public PageUtil getPageUtil() {
		return pageUtil;
	}


	public void setPageUtil(PageUtil pageUtil) {
		this.pageUtil = pageUtil;
	}


	public Map<String, String[]> getPageMap() {
		return pageMap;
	}


	public void setPageMap(Map<String, String[]> pageMap) {
		this.pageMap = pageMap;
	}


	public Map getMap() {
		return map;
	}


	public void setMap(Map map) {
		this.map = map;
	}


	public EquipDispose getEquipDispose() {
		return equipDispose;
	}


	public void setEquipDispose(EquipDispose equipDispose) {
		this.equipDispose = equipDispose;
	}


	public String getApplytime1() {
		return applytime1;
	}


	public void setApplytime1(String applytime1) {
		this.applytime1 = applytime1;
	}


	public String getApplytime2() {
		return applytime2;
	}


	public void setApplytime2(String applytime2) {
		this.applytime2 = applytime2;
	}



	public String getApply_remark() {
		return apply_remark;
	}

	public void setApply_remark(String apply_remark) {
		this.apply_remark = apply_remark;
	}

	public void setPk_id(String pk_id) {
		this.pk_id = pk_id;
	}

	public void setEquip_id(String equip_id) {
		this.equip_id = equip_id;
	}

}
