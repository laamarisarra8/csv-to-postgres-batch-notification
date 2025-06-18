This project processes CSV transaction data using Spring Batch, stores it in PostgreSQL, and notifies users via email and WebSocket through a Kafka-driven notification system.


**csv-to-postgres-batch-notification** consists of two Spring Boot applications:
1. **Batch**: Imports transaction data from `transactions.csv` (in `/src/main/resources/transactions.csv`) into a PostgreSQL database using Spring Batch. Triggered via `GET http://localhost:8096/startJob`
    it publishes job completion events to a Kafka topic (`batch-job-topic`).
   **Transaction.csv:
transaction_id,account_number,transaction_date,transaction_type,transaction_amount
1	123456789	2023-08-23 09:15:00	D	100.00
2	987654321	2023-08-22 14:30:00	W	50.00
3	456789123	2023-08-21 11:45:00	D	200.00
4	789123456	2023-08-20 16:20:00	W	75.00
...



   
3. **Notification**: Consumes Kafka events and sends:
   - Emails with job details (Job Name, Status, Completion Time, Records Processed).
   
