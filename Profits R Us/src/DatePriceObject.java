
public class DatePriceObject {
	
	// ATTRIBUTES
	String date;
	double closingPrice;
	 
	//METHODS
	public DatePriceObject(){
		this.date = "";
		this.closingPrice = 0;
	}
	   
	public DatePriceObject(String date, double closingPrice){
	   this.date = date;
	   this.closingPrice = closingPrice;
	}
	   
	public void setValues(String date, double closingPrice){
		this.date = date;
		this.closingPrice = closingPrice;
	}
	public void printObject(){
	   System.out.println("Date: " + date + " and closing price of: " + closingPrice);
	}
	public String getDate(){
	   return date;
	}
	public double getPrice(){
	   return closingPrice;
	}
}
