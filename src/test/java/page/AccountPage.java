package page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class AccountPage {

    private final SelenideElement dashboard = $("[data-test-id='dashboard']");
    private static final SelenideElement cardFirst = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] button");
    private static final SelenideElement cardSecond = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] button");
    static int equateValue;

    public AccountPage() {
        dashboard.shouldBe(visible);
    }

    public TransferPage toTransferCard(int card) {
        if (card == 1) {
            cardFirst.click();
        } else if (card == 2) {
            cardSecond.click();
        }
        return new TransferPage();
    }

    public static int getBalanceCard(int card) {
        ElementsCollection cards = $$(".list__item");
        var text = cards.first().text(); // По умолчанию первая карточка.
        if (card == 1) {
            text = cards.first().text();
        } else if (card == 2) {
            text = cards.last().text();
        }
        return extractBalance(text);
    }

    private static int extractBalance(String text) {
        String balanceStart = "баланс: ";
        String balanceFinish = " р.";
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public TransferPage equateBalance() {
        int card1 = getBalanceCard(1);
        int card2 = getBalanceCard(2);
        int inequality = card1 - card2;
        equateValue = inequality / 2;
        if (inequality == 0) return new TransferPage();
        else if (inequality > 0) {
            cardSecond.click();
        }
        if (inequality < 0) {
            cardFirst.click();
        }
        return new TransferPage();
    }

    public static int getEquateValue() {
        return equateValue;
    }
}