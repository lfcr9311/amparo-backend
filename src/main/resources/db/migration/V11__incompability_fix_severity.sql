alter table "Incompatibility"
    alter column severity type SMALLINT using severity::smallint;


update "Incompatibility"
    set severity = 2
    where severity = 4;