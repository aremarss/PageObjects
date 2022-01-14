package page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.util.Objects;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private final SelenideElement sumInput = $("[data-test-id='amount'] input");
    private final SelenideElement fromInput = $("[data-test-id='from'] input");
    private final SelenideElement toCard = $("[data-test-id='to'] .input__control");
    private final SelenideElement buttonTransfer = $("[data-test-id='action-transfer']");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification']");
    String fromValue;

    public void transferCard(String sum) {
        anotherCard();
        clearSumInput();
        sumInput.setValue(sum);
        clearFromInput();
        fromInput.setValue(fromValue);
        buttonTransfer.click();
        new AccountPage();
    }

    public void transferIncorrectSum(String sum) {
        transferCard(sum);
        errorNotification.shouldBe(visible);
    }

    public void emptyAllTransferInput() {
        clearSumInput();
        clearFromInput();
        buttonTransfer.click();
        errorNotification.shouldBe(visible);
    }

    public void emptyFromTransferInput(String sum) {
        clearSumInput();
        sumInput.setValue(sum);
        clearFromInput();
        buttonTransfer.click();
        errorNotification.shouldBe(visible);
    }

    public void transferOnTheSameCard(String sum) {
        sameCard();
        clearSumInput();
        sumInput.setValue(sum);
        clearFromInput();
        fromInput.setValue(fromValue);
        buttonTransfer.click();
        errorNotification.shouldBe(visible);
    }

    public AccountPage transferReturnValues(int equateValue) {
        if (equateValue == 0) return new AccountPage();
        anotherCard();
        clearSumInput();
        sumInput.setValue(String.valueOf(equateValue));
        clearFromInput();
        fromInput.setValue(fromValue);
        buttonTransfer.click();
        return new AccountPage();
    }

    private void clearSumInput() {
        sumInput.sendKeys(Keys.CONTROL + "A");
        sumInput.sendKeys(Keys.DELETE);
    }

    private void clearFromInput() {
        fromInput.sendKeys(Keys.CONTROL + "A");
        fromInput.sendKeys(Keys.DELETE);
    }

    private void anotherCard() {
        if (Objects.equals(toCard.shouldHave(visible).getValue(), "**** **** **** 0001")) {
            fromValue = "5559 0000 0000 0002";
        } else if (Objects.equals(toCard.shouldHave(visible).getValue(), "**** **** **** 0002")) {
            fromValue = "5559 0000 0000 0001";
        }
    }

    private void sameCard() {
        if (Objects.equals(toCard.shouldHave(visible).getValue(), "**** **** **** 0001")) {
            fromValue = "5559 0000 0000 0001";
        } else if (Objects.equals(toCard.shouldHave(visible).getValue(), "**** **** **** 0002")) {
            fromValue = "5559 0000 0000 0002";
        }
    }
}