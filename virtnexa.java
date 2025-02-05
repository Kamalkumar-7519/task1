import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class virtnexa {
    //add expence function=====================================================.
    private static void Add_Expences(Connection com,Statement sta,Scanner sc) {
        System.out.println("Enter Description");
        String name = sc.next();
        sc.nextLine();
        System.out.print("Enter amount: ");
        double amount = sc.nextDouble();
        sc.nextLine(); // Consume newline
        String query = "insert into management(item,price)" +
                "value('" + name + "'," + amount + ");";
        try {
            int resultrow = sta.executeUpdate(query);
            if (resultrow > 0) {
                System.out.println("Expences add Sucessfully");
            } else {
                System.out.println("Erorr!!!!!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //================================================================================//
    // edit expance===================================================================//
    private static void edit_Expence(Connection com,Statement stat,Scanner sc)
    {
        try {
            System.out.println("Enter the Purched_id");
            int id = sc.nextInt();
            if (!if_exit(com, stat, id)) {
                System.out.println("This Purched id does not exits");
            }
            System.out.println("Enter new Description");
            String s = sc.next();
            System.out.println("Enter the change amount");
            double d = sc.nextInt();
            String query = "update management set item ='" + s + "'," +
                    "price =" + d + "where purched_id =" + id;
            int row = stat.executeUpdate(query);
            if (row > 0) {
                System.out.println("Updation is Succesfuly");
            } else {
                System.out.println("Something error!!!!!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    //================================================================================//
   public static boolean if_exit(Connection com ,Statement stat, int id) {
       try {
           String query = "select purched_id from management where purched_id= " + id;
           ResultSet rs = stat.executeQuery(query);
           return rs.next();
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
   }//===============================================================================//
    //deletion=====================================================================//
    private static void deletion(Connection com,Statement stat,Scanner sc)
    {
        try {
            System.out.println("Enter the purched id");
            int id = sc.nextInt();
            if (!if_exit(com, stat, id)) {
                System.out.println("This purched it cann't be delete!!!!!!!!");
            }
            String query = "delete from management where purched_id =" + id;
            int row = stat.executeUpdate(query);
            if(row>0){
                System.out.println("Delete Succesfully");
            }
            else {
                System.out.println("Something found errro!!!!!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //==================================================================================//
    //view_expence=====================================================================//
    private static void view_expence(Connection com,Statement stat)
    {
        String query="select * from management";
        try
        {
            ResultSet rs = stat.executeQuery(query);
            while (rs.next())
            {
                int id= rs.getInt("purched_id");
                String n = rs.getString("item");
                int amount1= rs.getInt("price");
                String date = rs.getTimestamp("purched_date").toString();
                System.out.println();
                System.out.println("=================================");
                System.out.println("your id is:"+id);
                System.out.println("item name is:"+n);
                System.out.println("price is is:"+amount1);
                System.out.println("Date and Time :"+date);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //===============================================================================//
    //currency converter=============================================================//
   private static void CurrencyConverter(String fromCurrency,String toCurrency,int amount) {
         Map<String, Double> conversionRates = new HashMap<>();
            // Sample fixed conversion rates
            conversionRates.put("USD_TO_EUR", 0.85);
            conversionRates.put("EUR_TO_USD", 1.18);
            conversionRates.put("USD_TO_GBP", 0.75);
            conversionRates.put("GBP_TO_USD", 1.33);
            conversionRates.put("INR_TO_USD",0.012);
            conversionRates.put("USD_TO_INR",85.77);
            double amount_change;
            // Add more as needed
            String conversionKey = fromCurrency + "_TO_" + toCurrency;
            if (conversionRates.containsKey(conversionKey)) {
                System.out.println(amount * conversionRates.get(conversionKey));
       }
    }
    //=================================================================================//
    //exist statement================================================================//
    public static void exit()
    {
        System.out.println("thankyou for using our site");
    }
    //================================================================================//
    private static final String url ="jdbc:mysql://127.0.0.1:3306/expance";
    private static final String username ="root";
    private static final String password ="Kamal@9600";

    public static void main(String[] args) {

        //loading the driver for connecting with mysql
            try
            {
                Class.forName("com.mysql.jdbc.Driver");
                System.out.println("Connection execute");
            }catch (ClassNotFoundException e)
            {
                System.out.println(e.getMessage());
            }
        //making connection with sql
        try
        {
            Connection com = DriverManager.getConnection(url,username,password);
            Statement stat = com.createStatement();
            //here we make connection with mysql
            while (true)
            {
                System.out.println("\nExpense Tracker Menu:");
                System.out.println("1. Add Expense");
                System.out.println("2. Edit Expense");
                System.out.println("3. Delete Expense");
                System.out.println("4. View Expenses");
                System.out.println("5. Currency Conversion");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");
                Scanner sc = new Scanner(System.in);
                int choice = sc.nextInt();
                switch (choice)
                {
                    case 1:
                        Add_Expences(com,stat,sc);
                        break;
                    case 2:
                        edit_Expence(com,stat,sc);
                        break;
                    case 3:
                        deletion(com,stat,sc);
                        break;
                    case 4:
                        view_expence(com,stat);
                        break;
                    case 5:
                        System.out.println("Enter the anount want exchange");
                        int a = sc.nextInt();
                        System.out.println("Enter source currency (USD, EUR, GBP,INR)");
                        String st = sc.next();
                        System.out.println("Enter target currency (USD, EUR, GBP,INR)");
                        String st2=sc.next();
                        CurrencyConverter(st,st2,a);
                    case 0:
                        exit();
                        sc.close();
                        return;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
