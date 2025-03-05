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
public class EmployeeDetail {
    private String user;
    private String name;
    
    public EmployeeDetail(){
        user="User";
        name="cafe cafe";
    }
    
    public EmployeeDetail(String us, String na){
        this.user=us;
        this.name=na;
    }

    public EmployeeDetail(EmployeeDetail detail){
        this.user=detail.user;
        this.name=detail.name;
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
