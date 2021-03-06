CREATE TABLE variable (
  id IDENTITY NOT NULL,
  created datetime DEFAULT NULL,
  modified datetime DEFAULT NULL,
  name varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
  id_creator int DEFAULT NULL,
  id_modificator int DEFAULT NULL,
  id_server int NOT NULL,
  CONSTRAINT FK_5d1vvlwc2xnudfj0gwnfrhb7a FOREIGN KEY (id_server) REFERENCES server (id),
  CONSTRAINT FK_fayeaas4rxt7i1fhnijoquido FOREIGN KEY (id_creator) REFERENCES user (id),
  CONSTRAINT FK_y2q5jvlx878m5kb4fqpuxjri FOREIGN KEY (id_modificator) REFERENCES user (id)
);
