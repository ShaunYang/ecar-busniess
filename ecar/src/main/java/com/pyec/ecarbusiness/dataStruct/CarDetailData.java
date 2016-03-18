package com.pyec.ecarbusiness.dataStruct;

import java.util.ArrayList;
/**
 * 车辆详情界面
 * @author Administrator
 *
 */
public class CarDetailData {

	public String start_time_str; //拍卖开始时间串

	public String attend_time; //出价时间
	
	public ArrayList<CarBaseImg> img = new ArrayList<CarBaseImg>();//图片

	public ArrayList<CarCheckData> check_item_info = new ArrayList<CarCheckData>(); //检测项信息
	
	public CarBaseInfo check_conf_info = new CarBaseInfo(); //车辆配置
	
	public String is_give_price;//是否出价
	
	public String final_price;//成交价
	
	public String my_price;//我的出价
	
	public String status;//状态
	
	public String start_time;//拍卖开始时间
	
	public CarCheckInfo check_info = new CarCheckInfo(); //检测信息
	
	public String is_vip;//是否VIP
	
	public String now;//服务器时间截
	
	public String service_fee;//服务费
	
	public String title;//标题
	
	public String end_time;//拍卖结束时间
}
