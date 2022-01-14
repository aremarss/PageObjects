package test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.AccountPage;
import page.LoginPage;
import page.TransferPage;
import page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.refresh;
import static data.DataHelper.getUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static page.AccountPage.getBalanceCard;
import static page.AccountPage.getEquateValue;

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
        new AccountPage().equateBalance();
        new TransferPage().transferReturnValues(getEquateValue());
        assertEquals(getBalanceCard(1), getBalanceCard(2));
    }

    @Test
    void shouldReturnSuccessLogin() {
        new LoginPage().validLogin(getUser()).validCode(getUser());
        assertEquals(getBalanceCard(1), getBalanceCard(2));
    }

    @Test
    void shouldReturnFailWithEmptyLoginAndPassword() {
        new LoginPage().emptyLoginAndPassword();
    }

    @Test
    void shouldReturnFailWithIncorrectLogin() {
        new LoginPage().incorrectLogin(getUser());
    }

    @Test
    void shouldReturnFailWithIncorrectPassword() {
        new LoginPage().incorrectPassword(getUser());
    }

    @Test
    void shouldReturnFailWithEmptyVerifyCode() {
        new LoginPage().validLogin(getUser());
        new VerificationPage().emptyCode();
    }

    @Test
    void shouldReturnFailWithIncorrectVerifyCode() {
        new LoginPage().validLogin(getUser());
        new VerificationPage().incorrectCode();
    }

    @Test
    void shouldGetBalanceFromCards() {
        new LoginPage().validLogin(getUser()).validCode(getUser());
        assertEquals(10000, getBalanceCard(1));
        assertEquals(10000, getBalanceCard(2));

    }

    @Test
    void shouldSuccessTransferFromFirstCardToSecond() {
        String sum = "10000"; // Сумма, которую нужно перевести
        new LoginPage().validLogin(getUser()).validCode(getUser());
        new AccountPage().toTransferCard(1).transferCard(sum); // toTransferCard(х) = выбрать номер карты (1 или 2).
        assertEquals(20000, getBalanceCard(1)); // Баланс карты (1 или 2).
        assertEquals(0, getBalanceCard(2));
    }

    @Test
    void shouldSuccessTransferFromSecondCardToFirst() {
        String sum = "10000";
        new LoginPage().validLogin(getUser()).validCode(getUser());
        new AccountPage().toTransferCard(2).transferCard(sum);
        assertEquals(0, getBalanceCard(1));
        assertEquals(20000, getBalanceCard(2));
    }

    @Test
    void shouldFailWithEmptyAllTransferInput() {
        new LoginPage().validLogin(getUser()).validCode(getUser());
        new AccountPage().toTransferCard(1).emptyAllTransferInput();
    }

    @Test
    void shouldFailWithEmptyFromTransferInput() {
        String sum = "1";
        new LoginPage().validLogin(getUser()).validCode(getUser());
        new AccountPage().toTransferCard(1).emptyFromTransferInput(sum);
    }

    @Test
    void shouldReturnFailWithEmptySumTransferInput() {
        new LoginPage().validLogin(getUser()).validCode(getUser());
        new AccountPage().toTransferCard(1).transferIncorrectSum("");
    }

    @Test
    void shouldReturnFailTransferOnTheSameCard() {
        String sum = "10000";
        new LoginPage().validLogin(getUser()).validCode(getUser());
        new AccountPage().toTransferCard(1).transferOnTheSameCard(sum);
    }

    @Test
    void shouldReturnFailTransferOverLimit() {
        String sum = "11000";
        new LoginPage().validLogin(getUser()).validCode(getUser());
        new AccountPage().toTransferCard(1).transferIncorrectSum(sum);
    }
}