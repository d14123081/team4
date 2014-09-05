package team4.retailsystem.model;

public class Supplier {
	static int suppliersCount = 0;
	
    private String name;
    private String email;
    private String address;
    private String telephone;
    private int ID;

    public Supplier(String name, String address, String email, String telephone) 
    {
        // TODO Auto-generated constructor stub
        this.name = name;
        this.email = email;
        this.address = address;
        this.telephone = telephone;
        this.ID = (++suppliersCount);
    }

    public Supplier(String name, String email, String address, String telephone, int id) 
    {
        this.name = name;
        this.email = email;
        this.address = address;
        this.telephone = telephone;
        this.ID = id;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setName(String name){
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
    
    @Override
    public String toString(){
    	return this.name;
    }
    
    @Override
	public boolean equals(Object o){
		if(! (o instanceof Supplier)){
			return false;
		}
		else{
			return this.ID == ((Supplier)o).getID();
		}
	}
}
