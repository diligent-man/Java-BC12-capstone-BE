USE uniclub;


# Mock data
INSERT INTO
    role(name)
VALUES
    ('ROLE_ADMIN'),
    ('ROLE_USER'),
    ('ROLE_GUEST');


# Pass: 1,2,3 respectively
INSERT INTO
    user(email, password, full_name, role_id)
VALUES
    ('nv1@gmail.com', '$2a$12$uK5K0iIRxTls1hxzSI3vMOnFTSX0q1QGZ3Qwe6lF7mZOPbe3RARre', 'Nguyen Van Mot', 1),
    ('nv2@gmail.com', '$2a$12$bh0p.LMf1PWLNGF1RMtG1O1dmxHzROL0OOZss9W4qp6bwZfC0blIq', 'Nguyen Van Hai', 2),
    ('nv3@gmail.com', '$2a$12$GaxiM43VkAG60uOfcOI0kuXbcqYF2aJ0e/HocPOl5tSRQTPH.HMKm', 'Nguyen Van Ba', 3);
