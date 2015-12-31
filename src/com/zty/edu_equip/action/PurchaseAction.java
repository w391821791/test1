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
import com.zty.edu_equip.dao.PurchaseDao;
import com.zty.edu_equip.entity.Equip;
import com.zty.edu_equip.entity.EquipType;
import com.zty.edu_equip.entity.Purchase;
import com.zty.edu_equip.entity.RefEquipPurchase;
import com.zty.edu_equip.util.Common;
import com.zty.edu_equip.util.PageUtil;

public class PurchaseAction extends ActionSupport {
	private String url;
	private Equip equip;
	private Map map;
	private String equipName;
	private PageUtil pageUtil;
	private PurchaseDao pd = new PurchaseDao();

	public String jump() {
		return url;
	}

	public String savePurchase() throws Exception {
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		EquipType equipType = (EquipType) baseDao.getObjFromDbById(
				EquipType.class, equip.getEquipTypeId() + "");
		List list = baseDao.query("select getParentList(" + equipType.getGroupId()
				+ ")  as parentIds");
		HashMap row = (HashMap) list.get(0);
		String parentIds = row.get("PARENTIDS").toString().substring(0, 1);
		int count = equip.getCount().intValue();
		String equipName = equip.getEquipName();
		String amount = "0";
		if (null != equip.getUnitPrice()) {
			amount = equip.getUnitPrice().intValue() * count + "";
		}
		Purchase purchase = new Purchase();
		purchase.setOrganId(UtilAction.getUser().getOrganId());
		purchase.setPurchaseDate(Util.parseTimestamp(equip.getPurchaseDate()
				.toString()));
		purchase.setAllCount(new BigDecimal(count));
		purchase.setAllAmount(new BigDecimal(amount));
		baseDao.addObj4Execute(purchase);
		equip.setCardStatus("A");
		equip.setEquipStatus("A");
		if ("1".equals(parentIds) && count > 1) {
			String pkValue = null;
			equip.setCount(new BigDecimal(1));
			for (int i = 0; i < count; i++) {
				Equip newequip = new Equip();
				pkValue = newequip.getPkValue();
				newequip = equip;
				newequip.setPkValue(pkValue);
				newequip.setCreateTime(Util.getNow());
				newequip.setVersion(SeqenceGenerator.getNextOutranetVer());
				newequip.setEquipName(equipName + "(" + (i + 1) + ")");
				baseDao.addSql4Execute(newequip.generateInsertSql());
				RefEquipPurchase rep = new RefEquipPurchase();
				rep.setPurchaseId(new BigDecimal(purchase.getPkValue()));
				rep.setEquipId(new BigDecimal(newequip.getPkValue()));
				rep.setAmount(new BigDecimal(amount));
				rep.setCount(new BigDecimal(count));
				baseDao.addObj4Execute(rep);
			}
		} else {
			equip.setCreateTime(Util.getNow());
			equip.setVersion(SeqenceGenerator.getNextOutranetVer());
			baseDao.addObj4Execute(equip);
			RefEquipPurchase rep = new RefEquipPurchase();
			rep.setPurchaseId(new BigDecimal(purchase.getPkValue()));
			rep.setEquipId(new BigDecimal(equip.getPkValue()));
			rep.setAmount(new BigDecimal(amount));
			rep.setCount(new BigDecimal(count));
			baseDao.addObj4Execute(rep);
		}
		baseDao.execute();
		return SUCCESS;
	}

	public String findPurchase() throws Exception {
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql = "SELECT P.PURCHASE_ID,EQUIP2.EQUIP_NAME,EQUIP2.ASSET_TYPE_NAME,EQUIP2.ROOM_NAME,"
				+ " P.PURCHASE_DATE,P.ALL_COUNT,P.ALL_AMOUNT"
				+ " FROM PURCHASE P,REF_EQUIP_PURCHASE REP,"
				+ " (SELECT DISTINCT E.EQUIP_ID,E.EQUIP_NAME,ET.ASSET_TYPE_NAME,R2.PURCHASE_ID,R.ROOM_NAME "
				+ " FROM EQUIP E,EQUIP_TYPE ET,REF_EQUIP_PURCHASE R2,ROOM R"
				+ " WHERE E.EQUIP_ID=R2.EQUIP_ID AND E.EQUIP_TYPE_ID=ET.EQUIP_TYPE_ID AND"
				+ " E.ROOM_ID=R.ROOM_ID AND R.ORGAN_ID="+UtilAction.getUser().getOrganId()
				+ " ) EQUIP2"
				+ " WHERE P.PURCHASE_ID=REP.PURCHASE_ID AND EQUIP2.EQUIP_ID=REP.EQUIP_ID AND EQUIP2.PURCHASE_ID=REP.PURCHASE_ID "
				+ " AND P.ORGAN_ID="+UtilAction.getUser().getOrganId();
		if (null != equipName && "" != equipName.trim()) {
			sql += " AND EQUIP2.EQUIP_NAME LIKE '%" + equipName + "%'";
		}
		sql+=" GROUP BY PURCHASE_ID";
		baseDao.query(sql);
		map = baseDao.getRowsWithPaging(sql, pageUtil.getPage() - 1,
				pageUtil.getLines());
		return SUCCESS;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Equip getEquip() {
		return equip;
	}

	public void setEquip(Equip equip) {
		this.equip = equip;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public PageUtil getPageUtil() {
		return pageUtil;
	}

	public void setPageUtil(PageUtil pageUtil) {
		this.pageUtil = pageUtil;
	}

	public String getEquipName() {
		return equipName;
	}

	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}

}
