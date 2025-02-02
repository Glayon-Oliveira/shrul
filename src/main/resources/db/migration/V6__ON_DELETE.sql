ALTER TABLE prefixes DROP FOREIGN KEY prefixes_ibfk_1;
ALTER TABLE links DROP FOREIGN KEY links_ibfk_1;
ALTER TABLE url_access DROP FOREIGN KEY url_access_ibfk_1;

ALTER TABLE prefixes ADD CONSTRAINT prefixes_ibfk_1 FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE links ADD CONSTRAINT links_ibfk_1 FOREIGN KEY(prefix_id) REFERENCES prefixes(id) ON DELETE CASCADE;
ALTER TABLE url_access ADD CONSTRAINT url_access_ibfk_1 FOREIGN KEY(link_id) REFERENCES links(id) ON DELETE CASCADE;