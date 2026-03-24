package com.deverytime.model;

public class PlanService {
	
	public PlanDto add() {
		PlanDao dao = new PlanDao();
		
		return dao.add();
	}

}
