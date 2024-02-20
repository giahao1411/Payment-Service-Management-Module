import java.time.Year;

public class IDCard
{
      private int iCode;                //ma dinh danh
      private String name;              //ho ten
      private String sex;               //gioi tinh
      private String birthDate;         //ngay thang nam sinh
      private String address;           //dia chi 
      private int phoneNum;             //so dien thoai

      public IDCard(int iCode, String name, String sex, String birthDate, String address, int phoneNum)
      {
            this.iCode = iCode;
            this.name = name;
            this.sex = sex;
            this.birthDate = birthDate;
            this.address = address;
            this.phoneNum = phoneNum;
      }

      //getAge constructor
      public int getAge()
      {
            String birthData = getBirthDate();
            String[] part = birthData.split("/");

            Year realTimYear = Year.now();

            int curYear = realTimYear.getValue();
            int year = Integer.valueOf(part[2]);

            //calculate current age
		int age = curYear - year;

            return age;
      }

      //get - set iCode
      public int getICode()
      {
            return this.iCode;
      }

      public void setICode(int iCode)
      {
            this.iCode = iCode;
      }

      //get - set name
      public String getName()
      {
            return this.name;
      }

      public void setName(String name)
      {
            this.name = name;
      }

      //get - set sex
      public String getSex()
      {
            return this.sex;
      }

      public void setSex(String sex)
      {
            this.sex = sex;
      }

      //get - set birthDate
      public String getBirthDate()
      {
            return this.birthDate;
      }

      public void setBirthDate(String birthDate)
      {
            this.birthDate = birthDate;
      }

      //get - set address
      public String getAddress()
      {
            return this.address;
      }

      public void setAddress(String address)
      {
            this.address = address;
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

      //toString constructor
      public String toString()
      {
            return this.iCode + "," + this.name + "," + this.sex + "," + this.birthDate + "," + this.address + "," + this.phoneNum;
      }
}