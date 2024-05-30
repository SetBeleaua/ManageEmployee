CREATE TABLE IF NOT EXISTS employee (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255),
                          email VARCHAR(255),
                          phone VARCHAR(255),
                          address VARCHAR(255),
                          city VARCHAR(255),
                          state VARCHAR(255),
                          position VARCHAR(255)
);