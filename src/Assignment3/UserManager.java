package Assignment3;
import Assignment2.*;

import java.util.TreeMap;


public class UserManager {
    private static UserManager instance;
    private  TreeMap<String , User> userLst;



    private UserManager(){
        this.userLst = new TreeMap<>();
        }

    public static UserManager getInstance(){
        if (instance == null) instance = new UserManager();
        return instance;
    }
    public void init(String[] usersIn) throws DataValidationException, InvalidInput {
        if (usersIn == null){
            throw new DataValidationException("User ID cannot be null");
        }
        for( String userId: usersIn){
            User user = new User(userId);
            userLst.put(userId, user);
        }
    }
    public void updateTradable(String userId, TradableDTO o) throws DataValidationException{
        if (userId == null || o == null){
            throw new DataValidationException("userId and tradabale cannot be null");
            }
            User user = userLst.get(userId);
            if (user == null) {
                throw new DataValidationException("User not found");
            }
            user.updateTradable(o);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (User user : userLst.values()) {
            sb.append(user.toString()).append("\n");
        }
        return sb.toString();
    }

    //assignment4
    public User getUser(String userId) throws InvalidInput{
        if (!userLst.containsKey(userId)){
            throw new InvalidInput("User cannot be null");
        }
        return userLst.get(userId);
    }
}



