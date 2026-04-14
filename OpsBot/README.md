# OpsBot - Server Log Automator

A Python script that parses `server.log` files and filters for security issues.

### What it does:
- Looks for keywords like `CRITICAL`, `ERROR`, and `FAILED LOGIN`.
- Counts how many times they appear.
- Generates a summary report.

### Usage:
Just run it in the terminal:
```bash
python opsbot.py
```
Make sure `server.log` is in the same directory.
