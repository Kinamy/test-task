INSERT INTO CLIENT (ID, FIRST_NAME, LAST_NAME, PATRONYMIC, PHONE) VALUES
(1, 'Петр', 'Петров', 'Петрович', '89996786778'),
(2, 'Сергей', 'Сергеев', 'Сергеевич', '89223454455'),
(3,'Михаил','Михайлов','Михайлович','89453453454');

INSERT INTO MECHANIC (ID, FIRST_NAME, LAST_NAME, PATRONYMIC, SALARY)
VALUES (1, 'Григорий', 'Григорьев', 'Сергеевич', 444.0),
 (2, 'Иванов', 'Иван', 'Иванович', 234.0),
 (3,'Олег','Газманов','Олегович', 555.0);

INSERT INTO ORDERS (ID,DESCRIPTION,CLIENT,MECHANIC,CREATE_DATE,END_DATE, PRICE, STATUS) VALUES
(1, 'Смазать детали', 1, 1, '2020-08-25', '2020-10-30', 569.34, 'ЗАПЛАНИРОВАН'),
(2, 'Покрасить крыло', 3, 2, '2020-08-25', '2020-08-31', 333.22, 'ВЫПОЛНЕН'),
(3, 'Заменить масло', 1, 1, '2020-08-25', '2020-09-29', 2345.23, 'ПРИНЯТ'),
(4, 'Проверить уровень масла', 2, 3, '2020-08-25', '2020-11-28', 4234.12, 'ЗАПЛАНИРОВАН');