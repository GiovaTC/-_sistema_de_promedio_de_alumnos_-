--? 1. Script Oracle 19c (Tabla + Datos)
CREATE TABLE ALUMNOS (
    ID            NUMBER PRIMARY KEY,
    NOMBRE        VARCHAR2(100),
    NOTA1         NUMBER(5,2),
    NOTA2         NUMBER(5,2),
    NOTA3         NUMBER(5,2),
    NOTA4         NUMBER(5,2),
    NOTA5         NUMBER(5,2)
);

-- Generar 50 alumnos automáticamente
BEGIN
    FOR i IN 1..50 LOOP
        INSERT INTO ALUMNOS VALUES (
            i,
            'Alumno ' || i,
            ROUND(DBMS_RANDOM.VALUE(2,5),2),
            ROUND(DBMS_RANDOM.VALUE(2,5),2),
            ROUND(DBMS_RANDOM.VALUE(2,5),2),
            ROUND(DBMS_RANDOM.VALUE(2,5),2),
            ROUND(DBMS_RANDOM.VALUE(2,5),2)
        );
    END LOOP;
    COMMIT;
END;
/

COMMIT;
