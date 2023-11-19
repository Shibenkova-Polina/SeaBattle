create table cells
(
    id int not null primary key,
    Player text not null,
    CellsStates text not null,
    LineNumber text not null
);

create table ships
(
    id int not null primary key,
    Player text not null,
    Data text not null
);