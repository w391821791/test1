package com.zty.edu_equip.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.components.Date;




import com.po.base.Util;
import com.po.serverbase.BaseDao;
import com.po.serverbase.Result;
import com.po.serverbase.SeqenceGenerator;
import com.po.serverbase.SysConfig;
import com.po.serverbase.SystemInfo;
import com.po.socket.SocketHandler;
import com.po.socket.SocketObject;
import com.zty.edu_equip.dao.EquipDao;
import com.zty.edu_equip.entity.BatchImport;
import com.zty.edu_equip.entity.Equip;
import com.zty.edu_equip.entity.RefBatchImportEquip;
import com.zty.edu_equip.entity.Room;
import com.zty.edu_equip.entity.UserInfo;
import com.zty.edu_equip.util.Common;
import com.zty.edu_equip.util.PageUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class EquipAction extends ActionSupport {

	private EquipDao ed = new EquipDao();
	
	
	private String url;
	private File myfile1;
	//分页工具类
	private PageUtil pageUtil;
	// 使用map 获取页面传递过来的数据 传递普通数据
	private Map<String, String[]> pageMap;

	// 使用 map 给页面传递数据
	private Map map;
	private String pk_id;
	private int total;
	private String type;
	private String equip_type_id;
	private String room_type_id;
	private String room_id;
	private String room_name;
	private String organ_id;
	private String equip_name;
	private String user_id;
	private boolean group_id;
	private Room  room;
	private Equip equip;


	private String equip_brand;
	private String remark;
	private String equip_specify;
	private String unit_code;
	private String unit_price;
	private String count;
	private String purchase_date;
	private String user_name;
	private String real_position;
	private String equip_id;
	private String textChange;
	
	public String jump(){
		return url;
	}
	// 进入明细(修改)页面
	public String showmingxi() throws Exception{
		/*String str=letYouCool();
		if(null!=str){
			if("".equals(str))return NONE;
			else return str;
		}*/
		BaseDao baseDao=new BaseDao(SystemInfo.getMainJndi(), null);
		Equip  equipinfo=(Equip) baseDao.getObjFromDbById(Equip.class, equip.getEquipId());
		map=new HashMap();
		String price =equipinfo.getUnitPrice()+"";
		if(null!=price&&!"".equals(price)){
		equipinfo.setUnitPrice(Util.newBigDecimal(price.subSequence(0, price.length()-3).toString()));
		}
		map.put("equip", equipinfo);
		map.put("room_name", Common.getRoomNameByRoomId(equipinfo.getRoomId()));
		map.put("equip_type", Common.getEquipTypeNameById(equipinfo.getEquipTypeId()));
		return url;
	}
	public String saveEquip() throws Exception{
		BaseDao baseDao=new BaseDao(SystemInfo.getMainJndi(), null);
		System.out.println(equip.getEquipId()+"呵呵");
		//room.setPersonCount(null);
		equip.setVersion(SeqenceGenerator.getNextOutranetVer());
		baseDao.execute(equip);
		return SUCCESS;
	} 


		public String findEquipList() throws Exception {

			map = new HashMap();
			/*total = ed.getCount(pageMap.get("EQUIP_TYPE_ID")[0], room.getRoomTypeId(),
					pageMap.get("ROOM_NAME")[0],pageMap.get("ORGAN_ID")[0],pageMap.get("EQUIP_NAME")[0],
					pageMap.get("GROUP_ID")[0]);*/
			
			String OrganId = UtilAction.getUser().getOrganId()+"";
			String groupId=null;
			String equipTypeId=null;
			if(group_id){
				equipTypeId=equip_type_id;
			}else{groupId=equip_type_id;}
			if (null == type || type.isEmpty()) {
				BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
				String sql = "select equip.*,equip_type.asset_type_name,room.room_name from equip " +
						" left join equip_type on equip.equip_type_id=equip_type.equip_type_id " +
						" left join room on equip.room_id=room.room_id " +
						" left join REF_ROOM_TYPE on REF_ROOM_TYPE.room_id=equip.room_id " +
						" where equip.equip_STATUS!='V'  " ;
				if(null!=equipTypeId&&!"".equals(equipTypeId)){
					sql += " and equip.equip_type_id="+equipTypeId+" ";
				}
				if(null!=groupId&&!"".equals(groupId)){
					sql += " and equip_type.group_id in ("
			        + Common
			                .getChildGroup(Integer.parseInt(groupId))
			        + ")";
				}
				if(null!=room_type_id&&!"".equals(room_type_id)){
					sql += " and REF_ROOM_TYPE.ROOM_TYPE_ID="+room_type_id+"";
				}
				if(null!=room){
				
				if(null!=room.getRoomId()&&!"".equals(room.getRoomId().toString())){
					sql += " and ROOM.ROOM_ID = "+room.getRoomId()+"";
				}
				}
				if(null!=organ_id&&!"".equals(organ_id)){
					sql += " and room.organ_id="+organ_id+" ";
				}else{sql += " and room.organ_id="+OrganId+""; }
				if(null!=pageMap.get("EQUIP_NAME")[0]&&!"".equals(pageMap.get("EQUIP_NAME")[0])){
					sql += " and equip.equip_name like '%"+pageMap.get("EQUIP_NAME")[0]+"%' ";
				}
				
				sql+=" group by equip_id ";
				if(!pageUtil.getOrderField().isEmpty()){
					sql+=" order by "+pageUtil.getOrderField()+pageUtil.getOrder();
				}
				map=base.getRowsWithPaging(sql,pageUtil.getPage()-1,pageUtil.getLines());
				System.out.println("return equips");
				
			} else {

				if (total > 5000) {

					map = (Map) ActionContext.getContext().get("request");
					map.put("over", "over");
					return "equips";
				}else{
                if(null==organ_id||"".equals(organ_id)){organ_id=OrganId;}
				List excellist = ed.findEquipList(equip_type_id, room_type_id,room_name,
						organ_id,equip_name,groupId);
					String[][] data = new String[excellist.size() + 1][13];
					
					
					data[0][0] = "装备名称";
					data[0][1] = "装备编号";
					data[0][2] = "装备类别";
					data[0][3] = "房间名称";
					data[0][4] = "规格型号";
					data[0][5] = "购置时间";
					data[0][6] = "产品编号";
					data[0][7] = "账目原值";
					data[0][8] = "单位";
					data[0][9] = "使用状况";
					data[0][10] = "使用年限";

					for (int i = 0; i < excellist.size(); i++) {
						


						HashMap hmap = new HashMap();
						hmap = (HashMap) excellist.get(i);
						

						if (hmap.get("ASSET_NAME") != null) {
							data[i + 1][0] = hmap.get("ASSET_NAME") + "";
						} else {
							data[i + 1][0] = "";
						}

						if (hmap.get("ASSET_NO") != null) {
							data[i + 1][1] = hmap.get("ASSET_NO") + "";
						} else {
							data[i + 1][1] = "";
						}

						if (hmap.get("ASSET_TYPE_NAME") != null) {
							data[i + 1][2] = hmap.get("ASSET_TYPE_NAME") + "";
						} else {
							data[i + 1][2] = "";
						}

						if (hmap.get("ROOM_NAME") != null) {
							data[i + 1][3] = hmap.get("ROOM_NAME") + "";
						} else {
							data[i + 1][3] = "";
						}


						if (hmap.get("ASSET_SPECIFY") != null) {
							data[i + 1][4] = hmap.get("ASSET_SPECIFY") + "";
						} else {
							data[i + 1][4] = "";
						}

						if (hmap.get("PURCHASE_DATE") != null) {
							data[i + 1][5] = DateFormat.getDateInstance().format(
									hmap.get("PURCHASE_DATE"));
						} else {
							data[i + 1][5] = "";
						}

						if (hmap.get("PRODUCT_NUMBER") != null) {
							data[i + 1][6] = hmap.get("PRODUCT_NUMBER") + "";
						} else {
							data[i + 1][6] = "";
						}

						if (hmap.get("FIRST_VALUE") != null) {
							data[i + 1][7] = hmap.get("FIRST_VALUE") + "";
						} else {
							data[i + 1][7] = "";
						}
						

						if (hmap.get("UNIT_NAME") != null) {
							data[i + 1][8] = hmap.get("UNIT_NAME") + "";
						} else {
							data[i + 1][8] = "";
						}
						if (null==hmap.get("CHECK")) {
							data[i + 1][9] = "在册（待核）";
						}else{
						if (hmap.get("CHECK").equals("C")) {
							data[i + 1][9] = "在册（正常）";
						}
						if (hmap.get("CHECK").equals("N")) {
							data[i + 1][9] = "在册（下架）";
						}
						}
						if (hmap.get("LIFE_YEAR") != null) {
							data[i + 1][10] = hmap.get("LIFE_YEAR") + "年";
						} else {
							data[i + 1][10] = "";
						}

					}
					Common.ExportExcel(data,"装备信息");

				}
			}

			return SUCCESS;

		}
		
		
		public String findEquipListForUse() throws Exception {

			map = new HashMap();
			
			
			String OrganId = UtilAction.getUser().getOrganId()+"";
			String groupId=null;
			String equipTypeId=null;
			if(group_id){
				equipTypeId=equip_type_id;
			}else{groupId=equip_type_id;}
			
				BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
				String sql = "select equip.*,equip_type.asset_type_name,room.room_name from equip " +
						" left join equip_type on equip.equip_type_id=equip_type.equip_type_id " +
						" left join room on equip.room_id=room.room_id " +
						" left join REF_ROOM_TYPE on REF_ROOM_TYPE.room_id=equip.room_id " +
						" where equip.equip_STATUS!='V'  " ;
				if(null!=equipTypeId&&!"".equals(equipTypeId)){
					sql += " and equip.equip_type_id="+equipTypeId+" ";
				}
				if(null!=groupId&&!"".equals(groupId)){
					sql += " and equip_type.group_id in ("
			        + Common
			                .getChildGroup(Integer.parseInt(groupId))
			        + ")";
				}
				
				if(null!=room){
				
				if(null!=room.getRoomId()&&!"".equals(room.getRoomId().toString())){
					sql += " and ROOM.ROOM_ID = "+room.getRoomId()+"";
				}
				}
				if(null!=organ_id&&!"".equals(organ_id)){
					sql += " and room.organ_id="+organ_id+" ";
				}else{sql += " and room.organ_id="+OrganId+""; }
				if(null!=equip_name&&!"".equals(equip_name)){
					sql += " and equip.equip_name like '%"+equip_name+"%' ";
				}
				if(null!=user_id&&!"".equals(user_id)){
					sql += " and equip.user_id = "+user_id+" ";
				}
				sql+=" group by equip_id";
				if(!pageUtil.getOrderField().isEmpty()){
					sql+="   order by "+pageUtil.getOrderField()+pageUtil.getOrder();
				}
				map=base.getRowsWithPaging(sql,pageUtil.getPage()-1,pageUtil.getLines());
				System.out.println("return equips");
				return SUCCESS;
		}
		
	public String inequipReportForm() throws Exception{
		map = new HashMap();
		String organ_id=UtilAction.getUser().getOrganId()+"";
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);		

		HashMap OrganScalType = EquipDao.getOrganScalType(organ_id);
		String OrganScal=OrganScalType.get("SCAL").toString();
		int group_id =0;
		System.out.println("type=="+type);
		if("equip".equals(type)){
			group_id=1;
		}
		if("instrument".equals(type)){
		if("A".equals(OrganScal)){
			group_id=9;
		}
		if("B".equals(OrganScal)){
			group_id=10;
		}
		if("C".equals(OrganScal)){
			group_id=11;
		}}
		System.out.println("group_id=="+group_id);
		String sql = "select  ROOM_STAND_EQUIP.* ,ROOM_TYPE.ROOM_TYPE_NAME ,room.room_id,EQUIP_TYPE.ASSET_TYPE_NAME,EQUIP_TYPE.UNIT_CODE,room.room_name" +
				"  FROM REF_ROOM_TYPE, ROOM_STAND_EQUIP,ROOM_TYPE,EQUIP_TYPE,room " +
				" WHERE REF_ROOM_TYPE.room_id=room.room_id and  REF_ROOM_TYPE.room_type_id= ROOM_STAND_EQUIP.ROOM_TYPE_ID " +
				" and  ROOM_TYPE.ROOM_TYPE_id =ROOM_STAND_EQUIP.ROOM_TYPE_ID " +
				" and ROOM_STAND_EQUIP.ORGAN_TYPE='"+OrganScal+"' and ROOM.ROOM_id="+room_id+"" +
				" and EQUIP_TYPE.equip_type_id= ROOM_STAND_EQUIP.EQUIP_TYPE_ID and room.organ_id="+organ_id+" " +
				
				" and equip_type.group_id in ("+ Common.getChildGroup(group_id) + ")" +
				" group by room_stand_equip_id";
		List biaozhunlist = base.query(sql);
		List list = new ArrayList();
		    if(null!=biaozhunlist&&biaozhunlist.size()>0){
		       sql="select equip.equip_type_id,equip.equip_specify,sum(count) as COUNT,EQUIP_ID,UNIT_PRICE,EQUIP.REMARK,EQUIP_BRAND," +
		       	" PURCHASE_DATE,USER_NAME,REAL_POSITION from equip where room_id="+room_id+" group by equip_type_id ";
		       List equip= base.query(sql);
		       for(int i=0;i<biaozhunlist.size();i++){
		    	   HashMap map = (HashMap) biaozhunlist.get(i);
		    	   String equip_type = map.get("EQUIP_TYPE_ID")+"";
		    	   map.put("PRICE","");
    			   map.put("EQUIP_BRAND","");
    			   map.put("E_SPEFY","");
    			   map.put("COUNT","");
    			   map.put("PURCHASE_DATE","");
    			   map.put("USER_NAME","");
    			   map.put("REAL_POSITION","");
		    	   for(int j=0;j<equip.size();j++){
		    		   HashMap equipmap = (HashMap) equip.get(j);
		    		   String e_type = equipmap.get("EQUIP_TYPE_ID")+"";
		    		   if(equip_type.equals(e_type)){
		    			   map.put("EQUIP_ID", equipmap.get("EQUIP_ID"));
		    			   map.put("PRICE", equipmap.get("UNIT_PRICE"));
		    			   map.put("REMARK", equipmap.get("REMARK"));
		    			   map.put("EQUIP_BRAND", equipmap.get("EQUIP_BRAND"));
		    			   map.put("E_SPEFY", equipmap.get("EQUIP_SPECIFY"));
		    			   map.put("COUNT", equipmap.get("COUNT"));
		    			   map.put("PURCHASE_DATE", equipmap.get("PURCHASE_DATE"));
		    			   map.put("USER_NAME", equipmap.get("USER_NAME"));
		    			   map.put("REAL_POSITION", equipmap.get("REAL_POSITION"));
		    		   }
		    	   }
		    	   list.add(map);
		       }
		
		    } 
		    
			map.put("list",Common.ConvertEmpty(list));
			map.put("length",list.size()+1);		
		return SUCCESS;
		
	}
	
	public String inputEquip() throws Exception{
		map = new HashMap();
		BaseDao basedao = new BaseDao(SystemInfo.getMainJndi(), null);
		String[] equipTypeId= equip_type_id.split(",");
		String[] equipName= equip_name.split(",");
		String[] equipBrand= equip_brand.split(",");
		String[] equipSpecify= equip_specify.split(",");
		String[] unitCode= unit_code.split(",");
		String[] unitrice= unit_price.split(",");
		String[] Count= count.split(",");
		String[] purchaseDate= purchase_date.split(",");
		String[] userName= user_name.split(",");
		String[] realPosition= real_position.split(",");
		String[] Remark= remark.split(",");
		String[] textchange= textChange.split(",");
		
		for(int i=1;i<equipName.length;i++){
			
			equipTypeId[i]=equipTypeId[i].replaceAll(" ", "");
			equipName[i]=equipName[i].replaceAll(" ", "");
			equipBrand[i]=equipBrand[i].replaceAll(" ", "");
			equipSpecify[i]=equipSpecify[i].replaceAll(" ", "");
			unitCode[i]=unitCode[i].replaceAll(" ", "");
			unitrice[i]=unitrice[i].replaceAll(" ", "");
			Count[i]=Count[i].replaceAll(" ", "");
			purchaseDate[i]=purchaseDate[i].replaceAll(" ", "");
			userName[i]=userName[i].replaceAll(" ", "");
			realPosition[i]=realPosition[i].replaceAll(" ", "");
			Remark[i]=Remark[i].replaceAll(" ", "");
			textchange[i]=textchange[i].replaceAll(" ", "");
			if(!"change".equals(textchange[i])){
				continue;
			}
			String sql="select * from equip where   room_id ="+room_id+"  and equip_type_id = "+equipTypeId[i]+" and CARD_STATUS='B'";
			List list =basedao.query(sql);
			if(null!=list&&list.size()>0){
				map.put("msg", equipName[i]+"已贴标签无法修改！");
			    return SUCCESS;
			}else{
				sql="delete REF_BATCH_IMPORT_EQUIP from equip,REF_BATCH_IMPORT_EQUIP where equip.equip_id=REF_BATCH_IMPORT_EQUIP.equip_id and " +
					" equip.room_id="+room_id+" and equip.equip_type_id="+equipTypeId[i]+"";
				basedao.addSql4Execute(sql);
				sql="delete from equip where   room_id ="+room_id+"  and equip_type_id = "+equipTypeId[i]+"";
				basedao.addSql4Execute(sql);
			}
			if(null!=Count[i]&&!"".equals(Count[i])){
				
			int intcount=Integer.parseInt(Count[i]);
			
			if(intcount>0){
				

				int equip_count =Integer.parseInt(Count[i]);
				String purdate=Common.Dateformat(purchaseDate[i]);
				if("57".equals(equipTypeId[i])){
					equip_count=1;
				}else{Count[i]="1";}
				
				for(int j=0;j<equip_count;j++){
					Equip equip = new Equip(); 
					int k = j + 1;
					if (equip_count == 1){
						
						equip.setEquipName(equipName[i]);}
					if (equip_count > 1){
						equip.setEquipName(equipName[i] + "(" + k + ")");}
			equip.setCount(Util.newBigDecimal(Count[i]));
			equip.setCreateTime(Util.getNow());
			equip.setPurchaseDate(Util.parseTimestamp(purdate));
			equip.setEquipBrand(equipBrand[i]);
			equip.setEquipSpecify(equipSpecify[i]);
			equip.setUnitCode(unitCode[i]);
			equip.setRoomId(Util.newBigDecimal(room_id));
			equip.setEquipTypeId(Util.newBigDecimal(equipTypeId[i]));
			equip.setRealPosition(realPosition[i]);
			equip.setUserName(userName[i]);
			if(null!=unitrice[i]&&!"".equals(unitrice[i])){
			equip.setUnitPrice(Util.newBigDecimal(unitrice[i]));}
			equip.setEquipStatus("A");
			equip.setCardStatus("A");
			equip.setRemark(Remark[i]);
			equip.setVersion(SeqenceGenerator.getNextOutranetVer());
			basedao.addObj4Execute(equip);
			
		
			}}}
		}
		basedao.execute();
		
		map.put("msg", "保存成功！");
    return SUCCESS;
		
	}
	
	
	
	public String inputInstrument() throws Exception{
		BaseDao basedao = new BaseDao(SystemInfo.getMainJndi(), null);
		map = new HashMap();
		String[] equipId= equip_id.split(",");
		String[] equipTypeId= equip_type_id.split(",");
		String[] equipName= equip_name.split(",");
		String[] equipBrand= equip_brand.split(",");
		String[] equipSpecify= equip_specify.split(",");
		String[] unitCode= unit_code.split(",");
		String[] unitrice= unit_price.split(",");
		String[] Count= count.split(",");
		String[] purchaseDate= purchase_date.split(",");
		String[] userName= user_name.split(",");
		String[] realPosition= real_position.split(",");
		String[] Remark= remark.split(",");
		
		for(int i=1;i<equipName.length;i++){
			equipId[i]=equipId[i].replaceAll(" ", "");
			equipTypeId[i]=equipTypeId[i].replaceAll(" ", "");
			equipName[i]=equipName[i].replaceAll(" ", "");
			equipBrand[i]=equipBrand[i].replaceAll(" ", "");
			equipSpecify[i]=equipSpecify[i].replaceAll(" ", "");
			unitCode[i]=unitCode[i].replaceAll(" ", "");
			unitrice[i]=unitrice[i].replaceAll(" ", "");
			Count[i]=Count[i].replaceAll(" ", "");
			purchaseDate[i]=purchaseDate[i].replaceAll(" ", "");
			userName[i]=userName[i].replaceAll(" ", "");
			realPosition[i]=realPosition[i].replaceAll(" ", "");
			Remark[i]=Remark[i].replaceAll(" ", "");
			
			
			
			
			if(null!=Count[i]&&!"".equals(Count[i])){
				System.out.println("Count="+Count[i]);
			int intcount=Integer.parseInt(Count[i]);
			if(intcount==0){
			String	sql="delete  from REF_BATCH_IMPORT_EQUIP where  equip_id="+equipId[i]+" ";
					basedao.addSql4Execute(sql);
					sql="delete  from equip where  equip_id="+equipId[i]+" ";
					basedao.addSql4Execute(sql);
			}
			if(intcount>0){

			String purdate=Common.Dateformat(purchaseDate[i]);
			Equip equip = null;
			if(null==equipId[i]||"".equals(equipId[i])){
				equip = new Equip(); 
			}else{equip=new Equip(equipId[i]);}
			equip.setEquipName(equipName[i]);
			equip.setCount(Util.newBigDecimal(Count[i]));
			equip.setCreateTime(Util.getNow());
			equip.setPurchaseDate(Util.parseTimestamp(purdate));
			equip.setEquipBrand(equipBrand[i]);
			equip.setEquipSpecify(equipSpecify[i]);
			equip.setUnitCode(unitCode[i]);
			equip.setRoomId(Util.newBigDecimal(room_id));
			equip.setEquipTypeId(Util.newBigDecimal(equipTypeId[i]));
			equip.setRealPosition(realPosition[i]);
			equip.setUserName(userName[i]);
			if(null!=unitrice[i]&&!"".equals(unitrice[i])){
			equip.setUnitPrice(Util.newBigDecimal(unitrice[i]));}
			equip.setEquipStatus("A");
			equip.setCardStatus("A");
			equip.setRemark(Remark[i]);
			equip.setVersion(SeqenceGenerator.getNextOutranetVer());
			basedao.addObj4Execute(equip);
			
		
			}}
		}
		basedao.execute();
		
		map.put("msg", "保存成功！");
    return SUCCESS;
		
	}
	
	
	public String importExcel() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		
			map = (Map) ActionContext.getContext().get("request");
			 if (Common.wboo)
				{
					map.put("msg", "网络堵车，请稍后再试");
					return "excelImport";
				}
			 String Exception =null;
			 String msg ="导入成功！";
			Common.wboo = true;
			try {
				
			int Numbers = Common.getFileNameNumber();
			File file = new File(SysConfig.getAttrValue("FileServerPath")
			        + File.separator + "Excel\\aa.xlsx");
			if(file.exists()){    file.delete();}
			myfile1.renameTo(file);
			String[] colNo =new String[] { "0", "1", "2", "3","4",  "5", "6", "7", "8", "9", "10"};
			
			String type= request.getParameter("TYPE");
			String filename= request.getParameter("filename");
            BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
			
            UserInfo user = UtilAction.getUser();
			//批量导入记录
			BatchImport batchImport= new BatchImport();
			batchImport.setImportTime(Util.getNow());
			batchImport.setImporter(user.getNickName());
			base.execute(batchImport);
			String import_id= batchImport.getPkValue();
			
			HashMap EquipType=Common.getEquipType(1);//装备类别
			List room=new ArrayList();
			HashMap OrganScalType = EquipDao.getOrganScalType(user.getOrganId().toString());
			String OrganScal=OrganScalType.get("SCAL").toString();		
            int group_id=0;
			if("A".equals(OrganScal)){
				group_id=9;
			}
			if("B".equals(OrganScal)){
				group_id=10;
			}
			if("C".equals(OrganScal)){
				group_id=11;
			}
			if("equip".equals(type)){
				
				room=Common.getAllRoomName(user.getOrganId().toString());			    
			}
			if("instrument".equals(type)){				
				
				room=Common.getInstrumentRoomName();
			}
			for (int i=0;i<room.size();i++){
				HashMap aasds = (HashMap) room.get(i);
				List fileDatas= Common.getDataFromExcel2007(file,"0", colNo,i);
				String[] strRoom =(String[]) fileDatas.get(0);
				String room_id =strRoom[10];
				if(room_id.contains(".")){room_id=room_id.substring(0,room_id.length()-2);}
				System.out.println("room_id===="+room_id);
                if(
                		"19822".equals(room_id)/*||"19214".equals(room_id)||"19215".equals(room_id)||"19216".equals(room_id)||"19217".equals(room_id)||"19218".equals(room_id)
                		||"19219".equals(room_id)||"19220".equals(room_id)||"19221".equals(room_id)||"19222".equals(room_id)||"19223".equals(room_id)||"19224".equals(room_id)
                		||"19225".equals(room_id)||"19226".equals(room_id)||"19227".equals(room_id)||"19228".equals(room_id)||"19229".equals(room_id)||"19230".equals(room_id)
                		||"19231".equals(room_id)||"19232".equals(room_id)||"19233".equals(room_id)||"19234".equals(room_id)||"19235".equals(room_id)||"19236".equals(room_id)
                		||"19237".equals(room_id)||"19238".equals(room_id)||"19239".equals(room_id)||"19240".equals(room_id)||"19241".equals(room_id)||"19242".equals(room_id)
                		||"19243".equals(room_id)||"19244".equals(room_id)||"19245".equals(room_id)||"19246".equals(room_id)||"19247".equals(room_id)||"19248".equals(room_id)
                		||"19249".equals(room_id)||"19250".equals(room_id)||"19251".equals(room_id)||"19252".equals(room_id)||"19253".equals(room_id)||"19254".equals(room_id)
                		||"19255".equals(room_id)||"19256".equals(room_id)||"19257".equals(room_id)||"19258".equals(room_id)||"19259".equals(room_id)||"19260".equals(room_id)
                		||"19261".equals(room_id)||"19262".equals(room_id)||"19263".equals(room_id)||"19264".equals(room_id)||"19265".equals(room_id)||"19266".equals(room_id)
                		||"19267".equals(room_id)||"19268".equals(room_id)||"19269".equals(room_id)||"19270".equals(room_id)||"19271".equals(room_id)||"19272".equals(room_id)
                		||"19273".equals(room_id)||"19274".equals(room_id)||"19275".equals(room_id)||"19276".equals(room_id)||"19277".equals(room_id)||"19278".equals(room_id)
*/
                		
                		){
				String room_name = strRoom[0];
				String sql="select * from equip where   room_id ="+room_id+"   and CARD_STATUS='B'";
				List list;
				
					list = base.query(sql);
				
				if(null!=list&&list.size()>0){
					map.put("msg", room_name+"已贴标签无法导入！");
					Common.wboo = false;
					return "excelImport";
				}else{
					sql="delete REF_BATCH_IMPORT_EQUIP from equip,REF_BATCH_IMPORT_EQUIP where equip.equip_id=REF_BATCH_IMPORT_EQUIP.equip_id and " +
						" equip.room_id="+room_id+" ";
					base.addSql4Execute(sql);
					
				}
				if("equip".equals(type)){
					sql="delete from equip where equip.room_id="+room_id+"";
				}
				if("instrument".equals(type)){
					sql="delete equip.* from equip,equip_type where equip_type.equip_type_id=equip.equip_type_id and equip.room_id="+room_id+" " +
							" and equip_type.group_id in ("
			        + Common
			                .getChildGroup(group_id)
			        + ")";
				}
				base.addSql4Execute(sql);
				String equip_name=null;
				String equip_type_id=null;
				String equip_brand=null;
				String equip_specify=null;
				String unit_code=null;
				String unit_price=null;
				String count = null;
				String purchase_date = null;
				String user_name = null;
				String real_position=null;
				String remark=null;
				Exception=aasds.get("ROOM_NAME")+"";
				for(int j = 2;j<fileDatas.size();j++){
					String[] str =(String[]) fileDatas.get(j);
					if("equip".equals(type)){
					     equip_name=str[0];
					     equip_brand=str[1];
					     equip_specify=str[2];
					     unit_code=str[3];
					     unit_price=str[4];
					     count = str[5];
					     
					     purchase_date = str[6];
					     user_name = str[7];
					     real_position=str[8];
					     remark=str[9];
					     equip_type_id=str[10];
					     if(null==equip_type_id||"".equals(equip_type_id)){
					    	 equip_type_id=EquipType.get(equip_name)+"";
					    	 if(null!=equip_name&&!"".equals(equip_name)){
					    		 String diannao =equip_name.substring(equip_name.length()-2,equip_name.length());
					    		
					    	 if("台式机".equals(equip_name)||"多功能一体机".equals(equip_name)||diannao.equals("电脑")||"计算机".equals(equip_name)){
						    	 equip_type_id="145"; 
						     }
					    	 if("空气调节电器".equals(equip_name)||equip_name.contains("空调")){
						    	 equip_type_id="104"; 
						     }
					    	 if("台、桌类".equals(equip_name)||"组合办工桌".equals(equip_name)||"组合办公台".equals(equip_name)){
						    	 equip_type_id="37"; 
						     }
						     if("椅凳类".equals(equip_name)){
						    	 equip_type_id="207"; 
						     }
						     if("打印设备".equals(equip_name)||equip_name.contains("打印机")){
						    	 equip_type_id="18"; 
						     }
						     if("柜架".equals(equip_name)){
						    	 equip_type_id="183"; 
						     }
						     if(equip_name.contains("文件柜")){
						    	 equip_type_id="127"; 
						     }
						     if(equip_name.contains("投影仪")){
						    	 equip_type_id="38"; 
						     }
						     if(equip_name.contains("电视机")){
						    	 equip_type_id="76"; 
						     }
						     if(equip_name.contains("冰箱")){
						    	 equip_type_id="29"; 
						     }
						     if(equip_name.contains("黑板")){
						    	 equip_type_id="120"; 
						     }
						     if(equip_name.contains("功放")){
						    	 equip_type_id="41"; 
						     }
						     if(equip_name.contains("调音台")){
						    	 equip_type_id="180"; 
						     }
						     if(equip_name.contains("机柜")){
						    	 equip_type_id="270"; 
						     }
						     if(equip_name.contains("四门铁柜")||equip_name.contains("四门柜")){
						    	 equip_type_id="95"; 
						     }
					     }  }
					     
					     //System.out.println("purchase_date==="+purchase_date);
						 if(null!=count&&!"".equals(count)){
							 if(count.contains(".")){
						    	 count=count.substring(0,count.length()-2); 
						     }
								if(Integer.parseInt(count)>0){
									int equip_count =Integer.parseInt(count);
									purchase_date=Common.Dateformat(purchase_date);
                                    if(equip_type_id.contains(".")){
                                    	equip_type_id=equip_type_id.substring(0,equip_type_id.length()-2);
                                    }
                                    if("57".equals(equip_type_id)||"213".equals(equip_type_id)){
										equip_count=1;
									}else{count="1";}
									for(int k=0;k<equip_count;k++){
										Equip equip = new Equip(); 
										int p = k + 1;
										
										if (equip_count == 1)
											equip.setEquipName(equip_name);
										if (equip_count > 1)
											equip.setEquipName(equip_name + "(" + p + ")");
								        equip.setCount(Util.newBigDecimal(count));
								        equip.setCreateTime(Util.getNow());
								        if(null!=purchase_date&&!"".equals(purchase_date)){
								        	//System.out.println("purchase_date==="+purchase_date);
								        equip.setPurchaseDate(Util.parseTimestamp(purchase_date));}
								        equip.setEquipBrand(equip_brand);
								        equip.setEquipSpecify(equip_specify);
								        equip.setUnitCode(unit_code);
								        equip.setRoomId(Util.newBigDecimal(room_id));
								        equip.setEquipTypeId(Util.newBigDecimal(equip_type_id));
								        equip.setRealPosition(real_position);
								        equip.setUserName(user_name);
								        equip.setRemark(remark);
								        if(null!=unit_price&&!"".equals(unit_price)){
								        equip.setUnitPrice(Util.newBigDecimal(unit_price));}
								        equip.setEquipStatus("A");
								        equip.setCardStatus("A");
								        equip.setVersion(SeqenceGenerator.getNextOutranetVer());
								        base.addObj4Execute(equip);
								        String equip_id= equip.getPkValue();
								
								        //导入关联
								        RefBatchImportEquip refBatchImportEquip=new RefBatchImportEquip();
								        refBatchImportEquip.setEquipId(Util.newBigDecimal(equip_id));
								        refBatchImportEquip.setImportId(Util.newBigDecimal(import_id));
		            				    base.addObj4Execute(refBatchImportEquip);
									}
								}
						
						 }
					
					}else{
						 equip_name=str[0];
						 unit_code=str[2];
						 count = str[4];
						 purchase_date = str[5];
						 unit_price=str[6];
						 user_name = str[7];
						 real_position=str[8];
						 remark=str[9];	
						 equip_type_id=str[10];
						 if(null!=count&&!"".equals(count)){
							 if(count.contains(".")){
						    	 count=count.substring(0,count.length()-2); 
						     }
								if(Integer.parseInt(count)>0){
									 
								    purchase_date=Common.Dateformat(purchase_date);
								    //System.out.println("purchase_date==="+purchase_date);
						    		
										Equip equip = new Equip(); 
										equip.setEquipName(equip_name);
			                            equip.setCardStatus("A");
			                            equip.setPurchaseDate(Util.parseTimestamp(purchase_date));
			                            equip.setCreateTime(Util.getNow());
			                            equip.setEquipSpecify(equip_specify);
			                            equip.setEquipStatus("A");
			                            equip.setEquipTypeId(Util.newBigDecimal(equip_type_id));
			                            if(null!=unit_price&&!"".equals(unit_price)){
			                				equip.setUnitPrice(Util.newBigDecimal(unit_price));}
			                            equip.setRemark(remark);
			                            equip.setUserName(user_name);
			                            equip.setRoomId(Util.newBigDecimal(room_id));
			                            equip.setCount(Util.newBigDecimal(count));
			                            equip.setRealPosition(real_position);
			                            equip.setUnitCode(unit_code);
			                            equip.setVersion(SeqenceGenerator.getNextOutranetVer());
			                            base.addObj4Execute(equip);
			            				String equip_id= equip.getPkValue();
			            				
			            				//导入关联
			            				RefBatchImportEquip refBatchImportEquip=new RefBatchImportEquip();
			            				refBatchImportEquip.setEquipId(Util.newBigDecimal(equip_id));
			            				refBatchImportEquip.setImportId(Util.newBigDecimal(import_id));
			            				base.addObj4Execute(refBatchImportEquip);
			            				
			            				
			            				
						    		 
						    	    }
						    }
					}
					

					
				}
				
			}
}
		    base.execute();
			}  catch (Exception e) {
				Common.wboo = false;
				e.printStackTrace();
				msg= Exception+"数据不正确！";
				//throw e;
			} finally {
				if (myfile1 != null && myfile1.exists() && myfile1.isFile())
					try {
						myfile1.delete();
					} catch (Exception ee) {
					}
				map.put("msg", msg);
			}


		
		Common.wboo = false;
		return "excelImport";
	}
	
	
	
	
	
	
	
       public String exportExcel() throws Exception{
	
	          HashMap data = new HashMap();
	         
              String filename=null;
              BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
              String organ_id=UtilAction.getUser().getOrganId()+"";
              HashMap OrganScalType = EquipDao.getOrganScalType(organ_id);
              String OrganScal=OrganScalType.get("SCAL").toString();
              
              List room =new ArrayList();
		       int group_id =0;
		
		       if("equip".equals(type)){
		    	  filename="公办中小学校（设备设施）录入表.xlsx";
			      group_id=1;
			      room =Common.getAllRoomName(organ_id);
		       }
		       if("instrument".equals(type)){
		    	  room =Common.getInstrumentRoomName();
		    	  filename="公办中小学校（仪器）录入表 .xlsx";
		       if("A".equals(OrganScal)){
			   group_id=9;
		       }
		       if("B".equals(OrganScal)){
			   group_id=10;
		       } 
		       if("C".equals(OrganScal)){
			   group_id=11;
		       }}
		      // System.out.println("group_id=="+group_id);
		     String sql = "select  ROOM_STAND_EQUIP.* ,ROOM_TYPE.ROOM_TYPE_NAME ,room.room_id,EQUIP_TYPE.ASSET_TYPE_NAME,EQUIP_TYPE.UNIT_CODE,room.room_name" +
				"  FROM REF_ROOM_TYPE, ROOM_STAND_EQUIP,ROOM_TYPE,EQUIP_TYPE,room " +
				" WHERE REF_ROOM_TYPE.room_id=room.room_id and  REF_ROOM_TYPE.room_type_id= ROOM_STAND_EQUIP.ROOM_TYPE_ID " +
				" and  ROOM_TYPE.ROOM_TYPE_id =ROOM_STAND_EQUIP.ROOM_TYPE_ID " +
				" and ROOM_STAND_EQUIP.ORGAN_TYPE='"+OrganScal+"' "+
				" and EQUIP_TYPE.equip_type_id= ROOM_STAND_EQUIP.EQUIP_TYPE_ID and room.organ_id="+organ_id+" " +
				
				" and equip_type.group_id in ("+ Common.getChildGroup(group_id) + ") group by ROOM_STAND_EQUIP.ROOM_STAND_EQUIP_id,room.room_id";
		    List biaozhunlist = base.query(sql);
		    List list = new ArrayList();
		    if(null!=biaozhunlist&&biaozhunlist.size()>0){
		       sql="select (equip.equiP_name) as ASSET_TYPE_NAME,equip.equip_type_id,room.room_id,equip.equip_specify,sum(count) as COUNT,EQUIP_ID,UNIT_PRICE,EQUIP.REMARK,EQUIP_BRAND," +
		       	" PURCHASE_DATE,USER_NAME,REAL_POSITION from equip,room where equip.room_id=room.room_id and room.organ_id="+organ_id+" group by equip_type_id,room.room_id ";
		       List equip= base.query(sql);
		       for(int i=0;i<biaozhunlist.size();i++){
		    	   HashMap map = (HashMap) biaozhunlist.get(i);
		    	   String equip_type = map.get("EQUIP_TYPE_ID")+"";
		    	   String room_id = map.get("ROOM_ID")+"";

		    	   for(int j=0;j<equip.size();j++){
		    		   HashMap equipmap = (HashMap) equip.get(j);
		    		   String e_type = equipmap.get("EQUIP_TYPE_ID")+"";
		    		   String r_id = equipmap.get("ROOM_ID")+"";
		    		   
		    		   
		    		   if(room_id.equals(r_id)){

		    			   if(equip_type.equals(e_type)){
		    			   map.put("PRICE", equipmap.get("UNIT_PRICE"));
		    			   map.put("REMARK", equipmap.get("REMARK"));
		    			   map.put("EQUIP_BRAND", equipmap.get("EQUIP_BRAND"));
		    			   map.put("E_SPEFY", equipmap.get("EQUIP_SPECIFY"));
		    			   map.put("COUNT", equipmap.get("COUNT"));
		    			   map.put("PURCHASE_DATE", equipmap.get("PURCHASE_DATE"));
		    			   map.put("USER_NAME", equipmap.get("USER_NAME"));
		    			   map.put("REAL_POSITION", equipmap.get("REAL_POSITION"));
		    			   }
		    		   }
		    	   }
		    	   list.add(map);
		       }
		
		    }
		    if("equip".equals(type)){
		    sql="SELECT room.* FROM room,ref_room_type,room_stand_equip where room.room_id=ref_room_type.room_id "
		    		+ " and room_stand_equip.room_type_id =ref_room_type.room_type_id and organ_id="+organ_id+""
		    		+ " group by room.room_id";
		    List equiproom= base.query(sql);
		    String room_id="";
		    for(int i=0;i<equiproom.size();i++){
		    	HashMap map = (HashMap) equiproom.get(i);
		    	room_id+=","+map.get("ROOM_ID");
		    }
		    
		   // System.out.println("room_id==="+room_id);		
		    sql=" select (equip.equip_name) as ASSET_TYPE_NAME, equip.equip_type_id,room.room_id,equip.equip_specify,sum(COUNT) as COUNT,EQUIP_ID,UNIT_PRICE,EQUIP.REMARK,EQUIP_BRAND,"
		    		+ " PURCHASE_DATE,USER_NAME,REAL_POSITION from  room ,equip"
		    		+ " left join equip_type on equip.equiP_type_id=equip_type.equip_type_id "
		    		+ " where room.room_id=equip.room_id  and equip_type.group_id not in (2,10,23,19,22,21,18,45,46,20,25,50,52,51"
		    		+ ",53,24,47,49,48,9,14,15,12,36,37,13,17,41,43,42,44,16,38,40,39,11,33,28,146,155,"
		    		+ "156,181,184,182,183,185,161,162,165,164,163,147,159,160,157,158,149,154,151,152,"
		    		+ "153,150,166,169,168,170,171,167,148,172,173,174,175,177,176,179,178,180,30,94,95"
		    		+ ",105,106,107,108,100,101,104,103,102,98,99,96,97,89,93,91,92,90,85,87,88,86,32,26,222,223,226,227,229,228,224,225,220,221,27,188,199,204,200,201,203,202,216,219"
		    		+ ",217,218,207,208,209,189,205,206,191,198,196,194,195,197,193,192,210,213,212,214,211,190,215,187,186,29,109,114,115,113,141,144,142,143,124,125,127,126,110,118,119,123,122,120,145,121,116,117,112,128,131,130,132,133,129,134,135,136,137,139,"
		    		+ "138,140,111,35,57,59,58,60,31,61,80,84,83,81,82,72,73,75,74,62,70,71,64,69,67,68,66,65,76,77,78,79,63,34,54,56,55)"
		    		+ " and room.organ_id="+organ_id+" and equip.equip_status!='V' group by equip.equip_type_id,room.room_id order by room.room_id";
		    List allroom= base.query(sql);
		    for(int p=0;p<list.size();p++){
		    	HashMap row = (HashMap) list.get(p);
		    	String ro_id=row.get("ROOM_ID")+"";
		    	String etid=row.get("EQUIP_TYPE_ID")+"";
		    	
		    	 for(int i=0;i<allroom.size();i++){
		    		 HashMap map = (HashMap) allroom.get(i);
				    	String roid=map.get("ROOM_ID")+"";
				    	String equip_type_id=map.get("EQUIP_TYPE_ID").toString();
				    	
				    	

		    	if(roid.equals(ro_id)){

		    		//System.out.println(equip_type_id +",           etid="+etid);
		    		if(equip_type_id.equals(etid)||equip_type_id==etid){
		    			//System.out.println("roid=="+roid+"    equip_name="+map.get("ASSET_TYPE_NAME")+"   EQUIP_TYPE_ID="+map.get("EQUIP_TYPE_ID"));
		    		allroom.remove(i);}
		    		
		    	}
		    	}
		    }
		    System.out.println("allroom.size()==="+allroom.size());
		    for(int i=0;i<allroom.size();i++){
		    	HashMap map = (HashMap) allroom.get(i);
		    	String roid=map.get("ROOM_ID")+"";
		    	
		    	//System.out.println("roid=="+roid+"    equip_name="+map.get("ASSET_TYPE_NAME")+"   EQUIP_TYPE_ID="+map.get("EQUIP_TYPE_ID"));
		    	if("410".equals(organ_id)&&"15".equals(roid)){
		    		continue;
		    	}else{
		    		list.add(map);
		    	}
		    }
		    sql="select (equip.equip_name) as ASSET_TYPE_NAME, equip.equip_type_id,equip.room_id,equip.equip_specify,COUNT,EQUIP_ID,UNIT_PRICE,EQUIP.REMARK,EQUIP_BRAND,"
		    		+ " PURCHASE_DATE,USER_NAME,REAL_POSITION from equip where equip.equip_type_id is null";
		    List notype= base.query(sql);
		    for(int i=0;i<notype.size();i++){
		    	HashMap map = (HashMap) notype.get(i);
		    	String roid=map.get("ROOM_ID")+"";
		    	
		    	//System.out.println("roid=="+roid+"    equip_name="+map.get("ASSET_TYPE_NAME")+"   EQUIP_TYPE_ID="+map.get("EQUIP_TYPE_ID"));
		    	if("410".equals(organ_id)&&"15".equals(roid)){
		    		continue;
		    	}else{
		    		list.add(map);
		    	}
		    }
		    }
		    data.put("equip", Common.ConvertEmpty(list));
		    data.put("room", Common.ConvertEmpty(room));
		    

     createExportEquipExcel(filename,data,type);
	return null;
}



	
	
	
	
	
	
	
			private String createExportEquipExcel(String filename, HashMap data,String type)
			        throws Exception
			{
				// 选择模板文件：
			
			
				String realpath = SysConfig.getAttrValue("FileServerPath")
				        + File.separator;
				FileInputStream input = new FileInputStream(new File(realpath
				        + "moban/" + filename)); // 读取的文件路径
			
			
			
				OutputStream os = new FileOutputStream(
				        SysConfig.getAttrValue("FileServerPath") + File.separator
				                + "Excel/" + filename);
			
				XSSFWorkbook wb = new XSSFWorkbook(new BufferedInputStream(input));
			
				XSSFSheet sheet;
			
			    List equip = (List)data.get("equip");
			    List room = (List)data.get("room");
			    for(int i=0;i<room.size();i++){
			    	HashMap row = (HashMap) room.get(i);
			    	
		            	sheet =wb.cloneSheet(0);
		            	String room_name=row.get("ROOM_NAME").toString();
		            	if(room_name.contains("/")){
		            		room_name=room_name.replace("/", "(");
		            		room_name= room_name+")";
		            	}
		            	wb.setSheetName(i+1,room_name);
		            	
		            	String room_id =row.get("ROOM_ID").toString();
		            	sheet.getRow(0).getCell(0).setCellValue(room_name);
		            	sheet.getRow(0).getCell(10).setCellValue(room_id);
		            	int no=2;
		            	for(int j=0;j<equip.size();j++){
		            		
		            		HashMap map = (HashMap) equip.get(j);
		            		String r_id =map.get("ROOM_ID")+"";
		            		
		            		if(room_id.equals(r_id)){
                               if("equip".equals(type)){  
                            	   XSSFRow Erow = sheet.createRow((int) no);
                            	   
	                                if(null!=map.get("ASSET_TYPE_NAME")&&!"".equals(map.get("ASSET_TYPE_NAME").toString())){
	                                	Erow.createCell(0).setCellValue(map.get("ASSET_TYPE_NAME").toString().trim());
	                                	Erow.getCell(0).getCellStyle().setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中
	                                	
	                                	Erow.getCell(0).getCellStyle().setWrapText(true);//设置自动换行
	                                }
			            			
	                                if(null!=map.get("EQUIP_BRAND")&&!"".equals(map.get("EQUIP_BRAND")+"")){
			            			    Erow.createCell(1).setCellValue(map.get("EQUIP_BRAND").toString().trim());
			            			   // Erow.getCell(1).getCellStyle().setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中
			            			    }
			            			
	                                if(null!=map.get("E_SPEFY")&&!"".equals(map.get("E_SPEFY")+"")){
			            			    Erow.createCell(2).setCellValue(map.get("E_SPEFY").toString().trim());	}
			            			
	                                if(null!=map.get("UNIT_CODE")&&!"".equals(map.get("UNIT_CODE")+"")){
			            			    Erow.createCell(3).setCellValue(map.get("UNIT_CODE").toString().trim());}	
			            			
	                                if(null!=map.get("PRICE")&&!"".equals(map.get("PRICE")+"")){
			            			    Erow.createCell(4).setCellValue(map.get("PRICE").toString().trim());	}
			            			
	                                if(null!=map.get("COUNT")&&!"".equals(map.get("COUNT")+"")){
			            			    Erow.createCell(5).setCellValue(map.get("COUNT").toString().trim());	}
			            			
	                                if(null!=map.get("PURCHASE_DATE")&&!"".equals(map.get("PURCHASE_DATE")+"")){
			            			    Erow.createCell(6).setCellValue(map.get("PURCHASE_DATE").toString().substring(0,10).trim());	}
			            			
	                                if(null!=map.get("USER_NAME")&&!"".equals(map.get("USER_NAME")+"")){
			            			    Erow.createCell(7).setCellValue(map.get("USER_NAME").toString().trim());	}
			            			
	                                if(null!=map.get("REAL_POSITION")&&!"".equals(map.get("REAL_POSITION")+"")){
			            			    Erow.createCell(8).setCellValue(map.get("REAL_POSITION").toString().trim());}
			            			
	                                if(null!=map.get("EQUIP_SPECIFY")&&!"".equals(map.get("EQUIP_SPECIFY")+"")){
			            			    Erow.createCell(9).setCellValue(map.get("EQUIP_SPECIFY").toString().trim());
			            			   // Erow.getCell(9).getCellStyle().setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中    
	                                }
	                                Erow.createCell(10).setCellValue(map.get("EQUIP_TYPE_ID").toString());
                               }else{
                            	   
                            	   if(null!=map.get("ASSET_TYPE_NAME")&&!"".equals(map.get("ASSET_TYPE_NAME").toString())){
                                   	sheet.getRow(no).getCell(0).setCellValue(map.get("ASSET_TYPE_NAME").toString().trim());	}
   		            			
                                   if(null!=map.get("EQUIP_SPECIFY")&&!"".equals(map.get("EQUIP_SPECIFY")+"")){
   		            			    sheet.getRow(no).getCell(1).setCellValue(map.get("EQUIP_SPECIFY").toString().trim());	}
   		            			
                                   if(null!=map.get("UNIT_CODE")&&!"".equals(map.get("UNIT_CODE")+"")){
   		            			    sheet.getRow(no).getCell(2).setCellValue(map.get("UNIT_CODE").toString().trim());	}
   		            			
                                   if(null!=map.get("UNIT_PRICE")&&!"".equals(map.get("UNIT_PRICE")+"")){
   		            			    sheet.getRow(no).getCell(3).setCellValue(map.get("UNIT_PRICE").toString().trim());}	
   		            			
                                   if(null!=map.get("COUNT")&&!"".equals(map.get("COUNT")+"")){
   		            			    sheet.getRow(no).getCell(4).setCellValue(map.get("COUNT").toString().trim());	}
                                   
                                   if(null!=map.get("PURCHASE_DATE")&&!"".equals(map.get("PURCHASE_DATE")+"")){
      		            			    sheet.getRow(no).getCell(5).setCellValue(map.get("PURCHASE_DATE").toString().substring(0,10).trim());	}

   		            			
                                   if(null!=map.get("PRICE")&&!"".equals(map.get("PRICE")+"")){
   		            			    sheet.getRow(no).getCell(6).setCellValue(map.get("PRICE").toString().trim());	}
   		            			
                                     		            			
                                   if(null!=map.get("USER_NAME")&&!"".equals(map.get("USER_NAME")+"")){
   		            			    sheet.getRow(no).getCell(7).setCellValue(map.get("USER_NAME").toString().trim());	}
   		            			
                                   if(null!=map.get("REAL_POSITION")&&!"".equals(map.get("REAL_POSITION")+"")){
   		            			    sheet.getRow(no).getCell(8).setCellValue(map.get("REAL_POSITION").toString().trim());}
   		            			
                                   if(null!=map.get("REMARK")&&!"".equals(map.get("REMARK")+"")){
   		            			    sheet.getRow(no).getCell(9).setCellValue(map.get("REMARK").toString().trim());}
                                   sheet.getRow(no).getCell(10).setCellValue(map.get("EQUIP_TYPE_ID").toString().trim());
                            	   
                               }
		            			no++;
		            		}
		            	}
			    	
			    }
			
			    wb.removeSheetAt(0);//删除sheet0
			
			    String name=null;
			    if("equip".equals(type)){ 
			    	name="equip.xlsx";
			    }else{
			    	name="instrument.xlsx";
			    }
			
				wb.write(os);
				input.close();
				os.close();
				HttpServletResponse response = null;
				OutputStream out = null;
				try
				{
					response = ServletActionContext.getResponse();
					response.setDateHeader("Expires", 0);
					response.setContentType("multipart/form-data");
					response.setHeader(
					        "Content-Disposition",
					        "attachment"
					                + "; filename=\""
					                + name + "\"");
					out = response.getOutputStream();
					File file = new File(SysConfig.getAttrValue("FileServerPath")
					        + File.separator + "Excel/" + filename);
					InputStream in = new FileInputStream(file);
					byte[] readed = new byte[1024];
					int len = in.read(readed);
					while (len > 0)
					{
						out.write(readed, 0, len);
						len = in.read(readed);
					}
					System.out.println("我的天");
					out.flush();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					if (out != null)
					{
						try
						{
							out.close();
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}
			
				return null;
			}
				
				
	
	
	
	

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public File getMyfile1() {
		return myfile1;
	}
	public void setMyfile1(File myfile1) {
		this.myfile1 = myfile1;
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
	
	public String getPk_id() {
		return pk_id;
	}
	public void setPk_id(String pk_id) {
		this.pk_id = pk_id;
	}

	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}



	public int getTotal() {
		return total;
	}



	public void setTotal(int total) {
		this.total = total;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}

	public String getEquip_type_id() {
		return equip_type_id;
	}

	public void setEquip_type_id(String equip_type_id) {
		this.equip_type_id = equip_type_id;
	}

	public String getRoom_name() {
		return room_name;
	}



	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}



	public String getOrgan_id() {
		return organ_id;
	}



	public void setOrgan_id(String organ_id) {
		this.organ_id = organ_id;
	}



	public String getEquip_name() {
		return equip_name;
	}



	public void setEquip_name(String equip_name) {
		this.equip_name = equip_name;
	}
	public String getRoom_type_id() {
		return room_type_id;
	}
	public void setRoom_type_id(String room_type_id) {
		this.room_type_id = room_type_id;
	}
	
	public boolean isGroup_id() {
		return group_id;
	}
	public void setGroup_id(boolean group_id) {
		this.group_id = group_id;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public Equip getEquip() {
		return equip;
	}
	public void setEquip(Equip equip) {
		this.equip = equip;
	}
	public String getRoom_id() {
		return room_id;
	}
	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}
	public void setEquip_brand(String equip_brand) {
		this.equip_brand = equip_brand;
	}
	public void setEquip_specify(String equip_specify) {
		this.equip_specify = equip_specify;
	}
	public void setUnit_code(String unit_code) {
		this.unit_code = unit_code;
	}
	public void setUnit_price(String unit_price) {
		this.unit_price = unit_price;
	}
	public void setPurchase_date(String purchase_date) {
		this.purchase_date = purchase_date;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public void setReal_position(String real_position) {
		this.real_position = real_position;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setEquip_id(String equip_id) {
		this.equip_id = equip_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public void setTextChange(String textChange) {
		this.textChange = textChange;
	}


}
