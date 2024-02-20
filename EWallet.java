public class EWallet implements Payment, Transfer 
{
	private int phoneNum;
	private double balance;

	//constructor with one parameter
	public EWallet(int phoneNum)
	{
		this.phoneNum = phoneNum;
		this.balance = 0.0;
	}

	//get - set phoneNum
	public int getPhoneNum()
	{
		return this.phoneNum;
	}

	public void setPhoneNum(int phoneNum)
	{
		this.phoneNum = phoneNum;
	}

	//get - set balance
	public double getBalance()
	{
		return this.balance;
	}

	public void setPhoneNum(double balance)
	{
		this.balance = balance;
	}

	//topUp constructor - nap tien
	public boolean topUp(double amount)
	{
		this.balance = this.balance + amount;
		return true;
	}

	//Transfer interface constructors
	public static final double transferFee = 0.05;

	//transfer money constructor - chuyen tien
    public boolean transfer (double amount, Transfer to)
	{
		if((this.balance >= (amount + amount * transferFee)) && (to instanceof EWallet))
		{
			this.balance = this.balance - amount * transferFee - amount;
			//if balance after transfer less than 50.0 -> stop transfer and give back money
			if(this.balance < 50.0)
			{
				this.balance = this.balance + amount * transferFee + amount;
				return false;
			}
			//continue if true
			else 
			{
				((EWallet) to).topUp(amount);
				return true;
			}
		}
		else if((this.balance >= (amount + amount * transferFee)) && (to instanceof BankAccount))
		{
			this.balance = this.balance - amount * transferFee - amount;
			((BankAccount) to).topUp(amount);
			return true;
		}
		return false;
	}

    public double checkBalance()
	{
		return this.balance;
	}

	//Payment interface constructors
	public boolean pay(double amount)
	{
		if(this.balance >= amount)
		{
			this.balance = this.balance - amount;
			return true;
		}
		return false;
	}

	//toString constructor
	public String toString()
	{
		return this.phoneNum + "," + checkBalance();
	}
}
