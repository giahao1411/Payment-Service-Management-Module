import java.util.*;
import java.io.*;

public class TransactionProcessing 
{
    private ArrayList<Payment> paymentObjects;
    private IDCardManagement idcm;
    
    public TransactionProcessing(String idCardPath, String paymentPath) 
    {
        idcm = new IDCardManagement(idCardPath);
        readPaymentObject(paymentPath);
    }

    public ArrayList<Payment> getPaymentObject() 
    {
        return this.paymentObjects;
    }

    // Requirement 3 - temporary fine
    public boolean readPaymentObject(String path) 
    {
        //get ArrayList<IDCard> from idcm
        ArrayList<IDCard> idcs = idcm.getIDCards();

        //try - catch read file
        try 
        {
            File readPayInf = new File(path);
            paymentObjects = new ArrayList<Payment>();
            Scanner readFile = new Scanner(readPayInf);
            while(readFile.hasNextLine())
            {
                String data = readFile.nextLine();
                String[] splitData = data.split(",");

                //case ConvenientCard and EWallet
                if(splitData.length == 1)
                {
                    //case ConvenientCard if the age > 12 
                    if(data.length() == 6)
                    {
                        //try - catch getType() in ConvenientCard for exception CannotCreateCard
                        try
                        {
                            //usinh ArrayList idcs to compare the data iCode
                            for(IDCard idc : idcs)
                            {
                                if(idc.getICode() == Integer.valueOf(data))
                                {
                                    ConvenientCard cc = new ConvenientCard(idc);
                                    cc.getType();
                                    paymentObjects.add((Payment) cc);
                                }
                            }
                        }
                        catch (CannotCreateCard e)
                        {
                            System.out.println("Not enough age");
                        }
                    }
                    //case EWallet
                    if(data.length() == 7)
                    {
                        EWallet ew = new EWallet(Integer.valueOf(data));
                        paymentObjects.add((Payment) ew);
                    }
                }
                //case BankAccount
                if(splitData.length == 2)
                {
                    BankAccount ba = new BankAccount(Integer.valueOf(splitData[0]), Double.valueOf(splitData[1]));
                    paymentObjects.add((Payment) ba);
                }
            }
            //close Scanner readFile
            readFile.close();

            return true;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    // Requirement 4 - temporary fine
    public ArrayList<ConvenientCard> getAdultConvenientCards() 
    {
        //initiate ArrayList<ConvenientCard> -> return this ArrayList for result
        ArrayList<ConvenientCard> convenientCardlst = new ArrayList<ConvenientCard>();

        //using for - each loop in paymentObject to check for the type of card
        for(Payment obj : paymentObjects)
        { 
            //if true
            if(obj instanceof ConvenientCard)
            {
                //try - catch statement to control CannotCreateCard exception
                try
                {   
                    //the obj call funct getType() to check if its type is "Adult" or not
                    if(((ConvenientCard) obj).getType().equals("Adult"))
                    {
                        //add to ArrayList if it's true
                        convenientCardlst.add((ConvenientCard) obj);
                    }
                }
                catch (CannotCreateCard e)
                {
                    System.out.println("Not enough age");
                }
            }
        }
        return convenientCardlst;
    }

    // Requirement 5 - temporary fine
    public ArrayList<IDCard> getCustomersHaveBoth() 
    {
        //initiate ArrayList<IDCard> -> return this ArrayList
        ArrayList<IDCard> idcsList = new ArrayList<IDCard>();

        //initiate ArrayList<ConvenientCard> to sort again 
        ArrayList<ConvenientCard> conCardsList = new ArrayList<ConvenientCard>();

        //get ArrayList<IDCard> from idcm
        ArrayList<IDCard> idCards = idcm.getIDCards();

        //for - each loop to check if customers have both CC, EW, BA
        for(Payment obj : paymentObjects)
        {
            if(obj instanceof ConvenientCard)
            {
                IDCard idc = ((ConvenientCard) obj).getTheDinhDanh();
                int iCode = idc.getICode();
                int phoneNum = idc.getPhoneNum();
                for(Payment obj1 : paymentObjects)
                {
                    if(obj1 instanceof EWallet)
                    {
                        if(((EWallet) obj1).getPhoneNum() == phoneNum)
                        {
                            for(Payment obj2 : paymentObjects)
                            {
                                if(obj2 instanceof BankAccount)
                                {
                                    if(((BankAccount) obj2).getSTK() == iCode)
                                    {
                                        conCardsList.add((ConvenientCard) obj);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        //for - each loop to sort again to IDCard.txt file and add it to return value
        for(IDCard idc : idCards)
        {
            for(ConvenientCard cc : conCardsList)
            {
                IDCard idCard = cc.getTheDinhDanh();
                if(idc.getICode() == idCard.getICode())
                {
                    idcsList.add(idc);
                }
            }
        }
        return idcsList;
    }

    // Requirement 6 - temporary fine
    public void processTopUp(String path)
    {
        //try - cath reading TopUpHistory.txt file
        try
        {
            //initiate file by the path 
            File fileProcessTopUp = new File(path);
            Scanner readTopUp = new Scanner(fileProcessTopUp);

            while(readTopUp.hasNextLine())
            {
                String topUpData = readTopUp.nextLine();
                String[] data = topUpData.split(",");
                
                //case ConvenientCard
                if(data[0].equals("CC"))
                {
                    for(Payment obj : paymentObjects)
                    {
                        if(obj instanceof ConvenientCard)
                        {
                            IDCard idc = ((ConvenientCard) obj).getTheDinhDanh();
                            if(idc.getICode() == Integer.valueOf(data[1]))
                            {
                                ((ConvenientCard) obj).topUp(Double.valueOf(data[2]));
                            }
                        }
                    }
                }
                //case EWallet
                if(data[0].equals("EW"))
                {
                    for(Payment obj : paymentObjects)
                    {
                        if(obj instanceof EWallet)
                        {
                            if(((EWallet) obj).getPhoneNum() == Integer.valueOf(data[1]))
                            {
                                ((EWallet) obj).topUp(Double.valueOf(data[2]));
                            }
                        }
                    }
                }
                //case BankAccount
                if(data[0].equals("BA"))
                {
                    for(Payment obj : paymentObjects)
                    {
                        if(obj instanceof BankAccount)
                        {
                            if(((BankAccount) obj).getSTK() == Integer.valueOf(data[1]))
                            {
                                ((BankAccount) obj).topUp(Double.valueOf(data[2]));
                            }
                        }
                    }
                }
            }
            //close readTopUp Scanner
            readTopUp.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    // Requirement 7 - temporary fine
    public ArrayList<Bill> getUnsuccessfulTransactions(String path) 
    {
        //initiate ArrayList<Bill> to add unsuccessfull transaction and return it
        ArrayList<Bill> unsuccessfulTrans = new ArrayList<Bill>();

        //try - catch reading Bill.txt file
        try
        {
            File bill = new File(path);
            Scanner readBill = new Scanner(bill);

            while(readBill.hasNextLine())
            {
                String data = readBill.nextLine();
                String[] spData = data.split(",");

                //case BankAccount
                if(spData[3].equals("BA"))
                {
                    for(Payment obj : paymentObjects)
                    {
                        if(obj instanceof BankAccount)
                        {
                            //compare file Bill.txt icode and file PaymentInformation.txt icode
                            if(((BankAccount) obj).getSTK() == Integer.valueOf(spData[4]))
                            {
                                //if unsuccessful payment -> add it to ArrayList 
                                if(((BankAccount) obj).pay(Double.valueOf(spData[1])) == false)
                                {
                                    unsuccessfulTrans.add(new Bill(Integer.valueOf(spData[0]), Double.valueOf(spData[1]), spData[2]));
                                }
                            }
                        }
                    }
                }
                //case EWallet
                if(spData[3].equals("EW"))
                {
                    for(Payment obj : paymentObjects)
                    {
                        if(obj instanceof EWallet)
                        {
                            //compare file Bill.txt phoneNum and file PaymentInformation.txt phoneNum
                            if(((EWallet) obj).getPhoneNum() == Integer.valueOf(spData[4]))
                            {
                                //if unsuccessful payment -> add it to ArrayList 
                                if(((EWallet) obj).pay(Double.valueOf(spData[1])) == false)
                                {
                                    unsuccessfulTrans.add(new Bill(Integer.valueOf(spData[0]), Double.valueOf(spData[1]), spData[2]));
                                }
                            }
                        }
                    }
                }
                //case ConvenientCard
                if(spData[3].equals("CC"))
                {
                    for(Payment obj : paymentObjects)
                    {
                        if(obj instanceof ConvenientCard)
                        {
                            IDCard idc = ((ConvenientCard) obj).getTheDinhDanh();
                            //compare file Bill.txt icode and file PaymentInformation.txt icode
                            if(idc.getICode() == Integer.valueOf(spData[4]))
                            {
                                //if unsuccessful payment -> add it to ArrayList 
                                if(((ConvenientCard) obj).pay(Double.valueOf(spData[1])) == false)
                                {
                                    unsuccessfulTrans.add(new Bill(Integer.valueOf(spData[0]), Double.valueOf(spData[1]), spData[2]));
                                }
                            }
                        }
                    }
                }
            }
            //close readBill Scanner 
            readBill.close();

            return unsuccessfulTrans;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return unsuccessfulTrans;
        }
    }

    // Requirement 8 - temporary fine
    public ArrayList<BankAccount> getLargestPaymentByBA(String path) 
    {
        //initiate ArrayList<BankACcount> to get the largest payment BA and return this ArrayList
        ArrayList<BankAccount> baList = new ArrayList<BankAccount>();

        //initiate HashMap<BankAccount, Double> to find the largest amount payment
        HashMap<BankAccount, Double> hmCheck = new HashMap<BankAccount, Double>();
        
        //initiate variable to the largest amount payment
        double largestAmount = 0.0;

        //try - catch reading file Bill.txt
        try 
        {
            File bill = new File(path);
            Scanner readBill = new Scanner(bill);

            while(readBill.hasNextLine())
            {
                String line = readBill.nextLine();
                String[] data = line.split(",");

                if(data[3].equals("BA"))
                {
                    for(Payment obj : paymentObjects)
                    {
                        if(obj instanceof BankAccount)
                        {
                            BankAccount ba = ((BankAccount) obj);
                            if(ba.getSTK() == Integer.valueOf(data[4]))
                            {
                                if(ba.pay(Double.valueOf(data[1])) == true)
                                {
                                    //if found sum for value which mean value += amount
                                    if(hmCheck.containsKey(ba))
                                    {
                                        hmCheck.put(ba, hmCheck.get(ba) + Double.valueOf(data[1]));
                                    }
                                    //if not found, initiate key, value with (BankAccount) obj, 0.0 
                                    else
                                    {
                                        hmCheck.put(ba, 0.0);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //close readBill Scanner 
            readBill.close();

            //find the largest amount payment
            for(BankAccount obj : hmCheck.keySet())
            {
                if(hmCheck.get(obj) >= largestAmount)
                {
                    largestAmount = hmCheck.get(obj);
                }
            }

            //find the BankAccount which equal to the largest amount payment and add it to ArrayList
            for(BankAccount obj : hmCheck.keySet())
            {
                if(hmCheck.get(obj) == largestAmount)
                {
                    baList.add(obj);
                }
            }
            return baList;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return baList;
        }
    }

    //Requirement 9 - temporary fine
    public void processTransactionWithDiscount(String path) 
    {
        //initiate ArrayList<IDCard> from idcm
        ArrayList<IDCard> idcs = idcm.getIDCards();

        //try - catch reading Bill.txt file
        try
        {
            File bill = new File(path);
            Scanner readBill = new Scanner(bill);

            while(readBill.hasNextLine())
            {
                String data = readBill.nextLine();
                String[] part = data.split(",");

                double amount = Double.valueOf(part[1]);
                int code = Integer.valueOf(part[4]);

                for(Payment obj : paymentObjects)
                {
                    //case EWallet but male under 20 and female under 18 will be discount 15% if amount greater than 500.0 when paying for Clothing
                    if(obj instanceof EWallet && part[2].equals("Clothing") && part[3].equals("EW") && amount > 500.0)
                    {
                        for(IDCard idc : idcs)
                        {
                            //case Male and age lower than 20
                            if((idc.getPhoneNum() == code) && (idc.getSex().equals("Male")) && (idc.getAge() < 20))
                            {
                                ((EWallet) obj).pay(amount * (1 - 0.15));
                            }
                            //case Female and age lower than 18
                            if((idc.getPhoneNum() == code) && (idc.getSex().equals("Female")) && (idc.getAge() < 18))
                            {
                                ((EWallet) obj).pay(amount * (1 - 0.15));
                            }
                        }
                        break;
                    }

                    //case EWallet but has no discount
                    if(obj instanceof EWallet && part[3].equals("EW"))
                    {
                        if(((EWallet) obj).getPhoneNum() == code)
                        {
                            ((EWallet) obj).pay(amount);
                        }
                    }

                    //case BankAccount
                    if(obj instanceof BankAccount && part[3].equals("BA"))
                    {
                        if(((BankAccount) obj).getSTK() == code)
                        {
                            ((BankAccount) obj).pay(amount);
                        }
                    }

                    //case ConvenientCard
                    if(obj instanceof ConvenientCard && part[3].equals("CC"))
                    {
                        IDCard idc = ((ConvenientCard) obj).getTheDinhDanh();
                        if(idc.getICode() == code)
                        {
                            ((ConvenientCard) obj).pay(amount);
                        }
                    }
                }
            }
            //close readBill Scanner
            readBill.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}