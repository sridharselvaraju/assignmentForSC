-- CONNECTION: url=jdbc:postgresql://localhost:5432/oe
CREATE OR REPLACE PROCEDURE createSeqIfNotExists (seqName VARCHAR)
LANGUAGE plpgsql
AS $$
BEGIN
  execute 'CREATE SEQUENCE IF NOT exists ' || seqName || ' START 7000;';
end;
$$;
