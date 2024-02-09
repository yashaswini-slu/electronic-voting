INSERT INTO code.locale_cd(locale_cd, code, start_date, end_date)
VALUES (1, 'eng', current_date, null);
INSERT INTO code.party_cd(party_cd, code, start_date, end_date)
VALUES (1, 'ind', current_date, null),
(2,'org',current_date,null);
INSERT INTO code.party_locale(description, start_time, party_cd, locale_cd)
VALUES ('Individual', current_date, 1, 1),
('Organization', current_date, 2, 1);
INSERT INTO code.party_role_cd(party_role_cd, code, start_date)
VALUES (1, 'user', current_date),
(2, 'organiser', current_date),
(3, 'voter', current_date);
INSERT INTO code.party_role_locale(party_role_cd, locale_cd, description,
start_time)
VALUES (1, 1, 'User', current_date),
(2, 1, 'Organiser', current_date),
(3, 1, 'Voter', current_date);
INSERT INTO code.pr_pr_relation_cd(pr_pr_relation_cd, code, start_date)
VALUES (1, 'user-organiser', current_date),
(2, 'user-voter', current_date);
INSERT INTO code.pr_pr_relation_locale(pr_pr_relation_cd, locale_cd, description,
start_time)
VALUES (1, 1, 'user-organiser', current_date),
(2,1,'user-voter', current_date);