package ozden.apps.currency;

public class CurrencyConversion {
	private String sourceCurrencyCode;
	private String destinationCurrencyCode;
	private double sourceRate;
	private double destinationRate;
	private double rate;
	
	protected CurrencyConversion(){
		
	}
	public CurrencyConversion(String sourceCurrencyCode, String destinationCurrencyCode, double sourceRate,
			double destinationRate) {
		super();
		this.sourceCurrencyCode = sourceCurrencyCode;
		this.destinationCurrencyCode = destinationCurrencyCode;
		this.sourceRate = sourceRate;
		this.destinationRate = destinationRate;
	}
	public String getSourceCurrencyCode() {
		return sourceCurrencyCode;
	}
	public void setSourceCurrencyCode(String sourceCurrencyCode) {
		this.sourceCurrencyCode = sourceCurrencyCode;
	}
	public String getDestinationCurrencyCode() {
		return destinationCurrencyCode;
	}
	public void setDestinationCurrencyCode(String destinationCurrencyCode) {
		this.destinationCurrencyCode = destinationCurrencyCode;
	}
	public double getSourceRate() {
		return sourceRate;
	}
	public void setSourceRate(double sourceRate) {
		this.sourceRate = sourceRate;
	}
	public double getDestinationRate() {
		return destinationRate;
	}
	public void setDestinationRate(double destinationRate) {
		this.destinationRate = destinationRate;
	}
	
//	public double getConvRate(){
//		this.rate = this.destinationRate / this.sourceRate;
//		return this.rate;
//	}
	
	public double getRate() {
		this.rate = this.destinationRate / this.sourceRate;
		return this.rate;
	}
	protected void setRate(double rate) {
		this.rate = rate;
	}
	@Override
	public String toString() {
		return "CurrencyConversion [sourceCurrencyCode=" + sourceCurrencyCode + ", destinationCurrencyCode="
				+ destinationCurrencyCode + ", sourceRate=" + sourceRate + ", destinationRate=" + destinationRate
				+ ", rate=" + rate + "]";
	}

	
	
	

}
