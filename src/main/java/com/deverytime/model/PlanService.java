package com.deverytime.model;

import java.util.ArrayList;

public class PlanService {
	
	public ArrayList<PlanDto> list (int memberSeq) {
		PlanDao dao = new PlanDao();
		
		return dao.list(memberSeq);
	}
	
	public PlanDto add() {
		PlanDao dao = new PlanDao();
		
		return dao.add();
	}

}
