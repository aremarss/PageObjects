package page;

import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    Faker faker = new Faker();

    private final SelenideElement verifyInput = $("[data-test-id='code'] input");
    private final SelenideElement buttonVerify = $("[data-test-id='action-verify']");
    private final SelenideElement errorCode = $("[data-test-id='code'] .input__sub");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification']");

    public VerificationPage() {
        verifyInput.shouldBe(visible);
    }

    public AccountPage validCode(DataHelper.AuthInfo info) {
        verifyInput.setValue(info.getVerificationCode());
        buttonVerify.click();
        return new AccountPage();
    }

    public void emptyCode() {
        verifyInput.setValue("");
        buttonVerify.click();
        errorCode.shouldBe(visible);
    }

    public void incorrectCode() {
        verifyInput.setValue(faker.code().ean13());
        buttonVerify.click();
        errorNotification.shouldBe(visible);
    }
}