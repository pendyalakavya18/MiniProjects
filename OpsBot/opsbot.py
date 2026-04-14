import os
from datetime import datetime

LOG_FILE = "server.log"
KEYWORDS = ["CRITICAL", "ERROR", "FAILED LOGIN"]

def run_opsbot():
    today = datetime.now().strftime("%Y-%m-%d")
    report_filename = f"security_alert_{today}.txt"
    
  
    error_counts = {
        "CRITICAL": 0,
        "ERROR": 0,
        "FAILED LOGIN": 0
    }
    
    filtered_lines = []

    if not os.path.exists(LOG_FILE):
        print(f"Error: {LOG_FILE} not found. Please make sure the file exists.")
        return
    print(f"Reading {LOG_FILE}")
    try:
        with open(LOG_FILE, 'r') as f:
            for line in f:
                for key in KEYWORDS:
                    if key in line:
                        filtered_lines.append(line.strip())
                        error_counts[key] += 1
                        break 
        if filtered_lines:
            with open(report_filename, 'w') as out_file:
                out_file.write(f"--- SECURITY ALERT REPORT - {today} ---\n")
                out_file.write(f"Summary: {error_counts}\n\n")
                for alert in filtered_lines:
                    out_file.write(f"{alert}\n")
            file_size = os.path.getsize(report_filename)
            print(f"Report generated: {report_filename}")
            print(f"File size: {file_size} bytes")
            print("Summary of events found:")
            for key, count in error_counts.items():
                print(f"{key}: {count}")
        else:
            print("No security threats or errors found today. Good job!")

    except Exception as e:
        print(f"Something went wrong while processing: {e}")

if __name__ == "__main__":
    run_opsbot()
