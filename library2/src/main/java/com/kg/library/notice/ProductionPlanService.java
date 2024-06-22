package com.kg.library.notice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kg.library.notice.ProductionPlanMapper;


@Service
public class ProductionPlanService {
	
	@Autowired
	private ProductionPlanMapper productionPlanMapper;

	
	//생산계획 전체 조회
	public List<productionDTO>getProductionAllPlanList(){
		return productionPlanMapper.getProductionAllPlanList();
	}


	public void noticeboard_cal(productionDTO cal) {
		productionPlanMapper.noticeboard_cal(cal);
	};
}