package page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.Objects;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static data.DataHelper.AuthInfo.shortCardNumber;

public class AccountPage {

    public AccountPage() {
        SelenideElement dashboard = $("[data-test-id='dashboard']");
        dashboard.shouldBe(visible);
    }

    public TransferPage toTransferCard(int cardNumber) {
        ElementsCollection cards = $$(".list__item");
        cards.findBy(matchText(Objects.requireNonNull(shortCardNumber(cardNumber)))).$("button").click();
        return new TransferPage();
    }
}