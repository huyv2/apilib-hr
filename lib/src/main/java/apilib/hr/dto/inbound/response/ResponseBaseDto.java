package apilib.hr.dto.inbound.response;

import com.google.gson.annotations.SerializedName;

import apilib.hr.dto.inbound.BaseDto;

public abstract class ResponseBaseDto extends BaseDto {
	@SerializedName("result")
	private BaseResultDto result;
}
