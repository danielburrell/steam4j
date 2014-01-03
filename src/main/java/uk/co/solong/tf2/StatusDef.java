package uk.co.solong.tf2;

public enum StatusDef {
	SUCCESS(1L), MISSING_INVALID_ID(8L), PRIVATE(15L), NO_SUCH_ID(18L);

	private Long value;

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	StatusDef(Long value) {
		this.value = value;
	}

}
