package com.kg.library.notice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/production")
public class ProductionController {	

	private static final Logger log = LoggerFactory.getLogger(ProductionController.class);
	
	private final ProductionPlanService productionPlanService;
	
	public ProductionController(ProductionPlanService productionPlanService) {
		this.productionPlanService = productionPlanService;
	}

@RequestMapping(value="/monthPlan", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray monthPlan() {
		List<productionDTO> list = productionPlanService.getProductionAllPlanList();
		
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		HashMap<String, Object> hash = new HashMap<String, Object>();		
		for(int i=0; i < list.size(); i++) {			
			hash.put("title", list.get(i).getEvents()); //제목
			hash.put("start", list.get(i).getStartDate()); //시작일자
			hash.put("end", list.get(i).getEndDate()); //종료일자
			
			jsonObj = new JSONObject(hash); //중괄호 {key:value , key:value, key:value}
			jsonArr.add(jsonObj); // 대괄호 안에 넣어주기[{key:value , key:value, key:value},{key:value , key:value, key:value}]
		}
		
		log.info("jsonArrCheck: {}", jsonArr);
		
		return jsonArr;
	}
}