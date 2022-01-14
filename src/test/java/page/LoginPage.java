package page;

import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private final SelenideElement loginInput = $("[data-test-id='login'] input");
    private final SelenideElement errorLogin = $("[data-test-id='login'] .input__sub");
    private final SelenideElement passwordInput = $("[data-test-id='password'] input");
    private final SelenideElement errorPassword = $("[data-test-id='password'] .input__sub");
    private final SelenideElement buttonLogin = $("[data-test-id='action-login']");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification']");

    Faker faker = new Faker();

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginInput.setValue(info.getLogin());
        passwordInput.setValue(info.getPassword());
        buttonLogin.click();
        return new VerificationPage();
    }

    public void emptyLoginAndPassword() {
        loginInput.setValue("");
        passwordInput.setValue("");
        buttonLogin.click();
        errorLogin.shouldBe(visible, text("Поле обязательно для заполнения"));
        errorPassword.shouldBe(visible, text("Поле обязательно для заполнения"));
    }

    public void incorrectLogin(DataHelper.AuthInfo info) {
        loginInput.setValue(faker.name().username());
        passwordInput.setValue(info.getPassword());
        buttonLogin.click();
        errorNotification.shouldBe(visible);
    }

    public void incorrectPassword(DataHelper.AuthInfo info) {
        loginInput.setValue(info.getLogin());
        passwordInput.setValue(faker.internet().password());
        buttonLogin.click();
        errorNotification.shouldBe(visible);
    }
}