use  notify;
create table Template
(
 ID int primary key auto_increment,
 Subjet varchar(300),
 Content varchar(500),
 Language varchar (50),
 numofPlace int
);
create table NotifQueue
(
 ID int primary key auto_increment,
 Subjet varchar(300),
 Content varchar(500),
 Sender varchar(50),
 Language varchar (50),
 ch int ,
 statue varchar(15)
);
insert into template  (Subjet,Content,Language,numofPlace)values("confirming","Dear {x} , your booking of the {y} is confirmed. thanks for using our store :) ","Arabic",1);
select *from template;
select *from NotifQueue;
INSERT INTO NotifQueue (Subjet,Content,Sender,Language,channel,statue) VALUES("helooo","hahahhahahahahahahahahah","salma","english",0,"sucesss");

