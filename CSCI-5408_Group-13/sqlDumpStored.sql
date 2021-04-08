
create table courseSelector ( cs_id int primary key , professor_id int , course_id int , constraint fk1 foreign key (professor_id) references professor ( professor_id ) ) ;
