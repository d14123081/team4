package team4.retailsystem.model;

public class Supplier {
	static int suppliersCount = 0;
	
    private String name;
    private String email;
    private String address;
    private String telephone;
    private int ID;

    public Supplier(String name, String email, String address, String telephone) 
    {
        // TODO Auto-generated constructor stub
        this.name = name;
        this.email = email;
        this.address = address;
        this.telephone = telephone;
        this.ID = (++suppliersCount);
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
    
    public String getTelephoneNumber(){
        return this.telephone;
    }
    
    public void setTelephone(String telephone){
        this.telephone = telephone;
    }
    
    public int getID()
    {
    	return this.ID;
    }
}
