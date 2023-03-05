create table accounts (
    id_account varchar(36) not null,
    lastname varchar(30) not null,
    firstname varchar(30) not null,
    patronymic varchar(30) not null,
    phone varchar(13) not null unique,
    email varchar(100) not null unique,
    `role` varchar(30) not null,
    `password` varchar(256) not null,
    is_active bit not null default 1,
    primary key (id_account),
    UNIQUE INDEX `uq_accounts_phone` (`phone` ASC) VISIBLE,
    UNIQUE INDEX `uq_accounts_email` (`email` ASC) VISIBLE
) ENGINE = InnoDB;


