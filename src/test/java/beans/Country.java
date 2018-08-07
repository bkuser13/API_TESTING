package beans;

import com.google.gson.annotations.SerializedName;

public class Country {

	@SerializedName("country_id")
	private String countryId;
	@SerializedName("country_name")
	private String countryName;
	@SerializedName("region_id")
	private Integer regionId;

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

}