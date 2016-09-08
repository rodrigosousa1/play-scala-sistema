# Cliente schema
# 
#  --- !Ups

CREATE TABLE "public"."cliente" (
"id" SERIAL NOT NULL PRIMARY KEY,
"razao_social" varchar(80) COLLATE "default" NOT NULL,
"cnpj" int8 NOT NULL,
"inscricao_municipal" int8 NOT NULL
)


# --- !Downs

DROP TABLE "public".cliente