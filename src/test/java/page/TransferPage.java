package page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static data.DataHelper.AuthInfo.cardNumber;

public class TransferPage {

    private final SelenideElement sumInput = $("[data-test-id='amount'] input");
    private final SelenideElement fromInput = $("[data-test-id='from'] input");
    private final SelenideElement buttonTransfer = $("[data-test-id='action-transfer']");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification']");

    public AccountPage transferMoney(int cardNumberFrom, String sum) {
        clearInputs();
        sumInput.setValue(sum);
        fromInput.setValue(cardNumber(cardNumberFrom));
        buttonTransfer.click();
        return new AccountPage();
    }

    public void incorrectTransfer(int cardNumberFrom, String sum) {
        clearInputs();
        sumInput.setValue(sum);
        fromInput.setValue(cardNumber(cardNumberFrom));
        buttonTransfer.click();
        errorNotification.shouldBe(visible);
    }

    private void clearInputs() {
        sumInput.sendKeys(Keys.CONTROL + "A");
        sumInput.sendKeys(Keys.DELETE);
        fromInput.sendKeys(Keys.CONTROL + "A");
        fromInput.sendKeys(Keys.DELETE);
    }
}