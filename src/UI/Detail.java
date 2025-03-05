/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

/**
 *
 * @author NQHuy
 */

public class Detail {
    private String user;
    private String name;
    
    public Detail(){
        user="User";
        name="cafe cafe";
    }
    
    public Detail(String us, String na){
        this.user=us;
        this.name=na;
    }

    public Detail(Detail detail){
        this.user=detail.user;
        this.name=detail.name;
    }

    Detail(String User) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
