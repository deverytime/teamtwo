package com.deverytime.study;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.deverytime.model.MemberDao;
import com.deverytime.model.MemberDto;
import com.deverytime.model.StudyDao;
import com.deverytime.model.StudyDto;
import com.deverytime.model.StudyScheduleDao;
import com.deverytime.model.StudyScheduleDto;

public class StudyService {
	
	public ArrayList<StudyDto> list(HashMap<String, String> map, MemberDto mdto){
		
		StudyDao dao = new StudyDao();
		
		ArrayList<StudyDto> list = dao.list(map, mdto);
		
		if(list == null) return null;
		
		for(StudyDto dto : list) {
			
			if(dto != null) {
				String createDate = dto.getCreateDate();
				
				createDate = createDate.substring(0, 10);
				
				dto.setCreateDate(createDate);
				
				String name = dto.getName();
				
				String desc = dto.getDescription();
				
				name = name.replace("<", "&lt;").replace(">", "&rt;");
				
				if(name.length() > 15) {
					name = name.substring(0, 15) + "...";
				}
				
				dto.setName(name);
				
				if(desc.length() > 30) {
					desc = desc.substring(0, 30) + "...";
				}
				
				dto.setDescription(desc);
			}
			
		}
		
		
		return list;
		
	}

	public int getTotalCountSM(HashMap<String, String> map, MemberDto mdto) {
		
		StudyDao dao = new StudyDao();
		
		return dao.getTotalCountSM(map, mdto);
		
	}
	
	public int getTotalCount(HashMap<String, String> map) {
		
		StudyDao dao = new StudyDao();
		
		return dao.getTotalCount(map);
		
	}

	public StudyDto get(String seq) {
		
		StudyDao dao = new StudyDao();
		
		StudyDto dto = dao.get(seq);
		
		if(dto != null) {
			String createDate = dto.getCreateDate();
			
			createDate = createDate.substring(0, 10);
			
			dto.setCreateDate(createDate);
		}
		
		return dto;
		
	}


	public ArrayList<MemberDto> memberList(String seq, HashMap<String, String> map) {
		
		StudyDao dao = new StudyDao();
		
		ArrayList<MemberDto> list =  dao.memberlist(seq, map);
		
		for(MemberDto mdto : list) {
		
			if(mdto != null) {
				String createDate = mdto.getRegdate();
				
				createDate = createDate.substring(0, 10);
				
				mdto.setRegdate(createDate);
			}
		}
		
		return list;
		
	}

	public int getTotalCountM(String seq) {
		
		StudyDao dao = new StudyDao();
		
		return dao.getTotalCountM(seq);
		
	}

	public int add(StudyDto dto, MemberDto mdto) {
		
		StudyDao dao = new StudyDao();
		
		return dao.add(dto, mdto);
		
	}

	public MemberDto getMember(String id) {
		
		StudyDao dao = new StudyDao();
		
		return dao.getMember(id);
	}

	public ArrayList<StudyDto> mylist(HashMap<String, String> map, MemberDto mdto) {
		
		StudyDao dao = new StudyDao();
		
		ArrayList<StudyDto> list = dao.mylist(map, mdto);
		
		for(StudyDto dto : list) {
			
			String createDate = dto.getCreateDate();
			
			createDate = createDate.substring(0, 10);
			
			dto.setCreateDate(createDate);
			
			String name = dto.getName();
			
			String desc = dto.getDescription();
			
			name = name.replace("<", "&lt;").replace(">", "&rt;");
			
			if(name.length() > 15) {
				name = name.substring(0, 15) + "...";
			}
			
			dto.setName(name);
			
			if(desc.length() > 30) {
				desc = desc.substring(0, 30) + "...";
			}
			
			dto.setDescription(desc);
			
		}
		
		
		return list;
		
	}

	public int regStudy(StudyDto dto, MemberDto mdto) {
		
		StudyDao dao = new StudyDao();
		
		return dao.regStudy(dto, mdto);
	}


	public int unregStudy(MemberDto mdto, String seq) {
		
		StudyDao dao = new StudyDao();
		
		return dao.unregStudy(mdto, seq);
		
	}

	public int isManager(MemberDto mdto, StudyDto dto) {
		
		StudyDao dao = new StudyDao();
		
		return dao.isManager(mdto, dto);
		
	}

	public int edit(StudyDto dto) {
		
		StudyDao dao = new StudyDao();
		
		return dao.edit(dto);
		
	}

	public int del(String seq) {
		
		StudyDao dao = new StudyDao();
		
		return dao.del(seq);
		
	}

	public int isMember(MemberDto mdto, StudyDto dto) {
		
		StudyDao dao = new StudyDao();
		
		return dao.isMember(mdto, dto);
		
	}

	public int deport(String mseq, String seq) {
		
		StudyDao dao = new StudyDao();
		
		return dao.deport(mseq, seq);
		
	}

	public String getManagerSeq(String seq) {
		
		StudyDao dao = new StudyDao();
		
		return dao.getManagerSeq(seq);
		
	}

	public int delegateManager(String seq, String mseq, String managerSeq) {
		
		StudyDao dao = new StudyDao();
		
		return dao.delegateManager(seq, mseq, managerSeq);
	}

	
}
