package lk.ijse.pos.dto;

public class CustomerDTO {
    private String cusID;
    private String cusName;
    private String cusAddress;
    private double cusSalary;

    public CustomerDTO() {
    }

    public CustomerDTO(String cusID, String cusName, String cusAddress, double cusSalary) {
        this.cusID = cusID;
        this.cusName = cusName;
        this.cusAddress = cusAddress;
        this.cusSalary = cusSalary;
    }

    public String getCusID() {
        return cusID;
    }

    public void setCusID(String cusID) {
        this.cusID = cusID;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusAddress() {
        return cusAddress;
    }

    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }

    public double getCusSalary() {
        return cusSalary;
    }

    public void setCusSalary(double cusSalary) {
        this.cusSalary = cusSalary;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "cusID='" + cusID + '\'' +
                ", cusName='" + cusName + '\'' +
                ", cusAddress='" + cusAddress + '\'' +
                ", cusSalary=" + cusSalary +
                '}';
    }
}
