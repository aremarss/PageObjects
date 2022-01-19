package page;

import com.codeborne.selenide.SelenideElement;

import java.util.Objects;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static data.DataHelper.cards;
import static data.DataHelper.shortCardNumber;

public class AccountPage {

    public AccountPage() {
        SelenideElement dashboard = $("[data-test-id='dashboard']");
        dashboard.shouldBe(visible);
    }

    public TransferPage toTransferCard(int cardNumber) {
        cards.findBy(matchText(Objects.requireNonNull(shortCardNumber(cardNumber)))).$("button").click();
        return new TransferPage();
    }

    public int getBalanceCard(int cardNumber) {
        var text = cards.findBy(matchText(Objects.requireNonNull(shortCardNumber(cardNumber)))).text();
        return extractBalance(text);
    }

    public int extractBalance(String text) {
        String balanceStart = "баланс: ";
        String balanceFinish = " р.";
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}