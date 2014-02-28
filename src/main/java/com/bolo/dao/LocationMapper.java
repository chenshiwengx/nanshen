package com.bolo.dao;

import com.bolo.entity.Location;

public interface LocationMapper {
	public void insertOne(Location location);
	//public void updateByBid(String bid, int avgLevel);
	public void update(Location location);
	public Location getByBid(String bid);
}
