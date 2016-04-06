
drop table user_sessions;
create table user_sessions(
	session_id varchar(1024) primary key,
	user_name varchar(128),
	session_start_time timestamp,
	session_exp_time timestamp

)

drop table notification_registry;
create table notification_registry (
	rec_id int,
	user_name varchar(128),
	src_currency_code varchar(128),
	dst_currency_code varchar(128),
	status varchar(32),
	notic_period  varchar(32),
	threshold_value float

)