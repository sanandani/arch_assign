Files/Folders:
1. Application.zip contains the NewEEP.jar application
2. NewEEP folder - contains the source code

Steps to run the application:

1. Please run the "createDB.sql" file first to create the databases;
2. We have used Mysql Username: remote and password: remote_pass
3. We have created below default accounts:
username pwd role
trail1   1   ORDER
trail2   2   IT(inventory)
trail3   3   SHIPPING
4. Our system implements role-based access, meaning only users that have a "ORDER" role can access the order view.
5. Unzip Application.zip 
6. In console type java -jar "NewEEP.jar" to run the application