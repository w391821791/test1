package com.zty.edu_equip.util;

import java.applet.Applet;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.po.base.Util;
import com.po.serverbase.BaseDao;
import com.po.serverbase.SysConfig;
import com.po.serverbase.SystemInfo;
import com.zty.edu_equip.action.UtilAction;
import com.zty.edu_equip.entity.Organ;

public class Common
{
	private static int fileNameNumber = 0;
	public static boolean wboo = false;
	public static int equipTypeCount = 0;

	public static int getFileNameNumber()
	{
		if (fileNameNumber > 100)
			fileNameNumber = 0;
		return ++fileNameNumber;
	}

	
	public static String getOrganName(String organ_id) throws Exception
	{
		BaseDao basedao = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql = "select organ_name from organ where organ_id=" + organ_id;
		List list = basedao.query(sql);
		HashMap map = (HashMap) list.get(0);
		if (null != map.get("ORGAN_NAME"))
		{
			return map.get("ORGAN_NAME").toString();
		}
		else
		{
			return null;
		}
	}
	/**
	 * 从数据库里面加载equipType 数据 并进行组装
	 * 
	 * @throws Exception
	 */
	
	public static List getEquipTypeRoot() throws Exception
	{
		String sql = "select * from EQUIP_TYPE_GROUP where parent_group_id is null";
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		List equipGroup = baseDao.query(sql);
		List<Map> listmap = new ArrayList();
		List<Map> equipTypeTree = new ArrayList();
		Map datamap = new HashMap();
		for (int i = 0; i < equipGroup.size(); i++)
		{
			HashMap map = (HashMap) equipGroup.get(i);
			HashMap itemMap = new HashMap();
			itemMap.put("id", map.get("GROUP_ID"));
			itemMap.put("text", map.get("GROUP_NAME"));
			itemMap.put("parentid", map.get("PARENT_GROUP_ID"));
			itemMap.put("children", null);
			itemMap.put("state", "closed");
			equipTypeTree.add(itemMap);
		}
		return equipTypeTree;
	}
	public static List getEquipTypeByGroup(String group_id) throws Exception
	{
		BaseDao baseDao=new BaseDao(SystemInfo.getMainJndi(), null);
		List equipType=new ArrayList();
		String sql="select * from equip_type_group where parent_group_id="+group_id;
		List list=baseDao.query(sql);
		for(int i=0;i<list.size() && null!=list;i++){
			HashMap row = (HashMap) list.get(i);
			HashMap itemMap = new HashMap();
			itemMap.put("id", row.get("GROUP_ID"));
			itemMap.put("text", row.get("GROUP_NAME"));
			itemMap.put("parentid",group_id);
			itemMap.put("children",null);
			itemMap.put("state", "closed");
			equipType.add(itemMap);
		}
		sql="select * from equip_type where group_id="+group_id;
		list=baseDao.query(sql);
		for(int i=0;i<list.size() && null!=list;i++){
				HashMap row = (HashMap) list.get(i);
				HashMap itemMap = new HashMap();
				itemMap.put("id", row.get("EQUIP_TYPE_ID"));
				itemMap.put("text", row.get("ASSET_TYPE_NAME"));
				itemMap.put("parentid", row.get("GROUP_ID"));
				equipType.add(itemMap);
		}
		return equipType;
	}

	/**
	 * 从数据库里面加载roomType 数据 并进行组装
	 * true 加载所有数据   false 只记载拥有房间的房间类别
	 * @throws Exception
	 */
	public static List getRoomByTypeTree(String organ_id,boolean boo) throws Exception
	{
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql = "select room_type.room_type_id,room_type.room_type_name,organ.scal_id from room_type,organ,room_stand  where room_type.room_type_id =room_stand.room_type_id "
		        + " and organ.scal_id=room_stand.scal_id"
		        + " and organ_id="
		        + organ_id
		        + " order by room_type.REINDEX, convert(room_type.room_type_name using gbk) asc";
		List roomType = baseDao.query(sql);
		HashMap one =(HashMap) roomType.get(0);
		String scal =one.get("SCAL_ID")+"";
		HashMap map0 = new HashMap();
		map0.put("ROOM_TYPE_ID", 82);
		map0.put("ROOM_TYPE_NAME", "音乐教学仪器");
		roomType.add(map0);

		HashMap map1 = new HashMap();
		map1.put("ROOM_TYPE_ID", 71);
		map1.put("ROOM_TYPE_NAME", "美术教学仪器");
		roomType.add(map1);

		HashMap map2 = new HashMap();
		map2.put("ROOM_TYPE_ID", 4);
		map2.put("ROOM_TYPE_NAME", "体育教学仪器");
		roomType.add(map2);

		HashMap map3 = new HashMap();
		map3.put("ROOM_TYPE_ID", 33);
		map3.put("ROOM_TYPE_NAME", "数学教学仪器");
		roomType.add(map3);
		
		String r_kexue ="";
		for(int i=0;i<roomType.size();i++){
			HashMap row =(HashMap) roomType.get(i);
			r_kexue=r_kexue+","+row.get("ROOM_TYPE_ID");
		}
		if(!r_kexue.contains("60")&&(scal.equals("1")||scal.equals("2")||scal.equals("3"))){
			HashMap map4 = new HashMap();
			map4.put("ROOM_TYPE_ID", 60);
			map4.put("ROOM_TYPE_NAME", "科学仪器室（含准备室丶实验员室）");
			roomType.add(map4);
		}
		

		List roomTypeTree = new ArrayList();
		sql = "select r.ROOM_ID,r.room_name,refRt.ROOM_TYPE_ID from room r"
		        + " left join  ref_room_type refRt on refRt.ROOM_ID=r.ROOM_ID where  r.organ_id="
		        + organ_id;
		List roomList = baseDao.query(sql);
		HashMap rtMap = new HashMap();
		// 组装数据
		for (int i = 0; i < roomList.size(); i++)
		{
			HashMap hmap = (HashMap) roomList.get(i);
			HashMap itemMap = new HashMap();
			itemMap.put("id", hmap.get("ROOM_ID"));
			itemMap.put("text", hmap.get("ROOM_NAME"));
			itemMap.put("parentid", hmap.get("ROOM_TYPE_ID"));
			if (null == rtMap.get(hmap.get("ROOM_TYPE_ID")))
			{
				List list = new ArrayList();
				list.add(itemMap);
				rtMap.put(hmap.get("ROOM_TYPE_ID"), list);
				// etMap.put(hmap.get("GROUP_ID"),new ArrayList().add(itemMap));
			}
			else
			{
				((List) rtMap.get(hmap.get("ROOM_TYPE_ID"))).add(itemMap);
			}
		}
		// 把没有数据的房间添加到树下面
		for (int i = 0; i < roomType.size(); i++)
		{
			HashMap map = (HashMap) roomType.get(i);
			HashMap itemMap = new HashMap();
			itemMap.put("id", map.get("ROOM_TYPE_ID"));
			int count = 0;
			if (null != rtMap.get(map.get("ROOM_TYPE_ID")))
			{
				itemMap.put("children", rtMap.get(map.get("ROOM_TYPE_ID")));
				List list = (List) rtMap.get(map.get("ROOM_TYPE_ID"));
				count = list.size();
			}
			String textHtml = "";
			if (0 == count)
			{
				textHtml = "<span style='color:red'>(" + count + ")</span>";
			}
			else
			{
				textHtml = "<span style='color:black'>(" + count + ")</span>";
			}
			itemMap.put("text", map.get("ROOM_TYPE_NAME") + textHtml);
			itemMap.put("state", "closed");
			if(boo){
				roomTypeTree.add(itemMap);
			}else{
				 if(count!=0)roomTypeTree.add(itemMap);
			}
		}
		// 添加没有类别的数据到根目录
		for (int i = 0; i < roomList.size(); i++)
		{
			HashMap hmap = (HashMap) roomList.get(i);
			if (null == hmap.get("ROOM_TYPE_ID"))
			{
				HashMap itemMap = new HashMap();
				itemMap.put("id", hmap.get("ROOM_ID"));
				itemMap.put("text", hmap.get("ROOM_NAME"));
				roomTypeTree.add(itemMap);
			}
		}
		return roomTypeTree;
	}
	public static List getUserTree() throws Exception {
		String organ_id = UtilAction.getUser().getOrganId() + "";
		String sql = "select * from user_group where organ_id=" + organ_id;
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		List list = baseDao.query(sql);
		sql = "select user_id,user_name,user_group_id from user_info where organ_id="+ organ_id;
		List userlist = baseDao.query(sql);
		List userTree = new ArrayList();
		HashMap row = null;
		HashMap userMap = new HashMap();
		for (int j = 0; j < userlist.size(); j++) {
			row = (HashMap) userlist.get(j);
			HashMap itemMap = new HashMap();
			itemMap.put("id", row.get("USER_ID"));
			itemMap.put("text", row.get("USER_NAME"));
			itemMap.put("parentid", row.get("USER_GROUP_ID"));
			if (null == userMap.get(row.get("USER_GROUP_ID"))) {
				List children = new ArrayList();
				children.add(itemMap);
				userMap.put(row.get("USER_GROUP_ID"), children);
			} else {
				((List) userMap.get(row.get("USER_GROUP_ID"))).add(itemMap);
			}
		}
		for (int i = 0; null != list && i < list.size(); i++) {
			row = (HashMap) list.get(i);
			HashMap itemMap = new HashMap();
			itemMap.put("id", row.get("USER_GROUP_ID"));
			itemMap.put("text", row.get("GROUP_NAME"));
			itemMap.put("state", "closed");
			int count=0;
			if (null != userMap.get(row.get("USER_GROUP_ID"))) {
				List children = (List) userMap.get(row.get("USER_GROUP_ID"));
				itemMap.put("children", children);
				count = children.size();
			}
			if(count!=0)userTree.add(itemMap);
		}
		// 添加没有类别的数据到根目录
		for (int i = 0; i < userlist.size(); i++) {
			row = (HashMap) userlist.get(i);
			if (null == row.get("USER_GROUP_ID")) {
				HashMap itemMap = new HashMap();
				itemMap.put("id", row.get("USER_ID"));
				itemMap.put("text", row.get("USER_NAME"));
				userTree.add(itemMap);
			}
		}
		return userTree;
	}
   

	/**
	 * 取出EXCEL中指定的列数据
	 * 
	 * 
	 * 
	 * 
	 * @param file
	 *            EXCEL文件
	 * @param firstRowNo
	 *            首行数据所在行（EXCEL中首行数据行号为0）
	 * 
	 * 
	 * 
	 * @param colTitles
	 *            待取出的列的名称
	 * @param colValues
	 *            待取出的列所在EXCLE中的列号（首列列号为0）
	 * @param sheetnumber
	 *            取第几个sheet
	 * 
	 * 
	 * @return 由String[]组成的List，String[]长度为colValues+1，最末数据为该数据所在EXCEL行号（首行为1）
	 * 
	 * 
	 * 
	 * @throws Exception
	 */
	public static List getDataFromExcel(File file, String firstRowNo,
	        String[] colValues, int sheetnumber) throws Exception
	{
		List datas = new ArrayList();
		Workbook book = Workbook.getWorkbook(file);
		try
		{
			Sheet sheet = book.getSheet(sheetnumber);
			// 转换为整数

			int firstRowNoInt = Integer.parseInt(firstRowNo);
			// 取数据
			for (int i = firstRowNoInt; i < sheet.getRows(); i++)
			{

				String[] colValue = new String[colValues.length];

				for (int j = 0; j < colValues.length; j++)
				{

					colValue[j] = Util.nullToStr(sheet.getCell(
					        Integer.parseInt(colValues[j]), i).getContents());
					// System.out.println("     col="+j+"("+colValuesInt[j]+")="+colValue[j]);

				}
				datas.add(colValue);
			}

		}

		catch (Exception e)
		{

			return null;
		}
		finally
		{
			book.close();
		}
		return datas;
	}
	 /** 
     * 读取Office 2007 excel 
     * */  
	public static List getDataFromExcel2007(File file, String firstRowNo,
	        String[] colValues, int sheetnumber) throws Exception
	{
		List datas = new ArrayList();

		XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
		try
		{
			
			XSSFSheet sheet = xwb.getSheetAt(sheetnumber);
			// 转换为整数

			int firstRowNoInt = Integer.parseInt(firstRowNo);
			// 取数据
			//System.out.println("第"+sheetnumber+"个sheet-----------------");
			for (int i = firstRowNoInt; i < sheet.getPhysicalNumberOfRows(); i++)
			{

				
				String[] colValue = new String[colValues.length];

				for (int j = 0; j < colValues.length; j++)
				{
					//System.out.println("第"+i+"行-----------------    第"+j+"");
					if((null!=sheet.getRow(i).getCell(Integer.parseInt(colValues[j])))){
					 if((sheet.getRow(i).getCell(Integer.parseInt(colValues[j])).getCellType()==0)){

					        if (HSSFDateUtil.isCellDateFormatted(sheet.getRow(i).getCell(Integer.parseInt(colValues[j]))))
					         {
					            
					            Date date = sheet.getRow(i).getCell(Integer.parseInt(colValues[j])).getDateCellValue();
								SimpleDateFormat sdf = new SimpleDateFormat(
								        "yyyy-MM-dd");
								 colValue[j] = sdf.format(date);
					            //日期
		//System.out.println("date=="+date);
					         }
					        else{colValue[j]=sheet.getRow(i).getCell(Integer.parseInt(colValues[j])).getNumericCellValue()+"";}
					}else{
					colValue[j] = Util.nullToStr(sheet.getRow(i).getCell(Integer.parseInt(colValues[j])).toString().trim());
					}
					// System.out.println("     col="+j+"("+colValuesInt[j]+")="+colValue[j]);

				}else{
					colValue[j]=null;
				}
					}
				datas.add(colValue);
			}

		}

		catch (Exception e)
		{

			e.printStackTrace();
			throw e;
		}

		return datas;
	}

	// 获取房间类别
	public static HashMap getExcelRoomType() throws Exception
	{
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		HashMap map = new HashMap();
		String sql = "select * from room_type";
		List list = base.query(sql);
		for (int i = 0; i < list.size(); i++)
		{
			HashMap row = (HashMap) list.get(i);
			String name = row.get("ROOM_TYPE_NAME") + "";
			BigDecimal id = (BigDecimal) row.get("ROOM_TYPE_ID");
			map.put(name, id);
		}

		return map;
	}
	public static String getOrganScalId(String organId) throws Exception{
		BaseDao baseDao=new BaseDao(SystemInfo.getMainJndi(), null);
		Organ organ=(Organ) baseDao.getObjFromDbById(Organ.class, organId);
		if(null!=organ.getScalId()){
			return organ.getScalId()+""; 
		}
		return null;
	}
	// 获取房间类别
	public static List getRoomType() throws Exception
	{
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		String organ_id = UtilAction.getUser().getOrganId() + "";
		Integer scal=Integer.parseInt(getOrganScalId(organ_id));
		StringBuffer sql=new StringBuffer();
		sql.append("select room_type.room_type_id,room_type.room_type_name,organ.scal_id from room_type,organ,room_stand  "
					+ " where room_type.room_type_id =room_stand.room_type_id "
			        + " and organ.scal_id=room_stand.scal_id");
		if(null!=scal){
			sql.append(" and room_stand.scal_id="+scal);
		}
		sql.append( " and organ_id="+ organ_id+ " group by room_type.room_type_name "
					+ " order by ROOM_TYPE.REINDEX, convert(room_type.room_type_name using gbk) asc");
		List list = base.query(sql.toString());
		List roomTypeTree=new ArrayList();
		HashMap row=null;
		HashMap itemMap=null;
		boolean boo=false;
		for (int i = 0; i < list.size(); i++) {
			row = (HashMap) list.get(i);
			itemMap = new HashMap();
			if("60".equals(row.get("ROOM_TYPE_ID").toString()))boo=true;
			itemMap.put("id", row.get("ROOM_TYPE_ID"));
			itemMap.put("text", row.get("ROOM_TYPE_NAME"));
			roomTypeTree.add(itemMap);
		}
		itemMap = new HashMap();
		itemMap.put("id", 82);
		itemMap.put("text", "音乐教学仪器");
		roomTypeTree.add(itemMap);
		itemMap = new HashMap();
		itemMap.put("id", 71);
		itemMap.put("text", "美术教学仪器");
		roomTypeTree.add(itemMap);
		itemMap = new HashMap();
		itemMap.put("id", 4);
		itemMap.put("text", "体育教学仪器");
		roomTypeTree.add(itemMap);
		itemMap = new HashMap();
		itemMap.put("id", 33);
		itemMap.put("text", "数学教学仪器");
		roomTypeTree.add(itemMap);
		if(!boo && scal<=3){
			itemMap = new HashMap();
			itemMap.put("id", 60);
			itemMap.put("text", "科学仪器室（含准备室丶实验员室）");
			roomTypeTree.add(itemMap);
		}
		return roomTypeTree;
	}

	// 获取学校下所有房间
	public static List getRoomName() throws Exception
	{
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		String organ_id = UtilAction.getUser().getOrganId() + "";
		HashMap map = new HashMap();
		String sql = "select * from room where organ_id=" + organ_id
		        + " order by   room.room_name asc";
		return base.query(sql);
	}

	// 获取学校下所有装备类别房间
	public static List getEquipRoomName() throws Exception
	{
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		String organ_id = UtilAction.getUser().getOrganId() + "";
		HashMap map = new HashMap();
		String sql = "select room.* from REF_ROOM_TYPE,room,ROOM_STAND_EQUIP,EQUIP_TYPE where room.organ_id="
		        + organ_id
		        + " and REF_ROOM_TYPE.room_id =room.room_id"
		        + " and REF_ROOM_TYPE.room_type_id = ROOM_STAND_EQUIP.ROOM_TYPE_id"
		        + " and ROOM_STAND_EQUIP.EQUIP_TYPE_ID=EQUIP_TYPE.equip_type_id and EQUIP_TYPE.group_id in (4,5,6,7,8) "
		        + " group by room.room_id order by   room.REINDEX, convert(room.room_name using gbk) asc";
		return base.query(sql);
	}

	// 获取学校下所有仪器房间
	public static List getInstrumentRoomName() throws Exception
	{
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		String organ_id = UtilAction.getUser().getOrganId() + "";
		HashMap map = new HashMap();
		String sql = "select room.* from REF_ROOM_TYPE, room,ROOM_STAND_EQUIP,EQUIP_TYPE where room.organ_id="
		        + organ_id
		        + " and REF_ROOM_TYPE.room_id =room.room_id"
		        + " and REF_ROOM_TYPE.room_type_id = ROOM_STAND_EQUIP.ROOM_TYPE_id"
		        + " and ROOM_STAND_EQUIP.EQUIP_TYPE_ID=EQUIP_TYPE.equip_type_id and EQUIP_TYPE.group_id in ("
		        + getChildGroup(2)
		        + ")"
		        + " group by room.room_id order by  room.REINDEX, convert(room.room_name using gbk) asc";
		return base.query(sql);
	}

	// 获取装备类别类别
	public static List getEquipTypeforJS() throws Exception
	{
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		HashMap map = new HashMap();
		String sql = "select * from equip_type";
		return base.query(sql);
	}

	// 获取装备类别
	public static HashMap getEquipType(int group_id) throws Exception
	{
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		HashMap map = new HashMap();
		String sql = "select * from EQUIP_TYPE where group_id in ("
		        + getChildGroup(group_id) + ")";
		List list = base.query(sql);
		for (int i = 0; i < list.size(); i++)
		{
			HashMap row = (HashMap) list.get(i);
			String name = row.get("ASSET_TYPE_NAME") + "";
			BigDecimal id = (BigDecimal) row.get("EQUIP_TYPE_ID");
			map.put(name, id);
		}

		return map;
	}

	// 日期格式化
	public static String Dateformat(String dateformat)
	{
		if ("".equals(dateformat) || null == dateformat)
			return null;
		else
		{
			if (dateformat.contains("."))
			{
				dateformat = dateformat.replace(".", "-");
			}
			if (dateformat.contains("/"))
			{
				dateformat = dateformat.replaceAll("/", "-");
			}

			String date = dateformat;
			String s1 = null, s2 = null, s0 = null;
			if ((date.length() == 8 || date.length() == 6)
			        && (date.indexOf("-") < 0))
			{
				System.out.println(date);
				s0 = date.substring(0, 4);
				s1 = date.substring(4, 6);
				if (date.length() == 8)
				{
					s2 = date.substring(6, 8);
				}
				else
				{
					s2 = "01";
				}
			}
			else
			{
				String[] sss = date.split("-");
				s0 = sss[0];
				if (sss.length == 1)
				{
					s1 = "01";
					s2 = "01";
				}
				if (sss.length == 2)
				{
					s1 = sss[1];
					s2 = "01";
				}
				if (sss.length == 3)
				{
					s1 = sss[1];
					s2 = sss[2];
				}
				if (s1.length() <= 1)
				{
					s1 = "0" + s1;
				}
				if (s2.length() <= 1)
				{
					s2 = "0" + s2;
				}
			}
			String date1 = s0 + "-" + s1 + "-" + s2;
			return date1;
		}
	}

	// 判断是否是数字
	public static boolean isNumeric(String str)
	{
		for (int i = 0; i < str.length(); i++)
		{
			System.out.println(str.charAt(i));
			if (!Character.isDigit(str.charAt(i)))
			{
				return false;
			}
		}
		return true;
	}

	// 根据ORGAN_NAME 获取 机构
	public static Organ getOrganByName(String organ_name) throws Exception
	{
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		HashMap map = new HashMap();
		String sql = "select * from ORGAN where organ_name ='" + organ_name
		        + "'";
		List list = base.query(sql);
		if (list.size() < 1 || null == list)
			return null;
		HashMap row = (HashMap) list.get(0);
		Organ organ = new Organ(row.get("ORGAN_ID").toString());
		return organ;
	}

	public static boolean selectOrganRoom(String room_name, String organ_id)
	        throws Exception
	{
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		HashMap map = new HashMap();
		String sql = "select * from room where room_name='" + room_name
		        + "' and organ_id=" + organ_id + "";
		List list = base.query(sql);
		if (list.size() < 1 || null == list)
			return false;
		else
			return true;
	}

	//
	public static List getOrganTree() throws Exception
	{

		String sql = "select ORGAN_ID,PARENT_ID,ORGAN_NAME from ORGAN where  "
		        + UtilAction.getChildOrganId();
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		List<Map> equipTypeTree = new ArrayList();
		try
		{
			// put data to map
			List list = base.query(sql);
			List<Map> listmap = new ArrayList();
			Map datamap = new HashMap();
			//
			for (int i = 0; i < list.size(); i++)
			{
				HashMap map = (HashMap) list.get(i);
				HashMap itemMap = new HashMap();
				itemMap.put("id", map.get("ORGAN_ID"));
				itemMap.put("text", map.get("ORGAN_NAME"));
				itemMap.put("parentid", map.get("PARENT_ID"));
				itemMap.put("children", new ArrayList());
				datamap.put(map.get("ORGAN_ID"), itemMap);
				listmap.add(itemMap);
				if (map.get("PARENT_ID") == null)
				{
					equipTypeTree.add(itemMap);
				}
			}
			// generate children
			//
			for (int i = 0; i < listmap.size(); i++)
			{
				HashMap itemMap = (HashMap) listmap.get(i);
				HashMap parentItemMap = (HashMap) datamap.get(itemMap
				        .get("parentid"));
				if (parentItemMap != null)
				{
					List children = (List) parentItemMap.get("children");
					children.add(itemMap);
					parentItemMap.put("children", children);
					parentItemMap.put("state", "closed");
				}

			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return equipTypeTree;

	}

	private static List getChildItems(HashMap parentItemMap, int level)
	        throws Exception
	{
		List childs = new ArrayList();
		List children = (List) parentItemMap.get("CHILDREN");
		for (int i = 0; children != null && i < children.size(); i++)
		{
			HashMap itemMap = (HashMap) children.get(i);
			itemMap.put("LEVEL", Integer.toString(level));
			childs.add(itemMap);
			List childItemMaps = getChildItems(itemMap, level + 1);
			childs.addAll(childItemMaps);
		}
		return childs;
	}

	public static List sortChildItems(String organID) throws Exception
	{
		List datas = new ArrayList();
		List topDatas = (List) getChildRefData(organID)[0];
		for (int i = 0; topDatas != null && i < topDatas.size(); i++)
		{
			HashMap itemMap = (HashMap) topDatas.get(i);
			itemMap.put("LEVEL", Integer.toString(0));
			datas.add(itemMap);
			List childItemMaps = getChildItems(itemMap, 1);
			datas.addAll(childItemMaps);
		}
		return datas;
	}

	public static Object[] getChildRefData(String organID) throws Exception
	{
		System.out.println("organID=" + organID + "getChildRefData");
		List topDatas = new ArrayList();
		HashMap dataMap = new HashMap();
		List datas = new ArrayList();
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql = "SELECT ORGAN_ID,PARENT_ID,ORGAN_NAME FROM ORGAN";
		List rows = base.query(sql);
		for (int i = 0; rows != null && i < rows.size(); i++)
		{
			HashMap row = (HashMap) rows.get(i);
			String id = ((BigDecimal) row.get("ORGAN_ID")).toString();
			BigDecimal parentid = (BigDecimal) row.get("PARENT_ID");
			HashMap itemMap = new HashMap();
			itemMap.put("ORGAN_ID", id);
			if (parentid != null)
				itemMap.put("PARENT_ID", parentid.toString());
			itemMap.put("ORGAN_NAME", (String) row.get("ORGAN_NAME"));

			datas.add(itemMap);
			dataMap.put(id, itemMap);
			if (id.equals(organID))
				topDatas.add(itemMap);
		}
		for (int i = 0; datas != null && i < datas.size(); i++)
		{
			HashMap itemMap = (HashMap) datas.get(i);
			String parentId = (String) itemMap.get("PARENT_ID");
			if (parentId != null && !"".equals(parentId))
			{
				HashMap parentItemMap = (HashMap) dataMap.get(parentId);
				if (parentItemMap != null)
				{
					List children = (List) parentItemMap.get("CHILDREN");
					if (children == null)
						children = new ArrayList();
					children.add(itemMap);
					parentItemMap.put("CHILDREN", children);
				}
			}
		}
		Object[] objs = new Object[] { topDatas, dataMap, datas };
		return objs;
	}

	// 使用 函数 find——in——set 和 自创函数 getChildLst 获取机构下面的所有叶子节点

	public static List getSummaryExcelChild(String organID, String standard_id)
	        throws Exception
	{
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql = "SELECT ORGAN_ID,PARENT_ID,ORGAN_NAME,STANDARD_ID,SCOPE_ID,STUDENT_COUNT FROM ORGAN "
		        + "WHERE STANDARD_ID="
		        + standard_id
		        + " AND SCOPE_ID IS NOT NULL and find_in_set(organ_id,getChildLst("
		        + organID + "))";
		List rows = base.query(sql);
		return rows;
	}

	public static String ExportExcel(String[][] data, String name)
	{
		HttpServletResponse response = null;
		OutputStream out = null;
		try
		{
			response = ServletActionContext.getResponse();
			response.setDateHeader("Expires", 0);
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachment"
			        + "; filename=\""
			        + toUtf8String("教育装备云管理-" + name + ".xls") + "\"");
			out = response.getOutputStream();
			File file = new File(createExcel(name, data));
			InputStream in = new FileInputStream(file);
			byte[] readed = new byte[1024];
			int len = in.read(readed);
			while (len > 0)
			{
				out.write(readed, 0, len);
				len = in.read(readed);
			}
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

	public static String createExcel(String as_Title, String[][] a_Table)
	        throws Exception
	{
		File filePath = new File(SysConfig.getAttrValue("FileServerPath")
		        + File.separator + "Excel");
		if (filePath == null || !filePath.exists())
		{
			filePath.mkdir();
		}
		// HttpServletRequest r = ServletActionContext.getRequest();
		String basePath = SysConfig.getAttrValue("FileServerPath")
		        + File.separator;
		int number = Common.getFileNameNumber();
		delAllFile(SysConfig.getAttrValue("ExcelPath") + "\\Excel"); // 删除完里面所有内容

		String ls_FileName = basePath + "Excel" + File.separator + number
		        + ".xls";
		WritableWorkbook book = null;
		try
		{
			File l_File = new File(ls_FileName);
			if (l_File.exists())
				l_File.delete();
			WorkbookSettings workbookSettings = new WorkbookSettings();
			workbookSettings.setEncoding("GBK");
			book = Workbook.createWorkbook(new File(ls_FileName),
			        workbookSettings);
			WritableSheet sheet = book.createSheet("Sheet1", 0);
			int li_RowCount = a_Table.length;
			int li_ColCount = a_Table[0].length;
			Label label = new Label(0, 0, Util.nullToStr(as_Title));
			sheet.addCell(label);
			for (int i = 0; i < li_RowCount; i++)
			{
				for (int j = 0; j < li_ColCount; j++)
				{
					// System.out.println(i+":"+j+"="+a_Table[i][j]);
					if (a_Table[i][j] instanceof String)
					{
						if (Util.isFloat((String) a_Table[i][j]))
						{
							jxl.write.Number labelN = new jxl.write.Number(j,
							        i + 1,
							        Util.parseFloat((String) a_Table[i][j]));
							sheet.addCell(labelN);
						}
						else
						{
							label = new Label(j, i + 1,
							        Util.nullToStr(a_Table[i][j]));
							sheet.addCell(label);
						}
					}
					else
					{
						// null:Lable,Number,Boolean,DateTime
						sheet.addCell(new Label(j, i + 1, ""));
					}
				}
			}
			book.write();
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			if (book != null)
				book.close();
		}
		return ls_FileName;
	}

	public static boolean delAllFile(String path)
	{
		boolean flag = false;
		File file = new File(path);
		if (!file.exists())
		{
			return flag;
		}
		if (!file.isDirectory())
		{
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++)
		{
			if (path.endsWith(File.separator))
			{
				temp = new File(path + tempList[i]);
			}
			else
			{
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile())
			{
				temp.delete();
			}
			if (temp.isDirectory())
			{
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件

				flag = true;
			}
		}
		return flag;
	}

	public static String toUtf8String(String s)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			if (c >= 0 && c <= 255)
			{
				sb.append(c);
			}
			else
			{
				byte[] b;
				try
				{
					b = Character.toString(c).getBytes("utf-8");
				}
				catch (Exception ex)
				{
					System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++)
				{
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	public static List getRoomTree() throws Exception
	{
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		String organ_id = UtilAction.getUser().getOrganId() + "";
		String sql = "select room.* FROM room  where organ_id='"
		        + organ_id
		        + "' order by  room.REINDEX, convert(room.room_name using gbk) asc";
		List list = baseDao.query(sql);
		List<Map> equipTypeTree = new ArrayList();
		try
		{
			// put data to map

			List<Map> listmap = new ArrayList();
			Map datamap = new HashMap();
			// 创建页面需要的属性格式

			for (int i = 0; i < list.size(); i++)
			{
				HashMap map = (HashMap) list.get(i);
				HashMap itemMap = new HashMap();
				itemMap.put("id", map.get("ROOM_ID"));
				itemMap.put("text", map.get("ROOM_NAME"));
				itemMap.put("children", new ArrayList());
				datamap.put(map.get("ROOM_ID"), itemMap);
				listmap.add(itemMap);
				if (map.get("PARENT_ID") == null)
				{
					equipTypeTree.add(itemMap);
				}
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return equipTypeTree;

	}

	public static List ConvertEmpty(List list)
	{
		List list2 = new ArrayList();
		for (int i = 0; null != list && i < list.size(); i++)
		{
			HashMap hmap = (HashMap) list.get(i);
			HashMap map = new HashMap();
			Iterator iterator = hmap.keySet().iterator();
			while (iterator.hasNext())
			{
				String key = (String) iterator.next();
				if (null == hmap.get(key))
				{
					map.put(key, "");
				}
				else
				{
					map.put(key, hmap.get(key));
				}
			}
			list2.add(map);
		}
		return list2;
	}

	// 循环查询子groupId 与下面的方法一起使用三
	public static String getChildGroup(int parentId) throws Exception
	{
		HashMap dataMap = (HashMap) getChildRefData()[1];
		HashMap parentDataMap = (HashMap) dataMap.get(Integer
		        .toString(parentId));
		List childrenDatas = getChildItems(parentDataMap, 1);
		String ids = (String) parentDataMap.get("GROUP_ID");
		for (int i = 0; i < childrenDatas.size(); i++)
		{
			HashMap childItemMap = (HashMap) childrenDatas.get(i);
			ids += "," + (String) childItemMap.get("GROUP_ID");
		}
		return ids;
	}

	public static Object[] getChildRefData() throws Exception
	{
		List topDatas = new ArrayList();
		HashMap dataMap = new HashMap();
		List datas = new ArrayList();
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql = "SELECT GROUP_ID,PARENT_GROUP_ID,GROUP_NAME FROM EQUIP_TYPE_GROUP ORDER by GROUP_NAME";
		List rows = base.query(sql);
		for (int i = 0; rows != null && i < rows.size(); i++)
		{
			HashMap row = (HashMap) rows.get(i);
			String id = ((BigDecimal) row.get("GROUP_ID")).toString();
			BigDecimal parentid = (BigDecimal) row.get("PARENT_GROUP_ID");
			HashMap itemMap = new HashMap();
			itemMap.put("GROUP_ID", id);
			if (parentid != null)
				itemMap.put("PARENT_GROUP_ID", parentid.toString());
			itemMap.put("GROUP_NAME", (String) row.get("GROUP_NAME"));

			datas.add(itemMap);
			dataMap.put(id, itemMap);
		}
		for (int i = 0; datas != null && i < datas.size(); i++)
		{
			HashMap itemMap = (HashMap) datas.get(i);
			String parentId = (String) itemMap.get("PARENT_GROUP_ID");
			if (parentId == null || "".equals(parentId))
				topDatas.add(itemMap);
			else
			{
				HashMap parentItemMap = (HashMap) dataMap.get(parentId);
				if (parentItemMap != null)
				{
					List children = (List) parentItemMap.get("CHILDREN");
					if (children == null)
						children = new ArrayList();
					children.add(itemMap);
					parentItemMap.put("CHILDREN", children);
				}
			}
		}
		Object[] objs = new Object[] { topDatas, dataMap, datas };
		return objs;
	}

	// 循环查询子organId 与下面的方法一起使用四
	public static String getChildOrgan(int parentId) throws Exception
	{
		System.out.println("进入getChildOrgan");
		System.out.println("parentId=" + parentId);
		HashMap dataMap = (HashMap) getChildOrganData()[1];
		HashMap parentDataMap = (HashMap) dataMap.get(Integer
		        .toString(parentId));
		List childrenDatas = getChildItems(parentDataMap, 1);
		String ids = (String) parentDataMap.get("ORGAN_ID");
		for (int i = 0; i < childrenDatas.size(); i++)
		{
			HashMap childItemMap = (HashMap) childrenDatas.get(i);
			ids += "," + (String) childItemMap.get("ORGAN_ID");
		}
		return ids;
	}

	public static Object[] getChildOrganData() throws Exception
	{
		List topDatas = new ArrayList();
		HashMap dataMap = new HashMap();
		List datas = new ArrayList();
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql = "SELECT * FROM ORGAN";
		List rows = base.query(sql);
		for (int i = 0; rows != null && i < rows.size(); i++)
		{
			HashMap row = (HashMap) rows.get(i);
			String id = ((BigDecimal) row.get("ORGAN_ID")).toString();
			BigDecimal parentid = (BigDecimal) row.get("PARENT_ID");
			HashMap itemMap = new HashMap();
			itemMap.put("ORGAN_ID", id);
			if (parentid != null)
				itemMap.put("PARENT_ID", parentid.toString());
			itemMap.put("ORGAN_NAME", (String) row.get("ORGAN_NAME"));

			datas.add(itemMap);
			dataMap.put(id, itemMap);
		}
		for (int i = 0; datas != null && i < datas.size(); i++)
		{
			HashMap itemMap = (HashMap) datas.get(i);
			String parentId = (String) itemMap.get("PARENT_ID");
			if (parentId == null || "".equals(parentId))
				topDatas.add(itemMap);
			else
			{
				HashMap parentItemMap = (HashMap) dataMap.get(parentId);
				if (parentItemMap != null)
				{
					List children = (List) parentItemMap.get("CHILDREN");
					if (children == null)
						children = new ArrayList();
					children.add(itemMap);
					parentItemMap.put("CHILDREN", children);
				}
			}
		}
		Object[] objs = new Object[] { topDatas, dataMap, datas };
		return objs;
	}

	public static String getRoomNameByRoomId(BigDecimal roomId)
	        throws Exception
	{
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql = "select * from room where room_id=" + roomId + "";
		List list = baseDao.query(sql);
		HashMap map = (HashMap) list.get(0);
		return map.get("ROOM_NAME").toString();
	}

	public static String getEquipTypeNameById(BigDecimal equipTypeId)
	        throws Exception
	{
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql = "select * from equip_type where equip_Type_Id="
		        + equipTypeId + "";
		List list = baseDao.query(sql);
		if(null!=list&&list.size()>0){
		HashMap map = (HashMap) list.get(0);
		return map.get("ASSET_TYPE_NAME").toString();
		}
		return null;
	}

	public static List sortChildItems() throws Exception
	{
		List datas = new ArrayList();
		List topDatas = (List) getChildRefData()[0];
		for (int i = 0; topDatas != null && i < topDatas.size(); i++)
		{
			HashMap itemMap = (HashMap) topDatas.get(i);
			itemMap.put("LEVEL", Integer.toString(0));
			datas.add(itemMap);
			List childItemMaps = getChildItems(itemMap, 1);
			datas.addAll(childItemMaps);
		}
		return datas;
	}

	public static String getRoomIdByRoom(String room_name) throws Exception
	{
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql = "select * from room where room_name='" + room_name + "'";
		List list = baseDao.query(sql);
		if (list.size() < 1 || null == list)
		{
			return null;
		}
		HashMap map = (HashMap) list.get(0);
		return map.get("ROOM_ID").toString();
	}

	public static List getInstrumentRoomTypeName(String organ_id) throws Exception
	{
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		if(null==organ_id || "".equals(organ_id))organ_id=UtilAction.getUser().getOrganId() + "";
		HashMap map = new HashMap();
		String sql = "select room_type.room_type_id,room_type.room_type_name from organ,ORGAN_SCAL, room_type,ROOM_STAND_EQUIP,EQUIP_TYPE "
		        + " where organ.organ_id="
		        + organ_id
		        + " and organ.SCAL_ID=ORGAN_SCAL.SCAL_ID and ORGAN_SCAL.ORGAN_TYPE=ROOM_STAND_EQUIP.ORGAN_TYPE"
		        + " and room_type.room_type_id = ROOM_STAND_EQUIP.ROOM_TYPE_id"
		        + " and ROOM_STAND_EQUIP.EQUIP_TYPE_ID=EQUIP_TYPE.equip_type_id and EQUIP_TYPE.group_id in ("
		        + getChildGroup(2)
		        + ")"
		        + " group by room_type.room_type_id order by  room_type.REINDEX, convert(room_type.room_type_name using gbk) asc";
		return base.query(sql);
	}

	public static List getUserName(String user_id) throws Exception
	{
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		String organ_id = UtilAction.getUser().getOrganId() + "";
		String sql = "select * from user_info where organ_id=" + organ_id + "";
		if (null != user_id && !"".equals(user_id))
		{
			sql += " and user_id=" + user_id
			        + " order by convert(user_info.nick_name using gbk) asc";
		}
		// TODO Auto-generated method stub
		return base.query(sql);
	}

	public static HashMap getMoveBefore() throws Exception
	{
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		HashMap map = new HashMap();
		String organ_id = UtilAction.getUser().getOrganId() + "";
		String sql = "select * from equip,room where equip.room_id=room.room_id and  room.organ_id="
		        + organ_id + " ";
		List list2 = base.query(sql);
		for (int i = 0; i < list2.size(); i++)
		{
			HashMap row = (HashMap) list2.get(i);
			map.put("USER_NAME" + row.get("EQUIP_ID"), row.get("USER_NAME"));
			map.put("USER_ID" + row.get("EQUIP_ID"), row.get("USER_ID"));
			map.put("ROOM_ID" + row.get("EQUIP_ID"), row.get("ROOM_ID"));
		}
		return map;
	}

	public static HashMap getMoveAfter() throws Exception
	{
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		HashMap map = new HashMap();
		String organ_id = UtilAction.getUser().getOrganId() + "";
		String sql = "select * from user_info where organ_id=" + organ_id + " ";
		List list2 = base.query(sql);
		for (int i = 0; i < list2.size(); i++)
		{
			HashMap row = (HashMap) list2.get(i);
			map.put("USER" + row.get("USER_ID"), row.get("USER_NAME"));
		}
		return map;
	}

	public static List getAllRoomName(String organ_id) throws Exception {
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql="select * from room where organ_id="+organ_id+"";

		List list =base.query(sql);			
		return list;
	}

}
