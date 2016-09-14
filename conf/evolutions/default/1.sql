# Customer schema
# 
#  --- !Ups

CREATE TABLE "public"."customer" (
"id" serial8 NOT NULL PRIMARY KEY,
"name" varchar(80) COLLATE "default" NOT NULL,
"cnpj" int8 NOT NULL,
"registration" int8 NOT NULL
)


# --- !Downs

DROP TABLE "public".customer