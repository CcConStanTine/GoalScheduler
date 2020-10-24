insert into my_schedule_user (user_id, email, first_name, last_name, nick, password) values (NEXTVAL('myscheduleuser_seq'), 'pawel@gmail.com', 'Pawel', 'Karpiel', 'CcConstantine', 'haslo123');

insert into my_schedule_user (user_id, email, first_name, last_name, nick, password) values (NEXTVAL('myscheduleuser_seq'), 'konradduleba@gmail.com', 'Konrad', 'Duleba', 'duUuleb', 'haslo123');

insert into my_schedule_user (user_id, email, first_name, last_name, nick, password) values (NEXTVAL('myscheduleuser_seq'), 'krzysiek@gmail.com', 'Krzysztof', 'Janowicz', 'krzys', 'haslo123');

insert into role (id, name) values (NEXTVAL('role_seq'), 'ROLE_USER');

insert into role (id, name) values (NEXTVAL('role_seq'), 'ROLE_MODERATOR');

insert into role (id, name) values (NEXTVAL('role_seq'), 'ROLE_ADMIN');

insert into my_schedule_user_roles (my_schedule_user_id, role_id) values (1, 1);

insert into my_schedule_user_roles (my_schedule_user_id, role_id) values (2, 1);

insert into my_schedule_user_roles (my_schedule_user_id, role_id) values (3, 1);

insert into schedule (schedule_id, user_id) values (NEXTVAL('schedule_seq'), 1);

insert into schedule (schedule_id, user_id) values (NEXTVAL('schedule_seq'), 2);

insert into schedule (schedule_id, user_id) values (NEXTVAL('schedule_seq'), 3);

insert into long_term_plan (long_term_plan_id, content, start_date, end_date, is_fulfilled, schedule_id) values (NEXTVAL('longtermplan_seq'), 'Otwórz firmę', '2021-01-01', '2026-01-01', false, 1);

insert into long_term_plan (long_term_plan_id, content, start_date, end_date, is_fulfilled, schedule_id) values (NEXTVAL('longtermplan_seq'), 'Zatrudniaj 10 ludzi', '2021-10-01', '2026-01-01', false, 1);

insert into long_term_plan (long_term_plan_id, content, start_date, end_date, is_fulfilled, schedule_id) values (NEXTVAL('longtermplan_seq'), 'Wybuduj dom', '2021-01-01', '2030-01-01', false, 1);

insert into long_term_plan (long_term_plan_id, content, start_date, end_date, is_fulfilled, schedule_id) values (NEXTVAL('longtermplan_seq'), 'Zasadź drzewo xD', '2021-01-01', '2030-01-01', false, 1);

insert into long_term_plan (long_term_plan_id, content, start_date, end_date, is_fulfilled, schedule_id) values (NEXTVAL('longtermplan_seq'), 'Znajdz pracę jako front dev', '2021-01-01', '2023-01-01', false, 2);

insert into long_term_plan (long_term_plan_id, content, start_date, end_date, is_fulfilled, schedule_id) values (NEXTVAL('longtermplan_seq'), 'Zrób 700 total w trójboju', '2021-01-01', '2024-01-01', false, 2);

insert into long_term_plan (long_term_plan_id, content, start_date, end_date, is_fulfilled, schedule_id) values (NEXTVAL('longtermplan_seq'), 'Uzbieraj hajs na nowy samochód (200 tyś)', '2020-11-01', '2024-12-30', false, 2);

insert into long_term_plan (long_term_plan_id, content, start_date, end_date, is_fulfilled, schedule_id) values (NEXTVAL('longtermplan_seq'), 'Zdobądź tytuł magistra', '2021-01-01', '2025-01-01', false, 3);

insert into long_term_plan (long_term_plan_id, content, start_date, end_date, is_fulfilled, schedule_id) values (NEXTVAL('longtermplan_seq'), 'Uzbieraj hajs na nowy samochód (300 tyś)', '2020-11-01', '2024-12-30', false, 3);

insert into year (year_id, year_number, is_leap_year, days_amount, schedule_id) values (NEXTVAL('year_seq'), 2019, false, 365, 1);

insert into year (year_id, year_number, is_leap_year, days_amount, schedule_id) values (NEXTVAL('year_seq'), 2020, true, 366, 1);

insert into year (year_id, year_number, is_leap_year, days_amount, schedule_id) values (NEXTVAL('year_seq'), 2021, false, 365, 1);

insert into year (year_id, year_number, is_leap_year, days_amount, schedule_id) values (NEXTVAL('year_seq'), 2020, true, 366, 2);

insert into year (year_id, year_number, is_leap_year, days_amount, schedule_id) values (NEXTVAL('year_seq'), 2021, false, 365, 2);

insert into year (year_id, year_number, is_leap_year, days_amount, schedule_id) values (NEXTVAL('year_seq'), 2020, true, 366, 3);


insert into year_plan (year_plan_id, content, start_date, end_date, is_fulfilled, year_id) values (NEXTVAL('yearplan_seq'), 'Nie uwal żadnego przedmiotu na studiach', '2019-01-01', '2019-12-31', true, 1);

insert into year_plan (year_plan_id, content, start_date, end_date, is_fulfilled, year_id) values (NEXTVAL('yearplan_seq'), 'Zrób 500 w totalu w trójboju', '2019-01-01', '2019-12-31', true, 1);

insert into year_plan (year_plan_id, content, start_date, end_date, is_fulfilled, year_id) values (NEXTVAL('yearplan_seq'), 'Znajdź pracę jako back end dev', '2019-01-01', '2019-12-31', false, 1);

insert into year_plan (year_plan_id, content, start_date, end_date, is_fulfilled, year_id) values (NEXTVAL('yearplan_seq'), 'Zdobądź średnią minimum 4.5', '2020-01-01', '2020-12-31', false, 2);

insert into year_plan (year_plan_id, content, start_date, end_date, is_fulfilled, year_id) values (NEXTVAL('yearplan_seq'), 'Znajdź pracę jako back end dev', '2020-01-01', '2020-12-31', false, 2);

insert into year_plan (year_plan_id, content, start_date, end_date, is_fulfilled, year_id) values (NEXTVAL('yearplan_seq'), 'Wyjdź na Rysy', '2020-01-01', '2020-12-31', true, 2);

insert into year_plan (year_plan_id, content, start_date, end_date, is_fulfilled, year_id) values (NEXTVAL('yearplan_seq'), 'Schudnij 5 kg w 3 miesiące do końca roku', '2020-10-01', '2020-12-31', false, 2);

insert into year_plan (year_plan_id, content, start_date, end_date, is_fulfilled, year_id) values (NEXTVAL('yearplan_seq'), 'Znajdź mieszkanie w Krakowie do końca wakacji', '2021-01-01', '2021-09-30', false, 3);

insert into year_plan (year_plan_id, content, start_date, end_date, is_fulfilled, year_id) values (NEXTVAL('yearplan_seq'), 'Dołącz do jakiegoś startupu', '2021-01-01', '2021-12-31', false, 3);


insert into year_plan (year_plan_id, content, start_date, end_date, is_fulfilled, year_id) values (NEXTVAL('yearplan_seq'), 'Znajdz pracę jako front dev', '2020-01-01', '2020-12-31', false, 4);

insert into year_plan (year_plan_id, content, start_date, end_date, is_fulfilled, year_id) values (NEXTVAL('yearplan_seq'), 'Schudnij 3 kg w 2 miesiące', '2020-11-01', '2020-12-31', false, 4);

insert into year_plan (year_plan_id, content, start_date, end_date, is_fulfilled, year_id) values (NEXTVAL('yearplan_seq'), 'Wyciśnij 100 na ławce', '2021-01-01', '2021-12-31', false, 5);

insert into year_plan (year_plan_id, content, start_date, end_date, is_fulfilled, year_id) values (NEXTVAL('yearplan_seq'), 'Wyciśnij 100 na ławce', '2021-01-01', '2021-12-31', false, 5);


insert into year_plan (year_plan_id, content, start_date, end_date, is_fulfilled, year_id) values (NEXTVAL('yearplan_seq'), 'Przebiegnij 50 km maraton', '2020-01-01', '2020-12-31', false, 6);

insert into year_plan (year_plan_id, content, start_date, end_date, is_fulfilled, year_id) values (NEXTVAL('yearplan_seq'), 'Zdaj maturę ', '2020-01-01', '2020-05-31', false, 6);


insert into month (month_id, month_name, days_amount, year_id) values (NEXTVAL('month_seq'), 1, 31, 1);

insert into month (month_id, month_name, days_amount, year_id) values (NEXTVAL('month_seq'), 2, 28, 1);

insert into month (month_id, month_name, days_amount, year_id) values (NEXTVAL('month_seq'), 3, 31, 1);

insert into month (month_id, month_name, days_amount, year_id) values (NEXTVAL('month_seq'), 4, 30, 1);

insert into month (month_id, month_name, days_amount, year_id) values (NEXTVAL('month_seq'), 5, 31, 1);

insert into month (month_id, month_name, days_amount, year_id) values (NEXTVAL('month_seq'), 6, 30, 1);

insert into month (month_id, month_name, days_amount, year_id) values (NEXTVAL('month_seq'), 7, 31, 1);

insert into month (month_id, month_name, days_amount, year_id) values (NEXTVAL('month_seq'), 8, 31, 1);

insert into month (month_id, month_name, days_amount, year_id) values (NEXTVAL('month_seq'), 9, 30, 1);

insert into month (month_id, month_name, days_amount, year_id) values (NEXTVAL('month_seq'), 10, 31, 1);

insert into month (month_id, month_name, days_amount, year_id) values (NEXTVAL('month_seq'), 11, 30, 1);

insert into month (month_id, month_name, days_amount, year_id) values (NEXTVAL('month_seq'), 12, 31, 1);


insert into month (month_id, month_name, days_amount, year_id) values (NEXTVAL('month_seq'), 1, 31, 2);

insert into month (month_id, month_name, days_amount, year_id) values (NEXTVAL('month_seq'), 2, 28, 2);

insert into month (month_id, month_name, days_amount, year_id) values (NEXTVAL('month_seq'), 3, 31, 2);

insert into month (month_id, month_name, days_amount, year_id) values (NEXTVAL('month_seq'), 4, 30, 2);

insert into month (month_id, month_name, days_amount, year_id) values (NEXTVAL('month_seq'), 5, 31, 2);

insert into month (month_id, month_name, days_amount, year_id) values (NEXTVAL('month_seq'), 6, 30, 2);

insert into month (month_id, month_name, days_amount, year_id) values (NEXTVAL('month_seq'), 7, 31, 2);

insert into month (month_id, month_name, days_amount, year_id) values (NEXTVAL('month_seq'), 8, 31, 2);

insert into month (month_id, month_name, days_amount, year_id) values (NEXTVAL('month_seq'), 9, 30, 2);

insert into month (month_id, month_name, days_amount, year_id) values (NEXTVAL('month_seq'), 10, 31, 2);


insert into month (month_id, month_name, days_amount, year_id) values (NEXTVAL('month_seq'), 8, 31, 4);

insert into month (month_id, month_name, days_amount, year_id) values (NEXTVAL('month_seq'), 9, 30, 4);

insert into month (month_id, month_name, days_amount, year_id) values (NEXTVAL('month_seq'), 10, 31, 4);


insert into month (month_id, month_name, days_amount, year_id) values (NEXTVAL('month_seq'), 10, 31, 6);

insert into month_plan (month_plan_id, content, start_date, end_date, is_fulfilled, month_id) values (NEXTVAL('monthplan_seq'), 'Poświęć na rozwój projektu 100h', '2020-09-01', '2020-09-30', true, 21);

insert into month_plan (month_plan_id, content, start_date, end_date, is_fulfilled, month_id) values (NEXTVAL('monthplan_seq'), 'Złóż papiery o stypendium', '2020-09-01', '2020-09-30', true, 21);

insert into month_plan (month_plan_id, content, start_date, end_date, is_fulfilled, month_id) values (NEXTVAL('monthplan_seq'), 'Przeczytaj 2 książki', '2020-09-01', '2020-09-30', true, 21);

insert into month_plan (month_plan_id, content, start_date, end_date, is_fulfilled, month_id) values (NEXTVAL('monthplan_seq'), 'Stwórz CV', '2020-09-01', '2020-09-30', false, 21);


insert into month_plan (month_plan_id, content, start_date, end_date, is_fulfilled, month_id) values (NEXTVAL('monthplan_seq'), 'Poświęć na rozwój projektu 50h', '2020-10-01', '2020-10-31', false, 22);

insert into month_plan (month_plan_id, content, start_date, end_date, is_fulfilled, month_id) values (NEXTVAL('monthplan_seq'), 'Poświęć na rozwój projektu grupowego 50h', '2020-10-01', '2020-10-31', false, 22);

insert into month_plan (month_plan_id, content, start_date, end_date, is_fulfilled, month_id) values (NEXTVAL('monthplan_seq'), 'Przeczytaj książke', '2020-10-01', '2020-10-31', false, 22);

insert into month_plan (month_plan_id, content, start_date, end_date, is_fulfilled, month_id) values (NEXTVAL('monthplan_seq'), 'Wyjdź w góry', '2020-10-01', '2020-10-31', true, 22);


insert into month_plan (month_plan_id, content, start_date, end_date, is_fulfilled, month_id) values (NEXTVAL('monthplan_seq'), 'Dołącz do jakiegoś projektu open source', '2020-10-01', '2020-10-31', false, 25);

insert into month_plan (month_plan_id, content, start_date, end_date, is_fulfilled, month_id) values (NEXTVAL('monthplan_seq'), 'Zabij premiera', '2020-10-01', '2020-10-31', false, 25);

insert into month_plan (month_plan_id, content, start_date, end_date, is_fulfilled, month_id) values (NEXTVAL('monthplan_seq'), 'Wprowadź anarchie w państwie', '2020-10-01', '2020-10-31', false, 25);

insert into month_plan (month_plan_id, content, start_date, end_date, is_fulfilled, month_id) values (NEXTVAL('monthplan_seq'), 'Idź na przegląd do denstysty', '2020-10-01', '2020-10-31', false, 25);


insert into month_plan (month_plan_id, content, start_date, end_date, is_fulfilled, month_id) values (NEXTVAL('monthplan_seq'), 'Zrób coś w ciągu miesiąca', '2020-10-01', '2020-10-31', false, 26);

insert into month_plan (month_plan_id, content, start_date, end_date, is_fulfilled, month_id) values (NEXTVAL('monthplan_seq'), 'Przetestuj swoje maxy', '2020-10-01', '2020-10-31', false, 26);


insert into week (week_id, start_date, end_date, year_id) values (NEXTVAL('week_seq'), '2020-01-01', '2020-01-05', 2);

insert into week (week_id, start_date, end_date, year_id) values (NEXTVAL('week_seq'), '2020-01-06', '2020-01-12', 2);

insert into week (week_id, start_date, end_date, year_id) values (NEXTVAL('week_seq'), '2020-01-13', '2020-01-19', 2);

insert into week (week_id, start_date, end_date, year_id) values (NEXTVAL('week_seq'), '2020-01-20', '2020-01-26', 2);

insert into week (week_id, start_date, end_date, year_id) values (NEXTVAL('week_seq'), '2020-01-27', '2020-02-02', 2);

insert into week (week_id, start_date, end_date, year_id) values (NEXTVAL('week_seq'), '2020-02-03', '2020-02-09', 2);

insert into week (week_id, start_date, end_date, year_id) values (NEXTVAL('week_seq'), '2020-02-10', '2020-02-16', 2);

insert into week (week_id, start_date, end_date, year_id) values (NEXTVAL('week_seq'), '2020-02-17', '2020-02-23', 2);

insert into week (week_id, start_date, end_date, year_id) values (NEXTVAL('week_seq'), '2020-02-24', '2020-03-01', 2);

insert into week (week_id, start_date, end_date, year_id) values (NEXTVAL('week_seq'), '2020-03-02', '2020-03-08', 2);

insert into week (week_id, start_date, end_date, year_id) values (NEXTVAL('week_seq'), '2020-03-09', '2020-03-15', 2);

insert into week (week_id, start_date, end_date, year_id) values (NEXTVAL('week_seq'), '2020-03-16', '2020-03-22', 2);

insert into week (week_id, start_date, end_date, year_id) values (NEXTVAL('week_seq'), '2020-03-23', '2020-03-29', 2);

insert into week (week_id, start_date, end_date, year_id) values (NEXTVAL('week_seq'), '2020-03-30', '2020-04-05', 2);

insert into week (week_id, start_date, end_date, year_id) values (NEXTVAL('week_seq'), '2020-04-06', '2020-04-12', 2);

insert into week (week_id, start_date, end_date, year_id) values (NEXTVAL('week_seq'), '2020-09-01', '2020-09-06', 2);

insert into week (week_id, start_date, end_date, year_id) values (NEXTVAL('week_seq'), '2020-09-07', '2020-09-13', 2);

insert into week (week_id, start_date, end_date, year_id) values (NEXTVAL('week_seq'), '2020-09-14', '2020-09-20', 2);

insert into week (week_id, start_date, end_date, year_id) values (NEXTVAL('week_seq'), '2020-09-21', '2020-09-27', 2);

insert into week (week_id, start_date, end_date, year_id) values (NEXTVAL('week_seq'), '2020-09-28', '2020-10-04', 2);

insert into week (week_id, start_date, end_date, year_id) values (NEXTVAL('week_seq'), '2020-10-05', '2020-10-11', 2);

insert into week (week_id, start_date, end_date, year_id) values (NEXTVAL('week_seq'), '2020-10-12', '2020-10-18', 2);

insert into week (week_id, start_date, end_date, year_id) values (NEXTVAL('week_seq'), '2020-10-19', '2020-10-25', 2);


insert into week_plan (week_plan_id, content, start_date, end_date, is_fulfilled, week_id) values (NEXTVAL('weekplan_seq'), 'jakis tygodniowy plan 1', '2020-10-05', '2020-10-11', false, 21);

insert into week_plan (week_plan_id, content, start_date, end_date, is_fulfilled, week_id) values (NEXTVAL('weekplan_seq'), 'jakis tygodniowy plan 2', '2020-10-05', '2020-10-11', false, 21);

insert into week_plan (week_plan_id, content, start_date, end_date, is_fulfilled, week_id) values (NEXTVAL('weekplan_seq'), 'jakis tygodniowy plan 3', '2020-10-05', '2020-10-11', false, 21);

insert into week_plan (week_plan_id, content, start_date, end_date, is_fulfilled, week_id) values (NEXTVAL('weekplan_seq'), 'Umów się do dentysty', '2020-10-12', '2020-10-18', true, 22);

insert into week_plan (week_plan_id, content, start_date, end_date, is_fulfilled, week_id) values (NEXTVAL('weekplan_seq'), 'Znajdź otwartą siłownię', '2020-10-12', '2020-10-18', false, 22);

insert into week_plan (week_plan_id, content, start_date, end_date, is_fulfilled, week_id) values (NEXTVAL('weekplan_seq'), 'Poświęć na rozwój projektu 30h', '2020-10-12', '2020-10-18', true, 22);

insert into week_plan (week_plan_id, content, start_date, end_date, is_fulfilled, week_id) values (NEXTVAL('weekplan_seq'), 'Poświęć na rozwój projektu 15h', '2020-10-19', '2020-10-25', false, 23);

insert into week_plan (week_plan_id, content, start_date, end_date, is_fulfilled, week_id) values (NEXTVAL('weekplan_seq'), 'Poświęć na rozwój projektu grupowego 15h', '2020-10-19', '2020-10-25', false, 23);

insert into week_plan (week_plan_id, content, start_date, end_date, is_fulfilled, week_id) values (NEXTVAL('weekplan_seq'), 'Przeczytaj 30% książki', '2020-10-19', '2020-10-25', false, 23);

insert into week_plan (week_plan_id, content, start_date, end_date, is_fulfilled, week_id) values (NEXTVAL('weekplan_seq'), 'Zaraź 5 ludzi koronawirusem', '2020-10-19', '2020-10-25', false, 23);


insert into day (day_id, day_name, day_date, month_id, week_id) values (NEXTVAL('day_seq'), 1, '2020-10-12', 22, 22);

insert into day (day_id, day_name, day_date, month_id, week_id) values (NEXTVAL('day_seq'), 2, '2020-10-13', 22, 22);

insert into day (day_id, day_name, day_date, month_id, week_id) values (NEXTVAL('day_seq'), 3, '2020-10-14', 22, 22);

insert into day (day_id, day_name, day_date, month_id, week_id) values (NEXTVAL('day_seq'), 4, '2020-10-15', 22, 22);

insert into day (day_id, day_name, day_date, month_id, week_id) values (NEXTVAL('day_seq'), 5, '2020-10-16', 22, 22);

insert into day (day_id, day_name, day_date, month_id, week_id) values (NEXTVAL('day_seq'), 6, '2020-10-17', 22, 22);

insert into day (day_id, day_name, day_date, month_id, week_id) values (NEXTVAL('day_seq'), 7, '2020-10-18', 22, 22);

insert into day (day_id, day_name, day_date, month_id, week_id) values (NEXTVAL('day_seq'), 1, '2020-10-19', 22, 23);

insert into day (day_id, day_name, day_date, month_id, week_id) values (NEXTVAL('day_seq'), 2, '2020-10-20', 22, 23);

insert into day (day_id, day_name, day_date, month_id, week_id) values (NEXTVAL('day_seq'), 3, '2020-10-21', 22, 23);

insert into day (day_id, day_name, day_date, month_id, week_id) values (NEXTVAL('day_seq'), 4, '2020-10-22', 22, 23);

insert into day (day_id, day_name, day_date, month_id, week_id) values (NEXTVAL('day_seq'), 5, '2020-10-23', 22, 23);

insert into day (day_id, day_name, day_date, month_id, week_id) values (NEXTVAL('day_seq'), 6, '2020-10-24', 22, 23);

insert into day (day_id, day_name, day_date, month_id, week_id) values (NEXTVAL('day_seq'), 7, '2020-10-25', 22, 23);



insert into day_plan (day_plan_id, content, start_date, end_date, is_fulfilled, day_id) values (NEXTVAL('dayplan_seq'), 'jakis dzienny plan 1', '06:00:00', '21:00:00', true, 8);

insert into day_plan (day_plan_id, content, start_date, end_date, is_fulfilled, day_id) values (NEXTVAL('dayplan_seq'), 'jakis dzienny plan 2', '06:00:00', '21:00:00', false, 8);

insert into day_plan (day_plan_id, content, start_date, end_date, is_fulfilled, day_id) values (NEXTVAL('dayplan_seq'), 'jakis dzienny plan 3', '06:00:00', '21:00:00', true, 8);


insert into day_plan (day_plan_id, content, start_date, end_date, is_fulfilled, day_id) values (NEXTVAL('dayplan_seq'), 'jakis dzienny plan 1', '06:00:00', '21:00:00', true, 9);

insert into day_plan (day_plan_id, content, start_date, end_date, is_fulfilled, day_id) values (NEXTVAL('dayplan_seq'), 'jakis dzienny plan 2', '06:00:00', '21:00:00', false, 9);

insert into day_plan (day_plan_id, content, start_date, end_date, is_fulfilled, day_id) values (NEXTVAL('dayplan_seq'), 'jakis dzienny plan 3', '06:00:00', '21:00:00', true, 9);


insert into day_plan (day_plan_id, content, start_date, end_date, is_fulfilled, day_id) values (NEXTVAL('dayplan_seq'), 'jakis dzienny plan 1', '06:00:00', '21:00:00', true, 10);

insert into day_plan (day_plan_id, content, start_date, end_date, is_fulfilled, day_id) values (NEXTVAL('dayplan_seq'), 'jakis dzienny plan 2', '06:00:00', '21:00:00', false, 10);

insert into day_plan (day_plan_id, content, start_date, end_date, is_fulfilled, day_id) values (NEXTVAL('dayplan_seq'), 'jakis dzienny plan 3', '06:00:00', '21:00:00', true, 10);


insert into day_plan (day_plan_id, content, start_date, end_date, is_fulfilled, day_id) values (NEXTVAL('dayplan_seq'), 'jakis dzienny plan 1', '06:00:00', '21:00:00', true, 11);

insert into day_plan (day_plan_id, content, start_date, end_date, is_fulfilled, day_id) values (NEXTVAL('dayplan_seq'), 'jakis dzienny plan 2', '06:00:00', '21:00:00', false, 11);

insert into day_plan (day_plan_id, content, start_date, end_date, is_fulfilled, day_id) values (NEXTVAL('dayplan_seq'), 'jakis dzienny plan 3', '06:00:00', '21:00:00', true, 11);


insert into day_plan (day_plan_id, content, start_date, end_date, is_fulfilled, day_id) values (NEXTVAL('dayplan_seq'), 'jakis dzienny plan 1', '06:00:00', '21:00:00', true, 12);

insert into day_plan (day_plan_id, content, start_date, end_date, is_fulfilled, day_id) values (NEXTVAL('dayplan_seq'), 'jakis dzienny plan 2', '06:00:00', '21:00:00', false, 12);

insert into day_plan (day_plan_id, content, start_date, end_date, is_fulfilled, day_id) values (NEXTVAL('dayplan_seq'), 'jakis dzienny plan 3', '06:00:00', '21:00:00', true, 12);


insert into day_plan (day_plan_id, content, start_date, end_date, is_fulfilled, day_id) values (NEXTVAL('dayplan_seq'), 'jakis dzienny plan 1', '06:00:00', '21:00:00', true, 13);

insert into day_plan (day_plan_id, content, start_date, end_date, is_fulfilled, day_id) values (NEXTVAL('dayplan_seq'), 'jakis dzienny plan 2', '06:00:00', '21:00:00', false, 13);

insert into day_plan (day_plan_id, content, start_date, end_date, is_fulfilled, day_id) values (NEXTVAL('dayplan_seq'), 'jakis dzienny plan 3', '06:00:00', '21:00:00', true, 13);


insert into day_plan (day_plan_id, content, start_date, end_date, is_fulfilled, day_id) values (NEXTVAL('dayplan_seq'), 'jakis dzienny plan 1', '06:00:00', '21:00:00', true, 14);

insert into day_plan (day_plan_id, content, start_date, end_date, is_fulfilled, day_id) values (NEXTVAL('dayplan_seq'), 'jakis dzienny plan 2', '06:00:00', '21:00:00', false, 14);

insert into day_plan (day_plan_id, content, start_date, end_date, is_fulfilled, day_id) values (NEXTVAL('dayplan_seq'), 'jakis dzienny plan 3', '06:00:00', '21:00:00', true, 14);




