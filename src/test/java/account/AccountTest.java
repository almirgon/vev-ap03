package account;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AccountTest {


    @ParameterizedTest(name = "{index} => Cria uma conta com saldo inicial de {0}, e espera que ela seja de um cliente especial")
    @CsvSource({"100, 180", "200", "215", "300"
    })
    void createaccountwithspecialclient(double depositValue){
        Account account = new Account();
        account.createAccount(depositValue);
        Assertions.assertTrue(account.isSpecialClient());
    }

    @ParameterizedTest(name = "{index} => Cria uma conta com saldo inicial de {0}, e espera que ela tenha limite de saque menor que 2")
    @CsvSource({"50, 23", "75", "15", "18"
    })
    void createaccountwithoutspecialclient(double depositValue){
        Account account = new Account();
        account.createAccount(depositValue);
        Assertions.assertFalse(account.getWithdrawlLimit() > 2);
    }

    @Test
    void closeinactivatedaccount(){
        Account account = new Account();
        IllegalArgumentException exception =  Assertions.assertThrows(IllegalArgumentException.class, () -> account.closeAccount());
        Assertions.assertEquals("A conta já está desativada", exception.getMessage());
    }

    @ParameterizedTest(name = "{index} => Cria a conta com valor inicial de {0} e faz depositos de {1} e {2} esperando um saldo igual a {3}")
    @CsvSource({"150, 15,10, 175", "10, 50,50, 110", "25,50,12, 87", "35,10,15,60"
    })
    void successfuldeposit(double creationDeposit, double deposit1, double deposit2, double expected){
        Account account = new Account();
        account.createAccount(creationDeposit);
        account.deposit(deposit1);
        account.deposit(deposit2);
        Assertions.assertEquals(expected, account.getBalance());
    }


    @ParameterizedTest(name = "{index} => Cria uma conta com saldo inicial de {0} e faz o saques de {1} e {2} esperando um saldo com valor igual a {3}")
    @CsvSource({"55.15, 25.18, 29.97, 0.00", "18.30, 8.05, 1.70, 8.55", "12.55, 2.55, 9.00, 1.00"
    })
    void successfulwithdrawal(double createValue, double withdrawl1, double withdrawl2, double expected){
        Account account = new Account();
        account.createAccount(createValue);
        account.withdrawl(withdrawl1);
        account.withdrawl(withdrawl2);
        Assertions.assertEquals(expected, account.getBalance());

    }


}
