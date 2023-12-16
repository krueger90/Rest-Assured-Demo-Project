package config;

public interface ParaBankEndpoints {

    /*
     * Account operations
     */
    String DEPOSIT = "/deposit";
    String SINGLE_ACCOUNT = "/accounts/{accountId}";
    String WITHDRAW = "/withdraw";
    String CREATE_ACCOUNT = "/createAccount";
    String TRANSACTIONS_PER_ACCOUNT = "/accounts/{accountId}/transactions";
    String TRANSACTIONS_PER_ACCOUNT_TYPE_BY_MONTH = "/accounts/{accountId}/transactions/month/{month}/type/{type}";
    String TRANSACTIONS_DATE_RANGE = "/accounts/{accountId}/transactions/fromDate/{fromDate}/toDate/{toDate}";
    String TRANSACTIONS_SPECIFIC_DATE = "/accounts/{accountId}/transactions/fromDate/{fromDate}/toDate/{toDate}";
    String TRANSFER = "/transfer";
    String PAY_BILL = "/billpay";

    /*
     * Customer operations
     */
    String CUSTOMER_DETAILS = "/customers/{customerId}";
    String UPDATE_CUSTOMER = "/customers/update/{customerId}";
    String CREATE_CUSTOMER = "/createAccount";
    String CUSTOMER_ACCOUNTS = "//customers/{customerId}/accounts";
}
