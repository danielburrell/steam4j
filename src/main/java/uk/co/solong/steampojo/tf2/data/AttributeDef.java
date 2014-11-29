package uk.co.solong.steampojo.tf2.data;

public enum AttributeDef {
	CRAFT_NUMBER(229L), SERIES(187L), GIFTWRAPPED(186L) ;
	
	private Long defindex;

	AttributeDef(Long defindex) {
		this.defindex = defindex;
	}

	public Long getDefindex() {
		return defindex;
	}

	public void setDefindex(Long defindex) {
		this.defindex = defindex;
	}

}
