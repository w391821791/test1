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
import com.zty.edu_equip.entity.EquipMove;
import com.zty.edu_equip.entity.RefEquipMoveEquip;
import com.zty.edu_equip.entity.UserInfo;
import com.zty.edu_equip.util.Common;
import com.zty.edu_equip.util.PageUtil;

public class MoveAction extends ActionSupport {

	private String url;
	// 分页工具类
	private PageUtil pageUtil;
	// 使用map 获取页面传递过来的数据 传递普通数据
	private Map<String, String[]> pageMap;

	// 使用 map 给页面传递数据
	private Map map;
	private EquipMove equipMove;
	private RefEquipMoveEquip refEquipMoveEquip;
	private String applytime1;
	private String applytime2;
	private String equip_id;
	private String pk_id;
	private String apply_remark;

	public String jump() {
		return url;
	}

	// 转移申请页面数据
	public String findMove() throws Exception {
		map = new HashMap();
		UserInfo user = UtilAction.getUser();
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql = "SELECT em.EQUIP_MOVE_ID,e.EQUIP_ID,e.EQUIP_NAME,refmq.USER_NAME1,refmq.USER_NAME2,(select r.ROOM_NAME from room r where r.ROOM_ID=refmq.ROOM_ID1) room_name1, "
				+ " (select r.ROOM_NAME from room r where r.ROOM_ID=refmq.ROOM_ID2) room_name2,em.APPROVE_RESULT"
				+ " FROM equip e,ref_equip_move_equip refmq,EQUIP_MOVE em"
				+ " where refmq.EQUIP_ID=e.EQUIP_ID and em.EQUIP_MOVE_ID=refmq.EQUIP_MOVE_ID"
				+ " and em.ORGAN_ID=" + user.getOrganId();
		if ("R_SCHOOL".equals(user.getUserPerms())) {
			sql += " and (refmq.USER_ID1=" + user.getPkValue()
					+ " or refmq.USER_ID2=" + user.getPkValue() + ")";
		}
		if (null != equipMove) {
			if (null != equipMove.getApproveResult()
					&& !"".equals(equipMove.getApproveResult())) {
				if ("S".equals(equipMove.getApproveResult()))
					sql += " and em.APPROVE_RESULT is null";
				else
					sql += " and em.APPROVE_RESULT = '"
							+ equipMove.getApproveResult() + "'";
			}
			String equip_name = pageMap.get("equip_name")[0];
			String room_id = pageMap.get("room_id")[0];
			String user_name = pageMap.get("user_name")[0];
			if (null != equip_name && !"".equals(equip_name.trim())) {
				sql += " and e.EQUIP_NAME like '%" + equip_name + "%'";
			}
			if (null != room_id && !"".equals(room_id.trim())) {
				sql += " and (refmq.room_id1=" + room_id
						+ " or refmq.room_id2=" + room_id + ")";
			}
			if (null != user_name && !"".equals(user_name.trim())) {
				sql += " and (refmq.USER_ID1=" + user_name+" or refmq.USER_ID2=" + user_name + ")";
			}
		}
		sql += " ORDER BY APPLY_TIME DESC";
		map = base.getRowsWithPaging(sql, pageUtil.getPage() - 1,
				pageUtil.getLines());
		return SUCCESS;
	}

	public String findMoveById() throws Exception {
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		equipMove = (EquipMove) baseDao.getObjFromDbById(EquipMove.class,
				equipMove.getPkValue());
		List refems = baseDao.getObjsFromDbById(RefEquipMoveEquip.class,
				"EQUIP_MOVE_ID=" + equipMove.getPkValue());
		String sql = "";
		map = new HashMap();
		map.put("equipMpve", equipMove);
		map.put("refems", refems);
		return "moveSave";
	}

	// 转移装备页面数据
	public String findMoveEquip() throws Exception {
		map = new HashMap();
		BaseDao base = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql = "SELECT ref_equip_move_equip.*,equip.equip_name ,(r1.room_name) as ROOM_NAME1,(r2.room_name) as ROOM_NAME2 "
				+ " FROM equip,ref_equip_move_equip "
				+ " left join room r1 on ref_equip_move_equip.room_id1=r1.room_id  "
				+ " left join room r2 on ref_equip_move_equip.room_id2 =r2.room_id"
				+ " where ref_equip_move_equip.equip_id=equip.equip_id "
				+ "  and ref_equip_move_equip.EQUIP_MOVE_ID=" + pk_id + " ";
		map = base.getRowsWithPaging(sql, pageUtil.getPage() - 1,
				pageUtil.getLines());
		sql = "select EQUIP_MOVE.*,USER_INFO.NICK_NAME from EQUIP_MOVE,USER_INFO where USER_INFO.USER_ID=EQUIP_MOVE.APPLYER_ID and EQUIP_MOVE.EQUIP_MOVE_ID="
				+ pk_id + "";
		List list = base.query(sql);
		HashMap row = (HashMap) list.get(0);
		String type = row.get("MOVE_TYPE") + "";
		if ("A".equals(type))
			type = "领用";
		if ("B".equals(type))
			type = "转移";
		if ("C".equals(type))
			type = "借用";
		if ("D".equals(type))
			type = "归还";
		map.put("MOVE_TYPE", type);
		map.put("APPLY_TIME", row.get("APPLY_TIME").toString().substring(0, 10));
		map.put("NICK_NAME", row.get("NICK_NAME"));
		map.put("STATUS", row.get("STATUS"));
		map.put("APPLY_REMARK", row.get("APPLY_REMARK"));
		map.put("APPROVE_REAMRK", row.get("APPROVE_REAMRK"));
		return SUCCESS;
	}

	// 保存转移
	public String saveMove() throws Exception {
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		// room.setPersonCount(null);
		System.out.println(refEquipMoveEquip.getRoomId2());
		HashMap MoveBefore = Common.getMoveBefore();// 转移前名称，ID
		HashMap MoveAfter = Common.getMoveAfter();// 转以后名称,ID
		UserInfo user = UtilAction.getUser();
		String organ_id = user.getOrganId() + "";
		String user_id = user.getPkValue() + "";
		System.out.println(apply_remark);
		EquipMove equipMove = new EquipMove();
		equipMove.setApplyRemark(apply_remark);
		equipMove.setApplyTime(Util.getNow());
		equipMove.setOrganId(Util.newBigDecimal(organ_id));
		System.out.println("refEquipMoveEquip.getMoveType()="
				+ refEquipMoveEquip.getMoveType());
		equipMove.setMoveType(refEquipMoveEquip.getMoveType());
		System.out.println("user_id=" + user_id);
		equipMove.setApplyerId(Util.newBigDecimal(user_id));
		equipMove.setStatus("2");
		equipMove.setVersion(SeqenceGenerator.getNextOutranetVer());
		baseDao.addObj4Execute(equipMove);
		String equipMoveId = equipMove.getPkValue();

		String equipId[] = equip_id.split(",");
		for (int i = 0; i < equipId.length; i++) {
			RefEquipMoveEquip refEquipMove = new RefEquipMoveEquip();
			refEquipMove.setMoveType(refEquipMoveEquip.getMoveType());
			refEquipMove.setEquipId(Util.newBigDecimal(equipId[i]));
			refEquipMove.setStatus("A");

			String userId2 = refEquipMoveEquip.getUserId2() + "";
			String roomId2 = refEquipMoveEquip.getRoomId2() + "";
			System.out.println("roomId2=====" + roomId2);
			if (null != userId2 && !"".equals(userId2)) {
				refEquipMove.setUserId2(Util.newBigDecimal(userId2));
			}
			if (null != roomId2 && !"".equals(roomId2)) {
				refEquipMove.setRoomId2(Util.newBigDecimal(roomId2));
			}

			if (null != MoveAfter.get("USER" + refEquipMoveEquip.getUserId2())
					&& !"".equals(MoveAfter.get("USER"
							+ refEquipMoveEquip.getUserId2()))) {
				refEquipMove.setUserName2(MoveAfter.get("USER"
						+ refEquipMoveEquip.getUserId2())
						+ "");
			}

			String user_id1 = MoveBefore.get("USER_ID" + equipId[i]) + "";
			if (null != user_id1 && !"".equals(user_id1)) {
				refEquipMove.setUserId1(Util.newBigDecimal(user_id1));
			}

			String room_id1 = MoveBefore.get("ROOM_ID" + equipId[i]) + "";
			if (null != room_id1 && !"".equals(room_id1)) {
				refEquipMove.setRoomId1(Util.newBigDecimal(room_id1));
			}

			String user_name1 = MoveBefore.get("USER_NAME" + equipId[i]) + "";
			if (null != user_name1 && !"".equals(user_name1)) {
				refEquipMove.setUserName1(user_name1);
			}

			refEquipMove.setEquipMoveId(Util.newBigDecimal(equipMoveId));
			baseDao.addObj4Execute(refEquipMove);

		}
		;

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

	public EquipMove getEquipMove() {
		return equipMove;
	}

	public void setEquipMove(EquipMove equipMove) {
		this.equipMove = equipMove;
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

	public RefEquipMoveEquip getRefEquipMoveEquip() {
		return refEquipMoveEquip;
	}

	public void setRefEquipMoveEquip(RefEquipMoveEquip refEquipMoveEquip) {
		this.refEquipMoveEquip = refEquipMoveEquip;
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
