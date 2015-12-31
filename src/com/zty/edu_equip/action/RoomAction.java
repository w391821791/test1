package com.zty.edu_equip.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;













import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.po.serverbase.BaseDao;
import com.po.serverbase.SeqenceGenerator;
import com.po.serverbase.SysConfig;
import com.po.serverbase.SystemInfo;
import com.zty.edu_equip.entity.Equip;
import com.zty.edu_equip.entity.Organ;
import com.zty.edu_equip.entity.OrganScal;
import com.zty.edu_equip.entity.RefRoomType;
import com.zty.edu_equip.entity.Room;
import com.zty.edu_equip.entity.UserInfo;
import com.zty.edu_equip.util.Common;
import com.zty.edu_equip.util.PageUtil;

public class RoomAction extends ActionSupport
{

	private Map map;
	private String url;
	private PageUtil pageUtil;
	private Map<String, String[]> pageMap;
	private Room room;
	private String room_id;
	private String roomTypeId;
	private String organId;
	
	public String jump()
	{
		return url;
	}

	public String findRoom() throws Exception
	{
		System.out.println("organId"+organId);
		if(null==organId || "".equals(organId)){
			organId=UtilAction.getUser().getOrganId()+"";
		}
		String sql = "select r.*,refRt.ROOM_TYPE_ID,rt.ROOM_TYPE_NAME from room r "
		        + " left join  ref_room_type refRt on refRt.ROOM_ID=r.ROOM_ID"
		        + " left join ROOM_TYPE rt on rt.ROOM_TYPE_ID=refRt.ROOM_TYPE_ID"
		        + " WHERE r.ORGAN_ID=" +organId;
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		if(null!=room_id && !"".equals(room_id)){
			if(null!=roomTypeId && !"".equals(roomTypeId))
				sql+=" AND rt.ROOM_TYPE_ID="+roomTypeId;
			sql+=" AND r.ROOM_ID="+room_id;
		}
		else if(null!=roomTypeId && !"".equals(roomTypeId))
			sql+=" AND rt.ROOM_TYPE_ID="+roomTypeId;
		else if(null!=room){
			if(null!=room.getRoomName() && !"".equals(room.getRoomName()))
				sql+=" AND r.ROOM_NAME like '%"+room.getRoomName()+"%'";
			if(null!=room.getRoomNo()  && !"".equals(room.getRoomNo())){
				sql+=" AND r.ROOM_NO like '%"+room.getRoomNo()+"%'";
			}
		}
		if (!pageUtil.getOrderField().isEmpty())
		{
			sql += " order by " + pageUtil.getOrderField()
			        + pageUtil.getOrder();
		}
		map = baseDao.getRowsWithPaging(sql, pageUtil.getPage() - 1,
		        pageUtil.getLines());
		return SUCCESS;
	}

	public String findRoomById() throws Exception
	{
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql="select r.*,refRt.ROOM_TYPE_ID,rt.ROOM_TYPE_NAME from room r "
				+ " left join  ref_room_type refRt on refRt.ROOM_ID=r.ROOM_ID"
				+ " left join ROOM_TYPE rt on rt.ROOM_TYPE_ID=refRt.ROOM_TYPE_ID";
		if(null==roomTypeId || "".equals(roomTypeId))
			sql+= " where refRt.ROOM_TYPE_ID is null and r.room_id="+room_id;
		else
			sql+= " where refRt.ROOM_TYPE_ID="+roomTypeId+" and r.room_id="+room_id;
		List roomInfo=baseDao.query(sql);
		sql=" select refRt.*,rt.ROOM_TYPE_NAME from ref_room_type refRt,ROOM_TYPE rt "
		   +" where  refRt.room_type_id=rt.room_type_id and room_id="+room_id;
		List roomTypeList=baseDao.query(sql);
		map = new HashMap();
		map.put("roomInfo", roomInfo);
		map.put("roomTypeList", JSONUtil.serialize(roomTypeList));
		return "save";
	}

	public String saveRoom() throws Exception
	{
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		String roomTypes=ServletActionContext.getRequest().getParameter("roomTypes");
		String[] arr=null;
		if(null!=roomTypes)arr=roomTypes.split(",");
		room.setOrganId(UtilAction.getUser().getOrganId());
		room.setCardNo(room.getPkValue());
		String sql="DELETE FROM ref_room_type WHERE ROOM_ID="+room.getPkValue();
		baseDao.addSql4Execute(sql);
		baseDao.addObj4Execute(room);
		for(int i=0;null!=arr && i<arr.length;i++){
			if(!"".equals(arr[i])){
				RefRoomType refRT=new RefRoomType();
				refRT.setRoomId(new BigDecimal(room.getPkValue()));
				refRT.setRoomTypeId(new BigDecimal(arr[i]));
				baseDao.addObj4Execute(refRT);
			}
		}
		baseDao.execute();
		// room.setPersonCount(null);
		return SUCCESS;
	}
	public String delRoom() throws Exception{
		BaseDao baseDao=new BaseDao(SystemInfo.getMainJndi(), null);
		List equips=baseDao.getObjsFromDbById(Equip.class," ROOM_ID="+room.getPkValue());
		map=new HashMap();
		if(null!=equips && equips.size()>0){
			map.put("msg", "该房间下面拥有装备不能删除");
		}else{
			String sql="";
			List refRTs=baseDao.getObjsFromDbById(RefRoomType.class," ROOM_ID="+room.getPkValue());
			for (int i=0;i<refRTs.size();i++)
            {
				RefRoomType refRT=(RefRoomType) refRTs.get(i);
				sql="delete from REF_ROOM_TYPE where ref_id="+refRT.getPkValue();
				baseDao.addSql4Execute(sql);
            }
			sql="delete from room where room_id="+room.getPkValue();
			baseDao.addSql4Execute(sql);
			baseDao.execute();
			map.put("msg", "删除成功");
		}
		return SUCCESS;
	}
	public String checkRoomName() throws Exception{
		BaseDao baseDao=new BaseDao(SystemInfo.getMainJndi(), null);
		List rooms=baseDao.getObjsFromDbById(Room.class, " ORGAN_ID="+UtilAction.getUser().getOrganId()
											+" AND ROOM_NAME='"+room.getRoomName()+"'");
		map=new HashMap();
		if(rooms.isEmpty()){
			map.put("boo",true);
		}else{
			map.put("boo",false);
		}
		return SUCCESS;
	} 

	public String findRoomEquip() throws Exception
	{
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql = "SELECT e.*,et.ASSET_TYPE_NAME FROM EQUIP e"
				+ " left join EQUIP_TYPE et on et.EQUIP_TYPE_ID=e.EQUIP_TYPE_ID"
				+ " WHERE  EQUIP_STATUS='A'  AND ROOM_ID="
		        + room_id;
		if (!pageUtil.getOrderField().isEmpty())
		{
			sql += " order by " + pageUtil.getOrderField()
			        + pageUtil.getOrder();
		}
		map = baseDao.getRowsWithPaging(sql, pageUtil.getPage() - 1,
		        pageUtil.getLines());
		return SUCCESS;
	}
	public String exportRoomEquip() throws Exception
	{
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		Room room=(Room) baseDao.getObjFromDbById(Room.class, room_id);
		List equips=baseDao.getObjsFromDbById(Equip.class," EQUIP_STATUS='A' AND ROOM_ID="+room_id);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet equipSheet =wb.createSheet();
		HSSFHeader header=equipSheet.getHeader();
		header.setCenter(room.getRoomName()+"     "+room.getRoomNo());
		HSSFFooter footer = equipSheet.getFooter();
		footer.setCenter("负责人："+room.getDutyMan()+"     第"+HSSFFooter.page()+"页 共"+HSSFFooter.numPages()+"页     ");
		equipSheet.setColumnWidth(0,1000);
		equipSheet.setColumnWidth(1,7900);
		for (int i = 0; i < equips.size(); i++)
        {
			Equip  equip=(Equip) equips.get(i);
			HSSFRow row=equipSheet.createRow(i);
			row.createCell(0).setCellValue((i+1)<10?"0"+(i+1)+".":(i+1)+".");
			row.createCell(1).setCellValue(equip.getEquipName());
        }
		ServletOutputStream sos = null;
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");
		response.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode("打印数据.xls","UTF-8"));
		sos= response.getOutputStream();
		wb.write(sos);
		sos.close();
		return SUCCESS;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}

	public Map getMap()
	{
		return map;
	}

	public void setMap(Map map)
	{
		this.map = map;
	}

	public Room getRoom()
	{
		return room;
	}

	public void setRoom(Room room)
	{
		this.room = room;
	}

	public PageUtil getPageUtil()
	{
		return pageUtil;
	}

	public void setPageUtil(PageUtil pageUtil)
	{
		this.pageUtil = pageUtil;
	}

	public String getRoom_id()
	{
		return room_id;
	}

	public void setRoom_id(String room_id)
	{
		this.room_id = room_id;
	}

	public Map<String, String[]> getPageMap()
	{
		return pageMap;
	}

	public void setPageMap(Map<String, String[]> pageMap)
	{
		this.pageMap = pageMap;
	}

	public void setRoomTypeId(String roomTypeId)
	{
		this.roomTypeId = roomTypeId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
	
}
