package data;

public class DataHelper {
    private DataHelper() {
    }

    public static class AuthInfo {
        private final String login;
        private final String password;
        private final String verificationCode;

        public AuthInfo(String login, String password, String verificationCode) {
            this.login = login;
            this.password = password;
            this.verificationCode = verificationCode;
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

    public static AuthInfo getUser() {
        return new AuthInfo("vasya", "qwerty123", "12345");
    }
}