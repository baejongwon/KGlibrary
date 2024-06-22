package com.kg.library.notice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/*
  CREATE TABLE production_plan (
    production_plan_code VARCHAR2(50),
    product_code VARCHAR2(50),
    charge_employee_code VARCHAR2(50),
    production_plan_quantity NUMBER,
    production_plan_qantity_unit VARCHAR2(50),
    expected_production_start_date DATE,
    expected_production_end_date DATE
);

CREATE TABLE product_code_detail (
    product_code VARCHAR2(50),
    gender_categorized_name VARCHAR2(50),
    detailed_categorized_name VARCHAR2(50)
);

CREATE TABLE employee_info (
    employee_code VARCHAR2(50),
    employee_name VARCHAR2(50)
);

-- product_code_detail 테이블에서 product_code 열을 기본 키로 설정
ALTER TABLE product_code_detail
ADD CONSTRAINT pk_product_code
PRIMARY KEY (product_code);

-- employee_info 테이블에서 employee_code 열을 기본 키로 설정
ALTER TABLE employee_info
ADD CONSTRAINT pk_employee_code
PRIMARY KEY (employee_code);

ALTER TABLE production_plan
ADD CONSTRAINT fk_product_code
FOREIGN KEY (product_code)
REFERENCES product_code_detail(product_code);

ALTER TABLE production_plan
ADD CONSTRAINT fk_employee_code
FOREIGN KEY (charge_employee_code)
REFERENCES employee_info(employee_code);
 
*/
@Mapper
public interface ProductionPlanMapper {
	//생산계획 전체 조회
	public List<productionDTO> getProductionAllPlanList();

	public void noticeboard_cal(productionDTO cal);	

}
