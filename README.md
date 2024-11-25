# Charge-Config-Log-Service

This repository contains the implementation of a service to handle the retrieval of content, processing it to save in the inbox, and performing a charge operation. Based on the result of the charging process, logs are stored in either a success log table or a failure log table.

The core logic of this project is implemented in the ContentRetrievalProcessService class. Its responsibilities include:
Fetching the content for processing.
Storing the content in the inbox.
Retrieving the unlock code.
Initiating the charge process.
Logging results based on success or failure.
This service acts as the primary processing unit of the application.
