Фикстура и схема данных находится в ./fixture/db.sql и содержит двух пользователей:  
- admin / pass0Word!
- fa / pass1Word!

первый с правами админа, второй -- с обычными.

`psql forum < fixture/db.sql`

База данных по умолчанию "forum".  

Паджинация по 2 элемента на страницу.

Нету CSRF защиты от удаления.  
Поле role в БД подразумевалось быть типа enum, но в процессе сохранения в postgres через hibernate возникала ошибка которой не было с mysql, поэтому оставил как varchar, можно нормализовать выделив role в отдельную таблицу.
