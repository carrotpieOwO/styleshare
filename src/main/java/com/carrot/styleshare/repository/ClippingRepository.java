package com.carrot.styleshare.repository;

import java.util.List;

import com.carrot.styleshare.model.clipping.Clipping;

public interface ClippingRepository {
	public int clipping (int styleId, int userId);
	public Clipping findByUserIdAndstyleID(int styleId, int userId);
	public int unClipping(int id);
	public int clippingCount(int styleId);
	public int myClippingCount(int userId);
	public List<Clipping> findByUserId(int userId);
}
