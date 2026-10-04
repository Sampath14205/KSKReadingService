# WhatsApp Reader - Java 17

Spring Boot application for receiving incoming WhatsApp Cloud API webhook messages.

## Run

Set your webhook verify token:

Windows PowerShell:
`$env:WHATSAPP_VERIFY_TOKEN="my-secret-token"`

Linux/macOS:
`export WHATSAPP_VERIFY_TOKEN="my-secret-token"`

Then:

`mvn clean spring-boot:run`

Webhook:
`POST /webhook/whatsapp`

Meta verification:
`GET /webhook/whatsapp`

Received messages:
`GET /api/messages`

## Meta callback

Configure Meta with:

`https://YOUR-PUBLIC-HTTPS-URL/webhook/whatsapp`

Use the same value configured in `WHATSAPP_VERIFY_TOKEN`.

Subscribe to the WhatsApp `messages` webhook field.

## Test locally

Meta cannot call localhost directly. Use an HTTPS tunnel such as ngrok:

`ngrok http 8080`

Then use the generated HTTPS URL in Meta.

When a user sends "Hello", the application extracts the sender number, contact name, message ID, type and text, logs them, stores them in memory, and returns HTTP 200 with `EVENT_RECEIVED`.

## Read messages

`curl http://localhost:8080/api/messages`

## Build

`mvn clean package`

Executable JAR:

`target/whatsapp-reader.jar`

Run:

`java -jar target/whatsapp-reader.jar`

## Production note

This sample uses in-memory storage. Messages disappear after restart. For production, replace it with PostgreSQL/MySQL/MongoDB and persist message ID, sender, contact, type, text, timestamps, status and raw payload.

For a larger messaging platform, process webhook events asynchronously through Kafka/RabbitMQ and return HTTP 200 quickly.
