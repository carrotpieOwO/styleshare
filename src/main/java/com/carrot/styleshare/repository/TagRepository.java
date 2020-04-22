package com.carrot.styleshare.repository;

import java.util.List;

public interface TagRepository {
	public int write(String tag, int styleId);
	public List<String> findById(int styleId);

}
