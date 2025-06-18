📊 CSV to PostgreSQL Batch Processor with Kafka Notification System
This project is a Spring Boot-based data processing pipeline that imports transaction data from a CSV file into a PostgreSQL database using Spring Batch, and then sends notifications via Kafka, email, and WebSocket.

🧩 Project Structure
This system is split into two Spring Boot applications:

📁 1. batch/ — CSV Import & Kafka Producer
Function: Reads transaction records from a CSV file and stores them in PostgreSQL.

Technology: Spring Batch

Data Source: transactions.csv

Trigger: GET http://localhost:8096/startJob

Kafka Output: Publishes a job completion event to the topic batch-job-topic after processing.

📄 Sample CSV format:

csv
Copier
Modifier
transaction_id,account_number,transaction_date,transaction_type,transaction_amount
1,123456789,2023-08-23 09:15:00,D,100.00
2,987654321,2023-08-22 14:30:00,W,50.00
3,456789123,2023-08-21 11:45:00,D,200.00
...

📁 2. Notification/ — Kafka Consumer, Email & WebSocket Notifier
Function: Listens to the batch-job-topic Kafka topic.

On Event:

Sends a detailed email about the batch job.

Sends a WebSocket notification to the frontend.

Notification includes:

✅ Job Name

📈 Status

🕒 Completion Time

🔢 Records Processed

🚀 Tech Stack
Spring Boot

Spring Batch

Spring Kafka

PostgreSQL

WebSocket

Email Service (JavaMailSender)

Docker (optional)

🛠️ How to Run
1.Start Kafka and PostgreSQL .

2.Run the batch project.

3.Trigger job:

GET http://localhost:8096/startJob

4.Run the Notification project.

5.Receive notifications on success (email + WebSocket frontend).
