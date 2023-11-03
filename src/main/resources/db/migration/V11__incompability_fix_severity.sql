alter table "Incompatibility"
    alter column severity type SMALLINT;


update "Incompatibility"
    set severity = 2
    where severity = 4;