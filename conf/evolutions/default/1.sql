# Customer schema
# 
#  --- !Ups

CREATE TABLE "public"."customer" (
"id" serial8 NOT NULL PRIMARY KEY,
"name" varchar(80) COLLATE "default" NOT NULL,
"cnpj" varchar(14) NOT NULL,
"registration" varchar(11) NOT NULL
)


# --- !Downs

DROP TABLE "public".customer