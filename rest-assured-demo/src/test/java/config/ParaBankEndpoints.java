package config;

public interface ParaBankEndpoints {

    /*
     * Account operations
     */
    String DEPOSIT = "/deposit";
    String SINGLE_ACCOUNT = "/accounts/{accountId}";
    String WITHDRAW = "/withdraw";
    String CREATE_ACCOUNT = "/createAccount";
    String TRANSFER = "/transfer";
    String PAY_BILL = "/billpay";
}
