-- cria usuário
CREATE ROLE sistemaspu WITH
	NOSUPERUSER
	NOCREATEDB
	NOCREATEROLE
	INHERIT
	NOREPLICATION
	CONNECTION LIMIT -1;

-- cria senha para o usuário
ALTER ROLE sistemaspu
	PASSWORD '[SENHA AQUI]';

-- cria banco com grant ao usuário criado
CREATE DATABASE sistemaspu
    WITH 
    OWNER = sistemaspu
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

GRANT ALL ON DATABASE sistemaspu TO sistemaspu;