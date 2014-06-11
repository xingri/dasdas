CREATE USER 'spartan'@'localhost' IDENTIFIED BY 'spartan';
CREATE USER 'spartan'@'%' IDENTIFIED BY 'spartan';
GRANT ALL PRIVILEGES ON spartan.* TO 'spartan'@'localhost';
GRANT ALL PRIVILEGES ON spartan.* TO 'spartan'@'%';
FLUSH PRIVILEGES;
