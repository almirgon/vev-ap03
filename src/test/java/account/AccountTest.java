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

    @ParameterizedTest(name = "{index} => Cria uma conta com saldo inicial de {0}, faz saque de {1} e tenta desativar a conta com debito")
    @CsvSource({"101, 102", "130, 1000", "200, 300", "147.0, 149.99", "775.86, 775.87"
    })
    void closeaccountwithdebit(double createValue, double withdrawlValue){
        Account account = new Account();
        account.createAccount(createValue);
        account.withdrawl(withdrawlValue);
        IllegalArgumentException exception =  Assertions.assertThrows(IllegalArgumentException.class, () -> account.closeAccount());
        Assertions.assertEquals("Regularize sua situação", exception.getMessage());
    }

    @ParameterizedTest(name = "{index} => Realiza depositos de {0} em uma conta desativada")
    @CsvSource({"20","50", "0", "18", "25"
    })
    void depositdeactivatedaccount(double value){
        Account account = new Account();
        IllegalArgumentException exception =  Assertions.assertThrows(IllegalArgumentException.class, () -> account.deposit(value));
        Assertions.assertEquals("Não é possivel depositar em uma conta desativada", exception.getMessage());
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

    @Test
    void withdrawaldeactivatedaccount(){
        Account account = new Account();
        IllegalArgumentException exception =  Assertions.assertThrows(IllegalArgumentException.class, () -> account.withdrawl(50));
        Assertions.assertEquals("Não é possivel sacar de uma conta desativada", exception.getMessage());
    }

    @ParameterizedTest(name = "{index} => Cria uma conta com saldo inicial de {0} e faz o saques de ({1}, {2}, {3}, {4}, {5}, {6}), e atinge o limite de 5 saques para uma conta especial")
    @CsvSource({"128.50,20.56,18,17,51.48,15,12.50", "273.41,18.52,130.42,11,5.85,52.36,16.17", "1078.0,52.30,67.18,527, 26.85,69.36,78.17"
    })
    void specialaccountreachedwithdrawallimit(double createValue, double withdrawl1, double withdrawl2, double withdrawl3, double withdrawl4, double withdrawl5, double withdrawl6){
        Account account = new Account();
        account.createAccount(createValue);
        account.withdrawl(withdrawl1);
        account.withdrawl(withdrawl2);
        account.withdrawl(withdrawl3);
        account.withdrawl(withdrawl4);
        account.withdrawl(withdrawl5);
        IllegalArgumentException exception =  Assertions.assertThrows(IllegalArgumentException.class, () -> account.withdrawl(withdrawl6));
        Assertions.assertEquals("Limite de saques atingido", exception.getMessage());
    }


}
