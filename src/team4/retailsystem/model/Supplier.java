package team4.retailsystem.model;

public class Supplier {
    private String name;
    private String email;
    private String address;
    private long telphone;

    public Supplier(String name, String email, String address, long telphone) {
        // TODO Auto-generated constructor stub
        this.name = name;
        this.email = email;
        this.address = address;
        this.telphone = telphone;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setSupplier(String name){
        this.name = name;
    }
    
    public String getEmail(){
        return this.email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getAddress(){
        return this.address;
    }
    
    public void setAddress(String address){
        this.address = address;
    }
    
    public long getTelephone(){
        return this.telphone;
    }
    
    public void setTelphone(long telphone){
        this.telphone = telphone;
    }

}
