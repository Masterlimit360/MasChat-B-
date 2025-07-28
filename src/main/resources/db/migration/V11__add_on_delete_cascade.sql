-- V11__add_on_delete_cascade.sql
-- Drop and recreate all foreign key constraints with ON DELETE CASCADE

ALTER TABLE ad_campaigns DROP CONSTRAINT IF EXISTS fk6k8v1mrgx33v4b7ngsaaaw17q;
ALTER TABLE ad_campaigns ADD CONSTRAINT fk6k8v1mrgx33v4b7ngsaaaw17q FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE ai_chat_messages DROP CONSTRAINT IF EXISTS fkbextknipyw4a78ixtt5f8cjr3;
ALTER TABLE ai_chat_messages ADD CONSTRAINT fkbextknipyw4a78ixtt5f8cjr3 FOREIGN KEY (ai_chat_id) REFERENCES ai_chats(id) ON DELETE CASCADE;

ALTER TABLE ai_chats DROP CONSTRAINT IF EXISTS fkit5cbyac654c4gqpfy20urif9;
ALTER TABLE ai_chats ADD CONSTRAINT fkit5cbyac654c4gqpfy20urif9 FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE attachments DROP CONSTRAINT IF EXISTS fkcf4ta8qdkixetfy7wnqfv3vkv;
ALTER TABLE attachments ADD CONSTRAINT fkcf4ta8qdkixetfy7wnqfv3vkv FOREIGN KEY (message_id) REFERENCES messages(id) ON DELETE CASCADE;
ALTER TABLE attachments DROP CONSTRAINT IF EXISTS fkpeivhpes6blw5rflv2ty65erl;
ALTER TABLE attachments ADD CONSTRAINT fkpeivhpes6blw5rflv2ty65erl FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE;

ALTER TABLE chat_users DROP CONSTRAINT IF EXISTS fklblrwneuhods5qvh9od8yyewy;
ALTER TABLE chat_users ADD CONSTRAINT fklblrwneuhods5qvh9od8yyewy FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE;
ALTER TABLE chat_users DROP CONSTRAINT IF EXISTS fkbbuxrxc67blnxsexqdlfmrpbh;
ALTER TABLE chat_users ADD CONSTRAINT fkbbuxrxc67blnxsexqdlfmrpbh FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE chats DROP CONSTRAINT IF EXISTS fk1y1rf0uqgee45eq5dm773n4gb;
ALTER TABLE chats ADD CONSTRAINT fk1y1rf0uqgee45eq5dm773n4gb FOREIGN KEY (user2_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE chats DROP CONSTRAINT IF EXISTS fkqpix6490s1qlon17w020ax7h2;
ALTER TABLE chats ADD CONSTRAINT fkqpix6490s1qlon17w020ax7h2 FOREIGN KEY (user1_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE comments DROP CONSTRAINT IF EXISTS fkh4c7lvsc298whoyd4w9ta25cr;
ALTER TABLE comments ADD CONSTRAINT fkh4c7lvsc298whoyd4w9ta25cr FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE;
ALTER TABLE comments DROP CONSTRAINT IF EXISTS fk8omq0tc18jd43bu5tjh6jvraq;
ALTER TABLE comments ADD CONSTRAINT fk8omq0tc18jd43bu5tjh6jvraq FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE feed_posts DROP CONSTRAINT IF EXISTS fkkechwr2d2rvscrv8qnieqfc2c;
ALTER TABLE feed_posts ADD CONSTRAINT fkkechwr2d2rvscrv8qnieqfc2c FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE;
ALTER TABLE feed_posts DROP CONSTRAINT IF EXISTS fkk973gq2rrt6jaa5y777x8wxm2;
ALTER TABLE feed_posts ADD CONSTRAINT fkk973gq2rrt6jaa5y777x8wxm2 FOREIGN KEY (feed_id) REFERENCES feeds(id) ON DELETE CASCADE;

ALTER TABLE feeds DROP CONSTRAINT IF EXISTS fka4nmt7wyx9clm9okj61dgd1tw;
ALTER TABLE feeds ADD CONSTRAINT fka4nmt7wyx9clm9okj61dgd1tw FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE friend_requests DROP CONSTRAINT IF EXISTS fktcmqalc5v4qdt1slgcsa544i5;
ALTER TABLE friend_requests ADD CONSTRAINT fktcmqalc5v4qdt1slgcsa544i5 FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE friend_requests DROP CONSTRAINT IF EXISTS fkcchlh48b4347amfvmke793bg7;
ALTER TABLE friend_requests ADD CONSTRAINT fkcchlh48b4347amfvmke793bg7 FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE friend_requests DROP CONSTRAINT IF EXISTS fkmofsv4jjyk6flwplaqwigukl8;
ALTER TABLE friend_requests ADD CONSTRAINT fkmofsv4jjyk6flwplaqwigukl8 FOREIGN KEY (recipient_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE group_members DROP CONSTRAINT IF EXISTS fkkv9vlrye4rmhqjq4qohy2n5a6;
ALTER TABLE group_members ADD CONSTRAINT fkkv9vlrye4rmhqjq4qohy2n5a6 FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE;
ALTER TABLE group_members DROP CONSTRAINT IF EXISTS fknr9qg33qt2ovmv29g4vc3gtdx;
ALTER TABLE group_members ADD CONSTRAINT fknr9qg33qt2ovmv29g4vc3gtdx FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE groups DROP CONSTRAINT IF EXISTS fkkhpvhy2p2c1un4krvhwnau23b;
ALTER TABLE groups ADD CONSTRAINT fkkhpvhy2p2c1un4krvhwnau23b FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE likes DROP CONSTRAINT IF EXISTS fknvx9seeqqyy71bij291pwiwrg;
ALTER TABLE likes ADD CONSTRAINT fknvx9seeqqyy71bij291pwiwrg FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE likes DROP CONSTRAINT IF EXISTS fke4guax66lb963pf27kvm7ikik;
ALTER TABLE likes ADD CONSTRAINT fke4guax66lb963pf27kvm7ikik FOREIGN KEY (comment_id) REFERENCES comments(id) ON DELETE CASCADE;
ALTER TABLE likes DROP CONSTRAINT IF EXISTS fkry8tnr4x2vwemv2bb0h5hyl0x;
ALTER TABLE likes ADD CONSTRAINT fkry8tnr4x2vwemv2bb0h5hyl0x FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE;

ALTER TABLE marketplace_business_accounts DROP CONSTRAINT IF EXISTS fksegn7pwh7moebd5lcj5158ift;
ALTER TABLE marketplace_business_accounts ADD CONSTRAINT fksegn7pwh7moebd5lcj5158ift FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE marketplace_item_images DROP CONSTRAINT IF EXISTS fk8n2ai9ehblthvidchk256m9ts;
ALTER TABLE marketplace_item_images ADD CONSTRAINT fk8n2ai9ehblthvidchk256m9ts FOREIGN KEY (marketplace_item_id) REFERENCES marketplace_items(id) ON DELETE CASCADE;

ALTER TABLE marketplace_items DROP CONSTRAINT IF EXISTS fknih9s7s9apio9nbaph6gmtbk3;
ALTER TABLE marketplace_items ADD CONSTRAINT fknih9s7s9apio9nbaph6gmtbk3 FOREIGN KEY (seller_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE marketplace_items DROP CONSTRAINT IF EXISTS fkf8tpqcr8riwcjb5xad7udnok1;
ALTER TABLE marketplace_items ADD CONSTRAINT fkf8tpqcr8riwcjb5xad7udnok1 FOREIGN KEY (category_id) REFERENCES marketplace_categories(id) ON DELETE CASCADE;

ALTER TABLE marketplace_orders DROP CONSTRAINT IF EXISTS fk2wp0pg3ix0cf0l25sqxap9gkd;
ALTER TABLE marketplace_orders ADD CONSTRAINT fk2wp0pg3ix0cf0l25sqxap9gkd FOREIGN KEY (buyer_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE marketplace_orders DROP CONSTRAINT IF EXISTS fkeu93g82pygmxhsjhefdo7ty2j;
ALTER TABLE marketplace_orders ADD CONSTRAINT fkeu93g82pygmxhsjhefdo7ty2j FOREIGN KEY (item_id) REFERENCES marketplace_items(id) ON DELETE CASCADE;
ALTER TABLE marketplace_orders DROP CONSTRAINT IF EXISTS fkf31svp5jpvlr510lr2hgbyjf9;
ALTER TABLE marketplace_orders ADD CONSTRAINT fkf31svp5jpvlr510lr2hgbyjf9 FOREIGN KEY (seller_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE marketplace_reviews DROP CONSTRAINT IF EXISTS fkgwnr5dxwvgia3nyev4vw1gnkk;
ALTER TABLE marketplace_reviews ADD CONSTRAINT fkgwnr5dxwvgia3nyev4vw1gnkk FOREIGN KEY (reviewer_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE marketplace_reviews DROP CONSTRAINT IF EXISTS fk9o7emn0wm1c6p47rsjhqwys0t;
ALTER TABLE marketplace_reviews ADD CONSTRAINT fk9o7emn0wm1c6p47rsjhqwys0t FOREIGN KEY (item_id) REFERENCES marketplace_items(id) ON DELETE CASCADE;

ALTER TABLE marketplace_saved_searches DROP CONSTRAINT IF EXISTS fk8sa2dn40d9a3bly68aakaneah;
ALTER TABLE marketplace_saved_searches ADD CONSTRAINT fk8sa2dn40d9a3bly68aakaneah FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE mass_coin_transactions DROP CONSTRAINT IF EXISTS fkhdy0oembli34bkcr43j8gq83i;
ALTER TABLE mass_coin_transactions ADD CONSTRAINT fkhdy0oembli34bkcr43j8gq83i FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE mass_coin_transactions DROP CONSTRAINT IF EXISTS fki4dd1bji4qqkkhx66gj9uvsm5;
ALTER TABLE mass_coin_transactions ADD CONSTRAINT fki4dd1bji4qqkkhx66gj9uvsm5 FOREIGN KEY (recipient_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE mass_coin_transfer_requests DROP CONSTRAINT IF EXISTS fkngahi2jql7pcynp34p63qbh1o;
ALTER TABLE mass_coin_transfer_requests ADD CONSTRAINT fkngahi2jql7pcynp34p63qbh1o FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE mass_coin_transfer_requests DROP CONSTRAINT IF EXISTS fk9lg2y0wi1fhmaoido7giq02m5;
ALTER TABLE mass_coin_transfer_requests ADD CONSTRAINT fk9lg2y0wi1fhmaoido7giq02m5 FOREIGN KEY (recipient_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE messages DROP CONSTRAINT IF EXISTS fkhdkwfnspwb3s60j27vpg0rpg6;
ALTER TABLE messages ADD CONSTRAINT fkhdkwfnspwb3s60j27vpg0rpg6 FOREIGN KEY (recipient_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE messages DROP CONSTRAINT IF EXISTS fk64w44ngcpqp99ptcb9werdfmb;
ALTER TABLE messages ADD CONSTRAINT fk64w44ngcpqp99ptcb9werdfmb FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE;
ALTER TABLE messages DROP CONSTRAINT IF EXISTS fk4ui4nnwntodh6wjvck53dbk9m;
ALTER TABLE messages ADD CONSTRAINT fk4ui4nnwntodh6wjvck53dbk9m FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE messages DROP CONSTRAINT IF EXISTS fk_chat;
ALTER TABLE messages ADD CONSTRAINT fk_chat FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE;

ALTER TABLE notifications DROP CONSTRAINT IF EXISTS fk9y21adhxn0ayjhfocscqox7bh;
ALTER TABLE notifications ADD CONSTRAINT fk9y21adhxn0ayjhfocscqox7bh FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE password_reset_tokens DROP CONSTRAINT IF EXISTS fkk3ndxg5xp6v7wd4gjyusp15gq;
ALTER TABLE password_reset_tokens ADD CONSTRAINT fkk3ndxg5xp6v7wd4gjyusp15gq FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE post_likes DROP CONSTRAINT IF EXISTS fka5wxsgl4doibhbed9gm7ikie2;
ALTER TABLE post_likes ADD CONSTRAINT fka5wxsgl4doibhbed9gm7ikie2 FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE;
ALTER TABLE post_likes DROP CONSTRAINT IF EXISTS fkkgau5n0nlewg6o9lr4yibqgxj;
ALTER TABLE post_likes ADD CONSTRAINT fkkgau5n0nlewg6o9lr4yibqgxj FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE posts DROP CONSTRAINT IF EXISTS fk5lidm6cqbc7u4xhqpxm898qme;
ALTER TABLE posts ADD CONSTRAINT fk5lidm6cqbc7u4xhqpxm898qme FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE reel_comments DROP CONSTRAINT IF EXISTS fkeke1trrxf1tlm1air9j0bbnv;
ALTER TABLE reel_comments ADD CONSTRAINT fkeke1trrxf1tlm1air9j0bbnv FOREIGN KEY (reel_id) REFERENCES reels(id) ON DELETE CASCADE;
ALTER TABLE reel_comments DROP CONSTRAINT IF EXISTS fkhhle3yobt5nimcnh2vtvih8eq;
ALTER TABLE reel_comments ADD CONSTRAINT fkhhle3yobt5nimcnh2vtvih8eq FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE reel_likes DROP CONSTRAINT IF EXISTS fkqkv0mkool4r72pncxgsp3xvlo;
ALTER TABLE reel_likes ADD CONSTRAINT fkqkv0mkool4r72pncxgsp3xvlo FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE reel_likes DROP CONSTRAINT IF EXISTS fkb8ly1bthad5sbqsraw2l0ffuq;
ALTER TABLE reel_likes ADD CONSTRAINT fkb8ly1bthad5sbqsraw2l0ffuq FOREIGN KEY (reel_id) REFERENCES reels(id) ON DELETE CASCADE;

ALTER TABLE reels DROP CONSTRAINT IF EXISTS fkdj712qasvfd6vi19pdhkvp5f1;
ALTER TABLE reels ADD CONSTRAINT fkdj712qasvfd6vi19pdhkvp5f1 FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE stories DROP CONSTRAINT IF EXISTS fkshv2ytgbsn9w9mpu43mc6ln6j;
ALTER TABLE stories ADD CONSTRAINT fkshv2ytgbsn9w9mpu43mc6ln6j FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE story_likes DROP CONSTRAINT IF EXISTS fka8fytw09g74d1n80nbn6clbt8;
ALTER TABLE story_likes ADD CONSTRAINT fka8fytw09g74d1n80nbn6clbt8 FOREIGN KEY (story_id) REFERENCES stories(id) ON DELETE CASCADE;
ALTER TABLE story_likes DROP CONSTRAINT IF EXISTS fko2p5ura3qbwbn1lb46om8en1w;
ALTER TABLE story_likes ADD CONSTRAINT fko2p5ura3qbwbn1lb46om8en1w FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE user_details DROP CONSTRAINT IF EXISTS user_details_user_id_fkey;
ALTER TABLE user_details ADD CONSTRAINT user_details_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE user_friends DROP CONSTRAINT IF EXISTS fkk08ugelrh9cea1oew3hgxryw2;
ALTER TABLE user_friends ADD CONSTRAINT fkk08ugelrh9cea1oew3hgxryw2 FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE user_friends DROP CONSTRAINT IF EXISTS fk11y5boh1e7gh60rdqixyetv3x;
ALTER TABLE user_friends ADD CONSTRAINT fk11y5boh1e7gh60rdqixyetv3x FOREIGN KEY (friend_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE user_settings DROP CONSTRAINT IF EXISTS fk8v82nj88rmai0nyck19f873dw;
ALTER TABLE user_settings ADD CONSTRAINT fk8v82nj88rmai0nyck19f873dw FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE user_wallets DROP CONSTRAINT IF EXISTS fk423n8ap6gdudl8fcab7ugv3qt;
ALTER TABLE user_wallets ADD CONSTRAINT fk423n8ap6gdudl8fcab7ugv3qt FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE users DROP CONSTRAINT IF EXISTS fk4k6tolrdcqmbkue04ivjjs7ry;
ALTER TABLE users ADD CONSTRAINT fk4k6tolrdcqmbkue04ivjjs7ry FOREIGN KEY (details_id) REFERENCES user_details(id) ON DELETE CASCADE;