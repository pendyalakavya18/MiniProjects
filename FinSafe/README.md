# FinSafe Transaction Validator

This is a simple Java console app that simulates a digital wallet. The entire logic is contained within a single Java file for simplicity.

### Features:
- Balance management with private variables.
- Custom `InSufficientFundsException` for overdrafts.
- Validation for negative amounts and overspending.
- Keeps track of the last 5 transactions.

### To Run:
Go to the `src` folder and run:
```bash
javac FinSafeApp.java
java FinSafeApp
```
