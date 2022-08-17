package apilib.hr.dto.inbound;

import com.google.gson.annotations.SerializedName;

public class MetaDto {
	@SerializedName("source")
	private String source;
	@SerializedName("requestId")
	private String requestId;
	@SerializedName("timestamp")
	private String timestamp;
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
