package sg.edu.nus.iss;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class ShoppingCartDB {
    private File repository;

    public ShoppingCartDB(String _repository) {
        this.repository = new File(_repository);
    }

    public File getRepository() {
        return repository;
    }

    public void setRepository(File repository) {
        this.repository = repository;
    }

    public List<String> listUsers(){
        List<String> users = new LinkedList<String>();
        for(File cartFile: repository.listFiles()){
            users.add(cartFile.getName().replace(".db", ""));
        }
        return users;
    }

    public ShoppingCart load(String username){
        String cartName = username + ".db";
        ShoppingCart cart = new ShoppingCart(username);
        for(File cartFile: repository.listFiles()){
            if(cartFile.getName().equals(cartName)){
                InputStream is;
                try {
                    is = new FileInputStream(cartFile);
                    cart.load(is);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return cart;
    }

    public void save(ShoppingCart cart) throws IOException {
        // construct db file based on the username
        String cartDBfilename = cart.getUsername() + ".db";
        String savedbLocation = repository.getPath() 
                + File.separator + cartDBfilename;

        File cartDB = new File(savedbLocation);
        OutputStream os = null;
        try{
            if(!cartDB.exists()){
                try{
                    Path p = Paths.get(repository.getPath());
                    Files.createDirectory(p);
                }catch(FileAlreadyExistsException e){
                    System.err.println("File already exists: " + e.getMessage());
                }
                cartDB.createNewFile();
            }

            os = new FileOutputStream(savedbLocation);
            cart.save(os);
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            os.flush();
            os.close();
        }
    }

    
    
}
