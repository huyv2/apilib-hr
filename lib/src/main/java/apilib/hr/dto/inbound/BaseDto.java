package apilib.hr.dto.inbound;

import com.google.gson.annotations.SerializedName;

public abstract class BaseDto {
	@SerializedName("meta")
	private MetaDto meta = null;

	public MetaDto getMeta() {
		return meta;
	}
	public void setMeta(MetaDto meta) {
		this.meta = meta;
	}
}
