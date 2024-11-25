# Info


Project Overview:
The Form Tracker application is built using the Spring Boot framework. 
It provides a RESTful API for managing forms and their fields. The application supports user authentication 
with JWT tokens, CRUD operations for key entities, and daily statistics generation for submitted forms.

# Prerequisites

Before running the application, make sure you have the following installed:

1. Maven: For building the application.
3. Java (JDK 17): To run the application locally.
4. MySQL: A MySQL server running for the database.

# Installation and Setup

1. Clone the repository:
    - ```git clone https://github.com/MonaDjordjevic/form-tracker.git ```
    - ```cd form-tracker```
2. MySQL: Ensure that MySQL is running on your local machine and create database for the project
3. Database Configuration: Update your application.yml to reflect the local MySQL database configuration:
```yaml
   spring:
    datasource:
      url: jdbc:mysql://localhost:3306/form_tracker
      username: your_username
      password: your_password
```
4. Build the application with Maven:
    - ```mvn clean install```
5. Run the application: Using your 
IDE (such as IntelliJ or Eclipse) or using the command line with Maven: 
   - ```mvn spring-boot:run```

# REST API Endpoints
You can view and interact with the available endpoints through the Swagger UI by navigating to:
http://localhost:8080/form-tracker/swagger-ui.html

Before interacting with  endpoints, authentication is required to obtain a valid JWT token, 
which will grant access based on your user role. Follow the steps below to authorize your requests:
- After obtaining the JWT token using the **POST /authenticate** endpoint, click on the **green Authorize button**
located at the top-right corner of the Swagger UI.
 - A pop-up window will appear. Paste your token in the text field.
 - Click "Authorize" to complete the process. Once the token is authorized, the lock icons next to the endpoints will indicate that you are authenticated, and you can interact with the secured endpoints based on your role (ADMIN or WORKER).

Explanation of the endpoints:
- **POST /authenticate**: Authenticate and get a JWT token.
  How it works:
    - You use this endpoint to authenticate and obtain a JWT token by providing a valid username and password.
    - This token will then be used for subsequent requests to all other endpoints because they require authentication.
    - **Important**: This authentication requires the username and password of a user that already exists in the database.
      Users pre-loaded in the database:
        - **Admin User:**
            - Username: admin
            - Password: password
            - Role: ADMIN
              - The admin user has full access to all endpoints and is authorized to perform POST and PUT operations on forms and fields.
        - **Worker User:**
          - Username: user
          - Password: pass123
          - Role: WORKER
            - The worker user has limited permissions: Not Allowed: POST or PUT requests for managing forms and fields,
              However, the worker can fill out forms and fields, meaning they are permitted to perform POST and PUT 
              operations for creating or updating filled forms and filled fields.
          
    - You can authenticate using any of these credentials to obtain a valid JWT token.
      - **Request body example**:
        ```json
        {
          "username": "admin",
          "password": "password"
        }
        ```

**Form Endpoints**
- **POST /api/forms:** Create a new form.
    - Request Body Example:
       ```json
       {
        "name": "Example name"
       }
       ```
- **POST /api/forms/{formId}/fields:** Add multiple fields to a specific form by its ID. Each field in the
  request body must include unique displayOrder to define the order of the fields in the form. 
  The type field can only be one of the following values: **TEXT** and **NUMBER**
    - Request Body Example:
     ```json
      [
        {
          "name": "Surname",
          "displayOrder": 1,
          "type": "TEXT"
        },
        {
          "name": "Age",
          "displayOrder": 2,
          "type": "NUMBER"
        }
       ]
   ```
- **GET /api/forms/{id}:** Retrieve a specific form by its ID along with a list of its associated fields.
- **GET /api/forms:** Retrieve a list of all forms along with a list of their associated fields, with pagination.
- **PUT /api/forms/{id}:** Update a specific form by its ID.
- **DELETE /api/forms/{id}:** Delete a specific form by its ID.

**Filled Form Endpoints**

- **POST /api/filled-forms/form/{formId}:** Create a new filled form for a specific form ID. This endpoint allows you
  to create a filled form along with its filled fields. Each filled field must reference an existing ``fieldId`` that belongs to the specified form.
  Depending on the type of the field: Use ``numberValue`` for fields of type NUMBER. Use ``textValue`` for fields of type TEXT.
  The form cannot be saved if no fields are filled.
    - Request Body Example:
     ```json
  {
       "filledFields": [
         {
           "fieldId": 1,
           "numberValue": 34.6
         },
         {
           "fieldId": 2,
           "textValue": "Surname"
         }
       ]
  }
   ```
- **GET /api/filled-forms/{id}:** Retrieve a specific filled form by its ID.
- **GET /api/filled-forms:** Retrieve a list of all filled forms with pagination.
- **DELETE /api/filled-forms/{id}:** Delete a specific filled form by its ID.

**Field Endpoints**
 - **GET /api/fields/{id}:** Retrieve a specific field by its ID.
 - **GET /api/fields:** Retrieve a list of all fields.
 - **PUT /api/fields/{id}:** Update a specific field by its ID. The **type** field can only be one of the following 
values: **TEXT** and **NUMBER**
     - Request Body Example
      ```json
      {
         "name": "string",
         "displayOrder": 1,
         "type": "TEXT"
      }
      ```
 - **DELETE /api/fields/{id}:** Delete a specific field by its ID.

 **Filled Field Endpoints**
   - **GET /api/filled-fields/{id}:** Retrieve a specific filled field by its ID.
   - **GET /api/filled-fields:** Retrieve a list of all filled fields.
   - **PUT /api/filled-fields/{id}:** Update a specific filled field by its ID.
     The **textValue** field is used when the field type is **TEXT**.
     The **numberValue** field is used when the field type is **NUMBER**.
     - Request Body Example:
        ```json
        {
         "textValue": "Example text"
        }
        ```

   - **DELETE /api/filled-fields/{id}:** Delete a specific filled field by its ID.

**Audit Information**

For each entity (forms, fields, filled forms, and filled fields), the following metadata is tracked:

 - Created At: The timestamp when the entity was created. 
 - Updated At: The timestamp when the entity was last updated.
 - Created By: The ID of the user who created the entity.
 - Updated By: The ID of the user who last updated the entity.