# Address schema
# 
#  --- !Ups

CREATE TABLE "public"."address" (
"id" serial8 NOT NULL,
"customer_id" int8 NOT NULL,
"street" varchar(50) NOT NULL,
"neighborhood" varchar(30) NOT NULL,
"city" varchar(15) NOT NULL,
"state" varchar(2) NOT NULL,
"cep" varchar(8) NOT NULL,
PRIMARY KEY ("id"),
CONSTRAINT "customer_fk" FOREIGN KEY ("customer_id") REFERENCES "public"."customer" ("id") ON DELETE CASCADE
)


# --- !Downs

DROP TABLE "public".address