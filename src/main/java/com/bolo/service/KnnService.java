package com.bolo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolo.dao.LocationMapper;
import com.bolo.dao.WifiinfoMapper;
import com.bolo.entity.Location;
import com.bolo.entity.Mylocaltion;
import com.bolo.entity.Wifiinfo;
import com.bolo.entity.Wifiinfoavg;
import com.bolo.util.TxtRwriteFile;
import com.google.gson.Gson;
@Service
public class KnnService {
	@Autowired
	private LocationMapper locationMapper;
	@Autowired
	private WifiinfoMapper wifiinfoMapper;
	public LocationMapper getLocationMapper() {
		return locationMapper;
	}

	public void setLocationMapper(LocationMapper locationMapper) {
		this.locationMapper = locationMapper;
	}
	public WifiinfoMapper getWifiinfoMapper() {
		return wifiinfoMapper;
	}

	public void setWifiinfoMapper(WifiinfoMapper wifiinfoMapper) {
		this.wifiinfoMapper = wifiinfoMapper;
	}
	/**
	 * 定位方法函数1入口（最近邻）
	 * @param strJon待测点wifi集合json
	 * @return
	 */
	@Transactional
	public String getMylocaltion(String strJon){
		Gson gson=new Gson(); 
		Mylocaltion local=gson.fromJson(strJon, Mylocaltion.class);
		System.out.println(strJon);
		List<List<Wifiinfo>> wifiinfosList=local.getWifiinfos();
		System.out.println(wifiinfosList.size());
		Map<String,Double> mywifilevel= getAvgWifiinfo(wifiinfosList);
		String[] xy=getMylocaltion("1",mywifilevel);
		if(xy!=null&&xy.length>0){
			return xy[0]+" "+xy[1];
		}else{
			return "定位失败！";
		}
	}
	/**
	 * 待测点各bssid的均值
	 * @param wifiinfosList待测点手机的wifi集合
	 * @return
	 */
	public Map<String,Double> getAvgWifiinfo(List<List<Wifiinfo>> wifiinfosList){
		Map<String,Double> wlevelSumMap=new HashMap<String,Double>();
		Map<String,Double> wlevelCountMap=new HashMap<String,Double>();
		Map<String,Double> avgWlevelMap=new HashMap<String,Double>();
		for(int i=0;i<wifiinfosList.size();i++){
			List<Wifiinfo> wifiinfos=wifiinfosList.get(i);
			for(int j=0;j<wifiinfos.size();j++){
				Wifiinfo wf=wifiinfos.get(j);
				String bssid=wf.getBssid();
				double wlevel=wf.getWlevel();
				if(wlevelSumMap.containsKey(bssid)&&wlevelCountMap.containsKey(bssid)){
					Double sum=wlevelSumMap.get(bssid);
					Double count=wlevelCountMap.get(bssid);
					sum+=wlevel;
					count++;
					wlevelSumMap.put(bssid, sum);
					wlevelCountMap.put(bssid, count);
					avgWlevelMap.put(bssid, (double)sum/count);
				}else{
					wlevelSumMap.put(bssid, wlevel);
					wlevelCountMap.put(bssid, 1.0);
					avgWlevelMap.put(bssid, (double)wlevel);
				}
			}
		}
		return avgWlevelMap;
	}
	/**
	 * 根据以下例子最近邻算法计算
	 * @param cindex 指纹点的组号，目前所有的指纹点组号为1
	 * @param mywifilevel 待测点的wifi强度信息
	 * @return
	 *  Customer     Age     Income     Purchased Product
		1            45       46k       Book
		2            39       100k      TV
		3            35       38k       DVD
		4            69       150k      Car Cover
		5            58       51k       ???
				
		Step 1:  Determine Distance Formula
		Distance = SQRT( ((58 - Age)/(69-35))^2) + ((51000 - Income)/(150000-38000))^2 )
		
		Step 2:  Calculate the Score
		Customer     Score     Purchased Product
		1            .385         Book
		2            .710         TV
		3            .686         DVD
		4            .941         Car Cover
		5            0.0          ???
	 */
	public String[] getMylocaltion(String cindex,Map<String,Double> mywifilevel){
		//获取所有指纹点的各wifi强度（已为均值）
		List<Wifiinfoavg> Wvgslist=wifiinfoMapper.gettAvgByCindex(cindex);
		if(Wvgslist==null||Wvgslist.size()==0){
			wifiinfoMapper.insertWifiinfoAvg(cindex);
			Wvgslist=wifiinfoMapper.gettAvgByCindex(cindex);
		}
		//获取各wifi强度的最大值减去最小值
		List<Map> maxAndmin=wifiinfoMapper.getMaxAndMinByCindex(cindex);
		Map<String,Double> maxAndminMinMap=new HashMap<String,Double>();
		for (int i = 0; i < maxAndmin.size(); i++ ) {
			Map map = (Map) maxAndmin.get(i);
	    	String bssid = (String) map.get("bssid");
            double maxlevel = Double.parseDouble( map.get("maxlevel").toString());
            double minlevel = Double.parseDouble( map.get("minlevel").toString());
            maxAndminMinMap.put(bssid,(maxlevel-minlevel));
	    }
		//获取各bssid的数目，数目越小说明影响力越小
		List<Map> bssidCount=wifiinfoMapper.getBssidCountByCindex(cindex);
		Map<String,Double> bssidCountMap=new HashMap<String,Double>();
		for(int i = 0; i < bssidCount.size(); i++){
			Map map = (Map) bssidCount.get(i);
			bssidCountMap.put(map.get("bssid").toString(),Double.parseDouble( map.get("NUM").toString()));
		}
		String oldx="";
		String oldy="";
		double mindis=999999999;//预设最小距离得分为尽量大的数
		double disSumPow=0;
		String min_x="";
		String min_y="";
		String currentX="";
		String currentY="";
		if(Wvgslist!=null&&Wvgslist.size()>0){
			oldx=Wvgslist.get(0).getX();
			oldy=Wvgslist.get(0).getY();
		}
		for(int i=0;i<Wvgslist.size();i++){
			Wifiinfoavg wavg=Wvgslist.get(i);
			currentX=wavg.getX();
			currentY=wavg.getY();
			//当x与y其中一个变化时，应完成一个指纹点的距离得分计算
			if(!currentX.equals(oldx)||!currentY.equals(oldy)){
				double dis=Math.sqrt(disSumPow);
				if(dis<mindis){
					mindis=dis;
					min_x=oldx;
					min_y=oldy;
				}
				oldx=currentX;
				oldy=currentY;
				disSumPow=0;
			}
			if(currentX.equals(oldx)&&currentY.equals(oldy)){
				String bssid=wavg.getBssid();
				//排除指纹点中bssid总数少于5个的
				if(mywifilevel.containsKey(bssid)&&bssidCountMap.get(bssid)>3){
					double onessid=(double)(mywifilevel.get(bssid)-wavg.getWlevel())/maxAndminMinMap.get(bssid);
					disSumPow+=Math.pow(onessid, 2);
				}
			}
		}
		//对最后一个指纹点进行计算
		double dis=Math.sqrt(disSumPow);
		if(dis<mindis){
			mindis=dis;
			min_x=oldx;
			min_y=oldy;
		}
		return new String[]{min_x,min_y};
	}
	
	/**
	 * 定位方法函数2入口（权重）
	 * @param strJon待测点wifi集合json
	 * @return
	 */
	public String getMylocaltionWithValue(String strJon){
		Gson gson=new Gson(); 
		Mylocaltion local=gson.fromJson(strJon, Mylocaltion.class);
		System.out.println(strJon);
		List<List<Wifiinfo>> wifiinfosList=local.getWifiinfos();
		Map<String,Double> mywfAvg=getAvgWifiinfo(wifiinfosList);
		System.out.println(wifiinfosList.size());
		String xy="";
		if(wifiinfosList.size()>0){
			List<Wifiinfo> mywifiList =changeToList(mywfAvg);
			if(mywifiList.size()<=0) return "待测点无数据！";
			quickSortDse(mywifiList,0,mywifiList.size()-1);
			List<Map> wvgList=getWifiavgMapList("1",mywifiList);
			//List<Map> wifiavgs=getLast(5,5,wvgList,mywifiList);
			List<Map> wifiavgs=getLast2(1.2,1.2,wvgList,mywifiList);
			if(wifiavgs.size()>0) xy=wifiavgs.get(0).get("x")+" "+wifiavgs.get(0).get("y");
		}		
		return xy;
	}
	public List<Map> getWifiavgMapList(String cindex,List<Wifiinfo> mywifiList){
		List<Wifiinfoavg> wvgs=null;
		for(int i=0;i<mywifiList.size();i++){
			Map<String, Object> prams=new HashMap<String, Object>();
			prams.put("cindex", 1);
			prams.put("bssid", mywifiList.get(i).getBssid());
			prams.put("wlevelmin", mywifiList.get(i).getWlevel()-20);
			prams.put("wlevelmax", mywifiList.get(i).getWlevel()+20);
		    wvgs=wifiinfoMapper.getWifiAvgWithValue(prams);
			if(wvgs.size()>0){
				break;
			}
		}
		String oldx="";
		String oldy="";
		Map<String,Object> wvgmap=new HashMap<String,Object>();
		List<Map> wvgList=new ArrayList<Map>();
		if(wvgs!=null&&wvgs.size()>0){
			oldx=wvgs.get(0).getX();
			oldy=wvgs.get(0).getY();
		}
		for(int i=0;i<wvgs.size();i++){
			String currentx=wvgs.get(i).getX();
			String currenty=wvgs.get(i).getY();
			if(!oldx.equals(currentx)||!oldy.equals(currenty)){
				if(wvgmap!=null){
					wvgmap.put("x", oldx);
					wvgmap.put("y", oldy);
					wvgList.add(wvgmap);
				}	
				oldx=currentx;
				oldy=currenty;
				wvgmap=new HashMap<String,Object>();	
			}else{
				wvgmap.put(wvgs.get(i).getBssid(), wvgs.get(i).getWlevel());
			}
		}
		if(wvgs!=null&&wvgs.size()>0){
			wvgList.add(wvgmap);
			wvgmap.put("x", oldx);
			wvgmap.put("y", oldy);
		}
		return wvgList;
	}
	/**
	 * 不在选择区间则排除掉
	 * @param addWelevel
	 * @param minWelevel
	 * @param wifiavgs
	 * @param mywifiList
	 * @return
	 */
	public List<Map> getLast(double addWelevel,double minWelevel,List<Map> wifiavgs,List<Wifiinfo> mywifiList){
		for(int i=0;i<mywifiList.size();i++){
			Wifiinfo wf=mywifiList.get(i);
			String bssid=wf.getBssid();
			for(int j=0;j<wifiavgs.size();j++){
				Map<String,Object> avgmap=wifiavgs.get(j);
				if(avgmap.containsKey(bssid)){
					double mywlevel=wf.getWlevel();
					double avgwlevel=Double.parseDouble(avgmap.get(bssid).toString());
					if((mywlevel-minWelevel)>avgwlevel||avgwlevel>(mywlevel+addWelevel)){
						wifiavgs.remove(j);
						j--;
					}
				}	
			}
			if(wifiavgs.size()==1){
				break;
			}
		}
		return wifiavgs;
	}
	/**
	 * 对待测点做选择区间
	 * @param addWelevel
	 * @param minWelevel
	 * @param wifiavgs
	 * @param mywifiList
	 * @return
	 */
	public List<Map> getLast2(double addWelevel,double minWelevel,List<Map> wifiavgs,List<Wifiinfo> mywifiList){		
			for(int j=0;j<wifiavgs.size();j++){
				Map<String,Object> avgmap=wifiavgs.get(j);
				int score=0;
				for(int i=0;i<mywifiList.size();i++){
					Wifiinfo wf=mywifiList.get(i);
					String bssid=wf.getBssid();
					if(avgmap.containsKey(bssid)){
						double mywlevel=wf.getWlevel();
						double avgwlevel=Double.parseDouble(avgmap.get(bssid).toString());
						if((mywlevel-minWelevel)<avgwlevel&&avgwlevel<(mywlevel+addWelevel)){
							score++;
						}
					}	
				}
				avgmap.put("score", score);
			}
			quickMapSortDse(wifiavgs,0,wifiavgs.size()-1);
			return wifiavgs;
	}
	/**
	 * 对指纹点做选择区间
	 * @param addWelevel
	 * @param minWelevel
	 * @param wifiavgs
	 * @param mywifiList
	 * @return
	 */
	public List<Map> getLast3(double addWelevel,double minWelevel,List<Map> wifiavgs,List<Wifiinfo> mywifiList){		
		for(int j=0;j<wifiavgs.size();j++){
			Map<String,Object> avgmap=wifiavgs.get(j);
			int score=0;
			for(int i=0;i<mywifiList.size();i++){
				Wifiinfo wf=mywifiList.get(i);
				String bssid=wf.getBssid();
				if(avgmap.containsKey(bssid)){
					double mywlevel=wf.getWlevel();
					double avgwlevel=Double.parseDouble(avgmap.get(bssid).toString());
					if(mywlevel>(avgwlevel-minWelevel)&&(avgwlevel+addWelevel)>mywlevel){
						score++;
					}
				}	
			}
			avgmap.put("score", score);
		}
		quickMapSortDse(wifiavgs,0,wifiavgs.size()-1);
		return wifiavgs;
}
	/**
	 * 快速排序升序
	 * @param wifiinfoList
	 * @param start
	 * @param end
	 */
	public void quickSortAsc(List<Wifiinfo> wifiinfoList,int start, int end){
		int i,j;
        i = start;
        j = end;
        if((wifiinfoList==null)||(wifiinfoList.size()==0))
            return;
        while(i<j){
            while(i<j&&wifiinfoList.get(i).getWlevel()<=wifiinfoList.get(j).getWlevel()){     //以数组start下标的数据为key，右侧扫描
                j--;
            }
            if(i<j){                   //右侧扫描，找出第一个比key小的，交换位置
            	Wifiinfo temp = wifiinfoList.get(i);
            	wifiinfoList.set(i, wifiinfoList.get(j));
                wifiinfoList.set(j,temp);
            }
             while(i<j&&wifiinfoList.get(i).getWlevel()<wifiinfoList.get(j).getWlevel()){    //左侧扫描（此时a[j]中存储着key值）
                i++;
              }
            if(i<j){                 //找出第一个比key大的，交换位置
            	Wifiinfo temp = wifiinfoList.get(i);
            	wifiinfoList.set(i, wifiinfoList.get(j));
                wifiinfoList.set(j,temp);
            }
       }
       if(i-start>1){
            //递归调用，把key前面的完成排序
    	   quickSortAsc(wifiinfoList,start,i-1);
       }
       if(end-j>1){
    	   quickSortAsc(wifiinfoList,j+1,end);//递归调用，把key后面的完成排序
       }
	}
	/**
	 * 快速排序降序
	 * @param wifiinfoList
	 * @param start
	 * @param end
	 */
	public void quickSortDse(List<Wifiinfo> wifiinfoList,int start, int end){
		int i,j;
        i = start;
        j = end;
        if((wifiinfoList==null)||(wifiinfoList.size()==0))
            return;
        while(i<j){
        	while(i<j&&wifiinfoList.get(i).getWlevel()>wifiinfoList.get(j).getWlevel()){    //左侧扫描（此时a[j]中存储着key值）
                 i++;
                 //j--;
               }
            if(i<j){                 //找出第一个比key大的，交换位置
             	Wifiinfo temp = wifiinfoList.get(i);
             	wifiinfoList.set(i, wifiinfoList.get(j));
                 wifiinfoList.set(j,temp);
            }
        	while(i<j&&wifiinfoList.get(i).getWlevel()>=wifiinfoList.get(j).getWlevel()){     //以数组start下标的数据为key，右侧扫描
                j--;
               // i++;
            }
            if(i<j){                   //右侧扫描，找出第一个比key小的，交换位置
            	Wifiinfo temp = wifiinfoList.get(i);
            	wifiinfoList.set(i, wifiinfoList.get(j));
                wifiinfoList.set(j,temp);
            }
            
       }
       if(i-start>1){
            //递归调用，把key前面的完成排序
    	   quickSortDse(wifiinfoList,start,i-1);
       }
       if(end-j>1){
    	   quickSortDse(wifiinfoList,j+1,end);//递归调用，把key后面的完成排序
       }
	}
	/**
	 * 快速排序降序
	 * @param wifiinfoList
	 * @param start
	 * @param end
	 */
	public void quickMapSortDse(List<Map> wifiinfoMapList,int start, int end){
		int i,j;
        i = start;
        j = end;
        if((wifiinfoMapList==null)||(wifiinfoMapList.size()==0))
            return;
        while(i<j){
        	while(i<j&&Integer.parseInt(wifiinfoMapList.get(i).get("score").toString())>Integer.parseInt(wifiinfoMapList.get(j).get("score").toString())){    //左侧扫描（此时a[j]中存储着key值）
                 i++;
                 //j--;
               }
            if(i<j){                 //找出第一个比key大的，交换位置
             	 Map temp = wifiinfoMapList.get(i);
             	 wifiinfoMapList.set(i, wifiinfoMapList.get(j));
             	 wifiinfoMapList.set(j,temp);
            }
        	while(i<j&&Integer.parseInt(wifiinfoMapList.get(i).get("score").toString())>=Integer.parseInt(wifiinfoMapList.get(j).get("score").toString())){     //以数组start下标的数据为key，右侧扫描
                j--;
               // i++;
            }
            if(i<j){                   //右侧扫描，找出第一个比key小的，交换位置
            	Map temp = wifiinfoMapList.get(i);
            	wifiinfoMapList.set(i, wifiinfoMapList.get(j));
            	wifiinfoMapList.set(j,temp);
            }
            
       }
       if(i-start>1){
            //递归调用，把key前面的完成排序
    	   quickMapSortDse(wifiinfoMapList,start,i-1);
       }
       if(end-j>1){
    	   quickMapSortDse(wifiinfoMapList,j+1,end);//递归调用，把key后面的完成排序
       }
	}
	/**
	 * 将wifi的map转化为list
	 * @param map
	 * @return
	 */
	public List<Wifiinfo> changeToList( Map<String,Double> map){
		Iterator iter = map.entrySet().iterator(); 
		List<Wifiinfo> wifilist=new ArrayList<Wifiinfo>();
		while (iter.hasNext()) { 
			Wifiinfo wf=new Wifiinfo();
			Map.Entry entry = (Map.Entry) iter.next(); 
		    Object key = entry.getKey(); 
		    Object val = entry.getValue();
		    wf.setBssid(key.toString());
		    wf.setWlevel(Double.parseDouble(val.toString()));
		    wifilist.add(wf);
		}
		return wifilist;
	}
}
