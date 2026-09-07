USE uniclub;


# Mock data
########################################          Product          #####################################################
INSERT INTO
    brand(name)
VALUES
    ('Adidas'),
    ('Nike'),
    ('Gucci')
;


INSERT INTO
    category(name)
VALUES
    ('mens'),
    ('womens'),
    ('childrens')
;


INSERT INTO
    tag(name)
VALUES
    ('jacket'),
    ('jean'),
    ('short'),
    ('t-shirt'),
    ('pant')
;


INSERT INTO
    color(name)
VALUES
    ('black'),
    ('white'),
    ('blue'),
    ('red'),
    ('grey')
;


INSERT INTO
    size(name)
VALUES
    ('XS'),
    ('S'),
    ('M'),
    ('L'),
    ('XL'),
    ('XXL')
;


INSERT INTO
    product(name, description, information, price, id_brand)
VALUES
    ('firebird loose monogram track pants',
     'The Firebird Loose Monogram Track Pants are a nod to adidas’ iconic heritage, blending classic design with modern flair. Crafted from jacquard fabric, these track pants offer a relaxed fit that offers comfort and style for your everyday lifestyle.

With a focus on versatility, these track pants feature a loose silhouette that pairs effortlessly with your favourite tops. The drawcord closure allows for a personalised fit, making them a smart choice for any occasion.

adidas Originals brings you a piece that not only celebrates the past but also embraces the future of fashion. Elevate your wardrobe with this piece and experience the blend of tradition and innovation.',
     'Loose fit
Elastic waistband with drawcord
Main Material: 100% Polyester(100% Recycled)
Jacquard
Side pockets
adidas branding elements
All-over print
Colour: Black
Product code: KC9159',
     39.99,
     1),

    ('adilenium season 5 denim jeans',
     'Crafted for those who are looking for casual comfort with a touch of rebellion, the Adilenium Season 5 Denim Jeans embody the spirit of adidas Originals. They’re a great choice for urban trendsetters.

They offer a loose fit that speaks to both style and ease. The woven construction provides durability, making these jeans your go-to apparel for everyday wear. Featuring a classic zip closure, the jeans combine functionality with a timeless aesthetic.

Pair these denim jeans with your favourite adidas apparel, and transform your wardrobe with the bold and authentic flair that adidas delivers.',
     'Loose fit
Zip
Main Material: 100% Cotton / Pockets: 100% Cotton
Denim
Colour: Chalky Brown
Product code: KX4821',
     59.99,
     1),

    ('originals twistknit pleated pants',
     'Climb the leaderboard with distraction-free performance and comfort in these adidas golf pants. Stretchy TWISTKNIT fabric offers full mobility to bend, twist and crouch without restriction. Pleats add traditional course style.',
     'Regular fit
Belt loops
100% polyester (80% recycled)
Front and back pockets
Coin pocket
Pleated
Colour: Night Indigo
Product code: JF5014',
     89.99,
     1),

    ('essential 3-stripes french terry pants',
     'Whether you''re lounging at home or heading out, these adidas pants have you covered. The soft French terry build keeps you comfortable, and the 3-Stripes down the sides add a sporty finish. With a modern, tapered fit, these pants are an everyday essential.

By choosing recycled, we are able to reuse materials that have already been created, which helps to reduce waste. Renewable materials choices will help us to remove our reliance on finite resources. Our products made with a blend of recycled and renewable materials feature at least 70% total of these materials.',
     'Regular fit
Drawstring closure
Body: 55% Cotton / 36% Polyester(100% Recycled) / 9% Viscose
Ribbed cuffs
Tapered legs
Contains a minimum of 70% recycled and renewable content
Colour: Warm Sandstone / Black
Product code: JX0724',
     89.50,
     1),

    ('adicolor classics 3-stripes cargo pants',
     'The Adicolor Classics 3-Stripes Cargo Pants are your go-to for everyday casual wear. With a nod to the adidas Originals heritage, they blend iconic style with modern comfort.

Crafted from durable ripstop fabric, these trousers are built to last. The loose fit and mid-rise waist create a relaxed feel, while cargo pockets provide ample storage for your essentials. An embroidered trefoil on the left leg and signature 3-Stripes complete the look with a touch of adidas flair.

Whether you''re heading out and about or just lounging around, these trousers give you the versatility and style you need. Embrace the rebellious optimism of adidas and make them a staple in your wardrobe.',
     'oose fit
Drawstring
Main Material: 100% Polyamide(100% Recycled)
Ripstop fabric
Cargo pockets
Iconic branding
Colour: Black
Product code: KE2927',
     149.00,
     1),


    ('nike sportswear',
     'Nothing hits like a classic tee. This one is relaxed through the body for easy layering. Lightweight cotton feels soft and comfortable for everyday wear.',
     'Ribbed collar
100% cotton
Machine wash
Imported
Shown: Dark Grey Heather
Style: IH1141-063',
     44.99,
     2),

    ('nike dri-fit',
     'The Nike Dri-FIT Shorts are made to keep you moving during high-intensity training. Woven fabric stretches with every move, while vents at the hems are ideal for deep bends like lunges and squats. This product is made with 100% recycled polyester fibers.',
     'Standard fit for a relaxed, easy feel
9" inseam
100% polyester
Machine wash
Imported
Shown: Black/Black/White
Style: DM6617-010',
     65.00,
     2),

    ('brazil',
     'Inspired by their home kit, this Brazil top is made with sweat-wicking technology to help keep you dry and comfortable on and off the field.',
     '100% polyester
Machine wash
Imported
Shown: Canary/Light Menta/Geode Teal
Style: JU1492-724',
     49.99,
     2),

    ('jordan',
     'Lightweight mesh and our sweat-wicking technology help you stay fresh all day long. And that signature diamond taping? It''s the cherry on top.',
     'Body: 100% Polyester; Lining: 100% Polyester
Machine wash
Imported
Shown: Old Royal/Yellow Pulse
Style: IF3912-417',
     129.99,
     2),

    ('usmnt',
     'Show love for your squad in this relaxed USMNT tee.',
     '100% cotton
Machine wash
Imported
Shown: Sail
Style: IQ2280-133',
     34.99,
     2),

    ('leather biker jacket with web detail',
     'This style is part of the Gucci Primavera collection. Biker inspirations are reinterpreted with leather Web detail along the sleeves on this regular-fit jacket. Crafted from treated calf leather with subtly worn-out effect, the style features intentional creases for a lived-in appearance. Special finishings and a softened grain complete the distinctive design.',
     'Black treated calf leather with worn-out effect
Stretch side inserts
Green and red Web leather detail at the sleeves
Tonal lining
Padded collar
Hidden snap buttons at the collar and cuffs
Zip closure at the cuffs
Front zip closure
Regular fit
Total length: 22.36"; Shoulder: 20.79"; Chest: 41.34"; Sleeves length: 24.65"; based on a size 48 (IT)
Made in Italy
Genuine leather: Calfskin
Genuine leather: Buffalo
Lining: 75% Acetate, 25% Cotton
Lining: 78% Polyamide, 18% Viscose, 4% Elastane
Lining: 100% Silk
Pocket lining: 100% Cotton',
     199.99,
     3),

    ('lightweight nylon padded jacket',
     'Gucci Primavera establishes a new vocabulary of silhouettes, textures, and materials, merging innovation with a grounded vision. New offerings in nylon are grounded in practicality, defined by sartorial construction and richer tones. Crafted from lightweight compact nylon, this jacket reveals an internal Web detail.',
     'Black lightweight compact nylon
Lined and padded
Stand collar
Two side pockets
Internal Web detail
Internal pocket
Rib trim
Magnetic closure and zip closure with engraved Gucci zip puller
Relaxed silhouette
Total length: 29.5"; Chest: 24.8"; Sleeves length: 37"; based on a size 48 IT
Made in Italy
Fabric: 100% Polyamide
Details: 83% Polyamide, 17% Polyester
Parts in knitted fabric: 95% Polyester, 5% Elastane
Lining: 100% Cupro
Pocket lining: 100% Cotton
Filling: 100% Polyester',
     79.99,
     3),

    ('coated cotton twill denim pants with web',
     'Gucci Primavera establishes a new vocabulary of silhouettes, textures, and materials, merging innovation with a grounded vision. New denim offerings are defined by sartorial construction and richer tones, as denim pants evolve into modern sets with a sleek edge. Crafted from coated stretch cotton twill denim, this style is designed with a loose fit, enhanced by Web inserts along the sides.',
     'Black coated stretch cotton twill denim
Side Web insert
Belt loops
''Gucci Made in Italy'' leather label
Two front welt pockets
Hidden snap button and zip closure
Loose fit
Total length: 44.4"; Waist: 30.7"; Hips: 37.7"; Hem width: 19.4"; based on a size 30 IT
Made in Italy
Label: Calfskin
Fabric: 94% Cotton, 6% Elastane
Details: 88% Wool, 12% Polyester',
     39.99,
     3),

    ('cotton viscose jacket with python effect',
     'Gucci Primavera establishes a new vocabulary of silhouettes, textures, and materials, merging innovation with a grounded vision. New leather offerings are defined by sartorial construction and richer tones, as jackets evolve into modern sets with a sleek edge. Crafted from washed cotton polyester viscose, this slim-fit jacket features a distinctive python effect.',
     'Black washed cotton polyester viscose with python effect
Interlocking G embroidery
Stand collar
Long sleeves
Zip closure
Slim fit
Total length: 21.8"; Shoulder: 16.7"; Chest: 37.4"; Sleeves length: 26.3"; based on a size 44 IT
Made in Italy
We recommend selecting at least one size up.
Fabric: 53% Cotton, 26% Viscose, 21% Polyester
Pocket lining: 65% Polyester, 35% Cotton
Embroidery: 100% Polyester
Non textile decoration: 100% Polyamide',
     54.99,
     3),

    ('grainy laminated bull leather jacket',
     'Gucci Primavera establishes a new vocabulary of silhouettes, textures, and materials, merging innovation with a grounded vision. New leather offerings are defined by sartorial construction and rich hues, along with subtle House codes. On this jacket, a tonal trim inspired by the Web offers an understated expression of the signature motif. Crafted from grainy bull leather, it is complete with Gucci embroidery, padded details, embossed details, and leather patches.',
     '
Black soft, grainy laminated bull leather
Gucci embroidery
Embossed ''Primavera'' detail
Interlocking G leather patch
Black mesh lining
Stand collar
Dropped shoulders
Long sleeves with zip and snap button cuffs
Padded details
Adjustable velcro strap
Muted Web
Zip closure
Relaxed fit
Total length: 24"; Shoulder: 21.6"; Chest: 46.4"; Sleeves length: 24"; based on a size M
Made in Italy
Genuine leather: Cow leather
Lining: 100% Polyester
Lining: 100% Viscose
Embroidery: 100% Polyester',
     99.99,
     3)
;


INSERT INTO
    product_category(id_category, id_product)
VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 4),
    (1, 5),
    (1, 6),
    (1, 7),
    (1, 8),
    (1, 9),
    (1, 10),
    (1, 11),
    (1, 12),
    (1, 13),
    (1, 14),
    (1, 15)
;


INSERT INTO
    product_tag(id_tag, id_product)
VALUES
    (5, 1),
    (2, 2),
    (5, 3),
    (5, 4),
    (5, 5),
    (4, 6),
    (3, 7),
    (3, 8),
    (3, 9),
    (4, 10),
    (1, 11),
    (1, 12),
    (5, 13),
    (1, 14),
    (1, 15)
;



# TODO: fix data correctedness later
# 'item1.jpg, item4.jpg'
INSERT INTO
    variant (id_product, id_color, id_size, images, quantity, price)
VALUES
    (1, 1, 1, null, RAND(10), null),
    (1, 1, 2, 'item1.jpg, item4.jpg', RAND(10), null),
    (1, 1, 3, 'item1.jpg, item4.jpg', RAND(10), null),
    (1, 2, 1, 'item1.jpg, item4.jpg', RAND(10), 45.99),
    (1, 2, 2, 'item1.jpg, item4.jpg', RAND(10), 45.99),
    (1, 2, 3, 'item1.jpg, item4.jpg', RAND(10), 45.99),

    (2, 1, 1, 'item1.jpg, item4.jpg', RAND(10), null),
    (2, 1, 2, 'item1.jpg, item4.jpg', RAND(10), null),
    (2, 1, 3, 'item1.jpg, item4.jpg', RAND(10), null),
    (2, 2, 1, 'item1.jpg, item4.jpg', RAND(10), 69.99),
    (2, 2, 2, 'item1.jpg, item4.jpg', RAND(10), 69.99),
    (2, 2, 3, 'item1.jpg, item4.jpg', RAND(10), 69.99),

    (3, 1, 1, 'item1.jpg, item4.jpg', RAND(10), null),
    (3, 1, 2, 'item1.jpg, item4.jpg', RAND(10), null),
    (3, 1, 3, 'item1.jpg, item4.jpg', RAND(10), null),
    (3, 2, 1, 'item1.jpg, item4.jpg', RAND(10), 99.99),
    (3, 2, 2, 'item1.jpg, item4.jpg', RAND(10), 99.99),
    (3, 2, 3, 'item1.jpg, item4.jpg', RAND(10), 99.99),

    (4, 1, 1, 'item1.jpg, item4.jpg', RAND(10), null),
    (4, 1, 2, 'item1.jpg, item4.jpg', RAND(10), null),
    (4, 1, 3, 'item1.jpg, item4.jpg', RAND(10), null),
    (4, 2, 1, 'item1.jpg, item4.jpg', RAND(10), 70.5),
    (4, 2, 2, 'item1.jpg, item4.jpg', RAND(10), 70.5),
    (4, 2, 3, 'item1.jpg, item4.jpg', RAND(10), 70.5),

    (5, 1, 1, 'item1.jpg, item4.jpg', RAND(10), null),
    (5, 1, 2, 'item1.jpg, item4.jpg', RAND(10), null),
    (5, 1, 3, 'item1.jpg, item4.jpg', RAND(10), null),
    (5, 2, 1, 'item1.jpg, item4.jpg', RAND(10), 130),
    (5, 2, 2, 'item1.jpg, item4.jpg', RAND(10), 130),
    (5, 2, 3, 'item1.jpg, item4.jpg', RAND(10), 130),

    (6, 1, 1, 'item1.jpg, item4.jpg', RAND(10), null),
    (6, 1, 2, 'item1.jpg, item4.jpg', RAND(10), null),
    (6, 1, 3, 'item1.jpg, item4.jpg', RAND(10), null),
    (6, 2, 1, 'item1.jpg, item4.jpg', RAND(10), 54.99),
    (6, 2, 2, 'item1.jpg, item4.jpg', RAND(10), 54.99),
    (6, 2, 3, 'item1.jpg, item4.jpg', RAND(10), 54.99),

    (7, 1, 1, 'item1.jpg, item4.jpg', RAND(10), null),
    (7, 1, 2, 'item1.jpg, item4.jpg', RAND(10), null),
    (7, 1, 3, 'item1.jpg, item4.jpg', RAND(10), null),
    (7, 2, 1, 'item1.jpg, item4.jpg', RAND(10), 75),
    (7, 2, 2, 'item1.jpg, item4.jpg', RAND(10), 75),
    (7, 2, 3, 'item1.jpg, item4.jpg', RAND(10), 75),

    (8, 1, 1, 'item1.jpg, item4.jpg', RAND(10), null),
    (8, 1, 2, 'item1.jpg, item4.jpg', RAND(10), null),
    (8, 1, 3, 'item1.jpg, item4.jpg', RAND(10), null),
    (8, 2, 1, 'item1.jpg, item4.jpg', RAND(10), 39.99),
    (8, 2, 2, 'item1.jpg, item4.jpg', RAND(10), 39.99),
    (8, 2, 3, 'item1.jpg, item4.jpg', RAND(10), 39.99),

    (9, 1, 1, 'item1.jpg, item4.jpg', RAND(10), null),
    (9, 1, 2, 'item1.jpg, item4.jpg', RAND(10), null),
    (9, 1, 3, 'item1.jpg, item4.jpg', RAND(10), null),
    (9, 2, 1, 'item1.jpg, item4.jpg', RAND(10), 119.50),
    (9, 2, 2, 'item1.jpg, item4.jpg', RAND(10), 119.50),
    (9, 2, 3, 'item1.jpg, item4.jpg', RAND(10), 119.50),

    (10, 1, 1, 'item1.jpg, item4.jpg', RAND(10), null),
    (10, 1, 2, 'item1.jpg, item4.jpg', RAND(10), null),
    (10, 1, 3, 'item1.jpg, item4.jpg', RAND(10), null),
    (10, 2, 1, 'item1.jpg, item4.jpg', RAND(10), 24.99),
    (10, 2, 2, 'item1.jpg, item4.jpg', RAND(10), 24.99),
    (10, 2, 3, 'item1.jpg, item4.jpg', RAND(10), 24.99),

    (11, 1, 1, 'item1.jpg, item4.jpg', RAND(10), null),
    (11, 1, 2, 'item1.jpg, item4.jpg', RAND(10), null),
    (11, 1, 3, 'item1.jpg, item4.jpg', RAND(10), null),
    (11, 2, 1, 'item1.jpg, item4.jpg', RAND(10), 189.19),
    (11, 2, 2, 'item1.jpg, item4.jpg', RAND(10), 189.19),
    (11, 2, 3, 'item1.jpg, item4.jpg', RAND(10), 189.19),

    (12, 1, 1, 'item1.jpg, item4.jpg', RAND(10), null),
    (12, 1, 2, 'item1.jpg, item4.jpg', RAND(10), null),
    (12, 1, 3, 'item1.jpg, item4.jpg', RAND(10), null),
    (12, 2, 1, 'item1.jpg, item4.jpg', RAND(10), null),
    (12, 2, 2, 'item1.jpg, item4.jpg', RAND(10), null),
    (12, 2, 3, 'item1.jpg, item4.jpg', RAND(10), null),

    (13, 1, 1, 'item1.jpg, item4.jpg', RAND(10), null),
    (13, 1, 2, 'item1.jpg, item4.jpg', RAND(10), null),
    (13, 1, 3, 'item1.jpg, item4.jpg', RAND(10), null),
    (13, 2, 1, 'item1.jpg, item4.jpg', RAND(10), 30.99),
    (13, 2, 2, 'item1.jpg, item4.jpg', RAND(10), 30.99),
    (13, 2, 3, 'item1.jpg, item4.jpg', RAND(10), 30.99),

    (14, 1, 1, 'item1.jpg, item4.jpg', RAND(10), null),
    (14, 1, 2, 'item1.jpg, item4.jpg', RAND(10), null),
    (14, 1, 3, 'item1.jpg, item4.jpg', RAND(10), null),
    (14, 2, 1, 'item1.jpg, item4.jpg', RAND(10), 99.59),
    (14, 2, 2, 'item1.jpg, item4.jpg', RAND(10), 99.59),
    (14, 2, 3, 'item1.jpg, item4.jpg', RAND(10), 99.59),

    (15, 1, 1, 'item1.jpg, item4.jpg', RAND(10), null),
    (15, 1, 2, 'item1.jpg, item4.jpg', RAND(10), null),
    (15, 1, 3, 'item1.jpg, item4.jpg', RAND(10), null),
    (15, 2, 1, 'item1.jpg, item4.jpg', RAND(10), 44.99),
    (15, 2, 2, 'item1.jpg, item4.jpg', RAND(10), 44.99),
    (15, 2, 3, 'item1.jpg, item4.jpg', RAND(10), 44.99)
;


#######################################          User/ Role          ###################################################
INSERT INTO
    role(name)
VALUES
    ('ROLE_ADMIN'),
    ('ROLE_USER'),
    ('ROLE_GUEST')
;


# Pass: 1,2,3 respectively
INSERT INTO
    user(email, password, full_name, role_id)
VALUES
    ('nv1@gmail.com', '$2a$12$uK5K0iIRxTls1hxzSI3vMOnFTSX0q1QGZ3Qwe6lF7mZOPbe3RARre', 'Nguyen Van Mot', 1),
    ('nv2@gmail.com', '$2a$12$bh0p.LMf1PWLNGF1RMtG1O1dmxHzROL0OOZss9W4qp6bwZfC0blIq', 'Nguyen Van Hai', 2),
    ('nv3@gmail.com', '$2a$12$GaxiM43VkAG60uOfcOI0kuXbcqYF2aJ0e/HocPOl5tSRQTPH.HMKm', 'Nguyen Van Ba', 3)
;


# INSERT INTO wishlist(id_product, id_user) VALUES ();
# INSERT INTO review(id_product, id_user, star, content, create_date, images) VALUES ();


##########################################          Orders          ####################################################
# ref: https://stripe.com/guides/payment-methods-guide
INSERT INTO
    payment_method(name, description)
VALUES
    ('cash-based', 'With cash-based vouchers, customers receive a scannable voucher with a transaction reference number that they can then bring to an ATM, bank, convenience store, or supermarket to complete the payment in cash.'),
    ('bank transfer', 'Credit transfers allow customers to push funds from their bank account to yours. You provide customers with the bank account information they should send funds to.'),
    ('digital wallet', 'Wallets are linked to a card or bank account, but can also store monetary value. Wallets typically require customer verification (e.g., biometrics, SMS, passcode) to complete a payment.'),
    ('card', 'Cards are linked to a debit or credit account at a bank. To complete a payment online, customers enter their card information at checkout.'),
    ('cash-based vouchers', 'With cash-based vouchers, customers receive a scannable voucher with a transaction reference number that they can then bring to an ATM, bank, convenience store, or supermarket to complete the payment in cash.')
;


# INSERT INTO
#     orders(total, note, id_payment, id_user)
# VALUES
#     ();

# INSERT INTO
#     order_variant(id_order, sku_variant, quantity, price)
# VALUES
#     ();


######################################          Billing details           ##############################################
INSERT INTO
    country(iso, name, nice_name, iso3, num_code, phone_code)
VALUES
    ('AF', 'AFGHANISTAN', 'Afghanistan', 'AFG', 4, 93),
    ('AL', 'ALBANIA', 'Albania', 'ALB', 8, 355),
    ('DZ', 'ALGERIA', 'Algeria', 'DZA', 12, 213),
    ('AS', 'AMERICAN SAMOA', 'American Samoa', 'ASM', 16, 1684),
    ('AD', 'ANDORRA', 'Andorra', 'AND', 20, 376),
    ('AO', 'ANGOLA', 'Angola', 'AGO', 24, 244),
    ('AI', 'ANGUILLA', 'Anguilla', 'AIA', 660, 1264),
    ('AQ', 'ANTARCTICA', 'Antarctica', 'ATA', 10, 0),
    ('AG', 'ANTIGUA AND BARBUDA', 'Antigua and Barbuda', 'ATG', 28, 1268),
    ('AR', 'ARGENTINA', 'Argentina', 'ARG', 32, 54),
    ('AM', 'ARMENIA', 'Armenia', 'ARM', 51, 374),
    ('AW', 'ARUBA', 'Aruba', 'ABW', 533, 297),
    ('AU', 'AUSTRALIA', 'Australia', 'AUS', 36, 61),
    ('AT', 'AUSTRIA', 'Austria', 'AUT', 40, 43),
    ('AZ', 'AZERBAIJAN', 'Azerbaijan', 'AZE', 31, 994),
    ('BS', 'BAHAMAS', 'Bahamas', 'BHS', 44, 1242),
    ('BH', 'BAHRAIN', 'Bahrain', 'BHR', 48, 973),
    ('BD', 'BANGLADESH', 'Bangladesh', 'BGD', 50, 880),
    ('BB', 'BARBADOS', 'Barbados', 'BRB', 52, 1246),
    ('BY', 'BELARUS', 'Belarus', 'BLR', 112, 375),
    ('BE', 'BELGIUM', 'Belgium', 'BEL', 56, 32),
    ('BZ', 'BELIZE', 'Belize', 'BLZ', 84, 501),
    ('BJ', 'BENIN', 'Benin', 'BEN', 204, 229),
    ('BM', 'BERMUDA', 'Bermuda', 'BMU', 60, 1441),
    ('BT', 'BHUTAN', 'Bhutan', 'BTN', 64, 975),
    ('BO', 'BOLIVIA', 'Bolivia', 'BOL', 68, 591),
    ('BQ', 'BONAIRE, SINT EUSTATIUS AND SABA', 'Bonaire, Sint Eustatius and Saba', 'BES', 535, 599),
    ('BA', 'BOSNIA AND HERZEGOVINA', 'Bosnia and Herzegovina', 'BIH', 70, 387),
    ('BW', 'BOTSWANA', 'Botswana', 'BWA', 72, 267),
    ('BV', 'BOUVET ISLAND', 'Bouvet Island', 'BVT', 74, 0),
    ('BR', 'BRAZIL', 'Brazil', 'BRA', 76, 55),
    ('IO', 'BRITISH INDIAN OCEAN TERRITORY', 'British Indian Ocean Territory', 'IOT', 86, 246),
    ('BN', 'BRUNEI DARUSSALAM', 'Brunei Darussalam', 'BRN', 96, 673),
    ('BG', 'BULGARIA', 'Bulgaria', 'BGR', 100, 359),
    ('BF', 'BURKINA FASO', 'Burkina Faso', 'BFA', 854, 226),
    ('BI', 'BURUNDI', 'Burundi', 'BDI', 108, 257),
    ('CV', 'CABO VERDE', 'Cabo Verde', 'CPV', 132, 238),
    ('KH', 'CAMBODIA', 'Cambodia', 'KHM', 116, 855),
    ('CM', 'CAMEROON', 'Cameroon', 'CMR', 120, 237),
    ('CA', 'CANADA', 'Canada', 'CAN', 124, 1),
    ('KY', 'CAYMAN ISLANDS', 'Cayman Islands', 'CYM', 136, 1345),
    ('CF', 'CENTRAL AFRICAN REPUBLIC', 'Central African Republic', 'CAF', 140, 236),
    ('TD', 'CHAD', 'Chad', 'TCD', 148, 235),
    ('CL', 'CHILE', 'Chile', 'CHL', 152, 56),
    ('CN', 'CHINA', 'China', 'CHN', 156, 86),
    ('CX', 'CHRISTMAS ISLAND', 'Christmas Island', 'CXR', 162, 61),
    ('CC', 'COCOS (KEELING) ISLANDS', 'Cocos (Keeling) Islands', 'CCK', 166, 61),
    ('CO', 'COLOMBIA', 'Colombia', 'COL', 170, 57),
    ('KM', 'COMOROS', 'Comoros', 'COM', 174, 269),
    ('CG', 'CONGO', 'Congo', 'COG', 178, 242),
    ('CD', 'CONGO, THE DEMOCRATIC REPUBLIC OF THE', 'Congo, the Democratic Republic of the', 'COD', 180, 243),
    ('CK', 'COOK ISLANDS', 'Cook Islands', 'COK', 184, 682),
    ('CR', 'COSTA RICA', 'Costa Rica', 'CRI', 188, 506),
    ('CI', 'COTE D''IVOIRE', 'Côte d''Ivoire', 'CIV', 384, 225),
    ('HR', 'CROATIA', 'Croatia', 'HRV', 191, 385),
    ('CU', 'CUBA', 'Cuba', 'CUB', 192, 53),
    ('CW', 'CURACAO', 'Curaçao', 'CUW', 531, 599),
    ('CY', 'CYPRUS', 'Cyprus', 'CYP', 196, 357),
    ('CZ', 'CZECHIA', 'Czechia', 'CZE', 203, 420),
    ('DK', 'DENMARK', 'Denmark', 'DNK', 208, 45),
    ('DJ', 'DJIBOUTI', 'Djibouti', 'DJI', 262, 253),
    ('DM', 'DOMINICA', 'Dominica', 'DMA', 212, 1767),
    ('DO', 'DOMINICAN REPUBLIC', 'Dominican Republic', 'DOM', 214, 1809),
    ('EC', 'ECUADOR', 'Ecuador', 'ECU', 218, 593),
    ('EG', 'EGYPT', 'Egypt', 'EGY', 818, 20),
    ('SV', 'EL SALVADOR', 'El Salvador', 'SLV', 222, 503),
    ('GQ', 'EQUATORIAL GUINEA', 'Equatorial Guinea', 'GNQ', 226, 240),
    ('ER', 'ERITREA', 'Eritrea', 'ERI', 232, 291),
    ('EE', 'ESTONIA', 'Estonia', 'EST', 233, 372),
    ('SZ', 'ESWATINI', 'Eswatini', 'SWZ', 748, 268),
    ('ET', 'ETHIOPIA', 'Ethiopia', 'ETH', 231, 251),
    ('FK', 'FALKLAND ISLANDS (MALVINAS)', 'Falkland Islands (Malvinas)', 'FLK', 238, 500),
    ('FO', 'FAROE ISLANDS', 'Faroe Islands', 'FRO', 234, 298),
    ('FJ', 'FIJI', 'Fiji', 'FJI', 242, 679),
    ('FI', 'FINLAND', 'Finland', 'FIN', 246, 358),
    ('FR', 'FRANCE', 'France', 'FRA', 250, 33),
    ('GF', 'FRENCH GUIANA', 'French Guiana', 'GUF', 254, 594),
    ('PF', 'FRENCH POLYNESIA', 'French Polynesia', 'PYF', 258, 689),
    ('TF', 'FRENCH SOUTHERN TERRITORIES', 'French Southern Territories', 'ATF', 260, 0),
    ('GA', 'GABON', 'Gabon', 'GAB', 266, 241),
    ('GM', 'GAMBIA', 'Gambia', 'GMB', 270, 220),
    ('GE', 'GEORGIA', 'Georgia', 'GEO', 268, 995),
    ('DE', 'GERMANY', 'Germany', 'DEU', 276, 49),
    ('GH', 'GHANA', 'Ghana', 'GHA', 288, 233),
    ('GI', 'GIBRALTAR', 'Gibraltar', 'GIB', 292, 350),
    ('GR', 'GREECE', 'Greece', 'GRC', 300, 30),
    ('GL', 'GREENLAND', 'Greenland', 'GRL', 304, 299),
    ('GD', 'GRENADA', 'Grenada', 'GRD', 308, 1473),
    ('GP', 'GUADELOUPE', 'Guadeloupe', 'GLP', 312, 590),
    ('GU', 'GUAM', 'Guam', 'GUM', 316, 1671),
    ('GT', 'GUATEMALA', 'Guatemala', 'GTM', 320, 502),
    ('GG', 'GUERNSEY', 'Guernsey', 'GGY', 831, 44),
    ('GN', 'GUINEA', 'Guinea', 'GIN', 324, 224),
    ('GW', 'GUINEA-BISSAU', 'Guinea-Bissau', 'GNB', 624, 245),
    ('GY', 'GUYANA', 'Guyana', 'GUY', 328, 592),
    ('HT', 'HAITI', 'Haiti', 'HTI', 332, 509),
    ('HM', 'HEARD ISLAND AND MCDONALD ISLANDS', 'Heard Island and McDonald Islands', 'HMD', 334, 0),
    ('VA', 'HOLY SEE', 'Holy See', 'VAT', 336, 379),
    ('HN', 'HONDURAS', 'Honduras', 'HND', 340, 504),
    ('HK', 'HONG KONG', 'Hong Kong', 'HKG', 344, 852),
    ('HU', 'HUNGARY', 'Hungary', 'HUN', 348, 36),
    ('IS', 'ICELAND', 'Iceland', 'ISL', 352, 354),
    ('IN', 'INDIA', 'India', 'IND', 356, 91),
    ('ID', 'INDONESIA', 'Indonesia', 'IDN', 360, 62),
    ('IR', 'IRAN, ISLAMIC REPUBLIC OF', 'Iran, Islamic Republic of', 'IRN', 364, 98),
    ('IQ', 'IRAQ', 'Iraq', 'IRQ', 368, 964),
    ('IE', 'IRELAND', 'Ireland', 'IRL', 372, 353),
    ('IM', 'ISLE OF MAN', 'Isle of Man', 'IMN', 833, 44),
    ('IL', 'ISRAEL', 'Israel', 'ISR', 376, 972),
    ('IT', 'ITALY', 'Italy', 'ITA', 380, 39),
    ('JM', 'JAMAICA', 'Jamaica', 'JAM', 388, 1876),
    ('JP', 'JAPAN', 'Japan', 'JPN', 392, 81),
    ('JE', 'JERSEY', 'Jersey', 'JEY', 832, 44),
    ('JO', 'JORDAN', 'Jordan', 'JOR', 400, 962),
    ('KZ', 'KAZAKHSTAN', 'Kazakhstan', 'KAZ', 398, 7),
    ('KE', 'KENYA', 'Kenya', 'KEN', 404, 254),
    ('KI', 'KIRIBATI', 'Kiribati', 'KIR', 296, 686),
    ('KP', 'KOREA, DEMOCRATIC PEOPLE''S REPUBLIC OF', 'Korea, Democratic People''s Republic of', 'PRK', 408, 850),
    ('KR', 'KOREA, REPUBLIC OF', 'Korea, Republic of', 'KOR', 410, 82),
    ('XK', 'KOSOVO', 'Kosovo', 'XKX', NULL, 383),
    ('KW', 'KUWAIT', 'Kuwait', 'KWT', 414, 965),
    ('KG', 'KYRGYZSTAN', 'Kyrgyzstan', 'KGZ', 417, 996),
    ('LA', 'LAO PEOPLE''S DEMOCRATIC REPUBLIC', 'Lao People''s Democratic Republic', 'LAO', 418, 856),
    ('LV', 'LATVIA', 'Latvia', 'LVA', 428, 371),
    ('LB', 'LEBANON', 'Lebanon', 'LBN', 422, 961),
    ('LS', 'LESOTHO', 'Lesotho', 'LSO', 426, 266),
    ('LR', 'LIBERIA', 'Liberia', 'LBR', 430, 231),
    ('LY', 'LIBYA', 'Libya', 'LBY', 434, 218),
    ('LI', 'LIECHTENSTEIN', 'Liechtenstein', 'LIE', 438, 423),
    ('LT', 'LITHUANIA', 'Lithuania', 'LTU', 440, 370),
    ('LU', 'LUXEMBOURG', 'Luxembourg', 'LUX', 442, 352),
    ('MO', 'MACAO', 'Macao', 'MAC', 446, 853),
    ('MG', 'MADAGASCAR', 'Madagascar', 'MDG', 450, 261),
    ('MW', 'MALAWI', 'Malawi', 'MWI', 454, 265),
    ('MY', 'MALAYSIA', 'Malaysia', 'MYS', 458, 60),
    ('MV', 'MALDIVES', 'Maldives', 'MDV', 462, 960),
    ('ML', 'MALI', 'Mali', 'MLI', 466, 223),
    ('MT', 'MALTA', 'Malta', 'MLT', 470, 356),
    ('MH', 'MARSHALL ISLANDS', 'Marshall Islands', 'MHL', 584, 692),
    ('MQ', 'MARTINIQUE', 'Martinique', 'MTQ', 474, 596),
    ('MR', 'MAURITANIA', 'Mauritania', 'MRT', 478, 222),
    ('MU', 'MAURITIUS', 'Mauritius', 'MUS', 480, 230),
    ('YT', 'MAYOTTE', 'Mayotte', 'MYT', 175, 262),
    ('MX', 'MEXICO', 'Mexico', 'MEX', 484, 52),
    ('FM', 'MICRONESIA, FEDERATED STATES OF', 'Micronesia, Federated States of', 'FSM', 583, 691),
    ('MD', 'MOLDOVA, REPUBLIC OF', 'Moldova, Republic of', 'MDA', 498, 373),
    ('MC', 'MONACO', 'Monaco', 'MCO', 492, 377),
    ('MN', 'MONGOLIA', 'Mongolia', 'MNG', 496, 976),
    ('ME', 'MONTENEGRO', 'Montenegro', 'MNE', 499, 382),
    ('MS', 'MONTSERRAT', 'Montserrat', 'MSR', 500, 1664),
    ('MA', 'MOROCCO', 'Morocco', 'MAR', 504, 212),
    ('MZ', 'MOZAMBIQUE', 'Mozambique', 'MOZ', 508, 258),
    ('MM', 'MYANMAR', 'Myanmar', 'MMR', 104, 95),
    ('NA', 'NAMIBIA', 'Namibia', 'NAM', 516, 264),
    ('NR', 'NAURU', 'Nauru', 'NRU', 520, 674),
    ('NP', 'NEPAL', 'Nepal', 'NPL', 524, 977),
    ('NL', 'NETHERLANDS', 'Netherlands', 'NLD', 528, 31),
    ('NC', 'NEW CALEDONIA', 'New Caledonia', 'NCL', 540, 687),
    ('NZ', 'NEW ZEALAND', 'New Zealand', 'NZL', 554, 64),
    ('NI', 'NICARAGUA', 'Nicaragua', 'NIC', 558, 505),
    ('NE', 'NIGER', 'Niger', 'NER', 562, 227),
    ('NG', 'NIGERIA', 'Nigeria', 'NGA', 566, 234),
    ('NU', 'NIUE', 'Niue', 'NIU', 570, 683),
    ('NF', 'NORFOLK ISLAND', 'Norfolk Island', 'NFK', 574, 672),
    ('MK', 'NORTH MACEDONIA', 'North Macedonia', 'MKD', 807, 389),
    ('MP', 'NORTHERN MARIANA ISLANDS', 'Northern Mariana Islands', 'MNP', 580, 1670),
    ('NO', 'NORWAY', 'Norway', 'NOR', 578, 47),
    ('OM', 'OMAN', 'Oman', 'OMN', 512, 968),
    ('PK', 'PAKISTAN', 'Pakistan', 'PAK', 586, 92),
    ('PW', 'PALAU', 'Palau', 'PLW', 585, 680),
    ('PS', 'PALESTINE, STATE OF', 'Palestine, State of', 'PSE', 275, 970),
    ('PA', 'PANAMA', 'Panama', 'PAN', 591, 507),
    ('PG', 'PAPUA NEW GUINEA', 'Papua New Guinea', 'PNG', 598, 675),
    ('PY', 'PARAGUAY', 'Paraguay', 'PRY', 600, 595),
    ('PE', 'PERU', 'Peru', 'PER', 604, 51),
    ('PH', 'PHILIPPINES', 'Philippines', 'PHL', 608, 63),
    ('PN', 'PITCAIRN', 'Pitcairn', 'PCN', 612, 64),
    ('PL', 'POLAND', 'Poland', 'POL', 616, 48),
    ('PT', 'PORTUGAL', 'Portugal', 'PRT', 620, 351),
    ('PR', 'PUERTO RICO', 'Puerto Rico', 'PRI', 630, 1787),
    ('QA', 'QATAR', 'Qatar', 'QAT', 634, 974),
    ('RE', 'REUNION', 'Réunion', 'REU', 638, 262),
    ('RO', 'ROMANIA', 'Romania', 'ROU', 642, 40),
    ('RU', 'RUSSIAN FEDERATION', 'Russian Federation', 'RUS', 643, 7),
    ('RW', 'RWANDA', 'Rwanda', 'RWA', 646, 250),
    ('BL', 'SAINT BARTHELEMY', 'Saint Barthélemy', 'BLM', 652, 590),
    ('SH', 'SAINT HELENA, ASCENSION AND TRISTAN DA CUNHA', 'Saint Helena, Ascension and Tristan da Cunha', 'SHN', 654, 290),
    ('KN', 'SAINT KITTS AND NEVIS', 'Saint Kitts and Nevis', 'KNA', 659, 1869),
    ('LC', 'SAINT LUCIA', 'Saint Lucia', 'LCA', 662, 1758),
    ('MF', 'SAINT MARTIN (FRENCH PART)', 'Saint Martin (French part)', 'MAF', 663, 590),
    ('PM', 'SAINT PIERRE AND MIQUELON', 'Saint Pierre and Miquelon', 'SPM', 666, 508),
    ('VC', 'SAINT VINCENT AND THE GRENADINES', 'Saint Vincent and the Grenadines', 'VCT', 670, 1784),
    ('WS', 'SAMOA', 'Samoa', 'WSM', 882, 685),
    ('SM', 'SAN MARINO', 'San Marino', 'SMR', 674, 378),
    ('ST', 'SAO TOME AND PRINCIPE', 'Sao Tome and Principe', 'STP', 678, 239),
    ('SA', 'SAUDI ARABIA', 'Saudi Arabia', 'SAU', 682, 966),
    ('SN', 'SENEGAL', 'Senegal', 'SEN', 686, 221),
    ('RS', 'SERBIA', 'Serbia', 'SRB', 688, 381),
    ('SC', 'SEYCHELLES', 'Seychelles', 'SYC', 690, 248),
    ('SL', 'SIERRA LEONE', 'Sierra Leone', 'SLE', 694, 232),
    ('SG', 'SINGAPORE', 'Singapore', 'SGP', 702, 65),
    ('SX', 'SINT MAARTEN (DUTCH PART)', 'Sint Maarten (Dutch part)', 'SXM', 534, 1721),
    ('SK', 'SLOVAKIA', 'Slovakia', 'SVK', 703, 421),
    ('SI', 'SLOVENIA', 'Slovenia', 'SVN', 705, 386),
    ('SB', 'SOLOMON ISLANDS', 'Solomon Islands', 'SLB', 90, 677),
    ('SO', 'SOMALIA', 'Somalia', 'SOM', 706, 252),
    ('ZA', 'SOUTH AFRICA', 'South Africa', 'ZAF', 710, 27),
    ('GS', 'SOUTH GEORGIA AND THE SOUTH SANDWICH ISLANDS', 'South Georgia and the South Sandwich Islands', 'SGS', 239, 500),
    ('SS', 'SOUTH SUDAN', 'South Sudan', 'SSD', 728, 211),
    ('ES', 'SPAIN', 'Spain', 'ESP', 724, 34),
    ('LK', 'SRI LANKA', 'Sri Lanka', 'LKA', 144, 94),
    ('SD', 'SUDAN', 'Sudan', 'SDN', 729, 249),
    ('SR', 'SURINAME', 'Suriname', 'SUR', 740, 597),
    ('SJ', 'SVALBARD AND JAN MAYEN', 'Svalbard and Jan Mayen', 'SJM', 744, 47),
    ('SE', 'SWEDEN', 'Sweden', 'SWE', 752, 46),
    ('CH', 'SWITZERLAND', 'Switzerland', 'CHE', 756, 41),
    ('SY', 'SYRIAN ARAB REPUBLIC', 'Syrian Arab Republic', 'SYR', 760, 963),
    ('TW', 'TAIWAN, PROVINCE OF CHINA', 'Taiwan, Province of China', 'TWN', 158, 886),
    ('TJ', 'TAJIKISTAN', 'Tajikistan', 'TJK', 762, 992),
    ('TZ', 'TANZANIA, UNITED REPUBLIC OF', 'Tanzania, United Republic of', 'TZA', 834, 255),
    ('TH', 'THAILAND', 'Thailand', 'THA', 764, 66),
    ('TL', 'TIMOR-LESTE', 'Timor-Leste', 'TLS', 626, 670),
    ('TG', 'TOGO', 'Togo', 'TGO', 768, 228),
    ('TK', 'TOKELAU', 'Tokelau', 'TKL', 772, 690),
    ('TO', 'TONGA', 'Tonga', 'TON', 776, 676),
    ('TT', 'TRINIDAD AND TOBAGO', 'Trinidad and Tobago', 'TTO', 780, 1868),
    ('TN', 'TUNISIA', 'Tunisia', 'TUN', 788, 216),
    ('TR', 'TURKIYE', 'Türkiye', 'TUR', 792, 90),
    ('TM', 'TURKMENISTAN', 'Turkmenistan', 'TKM', 795, 993),
    ('TC', 'TURKS AND CAICOS ISLANDS', 'Turks and Caicos Islands', 'TCA', 796, 1649),
    ('TV', 'TUVALU', 'Tuvalu', 'TUV', 798, 688),
    ('UG', 'UGANDA', 'Uganda', 'UGA', 800, 256),
    ('UA', 'UKRAINE', 'Ukraine', 'UKR', 804, 380),
    ('AE', 'UNITED ARAB EMIRATES', 'United Arab Emirates', 'ARE', 784, 971),
    ('GB', 'UNITED KINGDOM', 'United Kingdom', 'GBR', 826, 44),
    ('US', 'UNITED STATES', 'United States', 'USA', 840, 1),
    ('UM', 'UNITED STATES MINOR OUTLYING ISLANDS', 'United States Minor Outlying Islands', 'UMI', 581, 1),
    ('UY', 'URUGUAY', 'Uruguay', 'URY', 858, 598),
    ('UZ', 'UZBEKISTAN', 'Uzbekistan', 'UZB', 860, 998),
    ('VU', 'VANUATU', 'Vanuatu', 'VUT', 548, 678),
    ('VE', 'VENEZUELA', 'Venezuela', 'VEN', 862, 58),
    ('VN', 'VIET NAM', 'Viet Nam', 'VNM', 704, 84),
    ('VG', 'VIRGIN ISLANDS, BRITISH', 'Virgin Islands, British', 'VGB', 92, 1284),
    ('VI', 'VIRGIN ISLANDS, U.S.', 'Virgin Islands, U.S.', 'VIR', 850, 1340),
    ('WF', 'WALLIS AND FUTUNA', 'Wallis and Futuna', 'WLF', 876, 681),
    ('EH', 'WESTERN SAHARA', 'Western Sahara', 'ESH', 732, 212),
    ('YE', 'YEMEN', 'Yemen', 'YEM', 887, 967),
    ('ZM', 'ZAMBIA', 'Zambia', 'ZMB', 894, 260),
    ('ZW', 'ZIMBABWE', 'Zimbabwe', 'ZWE', 716, 263)
;
