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
    id bigint NOT NULL,
    description character varying(255) COLLATE pg_catalog."default",
    donationdt timestamp without time zone,
    donoraddress1 character varying(255) COLLATE pg_catalog."default",
    donoraddress2 character varying(255) COLLATE pg_catalog."default",
    donorcity character varying(255) COLLATE pg_catalog."default",
    donorcountry character varying(255) COLLATE pg_catalog."default",
    donorid character varying(255) COLLATE pg_catalog."default",
    donorname character varying(255) COLLATE pg_catalog."default",
    donorphone character varying(255) COLLATE pg_catalog."default",
    donorstate character varying(255) COLLATE pg_catalog."default",
    donorzipcode character varying(255) COLLATE pg_catalog."default",
    status character varying(255) COLLATE pg_catalog."default",
    volunteerid character varying(255) COLLATE pg_catalog."default",
    volunteername character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT donation_pkey PRIMARY KEY (id)
   );

-- 12-16-20 To track all the events

DROP TABLE event_Log;
CREATE TABLE event_Log(
  id serial PRIMARY KEY,
  participantId integer ,
  eventLoginTime timestamp NOT NULL DEFAULT NOW(),
  eventName VARCHAR (200) ,
  logData VARCHAR (10000)
);


