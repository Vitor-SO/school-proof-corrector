CREATE TABLE IF NOT EXISTS  public.tenants (
  id           uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  school_name  text UNIQUE NOT NULL,
  db_name      text UNIQUE NOT NULL,
  db_user      text UNIQUE NOT NULL,
  db_password  text NOT NULL,
  status       text NOT NULL DEFAULT 'active',
  created_at   TIMESTAMP NOT NULL DEFAULT now(),
  updated_at   TIMESTAMP NOT NULL DEFAULT now()
);

