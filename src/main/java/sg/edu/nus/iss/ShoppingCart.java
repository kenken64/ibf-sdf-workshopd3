package sg.edu.nus.iss;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class ShoppingCart {

    private static HashSet<String> contents  = new HashSet<>();
    private String username;

    public ShoppingCart(String _username){
        this.username = _username;
    }

    public List<String> getContents() {
        List<String> list = new LinkedList<String>(contents);
        return list;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void add(String item) {
        if(this.contents.contains(item))
            return;
        this.contents.add(item);
    }

    public void remove(int index) {
        if(index < contents.size())
            contents.remove(index);
    }

    public void load(InputStream is) throws IOException{
        String item;
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(reader);
        while((item = br.readLine()) != null){
            contents.add(item);
        }
        br.close();
        reader.close();
    }

    public void save(OutputStream os){
        OutputStreamWriter writer = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(writer);
        try{
            for(String item: contents){
                try{
                    bw.write(item);
                    bw.newLine();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }finally{
            try{
                writer.flush();
                bw.flush();
                writer.close();
                bw.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
    }


}
