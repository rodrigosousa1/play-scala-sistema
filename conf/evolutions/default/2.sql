# Phone schema
# 
#  --- !Ups

CREATE TABLE "public"."phone" (
"id" serial8 NOT NULL,
"customer_id" int8 NOT NULL,
"number" int8 NOT NULL,
PRIMARY KEY ("id"),
CONSTRAINT "customer_fk" FOREIGN KEY ("customer_id") REFERENCES "public"."customer" ("id") ON DELETE CASCADE
)


# --- !Downs

DROP TABLE "public".phone