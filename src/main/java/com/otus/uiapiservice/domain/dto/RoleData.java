package com.otus.uiapiservice.domain.dto;

import java.io.Serializable;
import java.util.Objects;

public class RoleData implements Serializable {

	private Long id;
	private String name;

	public RoleData() {
	}

	public RoleData(Long id) {
		this.id = id;
		this.name = id.toString();
	}

	public RoleData(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RoleData roleData = (RoleData) o;
		return Objects.equals(id, roleData.id) && Objects.equals(name, roleData.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}
}
