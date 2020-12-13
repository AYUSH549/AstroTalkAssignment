package com.developer.ayush.entities;

public class RelationDTO {
	
	private Long parentId;
	private Long Lid;
	private String relation;
	
	
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Long getLid() {
		return Lid;
	}
	public void setLid(Long lid) {
		Lid = lid;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	
	

}
