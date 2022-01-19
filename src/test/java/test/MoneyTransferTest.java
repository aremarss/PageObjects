package test;

import com.codeborne.selenide.Configuration;
import data.DataHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.AccountPage;
import page.LoginPage;
import page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.refresh;
import static data.DataHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @BeforeAll
    static void setUpAll() {
        Configuration.headless = true;
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @AfterEach
    void setDown() {
        refresh();
        open("http://localhost:9999");
        new LoginPage().validLogin(getUser()).validCode(getUser());
        DataHelper.equateBalance(1, 2); // 1 карта - 0001, 2 карта - 0002.
        var accountPage = new AccountPage();
        assertEquals(accountPage.getBalanceCard(1), accountPage.getBalanceCard(2)); // Баланс карты (1 или 2).
    }

    @Test
    void shouldReturnSuccessLogin() {
        new LoginPage().validLogin(getUser()).validCode(getUser());
        var accountPage = new AccountPage();
        assertEquals(accountPage.getBalanceCard(1), accountPage.getBalanceCard(2));
    }

    @Test
    void shouldReturnFailWithIncorrectUser() {
        new LoginPage().invalidLogin(getIncorrectUser());
    }

    @Test
    void shouldReturnFailWithEmptyLoginAndPassword() {
        new LoginPage().emptyLoginOrPassword(getEmptyUser());
    }

    @Test
    void shouldReturnFailWithEmptyLogin() {
        new LoginPage().emptyLoginOrPassword(getEmptyLogin());
    }

    @Test
    void shouldReturnFailWithEmptyPassword() {
        new LoginPage().emptyLoginOrPassword(getEmptyPassword());
    }

    @Test
    void shouldReturnFailWithEmptyVerifyCode() {
        new LoginPage().validLogin(getUser());
        new VerificationPage().emptyCode(getEmptyUser());
    }

    @Test
    void shouldReturnFailWithIncorrectVerifyCode() {
        new LoginPage().validLogin(getUser());
        new VerificationPage().incorrectCode(getIncorrectUser());
    }

    @Test
    void shouldGetBalanceFromCards() {
        new LoginPage().validLogin(getUser()).validCode(getUser());
        var accountPage = new AccountPage();
        assertEquals(10000, accountPage.getBalanceCard(1));
        assertEquals(10000, accountPage.getBalanceCard(2));

    }

    @Test
    void shouldSuccessTransferFromFirstCardToSecond() {
        String sum = "10000"; // Сумма, которую нужно перевести
        new LoginPage().validLogin(getUser()).validCode(getUser());
        // toTransferCard = карта для пополнения; transferMoney = карта перечисления и сумма.
        var accountPage = new AccountPage().toTransferCard(1).transferMoney(2, sum);
        assertEquals(20000, accountPage.getBalanceCard(1));
        assertEquals(0, accountPage.getBalanceCard(2));
    }

    @Test
    void shouldSuccessTransferFromSecondCardToFirst() {
        String sum = "10000";
        new LoginPage().validLogin(getUser()).validCode(getUser());
        var accountPage = new AccountPage().toTransferCard(2).transferMoney(1, sum);
        assertEquals(0, accountPage.getBalanceCard(1));
        assertEquals(20000, accountPage.getBalanceCard(2));
    }

    @Test
    void shouldFailWithEmptyAllTransferInput() {
        new LoginPage().validLogin(getUser()).validCode(getUser());
        new AccountPage().toTransferCard(1).incorrectTransfer(0, "");
    }

    @Test
    void shouldFailWithEmptyFromTransferInput() {
        String sum = "1";
        new LoginPage().validLogin(getUser()).validCode(getUser());
        new AccountPage().toTransferCard(1).incorrectTransfer(0, sum);
    }

    @Test
    void shouldReturnFailWithEmptySumTransferInput() {
        new LoginPage().validLogin(getUser()).validCode(getUser());
        new AccountPage().toTransferCard(1).incorrectTransfer(2, "");
    }

    @Test
    void shouldReturnFailTransferOnTheSameCard() {
        String sum = "10000";
        new LoginPage().validLogin(getUser()).validCode(getUser());
        new AccountPage().toTransferCard(1).incorrectTransfer(1, sum);
    }

    @Test
    void shouldReturnFailTransferOverLimit() {
        String sum = "11000";
        new LoginPage().validLogin(getUser()).validCode(getUser());
        new AccountPage().toTransferCard(1).incorrectTransfer(2, sum);
    }
}