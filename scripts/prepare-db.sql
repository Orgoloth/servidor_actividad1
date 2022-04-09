CREATE DATABASE IF NOT EXISTS servidor_actividad1;
CREATE USER IF NOT EXISTS servidor_actividad1 IDENTIFIED BY 'servidor_actividad1';
GRANT ALL PRIVILEGES ON servidor_actividad1.* TO servidor_actividad1;
FLUSH PRIVILEGES;