INSERT INTO plans (name, total_amount, given_amount, advance, daily_emi, days, status, created_at)
SELECT 'BRONZE', 10000.00, 10000.00 - 1000.00, 1000.00, 101.00, 100, 'ACTIVE', NOW()
    WHERE NOT EXISTS (SELECT 1 FROM plans WHERE name = 'BRONZE');

INSERT INTO plans (name, total_amount, given_amount, advance, daily_emi, days, status, created_at)
SELECT 'SILVER', 20000.00, 20000.00 - 2000.00, 2000.00, 202.00, 100, 'ACTIVE', NOW()
    WHERE NOT EXISTS (SELECT 1 FROM plans WHERE name = 'SILVER');

INSERT INTO plans (name, total_amount, given_amount, advance, daily_emi, days, status, created_at)
SELECT 'GOLD', 30000.00, 30000.00 - 3000.00, 3000.00, 303.00, 100, 'ACTIVE', NOW()
    WHERE NOT EXISTS (SELECT 1 FROM plans WHERE name = 'GOLD');

INSERT INTO plans (name, total_amount, given_amount, advance, daily_emi, days, status, created_at)
SELECT 'PLATINUM', 50000.00, 50000.00 - 5000.00, 5000.00, 505.00, 100, 'ACTIVE', NOW()
    WHERE NOT EXISTS (SELECT 1 FROM plans WHERE name = 'PLATINUM');

INSERT INTO plans (name, total_amount, given_amount, advance, daily_emi, days, status, created_at)
SELECT 'DIAMOND', 75000.00, 75000.00 - 7500.00, 7500.00, 757.50, 100, 'ACTIVE', NOW()
    WHERE NOT EXISTS (SELECT 1 FROM plans WHERE name = 'DIAMOND');