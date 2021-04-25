-- CONNECTION: url=jdbc:postgresql://localhost:5432/oe
DO $$ DECLARE
    r RECORD;
BEGIN
    FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = 'sinecycle' and tablename not like 'databasechangelog%') LOOP
        EXECUTE 'TRUNCATE TABLE sinecycle.' || r.tablename || ' CASCADE';
    END LOOP;
END $$;
