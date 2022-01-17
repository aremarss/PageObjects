package data;

import com.codeborne.selenide.ElementsCollection;
import com.github.javafaker.Faker;
import lombok.*;
import page.AccountPage;
import page.TransferPage;

import java.util.Objects;

import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Selenide.$$;

public class DataHelper {
    private DataHelper() {
    }

    static ElementsCollection cards = $$(".list__item");
    static int equateValue;

    @Value
    public static class AuthInfo {
        String login;
        String password;
        String verificationCode;

        public AuthInfo(String login, String password, String verificationCode) {
            this.login = login;
            this.password = password;
            this.verificationCode = verificationCode;
        }

        public static AuthInfo getUser() {
            return new AuthInfo("vasya", "qwerty123", "12345");
        }

        public static AuthInfo getIncorrectUser() {
            Faker faker = new Faker();
            return new AuthInfo(faker.name().username(), faker.internet().password(), faker.code().ean13());
        }

        public static AuthInfo getEmptyUser() {
            return new AuthInfo("", "", "");
        }

        public static AuthInfo getEmptyLogin() {
            return new AuthInfo("", "qwerty123", "12345");
        }

        public static AuthInfo getEmptyPassword() {
            return new AuthInfo("vasya", "", "12345");
        }

        public static int getBalanceCard(int cardNumber) {
            var text = cards.findBy(matchText(Objects.requireNonNull(shortCardNumber(cardNumber)))).text();
            return extractBalance(text);
        }

        public static int extractBalance(String text) {
            String balanceStart = "баланс: ";
            String balanceFinish = " р.";
            var start = text.indexOf(balanceStart);
            var finish = text.indexOf(balanceFinish);
            var value = text.substring(start + balanceStart.length(), finish);
            return Integer.parseInt(value);
        }

        public static AccountPage equateBalance(int cardOne, int cardTwo) {
            int inequality = getBalanceCard(1) - getBalanceCard(2);
            equateValue = inequality / 2;
            if (inequality > 0) {
                cards.findBy(matchText(Objects.requireNonNull(shortCardNumber(cardTwo)))).$("button").click();
                return new TransferPage().transferMoney(1, String.valueOf(equateValue));
            }
            if (inequality < 0) {
                cards.findBy(matchText(Objects.requireNonNull(shortCardNumber(cardOne)))).$("button").click();
                return new TransferPage().transferMoney(2, String.valueOf(equateValue));
            }
            return new AccountPage();
        }

        public static String cardNumber(int item) {
            String[] cards = {"", "5559 0000 0000 0001", "5559 0000 0000 0002"};
            if (item >= 1 && item <= 2) {
                return cards[item];
            }
            return "";
        }

        public static String shortCardNumber(int item) {
            String[] cards = {"", "0001", "0002"};
            if (item >= 1 && item <= 2) {
                return cards[item];
            }
            return "";
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }

        public String getVerificationCode() {
            return verificationCode;
        }
    }
}