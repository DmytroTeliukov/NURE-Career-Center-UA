
create table if not exists accounts (
    id_account varchar not null primary key,
    lastname varchar not null,
    firstname varchar not null,
    patronymic varchar not null,
    phone varchar not null unique,
    email varchar not null unique,
    `role` varchar not null,
    `password` varchar not null,
    is_active bit not null default 1,
    joined timestamp not null,
    last_login timestamp not null,
    last_updated timestamp not null,
    is_non_locked bit not null default 1
);