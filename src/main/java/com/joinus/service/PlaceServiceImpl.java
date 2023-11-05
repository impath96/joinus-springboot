package com.joinus.service;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.joinus.domain.LocationCityVo;
import com.joinus.domain.PlacesVo;
import com.joinus.persistence.PlaceDao;

@Service
public class PlaceServiceImpl implements PlaceService{
	
	private static final Logger log = LoggerFactory.getLogger(PlaceServiceImpl.class);

	@Inject
	private PlaceDao dao;
	
	// 시설 목록(비제휴)
	@Override
	public List<PlacesVo> getPlaceList() {
		return dao.getPlaceList();
	}

	// 부산 (구) 리스트
	@Override
	public List<LocationCityVo> getBusanGuList() {
		return dao.getBusanGuList();
	}

	// (구) 별 제휴시설리스트	
	@Override
	public List<PlacesVo> getCityPlaceList(String place_address) {
		return dao.getCityPlaceList(place_address);
	}
	// 제휴시설 상세보기
	@Override
	public PlacesVo getPlaceContent(int place_no) {
		return dao.getPlaceContent(place_no);
	}

	
/////////////////////////////////////클래스 리스트 크롤링//////////////////////////////
	@Override
	public JSONArray classList() {
		
		log.info("원데이 클래스 목록 불러오기");

		String url = "https://www.umclass.com/plan/28?page=1";
		Document doc = null;

		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		log.info(doc.toString());
		
		Elements class_title = doc.select(".classPlan-contents-list .listitempage .classPlan-view.class-list-view.event-scroll span.plan-item-name");
		Elements class_image = doc.select(".classPlan-contents-list .listitempage .classPlan-view.class-list-view.event-scroll div.class-list-img-height.classPlan-lazy");
		Elements class_category = doc.select(".classPlan-contents-list .listitempage .classPlan-view.class-list-view.event-scroll .plan-sub-info span:nth-child(2)");
		Elements class_price = doc.select(".classPlan-contents-list .listitempage .classPlan-view.class-list-view.event-scroll .plan-area-mt > div > span:nth-child(2)");
		Elements class_number = doc.select(".classPlan-contents-list .listitempage .classPlan-view.class-list-view.event-scroll a");
		
		JSONArray classList = new JSONArray();

		for (int i = 0; i < class_title.size(); i++) {
			JSONObject obj = new JSONObject();

			obj.put("class_title", class_title.get(i).text());
			obj.put("class_image", class_image.get(i).attr("data-original"));
			obj.put("class_category", class_category.get(i).text());
			obj.put("class_price", class_price.get(i).text().substring(0,2)+class_price.get(i).text().substring(3,6));
			obj.put("class_number", class_number.get(i).attr("abs:href").substring(34));
//			log.info("obj: "+obj);
			
			classList.add(obj); 
		}
//		log.info("class_title: "+class_title);
//		log.info("class_image: "+class_image);
//		log.info("class_category: "+class_category);
//		log.info("class_price: "+class_price);
//		log.info("class_number: "+class_number);
		
		log.info("classList: "+classList);
		
		return classList;
	}
/////////////////////////////////////클래스 리스트 크롤링//////////////////////////////
	
	
/////////////////////////////////////클래스 크롤링//////////////////////////////
//	@Override
//	public JSONArray classContent() {
//		
//		log.info("원데이 클래스 목록 불러오기");
//		
//		int number = 0;
//		String url = "https://www.umclass.com/classInfo/"+number;
//		Document doc = null;
//		
//		try {
//			doc = Jsoup.connect(url).get();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
////		log.info(doc.toString());
//		
//		Elements class_title = doc.select(".classPlan-contents-list .listitempage .classPlan-view.class-list-view.event-scroll span.plan-item-name");
//		Elements class_image = doc.select(".classPlan-contents-list .listitempage .classPlan-view.class-list-view.event-scroll div.class-list-img-height.classPlan-lazy");
//		Elements class_category = doc.select(".classPlan-contents-list .listitempage .classPlan-view.class-list-view.event-scroll .plan-sub-info span:nth-child(2)");
////		Elements class_address = doc.select(".classPlan-contents-list .listitempage .classPlan-view.class-list-view.event-scroll");
//		Elements class_price = doc.select(".classPlan-contents-list .listitempage .classPlan-view.class-list-view.event-scroll .plan-area-mt > div > span:nth-child(2)");
//		Elements class_number = doc.select(".classPlan-contents-list .listitempage .classPlan-view.class-list-view.event-scroll .plan-area-mt > div > span:nth-child(2)");
//		
//		
//		JSONArray classContent = new JSONArray();
//		
//		for (int i = 0; i < class_title.size(); i++) {
//			JSONObject obj = new JSONObject();
//			
//			obj.put("class_title", class_title.get(i).text());
//			obj.put("class_image", class_image.get(i).attr("data-original"));
//			obj.put("class_category", class_category.get(i).text());
////			obj.put("class_address", class_address.get(i).text());
//			obj.put("class_price", class_price.get(i).text().substring(2)+class_price.get(i).text().substring(6));
//			obj.put("class_number", class_number.get(i).text().substring(34));
//			log.info("obj: "+obj);
//			
//			classContent.add(obj);
//		}
//		log.info("class_title: "+class_title);
//		log.info("class_image: "+class_image);
//		log.info("class_category: "+class_category);
////		log.info("class_address: "+class_address);
//		log.info("class_price: "+class_price);
//		log.info("class_number: "+class_number);
//		
//		log.info("classContent: "+classContent);
//		
//		return classContent;
//	}
/////////////////////////////////////클래스 크롤링//////////////////////////////

	

}