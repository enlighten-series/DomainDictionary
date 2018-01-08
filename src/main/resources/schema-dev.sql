CREATE TABLE domain (
    id IDENTITY
    ,name TEXT NOT NULL
    ,format TEXT
    ,description TEXT
    ,existential TEXT
    ,created TIMESTAMP
    ,updated TIMESTAMP
);

CREATE TABLE metadata (
    key TEXT NOT NULL
    ,value TEXT
    ,created TIMESTAMP
    ,updated TIMESTAMP
);
