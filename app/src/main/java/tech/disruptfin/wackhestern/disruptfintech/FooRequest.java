package tech.disruptfin.wackhestern.disruptfintech;
public class FooRequest {
    final String amount;
    final String stockName;

    FooRequest(String amount, String stockName) {
        this.amount = amount;
        this.stockName = stockName;
    }
}