CREATE TABLE donor(
   id serial PRIMARY KEY,
   name VARCHAR (50) NOT NULL,
   email VARCHAR (355) UNIQUE NOT NULL,
   password VARCHAR (50) NOT NULL,
   phone VARCHAR(50) NOT NULL,
   address1 VARCHAR(100) NOT NULL,
   address2 VARCHAR(100) NOT NULL,
   city VARCHAR(100) NOT NULL,
   state VARCHAR(100) NOT NULL,
   zipcode VARCHAR(100) NOT NULL,
   country VARCHAR(100) NOT NULL,
   last_login TIMESTAMP
);