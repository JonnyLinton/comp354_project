
public class Stock
{
	
	private String stock_name;
	private String stock_ticker;

	//We should use Map<String, Double> = new HashMap<String, Double>()
	// java does not have a Dictionnary
	private Map<String, Double> stock_prices;

	private MovingAverage[] moving_averages;


	public Stock(String stock_name, String stock_ticker, Map<String, Double> stock_prices)
	{
		this.stock_name = stock_name;
		this.stock_ticker = stock_ticker;
		this.stock_prices = stock_prices;
	}

	// getters and setters for stock class
	public String getStock_name() {
		return stock_name;
	}

	public void setStock_name(String stock_name) {
		this.stock_name = stock_name;
	}

	public String getStock_ticker() {
		return stock_ticker;
	}

	public void setStock_ticker(String stock_ticker) {
		this.stock_ticker = stock_ticker;
	}

	public Map<String, Double> getStock_prices() {
		return stock_prices;
	}

	public void setStock_prices(Map<String, Double> stock_prices) {
		this.stock_prices = stock_prices;
	}

	public MovingAverage[] getMoving_averages() {
		return moving_averages;
	}

	public void setMoving_averages(MovingAverage[] moving_averages) {
		this.moving_averages = moving_averages;
	}


}

