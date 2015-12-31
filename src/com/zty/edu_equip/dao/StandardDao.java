package com.zty.edu_equip.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;





import com.po.serverbase.BaseDao;
import com.po.serverbase.SeqenceGenerator;
import com.po.serverbase.SystemInfo;
import com.zty.edu_equip.util.Common;


public class StandardDao
{
	/**
	 * ï¿½ï¿½Ñ¯ï¿½ï¿½ï¿½ï¿½ï¿½Ò±ï¿½×¼,Ò³ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ó²¿²Ëµï¿½ï¿½ï¿?
	 * @return
	 */
	public List getStandardTree(){
		
		List<Map> toplistmap=new ArrayList();
		try
		{
			//put data to map

			List<Map> listmap=new ArrayList();
        	
        	
	              	
	        	HashMap itemMap=new HashMap();	        	
	        	itemMap.put("text","Ð¡Ñ§±ê×¼");
	        	itemMap.put("id","A");
	        	listmap.add(itemMap); 
	        	
	        	HashMap itemMap1=new HashMap();	        	
	        	itemMap1.put("text","³õÖÐ±ê×¼");
	        	itemMap1.put("id","B");
	        	listmap.add(itemMap1); 
	        	
	        	HashMap itemMap2=new HashMap();	        	
	        	itemMap2.put("text","¸ßÖÐ±ê×¼");
	        	itemMap2.put("id","C");
	        	listmap.add(itemMap2); 
	        	toplistmap.add(itemMap); 	        
        }
        catch (Exception e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		return toplistmap;
	}

	/**
	 * 
	 */
	public List getStandardRoom(String standard_id,int start,int number,String room_type){
		
		String sql="select distinct sr.STANDARD_ROOM_ID,sr.ROOM_AREA,sr.PRICE,sr.REMARK,rt.ROOM_TYPE_ID" 
		          +" from standard_room sr,ROOM_TYPE rt"
		          +" where sr.VALID_FLAG='A' and sr.ROOM_TYPE_ID=rt.ROOM_TYPE_ID  and sr.STANDARD_ID="+standard_id;
		if(null!=room_type && !"".equals(room_type)){
			sql+=" and sr.ROOM_TYPE_ID="+room_type;
		}
		sql+=" limit " + start + "," + number;
		BaseDao base=new BaseDao(SystemInfo.getMainJndi(),null);
		List standardroom=new ArrayList();
        try
        {
        	List list = base.query(sql);
        	if(list!=null){
        		
		        standardroom.add(Common.ConvertEmpty(list));
		       
		        List standardRoomId=new ArrayList();
		        for (int i=0;i<list.size();i++)
	            {
		        	HashMap map=(HashMap) list.get(i);
		        	standardRoomId.add(map.get("STANDARD_ROOM_ID"));
	            }
		        
			    
			    list=getRoomScopeCNT(standardRoomId);
			   
		        List scope_id=new ArrayList();
		        
			    HashMap distinctmap=new HashMap();
		        for (int i=0;i<list.size();i++)
	            {
		        	HashMap map=(HashMap) list.get(i);
		        	distinctmap.put(map.get("SCOPE_ID"),map.get("SCOPE_ID"));
	            }
		        
		        Iterator it=distinctmap.keySet().iterator();
		        while(it.hasNext()){
		        	scope_id.add(distinctmap.get(it.next()));
		        }
		        if(scope_id!=null && scope_id.size()!=0){
		        	
			        sql="select CNT_ID,ROOM_COUNT_MIN,ROOM_COUNT_MAX from room_scope_cnt where  SCOPE_ID in(";
			        for(int i=0;i<scope_id.size();i++){
						if(i!=0){
							sql+=",";
						}
						sql+=scope_id.get(i);
					}
					sql+=") and STANDARD_ROOM_ID in(";
					for(int i=0;i<standardRoomId.size();i++){
						if(i!=0){
							sql+=",";
						}
						sql+=standardRoomId.get(i);
					}
					sql+=")  group by STANDARD_ROOM_ID,SCOPE_ID";
					
					list=base.query(sql);
					List cnts=new ArrayList();
					List cntIds=new ArrayList();
					String[] cnt = null;
					String[] cntid = null;
					int count=0;
					
					
					for(int i=0;i<list.size();i++){
							if(i%scope_id.size()==0){
								if(cnt!=null){
								    cnts.add(cnt);
								    cntIds.add(cntid);
								}
								cntid= new String[scope_id.size()];
								cnt = new String[scope_id.size()];
								count=0;
							}
							HashMap map=(HashMap) list.get(i);
							if(null!=map.get("ROOM_COUNT_MAX")){
								cnt[count]=map.get("ROOM_COUNT_MIN")+"~"+map.get("ROOM_COUNT_MAX");
							}
							else{
								cnt[count]=map.get("ROOM_COUNT_MIN")+"";
							}
							cntid[count]=map.get("CNT_ID")+"";
							count++;
					}
					if(cnt!=null){
					    cnts.add(cnt);
					    cntIds.add(cntid);
					}
					standardroom.add(getRoomScope(scope_id));
					standardroom.add(cnts);
					standardroom.add(cntIds);
		        }
        	}
        }
        catch (Exception e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		return standardroom;
	}
	/**
	 * 
	 * @param standard_id
	 * @return
	 */
	public Integer getStandardRoomTotal(String standard_id,String room_type){
		String sql="select count(distinct sr.STANDARD_ROOM_ID) " 
			+" from standard_room sr,ROOM_TYPE rt"
			+" where sr.VALID_FLAG='A' and sr.ROOM_TYPE_ID=rt.ROOM_TYPE_ID  and sr.STANDARD_ID="+standard_id;
		if(!room_type.isEmpty()){
			sql+=" and sr.ROOM_TYPE_ID="+room_type;
		}
		BaseDao base=new BaseDao(SystemInfo.getMainJndi(),null);
		List standardroom=null;
        try
        {
	        standardroom = base.query(sql);
        }
        catch (Exception e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		HashMap map=(HashMap) standardroom.get(0);
		return Integer.parseInt(map.get("COUNT(DISTINCT SR.STANDARD_ROOM_ID)").toString()) ;
	}
	/**
	 * 
	 * @param standardRoomId
	 * @return
	 */
	public List getRoomScopeCNT(List standardRoomId){
		String sql="select distinct SCOPE_ID from room_scope_cnt where  STANDARD_ROOM_ID in(";
		for(int i=0;i<standardRoomId.size();i++){
			if(i!=0){
				sql+=",";
			}
			sql+=standardRoomId.get(i);
		}
		sql+=")";
		BaseDao base=new BaseDao(SystemInfo.getMainJndi(),null);
		List list=null;
        try
        {
	        list = base.query(sql);
        }
        catch (Exception e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		return list;
	}
	/**
	 * 
	 */
	public List getRoomScope(List scope_id){
		String sql="select SCOPE_ID,SCOPE_NAME from ROOM_SCOPE where VALID_FLAG='A' and SCOPE_ID in(";
		for(int i=0;i<scope_id.size();i++){
			if(i!=0){
				sql+=",";
			}
			sql+=scope_id.get(i);
		}
		sql+=")";
		BaseDao base=new BaseDao(SystemInfo.getMainJndi(),null);
		List list=null;
        try
        {
	        list = base.query(sql);
        }
        catch (Exception e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		return list;
	}
	public String getStandardName(String id) throws Exception{
		BaseDao base=new BaseDao(SystemInfo.getMainJndi(),null);
		String sql="SELECT ROOM_NAME FROM STANDARD_ROOM WHERE VALID_FLAG='A' AND STANDARD_ROOM_ID="+id;
		List list=base.query(sql);
		if(null==list){
			return "";
		}
		HashMap map=(HashMap) list.get(0);
		return map.get("ROOM_NAME").toString();
	}
	/**
	 * 
	 * @param id
	 * @param start
	 * @param number
	 * @return
	 */
	public List getStandardRoomFac(String id,int start,int number){
		BaseDao base=new BaseDao(SystemInfo.getMainJndi(),null);
		String sql="SELECT src.SRFAC_ID,atype.ASSET_TYPE_ID,atype.ASSET_TYPE_NAME,atype.TYPE_RANGE,src.DEVICE_SPECIFY,src.UNIT_NAME,src.DEVICE_COUNT_MIN,src.DEVICE_COUNT_MAX,src.DEVICE_LIFE,src.PRICE,src.REMARK" 
					+"  FROM standard_room_fac src,asset_type atype " 
					+"  where src.ASSET_TYPE_ID=atype.ASSET_TYPE_ID  " 
					+"  and  atype.VALID_FLAG='A' and src.VALID_FLAG='A' "
					+"  and  STANDARD_ROOM_ID="+id;
		sql+=" order by src.SRFAC_ID desc limit " + start + "," + number;
		List list=null;
		try
        {
			list=base.query(sql);
        }
        catch (Exception e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		return Common.ConvertEmpty(list);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Integer getStandardRoomFacTotal(String id){
		BaseDao base=new BaseDao(SystemInfo.getMainJndi(),null);
		String sql="SELECT COUNT(src.SRFAC_ID) " 
					+" FROM standard_room_fac src,asset_type atype where src.ASSET_TYPE_ID=atype.ASSET_TYPE_ID " 
					+"  and  atype.VALID_FLAG='A' and src.VALID_FLAG='A' "
					+"  and  STANDARD_ROOM_ID="+id;
		List list=null;
		try
        {
			list=base.query(sql);
        }
        catch (Exception e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
        HashMap map=(HashMap) list.get(0);
		return Integer.parseInt(map.get("COUNT(SRC.SRFAC_ID)").toString());
	}
	
	

	
	
	
	
	public String getPriceSumtoSRC(String sr_id) throws Exception{
		BaseDao baseDao=new BaseDao(SystemInfo.getMainJndi(),null);
		String sql="SELECT SUM(DEVICE_COUNT_MIN*PRICE) AS SUBTOTAL FROM STANDARD_ROOM_FAC WHERE VALID_FLAG='A' AND STANDARD_ROOM_ID="+sr_id;
		List list=baseDao.query(sql);
		if(list.isEmpty()){
			return "0.00";
		}
		HashMap map=(HashMap) list.get(0);
		if(null==map.get("SUBTOTAL")){
			return "0.00";
		}
		return map.get("SUBTOTAL").toString();
	}
	
}
