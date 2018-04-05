package customDatatypes;

public enum EvaluationTypes {
	FULL_CREDIT("FC"),
	FULL_AUDIT("FA"),
	PART_CREDIT("PC"),
	PART_AUDIT("PA");
	
	private String text;

	  EvaluationTypes(String text) {
	    this.text = text;
	  }

	  public String getText() {
	    return this.text;
	  }

	  public static EvaluationTypes fromString(String text) {
	    for (EvaluationTypes b : EvaluationTypes.values()) {
	      if (b.text.equalsIgnoreCase(text)) {
	        return b;
	      }
	    }
	    return null;
	  }
	
}
