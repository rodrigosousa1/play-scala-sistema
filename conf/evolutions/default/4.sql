# Quote schema
# 
#  --- !Ups

CREATE TABLE "public"."quote" (
"id" serial8 NOT NULL,
"service_to" varchar(30) NOT NULL,
"service_description" varchar(100) NOT NULL,
"date" timestamp NOT NULL,
PRIMARY KEY ("id")
)


# --- !Downs

DROP TABLE "public".quote