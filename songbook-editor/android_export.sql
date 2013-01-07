CREATE TABLE "android_metadata" ("locale" TEXT DEFAULT 'en_US');
INSERT INTO "android_metadata" VALUES ('en_US');
ALTER TABLE "songs" ADD COLUMN _id INTEGER PRIMARY KEY;