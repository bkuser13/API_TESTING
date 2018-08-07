package beans;

import com.google.gson.annotations.SerializedName;

public class Region {

	@SerializedName("region_id")
	private Integer regionId;
	@SerializedName("region_name")
	private String regionName;

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

}
