if exists(select * from sys.databases where name='Shop')
begin
    drop database Shop;
end

create database Shop;

use Shop;

create table Users(
username varchar(30) primary key,
psw		 varchar(30),
AUTH	 int not null,
UCIF     varchar(20),
Uaddr    varchar(200)
)

create table Good(
Gno     varchar(20) primary key,
Gname   varchar(50) not null,
Gprice  dec(10,4) not null,
Ginfo   varchar(200),
Brand   varchar(50),
Spec    varchar(50),
MATL    varchar(50),
CATEG   varchar(50),
EXPdate int
)

create table Supplier(
Sno   varchar(20) primary key,
Sname varchar(50),
SCIF  varchar(20)
)

create table Supplier_Good(
Sno     varchar(20),
Gno     varchar(20),
Inprice dec(10,4),
Infee   dec(10,4),
constraint PK_SG primary key(Sno,Gno),
constraint FK_SG_S foreign key (Sno) references Supplier(Sno)
    on update cascade,
constraint FK_SG_G foreign key (Gno) references Good(Gno)
    on update cascade
)

create table Purchase(
Pno     varchar(20) primary key,
PBno    varchar(20),
Gno     varchar(20) not null,
Sno     varchar(20) not null,
Ptime   datetime,
Pnum    int,
Pprice  dec(10,4),
Pperson varchar(30),
Pflag	int,
constraint FK_P_G foreign key (Gno) references Good(Gno)
    on update cascade,
constraint FK_P_S foreign key (Sno) references Supplier(Sno)
    on update cascade,
constraint FK_P_U foreign key (Pperson) references Users(username)
    on update cascade
)

create table Orders(
Ono    varchar(20) primary key,
OBno   varchar(20),
Obuyer varchar(30) not null,
Gno    varchar(20) not null,
Onum   int,
Otime  datetime,
Oprice dec(10,4),
Oinfo  int,
constraint FK_O_U foreign key (Obuyer) references Users(username)
    on update cascade,
constraint FK_O_G foreign key (Gno) references Good(Gno)
    on update cascade
)

create table Store(
Pno  varchar(20) primary key,
Snum int,
constraint FK_S_O foreign key (Pno) references Purchase(Pno)
    on update cascade
)

create table Discount(
Dno   varchar(20) primary key,
Dflag int not null,
Gno1  varchar(20),
Gno2  varchar(20),
Gno3  varchar(20),
Gno4  varchar(20),
Gno5  varchar(20),
PCT   dec(10,4),
TPR   dec(10,4),
constraint FK_D_S1 foreign key (Gno1) references Good(Gno)
    on update no action,
constraint FK_D_S2 foreign key (Gno2) references Good(Gno)
    on update no action,
constraint FK_D_S3 foreign key (Gno3) references Good(Gno)
    on update no action,
constraint FK_D_S4 foreign key (Gno4) references Good(Gno)
    on update no action,
constraint FK_D_S5 foreign key (Gno5) references Good(Gno)
    on update no action
)

go
create view V_Sell as
    select G.Gno,G.Gname,G.Gprice,sum(S.Snum) as Ssum
    from Store S,Purchase P,Good G
    where S.Pno=P.Pno and P.Gno=G.Gno
    group by G.Gno,G.Gname,G.Gprice
go
select * from V_Sell