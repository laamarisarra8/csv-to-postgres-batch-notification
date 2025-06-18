CSV-to-PostgreSQL Batch Notification System
Welcome to the CSV-to-PostgreSQL Batch Notification System! This project showcases a robust Spring Boot-based solution for processing CSV transaction data, storing it in a PostgreSQL database, and notifying users via email and WebSocket through a Kafka-driven notification system. It's designed to be efficient, scalable, and easy to use.
Project Overview
This repository contains two Spring Boot applications working together to handle transaction data processing and notifications:

Batch Application:

Purpose: Imports transaction data from a CSV file into a PostgreSQL database using Spring Batch.
Input: Reads from transactions.csv located in /src/main/resources/.
Trigger: Start the batch job via a simple GET request: http://localhost:8096/startJob.
Output: Publishes job completion events to a Kafka topic (batch-job-topic) for further processing.
CSV Format Example:transaction_id,account_number,transaction_date,transaction_type,transaction_amount
1,123456789,2023-08-23 09:15:00,D,100.00
2,987654321,2023-08-22 14:30:00,W,50.00
3,456789123,2023-08-21 11:45:00,D,200.00
4,789123456,2023-08-20 16:20:00,W,75.00




Notification Application:

Purpose: Listens for Kafka events from the batch-job-topic and sends notifications.
Notifications:
Email: Delivers detailed job summaries, including Job Name, Status, Completion Time, and Records Processed.
WebSocket: Provides real-time updates to connected clients.





Features

Efficient Data Processing: Leverages Spring Batch for reliable and scalable CSV processing.
Real-Time Notifications: Uses Kafka to ensure seamless communication between applications.
Dual Notification Channels: Combines email and WebSocket for flexible user updates.
Database Integration: Stores transaction data in PostgreSQL for persistence and querying.
Easy to Trigger: Simple HTTP endpoint to kick off the batch job.

Getting Started

Prerequisites:

Java 17+
PostgreSQL
Kafka
Maven
SMTP server for email notifications


Setup:

Clone the repository: git clone <repository-url>
Configure PostgreSQL and Kafka settings in application.properties for both applications.
Ensure transactions.csv is placed in /src/main/resources/ of the Batch application.


Running the Applications:

Start the Batch application: mvn spring-boot:run -pl batch
Start the Notification application: mvn spring-boot:run -pl notification
Trigger the batch job: curl http://localhost:8096/startJob


Verify Notifications:

Check your email for job completion details.
Connect to the WebSocket endpoint for real-time updates.



Project Structure
csv-to-postgres-batch-notification/
├── batch/
│   ├── src/main/resources/transactions.csv
│   └── (Spring Batch configuration and job logic)
├── notification/
│   └── (Kafka consumer, email, and WebSocket logic)
└── README.md

Future Enhancements

Add support for multiple CSV formats.
Implement retry mechanisms for failed notifications.
Enhance WebSocket UI for a richer user experience.

Contributing
Contributions are welcome! Feel free to open issues or submit pull requests to improve the project.
