package sg.edu.nus.iss;

import java.io.Console;
import java.io.IOException;
import java.util.List;

public class Session {
    private ShoppingCartDB repository;

    private static final String END = "end";
    private static final String LOGIN = "login";
    private static final String ADD = "add";
    private static final String SAVE = "save";
    private static final String LIST = "list";
    private static final String USERS = "users";
    

    private ShoppingCart currentCart; 

    public Session(ShoppingCartDB repository) {
        this.repository = repository;
    }

    public void start(){
        Console cons = System.console();
        boolean stop = false;
        while(!stop){
            String input = cons.readLine("> ");
            String[] term = input.split(" ");
            switch (term[0]) {
                case END:
                    stop = true;
                    break;
                case ADD:
                    for(int x = 1; x < term.length; x++){
                        try{
                            currentCart.add(term[x]);
                            System.out.println(term[x] + " added to the cart.");
                        }catch(NullPointerException e){
                            System.out.println("Please login before adding to the cart!");
                        } 
                    } 
                    break;

                case LIST:
                    currentCart = repository.load(currentCart.getUsername());
                    printAllItems(currentCart.getContents());
                    break;
                case USERS:
                    List<String> users = repository.listUsers();
                    for(String user: users){
                        System.out.println(user);
                    }
                    break;
                case SAVE:
                    try {
                        repository.save(currentCart);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case LOGIN:
                    currentCart = new ShoppingCart(term[1]);
                    break;
                default:
                    break;
            }
        }
    }


    public void printAllItems(List<String> items){
        if(items.size() == 0){
            System.out.println("No items in the cart.");
            return;
        }

        for(String item: items){
            System.out.printf("%d %s\n", (items.indexOf(item) + 1), item);
        }
    }
}
