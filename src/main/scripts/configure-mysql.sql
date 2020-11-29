## Use to run mysql db docker image, optional if you're not using a local mysqldb
# docker run --name mysqldb -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql

# connect to mysql and run as root user
#Create Databases
CREATE DATABASE sf_recipe_dev;
CREATE DATABASE sf_recipe_prod;

#Create database service accounts
CREATE USER 'sf_dev_user'@'localhost' IDENTIFIED BY 'manj';
CREATE USER 'sf_prod_user'@'localhost' IDENTIFIED BY 'manj';

#not needed, only for docker mysql, so not run
#CREATE USER 'sf_dev_user'@'%' IDENTIFIED BY 'manj';
#CREATE USER 'sf_prod_user'@'%' IDENTIFIED BY 'manj';

#Database grants
GRANT SELECT ON sf_recipe_dev.* to 'sf_dev_user'@'localhost';
GRANT INSERT ON sf_recipe_dev.* to 'sf_dev_user'@'localhost';
GRANT DELETE ON sf_recipe_dev.* to 'sf_dev_user'@'localhost';
GRANT UPDATE ON sf_recipe_dev.* to 'sf_dev_user'@'localhost';
GRANT SELECT ON sf_recipe_prod.* to 'sf_prod_user'@'localhost';
GRANT INSERT ON sf_recipe_prod.* to 'sf_prod_user'@'localhost';
GRANT DELETE ON sf_recipe_prod.* to 'sf_prod_user'@'localhost';
GRANT UPDATE ON sf_recipe_prod.* to 'sf_prod_user'@'localhost';

#not needed, so not run
GRANT SELECT ON sf_recipe_dev.* to 'sf_dev_user'@'%';
GRANT INSERT ON sf_recipe_dev.* to 'sf_dev_user'@'%';
GRANT DELETE ON sf_recipe_dev.* to 'sf_dev_user'@'%';
GRANT UPDATE ON sf_recipe_dev.* to 'sf_dev_user'@'%';
GRANT SELECT ON sf_recipe_prod.* to 'sf_prod_user'@'%';
GRANT INSERT ON sf_recipe_prod.* to 'sf_prod_user'@'%';
GRANT DELETE ON sf_recipe_prod.* to 'sf_prod_user'@'%';
GRANT UPDATE ON sf_recipe_prod.* to 'sf_prod_user'@'%';