public class ConvenientCard implements Payment
{
    private String type;
	private IDCard theDinhDanh;
	private double balance;

	//constructor with one parameter
	public ConvenientCard(IDCard theDinhDanh)
	{
		this.theDinhDanh = theDinhDanh;
		this.balance = 100.0;
	}
	
	//get - set type 
	public String getType() throws CannotCreateCard
	{
		IDCard idc = getTheDinhDanh();
		int age = idc.getAge();
		
		//set type
		if(age <= 18)
		{
			this.type = "Student";
		}
		if(age < 12)
		{
			throw new CannotCreateCard("Not enough age");
		}
		else if(age > 18)
		{
			this.type = "Adult";
		}

		return this.type;
	}

	public void setType(String type)
	{
		this.type = type;	
	}

	//get - set theDinhDanh
	public IDCard getTheDinhDanh()
	{
		return this.theDinhDanh;
	}

	public void setIDCard(IDCard theDinhDanh)
	{
		this.theDinhDanh = theDinhDanh;
	}

	//get - set balance
	public double getBalance()
	{
		return this.balance;
	}

	public void setBalance(double balance)
	{
		this.balance = balance;
	}

	//recharge money constructor - nap tien
	public boolean topUp(double amount)
	{
		this.balance = this.balance + amount;
		return true;
	}

	//Payment interface constructors
	public boolean pay(double amount)
	{
		if(this.type.equals("Adult") && (this.balance >= (amount + amount * 0.01)))
		{
			this.balance = this.balance - (amount + amount * 0.01);
			return true;
		}
		if(this.type.equals("Student") && (this.balance >= amount))
		{
			this.balance = this.balance - amount;
			return true;
		}
		return false;
	}

	public double checkBalance()
	{
		return this.balance;
	}

	//toString constructor
	public String toString()
	{
		return this.theDinhDanh + "," + this.type + "," + checkBalance();
	}
}
