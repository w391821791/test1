package com.zty.edu_equip.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.po.serverbase.BaseDao;
import com.po.serverbase.SystemInfo;
import com.zty.edu_equip.entity.Organ;
import com.zty.edu_equip.util.Common;
import com.zty.edu_equip.util.PageUtil;

public class OrganManageAction extends ActionSupport
{
	private String url;
	private String organName;
	private String organID;
	
	private String scalID;
	private String organType;
	private String createOrganTime;
	private String areaCovered;
	private String address;
	private String coveredArea;
	private String studentCount;
	private String teacherCount;

	
	private Map map = new HashMap();
	private PageUtil pageUtil = new PageUtil();
	public String jump(){
		return url;
	}

	public String organList() throws Exception{
		System.out.println("½øÈëorganList");
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql = "SELECT ORGAN_SCAL.SCAL_NAME, ORGAN.* FROM ORGAN,ORGAN_SCAL WHERE "
				+ "ORGAN.SCAL_ID = ORGAN_SCAL.SCAL_ID AND ORGAN_ID IN ("+Common.getChildOrgan(Integer.parseInt(UtilAction.getUser().getOrganId().toString()))+")";
		if(null!=organName && ""!=organName.trim()){
			sql+=" AND ORGAN.ORGAN_NAME LIKE '%"+organName+"%'";
		}
		sql += " ORDER BY CONVERT(ORGAN.ORGAN_NAME USING gbk)";
		baseDao.query(sql);
		map = baseDao.getRowsWithPaging(sql, pageUtil.getPage() - 1,
		        pageUtil.getLines());
		return SUCCESS;
	}
	
	public String changeOrgan() throws Exception{
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		String sql = "SELECT * FROM ORGAN WHERE ORGAN.ORGAN_ID = '"+organID+"'";
		String sql2 = "SELECT * FROM ORGAN_SCAL";
		List organInfo = baseDao.query(sql);
		List organScal = baseDao.query(sql2);
		map.put("organInfo", organInfo);
		map.put("organScal", organScal);
		return "changeOrgan";
	}
	
	public String saveOrgan() throws Exception{
		
		BaseDao baseDao = new BaseDao(SystemInfo.getMainJndi(), null);
		if(""==coveredArea || null==coveredArea){coveredArea="0";}
		if(""==areaCovered || null==areaCovered){areaCovered="0";}
		if(""==studentCount || null==studentCount){studentCount="0";}
		if(""==teacherCount || null==teacherCount){teacherCount="0";}
		String sql = "UPDATE ORGAN SET SCAL_ID='"+scalID+"',ORGAN_TYPE='"+organType+"',ORGAN_NAME='"+organName+"',ADDRESS='"+address+"',AREA_COVERED='"+areaCovered+"',"
				+ "COVERED_AREA='"+coveredArea+"',STUDENT_COUNT='"+studentCount+"',TEACHER_COUNT='"+teacherCount+"'";
		
		if(createOrganTime.isEmpty()){
		}else {
			sql+=",CREATE_ORGAN_TIME='"+createOrganTime+"' ";		
		}
		sql+=" WHERE ORGAN_ID = '"+organID+"'";
		
		baseDao.execute(sql);
		return SUCCESS;
	}
	
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getOrganName()
	{
		return organName;
	}

	public void setOrganName(String organName)
	{
		this.organName = organName;
	}

	public Map getMap()
	{
		return map;
	}

	public void setMap(Map map)
	{
		this.map = map;
	}

	public PageUtil getPageUtil()
	{
		return pageUtil;
	}

	public void setPageUtil(PageUtil pageUtil)
	{
		this.pageUtil = pageUtil;
	}

	public String getOrganID()
	{
		return organID;
	}

	public void setOrganID(String organID)
	{
		this.organID = organID;
	}

	public String getScalID()
	{
		return scalID;
	}

	public void setScalID(String scalID)
	{
		this.scalID = scalID;
	}

	public String getOrganType()
	{
		return organType;
	}

	public void setOrganType(String organType)
	{
		this.organType = organType;
	}

	public String getCreateOrganTime()
	{
		return createOrganTime;
	}

	public void setCreateOrganTime(String createOrganTime)
	{
		this.createOrganTime = createOrganTime;
	}

	public String getAreaCovered()
	{
		return areaCovered;
	}

	public void setAreaCovered(String areaCovered)
	{
		this.areaCovered = areaCovered;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getCoveredArea()
	{
		return coveredArea;
	}

	public void setCoveredArea(String coveredArea)
	{
		this.coveredArea = coveredArea;
	}

	public String getStudentCount()
	{
		return studentCount;
	}

	public void setStudentCount(String studentCount)
	{
		this.studentCount = studentCount;
	}

	public String getTeacherCount()
	{
		return teacherCount;
	}

	public void setTeacherCount(String teacherCount)
	{
		this.teacherCount = teacherCount;
	}
	
	
	
}
