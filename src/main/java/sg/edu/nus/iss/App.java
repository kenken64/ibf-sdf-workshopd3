package sg.edu.nus.iss;

public class App 
{
    private static String defaultdb= "db";
    public static void main( String[] args )
    {
        System.out.println(args.length);
        if(args.length>0){
            System.out.println("arg db directory from arg");
            App.defaultdb = args[0];
        }
        System.out.println(defaultdb);

        ShoppingCartDB db = new ShoppingCartDB(defaultdb);
        Session session = new Session(db);
        session.start();
    }
}
