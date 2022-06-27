package account;
import java.util.ArrayList;
import java.util.List;


public class Account {

    private boolean activeAccount;

    private boolean specialClient;

    private double balance;

    private List<Double> withdrawalHistory;

    private double withdrawlLimit;

    public Account(){
        this.withdrawalHistory = new ArrayList<>();
    }

    public double createAccount(double deposit){
        setActiveAccount(true);
        if(deposit >= 100){
            setSpecialClient(true);
            setWithdrawlLimit(5);
        }else{
            setWithdrawlLimit(2);
        }
        setBalance(deposit);
        return getBalance();
    }

    public void closeAccount(){
        if(isActiveAccount()){
            if(getBalance() < 0){
                throw new IllegalArgumentException("Regularize sua situação");
            }
            else{
                setActiveAccount(false);
            }
        }else{
            throw new IllegalArgumentException("A conta já está desativada");
        }

    }

    public double deposit(double value){
        if(isActiveAccount()){
            setBalance(getBalance() + value);
        }else{
            throw new IllegalArgumentException("Não é possivel depositar em uma conta desativada");
        }
        return getBalance();
    }

    public double withdrawl(double value){
        if(isActiveAccount()){
            if(!isSpecialClient() && value > getBalance()){
                throw new IllegalArgumentException("Saldo insuficiente");
            }else if(getWithdrawalHistory().size() == getWithdrawlLimit()){
                throw new IllegalArgumentException("Limite de saques atingido");
            }else if(isSpecialClient() && value > getBalance()){
                setBalance(getBalance() - value);
                this.addWithdrawlTransaction(value);
            }
            else{
                setBalance(getBalance() - value);
                this.addWithdrawlTransaction(value);
            }
        }else{
            throw new IllegalArgumentException("Não é possivel sacar de uma conta desativada");
        }
        return getBalance();
    }


    public boolean isActiveAccount() {
        return activeAccount;
    }

    public void setActiveAccount(boolean activeAccount) {
        this.activeAccount = activeAccount;
    }

    public boolean isSpecialClient() {
        return specialClient;
    }

    public void setSpecialClient(boolean specialClient) {
        this.specialClient = specialClient;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


    public List<Double> getWithdrawalHistory() {
        return withdrawalHistory;
    }

    public void addWithdrawlTransaction(double withdrawl){
        this.getWithdrawalHistory().add(withdrawl);
    }


    public double getWithdrawlLimit() {
        return withdrawlLimit;
    }

    public void setWithdrawlLimit(double withdrawlLimit) {
        this.withdrawlLimit = withdrawlLimit;
    }

}
