package page;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import java.util.Objects;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private final SelenideElement loginInput = $("[data-test-id='login'] input");
    private final SelenideElement errorLogin = $("[data-test-id='login'] .input__sub");
    private final SelenideElement passwordInput = $("[data-test-id='password'] input");
    private final SelenideElement errorPassword = $("[data-test-id='password'] .input__sub");
    private final SelenideElement buttonLogin = $("[data-test-id='action-login']");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification']");

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginInput.setValue(info.getLogin());
        passwordInput.setValue(info.getPassword());
        buttonLogin.click();
        return new VerificationPage();
    }

    public void invalidLogin(DataHelper.AuthInfo info) {
        loginInput.setValue(info.getLogin());
        passwordInput.setValue(info.getPassword());
        buttonLogin.click();
        errorNotification.shouldBe(visible);
    }

    public void emptyLoginOrPassword(DataHelper.AuthInfo info) {
        loginInput.setValue(info.getLogin());
        passwordInput.setValue(info.getPassword());
        buttonLogin.click();
        if (Objects.requireNonNull(loginInput.getValue()).isEmpty()) {
            errorLogin.shouldBe(visible, text("Поле обязательно для заполнения"));
        }
        if (Objects.requireNonNull(passwordInput.getValue()).isEmpty()) {
            errorPassword.shouldBe(visible, text("Поле обязательно для заполнения"));
        }
    }
}