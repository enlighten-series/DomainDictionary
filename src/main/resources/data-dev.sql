insert into domain (
  id
  ,name
  ,format
  ,description
  ,existential
  ,created
  ,updated
) values (
  1
  ,'DBドメイン１'
  ,'AAAAA'
  ,'DBドメイン１説明'
  ,'DBドメイン１理由'
  ,CURRENT_TIMESTAMP()
  ,CURRENT_TIMESTAMP()
);

insert into domain (
  id
  ,name
  ,format
  ,description
  ,existential
  ,created
  ,updated
) values (
  2
  ,'DBドメイン２'
  ,'BBBBB'
  ,'DBドメイン２説明'
  ,'DBドメイン２理由'
  ,CURRENT_TIMESTAMP()
  ,CURRENT_TIMESTAMP()
);

insert into metadata values (
  'major_version'
  ,'1'
  ,CURRENT_TIMESTAMP()
  ,CURRENT_TIMESTAMP()
);

insert into metadata values (
  'minor_version'
  ,'2'
  ,CURRENT_TIMESTAMP()
  ,CURRENT_TIMESTAMP()
);

insert into metadata values (
  'patch_version'
  ,'3'
  ,CURRENT_TIMESTAMP()
  ,CURRENT_TIMESTAMP()
);