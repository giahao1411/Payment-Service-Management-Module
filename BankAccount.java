public class BankAccount implements Payment, Transfer
{
    private int stk;
    private double tiLeLaiSuat;
    private double balance;

    //constructor with 2 parameters
    public BankAccount(int stk, double tiLeLaiSuat)
    {
        this.stk = stk;
        this.tiLeLaiSuat = tiLeLaiSuat;
        this.balance = 50.0;
    }

    //get - set stk
    public int getSTK()
    {
        return this.stk;
    }

    public void setSTK(int stk)
    {
        this.stk = stk;
    }

    //get - set tiLeLaiSuat
    public double getTiLeLaiSuat()
    {
        return this.tiLeLaiSuat;
    }

    public void setTiLeLaiSuat(double tiLeLaiSuat)
    {
        this.tiLeLaiSuat = tiLeLaiSuat;
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

    //topUp constructor - nap tien
	public boolean topUp(double amount)
	{
		this.balance = this.balance + amount;
        return true;
	}

    //Payment interface constructors
    public boolean pay(double amount)
    {
        if(this.balance >= amount)
        {
            this.balance = this.balance - amount;
            if(this.balance < 50.0)
            {
                this.balance = this.balance + amount;
                return false;
            }
            return true;
        }
        return false;
    }

    public double checkBalance()
    {
        return this.balance;
    }

    //Transfer interface constructor
    public static final double transferFee = 0.05;

    //transfer money constructor
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

    //toString constructor
    public String toString()
    {
        return this.stk + "," + this.tiLeLaiSuat + "," + checkBalance();
    }
}
