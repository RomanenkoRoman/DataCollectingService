# Data Collecting Service

## Technologies:
1. Java 8
2. Hibernate 5.x
3. Play Framework 2.5.x (scala-templating)
4. Database: PostgreSQL 9.x
5. Bootstrap, HTML, CSS, JavaScript/JQuery

Application is designed to collect data from users. 
Form for data collection will have generic number of fields.
Field is an entity from database.

## Application consist of 4 pages:
1. List of field page. (http://localhost:9000/fields)
2. Field create/edit page. (http://localhost:9000/FIELDUUID)
3. Responses page. (http://localhost:9000/responses)
4. Response collecting page. (http://localhost:9000)

The application have the roles distinction. There is 2 roles accessible - Admin and Anonymous User. The administrator is redefined in the database. An application authorization is available for Admin. Only admin have access to pages such as List of field page, Field create/edit page, Responses page. Anonymous User have access only to Response collecting page.

In the root of project there is sql script, which adds admin record to
data base with login=admin, password=admin

## To run application:
1. Download ZIP
2. Unzip it.
3. Open the console in the project directory.
4. Enter without the quotes 'sbt'
5. When the loading is ends: enter 'compile' without quotes.
6. After successful compilation: enter 'run' without quotes.
7. If your settings of database are not default, change them in the file conf/application.conf on lines 337-340
8. Run the sql script (ADD_ADMIN_SCRIPT.SQL).
9. Then open in your browser 'localhost:9000'.
10. Click on 'Log in' and enter login - 'admin', password - 'admin'.
11. Click on 'Fields' and add some fields to form.
12. Then go to http://localhost:9000 and fill them.
13. Go to http://localhost:9000/responses.
