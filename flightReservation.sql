drop database if exists flightReservation;
create database flightReservation;
use flightReservation;

drop table if exists user;
create table user
(uID INT AUTO_INCREMENT Primary Key,
 uNAME VARCHAR(30) NOT NULL,
age INT);
ALTER Table user AUTO_INCREMENT = 100;


drop table if exists flightList;
create table flightList
(fID INT AUTO_INCREMENT Primary Key,
aName VARCHAR(30) NOT NULL,
numSeats INT NOT NULL);
ALTER Table flightList AUTO_INCREMENT = 200;


create table reservation
(uID INT, 
 fID INT,
 reservedDate Date NOT NULL Default '0000-00-00' ,
 updatedAt timestamp NOT NULL on update current_timestamp default current_timestamp,
 PRIMARY KEY (uID,fID,reservedDate),
 FOREIGN KEY (uID) references user (uID) on delete cascade,
 FOREIGN KEY (fID) references flightList (fID) on delete cascade);


drop table if exists seat;
create table seat
(uID INT,
fID INT,
seatNumber INT NOT NULL auto_increment UNIQUE,
 PRIMARY KEY (uID,fID,seatNumber),
 FOREIGN KEY (uID) references user (uID) on delete cascade,
 FOREIGN KEY (fID) references flightList (fID) on delete cascade);
ALTER Table seat AUTO_INCREMENT = 300;

drop table if exists canceledReservation;
create table canceledReservation
(uID INT,
 fID INT,
 canceledDate Date NOT NULL Default '0000-00-00',
 reservedDate Date NOT NULL Default '0000-00-00',
 PRIMARY KEY (uID,fID,canceledDate)
);

drop table if exists Archive;
create table Archive
(uID INT, 
 fID INT,
updatedAt timestamp NOT NULL);



/* Trigger : Automatically insert into seat when a reservation is made */

DROP TRIGGER IF EXISTS INSERTTORES;
delimiter //
CREATE TRIGGER INSERTTORES
AFTER INSERT ON RESERVATION
FOR EACH ROW
BEGIN
 insert into seat(uID,fID) values(new.uID,new.fID);
END;//
delimiter ;


/* Trigger : Automatically delete from reservation and seat when query is entered into canceledReservation */
/*
DROP TRIGGER IF EXISTS INSERTTOCANCEL;
delimiter //
CREATE TRIGGER INSERTTOCANCEL
AFTER INSERT ON CANCELEDRESERVATION
FOR EACH ROW
BEGIN
 delete from reservation where new.uID = uID and new.fID = fID and new.reservedDate = reservedDate;
 delete from seat where new.uID = uID and new.fID = fID;
END;//
delimiter ;
*/
DROP TRIGGER IF EXISTS INSERTTOCANCEL;
delimiter //
CREATE TRIGGER INSERTTOCANCEL
AFTER delete ON reservation
FOR EACH ROW
BEGIN
 insert into canceledreservation(uid,fid,canceledDate,reservedDate) values (old.uid,old.fid,current_date(),old.reservedDate);
 delete from seat where uID = uID and fID = fID;
END;//
delimiter ;


drop PROCEDURE if exists archivedReservation;
delimiter //
create PROCEDURE archivedReservation(IN value varchar(55))
BEGIN
insert into Archive select uid,fid,updatedAt from reservation where date(updatedAt) < value ;
delete from reservation where date(updatedAt) < value;
END; //
delimiter ;
