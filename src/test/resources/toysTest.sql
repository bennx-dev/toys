insert into orders (ordered, required, shipped, comments, customerId, status, version)
values ('2005-04-07', '2005-04-14', null, null, 1, 'SHIPPED', 1)
, ('2005-05-07', '2005-05-14', null, null, 1, 'PROCESSING', 1)
, ('2005-06-07', '2005-06-14', null, null, 1, 'CANCELLED', 1);

insert into customers (name, streetAndNumber, city, state, postalCode, countryId, version)
values ('Atelier graphique Mini', '54, rue Royale', 'Nantes', null, 44000, 7, 1)
     , ('Atelier graphique Maxi', '164, rue Royale', 'Nantes', null, 44000, 7, 1);