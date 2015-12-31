package com.zty.edu_equip.dao;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.po.base.Encrypt;
import com.po.base.Util;
import com.po.serverbase.BaseDao;
import com.po.serverbase.Result;
import com.po.serverbase.SysConfig;
import com.po.serverbase.SystemInfo;
import com.po.socket.SocketHandler;
import com.po.socket.SocketObject;
import com.zty.edu_equip.util.Common;

public class EquipDao {

	// 资产详细信息 业务表数据不会很多 就不进行分页查询了

	public Map equip_info(String rid) throws Exception {
		List equip = new ArrayList();
		List equiptransfer = new ArrayList();
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		HashMap maps = new HashMap();
		// 查询资产基本信息
		String sql = "select equip.*,equip_type.equip_type_name,room.room_name from equip,equip_type,"
				+ " room where equip.equip_STATUS!='V' and equip.equip_type_id=equip_type.equip_type_id "
				+ " and room.room_id = equip.room_id";
		equip = base.query(sql);
		if (null == equip || equip.isEmpty()) {
			// 如果资产表没有这个资产那么就return
			maps.put("equip_info", false);
			return maps;
		}

		// 查询转移记录
		/*
		 * sql = "select * from ASSET_TRANSFER   where ASSET_ID=" + rid;
		 * equiptransfer = base.query(sql);
		 */

		maps.put("equip_info", true);
		maps.put("equip", equip);
		// maps.put("equip_transfer", equiptransfer);
		return maps;
	}

	public int getCount(String equip_type_id, String room_type_id,
			String room_name, String organ_id, String equip_name,
			String group_id) throws Exception {
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql = "select COUNT(EQUIP.EQUIP_ID)AS TOTAL from equip,equip_type,"
				+ " room where equip.equip_STATUS!='V' and equip.equip_type_id=equip_type.equip_type_id "
				+ " and room.room_id = equip.room_id ";
		if (null != equip_type_id || !"".equals(equip_type_id)) {
			sql += " and equip.equip_type_id=" + equip_type_id + " ";
		}
		if (null != room_type_id || !"".equals(room_type_id)) {
			sql += " and equip.room_type_id =" + room_type_id + " ";
		}
		if (null != room_name || !"".equals(room_name)) {
			sql += " and room.room_name like '%" + room_name + "%' ";
		}
		if (null != group_id || !"".equals(group_id)) {
			sql += " and equip_type.group_id =" + group_id + " ";
		}
		if (null != organ_id || !"".equals(organ_id)) {
			sql += " and room.organ_id=" + organ_id + " ";
		}

		if (null != equip_name || !"".equals(equip_name)) {
			sql += " and equip.equip_name like '%" + equip_name + "%' ";
		}
		List list = base.query(sql);
		HashMap map = (HashMap) list.get(0);

		return Integer.parseInt(map.get("TOTAL").toString());
	}

	public List findEquipList(String equip_type_id, String room_type_id,
			String room_name, String organ_id, String equip_name,
			String group_id) throws Exception {
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql = "select equip.*,equip_type.equip_type_name,room.room_name from equip,equip_type,"
				+ " room where equip.equip_STATUS!='V' and equip.equip_type_id=equip_type.equip_type_id "
				+ " and room.room_id = equip.room_id ";
		if (null != equip_type_id || !"".equals(equip_type_id)) {
			sql += " and equip.equip_type_id=" + equip_type_id + " ";
		}
		if (null != room_type_id || !"".equals(room_type_id)) {
			sql += " and room.room_type_id =" + room_type_id + " ";
		}
		if (null != group_id || !"".equals(group_id)) {
			sql += " and equip_type.group_id =" + group_id + " ";
		}
		if (null != room_name || !"".equals(room_name)) {
			sql += " and room.room_name like '%" + room_name + "%' ";
		}
		if (null != organ_id || !"".equals(organ_id)) {
			sql += " and room.organ_id=" + organ_id + " ";
		}
		if (null != equip_name || !"".equals(equip_name)) {
			sql += " and equip.equip_name like '%" + equip_name + "%' ";
		}
		sql += " group by equip_id";
		List list = base.query(sql);
		return list;
	}

	public static List getroom(String organ_id) throws Exception {

		List list = new ArrayList();
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql = "SELECT * FROM ROOM WHERE  ORGAN_ID=" + organ_id
				+ " order by room_name";
		list = base.query(sql);

		return list;

	}

	public static HashMap getOrganScalType(String organ_ID) throws Exception {
		HashMap row = new HashMap();
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql = "SELECT (ORGAN_SCAL.ORGAN_TYPE) as SCAL ,ORGAN.ORGAN_TYPE FROM ORGAN,ORGAN_SCAL WHERE ORGAN.SCAL_ID=ORGAN_SCAL.SCAL_ID AND ORGAN_ID="
				+ organ_ID + " ";
		List list = base.query(sql);
		if (list.size() == 0 || null == list) {
			return null;
		}
		HashMap map = (HashMap) list.get(0);
		row.put("SCAL", map.get("SCAL").toString());
		row.put("TYPE", map.get("ORGAN_TYPE").toString());
		return row;
	}

	public static HashMap getOrganName() {
		HashMap organ = new HashMap();
		String sql = "select * from organ ";
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		try {
			List list = base.query(sql);
			for (int i = 0; i < list.size(); i++) {
				HashMap map = (HashMap) list.get(i);
				BigDecimal organ_Id = (BigDecimal) map.get("ORGAN_ID");
				String organ_NAME = map.get("ORGAN_NAME") + "";
				organ.put(organ_Id, organ_NAME);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return organ;
	}

	// 装备报表
	public static List getStandardEquip(String type, String organ_id,
			String Organ_Type) throws Exception {
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql = "select rse.*,rt.ROOM_TYPE_ID,rt.ROOM_TYPE_NAME,et.EQUIP_TYPE_ID,et.asset_type_name,e.UNIT_CODE,"
				+ " sum(e.count) as EQUIP_COUNT,rs.ROOM_COUNT,rs.ROOM_COUNT2,rs.ROOM_AREA,"
				+ " (select count(rrt.REF_ID) from room r2,ref_room_type rrt where r2.ORGAN_ID=r.organ_id  "
				+ " and r2.room_id=rrt.room_id"
				+ " and rrt.ROOM_TYPE_ID=rt.ROOM_TYPE_ID) as actual  "
				+ " from (ROOM_STAND rs,ROOM_TYPE rt,ROOM_STAND_EQUIP rse,equip_type et,room r,organ,ref_room_type refRT)"
				+ " left join equip e on e.equip_type_id=rse.EQUIP_TYPE_ID and e.EQUIP_STATUS!='V' and e.room_id= r.room_id  and e.equip_type_id=et.equip_type_id"
				+ " where rt.ROOM_TYPE_ID=rs.ROOM_TYPE_ID and rs.SCAL_ID=organ.scal_id and rse.ROOM_TYPE_ID=rt.ROOM_TYPE_ID"
				+ " and rse.ORGAN_TYPE='"+Organ_Type+"' and rs.scal_id=organ.scal_id and r.organ_id=organ.organ_id"
				+ " and r.organ_id="+organ_id+" and refRt.ROOM_ID=r.ROOM_ID and refRt.room_type_id=rt.room_type_id"
				+ " and et.EQUIP_TYPE_ID=rse.EQUIP_TYPE_ID and et.group_id in (4,5,6,7,8)"
				+ " group by rse.ROOM_STAND_EQUIP_ID";
		List list = base.query(sql);
		return list;
	}

	public static List getInstrument(String room_type_id, String organID)
			throws Exception {
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql = "select  ROOM_STAND_EQUIP.* ,ROOM_TYPE.ROOM_TYPE_NAME,EQUIP_TYPE.ASSET_TYPE_NAME,EQUIP_TYPE.UNIT_CODE"
				+ " FROM  ROOM_STAND_EQUIP,EQUIP_TYPE,ROOM_TYPE,organ,ORGAN_SCAL"
				+ " where ROOM_STAND_EQUIP.EQUIP_TYPE_id=EQUIP_TYPE.EQUIP_TYPE_id"
				+ " and ROOM_STAND_EQUIP.ROOM_TYPE_id=ROOM_TYPE.ROOM_TYPE_id and ROOM_TYPE.ROOM_TYPE_id="
				+ room_type_id
				+ " and equip_type.group_id in ("
				        + Common
		                .getChildGroup(2)
		        + ")"
				+ " and organ.scal_id=ORGAN_SCAL.scal_id and ORGAN_SCAL.ORGAN_TYPE=ROOM_STAND_EQUIP.ORGAN_TYPE"
				+ " and organ_id=" + organID + "";
		List biaozhunlist = base.query(sql);
		List list = new ArrayList();
		if (null != biaozhunlist && biaozhunlist.size() > 0) {
			sql = "select equip.equip_type_id,sum(count) as COUNT from equip,room,REF_ROOM_TYPE"
					+ " where room.room_id=equip.room_id and REF_ROOM_TYPE.ROOM_TYPE_ID="
					+ room_type_id
					+ ""
					+ " and REF_ROOM_TYPE.room_id=room.room_id  group by equip_type_id ";
			List equip = base.query(sql);
			for (int i = 0; i < biaozhunlist.size(); i++) {
				HashMap map = (HashMap) biaozhunlist.get(i);
				String equip_type = map.get("EQUIP_TYPE_ID") + "";
				map.put("COUNT", "0");
				for (int j = 0; j < equip.size(); j++) {
					HashMap equipmap = (HashMap) equip.get(j);
					String e_type = equipmap.get("EQUIP_TYPE_ID") + "";
					if (equip_type.equals(e_type)) {
						map.put("COUNT", equipmap.get("COUNT"));
					}
				}
				list.add(map);
			}

		}
		return list;
	}

}
