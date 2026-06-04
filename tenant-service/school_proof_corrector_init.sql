SELECT 'CREATE DATABASE school_pick ENCODING = ''UTF8'' TEMPLATE template0'
WHERE NOT EXISTS (
    SELECT FROM pg_database WHERE datname = 'school_pick'
)\gexec