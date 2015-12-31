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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

import com.opensymphony.xwork2.ActionContext;
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
import com.zty.edu_equip.entity.EquipMove;
import com.zty.edu_equip.entity.RefEquipMoveEquip;
import com.zty.edu_equip.entity.UserInfo;
import com.zty.edu_equip.util.Common;
import com.zty.edu_equip.util.PageUtil;

public class AuditAction extends ActionSupport
{
	private String url;
	//分页工具类
	private PageUtil pageUtil;
	// 使用map 获取页面传递过来的数据 传递普通数据
	private Map<String, String[]> pageMap;

	// 使用 map 给页面传递数据
	private Map map;
	private String applytime1;
	private String applytime2;
	private String type;
	private String status;
	private String pk_id;
	private String approveResult;
	private String approve_reamrk;

	public String jump(){
		map=new HashMap();
		map.put("pk_id", pk_id);
		System.out.println("pk_id===="+pk_id);
		return url;
	}
	
	
	//转移申请页面数据
		public String findAudit() throws Exception {
			map = new HashMap();	
			UserInfo user = UtilAction.getUser();
				BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
				String sql = "select * from(select 'MOVE' as type, EQUIP_MOVE_ID as AUDIT_ID,b.organ_id, user_info.nick_name, b.APPLYER_ID,b.APPLY_TIME,b.APPLY_REMARK,b.STATUS from user_info,EQUIP_MOVE b " +
						" where user_info.user_id=b.APPLYER_ID and STATUS !=1" +
						" union all " +
						" select 'DISPOSE' as type,EQUIP_DISPOSE_ID as AUDIT_ID,b.organ_id,user_info.nick_name, b.APPLYER_ID,b.APPLY_TIME,b.APPLY_REMARK,b.STATUS from user_info,EQUIP_DISPOSE b" +
						" where user_info.user_id=b.APPLYER_ID and STATUS !=1) d where organ_id="+user.getOrganId()+"";
				
				if(null!=type&&!"".equals(type)){
					sql+=" and d.type='"+type+"'";
				}
				
				if(null!=status&&!"".equals(status)){
					sql+=" and d.status='"+status+"'";
				}
				
				if(null!=applytime1&&!"".equals(applytime1)){
					sql += " and d.APPLY_TIME < "+applytime1+"";
				}
				if(null!=applytime2&&!"".equals(applytime2)){
					sql += " and d.APPLY_TIME > "+applytime2+"";
				}
					
				sql+=" order by d.APPLY_TIME desc ";
				map=base.getRowsWithPaging(sql,pageUtil.getPage()-1,pageUtil.getLines());
				return SUCCESS;
			}
	
	
	
		//转移装备页面数据
				public String findMoveEquip() throws Exception {
					map = new HashMap();	
						BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
						String sql = "SELECT ref_equip_move_equip.*,equip.equip_name ,equip.EQUIP_SPECIFY,equip.PURCHASE_DATE,(r1.room_name) as ROOM_NAME1,(r2.room_name) as ROOM_NAME2 " +
								"  FROM equip,ref_equip_move_equip  " +
								" left join room r1 on  ref_equip_move_equip.room_id1=r1.room_id" + 
								" left join room r2 on  ref_equip_move_equip.room_id2 =r2.room_id" +
								" where ref_equip_move_equip.equip_id=equip.equip_id   " +
								" and ref_equip_move_equip.EQUIP_MOVE_ID="+pk_id+" ";
						map=base.getRowsWithPaging(sql,pageUtil.getPage()-1,pageUtil.getLines());
						
						sql="select EQUIP_MOVE.*,USER_INFO.NICK_NAME from EQUIP_MOVE,USER_INFO where USER_INFO.USER_ID=EQUIP_MOVE.APPLYER_ID and EQUIP_MOVE.EQUIP_MOVE_ID="+pk_id+"";
						List list =base.query(sql);
						HashMap row = (HashMap) list.get(0);
						String type=row.get("MOVE_TYPE")+"";
						if("A".equals(type))type="领用";
						if("B".equals(type))type="转移";
						if("C".equals(type))type="借用";
						if("D".equals(type))type="归还";
						map.put("MOVE_TYPE", type);
						map.put("APPLY_TIME", row.get("APPLY_TIME").toString().substring(0, 10));
						map.put("NICK_NAME", row.get("NICK_NAME"));
						map.put("STATUS", row.get("STATUS"));
						map.put("APPLY_REMARK", row.get("APPLY_REMARK"));
						map.put("APPROVE_REAMRK", row.get("APPROVE_REAMRK"));
						return SUCCESS;
					}
	
	
	
				//处置装备页面数据
				public String findDisposeEquip() throws Exception {
					map = new HashMap();	
						BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
						String sql = "SELECT equip.*,room.room_name   " +
						" FROM room,equip,ref_equip_dispose " +
						" where room.room_id=equip.room_id AND ref_equip_dispose.equip_id=equip.equip_id " +
						"  and ref_equip_dispose.EQUIP_DISPOSE_ID="+pk_id+" ";
						map=base.getRowsWithPaging(sql,pageUtil.getPage()-1,pageUtil.getLines());
						
						sql="select EQUIP_DISPOSE.*,USER_INFO.NICK_NAME from EQUIP_DISPOSE,USER_INFO where USER_INFO.USER_ID=EQUIP_DISPOSE.APPLYER_ID and EQUIP_DISPOSE.EQUIP_DISPOSE_ID="+pk_id+"";
						List list =base.query(sql);
						HashMap row = (HashMap) list.get(0);
						String type=row.get("DISPOSE_TYPE")+"";
						if("1".equals(type))type="无偿调拨";
						if("2".equals(type))type="出售";
						if("3".equals(type))type="对外捐赠";
						if("4".equals(type))type="置换";
						if("5".equals(type))type="报废";
						if("6".equals(type))type="报损";
						if("7".equals(type))type="核销";
						
						
						String reason = row.get("DISPOSE_REASON")+"";
						if("1".equals(reason))reason="长期停用";
						if("2".equals(reason))reason="产权转移";
						if("3".equals(reason))reason="使用权转移";
						if("4".equals(reason))reason="超过规定使用年限且不能继续使用";
						if("5".equals(reason))reason="未达到规定使用年限但需报废、淘汰";
						if("6".equals(reason))reason="盘亏";
						if("7".equals(reason))reason="其他非正常损失资产";
						map.put("DISPOSE_TYPE", type);
						map.put("DISPOSE_REASON",reason);
						map.put("NICK_NAME", row.get("NICK_NAME"));
						map.put("STATUS", row.get("STATUS"));
						map.put("APPLY_REMARK", row.get("APPLY_REMARK"));
						map.put("APPROVE_REAMRK", row.get("APPROVE_REAMRK"));
						
						
						
						
						return SUCCESS;
					}
	
	
	public String updateMove() throws Exception{
		EquipMove equipMove = new EquipMove(pk_id);
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		UserInfo user = UtilAction.getUser();
		equipMove.setApproverId(Util.newBigDecimal(user.getPkValue()));
		equipMove.setApproveTime(Util.getNow());
		equipMove.setApproveResult(approveResult);
		equipMove.setApproveReamrk(approve_reamrk);
		System.out.println("approveResult==="+approveResult);
		
		if("C".equals(approveResult)){
		equipMove.setStatus("1");
		String sql="update REF_EQUIP_MOVE_EQUIP set STATUS='C' where EQUIP_MOVE_ID="+pk_id+"";
		base.addSql4Execute(sql);
		}
		
		else{equipMove.setStatus("3");
		if("D".equals(approveResult)){
			String sql="select * from REF_EQUIP_MOVE_EQUIP where EQUIP_MOVE_ID="+pk_id+"";
			List list = baseDao.query(sql);
			    for(int i=0;i<list.size();i++){
			    	HashMap map =(HashMap) list.get(i);
				    Equip equip = new Equip(map.get("EQUIP_ID")+""); 
				    
				    if(null!=map.get("ROOM_ID2")){
				    	equip.setRoomId(Util.newBigDecimal(map.get("ROOM_ID2").toString()));
				    }
				    if(null!=map.get("USER_ID2")){
				    	equip.setUserId(Util.newBigDecimal(map.get("USER_ID2").toString()));
				    }
				    if(null!=map.get("USER_NAME2")){
				    	equip.setUserName(map.get("USER_NAME2").toString());
				    }
				    equip.setLastModifyTime(Util.getNow());
				    base.addObj4Execute(equip);
				    
				    RefEquipMoveEquip refEquipMoveEquip = new RefEquipMoveEquip(map.get("REF_ID")+"");
				    refEquipMoveEquip.setStatus("D");
				    base.addObj4Execute(refEquipMoveEquip);
			        }
			
			
			
		    }
		}
		
		equipMove.setVersion(SeqenceGenerator.getNextOutranetVer());
		base.addObj4Execute(equipMove);
		base.execute();
		
		return SUCCESS;
	}
	
	public String updateDispose() throws Exception{
		EquipDispose equipDispose = new EquipDispose(pk_id);
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		UserInfo user = UtilAction.getUser();
		equipDispose.setApproverId(Util.newBigDecimal(user.getPkValue()));
		equipDispose.setApproveTime(Util.getNow());
		equipDispose.setApproveResult(approveResult);
		equipDispose.setApproveReamrk(approve_reamrk);
		if("C".equals(approveResult)){
		equipDispose.setStatus("1");
		}
		
		else{equipDispose.setStatus("3");
		     if("D".equals(approveResult)){
			    String sql="update equip,REF_EQUIP_DISPOSE set equip.equip_status='V' where equip.equip_id=REF_EQUIP_DISPOSE.equip_id and  REF_EQUIP_DISPOSE.EQUIP_DISPOSE_ID="+pk_id+"";
			    base.addSql4Execute(sql);
		        }
		    }
		equipDispose.setVersion(SeqenceGenerator.getNextOutranetVer());
		base.addObj4Execute(equipDispose);
		base.execute();
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

	

	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getPk_id() {
		return pk_id;
	}


	public void setPk_id(String pk_id) {
		this.pk_id = pk_id;
	}


	public String getApproveResult() {
		return approveResult;
	}


	public void setApproveResult(String approveResult) {
		this.approveResult = approveResult;
	}


	public String getApprove_reamrk() {
		return approve_reamrk;
	}


	public void setApprove_reamrk(String approve_reamrk) {
		this.approve_reamrk = approve_reamrk;
	}


}
