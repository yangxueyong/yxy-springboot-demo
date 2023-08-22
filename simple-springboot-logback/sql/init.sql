create table custom_log(
    `id` bigint  auto_increment primary key,
     logTime datetime not null,
     logThread varchar(200),
     logClass varchar(200),
     logMethod varchar(200),
     logLineNum bigint,
     logLevel varchar(200),
     trackId varchar(200),
     logContent varchar(200)
)
    