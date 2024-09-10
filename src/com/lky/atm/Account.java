package src.com.lky.atm;

public class Account {
    private String cardId;
    private String name;
    private char sex;
    private String password;
    private double money;
    private double limit;

    public Account() {
    }

    public Account(String cardId, String name, char sex, String password, double money, double limit) {
        this.cardId = cardId;
        this.name = name;
        this.sex = sex;
        this.password = password;
        this.money = money;
        this.limit = limit;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getName() {
        return name+(sex=='n'?"男士":"女士");
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }
}
