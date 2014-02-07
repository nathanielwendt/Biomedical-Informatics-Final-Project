use medicaldata;

CREATE  TABLE `medicaldata`.`adr` (
	ISR int,
	Drugname varchar(200),
	PT varchar(100),
);

truncate adr;
insert into adr
(Select drug08q1.isr, drug08q1.drugname, reac08q1.pt 
from drug08q1
INNER JOIN reac08q1
ON drug08q1.isr = reac08q1.isr);

select * from adr;