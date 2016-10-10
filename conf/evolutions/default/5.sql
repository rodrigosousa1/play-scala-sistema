# Item schema
# 
#  --- !Ups

CREATE TABLE "public"."item" (
"id" serial8 NOT NULL,
"quote_id" int8 NOT NULL,
"quantity" int4 NOT NULL,
"description" varchar(100) NOT NULL,
"price" float4 NOT NULL,
"total" float4 NOT NULL,
PRIMARY KEY ("id"),
CONSTRAINT "quote_fk" FOREIGN KEY ("quote_id") REFERENCES "public"."quote" ("id") ON DELETE CASCADE
)


# --- !Downs

DROP TABLE "public".item
