USE uniclub;


# Mock data
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


INSERT INTO
    brand(name)
VALUES
    ('Adidas'),
    ('Nike'),
    ('Gucci')
;


INSERT INTO
    color(name)
VALUES
    ('Black'),
    ('White'),
    ('Blue'),
    ('Red'),
    ('Grey')
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