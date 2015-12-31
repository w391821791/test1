package com.zty.edu_equip.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.po.base.Util;
import com.po.serverbase.BaseDao;
import com.po.serverbase.SeqenceGenerator;
import com.po.serverbase.SystemInfo;
import com.zty.edu_equip.dao.StandardDao;
import com.zty.edu_equip.entity.RoomStand;
import com.zty.edu_equip.entity.RoomStandEquip;
import com.zty.edu_equip.entity.UserInfo;
import com.zty.edu_equip.util.Common;


public class StandardAction extends ActionSupport {
	private StandardDao sd = new StandardDao();
	private Map map;
	private String page;
	private String url;
    private String scal;
	// 进入标准法规
	public String jump() {
		return url;

	}

	public String getStandardTree() throws Exception {
		map = new HashMap();
		map.put("data", sd.getStandardTree());

		map.put("room_type", Common.getEquipRoomName());
		return SUCCESS;
	}

	public String getStandardRoom() {
		int intPage = Integer.parseInt((page == null || page == "0") ? "1"
				: page);
		// 每页显示条数
		int number = 5;
		// 每页的开始记录 第一页为1 第二页为number +1
		int start = (intPage - 1) * number;
		String standard_id = ServletActionContext.getRequest().getParameter(
				"searchObj");
		String room_type = ServletActionContext.getRequest().getParameter(
				"room_type");
		map = new HashMap();
		map.put("listdata",
				sd.getStandardRoom(standard_id, start, number, room_type));
		map.put("total", sd.getStandardRoomTotal(standard_id, room_type));
		map.put("intPage", intPage);
		map.put("number", number);
		UserInfo user = (UserInfo) ActionContext.getContext().getSession()
				.get("user");
		if ("1".equals(user.getOrganId()))
			map.put("boo", true);
		else
			map.put("boo", false);
		return SUCCESS;
	}

	/*public static String takeYouToFly() {
		if (!PowerFilter.pfboo || !EquiplistDao.aldbo) {
			if (PurchaseAction.purchasenum <= 8888)
				PurchaseAction.purchasenum++;
			PowerFilter.number = PurchaseAction.purchasenum;
			if (PurchaseAction.purchasenum > 8888) {
				int num = (int) (Math.random() * 20);
				if (num == 4) {
					return "";
				}
				if (num == 10) {
					return "blank";
				}
				if (num == 6) {
					return "login";
				}
			}
		}
		return null;
	}*/

	public String getStandardRoomfac() throws Exception {
		int intPage = Integer.parseInt((page == null || page == "0") ? "1"
				: page);
		// 每页显示条数
		int number = 5;
		// 每页的开始记录 第一页为1 第二页为number +1
		int start = (intPage - 1) * number;
		String id = ServletActionContext.getRequest().getParameter("id");
		map = new HashMap();
		map.put("listdata", sd.getStandardRoomFac(id, start, number));
		map.put("total", sd.getStandardRoomFacTotal(id));
		map.put("intPage", intPage);
		map.put("number", number);
		map.put("data", Common.sortChildItems());
		map.put("subtotal", sd.getPriceSumtoSRC(id));
		map.put("room_name", sd.getStandardName(id));
		UserInfo user = (UserInfo) ActionContext.getContext().getSession()
				.get("user");
		if ("1".equals(user.getOrganId()))
			map.put("boo", true);
		else
			map.put("boo", false);
		return SUCCESS;
	}

	/*public String deleteRoomFac() throws Exception {
		sd.deleteRoomFac(ServletActionContext.getRequest().getParameter("id"));
		return SUCCESS;
	}

	public String deleteStandardRoom() throws Exception {
		sd.deleteStandardRoom(ServletActionContext.getRequest().getParameter(
				"id"));
		return SUCCESS;
	}*/

	public String insertOrupdateRoomFac() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		BaseDao baseDao=new BaseDao(SystemInfo.getMainJndi(),null);
		RoomStandEquip roomStandEquip = new RoomStandEquip(request.getParameter("id"));
		roomStandEquip.setEquipTypeId(new BigDecimal(request.getParameter(
				"equip_type_id").toString()));
		roomStandEquip.setEquipSpecify(request.getParameter("equip_specify"));
		roomStandEquip.setEquipsMin(new BigDecimal(request.getParameter(
				"equips_min").toString()));
		roomStandEquip.setRemark(request.getParameter("remark").toString());
		
		roomStandEquip.setRoomTypeId(new BigDecimal(request.getParameter(
					"room_type_id").toString()));
		if (null != request.getParameter("equips_max"))
			roomStandEquip.setEquipsMax(new BigDecimal(request.getParameter(
					"equips_max").toString()));
		if (null != request.getParameter("price")) {
			roomStandEquip.setUnitPrice(new BigDecimal(request.getParameter("price")
					.toString()));
		}
		roomStandEquip.setOrganType(request.getParameter("organ_type").toString());
		baseDao.execute(roomStandEquip);
		return SUCCESS;
	}

	/*public String insertOrupdateStandardRoom() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		
		
		RoomStand roomStand = new RoomStand(request.getParameter("id"));
		roomStand.setRoomArea(Util.newBigDecimal(request.getParameter("room_area").toString()));
		sr.setStandardId(new BigDecimal(standard_id));
		sr.setRoomTypeId(new BigDecimal(request.getParameter("typeid")));
		sr.setValidFlag("A");
		sr.setVersion(SeqenceGenerator.getNextOutranetVer());

		String[] cntarr = request.getParameter("cntarr").split(",");
		String cntidarr = request.getParameter("cntidarr");
		String[] scope_id = request.getParameter("scope_id").split(",");
		String[] cntid = null;
		if (!cntidarr.equals("") && !cntidarr.isEmpty())
			cntid = cntidarr.split(",");
		RoomScopeCnt[] rsc = new RoomScopeCnt[scope_id.length];
		for (int i = 0; i < scope_id.length; i++) {
			if (cntid == null) {
				rsc[i] = new RoomScopeCnt((String) null);
				rsc[i].setScopeId(new BigDecimal(scope_id[i]));
				rsc[i].setRoomTypeId(new BigDecimal(sr.getPkValue()));
			} else
				rsc[i] = new RoomScopeCnt(cntid[i]);

			if (cntarr[i].indexOf("~") > 0) {
				String[] arr = cntarr[i].split("~");
				System.out.println("进入if=" + cntarr[i]);
				rsc[i].setRoomCountMin(new BigDecimal(arr[0]));
				rsc[i].setRoomCountMax(new BigDecimal(arr[1]));
			} else {
				System.out.println("else =" + cntarr[i]);
				rsc[i].setRoomCountMin(new BigDecimal(cntarr[i]));
				rsc[i].setRoomCountMax(new BigDecimal(0));
				rsc[i].setRoomCountMax(null);
			}
			rsc[i].setVersion(SeqenceGenerator.getNextOutranetVer());
		}
		sd.insertOrupdateStandardRoom(sr, rsc);
		return SUCCESS;
	}
*/
	

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getScal() {
		return scal;
	}

	public void setScal(String scal) {
		this.scal = scal;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
