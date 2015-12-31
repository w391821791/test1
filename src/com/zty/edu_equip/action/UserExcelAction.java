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


import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.po.base.Util;
import com.po.serverbase.BaseDao;
import com.po.serverbase.SeqenceGenerator;
import com.po.serverbase.SysConfig;
import com.po.serverbase.SystemInfo;


import com.zty.edu_equip.entity.BatchImport;
import com.zty.edu_equip.entity.Equip;
import com.zty.edu_equip.entity.EquipPart;
import com.zty.edu_equip.entity.Organ;
import com.zty.edu_equip.entity.RefBatchImportEquip;
import com.zty.edu_equip.entity.RefRoomType;
import com.zty.edu_equip.entity.Room;
import com.zty.edu_equip.util.Common;
import com.zty.edu_equip.util.PageUtil;
public class UserExcelAction extends ActionSupport {


	private String url;
	private File myfile1;
	private String filename;
	//分页工具类
	private PageUtil pageUtil;
	// 使用map 获取页面传递过来的数据 传递普通数据
	private Map<String, String[]> pageMap;

	// 使用 map 给页面传递数据
	private Map map;
	private String pk_id;
	private String organ_id;
	private String import_id;
	private String flag;
	public String jump() {
		map = (Map) ActionContext.getContext().get("request");
		return url;
	}
	
	public String showexcelHistoryAsset() {
		map = (Map) ActionContext.getContext().get("request");
		map.put("pk_id", pk_id);
		return "excelHistoryAsset";
	}
	
	public String excel() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			map = (Map) ActionContext.getContext().get("request");
			int Numbers = Common.getFileNameNumber();
			File file = new File(SysConfig.getAttrValue("FileServerPath")
			        + File.separator + "Excel\\aa.xls");
			if(file.exists()){    file.delete();}
			myfile1.renameTo(file);
			List fileDatas1= new ArrayList();//学校List(SHEET0)

			String firstRowNo = request.getParameter("FIRST_ROW_NO");
			
			String[] colNoValues = new String[] { "0", "1", "2", "3", "4", "5",
			        "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17" };
			
			if(filename.contains("小学")){
				colNoValues=new String[] { "0", "1", "2", "3", "4", "5",
				        "6", "7", "8", "9", "10", "11", "12", "13", "14", "15" };
			}
			
			//sheet1,2,3,5 代表小学初中高中形式相同的sheet;colNo1,2,3,5表示小学初中高中的EXCEL列数
			String[] sheetno1 = new String[] {"2", "10","12"};
			String[] colNo1 =new String[] { "0", "1", "2", "3","4",  "5","6", "7", "8", "9", "10", "11", "12","13"};
			
			String[] sheetno2 = new String[] { "3",  "5", "7",  "9",  "11",  "13" };
			String[] colNo2 =new String[] { "0", "1", "2", "3","4",  "5","6", "7", "8","9"};
			
			String[] sheetno4 = new String[] { "8"};//初中，高中探究
			String[] colNo4 =new String[] { "0", "1", "2", "3","4", "5","6", "7", "8","9", "10", "11", "12","13","14"};
			
			
			fileDatas1 = Common.getDataFromExcel(file, "0", colNoValues,0);			
			
			BaseDao basedao = new BaseDao(SystemInfo.getMainJndi(), null);//学校，房间，装备
			
			BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);//仪器
			
			//批量导入记录
			BatchImport batchImport= new BatchImport();
			//batchImport.setImporter(importer);
			batchImport.setImportTime(Util.getNow());
			//batchImport.setImporter(UtilAction.getUser().getNickName());
			
			basedao.execute(batchImport);
			String import_id= batchImport.getPkValue();
			
			//导入学校
			String[] str1 = (String[]) fileDatas1.get(2);
			String organ_name=null;
			String address=null;
			String code=null;
			
			String[] str2 = (String[]) fileDatas1.get(3);
			String contact=null;
			String telephone=null;
			
			String[] str3 = (String[]) fileDatas1.get(5);
			String student_count=null;
			
			int group_id=0;//仪器仪表
			
			if(filename.contains("小学")){
				organ_name = str1[1];
				address =str1[7];
				contact=str2[1];
				telephone=str2[5];
				student_count=str3[10];
				group_id=9;
			}
			if(filename.contains("初中")){
				organ_name = str1[2];
				address =str1[8];
				contact=str2[2];
				telephone=str2[6];
				student_count=str3[11];
				sheetno1 = new String[] { "2","4", "6","10","12","14","16" };
				sheetno2 = new String[] { "3",  "5", "7",  "9",  "11",  "13" ,"15","17"};
				group_id=10;
				
			}
			if(filename.contains("高中")){
                organ_name = str1[2];
				address =str1[7];
				contact=str2[1];
				telephone=str2[5];
				student_count=str3[10];
				sheetno1 = new String[] { "2",  "4", "6", "10", "12" ,"16","18","20"};
				sheetno2 = new String[] { "3",  "5", "7",  "9",  "11",  "13" ,"15","17","19","21"};				
				sheetno4 = new String[] { "14"};
				group_id=11;
			}
			Organ organ=Common.getOrganByName(organ_name);
			if(null==organ){
				
				
				map.put("msg", "未找到该学校，请检查学校名称是否正确！");
				return "excel";
			}
			if(null!=contact&&!"".equals(contact)){
			    organ.setContact(contact);
			}
			if(null!=address&&!"".equals(address)){
				organ.setAddress(address);
			}			
			if(null!=telephone&&!"".equals(telephone)){
				organ.setTelephone(telephone);
			}			
			if(null!=student_count&&!"".equals(student_count)){
				organ.setStudentCount(Util.newBigDecimal(student_count));
			}
			
			
			basedao.addObj4Execute(organ);	
			
			String organ_id = organ.getPkValue();//机构ID
			HashMap RoomType = Common.getExcelRoomType();//获取房间类别
			HashMap EquipType=new HashMap();//获取装备类别
			if("0".equals(firstRowNo)){
			//导入房间，装备
			String[] colNo =new String[] { "0", "1", "2", "3","4",  "5",
			        "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17" };
			List fileDatas2 = Common.getDataFromExcel(file, "0", colNo,1);
			//room
			
			String room_type_name = null;
			String room_name=null;
			String person_count=null;
			String room_no=null;
			String area=null;
			String duty_man=null;
			String room_id = null;
			EquipType=Common.getEquipType(1);
			for(int i =2;i<fileDatas2.size();i++){
				String[] str =(String[]) fileDatas2.get(i);
				
				if(null!=str[0]&&!"".equals(str[0])){
					
					room_type_name=str[0];
					if("类别".equals(room_type_name)){
						continue;
					}
					if(room_type_name.contains("科学仪器室")){
						room_type_name="科学仪器室（含准备室丶实验员室）";
					}
					if(room_type_name.contains("多功能教室")){
						room_type_name="多功能教室（综合电教室）";
					}
					/*if("综合 实践活动室".equals(room_type_name)){
						room_type_name="综合实践活动室";
					}*/
					if(room_type_name.contains("学生阅览室")){
						room_type_name="学生阅览室";
					}
					if(room_type_name.contains("教师阅览室")){
						room_type_name="教师阅览室";
					}
					if(room_type_name.contains("藏书室")){
						room_type_name="图书馆藏书室";
					}
					if(room_type_name.contains("演播室")){
						room_type_name="演播室";
					}
					if(room_type_name.contains("广播室")){
						room_type_name="广播室";
					}
					if(room_type_name.contains("美术室")){
						room_type_name="美术室";
					}
					if(room_type_name.contains("卫生")){
						room_type_name="卫生室";
					}
					if(room_type_name.contains("物理准备室")){
						room_type_name="物理准备室";
					}
					if(room_type_name.contains("化学准备室")){
						room_type_name="化学准备室";
					}
					if(room_type_name.contains("生物准备室")){
						room_type_name="生物准备室";
					}
					if(room_type_name.contains("阶梯教室")){
						room_type_name="阶梯教室";
					}
					if("综合实践活动（劳技）室".equals(room_type_name)){
						room_type_name="综合实践活动室";
					}
					if("校史（德育）展览室".equals(room_type_name)){
						room_type_name="校史展览室";
					}
					if("药品室".equals(room_type_name)){
						room_type_name="化学药品室";
					}
					if("危险药品室".equals(room_type_name)){
						room_type_name="化学危险药品室";
					}
					if("培养室".equals(room_type_name)){
						room_type_name="生物培养室";
					}

					if(room_type_name.contains("\n")){
					room_type_name=room_type_name.replaceAll("\n", "");
					}
					
					
					BigDecimal room_type_id =(BigDecimal) RoomType.get(room_type_name);
					room_name=str[1];
					if("".equals(room_name)){
						room_name=room_type_name;
					}
					
					person_count=str[4];
					room_no=str[2];
					area=str[3];
					duty_man=str[5];
					if(!"0".equals(room_name)){
					Room room;
					room_id=Common.getRoomIdByRoom(room_name);
					if(null==room_id){	
					room = new Room();}
					else{room=new Room(room_id);}
					room.setArea(area);
					room.setDutyMan(duty_man);
					room.setRoomName(room_name);
					room.setRoomNo(room_no);
					room.setCardNo(room.getPkValue());
					room.setOrganId(Util.newBigDecimal(organ_id));
					room.setPersonCount(Util.newBigDecimal(person_count));
					basedao.addObj4Execute(room);
					room_id=room.getPkValue();
					RefRoomType refRoomType= new RefRoomType();
					refRoomType.setRoomTypeId(room_type_id);
					refRoomType.setRoomId(Util.newBigDecimal(room_id));
					basedao.addObj4Execute(refRoomType);
					}
					/*System.out.println(room_name);
					System.out.println(room_type_name);*/
				}
				/*//equip_name
				 
				String equip_name=str[8];
				String equip_brand=str[9];
				String equip_specify=str[10];
				String unit_code=str[11];
				String unit_price=str[12];
				String count = str[13];
				String purchase_date = str[14];
				String user_name = str[15];
				String real_position=str[16];
				String remark=str[17];
				if(equip_name.contains("等")){
					equip_name=equip_name.replace("等", "");
				}
				if("有线".equals(equip_name)||"无线麦克风".equals(equip_name)){
					equip_name="有线、无线麦克风";
				}
				
				BigDecimal equip_type_id=(BigDecimal) EquipType.get(equip_name);
				if(null!=count&&!"".equals(count)){
				if(Integer.parseInt(count)>0){
					int equip_count =Integer.parseInt(count);
					purchase_date=Common.Dateformat(purchase_date);

					if("图书".equals(equip_name)||"藏书".equals(equip_name)){
						equip_count=1;
					}else{count="1";}
					
					for(int j=0;j<equip_count;j++){
						Equip equip = new Equip(); 
						int k = j + 1;
						
						if (equip_count == 1)
							equip.setEquipName(equip_name);
						if (equip_count > 1)
							equip.setEquipName(equip_name + "(" + k + ")");
				        equip.setCount(Util.newBigDecimal(count));
				        equip.setCreateTime(Util.getNow());
				        if(null!=purchase_date&&!"".equals(purchase_date)){
				        equip.setPurchaseDate(Util.parseTimestamp(purchase_date));}
				        equip.setEquipBrand(equip_brand);
				        equip.setEquipSpecify(equip_specify);
				        equip.setUnitCode(unit_code);
				        equip.setRoomId(Util.newBigDecimal(room_id));
				        equip.setEquipTypeId(equip_type_id);
				        equip.setRealPosition(real_position);
				        equip.setUserName(user_name);
				        equip.setRemark(remark);
				        if(null!=unit_price&&!"".equals(unit_price)){
				        equip.setUnitPrice(Util.newBigDecimal(unit_price));}
				        equip.setEquipStatus("A");
				        equip.setCardStatus("A");
				        equip.setVersion(SeqenceGenerator.getNextOutranetVer());
				        basedao.addObj4Execute(equip);
				        String equip_id= equip.getPkValue();
				
				        //导入关联
				        RefBatchImportEquip refBatchImportEquip=new RefBatchImportEquip();
				        refBatchImportEquip.setEquipId(Util.newBigDecimal(equip_id));
				        refBatchImportEquip.setImportId(Util.newBigDecimal(import_id));
				        basedao.addObj4Execute(refBatchImportEquip);
				
				
				}}}*/
			}
			basedao.execute();//导入房间，装备
			
			}else{
				EquipType=Common.getEquipType(group_id);
			//仪器1(sheet1)
			List fileDatas3= new ArrayList();
			
			for(int i=0;i<sheetno1.length;i++){
				System.out.println("sheetno1=="+sheetno1[i]+"   sheetno1.length="+sheetno1.length);
			fileDatas3 = Common.getDataFromExcel(file, "0", colNo1,Integer.parseInt(sheetno1[i]));
			for(int j =2;j<fileDatas3.size();j++){
			String[] str =(String[]) fileDatas3.get(j);
			    String name =  str[1];
			    String unit_code =  str[3];
			    String count =  str[6];
			    String purchase_date =  str[7];	
			    String unit_price =  str[8];
			    String user_name =  str[9];
			    String real_position =  str[10];
			    String equip_specify =  str[11];
			    String remark =  str[12];
			    String room_id =  str[13];
			    
			    
			    if(null!=count&&!"".equals(count)){
					if(Integer.parseInt(count)>0){
						BigDecimal equip_type_id=(BigDecimal) EquipType.get(name);
						
					    purchase_date=Common.Dateformat(purchase_date);
			    	
			    		
							Equip equip = new Equip(); 
							
							equip.setEquipName(name);
                            equip.setCardStatus("A");
                            equip.setPurchaseDate(Util.parseTimestamp(purchase_date));
                            equip.setCreateTime(Util.getNow());
                            equip.setEquipSpecify(equip_specify);
                            equip.setEquipStatus("A");
                            equip.setEquipTypeId(equip_type_id);
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
			
			
			   
			}}
			
			//仪器2(sheet2)
			List fileDatas4= new ArrayList();
			for(int i=0;i<sheetno2.length;i++)
			{
				System.out.println("sheetno2==="+sheetno2[i]);
			fileDatas4 = Common.getDataFromExcel(file, "0", colNo2,Integer.parseInt(sheetno2[i]));
			   for(int j =1;j<fileDatas4.size()-2;j++)
			   {
			   String[] str =(String[]) fileDatas4.get(j);	
			   String name =  str[0];
		       String unit_code =  str[2];
		       String count =  str[3];
		       String purchase_date =  str[4];	
		       String unit_price=str[5];	
		       String user_name =  str[6];
		       String real_position =  str[7];
		       String equip_specify =  str[8];
		       String room_id =  str[9];
		       if(null!=count&&!"".equals(count)){
					if(Integer.parseInt(count)>0){
						BigDecimal equip_type_id=(BigDecimal) EquipType.get(name);
					    purchase_date=Common.Dateformat(purchase_date);

						Equip equip = new Equip(); 
						equip.setEquipName(name);
                        equip.setCardStatus("A");
                        equip.setPurchaseDate(Util.parseTimestamp(purchase_date));
                        equip.setCreateTime(Util.getNow());
                        equip.setEquipSpecify(equip_specify);
                        equip.setEquipStatus("A");
                        equip.setEquipTypeId(equip_type_id);
                        if(null!=unit_price&&!"".equals(unit_price)){
            				equip.setUnitPrice(Util.newBigDecimal(unit_price));}
                        equip.setUserName(user_name);
                        equip.setRealPosition(real_position);
                        equip.setUnitCode(unit_code);
                        equip.setCount(Util.newBigDecimal(count));
                        equip.setRoomId(Util.newBigDecimal(room_id));
                        equip.setVersion(SeqenceGenerator.getNextOutranetVer());
                        base.addObj4Execute(equip);
        				String equip_id= equip.getPkValue();
        				
        				//导入关联
        				RefBatchImportEquip refBatchImportEquip=new RefBatchImportEquip();
        				refBatchImportEquip.setEquipId(Util.newBigDecimal(equip_id));
        				refBatchImportEquip.setImportId(Util.newBigDecimal(import_id));
        				base.addObj4Execute(refBatchImportEquip);
        				
		    		
			  }
		       }}
         }
			if(filename.contains("小学"))
			{
			//仪器（小学sheet4）小学科学探究1
			List fileDatas6= new ArrayList();
			String[] colNo3 =new String[] { "0", "1", "2", "3","4", "5","6", "7", "8","9", "10", "11", "12","13","14"};
			fileDatas6 = Common.getDataFromExcel(file, "0", colNo3,4);
			String equip_id = null;
			   for(int j =1;j<fileDatas6.size()-2;j++)
			   {
			   String[] str =(String[]) fileDatas6.get(j);	
			   String number =  str[0];
			   String name =  str[1];
		       String unit_code =  str[3];
		       String count =  str[7];
		       String purchase_date =  str[8];	
		       String unit_price=str[9];	
		       String user_name =  str[10];
		       String real_position =  str[11];
		       String equip_specify =  str[12];
		       String remark =  str[13];
		       String room_id =  str[14];
		       
		       
		       if(null!=count&&!"".equals(count)){
					if(Integer.parseInt(count)>0){
						BigDecimal equip_type_id=(BigDecimal) EquipType.get(name);
					    purchase_date=Common.Dateformat(purchase_date);
					    if(number.length()>4){

						       Equip equip = new Equip(); 
							   equip.setEquipName(name);
                               equip.setCardStatus("A");
                               equip.setPurchaseDate(Util.parseTimestamp(purchase_date));
                               equip.setCreateTime(Util.getNow());
                               equip.setEquipSpecify(equip_specify);
                               equip.setEquipStatus("A");
                               equip.setEquipTypeId(equip_type_id);
                               if(null!=unit_price&&!"".equals(unit_price)){
            			          equip.setUnitPrice(Util.newBigDecimal(unit_price));}
                               equip.setUserName(user_name);
                               equip.setRoomId(Util.newBigDecimal(room_id));
                               equip.setRealPosition(real_position);
                               equip.setUnitCode(unit_code);
                               equip.setCount(Util.newBigDecimal(count));
                               equip.setRemark(remark);
                               equip.setVersion(SeqenceGenerator.getNextOutranetVer());
                               base.addObj4Execute(equip);
        				       equip_id= equip.getPkValue();
        				
        				       //导入关联
        				       RefBatchImportEquip refBatchImportEquip=new RefBatchImportEquip();
        				       refBatchImportEquip.setEquipId(Util.newBigDecimal(equip_id));
        				       refBatchImportEquip.setImportId(Util.newBigDecimal(import_id));
        				       base.addObj4Execute(refBatchImportEquip);
		    		          
					    }else{

					    		  
					    		   EquipPart equipPart = new EquipPart();					    		   
					    		   equipPart.setPartName(name);
								   equipPart.setPurchaseDate(Util.parseTimestamp(purchase_date));
								   equipPart.setCreateTime(Util.getNow());
								   equipPart.setEquipId(Util.newBigDecimal(equip_id));
								   equipPart.setPartSpecify(equip_specify);
								   equipPart.setCount(Util.newBigDecimal(count));
								   equipPart.setEquipStatus("A");
								   if(null!=unit_price&&!"".equals(unit_price)){
		                               equipPart.setUnitPrice(Util.newBigDecimal(unit_price));}
		                           equipPart.setUserName(user_name);
		                           equipPart.setRealPosition(real_position);
		                           equipPart.setUnitCode(unit_code);
		                           base.addObj4Execute(equipPart);

					    	       
					    }
			  }}}
			   
			   
			 //仪器5(sheet5)小学体育
				List fileDatas7= new ArrayList();
				String[] colNo5 =new String[] { "0", "1", "2", "3","4",  "5","6", "7", "8","9", "10", "11","12"};
				fileDatas7 = Common.getDataFromExcel(file, "0", colNo5,6);
				   for(int j =1;j<fileDatas7.size()-2;j++)
				   {
				   String[] str =(String[]) fileDatas7.get(j);	
				   String name =  str[0];
			       String unit_code =  str[2];
			       String count =  str[5];
			       String purchase_date =  str[6];	
			       String unit_price=str[7];	
			       String user_name =  str[8];
			       String real_position =  str[9];
			       String equip_specify =  str[10];
			       String remark =  str[11];
			       String room_id =  str[12];
			       
			       if(null!=count&&!"".equals(count)){
						if(Integer.parseInt(count)>0){
							BigDecimal equip_type_id=(BigDecimal) EquipType.get(name);
						    purchase_date=Common.Dateformat(purchase_date);

							Equip equip = new Equip(); 							
							equip.setEquipName(name);
	                        equip.setCardStatus("A");
	                        equip.setPurchaseDate(Util.parseTimestamp(purchase_date));
	                        equip.setCreateTime(Util.getNow());
	                        equip.setEquipSpecify(equip_specify);
	                        equip.setEquipStatus("A");
	                        equip.setCount(Util.newBigDecimal(count));
	                        equip.setEquipTypeId(equip_type_id);
	                        if(null!=unit_price&&!"".equals(unit_price)){
	            				equip.setUnitPrice(Util.newBigDecimal(unit_price));}
	                        equip.setUserName(user_name);
	                        equip.setRealPosition(real_position);
	                        equip.setUnitCode(unit_code);
	                        equip.setRoomId(Util.newBigDecimal(room_id));
	                        equip.setRemark(remark);
	                        equip.setVersion(SeqenceGenerator.getNextOutranetVer());
	                        base.addObj4Execute(equip);
	        				equip_id= equip.getPkValue();
	        				
	        				//导入关联
	        				RefBatchImportEquip refBatchImportEquip=new RefBatchImportEquip();
	        				refBatchImportEquip.setEquipId(Util.newBigDecimal(equip_id));
	        				refBatchImportEquip.setImportId(Util.newBigDecimal(import_id));
	        				base.addObj4Execute(refBatchImportEquip);
	        				
			    		 
				  }}}
	         
				
				//仪器6(sheet6)小学田径
				List fileDatas8= new ArrayList();

				String[] colNo6 =new String[] { "0", "1", "2", "3","4",  "5","6", "7", "8", "9", "10", "11", "12","13"};//小学田径
				fileDatas8 = Common.getDataFromExcel(file, "0", colNo6,8);
				   for(int j =1;j<fileDatas8.size()-2;j++)
				   {
				   String[] str =(String[]) fileDatas8.get(j);	
				   String name =  str[1];
			       String unit_code =  str[3];
			       String count =  str[7];
			       String purchase_date =  str[8];	
			       String unit_price=str[9];	
			       String user_name =  str[10];
			       String real_position =  str[11];
			       String equip_specify =  str[12];
			       String room_id =  str[13];
			      
			       if(null!=count&&!"".equals(count)){
						if(Integer.parseInt(count)>0){
							BigDecimal equip_type_id=(BigDecimal) EquipType.get(name);
						    purchase_date=Common.Dateformat(purchase_date);
			    		
							Equip equip = new Equip(); 							
							equip.setEquipName(name);
	                        equip.setCardStatus("A");
	                        equip.setPurchaseDate(Util.parseTimestamp(purchase_date));
	                        equip.setCreateTime(Util.getNow());
	                        equip.setEquipSpecify(equip_specify);
	                        equip.setEquipStatus("A");
	                        equip.setEquipTypeId(equip_type_id);
	                        if(null!=unit_price&&!"".equals(unit_price)){
	            				equip.setUnitPrice(Util.newBigDecimal(unit_price));}
	                        equip.setUserName(user_name);
	                        equip.setCount(Util.newBigDecimal(count));
	                        equip.setRealPosition(real_position);
	                        equip.setUnitCode(unit_code);
	                        equip.setCount(Util.newBigDecimal(count));
	                        equip.setRoomId(Util.newBigDecimal(room_id));
	                        equip.setVersion(SeqenceGenerator.getNextOutranetVer());
	                        base.addObj4Execute(equip);
	        				equip_id= equip.getPkValue();
	        				
	        				//导入关联
	        				RefBatchImportEquip refBatchImportEquip=new RefBatchImportEquip();
	        				refBatchImportEquip.setEquipId(Util.newBigDecimal(equip_id));
	        				refBatchImportEquip.setImportId(Util.newBigDecimal(import_id));
	        				base.addObj4Execute(refBatchImportEquip);
	        				
			    		 
				  }}}
	         }
         
			
			//仪器(sheet8)高中生物
			if(filename.contains("高中")){
			List fileDatas9= new ArrayList();	
			String[]  shengwucolNo= new String[]{ "0", "1", "2", "3", "4", "5",
						        "6", "7", "8", "9", "10", "11", "12","13"};
			
			fileDatas9 = Common.getDataFromExcel(file, "0", shengwucolNo,8);
			String equip_id = null;
			   for(int j =3;j<fileDatas9.size()-1;j++)
			   {
			   String[] str =(String[]) fileDatas9.get(j);	
			   String number =  str[0];
			   String name =  str[1];
		       String unit_code =  str[3];
		       String count =  str[6];
		       String purchase_date =  str[7];	
		       String unit_price=str[8];	
		       String user_name =  str[9];
		       String real_position =  str[10];
		       String equip_specify =  str[11];
		       String remark =  str[12];
		       String room_id =  str[13];
		       
		       if(null!=count&&!"".equals(count)){
					if(Integer.parseInt(count)>0){
						BigDecimal equip_type_id=(BigDecimal) EquipType.get(name);
					    purchase_date=Common.Dateformat(purchase_date);
					    if(number.length()>4){

						       Equip equip = new Equip(); 
						      
							   equip.setEquipName(name);
						      
                               equip.setCardStatus("A");
                               equip.setPurchaseDate(Util.parseTimestamp(purchase_date));
                               equip.setCreateTime(Util.getNow());
                               equip.setEquipSpecify(equip_specify);
                               equip.setEquipStatus("A");
                               equip.setEquipTypeId(equip_type_id);
                               if(null!=unit_price&&!"".equals(unit_price)){
            			          equip.setUnitPrice(Util.newBigDecimal(unit_price));}
                               equip.setUserName(user_name);
                               equip.setRealPosition(real_position);
                               equip.setUnitCode(unit_code);
                               equip.setCount(Util.newBigDecimal(unit_price));
                               equip.setRemark(remark);
                               equip.setRoomId(Util.newBigDecimal(room_id));
                               equip.setVersion(SeqenceGenerator.getNextOutranetVer());
                               base.addObj4Execute(equip);
        				       equip_id= equip.getPkValue();
        				
        				       //导入关联
        				       RefBatchImportEquip refBatchImportEquip=new RefBatchImportEquip();
        				       refBatchImportEquip.setEquipId(Util.newBigDecimal(equip_id));
        				       refBatchImportEquip.setImportId(Util.newBigDecimal(import_id));
        				       base.addObj4Execute(refBatchImportEquip);
		    		          
					    }else{
					    	   
					    		   
					    		   EquipPart equipPart = new EquipPart();					    		  
					    		   equipPart.setPartName(name);
								   equipPart.setPurchaseDate(Util.parseTimestamp(purchase_date));
								   equipPart.setCreateTime(Util.getNow());
								   equipPart.setPartSpecify(equip_specify);
								   equipPart.setEquipStatus("A");
								   if(null!=unit_price&&!"".equals(unit_price)){
		                               equipPart.setUnitPrice(Util.newBigDecimal(unit_price));}
		                           equipPart.setUserName(user_name);
		                           equipPart.setEquipId(Util.newBigDecimal(equip_id));
		                           equipPart.setCount(Util.newBigDecimal(count));
		                           equipPart.setRealPosition(real_position);
		                           equipPart.setUnitCode(unit_code);
		                           base.addObj4Execute(equipPart);
		    		           }
					    	       
					    
			  }}}
			}
			
			if(filename.contains("高中")||filename.contains("初中")){
			//仪器4(sheet4)初中探究，高中探究
			List fileDatas10= new ArrayList();
			
			for(int i=0;i<sheetno4.length;i++){
				
				fileDatas10 = Common.getDataFromExcel(file, "0", colNo4,Integer.parseInt(sheetno4[i]));
			for(int j =1;j<fileDatas10.size();j++){
			String[] str =(String[]) fileDatas10.get(j);
			    String name =  str[1];
			    String unit_code =  str[3];
			    String count =  str[7];
			    String purchase_date =  str[8];	
			    String unit_price =  str[9];
			    String user_name =  str[10];
			    String real_position =  str[11];
			    String equip_specify =  str[12];
			    String remark =  str[13];
			    String room_id =  str[14];
			       
			    
			    if(null!=count&&!"".equals(count)){
					if(Integer.parseInt(count)>0){
						BigDecimal equip_type_id=(BigDecimal) EquipType.get(name);
					    purchase_date=Common.Dateformat(purchase_date);
			    	
			    		
							Equip equip = new Equip(); 
							equip.setEquipName(name);
                            equip.setCardStatus("A");
                            equip.setPurchaseDate(Util.parseTimestamp(purchase_date));
                            equip.setCreateTime(Util.getNow());
                            equip.setEquipSpecify(equip_specify);
                            equip.setEquipStatus("A");
                            equip.setEquipTypeId(equip_type_id);
                            if(null!=unit_price&&!"".equals(unit_price)){
                				equip.setUnitPrice(Util.newBigDecimal(unit_price));}
                            equip.setRemark(remark);
                            equip.setRoomId(Util.newBigDecimal(room_id));
                            equip.setUserName(user_name);
                            equip.setRealPosition(real_position);
                            equip.setCount(Util.newBigDecimal(count));
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
			    }  }
			}

			base.execute();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (myfile1 != null && myfile1.exists() && myfile1.isFile())
				try {
					myfile1.delete();
				} catch (Exception ee) {
				}

		}
		map.put("msg", "导入成功！");
		return "excel";

	}
	
	// 获取历史导入批次信息
		public String findExcelHistory() throws Exception
		{
			BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
			String sql=" select BATCH_IMPORT.*,count(a.EQUIP_ID) as TOTAL ," +
					" room.organ_id,(select count(equip.equip_id) from equip,ref_batch_import_equip b " +
					" where  equip.equip_status='A' and equip.EQUIP_ID=b.EQUIP_ID " +
					" and b.import_id=BATCH_IMPORT.import_id) as AVA_EQUIP " +
					" from room,equip,BATCH_IMPORT,ref_batch_import_equip a" +
					" where equip.EQUIP_ID=a.EQUIP_ID and equip.room_id=room.room_id " +
					" and BATCH_IMPORT.import_id= a.import_id group by BATCH_IMPORT.import_id";
			map=base.getRowsWithPaging(sql,pageUtil.getPage()-1,pageUtil.getLines());
			return SUCCESS;
		}

		// 获取历史导入资产信息
		public String findHistoryAsset() throws Exception
		{
			BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
			String sql=" SELECT equip.*,room.room_name,EQUIP_TYPE.asset_type_name FROM EQUIP_TYPE,BATCH_IMPORT,equip,room,ref_batch_import_equip WHERE " +
					" equip.equip_ID=ref_batch_import_equip.equip_ID AND BATCH_IMPORT.import_id=ref_batch_import_equip.import_id AND" +
					" equip.room_id=room.room_id and EQUIP_TYPE.EQUIP_TYPE_ID=equip.EQUIP_TYPE_ID " +
					" and BATCH_IMPORT.import_id="+pageMap.get("import_id")[0]+"";
			map=base.getRowsWithPaging(sql,pageUtil.getPage()-1,pageUtil.getLines());
			return SUCCESS;

		}
		
		public String deleteHistory() throws Exception
		{

			BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);

			String  sql = "update equip,ref_batch_import_equip set equip.EQUIP_STATUS='"+ flag
			        + "' where ref_batch_import_equip.equip_id=equip.equip_id and ref_batch_import_equip.import_id="+import_id+"";
			base.addSql4Execute(sql);
			// 关联表，购置录入单部门信息，未逻辑删除。
			sql="update EQUIP_PART,EQUIP set EQUIP_PART.EQUIP_STATUS='"+flag+"' where EQUIP_PART.EQUIP_ID=equip.EQUIP_ID";

			base.execute();

			return SUCCESS;
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
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
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
	public String getOrgan_id() {
		return organ_id;
	}
	public void setOrgan_id(String organ_id) {
		this.organ_id = organ_id;
	}

	public String getImport_id() {
		return import_id;
	}

	public void setImport_id(String import_id) {
		this.import_id = import_id;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}
	
}
