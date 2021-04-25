-- CONNECTION: url=jdbc:postgresql://localhost:5432/oe
DO $$ DECLARE
    r RECORD;
BEGIN
    FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = 'sinecycle') LOOP
        EXECUTE 'DROP TABLE IF EXISTS sinecycle.' || r.tablename || ' CASCADE';
    END LOOP;
END $$;
