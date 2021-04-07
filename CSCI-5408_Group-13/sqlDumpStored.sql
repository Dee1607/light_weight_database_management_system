create table student ( id int primary key , name varchar(255) , department varchar(255) ) ;
create table course  ( course_id int primary key , course_title varchar(255) ) ;
create table grade ( grade_id int primary key , student_id int , course_id int ) constraint fk1 foreign key (student_id) references student ( student_id ) ;
