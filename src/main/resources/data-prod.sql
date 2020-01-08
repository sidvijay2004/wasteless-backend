CREATE TABLE participant(
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

INSERT INTO participant(name,email,password,phone,address1,address2,city,state,zipcode,country) values('abc','abc@xyz.com','abc','98842','abc','abcxdde','buffalo','ny','14214','usa');


CREATE TABLE Donation(
   id serial PRIMARY KEY,
   name VARCHAR (50) NOT NULL,
   DonorId integer NOT NULL,
   Status VARCHAR(20) NOT NULL,
   DonationDt TIMESTAMP NOT NULL,
   volunteerId integer NOT NULL
   );
